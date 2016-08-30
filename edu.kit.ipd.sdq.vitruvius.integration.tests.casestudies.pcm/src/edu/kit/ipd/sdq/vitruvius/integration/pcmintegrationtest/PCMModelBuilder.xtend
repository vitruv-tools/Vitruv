package edu.kit.ipd.sdq.vitruvius.integration.pcmintegrationtest

import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList
import org.palladiosimulator.pcm.core.composition.AssemblyConnector
import org.palladiosimulator.pcm.core.composition.AssemblyContext
import org.palladiosimulator.pcm.core.composition.CompositionFactory
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity
import org.palladiosimulator.pcm.core.entity.Entity
import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.CompositeComponent
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.InnerDeclaration
import org.palladiosimulator.pcm.repository.Interface
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.OperationProvidedRole
import org.palladiosimulator.pcm.repository.OperationRequiredRole
import org.palladiosimulator.pcm.repository.OperationSignature
import org.palladiosimulator.pcm.repository.Parameter
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChange;
import edu.kit.ipd.sdq.vitruvius.framework.change.description.ConcreteChange;
import edu.kit.ipd.sdq.vitruvius.framework.change.description.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.root.InsertRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.feature.reference.InsertEReference;
import java.util.List
import org.eclipse.emf.ecore.EObject

class PCMModelBuilder { 
	
	private List<VitruviusChange> changes
	private EList<CompositeDataType> compositeDataTypes = new BasicEList<CompositeDataType>
	private EList<CollectionDataType> collectionDataTypes = new BasicEList<CollectionDataType>
	private EList<ComposedProvidingRequiringEntity> composedEntities = new BasicEList<ComposedProvidingRequiringEntity>
	private Repository repo
	
	int i
	
	new(List<VitruviusChange> changes) {
		this.changes = changes
		repo = RepositoryFactory.eINSTANCE.createRepository
		val repoChange = changes.get(0) as ConcreteChange
		val rootChange = repoChange.getEChanges.get(0) as InsertRootEObject<EObject>
		val oldRepo = rootChange.newValue as Repository
		repo.entityName = oldRepo.entityName
		repo.id = oldRepo.id
		repo.repositoryDescription = oldRepo.repositoryDescription
		changes.remove(0)
		//oldCompositeDataTypes.addAll(oldRepo.dataTypes__Repository.filter(typeof(CompositeDataType)))
		//oldCollectionDataTypes.addAll(oldRepo.dataTypes__Repository.filter(typeof(CollectionDataType)))
	}
	 
	
	public def createPCMModel() {
		
		changes.forEach[createModelElement]
		
		return repo
		
	}
	
	def createModelElement(VitruviusChange change) {
		
		switch change {
			CompositeChange: change.changes.forEach[createModelElement]
			ConcreteChange: change.createModelElementFromChange
		}
		
	}
	
	def findDataTypeId(CollectionDataType type) {
		val data = collectionDataTypes.findFirst[el|el.id.equals(type.id)]
		return data.id
	}
	
	def findDataTypeId(CompositeDataType type) {
		val data = compositeDataTypes.findFirst[el|el.id.equals(type.id)]
		return data.id
	}
	
	def createModelElementFromChange(ConcreteChange change) {
		
		val innerChange = change.getEChanges.get(0) as InsertEReference<EObject, EObject>
		val newValue = innerChange.newValue
		switch newValue {
			CompositeDataType : newValue.createCompositeDataType
			CollectionDataType : newValue.createCollectionDataType
			InnerDeclaration : newValue.createInnerDeclaration
			BasicComponent : newValue.createBasicComponent
			CompositeComponent : newValue.createCompositeComponent
			Interface : newValue.createInterface
			OperationSignature : newValue.createSignature
			Parameter : newValue.createParameter
			OperationRequiredRole : newValue.createRequiredRole
			OperationProvidedRole : newValue.createProvidedRole
			AssemblyContext : newValue.createAssemblyContext
			ProvidedDelegationConnector : newValue.createProvidedDelegationConnector
			RequiredDelegationConnector : newValue.createRequiredDelegationConnector
			AssemblyConnector : newValue.createAssemblyConnector	
		}
	}
	
