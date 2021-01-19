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
		changePropagationSpecifications = generator.generate(testProject,
			'IdentifiedWithAliases.commonality' -> '''
				concept test
				
				commonality IdentifiedWithAliases {
				
					with AllElementTypes:(Root in Resource) as "All"
					with AllElementTypes2:(Root2 in Resource)
					with PcmMockup:(Repository called "Repo" in Resource called "Res") as "Palladio"
					with UmlMockup:(UPackage called "Package" in Resource)
				
					has id {
						= All:Root.id
						= AllElementTypes2:Root2.id2
						= Palladio:Repo.id
						= UmlMockup:Package.id
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
				concept test
				
				commonality SubWithAliases {
				
					with AllElementTypes:NonRoot
					with AllElementTypes2:NonRoot2 called "SecondNonRoot"
					with PcmMockup:Component as "Palladio"
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
