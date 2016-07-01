/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capa_Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Javier Santibañez
 */
public class ConexionDB {
    
    
    public static Connection GetConnection(){
        Connection conexion = null;
        String ruta = "jdbc:sqlserver://Blazter\\MSSQLSERVER:1433;databaseName=Proyecto";
        
     
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
           
            conexion= DriverManager.getConnection(ruta,"sa","1234");
            conexion.createStatement();
           
        }
        catch(ClassNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null,  "No se encuentra la clase para conectar con SQL ","", JOptionPane.CLOSED_OPTION,null);
            
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al conectar con la Base de Datos " + ex.toString(),"", JOptionPane.CLOSED_OPTION,null);
            
        }
        
        finally
        {
            return conexion;
        }
    }
    
}
