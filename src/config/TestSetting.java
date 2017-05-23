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

import action_lib.common_action.CommonAction;
import autoMain.Reslist;
import script_lib.CommUtil;
import script_lib.ExcelUtil;
import script_lib.Progressbar;

public class TestSetting {
	
	public static String SiteURL = "";
	public static String UserName = "";
	public static String UserPassword = "";		
	public static String Customer = "";		
	public static String Project = "";		
	public static String HPProject = "";		
	public static String Site = "";		
	public static String StandardPONum = "";	
	public static String RepairPONum = "";	
	public static String ShipVia = "";
	public static String ProjectPart1 = "";
	public static String HPPart1 = "";
	public static int Timeout_Sec = 0;	
	public static String ExcelFileName = "";	
	public static String IEDriverPath = "";	
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
	public static String DebugCase = "";	
	public static String StockLocation="";
	public static String RepairLocation="";
	public static String StockGroup="";
	public static String HPStockGroup="";
	public static String DefaultGenQty="";

	public static String MailServer="";
	public static String MailAccount="";
	public static String MailReceiver="";
	public static Progressbar progressbar;
	public static Reslist reslist;
	public static CommonAction CA;

	public static int RunThread=1;
	
	public static WebDriver driver;	
	public static ArrayList<Object> WebDriverList = new ArrayList<Object>();
	public static ArrayList<HashMap<String, String>> caselist;
	public static String TestResult;
	public static String MailResult="";
	
