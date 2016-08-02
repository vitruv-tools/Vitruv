package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjavaejb.responses.ejbjava2pcm

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.JaMoPP2PCMUtils
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.types.TypeReference
import org.palladiosimulator.pcm.repository.Repository
import org.emftext.language.java.classifiers.Interface

class EJBJava2PcmHelper {
	private new(){}
	
	public static def Classifier getClassifier(TypeReference typeReference){
		return JaMoPP2PCMUtils.getTargetClassifierFromTypeReference(typeReference)
	}
	
	public static def boolean overridesInterfaceMethod(ClassMethod classMethod, Class jaMoPPClass){
		val implementedEJBInterfaces = jaMoPPClass.implements.map[it.classifier].filter(typeof(Interface)).filter[EJBAnnotationHelper.isEJBBuisnessInterface(it)]
		for(ejbInterface : implementedEJBInterfaces){
			val method = ejbInterface.methods.findFirst[EqualityChecker.areFunctionsEqual(it, classMethod)]
			if(null != method){
				return true
			}
		}
		return false
	} 
	
	public static def Repository findRepository(CorrespondenceInstance<Correspondence> correspondenceInstance){ 
		return JaMoPP2PCMUtils.getRepository(correspondenceInstance)
	}
	
}