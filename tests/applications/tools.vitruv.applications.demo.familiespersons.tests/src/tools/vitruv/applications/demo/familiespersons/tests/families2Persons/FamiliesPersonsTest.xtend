package tools.vitruv.applications.demo.familiespersons.tests.families2Persons

import org.hamcrest.MatcherAssert;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.core.*
import org.hamcrest.*
import org.hamcrest.org.hamcrest.*;

import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.containsInAnyOrder;


import static org.hamcrest.CoreMatchers.hasItem
import tools.vitruv.testutils.matchers.ModelMatchers;
import static tools.vitruv.testutils.matchers.ModelMatchers.*
import static tools.vitruv.testutils.matchers.ModelMatchers.containsModelOf
import static tools.vitruv.testutils.matchers.ModelMatchers.equalsDeeply
import static org.hamcrest.CoreMatchers.*
import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.instanceOf
import static org.hamcrest.CoreMatchers.notNullValue

import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister
import edu.kit.ipd.sdq.metamodels.families.impl.FamilyRegisterImpl
import edu.kit.ipd.sdq.metamodels.persons.PersonsFactory
import edu.kit.ipd.sdq.metamodels.persons.impl.FemaleImpl
import edu.kit.ipd.sdq.metamodels.persons.impl.MaleImpl
import edu.kit.ipd.sdq.metamodels.persons.impl.PersonImpl
import edu.kit.ipd.sdq.metamodels.persons.impl.PersonRegisterImpl
import java.nio.file.Path
import org.apache.log4j.ConsoleAppender
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.apache.log4j.SimpleLayout
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.vitruv.applications.demo.familiespersons.families2persons.FamiliesToPersonsChangePropagationSpecification
import tools.vitruv.domains.demo.families.FamiliesDomainProvider
import tools.vitruv.domains.demo.persons.PersonsDomainProvider
import tools.vitruv.testutils.VitruvApplicationTest
import tools.vitruv.testutils.domains.DomainUtil

import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.testutils.matchers.ModelMatchers.exists
import tools.vitruv.testutils.matchers.ModelDeepEqualityOption
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.util.EList
import java.util.ArrayList
import edu.kit.ipd.sdq.metamodels.persons.PersonRegister
import edu.kit.ipd.sdq.metamodels.persons.Male
import edu.kit.ipd.sdq.metamodels.persons.Female

//import static org.junit.Assert.isEquals

class FamiliesPersonsTest extends VitruvApplicationTest {
	static val logger = Logger.getLogger(FamiliesPersonsTest);
// Es gibt auch noch einen ModelPrinting
	static String FAMILY_NAME = "Mustermann"
	static String FIRST_NAME_FATHER = "Anton"
	static String FIRST_NAME_MOTHER = "Berta"
	static String FIRST_NAME_SON = "Charlie"
	static String FIRST_NAME_DAUGHTER = "Dalia"
	static Path PERSONS_MODEL = DomainUtil.getModelFileName('model/persons', new PersonsDomainProvider)
	static Path FAMILIES_MODEL = DomainUtil.getModelFileName('model/families', new FamiliesDomainProvider)

	override protected getChangePropagationSpecifications() {
		return #[new FamiliesToPersonsChangePropagationSpecification()]
	}

	def createRegister() {
		FamiliesFactory.eINSTANCE.createFamilyRegister
	}

	@BeforeEach
	def void insertRegister() {
		resourceAt(FAMILIES_MODEL).propagate[contents += createRegister()]
	}

	def createFamily(String familieName) {
		FamiliesFactory.eINSTANCE.createFamily => [lastName = familieName]
	}

	def void insertFamily(String familieName) {
		FamilyRegister.from(FAMILIES_MODEL).propagate[families += createFamily(familieName)]
	}

	@Test
	def void testInsertRegister() {
		// insertRegister(); -> Schon erledigt durch @Before Each
		assertThat(resourceAt(PERSONS_MODEL), exists);
		assertEquals(resourceAt(PERSONS_MODEL).contents.size, 1);
		assertEquals(resourceAt(PERSONS_MODEL).allContents.size, 1);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegisterImpl));
		assertEquals(resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size, 0);
	}

	@Test
	def void testInsertNewFamily() {
		val String familieName = "Meier";
		insertFamily(familieName)
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(resourceAt(PERSONS_MODEL).contents.size, 1);
		assertEquals(resourceAt(PERSONS_MODEL).allContents.size, 1);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegisterImpl));
		assertEquals(resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size, 0);
	}

	def getExistingFamily(String lastName) {
		FamilyRegister.from(FAMILIES_MODEL).families.findFirst[x|x.lastName.equals(lastName)]
	}



	@Test
	def void testInsertFamilyWithFather() {
		val String last = "Meier";
		val String first = "Anton";
		val fam = createFamily(last);
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += fam;
			fam.father = FamiliesFactory.eINSTANCE.createMember => [firstName = first; familyFather = fam]
		]

		logger.debug("\n\n===FATHER START===\n")
		resourceAt(PERSONS_MODEL).allContents.forEach[x, i|logger.debug(i + ": " + x.toString())]
		logger.debug("\n===END===\n\n")

