package tools.vitruv.variability.vave;

import java.util.Collection;

import tools.vitruv.framework.change.description.VitruviusChange;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

public interface VirtualProductModel extends InternalVirtualModel {

	public String getConfiguration();

	public Collection<VitruviusChange> getDeltas();

	public void clearDeltas();

}
