/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capa_Controlador;

import Capa_Modelo.Modelo_Paciente;
import Capa_Vista.Vista_AgregarP;
import Capa_Vista.Vista_BuscarP;
import Capa_Vista.Vista_EditarP;
import Capa_Vista.Vista_EliminarP;
import Capa_Vista.Vista_Principal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * Esta es la clase controladora de Paciente, contiene las transiciones de los botones de las vistas
 * Esta clase es parte de la capa controlador 
 * @author Javier Santibáñez,Franco Soto and José Valdivia
 * @version version 1.0
 */

public class Controlador_Paciente {
    
    /**
     * Objetos de la capa vista
    */
    private Vista_AgregarP vAddPac;
    private Vista_EditarP vEditPac;
    private Vista_EliminarP vDelPac;
    private Vista_BuscarP vBusPac;
    private Vista_Principal vPrin;
    /**
     *  Objetos de la capa modelo 
     */
    private Modelo_Paciente mPac;
    
    /**
     * Constructor de la clase
     * @param vAddPac objeto de la vista agregar paciente
     * @param vPrin objeto de la vista principal
     * @param mPac objeto del modelo paciente
     * @param vEditPac objeto de la vista editar paciente
     * @param vEliPac objeto de la vista eliminar paciente
     * @param vBusPac objeto de la vista buscar paciente
     */
    public Controlador_Paciente(Vista_AgregarP vAddPac, Vista_Principal vPrin, Modelo_Paciente mPac,
                                Vista_EditarP vEditPac, Vista_EliminarP vEliPac, Vista_BuscarP vBusPac){
        this.vAddPac = vAddPac;
        this.vPrin = vPrin;
        this.mPac = mPac;
        this.vBusPac = vBusPac;
        this.vEditPac = vEditPac;
        this.vDelPac = vEliPac;
        this.vAddPac.botonAtras(new Atras());
        this.vEditPac.botonAtras(new Atras());
        this.vDelPac.botonAtras(new Atras());
        this.vBusPac.botonAtras(new Atras());
        this.vAddPac.botonAceptar(new Aceptar());
        this.vEditPac.botonConsultar(new Consulta());
        this.vEditPac.botonActualizar(new Actualizar());
        this.vDelPac.botonEliminar(new Eliminar());
        this.vBusPac.botonBuscarPaciente(new BuscarPaciente());
    }
    

    /**
     * Clase Abstracta que captura el boton Atras de las vistas Vista_AgregarP, Vista_EditarP y Vista_EliminarP
    */
    class Atras implements ActionListener{
        @Override
        /**
         * Este método vuelve hacia la vista principal del programa
         */
        public void actionPerformed(ActionEvent a) {              
               try{
                   vBusPac.vaciarCampos();
                   vAddPac.limpiarTextField();
                   vEditPac.limpiarTextField();
                   vAddPac.setVisible(false);
                   vEditPac.setVisible(false);
                   vDelPac.setVisible(false);
                   vBusPac.setVisible(false);
                   vPrin.setVisible(true);
                  
               }catch(NumberFormatException ex){
                   JOptionPane.showMessageDialog(vPrin, "Error al volver a la pagina principal");
               }
            }
    }
    
    /**
     * Clase Abstracta que captura el boton Aceptar de la vista Vista_IngresarP
    */
    class Aceptar implements ActionListener{
        @Override
        /**
         * Este método ...
         */
        public void actionPerformed(ActionEvent a) {
              
               try{
                   //enviar a modelo
                   mPac.Ingresarpaciente(vAddPac.getCalle(),vAddPac.getNumero(),vAddPac.getDepto(),vAddPac.getTorre(),vAddPac.getComuna(),
                   vAddPac.getCiudad(),vAddPac.getRegion(),vAddPac.getRut(),vAddPac.getPrimerNombre(),vAddPac.getSegundoNombre(),vAddPac.getApellidoPaterno(),
                   vAddPac.getApellidoMaterno(),vAddPac.getFNacimiento(),vAddPac.getTelefono(),vAddPac.getCorreoElectronico(),vAddPac.getDiagnostico());
                   //limpiar texto
                   vAddPac.limpiarTextField();
                   
               }catch(NumberFormatException ex){
                   JOptionPane.showMessageDialog(vPrin, "Todos los campos deben estar completados");
               }
            }
    }
    
    //boton actualizar para editar
    class Actualizar implements ActionListener{
        @Override
        /**
         * Este método ...
         */
        public void actionPerformed(ActionEvent a) {
              
               try{
                   //enviar a modelo
                   mPac.ActualizarPaciente(vEditPac.getCalle(),vEditPac.getNumero(),vEditPac.getDepto(),vEditPac.getTorre(),vEditPac.getComuna(),
                   vEditPac.getCiudad(),vEditPac.getRegion(),vEditPac.getRut(),vEditPac.getPrimerNombre(),vEditPac.getSegundoNombre(),vEditPac.getApellidoPaterno(),
                   vEditPac.getApellidoMaterno(),vEditPac.getFNacimiento(),vEditPac.getTelefono(),vEditPac.getCorreoElectronico(),vEditPac.getDiagnostico());
                   //limpiar texto
                   vEditPac.limpiarTextField();
                   
                   vEditPac.setVisible(false);
                   
               }catch(NumberFormatException ex){
                   JOptionPane.showMessageDialog(vPrin, "Error al actualizar los datos del paciente");
               }
            }
    }
    
    //Boton consulta para editar
    class Consulta implements ActionListener{
        
        /**
         * Este método ...
         */
        public void setDatosPaciente (String [] aux){
            
            vEditPac.setDatos(aux[0],aux[1],aux[2],aux[3],aux[4],aux[5],aux[7],aux[8],
                              aux[9],aux[10],aux[11],aux[12],aux[13],aux[14],aux[15]);
            
            
        }
        
        public void actionPerformed(ActionEvent a) {
            String [] datos = new String[9]; 
            try{
               //realiza la consulta a db y set datos en vista
               setDatosPaciente (mPac.ConsultaPaciente(vEditPac.getRut()));




               //funcion que habilita el contenido a editar:
               vEditPac.habilitarContenido();
            }
            catch(NumberFormatException ex){
               JOptionPane.showMessageDialog(null, "Error al realizar la consulta");
            }
        }
    }
    
    /**
     * Clase Abstracta que captura el boton eliminar de la vista Vista_EliminarP
    */
    class Eliminar implements ActionListener{
        
        /**
         * Este método ...
         */
 
        
        public void actionPerformed(ActionEvent a) {
            
            try{
               mPac.EliminarPaciente(vDelPac.getRut());
               vDelPac.limpiar();
            }
            catch(NumberFormatException ex){
               JOptionPane.showMessageDialog(vPrin, "Error al eliminar el paciente");
            }
        }
    }
    
    class BuscarPaciente implements ActionListener{
        
        /**
         * Este método ...
         */
        
         public void setDatosMedicamento(String [] aux){
            
             vBusPac.setDatosBuscarP(aux[0],aux[1],aux[2],aux[3],aux[4],aux[5],aux[7],aux[8],
                                    aux[9],aux[10],aux[11],aux[12],aux[13],aux[14],aux[15]);
             
        }
         @Override
        public void actionPerformed(ActionEvent a) {
              
               try{
                   //enviar a modelo y set dato
                   
                   setDatosMedicamento(mPac.ConsultaPaciente(vBusPac.getRut()));
                   
                   //limpiar texto
                   
               }catch(NumberFormatException ex){
                        
               }
            }
    }
}
