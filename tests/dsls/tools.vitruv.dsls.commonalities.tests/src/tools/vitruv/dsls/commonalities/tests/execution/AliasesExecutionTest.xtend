package tools.vitruv.dsls.commonalities.tests.execution

import org.junit.jupiter.api.DisplayName
import java.nio.file.Path
import tools.vitruv.testutils.TestProject
import org.junit.jupiter.api.BeforeAll

/**
 * Inherits the Identified execution tests, but uses modified commonalities that use aliases
 */
@DisplayName('executing commonalities with aliases')
class AliasesExecutionTest extends IdentifiedExecutionTest {
	@BeforeAll
	override generate(@TestProject Path testProject) {
		generator.generate(testProject,
			'IdentifiedWithAliases.commonality' -> '''
				import "http://tools.vitruv.testutils.metamodels.allElementTypes" as AllElementTypes
				import "http://tools.vitruv.testutils.metamodels.allElementTypes2" as AllElementTypes2
				import "http://tools.vitruv.testutils.metamodels.pcm_mockup" as PcmMockup
				import "http://tools.vitruv.testutils.metamodels.uml_mockup" as UmlMockup
				
				concept test
				
				commonality IdentifiedWithAliases {
				
					with (AllElementTypes as All):(Root in Resource)
					with AllElementTypes2:(Root2 in Resource)
					with (PcmMockup as Palladio):((Repository as Repo) in (Resource as Res))
					with UmlMockup:((UPackage as Package) in Resource)
				
					has id {
						= All:Root.id
						= AllElementTypes2:Root2.id2
						= Palladio:Repo.name
						= UmlMockup:Package.name
						-> All:Resource.name
						-> AllElementTypes2:Resource.name
						-> Palladio:Res.name
						-> UmlMockup:Resource.name
					}
				
					has number {
						= All:Root.singleValuedEAttribute
						= AllElementTypes2:Root2.singleValuedEAttribute2
					}
				
					has numberList {
						= All:Root.multiValuedEAttribute
						= AllElementTypes2:Root2.multiValuedEAttribute2
					}
				
					has sub referencing test:SubWithAliases {
						= All:Root.multiValuedContainmentEReference
						= AllElementTypes2:Root2.multiValuedContainmentEReference2
						= Palladio:Repo.components
						= UmlMockup:Package.classes
					}
				}
			''',
			'SubWithAliases.commonality' -> '''
				import "http://tools.vitruv.testutils.metamodels.allElementTypes" as AllElementTypes
				import "http://tools.vitruv.testutils.metamodels.allElementTypes2" as AllElementTypes2
				import "http://tools.vitruv.testutils.metamodels.pcm_mockup" as PcmMockup
				import "http://tools.vitruv.testutils.metamodels.uml_mockup" as UmlMockup
				
				concept test
				
				commonality SubWithAliases {
				
					with AllElementTypes:NonRoot
					with AllElementTypes2:(NonRoot2 as SecondNonRoot)
					with (PcmMockup as Palladio):Component
					with UmlMockup:UClass
				
					has name {
						= AllElementTypes:NonRoot.id
						= AllElementTypes2:SecondNonRoot.id2
						= Palladio:Component.name
						= UmlMockup:UClass.name
					}
				}
			'''
		)
	}
}
