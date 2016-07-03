/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capa_Modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Javier Santibañez
 */
public class Modelo_DosisUnitaria {
    
    
   public Connection con;
   public ResultSet res, res2;
   private PreparedStatement ps;
   DefaultTableModel dtm;
   private ResultSetMetaData rsm;
   
   
   public Modelo_DosisUnitaria(){
    
    }
   
   
    public void IngresarDosisUnitaria(int rut,Date elab, Date venc, Date entrega){

        Statement sentencia;

          try{

            con = ConexionDB.GetConnection();
            sentencia =con.createStatement();
            sentencia.executeUpdate("INSERT INTO Dosis_Unitaria VALUES ("+rut+","+"'"+elab+"',"+
                                     "'"+venc+"',"+"'"+entrega+"','No entregada')");


             JOptionPane.showMessageDialog(null,"La dosis unitaria  ha sido ingresado exitosamente ");                 
             con.close();
        }
        catch(SQLException e){JOptionPane.showMessageDialog(null,"Error al ingresar la dosis unitaria");}
        
    }
    
    public void ConsultaDUxRut(JTable tabla, int rut)throws Exception{
        
        con=ConexionDB.GetConnection();
        ps = con.prepareStatement("SELECT * FROM Dosis_Unitaria WHERE Rut_Paciente = "+rut+"");
        res=ps.executeQuery();
        rsm=res.getMetaData();
        ArrayList<Object[]> datos=new ArrayList<>();
        while (res.next()) {            
            Object[] filas=new Object[rsm.getColumnCount()];
            for (int i = 0; i < filas.length; i++) {
                filas[i]=res.getObject(i+1);
                
            }
            datos.add(filas);
        }
        dtm=(DefaultTableModel)tabla.getModel();
        for (int i = 0; i <datos.size(); i++) {
            dtm.addRow(datos.get(i));
        }
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
    
    public void ActualizarDosisUnitaria(int id,int rut,Date elab, Date venc, Date entrega, String stock){
         
        
         PreparedStatement act;
         try
         {
    
            act = con.prepareStatement("UPDATE Dosis_Unitaria  SET Rut_Paciente= ?,FechaElaboracion= ?, FechaVencimiento= ?,FechaEntrega= ?, Disponible = ? "
                                        + "WHERE ID_Dosis = "+id+"");
            
            
       
            act.setInt(1, rut);
            act.setDate(2, elab);
            act.setDate(3, venc);
            act.setDate(4, entrega);
            act.setString(5, stock);
            
            
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
    
    
    public void EntregarDosisUnitaria(int rut){
        PreparedStatement act;
        
        
        java.util.Date a = new java.util.Date();            
        long d = a.getTime();
        java.sql.Date fecha = new java.sql.Date(d);
            
         try
         { 
            
            con = ConexionDB.GetConnection();
            act = con.prepareStatement("UPDATE Dosis_Unitaria  SET Disponible = ? "
                                        + "WHERE FechaEntrega = '"+fecha+"' and Rut_Paciente = " +rut+"");
            
            
       
            act.setString(1, "Entregada");
           
            
            
            act.executeUpdate();
            act.close();
            JOptionPane.showMessageDialog(null, "La dosis unitaria fue entregada exitosamente");
            con.close();
          
         }
         
         catch(SQLException e){
                JOptionPane.showMessageDialog(null,"Error al entregar la dosis");}
    }
    
}
