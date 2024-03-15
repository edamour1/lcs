package com.warner.lcs.common.util;

import com.codingerror.model.Product;
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PdfGenerator {

    private Client client;
    private Address address;
    private InvoiceInformation information;
    private Business business;
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
    float fivecolumnWidth[] = {fourcol+10,fourcol+10,fourcol-5,fourcol-5,fourcol-5};
    float fullWidth []={threecol*3};
    Paragraph onesp = new Paragraph("\n");
    private CodingErrorPdfInvoiceCreator cepdf;

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


        this.createHeader();
        document.close();
    }

    public void createHeader() throws MalformedURLException {

        Table table = new Table(this.twocolumnWidth);
        table.addCell(new Cell().add("Invoice").setFontSize(20f).setBorder(Border.NO_BORDER).setBold());
        Table nestedTable = new Table(new float[]{twocol/2,twocol/2});
        nestedTable.addCell(this.getHeaderTextCell("Invoice No.").setBold());
        nestedTable.addCell(this.getHeaderTextCell(invoiceNumberGenerator.generateInvoiceNo()));
        nestedTable.addCell(this.getHeaderTextCell("Invoice Date").setBold());
        nestedTable.addCell(this.getHeaderTextCell("15/16/2024"));

        table.addCell(new Cell().add(nestedTable).setBorder(Border.NO_BORDER));

        Border gb = new SolidBorder(Color.GRAY,1f/2f);
        Table divider = new Table(fullWidth);
        divider.setBorder(gb);
        document.add(table);
        document.add(onesp);
        document.add(divider);
        document.add(onesp);

        Table twoColTable = new Table(twocolumnWidth);
        twoColTable.addCell(getBillingAndShippingCell("Biling Information"));
        twoColTable.addCell(getBillingAndShippingCell("Service"));
        document.add(twoColTable.setMarginBottom(12f));

        Table twoColTable2 = new Table(twocolumnWidth);
        twoColTable2.addCell(getCell10fLeft("Company",true));
        twoColTable2.addCell(getCell10fLeft("Name",true));
        twoColTable2.addCell(getCell10fLeft("Coding Error",false));
        twoColTable2.addCell(getCell10fLeft("Coding",false));
        document.add(twoColTable2);

        Table twoColTable3 = new Table(twocolumnWidth);
        twoColTable3.addCell(getCell10fLeft("Name",true));
        twoColTable3.addCell(getCell10fLeft("Address",true));
        twoColTable3.addCell(getCell10fLeft("Arlyn Puttergill",false));
        twoColTable3.addCell(getCell10fLeft("8570 Guleth Terra, 3324 Eastwood\nSpringi, Ma, 01114",false));
        document.add(twoColTable3);

        float oneColumnWidth[]={twocol150};

        Table oneColTable1 = new Table(oneColumnWidth);
        oneColTable1.addCell(getCell10fLeft("Address",true));
        oneColTable1.addCell(getCell10fLeft("8570 Gulseth Terra, 3324 Eastwood\nSpringfi, Ma, 01114",false));
        oneColTable1.addCell(getCell10fLeft("Email",true));
        oneColTable1.addCell(getCell10fLeft("stern@example.com",false));
        document.add(oneColTable1.setMarginBottom(10f));


        Table tableDivider2 = new Table(fullWidth);
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

        List<Product> productList = new ArrayList<>();
        productList.add(new Product("apple",2,159));
        productList.add(new Product("mango",4,159));
        productList.add(new Product("bannana",2,90));
        productList.add(new Product("grapes",2,10));
        productList.add(new Product("coconut",2,61));
        productList.add(new Product("cherry",1,1000));
        productList.add(new Product("kiwi",3,30));

        List<Treatment> treatments = new ArrayList<>();
        Treatment t1 = new Treatment(),t2 = new Treatment(),t3 = new Treatment();
        t1.setTreatmentName("treatment 1");
        t1.setTreatmentDescription("Pre-M #1/spring");
        t1.setQty(2);
        t1.setPrice(35.00);

        t2.setTreatmentName("treatment 2");
        t2.setTreatmentDescription("descritption 2");
        t2.setQty(6);
        t2.setPrice(55.00);

        t3.setTreatmentName("treatment 3");
        t3.setTreatmentDescription("descritption 3");
        t3.setQty(1);
        t3.setPrice(65.00);
        treatments.add(t1);
        treatments.add(t2);
        treatments.add(t3);

        List<AdditionalCostService> additionalCostServices = new ArrayList<>();
        AdditionalCostService a1 = new AdditionalCostService(),a2 = new AdditionalCostService(),a3 = new AdditionalCostService();
        a1.setTreatmentName("additional cost 1");
        a1.setTreatmentDescription("descritption 1");
        a1.setQty(1);
        a1.setPrice(20.00);

        a2.setTreatmentName("additional cost 2");
        a2.setTreatmentDescription("descritption 2");
        a2.setQty(6);
        a2.setPrice(30.00);

        a3.setTreatmentName("additional cost 3");
        a3.setTreatmentDescription("descritption 3");
        a3.setQty(1);
        a3.setPrice(40.00);
        additionalCostServices.add(a1);
        additionalCostServices.add(a2);
        additionalCostServices.add(a3);

        Table threeColTable2 = new Table(fivecolumnWidth);
        float totalSum=0f;
        for(Treatment treatment:treatments)
        {
            double total = treatment.getQty()*treatment.getPrice();
            totalSum+=total;

            threeColTable2.addCell(new Cell().add(treatment.getTreatmentName()).setBorder(Border.NO_BORDER)).setMarginLeft(10f);
            threeColTable2.addCell(new Cell().add(treatment.getTreatmentDescription()).setBorder(Border.NO_BORDER)).setMarginLeft(10f);
            threeColTable2.addCell(new Cell().add(String.valueOf(treatment.getQty())).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER)).setMarginLeft(10f);
            threeColTable2.addCell(new Cell().add(String.valueOf(treatment.getPrice())).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)).setMarginRight(15f);
            threeColTable2.addCell(new Cell().add(String.valueOf(total)).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)).setMarginRight(15f);
        }

        for(AdditionalCostService additionalCostService:additionalCostServices)
        {
            double total = additionalCostService.getQty()*additionalCostService.getPrice();
            totalSum+=total;

            threeColTable2.addCell(new Cell().add(additionalCostService.getTreatmentName()).setBorder(Border.NO_BORDER)).setMarginLeft(10f);
            threeColTable2.addCell(new Cell().add(additionalCostService.getTreatmentDescription()).setBorder(Border.NO_BORDER)).setMarginLeft(10f);
            threeColTable2.addCell(new Cell().add(String.valueOf(additionalCostService.getQty())).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER)).setMarginLeft(10f);
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
    public PdfGenerator(Client client, Address address, InvoiceInformation information, Business business) {
        this.cepdf = new CodingErrorPdfInvoiceCreator(pdfName);
        this.client = client;
        this.address = address;
        this.information = information;
        this.business = business;
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

}
