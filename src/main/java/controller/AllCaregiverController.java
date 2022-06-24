package controller;

import datastorage.PatientDAO;
import datastorage.CaregiverDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Caregiver;
import datastorage.DAOFactory;
import model.Patient;

import java.sql.SQLException;
import java.util.List;


/**
 * The <code>AllCaregiverController</code> contains the entire logic of the caregiver view. It determines which data is displayed and how to react to events.
 */
public class AllCaregiverController {
    @FXML
    private TableView<Caregiver> tableView;
    @FXML
    private TableColumn<Caregiver, Integer> colID;
    @FXML
    private TableColumn<Caregiver, String> colSurname;

    @FXML
    private TableColumn<Caregiver,String> colFirstname;

    @FXML
    private TableColumn<Caregiver, String> colPhonenumber;

    @FXML
    Button btnDelete;
    @FXML
    Button btnAdd;
    @FXML
    TextField txtSurname;
    @FXML
    TextField txtFirstname;
    @FXML
    TextField txtPhonenumber;

    private ObservableList<Caregiver> tableviewContent = FXCollections.observableArrayList();
    private CaregiverDAO dao;

    /**
     * Initializes the corresponding fields. Is called as soon as the corresponding FXML file is to be displayed.
     */
    public void initialize() {
        readAllAndShowInTableView();

        this.colID.setCellValueFactory(new PropertyValueFactory<Caregiver, Integer>("pid"));

        //CellValuefactory zum Anzeigen der Daten in der TableView
        this.colSurname.setCellValueFactory(new PropertyValueFactory<Caregiver, String>("surname"));
        this.colSurname.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colFirstname.setCellValueFactory(new PropertyValueFactory<Caregiver, String>("firstname"));
        this.colFirstname.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colPhonenumber.setCellValueFactory(new PropertyValueFactory<Caregiver, String>("phonenumber"));
        this.colPhonenumber.setCellFactory(TextFieldTableCell.forTableColumn());
        //Anzeigen der Daten
        this.tableView.setItems(this.tableviewContent);
    }

    /**
     * handles new firstname value
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditFirstname(TableColumn.CellEditEvent<Caregiver, String> event){
        event.getRowValue().setFirstName(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new surname value
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditSurname(TableColumn.CellEditEvent<Caregiver, String> event){
        event.getRowValue().setSurname(event.getNewValue());
        doUpdate(event);
    }
    @FXML
    public void handleOnEditPhonenumber(TableColumn.CellEditEvent<Caregiver, String> event){
        event.getRowValue().setPhonenumber(Integer.parseInt(event.getNewValue()));
        doUpdate(event);
    }


    /**
     * handles new asset value
     * @param event event including the value that a user entered into the cell
     */
    //@FXML
    //public void handleOnEditAssets(TableColumn.CellEditEvent<Patient, String> event){
    //  event.getRowValue().setAssets(event.getNewValue());
    //doUpdate(event);
    //}

    /**
     * updates a patient by calling the update-Method in the {@link CaregiverDAO}
     * @param t row to be updated by the user (includes the patient)
     */
    private void doUpdate(TableColumn.CellEditEvent<Caregiver, String> t) {
        try {
            dao.update(t.getRowValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * calls readAll in {@link CaregiverDAO} and shows patients in the table
     */
    private void readAllAndShowInTableView() {
        this.tableviewContent.clear();
        this.dao = DAOFactory.getDAOFactory().createCaregiverDAO();
        List<Caregiver> allCaregiver;
        try {
            allCaregiver = dao.readAll();
            for (Caregiver c : allCaregiver) {
                this.tableviewContent.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * handles a delete-click-event. Calls the delete methods in the {@link CaregiverDAO}
     */
    /*
    @FXML
    public void handleDeleteRow() {
        TreatmentDAO tDao = DAOFactory.getDAOFactory().createTreatmentDAO();
        Patient selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        try {
            tDao.deleteByPid(selectedItem.getPid());
            dao.deleteById(selectedItem.getPid());
            this.tableView.getItems().remove(selectedItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    */


    /**
     * handles a add-click-event. Creates a patient and calls the create method in the {@link CaregiverDAO}
     */
    @FXML
    public void handleAdd() {
        String surname = this.txtSurname.getText();
        String firstname = this.txtFirstname.getText();
        int phonenumber = Integer.parseInt(this.txtPhonenumber.getText());

        try {
            Caregiver p = new Caregiver(surname, firstname, phonenumber,  2);
            dao.create(p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        readAllAndShowInTableView();
        clearTextfields();
    }

    /**
     * removes content from all textfields
     */
    private void clearTextfields() {
        this.txtFirstname.clear();
        this.txtSurname.clear();
        this.txtPhonenumber.clear();
    }
}