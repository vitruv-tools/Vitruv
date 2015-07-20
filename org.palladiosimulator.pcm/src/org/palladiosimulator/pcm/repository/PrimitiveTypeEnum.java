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
 * <em><b>Primitive Type Enum</b></em>', and utility methods for working with them. <!--
 * end-user-doc --> <!-- begin-model-doc --> Primitive types for usage in datatype and interface
 * definitions <!-- end-model-doc -->
 *
 * @see org.palladiosimulator.pcm.repository.RepositoryPackage#getPrimitiveTypeEnum()
 * @model
 * @generated
 */
public enum PrimitiveTypeEnum implements Enumerator {
    /**
     * The '<em><b>INT</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #INT_VALUE
     * @generated
     * @ordered
     */
    INT(0, "INT", "INT"),

    /**
     * The '<em><b>STRING</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #STRING_VALUE
     * @generated
     * @ordered
     */
    STRING(1, "STRING", "STRING"),

    /**
     * The '<em><b>BOOL</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #BOOL_VALUE
     * @generated
     * @ordered
     */
    BOOL(2, "BOOL", "BOOL"),

    /**
     * The '<em><b>DOUBLE</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #DOUBLE_VALUE
     * @generated
     * @ordered
     */
    DOUBLE(3, "DOUBLE", "DOUBLE"),

    /**
     * The '<em><b>CHAR</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #CHAR_VALUE
     * @generated
     * @ordered
     */
    CHAR(4, "CHAR", "CHAR"),

    /**
     * The '<em><b>BYTE</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #BYTE_VALUE
     * @generated
     * @ordered
     */
    BYTE(5, "BYTE", "BYTE"),

    /**
     * The '<em><b>LONG</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #LONG_VALUE
     * @generated
     * @ordered
     */
    LONG(6, "LONG", "LONG");

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * The '<em><b>INT</b></em>' literal value. <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>INT</b></em>' literal object isn't clear, there really should be
     * more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @see #INT
     * @model
     * @generated
     * @ordered
     */
    public static final int INT_VALUE = 0;

    /**
     * The '<em><b>STRING</b></em>' literal value. <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>STRING</b></em>' literal object isn't clear, there really should be
     * more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @see #STRING
     * @model
     * @generated
     * @ordered
     */
    public static final int STRING_VALUE = 1;

    /**
     * The '<em><b>BOOL</b></em>' literal value. <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>BOOL</b></em>' literal object isn't clear, there really should be
     * more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @see #BOOL
     * @model
     * @generated
     * @ordered
     */
    public static final int BOOL_VALUE = 2;

    /**
     * The '<em><b>DOUBLE</b></em>' literal value. <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>DOUBLE</b></em>' literal object isn't clear, there really should be
     * more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @see #DOUBLE
     * @model
     * @generated
     * @ordered
     */
    public static final int DOUBLE_VALUE = 3;

    /**
     * The '<em><b>CHAR</b></em>' literal value. <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>CHAR</b></em>' literal object isn't clear, there really should be
     * more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @see #CHAR
     * @model
     * @generated
     * @ordered
     */
    public static final int CHAR_VALUE = 4;

    /**
     * The '<em><b>BYTE</b></em>' literal value. <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>BYTE</b></em>' literal object isn't clear, there really should be
     * more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @see #BYTE
     * @model
     * @generated
     * @ordered
     */
    public static final int BYTE_VALUE = 5;

    /**
     * The '<em><b>LONG</b></em>' literal value. <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>LONG</b></em>' literal object isn't clear, there really should be
     * more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @see #LONG
     * @model
     * @generated
     * @ordered
     */
    public static final int LONG_VALUE = 6;

    /**
     * An array of all the '<em><b>Primitive Type Enum</b></em>' enumerators. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private static final PrimitiveTypeEnum[] VALUES_ARRAY = new PrimitiveTypeEnum[] { INT, STRING, BOOL, DOUBLE, CHAR,
            BYTE, LONG, };

    /**
     * A public read-only list of all the '<em><b>Primitive Type Enum</b></em>' enumerators. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final List<PrimitiveTypeEnum> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Primitive Type Enum</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param literal
     *            the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static PrimitiveTypeEnum get(final String literal) {
        for (final PrimitiveTypeEnum result : VALUES_ARRAY) {
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Primitive Type Enum</b></em>' literal with the specified name. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param name
     *            the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static PrimitiveTypeEnum getByName(final String name) {
        for (final PrimitiveTypeEnum result : VALUES_ARRAY) {
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Primitive Type Enum</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static PrimitiveTypeEnum get(final int value) {
        switch (value) {
        case INT_VALUE:
            return INT;
        case STRING_VALUE:
            return STRING;
        case BOOL_VALUE:
            return BOOL;
        case DOUBLE_VALUE:
            return DOUBLE;
        case CHAR_VALUE:
            return CHAR;
        case BYTE_VALUE:
            return BYTE;
        case LONG_VALUE:
            return LONG;
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
    private PrimitiveTypeEnum(final int value, final String name, final String literal) {
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

} // PrimitiveTypeEnum
