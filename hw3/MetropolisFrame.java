import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MetropolisFrame extends JFrame{

    private static final int TEXT_FIELDS_SIZE = 10;
    private static final String POPULATION_SMALLER = "Population Smaller Then";
    private static final String POPULATION_LARGER  = "Population Larger Then";
    private static final String EXACT_MATCH = "Exact Match";
    private static final String PARTIAL_MATCH = "Partial Match";

    private JTextField metropolisField;
    private JTextField continentField;
    private JTextField populationField;
    private JComboBox populationPulldown;
    private JComboBox matchTypePulldown;
    private JButton addButton;
    private JButton searchButton;
    private JTable dataTable;

    private MetropolisModel metropolisModel;

    public MetropolisFrame(){
        super("Metropolis Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(4,4));

        // Top menu
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

        // Center data table
        metropolisModel = new MetropolisModel();
        dataTable = new JTable(metropolisModel);
        JScrollPane scrollTable = new JScrollPane(dataTable);
        scrollTable.setPreferredSize(new Dimension(300,200));

        // Side menu
        Box sideMenu = Box.createVerticalBox();

        addButton = new JButton("Add");
        searchButton = new JButton("Search");

        Box innerSideMenu = Box.createVerticalBox();
        innerSideMenu.setBorder(new TitledBorder("Search options"));
        populationPulldown = new JComboBox(new String[]{POPULATION_LARGER, POPULATION_SMALLER});
        matchTypePulldown = new JComboBox(new String[]{EXACT_MATCH, PARTIAL_MATCH});

        alignSideMenu();
        addActionListeners();

        // Adding everything on side menu panel
        sideMenu.add(addButton);
        sideMenu.add(searchButton);
        innerSideMenu.add(populationPulldown);
        innerSideMenu.add(matchTypePulldown);
        sideMenu.add(innerSideMenu);

        // Adding everything on frame
        add(topPanel, BorderLayout.NORTH);
        add(scrollTable, BorderLayout.CENTER);
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
                boolean exact = matchTypePulldown.getSelectedItem().equals(EXACT_MATCH);
                metropolisModel.search(metropolisField.getText(),
                        continentField.getText(), populationField.getText(), larger, exact);


            }
        });
    }
    private void alignSideMenu() {
        addButton.setAlignmentX( JComponent.LEFT_ALIGNMENT);
        searchButton.setAlignmentX( JComponent.LEFT_ALIGNMENT);
        populationPulldown.setAlignmentX( JComponent.LEFT_ALIGNMENT);
        matchTypePulldown.setAlignmentX( JComponent.LEFT_ALIGNMENT);

        Dimension menuSize = new Dimension(matchTypePulldown.getMaximumSize().width,
                                           matchTypePulldown.getPreferredSize().height);

        matchTypePulldown.setMaximumSize(menuSize);
        populationPulldown.setMaximumSize(menuSize);
    }


    public static void main(String[]args){
        try {
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.getLookAndFeelDefaults()
                    .put("defaultFont", new Font("Arial", Font.BOLD, 14));
        } catch (Exception ignored) {
        }

        MetropolisFrame frame = new MetropolisFrame();
    }
}
