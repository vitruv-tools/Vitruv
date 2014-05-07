/**
 */
package pcm_mockup;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Repository</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link pcm_mockup.Repository#getInterfaces <em>Interfaces</em>}</li>
 * </ul>
 * </p>
 *
 * @see pcm_mockup.Pcm_mockupPackage#getRepository()
 * @model
 * @generated
 */
public interface Repository extends Identifier {
    /**
     * Returns the value of the '<em><b>Interfaces</b></em>' containment reference list.
     * The list contents are of type {@link pcm_mockup.Interface}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Interfaces</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Interfaces</em>' containment reference list.
     * @see pcm_mockup.Pcm_mockupPackage#getRepository_Interfaces()
     * @model containment="true"
     * @generated
     */
    EList<Interface> getInterfaces();

} // Repository
