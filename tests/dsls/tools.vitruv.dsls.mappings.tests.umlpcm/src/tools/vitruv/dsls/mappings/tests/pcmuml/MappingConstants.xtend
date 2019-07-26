package tools.vitruv.dsls.mappings.tests.pcmuml

import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.domains.pcm.PcmDomainProvider
import tools.vitruv.domains.uml.UmlDomainProvider
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.framework.domains.VitruvDomain

import static tools.vitruv.dsls.mappings.tests.pcmuml.PcmUmlClassHelper.*
import org.eclipse.uml2.uml.UMLFactory

class MappingConstants {

	final static String MAPPING_NAME = 'umlXpcmTest'

	public final static VitruvDomain leftDomain = new UmlDomainProvider().domain
	public final static VitruvDomain rightDomain = new PcmDomainProvider().domain

	public final static MappingParameter repository = createMappingParameter(
		RepositoryFactory.eINSTANCE.createRepository.eClass, 'repository')

	public final static MappingParameter rootPkg = createMappingParameter(UMLFactory.eINSTANCE.createPackage.eClass,
		'rootPkg')

	public final static MappingParameter repositoryPkg = createMappingParameter(
		UMLFactory.eINSTANCE.createPackage.eClass, 'repositoryPkg')

	public final static MappingParameter contractsPkg = createMappingParameter(
		UMLFactory.eINSTANCE.createPackage.eClass, 'contractsPkg')

	public final static MappingParameter datatypesPkg = createMappingParameter(
		UMLFactory.eINSTANCE.createPackage.eClass, 'datatypesPkg')

	public static String tagRepositoryXrootPkg
	public static String tagRepositoryXrepositoryPkg
	public static String tagRepositoryXcontractsPkg
	public static String tagRepositoryXdatatypesPkg
	

	static def init() {
		val tagging = new CorrespondenceTaggingHelper(MAPPING_NAME, leftDomain, rightDomain)
		tagRepositoryXrootPkg = tagging.getTag(repository, rootPkg)
		tagRepositoryXrepositoryPkg = tagging.getTag(repository, repositoryPkg)
		tagRepositoryXcontractsPkg = tagging.getTag(repository, contractsPkg)
		tagRepositoryXdatatypesPkg = tagging.getTag(repository, datatypesPkg)		
	}

}
