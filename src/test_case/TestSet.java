package test_case;


import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import action_lib.cst_main.CST_MainAction;
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
	@Test
	public void test82_2() {
		
		String retVal = "-1";
		int caseidx = -1;
		
		String caseid = "82_2";
		String testid = "test"+caseid;
		
		try {
			
			caseidx = CommUtil.getCaselistIdxByCasename(caseid);

			retVal = Case82_2.run(webdriver);
			
			if (retVal.equals("1")) {
				CommUtil.setResultToCaselist(caseidx, ResultSuccess);	
				CommUtil.logger.info(testid + " result: " + ResultSuccess);			
			} else {
				CommUtil.setResultToCaselist(caseidx, ResultFail);	
				CommUtil.logger.info(testid + " retVal: " + retVal + " result: " + ResultFail);					
			}

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			exceptionHandle(testid, caseidx, e);
			retVal = "-1";
		}
		assertEquals("1",retVal);
	}
	@Test
	public void test82_1() {
		
		String retVal = "-1";
		int caseidx = -1;
		
		String caseid = "82_1";
		String testid = "test"+caseid;
		
		try {
			
			caseidx = CommUtil.getCaselistIdxByCasename(caseid);

			retVal = Case82_1.run(webdriver);
			
			if (retVal.equals("2")) {
				CommUtil.setResultToCaselist(caseidx, ResultSuccess);	
				CommUtil.logger.info(testid + " result: " + ResultSuccess);			
			} else {
				CommUtil.setResultToCaselist(caseidx, ResultFail);	
				CommUtil.logger.info(testid + " retVal: " + retVal + " result: " + ResultFail);					
			}

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			exceptionHandle(testid, caseidx, e);
			retVal = "-1";
		}
		assertEquals("2",retVal);
	}

}
