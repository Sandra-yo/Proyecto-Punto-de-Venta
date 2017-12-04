package Pruebas;

import Conexion_Base_datos.UsuariosCB;
import Interfaces.Principal;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author sandra
 */
public class Prueba_Vista_transacciones {

    public static void main(String[] args) {
        Principal n = new Principal();
        n.setSize(1000, 800);
        n.setResizable(false);
        n.setVisible(true);
        n.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     UsuariosCB v= new UsuariosCB();
       // System.out.println( v.getTipo());
     
    }

}
