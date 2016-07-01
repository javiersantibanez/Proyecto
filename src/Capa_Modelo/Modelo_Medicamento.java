/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capa_Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public ResultSet res, res2;
    
    public Modelo_Medicamento(){
    
    }   


    public void IngresarMedicamento(int nSerie,String nombre,String pActivo, String lab, String vAdmin,
                                    String elab, String venc, String llegada, String comp){
        
        Statement sentencia;
          
          try{
                
            con = ConexionDB.GetConnection();
            sentencia =con.createStatement();
            sentencia.executeUpdate("INSERT INTO Medicamento VALUES ("+nSerie+","+"'"+nombre+"',"+"'"+pActivo+"',"+
                                 "'"+lab+"',"+"'"+vAdmin+"',"+"'"+elab+"',"+
                                 "'"+venc+"',"+"'"+llegada+"',"+"'"+comp+"','Disponible')");
                                   



             JOptionPane.showMessageDialog(null,"El medicamento  ha sido ingresado exitosamente ");                 
             con.close();
        }
        catch(SQLException e){JOptionPane.showMessageDialog(null,"Error al ingresar el medicamento");}
        
  }

    
    public String [] ConsultaMedicamento(int nSerie){
        Statement sentencia;
        String [] datos = new String[10]; 
        try
            {
                con=ConexionDB.GetConnection();
                sentencia=con.createStatement();
                res=sentencia.executeQuery("SELECT * FROM Medicamento WHERE ID_Medicamento = "+nSerie+"");
                
                
                while (res.next()) {
                    
                    
                    datos[0] = res.getString("Nombre");
                    datos[1] = res.getString("PrincipioActivo");
                    datos[2] = res.getString("Laboratorio");
                    datos[3] = res.getString("ViaAdministracion");
                    datos[4] = res.getString("FechaElaboracion");
                    datos[5] = res.getString("FechaVencimiento");
                    datos[6] = res.getString("FechaLlegada");
                    datos[7] = res.getString("Composicion");
                    datos[8] = res.getString("Disponible");
                    datos[9] = Integer.toString(res.getInt("ID_Medicamento"));
                    
                    
                }
                if(datos[0] ==null ){
                    JOptionPane.showMessageDialog(null, "El medicamento no existe en la base de datos");
                }
            
            }
               
                
            catch(SQLException e){
                JOptionPane.showMessageDialog(null,e);}
                
            return datos;
            }
 
    
    
    public void ActualizarMedicamento(int nSerie,String nombre,String pActivo, String lab, String vAdmin,
                                    String elab, String venc, String llegada, String comp){
         
        
          PreparedStatement act;
         try
         {
            act = con.prepareStatement("UPDATE Medicamento  SET Nombre = ?,PrincipioActivo = ?,Laboratorio = ?,ViaAdministracion= ?,"
                                        + "FechaElaboracion= ?, FechaVencimiento= ?,FechaLlegada= ? ,Composicion= ? "
                                        + "WHERE ID_Medicamento = "+nSerie+"");
            
            
            
            act.setString(1, nombre);
            act.setString(2, pActivo);
            act.setString(3, lab);
            act.setString(4, vAdmin);
            act.setString(5, elab);
            act.setString(6, venc);
            act.setString(7, llegada);
            act.setString(8, comp);
            
            act.executeUpdate();
            act.close();
            

            
            
            JOptionPane.showMessageDialog(null, "El medicamento fue actualizado exitosamente");
            con.close();
         }
         
         catch(SQLException e){
                JOptionPane.showMessageDialog(null,"Error al actualizar datos del medicamento");}
                
         
            
    }




    public void EliminarMedicamento(int nSerie) {
        
        Statement elim;
        
        try{
            con=ConexionDB.GetConnection();
            elim = con.createStatement();
            elim.execute("DELETE FROM Medicamento WHERE ID_Medicamento = '"+nSerie+"'");
        
            JOptionPane.showMessageDialog(null,"El medicamento fue eliminado correctamente");
            con.close();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null,"El medicamento no existe en la base de datos");
        }
    }
    
}



