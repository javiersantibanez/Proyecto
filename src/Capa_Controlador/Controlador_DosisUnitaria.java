/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capa_Controlador;

import Capa_Modelo.ConexionDB;
import Capa_Modelo.Modelo_DosisUnitaria;
import Capa_Vista.Vista_AgregarDU;
import Capa_Vista.Vista_BuscarDU;
import Capa_Vista.Vista_EditarDU;
import Capa_Vista.Vista_EliminarDU;
import Capa_Vista.Vista_Principal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Esta es la clase controladora de Dosis Unitaria, contiene las transiciones de los botones de las vistas
 * Esta clase es parte de la capa controlador 
 * @author Javier Santibáñez,Franco Soto and José Valdivia
 * @version version 1.0
 */
public class Controlador_DosisUnitaria {
    
    /**
     * objetos de la capa vista
     */
    private Vista_Principal vPrin;
    private Vista_AgregarDU vAddDU;
    private Vista_EditarDU vEditDU;
    private Vista_EliminarDU vDelDU;
    private Vista_BuscarDU vBusDU;
    /**
     * Objeto de la capa modelo
     */
    private Modelo_DosisUnitaria mDU;
    /**
     * Constructor de la clase
     * @param vPrin objeto de la vista principal
     * @param vAddM objeto de la vista agregar medicamento
     * @param vEditM objeto de la vista editar medicamento
     * @param vDelM objeto de la vista eliminar medicamento
     * @param vBusDU objeto de la vista buscar medicamento
     */
    public Controlador_DosisUnitaria(Vista_Principal vPrin, Vista_AgregarDU vAddM, Vista_EditarDU vEditM, Vista_EliminarDU vDelM, Vista_BuscarDU vBusDU, Modelo_DosisUnitaria mDU ) {
        this.vPrin = vPrin;
        this.vAddDU = vAddM;
        this.vEditDU = vEditM;
        this.vDelDU = vDelM;
        this.vBusDU = vBusDU;
        this.mDU = mDU;
        this.vAddDU.botonAtras(new Atras());
        this.vEditDU.botonAtras(new Atras());
        this.vDelDU.botonAtras(new Atras());
        this.vBusDU.botonAtras(new Atras());
        this.vAddDU.botonAceptar(new AgregarDosisUnitaria());
        this.vEditDU.botonConsultaDU(new ConsultaDosisUnitaria());
        this.vEditDU.botonEditarDU(new ActualizarDosisUnitaria());
        this.vDelDU.botonEliminarDosisU(new EliminarDosisUnitaria() );
        this.vBusDU.botonBuscarDU(new BuscarDU());
        this.vPrin.botonEntregarDosis(new EntregarDosisUnitaria());
    }
    /**
     * Clase Abstracta que captura el boton Atras de las vistas Vista_AgregarDU, Vista_EditarDU y Vista_EliminarDU
    */
    class Atras implements ActionListener{
        @Override
        /**
         * Este método vuelve hacia la vista principal del programa
         */       
        public void actionPerformed(ActionEvent a) {              
               try{
                   vBusDU.vaciarTabla();
                   vAddDU.setVisible(false);
                   vEditDU.setVisible(false);
                   vDelDU.setVisible(false);
                   vBusDU.setVisible(false);
                   vPrin.setVisible(true);
                  
               }catch(NumberFormatException ex){
                   JOptionPane.showMessageDialog(vPrin, "Error al volver a la pagina principal");
               }
            }
    }
     /**
     * Clase Abstracta que captura el boton buscar dosis unitaria
    */
    class BuscarDU implements ActionListener{
        
