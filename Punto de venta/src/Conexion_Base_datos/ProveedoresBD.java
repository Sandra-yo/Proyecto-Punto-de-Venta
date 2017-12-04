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
public class ProveedoresBD {
    private int id;
    private String visita;
    private int idPerson;
    public DefaultTableModel dtm;
    
    public ProveedoresBD(){
        dtm= new DefaultTableModel();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getIdPerson() {
        return id;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }
     public void borrarTabla() {
         if(dtm.getRowCount()!=0){
        do {
            dtm.removeRow(dtm.getRowCount() - 1);
        } while (dtm.getRowCount() != 0);
         }
    }

    public String getVisita() {
        return visita;
    }

    public void setVisita(String visita) {
        this.visita = visita;
    }
    
    public void agregarColumnas() {
        dtm.addColumn("ID");
        dtm.addColumn("Ultima visita");
    }
    public DefaultTableModel LlenarTabla() {
        conectarBD();
        try {
            if (!conexion.isClosed()) {
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery("select*From proveedores");

                while (rs.next()) {
                    Object fila[] = new Object[2];
                    fila[0]= rs.getObject(1);
                    fila[1]= rs.getObject(2);
                    
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
     public void agregar(){
       conectarBD();
        String sql =
           "insert into proveedores (id_proveedor,ultima_visita,id_person_fk)" +
       "values(?,?,?)";
        try {
            if (!conexion.isClosed()) {
                PreparedStatement ps = Conexion.conexion.prepareStatement(sql);
                ps.setInt(1, this.id);
                ps.setString(2, this.visita);
                ps.setInt(3, this.idPerson);
                
                
                ps.executeUpdate();

                ps.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
   }
     public ResultSet buscar(String sql){
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
                ResultSet rs = st.executeQuery("select * From proveedores where id_proveedor='"+this.id+"'");
                
                PersonasBD p=new PersonasBD();
               while (rs.next()) {
                    Object fila[] = new Object[2];
                   
                        fila[0] = rs.getObject(1);
                        fila[1] = rs.getObject(2);
                        
                       
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
      public boolean modificar(){
        String sql="update proveedores set ultima_visita=? where id_proveedor="+this.id;
        conectarBD();
        try{
            PreparedStatement ps=Conexion.conexion.prepareStatement(sql);
            
            ps.setString(1,this.visita);
            ps.executeUpdate();
            ps.close();
            return true;
        }catch(Exception e){
            return false;
        }
    }
     public boolean borrar(){
        String sql="delete from proveedores where id_proveedor="+this.id;
        conectarBD();
        
        if(Conexion.ejecutarSQLUpdate(sql)){
                return true;
                
            }return false;
      }

    
}
