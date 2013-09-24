import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JCount extends JPanel {
   private final static int COUNT_NUM = 4;
   private static final int INPUT_FIELD_SIZE = 6;

   private Worker worker = null;

   private JTextField inputText;
   private JLabel infoLabel;
   private JButton startButton;
   private JButton stopButton;

   public JCount(){
       super();
       setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
       inputText = new JTextField("100000000", INPUT_FIELD_SIZE);
       infoLabel = new JLabel("0");
       startButton = new JButton("Start");
       stopButton = new JButton("Stop");

       startButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
              if(worker != null)
                  worker.interrupt();

               worker = new Worker(inputText.getText());
               worker.start();

           }
       });

       stopButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if (worker != null){
                   worker.interrupt();
                   worker = null;
               }
           }
       });


       add(inputText);
       add(infoLabel);
       add(startButton);
       add(stopButton);
       add(Box.createRigidArea(new Dimension(0, 40)));

   }
   private class Worker extends Thread{
       private static final int REFRESH_INTERVAL = 10000;
       private int num;

       public Worker(String number){
        this.num = Integer.parseInt(number);
       }

       @Override
       public void run(){
           // System.out.println(Thread.currentThread().getId()+ " Started");
          for(int i = 0; i <= num;i++){


              if (isInterrupted()) {
                  // System.out.println(Thread.currentThread().getId()+ " Interrupted from if");
                  break;
              }

              if(i % REFRESH_INTERVAL == 0){
                  try {
                      Thread.sleep(10);
                  } catch (InterruptedException ex) {
                      //System.out.println(Thread.currentThread().getId()+ " Interrupted from exception");
                      break;
                  }
                  final String displayText = i + "";
                  SwingUtilities.invokeLater( new Runnable() {
                      @Override
                      public void run() {
                          infoLabel.setText(displayText);
                      }
                  });
              }


          }
       }
   }
    private static void createAndShowGUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().setLayout(
                new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        for(int i = 0;i < COUNT_NUM; i++){
            frame.add(new JCount());
        }

        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });


    }


}
