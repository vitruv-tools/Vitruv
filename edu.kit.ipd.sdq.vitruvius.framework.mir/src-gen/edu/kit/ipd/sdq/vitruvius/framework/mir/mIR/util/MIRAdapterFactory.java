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
      public Adapter caseMapping(Mapping object)
      {
        return createMappingAdapter();
      }
      @Override
      public Adapter caseMappableElement(MappableElement object)
      {
        return createMappableElementAdapter();
      }
      @Override
      public Adapter caseWhen(When object)
      {
        return createWhenAdapter();
      }
      @Override
      public Adapter caseWhere(Where object)
      {
        return createWhereAdapter();
      }
      @Override
      public Adapter caseEClassParameter(EClassParameter object)
      {
        return createEClassParameterAdapter();
      }
      @Override
      public Adapter caseInvariant(Invariant object)
      {
        return createInvariantAdapter();
      }
      @Override
      public Adapter caseOCLBlock(OCLBlock object)
      {
        return createOCLBlockAdapter();
      }
      @Override
      public Adapter caseResponse(Response object)
      {
        return createResponseAdapter();
      }
      @Override
      public Adapter caseBaseMapping(BaseMapping object)
      {
        return createBaseMappingAdapter();
      }
      @Override
      public Adapter caseSubMapping(SubMapping object)
      {
        return createSubMappingAdapter();
      }
      @Override
      public Adapter caseNamedEClass(NamedEClass object)
      {
        return createNamedEClassAdapter();
      }
      @Override
      public Adapter caseNamedFeature(NamedFeature object)
      {
        return createNamedFeatureAdapter();
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
   * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappableElement <em>Mappable Element</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappableElement
   * @generated
   */
  public Adapter createMappableElementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.When <em>When</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.When
   * @generated
   */
  public Adapter createWhenAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Where <em>Where</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Where
   * @generated
   */
  public Adapter createWhereAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.EClassParameter <em>EClass Parameter</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.EClassParameter
   * @generated
   */
  public Adapter createEClassParameterAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Invariant <em>Invariant</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Invariant
   * @generated
   */
  public Adapter createInvariantAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.OCLBlock <em>OCL Block</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.OCLBlock
   * @generated
   */
  public Adapter createOCLBlockAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Response <em>Response</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Response
   * @generated
   */
  public Adapter createResponseAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.BaseMapping <em>Base Mapping</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.BaseMapping
   * @generated
   */
  public Adapter createBaseMappingAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.SubMapping <em>Sub Mapping</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.SubMapping
   * @generated
   */
  public Adapter createSubMappingAdapter()
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
   * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedFeature <em>Named Feature</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedFeature
   * @generated
   */
  public Adapter createNamedFeatureAdapter()
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
