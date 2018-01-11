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
public class TransxPerson {
   private int id_person;
   private int id_transaccion;
   private int id;
   private int usuario;

    public int getId_person() {
        return id_person;
    }

    public void setId_person(int id_person) {
        this.id_person = id_person;
    }

    public int getId_transaccion() {
        return id_transaccion;
    }

    public void setId_transaccion(int id_transaccion) {
        this.id_transaccion = id_transaccion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }
    public ResultSet buscarR(){
        String sql="SELECT id_transaccion FROM transaccion ORDER by id_transaccion DESC LIMIT 0,1";
        Conexion.conectarBD();
          ResultSet resultado = Conexion.ejecutarSQLSelect(sql);
        return resultado;
    }
    
    public void agregar() {
        conectarBD();
        String sql ="insert into TransxPerson(id_person_fk,id_transaccion_fk,usuario)values(?,?,?);";
        
            System.out.println("mmmm");
        try {
            if (!conexion.isClosed()) {
                PreparedStatement ps = Conexion.conexion.prepareStatement(sql);
                
                
                ps.setInt(1, this.id_person);
                ps.setInt(2, this.id_transaccion);
                ps.setInt(3, this.usuario);
               
                ps.executeUpdate();

                ps.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}
