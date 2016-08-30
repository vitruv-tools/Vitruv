package edu.kit.ipd.sdq.vitruvius.framework.correspondence.tuid;

import java.io.ObjectStreamException;
import java.io.Serializable;

import edu.kit.ipd.sdq.vitruvius.framework.correspondence.tuid.TUID;

/**
 * Class that is only used to serialize and deserialize TUIDs.
 * @author kramerm
 *
 */
public class TUID4Serialization implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4128645104543074428L;
	private String tuidString;
	
	protected void setTUIDString(String tuidString) {
		this.tuidString = tuidString;
	}
	
    private Object readResolve() throws ObjectStreamException {
    	return TUID.getInstance(this.tuidString);
    }
}
