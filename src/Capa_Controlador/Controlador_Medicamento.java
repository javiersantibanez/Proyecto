/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capa_Controlador;

import Capa_Modelo.Modelo_Medicamento;
import Capa_Vista.Vista_AgregarM;
import Capa_Vista.Vista_BuscarM;
import Capa_Vista.Vista_EditarM;
import Capa_Vista.Vista_EliminarM;
import Capa_Vista.Vista_Inventario;
import Capa_Vista.Vista_Principal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Esta es la clase controladora de Medicamento, contiene las transiciones de los botones de las vistas
 * Esta clase es parte de la capa controlador 
 * @author Javier Santibáñez,Franco Soto and José Valdivia
 * @version version 1.0
 */
public class Controlador_Medicamento {
    /**
     * Objetos de la capa vista
     */
    private Vista_AgregarM vAddM;
    private Vista_EditarM vEditM;
    private Vista_EliminarM vDelM;
    private Vista_BuscarM vBusM;    
    private Vista_Principal vPrin;
    
    /**
     * objeto de la capa modelo
     */
    private Modelo_Medicamento mMed;
    /**
     * Constructor de la clase
     * @param vPrin objeto de la vista principal
     * @param vAddM objeto de la vista agregar medicamento
     * @param vEditM objeto de la vista editar medicamento
     * @param vDelM objeto de la vista eliminar medicamento
     * @param mMed objeto del modelo medicamento
     * @param vBusM  objeto de la vista buscar medicamento
     */
    public Controlador_Medicamento(Vista_Principal vPrin, Vista_AgregarM vAddM, Vista_EditarM vEditM, Vista_EliminarM vDelM,Modelo_Medicamento mMed, Vista_BuscarM vBusM){
        this.vPrin = vPrin;
        this.vAddM = vAddM;
        this.vEditM = vEditM;
        this.vDelM = vDelM;
        this.mMed = mMed;
        this.vBusM = vBusM;
        this.vAddM.botonAtras(new Atras());
        this.vEditM.botonAtras(new Atras());
        this.vDelM.botonAtras(new Atras());
        this.vBusM.botonAtras(new Atras());
        this.vAddM.botonAgregarMed(new AgregarMedicamento());
        this.vEditM.botonEditarM(new ConsultaMedicamento());
        this.vEditM.botonConsultaM(new ConsultaMedicamento());
        this.vEditM.botonEditarM(new ActualizarMedicamento());
        this.vDelM.botonEliminarM(new EliminarMedicamento());
        this.vBusM.botonBuscarMedicamento(new BuscarMedicamento());
        this.vPrin.botonEntregarMedicamento(new EntregarMedicamento());
        
    }
    
    /**
     * Clase Abstracta que captura el boton Atras de las vistas Vista_AgregarM, Vista_EditarM y Vista_EliminarM
    */
    class Atras implements ActionListener{
        @Override
        /**
         * Este método vuelve hacia la vista principal del programa
         */       
        public void actionPerformed(ActionEvent a) {              
               try{
                   vAddM.setVisible(false);
                   vEditM.setVisible(false);
                   vDelM.setVisible(false);
                   vBusM.setVisible(false);
                   vPrin.setVisible(true);
                  
               }catch(NumberFormatException ex){
                   JOptionPane.showMessageDialog(vPrin, "Error al volver a la pagina principal");
               }
            }
    }

    /**
     * Clase Abstracta que captura el boton agregar medicamento
    */
    class AgregarMedicamento implements ActionListener{
        @Override
        /**
         * Este método ...
         */
        public void actionPerformed(ActionEvent a) {
              
               try{
                   //enviar a modelo
                   mMed.IngresarMedicamento(vAddM.getNumeroSerie(),vAddM.getNombre(),vAddM.getActivo(),vAddM.getLab(),
                                            vAddM.getAdmin(),vAddM.getElaboracion(),vAddM.getVencimiento(),vAddM.getLlegada(),
                                            vAddM.getComposicion());
                   //limpiar texto
                   vAddM.limpiar();
                   
                   //Cerrar ventana
                   vAddM.setVisible(false);
                   
               }catch(NumberFormatException ex){
                   JOptionPane.showMessageDialog(null, ex);
               }
            }
    }
    
