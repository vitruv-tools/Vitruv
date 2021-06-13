package tools.vitruv.variability.vave.tests;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.common.base.Preconditions;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil;
import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory;
import edu.kit.ipd.sdq.metamodels.families.Family;
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import edu.kit.ipd.sdq.metamodels.families.Member;
import edu.kit.ipd.sdq.metamodels.persons.Male;
import edu.kit.ipd.sdq.metamodels.persons.PersonRegister;
import edu.kit.ipd.sdq.metamodels.persons.PersonsFactory;
import tools.vitruv.applications.demo.familiespersons.FamiliesPersonsApplication;
import tools.vitruv.domains.demo.families.FamiliesDomainProvider;
import tools.vitruv.domains.demo.persons.PersonsDomainProvider;
import tools.vitruv.framework.change.description.PropagatedChange;
import tools.vitruv.framework.change.recording.ChangeRecorder;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.propagation.ChangePropagationSpecification;
import tools.vitruv.testutils.ChangePublishingTestView;
import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.TestLogging;
import tools.vitruv.testutils.TestProject;
import tools.vitruv.testutils.TestProjectManager;
import tools.vitruv.testutils.UriMode;
import tools.vitruv.testutils.domains.DomainUtil;
import tools.vitruv.testutils.matchers.ModelMatchers;
import tools.vitruv.variability.vave.VirtualVaVeModel;
import tools.vitruv.variability.vave.VirtualProductModel;
import tools.vitruv.variability.vave.impl.VirtualVaVeModeIImpl;

@ExtendWith({ TestProjectManager.class, TestLogging.class, RegisterMetamodelsInStandalone.class })
public class VaveFamiliesPersonsTest {
	private static final String FAMILY_NAME = "Mustermann";
	private static final String FIRST_NAME_FATHER = "Max";
	private static final String FIRST_NAME_SON = "Sohn";
	private static final String FIRST_NAME_DAUGHTER = "Tochter";
	private static final String FIRST_NAME_MOTHER = "Erika";

	private static final String MALE_PERSON_NAME = "Max Mustermann";
	private static final String SECOND_MALE_PERSON_NAME = "Bernd Mustermann";
	private static final String FEMALE_PERSON_NAME = "Erika Mustermann";

	private static final Path PERSONS_MODEL = DomainUtil.getModelFileName("model/persons", new PersonsDomainProvider());
	private static final Path FAMILIES_MODEL = DomainUtil.getModelFileName("model/model", new FamiliesDomainProvider());

	private ResourceSet resourceSet;
	private ChangeRecorder changeRecorder;

	private Path persistenceDirectory;
	private UriMode uriMode;

	private VirtualVaVeModel vave;
	private VirtualProductModel virtualModel;

	private Resource resourceAt(final Path viewRelativePath) {
		return this.resourceAt(this.getUri(viewRelativePath));
	}

	private Resource resourceAt(final URI modelUri) {
		return ResourceSetUtil.loadOrCreateResource(this.resourceSet, modelUri);
	}

	private URI getUri(final Path viewRelativePath) {
		Preconditions.checkArgument((viewRelativePath != null), "The viewRelativePath must not be null!");
		Preconditions.checkArgument(!IterableExtensions.isEmpty(viewRelativePath),
				"The viewRelativePath must not be empty!");
		if (this.uriMode == null)
			return null;
		else if (this.uriMode == UriMode.PLATFORM_URIS)
			return URI.createPlatformResourceURI(IterableExtensions
					.join(this.persistenceDirectory.getFileName().resolve(viewRelativePath).normalize(), "/"), true);
		else if (this.uriMode == UriMode.FILE_URIS)
			return URIUtil.createFileURI(this.persistenceDirectory.resolve(viewRelativePath).normalize().toFile());
		else
			return null;
	}

	private <T extends EObject> T from(final Class<T> clazz, final Path viewRelativePath) {
		return this.<T>from(clazz, this.getUri(viewRelativePath));
	}

	private <T extends EObject> T from(final Class<T> clazz, final URI modelUri) {
		final Resource resource = this.resourceSet.getResource(modelUri, true);
		return this.<T>from(clazz, resource);
	}

	private <T extends EObject> T from(final Class<T> clazz, final Resource resource) {
		Preconditions.checkState(!resource.getContents().isEmpty(),
				"The resource at " + resource.getURI() + " is empty!");
		return clazz.cast(resource.getContents().get(0));
	}

	private <T extends Notifier> T startRecordingChanges(final T notifier) {
		Preconditions.checkState(this.changeRecorder.isRecording(), "This test view has already been closed!");
		Preconditions.checkArgument((notifier != null), "The object to record changes of is null!");
		this.changeRecorder.addToRecording(notifier);
		return notifier;
	}

