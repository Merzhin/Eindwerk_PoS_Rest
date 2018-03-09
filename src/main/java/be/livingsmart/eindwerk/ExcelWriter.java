/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.livingsmart.eindwerk;

import be.livingsmart.eindwerk.domain.OrderBean;
import be.livingsmart.eindwerk.domain.Shift;
import be.livingsmart.eindwerk.domain.ShiftItem;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;



/**
 *  This class creates excel (.xlsx) files and sends an email to the specified email address
 * @author Pieter
 */
public class ExcelWriter {
    
    /**
     *  Given a {@link Shift} an excel file gets made. Date, start time, end time and then all the sold products gets listed (amounts + price per product + total prices), followed by a total of all the prices of all the products that got sold. Then it calls a private method that sends this file to a specified (hardcoded) email address.
     * @param shift This is the {@link Shift} where data gets pulled from. 
     * @throws FileNotFoundException When the file was not found 
     * @throws IOException  When a file couldn't be saved
     */
    public void shiftReport(Shift shift) throws FileNotFoundException, IOException, IOException
    {
        
        String fileName = "Verslag: " + shift.getCurrentDate() + ".xlsx" ;//+ "   "  + shift.getStartTime() + ", " + shift.getEndTime();
        System.out.println(fileName);
        System.out.println(shift.getStartTime());
        
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Shift");
        ArrayList<ArrayList<String>> sheetString = new ArrayList<ArrayList<String>>();
        
        for (int i = 0; i <= 7; i++) {
            sheetString.add(new ArrayList<String>());
        }
        sheetString.get(0).add("Datum: "); sheetString.get(0).add(""); sheetString.get(0).add("" + shift.getCurrentDate());  
        sheetString.get(1).add("Start shift: "); sheetString.get(1).add(""); sheetString.get(1).add("" + shift.getStartTime()); 
        sheetString.get(2).add("Einde shift: "); sheetString.get(2).add(""); sheetString.get(2).add("" + shift.getEndTime()); 
        sheetString.get(3).add("Supervisor: "); sheetString.get(3).add(""); sheetString.get(3).add("" + shift.getSupervisor());
        sheetString.get(4).add(""); 
        sheetString.get(5).add("Product: "); sheetString.get(5).add("Aantal verkocht: "); sheetString.get(5).add("Prijs per product"); sheetString.get(5).add("Totale prijs");
        
        int i = 6;
        double total = 0;
        for (Map.Entry<Long, ShiftItem> entry : shift.getShiftItems().entrySet()) 
        {
            sheetString.add(new ArrayList<String>());
            sheetString.get(i).add(entry.getValue().getItem().getName());
            sheetString.get(i).add("" + entry.getValue().getAmount());
            sheetString.get(i).add("" + entry.getValue().getItem().getPrice()); 
            double productTotal = entry.getValue().getItem().getPrice() * entry.getValue().getAmount();
            sheetString.get(i).add("" + productTotal);
            total += productTotal;
            i++;
        }
        sheetString.get(i+1).add("Totale prijs: "); sheetString.get(i+1).add(""); sheetString.get(i+1).add(""); sheetString.get(i+1).add("" + total);
        
        int rowNum = 0;
        System.out.println("Creating excel");

        for (ArrayList list : sheetString) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object field : list) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        }

        try {
            File file = new File(fileName);
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            workbook.close();
            sendEmail(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done");
    }
    
    private void sendEmail(final File file)  {    
        String hdr = "HDRPointOfSale@gmail.com";
        
        
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        //Using gmail
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(hdr);
        mailSender.setPassword("PointOfSale2018");
         
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "true");//Prints out everything on screen
         
        mailSender.setJavaMailProperties(javaMailProperties);
        
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage message) throws Exception {
                
                String email = "HDRPointOfSale@gmail.com";
                
                message.setFrom(new InternetAddress("HDRPointOfSale@gmail.com", "Kassa app"));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
                message.setText("Het shift report zit in de bijlage");
                message.setSubject("Shift report");
                
                Multipart multipart = new MimeMultipart();

                // creates body part for the message


                // creates body part for the attachment
                MimeBodyPart attachPart = new MimeBodyPart();
                attachPart.attachFile(file);

                // code to add attachment...will be revealed later

                // adds parts to the multiparts
                multipart.addBodyPart(attachPart);

                // sets the multipart as message's content
                message.setContent(multipart);
                
               
            }
        };
        
        try {
            mailSender.send(preparator);
            System.out.println("Message Send...Hurrey");
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
        
    }
}
