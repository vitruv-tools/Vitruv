/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository;

import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Composite Component</b></em>
 * '. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> Composite components are special implementation component types, which
 * are composed from inner components. Component developers compose inner components within
 * composite components with assembly connectors. An assembly connector binds a provided role with a
 * required role. To access the inner components, composite components themselves provide or require
 * interfaces. A delegation connector binds a provided (required) role of the composite component
 * with an inner component provided (required) role. A composite component may contain other
 * composite components, which are also themselves composed out of inner components. This enables
 * building arbitrary hierarchies of nested components. Like a basic component, a composite
 * component may contain a SEFF. However, this SEFF is not specified manually by the composite
 * component developer, but can be computed by combining the SEFFs of the inner components. <!--
 * end-model-doc -->
 *
 *
 * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getCompositeComponent()
 * @model
 * @generated
 */
public interface CompositeComponent extends ComposedProvidingRequiringEntity, ImplementationComponentType {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

} // CompositeComponent