//	testInsertFamilyWithMember("Berta", "Schulze", 1);
//	testInsertFamilyWithMember("Chris", "Schmidt", 2);
//	testInsertFamilyWithMember("Daria", "Krause", 3);
		assertThat(resourceAt(PERSONS_MODEL), exists)
		logger.addAppender(new ConsoleAppender(new SimpleLayout()))
		logger.setLevel(Level.INFO);
		assertEquals(resourceAt(PERSONS_MODEL).contents.size, 1);
		assertEquals(resourceAt(PERSONS_MODEL).allContents.size, 2);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size, 1);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0).eContents.get(0), instanceOf(Male));
		val MaleImpl father = resourceAt(PERSONS_MODEL).contents.get(0).eContents.get(0) as MaleImpl;
		assertEquals(father.fullName, "Anton Meier");
		assertEquals(father.birthday, null);
	}

	@Test
	def void testInsertFamilyWithMother() {
		val String last = "Meier";
		val String first = "Berta";
		val fam = createFamily(last);
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += fam;
			fam.mother = FamiliesFactory.eINSTANCE.createMember => [firstName = first; familyMother = fam]
		]

		logger.debug("\n\n===MOTHER START===\n")
		resourceAt(PERSONS_MODEL).allContents.forEach[x, i|logger.debug(i + ": " + x.toString())]
		logger.debug("\n===END===\n\n")

		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(resourceAt(PERSONS_MODEL).contents.size, 1);
		assertEquals(resourceAt(PERSONS_MODEL).allContents.size, 2);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size, 1);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0).eContents.get(0), instanceOf(Female));
		val FemaleImpl newMember = resourceAt(PERSONS_MODEL).contents.get(0).eContents.get(0) as FemaleImpl;
		assertEquals(newMember.fullName, "Berta Meier");
		assertEquals(newMember.birthday, null);
	}

	@Test
	def void testInsertFamilyWithSon() {
		val String last = "Meier";
		val String first = "Chris";
		val fam = createFamily(last);
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += fam;
			fam.sons += FamiliesFactory.eINSTANCE.createMember => [firstName = first; familySon = fam]
		]

		logger.debug("\n\n===SON START===\n")
		resourceAt(PERSONS_MODEL).allContents.forEach[x, i|logger.debug(i + ": " + x.toString())]
		logger.debug("\n===END===\n\n")

		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(resourceAt(PERSONS_MODEL).contents.size, 1);
		assertEquals(resourceAt(PERSONS_MODEL).allContents.size, 2);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size, 1);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0).eContents.get(0), instanceOf(Male));
		val MaleImpl son = resourceAt(PERSONS_MODEL).contents.get(0).eContents.get(0) as MaleImpl;
		assertEquals(son.fullName, "Chris Meier");
		assertEquals(son.birthday, null);
	}

	@Test
	def void testInsertFamilyWithDaughter() {
		val String last = "Meier";
		val String first = "Daria";
		val fam = createFamily(last);
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += fam;
			fam.daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = first; familyDaughter = fam]
		]

		logger.debug("\n\n===DAUGHTER START===\n")
		resourceAt(PERSONS_MODEL).allContents.forEach[x, i|logger.debug(i + ": " + x.toString())]
		logger.debug(resourceAt(PERSONS_MODEL).contents.get(0).eContents.get(0).class)
		logger.debug("\n===END===\n\n")

		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(resourceAt(PERSONS_MODEL).contents.size, 1);
		assertEquals(resourceAt(PERSONS_MODEL).allContents.size, 2);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size, 1);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0).eContents.get(0), instanceOf(Female));
		val FemaleImpl daughter = resourceAt(PERSONS_MODEL).contents.get(0).eContents.get(0) as FemaleImpl;
		assertEquals(daughter.fullName, "Daria Meier");
		assertEquals(daughter.birthday, null);
	// Test if object implements interface, instance-of-matcher von hamcrest
	}



	def void createFamilyBeforeTesting() {
		val String last = "Meier";
		val String fa = "Anton";
		val String mo = "Berta";
		val String so = "Chris";
		val String da = "Daria";
		val fam = createFamily(last);
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += fam;
			fam.father = FamiliesFactory.eINSTANCE.createMember => [firstName = fa; familyFather = fam;]
			fam.mother = FamiliesFactory.eINSTANCE.createMember => [firstName = mo; familyMother = fam]
			fam.sons += FamiliesFactory.eINSTANCE.createMember => [firstName = so; familySon = fam]		
			fam.daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = da; familyDaughter = fam]
		]
	}

	def boolean equalPersons(PersonImpl p1, PersonImpl p2) {
		val boolean bothNull = (p1 == null) && (p2 == null);
		val boolean bothNotNull = (p1 != null) && (p2 != null);
		val boolean bothSameType = bothNotNull && (p1.class == p2.class);

		val boolean bothNamesNull = bothNotNull && (p1.fullName == null) && (p2.fullName == null);
		val boolean bothNamesNotNull = bothNotNull && (p1.fullName != null) && (p2.fullName != null);
		val boolean bothNamesEqual = bothNotNull && bothNamesNotNull && p1.fullName.equals(p2.fullName);

		val boolean bothBirthdayNull = bothNotNull && (p1.birthday == null) && (p2.birthday == null);
		val boolean bothBirthdayNotNull = bothNotNull && (p1.birthday != null) && (p2.birthday != null);
		val boolean bothBirthdayEqual = bothNotNull && bothBirthdayNotNull && p1.birthday.equals(p2.birthday);

		return bothNull || ((bothNotNull && bothSameType) && (bothNamesNull || bothNamesEqual) && (bothBirthdayNull || bothBirthdayEqual));
	}



	@Test
	def void testDeleteFatherFromFamily() {
		//===== PRECONDITION =====
		//Set up the family
		this.createFamilyBeforeTesting();
		val MaleImpl dad = (PersonsFactory.eINSTANCE.createMale => [fullName = "Anton Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl mom = (PersonsFactory.eINSTANCE.createFemale => [fullName = "Berta Meier"; birthday = null]) as FemaleImpl;
		val MaleImpl son = (PersonsFactory.eINSTANCE.createMale => [fullName = "Chris Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl dau = (PersonsFactory.eINSTANCE.createFemale => [fullName = "Daria Meier"; birthday = null]) as FemaleImpl;
		
		//Check correct setup				
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(resourceAt(PERSONS_MODEL).contents.size, 1);
		assertEquals(resourceAt(PERSONS_MODEL).allContents.size, 5);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size, 4);
		
		val personListBefore = resourceAt(PERSONS_MODEL).contents.get(0).eContents;			
		assertThat(personListBefore.get(0), ModelMatchers.equalsDeeply(dad))
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,dad)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,mom)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,son)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,dau)], true)
		println("precondition done")		

		//===== ACTUAL TEST =====
		//Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate[
			val selectedFamily = families.findFirst[x|x.lastName.equals("Meier") && x.father.firstName.equals("Anton")]
			selectedFamily.father = null			
		]
		println("propagation done")
		
		//Check correct execution	
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(resourceAt(PERSONS_MODEL).contents.size, 1);
		assertEquals(resourceAt(PERSONS_MODEL).allContents.size, 4); //!
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size, 3); //!
		
		val personListAfter = resourceAt(PERSONS_MODEL).contents.get(0).eContents;			
		assertThat(personListAfter.get(0), ModelMatchers.equalsDeeply(mom)) //!
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,dad)], false) //!
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,mom)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,son)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,dau)], true)
		
