package autoMain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import script_lib.CommUtil;

public class Reslist {
	
	//private IdentityHashMap<String ,String> map = new IdentityHashMap<String,String>();
	private HashMap<String ,ArrayList<String>> map = new HashMap<String,ArrayList<String>>();
	
/*	public synchronized boolean addSN (String casename, String SN) {
		boolean ret = false;
		CommUtil.logger.info(Thread.currentThread().getName()+"try to add SN:"+SN);

		if (map.containsValue(SN)) {
			CommUtil.logger.info(Thread.currentThread().getName()+"contain SN:"+map);
			return ret;
		}
		
		map.put(casename, SN);
		CommUtil.logger.info(Thread.currentThread().getName()+"SN added:"+map);
		ret = true;
		return ret;
	}*/
	
	public synchronized boolean addBoxid (String casename, String Boxid) {
		boolean ret = false;
		CommUtil.logger.info(Thread.currentThread().getName()+"> try to add Boxid:"+Boxid);

		Collection<ArrayList<String>> vals = map.values();
		for (ArrayList<String> str : vals) {
		    for (int i=0; i< str.size(); i++) {
		    	if (str.get(i).equals(Boxid)) {
					CommUtil.logger.info(Thread.currentThread().getName()+"> contain Boxid:"+map);
					return ret;
		    	}
		    }
		}
		
/*		if (map.containsValue(Boxid)) {
			CommUtil.logger.info(Thread.currentThread().getName()+"> contain Boxid:"+map);
			return ret;
		}*/
		
		if (map.containsKey(casename)) {
			ArrayList<String> valarr = map.get(casename);
			valarr.add(Boxid);
			map.put(casename, valarr);
		} else {
			ArrayList<String> valarr = new ArrayList<String>();
			valarr.add(Boxid);
			map.put(casename, valarr);
		}
		
		CommUtil.logger.info(Thread.currentThread().getName()+"> Boxid added:"+map);
		ret = true;
		return ret;
	}
	
/*	public void releaseSN(String casename) {
		map.remove(casename);
		CommUtil.logger.info(Thread.currentThread().getName()+"case:"+casename+",removed SN:"+map);
	}*/
	
	public void releaseBoxid(String casename) {
		map.remove(casename);
		CommUtil.logger.info(Thread.currentThread().getName()+"> case:"+casename+",removed Boxid:"+map);
	}
	
	public boolean containCaseid(String caseid) {
		
		boolean ret = false;
		
		if (map.containsKey(caseid)) {
			ret = true;
		}
		
		return ret;
	}
	
}
