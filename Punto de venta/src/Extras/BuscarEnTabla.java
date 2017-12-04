/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Extras;

import static Conexion_Base_datos.Conexion.conectarBD;
import static Conexion_Base_datos.Conexion.conexion;
import Conexion_Base_datos.PersonasBD;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author sandra
 */
public class BuscarEnTabla extends Thread {
    DefaultTableModel dtm;
    String sql;
    int n;
    PersonasBD per;
 
    public BuscarEnTabla(DefaultTableModel dtm,String sql,int n){
       this.n=n;
       this.dtm=dtm;
       this.sql=sql;
       per= new PersonasBD();
       
    }
    
    public void run(){
        try{
         conectarBD();
       
            if (!conexion.isClosed()) {
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery(sql);

                 while (rs.next()) {
                    Object fila[] = new Object[n];
                    for (int i = 0; i < n; i++) {
                        fila[i] = rs.getObject(i + 1);

                    }
                    dtm.addRow(fila);
                }
                conexion.close();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
