package action_lib.inv_mngt;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import obj_repository.inv_mngt.InventoryLookupObj;

import script_lib.CommUtil;
import script_lib.SeleniumUtil;

public class InventoryLookupAction {
	
	WebDriver webdriver;

	public InventoryLookupAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	public HashMap<Object, Object> SearchInventory(HashMap<String, ?> InputObj) throws NoSuchElementException {

		int boxidColSeq = 0;
		int partStatusColSeq = 1;
		int PONumColSeq = 4;
		int LocationColSeq = 8;
		int SNColSeq = 11;

		int pagemaxrow = 25;
		
		
		
		
		String BoxID ="";
		String PartStatus="";
		String Location="";
		String SN="";
		String PONum="";
		String IsFound="";

		HashMap<String, String> RstObj = new HashMap<String, String>();
		RstObj.put("BoxID", BoxID);
		RstObj.put("PartStatus",PartStatus);
		RstObj.put("PONum", PONum);	
		RstObj.put("Location", Location);	
		RstObj.put("SN", SN);	
		
		ArrayList<HashMap<String, String>> rstArr = new ArrayList<HashMap<String, String>>();
		
		HashMap<Object, Object> RetObj = new HashMap<Object, Object>();
		RetObj.put("IsFound", IsFound);
		RetObj.put("RstArr", rstArr);
		
/*		
		HashMap<String, String> RetObj = new HashMap<String, String>();
		RetObj.put("PartStatus",PartStatus);
		RetObj.put("BoxID", BoxID);
		RetObj.put("Location", Location);	
		RetObj.put("SN", SN);	
		RetObj.put("PONum", PONum);	
		RetObj.put("IsFound", IsFound);
		*/
		String PartNum = InputObj.get("PartNum").toString();
		//CommUtil.logger.info(PartNum);
		String SerialNum = InputObj.get("SearchSN").toString();
		//CommUtil.logger.info(SerialNum);
		String PONumber = InputObj.get("SearchPONum").toString();
		//CommUtil.logger.info(PONumber);
		String ProjectCode = InputObj.get("ProjectCode").toString();
		//CommUtil.logger.info(ProjectCode);
		String SearchStatus = InputObj.get("SearchStatus").toString();
		//CommUtil.logger.info(SearchStatus);
		String StockGroup = InputObj.get("StockGroup").toString();
		//CommUtil.logger.info(StockGroup);
		String PalletID = InputObj.get("PalletID").toString();
		//CommUtil.logger.info(PalletID);
		//String inQty = InputObj.get("Qty").toString();
		//CommUtil.logger.info(inQty);
		
		//int totalPage = (int) Math.ceil(Double.valueOf(inQty)/pagemaxrow);		
		
	    InventoryLookupObj Obj = new InventoryLookupObj(webdriver);
	   
		WebElement TxtProjectCode = Obj.getTxtProjectCode();
		if (!TxtProjectCode.getAttribute("value").equals(ProjectCode)) {
			TxtProjectCode.sendKeys(ProjectCode);
		}
		
		WebElement TxtSerialNum = Obj.getTxtSerial();
		if (!TxtSerialNum.getAttribute("value").equals(SerialNum)) {
			TxtSerialNum.sendKeys(SerialNum);
		}
		
		WebElement TxtPONumber = Obj.getTxtPONumber();
		if (!TxtPONumber.getAttribute("value").equals(PONumber)) {
			TxtPONumber.sendKeys(PONumber);
		}
		
		WebElement TxtPartNumber= Obj.getTxtPartNumber();
		if (!TxtPartNumber.getAttribute("value").equals(PartNum)) {
			TxtPartNumber.sendKeys(PartNum);
		}

		WebElement TxtPallet= Obj.getTxtPallet();
		if (!TxtPallet.getAttribute("value").equals(PalletID)) {
			TxtPallet.sendKeys(PalletID);
		}

		boolean isHasVal = false;
        Select CmbStatus = Obj.getCmbStatus();
        if (!CmbStatus.getFirstSelectedOption().getAttribute("value").equals(SearchStatus)) {

			isHasVal = SeleniumUtil.isSelectHasOption(CmbStatus, SearchStatus);
	/*    	boolean isHasVal = false;
	    	List<WebElement> options = CmbStatus.getOptions();
	    	for (WebElement option : options) {
	    		if(option.getText().equals(SearchStatus)){
	    			isHasVal=true;
	    			CommUtil.logger.info(" > Status option found in UI");
	    			break;
	    		}
	    	}*/
	    		
			if (!isHasVal) {
				CommUtil.logger.info(" > Status option not found in UI");
				IsFound = "-1";
				RetObj.put("IsFound", IsFound);
				return RetObj;
			}
			
		   CmbStatus.selectByVisibleText(SearchStatus); 
        }
		
       Select CmbStock = Obj.getStockGroup();
       isHasVal=false;
       
       String selStockgroup = ProjectCode + " - " + StockGroup;
       if (!CmbStock.getFirstSelectedOption().getAttribute("value").equals(selStockgroup)) {
       
		   isHasVal = SeleniumUtil.isSelectHasOption(CmbStock, selStockgroup);
	      
	/*	     options = CmbStock.getOptions();
		     
		   	 for (WebElement option : options) {
		   		if(option.getText().equals(StockGroup)){
		   			isHasVal=true;
		   			CommUtil.logger.info(" > Stock group option found in UI");
		   			break;
		   		}
		   	}*/
	       
		  if (!isHasVal) {
				CommUtil.logger.info(" > Stock group option not found in UI");
				IsFound = "-1";
				RetObj.put("IsFound", IsFound);
				return RetObj;
			}
			
			CmbStock.selectByVisibleText(selStockgroup);
       }
		
		WebElement BtnSearch= Obj.getBtnSearch();
		BtnSearch.click();
		
		SeleniumUtil.waitPageRefresh(BtnSearch);
		
        WebElement tblResult = Obj.getTblSearchResult();
		
        int tblRow = SeleniumUtil.getTableRows(tblResult);
        
        if (tblRow > 1) {
        	CommUtil.logger.info(" > Search result is Found.");
        	List<WebElement> tableRows = tblResult.findElements(By.xpath("./tr"));
        	
        	int rstMaxRow = 0;
        	if (tableRows.size() == pagemaxrow+2) {
        		rstMaxRow = tableRows.size()-2;
        	} else {
        		rstMaxRow = tableRows.size()-1;
        	}      	
        	
        	for (int index = 1; index <=rstMaxRow; index++){
        		
        	   List<WebElement> Columns = tableRows.get(index).findElements(By.xpath("./td"));
        	  
        	   if(Columns.size() > 0) {
        		   RstObj = new HashMap<String, String>();
	        	   //CommUtil.logger.info("> BoxID : " + Columns.get(boxidColSeq).getText());
	        	   BoxID = Columns.get(boxidColSeq).getText();
	        	   //CommUtil.logger.info("> PartStatus : " + Columns.get(partStatusColSeq).getText());
	        	   PartStatus = Columns.get(partStatusColSeq).getText();
	        	   //CommUtil.logger.info("> Location : " + Columns.get(LocationColSeq).getText());
	        	   Location = Columns.get(LocationColSeq).getText();
	        	   //CommUtil.logger.info("> SN : " + Columns.get(SNColSeq).getText());
	        	   SN = Columns.get(SNColSeq).getText();
	        	   //CommUtil.logger.info("> PONum : " + Columns.get(PONumColSeq).getText());
	        	   PONum = Columns.get(PONumColSeq).getText();
	        	   
		       	   RstObj.put("BoxID", BoxID);
		    	   RstObj.put("PartStatus",PartStatus);
		    	   RstObj.put("PONum", PONum);	
		    	   RstObj.put("Location", Location);	
		    	   RstObj.put("SN", SN);
		    	   
		    	   rstArr.add(RstObj);

        	   } else {
					CommUtil.logger.info(" > Error while retrieving the Putaway Columns..");
					IsFound = "-1";
	       			RetObj.put("IsFound", IsFound);
	       			return RetObj;
        	   }
        	   
        	}
        	
     	   IsFound="1";

		} else{
			CommUtil.logger.info(" > Search result is not Found.");
			IsFound = "0";
		}
		
        
		RetObj.put("IsFound", IsFound);
		RetObj.put("RstArr", rstArr);
		return RetObj;
	}
}
