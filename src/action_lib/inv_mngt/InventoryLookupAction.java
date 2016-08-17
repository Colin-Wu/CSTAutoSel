package action_lib.inv_mngt;

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
	public HashMap<String, String> SearchInventory(HashMap<String, ?> InputObj) throws NoSuchElementException {

		String BoxID ="";
		String PartStatus="";
		String Location="";
		String SN="";
		String PONum="";
		String IsFound="";
		
		HashMap<String, String> RetObj = new HashMap<String, String>();
		RetObj.put("PartStatus",PartStatus);
		RetObj.put("BoxID", BoxID);
		RetObj.put("Location", Location);	
		RetObj.put("SN", SN);	
		RetObj.put("PONum", PONum);	
		RetObj.put("IsFound", IsFound);
		
		String PartNum = InputObj.get("PartNum").toString();
		CommUtil.logger.info(PartNum);
		String SerialNum = InputObj.get("SearchSN").toString();
		CommUtil.logger.info(SerialNum);
		String PONumber = InputObj.get("SearchPONum").toString();
		CommUtil.logger.info(PONumber);
		String ProjectCode = InputObj.get("ProjectCode").toString();
		CommUtil.logger.info(ProjectCode);
		String SearchStatus = InputObj.get("SearchStatus").toString();
		CommUtil.logger.info(SearchStatus);
		String StockGroup = InputObj.get("StockGroup").toString();
		CommUtil.logger.info(StockGroup);
		
		
	    InventoryLookupObj Obj = new InventoryLookupObj(webdriver);
	   
		WebElement TxtProjectCode = Obj.getTxtProjectCode();
		TxtProjectCode.sendKeys(ProjectCode);
		 
		
		WebElement TxtSerialNum = Obj.getTxtSerial();
		TxtSerialNum.sendKeys(SerialNum);
		
		WebElement TxtPONumber = Obj.getTxtPONumber();
		TxtPONumber.sendKeys(PONumber);
		
		WebElement TxtPartNumber= Obj.getTxtPartNumber();
		TxtPartNumber.sendKeys(PartNum);
		
        Select CmbStatus = Obj.getCmbStatus();
    	
		//boolean isHasVal = SeleniumUtil.isSelectHasOption(CmbStatus, SearchStatus);
    	boolean isHasVal = false;
    	List<WebElement> options = CmbStatus.getOptions();
    	for (WebElement option : options) {
    		if(option.getText().equals(SearchStatus)){
    			isHasVal=true;
    			CommUtil.logger.info(" > Status option found in UI");
    			break;
    		}
    	}
    		
		if (!isHasVal) {
			CommUtil.logger.info(" > Status option not found in UI");
			IsFound = "-1";
			RetObj.put("PartStatus",PartStatus);
			RetObj.put("BoxID", BoxID);
			RetObj.put("Location", Location);	
			RetObj.put("SN", SN);	
			RetObj.put("PONum", PONum);	
			RetObj.put("IsFound", IsFound);
			return RetObj;
		}
		
	   CmbStatus.selectByVisibleText(SearchStatus); 
		
       Select CmbStock = Obj.getStockGroup();
       isHasVal=false;
	   //isHasVal = SeleniumUtil.isSelectHasOption(CmbStock, StockGroup);
      
	     options = CmbStock.getOptions();
	     
	   	 for (WebElement option : options) {
	   		if(option.getText().equals(StockGroup)){
	   			isHasVal=true;
	   			CommUtil.logger.info(" > Stock group option found in UI");
	   			break;
	   		}
	   	}
       
	  if (!isHasVal) {
			CommUtil.logger.info(" > Stock group option not found in UI");
			IsFound = "-1";
			RetObj.put("PartStatus",PartStatus);
			RetObj.put("BoxID", BoxID);
			RetObj.put("Location", Location);	
			RetObj.put("SN", SN);	
			RetObj.put("PONum", PONum);	
			RetObj.put("IsFound", IsFound);
			return RetObj;
		}
		
		CmbStock.selectByVisibleText(StockGroup);
		
		WebElement BtnSearch= Obj.getBtnSearch();
		BtnSearch.click();
		
		SeleniumUtil.waitPageRefresh(BtnSearch);
		
        WebElement tblResult = Obj.getTblSearchResult();
		
        int tblRow = SeleniumUtil.getTableRows(tblResult);
        
        if (tblRow > 1) {
        	CommUtil.logger.info(" > Putaway is Found.");
        	int index = 1;
        	List<WebElement> tableRows = tblResult.findElements(By.tagName("tr"));
        	if(tableRows.size() > 0)
        	{
        	   List<WebElement> Columns = tableRows.get(index).findElements(By.tagName("td"));
        	  
        	   if(Columns.size() > 0)
        	   {
	        	   CommUtil.logger.info("> BoxID : " + Columns.get(0).getText());
	        	   BoxID = Columns.get(0).getText();
	        	   CommUtil.logger.info("> PartStatus : " + Columns.get(1).getText());
	        	   PartStatus = Columns.get(1).getText();
	        	   CommUtil.logger.info("> Location : " + Columns.get(8).getText());
	        	   Location = Columns.get(8).getText();
	        	   CommUtil.logger.info("> SN : " + Columns.get(11).getText());
	        	   SN = Columns.get(11).getText();
	        	   CommUtil.logger.info("> PONum : " + Columns.get(4).getText());
	        	   PONum = Columns.get(4).getText();
	        	   IsFound="1";
        	   }
        	   else
        	   {
        		   CommUtil.logger.info(" > Error while retrieving the Putaway Columns..");
           		   IsFound = "-1";
        	   }
        	   
        	}
        	else{
        		CommUtil.logger.info(" > Error while retrieving the Putaway row..");
        		IsFound = "-1";
        	}
		} else{
			CommUtil.logger.info(" > Putaway is not Found.");
			IsFound = "0";
		}
		
        RetObj.put("PartStatus",PartStatus);
		RetObj.put("BoxID", BoxID);
		RetObj.put("Location", Location);	
		RetObj.put("SN", SN);	
		RetObj.put("PONum", PONum);	
		RetObj.put("IsFound", IsFound);
		return RetObj;
	}
}
