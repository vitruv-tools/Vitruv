package tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.pcm2java

import tools.vitruv.framework.change.processing.impl.CompositeChangeProcessor
import org.palladiosimulator.pcm.PcmPackage
import org.emftext.language.java.JavaPackage
import tools.vitruv.framework.util.datatypes.MetamodelPair
import tools.vitruv.framework.userinteraction.impl.UserInteractor

class Pcm2JavaChangePropagationSpecification extends CompositeChangeProcessor {
	private MetamodelPair metamodelPair;
	
	new() {
		super(new UserInteractor())
		this.metamodelPair = new MetamodelPair(PcmPackage.eNS_URI, JavaPackage.eNS_URI);
		setup();
	}

	def protected setup() {
		addChangeMainprocessor(new Pcm2JavaChangeProcessor(userInteracting));
	}
	
	override getMetamodelPair() {
		return metamodelPair;
	}
	
}
