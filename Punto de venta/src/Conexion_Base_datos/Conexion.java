/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion_Base_datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author sandra
 */
public class Conexion {

    UsuariosCB u;
    public static Connection conexion = null;

    public Conexion() {

    }

    public static boolean conectarBD() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/FerreteriaTop", "root", "");

        } catch (Exception ae) {
            return false;
        }
        return true;

    }

    public static boolean ejecutarSQLUpdate(String sql) {
        try {

            Statement sentencia = conexion.createStatement();
            sentencia.executeUpdate(sql);
            return true;

        } catch (SQLException ex) {

            System.out.println("Error en instruccion:  " + ex);
            return false;
        }

    }

    public static ResultSet ejecutarSQLSelect(String sql) {
        try {
            Statement sentencia = conexion.createStatement();
            return sentencia.executeQuery(sql);

        } catch (SQLException ex) {

            System.out.println("Error al ejecutar instruccion: " + ex);
            return null;
        }

    }

    public static void cerrarConexion() {

        try {
            conexion.close();
        } catch (SQLException ex) {

            System.out.println("Error al cerrar conexion");
        }
    }

}
