package controller;
import datastorage.ConnectionBuilder;
import datastorage.DAOFactory;
import datastorage.LoginDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import loginform.LoginData;
import utils.Sha256;

import java.io.IOException;
import java.sql.SQLException;

/**
 * The <code>LoginController</code> contains the entire logic of the Login view. It determines which data is displayed and how to react to events.
 */
public class LoginController {

    @FXML
    private TableView<LoginData> tableView;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    Button btnLogin;
    @FXML
    TextField txtLogin;
    @FXML
    TextField txtPassword;

    @FXML
    public void handleCheckIsEqualPassword() throws SQLException {
        String loginName = this.txtLogin.getText();
        String txtFieldPassword = this.txtPassword.getText();

        if ((loginName.equals("")) || (txtFieldPassword.equals(""))) {
            ErrorMessageMissingLoginData();
        } else {

            LoginDAO dao = DAOFactory.getDAOFactory().createLoginDAO();

            String encryptedPasswordFromTable = dao.getPasswordFromTable(loginName);
            String txtFieldPassword_Encrypted = Sha256.getSecurePassword(txtFieldPassword, loginName);

            System.out.println();
            System.out.println(txtFieldPassword_Encrypted);
            System.out.println(encryptedPasswordFromTable);

            boolean isEqual = encryptedPasswordFromTable.equals(txtFieldPassword_Encrypted);

            if (isEqual) {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("/MainWindowView.fxml"));
                try {
                    mainBorderPane.setCenter(loader.load());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                MainWindowController controller = loader.getController();
            } else ErrorMessageFalseLoginData();
        }
    }


    private void ErrorMessageFalseLoginData() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Message");
        alert.setHeaderText("Incorrect login data");
        alert.setContentText("Password or login name is not entered or is entered incorrectly.\n" +
                             "Check if the data is correct or contact technical support.");

        alert.showAndWait();
    }

    private void ErrorMessageMissingLoginData() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Message");
        alert.setHeaderText("Incorrect login data");
        alert.setContentText("Password or login name is not entered or is entered incorrectly.\n" +
                "Check if the data is correct or contact technical support.");

        alert.showAndWait();
    }
}


