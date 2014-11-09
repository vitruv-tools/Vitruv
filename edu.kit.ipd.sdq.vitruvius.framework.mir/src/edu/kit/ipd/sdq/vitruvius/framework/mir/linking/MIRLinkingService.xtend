package edu.kit.ipd.sdq.vitruvius.framework.mir.linking

import com.google.inject.Inject
import java.util.Collections
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.conversion.IValueConverterService
import org.eclipse.xtext.conversion.ValueConverterException
import org.eclipse.xtext.linking.impl.DefaultLinkingService
import org.eclipse.xtext.linking.impl.IllegalNodeException
import org.eclipse.xtext.nodemodel.ILeafNode
import org.eclipse.xtext.nodemodel.INode
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EPackage
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Import

class MIRLinkingService extends DefaultLinkingService {
	private static final Logger log = Logger.getLogger(typeof(edu.kit.ipd.sdq.vitruvius.framework.mir.linking.MIRLinkingService));
	
	@Inject
	private IValueConverterService valueConverterService;
	
	override getLinkedObjects(EObject context, EReference ref, INode node) throws IllegalNodeException {
		if (ref == MIRPackage.eINSTANCE.import_Package && context instanceof Import)
			return getEPackage(context as Import, node as ILeafNode)

		super.getLinkedObjects(context, ref, node)
	}
	
	/**
	 * from org.eclipse.xtext.xtext.XtextLinkingService.getPackage(ReferencedMetamodel, ILeafNode)
	 */
	def List<EObject> getEPackage(Import context, ILeafNode text) {
		val nsUri = getMetamodelNsURI(text);
		if (nsUri == null)
			return Collections.emptyList();
			
		val resolvedEPackage = EPackage.Registry.INSTANCE.getEPackage(nsUri) as EObject
		if (resolvedEPackage == null)	
			return Collections.emptyList();
			
		return #[resolvedEPackage]
	}

	/**
	 * from org.eclipse.xtext.xtext.XtextLinkingService.getMetamodelNsURI(ILeafNode)
	 */
	def private String getMetamodelNsURI(ILeafNode text) {
		try {
			return valueConverterService.toValue(text.getText(), getLinkingHelper().getRuleNameFrom(text
					.getGrammarElement()), text) as String;
		} catch (ValueConverterException e) {
			log.debug("Exception on leaf '" + text.getText() + "'", e);
			return null;
		}
	}

}