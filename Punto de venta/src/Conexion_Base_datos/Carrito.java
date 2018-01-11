/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion_Base_datos;

import static Conexion_Base_datos.Conexion.conectarBD;
import static Conexion_Base_datos.Conexion.conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author sandra
 */
public class Carrito {
    private int idProducto;
    private int id_Trans;
    private String nombre;
    private int cantidad;
    private int unitario;
    private int total;
    public DefaultTableModel dtm;
    
    public Carrito(){
        dtm= new DefaultTableModel();
    }
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
    public int getIdTransaccion() {
        return idProducto;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.id_Trans = idTransaccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getUnitario() {
        return unitario;
    }

    public void setUnitario(int unitario) {
        this.unitario = unitario;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
 //metodos
    public void agregarColumnas() {
        dtm.addColumn("id");
        dtm.addColumn("Nombre");
        dtm.addColumn("Cantidad");
        dtm.addColumn("Precio unitario");
        dtm.addColumn("total");


    }
    public ResultSet buscarR(String sql){
        
        Conexion.conectarBD();
          ResultSet resultado = Conexion.ejecutarSQLSelect(sql);
        return resultado;
    }
    public DefaultTableModel LlenarTabla() {
        conectarBD();
        try {
            if (!conexion.isClosed()) {
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery("select*from L_producto where id_transaccion_fk="+this.id_Trans);
                
                while (rs.next()) {
                    
                    Object fila[] = new Object[5];
                    
                    fila[0]=rs.getObject(2);
                    
                    fila[2]=rs.getObject(3);
                    fila[3]=rs.getObject(4);
                    fila[4]=rs.getObject(5);
                    ProductosBD n= new ProductosBD();
                    n.setId((int) rs.getObject(2));
                    ResultSet r= n.buscarR();
                    while(r.next()){
                        fila[1]=r.getObject(2);
                    }
                    
                    dtm.addRow(fila);
                }
                conexion.close();
            }
            return dtm;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return dtm;
    }
    public void borrarTabla() {
       if(dtm.getRowCount()!=0){ do {
            dtm.removeRow(dtm.getRowCount() - 1);
        } while (dtm.getRowCount() != 0);
       }
    }
     public void agregar() {
        conectarBD();
        String sql ="insert into L_producto(id_producto_fk,cantidad,unitario,total,id_transaccion_fk) values(?,?,?,?,?)";
        try {
            if (!conexion.isClosed()) {
                PreparedStatement ps = Conexion.conexion.prepareStatement(sql);
                
                ps.setInt(1, this.idProducto);
                ps.setInt(2, this.cantidad);
                ps.setInt(3, this.unitario);
                ps.setInt(4, this.total);
                ps.setInt(5, this.id_Trans);
               
                ps.executeUpdate();

                ps.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
     
      public boolean borrar(){
        
        String sql="delete from L_producto where id_transaccion_fk="+this.id_Trans;
        conectarBD();
        
        if(Conexion.ejecutarSQLUpdate(sql)){
                return true;
                
            }
            else{
                return false;
            }
      }
    
       
}
