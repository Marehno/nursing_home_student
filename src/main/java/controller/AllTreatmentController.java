package controller;

import datastorage.PatientDAO;
import datastorage.TreatmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Patient;
import model.Treatment;
import datastorage.DAOFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * The <code>AllTreatmentController</code> contains the entire logic of the treatment view. It determines which data is displayed and how to react to events.
 */
public class AllTreatmentController {
    /**
     * @param tableView
     * @param colID
     * @param colPid
     * @param colDate
     * @param colBegin
     * @param colEnd
     * @param colDescription
     */
    @FXML
    private TableView<Treatment> tableView;
    @FXML
    private TableColumn<Treatment, Integer> colID;
    @FXML
    private TableColumn<Treatment, Integer> colPid;
    @FXML
    private TableColumn<Treatment, String> colDate;
    @FXML
    private TableColumn<Treatment, String> colBegin;
    @FXML
    private TableColumn<Treatment, String> colEnd;
    @FXML
    private TableColumn<Treatment, String> colDescription;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private Button btnNewTreatment;
    @FXML
    private Button btnCheckTheLockStatus;


    private ObservableList<Treatment> tableviewContent = FXCollections.observableArrayList();
    private TreatmentDAO dao;
    private ObservableList<String> myComboBoxData = FXCollections.observableArrayList();
    private ArrayList<Patient> patientList;
    private Main main;


    /**
     * In this Method <code>initialize()</code> the variables are declared and initialized.
     * And the values are displayed in the method <code>readAllAndShowInTableView()</code>
     */
    public void initialize() {
        readAllAndShowInTableView();
        comboBox.setItems(myComboBoxData);
        comboBox.getSelectionModel().select(0);
        this.main = main;

        this.colID.setCellValueFactory(new PropertyValueFactory<Treatment, Integer>("tid"));
        this.colPid.setCellValueFactory(new PropertyValueFactory<Treatment, Integer>("pid"));
        this.colDate.setCellValueFactory(new PropertyValueFactory<Treatment, String>("date"));
        this.colBegin.setCellValueFactory(new PropertyValueFactory<Treatment, String>("begin"));
        this.colEnd.setCellValueFactory(new PropertyValueFactory<Treatment, String>("end"));
        this.colDescription.setCellValueFactory(new PropertyValueFactory<Treatment, String>("description"));
        this.tableView.setItems(this.tableviewContent);
        createComboBoxData();
    }

    /**
     * In this method the text fields are cleared and then written to the table through the database connection and the For loop
     */

    public void readAllAndShowInTableView() {
        this.tableviewContent.clear();
        comboBox.getSelectionModel().select(0);
        this.dao = DAOFactory.getDAOFactory().createTreatmentDAO();
        List<Treatment> allTreatments;
        try {
            allTreatments = dao.readAll();
            for (Treatment treatment : allTreatments) {
                this.tableviewContent.add(treatment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method creates a list of patients written in a ComboBox with which one then links the treatments to the patients.
     * And shows the following treatments of the patients or all treatments
     */
    private void createComboBoxData(){
        PatientDAO dao = DAOFactory.getDAOFactory().createPatientDAO();
        try {
            patientList = (ArrayList<Patient>) dao.readAll();
            this.myComboBoxData.add("alle");
            for (Patient patient: patientList) {
                this.myComboBoxData.add(patient.getSurname());
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }


    @FXML
    public void handleComboBox(){
        String p = this.comboBox.getSelectionModel().getSelectedItem();
        this.tableviewContent.clear();
        this.dao = DAOFactory.getDAOFactory().createTreatmentDAO();
        List<Treatment> allTreatments;
        if(p.equals("alle")){
            try {
                allTreatments= this.dao.readAll();
                for (Treatment treatment : allTreatments) {
                    this.tableviewContent.add(treatment);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Patient patient = searchInList(p);
        if(patient !=null){
            try {
                allTreatments = dao.readTreatmentsByPid(patient.getPid());
                for (Treatment treatment : allTreatments) {
                    this.tableviewContent.add(treatment);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The method uses a For loop to search for the surname
     * @param surname
     * @return value is null
     */
    private Patient searchInList(String surname){
        for (int i =0; i<this.patientList.size();i++){
            if(this.patientList.get(i).getSurname().equals(surname)){
                return this.patientList.get(i);
            }
        }
        return null;
    }

    @FXML
    public void handleLock(){
        int index = this.tableView.getSelectionModel().getSelectedIndex();
        Treatment t = this.tableviewContent.remove(index);
        TreatmentDAO dao = DAOFactory.getDAOFactory().createTreatmentDAO();
        try {
            dao.checkTheLockStatus(t.getTid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method gets new treatments and checks if the treatments match with the patient
     */
    @FXML
    public void handleNewTreatment() {
        try{
            String p = this.comboBox.getSelectionModel().getSelectedItem();
            Patient patient = searchInList(p);
            newTreatmentWindow(patient);
        }
        catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Patient für die Behandlung fehlt!");
            alert.setContentText("Wählen Sie über die Combobox einen Patienten aus!");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleMouseClick(){
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (tableView.getSelectionModel().getSelectedItem() != null)) {
                int index = this.tableView.getSelectionModel().getSelectedIndex();
                Treatment treatment = this.tableviewContent.get(index);

                treatmentWindow(treatment);
            }
        });
    }

    /**
     * This method loads the FXML-File (NewTreatmentView.fxml) and shows it on the Screen via Scene and Stage
     * The method gets passed a patient and checks if an IOException comes or not with the controller.initialize
     * @param patient
     */
    public void newTreatmentWindow(Patient patient){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/NewTreatmentView.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            //da die primaryStage noch im Hintergrund bleiben soll
            Stage stage = new Stage();

            NewTreatmentController controller = loader.getController();
            controller.initialize(this, stage, patient);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void treatmentWindow(Treatment treatment){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/TreatmentView.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            //da die primaryStage noch im Hintergrund bleiben soll
            Stage stage = new Stage();
            TreatmentController controller = loader.getController();

            controller.initializeController(this, stage, treatment);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}