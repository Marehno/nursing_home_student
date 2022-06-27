package datastorage;

import model.Treatment;
import utils.DateConverter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TreatmentDAO extends DAOimp<Treatment> {

    public TreatmentDAO(Connection conn) {
        super(conn);
    }

    /**
     *
     * @param treatment gets the values the SQL-Statement and puts it in the Database
     * @return
     */

    @Override
    protected String getCreateStatementString(Treatment treatment) {
        return String.format("INSERT INTO treatment (pid, treatment_date, begin, end, description, remarks) VALUES " +
                "(%d, '%s', '%s', '%s', '%s', '%s')", treatment.getPid(), treatment.getDate(),
                treatment.getBegin(), treatment.getEnd(), treatment.getDescription(),
                treatment.getRemarks(), treatment.getLockStatus());
    }

    /**
     *
     * @param key
     * @return
     */
    @Override
    protected String getReadByIDStatementString(long key) {
        return String.format("SELECT * FROM treatment WHERE tid = %d and lock_status = false", key);
    }

    /**
     *
     * @param result
     * @return
     * @throws SQLException
     */
    @Override
    protected Treatment getInstanceFromResultSet(ResultSet result) throws SQLException {
        LocalDate date = DateConverter.convertStringToLocalDate(result.getString(3));
        LocalTime begin = DateConverter.convertStringToLocalTime(result.getString(4));
        LocalTime end = DateConverter.convertStringToLocalTime(result.getString(5));
        Treatment m = new Treatment(result.getLong(1), result.getLong(2),
                date, begin, end, result.getString(6), result.getString(7));
        return m;
    }

    /**
     *
     * @return all the treatmens where the Lock Status is false which means the treatments are available in the table
     */
    @Override
    protected String getReadAllStatementString() {
        return "SELECT * FROM treatment where lock_status = false";
    }

    /**
     *
     * @param result
     * @return
     * @throws SQLException
     */
    @Override
    protected ArrayList<Treatment> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Treatment> list = new ArrayList<Treatment>();
        Treatment t = null;
        while (result.next()) {
            LocalDate date = DateConverter.convertStringToLocalDate(result.getString(3));
            LocalTime begin = DateConverter.convertStringToLocalTime(result.getString(4));
            LocalTime end = DateConverter.convertStringToLocalTime(result.getString(5));
            t = new Treatment(result.getLong(1), result.getLong(2),
                    date, begin, end, result.getString(6), result.getString(7));
            list.add(t);
        }
        return list;
    }

    /**
     *
     * @param treatment
     * @return
     */
    @Override
    protected String getUpdateStatementString(Treatment treatment) {
        return String.format("UPDATE treatment SET pid = %d, treatment_date ='%s', begin = '%s', end = '%s'," +
                "description = '%s', remarks = '%s' WHERE tid = %d", treatment.getPid(), treatment.getDate(),
                treatment.getBegin(), treatment.getEnd(), treatment.getDescription(), treatment.getRemarks(),
                treatment.getTid());
    }

    /**
     *
     * @param key
     * @return
     */
    @Override
    protected String getDeleteStatementString(long key) {
        return String.format("Delete FROM treatment WHERE tid= %d", key);
    }

    /**
     *
     * @param pid
     * @return
     * @throws SQLException
     */
    public List<Treatment> readTreatmentsByPid(long pid) throws SQLException {
        ArrayList<Treatment> list = new ArrayList<Treatment>();
        Treatment object = null;
        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery(getReadAllTreatmentsOfOnePatientByPid(pid));
        list = getListFromResultSet(result);
        return list;
    }

    /**
     *
     * @param pid
     * @return
     */
    private String getReadAllTreatmentsOfOnePatientByPid(long pid) {
        return String.format("SELECT * FROM treatment WHERE pid = %d", pid);
    }

    /**
     *
     * @param key
     * @throws SQLException
     */
    public void deleteByPid(long key) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate(String.format("Delete FROM treatment WHERE pid= %d", key));
    }

    /**
     *
     * @param key
     * @throws SQLException
     */
    public void updateLock(long key) throws SQLException{
        Statement statement = conn.createStatement();
        statement.executeUpdate(String.format("Update treatment set lock_status = true where tid = %d", key));
    }
}