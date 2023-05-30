/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author Marc
 */
public class ControladorPrincipal {
    private static boolean loggedIn = false;

    public static void cambiarLoggedIn(boolean  b) {
        loggedIn = b;
    }

    public static boolean isLogged() {
        return loggedIn;
    }
}