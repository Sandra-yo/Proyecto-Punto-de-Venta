/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion_Base_datos;

import static Conexion_Base_datos.Conexion.conectarBD;
import static Conexion_Base_datos.Conexion.conexion;
import Interfaces.Principal;
import Interfaces.Usuarios;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author sandra
 */
public class UsuariosCB {

    private int id_usuario;
    private String tipo;
    private String contraseña;
    private int comision;
    private int id_person;
    public DefaultTableModel dtm;
    Usuarios us;

    public UsuariosCB() {
        dtm = new DefaultTableModel();
    }

    public UsuariosCB(int id_usuario, String tipo, int comision, int id_person) {
        this.comision = comision;
        this.id_person = id_person;
        this.id_usuario = id_usuario;
        this.tipo = tipo;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getComision() {
        return comision;
    }

    public void setComision(int comision) {
        this.comision = comision;
    }

    public int getId_person() {
        return id_person;
    }

    public void setId_person(int id_person) {
        this.id_person = id_person;
    }
    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void agregarColumnas() {
        dtm.addColumn("id");
        dtm.addColumn("tipo");
        dtm.addColumn("comision");
    }
      public ResultSet buscar(){
        String sql=
       "select * from usuario where id_usuario="+id_usuario;
        Conexion.conectarBD();
          ResultSet resultado = Conexion.ejecutarSQLSelect(sql);
        return resultado;
    } 
      public ResultSet buscar(String sql){
       
        Conexion.conectarBD();
          ResultSet resultado = Conexion.ejecutarSQLSelect(sql);
        return resultado;
    }

    public DefaultTableModel LlenarTabla() {
        conectarBD();
        try {
            if (!conexion.isClosed()) {
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery("select*From Usuario ");

                while (rs.next()) {
                    Object fila[] = new Object[3];
                    for (int i = 0; i < 3; i++) {
                        fila[i] = rs.getObject(i + 1);

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

    public void agregar() {
        conectarBD();
        String sql ="insert into Usuario (tipo,comision,id_person_fk,contraseña,estado)values (?,?,?,?,?) ";
        try {
            if (!conexion.isClosed()) {
                PreparedStatement ps = Conexion.conexion.prepareStatement(sql);
                
                ps.setString(1, tipo);
                ps.setInt(2, comision);
                ps.setInt(3, id_person);
                ps.setString(4, contraseña);
                ps.setString(5, "activo");
               
                ps.executeUpdate();

                ps.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public boolean borrar(){
        
        String sql="delete from Usuario where id_usuario="+id_usuario;
        conectarBD();
        
        if(Conexion.ejecutarSQLUpdate(sql)){
                return true;
                
            }
            else{
                return false;
            }
    }
    public boolean modificar(){
        String sql="update Usuario set tipo=?,comision=?,contraseña=? where id_usuario="+id_usuario;
        conectarBD();
        try{
            PreparedStatement ps=Conexion.conexion.prepareStatement(sql);
            ps.setString(1, tipo);
            ps.setInt(2,comision);
            ps.setString(3, contraseña);
            ps.executeUpdate();
            ps.close();
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public ResultSet buscarR(){
        String sql="select*from Usuario where id_usuario="+id_usuario;
        Conexion.conectarBD();
          ResultSet resultado = Conexion.ejecutarSQLSelect(sql);
        return resultado;
    }
    public DefaultTableModel buscarEnTabla(){
        conectarBD();
        try {
            borrarTabla();
            if (!conexion.isClosed()) {
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery("select*From Usuario where id_usuario="+id_usuario);

                while (rs.next()) {
                    Object fila[] = new Object[4];
                    for (int i = 0; i < 4; i++) {
                        fila[i] = rs.getObject(i + 1);

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
        do {
            dtm.removeRow(dtm.getRowCount() - 1);
        } while (dtm.getRowCount() != 0);

    }

}
