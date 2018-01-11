/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author sandra
 */
public class Transacciones extends JFrame {
    JLabel Jl[];
    JTextField jtxt[];
    JButton btn[];
    JPanel uno;
    JPanel paneles[];
    JTable tabla;
    String nombres[]={"producto","ID","cantidad","fecha","","usuario",""};
    
 public Transacciones(){
     super("Ferreteria");
     
     uno.setLayout(new GridLayout(3,1));
     paneles= new JPanel[5];
     
     add(uno,BorderLayout.CENTER);
 }
    
}
