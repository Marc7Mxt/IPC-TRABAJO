/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafxmlapplication.JavaFXMLApplication;
import model.Booking;
import model.Club;
import model.ClubDAOException;
import model.Court;
import model.Member;

/**
 *
 * @author coryy
 */
public class MisReservasController implements Initializable {
    
    private ObservableList<Booking> datos = null;
    
    @FXML
    private Button botonAnular;
    @FXML
    private TableView<Booking> TableView;
    @FXML
    private TableColumn<Booking, String> diaColumn;
    @FXML
    private TableColumn<Booking, String> pistaColumn;
    @FXML
    private TableColumn<Booking, String> inicioColumn;
    @FXML
    private TableColumn<Booking, String> salidaColumn;
    @FXML
    private TableColumn<Booking, String> pagoColumn;
    
    private int maxFilas = 10;
    
    private Member user;
    @FXML
    private Button botonUsuario;
    @FXML
    private Button botonInicio;
    @FXML
    private Button botonPistas;
    
    private Club club;
    
    public void initMember(Member m) throws ClubDAOException, IOException {
        this.user = m;
        inicializaModelo(user.getNickName());
        
    }
    
    //REVISAR DE NUEVO
    public  void inicializaModelo(String login) throws ClubDAOException, IOException{
        diaColumn.setCellValueFactory((diaFila ->new SimpleStringProperty(diaFila.getValue().getMadeForDay().toString())));
        pistaColumn.setCellValueFactory(pistaFila -> {
            ArrayList<Court> pistas = new ArrayList<Court>(club.getCourts());
            int index = pistas.indexOf(pistaFila.getValue().getCourt());
            int numeroPista = index + 1;
            return new SimpleStringProperty("Pista "+String.valueOf(numeroPista));
        });
        //pistaColumn.setCellValueFactory(pistaFila -> new SimpleStringProperty(pistaFila.getValue().getCourt().toString()));
        inicioColumn.setCellValueFactory(inicioFila -> new SimpleStringProperty(inicioFila.getValue().getFromTime().toString()));
        salidaColumn.setCellValueFactory(salidaFila ->{
            LocalTime horaInicio = salidaFila.getValue().getFromTime();
            LocalTime horaSalida = horaInicio.plusMinutes(60);
            return new SimpleStringProperty(horaSalida.toString());
        });
        pagoColumn.setCellValueFactory(pagoFila -> {
            if(pagoFila.getValue().getPaid()) {return new SimpleStringProperty("Sí");}
                else{return new SimpleStringProperty("No");}
        });
            //OPCIÓN 1
            ArrayList<Booking> reservas = new ArrayList<>(club.getUserBookings(login)); // Lista de reservas
            reservas.removeIf(booking -> {
               LocalDate hoy = LocalDate.now();
               LocalTime ahora = LocalTime.now();
               return booking.getMadeForDay().isBefore(hoy) || (booking.getMadeForDay().isEqual(hoy) && booking.getFromTime().isBefore(ahora));
            });
            //reservas.removeIf(booking -> booking.getMadeForDay().isBefore(LocalDate.now())); //Quita aquellas anteriores al día actual
            Comparator<Booking> reservasComparador = Comparator.comparing(Booking::getMadeForDay).thenComparing(Booking::getFromTime);
            reservas.sort(reservasComparador); //Ordena la lista de reservas según su día y hora
            ArrayList<Booking> ultimas10Reservas = new ArrayList<>(reservas.subList(0, Math.min(10, reservas.size()))); //10 últimas reservas

            // Invierte el orden de las reservas para que la más reciente esté en la parte superior
            //Collections.reverse(ultimas10Reservas);
            datos = FXCollections.observableArrayList(ultimas10Reservas);// Asigna el modelo de datos a la TableView
            TableView.setItems(datos);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            club = Club.getInstance();
            
        } catch (ClubDAOException ex) {
            Logger.getLogger(MisReservasController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MisReservasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        botonAnular.disableProperty().bind(Bindings.equal(-1,TableView.getSelectionModel().selectedIndexProperty()));
    }  
    
    @FXML
    private void IraPistas(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/pistas.fxml"));
        Parent root = loader.load();
        
        JavaFXMLApplication.setRoot(root);
    }

    @FXML
    private void IraInicio(ActionEvent event) throws IOException, ClubDAOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/inicio.fxml"));
        Parent userRoot = loader.load();
        InicioController inicioController = loader.getController();
        inicioController.initMember(user.getNickName(), user.getPassword());
        Stage userStage = new Stage();
        userStage.setScene(new Scene(userRoot));
        userStage.show();

        // Opcionalmente, puedes cerrar la ventana actual si es necesario
        Stage currentStage = (Stage) botonUsuario.getScene().getWindow();
        currentStage.close();
    }
    
    @FXML
    private void IraUsuario(ActionEvent event) throws IOException, ClubDAOException {
        verVentanaUsuario();
    }
    
    @FXML
    private void anularHandle(ActionEvent event) throws ClubDAOException, IOException {
        Club club = Club.getInstance();
        Alert anular = new Alert(AlertType.CONFIRMATION);
        anular.setTitle("Anular Reserva");
        anular.setHeaderText(null);
        anular.setContentText("¿Quieres anular la reserva del día " + TableView.getSelectionModel().getSelectedItem().getMadeForDay().toString() + " a las " + TableView.getSelectionModel().getSelectedItem().getFromTime().toString() + "?");
        Optional<ButtonType> result = anular.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            if(ChronoUnit.HOURS.between(LocalDate.now().atStartOfDay(),TableView.getSelectionModel().getSelectedItem().getMadeForDay().atStartOfDay()) <= 24){
                Alert fueradeplazo = new Alert(AlertType.INFORMATION);
                fueradeplazo.setTitle("Anulación fuera de plazo");
                fueradeplazo.setHeaderText(null);
                fueradeplazo.setContentText("La anulación no ha tenido éxito debido a que se encuentra fuera de plazo");
                fueradeplazo.showAndWait();
            }else{
                club.removeBooking(TableView.getSelectionModel().getSelectedItem());
                datos.remove(TableView.getSelectionModel().getSelectedIndex());
                Alert eliminada = new Alert(AlertType.INFORMATION);
                eliminada.setTitle("Reserva Eliminada");
                eliminada.setHeaderText(null);
                eliminada.setContentText("La reserva fue eliminada con éxito");
                eliminada.showAndWait();
            }
        } 
    }
    private void verVentanaUsuario() throws ClubDAOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/ventanaDatos.fxml"));
            Parent userRoot = loader.load();
            ventanaDatosController ventanaDatosController = loader.getController();
            ventanaDatosController.initMember(user);
            Stage userStage = new Stage();
            userStage.setScene(new Scene(userRoot));
            userStage.show();

            // Opcionalmente, puedes cerrar la ventana actual si es necesario
            Stage currentStage = (Stage) botonUsuario.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            // Manejo de errores al cargar la ventana de usuario
            
        }
    }
 
}
