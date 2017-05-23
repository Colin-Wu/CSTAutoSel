package action_lib.odr_mngt;

import java.util.HashMap;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import obj_repository.odr_mngt.TemplateAdminObj;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class TemplateAdminAction {
	WebDriver webdriver;

	public TemplateAdminAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public String SearchTemplate (HashMap<String, ?> InputObj) throws NoSuchElementException {

		String isFound = "0";	
		
		String SearchProjectCode = InputObj.get("ProjectCode").toString();
		String SearchTmpltName = InputObj.get("TmpltName").toString();
		
		TemplateAdminObj Obj = new TemplateAdminObj(webdriver);
		
		WebElement TxtProjectCode = Obj.getTxtProjectCode();
		if (!TxtProjectCode.getAttribute("value").equals(SearchProjectCode)) {
			TxtProjectCode.sendKeys(SearchProjectCode);
		}
		
		WebElement TxtTemplateName = Obj.getTxtTemplateName();
		if (!TxtTemplateName.getAttribute("value").equals(SearchTmpltName)) {
			TxtTemplateName.sendKeys(SearchTmpltName);
		}	
		
	
		WebElement BtnSearch = Obj.getBtnSearch();

		BtnSearch.click();
		
		SeleniumUtil.waitPageRefresh(BtnSearch);
		//SeleniumUtil.waitPageRefreshByLoadingIcon(webdriver);
		WebElement tblResult = Obj.getTblSearchResult();
		
        int tblRow = SeleniumUtil.getTableRows(tblResult);
		if (tblRow > 1) {
			CommUtil.logger.info(" > Template is Found.");
			isFound = "1";
		} else {
			CommUtil.logger.info(" > Template is not Found.");
		} 
		
		return isFound;
		
	}	
	
	public String DeleteTemplate (String TmpltName) throws NoSuchElementException {
		
		String outRetVal = "-1";
		
		String vTmpltColname = "Template Name";
		TemplateAdminObj Obj = new TemplateAdminObj(webdriver);
		WebElement tblResult = Obj.getTblSearchResult();
			
	    int tblRow = SeleniumUtil.getTableRows(tblResult);
	    
	    if(tblRow > 1)
	    {
	    	int colidx = SeleniumUtil.getTableColIdxByName(tblResult, vTmpltColname);
	    	//CommUtil.logger.info(" > Col Index: "+colidx);
			int searchrow = SeleniumUtil.getRowByVal(tblResult, colidx, TmpltName);
			
			//CommUtil.logger.info(" > Row Index: "+searchrow);
			int rowidx = searchrow-1;
			
			WebElement LinkDelect = Obj.getLinkDelect(rowidx);
			
			LinkDelect.click();
			
			SeleniumUtil.waitPageRefresh(LinkDelect);
					
			boolean isBtnDeleteConfirmYesExist = SeleniumUtil.isWebElementExist(webdriver, Obj.getBtnDeleteConfirmYesLocator(), 1);
			if (isBtnDeleteConfirmYesExist) {
			
				WebElement  BtnDeleteConfirmYes = Obj.getBtnDeleteConfirmYes();
				BtnDeleteConfirmYes.click();
				
				SeleniumUtil.waitPageRefresh(BtnDeleteConfirmYes);
				
				boolean isLblSuccessMessageExist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblSuccessMessageLocator(), 1);
				if (isLblSuccessMessageExist) {
					WebElement lblMessage = Obj.getLblSuccessMessage();	
					CommUtil.logger.info(" > Msg: "+lblMessage.getText());
	
					if (CommUtil.isMatchByReg(lblMessage.getText(), "Template \\(\\d+\\) was successfully deleted\\.")) {
						outRetVal = "0";
					} else {
						CommUtil.logger.info(" > Msg not handled.");
						outRetVal = "1";			
					}
				}

			}		
			
			
	    }
		return outRetVal;	
	}
}