	def createAssemblyConnector(AssemblyConnector newValue) {
		val connector = CompositionFactory.eINSTANCE.createAssemblyConnector
		connector.values = newValue
		
		// find providing context
		val comp = composedEntities.findFirst[el|el.id.equals(newValue.parentStructure__Connector.id)]
		val provContext = comp.assemblyContexts__ComposedStructure.findFirst[el|el.id.equals(newValue.providingAssemblyContext_AssemblyConnector.id)]
		connector.providingAssemblyContext_AssemblyConnector = provContext
		
		// find requiring context
		val reqContext = comp.assemblyContexts__ComposedStructure.findFirst[el|el.id.equals(newValue.requiringAssemblyContext_AssemblyConnector.id)]
		connector.requiringAssemblyContext_AssemblyConnector = reqContext
		
		// find providing role
		val provRole = provContext.encapsulatedComponent__AssemblyContext.providedRoles_InterfaceProvidingEntity.findFirst[el|el.id.equals(newValue.providedRole_AssemblyConnector.id)] as OperationProvidedRole
		connector.providedRole_AssemblyConnector = provRole
		
		// find requiring role
		val reqRole = reqContext.encapsulatedComponent__AssemblyContext.requiredRoles_InterfaceRequiringEntity.findFirst[el|el.id.equals(newValue.requiredRole_AssemblyConnector.id)] as OperationRequiredRole
		connector.requiredRole_AssemblyConnector = reqRole
		
		comp.connectors__ComposedStructure.add(connector)
	}
	
	def createProvidedDelegationConnector(ProvidedDelegationConnector newValue) {
		val connector = CompositionFactory.eINSTANCE.createProvidedDelegationConnector
		connector.values = newValue
		
		// find assembly context
		val comp = composedEntities.findFirst[el|el.id.equals(newValue.parentStructure__Connector.id)]
		val context = comp.assemblyContexts__ComposedStructure.findFirst[el|el.id.equals(newValue.assemblyContext_ProvidedDelegationConnector.id)]
		connector.assemblyContext_ProvidedDelegationConnector = context
		
		// find inner provided role
		val innerComp = repo.components__Repository.findFirst[
			el|el.id.equals(newValue.innerProvidedRole_ProvidedDelegationConnector.providingEntity_ProvidedRole.id)
		]	
		val innerProvRole = innerComp.providedRoles_InterfaceProvidingEntity.findFirst[
			el|el.id.equals(newValue.innerProvidedRole_ProvidedDelegationConnector.id)
		] as OperationProvidedRole
		connector.innerProvidedRole_ProvidedDelegationConnector = innerProvRole
		
		// find outer provided role
		val outerComp = repo.components__Repository.findFirst[
			el|el.id.equals(newValue.outerProvidedRole_ProvidedDelegationConnector.providingEntity_ProvidedRole.id)
		]	
		val outerProvRole = outerComp.providedRoles_InterfaceProvidingEntity.findFirst[
			el|el.id.equals(newValue.outerProvidedRole_ProvidedDelegationConnector.id)
		] as OperationProvidedRole
		connector.outerProvidedRole_ProvidedDelegationConnector = outerProvRole
		
		// add connector to component
		comp.connectors__ComposedStructure.add(connector)
	}
	
	def createRequiredDelegationConnector(RequiredDelegationConnector newValue) {
		val connector = CompositionFactory.eINSTANCE.createRequiredDelegationConnector
		connector.values = newValue
		
		// find assembly context
		val comp = composedEntities.findFirst[el|el.id.equals(newValue.parentStructure__Connector.id)]
		val context = comp.assemblyContexts__ComposedStructure.findFirst[el|el.id.equals(newValue.assemblyContext_RequiredDelegationConnector.id)]
		connector.assemblyContext_RequiredDelegationConnector = context
		
		// find inner Required role
		val innerComp = repo.components__Repository.findFirst[
			el|el.id.equals(newValue.innerRequiredRole_RequiredDelegationConnector.requiringEntity_RequiredRole.id)
		]	
		val innerReqRole = innerComp.requiredRoles_InterfaceRequiringEntity.findFirst[
			el|el.id.equals(newValue.innerRequiredRole_RequiredDelegationConnector.id)
		] as OperationRequiredRole
		connector.innerRequiredRole_RequiredDelegationConnector = innerReqRole
		
		// find outer Required role
		val outerComp = repo.components__Repository.findFirst[
			el|el.id.equals(newValue.outerRequiredRole_RequiredDelegationConnector.requiringEntity_RequiredRole.id)
		]	
		val outerReqRole = outerComp.requiredRoles_InterfaceRequiringEntity.findFirst[
			el|el.id.equals(newValue.outerRequiredRole_RequiredDelegationConnector.id)
		] as OperationRequiredRole
		connector.outerRequiredRole_RequiredDelegationConnector = outerReqRole
		
		comp.connectors__ComposedStructure.add(connector)
	}
	
