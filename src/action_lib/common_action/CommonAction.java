package action_lib.common_action;


import java.util.ArrayList;
import java.util.HashMap;


import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import action_lib.cst_main.CST_MainAction;
import action_lib.inv_mngt.DockReceivingAction;
import action_lib.inv_mngt.InvIndexAction;
import action_lib.inv_mngt.InventoryLookupAction;
import action_lib.inv_mngt.ReceivingPutawayAction;
import action_lib.odr_mngt.CreatePOAction;
import action_lib.odr_mngt.OrderIndexAction;
import action_lib.odr_mngt.POMngtAction;
import config.TestSetting;
import script_lib.CommUtil;



public class CommonAction {

	WebDriver webdriver;

	public CommonAction(WebDriver webdriver) {
		super();
		this.webdriver = webdriver;
	}
	
	public HashMap<String, String> CreateStdPO_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {

		CommUtil.logger.info(" > MenuOrdMngt");
		CST_MainAction mainpage = new CST_MainAction(webdriver);
		mainpage.MenuOrdMngt();
		
		CommUtil.logger.info(" > GotoPOMngt");
		OrderIndexAction ordidxpage = new OrderIndexAction(webdriver);
		ordidxpage.GotoPOMngt();
			
		CommUtil.logger.info(" > NewPO");
		POMngtAction pomngtpage = new POMngtAction(webdriver);
		pomngtpage.NewPO();
		
		CommUtil.logger.info(" > CreateStandardPO");
		CreatePOAction createPOpage = new CreatePOAction(webdriver);
		HashMap<String, String> retObj = createPOpage.CreateStandardPO(InputObj);
		
		return retObj;
	}
	
	public String SearchPO_CA (HashMap<String, ?> InputObj) throws NoSuchElementException  {
		String isFound = "0";	
		
		CommUtil.logger.info(" > MenuOrdMngt");
		CST_MainAction mainpage = new CST_MainAction(webdriver);
		mainpage.MenuOrdMngt();
		
		CommUtil.logger.info(" > GotoPOMngt");
		OrderIndexAction ordidxpage = new OrderIndexAction(webdriver);
		ordidxpage.GotoPOMngt();
			
		CommUtil.logger.info(" > SearchPO");
		POMngtAction pomngtpage = new POMngtAction(webdriver);
		isFound = pomngtpage.SearchPO(InputObj);
		
		return isFound;
	}
	
