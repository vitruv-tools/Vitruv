package tools.vitruv.applications.demo.insurancepersons.tests.insurance2persons

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.apache.log4j.Logger
import java.nio.file.Path
import tools.vitruv.domains.demo.persons.PersonsDomainProvider
import tools.vitruv.testutils.VitruvApplicationTest
import tools.vitruv.testutils.domains.DomainUtil
import tools.vitruv.applications.demo.insurancepersons.insurance2persons.InsuranceToPersonsChangePropagationSpecification
import tools.vitruv.domains.demo.insurance.InsuranceDomainProvider
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceFactory
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceDatabase
import edu.kit.ipd.sdq.metamodels.persons.PersonRegister
import edu.kit.ipd.sdq.metamodels.insurance.Gender
import edu.kit.ipd.sdq.metamodels.persons.PersonsFactory

import static org.hamcrest.CoreMatchers.*
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.testutils.matchers.ModelMatchers.*

class InsurancePersonsTest extends VitruvApplicationTest {
	static val logger = Logger.getLogger(InsurancePersonsTest)
	TestInfo testInfo = null
	static val MALE_NAME = "Max Mustermann"
	static val MALE_NAME_2 = "Bernd Mustermann"
	static val FEMALE_NAME = "Erika Mustermann"
	static val FEMALE_NAME_2 = "Berta Mustermann"
	static val FEMALE_NAME_3 = "Berta Musterfrau"
	// Model Paths
	final static Path PERSONS_MODEL = DomainUtil.getModelFileName('model/persons', new PersonsDomainProvider)
	final static Path INSURANCE_MODEL = DomainUtil.getModelFileName('model/insurance', new InsuranceDomainProvider)

	override protected getChangePropagationSpecifications() {
		return #[new InsuranceToPersonsChangePropagationSpecification()]
	}

	/**Before each test a new {@link InsuranceDatabase} is created as starting point.
	 * This is checked by several assertions to ensure correct preconditions for the tests. 
	 */
	@BeforeEach
	def void insertRegister(TestInfo testInfo) {
		this.testInfo = testInfo
		resourceAt(INSURANCE_MODEL).propagate[contents += InsuranceFactory.eINSTANCE.createInsuranceDatabase()]
		assertThat(resourceAt(PERSONS_MODEL), exists);
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(1, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(0, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);
	}
	
	
	/**Check if the actual {@link PersonRegister} looks like the expected one.
	 */
	def void assertCorrectPersonRegister(PersonRegister expectedPersonRegister) {
		val personModel = resourceAt(PERSONS_MODEL)
		assertThat(personModel, exists)
		assertEquals(1, personModel.contents.size)
		val personRegister = personModel.contents.get(0)
		assertThat(personRegister, instanceOf(PersonRegister))
		val PersonRegister castedPersonRegister = personRegister as PersonRegister
		assertThat(castedPersonRegister, equalsDeeply(expectedPersonRegister))
	}
	
	/**Check if the actual {@link InsuranceDatabase} looks like the expected one.
	 */
	def void assertCorrectInsuranceDatabase(InsuranceDatabase expectedInsuranceDatabase) {
		val insuranceModel = resourceAt(INSURANCE_MODEL)
		assertThat(insuranceModel, exists)
		assertEquals(1, insuranceModel.contents.size)
		val insuranceDatabase = insuranceModel.contents.get(0)
		assertThat(insuranceDatabase, instanceOf(InsuranceDatabase))
		val InsuranceDatabase castedInsuranceDatabase = insuranceDatabase as InsuranceDatabase
		assertThat(castedInsuranceDatabase, equalsDeeply(expectedInsuranceDatabase))
	}
	
	def void createClientsAndPropagate() {
		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [name = MALE_NAME gender = Gender.MALE]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [name = FEMALE_NAME gender = Gender.FEMALE]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [name = MALE_NAME_2 gender = Gender.MALE]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [name = FEMALE_NAME_2 gender = Gender.FEMALE]
		]
	}
	
