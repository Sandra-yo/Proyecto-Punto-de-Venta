/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Conexion_Base_datos.ClientesBD;
import Conexion_Base_datos.PersonasBD;
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
public class Clientes {

    JPanel fondo, pan1,pan5;
    JTabbedPane pestañas;
    JTable tableDatos, tableClientes;
    ClientesBD c;
    PersonasBD p;
    JLabel etiq[], titulo;
    JTextField text[];
    String nombres[] = {"Nombre", "Apellido Primero", "Apellido Segundo", "Direccion", "Colonia", "correo", "Telefonos", "Fecha Nacimiento"};
    String d_cliente[] = {"Id", "Razon Social", "RFC", "Tipo"};

    public Clientes() {

        pan1 = new JPanel();
        fondo = new JPanel();
        pan5 = new JPanel();
        pan5.setLayout(new FlowLayout());
        fondo.setLayout(null);
        pan1.setLayout(new GridLayout(12, 8));

        JButton btn = new JButton();
        int e = 0;
        int valor = nombres.length + d_cliente.length;

        text = new JTextField[valor];
        etiq = new JLabel[valor];
        titulo = new JLabel("Clientes");

        for (int i = 0; i < valor; i++) {
            text[i] = new JTextField();
            etiq[i] = new JLabel();
            pan1.add(etiq[i]);
            pan1.add(text[i]);
        }
        for (int i = 0; i < d_cliente.length; i++) {
            etiq[i].setText(d_cliente[i]);
        }
        for (int i = d_cliente.length; i < valor; i++) {
            etiq[i].setText(nombres[e]);
            e++;
        }
        pan1.setBounds(20, 20, 550, 300);
        titulo.setBounds(20, 0, 70, 20);
        fondo.add(titulo);
        fondo.add(pan1);

        //clases
        c = new ClientesBD();
        p = new PersonasBD();

        //tabla
        tableClientes = new JTable(c.dtm);
        tableDatos = new JTable(p.dtm);
        c.agregarColumnas();
        p.agregarColumnas();
        armarTablas();

        
        //scroll
        JScrollPane uno = new JScrollPane(tableClientes);
        JScrollPane dos = new JScrollPane(tableDatos);

        //pestañas
        pestañas = new JTabbedPane();
        pestañas.setVisible(false);

        pestañas.addTab("Cliente", uno);
        pestañas.addTab("datos Personales", dos);
        pan5.add(pestañas);

        
        //escuchadores
        tableClientes.addMouseListener(new Escucha());
        tableDatos.addMouseListener(new Escucha());

    }

    public void armarTablas() {
        tableDatos = new JTable(p.LlenarTabla("select*from persona where tipo='Cliente'"));
        
        tableClientes = new JTable(c.LlenarTabla());
        

    }

    public void agregarUsuario() throws SQLException {
         p.setNombre(text[4].getText());
         p.setApellido_P(text[5].getText());
         p.setApellido_M(text[6].getText());
        ResultSet r= p.buscar();
       
       if(r.next()){
        c.setId_persona((int) r.getObject(1));}
        c.setR_Social(text[1].getText());
        c.setRFC(text[2].getText());
        c.setTipo(text[3].getText());
        
        c.agregar();
    }

