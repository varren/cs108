import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DBFrame extends JFrame{

    private static final int TEXT_FIELDS_SIZE = 10;
    private static final String POPULATION_SMALLER = "Population Smaller Then";
    private static final String POPULATION_LARGER  = "Population Larger Then";
    private static final String MATCH_OPTION1  = "Exact Match";
    private static final String MATCH_OPTION2  = "Partial Match";

    private JTextField metropolisField;
    private JTextField continentField;
    private JTextField populationField;
    private JComboBox populationPulldown;
    private JComboBox matchTypePulldown;
    private JButton addButton;
    private JButton searchButton;
    JTable dataTable;

    private MetropolisModel metropolisModel;

    public DBFrame(){
        super("Metropolis Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout(4,4));

        //Top menu
        JPanel topPanel = new JPanel();

        metropolisField = new JTextField(TEXT_FIELDS_SIZE);
        continentField = new JTextField(TEXT_FIELDS_SIZE);
        populationField = new JTextField(TEXT_FIELDS_SIZE);

        topPanel.add(new JLabel(" Metropolis: "));
        topPanel.add(metropolisField);
        topPanel.add(new JLabel(" Continent: "));
        topPanel.add(continentField);
        topPanel.add(new JLabel(" Population: "));
        topPanel.add(populationField);

        //Center data table
        metropolisModel = new MetropolisModel();

        dataTable = new JTable(metropolisModel);

        //Side menu
        Box sideMenu = Box.createVerticalBox();

        addButton = new JButton("Add");
        searchButton = new JButton("Search");

        Box innerSideMenu = Box.createVerticalBox();
        innerSideMenu.setBorder(new TitledBorder("Search options"));

        populationPulldown = new JComboBox(new String[]{POPULATION_LARGER, POPULATION_SMALLER});
        matchTypePulldown = new JComboBox(new String[]{MATCH_OPTION1, MATCH_OPTION2});

        addButton.setAlignmentX( JComponent.LEFT_ALIGNMENT);
        searchButton.setAlignmentX( JComponent.LEFT_ALIGNMENT);
        populationPulldown.setAlignmentX( JComponent.LEFT_ALIGNMENT);
        matchTypePulldown.setAlignmentX( JComponent.LEFT_ALIGNMENT);

        setMaxSize(populationPulldown);
        setMaxSize(matchTypePulldown);

        addActionListeners();

        // Adding everything on field
        sideMenu.add(addButton);
        sideMenu.add(searchButton);
        innerSideMenu.add(populationPulldown);
        innerSideMenu.add(matchTypePulldown);
        sideMenu.add(innerSideMenu);

        add(topPanel, BorderLayout.NORTH);
        add(dataTable,BorderLayout.CENTER);
        add(sideMenu,BorderLayout.EAST);

        pack();
        setVisible(true);
    }

    private void addActionListeners(){


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metropolisModel.add(metropolisField.getText(),continentField.getText(),populationField.getText());

            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean larger = populationPulldown.getSelectedItem().equals(POPULATION_LARGER);
                boolean exact = matchTypePulldown.getSelectedItem().equals(MATCH_OPTION1);
                metropolisModel.search(metropolisField.getText(),
                        continentField.getText(), populationField.getText(), larger, exact);


            }
        });
    }

    private void setMaxSize(JComponent jc)
    {
        Dimension max = jc.getMaximumSize();
        Dimension pref = jc.getPreferredSize();
        max.height = pref.height;
        jc.setMaximumSize(max);
    }

    public static void main(String[]args){
        try {
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.getLookAndFeelDefaults()
                    .put("defaultFont", new Font("Arial", Font.BOLD, 14));
        } catch (Exception ignored) {
        }

        DBFrame frame = new DBFrame();
    }
}