	private <T extends Notifier> T stopRecordingChanges(final T notifier) {
		Preconditions.checkState(this.changeRecorder.isRecording(), "This test view has already been closed!");
		Preconditions.checkArgument((notifier != null), "The object to stop recording changes of is null!");
		this.changeRecorder.removeFromRecording(notifier);
		return notifier;
	}

	private <T extends Notifier> List<PropagatedChange> propagate(final T notifier, final Consumer<T> consumer)
			throws IOException {
		Resource toSave = this.determineResource(notifier);

		try {
			this.<T>startRecordingChanges(notifier);
			consumer.accept(notifier);
		} finally {
			this.<T>stopRecordingChanges(notifier);
		}

		if (toSave == null)
			toSave = this.determineResource(notifier);

		if (toSave != null) {
			this.saveOrDelete(toSave);
		}

		final List<PropagatedChange> delegateChanges = CollectionLiterals.<PropagatedChange>emptyList();

		this.changeRecorder.endRecording();
		List<PropagatedChange> propagationResult = this.virtualModel.propagateChange(this.changeRecorder.getChange());
		this.renewResourceCache();
		final List<PropagatedChange> ourChanges = propagationResult;
		this.changeRecorder.beginRecording();
		return ChangePublishingTestView.<PropagatedChange>operator_plus(delegateChanges, ourChanges);
	}

	private void renewResourceCache() {
		this.resourceSet.getResources().clear();
	}

	private Resource determineResource(final Notifier notifier) {
		if (notifier instanceof Resource)
			return ((Resource) notifier);
		else if (notifier instanceof EObject)
			return ((EObject) notifier).eResource();
		else
			return null;
	}

	private void saveOrDelete(final Resource resource) throws IOException {
		if (resource.getContents().isEmpty())
			resource.delete(CollectionLiterals.<Object, Object>emptyMap());
		else
			resource.save(CollectionLiterals.<Object, Object>emptyMap());
	}

	// --------------------------------------

	private Family ourFamily() {
		Family _createFamily = FamiliesFactory.eINSTANCE.createFamily();
		final Procedure1<Family> _function = (Family it) -> {
			it.setLastName(FAMILY_NAME);
		};
		return ObjectExtensions.<Family>operator_doubleArrow(_createFamily, _function);
	}

	@BeforeEach
	public void setupVaveAndcreateRoot(@TestProject final Path testProjectPath) throws Exception {
//		ChangePropagationSpecification changePropagationSpecification = new FamiliesToPersonsChangePropagationSpecification();
//		Set<ChangePropagationSpecification> changePropagationSpecifications = new HashSet<>();
//		changePropagationSpecifications.add(changePropagationSpecification);
//
//		Set<VitruvDomain> domains = new HashSet<>();
//		domains.add(changePropagationSpecification.getSourceDomain());
//		domains.add(changePropagationSpecification.getTargetDomain());

		FamiliesPersonsApplication fpa = new FamiliesPersonsApplication();
		Set<VitruvDomain> domains = fpa.getVitruvDomains();
		Set<ChangePropagationSpecification> changePropagationSpecifications = fpa.getChangePropagationSpecifications();

		this.vave = new VirtualVaVeModeIImpl(domains, changePropagationSpecifications, testProjectPath);

		this.virtualModel = vave.externalizeProduct(testProjectPath.resolve("vsum"), "");

		this.resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());

		this.persistenceDirectory = testProjectPath;
		this.uriMode = UriMode.FILE_URIS;

		this.changeRecorder = new ChangeRecorder(this.resourceSet);
		this.changeRecorder.beginRecording();

		this.<Resource>propagate(this.resourceAt(FAMILIES_MODEL), (Resource r) -> {
			r.getContents().add(FamiliesFactory.eINSTANCE.createFamilyRegister());
		});

