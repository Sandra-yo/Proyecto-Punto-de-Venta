/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion_Base_datos;

import static Conexion_Base_datos.Conexion.conectarBD;
import static Conexion_Base_datos.Conexion.conexion;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author sandra
 */
public class ReporteBD {
    int Usuario,cliente;
    String fecha,producto;
    public DefaultTableModel dtm;
    public ReporteBD(){
       dtm= new DefaultTableModel(); 
    }

    public int getUsuario() {
        return Usuario;
    }

    public void setUsuario(int Usuario) {
        this.Usuario = Usuario;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }
    
    public void agregarColumnas(){
        
        dtm.addColumn("Id Usuario");  
        dtm.addColumn("Fecha de compra"); 
        dtm.addColumn("Total");
        dtm.addColumn("Cliente"); 

    }
    public void valores(){
        
    }
    public DefaultTableModel LlenarTabla(String sql) {
        conectarBD();
        try {
            if (!conexion.isClosed()) {
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery(sql);

                while (rs.next()) {
                    Object fila[] = new Object[4];
                    for (int i = 0; i < 3; i++) {
                        fila[0] = rs.getObject(1);
                        fila[1]=rs.getObject(2);
                        fila[2]=rs.getObject(3);
                        fila[3]=rs.getObject(4);

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
    
}
