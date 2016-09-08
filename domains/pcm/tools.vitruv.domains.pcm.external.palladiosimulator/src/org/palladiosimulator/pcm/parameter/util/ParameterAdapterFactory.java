/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.parameter.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.parameter.CharacterisedVariable;
import org.palladiosimulator.pcm.parameter.ParameterPackage;
import org.palladiosimulator.pcm.parameter.VariableCharacterisation;
import org.palladiosimulator.pcm.parameter.VariableUsage;

import de.uka.ipd.sdq.stoex.Atom;
import de.uka.ipd.sdq.stoex.BooleanExpression;
import de.uka.ipd.sdq.stoex.Comparison;
import de.uka.ipd.sdq.stoex.Expression;
import de.uka.ipd.sdq.stoex.IfElse;
import de.uka.ipd.sdq.stoex.Power;
import de.uka.ipd.sdq.stoex.Product;
import de.uka.ipd.sdq.stoex.Term;
import de.uka.ipd.sdq.stoex.Unary;
import de.uka.ipd.sdq.stoex.Variable;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter
 * <code>createXXX</code> method for each class of the model. <!-- end-user-doc -->
 * 
 * @see org.palladiosimulator.pcm.parameter.ParameterPackage
 * @generated
 */
public class ParameterAdapterFactory extends AdapterFactoryImpl {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected static ParameterPackage modelPackage;

    /**
     * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public ParameterAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = ParameterPackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object. <!-- begin-user-doc
     * --> This implementation returns <code>true</code> if the object is either the model's package
     * or is an instance object of the model. <!-- end-user-doc -->
     * 
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType(final Object object) {
        if (object == modelPackage) {
            return true;
        }
        if (object instanceof EObject) {
            return ((EObject) object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * The switch that delegates to the <code>createXXX</code> methods. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    protected ParameterSwitch<Adapter> modelSwitch = new ParameterSwitch<Adapter>() {
        @Override
        public Adapter caseVariableUsage(final VariableUsage object) {
            return ParameterAdapterFactory.this.createVariableUsageAdapter();
        }

        @Override
        public Adapter caseVariableCharacterisation(final VariableCharacterisation object) {
            return ParameterAdapterFactory.this.createVariableCharacterisationAdapter();
        }

        @Override
        public Adapter caseCharacterisedVariable(final CharacterisedVariable object) {
            return ParameterAdapterFactory.this.createCharacterisedVariableAdapter();
        }

        @Override
        public Adapter caseExpression(final Expression object) {
            return ParameterAdapterFactory.this.createExpressionAdapter();
        }

        @Override
        public Adapter caseIfElse(final IfElse object) {
            return ParameterAdapterFactory.this.createIfElseAdapter();
        }

        @Override
        public Adapter caseBooleanExpression(final BooleanExpression object) {
            return ParameterAdapterFactory.this.createBooleanExpressionAdapter();
        }

        @Override
        public Adapter caseComparison(final Comparison object) {
            return ParameterAdapterFactory.this.createComparisonAdapter();
        }

        @Override
        public Adapter caseTerm(final Term object) {
            return ParameterAdapterFactory.this.createTermAdapter();
        }

        @Override
        public Adapter caseProduct(final Product object) {
            return ParameterAdapterFactory.this.createProductAdapter();
        }

        @Override
        public Adapter casePower(final Power object) {
            return ParameterAdapterFactory.this.createPowerAdapter();
        }

        @Override
        public Adapter caseUnary(final Unary object) {
            return ParameterAdapterFactory.this.createUnaryAdapter();
        }

        @Override
        public Adapter caseAtom(final Atom object) {
            return ParameterAdapterFactory.this.createAtomAdapter();
        }

        @Override
        public Adapter caseVariable(final Variable object) {
            return ParameterAdapterFactory.this.createVariableAdapter();
        }

        @Override
        public Adapter defaultCase(final EObject object) {
            return ParameterAdapterFactory.this.createEObjectAdapter();
        }
    };

    /**
     * Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param target
     *            the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(final Notifier target) {
        return this.modelSwitch.doSwitch((EObject) target);
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.parameter.VariableUsage <em>Variable Usage</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.parameter.VariableUsage
     * @generated
     */
    public Adapter createVariableUsageAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.parameter.VariableCharacterisation
     * <em>Variable Characterisation</em>}'. <!-- begin-user-doc --> This default implementation
     * returns null so that we can easily ignore cases; it's useful to ignore a case when
     * inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.parameter.VariableCharacterisation
     * @generated
     */
    public Adapter createVariableCharacterisationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link org.palladiosimulator.pcm.parameter.CharacterisedVariable
     * <em>Characterised Variable</em>}'. <!-- begin-user-doc --> This default implementation
     * returns null so that we can easily ignore cases; it's useful to ignore a case when
     * inheritance will catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.palladiosimulator.pcm.parameter.CharacterisedVariable
     * @generated
     */
    public Adapter createCharacterisedVariableAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link de.uka.ipd.sdq.stoex.Expression
     * <em>Expression</em>}'. <!-- begin-user-doc --> This default implementation returns null so
     * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
     * the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see de.uka.ipd.sdq.stoex.Expression
     * @generated
     */
    public Adapter createExpressionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link de.uka.ipd.sdq.stoex.IfElse
     * <em>If Else</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
     * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the
     * cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see de.uka.ipd.sdq.stoex.IfElse
     * @generated
     */
    public Adapter createIfElseAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link de.uka.ipd.sdq.stoex.BooleanExpression
     * <em>Boolean Expression</em>}'. <!-- begin-user-doc --> This default implementation returns
     * null so that we can easily ignore cases; it's useful to ignore a case when inheritance will
     * catch all the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see de.uka.ipd.sdq.stoex.BooleanExpression
     * @generated
     */
    public Adapter createBooleanExpressionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link de.uka.ipd.sdq.stoex.Comparison
     * <em>Comparison</em>}'. <!-- begin-user-doc --> This default implementation returns null so
     * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
     * the cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see de.uka.ipd.sdq.stoex.Comparison
     * @generated
     */
    public Adapter createComparisonAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link de.uka.ipd.sdq.stoex.Term <em>Term</em>}
     * '. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see de.uka.ipd.sdq.stoex.Term
     * @generated
     */
    public Adapter createTermAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link de.uka.ipd.sdq.stoex.Product
     * <em>Product</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
     * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the
     * cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see de.uka.ipd.sdq.stoex.Product
     * @generated
     */
    public Adapter createProductAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link de.uka.ipd.sdq.stoex.Power
     * <em>Power</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
     * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the
     * cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see de.uka.ipd.sdq.stoex.Power
     * @generated
     */
    public Adapter createPowerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link de.uka.ipd.sdq.stoex.Unary
     * <em>Unary</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
     * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the
     * cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see de.uka.ipd.sdq.stoex.Unary
     * @generated
     */
    public Adapter createUnaryAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link de.uka.ipd.sdq.stoex.Atom <em>Atom</em>}
     * '. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see de.uka.ipd.sdq.stoex.Atom
     * @generated
     */
    public Adapter createAtomAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link de.uka.ipd.sdq.stoex.Variable
     * <em>Variable</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
     * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the
     * cases anyway. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see de.uka.ipd.sdq.stoex.Variable
     * @generated
     */
    public Adapter createVariableAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for the default case. <!-- begin-user-doc --> This default
     * implementation returns null. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} // ParameterAdapterFactory