		MatcherAssert.<Resource>assertThat(this.resourceAt(PERSONS_MODEL), ModelMatchers.exists());
	}

	@Test
	public void testCreateFamilyFather(@TestProject final Path testProjectPath) throws Exception {
		FamilyRegister register = this.from(FamilyRegister.class, FAMILIES_MODEL);

		this.propagate(register, (FamilyRegister f) -> {
			Family family = this.ourFamily();
			Member father = FamiliesFactory.eINSTANCE.createMember();
			father.setFirstName(FIRST_NAME_FATHER);
			father.setFamilyFather(family);
			family.setFather(father);
			f.getFamilies().add(family);
		});

		MatcherAssert.<Resource>assertThat(this.resourceAt(PERSONS_MODEL), ModelMatchers.exists());
	}

	@Test
	public void testCreateFamilySon(@TestProject final Path testProjectPath) throws Exception {
		FamilyRegister register = this.from(FamilyRegister.class, FAMILIES_MODEL);

		this.propagate(register, (FamilyRegister f) -> {
			Family family = this.ourFamily();
			Member son = FamiliesFactory.eINSTANCE.createMember();
			son.setFirstName(FIRST_NAME_SON);
			son.setFamilySon(family);
			family.getSons().add(son);
			f.getFamilies().add(family);
		});

		MatcherAssert.<Resource>assertThat(this.resourceAt(PERSONS_MODEL), ModelMatchers.exists());
	}

	@Test
	public void testCreateFamilyMother(@TestProject final Path testProjectPath) throws Exception {
		FamilyRegister register = this.from(FamilyRegister.class, FAMILIES_MODEL);

		this.propagate(register, (FamilyRegister f) -> {
			Family family = this.ourFamily();
			Member mother = FamiliesFactory.eINSTANCE.createMember();
			mother.setFirstName(FIRST_NAME_MOTHER);
			mother.setFamilyMother(family);
			family.setMother(mother);
			f.getFamilies().add(family);
		});

		MatcherAssert.<Resource>assertThat(this.resourceAt(PERSONS_MODEL), ModelMatchers.exists());
	}

	@Test
	public void testCreateFamilyDaughter(@TestProject final Path testProjectPath) throws Exception {
		FamilyRegister register = this.from(FamilyRegister.class, FAMILIES_MODEL);

		this.propagate(register, (FamilyRegister f) -> {
			Family family = this.ourFamily();
			Member daughter = FamiliesFactory.eINSTANCE.createMember();
			daughter.setFirstName(FIRST_NAME_DAUGHTER);
			daughter.setFamilyDaughter(family);
			family.getDaughters().add(daughter);
			f.getFamilies().add(family);
		});

		MatcherAssert.<Resource>assertThat(this.resourceAt(PERSONS_MODEL), ModelMatchers.exists());
	}

	@Test
	public void testDeleteMember(@TestProject final Path testProjectPath) throws Exception {
		FamilyRegister register = this.from(FamilyRegister.class, FAMILIES_MODEL);

		this.propagate(register, (FamilyRegister f) -> {
			Family family = this.ourFamily();
			Member daughter = FamiliesFactory.eINSTANCE.createMember();
			daughter.setFirstName(FIRST_NAME_DAUGHTER);
			daughter.setFamilyDaughter(family);
			family.getDaughters().add(daughter);
			f.getFamilies().add(family);
		});

		this.propagate(register, (FamilyRegister f) -> {
			f.getFamilies().get(0).getDaughters().remove(0);
		});

		MatcherAssert.<Resource>assertThat(this.resourceAt(PERSONS_MODEL), ModelMatchers.exists());
	}

//	@Test
//	def void testChangeFirstName() {
//		val daughter = FamiliesFactory.eINSTANCE.createMember
//		FamilyRegister.from(FAMILIES_MODEL).propagate [
//			val family = ourFamily()
//			families += family => [
//				daughters += daughter => [
//					firstName = FIRST_NAME_DAUGHTER
//				]
//			]
//		]
//		
//		FamilyRegister.from(FAMILIES_MODEL).families.claimOne.daughters.claimOne.propagate[firstName = FIRST_NAME_MOTHER]
//		val personsWithMothersName = PersonRegister.from(PERSONS_MODEL).persons.filter[fullName.split(" ").get(0) == FIRST_NAME_MOTHER]
//		assertEquals(1, personsWithMothersName.length) 
//	}

	@Test
	@Disabled("The personsToFamilies is broken")
	public void testCreateMalePerson() throws IOException {
		PersonRegister register = this.from(PersonRegister.class, PERSONS_MODEL);
		this.propagate(register, (PersonRegister f) -> {
			Male male = PersonsFactory.eINSTANCE.createMale();
			male.setFullName(MALE_PERSON_NAME);
			f.getPersons().add(male);
		});
		MatcherAssert.<Resource>assertThat(resourceAt(FAMILIES_MODEL), ModelMatchers.exists());
	}

//	@Test
////	@Disabled("The personsToFamilies is broken")
//	def void testCreateMale() {
//		PersonRegister.from(PERSONS_MODEL).propagate [
//			persons += PersonsFactory.eINSTANCE.createMale => [
//				fullName = MALE_PERSON_NAME
//			]
//		]
//
//		assertThat(resourceAt(FAMILIES_MODEL), exists);
//	}
//
//	@Test
////	@Disabled("The personsToFamilies is broken")
//	def void testCreateFemale() {
//		PersonRegister.from(PERSONS_MODEL).propagate [
//			persons += PersonsFactory.eINSTANCE.createFemale => [
//				fullName = FEMALE_PERSON_NAME
//			]
//		]
//
//		assertThat(resourceAt(FAMILIES_MODEL), exists);
//	}

}
