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
 *
 * @author PC
 */
public class ExcelWriter {
    
    
    public void shiftReport(Shift shift) throws FileNotFoundException, IOException, IOException
    {
        
        String fileName = "Verslag: " + shift.getCurrentDate() ;//+ "   "  + shift.getStartTime() + ", " + shift.getEndTime();
        System.out.println(fileName);
        System.out.println(shift.getStartTime());
        
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Shift");
        ArrayList<ArrayList<String>> sheetString = new ArrayList<>();
        
        sheetString.get(0).add("Datum: "); sheetString.get(0).add(""); sheetString.get(0).add("" + shift.getCurrentDate());  
        sheetString.get(1).add("Start shift: "); sheetString.get(1).add(""); sheetString.get(1).add("" + shift.getStartTime()); 
        sheetString.get(2).add("Einde shift: "); sheetString.get(2).add(""); sheetString.get(2).add("" + shift.getEndTime()); 
        sheetString.get(3).add(""); 
        sheetString.get(4).add("Product: "); sheetString.get(4).add("Aantal verkocht: "); sheetString.get(4).add("Totale prijs");
        
        int i = 5;
//        BigDecimal total = new BigDecimal(0);
//        for (Map.Entry<Long, ShiftItem> entry : shift.getShiftItems().entrySet()) 
//        {
//            sheetString.get(i).add(entry.getValue().getItem().getName());
//            sheetString.get(i).add("" + entry.getValue().getAmount());
//            BigDecimal productTotal = entry.getValue().getItem().getPrice().multiply(new BigDecimal(entry.getValue().getAmount()));
//            sheetString.get(i).add("" + productTotal);
//            total.add(productTotal);
//            i++;
//        }
//        sheetString.get(i).add("Totale prijs: "); sheetString.get(i).add("" + total);
        
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
            sendEmail(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done");
    }
    
    private void sendEmail(String filename)  {    
        String hdr = "HDRPointOfSale@gmail.com";
        /*
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", hdr);
        props.put("mail.smtp.password", "PointOfSaleHDR2018");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        
        SmtpAuthenticator authenticator = new SmtpAuthenticator();
        

        try {
          javax.mail.Message msg = new MimeMessage(Session.getInstance(props, authenticator));
          msg.setFrom(new InternetAddress( hdr, "From"));
          InternetAddress to = new InternetAddress("pieterbogemans@hotmail.com", "Mr. User");
          
          msg.addRecipient(Message.RecipientType.TO, to);
          msg.setSubject("Your Example.com account has been activated");
          msg.setText("This is a test");
          Transport.send(msg);
        } catch (Exception e) {
          e.printStackTrace();
        }
        */
        
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        //Using gmail
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(hdr);
        mailSender.setPassword("PointOfSaleHDR2018");
         
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "true");//Prints out everything on screen
         
        mailSender.setJavaMailProperties(javaMailProperties);
        
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mm) throws Exception {
                mm.setFrom(new InternetAddress("HDRPointOfSale@gmail.com", "From"));
                mm.setRecipient(Message.RecipientType.TO, new InternetAddress("pieterbogemans@hotmail.com"));
                mm.setText("Dear ");
                mm.setSubject("Your order on Demoapp");
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
