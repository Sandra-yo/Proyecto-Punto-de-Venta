/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion_Base_datos;

import static Conexion_Base_datos.Conexion.conectarBD;
import static Conexion_Base_datos.Conexion.conexion;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author sandra
 */
public class PersonasBD {
 private  String nombre,apellido_P,apellido_M,direccion,colonia,correo;
 private  int id_Persona, id_listaT,id_transaccion;
 private  String fechaN,tipo;
 public DefaultTableModel dtm;

 public PersonasBD(){
   
        dtm = new DefaultTableModel();  
 }
    public String getNombre() {
        return nombre;
        
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
     public String getTipo() {
        return nombre;
        
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getApellido_P() {
        return apellido_P;
    }

    public void setApellido_P(String apellido_P) {
        this.apellido_P = apellido_P;
    }

    public String getApellido_M() {
        return apellido_M;
    }

    public void setApellido_M(String apellido_M) {
        this.apellido_M = apellido_M;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFecha_n() {
        return fechaN;
    }

    public void setFecha_n(String fecha_n) {
        this.fechaN = fecha_n;
    }

    public int getId_Persona() {
        return id_Persona;
    }

    public void setId_Persona(int id_Persona) {
        this.id_Persona = id_Persona;
    }
    
    public int getId_listaT() {
        return id_listaT;
    }

    public void setId_listaT(int id_listaT) {
        this.id_listaT = id_listaT;
    }

    public int getId_transaccion() {
        return id_transaccion;
    }

    public void setId_transaccion(int id_transaccion) {
        this.id_transaccion = id_transaccion;
    }
    public void agregarColumnas() {
        dtm.addColumn("Nombre");
        dtm.addColumn("Primer Apellido");
        dtm.addColumn("Segundo Apellido");
        dtm.addColumn("Direccion");
        dtm.addColumn("Colonia");
        dtm.addColumn("Correo");
        dtm.addColumn("Fecha de nacimiento");
    }
   public DefaultTableModel LlenarTabla() {
        conectarBD();
        try {
            if (!conexion.isClosed()) {
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery("select*From persona");

                while (rs.next()) {
                    Object fila[] = new Object[7];
                    for (int i = 0; i < 7; i++) {
                        fila[i] = rs.getObject(i + 2);

                    }
                    fila[6]=rs.getObject(9);
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
   public DefaultTableModel LlenarTabla(String sql) {
        conectarBD();
        try {
            if (!conexion.isClosed()) {
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery(sql);

                while (rs.next()) {
                    Object fila[] = new Object[7];
                    for (int i = 0; i < 7; i++) {
                        fila[i] = rs.getObject(i + 2);

                    }
                    fila[6]=rs.getObject(9);
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
        String sql="update persona set nombre=?,apellido_1=?,apellido_2=?,direccion=?,colonia=?, correo=?,fecha_nacimiento=? where nombre='"+nombre+"' and apellido_1='"+apellido_P+"' and apellido_2='"+apellido_M+"'";
        conectarBD();
        try{
            PreparedStatement ps=Conexion.conexion.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, apellido_P);
            ps.setString(3, apellido_M);
            ps.setString(4, direccion); 
            ps.setString(5, colonia);
            ps.setString(6, correo); 
            ps.setString(7, fechaN);
            ps.executeUpdate();
            ps.close();
            return true;
        }catch(Exception e){
            return false;
        }
    }
    public boolean modificarn(){
        String sql="update persona set tipo='no' where nombre='"+nombre+"';";
        conectarBD();
        try{
            PreparedStatement ps=Conexion.conexion.prepareStatement(sql);
            
            ps.executeUpdate();
            ps.close();
            return true;
        }catch(Exception e){
            return false;
        }
    }
   public void agregar(){
       conectarBD();
        String sql =
       "insert into persona (nombre,apellido_1,apellido_2,direccion," +
       "colonia,correo,fecha_nacimiento,tipo)" +
       "values(?,?,?,?,?,?,?,?)";
        try {
            if (!conexion.isClosed()) {
                PreparedStatement ps = Conexion.conexion.prepareStatement(sql);
                ps.setString(1, nombre);
                ps.setString(2, apellido_P);
                ps.setString(3, apellido_M);
                ps.setString(4, direccion);
                ps.setString(5, colonia);
                ps.setString(6, correo);
                ps.setString(7,fechaN);
                ps.setString(8, tipo);
                
                
                ps.executeUpdate();

                ps.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
   }

   
   public ResultSet buscar(){
        String sql=
       "select * from persona where nombre='"+nombre+"' and apellido_1='"+apellido_P+"' and apellido_2='"+apellido_M+"'";
        Conexion.conectarBD();
          ResultSet resultado = Conexion.ejecutarSQLSelect(sql);
          
        return resultado;
    }
    public ResultSet buscar(String sql){
         Conexion.conectarBD();
          ResultSet resultado = Conexion.ejecutarSQLSelect(sql);
        return resultado;
    }
    public boolean borrar(){
        
        String sql="delete from persona where nombre='"+nombre+"' and apellido_1='"+apellido_P+"' and apellido_2='"+apellido_M+"'";
        
        conectarBD(); 
        
        if(Conexion.ejecutarSQLUpdate(sql)){
                return true;
                
            }
            else{
                return false;
            }
    }
     public void borrarTabla() {
         if(dtm.getRowCount()!=0){
        do {
            dtm.removeRow(dtm.getRowCount() - 1);
        } while (dtm.getRowCount() != 0);
         }
    }
     
     public DefaultTableModel buscarEnTabla(){
        conectarBD();
        try {
            borrarTabla();
            if (!conexion.isClosed()) {
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery("select * from persona where nombre='"+nombre+"' and apellido_1='"+apellido_P+"' and apellido_2='"+apellido_M+"'");

                 while (rs.next()) {
                    Object fila[] = new Object[7];
                    for (int i = 0; i < 7; i++) {
                        fila[i] = rs.getObject(i + 2);

                    }
                    fila[6]=rs.getObject(9);
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
