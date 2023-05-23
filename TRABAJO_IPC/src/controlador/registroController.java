/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        comboBoxRegistro.getItems().addAll("/avatars/men.PNG","/avatars/men2.PNG",
                "/avatars/men3.PNG","/avatars/men4.PNG","/avatars/men5.PNG",
                "/avatars/woman.PNG","/avatars/woman2.PNG","/avatars/woman3.PNG",
                "/avatars/woman4.PNG","/avatars/woman5.PNG","/avatars/woman6.PNG");
        comboBoxRegistro.setCellFactory(c -> new ImagenTabCell());
    }    

    @FXML
    private void registrarClicked(ActionEvent event) {
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
                imagen = new Image(t,50,60,true,true);
                view.setImage(imagen);
                setGraphic(view);
                setText(null);
            }
        }
    }

    
}

