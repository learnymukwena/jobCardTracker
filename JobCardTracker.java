/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobcardtracker;
import java.awt.*;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.File; 
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.CopyOption;
import java.nio.file.StandardCopyOption;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableModel;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.GreekList;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.RomanList;
import com.itextpdf.text.Section;
import com.itextpdf.text.ZapfDingbatsList;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Radio Solutions
 */
public class JobCardTracker extends JWindow{
    
         // A simple little method to show a title screen in the center
    // of the screen for the amount of time given in the constructor
    public void showSplash() {
         
        JPanel content = (JPanel)getContentPane();
        content.setBackground(Color.BLACK);
         
        // Set the window's bounds, centering the window
        int width = 550;
        int height =215;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width-width)/2;
        int y = (screen.height-height)/2;
        setBounds(x,y,width,height);
         
        // Build the splash screen
        JLabel label = new JLabel(new ImageIcon("J4o.gif"));
        JLabel copyrt = new JLabel
                ("Job Card Tracking System, Radio Solutions", JLabel.CENTER);
        copyrt.setForeground(Color.WHITE);
        //copyrt.setFont(new Font("Sans-Serif", Font.BOLD, 12));
        content.add(label, BorderLayout.CENTER);
        content.add(copyrt, BorderLayout.SOUTH);
        Color oraRed = new Color(0, 0, 0,  0);
        content.setBorder(BorderFactory.createLineBorder(oraRed, 10));
         
        // Display it
        setVisible(true);
         
        // Wait a little while, maybe while loading resources
        try { Thread.sleep(10000); } catch (Exception e) {}
         
