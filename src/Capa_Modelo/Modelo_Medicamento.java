/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capa_Modelo;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 * Esta clase se comunica con la base de datos y accede a los datos del medicamento 
 * Esta clase es parte de la capa modelo
 * @author Javier Santibáñez,Franco Soto and José Valdivia
 * @version version 1.0
 */
public class Modelo_Medicamento {
    
    public Connection con;
    public ResultSet res, res2;
    
    /**
     * Contructor de la clase
     */
    public Modelo_Medicamento(){
    
    }   

    /**
     * Esta clase ingresa un nuevo medicamento a la base de datos
     * @param nSerie codigo del medicamento
     * @param nombre nombre del medicamento
     * @param pActivo principio activo del medicamento
     * @param lab laboratorio del medicamento
     * @param vAdmin via de administracion del medicamento
     * @param elab fecha de elaboracion del medicamento
     * @param venc fecha de vencimiento del medicamento
     * @param llegada fecha de llegada del medicamento
     * @param comp dosis del medicamento
     * @param cantidad cantidad del medicamento
     */
    public void setMedicamento(int nSerie,String nombre,String pActivo, String lab, String vAdmin,
                                    Date elab, Date venc, Date llegada, String comp, int cantidad){
        
        Statement sentencia,sentencia2;
          
          try{
                
            con = ConexionDB.getConnection();
            sentencia =con.createStatement();
            sentencia2 = con.createStatement();
            
            
            sentencia.executeUpdate("INSERT INTO Medicamento VALUES ("+nSerie+","+"'"+nombre+"',"+"'"+pActivo+"',"+
                                 "'"+lab+"',"+"'"+vAdmin+"',"+"'"+elab+"',"+
                                 "'"+venc+"',"+"'"+llegada+"',"+"'"+comp+"',"+""+cantidad+")");
                                   
            sentencia2.executeUpdate("INSERT INTO Inventario VALUES("+nSerie+","+""+cantidad+",10,50)");


             JOptionPane.showMessageDialog(null,"El lote de  medicamentos  ha sido ingresado exitosamente ");                 
             con.close();
        }
        catch(SQLException e){JOptionPane.showMessageDialog(null,"Error al ingresar el lote de medicamentos");}
        
  }

