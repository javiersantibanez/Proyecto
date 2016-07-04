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
import javax.swing.JComboBox;
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
                                    Date elab, Date venc, Date llegada, String comp, int cantidad){
        
        Statement sentencia,sentencia2;
          
          try{
                
            con = ConexionDB.GetConnection();
            sentencia =con.createStatement();
            sentencia2 = con.createStatement();
            
            
            sentencia.executeUpdate("INSERT INTO Medicamento VALUES ("+nSerie+","+"'"+nombre+"',"+"'"+pActivo+"',"+
                                 "'"+lab+"',"+"'"+vAdmin+"',"+"'"+elab+"',"+
                                 "'"+venc+"',"+"'"+llegada+"',"+"'"+comp+"')");
                                   
            sentencia2.executeUpdate("INSERT INTO Inventario VALUES("+nSerie+","+""+cantidad+",10,50)");


             JOptionPane.showMessageDialog(null,"El lote de  medicamentos  ha sido ingresado exitosamente ");                 
             con.close();
        }
        catch(SQLException e){JOptionPane.showMessageDialog(null,"Error al ingresar el lote de medicamentos");}
        
  }

    
    public String [] ConsultaMedicamento(int nSerie){
        Statement sentencia,sentencia2;
        String [] datos = new String[10]; 
        try
            {
                con=ConexionDB.GetConnection();
                sentencia=con.createStatement();
                sentencia2=con.createStatement();
                res=sentencia.executeQuery("SELECT * FROM Medicamento WHERE ID_Medicamento = "+nSerie+"");
                res2=sentencia2.executeQuery("SELECT Cantidad FROM Inventario WHERE ID_Medicamento ="+nSerie+"");
                
                while (res.next()) {
                    
                    
                    datos[0] = res.getString("Nombre");
                    datos[1] = res.getString("PrincipioActivo");
                    datos[2] = res.getString("Laboratorio");
                    datos[3] = res.getString("ViaAdministracion");
                    datos[4] = res.getString("FechaElaboracion");
                    datos[5] = res.getString("FechaVencimiento");
                    datos[6] = res.getString("FechaLlegada");
                    datos[7] = res.getString("Composicion");
                    datos[8] = Integer.toString(res.getInt("ID_Medicamento"));
                }
                
                while(res2.next()){
                    
                    datos[9]= Integer.toString(res2.getInt("Cantidad"));
                    
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
                                    Date elab, Date venc, Date llegada, String comp, int cantidad){
         
        
          PreparedStatement act,act2;
         try
         {
            act = con.prepareStatement("UPDATE Medicamento  SET Nombre = ?,PrincipioActivo = ?,Laboratorio = ?,ViaAdministracion= ?,"
                                        + "FechaElaboracion= ?, FechaVencimiento= ?,FechaLlegada= ? ,Composicion= ? "
                                        + "WHERE ID_Medicamento = "+nSerie+"");
            
            act2 = con.prepareStatement("UPDATE Inventario SET Cantidad=?");
            
            act.setString(1, nombre);
            act.setString(2, pActivo);
            act.setString(3, lab);
            act.setString(4, vAdmin);
            act.setDate(5, elab);
            act.setDate(6, venc);
            act.setDate(7, llegada);
            act.setString(8, comp);
            act2.setInt(1, cantidad);
            act2.executeUpdate();
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
    
    public void EntregarMedicamento(int rutPaciente, int id,String cantidad){
        
        
        Statement sentencia,sentencia2;
        
        try {
            con = ConexionDB.GetConnection();
            
            java.util.Date a = new java.util.Date();            
            long d = a.getTime();
            java.sql.Date fecha = new java.sql.Date(d);
            
          
            int cant = Integer.valueOf(cantidad);
            sentencia = con.createStatement();
            sentencia.executeUpdate("INSERT INTO MedicinaPaciente VALUES ("+rutPaciente+","+""+id+","+"'"+cant+"',"+
                                    "'"+fecha+"')");
            
            sentencia2 = con.createStatement();
            sentencia2.executeUpdate("UPDATE Inventario SET Cantidad = Cantidad -"+cant+"WHERE ID_Medicamento ="+id+"");
            
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
        ps = con.prepareStatement("SELECT Cantidad, Medicamento.Nombre, Medicamento.PrincipioActivo, Medicamento.Laboratorio, Medicamento.ViaAdministracion, Medicamento.Composicion FROM Inventario inner join Medicamento on Inventario.ID_Medicamento = Medicamento.ID_Medicamento  \n" +
                                  "GROUP BY  Cantidad,Nombre,PrincipioActivo,Laboratorio,ViaAdministracion,Composicion");
        
        res=ps.executeQuery();
        
        rsm=res.getMetaData();
        
        ArrayList<Object[]> datos=new ArrayList<>();
        while (res.next()) {            
            Object[] filas=new Object[6];
            filas[5] = res.getObject(1);
            filas[0] = res.getObject(2);
            filas[1] = res.getObject(3);
            filas[2] = res.getObject(4);
            filas[3] = res.getObject(5);
            filas[4] = res.getObject(6);
            
           
            
            datos.add(filas);
        }
        
        
        
       
        
        dtm=(DefaultTableModel)tabla.getModel();
        for (int i = 0; i <datos.size(); i++) {
            dtm.addRow(datos.get(i));
        }
    }
    
    public void consultarMed(JComboBox combox){
        
        Statement sentencia;
            try {
             
             con =ConexionDB.GetConnection();
             sentencia = con.createStatement();
             res = sentencia.executeQuery ("SELECT Nombre FROM Medicamento");

            while(res.next()){
               String aux = res.getString("Nombre");
               combox.addItem(aux);
            }
            con.close();
            } catch (Exception e) {
             System.out.println("ERROR: failed to load HSQLDB JDBC driver.");
             
         }     
    }
    
    public void consultaMesDosis(JComboBox combox,String nombre){
        
        Statement sentencia;
            try {
             
             con =ConexionDB.GetConnection();
             sentencia = con.createStatement();
             res = sentencia.executeQuery ("SELECT Composicion FROM Medicamento WHERE Nombre ='"+nombre+"'");

            while(res.next()){
               String aux = res.getString("Composicion");
               combox.addItem(aux);
            }
            con.close();
            } catch (Exception e) {
             System.out.println("ERROR: failed to load HSQLDB JDBC driver.");
             
         }     
    }
     
    public void vaciarComboBox(JComboBox combox){
        int itemCount = combox.getItemCount();

        for(int i=1;i<itemCount;i++){
            combox.removeItemAt(1);
         }
    }
    
    public int obtenerID( String nombre, String dosis) throws SQLException{
        
            Statement sentencia,sentencia2,sentencia3;
            String aux="",aux2="",aux3="";
            
            con =ConexionDB.GetConnection();
            sentencia = con.createStatement();
            sentencia2 = con.createStatement();
            sentencia3 = con.createStatement();
            
            res = sentencia2.executeQuery ("SELECT Nombre,Composicion FROM Medicamento WHERE Nombre = '"+nombre+"' and Composicion = '"+dosis+"'");
            
            while(res.next()){
                aux = res.getString("Nombre");
                aux2 = res.getString("Composicion");
            }

            
            res2 =  sentencia3.executeQuery("SELECT ID_Medicamento FROM Medicamento WHERE Nombre='"+aux+"' and Composicion= '"+aux2+"'" );
            
            while(res2.next()){
                 aux3 = res2.getString("ID_Medicamento");
            }
     return Integer.parseInt(aux3);       
    }
    
    public void cantidadMedicamento(JComboBox combox, int id){
         Statement sentencia;
            try {
             
             con =ConexionDB.GetConnection();
             sentencia = con.createStatement();
             res = sentencia.executeQuery ("SELECT Cantidad FROM Inventario WHERE ID_Medicamento = "+id+"");

            while(res.next()){
               String aux = res.getString("Cantidad");
               for(int i=1;i <= Integer.parseInt(aux);i++){
                   combox.addItem(i);
               }
            }
            con.close();
            } catch (Exception e) {
             System.out.println("ERROR: failed to load HSQLDB JDBC driver.");
             
         }     
        
    }
    
}