        setVisible(false);
         
    }
    
    
    
    String database = "localhost:3307";
    String password = "lt22111996";
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    
    
      public String getDate()
    {
        try
        {
            LocalDate localDate = LocalDate.now();
            //System.out.println(DateTimeFormatter.ofPattern("yyyMMdd").format(localDate));
            String datee = DateTimeFormatter.ofPattern("yyyMMdd").format(localDate);
            return datee;
        }catch(Exception ex)
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,ex.toString());
        }
        return null;
    }
      
       public String getCurrentDate()
    {
        try
        {
            Date date = new Date();
            return (sdf.format(date));
            
        }catch(Exception ex)
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,ex.toString());
        }
        return null;
    }
    
    public void createPdf(String technician,String fromdate,String todate)
    {
        Document document = new Document();
        
            try{
                
                    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\Radio Solutions\\Documents\\NetBeansProjects\\jobCardTracker\\report.pdf"));
                    document.open();
                    
                    Image image1 = Image.getInstance("C:\\Users\\Radio Solutions\\Documents\\NetBeansProjects\\jobCardTracker\\radio.jpg");
                    image1.scaleAbsolute(70, 70);
                    image1.setAbsolutePosition(100f, 750f);
                    document.add(image1);
                    
                    Paragraph title_sec1 = new Paragraph("Radio Solutions Pvt Ltd");
                    Paragraph title_sec2 = new Paragraph("4 Beit Avenue");
                    Paragraph title_sec3 = new Paragraph("Milton Park");
                    Paragraph title_sec4 = new Paragraph("Harare");
                    Paragraph title_sec5 = new Paragraph("Mobile:+2634796052");
                    Paragraph title_sec6 = new Paragraph("Email: office@radiosolutions.co.zw");
                    
                    title_sec1.setAlignment(Element.ALIGN_RIGHT);
                    document.add(title_sec1);
                    title_sec2.setAlignment(Element.ALIGN_RIGHT);
                    document.add(title_sec2);
                    title_sec3.setAlignment(Element.ALIGN_RIGHT);
                    document.add(title_sec3);
                    title_sec4.setAlignment(Element.ALIGN_RIGHT);
                    document.add(title_sec4);
                    title_sec5.setAlignment(Element.ALIGN_RIGHT);
                    document.add(title_sec5);
                    title_sec6.setAlignment(Element.ALIGN_RIGHT);
                    document.add(title_sec6);

                    document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));

                    Paragraph owner_name = new Paragraph("EMPLOYEE NAME: "+technician,FontFactory.getFont(FontFactory.HELVETICA, 12));
                    owner_name.setAlignment(Element.ALIGN_LEFT);
                    document.add(owner_name);


                    Paragraph owner_dept = new Paragraph("DEPARTMENT: Technical ",FontFactory.getFont(FontFactory.HELVETICA, 12));
                    owner_dept.setAlignment(Element.ALIGN_LEFT);
                    document.add(owner_dept);

                    Paragraph report_id = new Paragraph("REPORT NUMBER: "+new JobCardTracker().getDate()+technician,FontFactory.getFont(FontFactory.HELVETICA, 12));
                    owner_dept.setAlignment(Element.ALIGN_LEFT);
                    document.add(report_id);
                    //getCurrentDate()

                    Paragraph dategenerated = new Paragraph("DATE GENERATED: "+new JobCardTracker().getCurrentDate(),FontFactory.getFont(FontFactory.HELVETICA, 12));
                    owner_dept.setAlignment(Element.ALIGN_LEFT);
                    document.add(dategenerated);
                    
                    Paragraph range = new Paragraph("FROM-> "+fromdate+" TO-> "+todate,FontFactory.getFont(FontFactory.HELVETICA, 12));
                    owner_dept.setAlignment(Element.ALIGN_LEFT);
                    document.add(range);
                    
                    Paragraph hoursspend = new Paragraph("TOTAL HOURS: "+new JobCardTracker().getHours(technician,fromdate,todate),FontFactory.getFont(FontFactory.HELVETICA, 12));
                    owner_dept.setAlignment(Element.ALIGN_LEFT);
                    document.add(hoursspend);

                        PdfPTable table = new PdfPTable(8); // 3 columns.
                        table.setWidthPercentage(100); //Width 100%
                        table.setSpacingBefore(10f); //Space before table
                        table.setSpacingAfter(10f); //Space after table

                    //Set Column widths
                        float[] columnWidths = {1f, 1f, 1f,1f,1f,1f,1f,1f};
                        table.setWidths(columnWidths);

                        PdfPCell cell1 = new PdfPCell(new Paragraph("CLIENT",FontFactory.getFont(FontFactory.HELVETICA, 10)));
                        cell1.setBorderColor(BaseColor.BLUE);
                        cell1.setPaddingLeft(10);
                        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        PdfPCell cell2 = new PdfPCell(new Paragraph("ADDRESS",FontFactory.getFont(FontFactory.HELVETICA, 10)));
                        cell2.setBorderColor(BaseColor.GREEN);
                        cell2.setPaddingLeft(10);
                        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        PdfPCell cell3 = new PdfPCell(new Paragraph("JOBCARD",FontFactory.getFont(FontFactory.HELVETICA, 10)));
                        cell3.setBorderColor(BaseColor.RED);
                        cell3.setPaddingLeft(10);
                        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        PdfPCell cell4 = new PdfPCell(new Paragraph("HOURS",FontFactory.getFont(FontFactory.HELVETICA, 10)));
                        cell4.setBorderColor(BaseColor.RED);
                        cell4.setPaddingLeft(10);
                        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        PdfPCell cell5 = new PdfPCell(new Paragraph("MILES",FontFactory.getFont(FontFactory.HELVETICA, 10)));
                        cell5.setBorderColor(BaseColor.RED);
                        cell5.setPaddingLeft(10);
                        cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        PdfPCell cell6 = new PdfPCell(new Paragraph("S.TIME",FontFactory.getFont(FontFactory.HELVETICA, 10)));
                        cell6.setBorderColor(BaseColor.RED);
                        cell6.setPaddingLeft(10);
                        cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        PdfPCell cell7 = new PdfPCell(new Paragraph("F.TIME",FontFactory.getFont(FontFactory.HELVETICA, 10)));
                        cell7.setBorderColor(BaseColor.RED);
                        cell7.setPaddingLeft(10);
                        cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell7.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        PdfPCell cell8 = new PdfPCell(new Paragraph("DATE",FontFactory.getFont(FontFactory.HELVETICA, 10)));
                        cell8.setBorderColor(BaseColor.RED);
                        cell8.setPaddingLeft(10);
                        cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell8.setVerticalAlignment(Element.ALIGN_MIDDLE);


                        table.addCell(cell1);
                        table.addCell(cell2);
                        table.addCell(cell3);
                        table.addCell(cell4);
                        table.addCell(cell5);
                        table.addCell(cell6);
                        table.addCell(cell7);
                        table.addCell(cell8);

                        String sql = "SELECT * FROM job_details WHERE datesigned >= '"+fromdate+"' AND datesigned <= '"+todate+"' AND technician='"+technician+"' OR technician2='"+technician+"' ";

                    try{

                        //Connection con=DriverManager.getConnection(conString,username,password);
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://"+new JobCardTracker().database+"/jobcard","root",new JobCardTracker().password);
                        // prepared statement
                        Statement s=con.prepareStatement(sql);
                        ResultSet rs=s.executeQuery(sql);

                        //loop through getting all values
                        while(rs.next())
                        {
                            //get values
                            String client = rs.getString(1);
                            String address = rs.getString(2);
                            String jobnum = rs.getString(3);
                            String hours = rs.getString(4);
                            String miles = rs.getString(5);
                            String starttime = rs.getString(6);
                            String endtime = rs.getString(7);
                            //String newcomments = rs.getString(8);
                            //String newtechnician = rs.getString(9);
                            //String path = rs.getString(10);
                            String newdatesigned = rs.getString(11);
                            //String newdateentered = rs.getString(12);
                            //String newtechnician2 = rs.getString(14)
                                
                                
                                PdfPCell cell10 = new PdfPCell(new Paragraph(client,FontFactory.getFont(FontFactory.HELVETICA, 8)));
                                cell10.setBorderColor(BaseColor.BLUE);
                                cell10.setPaddingLeft(10);
                                cell10.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cell10.setVerticalAlignment(Element.ALIGN_MIDDLE);

                                PdfPCell cell11 = new PdfPCell(new Paragraph(address,FontFactory.getFont(FontFactory.HELVETICA, 8)));
                                cell11.setBorderColor(BaseColor.GREEN);
                                cell11.setPaddingLeft(10);
                                cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cell11.setVerticalAlignment(Element.ALIGN_MIDDLE);

                                PdfPCell cell12 = new PdfPCell(new Paragraph(jobnum,FontFactory.getFont(FontFactory.HELVETICA, 8)));
                                cell12.setBorderColor(BaseColor.GREEN);
                                cell12.setPaddingLeft(10);
                                cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cell12.setVerticalAlignment(Element.ALIGN_MIDDLE);

                                PdfPCell cell13 = new PdfPCell(new Paragraph(hours,FontFactory.getFont(FontFactory.HELVETICA, 8)));
                                cell13.setBorderColor(BaseColor.GREEN);
                                cell13.setPaddingLeft(10);
                                cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cell13.setVerticalAlignment(Element.ALIGN_MIDDLE);

                                PdfPCell cell14 = new PdfPCell(new Paragraph(miles,FontFactory.getFont(FontFactory.HELVETICA, 8)));
                                cell14.setBorderColor(BaseColor.GREEN);
                                cell14.setPaddingLeft(10);
                                cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cell14.setVerticalAlignment(Element.ALIGN_MIDDLE);

                                PdfPCell cell15 = new PdfPCell(new Paragraph(starttime,FontFactory.getFont(FontFactory.HELVETICA, 8)));
                                cell15.setBorderColor(BaseColor.GREEN);
                                cell15.setPaddingLeft(10);
                                cell15.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cell15.setVerticalAlignment(Element.ALIGN_MIDDLE);

                                PdfPCell cell16 = new PdfPCell(new Paragraph(endtime,FontFactory.getFont(FontFactory.HELVETICA, 8)));
                                cell16.setBorderColor(BaseColor.GREEN);
                                cell16.setPaddingLeft(10);
                                cell16.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cell16.setVerticalAlignment(Element.ALIGN_MIDDLE);

                                PdfPCell cell17 = new PdfPCell(new Paragraph(newdatesigned,FontFactory.getFont(FontFactory.HELVETICA, 8)));
                                cell17.setBorderColor(BaseColor.GREEN);
                                cell17.setPaddingLeft(10);
                                cell17.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cell17.setVerticalAlignment(Element.ALIGN_MIDDLE);

                                table.addCell(cell10);
                                table.addCell(cell11);
                                table.addCell(cell12);
                                table.addCell(cell13);
                                table.addCell(cell14);
                                table.addCell(cell15);
                                table.addCell(cell16);
                                table.addCell(cell17);

//                                document.add(table);

                        }

                    }catch(Exception ex){
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null,ex.toString());
                    }
                    
                    
                    document.add(table);
                    document.close();
                    writer.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
    }
    
     public Boolean updateResources(String jobnum,String resources)
    {
        String sql="UPDATE job_details SET resources='"+resources+"' WHERE jobnum='"+jobnum+"'";
        
        try
        {
            //get connection
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://"+database+"/jobcard","root",password);
            
            //statement
            Statement s=con.prepareStatement(sql);
            
            //execute
            s.execute(sql);
            
            return true;
        }catch(Exception ex)
        {
            ex.printStackTrace();
            return false;
            
            
        }
    }
    
     public String getHours(String technician,String fromdate,String todate)
     {
         String hours = "--";
         try{
             
             
            String sql="SELECT SUM(hours) FROM job_details WHERE datesigned >= '"+fromdate+"' AND datesigned <= '"+todate+"' AND technician='"+technician+"' OR technician2='"+technician+"'";
            //Connection con=DriverManager.getConnection(conString,username,password);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://"+new JobCardTracker().database+"/jobcard","root",password);
            // prepared statement
            Statement s=con.prepareStatement(sql);
            ResultSet rs=s.executeQuery(sql);
            
            //loop through getting all values
            while(rs.next())
            {
                //get values
                 hours = rs.getString(1);
                
            }
            
            return hours;
            
        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,ex.toString());
        }
         return hours;
     }
     
     
    public DefaultTableModel getJobCardData()
    {
        //ADD COLUMNS TO TABLE MODEL
        DefaultTableModel dm=new DefaultTableModel();
        dm.addColumn("CLIENT");
        dm.addColumn("ADDRESS");
        dm.addColumn("JOB NUMBER");
        dm.addColumn("HOURS"); 
        dm.addColumn("MILES");
        dm.addColumn("START TIME");
        dm.addColumn("END TIME");
        dm.addColumn("JOB DONE");
        dm.addColumn("TECHNICIAN1");
        dm.addColumn("TECHNICIAN2");
        dm.addColumn("DATE");
        dm.addColumn("RESOURCES");
        
        //SQL STATEMENT
        String sql="SELECT client,address,jobnum,hours,miles,starttime,endtime,comments,technician,datesigned,resources,technician2 FROM job_details";
        
        try{
        
            //Connection con=DriverManager.getConnection(conString,username,password);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://"+new JobCardTracker().database+"/jobcard","root",password);
            // prepared statement
            Statement s=con.prepareStatement(sql);
            ResultSet rs=s.executeQuery(sql);
            
            //loop through getting all values
            while(rs.next())
            {
                //get values
                String client = rs.getString(1);
                String address = rs.getString(2);
                String jobnum = rs.getString(3);
                String hours = rs.getString(4);
                String miles = rs.getString(5);
                String starttime = rs.getString(6);
                String endtime = rs.getString(7);
                String comments = rs.getString(8);
                String technician = rs.getString(9);
                String datesigned = rs.getString(10);
                String resources = rs.getString(11);
                String technician2 = rs.getString(12);
                
                dm.addRow(new String[]{client,address,jobnum,hours,miles,starttime,endtime,comments,technician,technician2,datesigned,resources});
            }
            return dm;
            
            
        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,ex.toString());
        }
        return null;
    }
    
    public Boolean deleteTechnician(String id)
    {
        String sql="DELETE FROM technician WHERE id='"+id+"'";
        
        try
        {
            //get connection
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://"+database+"/jobcard","root",password);
            
            //statement
            Statement s=con.prepareStatement(sql);
            
            //execute
            s.execute(sql);
            
            return true;
        }catch(Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
    
    public Boolean updateTechnician(String id,String name,String address, String phone)
    {
        //SQL STATEMENT
        try
        {
            
            //GET CONNECTION
            String sql= "UPDATE technician SET name='"+name+"',address='"+address+"',phone='"+phone+"' WHERE id='"+id+"'";
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://"+database+"/jobcard","root",password);
            
            //Prepaid statement
            Statement s=con.prepareStatement(sql);
            s.execute(sql);
            return true;
            
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return false;
    }
    
    public DefaultTableModel getTechnicianData()
    {
        //ADD COLUMNS TO TABLE MODEL
        DefaultTableModel dm=new DefaultTableModel();
        dm.addColumn("ID");
        dm.addColumn("NAME");
        dm.addColumn("ADDRESS");
        dm.addColumn("PHONE");       
        
        //SQL STATEMENT
        String sql="SELECT * FROM technician";
        
        try{
        
            //Connection con=DriverManager.getConnection(conString,username,password);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://"+new JobCardTracker().database+"/jobcard","root",password);
            // prepared statement
            Statement s=con.prepareStatement(sql);
            ResultSet rs=s.executeQuery(sql);
            
            //loop through getting all values
            while(rs.next())
            {
                //get values
                String id = rs.getString(1);
                String name = rs.getString(2);
                String address = rs.getString(3);
                String phone = rs.getString(4);
                
                dm.addRow(new String[]{id,name,address,phone});
            }
            return dm;
            
            
        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,ex.toString());
        }
        return null;
    }
    
     public Boolean addTechnician(String id,String name,String address, String phone)
    {
        //SQL STATEMENT
        try
        {
            
            //GET CONNECTION
            String sql= "INSERT INTO technician VALUES('"+id+"','"+name+"','"+address+"','"+phone+"')";
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://"+database+"/jobcard","root",password);
            
            //Prepaid statement
            Statement s=con.prepareStatement(sql);
            s.execute(sql);
            return true;
            
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return false;
    }
    
     public Boolean updateClient(String name,String project_name, String description, String number)
    {
        //SQL STATEMENT
       
        try
        {
            
            //GET CONNECTION
            String sql= "UPDATE client set name='"+name+"',project_name='"+project_name+"',description='"+description+"' WHERE number='"+number+"'";
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://"+new JobCardTracker().database+"/jobcard","root",password);
            
            //Prepaid statement
            Statement s=con.prepareStatement(sql);
            s.execute(sql);
            return true;
            
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return false;
    }
     
     public Boolean deleteClient(String number)
    {
        String sql="DELETE FROM client WHERE number='"+number+"'";
        
        try
        {
            //get connection
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://"+new JobCardTracker().database+"/jobcard","root",password);
            
            //statement
            Statement s=con.prepareStatement(sql);
            
            //execute
            s.execute(sql);
            
            return true;
        }catch(Exception ex)
        {
            ex.printStackTrace();
            return false;
            
            
        }
    }
     
     public DefaultTableModel getClientData()
    {
        //ADD COLUMNS TO TABLE MODEL
        DefaultTableModel dm=new DefaultTableModel();
        dm.addColumn("NAME");
        dm.addColumn("PROJECT");
        dm.addColumn("DESCRIPTION");
        dm.addColumn("NUMBER");       
        
        //SQL STATEMENT
        String sql="SELECT * FROM client";
        
        try{
        
            //Connection con=DriverManager.getConnection(conString,username,password);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://"+new JobCardTracker().database+"/jobcard","root",password);
            // prepared statement
            Statement s=con.prepareStatement(sql);
            ResultSet rs=s.executeQuery(sql);
            
            //loop through getting all values
            while(rs.next())
            {
                //get values
                String name = rs.getString(1);
                String project = rs.getString(2);
                String description = rs.getString(3);
                String number = rs.getString(4);
                
                dm.addRow(new String[]{name,project,description,number});
            }
            return dm;
            
            
        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,ex.toString());
        }
        return null;
    }
    
    
     public Boolean addClient(String name,String project_name, String description, String number)
    {
        //SQL STATEMENT
       
        try
        {
            
            //GET CONNECTION
            String sql= "INSERT INTO client VALUES('"+name+"','"+project_name+"','"+description+"','"+number+"')";
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://"+new JobCardTracker().database+"/jobcard","root",password);
            
            //Prepaid statement
            Statement s=con.prepareStatement(sql);
            s.execute(sql);
            return true;
            
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return false;
    }
    
    public void openJobCard(String name)
    {
        try {
            //"C:\Users\Radio Solutions\Documents\NetBeansProjects\canteen_manager\CanteenAddImage.pdf"

		File pdfFile = new File("C:\\Jobcards\\"+name+".pdf");
		if (pdfFile.exists()) {

			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(pdfFile);
			} else {
				System.out.println("Awt Desktop is not supported!");
			}

		} else {
			System.out.println("File is not exists!");
		}

		System.out.println("Done");

	  } catch (Exception ex) {
		ex.printStackTrace();
                JOptionPane.showMessageDialog(null,ex.toString());
	  }
    }
     
     public String getTodayDate()
     {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	Date date = new Date();
	System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
        
        return (dateFormat.format(date));
     }

    
     public void copyFile_Java7(String origin,String name) throws IOException {
        String destination = "C:\\Jobcards\\"+name+".pdf";
        Path FROM = Paths.get(origin);
        Path TO = Paths.get(destination);
        //overwrite the destination file if it exists, and copy
        // the file attributes, including the rwx permissions
        CopyOption[] options = new CopyOption[]{
          StandardCopyOption.REPLACE_EXISTING,
          StandardCopyOption.COPY_ATTRIBUTES
        }; 
        Files.copy(FROM, TO, options);
    }


    public Boolean rename(String original_name, String new_name )
    {
        try
        {
            File file1 = new File("Maths.docx");
 
            File file2 = new File("Renamed.docx");
            
            boolean success = file1.renameTo(file2);
            
            if (!success) {
            System.out.println("Error trying to rename file");
            }
            
            return true;
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return false;
    }
    
    public Boolean addjob_details(String client,String address,String jobnum,double  hours,double miles,String starttime,String endtime,String comments,String technician,String path,String datesigned,String dateentered,String technician2)
    {
        //SQL STATEMENT
        try
        {
            
            //GET CONNECTION
            String sql= "INSERT INTO job_details VALUES('"+client+"','"+address+"','"+jobnum+"','"+hours+"','"+miles+"','"+starttime+"','"+endtime+"','"+comments+"','"+technician+"','"+path+"','"+datesigned+"','"+dateentered+"','None','"+technician2+"')";
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://"+database+"/jobcard","root",password);
            
            //Prepaid statement
            Statement s=con.prepareStatement(sql);
            s.execute(sql);
            return true;
            
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return false;
    }
    
     public Boolean updatejob_details(String client,String address,String jobnum,double  hours,double miles,String starttime,String endtime,String comments,String technician,String path,String datesigned,String technician2)
    {
        //SQL STATEMENT
        try
        {
            
            //GET CONNECTION
            String sql= "UPDATE job_details SET client='"+client+"', address='"+address+"',hours='"+hours+"',miles='"+miles+"',starttime='"+starttime+"',endtime='"+endtime+"',comments='"+comments+"',technician='"+technician+"',datesigned='"+datesigned+"',technician2='"+technician2+"' WHERE jobnum='"+jobnum+"'";
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://"+database+"/jobcard","root",password);
            
            //Prepaid statement
            Statement s=con.prepareStatement(sql);
            s.execute(sql);
            return true;
            
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return false;
    }
    
      
    public Boolean deleteJobCard(String jobnum)
    {
        String sql="DELETE FROM job_details WHERE jobnum='"+jobnum+"'";
        
        try
        {
            //get connection
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://"+database+"/jobcard","root",password);
            
            //statement
            Statement s=con.prepareStatement(sql);
            
            //execute
            s.execute(sql);
            
            return true;
        }catch(Exception ex)
        {
            ex.printStackTrace();
            return false;
            
            
        }
    }
    
      public Boolean update_CardPath(String jobnum,String path)
    {
        //SQL STATEMENT
        try
        {
            
            //GET CONNECTION
            String sql= "UPDATE job_details SET path='"+path+"' WHERE jobnum='"+jobnum+"'";
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://"+database+"/jobcard","root",password);
            
            //Prepaid statement
            Statement s=con.prepareStatement(sql);
            s.execute(sql);
            return true;
            
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return false;
    }
      
    
    public static void main(String[] args) throws IOException {
       //new JobCardTracker().showSplash();
       
       viewJobcards vj = new viewJobcards();
       vj.setTitle("RS Job Card Tracker 1.0.1");
       vj.setVisible(true); 
       vj.setExtendedState(vj.MAXIMIZED_BOTH);
        
       jobSaver fc = new jobSaver();
       fc.setTitle("RS Job Card Tracker 1.0.1");
       fc.setVisible(true);
       
       
//        
//        File file1 = new File("Maths.docx");
// 
//        File file2 = new File("Renamed.docx");
//
//        boolean success = file1.renameTo(file2);
//        if (!success) {
//            System.out.println("Error trying to rename file");
//        }
//        String original_name = "Maths.docx";
//        String new_name  = "Renamed.docx";
//        String origin = "C:\\Users\\Radio Solutions\\Documents\\NetBeansProjects\\jobCardTracker\\Renamed.docx";
//        String destination = "C:\\Users\\Radio Solutions\\Documents\\Renamed.docx";
//        
//        //new JobCardTracker().rename(original_name, new_name);
//        new JobCardTracker().copyFile_Java7(origin,destination);
        
//        fileChoose fc = new fileChoose();
//        fc.setVisible(true);
//        
       
    }

   

   

    
    
}
