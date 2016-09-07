/**
 */
package tools.vitruvius.domains.jml.language.jML;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Modifier Value</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getModifierValue()
 * @model
 * @generated
 */
public enum ModifierValue implements Enumerator
{
  /**
   * The '<em><b>PUBLIC</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #PUBLIC_VALUE
   * @generated
   * @ordered
   */
  PUBLIC(0, "PUBLIC", "public"),

  /**
   * The '<em><b>PROTECTED</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #PROTECTED_VALUE
   * @generated
   * @ordered
   */
  PROTECTED(1, "PROTECTED", "protected"),

  /**
   * The '<em><b>PRIVATE</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #PRIVATE_VALUE
   * @generated
   * @ordered
   */
  PRIVATE(2, "PRIVATE", "private"),

  /**
   * The '<em><b>STATIC</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #STATIC_VALUE
   * @generated
   * @ordered
   */
  STATIC(3, "STATIC", "static"),

  /**
   * The '<em><b>ABSTRACT</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #ABSTRACT_VALUE
   * @generated
   * @ordered
   */
  ABSTRACT(4, "ABSTRACT", "abstract"),

  /**
   * The '<em><b>FINAL</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #FINAL_VALUE
   * @generated
   * @ordered
   */
  FINAL(5, "FINAL", "final"),

  /**
   * The '<em><b>NATIVE</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #NATIVE_VALUE
   * @generated
   * @ordered
   */
  NATIVE(6, "NATIVE", "native"),

  /**
   * The '<em><b>SYNCHRONIZED</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #SYNCHRONIZED_VALUE
   * @generated
   * @ordered
   */
  SYNCHRONIZED(7, "SYNCHRONIZED", "synchronized"),

  /**
   * The '<em><b>TRANSIENT</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #TRANSIENT_VALUE
   * @generated
   * @ordered
   */
  TRANSIENT(8, "TRANSIENT", "transient"),

  /**
   * The '<em><b>VOLATILE</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #VOLATILE_VALUE
   * @generated
   * @ordered
   */
  VOLATILE(9, "VOLATILE", "volatile"),

  /**
   * The '<em><b>STRICTFP</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #STRICTFP_VALUE
   * @generated
   * @ordered
   */
  STRICTFP(10, "STRICTFP", "strictfp");

  /**
   * The '<em><b>PUBLIC</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>PUBLIC</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #PUBLIC
   * @model literal="public"
   * @generated
   * @ordered
   */
  public static final int PUBLIC_VALUE = 0;

  /**
   * The '<em><b>PROTECTED</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>PROTECTED</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #PROTECTED
   * @model literal="protected"
   * @generated
   * @ordered
   */
  public static final int PROTECTED_VALUE = 1;

  /**
   * The '<em><b>PRIVATE</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>PRIVATE</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #PRIVATE
   * @model literal="private"
   * @generated
   * @ordered
   */
  public static final int PRIVATE_VALUE = 2;

  /**
   * The '<em><b>STATIC</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>STATIC</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #STATIC
   * @model literal="static"
   * @generated
   * @ordered
   */
  public static final int STATIC_VALUE = 3;

  /**
   * The '<em><b>ABSTRACT</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>ABSTRACT</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #ABSTRACT
   * @model literal="abstract"
   * @generated
   * @ordered
   */
  public static final int ABSTRACT_VALUE = 4;

  /**
   * The '<em><b>FINAL</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>FINAL</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #FINAL
   * @model literal="final"
   * @generated
   * @ordered
   */
  public static final int FINAL_VALUE = 5;

  /**
   * The '<em><b>NATIVE</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>NATIVE</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #NATIVE
   * @model literal="native"
   * @generated
   * @ordered
   */
  public static final int NATIVE_VALUE = 6;

  /**
   * The '<em><b>SYNCHRONIZED</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>SYNCHRONIZED</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #SYNCHRONIZED
   * @model literal="synchronized"
   * @generated
   * @ordered
   */
  public static final int SYNCHRONIZED_VALUE = 7;

  /**
   * The '<em><b>TRANSIENT</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>TRANSIENT</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #TRANSIENT
   * @model literal="transient"
   * @generated
   * @ordered
   */
  public static final int TRANSIENT_VALUE = 8;

  /**
   * The '<em><b>VOLATILE</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>VOLATILE</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #VOLATILE
   * @model literal="volatile"
   * @generated
   * @ordered
   */
  public static final int VOLATILE_VALUE = 9;

  /**
   * The '<em><b>STRICTFP</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>STRICTFP</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #STRICTFP
   * @model literal="strictfp"
   * @generated
   * @ordered
   */
  public static final int STRICTFP_VALUE = 10;

  /**
   * An array of all the '<em><b>Modifier Value</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final ModifierValue[] VALUES_ARRAY =
    new ModifierValue[]
    {
      PUBLIC,
      PROTECTED,
      PRIVATE,
      STATIC,
      ABSTRACT,
      FINAL,
      NATIVE,
      SYNCHRONIZED,
      TRANSIENT,
      VOLATILE,
      STRICTFP,
    };

  /**
   * A public read-only list of all the '<em><b>Modifier Value</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<ModifierValue> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Modifier Value</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param literal the literal.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static ModifierValue get(String literal)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      ModifierValue result = VALUES_ARRAY[i];
      if (result.toString().equals(literal))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Modifier Value</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param name the name.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static ModifierValue getByName(String name)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      ModifierValue result = VALUES_ARRAY[i];
      if (result.getName().equals(name))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Modifier Value</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the integer value.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static ModifierValue get(int value)
  {
    switch (value)
    {
      case PUBLIC_VALUE: return PUBLIC;
      case PROTECTED_VALUE: return PROTECTED;
      case PRIVATE_VALUE: return PRIVATE;
      case STATIC_VALUE: return STATIC;
      case ABSTRACT_VALUE: return ABSTRACT;
      case FINAL_VALUE: return FINAL;
      case NATIVE_VALUE: return NATIVE;
      case SYNCHRONIZED_VALUE: return SYNCHRONIZED;
      case TRANSIENT_VALUE: return TRANSIENT;
      case VOLATILE_VALUE: return VOLATILE;
      case STRICTFP_VALUE: return STRICTFP;
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
  private ModifierValue(int value, String name, String literal)
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
  
} //ModifierValue
