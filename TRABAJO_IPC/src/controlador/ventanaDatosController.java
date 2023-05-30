package controlador;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafxmlapplication.JavaFXMLApplication;
import model.Club;
import model.ClubDAOException;
import model.Member;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class ventanaDatosController implements Initializable {

    @FXML
    private TextField textfieldNombreRegistro;
    @FXML
    private TextField textfieldApellidosRegistro;
    @FXML
    private TextField textfieldTlfRegistro;
    @FXML
    private PasswordField passfieldRegistro;
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
    
    private Member user;
    @FXML
    private ImageView imagenUsuario;
    @FXML
    private Label nickName;
    
    public void initMember(Member m) throws ClubDAOException, IOException{
        //Club c = Club.getInstance();
        //usuario = c.getMemberByCredentials(nick, pass);
        this.user = m;
        textfieldNombreRegistro.setText(user.getName());
        textfieldApellidosRegistro.setText(user.getSurname());
        nickName.setText(user.getNickName());
        passfieldRegistro.setText(user.getPassword());
        passfieldRepRegistro.setText(user.getPassword());
        textfieldTlfRegistro.setText(user.getTelephone());
        if(user.getCreditCard() != null){
            svcRegistro.setText(user.getCreditCard());
        }
        if(user.getSvc() != 0){
            svcRegistro.setText(Integer.toString(user.getSvc()));
        }
        imagenUsuario.setImage(user.getImage());
        //Circle circle = new Circle();
        //circle.setRadius(50);
        //imagenUsuario.setClip(circle);
        
        //Image imglogo = new Image(getClass().getResourceAsStream("/resources/logo2.PNG"));
        // logo.setFill(new ImagePattern(imglogo));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }    

    @FXML
    private void registrarClicked(ActionEvent event) {
        if(validarDatos(textfieldNombreRegistro) && validarDatos(textfieldApellidosRegistro) && validarTlf(textfieldTlfRegistro)
                && validarPassword(passfieldRegistro, passfieldRepRegistro)
                && validarCredit(tarjetaRegistro, svcRegistro)){
            
            // si han completado el campo svc cambiarlo a INTEGER y cambiarlo
            if(!svcRegistro.getText().isEmpty()){
                int svc = Integer.parseInt(svcRegistro.getText());
                user.setSvc(svc);
            }
            // cambiar datos
            user.setName(textfieldNombreRegistro.getText());
            user.setSurname(textfieldApellidosRegistro.getText());
            user.setPassword(passfieldRegistro.getText());
            user.setTelephone(textfieldTlfRegistro.getText());
            user.setCreditCard(tarjetaRegistro.getText());
            // Alerta : DATOS CAMBIADOS
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("Se ha registrado correctamente");
            alert.showAndWait();
        }
            
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Campos Incorrectos");
            alert.showAndWait();         
        }
    }

    @FXML
    private void volverClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/inicio.fxml"));
        Parent root = loader.load();
        
        JavaFXMLApplication.setRoot(root);
    }
    
    private boolean validarDatos(TextField t){
        String s = t.getText();
        return (!s.isEmpty()) && (s.trim().length() != 0);
    }
    
    // metodo para saber si el TextField de la CONTRASEÑA es valido
    private boolean validarPassword(TextField t, TextField t1){
        String s = t.getText();
        String r = "^(?=.*[0-9])"
                + "(?=.*[A-Z])"
                + "(?=.*[a-z])"
                + "(?=\\S+$).{6,20}$";
        
        Pattern p = Pattern.compile(r);
        Matcher m = p.matcher(s);
        return validarDatos(t) && m.matches() && s.equals(t1.getText());
    }
    
    // metodo para saber si el TextField del TELEFONO es valido
    private boolean validarTlf(TextField t){
        String s = t.getText();
        Pattern p = Pattern.compile("^\\d{9}$");
        Matcher m = p.matcher(s);
        return validarDatos(t) && m.matches();
    }
    
    // metodo para saber si el TextField de la TARJETA y el SVC es valido
    private boolean validarCredit(TextField t, TextField t1){
        // String cleanCreditCard = t.replaceAll("\\s|-", "");
        String s = t.getText();
        Pattern p = Pattern.compile("^\\d{16}$");
        Matcher m = p.matcher(s);
        String s1 = t1.getText();
        Pattern p1 = Pattern.compile("^\\d{3}$");
        Matcher m1 = p1.matcher(s1);
        return (t.getText().isEmpty()&& t1.getText().isEmpty()) || (validarDatos(t) && validarDatos(t1) && m.matches() && m1.matches());
    }
    
   // public void initData(Member m) {
     //   this.user = m;
   // }
    
}