    public void agregarDatosUsuario() {
        p.setNombre(text[4].getText());
        p.setApellido_P(text[5].getText());
        p.setApellido_M(text[6].getText());
        p.setDireccion(text[7].getText());
        p.setColonia(text[8].getText());
        p.setCorreo(text[9].getText());
        p.setFecha_n(text[11].getText());
        p.setTipo("cliente");
        p.agregar();

}
     public void buscarUsuario() {
        c.setId_Cliente(Integer.parseInt(text[0].getText()));
        
        ResultSet t = c.buscar("select* from persona p join clientes c where c.id_person_fk=p.id_person and id_cliente=" + c.getId_Cliente());
        try {
            if (t.next()) {
                text[1].setText((String) t.getObject(13));     
                text[2].setText((String) t.getObject(14));
                text[3].setText((String) t.getObject(15));
                text[4].setText((String) t.getObject(2));
                text[5].setText((String) t.getObject(3));
                text[6].setText((String) t.getObject(4));
                text[7].setText((String) t.getObject(5));
                text[8].setText((String) t.getObject(6));
                text[9].setText((String) t.getObject(7));
                text[11].setText((String)t.getObject(9));

            }
            p.setNombre(text[4].getText());
            p.setApellido_P(text[5].getText());
            p.setApellido_M(text[6].getText());
            c.setR_Social(text[1].getText());
            
            tableClientes= new JTable(p.buscarEnTabla());
            tableDatos= new JTable(p.buscarEnTabla());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void modificarCliente() {//<-----------
        c.setId_Cliente(Integer.parseInt(text[0].getText()));
        c.setTipo(text[3].getText());
        c.setRFC(text[2].getText());
        c.setR_Social(text[1].getText());
        c.modificar();
       
        p.setNombre(text[4].getText());
        p.setApellido_P(text[5].getText());
        p.setApellido_M(text[6].getText());
        p.setDireccion(text[7].getText());
        p.setColonia(text[8].getText());
        p.setCorreo(text[9].getText());
        p.setFecha_n(text[11].getText());
        p.modificar();
    }
     public void borrarCliente() {
        c.setId_Cliente(Integer.parseInt(text[0].getText()));
        c.borrar();
        
        
        p.setNombre(text[4].getText());
        p.setApellido_P(text[5].getText());
        p.setApellido_M(text[6].getText());
        
        p.borrar();
    }

    public void actualizarTabla() {
        c.borrarTabla();
        p.borrarTabla();
        armarTablas();
    }
    public void limpiar(){
        for (int i = 0; i < text.length; i++) {
            text[i].setText(" ");
        }
    }

    private class Escucha extends MouseAdapter {//<------

        public void mouseClicked(MouseEvent evento) {

            if (evento.getSource() == tableClientes) {
              
                
                //llena los textos
                text[0].setText(Integer.toString((int) c.dtm.getValueAt(tableClientes.getSelectedRow(), 0)));
                text[1].setText((String) c.dtm.getValueAt(tableClientes.getSelectedRow(), 1));
                text[2].setText((String) c.dtm.getValueAt(tableClientes.getSelectedRow(), 2));
                text[3].setText((String) c.dtm.getValueAt(tableClientes.getSelectedRow(), 3));

                c.setId_Cliente(Integer.parseInt(text[0].getText()));
                ResultSet r = c.buscar("select* from persona p join clientes c where c.id_person_fk=p.id_person and id_cliente=" + c.getId_Cliente());
                try {
                    if (r.next()) {

                        text[4].setText((String) r.getObject(2));
                        text[5].setText((String) r.getObject(3));
                        text[6].setText((String) r.getObject(4));
                        text[7].setText((String) r.getObject(5));
                        text[8].setText((String) r.getObject(6));
                        text[9].setText((String) r.getObject(7));
                        text[11].setText((String) r.getObject(9));

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (evento.getSource() == tableDatos) {

                text[4].setText((String) p.dtm.getValueAt(tableDatos.getSelectedRow(), 0));
                text[5].setText((String) p.dtm.getValueAt(tableDatos.getSelectedRow(), 1));
                text[6].setText((String) p.dtm.getValueAt(tableDatos.getSelectedRow(), 2));
                text[7].setText((String) p.dtm.getValueAt(tableDatos.getSelectedRow(), 3));
                text[8].setText((String) p.dtm.getValueAt(tableDatos.getSelectedRow(), 4));
                text[9].setText((String) p.dtm.getValueAt(tableDatos.getSelectedRow(), 5));
                text[11].setText((String) p.dtm.getValueAt(tableDatos.getSelectedRow(), 6));
                
                p.setNombre(text[4].getText());
                p.setApellido_P(text[5].getText());
                p.setApellido_M(text[6].getText());

                ResultSet r = c.buscar("select* from persona p join clientes c where c.id_person_fk=p.id_person and nombre='"+p.getNombre()+"' and apellido_1='"+p.getApellido_P()+"' and apellido_2='"+p.getApellido_M()+"'");
                try {
                   
                    if (r.next()) {
                        text[0].setText(Integer.toString((int) r.getObject(12)));
                        text[1].setText((String) r.getObject(13));
                        text[2].setText((String) r.getObject(14));
                        text[3].setText((String) r.getObject(15));

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

}
