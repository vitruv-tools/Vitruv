/**
 * 
 */
package tools.vitruv.extensions.changevisualization;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import tools.vitruv.framework.change.description.PropagatedChange;

/**
 * Base class for all visualization-dependent change-information. Each different visualization type
 * implements its own specific ChangeDataSet. This class stores all information useful for all
 * different implementations
 * 
 * @author Andreas Löffler
 */
public abstract class ChangeDataSet {
	
	protected static enum ChangeType{
		ORIGINAL_CHANGE,
		CONSEQUENTIAL_CHANGE
	}
		
	//these are managed outside of this class and are here only as a central storage point
	private Hashtable<String,Object> userInfos=new Hashtable<String,Object>();
	
	private final String className;
	private final String testName;
	private final Date creationTime;
	private final String cdsId;
	
	private String srcModelID;
	private String dstModelID;
	
	private int nrPChanges=0;	
	private int nrOChanges=0;
	private int nrCChanges=0;	
		
	public ChangeDataSet(String cdsId, String className, String testName, List<PropagatedChange> propagationResult) {
		this.className=className;
		this.testName=testName;		
		this.cdsId=cdsId;
		this.creationTime=new Date();
		
		//I do not store the propagationResult here, i extract all needed data
		//Maybe this behaviour changes sometimes (if holding on to the original list doesn't have unexpected side effects
		//or unwanted huge memory consumation
		
		//Set modelId to root id of source model
		//Wurde ausgelassen da es nicht mehr genutzt wird und in JUnit zu Fehlern führte
		srcModelID="EGAL_SRC";//"HC:"+ModelHelper.getRootObject(propagationResult.get(0).getOriginalChange()).hashCode();//ModelHelper.getRootObjectID(origChange);
		dstModelID="EGAL_DST";//"HC:"+ModelHelper.getRootObject(propagationResult.get(0).getConsequentialChanges()).hashCode();//ModelHelper.getRootObjectID(consequentialChanges);
				
		extractData(propagationResult);
		
	}
	
	protected abstract void extractData(List<PropagatedChange> propagationResult);
	
	public abstract Object getData();
		
	public String getCdsID() {
		return cdsId;
	}	
	
	public String toString() {
		SimpleDateFormat df=new SimpleDateFormat("HH:mm:ss");
		return cdsId+" ("+df.format(creationTime)+")";
	}

	public String getSrcModelID() {
		return srcModelID;
	}

	public String getDstModelID() {
		return dstModelID;
	}

	public Date getCreationTime() {
		return creationTime;
	}
	
	protected void setNrPChanges(int nrPChanges) {
		this.nrPChanges=nrPChanges;
	}

	protected void setNrOChanges(int nrOChanges) {
		this.nrOChanges=nrOChanges;
	}

	protected void setNrCChanges(int nrCChanges) {
		this.nrCChanges=nrCChanges;
	}
	
	public int getNrPChanges() {
		return nrPChanges;
	}

	public int getNrOChanges() {
		return nrOChanges;
	}

	public int getNrCChanges() {
		return nrCChanges;
	}
	
	public void clearUserInfos() {
		userInfos.clear();
	}

	public void setUserInfo(String string, Object object) {
		userInfos.put(string,object);	
	}

	public Object getUserInfo(String string) {
		return userInfos.get(string);
	}

	public boolean hasUserInfo(String string) {
		return userInfos.containsKey(string);
	}

}
