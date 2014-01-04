package pdf;

import java.io.FileOutputStream;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDF {

	/**
	 * Autores Pallas, Martin, Recuenco
	 */
	public static void main(String[] args) {
		Document document = new Document();

        try {
            PdfWriter.getInstance(document,
                new FileOutputStream("tabla2.pdf"));
    
            document.open(); //Abre el documento
            
            //añadir el titulo
            Paragraph titulo = new Paragraph();

            titulo.setSpacingAfter(25);
            titulo.setSpacingBefore(25);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setIndentationLeft(50);
            titulo.setIndentationRight(50);
            Font fontTitulo = new Font(Font.FontFamily.HELVETICA  , 25, Font.BOLD | Font.UNDERLINE);
            titulo.setFont(fontTitulo);
            
            titulo.add(new Chunk("Informe de cambios - IW7I")); 
            document.add(titulo);
            
            //añadir subtitulo
            Paragraph subtitulo = new Paragraph();

            subtitulo.setSpacingAfter(25);
            subtitulo.setSpacingBefore(5);
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            subtitulo.setIndentationLeft(50);
            subtitulo.setIndentationRight(50);
            Font fontSubtitulo = new Font(Font.FontFamily.HELVETICA  , 12);
            subtitulo.setFont(fontSubtitulo);
            
           /* subtitulo.add(new Phrase("Web monitorizada: www.vayapardo.com")); 
            document.add(subtitulo);*/
            
            
            List unorderedList = new List(List.UNORDERED);
            unorderedList.add(new ListItem("Web monitorizada: www.vayapardo.com"));
            unorderedList.add(new ListItem("Fecha inicio: 12/12/2013"));
            unorderedList.add(new ListItem("Fecha fin: 22/12/2013"));
            unorderedList.add(new ListItem("Monitorizado cada: 5 minutos"));
            unorderedList.add(new ListItem("Número de cambios: 56"));
            unorderedList.add(new ListItem("E-mail entrega: cjperez@8086.com"));
            
            subtitulo.add(unorderedList);

            document.add(subtitulo);
            
            
            String[][] content = { //Contenido a añadir a la tabla
                    {"23/12/2013","17:34", "No"},
                    {"23/12/2013","17:34", "No"},
                    {"23/12/2013","17:34", "No"},
                    {"23/12/2013","17:34", "No"}} ;
            PdfPTable table = createTable(content); //Crea la tabla
            document.add(table); //La añade al documento
            document.close(); //Cierra el documento
        } catch(Exception e){
        	e.printStackTrace();
        	System.out.println("Problemas al generar el PDF");
        }
	}
	/*
	 * Crea una tabla con el contenido pasado por parámetro [content] y la devuelve
	 */
	public  static PdfPTable createTable(String[][] content){
		Font cabecera = new Font(Font.FontFamily.HELVETICA  , 14, Font.BOLD); //fuente para la cabecera de la tabla
		PdfPTable table = new PdfPTable(3); // 3 columns.

        PdfPCell fecha = new PdfPCell(new Paragraph("Fecha",cabecera));
        PdfPCell hora = new PdfPCell(new Paragraph("Hora", cabecera));
        PdfPCell cambio = new PdfPCell(new Paragraph("Cambio", cabecera));
        table.addCell(fecha);
        table.addCell(hora);
        table.addCell(cambio);
		
        //añade a la tabla el contenido que se le pasa por parámetro
      
        	
        for(int i = 0; i < content.length; i++){
        	for(int j = 0 ; j < content[i].length; j++){
        		table.addCell(new PdfPCell(new Paragraph(content[i][j])));
        	}
        }
		return table;
		
	}

	
}
