package tools.vitruv.framework.propagation;

import tools.vitruv.framework.change.description.VitruviusChange;
import tools.vitruv.framework.correspondence.CorrespondenceModel;

public interface ChangeRecordingModelRepository extends ResourceAccess, AutoCloseable {
	CorrespondenceModel getCorrespondenceModel();
	
	VitruviusChange applyChange(VitruviusChange change);

	void startRecording();

	Iterable<ChangeInPropagation> endRecording();
}
