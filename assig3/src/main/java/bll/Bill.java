package bll;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import model.Client;
import model.Produs;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Bill {
    private Client client ;
    private Produs produs ;
    private Integer cantitate ;
    private Integer pretFinal ;

    public Bill(Client client ,Produs produs , Integer cantitate) throws FileNotFoundException, DocumentException {
        this.client=client;
        this.produs=produs ;
        pretFinal= calcPret(produs ,cantitate) ;

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("ceva2.pdf"));
        document.open();

        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);


        Paragraph clientParagraph = new Paragraph("Nume client: " + client.getNume(), font);
        document.add(clientParagraph);

        Paragraph produsParagraph = new Paragraph("Produs: " + produs.getNume(), font);
        produsParagraph.add(new Chunk(" | Pret : " + produs.getPret() + " lei"));
        produsParagraph.add(new Chunk(" | Cantitate: " + cantitate + " bucati"));
        document.add(produsParagraph);


        Paragraph totalParagraph = new Paragraph("TOTAL: " + pretFinal, font);
        document.add(totalParagraph);

        document.close();
    }



    public int calcPret (Produs produs , Integer cantitate){
        int sum = produs.getPret() * cantitate;
        return sum ;
    }


}
