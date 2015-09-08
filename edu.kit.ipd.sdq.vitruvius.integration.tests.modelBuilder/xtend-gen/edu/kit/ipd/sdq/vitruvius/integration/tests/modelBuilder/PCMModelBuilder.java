package edu.kit.ipd.sdq.vitruvius.integration.tests.modelBuilder;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateRootEObject;
import java.util.List;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.ComposedStructure;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.Connector;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;
import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.ParameterModifier;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.RequiredRole;

@SuppressWarnings("all")
public class PCMModelBuilder {
  private EList<Change> changes;
  
  private EList<CompositeDataType> compositeDataTypes = new BasicEList<CompositeDataType>();
  
  private EList<CollectionDataType> collectionDataTypes = new BasicEList<CollectionDataType>();
  
  private EList<ComposedProvidingRequiringEntity> composedEntities = new BasicEList<ComposedProvidingRequiringEntity>();
  
  private Repository repo;
  
  private int i;
  
  public PCMModelBuilder(final EList<Change> changes) {
    this.changes = changes;
    Repository _createRepository = RepositoryFactory.eINSTANCE.createRepository();
    this.repo = _createRepository;
    Change _get = changes.get(0);
    final EMFModelChange repoChange = ((EMFModelChange) _get);
    EChange _eChange = repoChange.getEChange();
    final CreateRootEObject rootChange = ((CreateRootEObject) _eChange);
    EObject _newValue = rootChange.getNewValue();
    final Repository oldRepo = ((Repository) _newValue);
    String _entityName = oldRepo.getEntityName();
    this.repo.setEntityName(_entityName);
    String _id = oldRepo.getId();
    this.repo.setId(_id);
    String _repositoryDescription = oldRepo.getRepositoryDescription();
    this.repo.setRepositoryDescription(_repositoryDescription);
    changes.remove(0);
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
  
  public String findDataTypeId(final CollectionDataType type) {
    final Function1<CollectionDataType, Boolean> _function = new Function1<CollectionDataType, Boolean>() {
      @Override
      public Boolean apply(final CollectionDataType el) {
        String _id = el.getId();
        String _id_1 = type.getId();
        return Boolean.valueOf(_id.equals(_id_1));
      }
    };
    final CollectionDataType data = IterableExtensions.<CollectionDataType>findFirst(this.collectionDataTypes, _function);
    return data.getId();
  }
  
  public String findDataTypeId(final CompositeDataType type) {
    final Function1<CompositeDataType, Boolean> _function = new Function1<CompositeDataType, Boolean>() {
      @Override
      public Boolean apply(final CompositeDataType el) {
        String _id = el.getId();
        String _id_1 = type.getId();
        return Boolean.valueOf(_id.equals(_id_1));
      }
    };
    final CompositeDataType data = IterableExtensions.<CompositeDataType>findFirst(this.compositeDataTypes, _function);
    return data.getId();
  }
  
  public boolean createModelElementFromChange(final EMFModelChange change) {
    boolean _xblockexpression = false;
    {
      EChange _eChange = change.getEChange();
      final CreateNonRootEObjectInList innerChange = ((CreateNonRootEObjectInList) _eChange);
      final Object newValue = innerChange.getNewValue();
      boolean _switchResult = false;
      boolean _matched = false;
      if (!_matched) {
        if (newValue instanceof CompositeDataType) {
          _matched=true;
          _switchResult = this.createCompositeDataType(((CompositeDataType)newValue));
        }
      }
      if (!_matched) {
        if (newValue instanceof CollectionDataType) {
          _matched=true;
          _switchResult = this.createCollectionDataType(((CollectionDataType)newValue));
        }
      }
      if (!_matched) {
        if (newValue instanceof InnerDeclaration) {
          _matched=true;
          _switchResult = this.createInnerDeclaration(((InnerDeclaration)newValue));
        }
      }
      if (!_matched) {
        if (newValue instanceof BasicComponent) {
          _matched=true;
          _switchResult = this.createBasicComponent(((BasicComponent)newValue));
        }
      }
      if (!_matched) {
        if (newValue instanceof CompositeComponent) {
          _matched=true;
          _switchResult = this.createCompositeComponent(((CompositeComponent)newValue));
        }
      }
      if (!_matched) {
        if (newValue instanceof Interface) {
          _matched=true;
          _switchResult = this.createInterface(((Interface)newValue));
        }
      }
      if (!_matched) {
        if (newValue instanceof OperationSignature) {
          _matched=true;
          _switchResult = this.createSignature(((OperationSignature)newValue));
        }
      }
      if (!_matched) {
        if (newValue instanceof Parameter) {
          _matched=true;
          _switchResult = this.createParameter(((Parameter)newValue));
        }
      }
      if (!_matched) {
        if (newValue instanceof OperationRequiredRole) {
          _matched=true;
          _switchResult = this.createRequiredRole(((OperationRequiredRole)newValue));
        }
      }
      if (!_matched) {
        if (newValue instanceof OperationProvidedRole) {
          _matched=true;
          _switchResult = this.createProvidedRole(((OperationProvidedRole)newValue));
        }
      }
      if (!_matched) {
        if (newValue instanceof AssemblyContext) {
          _matched=true;
          _switchResult = this.createAssemblyContext(((AssemblyContext)newValue));
        }
      }
      if (!_matched) {
        if (newValue instanceof ProvidedDelegationConnector) {
          _matched=true;
          _switchResult = this.createProvidedDelegationConnector(((ProvidedDelegationConnector)newValue));
        }
      }
      if (!_matched) {
        if (newValue instanceof RequiredDelegationConnector) {
          _matched=true;
          _switchResult = this.createRequiredDelegationConnector(((RequiredDelegationConnector)newValue));
        }
      }
      if (!_matched) {
        if (newValue instanceof AssemblyConnector) {
          _matched=true;
          _switchResult = this.createAssemblyConnector(((AssemblyConnector)newValue));
        }
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  public boolean createAssemblyConnector(final AssemblyConnector newValue) {
    boolean _xblockexpression = false;
    {
      final AssemblyConnector connector = CompositionFactory.eINSTANCE.createAssemblyConnector();
      this.setValues(connector, newValue);
      final Function1<ComposedProvidingRequiringEntity, Boolean> _function = new Function1<ComposedProvidingRequiringEntity, Boolean>() {
        @Override
        public Boolean apply(final ComposedProvidingRequiringEntity el) {
          String _id = el.getId();
          ComposedStructure _parentStructure__Connector = newValue.getParentStructure__Connector();
          String _id_1 = _parentStructure__Connector.getId();
          return Boolean.valueOf(_id.equals(_id_1));
        }
      };
      final ComposedProvidingRequiringEntity comp = IterableExtensions.<ComposedProvidingRequiringEntity>findFirst(this.composedEntities, _function);
      EList<AssemblyContext> _assemblyContexts__ComposedStructure = comp.getAssemblyContexts__ComposedStructure();
      final Function1<AssemblyContext, Boolean> _function_1 = new Function1<AssemblyContext, Boolean>() {
        @Override
        public Boolean apply(final AssemblyContext el) {
          String _id = el.getId();
          AssemblyContext _providingAssemblyContext_AssemblyConnector = newValue.getProvidingAssemblyContext_AssemblyConnector();
          String _id_1 = _providingAssemblyContext_AssemblyConnector.getId();
          return Boolean.valueOf(_id.equals(_id_1));
        }
      };
      final AssemblyContext provContext = IterableExtensions.<AssemblyContext>findFirst(_assemblyContexts__ComposedStructure, _function_1);
      connector.setProvidingAssemblyContext_AssemblyConnector(provContext);
      EList<AssemblyContext> _assemblyContexts__ComposedStructure_1 = comp.getAssemblyContexts__ComposedStructure();
      final Function1<AssemblyContext, Boolean> _function_2 = new Function1<AssemblyContext, Boolean>() {
        @Override
        public Boolean apply(final AssemblyContext el) {
          String _id = el.getId();
          AssemblyContext _requiringAssemblyContext_AssemblyConnector = newValue.getRequiringAssemblyContext_AssemblyConnector();
          String _id_1 = _requiringAssemblyContext_AssemblyConnector.getId();
          return Boolean.valueOf(_id.equals(_id_1));
        }
      };
      final AssemblyContext reqContext = IterableExtensions.<AssemblyContext>findFirst(_assemblyContexts__ComposedStructure_1, _function_2);
      connector.setRequiringAssemblyContext_AssemblyConnector(reqContext);
      RepositoryComponent _encapsulatedComponent__AssemblyContext = provContext.getEncapsulatedComponent__AssemblyContext();
      EList<ProvidedRole> _providedRoles_InterfaceProvidingEntity = _encapsulatedComponent__AssemblyContext.getProvidedRoles_InterfaceProvidingEntity();
      final Function1<ProvidedRole, Boolean> _function_3 = new Function1<ProvidedRole, Boolean>() {
        @Override
        public Boolean apply(final ProvidedRole el) {
          String _id = el.getId();
          OperationProvidedRole _providedRole_AssemblyConnector = newValue.getProvidedRole_AssemblyConnector();
          String _id_1 = _providedRole_AssemblyConnector.getId();
          return Boolean.valueOf(_id.equals(_id_1));
        }
      };
      ProvidedRole _findFirst = IterableExtensions.<ProvidedRole>findFirst(_providedRoles_InterfaceProvidingEntity, _function_3);
      final OperationProvidedRole provRole = ((OperationProvidedRole) _findFirst);
      connector.setProvidedRole_AssemblyConnector(provRole);
      RepositoryComponent _encapsulatedComponent__AssemblyContext_1 = reqContext.getEncapsulatedComponent__AssemblyContext();
      EList<RequiredRole> _requiredRoles_InterfaceRequiringEntity = _encapsulatedComponent__AssemblyContext_1.getRequiredRoles_InterfaceRequiringEntity();
      final Function1<RequiredRole, Boolean> _function_4 = new Function1<RequiredRole, Boolean>() {
        @Override
        public Boolean apply(final RequiredRole el) {
          String _id = el.getId();
          OperationRequiredRole _requiredRole_AssemblyConnector = newValue.getRequiredRole_AssemblyConnector();
          String _id_1 = _requiredRole_AssemblyConnector.getId();
          return Boolean.valueOf(_id.equals(_id_1));
        }
      };
      RequiredRole _findFirst_1 = IterableExtensions.<RequiredRole>findFirst(_requiredRoles_InterfaceRequiringEntity, _function_4);
      final OperationRequiredRole reqRole = ((OperationRequiredRole) _findFirst_1);
      connector.setRequiredRole_AssemblyConnector(reqRole);
      EList<Connector> _connectors__ComposedStructure = comp.getConnectors__ComposedStructure();
      _xblockexpression = _connectors__ComposedStructure.add(connector);
    }
    return _xblockexpression;
  }
  
  public boolean createProvidedDelegationConnector(final ProvidedDelegationConnector newValue) {
    boolean _xblockexpression = false;
    {
      final ProvidedDelegationConnector connector = CompositionFactory.eINSTANCE.createProvidedDelegationConnector();
      this.setValues(connector, newValue);
      final Function1<ComposedProvidingRequiringEntity, Boolean> _function = new Function1<ComposedProvidingRequiringEntity, Boolean>() {
        @Override
        public Boolean apply(final ComposedProvidingRequiringEntity el) {
          String _id = el.getId();
          ComposedStructure _parentStructure__Connector = newValue.getParentStructure__Connector();
          String _id_1 = _parentStructure__Connector.getId();
          return Boolean.valueOf(_id.equals(_id_1));
        }
      };
      final ComposedProvidingRequiringEntity comp = IterableExtensions.<ComposedProvidingRequiringEntity>findFirst(this.composedEntities, _function);
      EList<AssemblyContext> _assemblyContexts__ComposedStructure = comp.getAssemblyContexts__ComposedStructure();
      final Function1<AssemblyContext, Boolean> _function_1 = new Function1<AssemblyContext, Boolean>() {
        @Override
        public Boolean apply(final AssemblyContext el) {
          String _id = el.getId();
          AssemblyContext _assemblyContext_ProvidedDelegationConnector = newValue.getAssemblyContext_ProvidedDelegationConnector();
          String _id_1 = _assemblyContext_ProvidedDelegationConnector.getId();
          return Boolean.valueOf(_id.equals(_id_1));
        }
      };
      final AssemblyContext context = IterableExtensions.<AssemblyContext>findFirst(_assemblyContexts__ComposedStructure, _function_1);
      connector.setAssemblyContext_ProvidedDelegationConnector(context);
      EList<RepositoryComponent> _components__Repository = this.repo.getComponents__Repository();
      final Function1<RepositoryComponent, Boolean> _function_2 = new Function1<RepositoryComponent, Boolean>() {
        @Override
        public Boolean apply(final RepositoryComponent el) {
          String _id = el.getId();
          OperationProvidedRole _innerProvidedRole_ProvidedDelegationConnector = newValue.getInnerProvidedRole_ProvidedDelegationConnector();
          InterfaceProvidingEntity _providingEntity_ProvidedRole = _innerProvidedRole_ProvidedDelegationConnector.getProvidingEntity_ProvidedRole();
          String _id_1 = _providingEntity_ProvidedRole.getId();
          return Boolean.valueOf(_id.equals(_id_1));
        }
      };
      final RepositoryComponent innerComp = IterableExtensions.<RepositoryComponent>findFirst(_components__Repository, _function_2);
      EList<ProvidedRole> _providedRoles_InterfaceProvidingEntity = innerComp.getProvidedRoles_InterfaceProvidingEntity();
      final Function1<ProvidedRole, Boolean> _function_3 = new Function1<ProvidedRole, Boolean>() {
        @Override
        public Boolean apply(final ProvidedRole el) {
          String _id = el.getId();
          OperationProvidedRole _innerProvidedRole_ProvidedDelegationConnector = newValue.getInnerProvidedRole_ProvidedDelegationConnector();
          String _id_1 = _innerProvidedRole_ProvidedDelegationConnector.getId();
          return Boolean.valueOf(_id.equals(_id_1));
        }
      };
      ProvidedRole _findFirst = IterableExtensions.<ProvidedRole>findFirst(_providedRoles_InterfaceProvidingEntity, _function_3);
      final OperationProvidedRole innerProvRole = ((OperationProvidedRole) _findFirst);
      connector.setInnerProvidedRole_ProvidedDelegationConnector(innerProvRole);
      EList<RepositoryComponent> _components__Repository_1 = this.repo.getComponents__Repository();
      final Function1<RepositoryComponent, Boolean> _function_4 = new Function1<RepositoryComponent, Boolean>() {
        @Override
        public Boolean apply(final RepositoryComponent el) {
          String _id = el.getId();
          OperationProvidedRole _outerProvidedRole_ProvidedDelegationConnector = newValue.getOuterProvidedRole_ProvidedDelegationConnector();
          InterfaceProvidingEntity _providingEntity_ProvidedRole = _outerProvidedRole_ProvidedDelegationConnector.getProvidingEntity_ProvidedRole();
          String _id_1 = _providingEntity_ProvidedRole.getId();
          return Boolean.valueOf(_id.equals(_id_1));
        }
      };
      final RepositoryComponent outerComp = IterableExtensions.<RepositoryComponent>findFirst(_components__Repository_1, _function_4);
      EList<ProvidedRole> _providedRoles_InterfaceProvidingEntity_1 = outerComp.getProvidedRoles_InterfaceProvidingEntity();
      final Function1<ProvidedRole, Boolean> _function_5 = new Function1<ProvidedRole, Boolean>() {
        @Override
        public Boolean apply(final ProvidedRole el) {
          String _id = el.getId();
          OperationProvidedRole _outerProvidedRole_ProvidedDelegationConnector = newValue.getOuterProvidedRole_ProvidedDelegationConnector();
          String _id_1 = _outerProvidedRole_ProvidedDelegationConnector.getId();
          return Boolean.valueOf(_id.equals(_id_1));
        }
      };
      ProvidedRole _findFirst_1 = IterableExtensions.<ProvidedRole>findFirst(_providedRoles_InterfaceProvidingEntity_1, _function_5);
      final OperationProvidedRole outerProvRole = ((OperationProvidedRole) _findFirst_1);
      connector.setOuterProvidedRole_ProvidedDelegationConnector(outerProvRole);
      EList<Connector> _connectors__ComposedStructure = comp.getConnectors__ComposedStructure();
      _xblockexpression = _connectors__ComposedStructure.add(connector);
    }
    return _xblockexpression;
  }
  
  public boolean createRequiredDelegationConnector(final RequiredDelegationConnector newValue) {
    boolean _xblockexpression = false;
    {
      final RequiredDelegationConnector connector = CompositionFactory.eINSTANCE.createRequiredDelegationConnector();
      this.setValues(connector, newValue);
      final Function1<ComposedProvidingRequiringEntity, Boolean> _function = new Function1<ComposedProvidingRequiringEntity, Boolean>() {
        @Override
        public Boolean apply(final ComposedProvidingRequiringEntity el) {
          String _id = el.getId();
          ComposedStructure _parentStructure__Connector = newValue.getParentStructure__Connector();
          String _id_1 = _parentStructure__Connector.getId();
          return Boolean.valueOf(_id.equals(_id_1));
        }
      };
      final ComposedProvidingRequiringEntity comp = IterableExtensions.<ComposedProvidingRequiringEntity>findFirst(this.composedEntities, _function);
      EList<AssemblyContext> _assemblyContexts__ComposedStructure = comp.getAssemblyContexts__ComposedStructure();
      final Function1<AssemblyContext, Boolean> _function_1 = new Function1<AssemblyContext, Boolean>() {
        @Override
        public Boolean apply(final AssemblyContext el) {
          String _id = el.getId();
          AssemblyContext _assemblyContext_RequiredDelegationConnector = newValue.getAssemblyContext_RequiredDelegationConnector();
          String _id_1 = _assemblyContext_RequiredDelegationConnector.getId();
          return Boolean.valueOf(_id.equals(_id_1));
        }
      };
      final AssemblyContext context = IterableExtensions.<AssemblyContext>findFirst(_assemblyContexts__ComposedStructure, _function_1);
      connector.setAssemblyContext_RequiredDelegationConnector(context);
      EList<RepositoryComponent> _components__Repository = this.repo.getComponents__Repository();
      final Function1<RepositoryComponent, Boolean> _function_2 = new Function1<RepositoryComponent, Boolean>() {
        @Override
        public Boolean apply(final RepositoryComponent el) {
          String _id = el.getId();
          OperationRequiredRole _innerRequiredRole_RequiredDelegationConnector = newValue.getInnerRequiredRole_RequiredDelegationConnector();
          InterfaceRequiringEntity _requiringEntity_RequiredRole = _innerRequiredRole_RequiredDelegationConnector.getRequiringEntity_RequiredRole();
          String _id_1 = _requiringEntity_RequiredRole.getId();
          return Boolean.valueOf(_id.equals(_id_1));
        }
      };
      final RepositoryComponent innerComp = IterableExtensions.<RepositoryComponent>findFirst(_components__Repository, _function_2);
      EList<RequiredRole> _requiredRoles_InterfaceRequiringEntity = innerComp.getRequiredRoles_InterfaceRequiringEntity();
      final Function1<RequiredRole, Boolean> _function_3 = new Function1<RequiredRole, Boolean>() {
        @Override
        public Boolean apply(final RequiredRole el) {
          String _id = el.getId();
          OperationRequiredRole _innerRequiredRole_RequiredDelegationConnector = newValue.getInnerRequiredRole_RequiredDelegationConnector();
          String _id_1 = _innerRequiredRole_RequiredDelegationConnector.getId();
          return Boolean.valueOf(_id.equals(_id_1));
        }
      };
      RequiredRole _findFirst = IterableExtensions.<RequiredRole>findFirst(_requiredRoles_InterfaceRequiringEntity, _function_3);
      final OperationRequiredRole innerReqRole = ((OperationRequiredRole) _findFirst);
      connector.setInnerRequiredRole_RequiredDelegationConnector(innerReqRole);
      EList<RepositoryComponent> _components__Repository_1 = this.repo.getComponents__Repository();
      final Function1<RepositoryComponent, Boolean> _function_4 = new Function1<RepositoryComponent, Boolean>() {
        @Override
        public Boolean apply(final RepositoryComponent el) {
          String _id = el.getId();
          OperationRequiredRole _outerRequiredRole_RequiredDelegationConnector = newValue.getOuterRequiredRole_RequiredDelegationConnector();
          InterfaceRequiringEntity _requiringEntity_RequiredRole = _outerRequiredRole_RequiredDelegationConnector.getRequiringEntity_RequiredRole();
          String _id_1 = _requiringEntity_RequiredRole.getId();
          return Boolean.valueOf(_id.equals(_id_1));
        }
      };
      final RepositoryComponent outerComp = IterableExtensions.<RepositoryComponent>findFirst(_components__Repository_1, _function_4);
      EList<RequiredRole> _requiredRoles_InterfaceRequiringEntity_1 = outerComp.getRequiredRoles_InterfaceRequiringEntity();
      final Function1<RequiredRole, Boolean> _function_5 = new Function1<RequiredRole, Boolean>() {
        @Override
        public Boolean apply(final RequiredRole el) {
          String _id = el.getId();
          OperationRequiredRole _outerRequiredRole_RequiredDelegationConnector = newValue.getOuterRequiredRole_RequiredDelegationConnector();
          String _id_1 = _outerRequiredRole_RequiredDelegationConnector.getId();
          return Boolean.valueOf(_id.equals(_id_1));
        }
      };
      RequiredRole _findFirst_1 = IterableExtensions.<RequiredRole>findFirst(_requiredRoles_InterfaceRequiringEntity_1, _function_5);
      final OperationRequiredRole outerReqRole = ((OperationRequiredRole) _findFirst_1);
      connector.setOuterRequiredRole_RequiredDelegationConnector(outerReqRole);
      EList<Connector> _connectors__ComposedStructure = comp.getConnectors__ComposedStructure();
      _xblockexpression = _connectors__ComposedStructure.add(connector);
    }
    return _xblockexpression;
  }
  
  public boolean createAssemblyContext(final AssemblyContext newValue) {
    boolean _xblockexpression = false;
    {
      final AssemblyContext context = CompositionFactory.eINSTANCE.createAssemblyContext();
      this.setValues(context, newValue);
      EList<RepositoryComponent> _components__Repository = this.repo.getComponents__Repository();
      final Function1<RepositoryComponent, Boolean> _function = new Function1<RepositoryComponent, Boolean>() {
        @Override
        public Boolean apply(final RepositoryComponent el) {
          String _id = el.getId();
          RepositoryComponent _encapsulatedComponent__AssemblyContext = newValue.getEncapsulatedComponent__AssemblyContext();
          String _id_1 = _encapsulatedComponent__AssemblyContext.getId();
          return Boolean.valueOf(_id.equals(_id_1));
        }
      };
      final RepositoryComponent encapsComp = IterableExtensions.<RepositoryComponent>findFirst(_components__Repository, _function);
      context.setEncapsulatedComponent__AssemblyContext(encapsComp);
      EList<RepositoryComponent> _components__Repository_1 = this.repo.getComponents__Repository();
      final Function1<RepositoryComponent, Boolean> _function_1 = new Function1<RepositoryComponent, Boolean>() {
        @Override
        public Boolean apply(final RepositoryComponent el) {
          String _id = el.getId();
          ComposedStructure _parentStructure__AssemblyContext = newValue.getParentStructure__AssemblyContext();
          String _id_1 = _parentStructure__AssemblyContext.getId();
          return Boolean.valueOf(_id.equals(_id_1));
        }
      };
      RepositoryComponent _findFirst = IterableExtensions.<RepositoryComponent>findFirst(_components__Repository_1, _function_1);
      final ComposedProvidingRequiringEntity compEntity = ((ComposedProvidingRequiringEntity) _findFirst);
      EList<AssemblyContext> _assemblyContexts__ComposedStructure = compEntity.getAssemblyContexts__ComposedStructure();
      _xblockexpression = _assemblyContexts__ComposedStructure.add(context);
    }
    return _xblockexpression;
  }
  
  public boolean createInnerDeclaration(final InnerDeclaration newValue) {
    boolean _xblockexpression = false;
    {
      final InnerDeclaration dec = RepositoryFactory.eINSTANCE.createInnerDeclaration();
      String _entityName = newValue.getEntityName();
      dec.setEntityName(_entityName);
      DataType _datatype_InnerDeclaration = newValue.getDatatype_InnerDeclaration();
      if ((_datatype_InnerDeclaration instanceof CollectionDataType)) {
        DataType _datatype_InnerDeclaration_1 = newValue.getDatatype_InnerDeclaration();
        final String dataTypeId = this.findDataTypeId(((CollectionDataType) _datatype_InnerDeclaration_1));
        final Function1<CollectionDataType, Boolean> _function = new Function1<CollectionDataType, Boolean>() {
          @Override
          public Boolean apply(final CollectionDataType el) {
            String _id = el.getId();
            return Boolean.valueOf(_id.equals(dataTypeId));
          }
        };
        final CollectionDataType data = IterableExtensions.<CollectionDataType>findFirst(this.collectionDataTypes, _function);
        dec.setDatatype_InnerDeclaration(data);
      } else {
        DataType _datatype_InnerDeclaration_2 = newValue.getDatatype_InnerDeclaration();
        if ((_datatype_InnerDeclaration_2 instanceof CompositeDataType)) {
          DataType _datatype_InnerDeclaration_3 = newValue.getDatatype_InnerDeclaration();
          final String dataTypeId_1 = this.findDataTypeId(((CompositeDataType) _datatype_InnerDeclaration_3));
          final Function1<CompositeDataType, Boolean> _function_1 = new Function1<CompositeDataType, Boolean>() {
            @Override
            public Boolean apply(final CompositeDataType el) {
              String _id = el.getId();
              return Boolean.valueOf(_id.equals(dataTypeId_1));
            }
          };
          final CompositeDataType data_1 = IterableExtensions.<CompositeDataType>findFirst(this.compositeDataTypes, _function_1);
          dec.setDatatype_InnerDeclaration(data_1);
        } else {
          DataType _datatype_InnerDeclaration_4 = newValue.getDatatype_InnerDeclaration();
          dec.setDatatype_InnerDeclaration(_datatype_InnerDeclaration_4);
        }
      }
      final Function1<CompositeDataType, Boolean> _function_2 = new Function1<CompositeDataType, Boolean>() {
        @Override
        public Boolean apply(final CompositeDataType el) {
          String _id = el.getId();
          CompositeDataType _compositeDataType_InnerDeclaration = newValue.getCompositeDataType_InnerDeclaration();
          String _id_1 = _compositeDataType_InnerDeclaration.getId();
          return Boolean.valueOf(_id.equals(_id_1));
        }
      };
      final CompositeDataType parent = IterableExtensions.<CompositeDataType>findFirst(this.compositeDataTypes, _function_2);
      EList<InnerDeclaration> _innerDeclaration_CompositeDataType = parent.getInnerDeclaration_CompositeDataType();
      _xblockexpression = _innerDeclaration_CompositeDataType.add(dec);
    }
    return _xblockexpression;
  }
  
  public boolean createCollectionDataType(final CollectionDataType newValue) {
    boolean _xblockexpression = false;
    {
      final CollectionDataType data = RepositoryFactory.eINSTANCE.createCollectionDataType();
      this.setValues(data, newValue);
      DataType _innerType_CollectionDataType = newValue.getInnerType_CollectionDataType();
      if ((_innerType_CollectionDataType instanceof CollectionDataType)) {
        DataType _innerType_CollectionDataType_1 = newValue.getInnerType_CollectionDataType();
        final String dataTypeId = this.findDataTypeId(((CollectionDataType) _innerType_CollectionDataType_1));
        final Function1<CollectionDataType, Boolean> _function = new Function1<CollectionDataType, Boolean>() {
          @Override
          public Boolean apply(final CollectionDataType el) {
            String _id = el.getId();
            return Boolean.valueOf(_id.equals(dataTypeId));
          }
        };
        final CollectionDataType data1 = IterableExtensions.<CollectionDataType>findFirst(this.collectionDataTypes, _function);
        data.setInnerType_CollectionDataType(data1);
      } else {
        DataType _innerType_CollectionDataType_2 = newValue.getInnerType_CollectionDataType();
        if ((_innerType_CollectionDataType_2 instanceof CompositeDataType)) {
          DataType _innerType_CollectionDataType_3 = newValue.getInnerType_CollectionDataType();
          final String dataTypeId_1 = this.findDataTypeId(((CompositeDataType) _innerType_CollectionDataType_3));
          final Function1<CompositeDataType, Boolean> _function_1 = new Function1<CompositeDataType, Boolean>() {
            @Override
            public Boolean apply(final CompositeDataType el) {
              String _id = el.getId();
              return Boolean.valueOf(_id.equals(dataTypeId_1));
            }
          };
          final CompositeDataType data1_1 = IterableExtensions.<CompositeDataType>findFirst(this.compositeDataTypes, _function_1);
          data.setInnerType_CollectionDataType(data1_1);
        } else {
          DataType _innerType_CollectionDataType_4 = newValue.getInnerType_CollectionDataType();
          data.setInnerType_CollectionDataType(_innerType_CollectionDataType_4);
        }
      }
      EList<DataType> _dataTypes__Repository = this.repo.getDataTypes__Repository();
      _dataTypes__Repository.add(data);
      _xblockexpression = this.collectionDataTypes.add(data);
    }
    return _xblockexpression;
  }
  
  public boolean createCompositeDataType(final CompositeDataType newValue) {
    boolean _xblockexpression = false;
    {
      final CompositeDataType data = RepositoryFactory.eINSTANCE.createCompositeDataType();
      String _id = newValue.getId();
      data.setId(_id);
      String _entityName = newValue.getEntityName();
      data.setEntityName(_entityName);
      EList<DataType> _dataTypes__Repository = this.repo.getDataTypes__Repository();
      _dataTypes__Repository.add(data);
      _xblockexpression = this.compositeDataTypes.add(data);
    }
    return _xblockexpression;
  }
  
  public boolean createProvidedRole(final OperationProvidedRole newValue) {
    boolean _xblockexpression = false;
    {
      final OperationProvidedRole role = RepositoryFactory.eINSTANCE.createOperationProvidedRole();
      this.setValues(role, newValue);
      EList<Interface> _interfaces__Repository = this.repo.getInterfaces__Repository();
      final Function1<Interface, Boolean> _function = new Function1<Interface, Boolean>() {
        @Override
        public Boolean apply(final Interface iface) {
          String _id = iface.getId();
          OperationInterface _providedInterface__OperationProvidedRole = newValue.getProvidedInterface__OperationProvidedRole();
          String _id_1 = _providedInterface__OperationProvidedRole.getId();
          return Boolean.valueOf(_id.equals(_id_1));
        }
      };
      Interface _findFirst = IterableExtensions.<Interface>findFirst(_interfaces__Repository, _function);
      final OperationInterface opIf = ((OperationInterface) _findFirst);
      role.setProvidedInterface__OperationProvidedRole(opIf);
      EList<RepositoryComponent> _components__Repository = this.repo.getComponents__Repository();
      final Function1<RepositoryComponent, Boolean> _function_1 = new Function1<RepositoryComponent, Boolean>() {
        @Override
        public Boolean apply(final RepositoryComponent comp) {
          String _id = comp.getId();
          InterfaceProvidingEntity _providingEntity_ProvidedRole = newValue.getProvidingEntity_ProvidedRole();
          String _id_1 = _providingEntity_ProvidedRole.getId();
          return Boolean.valueOf(_id.equals(_id_1));
        }
      };
      final RepositoryComponent reqComp = IterableExtensions.<RepositoryComponent>findFirst(_components__Repository, _function_1);
      EList<ProvidedRole> _providedRoles_InterfaceProvidingEntity = reqComp.getProvidedRoles_InterfaceProvidingEntity();
      _xblockexpression = _providedRoles_InterfaceProvidingEntity.add(role);
    }
    return _xblockexpression;
  }
  
  public boolean createRequiredRole(final OperationRequiredRole newValue) {
    boolean _xblockexpression = false;
    {
      final OperationRequiredRole role = RepositoryFactory.eINSTANCE.createOperationRequiredRole();
      this.setValues(role, newValue);
      EList<Interface> _interfaces__Repository = this.repo.getInterfaces__Repository();
      final Function1<Interface, Boolean> _function = new Function1<Interface, Boolean>() {
        @Override
        public Boolean apply(final Interface iface) {
          String _id = iface.getId();
          OperationInterface _requiredInterface__OperationRequiredRole = newValue.getRequiredInterface__OperationRequiredRole();
          String _id_1 = _requiredInterface__OperationRequiredRole.getId();
          return Boolean.valueOf(_id.equals(_id_1));
        }
      };
      Interface _findFirst = IterableExtensions.<Interface>findFirst(_interfaces__Repository, _function);
      final OperationInterface opIf = ((OperationInterface) _findFirst);
      role.setRequiredInterface__OperationRequiredRole(opIf);
      EList<RepositoryComponent> _components__Repository = this.repo.getComponents__Repository();
      final Function1<RepositoryComponent, Boolean> _function_1 = new Function1<RepositoryComponent, Boolean>() {
        @Override
        public Boolean apply(final RepositoryComponent comp) {
          String _id = comp.getId();
          InterfaceRequiringEntity _requiringEntity_RequiredRole = newValue.getRequiringEntity_RequiredRole();
          String _id_1 = _requiringEntity_RequiredRole.getId();
          return Boolean.valueOf(_id.equals(_id_1));
        }
      };
      final RepositoryComponent reqComp = IterableExtensions.<RepositoryComponent>findFirst(_components__Repository, _function_1);
      EList<RequiredRole> _requiredRoles_InterfaceRequiringEntity = reqComp.getRequiredRoles_InterfaceRequiringEntity();
      _xblockexpression = _requiredRoles_InterfaceRequiringEntity.add(role);
    }
    return _xblockexpression;
  }
  
  public boolean createParameter(final Parameter newValue) {
    boolean _xblockexpression = false;
    {
      final Parameter par = RepositoryFactory.eINSTANCE.createParameter();
      String _entityName = newValue.getEntityName();
      par.setEntityName(_entityName);
      DataType _dataType__Parameter = newValue.getDataType__Parameter();
      if ((_dataType__Parameter instanceof CollectionDataType)) {
        DataType _dataType__Parameter_1 = newValue.getDataType__Parameter();
        final String dataTypeId = this.findDataTypeId(((CollectionDataType) _dataType__Parameter_1));
        final Function1<CollectionDataType, Boolean> _function = new Function1<CollectionDataType, Boolean>() {
          @Override
          public Boolean apply(final CollectionDataType el) {
            String _id = el.getId();
            return Boolean.valueOf(_id.equals(dataTypeId));
          }
        };
        final CollectionDataType data1 = IterableExtensions.<CollectionDataType>findFirst(this.collectionDataTypes, _function);
        par.setDataType__Parameter(data1);
      } else {
        DataType _dataType__Parameter_2 = newValue.getDataType__Parameter();
        if ((_dataType__Parameter_2 instanceof CompositeDataType)) {
          DataType _dataType__Parameter_3 = newValue.getDataType__Parameter();
          final String dataTypeId_1 = this.findDataTypeId(((CompositeDataType) _dataType__Parameter_3));
          final Function1<CompositeDataType, Boolean> _function_1 = new Function1<CompositeDataType, Boolean>() {
            @Override
            public Boolean apply(final CompositeDataType el) {
              String _id = el.getId();
              return Boolean.valueOf(_id.equals(dataTypeId_1));
            }
          };
          final CompositeDataType data1_1 = IterableExtensions.<CompositeDataType>findFirst(this.compositeDataTypes, _function_1);
          par.setDataType__Parameter(data1_1);
        } else {
          DataType _dataType__Parameter_4 = newValue.getDataType__Parameter();
          par.setDataType__Parameter(_dataType__Parameter_4);
        }
      }
      ParameterModifier _modifier__Parameter = newValue.getModifier__Parameter();
      par.setModifier__Parameter(_modifier__Parameter);
      final OperationSignature oldSig = newValue.getOperationSignature__Parameter();
      final OperationInterface oldIf = oldSig.getInterface__OperationSignature();
      EList<Interface> _interfaces__Repository = this.repo.getInterfaces__Repository();
      final Function1<Interface, Boolean> _function_2 = new Function1<Interface, Boolean>() {
        @Override
        public Boolean apply(final Interface iface2) {
          String _id = iface2.getId();
          String _id_1 = oldIf.getId();
          return Boolean.valueOf(_id.equals(_id_1));
        }
      };
      Interface _findFirst = IterableExtensions.<Interface>findFirst(_interfaces__Repository, _function_2);
      final OperationInterface opIf = ((OperationInterface) _findFirst);
      EList<OperationSignature> _signatures__OperationInterface = opIf.getSignatures__OperationInterface();
      final Function1<OperationSignature, Boolean> _function_3 = new Function1<OperationSignature, Boolean>() {
        @Override
        public Boolean apply(final OperationSignature sig2) {
          String _id = sig2.getId();
          String _id_1 = oldSig.getId();
          return Boolean.valueOf(_id.equals(_id_1));
        }
      };
      OperationSignature _findFirst_1 = IterableExtensions.<OperationSignature>findFirst(_signatures__OperationInterface, _function_3);
      final OperationSignature opSig = ((OperationSignature) _findFirst_1);
      EList<Parameter> _parameters__OperationSignature = opSig.getParameters__OperationSignature();
      _xblockexpression = _parameters__OperationSignature.add(par);
    }
    return _xblockexpression;
  }
  
  public boolean createSignature(final OperationSignature newValue) {
    boolean _xblockexpression = false;
    {
      final OperationSignature sig = RepositoryFactory.eINSTANCE.createOperationSignature();
      this.setValues(sig, newValue);
      DataType _returnType__OperationSignature = newValue.getReturnType__OperationSignature();
      if ((_returnType__OperationSignature instanceof CollectionDataType)) {
        DataType _returnType__OperationSignature_1 = newValue.getReturnType__OperationSignature();
        final String dataTypeId = this.findDataTypeId(((CollectionDataType) _returnType__OperationSignature_1));
        final Function1<CollectionDataType, Boolean> _function = new Function1<CollectionDataType, Boolean>() {
          @Override
          public Boolean apply(final CollectionDataType el) {
            String _id = el.getId();
            return Boolean.valueOf(_id.equals(dataTypeId));
          }
        };
        final CollectionDataType data1 = IterableExtensions.<CollectionDataType>findFirst(this.collectionDataTypes, _function);
        sig.setReturnType__OperationSignature(data1);
      } else {
        DataType _returnType__OperationSignature_2 = newValue.getReturnType__OperationSignature();
        if ((_returnType__OperationSignature_2 instanceof CompositeDataType)) {
          DataType _returnType__OperationSignature_3 = newValue.getReturnType__OperationSignature();
          final String dataTypeId_1 = this.findDataTypeId(((CompositeDataType) _returnType__OperationSignature_3));
          final Function1<CompositeDataType, Boolean> _function_1 = new Function1<CompositeDataType, Boolean>() {
            @Override
            public Boolean apply(final CompositeDataType el) {
              String _id = el.getId();
              return Boolean.valueOf(_id.equals(dataTypeId_1));
            }
          };
          final CompositeDataType data1_1 = IterableExtensions.<CompositeDataType>findFirst(this.compositeDataTypes, _function_1);
          sig.setReturnType__OperationSignature(data1_1);
        } else {
          DataType _returnType__OperationSignature_4 = newValue.getReturnType__OperationSignature();
          sig.setReturnType__OperationSignature(_returnType__OperationSignature_4);
        }
      }
      EList<Interface> _interfaces__Repository = this.repo.getInterfaces__Repository();
      final Function1<Interface, Boolean> _function_2 = new Function1<Interface, Boolean>() {
        @Override
        public Boolean apply(final Interface iface) {
          String _id = iface.getId();
          OperationInterface _interface__OperationSignature = newValue.getInterface__OperationSignature();
          String _id_1 = _interface__OperationSignature.getId();
          return Boolean.valueOf(_id.equals(_id_1));
        }
      };
      Interface _findFirst = IterableExtensions.<Interface>findFirst(_interfaces__Repository, _function_2);
      final OperationInterface opIf = ((OperationInterface) _findFirst);
      EList<OperationSignature> _signatures__OperationInterface = opIf.getSignatures__OperationInterface();
      _xblockexpression = _signatures__OperationInterface.add(sig);
    }
    return _xblockexpression;
  }
  
  public boolean createInterface(final Interface newValue) {
    boolean _xblockexpression = false;
    {
      final OperationInterface interf = RepositoryFactory.eINSTANCE.createOperationInterface();
      this.setValues(interf, newValue);
      EList<Interface> _parentInterfaces__Interface = newValue.getParentInterfaces__Interface();
      int _length = ((Object[])Conversions.unwrapArray(_parentInterfaces__Interface, Object.class)).length;
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _length, true);
      for (final Integer i : _doubleDotLessThan) {
        {
          EList<Interface> _parentInterfaces__Interface_1 = newValue.getParentInterfaces__Interface();
          Interface _get = _parentInterfaces__Interface_1.get((i).intValue());
          final String id = _get.getId();
          EList<Interface> _parentInterfaces__Interface_2 = interf.getParentInterfaces__Interface();
          EList<Interface> _interfaces__Repository = this.repo.getInterfaces__Repository();
          final Function1<Interface, Boolean> _function = new Function1<Interface, Boolean>() {
            @Override
            public Boolean apply(final Interface el) {
              String _id = el.getId();
              return Boolean.valueOf(_id.equals(id));
            }
          };
          Interface _findFirst = IterableExtensions.<Interface>findFirst(_interfaces__Repository, _function);
          _parentInterfaces__Interface_2.add(_findFirst);
        }
      }
      EList<Interface> _interfaces__Repository = this.repo.getInterfaces__Repository();
      _xblockexpression = _interfaces__Repository.add(interf);
    }
    return _xblockexpression;
  }
  
  public boolean createBasicComponent(final BasicComponent newValue) {
    boolean _xblockexpression = false;
    {
      final BasicComponent comp = RepositoryFactory.eINSTANCE.createBasicComponent();
      this.setValues(comp, newValue);
      EList<RepositoryComponent> _components__Repository = this.repo.getComponents__Repository();
      _xblockexpression = _components__Repository.add(comp);
    }
    return _xblockexpression;
  }
  
  public boolean createCompositeComponent(final CompositeComponent newValue) {
    boolean _xblockexpression = false;
    {
      final CompositeComponent comp = RepositoryFactory.eINSTANCE.createCompositeComponent();
      this.setValues(comp, newValue);
      EList<RepositoryComponent> _components__Repository = this.repo.getComponents__Repository();
      _components__Repository.add(comp);
      _xblockexpression = this.composedEntities.add(comp);
    }
    return _xblockexpression;
  }
  
  public void setValues(final Entity createdElement, final Entity newValue) {
    String _entityName = newValue.getEntityName();
    createdElement.setEntityName(_entityName);
    String _id = newValue.getId();
    createdElement.setId(_id);
  }
}
