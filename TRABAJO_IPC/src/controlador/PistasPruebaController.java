/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.Booking;
import model.Club;
import model.ClubDAOException;

/**
 * FXML Controller class
 *
 * @author Marc
 */
public class PistasPruebaController implements Initializable {

    @FXML
    private TableView<Booking> pistasTableView;
    @FXML
    private TableColumn<Booking, String> columnaHorarios;
    @FXML
    private TableColumn<Booking, String> columnaPista1;
    @FXML
    private TableColumn<Booking, String> columnaPista2;
    @FXML
    private TableColumn<Booking, String> columnaPista3;
    @FXML
    private TableColumn<Booking, String> columnaPista4;
    @FXML
    private TableColumn<Booking, String> columnaPista5;
    @FXML
    private TableColumn<Booking, String> columnaPista6;
    @FXML
    private DatePicker datePickerPistas;
    
    private ObservableList<LocalTime> horario = null;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
            // TODO
            // Club.getInstance().addSimpleData();
            columnaHorarios.setCellValueFactory(new Callback<CellDataFeatures<Booking, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Booking, String> data) {
                int index = data.getValue().getFromTime().getHour(); // Obtener la hora de inicio de la reserva
                String[] horarios = {
                    "9:00 - 10:00", "10:00 - 11:00", "11:00 - 12:00", "12:00 - 13:00",
                    "13:00 - 14:00", "14:00 - 15:00", "15:00 - 16:00", "16:00 - 17:00",
                    "17:00 - 18:00", "18:00 - 19:00", "19:00 - 20:00", "20:00 - 21:00",
                    "21:00 - 22:00"
                };

                if (index >= 9 && index <= 21) {
                    int hour = index - 9;
                    return new SimpleStringProperty(horarios[hour]);
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });            /*columnaHorarios.setCellFactory(c -> {
                return new TableCell<Booking, LocalTime>(){
                    @Override
                    protected void updateItem(LocalTime t, boolean bln) {
                        super.updateItem(t, bln);
                        if(bln || t == null){
                            setText(null);
                        } else {
                            setText(t.format(DateTimeFormatter.ofPattern("HH:mm")));
                        }
                    }
                    
                };
            }); */

            columnaPista1.setCellValueFactory(c -> {
                Booking booking = c.getValue();
                if (booking == null) {
            // Pista disponible
                return new SimpleStringProperty("Disponible");
                } else {
            // Pista reservada
                    LocalDate diaSeleccionado = datePickerPistas.getValue();
                    if (booking.getMadeForDay().isEqual(diaSeleccionado)) {
                        return new SimpleStringProperty(booking.getMember().getNickName());
                    } else {
                        return new SimpleStringProperty("");
                    }
                }
            });
            
            columnaPista2.setCellValueFactory(c -> {
                Booking booking = c.getValue();
                if (booking == null) {
            // Pista disponible
                return new SimpleStringProperty("Disponible");
                } else {
            // Pista reservada
                    LocalDate diaSeleccionado = datePickerPistas.getValue();
                    if (booking.getMadeForDay().isEqual(diaSeleccionado)) {
                        return new SimpleStringProperty(booking.getMember().getNickName());
                    } else {
                        return new SimpleStringProperty("");
                    }
                }
            });
            
            columnaPista3.setCellValueFactory(c -> {
                Booking booking = c.getValue();
                if (booking == null) {
            // Pista disponible
                return new SimpleStringProperty("Disponible");
                } else {
            // Pista reservada
                    LocalDate diaSeleccionado = datePickerPistas.getValue();
                    if (booking.getMadeForDay().isEqual(diaSeleccionado)) {
                        return new SimpleStringProperty(booking.getMember().getNickName());
                    } else {
                        return new SimpleStringProperty("");
                    }
                }
            });
            
            columnaPista4.setCellValueFactory(c -> {
                Booking booking = c.getValue();
                if (booking == null) {
            // Pista disponible
                return new SimpleStringProperty("Disponible");
                } else {
            // Pista reservada
                    LocalDate diaSeleccionado = datePickerPistas.getValue();
                    if (booking.getMadeForDay().isEqual(diaSeleccionado)) {
                        return new SimpleStringProperty(booking.getMember().getNickName());
                    } else {
                        return new SimpleStringProperty("");
                    }
                }
            });
            
            columnaPista5.setCellValueFactory(c -> {
                Booking booking = c.getValue();
                if (booking == null) {
            // Pista disponible
                return new SimpleStringProperty("Disponible");
                } else {
            // Pista reservada
                    LocalDate diaSeleccionado = datePickerPistas.getValue();
                    if (booking.getMadeForDay().isEqual(diaSeleccionado)) {
                        return new SimpleStringProperty(booking.getMember().getNickName());
                    } else {
                        return new SimpleStringProperty("");
                    }
                }
            });
            
            columnaPista6.setCellValueFactory(c -> {
                Booking booking = c.getValue();
                if (booking == null) {
            // Pista disponible
                return new SimpleStringProperty("Disponible");
                } else {
            // Pista reservada
                    LocalDate diaSeleccionado = datePickerPistas.getValue();
                    if (booking.getMadeForDay().isEqual(diaSeleccionado)) {
                        return new SimpleStringProperty(booking.getMember().getNickName());
                    } else {
                        return new SimpleStringProperty("");
                    }
                }
            });
            
            
            List<Booking> bookings = Club.getInstance().getBookings();
            pistasTableView.setItems(FXCollections.observableArrayList(bookings));

            // Obtener la fecha actual y mostrar la disponibilidad de las pistas para esa fecha
            LocalDate diaActual = LocalDate.now();
            datePickerPistas.setValue(diaActual);
            datePickerPistas.setDayCellFactory((DatePicker picker) -> {
                return new  DateCell(){
                    @Override
                    public void updateItem(LocalDate date, boolean empty){
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now();
                    setDisable(empty || date.compareTo(today) < 0); 
                }
            };
         });
            datePickerPistas.valueProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    LocalDate selectedDate = newValue; // Obtener la nueva fecha seleccionada
                    updateTableView(selectedDate); // Actualizar el contenido del TableView
                } catch (ClubDAOException ex) {
                    Logger.getLogger(PistasPruebaController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(PistasPruebaController.class.getName()).log(Level.SEVERE, null, ex);
                }
        });
        
        } catch (ClubDAOException ex) {
            Logger.getLogger(PistasPruebaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PistasPruebaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void IraInicio(ActionEvent event) {
    }

    @FXML
    private void IraMisreservas(ActionEvent event) {
    }

    private void updateTableView(LocalDate selectedDate) throws ClubDAOException, IOException {
        // Obtener las reservas para el día seleccionado desde tu lógica de negocio
        List<Booking> bookings = Club.getInstance().getForDayBookings(selectedDate);

        // Actualizar el contenido del TableView con las nuevas reservas
        pistasTableView.setItems(FXCollections.observableList(bookings));
    }
    
    
    
}
