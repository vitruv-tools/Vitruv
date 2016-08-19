package edu.kit.ipd.sdq.vitruvius.codeintegration.change2command

import edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration.IntegrationCorrespondence
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.UserInteractionType
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge
import java.util.ArrayList
import java.util.HashSet
import java.util.List
import java.util.Set
import java.util.concurrent.Callable
import mir.responses.responsesJavaTo5_1.rename.ExecutorJavaTo5_1
import org.eclipse.emf.common.command.Command
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.containers.CompilationUnit

import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*
import org.palladiosimulator.pcm.core.entity.NamedElement
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.InsertRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.InsertEReference
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.RemoveRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeatureEChange

class IntegrationChange2CommandTransformer {
	
	private UserInteracting userInteracting
	
	new(UserInteracting userInteracting) {
		this.userInteracting = userInteracting
	}
	
	def compute(EChange change, Blackboard blackboard) {
		val commands = createCommandsList(change, blackboard)
		return new IntegrationChange2CommandResult(commands)
	}
	
	private def createCommandsList(EChange change, Blackboard blackboard) {
		// Since all correspondences are considered (not only IntegrationCorrespondences),
		// only return response commands, if one of the other 2 checks are successful
		val responseCommands = createResponseCommands(change, blackboard)
		val newClassOrInterfaceInIntegratedAreaCommand = createNewClassOrInterfaceInIntegratedAreaCommand(
			change, blackboard)
    	if (newClassOrInterfaceInIntegratedAreaCommand != null) {
    		if (responseCommands != null) {
				return responseCommands
			}
    		val commands = newClassOrInterfaceInIntegratedAreaCommand.toList as List<? extends Command>
    		return commands
    	}
    	val defaultIntegrationChangeCommand = getDefaultIntegrationChangeCommand(change, blackboard)
    	if (defaultIntegrationChangeCommand != null) {
    		if (responseCommands != null) {
				return responseCommands
			}
    		val commands = defaultIntegrationChangeCommand.toList as List<? extends Command>
    		return commands
    	}
    	return new ArrayList()
	}
	
	def createResponseCommands(EChange change, Blackboard blackboard) {
		val executor = new ExecutorJavaTo5_1(userInteracting)
		val commands = executor.generateCommandsForEvent(change, blackboard.correspondenceModel)
		if (commands != null && commands.size > 0) {
			return commands
		}
		return null
	}
	
	private def createNewClassOrInterfaceInIntegratedAreaCommand(EChange eChange, Blackboard blackboard) {
        if (eChange instanceof InsertEReference<?,?> && (eChange as InsertEReference<?,?>).isContainment()) { 
        	//Check if this is a creation of a class or interface on file level.
        	//In this case we need to check if any siblings in the package have been integrated
        	val change = eChange as InsertEReference<?,?>
        	val classOfAffected = change.getAffectedEObject().eClass().getInstanceClass()
        	val classOfCreated = change.getNewValue().eClass().getInstanceClass()
        	if (classOfAffected == typeof(CompilationUnit) && 
        			(classOfCreated.equals(typeof(Class)) || 
        			 classOfCreated.equals(typeof(Interface)))) {
        		val cu = change.getAffectedEObject() as CompilationUnit
        		//TODO use IntegrationCorrespondence view of InternalCorrespondenceInstance which is
        		//statically typed to Correspondence right now and needs to support views like 
        		//CorrespondenceInstance
                val ci = blackboard.correspondenceModel
                val newCompilationUnitTuid = ci.calculateTUIDFromEObject(cu)
                val packagePartOfNewTuid = getPackagePart(newCompilationUnitTuid)
    			for (Correspondence corr : ci.getAllCorrespondences()) {
    				if (corr instanceof IntegrationCorrespondence) {
    					val integrationCorr = corr as IntegrationCorrespondence
	    				if (integrationCorr.isCreatedByIntegration()) {
	    					val allTUIDs = new ArrayList<TUID>()
	    					allTUIDs.addAll(corr.getATUIDs())
	    					allTUIDs.addAll(corr.getBTUIDs())
	    					for (TUID tuid : allTUIDs) {
	    						val packagePart = getPackagePart(tuid)
	    						if (packagePartOfNewTuid.startsWith(packagePart)) {
	    							val command = EMFCommandBridge.createVitruviusTransformationRecordingCommand(new Callable<TransformationResult>() {
										override call() throws Exception {
											showNewTypeInIntegratedAreaDialog()
											return new TransformationResult()
										}
	    							})
	    							return command
	    						}
	    					}
	    				}
    				}
    			}
        	}
    	} 
        return null
	}
	
