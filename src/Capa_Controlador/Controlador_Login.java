/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capa_Controlador;

import Capa_Modelo.Modelo_Medicamento;
import Capa_Modelo.Modelo_Paciente;
import Capa_Modelo.Modelo_DosisUnitaria;
import Capa_Vista.*;

/**
 * Esta es la clase principal del programa, contiene los objetos de las clases e inicia la vista login
 * Esta clase es parte de la capa controlador 
 * @author Javier Santibáñez,Franco Soto and José Valdivia
 * @version version 1.0
 */
public class Controlador_Login {

    public static void main(String[] args) {
        /**
         * Objetos de la capa Vista
         */
        //Objetos de la Vista Paciente
            Vista_Principal farmAD = new Vista_Principal();
            Vista_AgregarP addPac = new Vista_AgregarP();
            Vista_EditarP editPac = new Vista_EditarP();
            Vista_EliminarP delPac = new Vista_EliminarP();
            Vista_BuscarP busPac = new Vista_BuscarP();
        //Objetos de la Vista Dosis Unitaria
            Vista_AgregarDU addDU = new Vista_AgregarDU();
            Vista_EditarDU editDU = new Vista_EditarDU();
            Vista_EliminarDU delDU = new Vista_EliminarDU();
            Vista_BuscarDU busDU = new Vista_BuscarDU();
        //Objetos de la Vista Medicamentos
            Vista_AgregarM addMed = new Vista_AgregarM();
            Vista_EditarM editMed = new Vista_EditarM();
            Vista_EliminarM delMed = new Vista_EliminarM();
            Vista_BuscarM busMed = new Vista_BuscarM();
        //Otros objetos de Vista
            Vista_Inventario inventario = new Vista_Inventario();
        /**
         * Objetos de la capa Modelo
         */
            Modelo_Paciente mPac = new Modelo_Paciente();
            Modelo_Medicamento mMed = new Modelo_Medicamento();
            Modelo_DosisUnitaria mDU = new Modelo_DosisUnitaria();
        /**
         * Objetos de la capa Controlador
         */
            Controlador_Paciente cPac = new Controlador_Paciente(addPac, farmAD, mPac, editPac, delPac, busPac);
            Controlador_Principal cPrin = new Controlador_Principal(farmAD, addPac, editPac, delPac, addDU, delDU, editDU, addMed, delMed, editMed, inventario, busPac,busDU,busMed);
            Controlador_Medicamento cMed = new Controlador_Medicamento(farmAD, addMed, editMed, delMed,mMed, busMed);
            Controlador_DosisUnitaria cDU = new Controlador_DosisUnitaria(farmAD, addDU, editDU, delDU, busDU,mDU);
        /**
         * Iniciar la vista principal
         */
            farmAD.setVisible(true);
    }
}