    /**
     * Este metodo obtiene los datos del medicamento de la base de datos
     * @param nSerie codigo del medicamento
     * @return los datos del medicamento
     */
    public String [] getMedicamento(int nSerie){
        Statement sentencia,sentencia2;
        String [] datos = new String[10]; 
        try
            {
                con=ConexionDB.getConnection();
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
                    datos[7] = res.getString("Dosis");
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
 
    
     /**
     * Este metodo actualiza los datos del medicamento
     * @param nSerie codigo del medicamento
     * @param nombre nombre del medicamento
     * @param pActivo principio activo del medicamento
     * @param lab laboratorio del medicamento
     * @param vAdmin via de administracion del medicamento
     * @param elab fecha de elaboracion del medicamento
     * @param venc fecha de vencimiento del medicamento
     * @param llegada fecha de llegada del medicamento
     * @param comp dosis del medicamento 
     * @param cantidad cantidad del medicamento
     */
    public void actualizarMedicamento(int nSerie,String nombre,String pActivo, String lab, String vAdmin,
                                    Date elab, Date venc, Date llegada, String comp, int cantidad){
         
        
          PreparedStatement act,act2;
         try
         {
            act = con.prepareStatement("UPDATE Medicamento  SET Nombre = ?,PrincipioActivo = ?,Laboratorio = ?,ViaAdministracion= ?,"
                                        + "FechaElaboracion= ?, FechaVencimiento= ?,FechaLlegada= ? ,Dosis= ?,CantidadLote=? "
                                        + "WHERE ID_Medicamento = "+nSerie+"");
            
            act2 = con.prepareStatement("UPDATE Inventario SET Cantidad=? WHERE ID_Medicamento="+nSerie+"");
            
            act.setString(1, nombre);
            act.setString(2, pActivo);
            act.setString(3, lab);
            act.setString(4, vAdmin);
            act.setDate(5, elab);
            act.setDate(6, venc);
            act.setDate(7, llegada);
            act.setString(8, comp);
            act.setInt(9,cantidad);
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


     /**
     * Este metodo elimina un medicamento de la base de datos
     * @param nSerie codigo del medicamento
     */
    public void eliminarMedicamento(int nSerie) {
        
        Statement elim;
        
        try{
            con=ConexionDB.getConnection();
            elim = con.createStatement();
            elim.execute("DELETE FROM Medicamento WHERE ID_Medicamento = '"+nSerie+"'");
        
            JOptionPane.showMessageDialog(null,"El medicamento fue eliminado correctamente");
            con.close();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null,"El medicamento no existe en la base de datos");
        }
    }
    
    /**
     * Este metodo ingresa un medicamento como entregado a la base de datos
     * @param rutPaciente rut del paciente
     * @param id codigo del medicamento
     * @param cantidad cantidad de medicamentos
     */
    public void entregarMedicamento(int rutPaciente, int id,String cantidad){
        
        
        Statement sentencia,sentencia2;
        
        try {
            con = ConexionDB.getConnection();
            
            java.util.Date a = new java.util.Date();            
            long d = a.getTime();
            java.sql.Date fecha = new java.sql.Date(d);
            
          
            int cant = Integer.valueOf(cantidad);
            sentencia = con.createStatement();
            sentencia.executeUpdate("INSERT INTO MedicinaPaciente VALUES ("+rutPaciente+","+""+id+","+"'"+cant+"',"+
                                    "'"+fecha+"')");
            
            sentencia2 = con.createStatement();
            sentencia2.executeUpdate("UPDATE Inventario SET Cantidad = Cantidad -"+cant+"WHERE ID_Medicamento ="+id+"");
            
            
            JOptionPane.showMessageDialog(null,alertaEscaces(id));
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"El paciente no existe en la base de datos");
        }
        
        
    }
    
    /**
     * Este metodo obtiene los datos del inventario de la base de datos
     * @param tabla tabla
     * @throws Exception SQL exception
     */
    public void consultaInv(JTable tabla)throws Exception{
        
        PreparedStatement ps,ps2;
        ResultSetMetaData rsm,rsm2;
        DefaultTableModel dtm;
        
        con=ConexionDB.getConnection();
        ps = con.prepareStatement("SELECT Cantidad, Medicamento.Nombre, Medicamento.PrincipioActivo, Medicamento.Laboratorio, Medicamento.ViaAdministracion, Medicamento.Dosis FROM Inventario inner join Medicamento on Inventario.ID_Medicamento = Medicamento.ID_Medicamento  \n" +
                                  "GROUP BY  Cantidad,Nombre,PrincipioActivo,Laboratorio,ViaAdministracion,Dosis");
        
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
    
    /**
     * Este metodo obtiene el nombre de los medicamentos en la base de datos y los envia a un combobox
     * @param combox combobox
     */
    public void consultarMed(JComboBox combox){
        
        Statement sentencia;
            try {
             
             con =ConexionDB.getConnection();
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
    
    /**
     * Este metodo obtiene la dosis de los medicamentos en la base de datos y los envia a un combobox
     * @param combox combobox
     * @param nombre nombre del medicamento
     */
    public void consultaMesDosis(JComboBox combox,String nombre){
        
        Statement sentencia;
            try {
             
             con =ConexionDB.getConnection();
             sentencia = con.createStatement();
             res = sentencia.executeQuery ("SELECT Dosis FROM Medicamento WHERE Nombre ='"+nombre+"'");

            while(res.next()){
               String aux = res.getString("Dosis");
               combox.addItem(aux);
            }
            con.close();
            } catch (Exception e) {
             System.out.println("ERROR: failed to load HSQLDB JDBC driver.");
             
         }     
    }
    
    /**
     * Este metodo vacia los datos del combobox
     * @param combox  combobox
     */
    public void vaciarComboBox(JComboBox combox){
        int itemCount = combox.getItemCount();

        for(int i=1;i<itemCount;i++){
            combox.removeItemAt(1);
         }
    }
    
    /**
     * Este metodo obtiene el id ingresado de un medicamento de la base de datos
     * @param nombre nombre del medicamento
     * @param dosis dosis del medicamento
     * @return id del medicamento
     * @throws SQLException 
     */
    public int obtenerID( String nombre, String dosis) throws SQLException{
        
            Statement sentencia,sentencia2,sentencia3;
            String aux="",aux2="",aux3="";
            
            con =ConexionDB.getConnection();
            sentencia = con.createStatement();
            sentencia2 = con.createStatement();
            sentencia3 = con.createStatement();
            
            res = sentencia2.executeQuery ("SELECT Nombre,Dosis FROM Medicamento WHERE Nombre = '"+nombre+"' and Dosis = '"+dosis+"'");
            
            while(res.next()){
                aux = res.getString("Nombre");
                aux2 = res.getString("Dosis");
            }
            res2 =  sentencia3.executeQuery("SELECT ID_Medicamento FROM Medicamento WHERE Nombre='"+aux+"' and Dosis= '"+aux2+"'" );
            
            while(res2.next()){
                 aux3 = res2.getString("ID_Medicamento");
            }
     return Integer.parseInt(aux3);       
    }
    
    /**
     * Este metodo obtiene la cantidad de medicamento del inventario de la base de datos y lo agrega a un combobox
     * @param combox combobox
     * @param id codigo del medicamento
     */
    public void cantidadMedicamento(JComboBox combox, int id){
         Statement sentencia;
            try {
             
             con =ConexionDB.getConnection();
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
    
    
    
    public String alertaEscaces(int id){
        Statement sentencia;
        int aux=0,aux2=0;
        String msj="";
            try {
             
             con =ConexionDB.getConnection();
             sentencia = con.createStatement();
             res = sentencia.executeQuery ("SELECT CantidadMin,Cantidad FROM Inventario WHERE ID_Medicamento = "+id+"");

            while(res.next()){
                aux  = res.getInt("CantidadMin");
                aux2 = res.getInt("Cantidad");                            
            }
                
            if(aux2 <=aux ){
                
                 msj = "Existe una escasez del medicamento, reponer de inmediato";
            }
            else{
                msj= "Entrega registrada con exito";
            }
            
            con.close();
            } catch (Exception e) {
             System.out.println(e);
             
         }  
            return msj;
    }
    
    
    
    public void vencimiento(){
        
        Statement sentencia;
        String  nombre="";
            try {
               
             java.util.Date a = new java.util.Date();   
             long d = a.getTime();
             java.sql.Date fecha = null;
             SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
             formato.format(a);
             
             
                
             con =ConexionDB.getConnection();
             sentencia = con.createStatement();
             res = sentencia.executeQuery ("SELECT Nombre,FechaVencimiento FROM Medicamento");

            while(res.next()){
                nombre = res.getString("Nombre");
                fecha= res.getDate("FechaVencimiento");
                
                
               
                
                System.out.println("comparar"+a);
                if(a.after(sumarRestarDiasFecha(fecha))){
                    System.out.println("true");
                }
                else{
                    System.out.println("false");
                }
                if(a.after(sumarRestarDiasFecha(fecha))){
                JOptionPane.showMessageDialog(null,"El medicamento "+nombre+" esta pronto a vencer");
            }
               
            }
                
            
           
            
            con.close();
            } catch (SQLException | HeadlessException e) {
             System.out.println(e);
             
         }     
        
    }
    
    
    public java.util.Date sumarRestarDiasFecha(Date fecha){
	
        

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(fecha); // Configuramos la fecha que se recibe

        calendar.add(Calendar.DAY_OF_YEAR, -10);  // numero de días a añadir, o restar en caso de días<0

       

        return  calendar.getTime();

 
	
 }
}



