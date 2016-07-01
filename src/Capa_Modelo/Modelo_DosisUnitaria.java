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
import javax.swing.JOptionPane;

/**
 *
 * @author Javier Santibañez
 */
public class Modelo_DosisUnitaria {
    
    
   public Connection con;
   public ResultSet res, res2; 
   
   
   public Modelo_DosisUnitaria(){
    
    }
   
   
    public void IngresarDosisUnitaria(int rut,String elab, String venc, String entrega){

        Statement sentencia;

          try{

            con = ConexionDB.GetConnection();
            sentencia =con.createStatement();
            sentencia.executeUpdate("INSERT INTO Dosis_Unitaria VALUES ("+rut+","+"'"+elab+"',"+
                                     "'"+venc+"',"+"'"+entrega+"','Disponible')");


             JOptionPane.showMessageDialog(null,"La dosis unitaria  ha sido ingresado exitosamente ");                 
             con.close();
        }
        catch(SQLException e){JOptionPane.showMessageDialog(null,"Error al ingresar la dosis unitaria");}
        
    }
    
    public String [] ConsultaDUxRut(int rut){
        Statement sentencia;
        String [] datos = new String[6]; 
        try
            {
                con=ConexionDB.GetConnection();
                sentencia=con.createStatement();
                res=sentencia.executeQuery("SELECT * FROM Dosis_Unitaria WHERE Rut_Paciente = "+rut+"");
                
                
                while (res.next()) {
                    
                    
                    
                    datos[0] = Integer.toString(res.getInt("Rut_Paciente"));
                    datos[1] = res.getString("FechaElaboracion");
                    datos[2] = res.getString("FechaVencimiento");
                    datos[3] = res.getString("FechaEntrega");
                    datos[4] = res.getString("Disponible");
                    datos[5] = Integer.toString(res.getInt("ID_Dosis"));
                    
                    
                    
                    
                }
                if(datos[0] ==null ){
                    JOptionPane.showMessageDialog(null, "No existe dosis para el paciente indicado o el paciente no existe");
                }
            
            }
               
                
            catch(SQLException e){
                JOptionPane.showMessageDialog(null,e);}
                
            return datos;
            }
    
    public String [] ConsultaDosisUnitaria(int id){
        Statement sentencia;
        String [] datos = new String[5]; 
        try
            {
                con=ConexionDB.GetConnection();
                sentencia=con.createStatement();
                res=sentencia.executeQuery("SELECT * FROM Dosis_Unitaria WHERE ID_Dosis = "+id+"");
                
                
                while (res.next()) {
                    
                    
                    
                    datos[0] = Integer.toString(res.getInt("Rut_Paciente"));
                    datos[1] = res.getString("FechaElaboracion");
                    datos[2] = res.getString("FechaVencimiento");
                    datos[3] = res.getString("FechaEntrega");
                    datos[4] = res.getString("Disponible");
                    
                    
                    
                    
                }
                if(datos[0] ==null ){
                    JOptionPane.showMessageDialog(null, "La dosis no existe en la base de datos");
                }
            
            }
               
                
            catch(SQLException e){
                JOptionPane.showMessageDialog(null,e);}
                
            return datos;
            }
    
    public void ActualizarDosisUnitaria(int id,int rut,String elab, String venc, String entrega){
         
        
         PreparedStatement act;
         try
         {
            act = con.prepareStatement("UPDATE Dosis_Unitaria  SET Rut_Paciente= ?,FechaElaboracion= ?, FechaVencimiento= ?,FechaEntrega= ? "
                                        + "WHERE ID_Dosis = "+id+"");
            
            
            
            act.setInt(1, rut);
            act.setString(2, elab);
            act.setString(3, venc);
            act.setString(4, entrega);
            
            
            act.executeUpdate();
            act.close();
            

            
            
            JOptionPane.showMessageDialog(null, "La dosis unitaria fue actualizada exitosamente");
            con.close();
         }
         
         catch(SQLException e){
                JOptionPane.showMessageDialog(null,"Error al actualizar datos de la dosis");}
                
         
            
    }
    
    public void EliminarDosisUnitaria(int id) {
        
        Statement elim;
        
        try{
            con=ConexionDB.GetConnection();
            elim = con.createStatement();
            elim.execute("DELETE FROM Dosis_Unitaria WHERE ID_Dosis = "+id+"");
        
            JOptionPane.showMessageDialog(null,"La dosis fue eliminada correctamente");
            con.close();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
