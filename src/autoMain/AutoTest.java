package autoMain;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;

import org.openqa.selenium.WebDriver;

import action_lib.index.IndexPageAction;
import config.TestSetting;
import junit.framework.TestCase;
import script_lib.CommUtil;
import script_lib.ExcelUtil;



public class AutoTest extends TestCase{


	public static void doInit () {
		CommUtil.logger.info("AutoTest initiating...");
		TestSetting.initTest();		
	}
	

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		doInit();
			
		try {
	        if (TestSetting.DebugCase.equals("")) {
	        	
				//ArrayList<?> caselist = CommUtil.getCaselistForMulti(TestSetting.RunThread);
				CountDownLatch threadSignal = new CountDownLatch(TestSetting.RunThread);
				//junit.textui.TestRunner.run(suite());
				/*						
				for (int i = 0; i < TestSetting.RunThread; i++) {
					MultiThreadRunner MulThread = new MultiThreadRunner(threadSignal);  
					@SuppressWarnings("unchecked")
					ArrayList<HashMap<String, String>> distributedCaselist = (ArrayList<HashMap<String, String>>) caselist.get(i);
					MulThread.setCaselist(distributedCaselist);
					Thread thread = new Thread(MulThread);  
					thread.start();  				
				}
				
				*/
				Caselist vcaselist = new Caselist(TestSetting.caselist);
				TestSetting.progressbar.showProgress();
				for (int i = 0; i < TestSetting.RunThread; i++) {
										
					CaselistRunner caselistRunner = new CaselistRunner(vcaselist, threadSignal);  
					Thread thread = new Thread(caselistRunner);  
					thread.start();  				
				}
				threadSignal.await();
			

				CommUtil.logger.info("Updating result to Excel...");
				ExcelUtil.updateExcelResult();
				
				TestSetting.endtime=System.currentTimeMillis();
				CommUtil.logger.info("Sending email notification...");
				CommUtil.sendMailNotification();
				CommUtil.logger.info("Completed.");
				TestSetting.progressbar.closeProgress();
	        } else {
	        	TestSetting.begintime = System.currentTimeMillis();
	        	
	        	WebDriver webdriver = TestSetting.openBrowser();
	        	
	        	// Login
	    		IndexPageAction idxpage = new IndexPageAction(webdriver);
	    		idxpage.login();
	    		
	        	//test_case.Case82_1
	        	System.out.println("Case name: " + TestSetting.DebugCase);
	        	Class<?> classType = Class.forName(TestSetting.DebugCase);
	        	Object obj  = classType.newInstance();
	        	Method method = classType.getMethod("run", WebDriver.class); 
	        	Object result = method.invoke(obj, webdriver);
	        	System.out.println("result: " + (String)result);
	        	
	        	TestSetting.endtime=System.currentTimeMillis();
	        	long costTime = (TestSetting.endtime - TestSetting.begintime) - TimeZone.getDefault().getRawOffset();

	        	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

	        	String hms = formatter.format(costTime);
	        	System.out.println("Execution time:"+hms);
	        	
	        	
	        }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


			

	}

}