	public String ReceiveToDetailReceiving_CA (HashMap<String, ?> InputObj) throws NoSuchElementException 
	{
		String PalletID = "0";	
		
		String inPONum = InputObj.get("PONum").toString();
		String inCarrier = InputObj.get("Carrier").toString();
	    String inProjectCode = InputObj.get("ProjectCode").toString();
	    String inBoxCnt = InputObj.get("BoxCnt").toString();
	    String inPalletCnt = InputObj.get("PalletCnt").toString();
	    String inTrackNum = InputObj.get("TrackNum").toString();
	    String inPartNum = InputObj.get("PartNum").toString();
	    String inQty = InputObj.get("Qty").toString();
	    String inSN = InputObj.get("SN").toString();
	    String inDisposition = InputObj.get("Disposition").toString();
	    
	    CommUtil.logger.info(" > MenuInventroy");
	    CST_MainAction mainpage = new CST_MainAction(webdriver);
		mainpage.MenuInventory();
		
		CommUtil.logger.info(" > InvIndex");
		
		InvIndexAction invIndexpage = new InvIndexAction(webdriver);
		invIndexpage.GotoDockReceiving();
		
		CommUtil.logger.info(" > New Doc receiving..");
		DockReceivingAction docReceiving = new DockReceivingAction(webdriver);
		
		HashMap<String, String> DetailRecevingInputObject = new HashMap<String, String>();
		DetailRecevingInputObject.put("TrackNum",inTrackNum);
		DetailRecevingInputObject.put("PONum", inPONum);
		DetailRecevingInputObject.put("Carrier",inCarrier);
		DetailRecevingInputObject.put("ProjectCode", inProjectCode);
		DetailRecevingInputObject.put("BoxCnt",inBoxCnt);
		DetailRecevingInputObject.put("PalletCnt", inPalletCnt);
		
		String outResult = docReceiving.NewDocReceiving(DetailRecevingInputObject);
	    
		if(outResult.equals("1"))
		{
			CommUtil.logger.info(" > NewDocReceiving :Project not found in the project list. Project:"+ inProjectCode);
			CST_MainAction cstMainaction = new CST_MainAction(webdriver);
			cstMainaction.logout();
		}
		
		CommUtil.logger.info(" > DocReceivingGoToDetail");
		String OutRetVal=docReceiving.DocReceivingGoToDetail(inTrackNum);
		
		if(OutRetVal.equals("0"))
		{
			CommUtil.logger.info("> DocReceivingGoToDetail: Success.");
		}
		else
		{
			CommUtil.logger.info("DocReceivingGoToDetail : Failed. Code:"+OutRetVal);
		}

		CommUtil.logger.info(" > DetailReceiving");
		HashMap<String, String> InputObject = new HashMap<String, String>();
		InputObject.put("PartNum",inPartNum);
		InputObject.put("Qty", inQty);
		InputObject.put("SN",inSN);
		InputObject.put("Disposition", inDisposition);
		HashMap<String, String> OutputObject= docReceiving.DetailReceiving(InputObj);
		
		String OutDetailReceivingRetVal= OutputObject.get("RetVal").toString();
		PalletID = OutputObject.get("PalletID").toString();
		
		if(OutDetailReceivingRetVal.equals("0"))
		{
			CommUtil.logger.info("> DetailReceiving. Pass.");
		}
		else
		{
			CommUtil.logger.info("> DetailReceiving. Failed. Code"+OutDetailReceivingRetVal);
		}
		
	    return PalletID;
	}
	
	
    @SuppressWarnings("unchecked")
	public ArrayList<HashMap<String, String>> GetAvailablePart_CA(HashMap<String, ?> InputObj) throws NoSuchElementException
    {
    	CommUtil.logger.info(" > GetAvailablePart_CA");
    	String inQty = InputObj.get("Qty").toString();
    	String inProjectCode = TestSetting.Project;
    	String inSearchPartNum =TestSetting.ProjectPart1;   
    	String inSN = "";
    	String inPONum = "";
    	String inStatus = "PUTAWAY"; 
    	String inStockGroup = TestSetting.StockGroup; 
/*    	String strBoxId="";
    	String strSN="";*/
    	ArrayList<HashMap<String, String>> rstArr = null;
    	
    	HashMap<String, String> RetObj = new HashMap<String, String>();
    	
    	RetObj.put("BoxID", "");
    	RetObj.put("SN", "");
    	
    	CommUtil.logger.info(" > MenuInventroy");
	    CST_MainAction mainpage = new CST_MainAction(webdriver);
		mainpage.MenuInventory();
		
		InvIndexAction invIndexpage = new InvIndexAction(webdriver);
		invIndexpage.GotoInventoryLookup();
		
		InventoryLookupAction inventoryLookupAction = new InventoryLookupAction(webdriver);
		HashMap<String, String> InputInventoryObject = new HashMap<String, String>();
		InputInventoryObject.put("PartNum", inSearchPartNum);
		InputInventoryObject.put("SearchSN", inSN);
		InputInventoryObject.put("SearchPONum", inPONum);
		InputInventoryObject.put("ProjectCode", inProjectCode);
		InputInventoryObject.put("SearchStatus", inStatus);
		InputInventoryObject.put("StockGroup", inStockGroup);
		InputInventoryObject.put("Qty", inQty);
		HashMap<Object, Object>OutputInventoryLookup= inventoryLookupAction.SearchInventory(InputInventoryObject);
    			
        String outIsFound= (String) OutputInventoryLookup.get("IsFound");
        
        if(!outIsFound.equals("1")) {
        	GenerateStandardPart_CA(inQty); 
        	
        	mainpage= new CST_MainAction(webdriver);
        	mainpage.MenuInventory();
        	
        	invIndexpage = new InvIndexAction(webdriver); 
        	invIndexpage.GotoInventoryLookup();
			
        	OutputInventoryLookup= inventoryLookupAction.SearchInventory(InputInventoryObject);
        	outIsFound= OutputInventoryLookup.get("IsFound").toString();
        	
        	if(!outIsFound.equals("1")) {
        		CommUtil.logger.info(">SearchInventory Failure. Code:" +outIsFound);
        		CST_MainAction cstMainaction = new CST_MainAction(webdriver);
        		CommUtil.logger.info(">Logging Out...");
    			cstMainaction.logout();
        	} else {
        		rstArr= (ArrayList<HashMap<String, String>>) OutputInventoryLookup.get("RstArr");       		
        	}
        } else {
        	rstArr= (ArrayList<HashMap<String, String>>) OutputInventoryLookup.get("RstArr"); 
        	if (rstArr.size() < Integer.parseInt(inQty)) {
            	GenerateStandardPart_CA(inQty); 
            	
            	mainpage= new CST_MainAction(webdriver);
            	mainpage.MenuInventory();
            	
            	invIndexpage = new InvIndexAction(webdriver); 
            	invIndexpage.GotoInventoryLookup();
    			
            	OutputInventoryLookup= inventoryLookupAction.SearchInventory(InputInventoryObject);
            	outIsFound= OutputInventoryLookup.get("IsFound").toString();
            	
            	if(!outIsFound.equals("1")) {
            		CommUtil.logger.info(">SearchInventory Failure. Code:" +outIsFound);
            		CST_MainAction cstMainaction = new CST_MainAction(webdriver);
            		CommUtil.logger.info(">Logging Out...");
        			cstMainaction.logout();
            	} else {
            		rstArr= (ArrayList<HashMap<String, String>>) OutputInventoryLookup.get("RstArr");       		
            	}        		
        	}   	
   	
        }
        
    	return rstArr;
    }
    
