/**
 */
package tools.vitruv.framework.change.echange.compound.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import tools.vitruv.framework.change.echange.compound.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CompoundFactoryImpl extends EFactoryImpl implements CompoundFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CompoundFactory init() {
		try {
			CompoundFactory theCompoundFactory = (CompoundFactory)EPackage.Registry.INSTANCE.getEFactory(CompoundPackage.eNS_URI);
			if (theCompoundFactory != null) {
				return theCompoundFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CompoundFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompoundFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case CompoundPackage.EXPLICIT_UNSET_EATTRIBUTE: return createExplicitUnsetEAttribute();
			case CompoundPackage.EXPLICIT_UNSET_EREFERENCE: return createExplicitUnsetEReference();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <A extends EObject, T extends Object> ExplicitUnsetEAttribute<A, T> createExplicitUnsetEAttribute() {
		ExplicitUnsetEAttributeImpl<A, T> explicitUnsetEAttribute = new ExplicitUnsetEAttributeImpl<A, T>();
		return explicitUnsetEAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <A extends EObject> ExplicitUnsetEReference<A> createExplicitUnsetEReference() {
		ExplicitUnsetEReferenceImpl<A> explicitUnsetEReference = new ExplicitUnsetEReferenceImpl<A>();
		return explicitUnsetEReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompoundPackage getCompoundPackage() {
		return (CompoundPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CompoundPackage getPackage() {
		return CompoundPackage.eINSTANCE;
	}

} //CompoundFactoryImpl
