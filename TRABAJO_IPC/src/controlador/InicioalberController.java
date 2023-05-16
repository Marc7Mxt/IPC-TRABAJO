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
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Marc
 */
public class InicioalberController implements Initializable {

    @FXML
    private Circle circulologo;
    @FXML
    private Circle fotoPorDefecto;
    @FXML
    private Button botonLogin;
    @FXML
    private Button botonVerPistas;
    @FXML
    private Button botonReservas;
    
    private boolean loggedIn = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Image imagen = new Image(getClass().getResourceAsStream("/fotostrabajo/foto club.jpg"));
        circulologo.setFill(new ImagePattern(imagen));
        
        Image imagenUser = new Image(getClass().getResourceAsStream("/fotostrabajo/foto club.jpg")); //buscar user predef
        fotoPorDefecto.setFill(new ImagePattern(imagenUser));
    
    }    

    @FXML
    private void clickLogin(ActionEvent event) throws IOException {
        
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vistas/login.fxml"));
        Parent root = miCargador.load();
        
        Scene scene = new Scene(root, 375, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Login GREENCLUB");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        
        
        
        
        
    }

    @FXML
    private void clickVerPistas(ActionEvent event) throws IOException{
        
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vistas/DisponibilidadPistas.fxml")); //cambiar fxml
        Parent root = miCargador.load();
        
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/estilos/estiloPrincipal.css").toExternalForm();
        scene.getStylesheets().add(css);
        Stage stage = new Stage();
        
        stage.setScene(scene);
        stage.setTitle("Ver Disponibilidad de Pistas");
        stage.setMinHeight(750);
        stage.setMinWidth(900);
        //stage.setMaximized(true);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        
        //cerrar vetntana
        stage = (Stage) botonVerPistas.getScene().getWindow();
        stage.hide();
        
    }

    @FXML
    private void clickReserva(ActionEvent event) throws IOException {
        FXMLLoader miReserva = new FXMLLoader(getClass().getResource("/vistas/DisponibilidadPistas.fxml")); //cambiar fxml
        Parent reservaRoot = miReserva.load();
        Scene reservaScene = new Scene(reservaRoot);
        
        FXMLLoader miLogin = new FXMLLoader(getClass().getResource("/vistas/DisponibilidadPistas.fxml")); //cambiar fxml
        Parent loginRoot = miLogin.load();
        Scene loginScene = new Scene(loginRoot);
        
        Stage stage = new Stage();
        
        if(!loggedIn){
            stage.setScene(reservaScene);
            stage.setTitle("Mis reservas");
            stage.setMinHeight(750);
            stage.setMinWidth(900);
            //stage.setMaximized(true);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage = (Stage) botonVerPistas.getScene().getWindow();
            stage.hide();
            
            
            
        } else {
            stage.setScene(loginScene);
            stage.setTitle("Inicio Sesión");
            stage.setMinHeight(750);
            stage.setMinWidth(900);
            //stage.setMaximized(true);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage = (Stage) botonVerPistas.getScene().getWindow();
            stage.hide();
        }
    }
    
}
