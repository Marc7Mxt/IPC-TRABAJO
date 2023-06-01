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
        user = club.getMemberByCredentials(nickname, password);
    
        if (nickname.isEmpty() || password.isEmpty()) {
            mostrarAlerta("Por favor rellena los campos para poder iniciar sesión");
            return;
        }
        else if (!club.existsLogin(nickname)) {
            mostrarAlerta("No existe el nickname. Por favor regístrate");
            return;
        }
        
        else if (user != null) {
            Stage stage = (Stage) botonEntrar.getScene().getWindow();
            stage.close();
            mostrarAlerta("Inicio de sesión con éxito, bienvenido " + nickname);
            ControladorPrincipal.cambiarLoggedIn(true);
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vistas/inicio.fxml"));
            Parent root = miCargador.load();
            InicioController controlador = miCargador.getController();
            controlador.initMember(nickname, password);
            Scene scene = new Scene(root, 600, 388.66666666666663);
            Stage inicioStage = new Stage();
            inicioStage.setScene(scene);
            inicioStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/fotostrabajo/logo.jpeg")));
            inicioStage.setTitle("CLUB DE TENIS GREENBALL");
            //stage2.setResizable(false);
            inicioStage.initModality(Modality.APPLICATION_MODAL);
            inicioStage.show();
            inicioStage = (Stage) botonEntrar.getScene().getWindow();
            inicioStage.close(); 

        } else { mostrarAlerta("Por favor proporciona los datos necesarios");}
    }

    private void mostrarAlerta(String mensaje) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Información");
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
}



    @FXML
    private void volverClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/inicio.fxml"));
        Parent userRoot = loader.load();
        Stage inicioStage = new Stage();
        inicioStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/fotostrabajo/logo.jpeg")));
        inicioStage.setTitle("CLUB DE TENIS GREENBALL");
        inicioStage.setScene(new Scene(userRoot));
        inicioStage.show();
        Stage stage = (Stage) botonVolver.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void registrarClicked(ActionEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vistas/registro.fxml"));
        Parent root = miCargador.load();
        Scene scene = new Scene(root, 600, 500);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/fotostrabajo/logo.jpeg")));
        stage.setTitle("REGISTRO DE USUARIO NUEVO");
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
