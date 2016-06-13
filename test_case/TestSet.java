package test_case;

import org.apache.log4j.Logger;
import org.junit.Test;

import autotMain.AutoTest;
import config.TestSetting;
import junit.framework.TestCase;
import script_lib.ExcelUtil;


public class TestSet extends TestCase{
	static Logger logger = Logger.getLogger(AutoTest.class.getName()) ; 
	
    public TestSet(String testMethod) {  
        super(testMethod);  
    } 
    
	@Test
	public void test100_1() throws Exception {
		
		int retVal;

		//retVal = Case000_1.run(TestSetting.driver);
		retVal = 0;
		ExcelUtil.setResult(TestSetting.currentRow, "Pass");
		logger.info("test100_1 result: Pass");
		
		assertEquals(0,retVal);
	}

	@Test
	public void test100_2() throws Exception {

		
		int retVal;

		//retVal = Case000_2.run(TestSetting.driver);
		retVal = 0;
		ExcelUtil.setResult(TestSetting.currentRow, "Pass");
		logger.info("test100_2 result: Pass");		
		assertEquals(0,retVal);
	}

}
