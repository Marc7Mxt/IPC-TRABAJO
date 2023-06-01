
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.io.IOException;
import model.ClubDAOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.Club;
import model.Court;
import model.Member;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Booking;

public class PistasPrueba2Controller implements Initializable {

    @FXML
    private GridPane gridPane;

    @FXML
    private Label labelHora1, labelHora2, labelHora3, labelHora4, labelHora5, labelHora6, labelHora7, labelHora8, labelHora9, labelHora10, labelHora11, labelHora12, labelHora13;
    @FXML
    private Label labelHoras;

    @FXML
    private DatePicker datePickerPistas;
    
    private Club club;
    private ArrayList<Member> members;
    private ArrayList<Court> courts;
    private ArrayList<Booking> dayBookings;
    
    private Member user;
    
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
    private DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("E MMM d");
    @FXML
    private Button botonInicio;
    @FXML
    private Button botonMisReservas;
    @FXML
    private Button botonUsuario;
    @FXML
    private Label labelHoras1;
    @FXML
    private Label labelHoras11;
    @FXML
    private Label labelHoras12;
    @FXML
    private Label labelHoras13;
    @FXML
    private Label labelHoras14;
    @FXML
    private Label labelHoras15;
    
    public void initMember(Member u) {
        this.user = u;
    }


    @Override
public void initialize(URL url, ResourceBundle rb) {
    // Inicializar el club y obtener los datos necesarios
    datePickerPistas.setValue(LocalDate.now());
    datePickerPistas.valueProperty().addListener((a, b, c) -> {
        setTimeSlotsGrid(c);
    });
    

    try {
        club = Club.getInstance();
        user = club.getMemberByCredentials(user.getNickName(),user.getPassword());
    } catch (ClubDAOException | IOException ex) {
        Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
    }

    members = new ArrayList<>(club.getMembers());
    courts = new ArrayList<>(club.getCourts());
    setTimeSlotsGrid(datePickerPistas.getValue());
    ArrayList<Label> elimlabels = new ArrayList<>();
}

private void setTimeSlotsGrid(LocalDate selectedDate) {
    limpiarCeldas();
    dayBookings = new ArrayList<>(club.getForDayBookings(selectedDate));
    ArrayList<LocalTime> horas = generarHorasDisponibles();
    int numPistas = courts.size();
    for (int i = 0; i < numPistas; i++) {
        Court court = courts.get(i);
        for (int j = 0; j < horas.size(); j++) {
            Label labelCelda = new Label();
            String usuario = "";
            boolean reservaEncontrada = false;
            for (Booking booking : dayBookings) {
                if (booking.getCourt() == court && booking.getFromTime().equals(horas.get(j))) {
                    usuario = booking.getMember().getNickName();
                    reservaEncontrada = true;
                    break;
                }
            }

            if (reservaEncontrada) {
                labelCelda.setText(usuario);
                labelCelda.setId("reservado");
                labelCelda.getStylesheets().add("/estilos/estiloPrincipal.css");
            } else {
                labelCelda.setText("Disponible");
                labelCelda.setId("disponible");
                labelCelda.getStylesheets().add("/estilos/estiloPrincipal.css");
            }

            gridPane.add(labelCelda, i + 1, j + 1);
            final int pistaIndex = i;
            final int horaIndex = j;
            labelCelda.setOnMouseClicked(this::reservarPista);
        }
    }
} 
    
    private ArrayList<LocalTime> generarHorasDisponibles() {
        ArrayList<LocalTime> horas = new ArrayList<>();
        LocalTime horaInicio = LocalTime.of(9, 0);
        LocalTime horaFin = LocalTime.of(21, 0);

        while (horaInicio.isBefore(horaFin) || horaInicio.equals(horaFin)) {
            horas.add(horaInicio);
            horaInicio = horaInicio.plusHours(1);
        }

        return horas;
    }

    private void limpiarCeldas() {
    ObservableList<Node> children = gridPane.getChildren();
    ArrayList<Node> etiquetasEliminar = new ArrayList<>();
    for (Node node : children) {
        if (node instanceof Label) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer columnIndex = GridPane.getColumnIndex(node);
            if (rowIndex != null && columnIndex != null && rowIndex > 0 && columnIndex > 0) {
                etiquetasEliminar.add(node);
            }
        }
    }

    gridPane.getChildren().removeAll(etiquetasEliminar);
    }
    
