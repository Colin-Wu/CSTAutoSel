package autoMain;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

import org.openqa.selenium.WebDriver;

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
	        	
				ArrayList<?> caselist = CommUtil.getCaselistForMulti(TestSetting.RunThread);
				CountDownLatch threadSignal = new CountDownLatch(TestSetting.RunThread);
				//junit.textui.TestRunner.run(suite());
						
				for (int i = 0; i < TestSetting.RunThread; i++) {
					MultiThreadRunner MulThread = new MultiThreadRunner(threadSignal);  
					@SuppressWarnings("unchecked")
					ArrayList<HashMap<String, String>> distributedCaselist = (ArrayList<HashMap<String, String>>) caselist.get(i);
					MulThread.setCaselist(distributedCaselist);
					Thread thread = new Thread(MulThread);  
					thread.start();  				
				}
				threadSignal.await();
			

				CommUtil.logger.info("Updating result to Excel...");
				ExcelUtil.updateExcelResult();
	        } else {
	        	WebDriver webdriver = TestSetting.openBrowser();
	        	//test_case.Case82_1
	        	System.out.println("Case name: " + TestSetting.DebugCase);
	        	Class<?> classType = Class.forName(TestSetting.DebugCase);
	        	Object obj  = classType.newInstance();
	        	Method method = classType.getMethod("run", WebDriver.class); 
	        	Object result = method.invoke(obj, webdriver);
	        	System.out.println("result: " + (String)result);
	        }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


			

	}

}
