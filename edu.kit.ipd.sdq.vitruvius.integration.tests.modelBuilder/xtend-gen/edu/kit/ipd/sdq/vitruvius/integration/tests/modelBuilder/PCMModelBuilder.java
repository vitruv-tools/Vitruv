package edu.kit.ipd.sdq.vitruvius.integration.tests.modelBuilder;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class PCMModelBuilder {
  private EList<Change> changes;
  
  private /* EList<CompositeDataType> */Object compositeDataTypes /* Skipped initializer because of errors */;
  
  private /* EList<CollectionDataType> */Object collectionDataTypes /* Skipped initializer because of errors */;
  
  private /* EList<ComposedProvidingRequiringEntity> */Object composedEntities /* Skipped initializer because of errors */;
  
  private /* Repository */Object repo;
  
  private int i;
  
  public PCMModelBuilder(final EList<Change> changes) {
    throw new Error("Unresolved compilation problems:"
      + "\nRepository cannot be resolved to a type."
      + "\nThe method or field RepositoryFactory is undefined for the type PCMModelBuilder"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRepository cannot be resolved"
      + "\nentityName cannot be resolved"
      + "\nentityName cannot be resolved"
      + "\nid cannot be resolved"
      + "\nid cannot be resolved"
      + "\nrepositoryDescription cannot be resolved"
      + "\nrepositoryDescription cannot be resolved");
  }
  
  public Repository createPCMModel() {
    final Procedure1<Change> _function = new Procedure1<Change>() {
      @Override
      public void apply(final Change it) {
        PCMModelBuilder.this.createModelElement(it);
      }
    };
    IterableExtensions.<Change>forEach(this.changes, _function);
    return this.repo;
  }
  
  public Boolean createModelElement(final Change change) {
    boolean _switchResult = false;
    boolean _matched = false;
    if (!_matched) {
      if (change instanceof CompositeChange) {
        _matched=true;
        List<Change> _changes = ((CompositeChange)change).getChanges();
        final Procedure1<Change> _function = new Procedure1<Change>() {
          @Override
          public void apply(final Change it) {
            PCMModelBuilder.this.createModelElement(it);
          }
        };
        IterableExtensions.<Change>forEach(_changes, _function);
      }
    }
    if (!_matched) {
      if (change instanceof EMFModelChange) {
        _matched=true;
        _switchResult = this.createModelElementFromChange(((EMFModelChange)change));
      }
    }
    return Boolean.valueOf(_switchResult);
  }
  
  public Object findDataTypeId(final /* CollectionDataType */Object type) {
    throw new Error("Unresolved compilation problems:"
      + "\nid cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nid cannot be resolved"
      + "\nid cannot be resolved");
  }
  
  public Object findDataTypeId(final /* CompositeDataType */Object type) {
    throw new Error("Unresolved compilation problems:"
      + "\nid cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nid cannot be resolved"
      + "\nid cannot be resolved");
  }
  
  public boolean createModelElementFromChange(final EMFModelChange change) {
    throw new Error("Unresolved compilation problems:"
      + "\nCompositeDataType cannot be resolved to a type."
      + "\nCollectionDataType cannot be resolved to a type."
      + "\nInnerDeclaration cannot be resolved to a type."
      + "\nBasicComponent cannot be resolved to a type."
      + "\nCompositeComponent cannot be resolved to a type."
      + "\nInterface cannot be resolved to a type."
      + "\nOperationSignature cannot be resolved to a type."
      + "\nParameter cannot be resolved to a type."
      + "\nOperationRequiredRole cannot be resolved to a type."
      + "\nOperationProvidedRole cannot be resolved to a type."
      + "\nAssemblyContext cannot be resolved to a type."
      + "\nProvidedDelegationConnector cannot be resolved to a type."
      + "\nRequiredDelegationConnector cannot be resolved to a type."
      + "\nAssemblyConnector cannot be resolved to a type."
      + "\nUnreachable code: The case can never match. It is already handled by a previous condition."
      + "\nUnreachable code: The case can never match. It is already handled by a previous condition."
      + "\nUnreachable code: The case can never match. It is already handled by a previous condition."
      + "\nUnreachable code: The case can never match. It is already handled by a previous condition."
      + "\nUnreachable code: The case can never match. It is already handled by a previous condition."
      + "\nUnreachable code: The case can never match. It is already handled by a previous condition."
      + "\nUnreachable code: The case can never match. It is already handled by a previous condition."
      + "\nUnreachable code: The case can never match. It is already handled by a previous condition."
      + "\nUnreachable code: The case can never match. It is already handled by a previous condition."
      + "\nUnreachable code: The case can never match. It is already handled by a previous condition."
      + "\nUnreachable code: The case can never match. It is already handled by a previous condition."
      + "\nUnreachable code: The case can never match. It is already handled by a previous condition."
      + "\nUnreachable code: The case can never match. It is already handled by a previous condition.");
  }
  
  public Object createAssemblyConnector(final /* AssemblyConnector */Object newValue) {
    throw new Error("Unresolved compilation problems:"
      + "\nOperationProvidedRole cannot be resolved to a type."
      + "\nOperationRequiredRole cannot be resolved to a type."
      + "\nThe method or field CompositionFactory is undefined for the type PCMModelBuilder"
      + "\nThe method id is undefined for the type PCMModelBuilder"
      + "\nThe method id is undefined for the type PCMModelBuilder"
      + "\nThe method id is undefined for the type PCMModelBuilder"
      + "\nThe method id is undefined for the type PCMModelBuilder"
      + "\nThere is no context to infer the closure\'s argument types from. Consider typing the arguments or put the closures into a typed context."
      + "\nThere is no context to infer the closure\'s argument types from. Consider typing the arguments or put the closures into a typed context."
      + "\nThere is no context to infer the closure\'s argument types from. Consider typing the arguments or put the closures into a typed context."
      + "\nThere is no context to infer the closure\'s argument types from. Consider typing the arguments or put the closures into a typed context."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateAssemblyConnector cannot be resolved"
      + "\nvalues cannot be resolved"
      + "\nid cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nparentStructure__Connector cannot be resolved"
      + "\nid cannot be resolved"
      + "\nassemblyContexts__ComposedStructure cannot be resolved"
      + "\nfindFirst cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nprovidingAssemblyContext_AssemblyConnector cannot be resolved"
      + "\nid cannot be resolved"
      + "\nprovidingAssemblyContext_AssemblyConnector cannot be resolved"
      + "\nassemblyContexts__ComposedStructure cannot be resolved"
      + "\nfindFirst cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nrequiringAssemblyContext_AssemblyConnector cannot be resolved"
      + "\nid cannot be resolved"
      + "\nrequiringAssemblyContext_AssemblyConnector cannot be resolved"
      + "\nencapsulatedComponent__AssemblyContext cannot be resolved"
      + "\nprovidedRoles_InterfaceProvidingEntity cannot be resolved"
      + "\nfindFirst cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nprovidedRole_AssemblyConnector cannot be resolved"
      + "\nid cannot be resolved"
      + "\nprovidedRole_AssemblyConnector cannot be resolved"
      + "\nencapsulatedComponent__AssemblyContext cannot be resolved"
      + "\nrequiredRoles_InterfaceRequiringEntity cannot be resolved"
      + "\nfindFirst cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nrequiredRole_AssemblyConnector cannot be resolved"
      + "\nid cannot be resolved"
      + "\nrequiredRole_AssemblyConnector cannot be resolved"
      + "\nconnectors__ComposedStructure cannot be resolved"
      + "\nadd cannot be resolved");
  }
  
  public Object createProvidedDelegationConnector(final /* ProvidedDelegationConnector */Object newValue) {
    throw new Error("Unresolved compilation problems:"
      + "\nOperationProvidedRole cannot be resolved to a type."
      + "\nOperationProvidedRole cannot be resolved to a type."
      + "\nThe method or field CompositionFactory is undefined for the type PCMModelBuilder"
      + "\nThe method id is undefined for the type PCMModelBuilder"
      + "\nThe method id is undefined for the type PCMModelBuilder"
      + "\nThe method id is undefined for the type PCMModelBuilder"
      + "\nThe method id is undefined for the type PCMModelBuilder"
      + "\nThe method id is undefined for the type PCMModelBuilder"
      + "\nThere is no context to infer the closure\'s argument types from. Consider typing the arguments or put the closures into a typed context."
      + "\nThere is no context to infer the closure\'s argument types from. Consider typing the arguments or put the closures into a typed context."
      + "\nThere is no context to infer the closure\'s argument types from. Consider typing the arguments or put the closures into a typed context."
      + "\nThere is no context to infer the closure\'s argument types from. Consider typing the arguments or put the closures into a typed context."
      + "\nThere is no context to infer the closure\'s argument types from. Consider typing the arguments or put the closures into a typed context."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateProvidedDelegationConnector cannot be resolved"
      + "\nvalues cannot be resolved"
      + "\nid cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nparentStructure__Connector cannot be resolved"
      + "\nid cannot be resolved"
      + "\nassemblyContexts__ComposedStructure cannot be resolved"
      + "\nfindFirst cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nassemblyContext_ProvidedDelegationConnector cannot be resolved"
      + "\nid cannot be resolved"
      + "\nassemblyContext_ProvidedDelegationConnector cannot be resolved"
      + "\ncomponents__Repository cannot be resolved"
      + "\nfindFirst cannot be resolved"
      + "\nequals cannot be resolved"
      + "\ninnerProvidedRole_ProvidedDelegationConnector cannot be resolved"
      + "\nprovidingEntity_ProvidedRole cannot be resolved"
      + "\nid cannot be resolved"
      + "\nprovidedRoles_InterfaceProvidingEntity cannot be resolved"
      + "\nfindFirst cannot be resolved"
      + "\nequals cannot be resolved"
      + "\ninnerProvidedRole_ProvidedDelegationConnector cannot be resolved"
      + "\nid cannot be resolved"
      + "\ninnerProvidedRole_ProvidedDelegationConnector cannot be resolved"
      + "\ncomponents__Repository cannot be resolved"
      + "\nfindFirst cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nouterProvidedRole_ProvidedDelegationConnector cannot be resolved"
      + "\nprovidingEntity_ProvidedRole cannot be resolved"
      + "\nid cannot be resolved"
      + "\nprovidedRoles_InterfaceProvidingEntity cannot be resolved"
      + "\nfindFirst cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nouterProvidedRole_ProvidedDelegationConnector cannot be resolved"
      + "\nid cannot be resolved"
      + "\nouterProvidedRole_ProvidedDelegationConnector cannot be resolved"
      + "\nconnectors__ComposedStructure cannot be resolved"
      + "\nadd cannot be resolved");
  }
  
  public Object createRequiredDelegationConnector(final /* RequiredDelegationConnector */Object newValue) {
    throw new Error("Unresolved compilation problems:"
      + "\nOperationRequiredRole cannot be resolved to a type."
      + "\nOperationRequiredRole cannot be resolved to a type."
      + "\nThe method or field CompositionFactory is undefined for the type PCMModelBuilder"
      + "\nThe method id is undefined for the type PCMModelBuilder"
      + "\nThe method id is undefined for the type PCMModelBuilder"
      + "\nThe method id is undefined for the type PCMModelBuilder"
      + "\nThe method id is undefined for the type PCMModelBuilder"
      + "\nThe method id is undefined for the type PCMModelBuilder"
      + "\nThere is no context to infer the closure\'s argument types from. Consider typing the arguments or put the closures into a typed context."
      + "\nThere is no context to infer the closure\'s argument types from. Consider typing the arguments or put the closures into a typed context."
      + "\nThere is no context to infer the closure\'s argument types from. Consider typing the arguments or put the closures into a typed context."
      + "\nThere is no context to infer the closure\'s argument types from. Consider typing the arguments or put the closures into a typed context."
      + "\nThere is no context to infer the closure\'s argument types from. Consider typing the arguments or put the closures into a typed context."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateRequiredDelegationConnector cannot be resolved"
      + "\nvalues cannot be resolved"
      + "\nid cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nparentStructure__Connector cannot be resolved"
      + "\nid cannot be resolved"
      + "\nassemblyContexts__ComposedStructure cannot be resolved"
      + "\nfindFirst cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nassemblyContext_RequiredDelegationConnector cannot be resolved"
      + "\nid cannot be resolved"
      + "\nassemblyContext_RequiredDelegationConnector cannot be resolved"
      + "\ncomponents__Repository cannot be resolved"
      + "\nfindFirst cannot be resolved"
      + "\nequals cannot be resolved"
      + "\ninnerRequiredRole_RequiredDelegationConnector cannot be resolved"
      + "\nrequiringEntity_RequiredRole cannot be resolved"
      + "\nid cannot be resolved"
      + "\nrequiredRoles_InterfaceRequiringEntity cannot be resolved"
      + "\nfindFirst cannot be resolved"
      + "\nequals cannot be resolved"
      + "\ninnerRequiredRole_RequiredDelegationConnector cannot be resolved"
      + "\nid cannot be resolved"
      + "\ninnerRequiredRole_RequiredDelegationConnector cannot be resolved"
      + "\ncomponents__Repository cannot be resolved"
      + "\nfindFirst cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nouterRequiredRole_RequiredDelegationConnector cannot be resolved"
      + "\nrequiringEntity_RequiredRole cannot be resolved"
      + "\nid cannot be resolved"
      + "\nrequiredRoles_InterfaceRequiringEntity cannot be resolved"
      + "\nfindFirst cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nouterRequiredRole_RequiredDelegationConnector cannot be resolved"
      + "\nid cannot be resolved"
      + "\nouterRequiredRole_RequiredDelegationConnector cannot be resolved"
      + "\nconnectors__ComposedStructure cannot be resolved"
      + "\nadd cannot be resolved");
  }
  
  public Object createAssemblyContext(final /* AssemblyContext */Object newValue) {
    throw new Error("Unresolved compilation problems:"
      + "\nComposedProvidingRequiringEntity cannot be resolved to a type."
      + "\nThe method or field CompositionFactory is undefined for the type PCMModelBuilder"
      + "\nThe method id is undefined for the type PCMModelBuilder"
      + "\nThe method id is undefined for the type PCMModelBuilder"
      + "\nThere is no context to infer the closure\'s argument types from. Consider typing the arguments or put the closures into a typed context."
      + "\nThere is no context to infer the closure\'s argument types from. Consider typing the arguments or put the closures into a typed context."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateAssemblyContext cannot be resolved"
      + "\nvalues cannot be resolved"
      + "\ncomponents__Repository cannot be resolved"
      + "\nfindFirst cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nencapsulatedComponent__AssemblyContext cannot be resolved"
      + "\nid cannot be resolved"
      + "\nencapsulatedComponent__AssemblyContext cannot be resolved"
      + "\ncomponents__Repository cannot be resolved"
      + "\nfindFirst cannot be resolved"
      + "\nequals cannot be resolved"
      + "\ngetParentStructure__AssemblyContext cannot be resolved"
      + "\nid cannot be resolved"
      + "\nassemblyContexts__ComposedStructure cannot be resolved"
      + "\nadd cannot be resolved");
  }
  
  public Object createInnerDeclaration(final /* InnerDeclaration */Object newValue) {
    throw new Error("Unresolved compilation problems:"
      + "\nCollectionDataType cannot be resolved to a type."
      + "\nCollectionDataType cannot be resolved to a type."
      + "\nCompositeDataType cannot be resolved to a type."
      + "\nCompositeDataType cannot be resolved to a type."
      + "\nThe method or field RepositoryFactory is undefined for the type PCMModelBuilder"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateInnerDeclaration cannot be resolved"
      + "\nentityName cannot be resolved"
      + "\nentityName cannot be resolved"
      + "\ndatatype_InnerDeclaration cannot be resolved"
      + "\ndatatype_InnerDeclaration cannot be resolved"
      + "\nid cannot be resolved"
      + "\nequals cannot be resolved"
      + "\ndatatype_InnerDeclaration cannot be resolved"
      + "\ndatatype_InnerDeclaration cannot be resolved"
      + "\ndatatype_InnerDeclaration cannot be resolved"
      + "\nid cannot be resolved"
      + "\nequals cannot be resolved"
      + "\ndatatype_InnerDeclaration cannot be resolved"
      + "\ndatatype_InnerDeclaration cannot be resolved"
      + "\ndatatype_InnerDeclaration cannot be resolved"
      + "\nid cannot be resolved"
      + "\nequals cannot be resolved"
      + "\ncompositeDataType_InnerDeclaration cannot be resolved"
      + "\nid cannot be resolved"
      + "\ninnerDeclaration_CompositeDataType cannot be resolved"
      + "\nadd cannot be resolved");
  }
  
  public boolean createCollectionDataType(final /* CollectionDataType */Object newValue) {
    throw new Error("Unresolved compilation problems:"
      + "\nCollectionDataType cannot be resolved to a type."
      + "\nCollectionDataType cannot be resolved to a type."
      + "\nCompositeDataType cannot be resolved to a type."
      + "\nCompositeDataType cannot be resolved to a type."
      + "\nThe method or field RepositoryFactory is undefined for the type PCMModelBuilder"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateCollectionDataType cannot be resolved"
      + "\nvalues cannot be resolved"
      + "\ninnerType_CollectionDataType cannot be resolved"
      + "\ninnerType_CollectionDataType cannot be resolved"
      + "\nid cannot be resolved"
      + "\nequals cannot be resolved"
      + "\ninnerType_CollectionDataType cannot be resolved"
      + "\ninnerType_CollectionDataType cannot be resolved"
      + "\ninnerType_CollectionDataType cannot be resolved"
      + "\nid cannot be resolved"
      + "\nequals cannot be resolved"
      + "\ninnerType_CollectionDataType cannot be resolved"
      + "\ninnerType_CollectionDataType cannot be resolved"
      + "\ninnerType_CollectionDataType cannot be resolved"
      + "\ndataTypes__Repository cannot be resolved"
      + "\nadd cannot be resolved");
  }
  
  public boolean createCompositeDataType(final /* CompositeDataType */Object newValue) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field RepositoryFactory is undefined for the type PCMModelBuilder"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateCompositeDataType cannot be resolved"
      + "\nid cannot be resolved"
      + "\nid cannot be resolved"
      + "\nentityName cannot be resolved"
      + "\nentityName cannot be resolved"
      + "\ndataTypes__Repository cannot be resolved"
      + "\nadd cannot be resolved");
  }
  
  public Object createProvidedRole(final /* OperationProvidedRole */Object newValue) {
    throw new Error("Unresolved compilation problems:"
      + "\nOperationInterface cannot be resolved to a type."
      + "\nThe method or field RepositoryFactory is undefined for the type PCMModelBuilder"
      + "\nThe method id is undefined for the type PCMModelBuilder"
      + "\nThe method id is undefined for the type PCMModelBuilder"
      + "\nThere is no context to infer the closure\'s argument types from. Consider typing the arguments or put the closures into a typed context."
      + "\nThere is no context to infer the closure\'s argument types from. Consider typing the arguments or put the closures into a typed context."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateOperationProvidedRole cannot be resolved"
      + "\nvalues cannot be resolved"
      + "\ninterfaces__Repository cannot be resolved"
      + "\nfindFirst cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nprovidedInterface__OperationProvidedRole cannot be resolved"
      + "\nid cannot be resolved"
      + "\nprovidedInterface__OperationProvidedRole cannot be resolved"
      + "\ncomponents__Repository cannot be resolved"
      + "\nfindFirst cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nprovidingEntity_ProvidedRole cannot be resolved"
      + "\nid cannot be resolved"
      + "\nprovidedRoles_InterfaceProvidingEntity cannot be resolved"
      + "\nadd cannot be resolved");
  }
  
  public Object createRequiredRole(final /* OperationRequiredRole */Object newValue) {
    throw new Error("Unresolved compilation problems:"
      + "\nOperationInterface cannot be resolved to a type."
      + "\nThe method or field RepositoryFactory is undefined for the type PCMModelBuilder"
      + "\nThe method id is undefined for the type PCMModelBuilder"
      + "\nThe method id is undefined for the type PCMModelBuilder"
      + "\nThere is no context to infer the closure\'s argument types from. Consider typing the arguments or put the closures into a typed context."
      + "\nThere is no context to infer the closure\'s argument types from. Consider typing the arguments or put the closures into a typed context."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateOperationRequiredRole cannot be resolved"
      + "\nvalues cannot be resolved"
      + "\ninterfaces__Repository cannot be resolved"
      + "\nfindFirst cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nrequiredInterface__OperationRequiredRole cannot be resolved"
      + "\nid cannot be resolved"
      + "\nrequiredInterface__OperationRequiredRole cannot be resolved"
      + "\ncomponents__Repository cannot be resolved"
      + "\nfindFirst cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nrequiringEntity_RequiredRole cannot be resolved"
      + "\nid cannot be resolved"
      + "\nrequiredRoles_InterfaceRequiringEntity cannot be resolved"
      + "\nadd cannot be resolved");
  }
  
  public Object createParameter(final /* Parameter */Object newValue) {
    throw new Error("Unresolved compilation problems:"
      + "\nCollectionDataType cannot be resolved to a type."
      + "\nCollectionDataType cannot be resolved to a type."
      + "\nCompositeDataType cannot be resolved to a type."
      + "\nCompositeDataType cannot be resolved to a type."
      + "\nOperationInterface cannot be resolved to a type."
      + "\nOperationSignature cannot be resolved to a type."
      + "\nThe method or field RepositoryFactory is undefined for the type PCMModelBuilder"
      + "\nThe method id is undefined for the type PCMModelBuilder"
      + "\nThe method id is undefined for the type PCMModelBuilder"
      + "\nThere is no context to infer the closure\'s argument types from. Consider typing the arguments or put the closures into a typed context."
      + "\nThere is no context to infer the closure\'s argument types from. Consider typing the arguments or put the closures into a typed context."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateParameter cannot be resolved"
      + "\nentityName cannot be resolved"
      + "\nentityName cannot be resolved"
      + "\ndataType__Parameter cannot be resolved"
      + "\ndataType__Parameter cannot be resolved"
      + "\nid cannot be resolved"
      + "\nequals cannot be resolved"
      + "\ndataType__Parameter cannot be resolved"
      + "\ndataType__Parameter cannot be resolved"
      + "\ndataType__Parameter cannot be resolved"
      + "\nid cannot be resolved"
      + "\nequals cannot be resolved"
      + "\ndataType__Parameter cannot be resolved"
      + "\ndataType__Parameter cannot be resolved"
      + "\ndataType__Parameter cannot be resolved"
      + "\nmodifier__Parameter cannot be resolved"
      + "\nmodifier__Parameter cannot be resolved"
      + "\noperationSignature__Parameter cannot be resolved"
      + "\ninterface__OperationSignature cannot be resolved"
      + "\ninterfaces__Repository cannot be resolved"
      + "\nfindFirst cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nid cannot be resolved"
      + "\nsignatures__OperationInterface cannot be resolved"
      + "\nfindFirst cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nid cannot be resolved"
      + "\nparameters__OperationSignature cannot be resolved"
      + "\nadd cannot be resolved");
  }
  
  public Object createSignature(final /* OperationSignature */Object newValue) {
    throw new Error("Unresolved compilation problems:"
      + "\nCollectionDataType cannot be resolved to a type."
      + "\nCollectionDataType cannot be resolved to a type."
      + "\nCompositeDataType cannot be resolved to a type."
      + "\nCompositeDataType cannot be resolved to a type."
      + "\nOperationInterface cannot be resolved to a type."
      + "\nThe method or field RepositoryFactory is undefined for the type PCMModelBuilder"
      + "\nThe method id is undefined for the type PCMModelBuilder"
      + "\nThere is no context to infer the closure\'s argument types from. Consider typing the arguments or put the closures into a typed context."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateOperationSignature cannot be resolved"
      + "\nvalues cannot be resolved"
      + "\nreturnType__OperationSignature cannot be resolved"
      + "\nreturnType__OperationSignature cannot be resolved"
      + "\nid cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nreturnType__OperationSignature cannot be resolved"
      + "\nreturnType__OperationSignature cannot be resolved"
      + "\nreturnType__OperationSignature cannot be resolved"
      + "\nid cannot be resolved"
      + "\nequals cannot be resolved"
      + "\nreturnType__OperationSignature cannot be resolved"
      + "\nreturnType__OperationSignature cannot be resolved"
      + "\nreturnType__OperationSignature cannot be resolved"
      + "\ninterfaces__Repository cannot be resolved"
      + "\nfindFirst cannot be resolved"
      + "\nequals cannot be resolved"
      + "\ninterface__OperationSignature cannot be resolved"
      + "\nid cannot be resolved"
      + "\nsignatures__OperationInterface cannot be resolved"
      + "\nadd cannot be resolved");
  }
  
  public Object createInterface(final /* Interface */Object newValue) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field RepositoryFactory is undefined for the type PCMModelBuilder"
      + "\nThe method id is undefined for the type PCMModelBuilder"
      + "\nThere is no context to infer the closure\'s argument types from. Consider typing the arguments or put the closures into a typed context."
      + "\neINSTANCE cannot be resolved"
      + "\ncreateOperationInterface cannot be resolved"
      + "\nvalues cannot be resolved"
      + "\nparentInterfaces__Interface cannot be resolved"
      + "\nlength cannot be resolved"
      + "\nparentInterfaces__Interface cannot be resolved"
      + "\nget cannot be resolved"
      + "\nid cannot be resolved"
      + "\nparentInterfaces__Interface cannot be resolved"
      + "\nadd cannot be resolved"
      + "\ninterfaces__Repository cannot be resolved"
      + "\nfindFirst cannot be resolved"
      + "\nequals cannot be resolved"
      + "\ninterfaces__Repository cannot be resolved"
      + "\nadd cannot be resolved");
  }
  
  public Object createBasicComponent(final /* BasicComponent */Object newValue) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field RepositoryFactory is undefined for the type PCMModelBuilder"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateBasicComponent cannot be resolved"
      + "\nvalues cannot be resolved"
      + "\ncomponents__Repository cannot be resolved"
      + "\nadd cannot be resolved");
  }
  
  public boolean createCompositeComponent(final /* CompositeComponent */Object newValue) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field RepositoryFactory is undefined for the type PCMModelBuilder"
      + "\neINSTANCE cannot be resolved"
      + "\ncreateCompositeComponent cannot be resolved"
      + "\nvalues cannot be resolved"
      + "\ncomponents__Repository cannot be resolved"
      + "\nadd cannot be resolved");
  }
  
  public Object setValues(final /* Entity */Object createdElement, final /* Entity */Object newValue) {
    throw new Error("Unresolved compilation problems:"
      + "\nentityName cannot be resolved"
      + "\nentityName cannot be resolved"
      + "\nid cannot be resolved"
      + "\nid cannot be resolved");
  }
}
