/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capa_Modelo;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Franco
 */
public class Reportes {  
    /**
     * Constructor de la clase
     */
    public Reportes() {
        
    }
    
    /**
     * Metodo que retorna la fecha del sistema
     * @return string mes dia año
     */
    public String fechaActual(){
        Calendar fecha = new GregorianCalendar();
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        return mes+"-"+dia+"-"+año;
    }
    /**
     * metodo que retorna la hora del sistema
     * @return string hora minuto segundo
     */
    public String horaActual(){
        Calendar fecha = new GregorianCalendar();
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);
        return hora+"-"+minuto+"-"+segundo;
    }
    
    public void generarReporteInventario() throws SQLException, DocumentException, FileNotFoundException{
        Connection con ;
        ResultSet res;
        Statement sentencia;
        Document documento = new Document(PageSize.A4);
        con = ConexionDB.GetConnection();
        sentencia=con.createStatement();
        res=sentencia.executeQuery("SELECT Cantidad, Cantidadmin, Cantidadmax, Medicamento.Nombre, Medicamento.FechaElaboracion, Medicamento.FechaVencimiento, Medicamento.Laboratorio FROM Inventario inner join Medicamento on Inventario.ID_Medicamento = Medicamento.ID_Medicamento "); 
        PdfWriter.getInstance(documento, new FileOutputStream("reportes/reporte_de_inventario "+fechaActual()+" "+horaActual()+".pdf"));
        documento.open();
        float[] columnWidths = {2,2,2,2,2,2,2};
        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100);
        table.getDefaultCell().setUseAscender(true);
        table.getDefaultCell().setUseDescender(true);
        Font f = new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL, GrayColor.GRAYWHITE);
        PdfPCell cell = new PdfPCell(new Phrase("Reporte de Medicamentos", f));
        cell.setBackgroundColor(GrayColor.GRAYBLACK);
        cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        cell.setColspan(7);
        table.addCell(cell);
        table.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
        for (int i = 0; i < 2; i++) {
            table.addCell("Nombre");
            table.addCell("Laboratorio");
            table.addCell("Fecha de Elaboracion");
            table.addCell("Fecha de Vencimiento");
            table.addCell("Cantidad minima");
            table.addCell("Cantidad actual");
            table.addCell("Cantidad maxima");
        }
        table.setHeaderRows(3);
        table.setFooterRows(1);
        table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
        table.getDefaultCell().setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        while ( res.next() ) {
                table.addCell(res.getString("Nombre"));
                table.addCell(res.getString("Laboratorio"));
                table.addCell(res.getString("FechaElaboracion"));
                table.addCell(res.getString("FechaVencimiento"));
                table.addCell(res.getString("Cantidadmin"));
                table.addCell(res.getString("Cantidad"));
                table.addCell(res.getString("Cantidadmax"));
        }
        documento.add(table);
        documento.close();
    }  
}
