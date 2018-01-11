/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Conexion_Base_datos.PersonasBD;
import Conexion_Base_datos.ProveedoresBD;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author sandra
 */
public class Proveedores {
    JPanel fondo,pan1,pan5;
    JLabel etiq[],titulo;
    JTextField text[];
    String nombres[]={"Nombre","Apellido Primero","Apellido Segundo","Direccion","Colonia","correo","Telefonos","Fecha Nacimiento"};
    String d_Proveedores[]={"Id","Ultima visita"};
    JTabbedPane pestañas;
    JTable tableDatos, tableProveedores;
    ProveedoresBD pro;
    PersonasBD per;
    public Proveedores(){
        
        pan5= new JPanel();
        pan1= new JPanel();
        fondo= new JPanel();
        fondo.setLayout(null);
        pan1.setLayout(new GridLayout(5,2));
        pan5.setLayout(new FlowLayout());
        
        JButton btn= new JButton();
        int e=0;
        int valor=nombres.length+d_Proveedores.length;
        
        text= new JTextField[valor];
        etiq= new JLabel[valor];
        titulo= new JLabel("Proveedores");
        
        //clases
        pro= new ProveedoresBD();
        per=new PersonasBD();
        
        for (int i = 0; i < valor; i++) {
            text[i]= new JTextField();
            etiq[i]= new JLabel();
            pan1.add(etiq[i]);
            pan1.add(text[i]);
        }
        for (int i = 0; i < d_Proveedores.length; i++) {
            etiq[i].setText(d_Proveedores[i]);
        }
        for (int i = d_Proveedores.length; i < valor; i++) {
            etiq[i].setText(nombres[e]);
            e++;
        }
        //tabla
        tableProveedores = new JTable(pro.dtm);
        tableDatos = new JTable(per.dtm);
        per.agregarColumnas();
        pro.agregarColumnas();
        armarTablas();
        
        pan1.setBounds(20,20,550,300);
        titulo.setBounds(20,0,110,20);
        fondo.add(titulo);
        fondo.add(pan1);
         //scroll
        JScrollPane uno = new JScrollPane(tableProveedores);
        JScrollPane dos = new JScrollPane(tableDatos);

        //pestañas
        pestañas = new JTabbedPane();
        //pestañas.setVisible(false);

        
        //Visibilidad
        fondo.setVisible(false);
        pestañas.addTab("Proveedor", uno);
        pestañas.addTab("datos Personales", dos);
        pan5.add(pestañas);
        
    
        //escucha
        tableDatos.addMouseListener(new Escucha());
        tableProveedores.addMouseListener(new Escucha());
    
    }
    
