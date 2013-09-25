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
    private static final String DEFAULT_DIR = System.getProperty("user.dir")+"\\hw4\\";
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

    private long elapsedTime;
    private AtomicInteger threadsRunning;
    private AtomicInteger threadsComplete;

    public WebFrame(){
        super("WebLoader");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        model = new DefaultTableModel(new String[]{"url", "status"}, 0);
        loadSiteList();
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600,300));

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

        threadNumInputField = new JTextField("4",INPUT_FIELD_WIDTH);
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

    /*
    *
    *
    * Action listeners
    *
    *
    * */

     private static final int SINGLE_THREAD = 1;
    private WebLauncher launcher = null;
    private void addActionListeners() {

        singleThreadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(launcher != null){
                    launcher.interrupt();
                }
                launcher = new WebLauncher(SINGLE_THREAD, model.getRowCount());
                launcher.start();
            }
        });

        concurrentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(launcher != null)
                    launcher.interrupt();

                launcher = new WebLauncher(threadNumInputField.getText(),model.getRowCount());
                launcher.start();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(launcher != null)
                    launcher.interrupt();

                launcher = null;
            }
        });
    }

     /*
     *
     *
     * Table model methods
     *
     *
     * */

      public void changeTableData(final String data,final int row){
         //can probably make it in any thread... don't know the correct way...
         SwingUtilities.invokeLater(new Runnable() {
             public void run() {
                 model.setValueAt(data, row, 1);
             }
         });
     }

    public String getUrl(int row){
        synchronized (model){  //don't really know, do i need to synchronize it or not
           return (String) model.getValueAt(row,0);
        }
    }

    /*
    *
    *
    * Update UI methods
    *
    *
    * */

     public void startFetchAnimation(){
        elapsedTime = System.currentTimeMillis();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                singleThreadButton.setEnabled(false);
                concurrentButton.setEnabled(false);
                stopButton.setEnabled(true);
                progressBar.setValue(0);

                //reset model
                for(int i =0;i<model.getRowCount();i++)
                    model.setValueAt("",i,1);
            }
        });
    }

    public void stopFetchAnimation() {
        elapsedTime = System.currentTimeMillis() - elapsedTime;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                singleThreadButton.setEnabled(true);
                concurrentButton.setEnabled(true);
                stopButton.setEnabled(false);
                elapsedLabel.setText("Elapsed:" + elapsedTime);

            }
        });
    }

    private void updateUIProgress(){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                runningLabel.setText("Running:" + threadsRunning);
                completedLabel.setText("Complete:" + threadsComplete);
                progressBar.setValue(threadsComplete.get());
            }
        });


    }

    /*
    *
    *
    * WebLauncher is WebFrame inner class to launch all the worker Threads
    *
    *
    * */

     public class WebLauncher extends Thread{
        private Semaphore workersCounter;
        private ArrayList<WebWorker> workers = new ArrayList<WebWorker>();
        private int numberOfSites;

        public WebLauncher(int numOfThreads, int numberOfSites){
            threadsRunning = new AtomicInteger(0);
            threadsComplete = new AtomicInteger(0);
            workersCounter = new Semaphore(numOfThreads);
            this.numberOfSites = numberOfSites;
        }

        public WebLauncher(String numOfThreads,int numberOfSites){
            this(Integer.parseInt(numOfThreads),numberOfSites);
        }

        public void run(){
            startFetchAnimation();
            initWorkers();
            startWorkers();
            stopFetchAnimation();
        }

         private void initWorkers() {
             for(int i = 0; i < numberOfSites;i++){
                 if(isInterrupted())  break;
                 WebWorker worker = new WebWorker(getUrl(i), i, this);
                 workers.add(worker);
             }
         }

        private void startWorkers() {
            for (WebWorker worker: workers){
                if(isInterrupted()){
                    for(WebWorker w: workers) w.interrupt();
                    break;
                }

                try {
                    workersCounter.acquire();
                    threadsRunning.incrementAndGet();
                    worker.start();
                    updateUIProgress();
                } catch (InterruptedException e) {
                    for(WebWorker w: workers) w.interrupt();
                    break;
                }
            }

            for(WebWorker worker: workers)
                try {
                    worker.join();
                } catch (InterruptedException e) {
                    for(WebWorker w: workers) w.interrupt();
                    break;
                }
        }

        // the last method called from WebWorker
        public void workerFinishedWith( String result, int row){
            if(isInterrupted()) result = WebWorker.INTERRUPTED;
            threadsComplete.incrementAndGet();
            threadsRunning.decrementAndGet();
            changeTableData(result,row);
            updateUIProgress();
            workersCounter.release();

        }
    }

    /*
    *
    *
    * Method loads file with URLs ( from constant filename) into the table model
    *
    *
    * */

     private void loadSiteList() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(FILENAME)));

            while(true){
                String line = br.readLine();
                if(line == null)break;
                model.addRow(new String []{line, ""});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    *
    *
    * main
    *
    *
    * */
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new WebFrame();
            }
        });

    }
}
