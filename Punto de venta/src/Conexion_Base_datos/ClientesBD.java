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
public class ClientesBD {
    private int id_Cliente;
    private String r_Social;
    private String RFC;
    private String tipo;
    private int id_persona;
    public DefaultTableModel dtm;
    private PersonasBD p;
    
    public ClientesBD(){
       dtm= new DefaultTableModel();
    }
    

    public int getId_Cliente() {
        return id_Cliente;
    }

    public void setId_Cliente(int id_Cliente) {
        this.id_Cliente = id_Cliente;
    }

    public String getR_Social() {
        return r_Social;
    }

    public void setR_Social(String r_Social) {
        this.r_Social = r_Social;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }
    public void agregarColumnas() {
        dtm.addColumn("id");
        dtm.addColumn("Razon social");
        dtm.addColumn("RFC");
        dtm.addColumn("Tipo");

    }
    public void agregar() {
        conectarBD();
        String sql ="insert into clientes(R_social,RFC,tipo,id_person_fk)values(?,?,?,?)";
        try {
            if (!conexion.isClosed()) {
                PreparedStatement ps = Conexion.conexion.prepareStatement(sql);
                
                ps.setString(1, this.r_Social);
                ps.setString(2, this.RFC);
                ps.setString(3, this.tipo);
                ps.setInt(4, this.id_persona);
               
                ps.executeUpdate();

                ps.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public DefaultTableModel LlenarTabla() {
        conectarBD();
        try {
            if (!conexion.isClosed()) {
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery("select*From clientes");

                while (rs.next()) {
                    Object fila[] = new Object[5];
                   
                        fila[0] = rs.getObject(1);
                        fila[1] = rs.getObject(2);
                        fila[2] = rs.getObject(3);
                        fila[3] = rs.getObject(4);
                        
                        
                                        
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
                ResultSet rs = st.executeQuery("select*From clientes where R_social='"+this.r_Social+"'");

               while (rs.next()) {
                    Object fila[] = new Object[5];
                   
                        fila[0] = rs.getObject(1);
                        fila[2] = rs.getObject(2);
                        fila[3] = rs.getObject(3);
                        fila[4] = rs.getObject(4);
                        
                       ResultSet n= p.buscar("select nombre from persona p join clientes c where c.id_person_fk=p.id_person and id_cliente="+this.id_Cliente);
                       while(n.next()){
                           fila[1]=n.getObject(1);
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
        if(dtm.getRowCount()!=0){
        do {
            dtm.removeRow(dtm.getRowCount() - 1);
        } while (dtm.getRowCount() != 0);
     }
    }
      public boolean borrar(){
        String sql="delete from clientes where id_cliente="+this.id_Cliente;
        conectarBD();
        
        if(Conexion.ejecutarSQLUpdate(sql)){
                return true;
                
            }return false;
      }
      public boolean modificar(){
        String sql="update clientes set R_social=?,RFC=   ?,tipo=? where id_cliente="+this.id_Cliente;
        conectarBD();
        try{
            PreparedStatement ps=Conexion.conexion.prepareStatement(sql);
            ps.setString(1, this.r_Social);
            ps.setString(2,this.RFC);
            ps.setString(3, this.getTipo());
            ps.executeUpdate();
            ps.close();
            return true;
        }catch(Exception e){
            return false;
        }
    }
        
    
}
