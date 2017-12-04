/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Conexion_Base_datos.PersonasBD;
import Conexion_Base_datos.UsuariosCB;
import Extras.Buscar;
import Extras.BuscarEnTabla;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author sandra
 */
public class Usuarios extends JFrame {

    public JPanel fondo, pan1, fondoTbla, TDatosP,espacio;
    public JLabel etiq[], titulo;
    public JTextField text[];
    private JPasswordField pass;
    public String nombres[] = {"Id Usuario", "Nombre", "Apellido Primero", "Apellido Segundo", "direccion", "colonia", "corr"
            + "eo", "Fecha de nacimiento", "Tipo", "Comision","Contraseña"};
    public UsuariosCB usu;
    public PersonasBD per;
    public JTable tabla, tabla2;
           DateFormat date;
    JButton botonPDF;
    
    public Usuarios() {

        usu = new UsuariosCB();
        per = new PersonasBD();

        date = new SimpleDateFormat("yyyy-M-d");
        pan1 = new JPanel();
        fondo = new JPanel();
        fondo.setLayout(null);
        pan1.setLayout(new GridLayout(6, 2));

        //  botones
        espacio= new JPanel();
        botonPDF= new JButton("Reportes");
        botonPDF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
        Reportes m = new Reportes();
        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m.setVisible(true);
    
            }
        });
        
        text = new JTextField[nombres.length];
        etiq = new JLabel[nombres.length];
        titulo = new JLabel("Usuarios");
        pass= new JPasswordField();
        for (int i = 0; i < nombres.length; i++) {
            text[i] = new JTextField();
            etiq[i] = new JLabel();
            pan1.add(etiq[i]);
            pan1.add(text[i]);
        }
         pan1.add(espacio);
         pan1.add(botonPDF);
        for (int i = 0; i < nombres.length; i++) {
            etiq[i].setText(nombres[i]);

        }

        
        //Tabla
        usu.agregarColumnas();
        per.agregarColumnas();
        armaTabla();

        tabla.setAutoCreateRowSorter(true);
        tabla2.setAutoCreateRowSorter(true);

        pan1.setBounds(20, 20, 550, 300);
        titulo.setBounds(20, 0, 110, 20);
        fondo.add(titulo);
        fondo.add(pan1);
        tabla.addMouseListener(new Escucha());
        tabla2.addMouseListener(new Escucha());
        add(fondo);
    }

    public void agregarDatosUsuario() {
        
        per.setNombre(text[1].getText());
        per.setApellido_P(text[2].getText());
        per.setApellido_M(text[2].getText());
        per.setDireccion(text[4].getText());
        per.setColonia(text[5].getText());
        per.setCorreo(text[6].getText());
        per.setFecha_n(text[7].getText());
        per.setTipo("usuario");
        per.agregar();
        
        
        
      
    }
    public void agregarUsuario() throws SQLException{
        per.setNombre(text[1].getText());
        ResultSet r= per.buscar();
       
       if(r.next()){
        usu.setId_person((int) r.getObject(1));}
        usu.setTipo(text[8].getText());
        usu.setComision(Integer.parseInt(text[9].getText()));
        usu.setContraseña(text[10].getText());
        usu.agregar();
        
    }

    public void armaTabla() {
        fondoTbla = new JPanel();
        tabla = new JTable(usu.LlenarTabla());
        JScrollPane scroll = new JScrollPane(tabla);
        fondoTbla.setLayout(new BorderLayout());
        fondoTbla.add(scroll, BorderLayout.CENTER);
        //datos personales
        TDatosP = new JPanel();
        tabla2 = new JTable(per.LlenarTabla("select*from persona where tipo='usuario'"));
        JScrollPane scroll2 = new JScrollPane(tabla2);
        TDatosP.setLayout(new BorderLayout());
        TDatosP.add(scroll2, BorderLayout.CENTER);
    }

    public void actualizarTabla() {
        usu.borrarTabla();
        per.borrarTabla();
        armaTabla();
    }

    public void borrarUsuario() {
        usu.setId_usuario(Integer.parseInt(text[0].getText()));
        usu.borrar();
        
        
        per.setNombre(text[1].getText());
        per.setApellido_P(text[2].getText());
        per.setApellido_M(text[3].getText());
        
        per.borrar();
    }

    public void modificarUsuario() {
        usu.setId_usuario(Integer.parseInt(text[0].getText()));
        usu.setTipo(text[8].getText());
        usu.setComision(Integer.parseInt(text[9].getText()));
        usu.setContraseña(text[10].getText());
        usu.modificar();
       
        per.setNombre(text[1].getText());
        per.setApellido_P(text[2].getText());
        per.setApellido_M(text[3].getText());
        per.setDireccion(text[4].getText());
        per.setColonia(text[5].getText());
        per.setCorreo(text[6].getText());
        per.setFecha_n(text[7].getText());
        per.modificar();
    }

    public void buscarUsuario() {
        per.borrarTabla();
        usu.borrarTabla();
        usu.setId_usuario(Integer.parseInt(text[0].getText()));
        
        ResultSet t = usu.buscar("select* from Usuario u join persona p where u.id_person_fk=p.id_person and id_usuario="+usu.getId_usuario());
        try {
            if (t.next()) {
                text[1].setText((String) t.getObject(8));
                text[2].setText((String) t.getObject(9));
                text[3].setText((String) t.getObject(10));
                text[4].setText((String) t.getObject(11));
                text[5].setText((String) t.getObject(12));
                text[6].setText((String) t.getObject(13));
                text[7].setText((String) t.getObject(15));
                text[8].setText((String) t.getObject(2));
                text[9].setText(Integer.toString((int) t.getObject(3)));
                text[10].setText((String)t.getObject(5));
            }
            per.setNombre(text[1].getText());
            per.setApellido_P(text[2].getText());
            per.setApellido_M(text[3].getText());
            
           
            BuscarEnTabla m= new BuscarEnTabla(per.dtm,"select nombre,apellido_1,apellido_2,direccion,colonia,correo,fecha_nacimiento from Usuario u join persona p where u.id_person_fk=p.id_person and id_usuario="+usu.getId_usuario(),7);
            BuscarEnTabla n= new BuscarEnTabla(usu.dtm,"select  id_usuario, u.tipo ,comision  from Usuario u join persona p where u.id_person_fk=p.id_person and id_usuario="+usu.getId_usuario(),3);
            m.start();
            n.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  

    private class Escucha extends MouseAdapter {

        public void mouseClicked(MouseEvent evento) {
            
                
                
            if (evento.getSource() == tabla) {
                text[0].setText(Integer.toString((int) usu.dtm.getValueAt(tabla.getSelectedRow(), 0)));
                text[8].setText((String) usu.dtm.getValueAt(tabla.getSelectedRow(), 1));
                text[9].setText(Integer.toString((int) usu.dtm.getValueAt(tabla.getSelectedRow(), 2)));
                
                usu.setId_usuario(Integer.parseInt(text[0].getText()));
                ResultSet r=usu.buscar("select* from persona p join Usuario u  where u.id_person_fk=p.id_person and id_usuario= "+usu.getId_usuario());
                    try {
                        if (r.next()){
                            
                        text[1].setText((String) r.getObject(2));
                        text[2].setText((String) r.getObject(3));
                        text[3].setText((String) r.getObject(4));
                        text[4].setText((String) r.getObject(5));
                        text[5].setText((String) r.getObject(6));
                        text[6].setText((String) r.getObject(7));
                        text[7].setText((String) r.getObject(9));
                        text[10].setText((String)r.getObject(16));
                        }   } catch (SQLException ex) {
                        Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
                    }

               
                
            } else if (evento.getSource() == tabla2) {
            
                text[1].setText((String) per.dtm.getValueAt(tabla2.getSelectedRow(), 0));
                text[2].setText((String) per.dtm.getValueAt(tabla2.getSelectedRow(), 1));
                text[3].setText((String) per.dtm.getValueAt(tabla2.getSelectedRow(), 2));
                text[4].setText((String) per.dtm.getValueAt(tabla2.getSelectedRow(), 3));
                text[5].setText((String) per.dtm.getValueAt(tabla2.getSelectedRow(), 4));
                text[6].setText((String) per.dtm.getValueAt(tabla2.getSelectedRow(), 5));
                text[7].setText((String) per.dtm.getValueAt(tabla2.getSelectedRow(), 6));
                
                
                ResultSet r=usu.buscar("select* from  Usuario u join persona p where u.id_person_fk=p.id_person and nombre='"+text[1].getText()+"' and apellido_1='"+text[2].getText()+"' and apellido_2='"+text[3].getText()+"'");
                    try {
                        if (r.next()){
                         
                        text[0].setText(Integer.toString((int)r.getObject(1)));
                        text[8].setText((String) r.getObject(2));
                        text[9].setText(Integer.toString((int)r.getObject(3)));
                        text[10].setText((String)r.getObject(5));
                        }   } catch (SQLException ex) {
                        Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
                    }

                
               

            }
        }
    }

}
