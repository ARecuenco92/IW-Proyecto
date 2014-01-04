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
	
	private String pdfName; //nombre del pdf
	private String webName; //nombre de la web monitorizada
	private String startDate; //fecha inicio monitorizaci�n
	private String endDate; //fecha fin monitorizaci�n
	private String frecuency; //frecuencia monitorizaci�n (en horas)
	private String emailAdress; // correo al cual mandar el pdf
	private String numberOfChanges; //numero de cambios en la web monitorizada
	private String[][] content; //contenido almacenado en la BD para generar la tabla
	
	/*
	 * EL contenido debe tener el siguiente formato:
	 * POR EJEMPLO:
	 * String[][] content = { 
     *               {"23/12/2013", "No"},
     *               {"23/12/2013", "No"},
     *               {"23/12/2013", "No"},
     *               {"23/12/2013", "No"}} ;
	 * 
	 * Fecha , cambio
	 */
	
	
	public PDF(String pdfName, String webName, String startDate, String endDate, String frecuency, String emailAddress,
			String numberOfChanges, String[][] content){
		pdfName = this.pdfName;
		webName = this.webName; 
		startDate = this.startDate;
		endDate = this.endDate;
		frecuency = this.frecuency;
		emailAddress = this.emailAdress;
		numberOfChanges = this.numberOfChanges;
		content = this.content;
	}
	public void generatePDF() {
		Document document = new Document();

        try {
            PdfWriter.getInstance(document,
                new FileOutputStream(pdfName));
    
            document.open(); //Abre el documento
            
            //a�adir el titulo
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
            
            //a�adir subtitulo
            Paragraph subtitulo = new Paragraph();

            subtitulo.setSpacingAfter(25);
            subtitulo.setSpacingBefore(5);
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            subtitulo.setIndentationLeft(50);
            subtitulo.setIndentationRight(50);
            Font fontSubtitulo = new Font(Font.FontFamily.HELVETICA  , 12);
            subtitulo.setFont(fontSubtitulo);
            
         
            
            
            List unorderedList = new List(List.UNORDERED);
            unorderedList.add(new ListItem("Web monitorizada: "+webName));
            unorderedList.add(new ListItem("Fecha inicio: "+startDate));
            unorderedList.add(new ListItem("Fecha fin: "+endDate));
            unorderedList.add(new ListItem("Monitorizado cada: "+frecuency+" minutos"));
            unorderedList.add(new ListItem("N�mero de cambios: "+numberOfChanges));
            unorderedList.add(new ListItem("E-mail entrega: "+emailAdress));
            
            subtitulo.add(unorderedList);

            document.add(subtitulo);
            
            
            String[][] content = { //Contenido a a�adir a la tabla
                    {"23/12/2013", "No"},
                    {"23/12/2013", "No"},
                    {"23/12/2013", "No"},
                    {"23/12/2013", "No"}} ;
            PdfPTable table = createTable(content); //Crea la tabla
            document.add(table); //La a�ade al documento
            document.close(); //Cierra el documento
        } catch(Exception e){
        	e.printStackTrace();
        	System.out.println("Problemas al generar el PDF");
        }
	}
	/*
	 * Crea una tabla con el contenido pasado por par�metro [content] y la devuelve
	 */
	public  static PdfPTable createTable(String[][] content){
		Font cabecera = new Font(Font.FontFamily.HELVETICA  , 14, Font.BOLD); //fuente para la cabecera de la tabla
		PdfPTable table = new PdfPTable(2); // 2 columns.

        PdfPCell fecha = new PdfPCell(new Paragraph("Fecha",cabecera));
        PdfPCell cambio = new PdfPCell(new Paragraph("Cambio", cabecera));
        table.addCell(fecha);
        table.addCell(cambio);
		
        //a�ade a la tabla el contenido que se le pasa por par�metro
      
        	
        for(int i = 0; i < content.length; i++){
        	for(int j = 0 ; j < content[i].length; j++){
        		table.addCell(new PdfPCell(new Paragraph(content[i][j])));
        	}
        }
		return table;
		
	}

	
}
