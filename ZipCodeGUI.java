import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

/*************************************************************
 * GUI for a Zip Code Database
 * 
 * @author Scott Grissom/Kevin Rufino
 * CIS 162
 ************************************************************/
public class ZipCodeGUI extends JFrame implements ActionListener{

    /** Results */
    JTextArea results;

    private ZipCodeDatabase data;

    /** menu items */
    private JMenuBar menus;
    private JMenu fileMenu;
    private JMenuItem openItem;
    private JMenuItem countItem;
    private JMenuItem aboutItem;
    private JMenuItem quitItem;

    /** JButtons */
    private JButton disBtn;
    private JButton findZipBtn;
    private JButton radZipBtn;
    private JButton nameSearchBtn;
    private JButton furthestZipBtn;

    /** JTextFields */
    private JTextField zipOneTF;
    private JTextField zipTwoTF;
    private JTextField radiusTF;
    private JTextField nameTF;

    /*****************************************************************
     * Main Method
     ****************************************************************/ 
    public static void main(String args[]){
        ZipCodeGUI gui = new ZipCodeGUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setTitle("Kevin Rufino");
        gui.pack();
        gui.setVisible(true);
    }

    /*****************************************************************
     * constructor installs all of the GUI components
     ****************************************************************/    
    public ZipCodeGUI(){   
        // instantiate the database object
        data =  new ZipCodeDatabase();
        setupGUI();
        setupMenus();
    }

    private void setupGUI(){
        // set the layout to GridBag
        setLayout(new GridBagLayout());
        GridBagConstraints loc = new GridBagConstraints();

        //instantiate JButtons
        disBtn = new JButton ("distance between Zip 1 and 2");
        findZipBtn = new JButton ("find Zip 1");
        radZipBtn = new JButton ("within radius of Zip 1");
        nameSearchBtn = new JButton ("search by name");
        furthestZipBtn = new JButton ("furthest from Zip 1");

        //instantiate JTextFields
        zipOneTF = new JTextField (8);
        zipTwoTF = new JTextField (8);
        radiusTF = new JTextField (5);
        nameTF = new JTextField (12);

        // create results area to span one column and 11 rows
        results = new JTextArea(20,20);
        JScrollPane scrollPane = new JScrollPane(results);
        loc.gridx = 0;
        loc.gridy = 1;
        loc.gridheight = 11;
        loc.insets = new Insets(0,20,20,20);
        add(scrollPane, loc);

        // create Results label
        loc = new GridBagConstraints();
        loc.gridx = 0;
        loc.gridy = 0;
        loc.insets.bottom = 20;
        add(new JLabel("Results"), loc);
        loc.gridwidth = 2;
        // create choices label
        loc.gridx = 1;
        loc.insets.right = 20;
        add(new JLabel("Choices"), loc);

        //create textFields and labels
        loc = new GridBagConstraints();
        loc.anchor = GridBagConstraints.LINE_START;
        loc.insets = new Insets(5,5,5,5);
        loc.gridx = 1;
        loc.gridy = 1;
        add(new JLabel("Zip 1"), loc);
        loc.gridx ++;
        add(zipOneTF, loc);
        loc.gridx = 1;
        loc.gridy = 2;
        add(new JLabel("Zip 2"), loc);
        loc.gridx ++;
        add(zipTwoTF, loc);
        loc.gridx = 1;
        loc.gridy = 3;
        add(new JLabel("radius"), loc);
        loc.gridx ++;
        add(radiusTF, loc);
        loc.gridx = 1;
        loc.gridy = 4;
        add(new JLabel("name"), loc);
        loc.gridx ++;
        add(nameTF, loc);

        //create JButtons
        loc = new GridBagConstraints();
        loc.gridx = 1;
        loc.gridy = 6;
        loc.gridwidth = 2;
        loc.insets = new Insets(5,5,5,5);
        add(disBtn, loc);

        loc.gridy = 7;
        loc.gridwidth = 2;
        loc.insets = new Insets(5,5,5,5);
        add(findZipBtn, loc);

        loc.gridy = 8;
        loc.gridwidth = 2;
        loc.insets = new Insets(5,5,5,5);
        add(radZipBtn, loc);

        loc.gridy = 9;
        loc.gridwidth = 2;
        loc.insets = new Insets(5,5,5,5);
        add(nameSearchBtn, loc);

        loc.gridy = 10;
        loc.gridwidth = 2;
        loc.insets = new Insets(5,5,5,5);
        add(furthestZipBtn, loc);

        //action listeners
        disBtn.addActionListener(this);
        findZipBtn.addActionListener(this);
        radZipBtn.addActionListener(this);
        nameSearchBtn.addActionListener(this);
        furthestZipBtn.addActionListener(this);
    }

