/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capa_Controlador;

import Capa_Modelo.Modelo_Medicamento;
import Capa_Modelo.Modelo_Paciente;
import Capa_Modelo.Reportes;
import Capa_Vista.Vista_AgregarDU;
import Capa_Vista.Vista_AgregarM;
import Capa_Vista.Vista_AgregarP;
import Capa_Vista.Vista_BuscarDU;
import Capa_Vista.Vista_BuscarM;
import Capa_Vista.Vista_BuscarP;
import Capa_Vista.Vista_EditarDU;
import Capa_Vista.Vista_EditarM;
import Capa_Vista.Vista_EditarP;
import Capa_Vista.Vista_EliminarDU;
import Capa_Vista.Vista_EliminarM;
import Capa_Vista.Vista_EliminarP;
import Capa_Vista.Vista_Inventario;
import Capa_Vista.Vista_Principal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Esta es la clase controladora principal del programa, contiene las transiciones de los botones de las vistas
 * Esta clase es parte de la capa controlador 
 * @author Javier Santibáñez,Franco Soto and José Valdivia
 * @version version 1.0
 */
public class Controlador_Principal {  
    /**
        Objetos de las vistas
    */
    // vista principal
    private Vista_Principal vPrin;
    // vista Paciente
    private Vista_EliminarP vDelPac;
    private Vista_EditarP vEditPac;
    private Vista_AgregarP vAddPac;
    private Vista_BuscarP vBusPac;
    // vista Dosis Unitaria
    private Vista_AgregarDU vAddDU;
    private Vista_EliminarDU vDelDU;
    private Vista_EditarDU vEditDU;
    private Vista_BuscarDU vBusDU;
    // vista Medicamento
    private Vista_AgregarM vAddM;
    private Vista_EliminarM vDelM;
    private Vista_EditarM vEditM;
    private Vista_BuscarM vBusM;
    // vista inventario
    private Vista_Inventario vInv;
    
    /**
     * Objetos de la capa modelo
     */
    private Modelo_Medicamento mMed;
    private Reportes mRep;
    
    /*
        Contructor de la clase
    */
    public Controlador_Principal(Vista_Principal vPrin, Vista_AgregarP vAddPac, Vista_EditarP vEditPac, Vista_EliminarP vDelPac,
            Vista_AgregarDU vAddDU,Vista_EliminarDU vDelDU,Vista_EditarDU vEditDU,
            Vista_AgregarM vAddM,Vista_EliminarM vDelM,Vista_EditarM vEditM, Vista_Inventario vInv,
            Vista_BuscarP vBusPac, Vista_BuscarDU vBusDU, Vista_BuscarM vBusM,Modelo_Medicamento mMed,Reportes mRep){
        //Objetos de la vista Paciente
        this.vAddPac = vAddPac;
        this.vDelPac = vDelPac;
        this.vEditPac = vEditPac;
        this.vBusPac = vBusPac;
        //Objetos de la vista Dosis Unitaria
        this.vAddDU = vAddDU;
        this.vDelDU = vDelDU;
        this.vEditDU = vEditDU;
        this.vBusDU = vBusDU;
        //Objetos de la vista Medicamento
        this.vAddM = vAddM;
        this.vDelM = vDelM;
        this.vEditM = vEditM;
        this.vBusM = vBusM;
        //Objetos de la vista Inventario
        this.vInv = vInv;
        //Objetos de la principal
        this.vPrin = vPrin;
        //Modelo
        this.mMed = mMed;
        this.mRep = mRep;
        //Captura los botones de las vistas Paciente
        this.vPrin.botonIngresarPaciente(new AgregarPac());
        this.vPrin.botonEliminarPaciente(new EliminarPac());
        this.vPrin.botonEditarPaciente(new EditarPac());
        this.vPrin.botonBuscarPaciente(new BuscarPac());
        //Captura los botones de las vistas Dosis Unitaria
        this.vPrin.botonIngresarDU(new AgregarDU());
        this.vPrin.botonEliminarDU(new EliminarDU());
        this.vPrin.botonEditarDU(new EditarDU());
        this.vPrin.botonBuscarDU(new BuscarDU());
        //Captura los botones de las vistas Medicamento
        this.vPrin.botonIngresarMedicamento(new AgregarMed());
        this.vPrin.botonEditarMedicamento(new EditarMed());
        this.vPrin.botonEliminarMedicamento(new EliminarMed());
        this.vPrin.botonBuscarMedicamento(new BuscarMed());
        this.vInv.botonAtras(new Atras());
        //Captura los botones de la vista inventario
        this.vPrin.botonVerInventario(new VerInventario());
        //Captura el boton de la vista reportes
        this.vPrin.botonGenerar(new GenerarReportes());
        
    }
    
