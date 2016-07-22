package autoMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import org.openqa.selenium.WebDriver;

import config.TestSetting;
import junit.framework.Test;
import junit.framework.TestSuite;
import script_lib.CommUtil;
import test_case.TestSet;

public class MultiThreadRunner implements Runnable{


	private ArrayList<HashMap<String, String>> distributedCaselist;
	private CountDownLatch threadsSignal; 

	public MultiThreadRunner(CountDownLatch threadsSignal) {
		super();
		this.threadsSignal = threadsSignal;
	}	

	public Test suite(WebDriver webdriver) throws Exception {
        TestSuite suite = new TestSuite();

        //suite.addTestSuite(TestSet.class);

        for (int i = 0; i < distributedCaselist.size(); i++) {
        	HashMap<?, ?> map = (HashMap<?, ?>) distributedCaselist.get(i);

        	String caseName = (String) map.get("casename");
        	String testName = "test" + caseName;
        	
        	suite.addTest(new TestSet(testName, webdriver));
        }
        return suite;
    }

	@Override
	public void run() {

		CommUtil.logger.info("Thread start."+Thread.currentThread());
		// TODO Auto-generated method stub
		try {
			WebDriver webdriver = TestSetting.openBrowser();
			junit.textui.TestRunner.run(suite(webdriver));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		threadsSignal.countDown();//线程结束时计数器减1  
		CommUtil.logger.info(Thread.currentThread().getName() + " ended, still " + threadsSignal.getCount() + " is running");
	}

	public void setCaselist(ArrayList<HashMap<String, String>> distributedCaselist) {
		this.distributedCaselist = distributedCaselist;
	}

}
