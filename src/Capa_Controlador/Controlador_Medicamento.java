/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capa_Controlador;

import Capa_Modelo.Modelo_Medicamento;
import Capa_Vista.Vista_AgregarM;
import Capa_Vista.Vista_EditarM;
import Capa_Vista.Vista_EliminarM;
import Capa_Vista.Vista_Principal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Franco
 */
public class Controlador_Medicamento {
    
    private Vista_AgregarM vAddM;
    private Vista_EditarM vEditM;
    private Vista_EliminarM vDelM;
    private Modelo_Medicamento mMed;
    private Vista_Principal vPrin;
    
    public Controlador_Medicamento(Vista_Principal vPrin, Vista_AgregarM vAddM, Vista_EditarM vEditM, Vista_EliminarM vDelM,Modelo_Medicamento mMed ){
        this.vPrin = vPrin;
        this.vAddM = vAddM;
        this.vEditM = vEditM;
        this.vDelM = vDelM;
        this.mMed = mMed;
        this.vAddM.botonAtras(new Atras());
        this.vEditM.botonAtras(new Atras());
        this.vDelM.botonAtras(new Atras());
        this.vAddM.botonAgregarMed(new AgregarMedicamento());
        this.vEditM.botonEditarM(new ConsultaMedicamento());
        this.vEditM.botonConsultaM(new ConsultaMedicamento());
        this.vEditM.botonEditarM(new ActualizarMedicamento());
        this.vDelM.botonEliminarM(new EliminarMedicamento());
        
        
        
        
        
    }
    
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
                   vPrin.setVisible(true);
                  
               }catch(NumberFormatException ex){
                   JOptionPane.showMessageDialog(vPrin, "Error al volver a la pagina principal");
               }
            }
    }

    
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
                   JOptionPane.showMessageDialog(null, "Error al ingresar el medicamento");
               }
            }
    }
    
    
    class ConsultaMedicamento implements ActionListener{
        
        /**
         * Este método ...
         */
        
         public void setDatosMedicamento(String [] aux){
            
             vEditM.setDatos(aux[0], aux[1], aux[2], aux[3], aux[4], aux[5], aux[6], aux[7]);
             
        }
         @Override
        public void actionPerformed(ActionEvent a) {
              
               try{
                   //enviar a modelo y set dato
                   
                   setDatosMedicamento(mMed.ConsultaMedicamento(vEditM.getNumeroSerie()));
                   
                   //limpiar texto
                   vEditM.habilitarContenido();
                   
                   
                   
                   
               }catch(NumberFormatException ex){
                        
               }
            }
    }
     
     
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
    
    class BuscarMedicamento implements ActionListener{
        
        /**
         * Este método ...
         */
        
         public void setDatosMedicamento(String [] aux){
            
             vPrin.setDatosBuscarM(aux[0], aux[1], aux[2], aux[3], aux[4], aux[5], aux[6], aux[7],aux[8]);
             
        }
         @Override
        public void actionPerformed(ActionEvent a) {
              
               try{
                   //enviar a modelo y set dato
                   
                   setDatosMedicamento(mMed.ConsultaMedicamento(vEditM.getNumeroSerie()));
                   
                   //limpiar texto
                   vEditM.habilitarContenido();
                   
                   
                   
                   
               }catch(NumberFormatException ex){
                        
               }
            }
    }
  
}
