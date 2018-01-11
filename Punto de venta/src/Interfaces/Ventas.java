/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Conexion_Base_datos.Carrito;
import Conexion_Base_datos.ClientesBD;
import Conexion_Base_datos.ProductosBD;
import Conexion_Base_datos.TransaccionBD;
import Conexion_Base_datos.TransxPerson;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author sandra
 */
public class Ventas extends Thread {
   public JPanel fondo,pan1,pan2,pan5;
   private ClientesBD c;
   private ProductosBD p;
   private Carrito crr;
   JTabbedPane pestañas;
   private JLabel[] etiq;
   private String nom[]={"Ventas","ID","Razon social","RFC","Tipo",
                        "Productos","ID","Nombre","Cantidad","Existencia","Precio"};
   private JTextField tex[];
   private JButton comprar, carrito;
   public JTable tableClientes,tableProductos,tableCarrito;
   private JLabel prec;
   private JTextField precio;
           float iva;
  TransxPerson tp;
   public Ventas(){
       comprar= new JButton("comprar");
       carrito= new JButton("carrito");
       pan1= new JPanel();
       fondo= new JPanel(null);
       pan2=new JPanel();
       pan5= new JPanel();
       pestañas=new JTabbedPane();
               
       //clases
       c= new ClientesBD();
       p= new ProductosBD();
       crr= new Carrito();
       
       //visibilidad
       pestañas.setVisible(false);


       //tablas
       c.agregarColumnas();
       p.agregarColumnas();
       crr.agregarColumnas();
       armarTablas();

       
       pan1.setLayout(new FlowLayout());
       pan5.setLayout(new FlowLayout());
       pan1.setBounds(0,50,590,70);
       etiq=new JLabel[12];
       prec= new JLabel("Precio");
       tex= new JTextField[12];
       precio= new JTextField(11);
       pan2.setLayout(new FlowLayout());
       for (int i = 1; i < 11; i++) {
           etiq[i]=new JLabel(nom[i]);
           tex[i]= new JTextField(11);
       }
        etiq[0]=new JLabel(nom[0]);
        etiq[0].setBounds(350,0,50,30);
       //etiq[1].setBounds(30,0,50,50);
       //etiq[2].setBounds(30,40,50,30);
       //etiq[3].setBounds(90,40,100,30);
       
       for (int i = 1; i < 5; i++) {
          pan1.add(etiq[i]);
          pan1.add(tex[i+1]);
       }
       for (int i = 6; i < 10; i++) {
          pan2.add(etiq[i]);
          pan2.add(tex[i+1]);
          
       }
       pan2.add(prec);
       pan2.add(precio);
       
       pan2.add(comprar);
       pan2.add(carrito);
       pan2.setBounds( 0, 130, 590, 110);
       fondo.add(etiq[0]);
       fondo.add(pan1);
       fondo.add(pan2);
       pan5.add(pestañas);
   }
   
