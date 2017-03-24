/**
 */
package tools.vitruv.framework.change.echange.compound;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.AtomicEChange;

import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Explicit Unset EAttribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * EChange which unsets a single or many valued attribute.
 * <!-- end-model-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getExplicitUnsetEAttribute()
 * @model ABounds="tools.vitruv.framework.change.echange.compound.EObj" TBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface ExplicitUnsetEAttribute<A extends EObject, T extends Object> extends ExplicitUnsetEFeature<A, EAttribute>, CompoundSubtraction<T, SubtractiveAttributeEChange<A, T>> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.common.util.EList%><<%tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange%><A, T>> _subtractiveChanges = this.getSubtractiveChanges();\nfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange%><A, T>, <%tools.vitruv.framework.change.echange.AtomicEChange%>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange%><A, T>, <%tools.vitruv.framework.change.echange.AtomicEChange%>>()\n{\n\tpublic <%tools.vitruv.framework.change.echange.AtomicEChange%> apply(final <%tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange%><A, T> it)\n\t{\n\t\treturn it;\n\t}\n};\nreturn <%org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions%>.<<%tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange%><A, T>, <%tools.vitruv.framework.change.echange.AtomicEChange%>>map(_subtractiveChanges, _function);'"
	 * @generated
	 */
	EList<AtomicEChange> getAtomicChanges();

} // ExplicitUnsetEAttribute
