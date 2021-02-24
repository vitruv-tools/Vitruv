package tools.vitruv.dsls.commonalities.tests.execution

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.BeforeAll
import tools.vitruv.testutils.TestProject
import java.nio.file.Path

/**
 * Inherits the Identified execution tests, but uses the modified commonalities
 * files located in this package.
 * <p>
 * TODO: Expand these tests to make use of more complex conditions and containment hierarchies.
 */
@DisplayName('executing commonalities with conditions')
class ConditionsExecutionTest extends IdentifiedExecutionTest {
	@BeforeAll
	override generate(@TestProject Path testProject) {
		changePropagationSpecifications = generator.generate(testProject,
			'IdentifiedWithConditions.commonality' -> '''
				concept test
				
				commonality IdentifiedWithConditions {
				
					with AllElementTypes:(Root, Resource)
					whereat {
						Root in Resource
					}
					with AllElementTypes2:(Root2, Resource)
					whereat {
						Root2 in Resource
					}
					with PcmMockup:(Repository, Resource)
					whereat {
						Repository in Resource
					}
					with UmlMockup:(UPackage, Resource)
					whereat {
						UPackage in Resource
					}
				
					has id {
						= AllElementTypes:Root.id
						= AllElementTypes2:Root2.id2
						= PcmMockup:Repository.name
						= UmlMockup:UPackage.name
						-> AllElementTypes:Resource.name
						-> AllElementTypes2:Resource.name
						-> PcmMockup:Resource.name
						-> UmlMockup:Resource.name
					}
				
					has number {
						= AllElementTypes:Root.singleValuedEAttribute
						= AllElementTypes2:Root2.singleValuedEAttribute2
					}
				
					has numberList {
						= AllElementTypes:Root.multiValuedEAttribute
						= AllElementTypes2:Root2.multiValuedEAttribute2
					}
				
					has sub referencing test:SubIdentified {
						= AllElementTypes:Root.multiValuedContainmentEReference
						= AllElementTypes2:Root2.multiValuedContainmentEReference2
						= PcmMockup:Repository.components
						= UmlMockup:UPackage.classes
					}
				}
			''',
			'SubIdentified.commonality' -> '''
				concept test
				
				commonality SubIdentified {
					with AllElementTypes:NonRoot
					with AllElementTypes2:NonRoot2
					with PcmMockup:Component
					with UmlMockup:UClass
				
					has name {
						= AllElementTypes:NonRoot.id
						= AllElementTypes2:NonRoot2.id2
						= PcmMockup:Component.name
						= UmlMockup:UClass.name
					}
				}	
			'''
		)
	}	
}
