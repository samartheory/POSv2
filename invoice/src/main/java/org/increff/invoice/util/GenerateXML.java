package org.increff.invoice.util;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.increff.invoice.model.InvoiceData;
import org.increff.invoice.model.OrderItemDataForInvoice;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
public class GenerateXML {

    public static void createXml(InvoiceData invoiceData)
            throws ParserConfigurationException, TransformerException {
        String xmlFilePath = "xmlfile.xml";

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();

        int i = 0;
        // root element
        Element root = document.createElement("bill");
        document.appendChild(root);
        double finalBill = 0;

        Element date = document.createElement("date");
        date.appendChild(document.createTextNode(getDate()));
        root.appendChild(date);

        Element time = document.createElement("time");
        time.appendChild(document.createTextNode(getTime()));
        root.appendChild(time);
        // Create elements from OrderDetailsData list
        List<OrderItemDataForInvoice> orderItemDataList = invoiceData.getOrderItemDataList();
        for (i = 0; i < orderItemDataList.size(); i++) {
            Element item = document.createElement("item");
            root.appendChild(item);
            Element id = document.createElement("id");
            id.appendChild(document.createTextNode(String.valueOf(i+1)));
            item.appendChild(id);

            Element name = document.createElement("name");
            name.appendChild(document.createTextNode(orderItemDataList.get(i).getName()));
            item.appendChild(name);
            // Calculate total bill amount
            finalBill = finalBill + orderItemDataList.get(i).getQuantity()* orderItemDataList.get(i).getMrp();
            double totalCost = 0;
            totalCost = totalCost + orderItemDataList.get(i).getQuantity() * orderItemDataList.get(i).getMrp();
            Element quantity = document.createElement("quantity");
            quantity.appendChild(document.createTextNode(String.valueOf(orderItemDataList.get(i).getQuantity())));
            item.appendChild(quantity);

            Element sellingPrice = document.createElement("sellingPrice");
            sellingPrice.appendChild(document.createTextNode(String.format("%.2f",orderItemDataList.get(i).getMrp())));
            item.appendChild(sellingPrice);

            Element cost = document.createElement("cost");
            cost.appendChild(document.createTextNode(String.format("%.2f",totalCost)));
            item.appendChild(cost);

        }

        Element total = document.createElement("total");
        total.appendChild(document.createTextNode("Rs. " + String.format("%.2f",finalBill)));
        root.appendChild(total);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);

        StreamResult streamResult = new StreamResult(new File(xmlFilePath));

        transformer.transform(domSource, streamResult);
    }

    // Get date in required format
    private static String getDate() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date dateobj = new Date();
        String date = df.format(dateobj);
        return date;
    }

    // Get time in required format
    private static String getTime() {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date dateobj = new Date();
        String time = df.format(dateobj);
        return time;
    }

}