    /**
     * Clase Abstracta que captura el boton consultar medicamento
    */
    class ConsultaMedicamento implements ActionListener{
        
        /**
         * Este método ...
         */
        
         public void setDatosMedicamento(String [] aux) throws ParseException{
            
             SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha_aux;
            Date fecha_aux2;
            Date fecha_aux3;
            fecha_aux= fecha.parse(aux[4]);
            fecha_aux2= fecha.parse(aux[5]);
            fecha_aux3= fecha.parse(aux[6]);
            
             
             vEditM.setDatos(aux[0], aux[1], aux[2], aux[3], fecha_aux, fecha_aux2, fecha_aux3, aux[7]);
             
        }
         @Override
        public void actionPerformed(ActionEvent a) {
              
               try{
                   //enviar a modelo y set dato
                   
                   setDatosMedicamento(mMed.ConsultaMedicamento(vEditM.getNumeroSerie()));
                   
                   //limpiar texto
                   vEditM.habilitarContenido();
                   
                   
                   
                   
               }catch(NumberFormatException ex){
                        
               } catch (ParseException ex) {
                 Logger.getLogger(Controlador_Medicamento.class.getName()).log(Level.SEVERE, null, ex);
             }
            }
    }
     
    /**
     * Clase Abstracta que captura el boton actualizar medicamento
    */
    class ActualizarMedicamento implements ActionListener{
        @Override
        /**
         * Este método ...
         */
        
       
        public void actionPerformed(ActionEvent a) {
              
               try{
                   //enviar a modelo
                   mMed.ActualizarMedicamento(vEditM.getNumeroSerie(),vEditM.getNombre(),vEditM.getActivo(), vEditM.getLab(),vEditM.getAdmin(),
                                              vEditM.getElaboracion(),vEditM.getVencimiento(),vEditM.getLlegada(),vEditM.getComposicion());
                   //limpiar texto
                   vEditM.limpiarTextField();
                   
                   
                   vEditM.setVisible(false);
                   
               }catch(NumberFormatException ex){
                   JOptionPane.showMessageDialog(null, "Error al actualizar los datos del medicamento");
               }
            }
    }
    
    /**
     * Clase Abstracta que captura el boton eliminar medicamento
    */
    class EliminarMedicamento implements ActionListener{
        
        /**
         * Este método ...
         */
 
        
        public void actionPerformed(ActionEvent a) {
            
            try{
               mMed.EliminarMedicamento(vDelM.getNserie());
               vDelM.limpiar();
               vDelM.setVisible(false);
            }
            catch(NumberFormatException ex){
               JOptionPane.showMessageDialog(vPrin, "Error al eliminar el medicamento");
            }
        }
    }
    
    /**
     * Clase Abtracta que solicita datos a la DB
     */
    class BuscarMedicamento implements ActionListener{
        
        /**
         * Este método ...
         */
        
         public void setDatosMedicamento(String [] aux){
            
             vBusM.setDatosBuscarM(aux[0], aux[1], aux[2], aux[3], aux[4], aux[5], aux[6], aux[7],aux[8]);
             
        }
         @Override
        public void actionPerformed(ActionEvent a) {
              
               try{
                   //enviar a modelo y set dato
                   
                   setDatosMedicamento(mMed.ConsultaMedicamento(vBusM.getNumeroSerie()));
                   
                   //limpiar texto
                   
               }catch(NumberFormatException ex){
                        
               }
            }
    }
  
    class EntregarMedicamento implements ActionListener{
        
        
        public void actionPerformed(ActionEvent a){
            try{
                System.out.println("Mandando a modelo");
                mMed.EntregarMedicamento(vPrin.getRutEntregarMedicamento(), vPrin.getIdDosis());
                System.out.println("Regresando de modelo");
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(vPrin, "Error al entregar el medicamento");
            }
        }
    }
}
