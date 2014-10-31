/**
 */
package pcm_mockup.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import pcm_mockup.Component;
import pcm_mockup.Interface;
import pcm_mockup.Pcm_mockupPackage;
import pcm_mockup.Repository;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Repository</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link pcm_mockup.impl.RepositoryImpl#getInterfaces <em>Interfaces</em>}</li>
 *   <li>{@link pcm_mockup.impl.RepositoryImpl#getComponents <em>Components</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RepositoryImpl extends IdentifiedImpl implements Repository {
    /**
     * The cached value of the '{@link #getInterfaces() <em>Interfaces</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInterfaces()
     * @generated
     * @ordered
     */
    protected EList<Interface> interfaces;

    /**
     * The cached value of the '{@link #getComponents() <em>Components</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getComponents()
     * @generated
     * @ordered
     */
    protected EList<Component> components;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected RepositoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Pcm_mockupPackage.Literals.REPOSITORY;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Interface> getInterfaces() {
        if (interfaces == null) {
            interfaces = new EObjectContainmentEList<Interface>(Interface.class, this, Pcm_mockupPackage.REPOSITORY__INTERFACES);
        }
        return interfaces;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Component> getComponents() {
        if (components == null) {
            components = new EObjectContainmentEList<Component>(Component.class, this, Pcm_mockupPackage.REPOSITORY__COMPONENTS);
        }
        return components;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case Pcm_mockupPackage.REPOSITORY__INTERFACES:
                return ((InternalEList<?>)getInterfaces()).basicRemove(otherEnd, msgs);
            case Pcm_mockupPackage.REPOSITORY__COMPONENTS:
                return ((InternalEList<?>)getComponents()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case Pcm_mockupPackage.REPOSITORY__INTERFACES:
                return getInterfaces();
            case Pcm_mockupPackage.REPOSITORY__COMPONENTS:
                return getComponents();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case Pcm_mockupPackage.REPOSITORY__INTERFACES:
                getInterfaces().clear();
                getInterfaces().addAll((Collection<? extends Interface>)newValue);
                return;
            case Pcm_mockupPackage.REPOSITORY__COMPONENTS:
                getComponents().clear();
                getComponents().addAll((Collection<? extends Component>)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case Pcm_mockupPackage.REPOSITORY__INTERFACES:
                getInterfaces().clear();
                return;
            case Pcm_mockupPackage.REPOSITORY__COMPONENTS:
                getComponents().clear();
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case Pcm_mockupPackage.REPOSITORY__INTERFACES:
                return interfaces != null && !interfaces.isEmpty();
            case Pcm_mockupPackage.REPOSITORY__COMPONENTS:
                return components != null && !components.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //RepositoryImpl
