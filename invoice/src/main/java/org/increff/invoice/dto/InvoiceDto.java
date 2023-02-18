package org.increff.invoice.dto;

import lombok.extern.log4j.Log4j;
import org.apache.fop.apps.FOPException;
import org.increff.invoice.model.InvoiceData;
import org.increff.invoice.util.GeneratePDF;
import org.increff.invoice.util.GenerateXML;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
@Log4j
@Service
public class InvoiceDto {

    public String generateString(InvoiceData invoiceData) {
        byte[] encodedBytes = null;
        try {
            GenerateXML.createXml(invoiceData);
            GeneratePDF.createPDF(invoiceData.getOrderId());
            encodedBytes = org.apache.commons.io.FileUtils.readFileToByteArray(new File("D:\\Increff\\POS23v2\\invoice\\all_invoice\\invoice_"+invoiceData.getOrderId()+".pdf"));
        }
        catch (Exception e){
        }
        String b64PDF = Base64.getEncoder().encodeToString(encodedBytes);
        System.out.println(encodedBytes);
        return b64PDF;
    }
}
