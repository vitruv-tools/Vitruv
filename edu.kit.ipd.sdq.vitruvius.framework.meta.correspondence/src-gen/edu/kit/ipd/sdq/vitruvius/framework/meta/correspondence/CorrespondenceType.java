/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getCorrespondenceType()
 * @model
 * @generated
 */
public enum CorrespondenceType implements Enumerator {
	/**
	 * The '<em><b>Identity</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #IDENTITY_VALUE
	 * @generated
	 * @ordered
	 */
	IDENTITY(0, "identity", "identity"),

	/**
	 * The '<em><b>Bijection</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BIJECTION_VALUE
	 * @generated
	 * @ordered
	 */
	BIJECTION(1, "bijection", "bijection"),

	/**
	 * The '<em><b>Unidirectional</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNIDIRECTIONAL_VALUE
	 * @generated
	 * @ordered
	 */
	UNIDIRECTIONAL(2, "unidirectional", "unidirectional"),

	/**
	 * The '<em><b>Constrained</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONSTRAINED_VALUE
	 * @generated
	 * @ordered
	 */
	CONSTRAINED(3, "constrained", "constrained"),

	/**
	 * The '<em><b>Unknown</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNKNOWN_VALUE
	 * @generated
	 * @ordered
	 */
	UNKNOWN(4, "unknown", "unknown");

	/**
	 * The '<em><b>Identity</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Identity</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #IDENTITY
	 * @model name="identity"
	 * @generated
	 * @ordered
	 */
	public static final int IDENTITY_VALUE = 0;

	/**
	 * The '<em><b>Bijection</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Bijection</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BIJECTION
	 * @model name="bijection"
	 * @generated
	 * @ordered
	 */
	public static final int BIJECTION_VALUE = 1;

	/**
	 * The '<em><b>Unidirectional</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Unidirectional</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #UNIDIRECTIONAL
	 * @model name="unidirectional"
	 * @generated
	 * @ordered
	 */
	public static final int UNIDIRECTIONAL_VALUE = 2;

	/**
	 * The '<em><b>Constrained</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Constrained</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CONSTRAINED
	 * @model name="constrained"
	 * @generated
	 * @ordered
	 */
	public static final int CONSTRAINED_VALUE = 3;

	/**
	 * The '<em><b>Unknown</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Unknown</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #UNKNOWN
	 * @model name="unknown"
	 * @generated
	 * @ordered
	 */
	public static final int UNKNOWN_VALUE = 4;

	/**
	 * An array of all the '<em><b>Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final CorrespondenceType[] VALUES_ARRAY =
		new CorrespondenceType[] {
			IDENTITY,
			BIJECTION,
			UNIDIRECTIONAL,
			CONSTRAINED,
			UNKNOWN,
		};

	/**
	 * A public read-only list of all the '<em><b>Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<CorrespondenceType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static CorrespondenceType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			CorrespondenceType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static CorrespondenceType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			CorrespondenceType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static CorrespondenceType get(int value) {
		switch (value) {
			case IDENTITY_VALUE: return IDENTITY;
			case BIJECTION_VALUE: return BIJECTION;
			case UNIDIRECTIONAL_VALUE: return UNIDIRECTIONAL;
			case CONSTRAINED_VALUE: return CONSTRAINED;
			case UNKNOWN_VALUE: return UNKNOWN;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private CorrespondenceType(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //CorrespondenceType
