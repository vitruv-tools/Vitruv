/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.subsystem;

import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.RepositoryComponent;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Sub System</b></em>'. <!--
 * end-user-doc -->
 *
 * <!-- begin-model-doc --> A SubSystem is structually comparable to a CompositeComponent. The major
 * difference is the white-blox property it preserves for System Deployers. While Component
 * Developer have a white-box view for their CompositeComponents, a System Deployer perceives a
 * CompositeComponent like any other component as a black-box entity, which thus cannot be allocated
 * onto different nodes in the resource environment (a CompositeComponent cannot be split up at
 * allocation time). Opposed to that, SubSystems are white-box entities for System Deployers,
 * meaning that they can be allocated to different nodes of the resource environment, if required.
 * They are pure logical groupings of components, which can be reused by Component Developers and
 * System Architects like usual components.
 *
 * Remark 1: If a SubSystem is part of a CompositeComponent (inner component) is looses its
 * white-box property, as there is a outer black-box component hiding the its and consequently the
 * SubSytem's internals.
 *
 * Remark 2: Structurally, SubSytem can be converted into CompositeComponents and vice versa.
 *
 * Example: To model a layered architecture, of which each layer is potentially split up to run on
 * multiple machines (in the resource environment), each layer can be represented by a subsystem,
 * allowing to allocated each layer's components individually. <!-- end-model-doc -->
 *
 *
 * @see org.palladiosimulator.pcm.subsystem.SubsystemPackage#getSubSystem()
 * @model
 * @generated
 */
public interface SubSystem extends ComposedProvidingRequiringEntity, RepositoryComponent {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

} // SubSystem
