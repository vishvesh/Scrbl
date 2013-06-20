package com.scrbl.action;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.json.annotations.JSON;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensymphony.xwork2.ActionSupport;
import com.scrbl.logic.CosineSimilarity;
import com.scrbl.logic.Figure;
import com.scrbl.logic.Stroke;
import com.scrbl.logic.VelocityVector;
import com.scrbl.model.Point;
import com.scrbl.model.Users;
import com.scrbl.service.UsersService;

public class BaseAction extends ActionSupport implements ServletRequestAware,ServletResponseAware, SessionAware {

	/**
	 * @author Vishvesh Deshmukh
	 * Created : 2nd February, 2013
	 * Project : Scrbl
	 */
	private static final long serialVersionUID = 1L;
	protected Logger logger = Logger.getLogger(getClass());
	
	private String name;
	private String pageX;
	private String pageY;
	private String timeArray;
	private String ci;
	private File file;
	private String nameOfFile = getText("email.nameOfFile");
	private String informedConsentPdf = getText("informedConsentPdf");
	//private List<Object> pointArray;
	private String pointArray;
	//private List<Point> stroke;
	private List<Double> points;
	
	private Figure figure;
	private Stroke stroke;
	private Figure template;
	private String matchedValue;
	
	@SuppressWarnings("unused")
	private Map<String, Object> sessionMap;
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	
	private String userFirstName;
	private String userLastName;
	private String userEmail;
	private String ageGroup;
	private List<Double> velocityVector;
	private int currentNumberOfStrokes;
	private InputStream inputStream;
	private String host;
	private String base64ImageUrl;
	private UsersService usersService;
	private String imageUrl;
	private String deviceName;
	private double percentageThresholdFromConfig;
	private String b;
	private String o;
	
	public void setB(String b) {
		this.b = b;
	}
	
	public String getB() {
		return b;
	}
	
	public void setO(String o) {
		this.o = o;
	}
	
	public String getO() {
		return o;
	}
	
	
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
	public String getDeviceName() {
		return deviceName;
	}
	
	public void setPercentageThresholdFromConfig(double percentageThresholdFromConfig) {
		this.percentageThresholdFromConfig = percentageThresholdFromConfig;
	}
	
	public double getPercentageThresholdFromConfig() {
		return percentageThresholdFromConfig;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}

	@Override
	public void setSession(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}
	
	@Override
	public void setServletResponse(HttpServletResponse servletResponse) {
		this.response = servletResponse;
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
		if(request.getSession().getAttribute(sessionAttributeName) != null)
			request.getSession().removeAttribute(sessionAttributeName);
	}
	
