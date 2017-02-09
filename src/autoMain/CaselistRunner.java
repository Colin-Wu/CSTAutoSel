package autoMain;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

import org.openqa.selenium.WebDriver;

import config.TestSetting;
import junit.framework.Test;
import junit.framework.TestSuite;
import script_lib.CommUtil;
import test_case.TestSet;

public class CaselistRunner implements Runnable{
    private Caselist Caselist;
	private CountDownLatch threadsSignal; 
   
    public CaselistRunner(Caselist Caselist, CountDownLatch threadsSignal) {
        this.Caselist = Caselist;

        this.threadsSignal = threadsSignal;

    }
    
	public Test suite(WebDriver webdriver, HashMap<?, ?> casemap) throws Exception {
        TestSuite suite = new TestSuite();

        //suite.addTestSuite(TestSet.class);
        if (TestSetting.DebugCase.equals("")) {

            	String caseName = (String) casemap.get("casename");
            	String testName = "test" + caseName;
            	
            	suite.addTest(new TestSet(testName, webdriver));
   	
        } else {
        	CommUtil.logger.info("Debug mode... case name:"+TestSetting.DebugCase);
        	suite.addTest(new TestSet(TestSetting.DebugCase, webdriver));
        }


        return suite;
    }
	
    @Override
	public void run() {
    	
		CommUtil.logger.info("Thread start."+Thread.currentThread());
		WebDriver webdriver = TestSetting.openBrowser();
    	
		// TODO Auto-generated method stub
    	HashMap<String, String> retCase = Caselist.fetchCase();
    	while (retCase != null) {

    		// TODO Auto-generated method stub
    		try {
    			junit.textui.TestRunner.run(suite(webdriver,retCase));
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}	
    		
    		Caselist.completeCase();
    		TestSetting.progressbar.refreshProgress(Caselist.getProgress());
    		retCase = Caselist.fetchCase();
    	}
    	
		//webdriver.close();
		webdriver.quit();
		threadsSignal.countDown();//线程结束时计数器减1  
		CommUtil.logger.info(Thread.currentThread().getName() + " ended, still " + threadsSignal.getCount() + " is running");    	
   	
	}

}