//		personList.forEach[x, i|println(i + ": " + equalPersons(x as PersonImpl,dad))]
//		assertThat(
//			resourceAt(PERSONS_MODEL).contents.get(0).eContents as EList<PersonImpl>, 
//			Matchers.containsInAnyOrder(
//				Matchers.equalTo(dad as PersonImpl),
//				Matchers.equalTo(mom as PersonImpl),
//				Matchers.equalTo(son as PersonImpl),
//				Matchers.equalTo(dau as PersonImpl)
//			)
//		)
	}

	@Test
	def void testDeleteSonFromFamily() {
		//===== PRECONDITION =====
		//Set up the family
		this.createFamilyBeforeTesting();
		val MaleImpl dad = (PersonsFactory.eINSTANCE.createMale => [fullName = "Anton Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl mom = (PersonsFactory.eINSTANCE.createFemale => [fullName = "Berta Meier"; birthday = null]) as FemaleImpl;
		val MaleImpl son = (PersonsFactory.eINSTANCE.createMale => [fullName = "Chris Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl dau = (PersonsFactory.eINSTANCE.createFemale => [fullName = "Daria Meier"; birthday = null]) as FemaleImpl;
		
		//Check correct setup				
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(resourceAt(PERSONS_MODEL).contents.size, 1);
		assertEquals(resourceAt(PERSONS_MODEL).allContents.size, 5);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size, 4);
		
		val personListBefore = resourceAt(PERSONS_MODEL).contents.get(0).eContents;			
//		assertThat(personListBefore, Matchers .anyOf((ModelMatchers.equalsDeeply(dad)))			
//		)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,dad)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,mom)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,son)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,dau)], true)
		println("precondition done")
		
		//===== ACTUAL TEST =====
		//Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate[
			val selectedFamily = families.findFirst[x|x.lastName.equals("Meier") && x.sons.exists[y|y.firstName.equals("Chris")]]
			val sonToDelete = selectedFamily.sons.findFirst[x|x.firstName.equals("Chris")]			
			selectedFamily.sons.remove(sonToDelete)
		]
		println("propagation done")
		
		//Check correct execution	
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(resourceAt(PERSONS_MODEL).contents.size, 1);
		assertEquals(resourceAt(PERSONS_MODEL).allContents.size, 4); //!
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size, 3); //!
		
		val personListAfter = resourceAt(PERSONS_MODEL).contents.get(0).eContents;			
		assertThat(personListAfter.get(0), ModelMatchers.equalsDeeply(dad)) 
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,dad)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,mom)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,son)], false) //!
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,dau)], true)
	}

	@Test
	def void testDeleteMotherFromFamily() {
		//===== PRECONDITION =====
		//Set up the family
		this.createFamilyBeforeTesting();
		val MaleImpl dad = (PersonsFactory.eINSTANCE.createMale => [fullName = "Anton Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl mom = (PersonsFactory.eINSTANCE.createFemale => [fullName = "Berta Meier"; birthday = null]) as FemaleImpl;
		val MaleImpl son = (PersonsFactory.eINSTANCE.createMale => [fullName = "Chris Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl dau = (PersonsFactory.eINSTANCE.createFemale => [fullName = "Daria Meier"; birthday = null]) as FemaleImpl;
		
		//Check correct setup				
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(resourceAt(PERSONS_MODEL).contents.size, 1);
		assertEquals(resourceAt(PERSONS_MODEL).allContents.size, 5);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0) , instanceOf(PersonRegister));
		assertEquals(resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size, 4);
		
		val personListBefore = resourceAt(PERSONS_MODEL).contents.get(0).eContents;		
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,dad)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,mom)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,son)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,dau)], true)
		println("precondition done")
		
		//===== ACTUAL TEST =====
		//Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate[
			val selectedFamily = families.findFirst[x|x.lastName.equals("Meier") && x.mother.firstName.equals("Berta")]
			selectedFamily.mother = null
		]
		println("propagation done")
		
		//Check correct execution	
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(resourceAt(PERSONS_MODEL).contents.size, 1);
		assertEquals(resourceAt(PERSONS_MODEL).allContents.size, 4); //!
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0) , instanceOf(PersonRegister));	
		assertEquals(resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size, 3); //!
		
		val personListAfter = resourceAt(PERSONS_MODEL).contents.get(0).eContents;			
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,dad)], true) //!
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,mom)], false)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,son)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,dau)], true)
		
