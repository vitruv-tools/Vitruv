package tools.vitruv.framework.vsum.filtered.util;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;

import registryoffice.RegistryOffice;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.correspondence.CorrespondenceModel;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.propagation.ResourceAccess;
import tools.vitruv.framework.propagation.impl.AbstractChangePropagationSpecification;
import tools.vitruv.framework.userinteraction.UserInteractionFactory;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.views.ViewTypeFactory;
import tools.vitruv.framework.vsum.VirtualModel;
import tools.vitruv.framework.vsum.VirtualModelBuilder;
import tools.vitruv.framework.vsum.accesscontrolsystem.RuleDatabase;
import tools.vitruv.framework.vsum.accesscontrolsystem.accesscontrol.OperationAccessRightEvaluator;
import tools.vitruv.framework.vsum.filtered.FilteredVirtualModelImpl;
import tools.vitruv.framework.vsum.filtered.tests.FilteredVirtualModelImplComplexTest;
import tools.vitruv.framework.vsum.filtered.tests.FilteredVirtualModelImplTest;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.framework.vsum.internal.VirtualModelImpl;
import tools.vitruv.testutils.domains.DomainModelCreators;
import tools.vitruv.testutils.metamodels.RegistryOfficeCreators;
import tools.vitruv.testutils.metamodels.UmlMockupCreators;

public final class Util {

	private Util() {
		throw new AssertionError("Don't instantiate Utility classes!");
	}

