/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class ventanaDatosController implements Initializable {

    @FXML
    private TextField textfieldNombreRegistro;
    @FXML
    private TextField textfieldApellidosRegistro;
    @FXML
    private TextField textfieldTlfRegistro;
    @FXML
    private PasswordField passfieldRegistro;
    @FXML
    private TextField tarjetaRegistro;
    @FXML
    private TextField svcRegistro;
    @FXML
    private Button botonRegistrar;
    @FXML
    private PasswordField passfieldRepRegistro;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void registrarClicked(ActionEvent event) {
    }
    
}