        /**
         * Este método ...
         */

         
        public void actionPerformed(ActionEvent a) {
              
               try{
                  mDU.ConsultaDUxRut(vBusDU.getTable(),vBusDU.getRut());
                   
               }catch(NumberFormatException ex){
                        
               } catch (Exception ex) {
                Logger.getLogger(Controlador_DosisUnitaria.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
    }
    
    class AgregarDosisUnitaria implements ActionListener{
        @Override
        /**
         * Este método ...
         */
        public void actionPerformed(ActionEvent a) {
              
               try{
                   //enviar a modelo
                   mDU.IngresarDosisUnitaria(vAddDU.getRut(),vAddDU.getElaboracion(),vAddDU.getVencimiento(),
                                           vAddDU.getEntrega());
                   //limpiar texto
                   vAddDU.limpiarTextField();
                   
                   //Cerrar ventana
                   vAddDU.setVisible(false);
                   
               }catch(NumberFormatException ex){
                   JOptionPane.showMessageDialog(null, "Error al ingresar el medicamento");
               }
            }
    }
   
    class ConsultaDosisUnitaria implements ActionListener{
        
        /**
         * Este método ...
         */
        public void setDatosDU (String [] aux) throws ParseException{
            int index; 
            SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha_aux;
            Date fecha_aux2;
            Date fecha_aux3;
            fecha_aux= fecha.parse(aux[1]);
            fecha_aux2= fecha.parse(aux[2]);
            fecha_aux3= fecha.parse(aux[3]);
                       
            if(aux[4].equalsIgnoreCase("Disponible")){
                index =1;
            }else{
                index=2;
            }
            
            vEditDU.setDatos(aux[0],fecha_aux,fecha_aux2,fecha_aux3,index);
            
            
        }
        
        public void actionPerformed(ActionEvent a) {
            String [] datos = new String[9]; 
            try{
               //realiza la consulta a db y set datos en vista
               setDatosDU (mDU.ConsultaDosisUnitaria(vEditDU.getIDdosis()));




               //funcion que habilita el contenido a editar:
               vEditDU.habilitarContenido();
            }
            catch(NumberFormatException ex){
               JOptionPane.showMessageDialog(null, "Error al realizar la consulta");
            } catch (ParseException ex) {
                Logger.getLogger(Controlador_DosisUnitaria.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
   
    class ActualizarDosisUnitaria implements ActionListener{
        @Override
        /**
         * Este método ...
         */
        
       
        public void actionPerformed(ActionEvent a) {
              
           if (vEditDU.getStock().equalsIgnoreCase("Selecciona estado")==true) {
               JOptionPane.showMessageDialog(null, "Selecciona un estado del stock");
            }
            else{
                try{
                 mDU.ActualizarDosisUnitaria(vEditDU.getIDdosis(), vEditDU.getRut(),vEditDU.getElaboracion(),vEditDU.getVencimiento(),vEditDU.getLlegada(), vEditDU.getStock());
                 //limpiar texto

                 vEditDU.limpiarTextField();

                 
                 vEditDU.setVisible(false);

                 }catch(NumberFormatException ex){
                     JOptionPane.showMessageDialog(null, "Error al actualizar los datos de la dosis unitaria");
                 }                           
            }
        }
    }
   
    class EliminarDosisUnitaria implements ActionListener{
        
        /**
         * Este método ...
         */   
        @Override
        public void actionPerformed(ActionEvent a) {
            
            try{
               mDU.EliminarDosisUnitaria(vDelDU.getID());
               vDelDU.limpiar();
               vDelDU.setVisible(false);
            }
            catch(NumberFormatException ex){
               JOptionPane.showMessageDialog(vPrin, "Error al eliminar el medicamento");
            }
        }
    }
   
    class EntregarDosisUnitaria implements ActionListener{
        @Override
        /**
         * Este método ...
         */
        
       
        public void actionPerformed(ActionEvent a) {
              
           
           
                try{
                 mDU.EntregarDosisUnitaria(vPrin.getRut(), vPrin.getIDMedicamento());
                 //limpiar texto
                 
                 vPrin.limpiar();
                 


                 }catch(NumberFormatException ex){
                     JOptionPane.showMessageDialog(null, "Error al entregar la dosis unitaria");
                 }                           
            }
        }
}
