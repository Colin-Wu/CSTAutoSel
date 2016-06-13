package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;


import autotMain.AutoTest;

public class TestSetting {
	
	public static String SiteURL = "";
	public static String UserName = "";
	public static String UserPassword = "";		
	public static int Timeout_Sec = 0;	
	public static String ExcelFileName = "";	
	public static String ExcelConsoleSheet = "";	
	public static int TestCaseCol = 0;	
	public static int TestResultCol = 0;	
	public static int TestRunDateCol = 0;	
	public static int TestExcuteCol = 0;	
	public static int TestStartRow = 0;	
	
	public static WebDriver driver;
	public static int currentRow = 0;	
	
	static Logger logger = Logger.getLogger(AutoTest.class.getName()) ; 
	
	public static void initTest(){
		
		logger.info("Loading config.properties");
		TestSetting.loadConfig();
		
/*		DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
		caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
		WebDriver driver = new InternetExplorerDriver(caps);


	    //Puts a Implicit wait, Will wait for 10 seconds before throwing exception
		driver.manage().timeouts().implicitlyWait(Timeout_Sec, TimeUnit.SECONDS);

	    //Launch website
		driver.navigate().to(SiteURL);*/
		
		TestSetting.driver = driver;

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

			logger.info("SiteURL="+TestSetting.SiteURL);
			logger.info("UserName="+TestSetting.UserName);
			logger.info("UserPassword="+TestSetting.UserPassword);
			logger.info("Timeout_Sec="+TestSetting.Timeout_Sec);
			logger.info("ExcelFileName="+TestSetting.ExcelFileName);
			logger.info("ExcelConsoleSheet="+TestSetting.ExcelConsoleSheet);
			logger.info("TestCaseCol="+TestSetting.TestCaseCol);
			logger.info("TestResultCol="+TestSetting.TestResultCol);
			logger.info("TestRunDateCol="+TestSetting.TestRunDateCol);
			logger.info("TestExcuteCol="+TestSetting.TestExcuteCol);
			logger.info("TestStartRow="+TestSetting.TestStartRow);
			
		  } catch (IOException e) { 
		   e.printStackTrace();    
		  } 

	} 
}
	
