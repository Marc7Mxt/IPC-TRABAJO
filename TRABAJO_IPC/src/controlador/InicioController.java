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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafxmlapplication.JavaFXMLApplication;
import model.Club;
import model.ClubDAOException;

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
    private Button buttonMisreservas;
    @FXML
    private Button buttonUsuario;

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
    private void IraPistas(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/pistas.fxml"));
        Parent root = loader.load();
        
        JavaFXMLApplication.setRoot(root);
    }

    @FXML
    private void IraMisreservas(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/misreservas.fxml"));
        Parent root = loader.load();
        
        JavaFXMLApplication.setRoot(root);
    }


    @FXML
    private void IraUsuario(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/ventanaDatos.fxml"));
        Parent root = loader.load();
        
        JavaFXMLApplication.setRoot(root);
    }
}
