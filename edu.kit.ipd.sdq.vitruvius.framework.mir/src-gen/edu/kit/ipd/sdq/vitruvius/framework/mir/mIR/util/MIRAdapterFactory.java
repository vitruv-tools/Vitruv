/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.util;

import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage
 * @generated
 */
public class MIRAdapterFactory extends AdapterFactoryImpl
{
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static MIRPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MIRAdapterFactory()
  {
    if (modelPackage == null)
    {
      modelPackage = MIRPackage.eINSTANCE;
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
  public boolean isFactoryForType(Object object)
  {
    if (object == modelPackage)
    {
      return true;
    }
    if (object instanceof EObject)
    {
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
  protected MIRSwitch<Adapter> modelSwitch =
    new MIRSwitch<Adapter>()
    {
      @Override
      public Adapter caseMIRFile(MIRFile object)
      {
        return createMIRFileAdapter();
      }
      @Override
      public Adapter caseImport(Import object)
      {
        return createImportAdapter();
      }
      @Override
      public Adapter caseBundle(Bundle object)
      {
        return createBundleAdapter();
      }
      @Override
      public Adapter caseMappingBody(MappingBody object)
      {
        return createMappingBodyAdapter();
      }
      @Override
      public Adapter caseMapping(Mapping object)
      {
        return createMappingAdapter();
      }
      @Override
      public Adapter caseTypedElement(TypedElement object)
      {
        return createTypedElementAdapter();
      }
      @Override
      public Adapter caseTypedElementRef(TypedElementRef object)
      {
        return createTypedElementRefAdapter();
      }
      @Override
      public Adapter caseClassMapping(ClassMapping object)
      {
        return createClassMappingAdapter();
      }
      @Override
      public Adapter caseFeatureMapping(FeatureMapping object)
      {
        return createFeatureMappingAdapter();
      }
      @Override
      public Adapter caseNamedEClass(NamedEClass object)
      {
        return createNamedEClassAdapter();
      }
      @Override
      public Adapter caseFeatureCall(FeatureCall object)
      {
        return createFeatureCallAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object)
      {
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
  public Adapter createAdapter(Notifier target)
  {
    return modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile <em>File</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile
   * @generated
   */
  public Adapter createMIRFileAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Import <em>Import</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Import
   * @generated
   */
  public Adapter createImportAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Bundle <em>Bundle</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Bundle
   * @generated
   */
  public Adapter createBundleAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappingBody <em>Mapping Body</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappingBody
   * @generated
   */
  public Adapter createMappingBodyAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping <em>Mapping</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping
   * @generated
   */
  public Adapter createMappingAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.TypedElement <em>Typed Element</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.TypedElement
   * @generated
   */
  public Adapter createTypedElementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.TypedElementRef <em>Typed Element Ref</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.TypedElementRef
   * @generated
   */
  public Adapter createTypedElementRefAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ClassMapping <em>Class Mapping</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ClassMapping
   * @generated
   */
  public Adapter createClassMappingAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureMapping <em>Feature Mapping</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureMapping
   * @generated
   */
  public Adapter createFeatureMappingAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass <em>Named EClass</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass
   * @generated
   */
  public Adapter createNamedEClassAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall <em>Feature Call</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall
   * @generated
   */
  public Adapter createFeatureCallAdapter()
  {
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
  public Adapter createEObjectAdapter()
  {
    return null;
  }

} //MIRAdapterFactory
