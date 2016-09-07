/**
 */
package tools.vitruvius.extensions.integration.correspondence.integration;

import tools.vitruvius.dsls.response.meta.correspondence.response.ResponseCorrespondence;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Correspondence</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.extensions.integration.correspondence.integration.IntegrationCorrespondence#isCreatedByIntegration <em>Created By Integration</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.extensions.integration.correspondence.integration.IntegrationPackage#getIntegrationCorrespondence()
 * @model
 * @generated
 */
public interface IntegrationCorrespondence extends ResponseCorrespondence {
	/**
	 * Returns the value of the '<em><b>Created By Integration</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Created By Integration</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Created By Integration</em>' attribute.
	 * @see #setCreatedByIntegration(boolean)
	 * @see tools.vitruvius.extensions.integration.correspondence.integration.IntegrationPackage#getIntegrationCorrespondence_CreatedByIntegration()
	 * @model default="false"
	 * @generated
	 */
	boolean isCreatedByIntegration();

	/**
	 * Sets the value of the '{@link tools.vitruvius.extensions.integration.correspondence.integration.IntegrationCorrespondence#isCreatedByIntegration <em>Created By Integration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Created By Integration</em>' attribute.
	 * @see #isCreatedByIntegration()
	 * @generated
	 */
	void setCreatedByIntegration(boolean value);

} // IntegrationCorrespondence