 public void armarTablas() {
        tableDatos = new JTable(per.LlenarTabla("select*from persona where tipo='Proveedor'"));
        
        tableProveedores = new JTable(pro.LlenarTabla());
        

    }
 public void agregarProveedor() throws SQLException {
         per.setNombre(text[2].getText());
         per.setApellido_P(text[3].getText());
         per.setApellido_M(text[4].getText());
         ResultSet r= per.buscar();
         
        pro.setVisita(text[1].getText());
       if (r.next()) {
           System.out.println("---");
            pro.setIdPerson((int) r.getObject(1));
        }
        pro.agregar();
    }
 public void agregarDatosUsuario() {
        
        per.setNombre(text[2].getText());
        per.setApellido_P(text[3].getText());
        per.setApellido_M(text[4].getText());
        per.setDireccion(text[5].getText());
        per.setColonia(text[6].getText());
        per.setCorreo(text[7].getText());
        per.setFecha_n(text[9].getText());
        per.setTipo("Proveedor");
        per.agregar();
        
        
        
      
    }
 public void buscarProveedor() {
        pro.setId(Integer.parseInt(text[0].getText()));
        
        ResultSet t = per.buscar("select* from proveedores p join persona pe where p.id_person_fk=pe.id_person and id_proveedor="+pro.getId());
        try {
            if (t.next()) { 
                text[1].setText((String) t.getObject(2));
                text[2].setText((String) t.getObject(5));
                text[3].setText((String) t.getObject(6));
                text[4].setText((String) t.getObject(7));
                text[5].setText((String) t.getObject(8));
                text[6].setText((String) t.getObject(9));
                text[7].setText((String) t.getObject(10));
                text[9].setText((String) t.getObject(12));

            }
            per.setNombre(text[2].getText());
            per.setApellido_P(text[3].getText());
            per.setApellido_M(text[4].getText());
            pro.setId(Integer.parseInt(text[0].getText()));
            
            tableProveedores= new JTable(pro.buscarEnTabla());
            tableDatos= new JTable(per.buscarEnTabla());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 public void limpiar(){
        for (int i = 0; i < text.length; i++) {
            text[i].setText(" ");
        }
    }
 public void modificarProveedor() {//<-----------
        pro.setId(Integer.parseInt(text[0].getText()));
        pro.setVisita(text[1].getText());
        
        pro.modificar();
       
        per.setNombre(text[2].getText());
        System.out.println(per.getNombre());
        per.setApellido_P(text[3].getText());
        System.out.println(per.getApellido_P());
        per.setApellido_M(text[4].getText());
        System.out.println(per.getApellido_M());
        per.setDireccion(text[5].getText());
        per.setColonia(text[6].getText());
        per.setCorreo(text[7].getText());
        per.setFecha_n(text[9].getText());
        per.modificar();
    }
 public void borrarProveedor() {
        pro.setId(Integer.parseInt(text[0].getText()));
        pro.borrar();
        
        per.setNombre(text[2].getText());
        per.setApellido_P(text[3].getText());
        per.setApellido_M(text[4].getText());
        
        per.borrar();
    }
   public void actualizarTabla() {
        pro.borrarTabla();
        per.borrarTabla();
        armarTablas();
    }
 
    private class Escucha extends MouseAdapter {

        public void mouseClicked(MouseEvent evento) {

            if (evento.getSource() == tableProveedores) {
                text[0].setText(Integer.toString((int) pro.dtm.getValueAt(tableProveedores.getSelectedRow(), 0)));
                text[1].setText((String) pro.dtm.getValueAt(tableProveedores.getSelectedRow(), 1));

                pro.setId(Integer.parseInt(text[0].getText()));
                ResultSet r = pro.buscar("select* from proveedores p join persona pe where p.id_person_fk=pe.id_person and id_proveedor=" + pro.getId());
                try {
                    if (r.next()) {

                        text[2].setText((String) r.getObject(5));
                        text[3].setText((String) r.getObject(6));
                        text[4].setText((String) r.getObject(7));
                        text[5].setText((String) r.getObject(8));
                        text[6].setText((String) r.getObject(9));
                        text[7].setText((String) r.getObject(10));
                        text[9].setText((String) r.getObject(12));

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (evento.getSource() == tableDatos) {

                text[2].setText((String) per.dtm.getValueAt(tableDatos.getSelectedRow(), 0));
                text[3].setText((String) per.dtm.getValueAt(tableDatos.getSelectedRow(), 1));
                text[4].setText((String) per.dtm.getValueAt(tableDatos.getSelectedRow(), 2));
                text[5].setText((String) per.dtm.getValueAt(tableDatos.getSelectedRow(), 3));
                text[6].setText((String) per.dtm.getValueAt(tableDatos.getSelectedRow(), 4));
                text[7].setText((String) per.dtm.getValueAt(tableDatos.getSelectedRow(), 5));
                text[9].setText((String) per.dtm.getValueAt(tableDatos.getSelectedRow(), 6));
                
                per.setNombre(text[2].getText());
                per.setApellido_P(text[3].getText());
                per.setApellido_M(text[4].getText());

                ResultSet r = per.buscar("select* from proveedores p join persona pe where p.id_person_fk=pe.id_person and nombre='"+per.getNombre()+"' and apellido_1='"+per.getApellido_P()+"' and apellido_2='"+per.getApellido_M()+"'");
                try {
                   
                    if (r.next()) {
                        text[0].setText(Integer.toString((int) r.getObject(1)));
                        text[1].setText((String) r.getObject(2));
                        

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

   
}