    public void GenerateStandardPart_CA(String Qty) throws NoSuchElementException
    {	
    	        String inQty = Qty;
    			String inPONum=TestSetting.StandardPONum; // Hard  coded string in UFT test cases

    		    String inMode = "0"; // comment out the mode 
    			
    		    String inProjCode = TestSetting.Project;
    			
    		    String inPartNum = TestSetting.ProjectPart1;
    			String inOrdNum = "";
    			String inCarrier=TestSetting.ShipVia;
    			String inBoxCnt="1";
    			String inPalletCnt ="1";
    			
    			String inSN=CommUtil.genDateTimeStamp();
    			String inBoxID="";
    			
    			String inLocation=TestSetting.StockLocation;
    			String inDisposition = "Good";
    			
    			HashMap<String, String> InputGeneratePart=new HashMap<String, String>();
      
    			InputGeneratePart.put("ProjectCode", inProjCode);
    			InputGeneratePart.put("PartNum", inPartNum);
    			InputGeneratePart.put("Qty", inQty);
    			InputGeneratePart.put("OrdNum", inOrdNum);
    			InputGeneratePart.put("PONum", inPONum);
    			InputGeneratePart.put("Carrier",inCarrier );
    			InputGeneratePart.put("BoxCnt", inBoxCnt);
    			InputGeneratePart.put("PalletCnt",inPalletCnt);
    			InputGeneratePart.put("SN", inSN);
    			InputGeneratePart.put("BoxID", inBoxID);
    			InputGeneratePart.put("Location", inLocation);
    			InputGeneratePart.put("Disposition", inDisposition);
    			InputGeneratePart.put("Mode", inMode);
    			
    			CommUtil.logger.info("Calling..GeneratePart_CA");
    			this.GeneratePart_CA(InputGeneratePart);
    }
    
