package autoMain;

import java.util.ArrayList;
import java.util.HashMap;

import script_lib.CommUtil;

public class Caselist {
    private ArrayList<HashMap<String, String>> vCaselist;
    
    private int currentCaseIdx = 0;
    private int currentprogress = 0;
    
    public Caselist(ArrayList<HashMap<String, String>> caselist) {
        this.vCaselist = caselist;

    }
    
    public synchronized HashMap<String, String> fetchCase(){
    	HashMap<String, String> ret = null;
    	
    	if (currentCaseIdx >=vCaselist.size()) {
    		return ret;
    	}
    	   	
    	ret = vCaselist.get(currentCaseIdx);
    	CommUtil.logger.info(Thread.currentThread().getName()+" fetched case:"+ret.get("casename"));

    	currentCaseIdx++;
        return ret;

    }
 
    public synchronized void completeCase() {
    	
    	currentprogress++;
    	
    }
    
    public int getProgress() {
    	int ret = 0;
    	
    	ret = (int)((double)(currentprogress*1.0/vCaselist.size())*100);
    	//CommUtil.logger.info(Thread.currentThread().getName()+" currentprogress:"+currentprogress+",size:"+vCaselist.size()+",ret:"+ret);
   	
    	return ret;
    	
    }
}
