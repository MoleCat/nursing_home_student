package datastorage;

import model.Patient;
import utils.DateConverter;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Implements the Interface <code>DAOImp</code>. Overrides methods to generate specific patient-SQL-queries.
 */
public class PatientDAO extends DAOimp<Patient> {

    /**
     * constructs Onbject. Calls the Constructor from <code>DAOImp</code> to store the connection.
     * @param conn
     */
    public PatientDAO(Connection conn) {
        super(conn);
    }

    /**
     * generates a <code>INSERT INTO</code>-Statement for a given patient
     * @param patient for which a specific INSERT INTO is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getCreateStatementString(Patient patient) {
        return String.format("INSERT INTO patient (firstname, surname, dateOfBirth, carelevel, roomnumber) VALUES ('%s', '%s', '%s', '%s', '%s')",
                patient.getFirstName(), patient.getSurname(), patient.getDateOfBirth(), patient.getCareLevel(), patient.getRoomnumber());
    }

    /**
     * generates a <code>select</code>-Statement for a given key
     * @param key for which a specific SELECTis to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getReadByIDStatementString(long key) {
        return String.format("SELECT * FROM patient WHERE pid = %d", key);
    }

    /**
     * maps a <code>ResultSet</code> to a <code>Patient</code>
     * @param result ResultSet with a single row. Columns will be mapped to a patient-object.
     * @return patient with the data from the resultSet.
     */
    @Override
    protected Patient getInstanceFromResultSet(ResultSet result) throws SQLException {
        Patient p = null;
        LocalDate date = DateConverter.convertStringToLocalDate(result.getString(4));
        p = new Patient(result.getInt(1), result.getString(2),
                result.getString(3), date, result.getString(5),
                result.getString(6));
        return p;
    }

    /**
     * generates a <code>SELECT</code>-Statement for all patients.
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getReadAllStatementString() {
        return "SELECT * FROM patient WHERE finished = false";
    }

    /**
     * maps a <code>ResultSet</code> to a <code>Patient-List</code>
     * @param result ResultSet with a multiple rows. Data will be mapped to patient-object.
     * @return ArrayList with patients from the resultSet.
     */
    @Override
    protected ArrayList<Patient> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Patient> list = new ArrayList<Patient>();
        Patient p = null;
        while (result.next()) {
            LocalDate date = DateConverter.convertStringToLocalDate(result.getString(4));
            p = new Patient(result.getInt(1), result.getString(2),
                    result.getString(3), date,
                    result.getString(5), result.getString(6));
            list.add(p);
        }
        return list;
    }

    /**
     * generates a <code>UPDATE</code>-Statement for a given patient
     * @param patient for which a specific update is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getUpdateStatementString(Patient patient) {
        return String.format("UPDATE patient SET firstname = '%s', surname = '%s', dateOfBirth = '%s', carelevel = '%s', " +
                "roomnumber = '%s' WHERE pid = %d", patient.getFirstName(), patient.getSurname(), patient.getDateOfBirth(),
                patient.getCareLevel(), patient.getRoomnumber(), patient.getPid());
    }

    /**
     * generates a <code>delete</code>-Statement for a given key
     * @param key for which a specific DELETE is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getDeleteStatementString(long key) {
        return String.format("Delete FROM patient WHERE pid=%d", key);
    }

    @Override
    protected String getSetFinishedOnBlockingState(long key) {
        long now = System.currentTimeMillis();

        Timestamp timestamp = new Timestamp(now);

        String str = String.format("Update patient SET finished = '%s', finishedFrom = '%s' WHERE pid = '%d'", true, timestamp, key);

        return str;
    }


}
