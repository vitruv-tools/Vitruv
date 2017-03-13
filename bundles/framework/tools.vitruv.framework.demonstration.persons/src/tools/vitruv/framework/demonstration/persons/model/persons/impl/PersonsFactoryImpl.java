/**
 */
package tools.vitruv.framework.demonstration.persons.model.persons.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import tools.vitruv.framework.demonstration.persons.model.persons.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PersonsFactoryImpl extends EFactoryImpl implements PersonsFactory {
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static PersonsFactory init() {
    try {
      PersonsFactory thePersonsFactory = (PersonsFactory)EPackage.Registry.INSTANCE.getEFactory(PersonsPackage.eNS_URI);
      if (thePersonsFactory != null) {
        return thePersonsFactory;
      }
    }
    catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new PersonsFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PersonsFactoryImpl() {
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
      case PersonsPackage.MALE: return createMale();
      case PersonsPackage.FEMALE: return createFemale();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Male createMale() {
    MaleImpl male = new MaleImpl();
    return male;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Female createFemale() {
    FemaleImpl female = new FemaleImpl();
    return female;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PersonsPackage getPersonsPackage() {
    return (PersonsPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static PersonsPackage getPackage() {
    return PersonsPackage.eINSTANCE;
  }

} //PersonsFactoryImpl
