/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.*;

/**
 * FXML Controller class
 *
 * @author Marc
 */
public class registroController implements Initializable {

    @FXML
    private TextField textfieldNombreRegistro;
    @FXML
    private TextField textfieldApellidosRegistro;
    @FXML
    private TextField textfieldTlfRegistro;
    @FXML
    private TextField textfieldNicknameRegistro;
    @FXML
    private PasswordField passfieldRegistro;
    @FXML
    private ComboBox<String> comboBoxRegistro;
    @FXML
    private TextField tarjetaRegistro;
    @FXML
    private TextField svcRegistro;
    @FXML
    private Button botonRegistrar;
    @FXML
    private PasswordField passfieldRepRegistro;
    @FXML
    private Button botonVolver;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxRegistro.getItems().addAll("/avatars/men.PNG", "/avatars/men2.PNG",
                "/avatars/men3.PNG", "/avatars/men4.PNG", "/avatars/men5.PNG",
                "/avatars/woman.PNG", "/avatars/woman2.PNG", "/avatars/woman3.PNG",
                "/avatars/woman4.PNG", "/avatars/woman5.PNG", "/avatars/woman6.PNG");
        comboBoxRegistro.setCellFactory(c -> new ImagenTabCell());

        TextFormatter<String> numTlf = new TextFormatter<>(c -> {
            String newText = c.getControlNewText();
            if (newText.matches("\\d*")) {
                return c;
            }
            return null;
        });

        TextFormatter<String> maxNumCredit = new TextFormatter<>(c -> {
            String entradaTeclado = c.getControlNewText();
            if (!entradaTeclado.matches("\\d*")) {
                return null;
            }
            if (entradaTeclado.length() > 16) {
                return null;
            }

            return c;
        });

        TextFormatter<String> maxNumSVC = new TextFormatter<>(c -> {
            String entradaTeclado = c.getControlNewText();
            if (!entradaTeclado.matches("\\d*")) {
                return null;
            }
            if (entradaTeclado.length() > 3) {
                return null;
            }
            return c;
        });

        TextFormatter<String> noSpaces = new TextFormatter<>(c -> {
            String entradaTeclado = c.getControlNewText();

            // Verificar si el nuevo texto contiene espacios en blanco
            if (entradaTeclado.contains(" ")) {
                return null;
            }

            return c;
        });

        textfieldTlfRegistro.setTextFormatter(numTlf);
        tarjetaRegistro.setTextFormatter(maxNumCredit);
        svcRegistro.setTextFormatter(maxNumSVC);
        textfieldNicknameRegistro.setTextFormatter(noSpaces);

    }

    @FXML
    private void registrarClicked(ActionEvent event) throws ClubDAOException, IOException {
        Club club = Club.getInstance();

        // revisar que todos los campos son correctos
        if (validarDatos(textfieldNombreRegistro) && validarDatos(textfieldApellidosRegistro) && validarTlf(textfieldTlfRegistro)
                && validarNickname(textfieldNicknameRegistro) && validarPassword(passfieldRegistro, passfieldRepRegistro)
                && validarCredit(tarjetaRegistro, svcRegistro)) {
            int svc = 0;
            // si han completado el campo svc cambiarlo a INTEGER
            if (!svcRegistro.getText().isEmpty()) {
                svc = Integer.parseInt(svcRegistro.getText());
            }
            Image img;
            // si no han elegido avatar poner el default
            // si han elegido darle un valor a img
            if (comboBoxRegistro.getValue() == null) {
                img = new Image("/avatars/default.png");
            } else {
                img = new Image(comboBoxRegistro.getValue());
            }
            // Alerta : REGISTRO CORRECTO
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Información");
            alert.setContentText("Se ha registrado correctamente");
            alert.showAndWait();
            // registrar el nuevo miembro
            club.registerMember(textfieldNombreRegistro.getText(), textfieldApellidosRegistro.getText(),
                    textfieldTlfRegistro.getText(), textfieldNicknameRegistro.getText(), passfieldRegistro.getText(),
                    tarjetaRegistro.getText(), svc, img);
            Stage stage = (Stage) botonRegistrar.getScene().getWindow();
            stage.close();
        } else {
            if (club.existsLogin(textfieldNicknameRegistro.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Nickname ya existente");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Completa correctamente los campos de registro");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void volverClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/login.fxml"));
        Parent userRoot = loader.load();
        Stage loginStage = new Stage();
        //loginStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/fotostrabajo/logo.jpeg")));
        //loginStage.setTitle("INICIO DE SESIÓN");
        //loginStage.setScene(new Scene(userRoot));
        //loginStage.show();
        Stage stage = (Stage) botonVolver.getScene().getWindow();
        stage.close();
    }

    class ImagenTabCell extends ComboBoxListCell<String> {

        private ImageView view = new ImageView();
        private Image imagen;

        @Override
        public void updateItem(String t, boolean bln) {
            super.updateItem(t, bln);
            if (t == null || bln) {
                setText(null);
                setGraphic(null);
            } else {
                imagen = new Image(t, 50, 60, true, true);
                view.setImage(imagen);
                setGraphic(view);
                setText(null);
            }
        }
    }
// metodo para saber si el TextField es valido

    private boolean validarDatos(TextField t) {
        String s = t.getText();
        return (!s.isEmpty()) && (s.trim().length() != 0);
    }

    // metodo para saber si el TextField del NICK es valido
    private boolean validarNickname(TextField t) throws ClubDAOException, IOException {
        Club club = Club.getInstance();
        boolean existe = club.existsLogin(t.getText());
        String s = t.getText();
        return validarDatos(t) && !existe;
    }

    // metodo para saber si el TextField de la CONTRASEÑA es valido
    private boolean validarPassword(TextField t, TextField t1) {
        String s = t.getText();
        String r = "^(?=.*[0-9])"
                + "(?=.*[a-z])"
                + "(?=\\S+$).{6,20}$";

        Pattern p = Pattern.compile(r);
        Matcher m = p.matcher(s);
        return validarDatos(t) && m.matches() && s.equals(t1.getText());
    }

    // metodo para saber si el TextField del TELEFONO es valido
    private boolean validarTlf(TextField t) {
        String s = t.getText();
        Pattern p = Pattern.compile("^\\d{9}$");
        Matcher m = p.matcher(s);
        return validarDatos(t) && m.matches();
    }

    // metodo para saber si el TextField de la TARJETA y el SVC es valido
    private boolean validarCredit(TextField t, TextField t1) {
        // String cleanCreditCard = t.replaceAll("\\s|-", "");
        String s = t.getText();
        Pattern p = Pattern.compile("^\\d{16}$");
        Matcher m = p.matcher(s);
        String s1 = t1.getText();
        Pattern p1 = Pattern.compile("^\\d{3}$");
        Matcher m1 = p1.matcher(s1);
        return (t.getText().isEmpty() && t1.getText().isEmpty()) || (validarDatos(t) && validarDatos(t1) && m.matches() && m1.matches());
    }

}