private void reservarPista(MouseEvent event) {
    Label celda = (Label) event.getSource();
    int columnIndex = GridPane.getColumnIndex(celda);
    int rowIndex = GridPane.getRowIndex(celda);

    if (columnIndex > 0 && rowIndex > 0) {
        int pistaIndex = columnIndex - 1;
        int horaIndex = rowIndex - 1;
        Court court = courts.get(pistaIndex);
        LocalTime hora = generarHorasDisponibles().get(horaIndex);
        if (ControladorPrincipal.isLogged()) {
            if (isPistaDisponible(court, hora)) {
                List<Booking> userBookings = club.getUserBookings(user.getNickName());
                if (tieneReservasConsecutivas(userBookings)) {
                    
                    Alert fallida = new Alert(AlertType.INFORMATION);
                    fallida.setTitle("Reserva Fallida");
                    fallida.setHeaderText(null);
                    fallida.setContentText("No puedes hacer reservas consecutivas");
                    fallida.showAndWait();
                    return;
                }

                Alert reservar = new Alert(AlertType.CONFIRMATION);
                reservar.setTitle("Anular Reserva");
                reservar.setHeaderText(null);
                reservar.setContentText("¿Quieres reservar el día " + datePickerPistas.getValue().toString() + " a las " + hora.toString() + " en la pista " + court.getName() + "?");
                Optional<ButtonType> result = reservar.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Member member = user;
                    boolean paid = true;
                    LocalDateTime bookingDate = LocalDateTime.now();
                    LocalDate madeForDay = datePickerPistas.getValue();

                    try {
                        club.registerBooking(bookingDate, madeForDay, hora, paid, court, member);
                    } catch (ClubDAOException ex) {
                        Logger.getLogger(PistasPrueba2Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    celda.setText(user.getNickName());
                    celda.setId("reservado");
                    celda.getStyleClass().add("/estilos/estiloPrincipal.css");
                }
            } else {
                Alert fallida = new Alert(AlertType.INFORMATION);
                fallida.setTitle("Reserva Fallida");
                fallida.setHeaderText(null);
                fallida.setContentText("No se pudo reservar porque existe otra reserva en esta hora");
                fallida.showAndWait();
            }
        } else {
            Alert fallida = new Alert(AlertType.INFORMATION);
            fallida.setTitle("Reserva Fallida");
            fallida.setHeaderText(null);
            fallida.setContentText("Dirígete a Usuario para iniciar sesión y poder reservar");
            fallida.showAndWait();
        }
    }
}


    private boolean isPistaDisponible(Court court, LocalTime hora) {
    for (Booking booking : dayBookings) {
        LocalTime bookingHora = booking.getFromTime();
        if (booking.getCourt() == court && bookingHora.getHour() == hora.getHour()) {
            return false;
        }
    }
    return true; 
    }
    
    public boolean tieneReservasConsecutivas(List<Booking> reservas) {
    for (int i = 0; i < reservas.size() - 2; i++) {
        Booking reservaActual = reservas.get(i);
        Booking siguienteReserva1 = reservas.get(i + 1);
        Booking siguienteReserva2 = reservas.get(i + 2);
        if (sonReservasConsecutivas(reservaActual, siguienteReserva1, siguienteReserva2) && reservaActual.getMember().getNickName().equals(siguienteReserva1.getMember().getNickName()) && reservaActual.getMember().getNickName().equals(siguienteReserva2.getMember().getNickName()) && reservaActual.belongsToMember(user.getNickName())) {
            return true;
        }
    }
    
    return false; 
}

    public boolean sonReservasConsecutivas(Booking r1, Booking r2, Booking r3) {
        
        if (r2.getMadeForDay().equals(r1.getMadeForDay()) && r3.getMadeForDay().equals(r1.getMadeForDay()) &&
            r2.getFromTime().equals(r1.getFromTime().plusHours(1))&& r3.getFromTime().equals(r1.getFromTime().plusHours(2))) {
            return true; 
        }

        return false;
    }

    @FXML
    private void handleReservation(MouseEvent event) {
    }

    @FXML
    private void IraInicio(ActionEvent event) throws IOException, ClubDAOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/inicio.fxml"));
        Parent userRoot = loader.load();
        InicioController inicioController = loader.getController();
        if(user!=null){
            inicioController.initMember(user.getNickName(), user.getPassword());
        }
        Stage inicioStage = new Stage();
        inicioStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/fotostrabajo/logo.jpeg")));
        inicioStage.setTitle("CLUB DE TENIS GREENBALL");
        inicioStage.setScene(new Scene(userRoot));
        inicioStage.show();
        Stage currentStage = (Stage) botonUsuario.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void IraMisreservas(ActionEvent event) throws ClubDAOException {
        if(ControladorPrincipal.isLogged()){
            verVentanaMisReservas();
        } else {
            verVentanaLogin();
        }
    }

    @FXML
    private void IraUsuario(ActionEvent event) throws ClubDAOException {
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