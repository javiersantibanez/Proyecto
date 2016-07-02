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
import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


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
                                    Date elab, Date venc, Date llegada, String comp){
        
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
                                    Date elab, Date venc, Date llegada, String comp){
         
        
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
            act.setDate(5, elab);
            act.setDate(6, venc);
            act.setDate(7, llegada);
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
    
    public void EntregarMedicamento(int rutPaciente, int nSerie){
        
        PreparedStatement act;
        Statement sentencia;
        
        try {
            con = ConexionDB.GetConnection();
            
            java.util.Date a = new java.util.Date();            
            long d = a.getTime();
            java.sql.Date fecha = new java.sql.Date(d);
            
          
            
            sentencia = con.createStatement();
            sentencia.executeUpdate("INSERT INTO MedicinaPaciente VALUES ("+rutPaciente+","+""+nSerie+","+""+1+","+
                                    "'"+fecha+"')");
            
            JOptionPane.showMessageDialog(null, "Entrega registrada con exito");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e);
        }
        
        
    }

    public void ConsultaInv(JTable tabla)throws Exception{
        
        PreparedStatement ps,ps2;
        ResultSetMetaData rsm,rsm2;
        DefaultTableModel dtm;
        
        con=ConexionDB.GetConnection();
        ps = con.prepareStatement("select Nombre,PrincipioActivo,Laboratorio,ViaAdministracion,Composicion ,count(Nombre)  AS Total from Medicamento \n" +
                                  "GROUP BY Nombre,PrincipioActivo,Laboratorio,ViaAdministracion,Composicion");
        
        res=ps.executeQuery();
        
        rsm=res.getMetaData();
        
        ArrayList<Object[]> datos=new ArrayList<>();
        while (res.next()) {            
            Object[] filas=new Object[6];
            filas[0] = res.getObject(1);
            filas[1] = res.getObject(2);
            filas[2] = res.getObject(3);
            filas[3] = res.getObject(4);
            filas[4] = res.getObject(5);
            filas[5] = res.getObject(6);
            
            datos.add(filas);
        }
        
       
        
        dtm=(DefaultTableModel)tabla.getModel();
        for (int i = 0; i <datos.size(); i++) {
            dtm.addRow(datos.get(i));
        }
    }
        
}



