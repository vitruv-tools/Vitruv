/**
 */
package tools.vitruvius.domains.jml.language.jML;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Spec Member Modifier</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getJMLSpecMemberModifier()
 * @model
 * @generated
 */
public enum JMLSpecMemberModifier implements Enumerator
{
  /**
   * The '<em><b>PURE</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #PURE_VALUE
   * @generated
   * @ordered
   */
  PURE(0, "PURE", "pure"),

  /**
   * The '<em><b>HELPER</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #HELPER_VALUE
   * @generated
   * @ordered
   */
  HELPER(1, "HELPER", "helper"),

  /**
   * The '<em><b>PUBLIC</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #PUBLIC_VALUE
   * @generated
   * @ordered
   */
  PUBLIC(2, "PUBLIC", "spec_public"),

  /**
   * The '<em><b>PROTECTED</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #PROTECTED_VALUE
   * @generated
   * @ordered
   */
  PROTECTED(3, "PROTECTED", "spec_protected");

  /**
   * The '<em><b>PURE</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>PURE</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #PURE
   * @model literal="pure"
   * @generated
   * @ordered
   */
  public static final int PURE_VALUE = 0;

  /**
   * The '<em><b>HELPER</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>HELPER</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #HELPER
   * @model literal="helper"
   * @generated
   * @ordered
   */
  public static final int HELPER_VALUE = 1;

  /**
   * The '<em><b>PUBLIC</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>PUBLIC</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #PUBLIC
   * @model literal="spec_public"
   * @generated
   * @ordered
   */
  public static final int PUBLIC_VALUE = 2;

  /**
   * The '<em><b>PROTECTED</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>PROTECTED</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #PROTECTED
   * @model literal="spec_protected"
   * @generated
   * @ordered
   */
  public static final int PROTECTED_VALUE = 3;

  /**
   * An array of all the '<em><b>Spec Member Modifier</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final JMLSpecMemberModifier[] VALUES_ARRAY =
    new JMLSpecMemberModifier[]
    {
      PURE,
      HELPER,
      PUBLIC,
      PROTECTED,
    };

  /**
   * A public read-only list of all the '<em><b>Spec Member Modifier</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<JMLSpecMemberModifier> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Spec Member Modifier</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param literal the literal.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static JMLSpecMemberModifier get(String literal)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      JMLSpecMemberModifier result = VALUES_ARRAY[i];
      if (result.toString().equals(literal))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Spec Member Modifier</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param name the name.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static JMLSpecMemberModifier getByName(String name)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      JMLSpecMemberModifier result = VALUES_ARRAY[i];
      if (result.getName().equals(name))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Spec Member Modifier</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the integer value.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static JMLSpecMemberModifier get(int value)
  {
    switch (value)
    {
      case PURE_VALUE: return PURE;
      case HELPER_VALUE: return HELPER;
      case PUBLIC_VALUE: return PUBLIC;
      case PROTECTED_VALUE: return PROTECTED;
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
  private JMLSpecMemberModifier(int value, String name, String literal)
  {
    this.value = value;
    this.name = name;
    this.literal = literal;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getValue()
  {
    return value;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getLiteral()
  {
    return literal;
  }

  /**
   * Returns the literal value of the enumerator, which is its string representation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    return literal;
  }
  
} //JMLSpecMemberModifier
