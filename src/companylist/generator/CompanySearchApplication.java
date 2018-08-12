package companylist.generator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Application for recording Company information.
 * 
 * @author Dickfoong
 *
 */
public class CompanySearchApplication extends JFrame implements Runnable {
  
  /**
   * Serial UID.
   */
  private static final long serialVersionUID = 19960314019950113L;

  private static final Logger LOGGER = Logger.getLogger(CompanySearchApplication.class);
  /**
   * The path of output file.
   */
  private static final String OUTPUT_FILE_PATH = "csv/companylist-nyc.csv";

  /**
   * The list of companies input.
   */
  private static List<Company> companyList;
  
  /**
   * The file menu.
   */
  private JMenu fileMenu;
  
  /**
   * The item for saving the progress.
   */
  private JMenuItem fileSave;
  
  /**
   * The label to print the application status.
   */
  private JLabel statusLabel;
  
  /**
   * The button for adding company into list.
   */
  private JButton addBtn;
  
  /**
   * The button for reseting the current information.
   */
  private JButton resetBtn;
  
  /**
   * The text field for typing company name.
   */
  private JTextField nameInputField;
  
  /**
   * The text field for typing company street.
   */
  private JTextField streetInputField;
  
  /**
   * The text field for typing company floor.
   */
  private JTextField floorInputField;
  
  /**
   * The text field for typing company city.
   */
  private JTextField cityInputField;
  
  /**
   * The text field for typing company state.
   */
  private JTextField stateInputField;
  
  /**
   * The text field for typing company zip code.
   */
  private JTextField zipInputField;
  
  /**
   * The output file for recording the list.
   */
  private File outputFile;
  
  /**
   * Set up the application UI.
   */
  public CompanySearchApplication() {
    LOGGER.info("INFO: Start up");
    
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setTitle("Company Information");
    
    companyList = new ArrayList<Company>();
    
    setupUserInterface();

    enableControls(true);
    pack();
    
    setVisible(true);
  }
  
  /**
   * Set up the components.
   */
  private void setupUserInterface() {
    LOGGER.debug("DEBUG: setupUserInterface");
    
    setupControls();
    setupMenus();
    
    getContentPane().setLayout(new BorderLayout());
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    getContentPane().add(panel, BorderLayout.CENTER);
    
    panel.add(setupInfoPanel(), BorderLayout.CENTER);
    
    panel.add(setupStatusPanel(), BorderLayout.SOUTH);
  }
  
