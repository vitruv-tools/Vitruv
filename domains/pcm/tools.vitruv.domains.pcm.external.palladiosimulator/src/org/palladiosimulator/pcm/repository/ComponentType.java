/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '
 * <em><b>Component Type</b></em>', and utility methods for working with them. <!-- end-user-doc -->
 * 
 * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getComponentType()
 * @model
 * @generated
 */
public enum ComponentType implements Enumerator {
    /**
     * The '<em><b>BUSINESS COMPONENT</b></em>' literal object. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #BUSINESS_COMPONENT_VALUE
     * @generated
     * @ordered
     */
    BUSINESS_COMPONENT(0, "BUSINESS_COMPONENT", "BUSINESS_COMPONENT"),

    /**
     * The '<em><b>INFRASTRUCTURE COMPONENT</b></em>' literal object. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #INFRASTRUCTURE_COMPONENT_VALUE
     * @generated
     * @ordered
     */
    INFRASTRUCTURE_COMPONENT(1, "INFRASTRUCTURE_COMPONENT", "INFRASTRUCTURE_COMPONENT");

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * The '<em><b>BUSINESS COMPONENT</b></em>' literal value. <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>BUSINESS COMPONENT</b></em>' literal object isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @see #BUSINESS_COMPONENT
     * @model
     * @generated
     * @ordered
     */
    public static final int BUSINESS_COMPONENT_VALUE = 0;

    /**
     * The '<em><b>INFRASTRUCTURE COMPONENT</b></em>' literal value. <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>INFRASTRUCTURE COMPONENT</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @see #INFRASTRUCTURE_COMPONENT
     * @model
     * @generated
     * @ordered
     */
    public static final int INFRASTRUCTURE_COMPONENT_VALUE = 1;

    /**
     * An array of all the '<em><b>Component Type</b></em>' enumerators. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private static final ComponentType[] VALUES_ARRAY = new ComponentType[] { BUSINESS_COMPONENT,
            INFRASTRUCTURE_COMPONENT, };

    /**
     * A public read-only list of all the '<em><b>Component Type</b></em>' enumerators. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final List<ComponentType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Component Type</b></em>' literal with the specified literal value. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param literal
     *            the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static ComponentType get(final String literal) {
        for (final ComponentType result : VALUES_ARRAY) {
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Component Type</b></em>' literal with the specified name. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param name
     *            the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static ComponentType getByName(final String name) {
        for (final ComponentType result : VALUES_ARRAY) {
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Component Type</b></em>' literal with the specified integer value. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static ComponentType get(final int value) {
        switch (value) {
        case BUSINESS_COMPONENT_VALUE:
            return BUSINESS_COMPONENT;
        case INFRASTRUCTURE_COMPONENT_VALUE:
            return INFRASTRUCTURE_COMPONENT;
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private final int value;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private final String name;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private final String literal;

    /**
     * Only this class can construct instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private ComponentType(final int value, final String name, final String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public int getValue() {
        return this.value;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String getLiteral() {
        return this.literal;
    }

    /**
     * Returns the literal value of the enumerator, which is its string representation. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String toString() {
        return this.literal;
    }

} // ComponentType
