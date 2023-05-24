/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafxmlapplication.JavaFXMLApplication;

/**
 *
 * @author coryy
 */
public class MisReservasController implements Initializable {

    @FXML
    private Button buttonAnular;
    @FXML
    private TableView<String> TableView;

    private ObservableList<String> datos = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        datos = TableView.getItems();
    }  
    
    @FXML
    private void IraPistas(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/pistas.fxml"));
        Parent root = loader.load();
        
        JavaFXMLApplication.setRoot(root);
    }

    @FXML
    private void IraInicio(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/inicio.fxml"));
        Parent root = loader.load();
        
        JavaFXMLApplication.setRoot(root);
    }
    
    @FXML
    private void IraUsuario(ActionEvent event) {
        
    }
    
    @FXML
    private void anularHandle(ActionEvent event) {
        Alert anular = new Alert(AlertType.CONFIRMATION);
        anular.setTitle("Anular Reserva");
        anular.setHeaderText(null);
        anular.setContentText("¿Quieres anular esta reserva?");
        Optional<ButtonType> result = anular.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) System.out.println("OK");
        else System.out.println("CANCEL");      
    }
}
