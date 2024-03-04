package tn.esprit.utils;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import tn.esprit.entities.TourismeEntities.Reservation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ReservationPDF {
    public void generate(final List<Reservation> reservationData) {
        final Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("iTextTable.pdf"));
        } catch (final DocumentException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        document.open();
        final List<String> attributes = new ArrayList<>() {{
            this.add("Reference");
            this.add("Durée");
            this.add("Prix");
            this.add("Date");
            this.add("nomHotel");
            this.add("TypeChombre");
        }};
        final float[] widths = {50, 50, 50, 80, 50, 50};
        final PdfPTable table = new PdfPTable(widths);
        this.addTableHeader(table, attributes);
        this.addRows(table, reservationData);
        // addCustomRows(table);

        try {
            document.add(table);
        } catch (final DocumentException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        document.close();
    }

    private void addTableHeader(final PdfPTable table, final List<String> attributes) {
        attributes.forEach(columnTitle -> {
            final PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setIndent(10);
            header.setPhrase(new Phrase(columnTitle));
            table.addCell(header);
        });
    }

    private void addRows(final PdfPTable table, final List<Reservation> reservationData) {
        for (final Reservation reservation : reservationData) {
            table.addCell(String.valueOf(reservation.getRef_reservation()));
            table.addCell(String.valueOf(reservation.getDuree_reservation()));
            table.addCell(String.valueOf(reservation.getPrix_reservation()));
            table.addCell(String.valueOf(reservation.getDate_reservation()));
            table.addCell(String.valueOf(reservation.getId_hotel()));
            table.addCell(String.valueOf(reservation.getType_chambre()));


        }
    }

}