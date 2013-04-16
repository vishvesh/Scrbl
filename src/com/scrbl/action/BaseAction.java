package com.scrbl.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
import org.apache.struts2.interceptor.SessionAware;

//import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensymphony.xwork2.ActionSupport;
import com.scrbl.model.Figure;
import com.scrbl.model.Point;
import com.scrbl.model.Stroke;

public class BaseAction extends ActionSupport implements ServletRequestAware, SessionAware {

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
	private String ci;
	private File file;
	private String nameOfFile = getText("email.nameOfFile");
	//private List<Object> pointArray;
	private String pointArray;
	//private List<Point> stroke;
	private List<Integer> points;
	
	private Figure figure;
	private Stroke stroke;
	private Figure template;
	private String matchedValue;
	private Map<String, Object> sessionMap;
	protected HttpServletRequest request;

	@Override
	public void setSession(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}
	
	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;
	}
	
	protected Object getValueBySessionAttribute(String sessionAttribute) {
		return request.getSession().getAttribute(sessionAttribute);
	}
	
	protected void setValueBySessionAttribute(String sessionAttributeName, Object value)
	{
		request.getSession().setAttribute(sessionAttributeName, value);
	}
	
	protected void removeSessionAttribute(String sessionAttributeName)
	{
		request.getSession().removeAttribute(sessionAttributeName);
	}
	
	protected void setRequestAttribute(String requestAttributeName, Object value) {
		request.getSession().setAttribute(requestAttributeName, value);
	}
	
	protected Object getRequestAttribute(String key) {
		return request.getSession().getAttribute(key);
	}

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
		//System.out.println(getText("emailfile.nameOfFile"));
        
		return SUCCESS;
	}

	private void compute() {
		figure = new Figure();
		
		pointArray = pointArray.replace("[", "").replace("]", "");
		System.out.println("Point Array : "+pointArray);
		//String[] splitString = pointArray.split("(d+),(d+)(,)?");
		//String[] splitString = pointArray.split("([d+,d+])(,)?");
		
		String[] splitString = pointArray.split("(,0,0)(,)?");
		
	    System.out.println(splitString.length);
	    points = new ArrayList<Integer>();
	    //stroke = new ArrayList<Point>();
	    
	    for (String string : splitString) {
	    	if (stroke == null)
			{
	    		System.out.println("STROKE NULL");
				stroke = new Stroke();
				figure.Add(stroke);
			}
			//stroke.Add(new Point(args.X, args.Y));

	    	System.out.println("COMPLETE STROKE ARRAY : "+string);
	    	String[] theString = string.split("([d+,d+])(,)?");
	    	for (String allPoints : theString) {
	    		//System.out.println("APP POINTS : "+Integer.valueOf(allPoints));
	    		points.add(Integer.valueOf(allPoints));
			}
	    	for(int i = 0, j = i + 1; i < points.size() - 1; i+=2, j+=2)
		    {
		    	Point point = new Point(points.get(i), points.get(j));
		    	//System.out.println("X : "+point.getX() + " : Y : "+point.getY() ); 
		    	stroke.Add(point);
		    	
		    }
	    	//figure.Add(stroke);
	    	
	    	if (stroke != null)
			{
	    		System.out.println("STROKE NOT NULL");
				stroke = null;
				figure.CurveLastStroke();
			}
	    	//figure.Add(stroke);
	    	System.out.println("1st Stroke Completed!");
	    }
	}
	
	public String saveFigure() {

		compute();

	    sessionMap.put("figure", figure);
	    
	    setRequestAttribute("figure", figure);
	    System.out.println("Session MAP Size : "+sessionMap.size());
	    
	    System.out.println("GET FIGURES STROKES LENGTGH : "+getFigure().getLength() + " : Curves LENGTH : "+getFigure().getCurvesLength());
		return SUCCESS;
	}
	
	public String matchFigure()
	{
		try{
		//System.out.println(figure.getLength());
			compute();
			//Figure template = (Figure) sessionMap.get("figure");
			//System.out.println("INSIDE MATCH : SESSIONMAP : "+sessionMap.size());
			
			Figure template = (Figure) getRequestAttribute("figure");
			matchedValue = (new Double(template.Match(getFigure()))).toString();
			System.out.println("Matched VALUE : "+matchedValue);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//matchedValue = (new Double(getTemplate().Match(getFigure()))).toString();
		//System.out.println("Matched VALUE : "+matchedValue);
		//System.out.println("template : "+template.getLength() + " : Figure : "+figure.getLength());
		
		return SUCCESS;
	}
	
	public String writeValues()
	{
		//for (Object element : pointArray) {
		/*String[] points = pointArray.split(",");
		for (String point : points) {
			System.out.println("Point is : "+point);
		}*/
		/*compute();
	    
	    setFigure(figure);
	    sessionMap.put("figure", figure);
	    
	    System.out.println("Session MAP Size : "+sessionMap.size());
	    
	    System.out.println("GET FIGURES STROKES LENGTGH : "+getFigure().getLength() + " : Curves LENGTH : "+getFigure().getCurvesLength());*/
	    
	    /*for(int i = 0, j = i + 1; i < points.size() - 1; i+=2, j+=2)
	    {
	    	Point point = new Point(points.get(i), points.get(j));
	    	//System.out.println("X : "+point.getX() + " : Y : "+point.getY() );
	    	stroke.add(point);
	    }*/
	    
	    /*for (Point value : stroke) {
			System.out.println("STROKE : "+value);
		}*/
		
		/*ObjectMapper mapper = new ObjectMapper();
		try {
			// read from file, convert it to user class
			//User user = mapper.readValue(new File("c:\\user.json"), User.class);	 
			// display to console
			//System.out.println(user);	 
		} catch (Exception e) {	 
			e.printStackTrace();
		}*/
	 
		//}
		//writeToExcel(pageX.replace("[", "").replace("]", ""), pageY.replace("[", "").replace("]", ""), timeArray.replace("[", "").replace("]", ""));
		//System.out.println("Page X : "+pageX.replace("[", "").replace("]", ""));
		//System.out.println("Page Y : "+pageY.replace("[", "").replace("]", ""));
		
		/*String replacedX = pageX.replaceAll(",", " ").replace("[", "").replace("]", "");
		System.out.println("Replaced PAGE X : "+replacedX);
		String[] pagex = replacedX.split("\\s0");
		for (String stringx : pagex) {
			String x = stringx;
			String[] splitStringX  = x.split(" ");
			for (String string : splitStringX) {
				System.out.print(" : splitString : "+string);
			}
			System.out.println();
			System.out.println("PAGE X : "+stringx);
		}
		
		String replacedY = pageY.replaceAll(",", " ").replace("[", "").replace("]", "");
		System.out.println("Replaced PAGE Y : "+replacedY);
		String[] pagey = replacedY.split("\\s0");
		for (String stringy : pagey) {
			System.out.println("PAGE Y : "+stringy);
		}*/
		//System.out.println("Time Array : "+timeArray.replace("[", "").replace("]", ""));
		//System.out.println("Client IP Address : "+ci);
		return SUCCESS;
	}
	
	private void writeToExcel(String pageX, String pageY, String timeArray)
	{
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat.format(cal.getTime()));
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Sample sheet");
		 
		Map<Integer, Object[]> data = new TreeMap<Integer, Object[]>();
		data.put(0, new String[] {"X-Coordinate", "Y-Coordinate", "Time", "IP Address : "+ci, "Date & Time : "+dateFormat.format(cal.getTime())});
		String[] pagex = pageX.split(",");
		String[] pagey = pageY.split(",");
		String[] timearray = timeArray.split(",");

		int counter = 1;
		
		while(counter <= pagex.length)
		{
			//System.out.print("Counter : "+counter + " ");
			data.put(counter, new String[] {pagex[counter - 1], pagey[counter - 1], timearray[counter - 1]});
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
			file = new File(nameOfFile);
			//System.out.println("File Path : "+(nameOfFile));
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
	  	String emailSubject = getText("email.emailSubject");
	  	String emailHost = getText("email.emailHost");
        String emailPassword = getText("email.emailPassword");
        String emailFrom = getText("email.emailFrom");
        String emailBody = getText("email.emailBody");
        //String toAddress = "deshmukh.vish@yahoo.com";
        //String emailAddresses = new String("fragbait14@gmail.com, amitshob@gmail.com");
        String emailAddresses = getText("email.emailAddresses");
        //String toAddress = "amitshob@gmail.com";
        String filename = nameOfFile;
        //System.out.println("File Path in sendemailwithattachment : "+(pathname+nameOfFile));
        // Get system properties
        Properties props = System.getProperties();
        props.put("mail.smtp.host", emailHost);
        props.put("mail.smtps.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        try {
	        Session session = Session.getInstance(props, null);
	        MimeMessage message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(emailFrom));
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddresses));
	        message.setSubject(emailSubject);

	        BodyPart messageBodyPart = new MimeBodyPart();
	        messageBodyPart.setText(emailBody);

	        Multipart multipart = new MimeMultipart();
	        multipart.addBodyPart(messageBodyPart);
	        messageBodyPart = new MimeBodyPart();

	        DataSource source = new FileDataSource(filename);
	        messageBodyPart.setDataHandler(new DataHandler(source));
	        messageBodyPart.setFileName(filename);
	        multipart.addBodyPart(messageBodyPart);	
	        message.setContent(multipart);

            Transport tr = session.getTransport("smtps");
            tr.connect(emailHost, emailFrom, emailPassword);
            tr.sendMessage(message, message.getAllRecipients());
            System.out.println("Mail Sent Successfully");
            tr.close();
        } catch (Exception sfe) {
            System.out.println(sfe);
        }
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
	
	public void setMatchedValue(String matchedValue) {
		this.matchedValue = matchedValue;
	}
	
	public String getMatchedValue() {
		return matchedValue;
	}
	
	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}
	
	public Stroke getStroke() {
		return stroke;
	}
	
	public void setTemplate(Figure template) {
		this.template = template;
	}
	
	public Figure getTemplate() {
		return template;
	}
	
	public void setFigure(Figure figure) {
		this.figure = figure;
	}
	
	public Figure getFigure() {
		return figure;
	}
	
	public void setPoints(List<Integer> points) {
		this.points = points;
	}
	
	public List<Integer> getPoints() {
		return points;
	}
	
	public void setCi(String ci) {
		this.ci = ci;
	}
	
	public String getCi() {
		return ci;
	}
	
	/*public void setStroke(List<Point> stroke) {
		this.stroke = stroke;
	}
	
	public List<Point> getStroke() {
		return stroke;
	}*/
	
	public void setPointArray(String pointArray) {
		this.pointArray = pointArray;
	}
	
	public String getPointArray() {
		return pointArray;
	}
	
	
}