    public String GeneratePart_CA(HashMap<String, String> InputObj) throws NoSuchElementException{
    	
    	String inMode = InputObj.get("Mode").toString();
    	CommUtil.logger.info("Mode:"+inMode);
    	String inProjCode = InputObj.get("ProjectCode").toString();
    	//CommUtil.logger.info(inProjCode);
    	String inPartNum = InputObj.get("PartNum").toString();
    	//CommUtil.logger.info(inPartNum);
    	String inQty = InputObj.get("Qty").toString();
    	//CommUtil.logger.info(inQty);
    	String inOrdNum = InputObj.get("OrdNum").toString();
    	//CommUtil.logger.info(inOrdNum);
    	String inPONum = InputObj.get("PONum").toString(); 
    	//CommUtil.logger.info(inPONum);
    	
      
        String vTrackNum=CommUtil.genDateTimeStamp(); 
        
        if( inMode.equals("1"))
         {
        	 HashMap<String, String> InputPoObject = new HashMap<String, String>();
        	 InputPoObject.put("ProjectCode", inProjCode);
        	 InputPoObject.put("PartNum", inPartNum);
        	 InputPoObject.put("Qty", inQty);
        	 InputPoObject.put("OrdNum", inOrdNum);
        	 HashMap<String, String> OutputPoObject = this.CreateStdPO_CA(InputPoObject);
        	 inPONum=OutputPoObject.get("PONum").toString();
         }
         
        InputObj.put("TrackNum", vTrackNum);
        InputObj.put("PONum", inPONum);
       
        CommUtil.logger.info("Calling ReceiveToPutaway_CA");
        this.ReceiveToPutaway_CA(InputObj);
        return inPONum;
    }
    
    @SuppressWarnings("unchecked")
	public ArrayList<HashMap<String, String>> ReceiveToPutaway_CA(HashMap<String, ?> InputObj) throws NoSuchElementException{
    	        
    	String inPONum = InputObj.get("PONum").toString();
    	//String inCarrier = InputObj.get("Carrier").toString();
    	String inProjectCode=InputObj.get("ProjectCode").toString();
    	//String inBoxCnt=InputObj.get("BoxCnt").toString();
    	//String inPalletCnt=InputObj.get("PalletCnt").toString();
    	//String inTrackNum=InputObj.get("TrackNum").toString();
    	String inPartNum=InputObj.get("PartNum").toString();
    	String inQty=InputObj.get("Qty").toString();
    	String inSN=InputObj.get("SN").toString();
    	String inBoxID=InputObj.get("BoxID").toString();
    	String inLocation=InputObj.get("Location").toString();
    	//String inDisposition = InputObj.get("Disposition").toString();
    	
    	String outIsFound = "-1";
    	ArrayList<HashMap<String, String>> rstArr;
    	
    	
    	CommUtil.logger.info("Calling ReceiveToDetailReceiving_CA");
    	 //CommUtil.logger.info("ReceiveToPutaway_CA ...read inputs");
    	
    	String OutPalletID= this.ReceiveToDetailReceiving_CA(InputObj);
    	
    	//CommUtil.logger.info("Pallet ID" + OutPalletID);
    	
    	if(!inQty.equals("1")) {
    		inSN = "";
    	}
    	
    	HashMap<String, String> InputPutAway=new HashMap<String, String>();
    	InputPutAway.put("SN",inSN);
    	InputPutAway.put("ProjectCode",inProjectCode);
    	InputPutAway.put("PalletID",OutPalletID);
    	InputPutAway.put("BoxID",inBoxID);
    	InputPutAway.put("Location",inLocation);
    	InputPutAway.put("PartNum",inPartNum);
    	InputPutAway.put("PONum",inPONum);
    	InputPutAway.put("Qty",inQty);
    	
    	 CommUtil.logger.info("Calling PutawayToInv_CA  ...");
    	
    	HashMap<Object, Object> OutputInvputaway=this.PutawayToInv_CA(InputPutAway);
    	outIsFound= OutputInvputaway.get("IsFound").toString();
    	rstArr= (ArrayList<HashMap<String, String>>) OutputInvputaway.get("RstArr");
    
    	if(outIsFound.equals("1")){
    		CommUtil.logger.info(">PutawayToInv.Success: " +inPartNum+" Result Array size: "+rstArr.size());
/*    		for (int i = 0; i < rstArr.size(); i++) {
    			HashMap<String, String> retObj = rstArr.get(i);
    			CommUtil.logger.info(">Result Obj : "+i);
    			for (Entry<String, String> entry : retObj.entrySet()) {
    				CommUtil.logger.info("> Key:" + entry.getKey()+" value: "+ entry.getValue());
    			}
    		}*/
    		
    		
    	}
    	else if (outIsFound.equals("0")) {
    		CommUtil.logger.info(">ReceiveToPutaway.Part not found. PartNum: " +inPartNum );
    		CST_MainAction cstMainaction = new CST_MainAction(webdriver);
    		CommUtil.logger.info(">Logging Out...");
			cstMainaction.logout();
    	}
    	else
    	{
    		CommUtil.logger.info(">ReceiveToPutaway.Error. PartNum: " +inPartNum );
    		CST_MainAction cstMainaction = new CST_MainAction(webdriver);
    		CommUtil.logger.info(">Logging Out...");
			cstMainaction.logout();
    	}
    	return rstArr;
    }
    
