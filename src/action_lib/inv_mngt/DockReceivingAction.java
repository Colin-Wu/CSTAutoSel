package action_lib.inv_mngt;



import java.util.HashMap;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


import obj_repository.inv_mngt.DockReceivingObj;

import script_lib.CommUtil;
import script_lib.SeleniumUtil;


public class DockReceivingAction {
	WebDriver webdriver;

	public DockReceivingAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public HashMap<String, String> DetailReceiving(HashMap<String, ?> InputObj) throws NoSuchElementException {
		
		int pagemaxrow = 25;
		HashMap<String, String> RetObj = new HashMap<String, String>();
		RetObj.put("RetVal","-1");
		RetObj.put("PalletID", "");
		
		String inPartNum = InputObj.get("PartNum").toString();
		String inQty = InputObj.get("Qty").toString();
		String inSN = InputObj.get("SN").toString();
		String inDisposition = InputObj.get("Disposition").toString();
		
		DockReceivingObj Obj = new DockReceivingObj(webdriver);
		WebElement TxtPartNum = Obj.getTxtPartNum();
		TxtPartNum.sendKeys(inPartNum);
	
		WebElement TxtQty = Obj.getTxtQty();
		TxtQty.click();
		SeleniumUtil.waitPageRefresh(TxtPartNum);
		//SeleniumUtil.waitPageRefreshByLoadingIcon(webdriver);
		
		TxtQty = Obj.getTxtQty();
		TxtQty.sendKeys(inQty);
		
		if (!inDisposition.equals("")) {
			WebElement WebEleCmbDisposition = Obj.getCmbDetailReceiveDisposition();
			Select CmbDisposition=new Select(WebEleCmbDisposition);
			boolean isHasVal = SeleniumUtil.isSelectHasOption(CmbDisposition, inDisposition);
			 
			if (!isHasVal) {
		      CommUtil.logger.info(" > Disposition option not found in UI.");
		      RetObj.put("PalletID","");
			  RetObj.put("RetVal", "-1");
			  return RetObj;
			}
			
			CmbDisposition.selectByVisibleText(inDisposition); 
		
		}
		WebElement BtnCreatePalletID = Obj.getBtnCreatePalletID();
		BtnCreatePalletID.click();
		
		SeleniumUtil.waitPageRefresh(BtnCreatePalletID);
        
		WebElement BtnDialogBoxYes = Obj.getBtnYesDialogBox();
		BtnDialogBoxYes.click();
		SeleniumUtil.waitPageRefresh(BtnDialogBoxYes);
		
		WebElement BtnGo = Obj.getBtnGo();
		BtnGo.click();
		SeleniumUtil.waitPageRefresh(BtnGo);

		
		int totalPage = (int) Math.ceil(Double.valueOf(inQty)/pagemaxrow);
		
		for (int pageno = 1; pageno <= totalPage; pageno++) {

	        WebElement tblResult = Obj.getTblDetailReceivingINVParts();
			
			int tblRow = SeleniumUtil.getTableRows(tblResult);
			
			//CommUtil.logger.info("Number of Rows : " + tblRow);
			// Iterate over the rows set the UniqueSerialNum 
			int index0=pagemaxrow*(pageno-1);
			if(tblRow >= 2)
			{
				for(int i=0; i< tblRow-1; i++)
				{
					 
					 WebElement SlNumElement= Obj.getUniqueSerialNum(i);
					 if (index0 != 0) {
						 SlNumElement.sendKeys(inSN+index0++);	
					 } else {
						 SlNumElement.sendKeys(inSN);
						 index0++;
					 }

				}
			}
			
			if (pageno != totalPage) {
				WebElement BtnNext = Obj.getBtnNext();
				BtnNext.click();
				SeleniumUtil.waitPageRefresh(BtnNext);
			}
		}
        
		WebElement BtnSave = Obj.getBtnSaveDetailReceiving();
		BtnSave.click();
		SeleniumUtil.waitPageRefresh(BtnSave);
		
		boolean isLblMsgexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblSuccessMessageDetailReceivingLocator(), 10);
		if (isLblMsgexist) {
			
			WebElement lblSuccessMessage = Obj.getLblSuccessDetailReceivingMessage();	
			CommUtil.logger.info("MSG:" +lblSuccessMessage.getText());
			if (CommUtil.isMatchByReg(lblSuccessMessage.getText(), "Dock Entry has been saved\\.")) {
				
				 RetObj.put("PalletID",Obj.gettxtPalletId().getAttribute("value"));
				 //CommUtil.logger.info(" > Pallet ID"+ Obj.gettxtPalletId().getAttribute("value"));
				 RetObj.put("RetVal", "0");
			}
		} else {
			CommUtil.logger.info("Message does not exist.");
		}
		// CommUtil.logger.info(" > RetVal "+  RetObj.get("RetVal").toString());
		 return RetObj;
	}
	
	
	public String DocReceivingGoToDetail(String TrackNum) throws NoSuchElementException{
		String RetValue = "-1";	
		DockReceivingObj Obj = new DockReceivingObj(webdriver);
		
		WebElement TxtTrackingNumber = Obj.getTxtTrackNum();
		TxtTrackingNumber.sendKeys(TrackNum);
		
		
		WebElement BtnSerach = Obj.getBtnSearch();
		BtnSerach.click();
		SeleniumUtil.waitPageRefresh(BtnSerach);
		
		WebElement tblDocReceivingResult = Obj.getTblSearchResult();
		int tblRow = SeleniumUtil.getTableRows(tblDocReceivingResult);
		
		if(tblRow < 2){
			RetValue= "-1";
		}
		
		else{
				WebElement BtnEditDetials = Obj.getBtnEditDetials();
				BtnEditDetials.click();
				SeleniumUtil.waitPageRefresh(BtnEditDetials);
				
				boolean isLblMsgexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblErrMessageLocator(), 0);
				
				if(isLblMsgexist){
					WebElement lblMessage = Obj.getLblErrMessage();	
					CommUtil.logger.info("> Error Message : " + lblMessage.getText());					
					if (CommUtil.isMatchByReg(lblMessage.getText(), "This PO has been canceled, please contact PS\\.")) {
						RetValue = "1";
					} else {
						RetValue = "-1";
					}

			    }
			    else{
					RetValue="0";
			    }
		   }
		
		//CommUtil.logger.info("Return Vale : "+RetValue);
		return  RetValue;
	}
	
	public String NewDocReceiving (HashMap<String, ?> InputObj) throws NoSuchElementException {
		//RetVal:-1:error;0: successful;1:Project code not found in the list
		String ret = "-1";	
		String TrackNum = InputObj.get("TrackNum").toString();
		String PONum = InputObj.get("PONum").toString();
		String Carrier = InputObj.get("Carrier").toString();
		String ProjectCode = InputObj.get("ProjectCode").toString();
		String BoxCnt = InputObj.get("BoxCnt").toString();
		String PalletCnt = InputObj.get("PalletCnt").toString();
		
		DockReceivingObj Obj = new DockReceivingObj(webdriver);
	
		WebElement BtnNewDockRcv = Obj.getBtnNewDockRcv();
		BtnNewDockRcv.click();
		
		//CommUtil.logger.info("clicked..");
		SeleniumUtil.waitPageRefresh(BtnNewDockRcv);
			
		WebElement TxtTrackingNumber = Obj.getTxtTrackingNumber();
		TxtTrackingNumber.sendKeys(TrackNum);	
		
		WebElement TxtPONumber = Obj.getTxtPONumber();
		TxtPONumber.sendKeys(PONum);	
		WebElement TxtCarrier = Obj.getTxtCarrier();
		TxtCarrier.click();
		
		SeleniumUtil.waitPageRefresh(TxtPONumber);
		
		TxtCarrier = Obj.getTxtCarrier();
		TxtCarrier.sendKeys(Carrier);	
		
		Select SelProjectCode = Obj.getSelProjectCode();
		boolean isHasVal = SeleniumUtil.isSelectHasOption(SelProjectCode, ProjectCode);
		if (!isHasVal) {
			CommUtil.logger.info(" > Project code not found in project list");
			ret = "1";
			return ret;
		}
		
		SelProjectCode.selectByVisibleText(ProjectCode);
		
		WebElement TxtBoxCount = Obj.getTxtBoxCount();
		TxtBoxCount.sendKeys(BoxCnt);	
		
		WebElement TxtPalletCount = Obj.getTxtPalletCount();
		TxtPalletCount.sendKeys(PalletCnt);
		
		WebElement BtnSave = Obj.getBtnSave();
		BtnSave.click();
		
		SeleniumUtil.waitPageRefresh(BtnSave);

		boolean isLblMsgexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblSuccessMessageLocator(), 0);
		if (isLblMsgexist) {
			
			WebElement lblSuccessMessage = Obj.getLblSuccessMessage();	

			if (CommUtil.isMatchByReg(lblSuccessMessage.getText(), "Dock Entry has been saved\\.")) {
				CommUtil.logger.info(" > Dock Entry has been saved.");
				ret = "0";
			}
		}
		
		return ret;
	}
	
	public String SearchDocReceiving (HashMap<String, ?> InputObj) throws NoSuchElementException {
		String isFound = "-1";	
		//RetVal:-1:error;0: successful;1:Project code not found in the list
		String TrackNum = InputObj.get("TrackNum").toString();
		String PONum = InputObj.get("PONum").toString();
		String ProjectCode = InputObj.get("ProjectCode").toString();
		
		DockReceivingObj Obj = new DockReceivingObj(webdriver);		

		WebElement TxtSearchTrackNum = Obj.getTxtSearchTrackNum();
		TxtSearchTrackNum.sendKeys(TrackNum);	
		
		WebElement TxtSearchPONum = Obj.getTxtSearchPONum();
		TxtSearchPONum.sendKeys(PONum);
		
		WebElement TxtSearchProjectCode = Obj.getTxtSearchProjectCode();
		TxtSearchProjectCode.sendKeys(ProjectCode);
		
		WebElement BtnSearch = Obj.getBtnSearch();
		BtnSearch.click();
		
		WebElement tblResult = Obj.getTblSearchResult();
		
		int tblRow = SeleniumUtil.getTableRows(tblResult);
		
		if (tblRow > 1) {
			CommUtil.logger.info(" > Doc Receiving is Found.");
			isFound = "1";
		} else {
			CommUtil.logger.info(" > Doc Receiving is not Found.");
		}
				
		return isFound;
	}
	public String SearchResultGotoDetail (HashMap<String, ?> InputObj) throws NoSuchElementException {
		//RetVal:-1:error;0: successful;1:PO is cancelled.
		String ret = "-1";	
		
		int delCol = 0;
		
		String TrackNum = InputObj.get("TrackNum").toString();
		
		DockReceivingObj Obj = new DockReceivingObj(webdriver);
		
		WebElement tblResult = Obj.getTblSearchResult();
			
		int colidx = SeleniumUtil.getTableColIdxByName(tblResult, "Tracking Number");

		int searchrow = SeleniumUtil.getRowByVal(tblResult, colidx, TrackNum);

		By locator = Obj.getLinkEditDetailLocator();
		
		WebElement editDetaillink = SeleniumUtil.getCellElement(webdriver, tblResult, delCol, searchrow, locator);
		
		if (editDetaillink != null) {
			CommUtil.logger.info(" > EditDetail link clicked. TrackNum:" + TrackNum);
			editDetaillink.click();
		}
		
		SeleniumUtil.waitPageRefresh(editDetaillink);
		
		boolean isLblMsgexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblErrMessageLocator(), 0);
		
		if (isLblMsgexist) {
			WebElement lblMessage = Obj.getLblErrMessage();	

			if (CommUtil.isMatchByReg(lblMessage.getText(), "This PO has been canceled, please contact PS\\.")) {
				CommUtil.logger.info(" > Msg: This PO has been canceled, please contact PS.");
				ret = "1";
			}
		} else {
/*			boolean isBtnYesexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getBtnConfirmYesLocator(), 0);
			if (isBtnYesexist) {
				WebElement BtnConfirmYes = Obj.getBtnConfirmYes();
				BtnConfirmYes.click();
				
				SeleniumUtil.waitPageRefresh(BtnConfirmYes);
				
				isLblMsgexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblSuccessMessageLocator(), 0);
				if (isLblMsgexist) {
					
					WebElement lblSuccessMessage = Obj.getLblSuccessMessage();	

					if (CommUtil.isMatchByReg(lblSuccessMessage.getText(), "PO delete successfully\\.")) {
						CommUtil.logger.info(" > PO delete successfully.");
						ret = "0";
					}
				}
				
				
			}*/
			
		}	
		
		return ret;
	}
	
	public HashMap<String, String> GoToEditLine (String TrackNum) throws NoSuchElementException {
		// ret:0 Success;1: Edit link disabled.
		String outRetVal = "-1";
		HashMap<String, String> RetObj = new HashMap<String, String>();
		RetObj.put("RetVal",outRetVal);
		RetObj.put("RowIdx", "");

		
		DockReceivingObj Obj = new DockReceivingObj(webdriver);
		
		WebElement tblResult = Obj.getTblSearchResult();
			

		
	    int tblRow = SeleniumUtil.getTableRows(tblResult);
	    
	    if(tblRow > 1) {
	    	
			int colidx = SeleniumUtil.getTableColIdxByName(tblResult, "Tracking Number");

			int searchrow = SeleniumUtil.getRowByVal(tblResult, colidx, TrackNum);
			
			//CommUtil.logger.info(" > Row Index: "+searchrow);
			int rowidx = searchrow-1;
			
			WebElement LinkEditLine = Obj.getLinkEditLine(rowidx);

			String LinkEditLineclass = LinkEditLine.getAttribute("href");
			if (LinkEditLineclass == null) {
				outRetVal = "1";
				RetObj.put("RetVal",outRetVal);	
				return RetObj;	

			}
			
			LinkEditLine.click();
			outRetVal = "0";
			RetObj.put("RetVal",outRetVal);
			RetObj.put("RowIdx", Integer.toString(rowidx));
	    }
		return RetObj;	
	}
	
	public String FillEditLine (HashMap<String, String> InputObj) throws NoSuchElementException  {
		//RetVal:-1:error;0: success;1:Project code not found in project list;2:PO and Project code are disabled.
		String ret = "-1";
		String inTrackNum = InputObj.get("TrackNum").toString();
		String inPONum = InputObj.get("PONum").toString();
		String inCarrier = InputObj.get("Carrier").toString();
		String inProjectCode = InputObj.get("ProjectCode").toString();
		String inBoxCnt = InputObj.get("BoxCnt").toString();
		String inPalletCnt = InputObj.get("PalletCnt").toString();
		String inRowIdx = InputObj.get("RowIdx").toString();


		DockReceivingObj Obj = new DockReceivingObj(webdriver);
		
		SeleniumUtil.waitWebElementVisible(webdriver, By.xpath(".//input[@id='ContentPlaceHolder1_gv_Entities_txtTrackingNumber_" + inRowIdx + "']"));

		WebElement TxtPONumber = Obj.getTxtPONumber(inRowIdx);
		String POdisabled = TxtPONumber.getAttribute("disabled");
		WebElement WebEleSelProjectCode  = Obj.getSelProjectCode(inRowIdx);
		String ProjectCodedisabled = WebEleSelProjectCode.getAttribute("disabled");
		if (POdisabled != null && ProjectCodedisabled != null) {
			if (POdisabled.equals("true")&&ProjectCodedisabled.equals("true")) {
				CommUtil.logger.info(" > PO and Project code are disabled.");
				ret = "2";
				return ret;			
			}
		}
			
		if (!inTrackNum.equals("")) {
			WebElement TxtTrackingNumber = Obj.getTxtTrackingNumber(inRowIdx);
			TxtTrackingNumber.clear();
			TxtTrackingNumber.sendKeys(inTrackNum);	
		}
		
		if (!inPONum.equals("")) {
			TxtPONumber = Obj.getTxtPONumber(inRowIdx);
			TxtPONumber.clear();
			TxtPONumber.sendKeys(inPONum);	
			
			WebElement TxtCarrier = Obj.getTxtCarrier(inRowIdx);
			TxtCarrier.click();
			
			SeleniumUtil.waitPageRefresh(TxtPONumber);
		}
		

		if (!inCarrier.equals("")) {
			WebElement TxtCarrier = Obj.getTxtCarrier(inRowIdx);
			TxtCarrier.clear();
			TxtCarrier.sendKeys(inCarrier);	
		}
		
		if (!inProjectCode.equals("")) {
			WebEleSelProjectCode  = Obj.getSelProjectCode(inRowIdx);
			Select SelProjectCode = new Select(WebEleSelProjectCode);
			boolean isHasVal = SeleniumUtil.isSelectHasOption(SelProjectCode, inProjectCode);
			if (!isHasVal) {
				CommUtil.logger.info(" > Project code not found in project list");
				ret = "1";
				return ret;
			}
			SelProjectCode.selectByVisibleText(inProjectCode);
		}
		
		if (!inBoxCnt.equals("")) {
			WebElement TxtBoxCount = Obj.getTxtBoxCount(inRowIdx);
			TxtBoxCount.clear();
			TxtBoxCount.sendKeys(inBoxCnt);	
		}
		
		if (!inPalletCnt.equals("")) {
			WebElement TxtPalletCount = Obj.getTxtPalletCount(inRowIdx);
			TxtPalletCount.clear();
			TxtPalletCount.sendKeys(inPalletCnt);
		}
		
		ret = "0";
		return ret;
	}
	public String SaveLine (HashMap<String, String> InputObj) throws NoSuchElementException  {
		//RetVal:-1:error;0: success;1:Project code not found in project list;2:PO and Project code are disabled.
		String ret = "-1";

		String inRowIdx = InputObj.get("RowIdx").toString();

		DockReceivingObj Obj = new DockReceivingObj(webdriver);

		
		WebElement BtnSave = Obj.getLinkSaveLine(inRowIdx);
		BtnSave.click();
		
		SeleniumUtil.waitPageRefresh(BtnSave);
		
/*		boolean isBtnYesDialogBoxexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getBtnYesDialogBoxConfirmPOLocator(), 5);
		if (isBtnYesDialogBoxexist) {
			
			WebElement BtnYesDialogBoxConfirmPO = Obj.getBtnYesDialogBoxConfirmPO();
			BtnYesDialogBoxConfirmPO.click();
			SeleniumUtil.waitPageRefresh(BtnYesDialogBoxConfirmPO);
		}*/
		boolean isLblMsgexist = SeleniumUtil.isWebElementExist(webdriver, Obj.getLblSuccessMessageLocator(), 5);
		if (isLblMsgexist) {
			
			WebElement lblSuccessMessage = Obj.getLblSuccessMessage();	
			CommUtil.logger.info(" > Msg: " +lblSuccessMessage.getText());
			if (CommUtil.isMatchByReg(lblSuccessMessage.getText(), "Dock Entry has been saved\\.")) {

				ret = "0";
			}
		}
			
		return ret;
	}
}
