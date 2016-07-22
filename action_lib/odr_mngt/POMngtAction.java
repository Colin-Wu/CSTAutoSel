package action_lib.odr_mngt;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import obj_repository.odr_mngt.POMngtObj;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class POMngtAction {


	WebDriver webdriver;

	public POMngtAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public void NewPO () throws NoSuchElementException {
		
		POMngtObj Obj = new POMngtObj(webdriver);
	
		WebElement BtnNewPO = Obj.getBtnNewPO();

		BtnNewPO.click();
		
	}
	
	public String SearchPO (HashMap<String, ?> InputObj) throws NoSuchElementException  {
		String isFound = "0";	
		
		String SearchProjectCode = InputObj.get("SearchProjectCode").toString();
		String SearchCustomerPO = InputObj.get("SearchCustomerPO").toString();
		
		POMngtObj Obj = new POMngtObj(webdriver);
	
		WebElement txtCustomerPO = Obj.getTxtCustomerPO();
		WebElement txtProjectCode = Obj.getTxtProjectCode();
		WebElement btnSearchPO = Obj.getBtnSearchPO();
		
		txtCustomerPO.clear();
		txtCustomerPO.sendKeys(SearchCustomerPO);

		txtProjectCode.clear();
		txtProjectCode.sendKeys(SearchProjectCode);
		
		btnSearchPO.click();
		
		WebElement tblResult = Obj.getTblSearchResult();
		
		int tblRow = SeleniumUtil.getTableRows(tblResult);
		
		if (tblRow > 1) {
			CommUtil.logger.info(POMngtAction.class.getName() + " > PO is Found.");
			isFound = "1";
		}
		
		return isFound;
	}
	
	public String DeletePO (HashMap<String, ?> InputObj) throws NoSuchElementException  {
		//RetVal:-1:error;0: delete successfully;1:PO received, can't delete;2:Repair PO, can't delete
		String ret = "-1";	
		
		int delCol = 0;
		
		String SearchCustomerPO = InputObj.get("SearchCustomerPO").toString();
		
		POMngtObj Obj = new POMngtObj(webdriver);
		
		WebElement tblResult = Obj.getTblSearchResult();
			
		int colidx = SeleniumUtil.getTableColIdxByName(tblResult, "Customer PO");

		int searchrow = SeleniumUtil.getRowByVal(tblResult, colidx, SearchCustomerPO);

		By locator = Obj.getLinkDelLocator();
		
		WebElement deletelink = SeleniumUtil.getCellElement(webdriver, tblResult, delCol, searchrow, locator);
		
		if (deletelink != null) {
			CommUtil.logger.info(POMngtAction.class.getName() + " > Delete link clicked. PO:" + SearchCustomerPO);
			deletelink.click();
		}
		
		boolean isLblMsgexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblMessageLocator(), 0);
		
		if (isLblMsgexist) {
			WebElement lblMessage = Obj.getLblMessage();	

			if (CommUtil.isMatchByReg(lblMessage.getText(), "There are item\\(s\\) received against this PO, you cannot cancel\\.")) {
				ret = "2";
			}
		} else {
			boolean isBtnYesexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getBtnConfirmYesLocator(), 0);
			if (isBtnYesexist) {
				WebElement BtnConfirmYes = Obj.getBtnConfirmYes();
				BtnConfirmYes.click();
				
				SeleniumUtil.waitPageRefresh(BtnConfirmYes);
				
				isLblMsgexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblSuccessMessageLocator(), 0);
				if (isLblMsgexist) {
					
					WebElement lblSuccessMessage = Obj.getLblSuccessMessage();	

					if (CommUtil.isMatchByReg(lblSuccessMessage.getText(), "PO delete successfully\\.")) {
						ret = "0";
					}
				}
				
				
			}
			
		}
						

			
		return ret;
	}
}
