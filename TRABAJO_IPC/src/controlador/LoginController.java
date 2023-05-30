/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Club;
import model.ClubDAOException;
import model.Member;

/**
 * FXML Controller class
 *
 * @author Marc
 */
public class LoginController implements Initializable {

    private Circle circuloLogo;
    @FXML
    private Button botonEntrar;
    @FXML
    private TextField textfieldNicknameLogin;
    @FXML
    private PasswordField passfieldLogin;
    @FXML
    private Button botonVolver;
    @FXML
    private Hyperlink hipervinculoRegistro;
    
    private Member user;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Image imagen = new Image (getClass().getResourceAsStream("/fotostrabajo/logo.jpeg"));
        //circuloLogo.setFill(new ImagePattern(imagen));
        
    }    

    

    @FXML
    private void entrarClicked(ActionEvent event) throws ClubDAOException, IOException {
        String nickname = textfieldNicknameLogin.getText();
        String password = passfieldLogin.getText();
        Club club = Club.getInstance();
        if(club.existsLogin(nickname)){
            user = club.getMemberByCredentials(nickname, password);
            if(user == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Usuario y/o contraseña erróneos");
                alert.showAndWait();
            } else {
                Stage stage = new Stage();
                stage = (Stage) botonEntrar.getScene().getWindow();
                stage.close();
                
            }
            ControladorPrincipal.cambiarLoggedIn(true);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No existe el nickname. Por favor registrate");
                alert.showAndWait();
        } 
    }

    @FXML
    private void volverClicked(ActionEvent event) {
        
        
    }

    @FXML
    private void registrarClicked(ActionEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vistas/registro.fxml"));
        Parent root = miCargador.load();
        
        
        Scene scene = new Scene(root, 600, 500);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Registro de Usuario Nuevo");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
    public boolean comprobarLogin() throws ClubDAOException, IOException{
        String nickname = textfieldNicknameLogin.getText();
        String password = passfieldLogin.getText();
        Club club = Club.getInstance();
        Member member = club.getMemberByCredentials(nickname, password);
        return member != null;
    }
    
}
