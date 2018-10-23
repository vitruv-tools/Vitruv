package tools.vitruv.framework.correspondence;

/**
 * Extension of the generic {@link CorrespondenceModelView}, but without generically typed operations.
 * Nevertheless, it is still a view, handling only specific types of correspondences, which are not
 * known from outside.
 * 
 * @author Heiko Klare
 *
 */
public interface CorrespondenceModel extends CorrespondenceModelView<Correspondence> {

}
