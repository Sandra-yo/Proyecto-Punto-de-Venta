/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Conexion_Base_datos.Conexion;
import static Conexion_Base_datos.Conexion.conectarBD;
import Conexion_Base_datos.ProductosBD;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author sandra
 */
public class Productos extends JFrame{
    JPanel fondo,pan1,pan5;
    JLabel etiq[],titulo;
    JTextField text[];
    ProductosBD prod;
    JTable tableProductos;
    JTabbedPane pestañas;
    JTextArea area;
    String d_Productos[]={"Id","Nombre","Tipo","Descripcion","Existencia","Costo","PrecioPublico","Precio maximo","Inventario min","Descripcion"};
    public Productos(){
        
        pan5= new JPanel();
        pan1= new JPanel();
        fondo= new JPanel();
        fondo.setLayout(null);
        pan1.setLayout(new GridLayout(5,4));
        pan5.setLayout(new FlowLayout());
        area= new JTextArea(20,40);
        ScrollPane scroll = new ScrollPane();
        scroll.add(area);
        
        //clases
        prod= new ProductosBD();
        
        text= new JTextField[d_Productos.length];
        etiq= new JLabel[d_Productos.length];
        titulo= new JLabel("Productos");
        
        for (int i = 0; i < d_Productos.length-1; i++) {
            text[i]= new JTextField(12);
            etiq[i]= new JLabel();
            pan1.add(etiq[i]);
            pan1.add(text[i]);
        }
        for (int i = 0; i < d_Productos.length-1; i++) {
            etiq[i].setText(d_Productos[i]);
        }
        //tabla
        tableProductos = new JTable(prod.dtm);
        prod.agregarColumnas();
      //  armarTablas();

        
        //scroll
        JScrollPane uno = new JScrollPane(tableProductos);
        //pestañas
        pestañas = new JTabbedPane();
        pan5.add(pestañas);
        
        pestañas.addTab("Productos", uno);
        pestañas.setVisible(false);

        pan1.setBounds(20,20,560,280);
        titulo.setBounds(20,0,110,20);
        fondo.add(titulo);
        fondo.add(pan1);
        add(fondo);
        
        //escuchadores
        tableProductos.addMouseListener(new Escucha());
    }
     public void armarTablas() {
        tableProductos = new JTable(prod.LlenarTabla());
     }
     public void actualizarTabla() {
        prod.borrarTabla();
        armarTablas();
    }

    public void agregarProductos() throws SQLException {
         
        prod.setNombre(text[1].getText());
        prod.setDescripcion(text[2].getText());
        prod.setCosto(Integer.parseInt((String)text[3].getText()));
        prod.setExistencia(Integer.parseInt((String)text[4].getText()));
        prod.setPrecio(Integer.parseInt((String)text[5].getText()));
        prod.setInvMax(Integer.parseInt((String)text[6].getText()));
        prod.setInvMin(Integer.parseInt((String)text[7].getText()));
        prod.agregar();
        
    

}
    public void borrarProducto() {
        prod.setId(Integer.parseInt(text[0].getText()));
        prod.borrar();
       }
     public void modificarProducto() {//<-----------
        prod.setId(Integer.parseInt(text[0].getText()));
        prod.setNombre(text[1].getText());
        prod.setDescripcion(text[2].getText());
        prod.setExistencia(Integer.parseInt(text[3].getText()));
        prod.setCosto(Integer.parseInt(text[4].getText()));
        prod.setPrecio(Integer.parseInt(text[5].getText()));
        prod.setInvMax(Integer.parseInt(text[6].getText()));
        prod.setInvMin(Integer.parseInt(text[7].getText()));
        
        prod.modificar();
       
       
    }
     public void buscar(){
       prod.setNombre(text[1].getText());
      
       tableProductos= new JTable(prod.buscarEnTabla());
   }
     public void limpiar(){
        for (int i = 0; i < text.length; i++) {
            text[i].setText(" ");
        }
    }
      
     private class Escucha extends MouseAdapter {

        public void mouseClicked(MouseEvent evento) {

            
               try{
                text[0].setText(Integer.toString((int) prod.dtm.getValueAt(tableProductos.getSelectedRow(), 0)));
                text[1].setText((String) prod.dtm.getValueAt(tableProductos.getSelectedRow(), 1));
                text[2].setText((String) prod.dtm.getValueAt(tableProductos.getSelectedRow(), 2));
                text[3].setText(Integer.toString((int) prod.dtm.getValueAt(tableProductos.getSelectedRow(), 3)));
                text[4].setText(Integer.toString((int) prod.dtm.getValueAt(tableProductos.getSelectedRow(), 4)));
                text[5].setText(Integer.toString((int) prod.dtm.getValueAt(tableProductos.getSelectedRow(), 5)));
                text[6].setText(Integer.toString((int) prod.dtm.getValueAt(tableProductos.getSelectedRow(), 6)));
                text[7].setText(Integer.toString((int) prod.dtm.getValueAt(tableProductos.getSelectedRow(), 7)));                
            }catch(Exception e){
                   System.out.println(e);
            }
            }
           }
        }
    



