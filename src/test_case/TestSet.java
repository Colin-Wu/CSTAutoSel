package test_case;


import java.lang.reflect.Method;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import action_lib.cst_main.CST_MainAction;
import action_lib.index.IndexPageAction;
import junit.framework.TestCase;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;


public class TestSet extends TestCase{
 
	private WebDriver webdriver;
	By logoutLocator = By.xpath(".//input[@id='cmdsignOut']");
	
	String ResultSuccess = "Pass";
	String ResultFail = "Fail";
	
	
    public TestSet(String testMethod, WebDriver webdriver) {  
        super(testMethod);  
        this.webdriver = webdriver;
    } 
    
    public void exceptionHandle(String casename, int caseidx, Exception e) {
  
		CommUtil.logger.info(casename + " exception:" + CommUtil.excpt2Str(e));
		CommUtil.setResultToCaselist(caseidx, "Fail");
		if (SeleniumUtil.isWebElementExist(webdriver, logoutLocator, 0)) {
			CST_MainAction mainpage = new CST_MainAction(webdriver);			
			mainpage.logout();		
		}	

    }
    public String runCase(String caseid, WebDriver webdriver,String expectRet) {
    	  
		String retVal = "-1";
		String testid = "test"+caseid;
		String caseClassname = "test_case.Case"+caseid;
		
		int caseidx = -1;
		
		CommUtil.logger.info(caseid + " > Login");	
		IndexPageAction idxpage = new IndexPageAction(webdriver);
		idxpage.login();
		
		try {
			
			caseidx = CommUtil.getCaselistIdxByCasename(caseid);

        	Class<?> classType = Class.forName(caseClassname);
        	Object obj  = classType.newInstance();
        	Method method = classType.getMethod("run", WebDriver.class); 
        	Object result = method.invoke(obj, webdriver);
        	retVal = (String)result;
        	
			if (retVal.equals(expectRet)) {
				CommUtil.setResultToCaselist(caseidx, ResultSuccess);	
				CommUtil.logger.info(testid + " result: " + ResultSuccess);			
			} else {
				CommUtil.setResultToCaselist(caseidx, ResultFail);	
				CommUtil.logger.info(testid + " retVal: " + retVal + " result: " + ResultFail);					
			}
			CommUtil.logger.info(caseid + " > Logout");	
			CST_MainAction mainpage = new CST_MainAction(webdriver);
			mainpage.logout();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			exceptionHandle(testid, caseidx, e);
			retVal = "-1";
		}
		return retVal;

    }
	@Test
	public void test82_4() {
		
		String retVal = "-1";
		String expectRet = "1";	
		String caseid = "82_4";
		
		retVal = runCase(caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test82_3() {
		
		String retVal = "-1";
		String expectRet = "2";	
		String caseid = "82_3";
		
		retVal = runCase(caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test82_2() {
		
		String retVal = "-1";
		String expectRet = "1";
		String caseid = "82_2";
		
		retVal = runCase(caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test82_1() {
		
		String retVal = "-1";
		String expectRet = "1";		
		String caseid = "82_1";
		
		retVal = runCase(caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}

}
