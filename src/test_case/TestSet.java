package test_case;


import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import action_lib.cst_main.CST_MainAction;
import action_lib.index.IndexPageAction;
import config.TestSetting;
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
    
    public void exceptionHandle(String casename, int caseidx, Exception e, String hms, String caseid) {
  
		CommUtil.logger.info(casename + " exception:" + CommUtil.excpt2Str(e));
		CommUtil.setResultToCaselist(caseidx, "Fail");
		CommUtil.logger.info(casename + " result: " + ResultFail);	
		TestSetting.TestResult = "Failed";
		TestSetting.MailResult += "<PRE><div style=\"color:#FF0000\">" + caseid + "		failed.		Time:"+hms+"</div></PRE>";
		CommUtil.screenshot((TakesScreenshot) webdriver, caseid);
		if (SeleniumUtil.isWebElementExist(webdriver, logoutLocator, 0)) {
			CST_MainAction mainpage = new CST_MainAction(webdriver);			
			mainpage.logout();		
		}	

    }
    public String runCase(String packagename, String caseid, WebDriver webdriver,String expectRet) {
    	  
		String retVal = "-1";
		String testid = "test"+caseid;
		String caseClassname = "test_case."+packagename+".Case"+caseid;
		
		int caseidx = -1;
		long casebegintime = System.currentTimeMillis();
		
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
        	
        	long caseendtime=System.currentTimeMillis();
        	long costTime = (caseendtime - casebegintime) - TimeZone.getDefault().getRawOffset();
        	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        	String hms = formatter.format(costTime);
			
			if (retVal.equals(expectRet)) {
				CommUtil.setResultToCaselist(caseidx, ResultSuccess);	
				CommUtil.logger.info(testid + " result: " + ResultSuccess);			
				TestSetting.MailResult += "<PRE>" + caseid + "		passed.		Time:"+hms+"</PRE>";
			} else {
				CommUtil.setResultToCaselist(caseidx, ResultFail);	
				CommUtil.logger.info(testid + " retVal: " + retVal + " result: " + ResultFail);	
				TestSetting.TestResult = "Failed";
				TestSetting.MailResult += "<PRE><div style=\"color:#FF0000\">" + caseid + "		failed.		Time:"+hms+"</div></PRE>";
				CommUtil.screenshot((TakesScreenshot) webdriver, caseid);
				
			}
			CommUtil.logger.info(caseid + " > Logout");	
			CST_MainAction mainpage = new CST_MainAction(webdriver);
			mainpage.logout();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
        	long caseendtime=System.currentTimeMillis();
        	long costTime = (caseendtime - casebegintime) - TimeZone.getDefault().getRawOffset();
        	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        	String hms = formatter.format(costTime);
			exceptionHandle(testid, caseidx, e,hms, caseid);
			retVal = "-1";
		} finally {
			if (TestSetting.reslist.containCaseid(caseid)) {
				TestSetting.reslist.releaseBoxid(caseid);
			}
		}
		return retVal;

    }
	@Test
	public void test100_1() {
		
		String retVal = "-1";
		String expectRet = "3";	
		String caseid = "100_1";
		String packagename = "test383";
		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test97_1() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "97_1";
		String packagename = "test383";
		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test98_1() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "98_1";
		String packagename = "test382";
		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test94_1() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "94_1";
		String packagename = "test382";
		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test89_4() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "89_4";
		String packagename = "test381";
		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test89_3() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "89_3";
		String packagename = "test381";
		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test89_2() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "89_2";
		String packagename = "test381";
		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test89_1() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "89_1";
		String packagename = "test381";
		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test88_1() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "88_1";
		String packagename = "test381";
		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test93_4() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "93_4";
		String packagename = "test380";
		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test93_3() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "93_3";
		String packagename = "test380";
		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test93_2() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "93_2";
		String packagename = "test380";
		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test93_1() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "93_1";
		String packagename = "test380";
		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test92_10() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "92_10";
		String packagename = "test380";
		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test92_9() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "92_9";
		String packagename = "test380";
		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test92_8() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "92_8";
		String packagename = "test380";
		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test92_7() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "92_7";
		String packagename = "test380";
		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test92_6() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "92_6";
		String packagename = "test380";
		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test92_5() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "92_5";
		String packagename = "test380";
		
		retVal = runCase(packagename, caseid, webdriver, expectRet);

		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test92_4() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "92_4";
		String packagename = "test380";
		
		retVal = runCase(packagename, caseid, webdriver, expectRet);

		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test92_3() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "92_3";
		String packagename = "test380";
		
		retVal = runCase(packagename, caseid, webdriver, expectRet);

		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test92_2() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "92_2";
		String packagename = "test380";
		
		retVal = runCase(packagename, caseid, webdriver, expectRet);

		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test92_1() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "92_1";
		String packagename = "test380";
		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test87_1() {
		
		String retVal = "-1";
		String expectRet = "1";	
		String caseid = "87_1";
		String packagename = "test380";
		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test85_2() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "85_2";
		String packagename = "test380";
		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test85_1() {
		
		String retVal = "-1";
		String expectRet = "1";	
		String caseid = "85_1";
		String packagename = "test380";
		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test82_19() {
		
		String retVal = "-1";
		String expectRet = "1";	
		String caseid = "82_19";
		String packagename = "test379";

		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test82_18() {
		
		String retVal = "-1";
		String expectRet = "1";	
		String caseid = "82_18";
		String packagename = "test379";

		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test82_17() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "82_17";
		String packagename = "test379";

		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test82_16() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "82_16";
		String packagename = "test379";

		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test82_15() {
		
		String retVal = "-1";
		String expectRet = "1";	
		String caseid = "82_15";
		String packagename = "test379";

		
		retVal = runCase(packagename, caseid, webdriver, expectRet);

		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test82_14() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "82_14";
		String packagename = "test379";

		
		retVal = runCase(packagename, caseid, webdriver, expectRet);

		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test82_13() {
		
		String retVal = "-1";
		String expectRet = "1";	
		String caseid = "82_13";
		String packagename = "test379";

		
		retVal = runCase(packagename, caseid, webdriver, expectRet);

		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test82_12() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "82_12";
		String packagename = "test379";

		
		retVal = runCase(packagename, caseid, webdriver, expectRet);

		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test82_11() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "82_11";
		String packagename = "test379";

		
		retVal = runCase(packagename, caseid, webdriver, expectRet);

		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test82_10() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "82_10";
		String packagename = "test379";

		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test82_9() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "82_9";
		String packagename = "test379";

		
		retVal = runCase(packagename, caseid, webdriver, expectRet);

		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test82_8() {
		
		String retVal = "-1";
		String expectRet = "1";	
		String caseid = "82_8";
		String packagename = "test379";

		
		retVal = runCase(packagename, caseid, webdriver, expectRet);

		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test82_7() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "82_7";
		String packagename = "test379";

		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test82_6() {
		
		String retVal = "-1";
		String expectRet = "1";	
		String caseid = "82_6";
		String packagename = "test379";

		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test82_5() {
		
		String retVal = "-1";
		String expectRet = "0";	
		String caseid = "82_5";
		String packagename = "test379";

		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test82_4() {
		
		String retVal = "-1";
		String expectRet = "1";	
		String caseid = "82_4";
		String packagename = "test379";

		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test82_3() {
		
		String retVal = "-1";
		String expectRet = "2";	
		String caseid = "82_3";
		String packagename = "test379";

		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test82_2() {
		
		String retVal = "-1";
		String expectRet = "1";
		String caseid = "82_2";
		String packagename = "test379";

		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}
	@Test
	public void test82_1() {
		
		String retVal = "-1";
		String expectRet = "1";		
		String caseid = "82_1";
		String packagename = "test379";

		
		retVal = runCase(packagename, caseid, webdriver, expectRet);
		
		assertEquals(expectRet,retVal);
	}

}
