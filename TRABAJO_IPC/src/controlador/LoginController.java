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
    
        // Verificar si los campos están vacíos
        if (nickname.isEmpty() || password.isEmpty()) {
            mostrarAlerta("Por favor rellena los campos para poder iniciar sesión");
            return;
        }
        // Verificar si el nickname existe en la base de datos
        if (!club.existsLogin(nickname)) {
            mostrarAlerta("No existe el nickname. Por favor regístrate");
            return;
        }
        // Verificar si las credenciales coinciden con la base de datos
        if (user != null) {
            // Obtener la referencia a la ventana actual
            Stage stage = (Stage) botonEntrar.getScene().getWindow();

            // Cerrar la ventana de inicio de sesión
            stage.close();

            // Mostrar mensaje de inicio de sesión exitoso
            mostrarAlerta("Inicio de sesión con éxito, bienvenido " + nickname);

            // Realizar acciones adicionales después del inicio de sesión exitoso

            // Actualizar la clase ControladorPrincipal si es necesario
            ControladorPrincipal.cambiarLoggedIn(true);
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vistas/inicio.fxml"));
            Parent root = miCargador.load();
            InicioController controlador = miCargador.getController();
            controlador.initMember(nickname, password);
            Scene scene = new Scene(root, 600, 500);
            Stage stage2 = new Stage();
            stage2.setScene(scene);
            stage2.setTitle("CLUB DE TENIS GREENBALL");
            //stage2.setResizable(false);
            stage2.initModality(Modality.APPLICATION_MODAL);
            stage2.show();
            stage2 = (Stage) botonEntrar.getScene().getWindow();
            stage2.close(); 

        }
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
        //ventanaDatosController ventanaDatosController = loader.getController();
        //ventanaDatosController.initMember(user);
        Stage userStage = new Stage();
        userStage.setScene(new Scene(userRoot));
        userStage.show();
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
