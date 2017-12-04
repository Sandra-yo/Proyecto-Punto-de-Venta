/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Conexion_Base_datos.Conexion;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author sandra
 */
public class Login extends JFrame implements ActionListener {
    JTextField usu,contr;
    JPasswordField contraseña;
    JLabel usua,cont,mensaje;
    JButton btn;
    String contraseñaT;
    String usuario;
    String tipo;
    
    public Login(){
        super("Inicio de Sesion");
        setLayout(new FlowLayout());
        usu= new JTextField(13);
        contraseña= new JPasswordField(13);
        contr= new JTextField(13);
        
        usua= new JLabel("Usuario:");
        cont=new JLabel("Contraseña:");
        
        mensaje= new JLabel();
        
        btn= new JButton("Entrar");
        btn.addActionListener(this);
        
        
        add(usua);
        add(usu);
        add(cont);
        add(contraseña);
        add(mensaje);
        add(btn);
    }
    public String leeContraseña(){
        char[] c= contraseña.getPassword();
        String s=new String(c);
        return s;

    }
    public static void main(String[] args) {
        Login n= new Login();
        n.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        n.setVisible(true);
        n.setSize(300,400);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        usuario=usu.getText();
        
        String sql="select*from Usuario where id_usuario="+usuario+" and contraseña= '"+leeContraseña()+"';";
        Conexion.conectarBD();
          ResultSet r = Conexion.ejecutarSQLSelect(sql);
        try {
            
            while(r.next()){
                  contr.setText((String) r.getObject(5));
                                  
                 Principal n= new Principal();
                 n.setVisible(true);
                 n.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                 n.setSize(800, 600);
                 n.setResizable(false);
                 tipo=(String) r.getObject(2);
                 
                 dispose();
                 
              }
                mensaje.setText("El usuario no existe dentro de la base de datos");     
               
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
