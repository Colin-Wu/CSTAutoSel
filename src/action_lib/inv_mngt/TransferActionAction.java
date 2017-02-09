package action_lib.inv_mngt;

import java.util.HashMap;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


import obj_repository.inv_mngt.TransferActionObj;

import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class TransferActionAction {

	WebDriver webdriver;
	
	public TransferActionAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	
	public String SearchTransferAction (HashMap<String, ?> InputObj) throws NoSuchElementException {

		String inStatus = InputObj.get("Status").toString();
		String inBoxID = InputObj.get("BoxID").toString();
		String inPalletID = InputObj.get("PalletID").toString();
	 
		String outIsFound = "-1";
		TransferActionObj Obj= new TransferActionObj(webdriver);

		if (!inStatus.equals("")) {
			WebElement WebEleSelStatus = Obj.getSelStatus();
			Select SelStatus = new Select(WebEleSelStatus);
			
	        if (!SelStatus.getFirstSelectedOption().getAttribute("value").equals(inStatus)) {

				boolean isHasVal = SeleniumUtil.isSelectHasOption(SelStatus, inStatus);
				if (!isHasVal) {
					CommUtil.logger.info(" > Status option not found in UI. "+inStatus);
					return outIsFound;
				}			
				SelStatus.selectByVisibleText(inStatus);	
	        }
		}
		
		WebElement TxtBoxID = Obj.getTxtBoxID();
		if (!TxtBoxID.getAttribute("value").equals(inBoxID)) {
			TxtBoxID.clear();
			TxtBoxID.sendKeys(inBoxID);
		}
		
		WebElement TxtPalletID = Obj.getTxtPalletID();
		if (!TxtPalletID.getAttribute("value").equals(inPalletID)) {
			TxtPalletID.clear();
			TxtPalletID.sendKeys(inPalletID);
		}
				
		WebElement BtnSearch = Obj.getBtnSearch();
		BtnSearch.click();
		//SeleniumUtil.waitPageRefresh(BtnSearch);
		SeleniumUtil.waitPageRefreshByLoadingIcon(webdriver);
		
        WebElement tblResult = Obj.getTblSearchResult();
        int tblRow = SeleniumUtil.getTableRows(tblResult);
	
		if (tblRow > 1) {
			CommUtil.logger.info(" > Rec is found.");
			outIsFound = "1";
		} else {
			CommUtil.logger.info(" > Rec is not found.");
			outIsFound = "0";
		}
		
		return outIsFound;
	}
	
	public String SelectTransfer (String TransNum) throws NoSuchElementException {
		
		String outRetVal = "-1";
		
		String vOrdNumColname = "Transfer #";
		TransferActionObj Obj= new TransferActionObj(webdriver);
		WebElement tblResult = Obj.getTblSearchResult();
			
	    int tblRow = SeleniumUtil.getTableRows(tblResult);
	    
	    if(tblRow > 1)
	    {
	    	int colidx = SeleniumUtil.getTableColIdxByName(tblResult, vOrdNumColname);
	    	//CommUtil.logger.info(" > Col Index: "+colidx);
			int searchrow = SeleniumUtil.getRowByVal(tblResult, colidx, TransNum);
			
			//CommUtil.logger.info(" > Row Index: "+searchrow);
			int rowidx = searchrow-1;
			
			WebElement LinkSelect = Obj.getLinkSelect(rowidx);
			
			LinkSelect.click();
			outRetVal = "0";
	    }
		return outRetVal;	
	}

}
