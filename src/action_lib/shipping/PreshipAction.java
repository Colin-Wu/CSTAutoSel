package action_lib.shipping;

import java.util.HashMap;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import obj_repository.shipping.PreshipObj;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class PreshipAction {
	WebDriver webdriver;

	public PreshipAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public String ReturnToStock () throws NoSuchElementException {
		//RetVal:-1:error;0: success;1: other error
		String ret = "-1";	
		
		PreshipObj Obj = new PreshipObj(webdriver);
	
		SeleniumUtil.waitWebElementProperty(webdriver, Obj.getBtnReturntoStockLocator(), "visible", "true");
		
		WebElement BtnReturntoStock = Obj.getBtnReturntoStock();
		
		BtnReturntoStock.click();
		
		SeleniumUtil.waitPageRefreshByLoadingIcon(webdriver);
		
		
		boolean isLblSuccessMessageExist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblSuccessMessageLocator(), 0);
		if (isLblSuccessMessageExist) {
			WebElement LblSuccessMessage = Obj.getLblSuccessMessage();	
			CommUtil.logger.info(" > Msg: " + LblSuccessMessage.getText());
			
			if (CommUtil.isMatchByReg(LblSuccessMessage.getText(), "These items can now be return to stock in the Location selected\\.")) {
				ret = "0";
			}
		} else {
			boolean isLblErrorMessageExist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblErrorMessageLocator(), 0);
			if (isLblErrorMessageExist) {
				WebElement LblErrorMessage = Obj.getLblErrorMessage();	
				CommUtil.logger.info(" > Msg: " + LblErrorMessage.getText());
				ret = "1";


			} else {
				CommUtil.logger.info(" > No message found.");
			}
		}		
		
		
		return ret;
		
	}	
	
	public String SendToShipping () throws NoSuchElementException {
		//RetVal:-1:error;0: success;1:Not QAed; 2: other error
		String ret = "-1";	
		
		PreshipObj Obj = new PreshipObj(webdriver);
	
		SeleniumUtil.waitWebElementProperty(webdriver, Obj.getBtnSendtoShippingLocator(), "visible", "true");
		
		WebElement BtnSendtoShipping = Obj.getBtnSendtoShipping();
		
		BtnSendtoShipping.click();
		
		SeleniumUtil.waitPageRefreshByLoadingIcon(webdriver);
		
		
		boolean isLblSuccessMessageExist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblSuccessMessageLocator(), 0);
		if (isLblSuccessMessageExist) {
			WebElement LblSuccessMessage = Obj.getLblSuccessMessage();	
			CommUtil.logger.info(" > Msg: " + LblSuccessMessage.getText());
			
			if (CommUtil.isMatchByReg(LblSuccessMessage.getText(), "Order is Ready to be shipped\\.")) {
				ret = "0";
			}
		} else {
			boolean isLblErrorMessageExist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblErrorMessageLocator(), 0);
			if (isLblErrorMessageExist) {
				WebElement LblErrorMessage = Obj.getLblErrorMessage();	
				CommUtil.logger.info(" > Msg: " + LblErrorMessage.getText());
				if (CommUtil.isMatchByReg(LblErrorMessage.getText(), "Order \"\\d+\" has not had a successful QA, please initiate QA before proceeding.")) {
					ret = "1";
				} else {
					ret = "2";
				}

			} else {
				CommUtil.logger.info(" > No message found.");
			}
		}		
		
		
		return ret;
		
	}	
	public String GoEditKitting (HashMap<String, ?> InputObj) throws NoSuchElementException {
		//RetVal:-1:error;0: success
		String ret = "-1";	
		String SearchOrdNum = InputObj.get("OrdNum").toString();
		
		PreshipObj Obj = new PreshipObj(webdriver);
	
		WebElement tblResult = Obj.getTblSearchResult();
		
		int colidx = SeleniumUtil.getTableColIdxByName(tblResult, "Order Number");

		int searchrow = SeleniumUtil.getRowByVal(tblResult, colidx, SearchOrdNum);

		WebElement editlink = Obj.getLinkEdit(searchrow-1);
		
		if (editlink != null) {
			CommUtil.logger.info(" > Edit link clicked. Order Number:" + SearchOrdNum);
			editlink.click();
			
			ret = "0";	
		}
		
		return ret;
		
	}	
	public String SearchKitting (HashMap<String, ?> InputObj) throws NoSuchElementException {

		String isFound = "0";	
	
		String SearchProjectCode = InputObj.get("ProjectCode").toString();
		String SearchOrdNum = InputObj.get("OrdNum").toString();
		String SearchBoxID = InputObj.get("BoxID").toString();
		String SearchStatus = InputObj.get("Status").toString();

		PreshipObj PreshipObjObj = new PreshipObj(webdriver);
		
		WebElement TxtProjectCode = PreshipObjObj.getTxtProjectCode();
		if (!TxtProjectCode.getAttribute("value").equals(SearchProjectCode)) {
			TxtProjectCode.sendKeys(SearchProjectCode);
		}
		
		WebElement TxtOrderNumber = PreshipObjObj.getTxtOrderNumber();
		if (!TxtOrderNumber.getAttribute("value").equals(SearchOrdNum)) {
			TxtOrderNumber.sendKeys(SearchOrdNum);
		}	
		
		WebElement TxtBoxID = PreshipObjObj.getTxtBoxID();
		if (!TxtBoxID.getAttribute("value").equals(SearchBoxID)) {
			TxtBoxID.sendKeys(SearchBoxID);
		}	
		
		if (!SearchStatus.equals("")) {
			WebElement WebEleSelStatus = PreshipObjObj.getSelStatus();
			Select SelStatus = new Select(WebEleSelStatus);
			
	        if (!SelStatus.getFirstSelectedOption().getAttribute("value").equals(SearchStatus)) {

				boolean isHasVal = SeleniumUtil.isSelectHasOption(SelStatus, SearchStatus);
				if (!isHasVal) {
					CommUtil.logger.info(" > Status option not found in UI. SearchStatus:"+SearchStatus);
					isFound = "-1";
					return isFound;
				}
				
				SelStatus.selectByVisibleText(SearchStatus);
	        }
		}
	
		WebElement BtnSearch = PreshipObjObj.getBtnSearch();

		BtnSearch.click();
		
		SeleniumUtil.waitPageRefreshByLoadingIcon(webdriver);
		
		WebElement tblResult = PreshipObjObj.getTblSearchResult();
		
        int tblRow = SeleniumUtil.getTableRows(tblResult);
		if (tblRow > 1) {
			CommUtil.logger.info(" > Kitting is Found.");
			isFound = "1";
		} else {
			CommUtil.logger.info(" > Kitting is not Found.");
		} 
		
		return isFound;
		
	}	
}
