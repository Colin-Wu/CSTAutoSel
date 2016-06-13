package autotMain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.Logger;



import config.TestSetting;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import script_lib.ExcelUtil;
import test_case.TestSet;


public class AutoTest extends TestCase{
	static Logger logger = Logger.getLogger(AutoTest.class.getName()) ; 

    public static Test suite(ArrayList caseList) throws Exception {
        TestSuite suite = new TestSuite();

        logger.info("Clearing results.");
        ExcelUtil.clearResult();
        //suite.addTestSuite(TestSet.class);

        for (int i = 0; i < caseList.size(); i++) {
        	HashMap<?, ?> map = (HashMap<?, ?>) caseList.get(i);
        	TestSetting.currentRow = (int) map.get("rowNum");
        	String caseName = (String) map.get("casename");
        	String testName = "test" + caseName;
        	
        	suite.addTest(new TestSet(testName));
        }
        return suite;
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int retVal = 0;

		logger.info("AutoTest initiating...");
		TestSetting.initTest();
		try {
			ExcelUtil.loadExcelFile();
			ArrayList<?> list = ExcelUtil.getAllExecuteCases();
			
			junit.textui.TestRunner.run(suite(list));

			
			ExcelUtil.closeWorkbook();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		if(retVal == 0){
			System.out.println("Case100_1: Success");
		}
	}

}