    class Atras implements ActionListener{
        @Override
        /**
         * Este método vuelve hacia la vista principal del programa
         */       
        public void actionPerformed(ActionEvent a) {              
               try{
                   vInv.vaciarTabla();
                   vInv.setVisible(false);
                   vPrin.setVisible(true);
                  
               }catch(NumberFormatException ex){
                   JOptionPane.showMessageDialog(vPrin, "Error al volver a la pagina principal");
               }
            }
    }
    
    /**
     * Clase Abstracta que captura el boton agregar Paciente de la clase Vista_Principal
    */
    class AgregarPac implements ActionListener{
        @Override
        /**
         * Este método vuelve visible la vista agregar Paciente
         */
        public void actionPerformed(ActionEvent a) {              
               try{
                   vAddPac.setVisible(true);                  
               }catch(NumberFormatException ex){
                  // JOptionPane.showMessageDialog(vPrin, "Error al volver a la pagina principal");
               }
            }
    }
    
    /**
     * Clase Abstracta que captura el boton editar Paciente de la clase Vista_Principal
    */
    class EditarPac implements ActionListener{
        @Override
        /**
         * Este método vuelve visible la vista editar paciente
         */
        public void actionPerformed(ActionEvent a) {              
               try{
                   vEditPac.setVisible(true);                  
               }catch(NumberFormatException ex){
                  // JOptionPane.showMessageDialog(vPrin, "Error al volver a la pagina principal");
               }
            }
    }
    
    /**
     * Clase Abstracta que captura el boton eliminar Paciente de la clase Vista_Principal
    */
    class EliminarPac implements ActionListener{
        @Override
        /**
         * Este método vuelve visible la vista eliminar paciente
         */
        public void actionPerformed(ActionEvent a) {             
               try{
                   vDelPac.setVisible(true);
                  
               }catch(NumberFormatException ex){
                  // JOptionPane.showMessageDialog(vPrin, "Error al volver a la pagina principal");
               }
            }
    }
    
    /**
     * Clase Abstracta que captura el boton Buscar Paciente de la clase Vista_Principal
    */
    class BuscarPac implements ActionListener{
        @Override
        /**
         * Este método vuelve visible la vista eliminar paciente
         */
        public void actionPerformed(ActionEvent a) {             
               try{
                   vBusPac.setVisible(true);
                  
               }catch(NumberFormatException ex){
                  // JOptionPane.showMessageDialog(vPrin, "Error al volver a la pagina principal");
               }
            }
    }
    
    /**
     * Clase Abstracta que captura el boton agregar Dosis Unitaria de la clase Vista_Principal
    */
    class AgregarDU implements ActionListener{
        @Override
        /**
         * Este método vuelve visible la vista agregar Dosis unitaria
         */
        public void actionPerformed(ActionEvent a) {              
               try{
                   vAddDU.setVisible(true);                  
               }catch(NumberFormatException ex){
                  // JOptionPane.showMessageDialog(vPrin, "Error al volver a la pagina principal");
               }
            }
    }
    
    /**
     * Clase Abstracta que captura el boton editar Dosis Unitaria de la clase Vista_Principal
    */
    class EditarDU implements ActionListener{
        @Override
        /**
         * Este método vuelve visible la vista editar Dosis unitaria
         */
        public void actionPerformed(ActionEvent a) {              
               try{
                   vEditDU.setVisible(true);                  
               }catch(NumberFormatException ex){
                  // JOptionPane.showMessageDialog(vPrin, "Error al volver a la pagina principal");
               }
            }
    }
    
    /**
     * Clase Abstracta que captura el boton eliminar Dosis Unitaria de la clase Vista_Principal
    */
    class EliminarDU implements ActionListener{
        @Override
        /**
         * Este método vuelve visible la vista eliminar Dosis unitaria
         */
        public void actionPerformed(ActionEvent a) {             
               try{
                   vDelDU.setVisible(true);
                  
               }catch(NumberFormatException ex){
                  // JOptionPane.showMessageDialog(vPrin, "Error al volver a la pagina principal");
               }
            }
    }
    
