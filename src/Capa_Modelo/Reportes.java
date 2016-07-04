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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

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
        return año+"-"+mes+"-"+dia;
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
    public boolean vencido(Date fecha){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date fechasistema = sdf.parse(fechaActual());
            return fechasistema.after(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public void generarReporteInventario(){
        Connection con ;
        ResultSet res;
        Statement sentencia;
        Document documento = new Document(PageSize.A4);
        con = ConexionDB.getConnection();
        try {
            sentencia=con.createStatement();
            res=sentencia.executeQuery("SELECT Cantidad, Cantidadmin,  Cantidadmax, Medicamento.Nombre, Medicamento.FechaElaboracion, Medicamento.Composicion, Medicamento.FechaVencimiento, Medicamento.Laboratorio FROM Inventario inner join Medicamento on Inventario.ID_Medicamento = Medicamento.ID_Medicamento "); 
            PdfWriter.getInstance(documento, new FileOutputStream("reportes/reporte_de_inventario "+fechaActual()+" "+horaActual()+".pdf"));
            documento.open();
            float[] columnWidths = {2,2,2,2,2,2,2,2};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            table.getDefaultCell().setUseAscender(true);
            table.getDefaultCell().setUseDescender(true);
            Font f = new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL, GrayColor.GRAYWHITE);
            PdfPCell cell = new PdfPCell(new Phrase("Reporte de Medicamentos", f));
            cell.setBackgroundColor(GrayColor.GRAYBLACK);
            cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            cell.setColspan(8);
            table.addCell(cell);
            table.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
            for (int i = 0; i < 2; i++) {
                table.addCell("Medicamento");
                table.addCell("Composicion");
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
                    table.addCell(res.getString("Composicion"));
                    table.addCell(res.getString("Laboratorio"));
                    table.addCell(res.getString("FechaElaboracion"));
                    table.addCell(res.getString("FechaVencimiento"));
                    table.addCell(res.getString("Cantidadmin"));
                    table.addCell(res.getString("Cantidad"));
                    table.addCell(res.getString("Cantidadmax"));
            }
            documento.add(table);
            documento.close();
            JOptionPane.showMessageDialog(null, "Reporte de Inventario generado correctamente");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos");
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error al generar la ruta del archivo");
        } catch (DocumentException ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el archivo");
        }
        
    }
    public void generarReporteVencimiento(){
        Connection con ;
        ResultSet res;
        Statement sentencia;
        Document documento = new Document(PageSize.A4);
        con = ConexionDB.getConnection();       
        try{
            sentencia=con.createStatement();
            res=sentencia.executeQuery("SELECT Cantidad, Cantidadmin, Cantidadmax, Medicamento.Nombre, Medicamento.FechaElaboracion, Medicamento.FechaVencimiento, Medicamento.Laboratorio FROM Inventario inner join Medicamento on Inventario.ID_Medicamento = Medicamento.ID_Medicamento "); 
            PdfWriter.getInstance(documento, new FileOutputStream("reportes/reporte_de_vencimiento "+fechaActual()+" "+horaActual()+".pdf"));
            documento.open();
            float[] columnWidths = {2,2,2,2};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            table.getDefaultCell().setUseAscender(true);
            table.getDefaultCell().setUseDescender(true);
            Font f = new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL, GrayColor.GRAYWHITE);
            PdfPCell cell = new PdfPCell(new Phrase("Reporte de Vencimiento", f));
            cell.setBackgroundColor(GrayColor.GRAYBLACK);
            cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            cell.setColspan(4);
            table.addCell(cell);
            table.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
            for (int i = 0; i < 2; i++) {
                table.addCell("Medicamento");
                table.addCell("Laboratorio");
                table.addCell("Fecha de Vencimiento");
                table.addCell("Cantidad actual");
            }
            table.setHeaderRows(3);
            table.setFooterRows(1);
            table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
            table.getDefaultCell().setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            while ( res.next() ) {
                    if(vencido(res.getDate("FechaVencimiento"))){
                        table.addCell(res.getString("Nombre"));
                        table.addCell(res.getString("Laboratorio"));
                        table.addCell(res.getString("FechaVencimiento"));
                        table.addCell(res.getString("Cantidad"));
                    }
            }
            documento.add(table);
            documento.close();        
            JOptionPane.showMessageDialog(null, "Reporte de Vencimiento generado correctamente");
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error en conectar con base de datos");
        } catch (DocumentException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, "El documento no se pudo generar");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, "EL archivo no se abrió");
        }
    }
    
    public void generarReporteConsumoMedicamentos(){
        DecimalFormat df = new DecimalFormat("####0.00");
        Connection con;
        ResultSet res;
        Statement sentencia;
        Document documento = new Document(PageSize.A4);
        double consumo,cantidad;
        double porcentaje;
        consumo=cantidad=0;
        con = ConexionDB.getConnection();
        try {
            sentencia=con.createStatement();
            res=sentencia.executeQuery("SELECT Medicamento.Nombre, Medicamento.Composicion, Medicamento.Laboratorio, Inventario.Cantidad, SUM(MedicinaPaciente.Cantidad) as Consumo FROM MedicinaPaciente INNER JOIN Medicamento ON MedicinaPaciente.ID_Medicamento = Medicamento.ID_Medicamento INNER JOIN Inventario ON MedicinaPaciente.ID_Medicamento = Inventario.ID_Medicamento  GROUP BY Medicamento.Nombre, Medicamento.Composicion , Medicamento.Laboratorio, Inventario.Cantidad ORDER BY Consumo"); 
            PdfWriter.getInstance(documento, new FileOutputStream("reportes/reporte_de_consumo_medicamentos "+fechaActual()+" "+horaActual()+".pdf"));
            documento.open();
            float[] columnWidths = {2,2,2,2,2,2};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            table.getDefaultCell().setUseAscender(true);
            table.getDefaultCell().setUseDescender(true);
            Font f = new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL, GrayColor.GRAYWHITE);
            PdfPCell cell = new PdfPCell(new Phrase("Reporte de Consumo Medicamentos", f));
            cell.setBackgroundColor(GrayColor.GRAYBLACK);
            cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            cell.setColspan(8);
            table.addCell(cell);
            table.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
            for (int i = 0; i < 2; i++) {
                table.addCell("Medicamento");
                table.addCell("Composicion");
                table.addCell("Laboratorio");
                table.addCell("Cantidad");
                table.addCell("Unidades consumidas");
                table.addCell("Porcentaje de consumo");
            }
            table.setHeaderRows(3);
            table.setFooterRows(1);
            table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
            table.getDefaultCell().setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            while ( res.next() ) {
                    consumo=res.getInt("Consumo");
                    cantidad=res.getInt("Cantidad");
                    porcentaje=consumo/cantidad*100;
                    System.out.println("consumo: "+consumo+" cantidad: "+cantidad+" porcentaje: "+porcentaje);
                    table.addCell(res.getString("Nombre"));
                    table.addCell(res.getString("Composicion"));
                    table.addCell(res.getString("Laboratorio"));
                    table.addCell(res.getString("Cantidad"));
                    table.addCell(res.getString("Consumo"));
                    table.addCell(((df.format(porcentaje)))+"%");
                    
            }
            documento.add(table);
            documento.close();
            JOptionPane.showMessageDialog(null, "Reporte de Consumo de Medicamentos generado correctamente");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos");
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error al generar la ruta del archivo");
        } catch (DocumentException ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el archivo");
        }
    }
    
    public void generarReporteESMedicamentos(){
        Connection con;
        ResultSet res,res2;
        Statement sentencia;
        Document documento = new Document(PageSize.A4);
        con = ConexionDB.getConnection();
        try {
            sentencia=con.createStatement();
            res=sentencia.executeQuery("SELECT Medicamento.Nombre, Medicamento.Composicion, Medicamento.Laboratorio, Medicamento.FechaLlegada, Inventario.Cantidad FROM Medicamento INNER JOIN Inventario ON Medicamento.ID_Medicamento = Inventario.ID_Medicamento ORDER BY Inventario.Cantidad, Medicamento.Nombre desc"); 
            PdfWriter.getInstance(documento, new FileOutputStream("reportes/reporte_de_entrada-salida_medicamentos "+fechaActual()+" "+horaActual()+".pdf"));
            documento.open();
            float[] columnWidths = {2,2,2,2,2};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            table.getDefaultCell().setUseAscender(true);
            table.getDefaultCell().setUseDescender(true);
            Font f = new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL, GrayColor.GRAYWHITE);
            PdfPCell cell = new PdfPCell(new Phrase("Reporte de Entrada de Medicamentos", f));
            cell.setBackgroundColor(GrayColor.GRAYBLACK);
            cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            cell.setColspan(8);
            table.addCell(cell);
            table.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
            for (int i = 0; i < 2; i++) {
                table.addCell("Medicamento");
                table.addCell("Composicion");
                table.addCell("Laboratorio");
                table.addCell("Fecha de Entrada");
                table.addCell("Cantidad");
            }
            table.setHeaderRows(3);
            table.setFooterRows(1);
            table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
            table.getDefaultCell().setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            while ( res.next() ) {
                    table.addCell(res.getString("Nombre"));
                    table.addCell(res.getString("Composicion"));
                    table.addCell(res.getString("Laboratorio"));
                    table.addCell(res.getString("FechaLlegada"));
                    table.addCell(res.getString("Cantidad"));
            }
            documento.add(table);
            documento.newPage();
            res2=sentencia.executeQuery("SELECT Medicamento.Nombre, Medicamento.Composicion, Medicamento.Laboratorio, MedicinaPaciente.FechaEntrega, SUM(MedicinaPaciente.Cantidad) AS Entregados FROM Medicamento INNER JOIN MedicinaPaciente ON Medicamento.ID_Medicamento = MedicinaPaciente.ID_Medicamento  group by Medicamento.Nombre, Medicamento.Composicion, Medicamento.Laboratorio,MedicinaPaciente.FechaEntrega ORDER BY Entregados"); 
            float[] columnWidths2 = {2,2,2,2,2};
            PdfPTable table2 = new PdfPTable(columnWidths);            
            table2.setWidthPercentage(100);
            table2.getDefaultCell().setUseAscender(true);
            table2.getDefaultCell().setUseDescender(true);
            PdfPCell cell2 = new PdfPCell(new Phrase("Reporte de Salida de Medicamentos", f));
            cell2.setBackgroundColor(GrayColor.GRAYBLACK);
            cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            cell2.setColspan(8);
            table2.addCell(cell2);
            table2.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
            for (int i = 0; i < 2; i++) {
                table2.addCell("Medicamento");
                table2.addCell("Composicion");
                table2.addCell("Laboratorio");
                table2.addCell("Fecha de Salida");
                table2.addCell("Cantidad");
            }
            table2.setHeaderRows(3);
            table2.setFooterRows(1);
            table2.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
            table2.getDefaultCell().setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            while ( res2.next() ) {
                    table2.addCell(res2.getString("Nombre"));
                    table2.addCell(res2.getString("Composicion"));
                    table2.addCell(res2.getString("Laboratorio"));
                    table2.addCell(res2.getString("FechaEntrega"));
                    table2.addCell(res2.getString("Entregados"));
            }
            documento.add(table2);
            documento.close();
            JOptionPane.showMessageDialog(null, "Reporte de Entrada y Salida de Medicamentos generado correctamente");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos");
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error al generar la ruta del archivo");
        } catch (DocumentException ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el archivo");
        }
    }
    
}
