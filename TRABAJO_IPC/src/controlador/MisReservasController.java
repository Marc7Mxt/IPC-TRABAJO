/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafxmlapplication.JavaFXMLApplication;
import model.Booking;
import model.Club;
import model.ClubDAOException;

/**
 *
 * @author coryy
 */
public class MisReservasController implements Initializable {
    
    private ObservableList<String> datos = null;
    
    @FXML
    private Button buttonAnular;
    @FXML
    private TableView<String> TableView;
    @FXML
    private Button prueba;
    @FXML
    private TableColumn<LocalDate, String> diaColumn;
    @FXML
    private TableColumn<Booking, String> pistaColumn;
    @FXML
    private TableColumn<LocalTime, String> inicioColumn;
    @FXML
    private TableColumn<Booking, String> salidaColumn;
    @FXML
    private TableColumn<Booking, String> pagoColumn;
    
    
    //REVISAR DE NUEVO
    private void inicializaModelo(String login) throws ClubDAOException, IOException{
        Club club = Club.getInstance();    
        diaColumn.setCellValueFactory((diaFila) ->new SimpleStringProperty(diaFila.getValue().toString()));
        
        List<Booking> misdatos = new ArrayList<Booking>();
        club.getUserBookings(login);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       //TableView.setItems(datos);
        datos = TableView.getItems();
        buttonAnular.disableProperty().bind(Bindings.equal(-1,TableView.getSelectionModel().selectedIndexProperty()));
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
        anular.setTitle("ANULAR RESERVA");
        anular.setHeaderText(null);
        anular.setContentText("¿Quieres anular esta reserva?");
        Optional<ButtonType> result = anular.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            datos.remove(TableView.getSelectionModel().getSelectedIndex());
            Alert eliminada = new Alert(AlertType.INFORMATION);
            eliminada.setTitle("RESERVA ELIMINADA");
            eliminada.setHeaderText(null);
            eliminada.setContentText("La reserva fue eliminada con éxito");
            eliminada.showAndWait();
        } 
    }

    @FXML
    private void añadirhandle(ActionEvent event) {
        System.out.println("funciona");
        datos.add("hola");
    }
}