   //Metodos
   public void buscar(){
       p.setNombre(tex[9].getText());
      
       tableProductos= new JTable(p.buscarEnTabla());
   }
   public void armarTablas(){
       tableClientes= new JTable(c.LlenarTabla());
       tableProductos= new JTable(p.LlenarTabla());
       crr.setIdTransaccion(0);
       tableCarrito= new JTable(crr.LlenarTabla());
       
       //scroll
       JScrollPane uno= new JScrollPane(tableClientes);
       JScrollPane dos= new JScrollPane(tableProductos);
       JScrollPane tres= new JScrollPane(tableCarrito);
       
       //pestañas
       pestañas.addTab("Clientes", uno);   
       pestañas.addTab("Productos", dos); 
       pestañas.addTab("Carrito", tres);
       
       //botones
       comprar.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
              String pago=null;
              
               int n=JOptionPane.showConfirmDialog(null, "¿Gusta pagar con tarjeta?");
               if(n==1){
                   pago="Efectivo";
                   int m=Integer.parseInt(JOptionPane.showInputDialog(null,"Cantidad recibida:"));
                   int cambio=m-calculaTotal();
                           JOptionPane.showMessageDialog(null,"Gracias por su compra :) \n su cambio es $ "+cambio+".00");
                            TransaccionBD tr= new TransaccionBD();
               
                    tr.setFecha((new Principal()).fecha_act.getText());
                    tr.setTipo("Venta");
                    tr.setPrecioSIva(calculaTotal());
                    tr.setIva((float) ((double)calculaTotal()*0.16));
                    tr.setPrecioCIva(calculaTotal()+(int) ((double)calculaTotal()*0.16));
                    tr.setFormaPago(pago);
                    
                    tr.agregar();
                    
                   tp= new TransxPerson();
                    habilitaTextos(true);
                               tp.setId_person(Integer.parseInt(tex[2].getText()));
                               
                  try {
                      ResultSet t=tr.buscarR();
                      if(t.next()){
                              tp.setId_transaccion ((int) (crr.getIdTransaccion()));
                      }
                  } catch (SQLException ex) {
                      Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
                  }
                                                          tp.setUsuario(new Principal().noUsuario);
                                         System.out.println((new Principal()).usu1.getText());
                    
                    tp.agregar();
                      
               }else if(n==0){
                   pago="Tarjeta";
                   JOptionPane.showMessageDialog(null,"Gracias por su compra :) ");
                    TransaccionBD tr= new TransaccionBD();
                    tr.setFecha((new Principal()).fecha_act.getText());
                    tr.setTipo("Venta");
                    tr.setPrecioSIva(calculaTotal());
                    tr.setIva((float) ((double)calculaTotal()*0.16));
                    tr.setPrecioCIva(calculaTotal()+(int) ((double)calculaTotal()*0.16));
                    tr.setFormaPago(pago);
                      tr.agregar();
               }else if(n==2){
                   //aqui codigo de borrar lista
                   
                   crr.borrar();
               }
              //aqui se genera el codigo de transaccion
             
                      
                              }
       });
       carrito.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               habilitaTextos(false);
               
               if(tex[9].getText().equals("")){
                        JOptionPane.showMessageDialog(null,"Por favor, ingrese la cantidad solicitada del producto que desea ");
               }else{
              crr.setIdProducto(Integer.parseInt(tex[7].getText()));
              crr.setCantidad(Integer.parseInt(tex[9].getText()));
              crr.setUnitario(Integer.parseInt(precio.getText()));
              crr.setTotal(calculaTotalP());
               }
              int num=0;
              ResultSet r=(new TransaccionBD()).buscarR();
               try {
                   while(r.next())
                   num=((int) (r.getObject(1)));
                   
               } catch (SQLException ex) {
                   Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
               }
               crr.setIdTransaccion(num+1);
               crr.agregar();
               crr.borrarTabla();
               crr.LlenarTabla();
               
           }  
       });
       //tabla
       tableClientes.addMouseListener(new Escucha());       
       tableProductos.addMouseListener(new Escucha());

   }
    public int calculaTotal(){
        int total=0;
        for (int i = 0; i < tableCarrito.getRowCount(); i++) {
          total+=(int)crr.dtm.getValueAt(i,4);
        }
        
        return total;
    }
    public int calculaTotalP(){
        int tP=0;
        tP=Integer.parseInt(tex[9].getText())*Integer.parseInt(precio.getText());
        return tP;
        
    }
    
    public void actualizarTabla() {
        crr.borrarTabla();
        armarTablas();
    }
    public void habilitaTextos(boolean hab){
         for (int i = 1; i < 7; i++) {
            tex[i].setEditable(hab);
        }
    }
    public void limpiar(){
        habilitaTextos(true);
        for (int i = 1; i < 7; i++) {
            tex[i].setText("");
        }
    }
    
    
    
    
   private class Escucha extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent evento) {
            
                
            if (evento.getSource() == tableClientes) {
                if(tableClientes.getSelectedRow()!=-1){
                tex[2].setText(Integer.toString((int) c.dtm.getValueAt(tableClientes.getSelectedRow(), 0)));
                tex[3].setText((String) c.dtm.getValueAt(tableClientes.getSelectedRow(), 1));
                tex[4].setText((String) c.dtm.getValueAt(tableClientes.getSelectedRow(), 2));
                tex[5].setText((String) c.dtm.getValueAt(tableClientes.getSelectedRow(), 3));
//                tex[6].setText((String) c.dtm.getValueAt(tableClientes.getSelectedRow(), 4));
                }
                   
                
            } else if (evento.getSource() == tableProductos) {
            if(tableProductos.getSelectedRow()!=-1){
                tex[7].setText(Integer.toString((int) p.dtm.getValueAt(tableProductos.getSelectedRow(), 0)));
                tex[8].setText((String) p.dtm.getValueAt(tableProductos.getSelectedRow(), 1));
                tex[10].setText(Integer.toString((int) p.dtm.getValueAt(tableProductos.getSelectedRow(), 3)));
                precio.setText(Integer.toString((int) p.dtm.getValueAt(tableProductos.getSelectedRow(), 5)));
                                       
               
            }
            }
        }
    }
}


