package tools.vitruv.applications.demo.familiespersons.tests.families2Persons

import org.hamcrest.MatcherAssert;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.core.*
import org.hamcrest.*
import org.hamcrest.org.hamcrest.*;

//import org.hamcrest.Matchers;
//import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.CoreMatchers.hasItem
import tools.vitruv.testutils.matchers.ModelMatchers;
import static tools.vitruv.testutils.matchers.ModelMatchers.*
import static org.hamcrest.CoreMatchers.*

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
//import tools.vitruv.framework.vsum.*
import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.testutils.matchers.ModelMatchers.exists
import tools.vitruv.testutils.matchers.ModelDeepEqualityOption
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.util.EList
import java.util.ArrayList
import edu.kit.ipd.sdq.metamodels.persons.PersonRegister
import edu.kit.ipd.sdq.metamodels.persons.Male
import edu.kit.ipd.sdq.metamodels.persons.Female
import tools.vitruv.testutils.TestUserInteraction
import tools.vitruv.testutils.TestUserInteraction.MultipleChoiceInteractionDescription
//import tools.vitruv.framework.vsum.internal.VirtualModelImpl
//import tools.vitruv.framework.correspondence.impl.InternalCorrespondenceModelImpl
import java.util.List
import tools.vitruv.framework.correspondence.impl.InternalCorrespondenceModelImpl
import tools.vitruv.framework.vsum.internal.VirtualModelImpl
import tools.vitruv.framework.correspondence.CorrespondenceModel
import edu.kit.ipd.sdq.metamodels.families.Family
import org.eclipse.emf.common.util.BasicEList
import edu.kit.ipd.sdq.metamodels.persons.Person

class FamiliesPersonsTest extends VitruvApplicationTest {
	static val logger = Logger.getLogger(FamiliesPersonsTest);
// Es gibt auch noch einen ModelPrinting
	static String FAMILY_NAME = "Meier"
	static String FIRST_FATHER = "Anton"
	static String FIRST_MOTHER = "Berta"
	static String FIRST_SON = "Chris"
	static String FIRST_DAUGHTER = "Daria"
	static Path PERSONS_MODEL = DomainUtil.getModelFileName('model/persons', new PersonsDomainProvider)
	static Path FAMILIES_MODEL = DomainUtil.getModelFileName('model/families', new FamiliesDomainProvider)

