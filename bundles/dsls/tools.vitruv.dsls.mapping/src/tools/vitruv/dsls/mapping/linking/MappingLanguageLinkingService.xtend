package tools.vitruv.dsls.mapping.linking

import com.google.inject.Inject
import java.util.Collections
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.xtext.conversion.IValueConverterService
import org.eclipse.xtext.conversion.ValueConverterException
import org.eclipse.xtext.linking.impl.DefaultLinkingService
import org.eclipse.xtext.linking.impl.IllegalNodeException
import org.eclipse.xtext.nodemodel.ILeafNode
import org.eclipse.xtext.nodemodel.INode

/**
 * Used to enable refering to packages by their metamodel namespace URI.
 * <p>
 * Uses <code>EPackage.Registry</code> to deresolve URIs.
 */
class MappingLanguageLinkingService extends DefaultLinkingService {
	private static final Logger log = Logger.getLogger(typeof(MappingLanguageLinkingService));
	
	@Inject
	private IValueConverterService valueConverterService;
	
	override getLinkedObjects(EObject context, EReference ref, INode node) throws IllegalNodeException {
		if (ref.EType.equals(EcorePackage.Literals.EPACKAGE))
			return getEPackage(node as ILeafNode)

		super.getLinkedObjects(context, ref, node)
	}
	
	/**
	 * from org.eclipse.xtext.xtext.XtextLinkingService.getPackage(ReferencedMetamodel, ILeafNode)
	 */
	def List<EObject> getEPackage(ILeafNode text) {
		val nsUri = getMetamodelNsURI(text);
		if (nsUri === null)
			return Collections.emptyList();
			
		val resolvedEPackage = EPackage.Registry.INSTANCE.getEPackage(nsUri) as EObject
		if (resolvedEPackage === null)	
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