    /**
     * Clase Abstracta que captura el boton buscar Dosis Unitaria de la clase Vista_Principal
    */
    class BuscarDU implements ActionListener{
        @Override
        /**
         * Este método vuelve visible la vista eliminar paciente
         */
        public void actionPerformed(ActionEvent a) {             
               try{
                   vBusDU.setVisible(true);
                   vBusDU.limpiarTextField();
                   vBusDU.vaciarTabla();
                  
               }catch(NumberFormatException ex){
                  // JOptionPane.showMessageDialog(vPrin, "Error al volver a la pagina principal");
               }
            }
    }
    
    /**
     * Clase Abstracta que captura el boton agregar Medicamento de la clase Vista_Principal
    */
    
    class AgregarMed implements ActionListener{
        @Override
        /**
         * Este método vuelve visible la vista agregar Medicamento
         */
        public void actionPerformed(ActionEvent a) {              
               try{
                   vAddM.setVisible(true);                  
               }catch(NumberFormatException ex){
                  // JOptionPane.showMessageDialog(vPrin, "Error al volver a la pagina principal");
               }
            }
    }
    
    /**
     * Clase Abstracta que captura el boton editar Medicamento de la clase Vista_Principal
    */
    class EditarMed implements ActionListener{
        @Override
        /**
         * Este método vuelve visible la vista editar Medicamento
         */
        public void actionPerformed(ActionEvent a) {              
               try{
                   vEditM.setVisible(true);                  
               }catch(NumberFormatException ex){
                  // JOptionPane.showMessageDialog(vPrin, "Error al volver a la pagina principal");
               }
            }
    }
    
    /**
     * Clase Abstracta que captura el boton eliminar Medicamento de la clase Vista_Principal
    */
    class EliminarMed implements ActionListener{
        @Override
        /**
         * Este método vuelve visible la vista eliminar Medicamento
         */
        public void actionPerformed(ActionEvent a) {             
               try{
                   vDelM.setVisible(true);
                  
               }catch(NumberFormatException ex){
                  // JOptionPane.showMessageDialog(vPrin, "Error al volver a la pagina principal");
               }
            }
    }
    
    /**
     * Clase Abstracta que captura el boton buscar Medicamento de la clase Vista_Principal
    */
    class BuscarMed implements ActionListener{
        @Override
        /**
         * Este método vuelve visible la vista eliminar paciente
         */
        public void actionPerformed(ActionEvent a) {             
               try{
                   vBusM.setVisible(true);
                  
               }catch(NumberFormatException ex){
                  // JOptionPane.showMessageDialog(vPrin, "Error al volver a la pagina principal");
               }
            }
    }
    
    /**
     * Clase Abstracta que captura el boton ver Inventario de la clase Vista_Principal
    */
    class VerInventario implements ActionListener{
        @Override
        /**
         * Este método vuelve visible la vista ver Inventario
         */
        public void actionPerformed(ActionEvent a) {              
               try{
                   vInv.setVisible(true); 
                   
                   mMed.ConsultaInv(vInv.getTable());
                   
                   
                   
               }catch(NumberFormatException ex){
                  // JOptionPane.showMessageDialog(vPrin, "Error al volver a la pagina principal");
               }catch (Exception ex) {
                Logger.getLogger(Controlador_Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }
    
    /**
     * Clase Abstracta que captura el boton generar de la clase Vista_Principal
    */
    class GenerarReportes implements ActionListener{
        @Override
        /**
         * Este método vuelve visible la vista ver Inventario
         */
        public void actionPerformed(ActionEvent a) {              
               try{
                   
                   if(vPrin.seleccionInventario())mRep.generarReporteInventario();
                   if(vPrin.seleccionConsumoMedicamentos())System.out.println("consumo");
                   if(vPrin.seleccionESMedicamentos())System.out.println("e s medicamentos");
                   if(vPrin.seleccionMermas())System.out.println("mermas");
                   if(vPrin.seleccionVencimiento())System.out.println("vencimiento");
                   System.out.println("");
                   
                   
               }catch(NumberFormatException ex){
                  // JOptionPane.showMessageDialog(vPrin, "Error al volver a la pagina principal");
               }catch (Exception ex) {
                Logger.getLogger(Controlador_Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }
    
    
    
}
