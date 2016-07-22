package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import script_lib.CommUtil;
import script_lib.ExcelUtil;

public class TestSetting {
	
	public static String SiteURL = "";
	public static String UserName = "";
	public static String UserPassword = "";		
	public static String Project = "";		
	public static String StandardPONum = "";	
	public static String ShipVia = "";
	public static String ProjectPart1 = "";
	public static int Timeout_Sec = 0;	
	public static String ExcelFileName = "";	
	public static String ExcelConsoleSheet = "";	
	public static int TestCaseCol = 0;	
	public static int TestResultCol = 0;	
	public static int TestRunDateCol = 0;	
	public static int TestExcuteCol = 0;	
	public static int TestStartRow = 0;	
	public static String Dburl = "";	
	public static String Dbuser = "";	
	public static String Dbpass = "";	
	public static String DriverName = "";	

	public static int RunThread=1;
	
	public static WebDriver driver;	
	public static ArrayList<Object> WebDriverList = new ArrayList<Object>();
	public static ArrayList<HashMap<String, String>> caselist;
	
	
	public static void initTest(){
		
		CommUtil.logger.info("Loading config.properties");
		TestSetting.loadConfig();
		//System.setProperty("webdriver.ie.driver","C:\\Users\\yren9\\workspace\\selenium\\IEDriverServer.exe");
		
		try {
			ExcelUtil.loadExcelFile();
			
			CommUtil.logger.info("Getting all executable cases...");
			TestSetting.caselist = ExcelUtil.getAllExecuteCases();
			ExcelUtil.clearResult();
			ExcelUtil.closeWorkbook();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

	}
	public static WebDriver openBrowser(){
		
	
		DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
		caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
		WebDriver driver = new InternetExplorerDriver(caps);


	    //Puts a Implicit wait, Will wait for 10 seconds before throwing exception
		driver.manage().timeouts().implicitlyWait(Timeout_Sec, TimeUnit.SECONDS);
		//driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

	    //Launch website
		driver.navigate().to(SiteURL);
		
		TestSetting.driver = driver;
		return driver;

	}
		
	public static void loadConfig() {
		 InputStream in;
		 Properties prop = null;
		try {
			in = new FileInputStream(System.getProperty("user.dir")+"\\config.properties");
			prop = new Properties(); 
			prop.load(in);
			

			TestSetting.SiteURL = prop.getProperty("SiteURL");
			TestSetting.UserName = prop.getProperty("UserName");
			TestSetting.UserPassword = prop.getProperty("UserPassword");
			TestSetting.Timeout_Sec = Integer.parseInt(prop.getProperty("Timeout_Sec"));
			TestSetting.ExcelFileName = prop.getProperty("ExcelFileName");
			TestSetting.ExcelConsoleSheet = prop.getProperty("ExcelConsoleSheet");
			TestSetting.TestCaseCol = Integer.parseInt(prop.getProperty("TestCaseCol"));
			TestSetting.TestResultCol = Integer.parseInt(prop.getProperty("TestResultCol"));
			TestSetting.TestRunDateCol = Integer.parseInt(prop.getProperty("TestRunDateCol"));
			TestSetting.TestExcuteCol = Integer.parseInt(prop.getProperty("TestExcuteCol"));
			TestSetting.TestStartRow = Integer.parseInt(prop.getProperty("TestStartRow"));
			TestSetting.RunThread = Integer.parseInt(prop.getProperty("RunThread"));
			TestSetting.Dburl = prop.getProperty("Dburl");
			TestSetting.Dbuser = prop.getProperty("Dbuser");
			TestSetting.Dbpass = prop.getProperty("Dbpass");
			TestSetting.DriverName = prop.getProperty("DriverName");
			TestSetting.Project = prop.getProperty("Project");
			TestSetting.StandardPONum = prop.getProperty("StandardPONum");
			TestSetting.ShipVia = prop.getProperty("ShipVia");
			TestSetting.ProjectPart1 = prop.getProperty("ProjectPart1");
			
			
			CommUtil.logger.info("SiteURL="+TestSetting.SiteURL);
			CommUtil.logger.info("UserName="+TestSetting.UserName);
			CommUtil.logger.info("UserPassword="+TestSetting.UserPassword);
			CommUtil.logger.info("Timeout_Sec="+TestSetting.Timeout_Sec);
			CommUtil.logger.info("ExcelFileName="+TestSetting.ExcelFileName);
			CommUtil.logger.info("ExcelConsoleSheet="+TestSetting.ExcelConsoleSheet);
			CommUtil.logger.info("TestCaseCol="+TestSetting.TestCaseCol);
			CommUtil.logger.info("TestResultCol="+TestSetting.TestResultCol);
			CommUtil.logger.info("TestRunDateCol="+TestSetting.TestRunDateCol);
			CommUtil.logger.info("TestExcuteCol="+TestSetting.TestExcuteCol);
			CommUtil.logger.info("TestStartRow="+TestSetting.TestStartRow);
			CommUtil.logger.info("RunThread="+TestSetting.RunThread);
			CommUtil.logger.info("Dburl="+TestSetting.Dburl);
			CommUtil.logger.info("Dbuser="+TestSetting.Dbuser);
			CommUtil.logger.info("Dbpass="+TestSetting.Dbpass);
			CommUtil.logger.info("DriverName="+TestSetting.DriverName);
			CommUtil.logger.info("Project="+TestSetting.Project);
			CommUtil.logger.info("StandardPONum="+TestSetting.StandardPONum);
			CommUtil.logger.info("ShipVia="+TestSetting.ShipVia);
			CommUtil.logger.info("ProjectPart1="+TestSetting.ProjectPart1);
			
		  } catch (IOException e) { 
		   e.printStackTrace();    
		  } 

	}

}
	