  /**
   * Setup the information input panel.
   * @return The information JPanel.
   */
  private JPanel setupInfoPanel() {
    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new GridLayout(7,1));
    infoPanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.black), "Informations"));
    
    JPanel namePanel = new JPanel();
    namePanel.setLayout(new GridLayout(1,1));
    namePanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.blue), "Company Name*"));
    namePanel.add(makeFlowPanel(nameInputField, FlowLayout.LEFT));
    
    JPanel streetPanel = new JPanel();
    streetPanel.setLayout(new GridLayout(1,1));
    streetPanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.blue), "Street*"));
    streetPanel.add(makeFlowPanel(streetInputField, FlowLayout.LEFT));
    
    JPanel floorPanel = new JPanel();
    floorPanel.setLayout(new GridLayout(1,1));
    floorPanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.blue), "Floor"));
    floorPanel.add(makeFlowPanel(floorInputField, FlowLayout.LEFT));
    
    JPanel cityPanel = new JPanel();
    cityPanel.setLayout(new GridLayout(1,1));
    cityPanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.blue), "City*"));
    cityPanel.add(makeFlowPanel(cityInputField, FlowLayout.LEFT));
    
    JPanel statePanel = new JPanel();
    statePanel.setLayout(new GridLayout(1,1));
    statePanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.blue), "State*"));
    statePanel.add(makeFlowPanel(stateInputField, FlowLayout.LEFT));
    
    JPanel zipPanel = new JPanel();
    zipPanel.setLayout(new GridLayout(1,1));
    zipPanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.blue), "Zip Code*"));
    zipPanel.add(makeFlowPanel(zipInputField, FlowLayout.LEFT));
    
    JPanel btnPanel = new JPanel();
    btnPanel.setLayout(new GridLayout(1,2));
    btnPanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.blue)));
    btnPanel.add(makeFlowPanel(addBtn, FlowLayout.LEFT));
    btnPanel.add(makeFlowPanel(resetBtn, FlowLayout.RIGHT));
    
    infoPanel.add(namePanel);
    infoPanel.add(streetPanel);
    infoPanel.add(floorPanel);
    infoPanel.add(cityPanel);
    infoPanel.add(statePanel);
    infoPanel.add(zipPanel);
    infoPanel.add(btnPanel);
    return infoPanel;
  }
  
  /**
   * Setup the status panel.
   * 
   * @return The status JPanel.
   */
  private JPanel setupStatusPanel() {
    JPanel statusPanel = new JPanel();
    
    statusPanel.setLayout(new GridLayout(1,1));
    statusPanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.black), "Status"));
    statusPanel.add(makeFlowPanel(statusLabel, FlowLayout.LEFT));
    
    return statusPanel;
  }
  
  /**
   * Create a JPanel that uses FlowLayout and a component to itself.
   * 
   * @param component Component to add.
   * @param alignment Position of the component.
   * @return The JPanel with component added.
   */
  private JPanel makeFlowPanel(JComponent component, int alignment) {
    JPanel panel = new JPanel();
    
    panel.setLayout(new FlowLayout(alignment));
    panel.add(component);
    
    return panel;
  }
  
  /**
   * Setup all the control components on UI.
   */
  private void setupControls() {
    LOGGER.debug("DEBUG: setupControls");
    
    addBtn = new JButton("Add Company");
    addBtn.addActionListener(new AddListener());
    
    resetBtn = new JButton("Reset Information");
    resetBtn.addActionListener(new ResetListener());
    
    statusLabel = new JLabel("Fields with * are required.");
    statusLabel.setHorizontalAlignment(JLabel.CENTER);
    
    nameInputField = new JTextField(255);
    streetInputField = new JTextField(255);
    floorInputField = new JTextField(255);
    cityInputField = new JTextField(255);
    stateInputField = new JTextField(255);
    zipInputField = new JTextField(255);
  }
  
  private void setupMenus() {
    LOGGER.debug("DEBUG: setupMenus");
    
    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);
    
    fileMenu = new JMenu("File");
    fileMenu.setMnemonic(KeyEvent.VK_F);
    menuBar.add(fileMenu);
    
    fileSave = new JMenuItem("Save");
    fileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.ALT_MASK));
    fileSave.setMnemonic('S');
    fileSave.setToolTipText("Save file");
    fileSave.addActionListener(new FileSaveListener());
    
    fileMenu.add(fileSave);
  }
  
  /**
   * Executes the command on saving the list to file.
   */
  private class FileSaveListener implements ActionListener {
    public FileSaveListener() {
      
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
      saveFile();
    }
  }
  
  /**
   * Executes the command on adding the company to list.
   */
  private class AddListener implements ActionListener {
    public AddListener() {
      
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
      addCompany();
    }
  }
  
  /**
   * Executes the command on resetting informations.
   */
  private class ResetListener implements ActionListener {
    public ResetListener() {
      
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
      resetInformation();
    }
  }
  
  /**
   * Save the inserted list of companies to csv file.
   */
  private void saveFile() {
    LOGGER.debug("DEBUG: saveFile");
    
    try {
      List<Company> companiesInFile = new ArrayList<Company>();
      outputFile = new File(OUTPUT_FILE_PATH);
      FileWriter writer = new FileWriter(outputFile, true);
      
      if (!outputFile.createNewFile()) {
        LOGGER.debug("DEBUG: OutputFile is existed");
        
        Scanner fileScanner = new Scanner(outputFile);
        String companyInfo;
        String[] informations;
        String comName;
        String comStreet;
        String comFloor;
        String comCity;
        String comState;
        int comZip;
        
        LOGGER.trace("TRACE: Load companies in output file");
        while (fileScanner.hasNext()) {
          companyInfo = fileScanner.nextLine();
          informations = companyInfo.split(",");
          comName = informations[0];
          comStreet = informations[1];
          comFloor = informations[2];
          comCity = informations[3];
          comState = informations[4];
          comZip = Integer.parseInt(informations[5]);
          
          LOGGER.trace("TRACEL: Got the company name: " + comName);
          
          Company comp;
          if (comFloor.equals("")) {
            comp = new Company(comName, comStreet, comCity, comState, comZip);
          } else {
            comp = new Company(comName, comStreet, comFloor, comCity, comState, comZip);
          }
          
          companiesInFile.add(comp);
        }
        fileScanner.close();
      }
      
      LOGGER.debug("DEBUG: Write companies in list to file");

      int companiesAdded = 0;
      if (companiesInFile.isEmpty()) {
        
        LOGGER.trace("TRACE: Write companies in list to file");
        
        for (Company comp : companyList) {
          
          LOGGER.trace("TRACE: Writing " + comp.getName() + " to file");
          
          writer.append(comp.toString() + "\n");
          companiesAdded++;
        }
      } else {
        
        LOGGER.trace("TRACE: Write companies which are not already in file");
        
        boolean isExisted;
        for (Company comp : companyList) {
          isExisted = false;
          for (Company compInFile : companiesInFile) {
            
            LOGGER.trace("TRACE: Comparing " + comp.getName() + " and " + compInFile.getName());
            
            if (comp.compareTo(compInFile) == 0) {
              isExisted = true;
            }
          }
          
          if (!isExisted) {
            writer.append(comp.toString() + "\n");
            companiesAdded++;
          }
        }
      }
      
      writer.close();
      setStatus(companiesAdded + " companies are added to " + OUTPUT_FILE_PATH);
      companyList.clear();
    } catch (IOException ioe) {
      LOGGER.warn("WARN: " + OUTPUT_FILE_PATH + " cannot be open");
      
      setStatus("List cannot be saved due to output file missing.");
    } catch (SecurityException sec) {
      LOGGER.warn("WARN: " + OUTPUT_FILE_PATH + " cannot be written");
      
      setStatus("List cannot be saved due to the rights for writing on output file missing.");
    } catch (NumberFormatException num) { 
      LOGGER.warn("WARN: " + OUTPUT_FILE_PATH + " contains non-numeric zip code");
      
      setStatus("Output file contains non-numeric zip code");
    }
    
  }
  
  /**
   * Add company to the list.
   */
  private void addCompany() {
    LOGGER.debug("DEBUG: Add company to list");
    
    Company company;
    String name;
    String street;
    String city;
    String state;
    int zip;
    
    if (nameInputField.getText().equals("") || streetInputField.getText().equals("")
        || cityInputField.getText().equals("") || stateInputField.getText().equals("")
        || zipInputField.getText().equals("")) {
      setStatus("Required information(s) Missing.");
    } else {
      try {
        name = nameInputField.getText();
        street = streetInputField.getText();
        city = cityInputField.getText();
        state = stateInputField.getText();
        zip = Integer.parseInt(zipInputField.getText());
        
      } catch (NumberFormatException nfe) {
        LOGGER.warn("WARN: Non-numeric zip code");
        
        setStatus("Zip code must be numbers.");
        return;
      }
      
      if (floorInputField.getText().equals("")) {
        company = new Company(name, street, city, state, zip);
      } else {
        String floor = floorInputField.getText();
        company = new Company(name, street, floor, city, state, zip); 
      }
      
      LOGGER.trace("TRACE: Checking if input company is on list");
      for (Company com : companyList) {
        LOGGER.trace("TRACE: Checking " + com.getName());
        if (company.compareTo(com) == 0) {
          setStatus("Company already in list.");
          return;
        }
      }
      
      companyList.add(company);
      setStatus(companyList.size() + " companies in the list.");
      
      resetInformation();
    }
  }
  
  /**
   * Reset all the field to empty.
   */
  private void resetInformation() {
    LOGGER.debug("DEBUG: resetInformation");
    
    nameInputField.setText("");
    streetInputField.setText("");
    floorInputField.setText("");
    cityInputField.setText("");
    stateInputField.setText("");
    zipInputField.setText("");
  }

  private void enableControls(boolean enable) {
    LOGGER.debug("DEBUG: enableControls");
    
    nameInputField.setEditable(enable);
    streetInputField.setEditable(enable);
    floorInputField.setEditable(enable);
    cityInputField.setEditable(enable);
    stateInputField.setEditable(enable);
    zipInputField.setEditable(enable);
    
    fileSave.setEnabled(true);
    
    addBtn.setEnabled(true);
    resetBtn.setEnabled(true);
  }
  
  /**
   * Set the status message.
   * @param message Message on status.
   */
  private void setStatus(String message) {
    statusLabel.setText(message);
  }
  
  @Override
  public void run() {
    System.out.println("Run method called");
    enableControls(true);
  }
  
  public static void main(String[] args) {
    BasicConfigurator.configure();
    new CompanySearchApplication();
  }

}
