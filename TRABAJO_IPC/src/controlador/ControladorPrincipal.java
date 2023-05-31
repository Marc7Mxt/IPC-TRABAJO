/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Member;

/**
 *
 * @author Marc
 */
public class ControladorPrincipal {
    
    //private StringProperty nicknameProperty = new SimpleStringProperty();
    //private StringProperty passwordProperty = new SimpleStringProperty();
    
    private static Member user;
    
    private static boolean loggedIn = false;

    public static void cambiarLoggedIn(boolean  b) {
        loggedIn = b;
    }

    public static boolean isLogged() {
        return loggedIn;
    }
    
    //public StringProperty getNicknameProperty() {
    //    return nicknameProperty;
    //}
    
    //public StringProperty getPasswordProperty() {
    //    return passwordProperty;
    //}
    
    public static void setUsuario(Member m) {
        ControladorPrincipal.user = m;
    }
    
    public static Member getUsuario() {
        return user;
    }
}