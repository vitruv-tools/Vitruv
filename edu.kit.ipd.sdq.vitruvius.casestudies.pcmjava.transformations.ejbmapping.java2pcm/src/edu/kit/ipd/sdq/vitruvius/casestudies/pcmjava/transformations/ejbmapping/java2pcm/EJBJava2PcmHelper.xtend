package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.ejbmapping.java2pcm

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.types.TypeReference
import org.palladiosimulator.pcm.repository.Repository
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.java2pcm.JaMoPP2PCMUtils

class EJBJava2PcmHelper {
	private new(){}
	
	public static def Classifier getClassifier(TypeReference typeReference){
		var classifier = JaMoPP2PCMUtils.getTargetClassifierFromImplementsReferenceAndNormalizeURI(typeReference)
		return classifier
	}
	
	public static def boolean  overridesInterfaceMethod(ClassMethod classMethod, Class jaMoPPClass){
		return null != getOoverridenInterfaceMethod(classMethod, jaMoPPClass) 
	}
	
	public static def getOoverridenInterfaceMethod(ClassMethod classMethod, Class jaMoPPClass){
		val implementedEJBInterfaces = jaMoPPClass.implements.map[it.classifier].filter(typeof(Interface)).filter[EJBAnnotationHelper.isEJBBuisnessInterface(it)]
		for(ejbInterface : implementedEJBInterfaces){
			val method = ejbInterface.methods.findFirst[JaMoPP2PCMUtils.hasSameSignature(it, classMethod)]
			if(null != method){
				return method
			}
		}
		return null
	} 
	
	public static def Repository findRepository(CorrespondenceInstance<Correspondence> correspondenceInstance){ 
		return JaMoPP2PCMUtils.getRepository(correspondenceInstance)
	}
	
}