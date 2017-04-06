/**
 */
package tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.InputTypesPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.InputTypesPackage
 * @generated
 */
public class InputTypesAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static InputTypesPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InputTypesAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = InputTypesPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InputTypesSwitch<Adapter> modelSwitch =
		new InputTypesSwitch<Adapter>() {
			@Override
			public Adapter caseString(tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.String object) {
				return createStringAdapter();
			}
			@Override
			public Adapter caseInteger(tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Integer object) {
				return createIntegerAdapter();
			}
			@Override
			public Adapter caseShort(tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Short object) {
				return createShortAdapter();
			}
			@Override
			public Adapter caseBoolean(tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Boolean object) {
				return createBooleanAdapter();
			}
			@Override
			public Adapter caseLong(tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Long object) {
				return createLongAdapter();
			}
			@Override
			public Adapter caseCharacter(tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Character object) {
				return createCharacterAdapter();
			}
			@Override
			public Adapter caseByte(tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Byte object) {
				return createByteAdapter();
			}
			@Override
			public Adapter caseFloat(tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Float object) {
				return createFloatAdapter();
			}
			@Override
			public Adapter caseDouble(tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Double object) {
				return createDoubleAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.String <em>String</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.String
	 * @generated
	 */
	public Adapter createStringAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Integer <em>Integer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Integer
	 * @generated
	 */
	public Adapter createIntegerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Short <em>Short</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Short
	 * @generated
	 */
	public Adapter createShortAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Boolean <em>Boolean</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Boolean
	 * @generated
	 */
	public Adapter createBooleanAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Long <em>Long</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Long
	 * @generated
	 */
	public Adapter createLongAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Character <em>Character</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Character
	 * @generated
	 */
	public Adapter createCharacterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Byte <em>Byte</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Byte
	 * @generated
	 */
	public Adapter createByteAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Float <em>Float</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Float
	 * @generated
	 */
	public Adapter createFloatAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Double <em>Double</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Double
	 * @generated
	 */
	public Adapter createDoubleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //InputTypesAdapterFactory