	public static long begintime = 0;
	public static long endtime = 0;

	
	public static String OrdType_Disposal = "Disposal";	
	public static String OrdType_Standard = "Standard";	
	public static String OrdType_Repair = "Repair";	
	public static String OrdType_EEX = "EEX";	
	public static String OrdType_FR = "Field Retrieval";	
	public static String OrdAttr_ExtRepair = "External repair of defective";	
	public static String OrdAttr_ExtRepairWithHP = "External repair of defective With HP triage";	
	public static String OrdAttr_ExtRepairNoHP = "External repair of defective/no HP triage";	
	public static String OrdAttr_IntRepair = "Internal repair of defective";	
	public static String OrdAttr_IntRepairDFS = "Internal Repair could be DFS";	
	public static String OrdAttr_RepairReturn = "Repair and Return";	
	public static String OrdAttr_RepairStock = "Repair and Stock";	
	public static String OrdAttr_NoRepair = "No repair of defective";	
	public static String OrdAttr_PPS = "Pick Pack and Ship";	
	public static String OrdAttr_NewBuild = "New Build with Config";	
	public static String OrdAttr_Prebuild = "Prebuild or breakdown and return to stock";	
	public static String OrdAttr_Ebox = "Ebox";	
	public static String OrdAttr_DisposalOfMtrl = "Disposal Of Material";	

	
	public static void initTest(){
		
		TestSetting.begintime = System.currentTimeMillis();
		CommUtil.logger.info("Loading config.properties");
		TestSetting.loadConfig();
		String IEDriverFullPath = IEDriverPath + "\\IEDriverServer.exe";
		System.setProperty("webdriver.ie.driver",IEDriverFullPath);
		
		progressbar = new Progressbar();
		reslist = new Reslist();
		CA = new CommonAction();
		
		try {
	        if (TestSetting.DebugCase.equals("")) {
				ExcelUtil.loadExcelFile();
				TestResult = "Pass";
				
				CommUtil.logger.info("Getting all executable cases...");
				TestSetting.caselist = ExcelUtil.getAllExecuteCases();
				CommUtil.logger.info("caselist:"+TestSetting.caselist);
				ExcelUtil.clearResult();
				ExcelUtil.closeWorkbook();
	        }
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
		
		driver.manage().window().maximize();
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
			TestSetting.IEDriverPath = prop.getProperty("IEDriverPath");
			TestSetting.ExcelFileName = prop.getProperty("ExcelFileName");
			TestSetting.ExcelConsoleSheet = prop.getProperty("ExcelConsoleSheet");
			TestSetting.TestCaseCol = Integer.parseInt(prop.getProperty("TestCaseCol"));
			TestSetting.TestResultCol = Integer.parseInt(prop.getProperty("TestResultCol"));
			TestSetting.TestRunDateCol = Integer.parseInt(prop.getProperty("TestRunDateCol"));
			TestSetting.TestExcuteCol = Integer.parseInt(prop.getProperty("TestExcuteCol"));
			TestSetting.TestStartRow = Integer.parseInt(prop.getProperty("TestStartRow"));
			TestSetting.RunThread = Integer.parseInt(prop.getProperty("RunThread"));
			TestSetting.DebugCase = prop.getProperty("DebugCase");
			TestSetting.Dburl = prop.getProperty("Dburl");
			TestSetting.Dbuser = prop.getProperty("Dbuser");
			TestSetting.Dbpass = prop.getProperty("Dbpass");
			TestSetting.DriverName = prop.getProperty("DriverName");
			TestSetting.Customer = prop.getProperty("Customer");
			TestSetting.Project = prop.getProperty("Project");
			TestSetting.HPProject = prop.getProperty("HPProject");
			TestSetting.Site = prop.getProperty("Site");
			TestSetting.StandardPONum = prop.getProperty("StandardPONum");
			TestSetting.RepairPONum = prop.getProperty("RepairPONum");
			TestSetting.ShipVia = prop.getProperty("ShipVia");
			TestSetting.ProjectPart1 = prop.getProperty("ProjectPart1");
			TestSetting.HPPart1 = prop.getProperty("HPPart1");
			TestSetting.StockLocation=prop.getProperty("StockLocation");
			TestSetting.RepairLocation=prop.getProperty("RepairLocation");
			TestSetting.StockGroup=prop.getProperty("StockGroup"); 
			TestSetting.HPStockGroup=prop.getProperty("HPStockGroup"); 
			TestSetting.DefaultGenQty=prop.getProperty("DefaultGenQty"); 
			TestSetting.MailServer=prop.getProperty("MailServer"); 
			TestSetting.MailAccount=prop.getProperty("MailAccount"); 
			TestSetting.MailReceiver=prop.getProperty("MailReceiver"); 
			
			
			CommUtil.logger.info("SiteURL="+TestSetting.SiteURL);
			CommUtil.logger.info("UserName="+TestSetting.UserName);
			CommUtil.logger.info("UserPassword="+TestSetting.UserPassword);
			CommUtil.logger.info("Timeout_Sec="+TestSetting.Timeout_Sec);
			CommUtil.logger.info("IEDriverPath="+TestSetting.IEDriverPath);
			CommUtil.logger.info("ExcelFileName="+TestSetting.ExcelFileName);
			CommUtil.logger.info("ExcelConsoleSheet="+TestSetting.ExcelConsoleSheet);
			CommUtil.logger.info("TestCaseCol="+TestSetting.TestCaseCol);
			CommUtil.logger.info("TestResultCol="+TestSetting.TestResultCol);
			CommUtil.logger.info("TestRunDateCol="+TestSetting.TestRunDateCol);
			CommUtil.logger.info("TestExcuteCol="+TestSetting.TestExcuteCol);
			CommUtil.logger.info("TestStartRow="+TestSetting.TestStartRow);
			CommUtil.logger.info("RunThread="+TestSetting.RunThread);
			CommUtil.logger.info("DebugCase="+TestSetting.DebugCase);
			CommUtil.logger.info("Dburl="+TestSetting.Dburl);
			CommUtil.logger.info("Dbuser="+TestSetting.Dbuser);
			CommUtil.logger.info("Dbpass="+TestSetting.Dbpass);
			CommUtil.logger.info("DriverName="+TestSetting.DriverName);
			CommUtil.logger.info("Customer="+TestSetting.Customer);
			CommUtil.logger.info("Project="+TestSetting.Project);
			CommUtil.logger.info("HPProject="+TestSetting.HPProject);
			CommUtil.logger.info("Site="+TestSetting.Site);
			CommUtil.logger.info("StandardPONum="+TestSetting.StandardPONum);
			CommUtil.logger.info("RepairPONum="+TestSetting.RepairPONum);
			CommUtil.logger.info("ShipVia="+TestSetting.ShipVia);
			CommUtil.logger.info("ProjectPart1="+TestSetting.ProjectPart1);
			CommUtil.logger.info("HPPart1="+TestSetting.HPPart1);
			CommUtil.logger.info("StockLocation="+TestSetting.StockLocation);
			CommUtil.logger.info("RepairLocation="+TestSetting.RepairLocation);
			CommUtil.logger.info("StockGroup="+TestSetting.StockGroup);
			CommUtil.logger.info("HPStockGroup="+TestSetting.HPStockGroup);
			CommUtil.logger.info("DefaultGenQty="+TestSetting.DefaultGenQty);
			CommUtil.logger.info("MailServer="+TestSetting.MailServer);
			CommUtil.logger.info("MailAccount="+TestSetting.MailAccount);
			CommUtil.logger.info("MailReceiver="+TestSetting.MailReceiver);
			
		  } catch (IOException e) { 
		   e.printStackTrace();    
		  } 

	}

}
	