	public String viewPdf() throws Exception {
		try {
			if(host.startsWith("localhost"))
				logger.info("Is Development : Host : "+host);
			else
				logger.info("Is Production : Host : "+host);

			URL url = getClass().getResource(informedConsentPdf);
			File file = new File(url.getPath());
			logger.info("Informed Consent's abs file path : "+file.getAbsolutePath());
			
			inputStream = new DataInputStream(new FileInputStream(file));
			
		} catch (IOException ioEx) {
			ioEx.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String saveUserDetails() throws Exception {
		logger.info("Inside saveUserDetails() : User's First Name : "+userFirstName +" : User's Last Name : "+ userLastName +
				" : \nUser's Email : "+userEmail + " : Age Group : "+ageGroup + " : Ip Address : "+ci+" : deviceName : "+deviceName);
		
		Users users = new Users();
		users.setFirstName(userFirstName);
		users.setLastName(userLastName);
		users.setEmail(userEmail);
		users.setAgeGroup(ageGroup);
		users.setIpAddress(ci);
		users.setDeviceName(deviceName);
		users.setCreatedAt(new Date());
		users.setLastUpdatedAt(new Date());
		
		logger.info(users.toString());

		//Check if the user already exists!
		Users user = usersService.getUserByEmail(userEmail);
		
		if(user == null) {
			logger.info("User Not Found! So saving new user!");
			Users newUser = usersService.saveNewUser(users);
			setValueBySessionAttribute("sessionUser", newUser.getEmail());
			
			setCookie(response, "userEmailFromCookie", newUser.getEmail());
		} else {
			logger.info("User already present : User Email : "+user.getEmail());
			setValueBySessionAttribute("sessionUser", user.getEmail());
			
			setCookie(response, "userEmailFromCookie", user.getEmail());
		}
		
		
		//List<Users> allUsers = usersService.getAllUsers();
		//logger.info("Size  : "+allUsers.size());
		
		return super.execute();
	}
	
	private String getUserFromCookieAndForwardToInstructions() throws Exception {
		String email = getCookie(request, "userEmailFromCookie");
		if(null != email) {
			logger.info("UserEmail found in Cookie : "+email+" : Forwarding user to stepWiseInstructions!");
			setValueBySessionAttribute("sessionUser", email);
			return "forwardToInstructions";
		} else {
			return super.execute();
		}
	}

	@Override
	public String execute() throws Exception {
		//logger.info("Comes inside Execute method!");
		return super.execute();
		//return getUserFromCookieAndForwardToInstructions();
	}
	
	public String firstBlood() throws Exception
	{
		//name = "Welcome To Scrbl!";
		logger.info("Comes inside firstBlood() method!");
		return getUserFromCookieAndForwardToInstructions();
		
		/*String email = getCookie(request, "userEmailFromCookie");
		if(null != email) {
			logger.info("UserEmail found in Cookie : "+email+" : Forwarding user to stepWiseInstructions!");
			setValueBySessionAttribute("sessionUser", email);
			return "forwardToInstructions";
		}*/
		
		/*String name = System.getProperty("user.dir")+File.separator+BaseAction.class.getPackage().getName()+File.separator;
        logger.info("User dir name : "+name);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        //URL url = classLoader.getResource("/WebContent/css/home.css");
        try {
			//logger.info("URL : "+url.toURI());
        	pathname = (String)getClass().getResource("").toURI().toString();
			logger.info("Different way : "+pathname+"X-Y-Coordinates.xls");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//logger.info(getText("emailfile.nameOfFile"));
        
		//return SUCCESS;
	}
	
	
	/**
	 * Setting the User's Email in the cookie
	 * which lasts for 1 year.... So that he doesn't have
	 * to fill in all his details once again!
	 * @param response
	 * @param name
	 * @param value
	 */
	public static void setCookie(HttpServletResponse response, String name, String value) {
	    try {
	        Cookie div = new Cookie(name, value);
	        div.setMaxAge(365 * 60 * 60 * 24); // Make the cookie last a year
	        response.addCookie(div);
	        System.out.println("Setting cookie with name : "+name+" : Value : User Email : "+value);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
	/**
	 * Returns the User's Email from the stored cookies so
	 * that we take that as the input and check if the user exists, 
	 * and then authenticate & forward the user to the appropriate page..
	 * This way he doesn't have to re-enter all his details!
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String name) {
	    String value = null;
	    try {
	    	if(null != request.getCookies()) 
	    	{
		        for (Cookie c : request.getCookies()) {
		            if (c.getName().equals(name)) {
		                value = c.getValue();
		            }
		        }
	    	} else {
	    		System.out.println("Cookies not found!");
	    	}
	    } catch (Exception e) {
	        //Logger.getLogger(StrutsUtils.class.getName()).log(Level.INFO, "message", e);
	    	e.printStackTrace();
	    }
	    return value;
	}
	
	
	public String startScribbling() {
		logger.info("Forwarding User Start Scribbling!");
		return SUCCESS;
	}
	
	@JSON
	public String getBase64ImageUrlFromDB() throws Exception {
		try {
			String email = (String) getValueBySessionAttribute("sessionUser");
			if (email != null) {
				//Check if the user already exists!
				Users user = usersService.getUserByEmail(email);
				if (user != null) {
					imageUrl = user.getTemplateBase64ImageUrl();
					logger.info("Image URL : "+imageUrl +" For User : "+user.getFirstName() + " "+user.getLastName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private List<Point> compute(boolean overrideValues) {
		List<Point> listOfPoints = null;
		try {
			figure = new Figure();
			
			
			pointArray = pointArray.replace("[", "").replace("]", "");
			logger.info("Point Array : "+pointArray);
			
			//TODO: Computing Velocity Vector using Euclidean Distance!
			String timeArr = timeArray.replace("[", "").replace("]", "").replaceAll(",0", "");
			logger.info("TIME ARRAY : "+timeArr);
			String[] timeSplit = timeArr.split(",");

			String pointString = pointArray.replaceAll("(,0,0)", "");
			logger.info("Point STRING : "+pointString);
			
			listOfPoints = new ArrayList<Point>();
			
			String[] pointArr = pointString.split(",");
			
			velocityVector = new ArrayList<Double>();

			for(int i = 0, j = i + 1, t = 0; i < pointArr.length - 1; i+=2, j+=2, t++) {
				Point point = new Point(Double.valueOf(pointArr[i]), Double.valueOf(pointArr[j]), Double.valueOf(timeSplit[t]));
				listOfPoints.add(point);
			}
			
			/*for (Point point : listOfPoints) {
				logger.info(point.toString());
			}*/
			
			VelocityVector velocityVectorObj = new VelocityVector();
			//Calculate Velocity Vector using Euclidean Distance Formula!
			velocityVector = velocityVectorObj.calculateVelocityVector(listOfPoints, timeSplit, velocityVector);
					
			logger.info("Velocity Vector's Size : "+velocityVector.size());
			
			logger.info("TIME LENGTH : "+timeSplit.length + " POINT ARR LENGTH : "+listOfPoints.size());
			//TODO: Computing Velocity Vector using Euclidean Distance!
			
						
			//String[] splitString = pointArray.split("(d+),(d+)(,)?");
			//String[] splitString = pointArray.split("([d+,d+])(,)?");
			
			String[] splitString = pointArray.split("(,0,0)(,)?");
			logger.info("splitString LENGTH : " +splitString.length);
			
			//if(getValueBySessionAttribute("numberOfStrokes") == null)
			if(overrideValues)
				setValueBySessionAttribute("numberOfStrokes", splitString.length);
			
			currentNumberOfStrokes = splitString.length;
			
			points = new ArrayList<Double>();
			//stroke = new ArrayList<Point>();
			
			if(splitString != null && !splitString.equals("") && currentNumberOfStrokes > 0) {
			    for (String string : splitString) {
			    	if (stroke == null)
					{
			    		logger.info("STROKE NULL");
						stroke = new Stroke();
					}
					//stroke.Add(new Point(args.X, args.Y));

			    	logger.info("COMPLETE STROKE ARRAY : "+string);
			    	String[] theString = string.split("([d+,d+])(,)?");
			    	for (String allPoints : theString) {
			    		//logger.info("APP POINTS : "+Integer.valueOf(allPoints));
			    		if(allPoints != null)
			    			points.add(Double.valueOf(allPoints));
					}
			    	for(int i = 0, j = i + 1; i < points.size() - 1; i+=2, j+=2)
				    {
				    	Point point = new Point(points.get(i), points.get(j));
				    	//logger.info("X : "+point.getX() + " : Y : "+point.getY() ); 
				    	stroke.Add(point);
				    	
				    }
			    	figure.Add(stroke);
			    	
			    	if (stroke != null)
					{
			    		logger.info("STROKE NOT NULL");
						stroke = null;
						figure.CurveLastStroke();
					}
			    	//figure.Add(stroke);
			    	logger.info("Stroke Completed!");
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Exception : "+e);
		}
		return listOfPoints;
	}
	
	public String saveFigure() throws Exception {

		logger.info("Inside Save Figure() : Printing Template Data : --v");
		
		List<Point> templateData = compute(true);
		
		if(templateData.size() > 0) {
			//**Convert List to JSON....
			final ObjectMapper mapper = new ObjectMapper();
			final String templateDataString = mapper.writeValueAsString(templateData);
			logger.info("TEMPLATE DATA : "+templateDataString);
			logger.info("TEMPLATE DATA LENGTH : "+templateDataString.length());
			
			try {
				String email = (String) getValueBySessionAttribute("sessionUser");
				if (email != null) {
					//Check if the user already exists!
					Users user = usersService.getUserByEmail(email);
					if (user != null) {
						user.setTemplateData(templateDataString);
						user.setLastUpdatedAt(new Date());
						user.setTemplateBase64ImageUrl(base64ImageUrl);
						user.setBrowserName(b);
						user.setOperatingSystem(o);
						usersService.saveNewUser(user);
						logger.info("TEMPLATE Data Saved for User : "+user.getFirstName() + " "+user.getLastName());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			//**** FOR Converting back to LIST Object****
			//List<Point> x = mapper.readValue(data, new TypeReference<List<Object>>() {});
			//logger.info("X SIZEEEEEEE : "+x.size());
			
			for (Point point : templateData) {
				point.toString();
			}
	
			//TODO: Removed the sessionMap attribute.... Not needed anymore as setValueBySessionAttribute() takes care of everything!
			//Maybe we can remove the "implements SessionAware" from the class declaration line!
		    //sessionMap.put("figure", figure);
		    
		    setValueBySessionAttribute("figure", figure);
		    setValueBySessionAttribute("velocityVector", velocityVector);
		    //logger.info("Session MAP Size : "+sessionMap.size() + " : Velocity Vector's Size : "+velocityVector.size());
		    logger.info("Velocity Vector's Size : "+velocityVector.size());
		    
		    //writeToExcel(pageX.replace("[", "").replace("]", ""), pageY.replace("[", "").replace("]", ""), timeArray.replace("[", "").replace("]", ""));
		    
		    logger.info("GET FIGURES STROKES LENGTH : "+getFigure().getLength() + " : Curves LENGTH : "+getFigure().getCurvesLength());
		}
		return SUCCESS;
	}
	
	public String clean() {
		try {
			pageX = null;
			pageY = null;
			timeArray = null;
			figure = null;
			stroke = null;
			template = null;
			if(velocityVector != null && velocityVector.size() > 0)
				velocityVector.clear();
			/*removeSessionAttribute("figure"); //null checks are handled inside the method!
			removeSessionAttribute("velocityVector"); //null checks are handled inside the method!
			removeSessionAttribute("numberOfStrokes");*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String matchFigure()
	{
		double cosineSimilarityValue = 0.0;
		Users user = null;
		String email = null;
		String matchedValue = null;
		
		try {
			email = (String) getValueBySessionAttribute("sessionUser");
			
			if(email != null) {
				//Check if the user already exists!
				user = usersService.getUserByEmail(email);
				
				if(user != null) {
					
					user.setLastUpdatedAt(new Date());
					
					//Get the Match Data <-- List of Points to match against the saved Session Template!
					List<Point> matchData = compute(false); //Not to override values, as we match two figures!
					
					if(matchData.size() > 0) {
						//**Convert List to JSON....
						final ObjectMapper mapper = new ObjectMapper();
						final String matchDataString = mapper.writeValueAsString(matchData);
						logger.info("MATCH DATA : "+matchDataString);
						
						user.setMatchData(matchDataString);
						
						logger.info("Executing Logic to Match the Two Figures!!!!!");
						Figure template = (Figure) getValueBySessionAttribute("figure");
						//logger.info("GIFURE L "+getFigure());
						if(!getFigure().equals(null) || getFigure() != null) {
							matchedValue = (new Double(template.Match(getFigure()))).toString();
							logger.info("MATCH VALUE CALCULATED SUCESSFULY : "+matchedValue);
						}
						else {
							logger.info("Comes in Else part.. Setting matched value to 10000.00.. Because the Template was NULL");
							matchedValue = "10000.00";
						}
						logger.info("**********************************************************************************************************");
						System.out.println();
						
						user.setShpValue(matchedValue);
						
						logger.info("MATCH Data Saved for User : "+user.getFirstName() + " "+user.getLastName());
					}
				}				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {
			try {
				if(user != null) {
					int numberOfStrokes = (Integer) getValueBySessionAttribute("numberOfStrokes");
					
					logger.info("Session Template's numberOfStrokes : "+ numberOfStrokes);
					logger.info("Current Figure's currentNumberOfStrokes : "+ currentNumberOfStrokes);
					
					if (currentNumberOfStrokes == numberOfStrokes 
							|| currentNumberOfStrokes == numberOfStrokes + 2 
								|| currentNumberOfStrokes == numberOfStrokes - 2
									|| currentNumberOfStrokes == numberOfStrokes + 1
										|| currentNumberOfStrokes == numberOfStrokes - 1) {
						logger.info("currentNumberOfStrokes | numberOfStrokes : So executing the CosineSimilarity Logic!");

						@SuppressWarnings("unchecked")
						List<Double> initialVelocityVector = (List<Double>) getValueBySessionAttribute("velocityVector");

						System.out.println();
						logger.info("**********************************************************************************************************");
						logger.info("Session Velocity Vector's size : "+ initialVelocityVector.size()+ " : Current velocity vectors' size : "
								+ velocityVector.size());
						int difference = Math.abs(initialVelocityVector.size() - velocityVector.size());
						logger.info("Difference between the two VECTORS = : "+difference);

						System.out.println();
						double percentageThresholdValue = 0;

						if (initialVelocityVector.size() >= velocityVector.size()) {
							percentageThresholdValue = initialVelocityVector.size() * percentageThresholdFromConfig;
							logger.info("Session Velocity Vector's Size : IS GREATER THAN OR EQUAL TO : Current Velocity Vector's Size");
						} else {
							percentageThresholdValue = velocityVector.size() * percentageThresholdFromConfig;
							logger.info("Current Velocity Vector's Size : IS LESS THAN : Session Velocity Vector's Size");
						}

						System.out.println();
						logger.info("Percentage Threshold to be Multiplied : "+ percentageThresholdFromConfig);
						logger.info("Percentage Threshold Value After Calculation(Vector size * threshold) : "+ percentageThresholdValue);
						System.out.println();
						logger.info("Difference between Vectors : "+ difference+ " : Percentage Threshold Value : "+ percentageThresholdValue);
						System.out.println();

						if (difference <= percentageThresholdValue) {
							logger.info("Difference b/w vectors : IS LESS THAN OR EQUAL TO : percentageThresholdValue : " +
									"So Calculating Cosine Similarity!");
							logger.info("**********************************************************************************************************");
							System.out.println();
							
							CosineSimilarity cosineSimilarity = new CosineSimilarity();
							
							cosineSimilarityValue = cosineSimilarity
									.calculateCosineSimilarity(initialVelocityVector, velocityVector);

						} else {
							logger.info("Difference b/w vectors : IS GREATER THAN : percentageThresholdValue : " +
									"So CANNOT Calculate Cosine Similarity!");
							logger.info("**********************************************************************************************************");
							System.out.println();
						}
					}
					logger.info("**********************************************************************************************************");
					
					if(null != matchedValue) {
						logger.info("MATCH VALUE CALCULATED SUCESSFULY : "+matchedValue);
					} else {
						logger.info("MATCH VALUE Was not calculated.. Because matchedValue was 'NULL' OR Some Error Occurred!");
					}
					logger.info("Cosine Similarity of the two resulting Vectors is : "+ cosineSimilarityValue);
					
					user.setCosValue(Double.toString(cosineSimilarityValue));
					user.setBrowserName(b);
					user.setOperatingSystem(o);
					user.setMatchBase64ImageUrl(base64ImageUrl);
					usersService.saveNewUser(user);
					
					logger.info("Data Saved for User : "+user.getFirstName() + " "+user.getLastName());
					logger.info("**********************************************************************************************************");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
		
	
	public String writeValues()
	{
		//for (Object element : pointArray) {
		/*String[] points = pointArray.split(",");
		for (String point : points) {
			logger.info("Point is : "+point);
		}*/
		/*compute();
	    
	    setFigure(figure);
	    sessionMap.put("figure", figure);
	    
	    logger.info("Session MAP Size : "+sessionMap.size());
	    
	    logger.info("GET FIGURES STROKES LENGTGH : "+getFigure().getLength() + " : Curves LENGTH : "+getFigure().getCurvesLength());*/
	    
	    /*for(int i = 0, j = i + 1; i < points.size() - 1; i+=2, j+=2)
	    {
	    	Point point = new Point(points.get(i), points.get(j));
	    	//logger.info("X : "+point.getX() + " : Y : "+point.getY() );
	    	stroke.add(point);
	    }*/
	    
	    /*for (Point value : stroke) {
			logger.info("STROKE : "+value);
		}*/
		
		/*ObjectMapper mapper = new ObjectMapper();
		try {
			// read from file, convert it to user class
			//User user = mapper.readValue(new File("c:\\user.json"), User.class);	 
			// display to console
			//logger.info(user);	 
		} catch (Exception e) {	 
			e.printStackTrace();
		}*/
	 
		//}
		//writeToExcel(pageX.replace("[", "").replace("]", ""), pageY.replace("[", "").replace("]", ""), timeArray.replace("[", "").replace("]", ""));
		//logger.info("Page X : "+pageX.replace("[", "").replace("]", ""));
		//logger.info("Page Y : "+pageY.replace("[", "").replace("]", ""));
		
		/*String replacedX = pageX.replaceAll(",", " ").replace("[", "").replace("]", "");
		logger.info("Replaced PAGE X : "+replacedX);
		String[] pagex = replacedX.split("\\s0");
		for (String stringx : pagex) {
			String x = stringx;
			String[] splitStringX  = x.split(" ");
			for (String string : splitStringX) {
				System.out.print(" : splitString : "+string);
			}
			logger.info();
			logger.info("PAGE X : "+stringx);
		}
		
		String replacedY = pageY.replaceAll(",", " ").replace("[", "").replace("]", "");
		logger.info("Replaced PAGE Y : "+replacedY);
		String[] pagey = replacedY.split("\\s0");
		for (String stringy : pagey) {
			logger.info("PAGE Y : "+stringy);
		}*/
		//logger.info("Time Array : "+timeArray.replace("[", "").replace("]", ""));
		//logger.info("Client IP Address : "+ci);
		return SUCCESS;
	}
	
	@SuppressWarnings("unused")
	private void writeToExcel(String pageX, String pageY, String timeArray)
	{
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		logger.info(dateFormat.format(cal.getTime()));
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Sample sheet");
		 
		Map<Integer, Object[]> data = new TreeMap<Integer, Object[]>();
		data.put(0, new String[] {"X-Coordinate", "Y-Coordinate", "Time", "Velocity Vector", "IP Address : "+ci, "Date & Time : "+dateFormat.format(cal.getTime())});
		String[] pagex = pageX.split(",");
		String[] pagey = pageY.split(",");
		String[] timearray = timeArray.split(",");

		int counter = 1;
		
		if(velocityVector.size() < pagex.length)
			while(velocityVector.size() < pagex.length)
				velocityVector.add(0.0);
		
		logger.info("PAGE X LENGTH : "+pagex.length + " : VECTOR SIZEEEEEEEEEEE : "+velocityVector.size());
		while(counter <= pagex.length)
		{
			//System.out.print("Counter : "+counter + " ");
			
				
			data.put(counter, new String[] {pagex[counter - 1], pagey[counter - 1], timearray[counter - 1], velocityVector.get(counter - 1).toString()});
			counter++;
		}
		/*Iterator it = data.entrySet().iterator();
		while (it.hasNext()) {
		Map.Entry pairs = (Map.Entry)it.next();
		 logger.info(pairs.getKey()+",");
		}*/
		SortedSet<Integer> keySet = new TreeSet<Integer>(data.keySet());
		//List<Integer> keySet=new ArrayList(data.keySet());
		//Collections.sort(keySet);
		//keySet =  (TreeSet<String>) data.keySet();
		int rownum = 0;
		for (int key : keySet) {
			//logger.info("KEYSET KEY : "+key);
		    Row row = sheet.createRow(rownum++);
		    String [] objArr = (String[]) data.get(key);
		    int cellnum = 0;
		    for (String obj : objArr) {
		        Cell cell = row.createCell(cellnum++);
		        //logger.info("String is : "+obj);
		        if(obj instanceof String)
		            cell.setCellValue((String)obj);
		    }
		}
		 
		try {
			file = new File(nameOfFile);
			//logger.info("File Path : "+(nameOfFile));
			logger.info("canonical path : "+file.getCanonicalPath()+ " : abs path : "+ file.getAbsolutePath() +" : path : "+ file.getPath());
		    FileOutputStream out = new FileOutputStream(file);
		    workbook.write(out);
		    out.close();
		    logger.info("Excel written successfully..");
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
        //logger.info("File Path in sendemailwithattachment : "+(pathname+nameOfFile));
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
            logger.info("Mail Sent Successfully");
            tr.close();
        } catch (Exception sfe) {
            logger.info(sfe);
        }
	}
	
	public void setBase64ImageUrl(String base64ImageUrl) {
		this.base64ImageUrl = base64ImageUrl;
	}
	
	public String getBase64ImageUrl() {
		return base64ImageUrl;
	}
	
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}
	
	public UsersService getUsersService() {
		return usersService;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public String getHost() {
		return host;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
	
	public int getCurrentNumberOfStrokes() {
		return currentNumberOfStrokes;
	}
	
	public void setVelocityVector(List<Double> velocityVector) {
		this.velocityVector = velocityVector;
	}
	
	public List<Double> getVelocityVector() {
		return velocityVector;
	}
	
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	
	public String getUserFirstName() {
		return userFirstName;
	}
	
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	
	public String getUserLastName() {
		return userLastName;
	}
	
	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}
	
	public String getAgeGroup() {
		return ageGroup;
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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
	
	public void setPoints(List<Double> points) {
		this.points = points;
	}
	
	public List<Double> getPoints() {
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