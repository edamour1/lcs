package com.warner.lcs.common.util;

import com.codingerror.model.AddressDetails;
import com.codingerror.model.HeaderDetails;
import com.codingerror.model.Product;
import com.codingerror.model.ProductTableHeader;
import com.codingerror.service.CodingErrorPdfInvoiceCreator;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DashedBorder;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.warner.lcs.app.domain.Address;
import com.warner.lcs.app.domain.Business;
import com.warner.lcs.app.domain.Client;
import com.warner.lcs.app.domain.InvoiceInformation;


import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    PdfDocument pdfDocument;
    float threecol = 190.0F;
    float twocol = 285.0F;
    float twocol150=twocol+150f;
    float twocolumnWidth[] = {twocol150,twocol};
    float fullwidth []={threecol*3};
    Paragraph onesp = new Paragraph("\n");
    private CodingErrorPdfInvoiceCreator cepdf;

    public PdfGenerator(String path) throws FileNotFoundException {
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        document = (Document) new Document(pdfDocument);

        this.createHeader();
        document.close();
    }

    public void createHeader() {
        Table table = new Table(this.twocolumnWidth);
        table.addCell(new Cell().add("Invoice").setFontSize(20f).setBorder(Border.NO_BORDER).setBold());
        Table nestedTable = new Table(new float[]{twocol/2,twocol/2});
        nestedTable.addCell(this.getHeaderTextCell("Invoice No."));
        nestedTable.addCell(this.getHeaderTextCell("RK356748"));
        nestedTable.addCell(this.getHeaderTextCell("Invoice Date"));
        nestedTable.addCell(this.getHeaderTextCell("15/16/2024"));

        table.addCell(new Cell().add(nestedTable).setBorder(Border.NO_BORDER));

        Border gb = new SolidBorder(Color.GRAY,1f/2f);
        Table divider = new Table(fullwidth);
        divider.setBorder(gb);
        document.add(table);
        document.add(onesp);
        document.add(divider);
        document.add(onesp);

        Table twoColTable = new Table(twocolumnWidth);
        twoColTable.addCell(getBillingAndShippingCell("Biling Information"));
        twoColTable.addCell(getBillingAndShippingCell("Shipping Information"));
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

        Table tableDivider2 = new Table(fullwidth);
        Border dgb = new DashedBorder(Color.GRAY,0.5f);
        document.add(tableDivider2.setBorder(dgb));
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


//    public String generatePdf() {
//        //Create Header start
//        HeaderDetails header = new HeaderDetails();
//        header.setInvoiceNo("RK35623").setInvoiceDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
//                .setInvoiceTitle("Invoice").build();
//        cepdf.createHeader(header);
//        //Header End
//
//        //Create Addrses start
//        AddressDetails addressDetails = new AddressDetails();
//        addressDetails
//                .setBillingCompany("Coding Error")
//                .setBillingName("Bhsakar")
//                .setBillingAddress("9322 Melody circle sw covington ga 30014")
//                .setBillingEmail("test@gmail.com")
//                .setShippingName("Customer Name")
//                .setShippingAddress("Test")
//                .build();
//        cepdf.createAddress(addressDetails);
//        //Address end
//
//        //Product Start
//        ProductTableHeader productTableHeader = new ProductTableHeader();
//        cepdf.createTableHeader(productTableHeader);
//        List<Product> productList = cepdf.getDummyProductList();
//        productList = cepdf.modifyProductList(productList);
//        cepdf.createProduct(productList);
//        //Product End
//
//        //Term and Condition Start
//        List<String> TncList = new ArrayList<>();
//        TncList.add("etc...");
//        TncList.add("etc...!");
////        String imagePath = "C:/Users/User/Desktop/pdf";
//
////        cepdf.createTnc(TncList,false,imagePath);
//        //Term and condition end
//        System.out.println("pdf generated");
//
//        return "Success";
//    }
//
//
//    public void test() throws FileNotFoundException {
//        LocalDate ld= LocalDate.now();
//        String pdfName= ld+".pdf";
//        CodingErrorPdfInvoiceCreator cepdf=new CodingErrorPdfInvoiceCreator(pdfName);
//        cepdf.createDocument();
//
//        //Create Header start
//        HeaderDetails header=new HeaderDetails();
//        header.setInvoiceNo("RK35623").setInvoiceDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).build();
//        cepdf.createHeader(header);
//        //Header End
//
//        //Create Address start
//        AddressDetails addressDetails=new AddressDetails();
//        addressDetails
//                .setBillingCompany("Coding Error")
//                .setBillingName("Bhaskar")
//                .setBillingAddress("Bangluru,karnataka,india\n djdj\ndsjdsk")
//                .setBillingEmail("codingerror303@gmail.com")
//                .setShippingName("Customer Name \n").setShippingInfoText("Client Information")
//                .setShippingAddress("Banglore Name sdss\n swjs\n")
//                .build();
//
//        cepdf.createAddress(addressDetails);
//        //Address end
//
//        //Product Start
//        ProductTableHeader productTableHeader=new ProductTableHeader();
//        cepdf.createTableHeader(productTableHeader);
//        List<Product> productList=cepdf.getDummyProductList();
//        productList=cepdf.modifyProductList(productList);
//        cepdf.createProduct(productList);
//        //Product End
//
//        //Term and Condition Start
//        List<String> TncList=new ArrayList<>();
//        TncList.add("1. The Seller shall not be liable to the Buyer directly or indirectly for any loss or damage suffered by the Buyer.");
//        TncList.add("2. The Seller warrants the product for one (1) year from the date of shipment");
//        String imagePath="src/main/resources/ce_logo_circle_transparent.png";
//        cepdf.createTnc(TncList,false,imagePath);
//        // Term and condition end
//        System.out.println("pdf genrated");
//    }


}
