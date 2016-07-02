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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
        
        try {
            con = ConexionDB.GetConnection();
            
            Calendar fecha = new GregorianCalendar();
            int año = fecha.get(Calendar.YEAR);
            int mes = fecha.get(Calendar.MONTH);
            int dia = fecha.get(Calendar.DAY_OF_MONTH);
            String fechaEntrega = (año+"-"+mes+"-"+dia);
            
            System.out.println("Ejecutando consulta");
            act = con.prepareStatement("INSERT INTO MedicinaPaciente (Rut_Paciente, ID_Medicamento, Cantidad, FechaEntrega) "
                                     + "VALUES (?,?,?,?)");
            
            act.setInt(1, rutPaciente);
            act.setInt(2,nSerie);
            act.setInt(3, 1);
            act.setString(3,fechaEntrega);
            
            act.close();
            System.out.println("Consulta ejecutada");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error al entregar el medicamento");
        }
        
        
    }

    public void ConsultaInv(JTable tabla)throws Exception{
        
        PreparedStatement ps;
        ResultSetMetaData rsm;
        DefaultTableModel dtm;
        
        con=ConexionDB.GetConnection();
        ps = con.prepareStatement("SELECT * FROM Medicamento");
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
        
}