    public HashMap<Object, Object> PutawayToInv_CA(HashMap<String, ?> InputObj) throws NoSuchElementException
    {
       HashMap<Object, Object> OutPutObj = new HashMap<Object, Object>();
       OutPutObj.put("IsFound", "");
   
       String outIsFound = "-1";
       
       String inSN = InputObj.get("SN").toString();
       //CommUtil.logger.info(inSN);
       String inProjCode = InputObj.get("ProjectCode").toString();
       //CommUtil.logger.info(inProjCode);
       String inPalletID = InputObj.get("PalletID").toString();
       //CommUtil.logger.info(inPalletID);
       String inBoxID = InputObj.get("BoxID").toString();
       //CommUtil.logger.info(inBoxID);
       String inLocation = InputObj.get("Location").toString();
       //CommUtil.logger.info(inLocation);
       String inPartNum = InputObj.get("PartNum").toString();
       //CommUtil.logger.info(inPartNum);
       String inPONum = InputObj.get("PONum").toString();
       //CommUtil.logger.info(inPONum);
       String inQty=InputObj.get("Qty").toString();
      
       
       CST_MainAction cstMain= new CST_MainAction(webdriver);
       cstMain.MenuInventory();
       InvIndexAction InvIndex = new InvIndexAction(webdriver);
       InvIndex.GotoReceivingPutaway();
       
       ReceivingPutawayAction ReceivingPutaway= new ReceivingPutawayAction(webdriver);
      
       HashMap<String, String> ReceivingPutawayInPutObj = new HashMap<String, String>();
       ReceivingPutawayInPutObj.put("Status","PendPutaway");
       ReceivingPutawayInPutObj.put("SN","");
       ReceivingPutawayInPutObj.put("ProjectCode",inProjCode);
       ReceivingPutawayInPutObj.put("PalletID",inPalletID);
       ReceivingPutawayInPutObj.put("BoxID",inBoxID);
       CommUtil.logger.info("SearchPutaway");
       outIsFound= ReceivingPutaway.SearchPutaway(ReceivingPutawayInPutObj);
       
       //CommUtil.logger.info("SearchPutaway : " + outIsFound);
      // RunAction "SearchPutaway [ReceivingPutaway]", oneIteration, "PendPutaway",inSN,inProjCode,inPalletID,inBoxID,outIsFound
       if(outIsFound.equals("1"))
       {
    	   CommUtil.logger.info(">SearchPutaway-PendPutaway Item found.");
       }
       else if(outIsFound.equals("0"))
       {
    	   CommUtil.logger.info(">SearchPutaway-PendPutaway Item not found. SN: " + inSN+" Projectcode : "+inProjCode+" PalletID : "+inPalletID+" BoxID"+inBoxID);
    	   CommUtil.logger.info(">Logging Out..");
    	   CST_MainAction cstMainaction = new CST_MainAction(webdriver);
    	   cstMainaction.logout();
       }
       else
       {
    	   CommUtil.logger.info(">SearchPutaway-PendPutaway Item found.Error..");
    	   CommUtil.logger.info(">Logging Out..");
    	   CST_MainAction cstMainaction = new CST_MainAction(webdriver);
    	   cstMainaction.logout();
       }
       
      CommUtil.logger.info("SaveToPutawayready");
       
      String OutRetVal= ReceivingPutaway.SaveToPutawayready(inLocation);
      //CommUtil.logger.info(" SaveToPutawayready : Return val"+OutRetVal);
      
      if(OutRetVal.equals("0")) {
   	   CommUtil.logger.info("SaveToPutawayready - Success");
      }
      else
      {
   	     CommUtil.logger.info("SaveToPutawayready - Failure. Code:"+OutRetVal);
   	     CST_MainAction cstMainaction = new CST_MainAction(webdriver);
 	     cstMainaction.logout();
      }
      
       ReceivingPutaway= new ReceivingPutawayAction(webdriver);
       
       ReceivingPutawayInPutObj = new HashMap<String, String>();
       ReceivingPutawayInPutObj.put("Status","PutawayReady");
       ReceivingPutawayInPutObj.put("SN","");
       ReceivingPutawayInPutObj.put("ProjectCode",inProjCode);
       ReceivingPutawayInPutObj.put("PalletID",inPalletID);
       ReceivingPutawayInPutObj.put("BoxID",inBoxID);
       CommUtil.logger.info("Calling SearchPutaway");
       outIsFound= ReceivingPutaway.SearchPutaway(ReceivingPutawayInPutObj);

       //CommUtil.logger.info("Calling SearchPutaway return val"+outIsFound);
     
       if(outIsFound.equals("1")){
    	   CommUtil.logger.info(">SearchPutaway-PutawayReady Item found.");
       }
       else if(outIsFound.equals("0")){
    	   CommUtil.logger.info(">SearchPutaway-PutawayReady Item not found. SN: " + inSN+" Projectcode : "+inProjCode+" PalletID : "+inPalletID+" BoxID"+inBoxID);
    	   CommUtil.logger.info(">Logging Out..");
    	   CST_MainAction cstMainaction = new CST_MainAction(webdriver);
    	   cstMainaction.logout();
       }
       else{
    	   CommUtil.logger.info(">SearchPutaway-PutawayReady Item found.Error..");
    	   CommUtil.logger.info(">Logging Out..");
    	   CST_MainAction cstMainaction = new CST_MainAction(webdriver);
    	   cstMainaction.logout();
       }
       CommUtil.logger.info("Calling SaveToPutaway");
       String outRetVal= ReceivingPutaway.SaveToPutaway(inQty);
       //CommUtil.logger.info("Calling SaveToPutaway : return val"+outRetVal);
      
       if(outRetVal.equals("0")) {
    	   CommUtil.logger.info("SaveToPutaway - Success");
       }
       else{
    	    CommUtil.logger.info("SaveToPutaway - Failure. Code:"+outRetVal);
    	    CST_MainAction cstMainaction = new CST_MainAction(webdriver);
  	        cstMainaction.logout();
       }
       
       cstMain= new CST_MainAction(webdriver); // correct it - new object
       cstMain.MenuInventory();
       InvIndex = new InvIndexAction(webdriver);
       InvIndex.GotoInventoryLookup();
       
      CommUtil.logger.info("Calling Inventory lookup...");
      InventoryLookupAction InvLookup = new InventoryLookupAction(webdriver);
      HashMap<String, String> InventoryLookupObj = new HashMap<String, String>();
      InventoryLookupObj.put("PartNum",inPartNum);
      InventoryLookupObj.put("SearchSN",inSN);
      InventoryLookupObj.put("SearchPONum",inPONum);
      InventoryLookupObj.put("ProjectCode",inProjCode);
      InventoryLookupObj.put("SearchStatus","");
      InventoryLookupObj.put("PalletID",inPalletID);
      InventoryLookupObj.put("StockGroup",TestSetting.StockGroup);
      InventoryLookupObj.put("Qty", inQty);     
      HashMap<Object, Object> OutPutInventoryObj=InvLookup.SearchInventory(InventoryLookupObj);
      // RunAction "SearchInventory [InventoryLookup]", oneIteration, inPartNum, inSN, inPONum, inProjCode,"","", outIsFound, outPartStatus, outBoxid, outLocation, outSN, outPONum
      OutPutObj.put("IsFound", OutPutInventoryObj.get("IsFound").toString());
  	  OutPutObj.put("RstArr", OutPutInventoryObj.get("RstArr"));
      
      return OutPutObj;
    }
    
}
