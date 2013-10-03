import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class WebFrame extends JFrame {
    private static final String DEFAULT_DIR = System.getProperty("user.dir") + "\\hw4\\";
    private static final String FILENAME = DEFAULT_DIR + "links.txt";
    private static final int INPUT_FIELD_WIDTH = 4;

    private DefaultTableModel model;
    private JTable table;
    private JPanel panel;

    private JButton singleThreadButton;
    private JButton concurrentButton;
    private JButton stopButton;

    private JLabel completedLabel;
    private JLabel runningLabel;
    private JLabel elapsedLabel;

    private JTextField threadNumInputField;
    private JProgressBar progressBar;

    private long startTime;
    private AtomicInteger threadsRunning;
    private AtomicInteger threadsComplete;
    private Semaphore workersCounter;


    public WebFrame() {
        super("WebLoader");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        model = new DefaultTableModel(new String[]{"url", "status"}, 0);
        loadSiteList();
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 300));

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        //Below the table, install the three buttons, three JLabels, one JTextField and one JProgressBar
        singleThreadButton = new JButton("Single Thread Fetch");
        concurrentButton = new JButton("Concurrent Fetch");
        stopButton = new JButton("Stop");
        stopButton.setEnabled(false);

        runningLabel = new JLabel("Running:");
        completedLabel = new JLabel("Completed:");
        elapsedLabel = new JLabel("Elapsed:");

        threadNumInputField = new JTextField("4", INPUT_FIELD_WIDTH);
        threadNumInputField.setMaximumSize(threadNumInputField.getPreferredSize());

        progressBar = new JProgressBar(0, model.getRowCount());

        addActionListeners();

        panel.add(scrollPane);
        panel.add(singleThreadButton);
        panel.add(concurrentButton);
        panel.add(threadNumInputField);
        panel.add(runningLabel);
        panel.add(completedLabel);
        panel.add(elapsedLabel);
        panel.add(progressBar);
        panel.add(stopButton);

        add(panel);

        pack();
        setVisible(true);
    }


    private static final int MULTIPLE_THREADS = 0;
    private static final int SINGLE_THREAD = 1;
    private WebLauncher launcher = null;

    private void addActionListeners() {

        singleThreadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (launcher != null) launcher.interrupt();
                startLauncher(SINGLE_THREAD);
                startFetchAnimation();
            }
        });

        concurrentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (launcher != null) launcher.interrupt();
                startLauncher(MULTIPLE_THREADS);
                startFetchAnimation();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (launcher != null) launcher.interrupt();
                launcher = null;
                stopFetchAnimation();
            }
        });
    }

    private void startLauncher(int threadsNum) {
        if (threadsNum == MULTIPLE_THREADS) {
            try {
                threadsNum = Integer.parseInt(threadNumInputField.getText());
            } catch (NumberFormatException ignore) {
                threadsNum = SINGLE_THREAD;     // will run only 1 thread if user entered not int
            }
        }
        launcher = new WebLauncher(threadsNum, model.getRowCount(), this);
        launcher.start();
    }

     /*
     *
     *
     * Table model methods
     * Can probably make it in any thread... don't know the correct way...
     *
     *    SwingUtilities.invokeLater(new Runnable() {
     *        public void run() {
     *            model.setValueAt(data, row, 1);
     *        }
     *    });
     *
     * And don't really know, do i need to synchronize it or not
     *   synchronized (model){
     *       return (String) model.getValueAt(row,0);
     *   }
     *
     * */

    public void changeTableData(final String data, final int row) {
        model.setValueAt(data, row, 1);
    }

    public String getUrl(int row) {
         return (String) model.getValueAt(row, 0);
    }


    public void startFetchAnimation() {
        startTime = System.currentTimeMillis();
        singleThreadButton.setEnabled(false);
        concurrentButton.setEnabled(false);
        stopButton.setEnabled(true);
        progressBar.setValue(0);
        for (int i = 0; i < model.getRowCount(); i++)  //reset model
            model.setValueAt("", i, 1);

    }

    public void stopFetchAnimation() {
        singleThreadButton.setEnabled(true);
        concurrentButton.setEnabled(true);
        stopButton.setEnabled(false);

    }
    // the last method called from WebWorker
    public void releaseWorker(String result, int row) {
        changeTableData(result, row);
        threadsComplete.incrementAndGet();
        threadsRunning.decrementAndGet();
        workersCounter.release();
        updateUIProgress();
    }

    private void updateUIProgress() {
        final long elapsedTime = System.currentTimeMillis() - startTime;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                runningLabel.setText("Running:" + threadsRunning);
                completedLabel.setText("Complete:" + threadsComplete);
                progressBar.setValue(threadsComplete.get());
                elapsedLabel.setText("Elapsed:" + elapsedTime);
            }
        });
    }

    public class WebLauncher extends Thread {
        private ArrayList<WebWorker> workers = new ArrayList<WebWorker>();
        private int urlNum;
        private WebFrame frame;

        public WebLauncher(int numOfThreads, int urlNum, WebFrame frame) {
            threadsRunning = new AtomicInteger(0);
            threadsComplete = new AtomicInteger(0);
            workersCounter = new Semaphore(numOfThreads);
            this.urlNum = urlNum;
            this.frame = frame;
        }

        public void run() {
            threadsRunning.incrementAndGet();
            try {
                initWorkers();
                startWorkers();
            } catch (InterruptedException e) {
                interruptAllWorkers();
            }
            threadsRunning.decrementAndGet();

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    stopFetchAnimation();
                }
            });
        }

        private void initWorkers() throws InterruptedException {
            for (int i = 0; i < urlNum; i++) {
                if (isInterrupted()) throw new InterruptedException();
                WebWorker worker = new WebWorker(getUrl(i), i, frame);
                workers.add(worker);
            }
        }

        private void startWorkers() throws InterruptedException {
            for (WebWorker worker : workers) {
                if (isInterrupted()) throw new InterruptedException();
                workersCounter.acquire();
                threadsRunning.incrementAndGet();
                worker.start();
            }

            for (WebWorker worker : workers)
                worker.join();

        }

        private void interruptAllWorkers() {
            for (WebWorker w : workers) w.interrupt();
        }
    }

    private void loadSiteList() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(FILENAME)));

            while (true) {
                String line = br.readLine();
                if (line == null) break;
                model.addRow(new String[]{line, ""});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new WebFrame();
            }
        });
    }
}