	public static VirtualModelImpl createBasicVirtualModel() {

		VitruvDomain exampleDomain = DomainModelCreators.getDomain(new RegistryOfficeCreators());
		VitruvDomain accesscontrolsystemDomain = new AccessControlSystemDomain();
		VitruvDomain umlMockupDomain = DomainModelCreators.getDomain(new UmlMockupCreators());
		return (VirtualModelImpl) new VirtualModelBuilder().withStorageFolder(Path.of(new File("").getAbsolutePath()))
				.withDomain(exampleDomain).withDomain(umlMockupDomain).withDomain(accesscontrolsystemDomain)
				.withChangePropagationSpecification(
						new AbstractChangePropagationSpecification(exampleDomain, exampleDomain) {

							@Override
							public boolean doesHandleChange(EChange change, CorrespondenceModel correspondenceModel) {
								// TODO Auto-generated method stub
								return false;
							}

							@Override
							public void propagateChange(EChange change, CorrespondenceModel correspondenceModel,
									ResourceAccess resourceAccess) {
								// TODO Auto-generated method stub

							}

						})

				.withUserInteractor(UserInteractionFactory.instance.createUserInteractor(
						UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null)))
				.buildAndInitialize();

	}

	public static RuleDatabase getRuleDatabase(ResourceSet resourceSet) {
		Optional<Resource> ruleDatabase = resourceSet.getResources().stream()
				.filter(t -> !t.getContents().isEmpty() && (t.getContents().get(0) instanceof RuleDatabase))
				.findFirst();
		if (ruleDatabase.isPresent()) {
			return (RuleDatabase) ruleDatabase.get().getContents().get(0);
		}
		return null;
	}

	public static final CommittableView createView(VirtualModel virtualModel) {
		ViewType viewType = ViewTypeFactory.createIdentityMappingViewType("myviewtype");
		ViewSelector selector = virtualModel.createSelector(viewType);
		selector.getSelectableElements().forEach(element -> selector.setSelected(element, true));
		return selector.createView().withChangeRecordingTrait();
	}

	public static final RegistryOffice getRegistryOffice(View view) {
		return view.getRootObjects(RegistryOffice.class).iterator().next();
	}

	public static final String randomName() {
		return new Random().ints('a', 'z' + 1).limit(5)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
	}

	public static final FilteredVirtualModelImpl constructFilteredVirtualModelAfterRootRegistration(ResourceSet set) {
		InternalVirtualModel vmi = Util.createBasicVirtualModel();
		CommittableView view = Util.createView(vmi);
		Resource model = set.getResources().get(0);
		view.registerRoot(model.getContents().get(0), model.getURI());
		view.commitChangesAndUpdate();
		return new FilteredVirtualModelImpl(vmi, set.getResources().size() == 2 ? (RuleDatabase) set.getResources().get(1).getContents().get(0) : null,
				List.of(0), OperationAccessRightEvaluator.create());
	}
	
	public static final FilteredVirtualModelImpl constructFilteredVirtualModelBeforeRootRegistration(ResourceSet set) {
		InternalVirtualModel vmi = Util.createBasicVirtualModel();
		RuleDatabase ruleDatabase = set.getResources().size() == 2
				? (RuleDatabase) set.getResources().get(1).getContents().get(0)
				: null;
		EcoreUtil.resolveAll(set);
		FilteredVirtualModelImpl impl = new FilteredVirtualModelImpl(vmi, ruleDatabase, List.of(0),
				OperationAccessRightEvaluator.create());
		CommittableView view = Util.createView(impl);
		Resource model = set.getResources().get(0);
		view.registerRoot(model.getContents().get(0), model.getURI());
		view.commitChanges();
		return impl;
	}

	public static final void createTempModelFile() {
		createTempFile(FilteredVirtualModelImplComplexTest.MODEL_SUFFIX,
				FilteredVirtualModelImplTest.ORIGINAL_FILE_NAME, FilteredVirtualModelImplTest.TEMP_FILE_NAME);
	}

	public static final void createTempACSFile() {
		createTempFile(FilteredVirtualModelImplComplexTest.ACS_SUFFIX,
				FilteredVirtualModelImplComplexTest.ORIGINAL_ACS_NAME,
				FilteredVirtualModelImplComplexTest.TEMP_ACS_NAME);
	}

	public static final ResourceSet load(String exampleFileName, String accessControlsystemFileName) {
		ResourceSet set = new ResourceSetImpl();
		set.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new EcoreResourceFactoryImpl());
		set.getResource(
				URI.createFileURI(new File("").getAbsolutePath() + "/resources/" + exampleFileName + ".registryoffice"),
				true);
		set.getResource(URI.createFileURI(
				new File("").getAbsolutePath() + "/resources/" + accessControlsystemFileName + ".accesscontrolsystem"),
				true);
		return set;
	}

	public static final String createTempFile(String suffix, String originalName, String tempName) {
		String original = new File("").getAbsolutePath() + "/resources/" + originalName + suffix;
		String copy = new File("").getAbsolutePath() + "/resources/" + tempName + suffix;
		try {
			Files.copy(Paths.get(original), Paths.get(copy), REPLACE_EXISTING);
		} catch (IOException e) {
			assert (false);
		}
		return copy;
	}

	public static final void removeTemporaryFiles() {
		try (Stream<Path> paths = Files.walk(Paths.get((new File("").getAbsolutePath() + "/resources/")))) {
			paths.filter(Files::isRegularFile)
					.filter(path -> path.getFileName().toString().contains(FilteredVirtualModelImplComplexTest.TEMP))
					.forEach(path -> path.toFile().delete());
		} catch (IOException e) {
			e.printStackTrace();
		}
		String modelsFile = new File("").getAbsolutePath() + "/vsum/models.models";
		Paths.get(modelsFile).toFile().delete();
		String correspondencesFile = new File("").getAbsolutePath() + "/vsum/correspondences.correspondence";
		Paths.get(correspondencesFile).toFile().delete();
	}

	/**
	 * 
	 * @param model the model where changes are performed on
	 * @param view  the view that was changed
	 * @return an extracted RegistryOffice
	 */
	public static final RegistryOffice updateOffice(VirtualModel model, CommittableView view) {
		view.commitChanges();
//		view = Util.createView(model);
		return Util.getRegistryOffice(view);
	}

}
