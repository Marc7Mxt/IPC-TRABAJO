/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author Marc
 */
public class LoginController implements Initializable {

    @FXML
    private Circle circuloLogo;
    @FXML
    private Button botonRegistrarse;
    @FXML
    private Button botonEntrar;
    @FXML
    private Label labelRegistro;
    @FXML
    private TextField textfieldNicknameLogin;
    @FXML
    private PasswordField passfieldLogin;
    @FXML
    private Button botonVolver;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Image imagen = new Image (getClass().getResourceAsStream("/fotostrabajo/foto club.jpg"));
        circuloLogo.setFill(new ImagePattern(imagen));
        
    }    

    @FXML
    private void registrarseClicked(ActionEvent event) {
        
        
    }

    @FXML
    private void entrarClicked(ActionEvent event) {
    }

    @FXML
    private void volverClicked(ActionEvent event) {
        
        
    }
    
}
