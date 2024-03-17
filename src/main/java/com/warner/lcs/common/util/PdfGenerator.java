package com.warner.lcs.common.util;

import com.codingerror.service.CodingErrorPdfInvoiceCreator;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DashedBorder;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;

import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.warner.lcs.app.domain.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PdfGenerator {

    private Client client;
    private InvoiceInformation invoiceInformation;
    private Business business;
    private Address billingAddress,address;
    LocalDate ld = LocalDate.now();
    private String pdfName = ld+".pdf";

    private Document document;
    private PdfDocument pdfDocument;

    private InvoiceNumberGenerator invoiceNumberGenerator;

    float fourcol = 95.0f;
    float threecol = 190.0F;
    float twocol = 285.0F;
    float twocol150=twocol+150f;
    float twocolumnWidth[] = {twocol150,twocol};
    float threecolumnWidth[] = {threecol,threecol,threecol};
    float fourcolumnWidth[] = {fourcol,fourcol,fourcol,fourcol};

    // Define the fixed width for the treatment description column
    float descriptionWidth = 130.0f; // Adjust this value as needed

    // Calculate the remaining width for the other columns
    float remainingWidth = fourcol - descriptionWidth;

    float fivecolumnWidth[] ={descriptionWidth, descriptionWidth, descriptionWidth -50,remainingWidth, descriptionWidth};
    float fullWidth []={threecol*3};
    Paragraph onesp = new Paragraph("\n");
    private Table tableDivider2 = new Table(fullWidth);

    private Table divider = new Table(fullWidth);
    private CodingErrorPdfInvoiceCreator cepdf;

    public PdfGenerator(String path, Client billingClient, Client client, Address billingAddress, Address address, InvoiceInformation invoiceInformation, Business business) throws FileNotFoundException, MalformedURLException{
        this.cepdf = new CodingErrorPdfInvoiceCreator(pdfName);
        this.client = client;
        this.invoiceInformation = invoiceInformation;
        this.business = business;

        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        document = (Document) new Document(pdfDocument);
        String imagePath="C:\\Users\\User\\Documents\\lcs\\lcs\\src\\main\\resources\\water_mark_lcs.png";
        ImageData imageData = ImageDataFactory.create(imagePath);
        Image image = new Image(imageData);

        float x = pdfDocument.getDefaultPageSize().getWidth()/2;
        float y = pdfDocument.getDefaultPageSize().getHeight()/2;
        image.setFixedPosition(x-150, y-150);
        image.setOpacity(0.18f);
        document.add(image);

        this.createHeader(invoiceInformation);
        this.createClientBillingInfo(billingAddress,address,client,client);
        this.createBody(invoiceInformation);
        this.createFooter();

        document.close();
    }

    public PdfGenerator(String path) throws IOException {
        invoiceNumberGenerator = new InvoiceNumberGenerator();
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        document = (Document) new Document(pdfDocument);
        String imagePath="C:\\Users\\User\\Documents\\lcs\\lcs\\src\\main\\resources\\water_mark_lcs.png";
        ImageData imageData = ImageDataFactory.create(imagePath);
        Image image = new Image(imageData);

        float x = pdfDocument.getDefaultPageSize().getWidth()/2;
        float y = pdfDocument.getDefaultPageSize().getHeight()/2;
        image.setFixedPosition(x-150, y-150);
        image.setOpacity(0.18f);
        document.add(image);

        document.close();
    }

    private void createHeader(InvoiceInformation invoiceInformation) throws MalformedURLException {
        // Assuming invoiceInformation.getDate() returns a Date object
        Date date = invoiceInformation.getDate(), paymentDate = invoiceInformation.getPaymentDueDate(), startDate = invoiceInformation.getStartDate(), endDate = invoiceInformation.getEndDate();

        // Create a SimpleDateFormat object with the desired date format
        SimpleDateFormat  sdf = new SimpleDateFormat("dd-MM-yyyy");

        Table table = new Table(this.twocolumnWidth);
        table.addCell(new Cell().add("Invoice").setFontSize(20f).setBorder(Border.NO_BORDER).setBold());
        Table nestedTable = new Table(new float[]{twocol/2,twocol/2});
        nestedTable.addCell(this.getHeaderTextCell("Invoice No.").setBold());
        nestedTable.addCell(this.getHeaderTextCell(invoiceInformation.getNo()));
        nestedTable.addCell(this.getHeaderTextCell("Invoice Date").setBold());
        nestedTable.addCell(this.getHeaderTextCell(sdf.format(date)));
        nestedTable.addCell(this.getHeaderTextCell("Payment Due Date").setBold());
        nestedTable.addCell(this.getHeaderTextCell(sdf.format(paymentDate)));
        nestedTable.addCell(this.getHeaderTextCell("Start Date").setBold());
        nestedTable.addCell(this.getHeaderTextCell(sdf.format(startDate)));
        nestedTable.addCell(this.getHeaderTextCell("End Date").setBold());
        nestedTable.addCell(this.getHeaderTextCell(sdf.format(endDate)));

        table.addCell(new Cell().add(nestedTable).setBorder(Border.NO_BORDER));

        Border gb = new SolidBorder(Color.GRAY,1f/2f);
        Table divider = new Table(fullWidth);
        divider.setBorder(gb);
        document.add(table);
        document.add(onesp);
        document.add(divider);
        document.add(onesp);
    }

    private void createClientBillingInfo(Address billingAddress, Address address, Client billigClient, Client client) {
        Table twoColTable = new Table(twocolumnWidth);
        twoColTable.addCell(getBillingAndShippingCell("Biling Information"));
        twoColTable.addCell(getBillingAndShippingCell("Service"));
        document.add(twoColTable.setMarginBottom(12f));

        Table twoColTable2 = new Table(twocolumnWidth);
        twoColTable2.addCell(getCell10fLeft("Company",true));
        twoColTable2.addCell(getCell10fLeft("Name",true));
        twoColTable2.addCell(getCell10fLeft(business.getName(), false));
        String fullName = client.getFirstName() + " " + client.getMiddleName() + " " + client.getLastName(), billingName, billingAddressString, addressString;
        twoColTable2.addCell(getCell10fLeft(fullName,false));
        document.add(twoColTable2);

        Table twoColTable3 = new Table(twocolumnWidth);
        twoColTable3.addCell(getCell10fLeft("Name",true));
        twoColTable3.addCell(getCell10fLeft("Address",true));
        billingName = billigClient.getFirstName() + " " + billigClient.getMiddleName() + " " + billigClient.getLastName();
        twoColTable3.addCell(getCell10fLeft(billingName,false));

        billingAddressString = billingAddress.getStreet() + ", " +billingAddress.getState().getState() +" "+ billingAddress.getCity().getCity() +" "+ billingAddress.getZipcode().getZipcode();

        twoColTable3.addCell(getCell10fLeft(billingAddressString,false));
        document.add(twoColTable3);

        float oneColumnWidth[]={twocol150};

        Table oneColTable1 = new Table(oneColumnWidth);
        oneColTable1.addCell(getCell10fLeft("Address",true));
        addressString = address.getStreet() + ", " +address.getState().getState() +" "+ address.getCity().getCity() +" "+ address.getZipcode().getZipcode();
        oneColTable1.addCell(getCell10fLeft(addressString,false));
        oneColTable1.addCell(getCell10fLeft("Email",true));
        oneColTable1.addCell(getCell10fLeft(billigClient.getEmail(),false));
        document.add(oneColTable1.setMarginBottom(10f));
    }


    private void createBody(InvoiceInformation invoiceInformation) {
        Border dgb = new DashedBorder(Color.GRAY,0.5f);
        document.add(tableDivider2.setBorder(dgb));
        Paragraph productPara = new Paragraph("Products");

        document.add(productPara.setBold());
        Table threeColTable1 = new Table(fivecolumnWidth);
        threeColTable1.setBackgroundColor(Color.BLACK,07.f).setPaddingLeft(50);

        threeColTable1.addCell(new Cell().add("Service").setBold().setFontColor(Color.WHITE).setBorder(Border.NO_BORDER));
        threeColTable1.addCell(new Cell().add("Description").setBold().setFontColor(Color.WHITE).setBorder(Border.NO_BORDER));
        threeColTable1.addCell(new Cell().add("Quantity").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        threeColTable1.addCell(new Cell().add("Price").setBold().setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)).setMargin(15f);
        threeColTable1.addCell(new Cell().add("Total").setBold().setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)).setMargin(15f);
        document.add(threeColTable1);

        List<Treatment> treatments = invoiceInformation.getTreatments();
        List<AdditionalCostService> additionalCostServices = invoiceInformation.getAdditionalCostServices();

        Table threeColTable2 = new Table(fivecolumnWidth);
        float totalSum=0f;
        for(Treatment treatment:treatments)
        {
            double total = treatment.getPrice();
            totalSum+=total;

            threeColTable2.addCell(new Cell().add(treatment.getTreatmentName()).setBorder(Border.NO_BORDER)).setMarginLeft(10f);
            threeColTable2.addCell(new Cell().add(treatment.getTreatmentDescription()).setBorder(Border.NO_BORDER)).setMarginLeft(10f);
            Unit unit = Unit.valueOf(getUnitSerachString(treatment.getUnit().toUpperCase()));
            threeColTable2.addCell(new Cell().add(treatment.getQty()+" "+unit.getAbbreviation()).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER)).setMarginLeft(10f);
            threeColTable2.addCell(new Cell().add(String.valueOf(treatment.getPrice())).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)).setMarginRight(15f);
            threeColTable2.addCell(new Cell().add(String.valueOf(total)).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)).setMarginRight(15f);
        }

        for(AdditionalCostService additionalCostService:additionalCostServices)
        {
            double total = additionalCostService.getPrice();
            totalSum+=total;

            threeColTable2.addCell(new Cell().add(additionalCostService.getTreatmentName()).setBorder(Border.NO_BORDER)).setMarginLeft(10f);
            threeColTable2.addCell(new Cell().add(additionalCostService.getTreatmentDescription()).setBorder(Border.NO_BORDER)).setMarginLeft(10f);
            Unit unit = Unit.valueOf(getUnitSerachString(additionalCostService.getUnit().toUpperCase()));
            threeColTable2.addCell(new Cell().add(additionalCostService.getQty()+" "+unit.getAbbreviation()).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER)).setMarginLeft(10f);
            threeColTable2.addCell(new Cell().add(String.valueOf(additionalCostService.getPrice())).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)).setMarginRight(15f);
            threeColTable2.addCell(new Cell().add(String.valueOf(total)).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)).setMarginRight(15f);
        }

        document.add(threeColTable2.setMarginBottom(20f));
        float onetwo[]={threecol+125f,threecol*2};
        Table threeColTable4 = new Table(onetwo);
        threeColTable4.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        threeColTable4.addCell(new Cell().add(tableDivider2).setBorder(Border.NO_BORDER));
        document.add(threeColTable4);

        Table threeColTable3 = new Table(threecolumnWidth);
        threeColTable3.addCell(new Cell().add("").setBorder(Border.NO_BORDER)).setMarginLeft(10f);
        threeColTable3.addCell(new Cell().add("Total").setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        threeColTable3.addCell(new Cell().add(String.valueOf(totalSum)).setPaddingLeft(150f).setBorder(Border.NO_BORDER)).setMarginRight(15f);

        document.add(threeColTable3);
    }

    private void createFooter(){
        document.add(tableDivider2);
        document.add(new Paragraph("\n"));
        document.add(divider.setBorder(new SolidBorder(Color.GRAY,1)).setMarginBottom(15f));
        String paraText = "Sample company overview\n" +
                "We provide residential and commercial lawn mowing, edging, trimming, pruning, weed control, yard cleanup, aeration, grass seeding and sodding, and snow shoveling and deicing in the winter months. We put customer happiness above all else and pride ourselves on doing the job right the first time.";
        document.add(new Paragraph("Notes: "+paraText));

        Table tb = new Table(fullWidth);
        tb.addCell(new Cell().add("Terms AND CONDITIONS\n").setBold().setBorder(Border.NO_BORDER));
        List <String> TncList = new ArrayList<>();
        TncList.add("1. The Seller shall not be liable to the Buyer directly or indirectly or for any loss or damage suffered by the buyer.");
        TncList.add("1. The Seller warrants the product for one (1) year from the data of shipment.");

        for(String tnc:TncList){
            tb.addCell(new Cell().add(tnc).setBorder(Border.NO_BORDER));
        }
        document.add(tb);
    }

    static Cell getHeaderTextCell(String textValue) {
        return new Cell().add(textValue).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }

    static Cell getBillingAndShippingCell(String textValue) {
        return new Cell().add(textValue).setFontSize(12f).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }

    static Cell getCell10fLeft(String textValue, Boolean isBold) {
        Cell cell = new Cell().add(textValue).setFontSize(10f).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
        return isBold ? cell.setBold() : cell;
    }

    public String getUnitSerachString(String enumName) {
        StringBuilder sb = new StringBuilder();
        char character;
        for (int i = 0;  i < enumName.length(); i++){
            character = enumName.charAt(i);

            if(character == ' '){
                sb.append('_');
            } else { sb.append(character); }
        }

        return sb.toString();
    }
}