	static Male DAD = PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_FATHER + " " + FAMILY_NAME]
	static Female MOM = PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOTHER + " " + FAMILY_NAME]
	static Male SON = PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON + " " + FAMILY_NAME]
	static Female DAU = PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAUGHTER + " " + FAMILY_NAME]

	override protected getChangePropagationSpecifications() {
		return #[new FamiliesToPersonsChangePropagationSpecification()]
	}

	def static Matcher<? super EList<EObject>> containsInAnyOder(EList<EObject> searchedItems,
		ModelDeepEqualityOption... options) {
		new EListMultipleContainmentMatcher(searchedItems, true, options)
	}
	def static Matcher<? super EList<EObject>> containsNoneOfList(EList<EObject> searchedItems,
		ModelDeepEqualityOption... options) {
		new EListMultipleContainmentMatcher(searchedItems, false, options)
	}

	def static Matcher<? super EList<EObject>> listContains(EObject searchedItem, ModelDeepEqualityOption... options) {
		new EListSingleContainmentMatcher(searchedItem, true, options)
	}

	def createRegister() {
		FamiliesFactory.eINSTANCE.createFamilyRegister
	}

	@BeforeEach
	def void insertRegister() {
//		val SimpleLayout layout = new SimpleLayout();
//		val ConsoleAppender consoleAppender = new ConsoleAppender(layout);
//		logger.addAppender(consoleAppender);
//		logger.setLevel(Level.INFO)
		logger.setLevel(Level.DEBUG)
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
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(1, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(0, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);
	}

	@Test
	def void testInsertNewFamily() {
		insertFamily(FAMILY_NAME)
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(1, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(0, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);
	}

	def getExistingFamily(String lastName) {
		FamilyRegister.from(FAMILIES_MODEL).families.findFirst[x|x.lastName.equals(lastName)]
	}

	@Test
	def void testInsertFamilyWithFather() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		val fam = createFamily(FAMILY_NAME);
		logger.info(name + " - preparation done")
		dprint()
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += fam;
			fam.father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER; familyFather = fam]
		]
		logger.info(name + " - propagation done")
		dprint()
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(2, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0).eContents.get(0), instanceOf(Male));
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0).eContents.get(0), equalsDeeply(DAD));
		logger.info(name + " - finished without errors")
	}

	@Test
	def void testInsertFamilyWithMother() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		val fam = createFamily(FAMILY_NAME);
		logger.info(name + " - preparation done")
		dprint()
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += fam;
			fam.mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER; familyMother = fam]
		]
		logger.info(name + " - propagation done")
		dprint()
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(2, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0).eContents.get(0), instanceOf(Female));
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0).eContents.get(0), equalsDeeply(MOM));
		logger.info(name + " - finished without errors")
	}

	@Test
	def void testInsertFamilyWithSon() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		val fam = createFamily(FAMILY_NAME);
		logger.info(name + " - preparation done")
		dprint()
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += fam;
			fam.sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON; familySon = fam]
		]
		logger.info(name + " - propagation done")
		dprint()
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(2, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0).eContents.get(0), instanceOf(Male));
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0).eContents.get(0), equalsDeeply(SON));
		logger.info(name + " - finished without errors")
	}

	@Test
	def void testInsertFamilyWithDaughter() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		val fam = createFamily(FAMILY_NAME);
		logger.info(name + " - preparation done")
		dprint()
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += fam;
			fam.daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER; familyDaughter = fam]
		]
		logger.info(name + " - propagation done")
		dprint()
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(2, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0).eContents.get(0), instanceOf(Female));
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0).eContents.get(0), equalsDeeply(DAU));
		logger.info(name + " - finished without errors")
	}

	def void createFamilyBeforeTesting() {
		val fam = createFamily(FAMILY_NAME);
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += fam;
			fam.father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER; familyFather = fam;]
			fam.mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER; familyMother = fam]
			fam.sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON; familySon = fam]
			fam.daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER; familyDaughter = fam]
		]
	}

	def static boolean equalPersons(PersonImpl p1, PersonImpl p2) {
		val boolean bothNull = (p1 == null) && (p2 == null);
		val boolean bothNotNull = (p1 != null) && (p2 != null);
		val boolean bothSameType = bothNotNull && (p1.class == p2.class);

		val boolean bothNamesNull = bothNotNull && (p1.fullName == null) && (p2.fullName == null);
		val boolean bothNamesNotNull = bothNotNull && (p1.fullName != null) && (p2.fullName != null);
		val boolean bothNamesEqual = bothNotNull && bothNamesNotNull && p1.fullName.equals(p2.fullName);

		val boolean bothBirthdayNull = bothNotNull && (p1.birthday == null) && (p2.birthday == null);
		val boolean bothBirthdayNotNull = bothNotNull && (p1.birthday != null) && (p2.birthday != null);
		val boolean bothBirthdayEqual = bothNotNull && bothBirthdayNotNull && p1.birthday.equals(p2.birthday);

		return bothNull || ((bothNotNull && bothSameType) && (bothNamesNull || bothNamesEqual) &&
			(bothBirthdayNull || bothBirthdayEqual));
	}

	@Test
	def void testDeleteFatherFromFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		
		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();

		// Check correct setup				
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(5, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(4, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);

		var EList<EObject> list = new BasicEList<EObject>();
		list.addAll(DAD, MOM, SON, DAU)

		val personListBefore = resourceAt(PERSONS_MODEL).contents.get(0).eContents;
//		assertThat(personListBefore.get(0), ModelMatchers.equalsDeeply(dad))
		assertThat(personListBefore, containsInAnyOder(list))
		
//		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, DAD)], true)
//		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, MOM)], true)
//		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, SON)], true)
//		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, DAU)], true)
		logger.info(name + " - preparation done")
		dprint()

		// ===== ACTUAL TEST =====
		// Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst[x|x.lastName.equals(FAMILY_NAME) && x.father.firstName.equals(FIRST_FATHER)]
			selectedFamily.father = null
		]
		logger.info(name + " - propagation done")
		dprint()

		// Check correct execution	
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(4, resourceAt(PERSONS_MODEL).allContents.size); // !
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(3, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size); // !
		val personListAfter = resourceAt(PERSONS_MODEL).contents.get(0).eContents;
		
		var EList<EObject> list2 = new BasicEList<EObject>();
		list2.addAll(MOM, SON, DAU)
		assertThat(personListAfter, containsInAnyOder(list2))
		assertThat(personListAfter, not(listContains(DAD)))
		