//		personList.forEach[x, i|println(i + ": " + equalPersons(x as PersonImpl,dad))]
//		assertThat(
//			resourceAt(PERSONS_MODEL).contents.get(0).eContents as EList<PersonImpl>, 
//			Matchers.containsInAnyOrder(
//				Matchers.equalTo(dad as PersonImpl),
//				Matchers.equalTo(mom as PersonImpl),
//				Matchers.equalTo(son as PersonImpl),
//				Matchers.equalTo(dau as PersonImpl)
//			)
//		)
	}

	@Test
	def void testDeleteDaughterFromFamily() {
		//===== PRECONDITION =====
		//Set up the family
		this.createFamilyBeforeTesting();
		val MaleImpl dad = (PersonsFactory.eINSTANCE.createMale => [fullName = "Anton Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl mom = (PersonsFactory.eINSTANCE.createFemale => [fullName = "Berta Meier"; birthday = null]) as FemaleImpl;
		val MaleImpl son = (PersonsFactory.eINSTANCE.createMale => [fullName = "Chris Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl dau = (PersonsFactory.eINSTANCE.createFemale => [fullName = "Daria Meier"; birthday = null]) as FemaleImpl;
		
		//Check correct setup				
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(resourceAt(PERSONS_MODEL).contents.size, 1);
		assertEquals(resourceAt(PERSONS_MODEL).allContents.size, 5);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size, 4);
		
		val personListBefore = resourceAt(PERSONS_MODEL).contents.get(0).eContents;			
		assertThat(personListBefore.get(0), ModelMatchers.equalsDeeply(dad))
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,dad)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,mom)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,son)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,dau)], true)
		println("precondition done")
		
		//===== ACTUAL TEST =====
		//Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate[
			val selectedFamily = families.findFirst[x|x.lastName.equals("Meier") && x.daughters.exists[y|y.firstName.equals("Daria")]]
			val daughterToDelete = selectedFamily.daughters.findFirst[x|x.firstName.equals("Daria")]			
			selectedFamily.daughters.remove(daughterToDelete)
		]
		println("propagation done")
		
		//Check correct execution	
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(resourceAt(PERSONS_MODEL).contents.size, 1);
		assertEquals(resourceAt(PERSONS_MODEL).allContents.size, 4); //!
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0) , instanceOf(PersonRegister));
		assertEquals(resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size, 3); //!
		
		val personListAfter = resourceAt(PERSONS_MODEL).contents.get(0).eContents;			
		assertThat(personListAfter.get(0), ModelMatchers.equalsDeeply(dad)) 
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,dad)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,mom)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,son)], true) //!
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,dau)], false)
	}



	@Test
	def void testChangeLastName(){
		//===== PRECONDITION =====
		//Set up the family
		this.createFamilyBeforeTesting();
		val MaleImpl old_dad = (PersonsFactory.eINSTANCE.createMale => [fullName = "Anton Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_mom = (PersonsFactory.eINSTANCE.createFemale => [fullName = "Berta Meier"; birthday = null]) as FemaleImpl;
		val MaleImpl old_son = (PersonsFactory.eINSTANCE.createMale => [fullName = "Chris Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_dau = (PersonsFactory.eINSTANCE.createFemale => [fullName = "Daria Meier"; birthday = null]) as FemaleImpl;
		
		val MaleImpl new_dad = (PersonsFactory.eINSTANCE.createMale => [fullName = "Anton Schulze"; birthday = null]) as MaleImpl;
		val FemaleImpl new_mom = (PersonsFactory.eINSTANCE.createFemale => [fullName = "Berta Schulze"; birthday = null]) as FemaleImpl;
		val MaleImpl new_son = (PersonsFactory.eINSTANCE.createMale => [fullName = "Chris Schulze"; birthday = null]) as MaleImpl;
		val FemaleImpl new_dau = (PersonsFactory.eINSTANCE.createFemale => [fullName = "Daria Schulze"; birthday = null]) as FemaleImpl;
		
		//Check correct setup				
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(resourceAt(PERSONS_MODEL).contents.size, 1);
		assertEquals(resourceAt(PERSONS_MODEL).allContents.size, 5);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size, 4);
		
		val personListBefore = resourceAt(PERSONS_MODEL).contents.get(0).eContents;	
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,old_dad)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,old_mom)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,old_son)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,old_dau)], true)
		println("precondition done")
		
		//===== ACTUAL TEST =====
		//Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate[
			val selectedFamily = families.findFirst[x|x.lastName.equals("Meier")]
			selectedFamily.lastName = "Schulze"	
		]
		println("propagation done")
		
		logger.info("\n\n===SON START===\n")
		resourceAt(PERSONS_MODEL).allContents.forEach[x, i|logger.info(i + ": " + x.toString())]
		logger.info("\n===END===\n\n")
		
		//Check correct execution	
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(resourceAt(PERSONS_MODEL).contents.size, 1);
		assertEquals(resourceAt(PERSONS_MODEL).allContents.size, 5); 
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size, 4);
		
		val personListAfter = resourceAt(PERSONS_MODEL).contents.get(0).eContents;	
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,old_dad)], false)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,old_mom)], false)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,old_son)], false)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,old_dau)], false)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,new_dad)], true) 
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,new_mom)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,new_son)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,new_dau)], true)
		
	}
	
	
	
	@Test
	def void testChangeFirstNameFather(){
		//===== PRECONDITION =====
		//Set up the family
		this.createFamilyBeforeTesting();
		val MaleImpl old_dad = (PersonsFactory.eINSTANCE.createMale => [fullName = "Anton Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_mom = (PersonsFactory.eINSTANCE.createFemale => [fullName = "Berta Meier"; birthday = null]) as FemaleImpl;
		val MaleImpl old_son = (PersonsFactory.eINSTANCE.createMale => [fullName = "Chris Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_dau = (PersonsFactory.eINSTANCE.createFemale => [fullName = "Daria Meier"; birthday = null]) as FemaleImpl;
		
		val MaleImpl new_dad = (PersonsFactory.eINSTANCE.createMale => [fullName = "Adam Meier"; birthday = null]) as MaleImpl;
		
		//Check correct setup				
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(resourceAt(PERSONS_MODEL).contents.size, 1);
		assertEquals(resourceAt(PERSONS_MODEL).allContents.size, 5);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0) , instanceOf(PersonRegister));
		assertEquals(resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size, 4);
		
		val personListBefore = resourceAt(PERSONS_MODEL).contents.get(0).eContents;	
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,old_dad)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,old_mom)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,old_son)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,old_dau)], true)
		println("precondition done")
		
		//===== ACTUAL TEST =====
		//Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate[
			val selectedFamily = families.findFirst[x|x.lastName.equals("Meier") && x.father.firstName.equals("Anton")]
			selectedFamily.father.firstName = "Adam"
		]
		println("propagation done")
		
		logger.info("\n\n===Father START===\n")
		resourceAt(PERSONS_MODEL).allContents.forEach[x, i|logger.info(i + ": " + x.toString())]
		logger.info("\n===END===\n\n")
		
		//Check correct execution	
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(resourceAt(PERSONS_MODEL).contents.size, 1);
		assertEquals(resourceAt(PERSONS_MODEL).allContents.size, 5); 
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size, 4);
		
		val personListAfter = resourceAt(PERSONS_MODEL).contents.get(0).eContents;	
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,old_dad)], false)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,old_mom)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,old_son)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,old_dau)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,new_dad)], true) 
		
	}
	
	@Test
	def void testChangeFirstNameSon(){
		//===== PRECONDITION =====
		//Set up the family
		this.createFamilyBeforeTesting();
		val MaleImpl old_dad = (PersonsFactory.eINSTANCE.createMale => [fullName = "Anton Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_mom = (PersonsFactory.eINSTANCE.createFemale => [fullName = "Berta Meier"; birthday = null]) as FemaleImpl;
		val MaleImpl old_son = (PersonsFactory.eINSTANCE.createMale => [fullName = "Chris Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_dau = (PersonsFactory.eINSTANCE.createFemale => [fullName = "Daria Meier"; birthday = null]) as FemaleImpl;
		
		val MaleImpl new_son = (PersonsFactory.eINSTANCE.createMale => [fullName = "Charles Meier"; birthday = null]) as MaleImpl;
		
		//Check correct setup				
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(resourceAt(PERSONS_MODEL).contents.size, 1);
		assertEquals(resourceAt(PERSONS_MODEL).allContents.size, 5);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0) , instanceOf(PersonRegister));
		assertEquals(resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size, 4);
		
		val personListBefore = resourceAt(PERSONS_MODEL).contents.get(0).eContents;	
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,old_dad)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,old_mom)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,old_son)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,old_dau)], true)
		println("precondition done")
		
		//===== ACTUAL TEST =====
		//Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate[
			val selectedFamily = families.findFirst[x|x.lastName.equals("Meier") && x.sons.exists[y|y.firstName.equals("Chris")]]
			val sonToChange = selectedFamily.sons.findFirst[x|x.firstName.equals("Chris")]			
			sonToChange.firstName = "Charles"
		]
		println("propagation done")
		
		logger.info("\n\n===Son START===\n")
		resourceAt(PERSONS_MODEL).allContents.forEach[x, i|logger.info(i + ": " + x.toString())]
		logger.info("\n===END===\n\n")
		
		//Check correct execution	
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(resourceAt(PERSONS_MODEL).contents.size, 1);
		assertEquals(resourceAt(PERSONS_MODEL).allContents.size, 5); 
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size, 4);
		
		val personListAfter = resourceAt(PERSONS_MODEL).contents.get(0).eContents;	
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,old_dad)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,old_mom)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,old_son)], false)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,old_dau)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,new_son)], true) 
		
	}
	
	@Test
	def void testChangeFirstNameMother(){
		//===== PRECONDITION =====
		//Set up the family
		this.createFamilyBeforeTesting();
		val MaleImpl old_dad = (PersonsFactory.eINSTANCE.createMale => [fullName = "Anton Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_mom = (PersonsFactory.eINSTANCE.createFemale => [fullName = "Berta Meier"; birthday = null]) as FemaleImpl;
		val MaleImpl old_son = (PersonsFactory.eINSTANCE.createMale => [fullName = "Chris Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_dau = (PersonsFactory.eINSTANCE.createFemale => [fullName = "Daria Meier"; birthday = null]) as FemaleImpl;
		
		val FemaleImpl new_mom = (PersonsFactory.eINSTANCE.createFemale => [fullName = "Birgit Meier"; birthday = null]) as FemaleImpl;
		
		//Check correct setup				
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(resourceAt(PERSONS_MODEL).contents.size, 1);
		assertEquals(resourceAt(PERSONS_MODEL).allContents.size, 5);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size, 4);
		
		val personListBefore = resourceAt(PERSONS_MODEL).contents.get(0).eContents;	
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,old_dad)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,old_mom)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,old_son)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,old_dau)], true)
		println("precondition done")
		
		//===== ACTUAL TEST =====
		//Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate[
			val selectedFamily = families.findFirst[x|x.lastName.equals("Meier") && x.mother.firstName.equals("Berta")]
			selectedFamily.mother.firstName = "Birgit"
		]
		println("propagation done")
		
		logger.info("\n\n===Mother START===\n")
		resourceAt(PERSONS_MODEL).allContents.forEach[x, i|logger.info(i + ": " + x.toString())]
		logger.info("\n===END===\n\n")
		
		//Check correct execution	
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(resourceAt(PERSONS_MODEL).contents.size, 1);
		assertEquals(resourceAt(PERSONS_MODEL).allContents.size, 5); 
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size, 4);
		
		val personListAfter = resourceAt(PERSONS_MODEL).contents.get(0).eContents;	
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,old_dad)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,old_mom)], false)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,old_son)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,old_dau)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,new_mom)], true) 
		
	}
	
	@Test
	def void testChangeFirstNameDaughter(){
		//===== PRECONDITION =====
		//Set up the family
		this.createFamilyBeforeTesting();
		val MaleImpl old_dad = (PersonsFactory.eINSTANCE.createMale => [fullName = "Anton Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_mom = (PersonsFactory.eINSTANCE.createFemale => [fullName = "Berta Meier"; birthday = null]) as FemaleImpl;
		val MaleImpl old_son = (PersonsFactory.eINSTANCE.createMale => [fullName = "Chris Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_dau = (PersonsFactory.eINSTANCE.createFemale => [fullName = "Daria Meier"; birthday = null]) as FemaleImpl;
		
		val FemaleImpl new_dau = (PersonsFactory.eINSTANCE.createFemale => [fullName = "Daniela Meier"; birthday = null]) as FemaleImpl;
		
		//Check correct setup				
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(resourceAt(PERSONS_MODEL).contents.size, 1);
		assertEquals(resourceAt(PERSONS_MODEL).allContents.size, 5);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size, 4);
		
		val personListBefore = resourceAt(PERSONS_MODEL).contents.get(0).eContents;	
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,old_dad)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,old_mom)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,old_son)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,old_dau)], true)
		println("precondition done")
		
		//===== ACTUAL TEST =====
		//Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate[
			val selectedFamily = families.findFirst[x|x.lastName.equals("Meier") && x.daughters.exists[y|y.firstName.equals("Daria")]]
			val daughterToChange = selectedFamily.daughters.findFirst[x|x.firstName.equals("Daria")]			
			daughterToChange.firstName = "Daniela"
		]
		println("propagation done")
		
		logger.info("\n\n===Daughter START===\n")
		resourceAt(PERSONS_MODEL).allContents.forEach[x, i|logger.info(i + ": " + x.toString())]
		logger.info("\n===END===\n\n")
		
		//Check correct execution	
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(resourceAt(PERSONS_MODEL).contents.size, 1);
		assertEquals(resourceAt(PERSONS_MODEL).allContents.size, 5); 
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size, 4);
		
		val personListAfter = resourceAt(PERSONS_MODEL).contents.get(0).eContents;	
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,old_dad)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,old_mom)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,old_son)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,old_dau)], false)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,new_dau)], true) 
		
	}

