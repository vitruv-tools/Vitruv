/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Abstract Branch Transition</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> Two types of branch transitions exist which correspond to the two types
 * of branches. The types cannot be mixed. Either all branch transitions of one BranchAction are
 * probabilistic or guarded. <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.AbstractBranchTransition#getBranchAction_AbstractBranchTransition
 * <em>Branch Action Abstract Branch Transition</em>}</li>
 * <li>
 * {@link org.palladiosimulator.pcm.seff.AbstractBranchTransition#getBranchBehaviour_BranchTransition
 * <em>Branch Behaviour Branch Transition</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.pcm.seff.SeffPackage#getAbstractBranchTransition()
 * @model abstract="true"
 * @generated
 */
public interface AbstractBranchTransition extends Entity {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Returns the value of the '<em><b>Branch Action Abstract Branch Transition</b></em>' container
     * reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.seff.BranchAction#getBranches_Branch
     * <em>Branches Branch</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Branch Action Abstract Branch Transition</em>' container reference
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Branch Action Abstract Branch Transition</em>' container
     *         reference.
     * @see #setBranchAction_AbstractBranchTransition(BranchAction)
     * @see org.palladiosimulator.pcm.seff.SeffPackage#getAbstractBranchTransition_BranchAction_AbstractBranchTransition()
     * @see org.palladiosimulator.pcm.seff.BranchAction#getBranches_Branch
     * @model opposite="branches_Branch" required="true" transient="false" ordered="false"
     * @generated
     */
    BranchAction getBranchAction_AbstractBranchTransition();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.AbstractBranchTransition#getBranchAction_AbstractBranchTransition
     * <em>Branch Action Abstract Branch Transition</em>}' container reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Branch Action Abstract Branch Transition</em>' container
     *            reference.
     * @see #getBranchAction_AbstractBranchTransition()
     * @generated
     */
    void setBranchAction_AbstractBranchTransition(BranchAction value);

    /**
     * Returns the value of the '<em><b>Branch Behaviour Branch Transition</b></em>' containment
     * reference. It is bidirectional and its opposite is '
     * {@link org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour#getAbstractBranchTransition_ResourceDemandingBehaviour
     * <em>Abstract Branch Transition Resource Demanding Behaviour</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Branch Behaviour Branch Transition</em>' containment reference
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Branch Behaviour Branch Transition</em>' containment reference.
     * @see #setBranchBehaviour_BranchTransition(ResourceDemandingBehaviour)
     * @see org.palladiosimulator.pcm.seff.SeffPackage#getAbstractBranchTransition_BranchBehaviour_BranchTransition()
     * @see org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour#getAbstractBranchTransition_ResourceDemandingBehaviour
     * @model opposite="abstractBranchTransition_ResourceDemandingBehaviour" containment="true"
     *        required="true" ordered="false"
     * @generated
     */
    ResourceDemandingBehaviour getBranchBehaviour_BranchTransition();

    /**
     * Sets the value of the '
     * {@link org.palladiosimulator.pcm.seff.AbstractBranchTransition#getBranchBehaviour_BranchTransition
     * <em>Branch Behaviour Branch Transition</em>}' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Branch Behaviour Branch Transition</em>' containment
     *            reference.
     * @see #getBranchBehaviour_BranchTransition()
     * @generated
     */
    void setBranchBehaviour_BranchTransition(ResourceDemandingBehaviour value);

} // AbstractBranchTransition
