package tn.esprit.utils;

import javafx.scene.text.Font;
import net.glxn.qrgen.javase.QRCode;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class QrCodeHotel {

    private static final Font BARCODE_TEXT_FONT = new Font("Arial", 12);

    public static BufferedImage generateEAN13BarcodeImage(String barcodeText) throws Exception {
        Barcode barcode = BarcodeFactory.createEAN13(barcodeText);
        barcode.setFont(new java.awt.Font("Arial",12,12));

        return BarcodeImageHandler.getImage(barcode);
    }

    public static BufferedImage generateQRCodeImage(String barcodeText) throws Exception {
        ByteArrayOutputStream stream = QRCode
                .from(barcodeText)
                .withSize(250, 250)
                .stream();
        ByteArrayInputStream bis = new ByteArrayInputStream(stream.toByteArray());

        return ImageIO.read(bis);
    }

}