//	static val MaleImpl old_dad = (PersonsFactory.eINSTANCE.createMale => [fullName = "Anton Meier"; birthday = null]) as MaleImpl;
//	static val FemaleImpl old_mom = (PersonsFactory.eINSTANCE.createFemale => [fullName = "Berta Meier"; birthday = null]) as FemaleImpl;
//	static val MaleImpl old_son = (PersonsFactory.eINSTANCE.createMale => [fullName = "Chris Meier"; birthday = null]) as MaleImpl;
//	static val FemaleImpl old_dau = (PersonsFactory.eINSTANCE.createFemale => [fullName = "Daria Meier"; birthday = null]) as FemaleImpl;

	@Test
	def void testInsertDadIfDadAlreadyExists(){
		//===== PRECONDITION =====
		//Set up the family
		this.createFamilyBeforeTesting();
		val MaleImpl old_dad = (PersonsFactory.eINSTANCE.createMale => [fullName = "Anton Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_mom = (PersonsFactory.eINSTANCE.createFemale => [fullName = "Berta Meier"; birthday = null]) as FemaleImpl;
		val MaleImpl old_son = (PersonsFactory.eINSTANCE.createMale => [fullName = "Chris Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_dau = (PersonsFactory.eINSTANCE.createFemale => [fullName = "Daria Meier"; birthday = null]) as FemaleImpl;
		
		val MaleImpl new_dad = (PersonsFactory.eINSTANCE.createMale => [fullName = "Albert Meier"; birthday = null]) as MaleImpl;
		
		println("\n\n===ExistingDad START===\n")
		resourceAt(PERSONS_MODEL).allContents.forEach[x, i|println(i + ": " + x.toString())]
		println("\n===END===\n\n")
		
		//Check correct setup				
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(5, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(4, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);
		
		val personListBefore = resourceAt(PERSONS_MODEL).contents.get(0).eContents;	
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,old_dad)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,old_mom)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,old_son)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl,old_dau)], true)
		println("precondition done")
		
		//===== ACTUAL TEST =====
		//Propagate changes
		FamilyRegister.from(FAMILIES_MODEL).propagate[
			val fam = families.findFirst[x|x.lastName.equals("Meier")]
			fam.father = FamiliesFactory.eINSTANCE.createMember => [firstName = "Albert"; familyFather = fam]			
		]
		println("propagation done")
		
		
		println("\n\n===ExistingDad START===\n")
		resourceAt(PERSONS_MODEL).allContents.forEach[x, i|println(i + ": " + x.toString())]
		println("\n===END===\n\n")		
		
		
		
		val int doNothing  = 0;
		val int replaceOld = 1;
		val int createNewFamily = 2;
		val int chosenCase = 0;
		
		
		//Check correct execution	
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		val personListAfter = resourceAt(PERSONS_MODEL).contents.get(0).eContents;	
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,old_mom)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,old_son)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,old_dau)], true)
		
		
//		assertThat(personListAfter, not(existsInAnyOrder(old_dau, old_son)))
		
		
		switch(chosenCase){
			case doNothing:{				
				assertEquals(resourceAt(PERSONS_MODEL).allContents.size, 5); 
				assertEquals(resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size, 4);
				assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,old_dad)], true)
				assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,new_dad)], false)
			}
			case replaceOld:{				
				assertEquals(resourceAt(PERSONS_MODEL).allContents.size, 5); 
				assertEquals(resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size, 4);
				assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,old_dad)], false)
				assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,new_dad)], true)
			}
			case createNewFamily:{				
				assertEquals(resourceAt(PERSONS_MODEL).allContents.size, 6); 
				assertEquals(resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size, 5);
				assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,old_dad)], true)
				assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl,new_dad)], true)
			}
		}				
	}

