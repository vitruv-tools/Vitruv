package tools.vitruv.framework.correspondence;

/**
 * Generic interface for factories generating {@link CorrespondenceModelView}s
 * for special correspondence types.
 * 
 * @author Heiko Klare
 *
 */
public interface CorrespondenceModelViewFactory<T extends CorrespondenceModelView<?>> {
	public T createCorrespondenceModelView(InternalCorrespondenceModel correspondenceModel);
	public T createEditableCorrespondenceModelView(InternalCorrespondenceModel correspondenceModel);
}