	private def String getPackagePart(TUID newCompilationUnitTuid) {
		val originalTuidAsString = newCompilationUnitTuid.toString()
		val lastSlashIndex = originalTuidAsString.lastIndexOf("/")
		return originalTuidAsString.substring(0, lastSlashIndex)
	}
	
	private def showNewTypeInIntegratedAreaDialog() {
		val buffer = new StringBuffer()
		buffer.append("Created class or interface in area with integrated objects.\n")
		buffer.append("Please handle consistency manually.")
		userInteracting.showMessage(UserInteractionType.MODAL, buffer.toString())
	}
	
	private def getDefaultIntegrationChangeCommand(EChange eChange, Blackboard blackboard) {
        val correspondingIntegratedEObjects = getCorrespondingEObjectsIfIntegrated(eChange, blackboard)
        if (correspondingIntegratedEObjects != null) {
	    	val buffer = new StringBuffer()
	    	buffer.append("Elements in change were integrated into Vitruvius.\n")
	    	buffer.append("Please fix manually. Corresponding object(s):\n")
	    	for (EObject obj : correspondingIntegratedEObjects) {
	    		val name = getReadableName(obj)
	    		buffer.append("\n")
	    		buffer.append(name)
	    	}
			val command = EMFCommandBridge.createVitruviusTransformationRecordingCommand(
				new Callable<TransformationResult>() {
					override call() throws Exception {
						userInteracting.showMessage(UserInteractionType.MODAL, buffer.toString())
						return new TransformationResult()
					}
				})
	    	return command
        } 
        return null
	}
	
	private def getReadableName(EObject obj) {
    	var name = getDirectNameIfNamed(obj)
		val className = obj.eClass().getName()
    	var container = obj.eContainer()
    	while (name == null) {
    		if (container == null) {
    			name = className
    		} else {
    			val containerName = getDirectNameIfNamed(container)
    			if (containerName != null) {
    				val containerClassName = container.eClass().getName()
					name = className + " in " + containerClassName + ": " + containerName
    			} else {
    				container = container.eContainer()
    			}
    		}
    	}
		return name
	}

	private def getDirectNameIfNamed(EObject obj) {
		var String name = null
		val className = obj.eClass().getName()
		if (obj instanceof NamedElement) {
			val named = obj as NamedElement
			name =  className + ": " + named.getEntityName()
		} else if (obj instanceof org.emftext.language.java.commons.NamedElement) {
			val named = obj as org.emftext.language.java.commons.NamedElement
			name =  className + ": " + named.getName()
		}
		return name
	}

	/**
     * 
     * @return set of corresponding EObjects if integrated, else null
     */
    private def getCorrespondingEObjectsIfIntegrated(EChange eChange,
            Blackboard blackboard) {
        val ci = blackboard.correspondenceModel
        var EObject eObj = null
        if (eChange instanceof FeatureEChange<?,?>) {
            eObj = eChange.getAffectedEObject()
        } else if (eChange instanceof InsertRootEObject<?>) {
            eObj = eChange.getNewValue()
        } else if (eChange instanceof RemoveRootEObject<?>) {
            eObj = eChange.getOldValue()
        }

        val  integrationView = ci.getView(typeof(IntegrationCorrespondence))
        if (eObj != null) {
            val set = CollectionBridge.toSet(eObj)
            val Set<IntegrationCorrespondence> correspondences = integrationView
                    .getCorrespondencesThatInvolveAtLeast(set)
            val correspondingObjects = new HashSet<EObject>()
            for (integratedCorrespondence : correspondences) {
                if (integratedCorrespondence.isCreatedByIntegration()) {
                	val aList = integratedCorrespondence.getAs()
                	val bList = integratedCorrespondence.getAs()
                	if (aList.contains(eObj)) {
                		correspondingObjects.addAll(bList)
                	} else {
                		correspondingObjects.addAll(aList)
                	}
                	return correspondingObjects
                }
            }
        }
        return null
    }
	
}