/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capa_Controlador;

import Capa_Modelo.Modelo_Paciente;
import Capa_Vista.Vista_AgregarDU;
import Capa_Vista.Vista_AgregarM;
import Capa_Vista.Vista_AgregarP;
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
    // vista Dosis Unitaria
    private Vista_AgregarDU vAddDU;
    private Vista_EliminarDU vDelDU;
    private Vista_EditarDU vEditDU;
    // vista Medicamento
    private Vista_AgregarM vAddM;
    private Vista_EliminarM vDelM;
    private Vista_EditarM vEditM;
    // vista inventario
    private Vista_Inventario vInv;
    /*
        Contructor de la clase
    */
    public Controlador_Principal(Vista_Principal vPrin, Vista_AgregarP vAddPac, Vista_EditarP vEditPac, Vista_EliminarP vDelPac,
            Vista_AgregarDU vAddDU,Vista_EliminarDU vDelDU,Vista_EditarDU vEditDU,
            Vista_AgregarM vAddM,Vista_EliminarM vDelM,Vista_EditarM vEditM, Vista_Inventario vInv
    ){
        //Objetos de la vista Paciente
        this.vAddPac = vAddPac;
        this.vDelPac = vDelPac;
        this.vEditPac = vEditPac;
        //Objetos de la vista Dosis Unitaria
        this.vAddDU = vAddDU;
        this.vDelDU = vDelDU;
        this.vEditDU = vEditDU;
        //Objetos de la vista Medicamento
        this.vAddM = vAddM;
        this.vDelM = vDelM;
        this.vEditM = vEditM;
        //Objetos de la vista Inventario
        this.vInv = vInv;
        //Objetos de la principal
        this.vPrin = vPrin;
        //Captura los botones de las vistas Paciente
        this.vPrin.botonIngresarPaciente(new AgregarPac());
        this.vPrin.botonEliminarPaciente(new EliminarPac());
        this.vPrin.botonEditarPaciente(new EditarPac());
        //Captura los botones de las vistas Dosis Unitaria
        this.vPrin.botonIngresarDU(new AgregarDU());
        this.vPrin.botonEliminarDU(new EliminarDU());
        this.vPrin.botonEditarDU(new EditarDU());
        //Captura los botones de las vistas Medicamento
        this.vPrin.botonIngresarMedicamento(new AgregarMed());
        this.vPrin.botonEditarMedicamento(new EditarMed());
        this.vPrin.botonEliminarMedicamento(new EliminarMed());
        //Captura los botones de la vista inventario
        this.vPrin.botonVerInventario(new VerInventario());
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
               }catch(NumberFormatException ex){
                  // JOptionPane.showMessageDialog(vPrin, "Error al volver a la pagina principal");
               }
            }
    }
    
}
