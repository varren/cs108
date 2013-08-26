import javax.swing.*;
import java.awt.*;

public class JBrainTetris extends JTetris {

    private Brain brain;
    private boolean seenPiece;
    private Brain.Move move;


    //GUI
    private JCheckBox brainMode;
    private JCheckBox animateMode;
    private JSlider  adversary;
    private JPanel little;
    private JLabel adversaryStatus;

    /**
     * Creates a new JTetris where each tetris square
     * is drawn with the given number of pixels.
     */
    JBrainTetris(int pixels) {
        super(pixels);
        brain = new DefaultBrain();
    }

    @Override
    public JComponent createControlPanel() {
        JPanel panel = (JPanel)super.createControlPanel();

        // make a little panel, put a JSlider in it. JSlider responds to getValue()
        little = new JPanel();
        // COUNT
        adversaryStatus = new JLabel("OK ");
        little.add(adversaryStatus);
        little.add(new JLabel("Adversary:"));

        adversary = new JSlider(0, 100, 0); // min, max, current
        adversary.setPreferredSize(new Dimension(100,15));
        little.add(adversary); // now add little to panel of controls

        panel.add(little);



        // Brain active
        brainMode = new JCheckBox("Brain active");
        panel.add(brainMode);

        // Animate Falling
        animateMode = new JCheckBox("Animate Falling");
        animateMode.setSelected(true);
        panel.add(animateMode);

        return panel;
    }

    @Override
    public void tick(int verb) {

        if (brainMode.isSelected() && verb == DOWN) {

            if (!seenPiece) {
                seenPiece = !seenPiece;
                board.undo();
                move = brain.bestMove(board, currentPiece, board.getHeight(), move);
            }

            if(move!=null){
                if      (!move.piece.equals(currentPiece))        super.tick(ROTATE);
                if      (move.x > currentX)                       super.tick(RIGHT);
                else if (move.x < currentX)                       super.tick(LEFT);
                else if (!animateMode.isSelected() &&
                         move.x == currentX && currentY > move.y) super.tick(DROP);
            }
        }

        super.tick(verb);
    }

    @Override
    public void addNewPiece() {
        seenPiece = false;
        super.addNewPiece();
    }

    @Override
    public Piece pickNextPiece() {
        if (random.nextInt(99) < adversary.getValue()) {
            Piece nextPiece = super.pickNextPiece();

            adversaryStatus.setText("OK*");
            double worstScore = 0;
            for (Piece piece : pieces) {
                board.undo();
                Brain.Move nextMove = brain.bestMove(board, piece, board.getHeight(), null);
                if (nextMove != null && nextMove.score > worstScore)   {
                    nextPiece = piece;
                    worstScore = nextMove.score;
                }
            }
            return nextPiece;
        }

        adversaryStatus.setText("OK ");
        return super.pickNextPiece();




    }
    /**
     Creates a frame with a JTetris.
     */
    public static void main(String[] args) {
        // Set GUI Look And Feel Boilerplate.
        // Do this incantation at the start of main() to tell Swing
        // to use the GUI LookAndFeel of the native platform. It's ok
        // to ignore the exception.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        JBrainTetris tetris = new JBrainTetris(16);
        JFrame frame = JTetris.createFrame(tetris);
        frame.setVisible(true);
    }
}