//===BEGIN=REGION=TESTS======================================================================================
//	@Test
//	def void createContainertestDeleteFamilyRegister() {
//		ass
//	}
//	@Test
//	def void testDeleteFamilyRegister() {
//		logger.debug(PERSONS_MODEL);
//		logger.debug(FAMILIES_MODEL);
//	}
//
//	@Test
//	def void testChangeFirstName() {
//		val daughter = FamiliesFactory.eINSTANCE.createMember
//		FamilyRegister.from(FAMILIES_MODEL).propagate [
//			val family = insertNewFamily(FAMILY_NAME)
//			families += family => [
//				daughters += daughter => [
//					firstName = FIRST_NAME_DAUGHTER
//				]
//			]
//		]
//
//		FamilyRegister.from(FAMILIES_MODEL).families.claimOne.daughters.claimOne.propagate [
//			firstName = FIRST_NAME_MOTHER
//		]
//		val personsWithMothersName = PersonRegister.from(PERSONS_MODEL).persons.filter [
//			fullName.split(" ").get(0) == FIRST_NAME_MOTHER
//		]
//		assertEquals(1, personsWithMothersName.length)
//	}
//=====END=REGION=TESTS======================================================================================
	def dprint(FamilyRegisterImpl fri) {
		val String str = '''FamilyRegister «IF fri.id != null»«fri.id»«ELSE»<no id>«ENDIF»
«FOR f : fri.families»	Family «f.lastName»
«IF f.father != null»		Father «f.father.firstName»«ENDIF»
«IF f.mother != null»		Mother «f.mother.firstName»«ENDIF»
«FOR s : f.sons BEFORE '		Sons (' SEPARATOR ', ' AFTER ')'»«s.firstName»«ENDFOR»
«FOR d : f.daughters BEFORE '		Daughters (' SEPARATOR ', ' AFTER ')'»«d.firstName»«ENDFOR»«ENDFOR»''';
		println(str)
	}
}
