/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.parameter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '
 * <em><b>Variable Characterisation Type</b></em>', and utility methods for working with them. <!--
 * end-user-doc --> <!-- begin-model-doc --> The variable characterisation types determine the set
 * of available meta-informations on variables. Possible values are STRUCTURE, NUMBER_OF_ELEMENTS,
 * VALUE, BYTESIZE, and TYPE. <!-- end-model-doc -->
 *
 * @see org.palladiosimulator.pcm.parameter.ParameterPackage#getVariableCharacterisationType()
 * @model
 * @generated
 */
public enum VariableCharacterisationType implements Enumerator {
    /**
     * The '<em><b>STRUCTURE</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #STRUCTURE_VALUE
     * @generated
     * @ordered
     */
    STRUCTURE(0, "STRUCTURE", "STRUCTURE"),

    /**
     * The '<em><b>NUMBER OF ELEMENTS</b></em>' literal object. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #NUMBER_OF_ELEMENTS_VALUE
     * @generated
     * @ordered
     */
    NUMBER_OF_ELEMENTS(1, "NUMBER_OF_ELEMENTS", "NUMBER_OF_ELEMENTS"),

    /**
     * The '<em><b>VALUE</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #VALUE_VALUE
     * @generated
     * @ordered
     */
    VALUE(2, "VALUE", "VALUE"),

    /**
     * The '<em><b>BYTESIZE</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #BYTESIZE_VALUE
     * @generated
     * @ordered
     */
    BYTESIZE(3, "BYTESIZE", "BYTESIZE"),

    /**
     * The '<em><b>TYPE</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #TYPE_VALUE
     * @generated
     * @ordered
     */
    TYPE(4, "TYPE", "TYPE");

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * The '<em><b>STRUCTURE</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     * <!-- begin-model-doc --> The structure characterisation type is used to describe the
     * structure of the data. With structure we refer to performance relevant properties of data
     * like for example whether an array is sorted, a tree is balanced, a collection is indexed, and
     * so on.... The allowed set of value of the structure characterisation has to be specified by
     * the component developer for a particular component. <!-- end-model-doc -->
     * 
     * @see #STRUCTURE
     * @model
     * @generated
     * @ordered
     */
    public static final int STRUCTURE_VALUE = 0;

    /**
     * The '<em><b>NUMBER OF ELEMENTS</b></em>' literal value. <!-- begin-user-doc --> <!--
     * end-user-doc --> <!-- begin-model-doc --> The number of elements of a collection data type
     * describe the amount of data objects contained in the collection. <!-- end-model-doc -->
     *
     * @see #NUMBER_OF_ELEMENTS
     * @model
     * @generated
     * @ordered
     */
    public static final int NUMBER_OF_ELEMENTS_VALUE = 1;

    /**
     * The '<em><b>VALUE</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
     * begin-model-doc --> The value characterisation can be used to specify the actual value of a
     * parameter. It is only available for parameters having a primitive type. The value
     * characterisation should be used with care as it increased the complexity of the resulting
     * analysis model significantly. It should be only used in performance-critical cases. <!--
     * end-model-doc -->
     *
     * @see #VALUE
     * @model
     * @generated
     * @ordered
     */
    public static final int VALUE_VALUE = 2;

    /**
     * The '<em><b>BYTESIZE</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     * <!-- begin-model-doc --> The bytesize characterisation specifies the memory footprint of a
     * data object. <!-- end-model-doc -->
     * 
     * @see #BYTESIZE
     * @model
     * @generated
     * @ordered
     */
    public static final int BYTESIZE_VALUE = 3;

    /**
     * The '<em><b>TYPE</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
     * begin-model-doc --> The type characterisation specifies the actual data type of polymorphic
     * data objects in cases where the actual type has an impact on performance. For example in a
     * shape drawing application the actual shape of a geometric object has an impact on the
     * performance as drawing a circle is much more time consuming than drawing a simple line. <!--
     * end-model-doc -->
     *
     * @see #TYPE
     * @model
     * @generated
     * @ordered
     */
    public static final int TYPE_VALUE = 4;

    /**
     * An array of all the '<em><b>Variable Characterisation Type</b></em>' enumerators. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static final VariableCharacterisationType[] VALUES_ARRAY = new VariableCharacterisationType[] { STRUCTURE,
            NUMBER_OF_ELEMENTS, VALUE, BYTESIZE, TYPE, };

    /**
     * A public read-only list of all the '<em><b>Variable Characterisation Type</b></em>'
     * enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final List<VariableCharacterisationType> VALUES = Collections
            .unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Variable Characterisation Type</b></em>' literal with the specified
     * literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param literal
     *            the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static VariableCharacterisationType get(final String literal) {
        for (final VariableCharacterisationType result : VALUES_ARRAY) {
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Variable Characterisation Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param name
     *            the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static VariableCharacterisationType getByName(final String name) {
        for (final VariableCharacterisationType result : VALUES_ARRAY) {
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Variable Characterisation Type</b></em>' literal with the specified
     * integer value. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static VariableCharacterisationType get(final int value) {
        switch (value) {
        case STRUCTURE_VALUE:
            return STRUCTURE;
        case NUMBER_OF_ELEMENTS_VALUE:
            return NUMBER_OF_ELEMENTS;
        case VALUE_VALUE:
            return VALUE;
        case BYTESIZE_VALUE:
            return BYTESIZE;
        case TYPE_VALUE:
            return TYPE;
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
    private VariableCharacterisationType(final int value, final String name, final String literal) {
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

} // VariableCharacterisationType
