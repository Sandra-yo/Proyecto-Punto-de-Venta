/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Conexion_Base_datos.ReporteBD;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author sandra
 */
public class Reportes extends JFrame {

    JPanel pan1, pan2, pan3;
    JButton btn, btn1, btn2, btn3, pdf, excel;
    JTextField usuario;
    JDateChooser fInicio, fFinal;
    JLabel fechaI, fechaF;
    JTable reporte;
    JTabbedPane pestaña;
    ReporteBD r;
    Principal p;

    public Reportes() {
        super("Reportes");
        setLayout(new BorderLayout());
        this.setSize(800, 300);
        //paneles
        pan1 = new JPanel();
        pan1.setLayout(new FlowLayout());

        pan2 = new JPanel();
        pan2.setLayout(new FlowLayout());

        pan3 = new JPanel();
        pan3.setLayout(new FlowLayout());

        r = new ReporteBD();
        r.agregarColumnas();
        reporte = new JTable(r.LlenarTabla("select  usuario, fecha, precio_con_iva,id_person_fk  from TransxPerson tp join transaccion t "));

        p = new Principal();

        pestaña = new JTabbedPane();
        btn = new JButton("Producto");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fInicio.setEnabled(false);
                fFinal.setEnabled(false);
                usuario.setEnabled(true);

            }
        });
        btn1 = new JButton("Usuario");

        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fInicio.setEnabled(false);
                fFinal.setEnabled(false);
                usuario.setEnabled(true);
                

            }
        });
        btn2 = new JButton("Periodo");
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usuario.setEnabled(false);
                fInicio.setEnabled(true);
                fFinal.setEnabled(true);

            }
        });
        btn3 = new JButton("Buscar");
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(usuario.getText()==""){
                    
                }else{
                    System.out.println("....");
                    borrarTabla();
               reporte= new JTable(r.LlenarTabla("select  usuario, fecha, precio_con_iva,id_person_fk  from TransxPerson tp join transaccion t where tp.id_transaccion_fk= t.id_transaccion and usuario="+usuario.getText()));
                }
            }
        });

        pdf = new JButton("PDF");
        pdf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ruta = dameRuta();
                try {
                    crearPDF(ruta);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DocumentException ex) {
                    Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        excel = new JButton("Excel");
        excel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ruta = dameRuta();
                try {
                    crearExcel(generaMatriz(), ruta);
                } catch (WriteException ex) {
                    Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        usuario = new JTextField(8);
        fechaI = new JLabel("Inicio:");
        fechaF = new JLabel("Final:");
        fInicio = new JDateChooser();
        fInicio.setDateFormatString("dd-MM-yyyy");
        fFinal = new JDateChooser();
        fFinal.setDateFormatString("dd-MM-yyyy");

        usuario.setEnabled(false);
        fInicio.setEnabled(false);
        fFinal.setEnabled(false);

        //panel 1
        pan1.add(btn);
        pan1.add(btn1);
        pan1.add(btn2);
        pan1.add(usuario);
        pan1.add(fechaI);
        pan1.add(fInicio);
        pan1.add(fechaF);
        pan1.add(fFinal);
        pan1.add(btn3);
        add(pan1, BorderLayout.NORTH);

        //panel 2
        reporte.setPreferredScrollableViewportSize(new Dimension(500, 200));
        JScrollPane scroll = new JScrollPane(reporte);
        pan2.add(scroll);

        add(pan2, BorderLayout.CENTER);

        //panel 3
        pan3.add(pdf);
        pan3.add(excel);
        add(pan3, BorderLayout.SOUTH);
    }

    private String dameRuta() {
        String file = "";
        try {
            JFileChooser dlg = new JFileChooser();
            int opcion = dlg.showSaveDialog(this);
            if (opcion == JFileChooser.APPROVE_OPTION) {
                File f = dlg.getSelectedFile();
                file = f.toString();
            } else {
                System.out.println("ninguna ruta asignada");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public void crearPDF(String arch) throws FileNotFoundException, DocumentException {
        FileOutputStream file = new FileOutputStream(arch + ".pdf");
        Document documento = new Document();

        PdfWriter.getInstance(documento, file);
        PdfPTable tabla = new PdfPTable(4);

        documento.open();

        tabla.addCell("Id usuario");
        tabla.addCell("Fecha de compra");
        tabla.addCell("Total");
        tabla.addCell("Cliente");

        for (int i = 0; i < reporte.getRowCount(); i++) {
            String contenidoT = "";
            for (int j = 0; j < reporte.getColumnCount(); j++) {

                contenidoT = contenidoT + reporte.getValueAt(i, j);
                tabla.addCell(contenidoT);

                contenidoT = "";
            }
            contenidoT = "";
        }
        documento.add(new Paragraph("Fecha de creacion del reporte: " + p.fecha_act.getText()));
        documento.add(new Paragraph(""));
        documento.add(new Paragraph(""));
        documento.add(new Paragraph(""));
        documento.addCreator("Sandra Teresa González Valadez");
        documento.add(tabla);

        documento.close();
        JOptionPane.showMessageDialog(null, "PDF correctamente creado");

    }

    public String[][] generaMatriz() {
        int filas = reporte.getRowCount();
        int columnas = reporte.getColumnCount();
        String m[][] = new String[filas + 3][columnas];

        m[0][0] = "Id usuario";
        m[0][1] = "Fecha de Compra";
        m[0][2] = "total";
        m[0][3] = "Cliente";
        //System.out.println(filas);
        int k = 1;
        for (int i = 0; i < filas; i++) {

            m[k][0] = Integer.toString((int) r.dtm.getValueAt(i, 0));
            m[k][1] = Integer.toString((int) r.dtm.getValueAt(i, 1));
            m[k][2] = Integer.toString((int) r.dtm.getValueAt(i, 2));
            m[k][3] = Integer.toString((int) r.dtm.getValueAt(i, 3));
            System.out.println(m[1][0]);

        }

        return m;
    }
   public void borrarTabla() {
        do {
            r.dtm.removeRow(r.dtm.getRowCount() - 1);
        } while (r.dtm.getRowCount() != 0);

    }
    public void crearExcel(String[][] matriz, String ruta) throws WriteException {
        try {

            WorkbookSettings conf = new WorkbookSettings();
            conf.setEncoding("ISO-8859-1");

            WritableWorkbook woorkBook = Workbook.createWorkbook(new File(ruta + ".xls"), conf);

            WritableSheet sheet = woorkBook.createSheet("Resultado", 0);
            WritableFont b = new WritableFont(WritableFont.COURIER, 9, WritableFont.NO_BOLD);
            WritableCellFormat hFormat = new WritableCellFormat(b);

            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[i].length; j++) {

                    sheet.addCell(new jxl.write.Label(j, i, matriz[i][j], hFormat));
                }
            }

            woorkBook.write();
            woorkBook.close();
            JOptionPane.showMessageDialog(null, "Excell generado correctamente");

        } catch (IOException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
