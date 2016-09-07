/**
 */
package tools.vitruvius.domains.jml.language.jML;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Compilation Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.CompilationUnit#getPackagedeclaration <em>Packagedeclaration</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.CompilationUnit#getImportdeclaration <em>Importdeclaration</em>}</li>
 *   <li>{@link tools.vitruvius.domains.jml.language.jML.CompilationUnit#getTypedeclaration <em>Typedeclaration</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getCompilationUnit()
 * @model
 * @generated
 */
public interface CompilationUnit extends EObject
{
  /**
   * Returns the value of the '<em><b>Packagedeclaration</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Packagedeclaration</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Packagedeclaration</em>' containment reference.
   * @see #setPackagedeclaration(PackageDeclaration)
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getCompilationUnit_Packagedeclaration()
   * @model containment="true"
   * @generated
   */
  PackageDeclaration getPackagedeclaration();

  /**
   * Sets the value of the '{@link tools.vitruvius.domains.jml.language.jML.CompilationUnit#getPackagedeclaration <em>Packagedeclaration</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Packagedeclaration</em>' containment reference.
   * @see #getPackagedeclaration()
   * @generated
   */
  void setPackagedeclaration(PackageDeclaration value);

  /**
   * Returns the value of the '<em><b>Importdeclaration</b></em>' containment reference list.
   * The list contents are of type {@link tools.vitruvius.domains.jml.language.jML.ImportDeclaration}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Importdeclaration</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Importdeclaration</em>' containment reference list.
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getCompilationUnit_Importdeclaration()
   * @model containment="true"
   * @generated
   */
  EList<ImportDeclaration> getImportdeclaration();

  /**
   * Returns the value of the '<em><b>Typedeclaration</b></em>' containment reference list.
   * The list contents are of type {@link tools.vitruvius.domains.jml.language.jML.ClassifierDeclarationWithModifier}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Typedeclaration</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Typedeclaration</em>' containment reference list.
   * @see tools.vitruvius.domains.jml.language.jML.JMLPackage#getCompilationUnit_Typedeclaration()
   * @model containment="true"
   * @generated
   */
  EList<ClassifierDeclarationWithModifier> getTypedeclaration();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model kind="operation"
   *        annotation="http://www.eclipse.org/emf/2002/GenModel body='if (getTypedeclaration().size() > 0) {\r\n\t<%org.eclipse.emf.ecore.EStructuralFeature%> feature = getTypedeclaration().get(0).getClassOrInterfaceDeclaration().eClass().getEStructuralFeature(\"identifier\");\r\n\tString typeName = (String)getTypedeclaration().get(0).getClassOrInterfaceDeclaration().eGet(feature);\r\n\tString pkgName = \"\";\r\n\tif (getPackagedeclaration() != null) {\r\n\t\tpkgName = getPackagedeclaration().getQualifiedname() + \".\";\r\n\t}\r\n\treturn pkgName + typeName;\r\n}\r\nthrow new <%java.lang.IllegalStateException%>(\"There has to be at least one type in the compilation unit in order to construct its name.\");'"
   * @generated
   */
  String getName();

} // CompilationUnit
