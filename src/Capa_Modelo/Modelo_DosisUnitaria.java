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
 * Esta clase se comunica con la base de datos y accede a los datos de las Dosis Unitarias
 * Esta clase es parte de la capa modelo
 * @author Javier Santibáñez,Franco Soto and José Valdivia
 * @version version 1.0
 */
public class Modelo_DosisUnitaria {
    
    
   public Connection con;
   public ResultSet res, res2;
   private PreparedStatement ps;
   DefaultTableModel dtm;
   private ResultSetMetaData rsm;
   
   /**
    * Constructor de la clase
    */
   public Modelo_DosisUnitaria(){
    
    }
   
   /**
    * Este metodo agrega una nueva dosis unitaria a la base de datos
    * @param rut rut del paciente
    * @param elab fecha de elaboracion de la dosis unitaria
    * @param venc fecha de vencimento de la dosis unitaria
    * @param entrega fecha de entrega de la dosis unitaria
    */
    public void setDosisUnitaria(int rut,Date elab, Date venc, Date entrega){

        Statement sentencia;

          try{

            con = ConexionDB.getConnection();
            sentencia =con.createStatement();
            sentencia.executeUpdate("INSERT INTO Dosis_Unitaria VALUES ("+rut+","+"'"+elab+"',"+
                                     "'"+venc+"',"+"'"+entrega+"','No entregada')");


             JOptionPane.showMessageDialog(null,"La dosis unitaria  ha sido ingresado exitosamente ");                 
             con.close();
        }
        catch(SQLException e){JOptionPane.showMessageDialog(null,"Error al ingresar la dosis unitaria");}
        
    }
    
    /**
     * Este metodo obtiene los datos de una dosis unitaria segun el rut del paciente
     * @param tabla tabla
     * @param rut rt del paciente
     * @throws Exception SQL exception
     */
    public void consultaDUxRut(JTable tabla, int rut)throws Exception{
        
        con=ConexionDB.getConnection();
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
    
    /**
     * Este metodo obtiene los datos de una dosis unitaria segun su id
     * @param id id de la dosis unitaria
     * @return datos de la dosis unitaria
     */
    public String [] getDosisUnitaria(int id){
        Statement sentencia;
        String [] datos = new String[5]; 
        try
            {
                con=ConexionDB.getConnection();
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
    
     /**
     * Este metodo actualiza los datos de una dosis unitaria de la base de datos
     * @param id codigo de la dosis unitaria
     * @param rut rut del paciente
     * @param elab fecha de elaboracion de la dosis unitaria
     * @param venc fecha de vencimiento de la dosis unitaria
     * @param entrega  fecha de entrega de la dosis unitaria
     */
    public void actualizarDosisUnitaria(int id,int rut,Date elab, Date venc, Date entrega, String stock){
         
        
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

    /**
     * Este metodo elimina una dosis unitaria de la base de datos
     * @param id codigo de la dosis unitaria
     */
    public void eliminarDosisUnitaria(int id) {
        
        Statement elim;
        
        try{
            con=ConexionDB.getConnection();
            elim = con.createStatement();
            elim.execute("DELETE FROM Dosis_Unitaria WHERE ID_Dosis = "+id+"");
        
            JOptionPane.showMessageDialog(null,"La dosis fue eliminada correctamente");
            con.close();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /**
     * Este metodo ingresa una dosis unitaria como entregada a la base de datos
     * @param rut rut del paciente  de la dosis
     */
    public void entregarDosisUnitaria(int rut){
        PreparedStatement act;
        
        
        java.util.Date a = new java.util.Date();            
        long d = a.getTime();
        java.sql.Date fecha = new java.sql.Date(d);
            
         try
         { 
            
            con = ConexionDB.getConnection();
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
