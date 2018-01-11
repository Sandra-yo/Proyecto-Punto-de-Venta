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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author sandra
 */
public class ProductosBD {
   private int id;
   private String nombre;
   private String descripcion;
   private int existencia;
   private int costo;
   private int precio;
   private int invMax;
   private int invMin;
   public DefaultTableModel dtm;
   
   public ProductosBD(){
     dtm= new DefaultTableModel();  
   }
   public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getInvMax() {
        return invMax;
    }

    public void setInvMax(int invMax) {
        this.invMax = invMax;
    }

    public int getInvMin() {
        return invMin;
    }

    public void setInvMin(int invMin) {
        this.invMin = invMin;
    }
    //metodos
    public void agregarColumnas() {
        dtm.addColumn("id");
        dtm.addColumn("Nombre");
        dtm.addColumn("Descripcion");
        dtm.addColumn("Existencia");
        dtm.addColumn("Costo");
        dtm.addColumn("Precio");
        dtm.addColumn("Inventario Max");
        dtm.addColumn("Inventario Min");


    }
     public void agregar() {
        conectarBD();
        String sql ="insert into Producto(nombre,descripcion,existencia,costo,precio,inventario_max,inventario_min)values(?,?,?,?,?,?,?);";
        try {
            if (!conexion.isClosed()) {
                PreparedStatement ps = Conexion.conexion.prepareStatement(sql);
                
                ps.setString(1, this.nombre);
                ps.setString(2, this.descripcion);
                ps.setInt(3, this.existencia);
                ps.setInt(4, this.costo);
                ps.setInt(5, this.precio);
                ps.setInt(6, this.invMax);
                ps.setInt(7, this.invMin);
               
                ps.executeUpdate();

                ps.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
      public boolean borrar(){
        String sql="delete from Producto where id_person="+this.id;
        conectarBD();
        
        if(Conexion.ejecutarSQLUpdate(sql)){
                return true;
                
            }return false;
      }
    public DefaultTableModel LlenarTabla() {
        conectarBD();
        try {
            if (!conexion.isClosed()) {
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery("select*From Producto");

                while (rs.next()) {
                    Object fila[] = new Object[8];
                    fila[0]=rs.getObject(1);   
                    fila[1]=rs.getObject(2);
                    fila[2]=rs.getObject(3);
                    fila[3]=rs.getObject(4);
                    fila[4]=rs.getObject(5);
                    fila[5]=rs.getObject(6);
                    fila[6]=rs.getObject(8);
                    fila[7]=rs.getObject(9);


                    
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
        do {
            dtm.removeRow(dtm.getRowCount() - 1);
        } while (dtm.getRowCount() != 0);

    }
    public DefaultTableModel buscarEnTabla(){
        conectarBD();
        try {
            borrarTabla();
            if (!conexion.isClosed()) {
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery("select*from Producto where nombre like '%"+nombre+"%'");
                System.out.println(nombre);

                while (rs.next()) {
                    Object fila[] = new Object[8];
                    for (int i = 0; i < 8; i++) {
                        fila[i] = rs.getObject(i + 1);
                        
                    }
                    fila[6]=rs.getObject(8);
                    
                    fila[7]=rs.getObject(9);

                    
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
    
    public ResultSet buscarR(){
        String sql="select*from Producto where id_person="+id;
        Conexion.conectarBD();
          ResultSet resultado = Conexion.ejecutarSQLSelect(sql);
        return resultado;
    } 
     public boolean modificar(){
        String sql="update Producto set nombre=?,descripcion=?,existencia=?,costo=?,precio=?,inventario_max=?,inventario_min=? where id_person="+this.id;
        conectarBD();
        try{
            PreparedStatement ps=Conexion.conexion.prepareStatement(sql);
            ps.setString(1, this.nombre);
            ps.setString(2,this.descripcion);
            ps.setInt(3, this.existencia);
            ps.setInt(4, this.costo);
            ps.setInt(5, this.precio);
            ps.setInt(6, this.invMax);
            ps.setInt(7, this.invMin);

            ps.executeUpdate();
            ps.close();
            return true;
        }catch(Exception e){
            return false;
        }
    }
      public boolean modificacionPorVenta(){
        String sql=
                "update Producto set existencia=? where id_person="+this.id;
        conectarBD();
        try{
            PreparedStatement ps=Conexion.conexion.prepareStatement(sql);
           
            ps.setInt(1, this.existencia);
            

            ps.executeUpdate();
            ps.close();
            return true;
        }catch(Exception e){
            return false;
        }
    }
     
    
        
}
