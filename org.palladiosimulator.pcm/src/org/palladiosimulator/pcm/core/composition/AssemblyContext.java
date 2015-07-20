/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.core.composition;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.RepositoryComponent;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Assembly Context</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> An AssemblyContext uniquely identifies an assembly instance of an
 * AssemblyContext. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.AssemblyContext#getParentStructure__AssemblyContext
 * <em>Parent Structure Assembly Context</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.AssemblyContext#getEncapsulatedComponent__AssemblyContext
 * <em>Encapsulated Component Assembly Context</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.core.composition.AssemblyContext#getConfigParameterUsages__AssemblyContext
 * <em>Config Parameter Usages Assembly Context</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getAssemblyContext()
 * @model
 * @generated
 */
public interface AssemblyContext extends Entity {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Parent Structure Assembly Context</b></em>' container
     * reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.core.composition.ComposedStructure#getAssemblyContexts__ComposedStructure
     * <em>Assembly Contexts Composed Structure</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parent Structure Assembly Context</em>' container reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Parent Structure Assembly Context</em>' container reference.
     * @see #setParentStructure__AssemblyContext(ComposedStructure)
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getAssemblyContext_ParentStructure__AssemblyContext()
     * @see org.palladiosimulator.pcm.core.composition.ComposedStructure#getAssemblyContexts__ComposedStructure
     * @model opposite="assemblyContexts__ComposedStructure" required="true" transient="false"
     *        ordered="false"
     * @generated
     */
    ComposedStructure getParentStructure__AssemblyContext();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyContext#getParentStructure__AssemblyContext
     * <em>Parent Structure Assembly Context</em>}' container reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Parent Structure Assembly Context</em>' container
     *            reference.
     * @see #getParentStructure__AssemblyContext()
     * @generated
     */
    void setParentStructure__AssemblyContext(ComposedStructure value);

    /**
     * Returns the value of the '<em><b>Encapsulated Component Assembly Context</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Encapsulated Component Assembly Context</em>' reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Encapsulated Component Assembly Context</em>' reference.
     * @see #setEncapsulatedComponent__AssemblyContext(RepositoryComponent)
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getAssemblyContext_EncapsulatedComponent__AssemblyContext()
     * @model required="true" ordered="false"
     * @generated
     */
    RepositoryComponent getEncapsulatedComponent__AssemblyContext();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyContext#getEncapsulatedComponent__AssemblyContext
     * <em>Encapsulated Component Assembly Context</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Encapsulated Component Assembly Context</em>' reference.
     * @see #getEncapsulatedComponent__AssemblyContext()
     * @generated
     */
    void setEncapsulatedComponent__AssemblyContext(RepositoryComponent value);

    /**
     * Returns the value of the '<em><b>Config Parameter Usages Assembly Context</b></em>'
     * containment reference list. The list contents are of type
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage}. It is bidirectional and its
     * opposite is '
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage#getAssemblyContext__VariableUsage
     * <em>Assembly Context Variable Usage</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Config Parameter Usages Assembly Context</em>' containment
     * reference list isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Config Parameter Usages Assembly Context</em>' containment
     *         reference list.
     * @see org.palladiosimulator.pcm.core.composition.CompositionPackage#getAssemblyContext_ConfigParameterUsages__AssemblyContext()
     * @see org.palladiosimulator.pcm.parameter.VariableUsage#getAssemblyContext__VariableUsage
     * @model opposite="assemblyContext__VariableUsage" containment="true" ordered="false"
     * @generated
     */
    EList<VariableUsage> getConfigParameterUsages__AssemblyContext();

} // AssemblyContext
