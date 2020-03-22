package tools.vitruv.framework.tuid;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Class that is only used to serialize and deserialize Tuids.
 * @author kramerm
 *
 */
public class Tuid4Serialization implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4128645104543074428L;
	private String tuidString;
	
	protected void setTuidString(String tuidString) {
		this.tuidString = tuidString;
	}
	
    private Object readResolve() throws ObjectStreamException {
    	return Tuid.getInstance(this.tuidString);
    }
}
