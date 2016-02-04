package edu.kit.ipd.sdq.vitruvius.dsls.mirbase.metamodelinferrer;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.xtext.GeneratedMetamodel;
import org.eclipse.xtext.xtext.ecoreInference.IXtext2EcorePostProcessor;

@SuppressWarnings("restriction")
public class MirBaseXtext2EcorePostProcessor implements IXtext2EcorePostProcessor {
	@Override
	public void process(GeneratedMetamodel metamodel) {
		if (!metamodel.getName().equals("mirBase"))
			return;
		
		final EPackage ePackage = metamodel.getEPackage();
		final EClass mirBaseFile = EcoreFactory.eINSTANCE.createEClass();
		mirBaseFile.setName("MirBaseFile");
		mirBaseFile.setAbstract(true);
		ePackage.getEClassifiers().add(mirBaseFile);
	}
}