/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafxmlapplication.JavaFXMLApplication;
import model.Club;
import model.ClubDAOException;
import model.Member;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class InicioController implements Initializable {

    @FXML
    private Text titulo;
    @FXML
    private Text lema;
    @FXML
    private Button botonPistas;
    @FXML
    private Button botonMisReservas;
    @FXML
    private Button botonUsuario;
    
    private Member user;
    
    public void initMember(String nick, String pass) throws ClubDAOException, IOException{
        Club c = Club.getInstance();
        user = c.getMemberByCredentials(nick, pass);
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*
        try {
        Club club = Club.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/misreservas.fxml"));
        Parent root = loader.load();
        MisReservasController misreservas = loader.getController();
        misreservas.inicializaModelo("user1");
        System.out.println(club.getBookings());
        } catch (ClubDAOException | IOException ex) {
        Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
         */ 
    }    

        
    
    @FXML
    private void IraPistas(ActionEvent event) throws IOException, ClubDAOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/pistasprueba2.fxml"));
        Parent pistasRoot = loader.load();
        PistasPrueba2Controller ventanaDatosController = loader.getController();
        ventanaDatosController.initMember(user);
        Stage pistasStage = new Stage();
        pistasStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/fotostrabajo/logo.jpeg")));
        pistasStage.setTitle("DISPONIBILIDAD DE PISTAS");
        pistasStage.setScene(new Scene(pistasRoot));
        pistasStage.showAndWait();
        Stage currentStage = (Stage) botonPistas.getScene().getWindow();
        currentStage.close();
        
    }

    @FXML
    private void IraMisreservas(ActionEvent event) throws IOException, ClubDAOException {
        if(ControladorPrincipal.isLogged()){
            verVentanaMisReservas();
        } else {
            verVentanaLogin();
        }
    }


    @FXML
    private void IraUsuario(ActionEvent event) throws IOException, ClubDAOException {
        if(ControladorPrincipal.isLogged()){
            verVentanaUsuario();
        } else {
            verVentanaLogin();
        }
    }
    
    private void verVentanaUsuario() throws ClubDAOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/ventanaDatos.fxml"));
            Parent userRoot = loader.load();
            ventanaDatosController ventanaDatosController = loader.getController();
            ventanaDatosController.initMember(user);
            Stage userStage = new Stage();
            userStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/fotostrabajo/logo.jpeg")));
            userStage.setTitle("USUARIO");
            userStage.setScene(new Scene(userRoot));
            userStage.show();
            Stage currentStage = (Stage) botonUsuario.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            // Manejo de errores al cargar la ventana de usuario
            
        }
    }

    private void verVentanaLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/login.fxml"));
            Parent loginRoot = loader.load();
            Stage loginStage = new Stage();
            loginStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/fotostrabajo/logo.jpeg")));
            loginStage.setTitle("INICIO DE SESIÓN");
            loginStage.setScene(new Scene(loginRoot));
            loginStage.show();
            Stage currentStage = (Stage) botonUsuario.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            // Manejo de errores al cargar la ventana de inicio de sesión
            
        }
    }
    private void verVentanaMisReservas() throws ClubDAOException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/misreservas.fxml"));
            Parent loginRoot = loader.load();
            MisReservasController misReservas = loader.getController();
            misReservas.initMember(user);
            Stage misReservasStage = new Stage();
            misReservasStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/fotostrabajo/logo.jpeg")));
            misReservasStage.setTitle("MIS RESERVAS");
            misReservasStage.setScene(new Scene(loginRoot));
            misReservasStage.show();
            Stage currentStage = (Stage) botonMisReservas.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            
        }
    }
}


