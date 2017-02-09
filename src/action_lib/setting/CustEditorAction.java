package action_lib.setting;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import obj_repository.setting.CustEditorObj;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class CustEditorAction {
	WebDriver webdriver;

	public CustEditorAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public String CheckPostCode (HashMap<String, ?> InputObj) throws NoSuchElementException {
		//retVal: -1:error;0:success;1:Only 2 Characters for country code displayed;2:only 5 digital displayed;3:Only alpha numeric dash displayed
		String outRetVal = "-1";
		
		String inCountryCode = InputObj.get("CountryCode").toString();
		String inPostCode = InputObj.get("PostCode").toString();
		
		CustEditorObj Obj = new CustEditorObj(webdriver);
		
		WebElement TxtCountryCode = Obj.getTxtCountryCode();
		if (!TxtCountryCode.getAttribute("value").equals(inCountryCode)) {
			TxtCountryCode.clear();
			TxtCountryCode.sendKeys(inCountryCode);
			WebElement TxtPostCode = Obj.getTxtPostCode();
			TxtPostCode.click();
		}
		
		boolean isCountryCodeMsgExist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblCountryCodeMessageLocator(), 1);
		if (isCountryCodeMsgExist) {
			WebElement LblCountryCodeMessage = Obj.getLblCountryCodeMessage();
			boolean isVisible = LblCountryCodeMessage.isDisplayed();
			if (isVisible) {
				CommUtil.logger.info(" > Msg: "+LblCountryCodeMessage.getText());
				if (CommUtil.isMatchByReg(LblCountryCodeMessage.getText(), "Only 2 characters")) {
					outRetVal = "1";
					return outRetVal;
				} else {
					CommUtil.logger.info(" > Unhandled message.");
					return outRetVal;
				}
			}
		}

		WebElement TxtPostCode = Obj.getTxtPostCode();
		if (!TxtPostCode.getAttribute("value").equals(inPostCode)) {
			TxtPostCode.clear();
			TxtPostCode.sendKeys(inPostCode);
			TxtCountryCode = Obj.getTxtCountryCode();
			TxtCountryCode.click();
		}	
		
		boolean isPostCodeMsgExist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblPostCodeMessageLocator(), 1);
		if (isPostCodeMsgExist) {
			WebElement LblPostCodeMessage = Obj.getLblPostCodeMessage();
			boolean isVisible = LblPostCodeMessage.isDisplayed();
			if (isVisible) {
				CommUtil.logger.info(" > Msg: "+LblPostCodeMessage.getText());
				if (CommUtil.isMatchByReg(LblPostCodeMessage.getText(), "Only 5 digits")) {
					outRetVal = "2";
					return outRetVal;
				} else if (CommUtil.isMatchByReg(LblPostCodeMessage.getText(), "Only alpha numeric dash space")) {
					outRetVal = "3";
					return outRetVal;					
				} else {
					CommUtil.logger.info(" > Unhandled message.");
					return outRetVal;
				}
			}
		}
		outRetVal = "0";
		return outRetVal;
		
	}
	
	public String SearchSite (HashMap<String, ?> InputObj) throws NoSuchElementException {

		String isFound = "0";	
		
		String SearchSiteName = InputObj.get("SiteName").toString();
		
		CustEditorObj Obj = new CustEditorObj(webdriver);
		
		WebElement TxtSiteName = Obj.getTxtSiteName();
		if (!TxtSiteName.getAttribute("value").equals(SearchSiteName)) {
			TxtSiteName.sendKeys(SearchSiteName);
		}	
	
		WebElement BtnSearch = Obj.getBtnSearch();

		BtnSearch.click();
		
		SeleniumUtil.waitPageRefresh(BtnSearch);
		//SeleniumUtil.waitPageRefreshByLoadingIcon(webdriver);
		WebElement tblResult = Obj.getTblSearchResult();
		
        int tblRow = SeleniumUtil.getTableRows(tblResult);
		if (tblRow > 1) {
			CommUtil.logger.info(" > Site is Found.");
			isFound = "1";
		} else {
			CommUtil.logger.info(" > Site is not Found.");
		} 
		
		return isFound;
		
	}	
	
	public String SelectSite (String SiteName) throws NoSuchElementException {
		
		String outRetVal = "-1";
		int selCol = 0;
		
		String vSiteNameColname = "Site Name";
		CustEditorObj Obj = new CustEditorObj(webdriver);
		WebElement tblResult = Obj.getTblSearchResult();
			
	    int tblRow = SeleniumUtil.getTableRows(tblResult);
	    
	    if(tblRow > 1)
	    {
	    	int colidx = SeleniumUtil.getTableColIdxByName(tblResult, vSiteNameColname);
	    	//CommUtil.logger.info(" > Col Index: "+colidx);
			int searchrow = SeleniumUtil.getRowByVal(tblResult, colidx, SiteName);
			
			By locator = Obj.getLinkSelLocator();
			
			WebElement selectlink = SeleniumUtil.getCellElement(webdriver, tblResult, selCol, searchrow, locator);
			
			if (selectlink != null) {
				CommUtil.logger.info(" > selectlink link clicked. site:" + SiteName);
				selectlink.click();
				outRetVal = "0";
			}
			
			
/*			
			//CommUtil.logger.info(" > Row Index: "+searchrow);
			int rowidx = searchrow-1;
			
			WebElement LinkSelect = Obj.getLinkSelect(rowidx);
			
			LinkSelect.click();
			
			outRetVal = "0";*/
			
			SeleniumUtil.waitPageRefresh(selectlink);			
			
	    }
		return outRetVal;	
	}
	
}