	def createAssemblyContext(AssemblyContext newValue) {
		val context = CompositionFactory.eINSTANCE.createAssemblyContext
		context.values = newValue
		
		// find encapsulated component
		val encapsComp = repo.components__Repository.findFirst[el|el.id.equals(newValue.encapsulatedComponent__AssemblyContext.id)]
		context.encapsulatedComponent__AssemblyContext = encapsComp
		
		// find composite entity to add context to
		val compEntity = repo.components__Repository.findFirst[el|el.id.equals(newValue.getParentStructure__AssemblyContext.id)] as ComposedProvidingRequiringEntity
		compEntity.assemblyContexts__ComposedStructure.add(context)
	}
	
	def createInnerDeclaration(InnerDeclaration newValue) {
		val dec = RepositoryFactory.eINSTANCE.createInnerDeclaration
		dec.entityName = newValue.entityName
		// set datatype of inner declaration
		if (newValue.datatype_InnerDeclaration instanceof CollectionDataType) {
			val dataTypeId = findDataTypeId(newValue.datatype_InnerDeclaration as CollectionDataType)
			val data = collectionDataTypes.findFirst[el|el.id.equals(dataTypeId)]
			dec.datatype_InnerDeclaration = data
		}
		else if (newValue.datatype_InnerDeclaration instanceof CompositeDataType) {
			val dataTypeId = findDataTypeId(newValue.datatype_InnerDeclaration as CompositeDataType)
			val data = compositeDataTypes.findFirst[el|el.id.equals(dataTypeId)]
			dec.datatype_InnerDeclaration = data
		}
		else {
			dec.datatype_InnerDeclaration = newValue.datatype_InnerDeclaration
		}
		// place inner declaration in according composite data type
		val parent = compositeDataTypes.findFirst[el|el.id.equals(newValue.compositeDataType_InnerDeclaration.id)]
		parent.innerDeclaration_CompositeDataType.add(dec)
	}
	
	def createCollectionDataType(CollectionDataType newValue) {
		val data = RepositoryFactory.eINSTANCE.createCollectionDataType
		data.values = newValue	
		
	
		if (newValue.innerType_CollectionDataType instanceof CollectionDataType) {
			val dataTypeId = findDataTypeId(newValue.innerType_CollectionDataType as CollectionDataType)
			val data1 = collectionDataTypes.findFirst[el|el.id.equals(dataTypeId)]
			data.innerType_CollectionDataType = data1
		}
		else if (newValue.innerType_CollectionDataType instanceof CompositeDataType) {
			val dataTypeId = findDataTypeId(newValue.innerType_CollectionDataType as CompositeDataType)
			val data1 = compositeDataTypes.findFirst[el|el.id.equals(dataTypeId)]
			data.innerType_CollectionDataType = data1
		}
		else {
			data.innerType_CollectionDataType = newValue.innerType_CollectionDataType
		}

		repo.dataTypes__Repository.add(data)
		collectionDataTypes.add(data)
	}
	
	def createCompositeDataType(CompositeDataType newValue) {
		val data = RepositoryFactory.eINSTANCE.createCompositeDataType
		data.id = newValue.id
		data.entityName = newValue.entityName
		repo.dataTypes__Repository.add(data)
		compositeDataTypes.add(data)
	}
	
	def createProvidedRole(OperationProvidedRole newValue) {
		val role = RepositoryFactory.eINSTANCE.createOperationProvidedRole
		role.values = newValue
		val opIf = repo.interfaces__Repository.findFirst[iface|iface.id.equals(newValue.providedInterface__OperationProvidedRole.id)] as OperationInterface
		role.providedInterface__OperationProvidedRole = opIf
		val reqComp = repo.components__Repository.findFirst[comp|comp.id.equals(newValue.providingEntity_ProvidedRole.id)]
		reqComp.providedRoles_InterfaceProvidingEntity.add(role)
	}
	
