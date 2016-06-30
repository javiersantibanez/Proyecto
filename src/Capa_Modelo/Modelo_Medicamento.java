/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capa_Modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Javier Santibañez
 */
public class Modelo_Medicamento {
    
    public Connection con;
    
    public Modelo_Medicamento(){
    
    }   


    public void IngresarMedicamento(String nSerie,String nombre,String pActivo, String lab, String vAdmin,
                                    String elab, String venc, String llegada, String comp){
        
        Statement sentencia;
          
          try{
                
            con = ConexionDB.GetConnection();
            sentencia =con.createStatement();
            sentencia.executeUpdate("INSERT INTO Medicamento VALUES ('"+nSerie+"',"+"'"+nombre+"',"+"'"+pActivo+"',"+
                                 "'"+lab+"',"+"'"+vAdmin+"',"+"'"+elab+"',"+
                                 "'"+venc+"',"+"'"+llegada+"',"+"'"+comp+"','disponible'");
                                   



             JOptionPane.showMessageDialog(null,"El medicamento  ha sido Ingresado Exitosamente ");                 
             con.close();
        }
        catch(SQLException e){JOptionPane.showMessageDialog(null,"Error al ingresar el paciente");}
        
  }

    
}



