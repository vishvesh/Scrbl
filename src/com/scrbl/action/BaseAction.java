package com.scrbl.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements ServletRequestAware {

	/**
	 * @author Vishvesh Deshmukh
	 * Created : 2nd February, 2013
	 * Project : Scrbl
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String pageX;
	private String pageY;
	private String timeArray;
	private File file;
	private String pathname = "";
	private String nameOfFile = "X-Y-Coordinates.xls";

	public String firstBlood()
	{
		name = "Welcome To Scrbl!";
		/*String name = System.getProperty("user.dir")+File.separator+BaseAction.class.getPackage().getName()+File.separator;
        System.out.println("User dir name : "+name);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        //URL url = classLoader.getResource("/WebContent/css/home.css");
        try {
			//System.out.println("URL : "+url.toURI());
        	pathname = (String)getClass().getResource("").toURI().toString();
			System.out.println("Different way : "+pathname+"X-Y-Coordinates.xls");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
		return SUCCESS;
	}
	
	public String writeValuesToExcel()
	{
		System.out.println("Page X : "+pageX);
		System.out.println("Page Y : "+pageY);
		writeToExcel(pageX,pageY);
		return SUCCESS;
	}
	
	private void writeToExcel(String pageX, String pageY)
	{
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Sample sheet");
		 
		Map<Integer, Object[]> data = new TreeMap<Integer, Object[]>();
		data.put(0, new String[] {"X-Coordinate", "Y-Coordinate", "Time"});
		String[] pagex = pageX.split(",");
		String[] pagey = pageY.split(",");
		int counter = 1;
		
		while(counter <= pagex.length)
		{
			//System.out.print("Counter : "+counter + " ");
			data.put(counter, new String[] {pagex[counter - 1], pagey[counter - 1], Integer.toString(counter)});
			counter++;
		}
		/*Iterator it = data.entrySet().iterator();
		while (it.hasNext()) {
		Map.Entry pairs = (Map.Entry)it.next();
		 System.out.println(pairs.getKey()+",");
		}*/
		SortedSet<Integer> keySet = new TreeSet<Integer>(data.keySet());
		//List<Integer> keySet=new ArrayList(data.keySet());
		//Collections.sort(keySet);
		//keySet =  (TreeSet<String>) data.keySet();
		int rownum = 0;
		for (int key : keySet) {
			//System.out.println("KEYSET KEY : "+key);
		    Row row = sheet.createRow(rownum++);
		    String [] objArr = (String[]) data.get(key);
		    int cellnum = 0;
		    for (String obj : objArr) {
		        Cell cell = row.createCell(cellnum++);
		        //System.out.println("String is : "+obj);
		        if(obj instanceof String)
		            cell.setCellValue((String)obj);
		    }
		}
		 
		try {
			file = new File(pathname+nameOfFile);
			System.out.println("File Path : "+(pathname+nameOfFile));
			System.out.println("canonical path : "+file.getCanonicalPath()+ " : abs path : "+ file.getAbsolutePath() +" : path : "+ file.getPath());
		    FileOutputStream out = new FileOutputStream(file);
		    workbook.write(out);
		    out.close();
		    System.out.println("Excel written successfully..");
		    sendEmailWithAttachment();
		    
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		 
	}
	
	private void sendEmailWithAttachment()
	{
		/*if (args.length != 5) 
		  {
		      System.out.println("usage: java sendfile <to> <from> <smtp> <file>");
		      System.exit(1);
		  }*/

		  	String subject = "X-Y-Coordinates";
		  	String host = "smtp.gmail.com";
	        String Password = "9869161525";
	        String from = "fragbait14@gmail.com";
	        //String toAddress = "deshmukh.vish@yahoo.com";
	        //String emailAddresses = new String("fragbait14@gmail.com, amitshob@gmail.com");
	        String emailAddresses = new String("fragbait14@gmail.com");
	        //String toAddress = "amitshob@gmail.com";
	        String filename = pathname+nameOfFile;
	        //System.out.println("File Path in sendemailwithattachment : "+(pathname+nameOfFile));
	        // Get system properties
	        Properties props = System.getProperties();
	        props.put("mail.smtp.host", host);
	        props.put("mail.smtps.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        
	        try {
		        Session session = Session.getInstance(props, null);
		        MimeMessage message = new MimeMessage(session);
		        message.setFrom(new InternetAddress(from));
		        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddresses));
		        message.setSubject(subject);
	
		        BodyPart messageBodyPart = new MimeBodyPart();
		        messageBodyPart.setText("Here's the file");
	
		        Multipart multipart = new MimeMultipart();
		        multipart.addBodyPart(messageBodyPart);
		        messageBodyPart = new MimeBodyPart();
	
		        DataSource source = new FileDataSource(filename);
		        messageBodyPart.setDataHandler(new DataHandler(source));
		        messageBodyPart.setFileName(filename);
		        multipart.addBodyPart(messageBodyPart);	
		        message.setContent(multipart);
	
	            Transport tr = session.getTransport("smtps");
	            tr.connect(host, from, Password);
	            tr.sendMessage(message, message.getAllRecipients());
	            System.out.println("Mail Sent Successfully");
	            tr.close();
	        } catch (Exception sfe) {
	            System.out.println(sfe);
	        }
	}
	
	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		
	}
	
	public String getPageX() {
		return pageX;
	}

	public void setPageX(String pageX) {
		this.pageX = pageX;
	}

	public String getPageY() {
		return pageY;
	}

	public void setPageY(String pageY) {
		this.pageY = pageY;
	}

	public String getTimeArray() {
		return timeArray;
	}

	public void setTimeArray(String timeArray) {
		this.timeArray = timeArray;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}