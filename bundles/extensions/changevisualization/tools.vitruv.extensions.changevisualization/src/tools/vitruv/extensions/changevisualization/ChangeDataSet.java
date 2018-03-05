package tools.vitruv.extensions.changevisualization;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.description.PropagatedChange;
import tools.vitruv.framework.domains.VitruvDomain;

/**
 * Base class for all visualization-dependent change-information. Each different visualization type
 * implements its own specific ChangeDataSet. This class stores all information useful for all
 * different implementations.
 * 
 * @author Andreas Loeffler
 */
public abstract class ChangeDataSet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6142833170227828098L;

	/**
	 * The different types of changes
	 * 
	 * @author Andreas Loeffler
	 */
	public static enum ChangeType{
		ORIGINAL_CHANGE,
		CONSEQUENTIAL_CHANGE
	}

	/**
	 * User infos are additional information stored as key/value pairs. The ChangeDataSet acts as the storage point,
	 * but does not use these information directly. It only provides a common usable access to it.
	 */
	private final Hashtable<String,Object> userInfos=new Hashtable<String,Object>();

	/**
	 * The time this cds is created
	 */
	private final Date creationTime;

	/**
	 * The ID of this cds
	 */
	private final String cdsId;
	
	private final String sourceModelInfo;
	private final String targetModelInfo;

	/**
	 * The number of propagated changes in this cds
	 */
	private int nrPChanges=0;

	/**
	 * The number of original changes in this cds (sum over all propagated changes)
	 */
	private int nrOChanges=0;

	/**
	 * The number of consequential changes in this cds (sum over all propagated changes)
	 */
	private int nrCChanges=0;	

	/**
	 * This constructor is called by subclasses to initialize the general ChangeDataSet
	 * 
	 * @param cdsId The ID of the cds
	 * @param propagationResult The propagation result whose values should be represented by this cds
	 */
	protected ChangeDataSet(String cdsId, VitruvDomain sourceDomain, VitruvDomain targetDomain, List<PropagatedChange> propagationResult) {
		this.cdsId=cdsId;
		this.creationTime=new Date();
		
		sourceModelInfo=sourceDomain==null?"-":sourceDomain.getName();
		targetModelInfo=targetDomain==null?"-":targetDomain.getName();
		
		extractData(propagationResult);		
	}	

	/**
	 * Subclasses have to implement their own visualization-dependent information extraction
	 * @param propagationResult The propagation result to process
	 */
	protected abstract void extractData(List<PropagatedChange> propagationResult);

	/**
	 * Returns the visualization-dependent object that is needed to visualize the cds 
	 * @return The visualization-dependent object needed to visualize this cds
	 */
	public abstract Object getData();

	/**
	 * Returns the ID of this cds
	 * 
	 * @return The ID of this cds
	 */
	public String getCdsID() {
		return cdsId;
	}	

	@Override
	public String toString() {
		SimpleDateFormat df=new SimpleDateFormat("HH:mm:ss");
		return cdsId+" ("+df.format(creationTime)+")";
	}

	/**
	 * Returns the creation time of this cds
	 * 
	 * @return The creation time of this cds
	 */
	public Date getCreationTime() {
		return creationTime;
	}

	/**
	 * Sets the number of propagated changes
	 * 
	 * @param nrPChanges The number of propageted changes
	 */
	protected void setNrPChanges(int nrPChanges) {
		this.nrPChanges=nrPChanges;
	}

	/**
	 * Sets the number of original changes
	 * 
	 * @param nrOChanges The number of original changes
	 */
	protected void setNrOChanges(int nrOChanges) {
		this.nrOChanges=nrOChanges;
	}

	/**
	 * Sets the number of consequential changes
	 * 
	 * @param nrCChanges The number of consequential changes
	 */
	protected void setNrCChanges(int nrCChanges) {
		this.nrCChanges=nrCChanges;
	}

	/**
	 * Gets the number of propagated changes
	 * 
	 * @return The number of propageted changes
	 */
	public int getNrPChanges() {
		return nrPChanges;
	}

	/**
	 * Gets the number of original changes
	 * 
	 * @return The number of original changes
	 */
	public int getNrOChanges() {
		return nrOChanges;
	}

	/**
	 * Gets the number of consequential changes
	 * 
	 * @return The number of consequential changes
	 */
	public int getNrCChanges() {
		return nrCChanges;
	}

	/**
	 * Resets the stored user infos
	 */
	public void clearUserInfos() {
		userInfos.clear();
	}

	/**
	 * Stores the given value under the given key in the local key/value storage
	 * 
	 * @param key The key
	 * @param value The value
	 */
	public void setUserInfo(String key, Object value) {
		userInfos.put(key,value);	
	}

	/**
	 * Gets the stored user info for the given key. Null if none exists.
	 * 
	 * @param key The key
	 * @return The value stored for this key, may be null
	 */
	public Object getUserInfo(String key) {
		return userInfos.get(key);
	}

	/**
	 * Checks whether a stored values exists for a given key
	 * 
	 * @param key The key
	 * @return true if a value exists for this key, false otherwise
	 */
	public boolean hasUserInfo(String key) {
		return userInfos.containsKey(key);
	}

	public String getSourceModelInfo() {
		return sourceModelInfo;
	}

	public String getTargetModelInfo() {
		return targetModelInfo;
	}

}
