/*
 * To change this license header, choose License Headers in Project Properties.
 * T o change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Conexion_Base_datos.PersonasBD;
import Conexion_Base_datos.UsuariosCB;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author sandra
 */
public class Principal extends JFrame {

    JMenuBar menu;
    JMenu catalogos, transaccion, extras;
    JMenuItem usuario, clientes, proveedores, productos, transaccionI, acercaDe;
    public JLabel fecha, fecha_act, usu1, usu;
    public JPanel pan1, pan2,pan3,pan4,pan5,pan6;
    public JButton buscar,guardar,modificar,borrar;
    Ventas p_Venta;
    Clientes p_Clientes;
    Proveedores p_Proveedores;
    Usuarios p_usu;
    Productos p_prod;
    PersonasBD personas;
    String columnas[]={"columna 1","columna 2","columna3"};
    String filas[][]={{"","",""}};
    public JTable tabla;
    public DefaultTableModel dtm;
    String tipo;
    JTabbedPane pestañas;
    int noUsuario;
    
    public Principal() {
        super("Ferreteria");
        
        menu = new JMenuBar();
    
        //menus
        catalogos = new JMenu("Catalogos");
        transaccion = new JMenu("Transacion");
        extras = new JMenu("Extras");
        
        //menu items
        acercaDe = new JMenuItem("Acerca de");
        usuario = new JMenuItem("Usuario");
        clientes = new JMenuItem("Clientes");
        proveedores = new JMenuItem("Proveedores");
        productos = new JMenuItem("Productos");
        transaccionI = new JMenuItem("Transacciones");
        
        //fecha
        Calendar fechaAct =  Calendar.getInstance();
        
        //label
        fecha = new JLabel("Fecha");
        fecha.setBounds(30,0,50,50);
        fecha_act=new JLabel();
        fecha_act.setBounds(80,0,70,50);
        fecha_act.setText(fechaAct.get(Calendar.DAY_OF_MONTH)+"/"+(fechaAct.get(Calendar.MONTH)+1)+"/"+ fechaAct.get(Calendar.YEAR));
        usu= new JLabel("Usuario:");
        usu.setBounds(30,20,70,50);
        usu1= new JLabel();
        usu1.setBounds(100,20,70,50);
              
        //clases
        p_Venta= new Ventas();
        p_Clientes= new Clientes();
        p_Proveedores= new Proveedores();
        p_usu= new Usuarios();
        p_prod=new Productos(); 
        
        //paneles
        pan1 = new JPanel();
        pan1.setLayout(null);
        pan2 = new JPanel();
        pan2.setLayout(null);
        pan3 = new JPanel();
        pan3.setLayout(null);
        pan4 = new JPanel();
        pan4.setLayout(new CardLayout());
        pan6= new JPanel();
        pan6.setLayout(new CardLayout());
        pan5 = new JPanel();
        pan5.setLayout(new BorderLayout());
    
        //botones
        guardar= new JButton("Guardar");
        guardar.setBounds(20,20,100,30);
        borrar= new JButton("Borrar");
        borrar.setBounds(20,60,100,30);
        
        buscar= new JButton("Buscar");
        buscar.setBounds(20,100,100,30);
        
        modificar= new JButton("Modificar");
        modificar.setBounds(20,140,100,30);
        
        JButton limpiar= new JButton("Limpiar");
        limpiar.setBounds(20,180,100,30);
        
        //accion de botones
       
        Manejador m= new Manejador();
        usuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pestañas.setLocation(5,5);
                pan4.removeAll();
                pan4.add(pestañas);
                pan4.revalidate();
                pan4.repaint();
                
                p_usu.fondo.setLocation(5,5);
                pan6.removeAll();
                pan6.add(p_usu.fondo);
                pan6.revalidate();
                pan6.repaint();
             
             tipo="usuario";
            }
        });
        transaccionI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             p_Venta.pestañas.setLocation(5,5);
                pan4.removeAll();
                pan4.add(p_Venta.pestañas);
                pan4.revalidate();
                pan4.repaint();
              
              p_Venta.fondo.setLocation(5,5);  
                pan6.removeAll();
                pan6.add(p_Venta.fondo);
                pan6.revalidate();
                pan6.repaint();
             tipo="ventas";
            }
        });
        clientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p_Clientes.pestañas.setLocation(5,5);
                pan4.removeAll();
                pan4.add(p_Clientes.pestañas);
                pan4.revalidate();
                pan4.repaint();
                
                p_Clientes.fondo.setLocation(5,5);
                pan6.removeAll();
                pan6.add(p_Clientes.fondo);
                pan6.revalidate();
                pan6.repaint();
                tipo="clientes";
            }
        });
        proveedores.addActionListener(new ActionListener() {//<----------------------------------------------------------------
            @Override
            public void actionPerformed(ActionEvent e) {
                tipo="Proveedores";
                p_Proveedores.pestañas.setLocation(5,5);
                pan4.removeAll();
                pan4.add(p_Proveedores.pestañas);
                pan4.revalidate();
                pan4.repaint();
                
                p_Proveedores.fondo.setLocation(5,5);
                pan6.removeAll();
                pan6.add(p_Proveedores.fondo);
                pan6.revalidate();
                pan6.repaint();
            }
        });
        productos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tipo="Productos";
                p_prod.pestañas.setLocation(5,5);
                pan4.removeAll();
                pan4.add(p_prod.pestañas);
                pan4.revalidate();
                pan4.repaint();
                
                p_prod.fondo.setLocation(5,5);
                pan6.removeAll();
                pan6.add(p_prod.fondo);
                pan6.revalidate();
                pan6.repaint();
                
            }
        });
        
        guardar.addActionListener(m);
        borrar.addActionListener(m);
        modificar.addActionListener(m);
        buscar.addActionListener(m);
        limpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               p_usu.limpiar();
               p_Clientes.limpiar();
               p_usu.limpiar();
               p_Venta.limpiar();
            }
        });
        
        catalogos.add(usuario);
        catalogos.add(clientes);
        catalogos.add(proveedores);
        catalogos.add(productos);
        catalogos.add(transaccionI);

        transaccion.add(transaccionI);
        extras.add(acercaDe);
        menu.add(catalogos);
        menu.add(transaccion);
        menu.add(extras);

        JButton btn= new JButton("sdfgbhnj");
        pan2.setBounds(612,0,185,95);
        pan3.setBounds(612,105,185,240);
        pan4.setBounds(20,370,600,191);
        pan6.setBounds(0,0,620,370);
        pan2.add(fecha);
        pan2.add(fecha_act);
        pan2.add(usu);  
        pan2.add(usu1);
        
        pan3.add(guardar);
        pan3.add(borrar);
        pan3.add(buscar);
        pan3.add(modificar);
        pan3.add(limpiar);
        
        
        
        //pestañas
        pestañas= new JTabbedPane();
        pestañas.addTab("Usuario",null,p_usu.fondoTbla);
        pestañas.addTab("Datos Personales ",null, 
                this.p_usu.tabla2);
        
        
        pan5.add(pestañas);
        
        
        pan1.add(pan6);
        pan1.add(pan2);
        pan1.add(pan3);
        pan1.add(pan4);
        
        
        add(pan1,BorderLayout.CENTER);
        
        setJMenuBar(menu);
        
    }
    public void visibilidad(){
        
            pan5.setVisible(false);
            p_Venta.pan5.setVisible(false);
            p_prod.pan5.setVisible(false);
            p_Proveedores.pan5.setVisible(false);
            p_Clientes.pan5.setVisible(false);
            
            p_Venta.fondo.setVisible(false);
            p_prod.fondo.setVisible(false);
            p_Proveedores.fondo.setVisible(false);
            p_usu.fondo.setVisible(false);
            p_Clientes.fondo.setVisible(false);
            
        }
    public void validaUsuario(int usuario,String tipo){
        usu1.setText(Integer.toString(usuario));
        this.noUsuario=usuario;
         if("usuario".equals(tipo)){
       this.usuario.setEnabled(false);
    }
           
       
       }
   
    private class Manejador implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
         
             if(e.getSource()==guardar){
                if(tipo=="usuario"){
                  p_usu.agregarDatosUsuario();
             try {
                 p_usu.agregarUsuario();
             } catch (SQLException ex) {
                 Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
             }
                  p_usu.actualizarTabla();
                }else if(tipo=="clientes"){
                    try {
                        
                        p_Clientes.agregarDatosUsuario();
                        p_Clientes.agregarUsuario();
                        p_Clientes.actualizarTabla();
                    } catch (SQLException ex) {
                        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                else if(tipo=="Proveedores"){
                    
                    try {
                        
                        p_Proveedores.agregarDatosUsuario();
                        p_Proveedores.agregarProveedor();
                        p_Proveedores.actualizarTabla();
                    } catch (SQLException ex) {
                        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else if(tipo=="Productos"){
                    try {
                        p_prod.agregarProductos();
                        p_prod.actualizarTabla();
                    } catch (SQLException ex) {
                        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
             }else if(e.getSource()==borrar ){
                 if(tipo=="usuario"){
                 p_usu.borrarUsuario();
                 p_usu.actualizarTabla();
                 }
                else if(tipo=="clientes"){
                     System.out.println("ññ");
                 p_Clientes.borrarCliente();
                 p_Clientes.actualizarTabla();
                 }else if(tipo=="Proveedores"){
                     
                 p_Proveedores.borrarProveedor();
                 p_Proveedores.actualizarTabla();
                 }else if(tipo=="Productos"){
                     p_prod.borrarProducto();
                     p_prod.actualizarTabla();
                 }
             }else if(e.getSource()==modificar  ){
                 if(tipo=="usuario"){
                 p_usu.modificarUsuario();
                 p_usu.actualizarTabla();}
                 else if(tipo=="clientes"){
                     p_Clientes.modificarCliente();
                     p_Clientes.actualizarTabla();
                 }else if(tipo=="Proveedores"){
                     p_Proveedores.modificarProveedor();
                     p_Proveedores.actualizarTabla();
                 }else if(tipo=="Productos"){
                     p_prod.modificarProducto();
                     p_prod.actualizarTabla();
                 }
             }else if(e.getSource()==buscar){
                 if(tipo=="usuario"){
                      p_usu.buscarUsuario();
                 }else if(tipo=="ventas"){
                     p_Venta.buscar();
                 }else if(tipo=="clientes"){
                     p_Clientes.buscarUsuario();
                 }else if(tipo=="Proveedores"){
                     p_Proveedores.buscarProveedor();
                 }else if(tipo=="Productos"){
                     p_prod.buscar();
                 }
                 
                
             }
         
        }
        
    }

}

 