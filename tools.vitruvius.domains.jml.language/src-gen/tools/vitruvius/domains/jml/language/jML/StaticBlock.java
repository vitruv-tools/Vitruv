/**
 */
package tools.vitruvius.domains.jml.language.jML;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Static Block</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.StaticBlock#isStatic <em>Static</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.StaticBlock#getBlock <em>Block</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getStaticBlock()
 * @model
 * @generated
 */
public interface StaticBlock extends ClassBodyDeclaration
{
  /**
   * Returns the value of the '<em><b>Static</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Static</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Static</em>' attribute.
   * @see #setStatic(boolean)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getStaticBlock_Static()
   * @model
   * @generated
   */
  boolean isStatic();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.StaticBlock#isStatic <em>Static</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Static</em>' attribute.
   * @see #isStatic()
   * @generated
   */
  void setStatic(boolean value);

  /**
   * Returns the value of the '<em><b>Block</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Block</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Block</em>' containment reference.
   * @see #setBlock(Block)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getStaticBlock_Block()
   * @model containment="true"
   * @generated
   */
  Block getBlock();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.StaticBlock#getBlock <em>Block</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Block</em>' containment reference.
   * @see #getBlock()
   * @generated
   */
  void setBlock(Block value);

} // StaticBlock
