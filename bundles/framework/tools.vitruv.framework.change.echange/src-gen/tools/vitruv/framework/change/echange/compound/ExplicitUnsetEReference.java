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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%tools.vitruv.framework.change.echange.EChange%&gt;, &lt;%java.util.List%&gt;&lt;&lt;%tools.vitruv.framework.change.echange.AtomicEChange%&gt;&gt;&gt; _function = new &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%tools.vitruv.framework.change.echange.EChange%&gt;, &lt;%java.util.List%&gt;&lt;&lt;%tools.vitruv.framework.change.echange.AtomicEChange%&gt;&gt;&gt;()\n{\n\tpublic &lt;%java.util.List%&gt;&lt;&lt;%tools.vitruv.framework.change.echange.AtomicEChange%&gt;&gt; apply(final &lt;%tools.vitruv.framework.change.echange.EChange%&gt; it)\n\t{\n\t\t&lt;%java.util.List%&gt;&lt;&lt;%tools.vitruv.framework.change.echange.AtomicEChange%&gt;&gt; _xifexpression = null;\n\t\tif ((it instanceof &lt;%tools.vitruv.framework.change.echange.AtomicEChange%&gt;))\n\t\t{\n\t\t\t_xifexpression = java.util.Collections.&lt;&lt;%tools.vitruv.framework.change.echange.AtomicEChange%&gt;&gt;unmodifiableList(org.eclipse.xtext.xbase.lib.CollectionLiterals.&lt;&lt;%tools.vitruv.framework.change.echange.AtomicEChange%&gt;&gt;newArrayList(((&lt;%tools.vitruv.framework.change.echange.AtomicEChange%&gt;)it)));\n\t\t}\n\t\telse\n\t\t{\n\t\t\t&lt;%org.eclipse.emf.common.util.EList%&gt;&lt;&lt;%tools.vitruv.framework.change.echange.AtomicEChange%&gt;&gt; _xifexpression_1 = null;\n\t\t\tif ((it instanceof &lt;%tools.vitruv.framework.change.echange.compound.CompoundEChange%&gt;))\n\t\t\t{\n\t\t\t\t_xifexpression_1 = ((&lt;%tools.vitruv.framework.change.echange.compound.CompoundEChange%&gt;)it).getAtomicChanges();\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\tthrow new &lt;%java.lang.IllegalArgumentException%&gt;();\n\t\t\t}\n\t\t\t_xifexpression = _xifexpression_1;\n\t\t}\n\t\treturn _xifexpression;\n\t}\n};\nreturn &lt;%org.eclipse.emf.common.util.ECollections%&gt;.&lt;&lt;%tools.vitruv.framework.change.echange.AtomicEChange%&gt;&gt;asEList(((&lt;%tools.vitruv.framework.change.echange.AtomicEChange%&gt;[])org.eclipse.xtext.xbase.lib.Conversions.unwrapArray(&lt;%com.google.common.collect.Iterables%&gt;.&lt;&lt;%tools.vitruv.framework.change.echange.AtomicEChange%&gt;&gt;concat(&lt;%org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions%&gt;.&lt;&lt;%tools.vitruv.framework.change.echange.EChange%&gt;, &lt;%java.util.List%&gt;&lt;&lt;%tools.vitruv.framework.change.echange.AtomicEChange%&gt;&gt;&gt;map(this.getChanges(), _function)), &lt;%tools.vitruv.framework.change.echange.AtomicEChange%&gt;.class)));'"
	 * @generated
	 */
	EList<AtomicEChange> getAtomicChanges();

} // ExplicitUnsetEReference
