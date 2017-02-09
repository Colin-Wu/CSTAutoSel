package action_lib.production;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import obj_repository.production.EvalQuoteObj;
import obj_repository.production.RepairTechQueueObj;
import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class EvalQuoteAction {
	WebDriver webdriver;

	public EvalQuoteAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public String GoToRepairDetail () throws NoSuchElementException {
		//retVal: -1:error;0:success;1:PartsPending
		String ret = "-1";
		
		EvalQuoteObj Obj = new EvalQuoteObj(webdriver);
	
		WebElement BtnGotoRepairDetail = Obj.getBtnGotoRepairDetail();

		BtnGotoRepairDetail.click();
		
/*		try {
			// solve issue page does not wait
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			CommUtil.logger.info(" > Sleep exception");
			return ret;
		}*/
		//SeleniumUtil.waitPageRefreshByLoadingIcon(webdriver);
		
		boolean isBtnConfirmNoExist = SeleniumUtil.isWebElementExist(webdriver, Obj.getBtnConfirmNoLocator(), 5);
		if (isBtnConfirmNoExist) {
			WebElement BtnConfirmNo = Obj.getBtnConfirmNo();	
			BtnConfirmNo.click();
			RepairTechQueueObj TechQueueObj = new RepairTechQueueObj(webdriver);
			TechQueueObj.getBtnSearch();
			ret = "1";
			
		} else {
			ret = "0";
		}		
		
		return ret;
	}	
	
	public String AddNewEvalQOrdLine (ArrayList<?> InputArr) throws NoSuchElementException  {
		//RetVal:-1:error;0: success;
		String ret = "-1";
		
		int PartCnt = InputArr.size();
			
		for (int i = 0; i < PartCnt; i++) {
			EvalQuoteObj Obj = new EvalQuoteObj(webdriver);
			
			WebElement BtnAddNew = Obj.getBtnAddNew();
			BtnAddNew.click();
			
			@SuppressWarnings("unchecked")
			HashMap<String, String> PartPara = (HashMap<String, String>) InputArr.get(i);
			
			String Type = PartPara.get("Type").toString();
			String PartNum = PartPara.get("PartNum").toString();
			String Qty = PartPara.get("Qty").toString();
			String Billable = PartPara.get("Billable").toString();

			WebElement WebEleSelType = Obj.getSelType(i);
			Select SelType = new Select(WebEleSelType);
			String oldVal = SelType.getFirstSelectedOption().getText();
			
	        if (!oldVal.equals(Type)) {

				boolean isHasVal = SeleniumUtil.isSelectHasOption(SelType, Type);
		    		
				if (!isHasVal) {
					CommUtil.logger.info(" > SelType option not found in UI");
					return ret;
				}
				
				SelType.selectByVisibleText(Type); 
				//SeleniumUtil.waitPageRefreshByLoadingIcon(webdriver);
				
		        SeleniumUtil.waitSelectProperty(webdriver, Obj.getSelTypeLocator(i), oldVal);
	        }        
	        
			WebElement TxtPartNum = Obj.getTxtPartNum(i);
			TxtPartNum.sendKeys(PartNum);	

			WebElement TxtQty = Obj.getTxtQty(i);
			TxtQty.sendKeys(Qty);	
			
			WebElement WebEleSelBillable = Obj.getSelBillable(i);
			Select SelBillable = new Select(WebEleSelBillable);
			
	        if (!SelBillable.getFirstSelectedOption().getText().equals(Billable)) {

				boolean isHasVal = SeleniumUtil.isSelectHasOption(SelBillable, Billable);
		    		
				if (!isHasVal) {
					CommUtil.logger.info(" > SelBillable option not found in UI."+Billable);
					return ret;
				}
				
				SelBillable.selectByVisibleText(Billable); 
	        }
			
			
			WebElement  LinkSave = Obj.getLinkSave(i);
			LinkSave.click();
			
/*			try {
				// solve issue page does not wait
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				CommUtil.logger.info(" > Sleep exception");
				return ret;
			}*/
			//CommUtil.logger.info(" > waitPageRefreshByLoadingIcon");
			SeleniumUtil.waitPageRefreshByLoadingIcon(webdriver);
			//CommUtil.logger.info(" >waitPageRefreshByLoadingIcon done");
			
			boolean isLblSuccessMessageExist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblSuccessMessageLocator(), 0);
			if (isLblSuccessMessageExist) {
				WebElement LblSuccessMessage = Obj.getLblSuccessMessage();	
				CommUtil.logger.info(" > Msg: " + LblSuccessMessage.getText());
				
				if (CommUtil.isMatchByReg(LblSuccessMessage.getText(), "Saved successfully\\.")) {
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
		
		}	


		return ret;
	}
}