//		assertThat(personListAfter.get(0), ModelMatchers.equalsDeeply(mom)) // !
//		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, dad)], false) // !
//		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, mom)], true)
//		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, son)], true)
//		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, dau)], true)
		
		logger.info(name + " - finished without errors")
	}

	@Test
	def void testDeleteSonFromFamily() {
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		val MaleImpl dad = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Anton Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl mom = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Berta Meier"; birthday = null]) as FemaleImpl;
		val MaleImpl son = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Chris Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl dau = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Daria Meier"; birthday = null]) as FemaleImpl;

		// Check correct setup				
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(5, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(4, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);

		val personListBefore = resourceAt(PERSONS_MODEL).contents.get(0).eContents;
//		assertThat(personListBefore, Matchers .anyOf((ModelMatchers.equalsDeeply(dad)))			
//		)
		var EList<EObject> asdf = new BasicEList<EObject>();
		asdf.add(dad);
		asdf.add(mom);
		asdf.add(son);
		asdf.add(dau);
		var EList<EObject> asdf2 = new BasicEList<EObject>();
		asdf2.add(dad);
		asdf2.add(son);
		asdf2.add(dau)

		assertThat(personListBefore, listContains(dad))
		assertThat(personListBefore, listContains(mom))
		assertThat(personListBefore, listContains(son))
		assertThat(personListBefore, listContains(dau))

		assertThat(asdf, listContains(dad))
		assertThat(asdf, listContains(mom))
		assertThat(asdf, listContains(son))
		assertThat(asdf, listContains(dau))

		assertThat(asdf2, listContains(dad))
		assertThat(asdf2, listContains(son))
		assertThat(asdf2, listContains(dau))

		assertThat(personListBefore, containsInAnyOder(asdf))
		assertThat(personListBefore, containsInAnyOder(asdf2))
		assertThat(asdf, containsInAnyOder(asdf2))

		assertThat(asdf2, not(containsInAnyOder(asdf)))
		assertThat(asdf2, not(listContains(mom)))

//		assertThat(asdf2, containsInAnyOder(asdf))
//		assertThat(personListBefore, containsInAnyOder(asdf))
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, dad)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, mom)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, son)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, dau)], true)
		println("precondition done")

		// ===== ACTUAL TEST =====
		// Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [ x |
				x.lastName.equals("Meier") && x.sons.exists[y|y.firstName.equals("Chris")]
			]
			val sonToDelete = selectedFamily.sons.findFirst[x|x.firstName.equals("Chris")]
			selectedFamily.sons.remove(sonToDelete)
		]
		println("propagation done")

		// Check correct execution	
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(4, resourceAt(PERSONS_MODEL).allContents.size); // !
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(3, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size); // !
		val personListInTheEnde = resourceAt(PERSONS_MODEL).contents.get(0).eContents;
		
		var EList<EObject> l_yes = new BasicEList<EObject>();
		l_yes.add(dad);
		l_yes.add(mom);
		l_yes.add(dau);
		var EList<EObject> l_no = new BasicEList<EObject>();
		l_no.add(son);
		l_no.add(son);
		l_no.add(son);
		
		assertThat(personListInTheEnde, containsInAnyOder(l_yes))
		assertThat(personListInTheEnde, containsNoneOfList(l_no))
		
