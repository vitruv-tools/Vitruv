package tools.vitruvius.extensions.dslsruntime.mapping.datatypes;

import org.eclipse.emf.ecore.EClass;

import tools.vitruvius.framework.change.echange.EChange;


public class ChangeType {
	public static final ChangeType ANY_CHANGE = new ChangeType(null, null);
	
	/* both: null -> any */
	private EClass contextType;
	private Class<? extends EChange> changeType;

	/* both: null -> any */
	public ChangeType(EClass contextType, Class<? extends EChange> changeType) {
		this.contextType = contextType;
		this.changeType = changeType;
	}

	public EClass getContextType() {
		return contextType;
	}

	public void setContextType(EClass contextType) {
		this.contextType = contextType;
	}

	public Class<? extends EChange> getChangeType() {
		return changeType;
	}

	public void setChangeType(Class<? extends EChange> changeType) {
		this.changeType = changeType;
	}
	
	public boolean covers(EChange change) {
		if ((this.changeType == null) && (this.contextType == null))
			return true;
		else
			throw new UnsupportedOperationException("TODO: implement");
	}
}