    private void setupMenus(){
        //instantiating
        fileMenu = new JMenu("File");
        openItem = new JMenuItem("Open...");
        countItem = new JMenuItem("Count");
        aboutItem = new JMenuItem("About");
        quitItem = new JMenuItem("Quit");

        fileMenu.add(openItem);
        fileMenu.add(countItem);
        fileMenu.add(aboutItem);
        fileMenu.add(quitItem);

        menus = new JMenuBar();
        setJMenuBar(menus);
        menus.add(fileMenu);

        //add action listeners to menu items
        openItem.addActionListener(this);
        countItem.addActionListener(this);
        aboutItem.addActionListener(this);
        quitItem.addActionListener(this);
    }

    private void searchForCity() {
        ArrayList <ZipCode> s = new ArrayList <ZipCode> ();
        s = data.search(nameTF.getText());
        results.setText("");
        if (s == null)
            results.setText("no entries were found");
        else
            listZipCodes(s);
    }

    /*****************************************************************
     * find a zip code
     ****************************************************************/ 
    private void findCity (){
        ZipCode zip = null;
        results.setText("");

        if(checkValidInteger(zipOneTF.getText(), "zip 1")){

            // search for the zip code
            int num = Integer.parseInt(zipOneTF.getText());
            ZipCode zipB = data.findZip(num);

            // if no zip code found
            if (zipB == null){
                results.setText("no city found with zip code ");
            }else{
                results.setText(zipB.toString());
            }
        }
    }

    private void distanceBetweenCities() {
        results.setText("");
        if(checkValidInteger(zipOneTF.getText(), "zip 1")){
            if(checkValidInteger(zipTwoTF.getText(), "zip 2")){
                int z = Integer.parseInt(zipOneTF.getText());
                int y = Integer.parseInt(zipTwoTF.getText());
                int dis = data.distance(z,y);

                if (dis == -1)
                    results.setText("One of the zip codes is not valid");
                else
                    results.setText("Distance between the two cities is "+dis+" miles");
            }
        }
    }

    private void findFurthestCity() {
        results.setText("");
        if(checkValidInteger(zipOneTF.getText(), "zip 1")){
            int z = Integer.parseInt(zipOneTF.getText());
            ZipCode zipC = data.findFurthest(z);

            if (zipC == null)
                results.setText("no city found");
            else
                results.setText("Furthest City:\n"+zipC);
        }
    }

    private void findCitiesWithinRadius() {
        results.setText("");
        if(checkValidInteger(zipOneTF.getText(), "zip 1")){ 
            if(checkValidInteger(radiusTF.getText(), "radius")){
                int zR = Integer.parseInt(zipOneTF.getText());
                int rR = Integer.parseInt(radiusTF.getText());
                ArrayList <ZipCode> c = new ArrayList <ZipCode> ();

                c = data.withinRadius(zR, rR);
                listZipCodes(c);
            }
        }
    }

    private void listZipCodes (ArrayList <ZipCode> list){
        for (ZipCode z : list){
            results.append(z.toString() + "\n");
        }
        results.append("total: " + list.size() +"\n");
    }

    /*****************************************************************
     * This method is called when any button is clicked.  The proper
     * internal method is called as needed.
     * 
     * @param e the event that was fired
     ****************************************************************/       
    public void actionPerformed(ActionEvent e){

        // extract the button that was clicked
        JComponent buttonPressed = (JComponent) e.getSource();

        if (buttonPressed == openItem)
            openFile();
        if (buttonPressed == countItem)
            JOptionPane.showMessageDialog(this, "Items on list: " + data.getCount());
        if (e.getSource() == aboutItem)
            JOptionPane.showMessageDialog(this, "I need a good grade");
        if (e.getSource() == quitItem)
            System.exit(1);

        if (buttonPressed == disBtn)
            distanceBetweenCities();
        if (buttonPressed == findZipBtn)
            findCity();
        if (buttonPressed == radZipBtn)
            findCitiesWithinRadius();
        if (buttonPressed == nameSearchBtn)
            searchForCity();
        if (buttonPressed == furthestZipBtn)
            findFurthestCity();

    }

    /*****************************************************************
     * open a data file with the name selected by the user
     ****************************************************************/ 
    private void openFile(){
        // create File Chooser so that it starts at the current directory
        String userDir = System.getProperty("user.dir");
        JFileChooser fc = new JFileChooser(userDir);

        // show File Chooser and wait for user selection
        int returnVal = fc.showOpenDialog(this);

        // did the user select a file?
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String filename = fc.getSelectedFile().getName();
            data.readZipCodeData(filename);          
        }
    }

    /*****************************************************************
     * Check if the String contains a valid integer.  Display
     * an appropriate warning if it is not valid.
     * 
     * @param numStr - the String to be checked
     * @param label - the textfield name that contains the String
     * @return true if valid
     ****************************************************************/   
    private boolean checkValidInteger(String numStr, String label){
        boolean isValid = true;
        try{
            int val = Integer.parseInt(numStr);

            // display error message if not a valid integer    
        }catch(NumberFormatException e){
            isValid = false;
            JOptionPane.showMessageDialog(this, "Enter an integer in " + label);
        }    
        return isValid;
    }
}