//		assertThat(personListAfter.get(0), ModelMatchers.equalsDeeply(dad))
//		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, dad)], true)
//		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, mom)], true)
//		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, son)], false) // !
//		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, dau)], true)
	}

	@Test
	def void testDeleteMotherFromFamily() {
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		val MaleImpl dad = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Anton Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl mom = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Berta Meier"; birthday = null]) as FemaleImpl;
		val MaleImpl son = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Chris Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl dau = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Daria Meier"; birthday = null]) as FemaleImpl;

		// Check correct setup				
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(5, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(4, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);

		val personListBefore = resourceAt(PERSONS_MODEL).contents.get(0).eContents;
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, dad)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, mom)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, son)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, dau)], true)
		println("precondition done")

		// ===== ACTUAL TEST =====
		// Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst[x|x.lastName.equals("Meier") && x.mother.firstName.equals("Berta")]
			selectedFamily.mother = null
		]
		println("propagation done")

		// Check correct execution	
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(4, resourceAt(PERSONS_MODEL).allContents.size); // !
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(3, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size); // !
		val personListAfter = resourceAt(PERSONS_MODEL).contents.get(0).eContents;
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, dad)], true) // !
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, mom)], false)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, son)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, dau)], true)

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
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		val MaleImpl dad = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Anton Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl mom = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Berta Meier"; birthday = null]) as FemaleImpl;
		val MaleImpl son = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Chris Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl dau = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Daria Meier"; birthday = null]) as FemaleImpl;

		// Check correct setup				
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(5, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(4, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);

		val personListBefore = resourceAt(PERSONS_MODEL).contents.get(0).eContents;
		assertThat(personListBefore.get(0), ModelMatchers.equalsDeeply(dad))
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, dad)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, mom)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, son)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, dau)], true)
		println("precondition done")

		// ===== ACTUAL TEST =====
		// Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [ x |
				x.lastName.equals("Meier") && x.daughters.exists[y|y.firstName.equals("Daria")]
			]
			val daughterToDelete = selectedFamily.daughters.findFirst[x|x.firstName.equals("Daria")]
			selectedFamily.daughters.remove(daughterToDelete)
		]
		println("propagation done")

		// Check correct execution	
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(4, resourceAt(PERSONS_MODEL).allContents.size); // !
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(3, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size); // !
		val personListAfter = resourceAt(PERSONS_MODEL).contents.get(0).eContents;
		assertThat(personListAfter.get(0), ModelMatchers.equalsDeeply(dad))
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, dad)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, mom)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, son)], true) // !
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, dau)], false)
	}

	@Test
	def void testChangeLastName() {
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		val MaleImpl old_dad = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Anton Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_mom = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Berta Meier"; birthday = null]) as FemaleImpl;
		val MaleImpl old_son = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Chris Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_dau = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Daria Meier"; birthday = null]) as FemaleImpl;

		val MaleImpl new_dad = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Anton Schulze"; birthday = null]) as MaleImpl;
		val FemaleImpl new_mom = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Berta Schulze"; birthday = null]) as FemaleImpl;
		val MaleImpl new_son = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Chris Schulze"; birthday = null]) as MaleImpl;
		val FemaleImpl new_dau = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Daria Schulze"; birthday = null]) as FemaleImpl;

		// Check correct setup				
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(5, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(4, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);

		val personListBefore = resourceAt(PERSONS_MODEL).contents.get(0).eContents;
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_dad)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_mom)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_son)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_dau)], true)
		println("precondition done")

		// ===== ACTUAL TEST =====
		// Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst[x|x.lastName.equals("Meier")]
			selectedFamily.lastName = "Schulze"
		]
		println("propagation done")

		logger.info("\n\n===SON START===\n")
		resourceAt(PERSONS_MODEL).allContents.forEach[x, i|logger.info(i + ": " + x.toString())]
		logger.info("\n===END===\n\n")

		// Check correct execution	
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(5, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(4, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);

		val personListAfter = resourceAt(PERSONS_MODEL).contents.get(0).eContents;
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, old_dad)], false)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, old_mom)], false)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, old_son)], false)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, old_dau)], false)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, new_dad)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, new_mom)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, new_son)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, new_dau)], true)

	}

	@Test
	def void testChangeFirstNameFather() {
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		val MaleImpl old_dad = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Anton Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_mom = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Berta Meier"; birthday = null]) as FemaleImpl;
		val MaleImpl old_son = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Chris Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_dau = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Daria Meier"; birthday = null]) as FemaleImpl;

		val MaleImpl new_dad = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Adam Meier"; birthday = null]) as MaleImpl;

		// Check correct setup				
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(5, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(4, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);

		val personListBefore = resourceAt(PERSONS_MODEL).contents.get(0).eContents;
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_dad)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_mom)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_son)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_dau)], true)
		println("precondition done")

		// ===== ACTUAL TEST =====
		// Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst[x|x.lastName.equals("Meier") && x.father.firstName.equals("Anton")]
			selectedFamily.father.firstName = "Adam"
		]
		println("propagation done")

		logger.info("\n\n===Father START===\n")
		resourceAt(PERSONS_MODEL).allContents.forEach[x, i|logger.info(i + ": " + x.toString())]
		logger.info("\n===END===\n\n")

		// Check correct execution	
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(5, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(4, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);

		val personListAfter = resourceAt(PERSONS_MODEL).contents.get(0).eContents;
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, old_dad)], false)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, old_mom)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, old_son)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, old_dau)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, new_dad)], true)

	}

	@Test
	def void testChangeFirstNameSon() {
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		val MaleImpl old_dad = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Anton Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_mom = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Berta Meier"; birthday = null]) as FemaleImpl;
		val MaleImpl old_son = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Chris Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_dau = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Daria Meier"; birthday = null]) as FemaleImpl;

		val MaleImpl new_son = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Charles Meier"; birthday = null]) as MaleImpl;

		// Check correct setup				
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(5, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(4, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);

		val personListBefore = resourceAt(PERSONS_MODEL).contents.get(0).eContents;
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_dad)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_mom)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_son)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_dau)], true)
		println("precondition done")

		// ===== ACTUAL TEST =====
		// Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [ x |
				x.lastName.equals("Meier") && x.sons.exists[y|y.firstName.equals("Chris")]
			]
			val sonToChange = selectedFamily.sons.findFirst[x|x.firstName.equals("Chris")]
			sonToChange.firstName = "Charles"
		]
		println("propagation done")

		logger.info("\n\n===Son START===\n")
		resourceAt(PERSONS_MODEL).allContents.forEach[x, i|logger.info(i + ": " + x.toString())]
		logger.info("\n===END===\n\n")

		// Check correct execution	
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(5, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(4, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);

		val personListAfter = resourceAt(PERSONS_MODEL).contents.get(0).eContents;
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, old_dad)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, old_mom)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, old_son)], false)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, old_dau)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, new_son)], true)

	}

	@Test
	def void testChangeFirstNameMother() {
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		val MaleImpl old_dad = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Anton Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_mom = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Berta Meier"; birthday = null]) as FemaleImpl;
		val MaleImpl old_son = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Chris Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_dau = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Daria Meier"; birthday = null]) as FemaleImpl;

		val FemaleImpl new_mom = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Birgit Meier"; birthday = null]) as FemaleImpl;

		// Check correct setup				
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(5, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(4, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);

		val personListBefore = resourceAt(PERSONS_MODEL).contents.get(0).eContents;
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_dad)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_mom)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_son)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_dau)], true)
		println("precondition done")

		// ===== ACTUAL TEST =====
		// Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst[x|x.lastName.equals("Meier") && x.mother.firstName.equals("Berta")]
			selectedFamily.mother.firstName = "Birgit"
		]
		println("propagation done")

		logger.info("\n\n===Mother START===\n")
		resourceAt(PERSONS_MODEL).allContents.forEach[x, i|logger.info(i + ": " + x.toString())]
		logger.info("\n===END===\n\n")

		// Check correct execution	
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(5, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(4, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);

		val personListAfter = resourceAt(PERSONS_MODEL).contents.get(0).eContents;
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, old_dad)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, old_mom)], false)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, old_son)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, old_dau)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, new_mom)], true)

	}

	@Test
	def void testChangeFirstNameDaughter() {
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		val MaleImpl old_dad = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Anton Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_mom = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Berta Meier"; birthday = null]) as FemaleImpl;
		val MaleImpl old_son = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Chris Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_dau = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Daria Meier"; birthday = null]) as FemaleImpl;

		val FemaleImpl new_dau = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Daniela Meier"; birthday = null]) as FemaleImpl;

		// Check correct setup				
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(5, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(4, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);

		val personListBefore = resourceAt(PERSONS_MODEL).contents.get(0).eContents;
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_dad)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_mom)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_son)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_dau)], true)
		println("precondition done")

		// ===== ACTUAL TEST =====
		// Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [ x |
				x.lastName.equals("Meier") && x.daughters.exists[y|y.firstName.equals("Daria")]
			]
			val daughterToChange = selectedFamily.daughters.findFirst[x|x.firstName.equals("Daria")]
			daughterToChange.firstName = "Daniela"
		]
		println("propagation done")

		logger.info("\n\n===Daughter START===\n")
		resourceAt(PERSONS_MODEL).allContents.forEach[x, i|logger.info(i + ": " + x.toString())]
		logger.info("\n===END===\n\n")

		// Check correct execution	
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(5, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(4, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);

		val personListAfter = resourceAt(PERSONS_MODEL).contents.get(0).eContents;
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, old_dad)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, old_mom)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, old_son)], true)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, old_dau)], false)
		assertEquals(personListAfter.exists[x|equalPersons(x as PersonImpl, new_dau)], true)

	}

	@Test
	def void testInsertDadIfDadAlreadyExists_DiscardChanges() {
		this.createFamilyBeforeTesting();
		val MaleImpl old_dad = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Anton Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_mom = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Berta Meier"; birthday = null]) as FemaleImpl;
		val MaleImpl old_son = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Chris Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_dau = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Daria Meier"; birthday = null]) as FemaleImpl;

		val MaleImpl replacing_dad = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "RepDad Meier"; birthday = null]) as MaleImpl;

		println("\n\n===testInsertDadIfDadAlreadyExists - Preparation===\n")
		resourceAt(PERSONS_MODEL).allContents.forEach[x, i|println(i + ": " + x.toString())]
		println("\n===END===\n\n")

		// Check correct setup				
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(5, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(4, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);

		val personListBefore = resourceAt(PERSONS_MODEL).contents.get(0).eContents;
		assertEquals(true, personListBefore.exists[x|equalPersons(x as PersonImpl, old_dad)])
		assertEquals(true, personListBefore.exists[x|equalPersons(x as PersonImpl, old_mom)])
		assertEquals(true, personListBefore.exists[x|equalPersons(x as PersonImpl, old_son)])
		assertEquals(true, personListBefore.exists[x|equalPersons(x as PersonImpl, old_dau)])
		println("precondition done")

		// ===== ACTUAL TEST =====
		userInteraction.addNextSingleSelection(0)

		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val fam = families.findFirst[x|x.lastName.equals("Meier")]
			fam.father = FamiliesFactory.eINSTANCE.createMember => [firstName = "RepDad"; familyFather = fam]
		]

		// Check correct execution	
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		val personListAfter0 = resourceAt(PERSONS_MODEL).contents.get(0).eContents;
		assertEquals(personListAfter0.exists[x|equalPersons(x as PersonImpl, old_mom)], true)
		assertEquals(personListAfter0.exists[x|equalPersons(x as PersonImpl, old_son)], true)
		assertEquals(personListAfter0.exists[x|equalPersons(x as PersonImpl, old_dau)], true)

		logger.info("\n\n===testInsertDadIfDadAlreadyExists - Preparation===\n")
		resourceAt(PERSONS_MODEL).allContents.forEach[x, i|logger.info(i + ": " + x.toString())]
		logger.info("\n===END===\n\n")

		assertEquals(5, resourceAt(PERSONS_MODEL).allContents.size);
		assertEquals(4, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);
		assertEquals(personListAfter0.exists[x|equalPersons(x as PersonImpl, old_dad)], true)
		assertEquals(personListAfter0.exists[x|equalPersons(x as PersonImpl, replacing_dad)], false)
	}

	@Test
	def void testInsertDadIfDadAlreadyExists_ReplaceExisting() {
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		val MaleImpl old_dad = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Anton Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_mom = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Berta Meier"; birthday = null]) as FemaleImpl;
		val MaleImpl old_son = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Chris Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_dau = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Daria Meier"; birthday = null]) as FemaleImpl;

		val MaleImpl replacing_dad = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "RepDad Meier"; birthday = null]) as MaleImpl;
		val MaleImpl parallel_dad = (PersonsFactory.eINSTANCE.createMale => [
			fullName = "ParDad Meier_2";
			birthday = null
		]) as MaleImpl;

		println("\n\n===testInsertDadIfDadAlreadyExists - Preparation===\n")
		resourceAt(PERSONS_MODEL).allContents.forEach[x, i|println(i + ": " + x.toString())]
		println("\n===END===\n\n")

		// Check correct setup				
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(5, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(4, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);

		val personListBefore = resourceAt(PERSONS_MODEL).contents.get(0).eContents;
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_dad)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_mom)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_son)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_dau)], true)
		println("precondition done")

		// ===== ACTUAL TEST =====
		userInteraction.addNextSingleSelection(1)
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val fam = families.findFirst[x|x.lastName.equals("Meier")]
			fam.father = FamiliesFactory.eINSTANCE.createMember => [firstName = "RepDad"; familyFather = fam]
		]
		// Check correct execution	
		println("\n\n===testInsertDadIfDadAlreadyExists - Part2 propagation Done===\n")
		resourceAt(PERSONS_MODEL).allContents.forEach[x, i|println(i + ": " + x.toString())]
		println("\n===END===\n\n")

		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		val personListAfter1 = resourceAt(PERSONS_MODEL).contents.get(0).eContents;
		assertEquals(personListAfter1.exists[x|equalPersons(x as PersonImpl, old_mom)], true)
		assertEquals(personListAfter1.exists[x|equalPersons(x as PersonImpl, old_son)], true)
		assertEquals(personListAfter1.exists[x|equalPersons(x as PersonImpl, old_dau)], true)

		assertEquals(5, resourceAt(PERSONS_MODEL).allContents.size);
		assertEquals(4, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);
		assertEquals(personListAfter1.exists[x|equalPersons(x as PersonImpl, old_dad)], false)
		assertEquals(personListAfter1.exists[x|equalPersons(x as PersonImpl, replacing_dad)], true)
		assertEquals(personListAfter1.exists[x|equalPersons(x as PersonImpl, parallel_dad)], false)
	}

	@Test
	def void testInsertDadIfDadAlreadyExists_MoveToNewFamily() {
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		val MaleImpl old_dad = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Anton Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_mom = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Berta Meier"; birthday = null]) as FemaleImpl;
		val MaleImpl old_son = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Chris Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_dau = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Daria Meier"; birthday = null]) as FemaleImpl;

		val MaleImpl parallel_dad = (PersonsFactory.eINSTANCE.createMale => [
			fullName = "ParDad Meier_2";
			birthday = null
		]) as MaleImpl;

		// Check correct setup				
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(5, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(4, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);

		val personListBefore = resourceAt(PERSONS_MODEL).contents.get(0).eContents;
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_dad)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_mom)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_son)], true)
		assertEquals(personListBefore.exists[x|equalPersons(x as PersonImpl, old_dau)], true)

		dprint(FamilyRegister.from(FAMILIES_MODEL), PersonRegister.from(PERSONS_MODEL))

		// ===== ACTUAL TEST =====
		userInteraction.addNextSingleSelection(2)
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val fam = families.findFirst[x|x.lastName.equals("Meier")]
			fam.father = FamiliesFactory.eINSTANCE.createMember => [firstName = "ParDad"; familyFather = fam]
		]

		dprint(FamilyRegister.from(FAMILIES_MODEL), PersonRegister.from(PERSONS_MODEL))

		// Check correct execution	
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		val personListAfter2 = resourceAt(PERSONS_MODEL).contents.get(0).eContents;
		assertEquals(personListAfter2.exists[x|equalPersons(x as PersonImpl, old_mom)], true)
		assertEquals(personListAfter2.exists[x|equalPersons(x as PersonImpl, old_son)], true)
		assertEquals(personListAfter2.exists[x|equalPersons(x as PersonImpl, old_dau)], true)

		assertEquals(6, resourceAt(PERSONS_MODEL).allContents.size);
		assertEquals(5, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);
		assertEquals(personListAfter2.exists[x|equalPersons(x as PersonImpl, old_dad)], true)
		assertEquals(personListAfter2.exists[x|equalPersons(x as PersonImpl, parallel_dad)], true)
	}

	@Test
	def void testDeleteFamily() {
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		val MaleImpl old_dad = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Anton Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_mom = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Berta Meier"; birthday = null]) as FemaleImpl;
		val MaleImpl old_son = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Chris Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_dau = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Daria Meier"; birthday = null]) as FemaleImpl;

		// Check correct setup				
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(5, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(4, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);

		val personListBefore = resourceAt(PERSONS_MODEL).contents.get(0).eContents;
		assertEquals(true, personListBefore.exists[x|equalPersons(x as PersonImpl, old_dad)])
		assertEquals(true, personListBefore.exists[x|equalPersons(x as PersonImpl, old_mom)])
		assertEquals(true, personListBefore.exists[x|equalPersons(x as PersonImpl, old_son)])
		assertEquals(true, personListBefore.exists[x|equalPersons(x as PersonImpl, old_dau)])

		dprint(FamilyRegister.from(FAMILIES_MODEL), PersonRegister.from(PERSONS_MODEL))

		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families.removeIf([x|x.lastName.equals("Meier")])
		]

		dprint(FamilyRegister.from(FAMILIES_MODEL), PersonRegister.from(PERSONS_MODEL))

		// Check correct execution	
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		val personListAfter2 = resourceAt(PERSONS_MODEL).contents.get(0).eContents;
		assertEquals(personListAfter2.exists[x|equalPersons(x as PersonImpl, old_mom)], false)
		assertEquals(personListAfter2.exists[x|equalPersons(x as PersonImpl, old_son)], false)
		assertEquals(personListAfter2.exists[x|equalPersons(x as PersonImpl, old_dau)], false)
		assertEquals(personListAfter2.exists[x|equalPersons(x as PersonImpl, old_dad)], false)
		assertEquals(1, resourceAt(PERSONS_MODEL).allContents.size);
		assertEquals(0, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);
	}

	@Test
	def void testDeleteFamilyRegister() {
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		val MaleImpl old_dad = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Anton Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_mom = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Berta Meier"; birthday = null]) as FemaleImpl;
		val MaleImpl old_son = (PersonsFactory.eINSTANCE.createMale =>
			[fullName = "Chris Meier"; birthday = null]) as MaleImpl;
		val FemaleImpl old_dau = (PersonsFactory.eINSTANCE.createFemale =>
			[fullName = "Daria Meier"; birthday = null]) as FemaleImpl;

		// Check correct setup				
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(5, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(4, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);

		val personListBefore = resourceAt(PERSONS_MODEL).contents.get(0).eContents;
		assertEquals(true, personListBefore.exists[x|equalPersons(x as PersonImpl, old_dad)])
		assertEquals(true, personListBefore.exists[x|equalPersons(x as PersonImpl, old_mom)])
		assertEquals(true, personListBefore.exists[x|equalPersons(x as PersonImpl, old_son)])
		assertEquals(true, personListBefore.exists[x|equalPersons(x as PersonImpl, old_dau)])

		dprint()

		resourceAt(FAMILIES_MODEL).propagate[contents.clear()]

		assertThat(resourceAt(FAMILIES_MODEL), not(exists))
		assertThat(resourceAt(PERSONS_MODEL), not(exists))
	}

	def dprint() {
		dprint(FamilyRegister.from(FAMILIES_MODEL), PersonRegister.from(PERSONS_MODEL))
	}

	def dprint(FamilyRegister fr, PersonRegister pr) {
		var String fs = ''
		var String ps = ''
		if (fr != null)
			fs = '''FamilyRegister «IF fr.id != null»«fr.id»«ELSE»<no id>«ENDIF»
«FOR f : fr.families SEPARATOR '\n'»	Family «f.lastName»
«IF f.father != null»		Father «f.father.firstName»«ENDIF»
«IF f.mother != null»		Mother «f.mother.firstName»«ENDIF»
«FOR s : f.sons BEFORE '		Sons (' SEPARATOR ', ' AFTER ')'»«s.firstName»«ENDFOR»
«FOR d : f.daughters BEFORE '		Daughters (' SEPARATOR ', ' AFTER ')'»«d.firstName»«ENDFOR»«ENDFOR»''';
		if (pr != null)
			ps = '''PersonRegister «IF pr.id != null»«pr.id»«ELSE»<no id>«ENDIF»
«FOR p : pr.persons»
«IF p instanceof Male»	Male: «ELSE»	Female: «ENDIF»«p.fullName» («IF p.birthday != null»«p.birthday»«ELSE»-«ENDIF»)
«ENDFOR»''';
		logger.debug('\n' + fs + '\n' + ps)
	}
}