	def createRequiredRole(OperationRequiredRole newValue) {
		val role = RepositoryFactory.eINSTANCE.createOperationRequiredRole
		role.values = newValue
		val opIf = repo.interfaces__Repository.findFirst[iface|iface.id.equals(newValue.requiredInterface__OperationRequiredRole.id)] as OperationInterface
		role.requiredInterface__OperationRequiredRole = opIf
		val reqComp = repo.components__Repository.findFirst[comp|comp.id.equals(newValue.requiringEntity_RequiredRole.id)]
		reqComp.requiredRoles_InterfaceRequiringEntity.add(role)
	}
	
	def createParameter(Parameter newValue) {
		val par = RepositoryFactory.eINSTANCE.createParameter
		par.entityName = newValue.entityName
		
		if (newValue.dataType__Parameter instanceof CollectionDataType) {
			val dataTypeId = findDataTypeId(newValue.dataType__Parameter as CollectionDataType)
			val data1 = collectionDataTypes.findFirst[el|el.id.equals(dataTypeId)]
			par.dataType__Parameter = data1
		}
		else if (newValue.dataType__Parameter instanceof CompositeDataType) {
			val dataTypeId = findDataTypeId(newValue.dataType__Parameter as CompositeDataType)
			val data1 = compositeDataTypes.findFirst[el|el.id.equals(dataTypeId)]
			par.dataType__Parameter = data1
		}
		else {
			par.dataType__Parameter = newValue.dataType__Parameter
		}
		
		par.modifier__Parameter = newValue.modifier__Parameter
		
		val oldSig = newValue.operationSignature__Parameter
		val oldIf = oldSig.interface__OperationSignature
		val opIf = repo.interfaces__Repository.findFirst[iface2|iface2.id.equals(oldIf.id)] as OperationInterface
		val opSig = opIf.signatures__OperationInterface.findFirst[sig2|sig2.id.equals(oldSig.id)] as OperationSignature
		opSig.parameters__OperationSignature.add(par)
	}
	
	def createSignature(OperationSignature newValue) {
		val sig = RepositoryFactory.eINSTANCE.createOperationSignature
		sig.values = newValue	
		
		if (newValue.returnType__OperationSignature instanceof CollectionDataType) {
			val dataTypeId = findDataTypeId(newValue.returnType__OperationSignature as CollectionDataType)
			val data1 = collectionDataTypes.findFirst[el|el.id.equals(dataTypeId)]
			sig.returnType__OperationSignature = data1
		}
		else if (newValue.returnType__OperationSignature instanceof CompositeDataType) {
			val dataTypeId = findDataTypeId(newValue.returnType__OperationSignature as CompositeDataType)
			val data1 = compositeDataTypes.findFirst[el|el.id.equals(dataTypeId)]
			sig.returnType__OperationSignature = data1
		}
		else {
			sig.returnType__OperationSignature = newValue.returnType__OperationSignature
		}
		
		val opIf = repo.interfaces__Repository.findFirst[iface|iface.id.equals(newValue.interface__OperationSignature.id)] as OperationInterface
		opIf.signatures__OperationInterface.add(sig)
	}
	
	def createInterface(Interface newValue) {
		val interf = RepositoryFactory.eINSTANCE.createOperationInterface
		interf.values = newValue
		
		// find parents
		for (i : 0 ..< newValue.parentInterfaces__Interface.length) {
    		val id = newValue.parentInterfaces__Interface.get(i).id
    		interf.parentInterfaces__Interface.add(repo.interfaces__Repository.findFirst[el|el.id.equals(id)])
		}
		
		repo.interfaces__Repository.add(interf)
	}
	
	def createBasicComponent(BasicComponent newValue) {
		val comp = RepositoryFactory.eINSTANCE.createBasicComponent
		comp.values = newValue
		repo.components__Repository.add(comp)
	}
	
	def createCompositeComponent(CompositeComponent newValue) {
		val comp = RepositoryFactory.eINSTANCE.createCompositeComponent
		comp.values = newValue
		repo.components__Repository.add(comp)
		composedEntities.add(comp)
	}
	
	def setValues(Entity createdElement, Entity newValue) {
        createdElement.entityName = newValue.entityName
        createdElement.id = newValue.id
    }
}