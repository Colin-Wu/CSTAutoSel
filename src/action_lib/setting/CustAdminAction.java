package action_lib.setting;

import java.util.HashMap;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import obj_repository.setting.CustAdminObj;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class CustAdminAction {
	WebDriver webdriver;

	public CustAdminAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public String SearchCust (HashMap<String, ?> InputObj) throws NoSuchElementException {

		String isFound = "0";	
		
		String SearchCustCode = InputObj.get("CustCode").toString();
		String SearchCustName = InputObj.get("CustName").toString();
		
		CustAdminObj Obj = new CustAdminObj(webdriver);
		
		WebElement TxtCustomerCode = Obj.getTxtCustomerCode();
		if (!TxtCustomerCode.getAttribute("value").equals(SearchCustCode)) {
			TxtCustomerCode.sendKeys(SearchCustCode);
		}
		
		WebElement TxtCustomerName = Obj.getTxtCustomerName();
		if (!TxtCustomerName.getAttribute("value").equals(SearchCustName)) {
			TxtCustomerName.sendKeys(SearchCustName);
		}	
		
	
		WebElement BtnSearch = Obj.getBtnSearch();

		BtnSearch.click();
		
		SeleniumUtil.waitPageRefresh(BtnSearch);
		//SeleniumUtil.waitPageRefreshByLoadingIcon(webdriver);
		WebElement tblResult = Obj.getTblSearchResult();
		
        int tblRow = SeleniumUtil.getTableRows(tblResult);
		if (tblRow > 1) {
			CommUtil.logger.info(" > Customer is Found.");
			isFound = "1";
		} else {
			CommUtil.logger.info(" > Customer is not Found.");
		} 
		
		return isFound;
		
	}	
	
	public String SelectCust (String CustCode) throws NoSuchElementException {
		
		String outRetVal = "-1";
		
		String vCustCodeColname = "Code";
		CustAdminObj Obj = new CustAdminObj(webdriver);
		WebElement tblResult = Obj.getTblSearchResult();
			
	    int tblRow = SeleniumUtil.getTableRows(tblResult);
	    
	    if(tblRow > 1)
	    {
	    	int colidx = SeleniumUtil.getTableColIdxByName(tblResult, vCustCodeColname);
	    	//CommUtil.logger.info(" > Col Index: "+colidx);
			int searchrow = SeleniumUtil.getRowByVal(tblResult, colidx, CustCode);
			
			//CommUtil.logger.info(" > Row Index: "+searchrow);
			int rowidx = searchrow-1;
			
			WebElement LinkSelect = Obj.getLinkSelect(rowidx);
			
			LinkSelect.click();
			
			outRetVal = "0";
			
			SeleniumUtil.waitPageRefresh(LinkSelect);			
			
	    }
		return outRetVal;	
	}
}
