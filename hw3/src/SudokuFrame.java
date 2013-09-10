

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;


 public class SudokuFrame extends JFrame {

    JTextArea puzzleText;
    JTextArea solutionText;

    Box boxMenu;
    JButton checkButton;
    JCheckBox autoCheckBox;

	public SudokuFrame() {
		super("Sudoku Solver");

		setLayout(new BorderLayout(4, 4));

        // 2 Text Areas Initialisation
        puzzleText = new JTextArea(15,20);
        solutionText = new JTextArea(15,20);

        puzzleText.setBorder(new TitledBorder("Puzzle"));
        solutionText.setBorder(new TitledBorder("Solution"));
        solutionText.setEditable(false);

        // Box Menu Initialisation
        boxMenu = Box.createHorizontalBox();
        checkButton = new JButton("Check");
        autoCheckBox = new JCheckBox("Auto Check");
        boxMenu.add(checkButton);
        boxMenu.add(autoCheckBox);

        // Setting Action Listeners
        puzzleText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(autoCheckBox.isSelected())
                    reDrawSolutions();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(autoCheckBox.isSelected())
                    reDrawSolutions();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if(autoCheckBox.isSelected())
                    reDrawSolutions();
            }
        });
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               reDrawSolutions();
            }
        });

        // Adding everything on Frame
        add(boxMenu, BorderLayout.SOUTH);
        add(puzzleText, BorderLayout.CENTER);
        add(solutionText, BorderLayout.EAST);

		// Was already in project.
		setLocationByPlatform(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

     private void reDrawSolutions() {
         String solution = "";
         try{
             Sudoku sudoku = new Sudoku(puzzleText.getText());
             int count = sudoku.solve();
             solution = sudoku.getSolutionText();
             solution+="\nsolutions:"+count;
             solution+="\nelapsed:"+ sudoku.getElapsed() + "ms";
         } catch (Exception ex ){
             solution = "Invalid data format";
         }
         solutionText.setText(solution);

     }


     public static void main(String[] args) {
		// GUI Look And Feel
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.getLookAndFeelDefaults()
                    .put("defaultFont", new Font("Arial", Font.BOLD, 14));
		} catch (Exception ignored) { }
		
		SudokuFrame frame = new SudokuFrame();
	}

}
