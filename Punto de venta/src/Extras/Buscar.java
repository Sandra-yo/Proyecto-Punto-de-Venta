/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Extras;

import Conexion_Base_datos.Conexion;
import static Conexion_Base_datos.Conexion.conectarBD;
import static Conexion_Base_datos.Conexion.conexion;
import Conexion_Base_datos.PersonasBD;
import Conexion_Base_datos.UsuariosCB;
import Interfaces.Usuarios;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author sandra
 */
public class Buscar extends Thread {

    Usuarios u;
    UsuariosCB ubd;
    PersonasBD per;
    String sql;
    boolean id;
    
    public Buscar(String s){
        this.u=new Usuarios();
        this.per= new PersonasBD();
        this.sql=s;
    }
    
    @Override
    public void run(){
        Conexion.conectarBD();
        ResultSet t= Conexion.ejecutarSQLSelect(sql);
        try {
           
            if (t.next()) {
               
            
            per.setNombre((String)t.getObject(8));
            per.setApellido_P((String)t.getObject(9));
            per.setApellido_M((String)t.getObject(10));
            per.setDireccion((String)t.getObject(11));
            per.setColonia((String)t.getObject(12));
            per.setCorreo((String)t.getObject(13));
            per.setFecha_n((String)t.getObject(15));
            
            conexion.close();
            }
            
      
    
    }   catch (SQLException ex) {
            Logger.getLogger(Buscar.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
}
}