	def void testExample() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		logger.trace(surroundingMethodName + " - preparation done")
		logger.trace(surroundingMethodName + " - propagation done")
		logger.trace(surroundingMethodName + " - finished without errors")
	}
	

	@Test
	def void testCreateInsuranceDatabase() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		assertThat(resourceAt(INSURANCE_MODEL), exists)
		assertThat(resourceAt(PERSONS_MODEL), exists)
		//TODO
		logger.trace(surroundingMethodName + " - finished without errors")
	}
	
	@Test
	def void testDeleteInsuranceDatabase() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		this.createClientsAndPropagate();
		logger.trace(surroundingMethodName + " - preparation done")
		resourceAt(INSURANCE_MODEL).propagate[contents.clear()]
		logger.trace(surroundingMethodName + " - propagation done")
		assertEquals(0, resourceAt(INSURANCE_MODEL).contents.size())
		assertEquals(0, resourceAt(PERSONS_MODEL).contents.size())
		assertThat(resourceAt(INSURANCE_MODEL), not(exists))
		assertThat(resourceAt(PERSONS_MODEL), not(exists))
		logger.trace(surroundingMethodName + " - finished without errors")
	}
	
	@Test
	def void testCreatedClient() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		logger.trace(surroundingMethodName + " - preparation done")
		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [name = MALE_NAME]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		
		val InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase => [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME
				// socialSecurityNumber = 000
				gender = Gender.MALE
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME]
		]
		
		assertCorrectInsuranceDatabase(expectedInsuranceDatabase)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}
	
	@Test
	def void testCreatedClient_multiple() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		
		logger.trace(surroundingMethodName + " - preparation done")
		
		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [name = MALE_NAME gender = Gender.MALE]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [name = FEMALE_NAME gender = Gender.FEMALE]
		]
		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [name = MALE_NAME_2 gender = Gender.MALE]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [name = FEMALE_NAME_2 gender = Gender.FEMALE]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		
		val InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase => [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME
				// socialSecurityNumber = 000
				gender = Gender.MALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = FEMALE_NAME
				// socialSecurityNumber = 000
				gender = Gender.FEMALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME_2
				// socialSecurityNumber = 000
				gender = Gender.MALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = FEMALE_NAME_2
				// socialSecurityNumber = 000
				gender = Gender.FEMALE
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME_2]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME_2]
		]
		
		assertCorrectInsuranceDatabase(expectedInsuranceDatabase)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}
	
	@Test
	def void testChangedName() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		
		createClientsAndPropagate()
		logger.trace(surroundingMethodName + " - preparation done")
		
		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			val searchedClient = insuranceclient.findFirst[x|x.name.equals(FEMALE_NAME_2)]
			searchedClient.name = FEMALE_NAME_3
		]
		logger.trace(surroundingMethodName + " - propagation done")
		
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME_2]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME_3]
		]
		val InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase => [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME
				// socialSecurityNumber = 000
				gender = Gender.MALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = FEMALE_NAME
				// socialSecurityNumber = 000
				gender = Gender.FEMALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME_2
				// socialSecurityNumber = 000
				gender = Gender.MALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = FEMALE_NAME_3
				// socialSecurityNumber = 000
				gender = Gender.FEMALE
			]
		]
		assertCorrectInsuranceDatabase(expectedInsuranceDatabase)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}
	
	@Test
	def void testChangedGender() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		
		createClientsAndPropagate()
		logger.trace(surroundingMethodName + " - preparation done")
		
		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			val searchedClient = insuranceclient.findFirst[x|x.gender.equals(Gender.MALE)]
			searchedClient.gender = Gender.FEMALE
		]
		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			val searchedClient = insuranceclient.findLast[x|x.gender.equals(Gender.FEMALE)]
			searchedClient.gender = Gender.MALE
		]
		logger.trace(surroundingMethodName + " - propagation done")
		
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = MALE_NAME]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME_2]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FEMALE_NAME_2]
		]
		val InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase => [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME
				// socialSecurityNumber = 000
				gender = Gender.FEMALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = FEMALE_NAME
				// socialSecurityNumber = 000
				gender = Gender.FEMALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME_2
				// socialSecurityNumber = 000
				gender = Gender.MALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = FEMALE_NAME_2
				// socialSecurityNumber = 000
				gender = Gender.MALE
			]
		]
		assertCorrectInsuranceDatabase(expectedInsuranceDatabase)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}
	
	@Test
	def void testDeletedClient_first_notOnly() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		
		createClientsAndPropagate()
		logger.trace(surroundingMethodName + " - preparation done")
		  
		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			val searchedClient = insuranceclient.findFirst[x|x.name.equals(MALE_NAME)]
			insuranceclient.remove(searchedClient)
		]
		logger.trace(surroundingMethodName + " - propagation done")
		
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME_2]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME_2]
		]
		val InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase => [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = FEMALE_NAME
				// socialSecurityNumber = 000
				gender = Gender.FEMALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME_2
				// socialSecurityNumber = 000
				gender = Gender.MALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = FEMALE_NAME_2
				// socialSecurityNumber = 000
				gender = Gender.FEMALE
			]
		]
		assertCorrectInsuranceDatabase(expectedInsuranceDatabase)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	@Test
	def void testDeletedClient_middle_notOnly() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		
		createClientsAndPropagate()
		logger.trace(surroundingMethodName + " - preparation done")
		  
		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			val searchedClient = insuranceclient.findFirst[x|x.name.equals(FEMALE_NAME)]
			insuranceclient.remove(searchedClient)
		]
		logger.trace(surroundingMethodName + " - propagation done")
		
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME_2]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME_2]
		]
		val InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase => [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME
				// socialSecurityNumber = 000
				gender = Gender.MALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME_2
				// socialSecurityNumber = 000
				gender = Gender.MALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = FEMALE_NAME_2
				// socialSecurityNumber = 000
				gender = Gender.FEMALE
			]
		]
		assertCorrectInsuranceDatabase(expectedInsuranceDatabase)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}
	
	@Test
	def void testDeletedClient_last_notOnly() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		
		createClientsAndPropagate()
		logger.trace(surroundingMethodName + " - preparation done")
		  
		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			val searchedClient = insuranceclient.findFirst[x|x.name.equals(FEMALE_NAME_2)]
			insuranceclient.remove(searchedClient)
		]
		logger.trace(surroundingMethodName + " - propagation done")
		
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FEMALE_NAME]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = MALE_NAME_2]
		]
		val InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase => [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME
				// socialSecurityNumber = 000
				gender = Gender.MALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = FEMALE_NAME
				// socialSecurityNumber = 000
				gender = Gender.FEMALE
			]
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [
				name = MALE_NAME_2
				// socialSecurityNumber = 000
				gender = Gender.MALE
				]
		]
		assertCorrectInsuranceDatabase(expectedInsuranceDatabase)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}
	
	@Test
	def void testDeletedClient_only() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		
		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			insuranceclient += InsuranceFactory.eINSTANCE.createInsuranceClient => [name = MALE_NAME gender = Gender.MALE]
		]
		logger.trace(surroundingMethodName + " - preparation done")
		
		InsuranceDatabase.from(INSURANCE_MODEL).propagate [
			val searchedClient = insuranceclient.findFirst[x|x.name.equals(MALE_NAME)]
			insuranceclient.remove(searchedClient)
		]
		logger.trace(surroundingMethodName + " - propagation done")
		
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
		]
		val InsuranceDatabase expectedInsuranceDatabase = InsuranceFactory.eINSTANCE.createInsuranceDatabase => [
		]
		
		assertCorrectInsuranceDatabase(expectedInsuranceDatabase)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}
}
	