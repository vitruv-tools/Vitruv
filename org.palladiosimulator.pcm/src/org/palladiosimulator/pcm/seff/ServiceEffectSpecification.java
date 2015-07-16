/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff;

import java.util.Map;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Signature;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Service Effect Specification</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> Service Effect Specification Models the effect of invoking a specific
 * service of a basic component. Therefore, it references a Signature from an Interface, for which
 * the component takes a ProvidedRole, to identify the described service. This class is abstract and
 * SEFFs for specific analysis purposes need to inherit from this class. A BasicComponent may have
 * an arbitrary number of SEFFs. It can have multiple SEFFs of a different type for a single
 * provided service. For example, one SEFF can express all external service calls with no particular
 * order, while another one includes a restricted order, or still another one expresses resource
 * demands of the service. While different SEFF types have been proposed, the only type currently
 * included in the meta-model is the ResourceDemandingSEFF for performance prediction. Different
 * types of SEFFs should not contradict each other if the languages are equally powerful. For
 * example, the order of allowed external service calls should be the same for each SEFF type
 * modelling sequences of such calls if the modelling languages have the same expressiveness. SEFFs
 * are part of a component and not part of an interface, because they are implementation dependent.
 * The SEFFs of a CompositeComponent are not represented in the meta-model and can be derived
 * automatically by connecting the SEFFs of the encapsulated components of its nested
 * AssemblyContexts. Different SEFFs of a single component access the same component parameter
 * specifications. That means that parameter dependencies to the same component parameters in
 * different SEFF types refer also to the same characterisations. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.palladiosimulator.pcm.seff.ServiceEffectSpecification#getSeffTypeID
 * <em>Seff Type ID</em>}</li>
 * <li>{@link org.palladiosimulator.pcm.seff.ServiceEffectSpecification#getDescribedService__SEFF
 * <em>Described Service SEFF</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.ServiceEffectSpecification#getBasicComponent_ServiceEffectSpecification
 * <em>Basic Component Service Effect Specification</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.seff.SeffPackage#getServiceEffectSpecification()
 * @model abstract="true"
 * @extends CDOObject
 * @generated
 */
public interface ServiceEffectSpecification extends CDOObject {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Seff Type ID</b></em>' attribute. The default value is
     * <code>"1"</code>. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Seff Type ID</em>' attribute isn't clear, there really should be
     * more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Seff Type ID</em>' attribute.
     * @see #setSeffTypeID(String)
     * @see org.palladiosimulator.pcm.seff.SeffPackage#getServiceEffectSpecification_SeffTypeID()
     * @model default="1" required="true" ordered="false"
     * @generated
     */
    String getSeffTypeID();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.ServiceEffectSpecification#getSeffTypeID
     * <em>Seff Type ID</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Seff Type ID</em>' attribute.
     * @see #getSeffTypeID()
     * @generated
     */
    void setSeffTypeID(String value);

    /**
     * Returns the value of the '<em><b>Described Service SEFF</b></em>' reference. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Described Service SEFF</em>' reference isn't clear, there really
     * should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Described Service SEFF</em>' reference.
     * @see #setDescribedService__SEFF(Signature)
     * @see org.palladiosimulator.pcm.seff.SeffPackage#getServiceEffectSpecification_DescribedService__SEFF()
     * @model required="true"
     * @generated
     */
    Signature getDescribedService__SEFF();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.ServiceEffectSpecification#getDescribedService__SEFF
     * <em>Described Service SEFF</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Described Service SEFF</em>' reference.
     * @see #getDescribedService__SEFF()
     * @generated
     */
    void setDescribedService__SEFF(Signature value);

    /**
     * Returns the value of the '<em><b>Basic Component Service Effect Specification</b></em>'
     * container reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.repository.BasicComponent#getServiceEffectSpecifications__BasicComponent
     * <em>Service Effect Specifications Basic Component</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Basic Component Service Effect Specification</em>' container
     * reference isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Basic Component Service Effect Specification</em>' container
     *         reference.
     * @see #setBasicComponent_ServiceEffectSpecification(BasicComponent)
     * @see org.palladiosimulator.pcm.seff.SeffPackage#getServiceEffectSpecification_BasicComponent_ServiceEffectSpecification()
     * @see org.palladiosimulator.pcm.repository.BasicComponent#getServiceEffectSpecifications__BasicComponent
     * @model opposite="serviceEffectSpecifications__BasicComponent" required="true"
     *        transient="false" ordered="false"
     * @generated
     */
    BasicComponent getBasicComponent_ServiceEffectSpecification();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.ServiceEffectSpecification#getBasicComponent_ServiceEffectSpecification
     * <em>Basic Component Service Effect Specification</em>}' container reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Basic Component Service Effect Specification</em>'
     *            container reference.
     * @see #getBasicComponent_ServiceEffectSpecification()
     * @generated
     */
    void setBasicComponent_ServiceEffectSpecification(BasicComponent value);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     *
     * @param diagnostics
     *            The chain of diagnostics to which problems are to be appended.
     * @param context
     *            The cache of context-specific information. <!-- end-model-doc -->
     * @model annotation=
     *        "http://www.eclipse.org/uml2/1.1.0/GenModel body='if self.describedService__SEFF.oclIsKindOf(pcm::repository::OperationSignature) then\r\n\tself.basicComponent_ServiceEffectSpecification.providedRoles_InterfaceProvidingEntity->exists(p | p.oclIsKindOf(pcm::repository::OperationProvidedRole) and (p.oclAsType(pcm::repository::OperationProvidedRole).providedInterface__OperationProvidedRole = self.describedService__SEFF.oclAsType(pcm::repository::OperationSignature).interface__OperationSignature))\r\nelse\r\n\tif self.describedService__SEFF.oclIsKindOf(pcm::repository::EventType) then\r\n\t\tself.basicComponent_ServiceEffectSpecification.providedRoles_InterfaceProvidingEntity->exists(p | p.oclIsKindOf(pcm::repository::SinkRole) and (p.oclAsType(pcm::repository::SinkRole).eventGroup__SinkRole = self.describedService__SEFF.oclAsType(pcm::repository::EventType).eventGroup__EventType))\r\n\telse\r\n\t\ttrue\r\n\tendif\r\nendif'"
     * @generated
     */
    boolean ReferencedSignatureMustBelongToInterfaceReferencedByProvidedRole(DiagnosticChain diagnostics,
            Map<Object, Object> context);

} // ServiceEffectSpecification
