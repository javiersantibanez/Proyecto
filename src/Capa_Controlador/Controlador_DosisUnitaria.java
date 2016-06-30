/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capa_Controlador;

import Capa_Vista.Vista_AgregarDU;
import Capa_Vista.Vista_EditarDU;
import Capa_Vista.Vista_EliminarDU;
import Capa_Vista.Vista_Principal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Franco
 */
public class Controlador_DosisUnitaria {

    private Vista_Principal vPrin;
    private Vista_AgregarDU vAddDU;
    private Vista_EditarDU vEditDU;
    private Vista_EliminarDU vDelDU;
    
    public Controlador_DosisUnitaria(Vista_Principal vPrin, Vista_AgregarDU vAddM, Vista_EditarDU vEditM, Vista_EliminarDU vDelM ) {
        this.vPrin = vPrin;
        this.vAddDU = vAddM;
        this.vEditDU = vEditM;
        this.vDelDU = vDelM;
        this.vAddDU.botonAtras(new Atras());
        this.vEditDU.botonAtras(new Atras());
        this.vDelDU.botonAtras(new Atras());
    }
    
    class Atras implements ActionListener{
        @Override
        /**
         * Este método vuelve hacia la vista principal del programa
         */       
        public void actionPerformed(ActionEvent a) {              
               try{
                   vAddDU.setVisible(false);
                   vEditDU.setVisible(false);
                   vDelDU.setVisible(false);
                   vPrin.setVisible(true);
                  
               }catch(NumberFormatException ex){
                   JOptionPane.showMessageDialog(vPrin, "Error al volver a la pagina principal");
               }
            }
    }
}
