package tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.java2pcm

import tools.vitruv.applications.pcmjava.util.java2pcm.Java2PcmPackagePreprocessor
import tools.vitruv.framework.change.processing.impl.CompositeChangeProcessor
import tools.vitruv.framework.util.datatypes.MetamodelPair
import org.emftext.language.java.JavaPackage
import org.palladiosimulator.pcm.PcmPackage
import tools.vitruv.framework.userinteraction.impl.UserInteractor

class JavaToPcmGplImplementationChangePropagationSpecification extends CompositeChangeProcessor {
	private MetamodelPair metamodelPair;
	
	new() {
		super(new UserInteractor())
		this.metamodelPair = new MetamodelPair(JavaPackage.eNS_URI, PcmPackage.eNS_URI);
		setup()
	}

	def protected setup() {
		addChangePreprocessor(new Java2PcmPackagePreprocessor(userInteracting));
		addChangeMainprocessor(new Java2PCMChangeProcessor(userInteracting));
	}
	
	override getMetamodelPair() {
		return metamodelPair;
	}
	
}
