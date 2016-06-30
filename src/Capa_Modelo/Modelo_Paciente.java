/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capa_Modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Javier Santibañez
 */
public class Modelo_Paciente {


   
    
    public  Connection con ;
    public  ResultSet resultado,res,res2;
    

    public Modelo_Paciente(){ 
 
}

      


    
    public void Ingresarpaciente(String calle, int numero, int depto, 
                                int torre, String comuna, String ciudad, 
                                String region, int rut, String Pnombre, 
                                String Snombre, String apellidoP, String apellidoM, 
                                String fechaNacimiento, int telefono, String correo, 
                                String Diagnostico){
        
        Statement sentencia;
          int id=2;
          try{

          con = ConexionDB.GetConnection();
          sentencia =con.createStatement();
          sentencia.executeUpdate("INSERT INTO Direccion VALUES ('"+id+"',"+"'"+calle+"',"+"'"+numero+"',"+
                                  "'"+depto+"',"+"'"+torre+"',"+"'"+comuna+"',"+"'"+ciudad+"',"+"'"+region+"')");
          
          sentencia.executeUpdate("INSERT INTO Paciente VALUES('"+rut+"',"+"'"+Pnombre+"',"+"'"+Snombre+"',"+
                               "'"+apellidoP+"',"+"'"+apellidoM+"',"+"'"+fechaNacimiento+"',"+
                               "'"+telefono+"',"+"'"+id+"',"+"'"+correo+"',"+"'"+Diagnostico+"')");

          

           JOptionPane.showMessageDialog(null,"El Paciente  " + Pnombre+ " "+ apellidoP+"   ha sido Ingresado Exitosamente ");                 
           con.close();
        }
        catch(SQLException e){JOptionPane.showMessageDialog(null,"Error al ingresar el paciente");}
        
  }

    
    public String [] ConsultaPaciente(int rut){
        Statement sentencia2,sentencia3;
        String [] datos = new String[16]; 
        try
            {
                con=ConexionDB.GetConnection();
                sentencia2=con.createStatement();
                res=sentencia2.executeQuery("SELECT * FROM Paciente WHERE Rut_Paciente = '"+rut+"'");
                
                
                while (res.next()) {
                    
                    datos[0] = res.getString("PrimerNombre");
                    datos[1] = res.getString("SegundoNombre");
                    datos[2] = res.getString("PrimerApellido");
                    datos[3] = res.getString("SegundoApellido");
                    datos[4] = res.getString("FechaNacimiento");
                    datos[5] = Integer.toString(res.getInt("Telefono"));
                    datos[6] = Integer.toString(res.getInt("ID_Direccion"));
                    datos[7] = res.getString("Correo_Electronico");
                    datos[8] = res.getString("Diagnostico");
                }
                
                sentencia3=con.createStatement();
                res2=sentencia3.executeQuery("SELECT * FROM Direccion WHERE ID_Direccion = '"+datos[6]+"'"); 
                
                while (res2.next()) {
                    
                    datos[9] = res2.getString("Calle");
                    datos[10] = Integer.toString(res2.getInt("Numero"));
                    datos[11] = Integer.toString(res2.getInt("Departamento"));
                    datos[12] = Integer.toString(res2.getInt("Torre"));
                    datos[13] = res2.getString("Comuna");
                    datos[14] = res2.getString("Ciudad");
                    datos[15] = res2.getString("Region");
                    
                }
            }
               
                
            catch(SQLException e){
                JOptionPane.showMessageDialog(null,"El paciente no existe en la base de datos");}
                
            return datos;
            }
 
    
    
    public void ActualizarPaciente( String calle, int numero, int depto, 
                                    int torre, String comuna, String ciudad, 
                                    String region, int rut, String Pnombre, 
                                    String Snombre, String apellidoP, String apellidoM, 
                                    String fechaNacimiento, int telefono, String correo, 
                                    String Diagnostico){
         
        
          PreparedStatement act,act2;
         try
         {
            act = con.prepareStatement("UPDATE Direccion SET Calle = ?,Numero = ?,Departamento = ?,Torre = ?,Comuna = ?,"
                                        + " Ciudad = ?,Region = ?");
            
            
            act.setString(1, calle);
            act.setInt(2, numero);
            act.setInt(3, depto);
            act.setInt(4, torre);
            act.setString(5, comuna);
            act.setString(6, ciudad);
            act.setString(7, region);
            
            act.executeUpdate();
            act.close();
            
            
            act2 = con.prepareStatement("UPDATE Paciente SET PrimerNombre = ?,SegundoNombre = ?,PrimerApellido = ?,SegundoApellido = ?,"
                                        + "FechaNacimiento = ?, Telefono = ?,Correo_Electronico= ?, Diagnostico = ?");
            
            
            
            act2.setString(1, Pnombre);
            act2.setString(2, Snombre);
            act2.setString(3, apellidoP);
            act2.setString(4, apellidoM);
            act2.setString(5, fechaNacimiento);
            act2.setInt(6, telefono);
            act2.setString(7, correo);
            act2.setString(8, Diagnostico);
            
            act2.executeUpdate();
            act2.close();
            con.close();
         }
         
         catch(SQLException e){
                JOptionPane.showMessageDialog(null,"Error al actualizar paciente");}
                
         
            
    }

    
    public void EliminarPaciente(int rut) {
        
        Statement elim;
        
        try{
        elim = con.createStatement();
        elim.execute("DELETE FROM Paciente WHERE Rut_Paciente = '"+rut+"'");
        
        JOptionPane.showMessageDialog(null,"El paciente fue eliminado correctamente");
        con.close();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    
}



