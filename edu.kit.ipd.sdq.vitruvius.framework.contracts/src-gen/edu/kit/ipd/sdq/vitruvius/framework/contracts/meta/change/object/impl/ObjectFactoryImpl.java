/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.object.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ObjectFactoryImpl extends EFactoryImpl implements ObjectFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ObjectFactory init() {
        try {
            ObjectFactory theObjectFactory = (ObjectFactory)EPackage.Registry.INSTANCE.getEFactory(ObjectPackage.eNS_URI);
            if (theObjectFactory != null) {
                return theObjectFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new ObjectFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ObjectFactoryImpl() {
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
            case ObjectPackage.CREATE_EOBJECT: return createCreateEObject();
            case ObjectPackage.DELETE_EOBJECT: return createDeleteEObject();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public <T extends EObject> CreateEObject<T> createCreateEObject() {
        CreateEObjectImpl<T> createEObject = new CreateEObjectImpl<T>();
        return createEObject;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public <T extends EObject> DeleteEObject<T> createDeleteEObject() {
        DeleteEObjectImpl<T> deleteEObject = new DeleteEObjectImpl<T>();
        return deleteEObject;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ObjectPackage getObjectPackage() {
        return (ObjectPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static ObjectPackage getPackage() {
        return ObjectPackage.eINSTANCE;
    }

} //ObjectFactoryImpl
