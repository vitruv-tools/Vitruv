/**
 */
package tools.vitruv.framework.change.echange.compound;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import tools.vitruv.framework.change.echange.AtomicEChange;
import tools.vitruv.framework.change.echange.EChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Explicit Unset EReference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * EChange which unsets a single or many valued reference.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.ExplicitUnsetEReference#getChanges <em>Changes</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getExplicitUnsetEReference()
 * @model ABounds="tools.vitruv.framework.change.echange.compound.EObj"
 * @generated
 */
public interface ExplicitUnsetEReference<A extends EObject> extends ExplicitUnsetEFeature<A, EReference> {
	/**
	 * Returns the value of the '<em><b>Changes</b></em>' containment reference list.
	 * The list contents are of type {@link tools.vitruv.framework.change.echange.EChange}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Changes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Changes</em>' containment reference list.
	 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getExplicitUnsetEReference_Changes()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<EChange> getChanges();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns the changes this unset consists of. Can also be compound changes, e.g., a RemoveAndDeleteNonRoot
	 * <!-- end-model-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.getChanges();'"
	 * @generated
	 */
	EList<EChange> getContainedChanges();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.common.util.EList%><<%tools.vitruv.framework.change.echange.EChange%>> _changes = this.getChanges();\nfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%tools.vitruv.framework.change.echange.EChange%>, <%java.util.List%><<%tools.vitruv.framework.change.echange.AtomicEChange%>>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%tools.vitruv.framework.change.echange.EChange%>, <%java.util.List%><<%tools.vitruv.framework.change.echange.AtomicEChange%>>>()\n{\n\tpublic <%java.util.List%><<%tools.vitruv.framework.change.echange.AtomicEChange%>> apply(final <%tools.vitruv.framework.change.echange.EChange%> it)\n\t{\n\t\t<%java.util.List%><<%tools.vitruv.framework.change.echange.AtomicEChange%>> _xifexpression = null;\n\t\tif ((it instanceof <%tools.vitruv.framework.change.echange.AtomicEChange%>))\n\t\t{\n\t\t\t_xifexpression = java.util.Collections.<<%tools.vitruv.framework.change.echange.AtomicEChange%>>unmodifiableList(org.eclipse.xtext.xbase.lib.CollectionLiterals.<<%tools.vitruv.framework.change.echange.AtomicEChange%>>newArrayList(((<%tools.vitruv.framework.change.echange.AtomicEChange%>)it)));\n\t\t}\n\t\telse\n\t\t{\n\t\t\t<%org.eclipse.emf.common.util.EList%><<%tools.vitruv.framework.change.echange.AtomicEChange%>> _xifexpression_1 = null;\n\t\t\tif ((it instanceof <%tools.vitruv.framework.change.echange.compound.CompoundEChange%>))\n\t\t\t{\n\t\t\t\t_xifexpression_1 = ((<%tools.vitruv.framework.change.echange.compound.CompoundEChange%>)it).getAtomicChanges();\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\tthrow new <%java.lang.IllegalArgumentException%>();\n\t\t\t}\n\t\t\t_xifexpression = _xifexpression_1;\n\t\t}\n\t\treturn _xifexpression;\n\t}\n};\n<%org.eclipse.emf.common.util.EList%><<%java.util.List%><<%tools.vitruv.framework.change.echange.AtomicEChange%>>> _map = <%org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions%>.<<%tools.vitruv.framework.change.echange.EChange%>, <%java.util.List%><<%tools.vitruv.framework.change.echange.AtomicEChange%>>>map(_changes, _function);\n<%java.lang.Iterable%><<%tools.vitruv.framework.change.echange.AtomicEChange%>> _flatten = <%com.google.common.collect.Iterables%>.<<%tools.vitruv.framework.change.echange.AtomicEChange%>>concat(_map);\nreturn <%org.eclipse.emf.common.util.ECollections%>.<<%tools.vitruv.framework.change.echange.AtomicEChange%>>asEList(((<%tools.vitruv.framework.change.echange.AtomicEChange%>[])org.eclipse.xtext.xbase.lib.Conversions.unwrapArray(_flatten, <%tools.vitruv.framework.change.echange.AtomicEChange%>.class)));'"
	 * @generated
	 */
	EList<AtomicEChange> getAtomicChanges();

} // ExplicitUnsetEReference
