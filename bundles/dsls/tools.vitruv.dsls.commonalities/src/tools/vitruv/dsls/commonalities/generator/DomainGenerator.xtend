package tools.vitruv.dsls.commonalities.generator

import tools.vitruv.domains.emf.builder.VitruviusEmfBuilderApplicator
import tools.vitruv.dsls.common.helper.JavaImportHelper
import tools.vitruv.extensions.dslruntime.commonalities.IntermediateVitruvDomain
import tools.vitruv.framework.domains.VitruvDomainProvider
import tools.vitruv.framework.domains.VitruviusProjectBuilderApplicator

import static extension tools.vitruv.dsls.common.helper.JavaFileGenerator.*
import static extension tools.vitruv.dsls.commonalities.generator.GeneratorConstants.*

package class DomainGenerator extends SubGenerator {
	
	override generate() {
		generatedConcepts.forEach [
			generateDomain()
			generateDomainProvider()
		]
	}

	def private generateDomain(String conceptName) {
		extension val importHelper = new JavaImportHelper
		val domainClass = conceptName.conceptDomainClass
		
		fsa.generateFile(domainClass.javaFilePath, '''
			public class «domainClass.simpleName» extends «IntermediateVitruvDomain.typeRef» {
				
				public «domainClass.simpleName»() {
					super("«conceptName.conceptDomainName»", «'''«conceptName.intermediateModelClassesPrefix»Package'''.typeRef».eINSTANCE, null);
				}
				
				@«Override.typeRef»
				public «VitruviusProjectBuilderApplicator.typeRef» getBuilderApplicator() {
					return new «VitruviusEmfBuilderApplicator.typeRef»();
				}
			}
		'''.generateClass(domainClass.packageName, importHelper))
	}

	def private generateDomainProvider(String conceptName) {
		extension val importHelper = new JavaImportHelper
		val domainProvider = conceptName.conceptDomainProvider
		val domainClass = conceptName.conceptDomainClass
		
		fsa.generateFile(domainProvider.javaFilePath, '''
			public class «domainProvider.simpleName» implements «VitruvDomainProvider.typeRef»<«domainClass.typeRef»> {
				private static «domainClass.typeRef» instance;
				
				@«Override.typeRef»
				public synchronized «domainClass.typeRef» getDomain() {
					if (instance == null) {
						instance = new «domainClass.typeRef»();
					}
					return instance;
				}
			}
		'''.generateClass(domainProvider.packageName, importHelper))
	}

}
