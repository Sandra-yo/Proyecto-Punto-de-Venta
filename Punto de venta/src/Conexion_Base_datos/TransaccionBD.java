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

/**
 *
 * @author sandra
 */
public class TransaccionBD {
    int id;
    String fecha,tipo,formaPago;
    int precioSIva,precioCIva;
    float iva;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public int getPrecioSIva() {
        return precioSIva;
    }

    public void setPrecioSIva(int precioSIva) {
        this.precioSIva = precioSIva;
    }

    public int getPrecioCIva() {
        return precioCIva;
    }

    public void setPrecioCIva(int precioCIva) {
        this.precioCIva = precioCIva;
    }

    public float getIva() {
        return iva;
    }

    public void setIva(float iva) {
        this.iva = iva;
    }
     public ResultSet buscarR(){
        String sql="SELECT id_transaccion FROM transaccion ORDER by id_transaccion DESC LIMIT 0,1";
        Conexion.conectarBD();
          ResultSet resultado = Conexion.ejecutarSQLSelect(sql);
        return resultado;
    }
    
    public void agregar() {
        conectarBD();
        String sql ="insert into transaccion(fecha,tipo,precio_sin_iva,iva,precio_con_iva,forma_pago) values(?,?,?,?,?,?);";
        try {
            if (!conexion.isClosed()) {
                PreparedStatement ps = Conexion.conexion.prepareStatement(sql);
                
                ps.setString(1, fecha);
                ps.setString(2, tipo);
                ps.setInt(3, precioSIva);
                ps.setFloat(4, iva);
                ps.setInt(5, precioCIva);
                ps.setString(6, formaPago);
               
                ps.executeUpdate();

                ps.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}
