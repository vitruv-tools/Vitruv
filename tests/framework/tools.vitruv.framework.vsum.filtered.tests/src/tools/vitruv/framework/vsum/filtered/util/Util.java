package tools.vitruv.framework.vsum.filtered.util;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;

import accesscontrol.operationaccessright.OperationAccessRightUtil;
import accesscontrolsystem.RuleDatabase;
import registryoffice.RegistryOffice;
import tools.vitruv.domains.java.JavaDomainProvider;
import tools.vitruv.domains.uml.UmlDomainProvider;
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
import tools.vitruv.framework.views.changederivation.DefaultStateBasedChangeResolutionStrategy;
import tools.vitruv.framework.vsum.VirtualModel;
import tools.vitruv.framework.vsum.VirtualModelBuilder;
import tools.vitruv.framework.vsum.filtered.FilteredVirtualModelImpl;
import tools.vitruv.framework.vsum.filtered.FilteredVirtualModelImplTest;
import tools.vitruv.framework.vsum.filtered.FilteredVirtualModelImplTestComplex;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.framework.vsum.internal.VirtualModelImpl;
import tools.vitruv.testutils.domains.DomainModelCreators;
import tools.vitruv.testutils.metamodels.RegistryOfficeCreators;

public final class Util {

	private Util() {
		throw new AssertionError("Don't instantiate Utility classes!");
	}

	public static VirtualModelImpl createBasicVirtualModel() {

		VitruvDomain exampleDomain = DomainModelCreators.getDomain(new RegistryOfficeCreators());
		VitruvDomain accesscontrolsystemDomain = new AccessControlSystemDomain();
		VitruvDomain umlDomain = new UmlDomainProvider().getDomain();
		VitruvDomain javaDomain = new JavaDomainProvider().getDomain();
		VirtualModelImpl virtualModel = (VirtualModelImpl) new VirtualModelBuilder()
				.withStorageFolder(Path.of(new File("").getAbsolutePath())).withDomain(exampleDomain)
				.withDomain(javaDomain).withChangePropagationSpecification(new AbstractChangePropagationSpecification(javaDomain, javaDomain) {
					
					@Override
					public void propagateChange(EChange arg0, CorrespondenceModel arg1, ResourceAccess arg2) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public boolean doesHandleChange(EChange arg0, CorrespondenceModel arg1) {
						// TODO Auto-generated method stub
						return false;
					}
				})
				.withDomain(umlDomain).withChangePropagationSpecification(new AbstractChangePropagationSpecification(umlDomain, umlDomain) {
					
					@Override
					public void propagateChange(EChange change, CorrespondenceModel correspondenceModel,
							ResourceAccess resourceAccess) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public boolean doesHandleChange(EChange change, CorrespondenceModel correspondenceModel) {
						// TODO Auto-generated method stub
						return false;
					}
				})
				.withDomain(accesscontrolsystemDomain).withChangePropagationSpecification(
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
		return virtualModel;

	}

	public static RuleDatabase getRuleDatabase(ResourceSet resourceSet) {
		Optional<Resource> ruleDatabase = resourceSet.getResources().stream()
		.filter(t -> !t.getContents().isEmpty() && (t.getContents().get(0) instanceof RuleDatabase)).findFirst();
		if (ruleDatabase.isPresent()) {
			return (RuleDatabase) ruleDatabase.get().getContents().get(0);
		}
		return null;
	}
	
	public static ViewType viewType = ViewTypeFactory.createIdentityMappingViewType("myviewtype");

	public static final CommittableView createView(VirtualModel virtualModel) {
		ViewSelector selector = virtualModel.createSelector(viewType);
		selector.getSelectableElements().forEach(element -> selector.setSelected(element, true));
		return selector.createView().withChangeDerivingTrait(new DefaultStateBasedChangeResolutionStrategy());
	}
	
	public static final RegistryOffice getRegistryOffice(View view) {
		return view.getRootObjects(RegistryOffice.class).iterator().next();
	}
	
	public static String randomName() {
		return new Random().ints('a', 'z' + 1).limit(5)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
	}
	
	public static FilteredVirtualModelImpl constructBasicVirtualModel(ResourceSet set) {
		InternalVirtualModel vmi = Util.createBasicVirtualModel();
		CommittableView view = Util.createView(vmi);
		Resource model = set.getResources().get(0);
		view.registerRoot(model.getContents().get(0), model.getURI());
		view.commitChangesAndUpdate();
		FilteredVirtualModelImpl impl = new FilteredVirtualModelImpl(vmi, (RuleDatabase) set.getResources().get(1).getContents().get(0), List.of(0),
				new OperationAccessRightUtil());
		return impl;
	}
	
	public static void createTempModelFile() {
		createTempFile(FilteredVirtualModelImplTestComplex.MODEL_SUFFIX, FilteredVirtualModelImplTest.ORIGINAL_FILE_NAME, FilteredVirtualModelImplTest.TEMP_FILE_NAME);
	}
	
	public static void createTempACSFile() {
		createTempFile(FilteredVirtualModelImplTestComplex.ACS_SUFFIX, FilteredVirtualModelImplTestComplex.ORIGINAL_ACS_NAME, FilteredVirtualModelImplTestComplex.TEMP_ACS_NAME);
	}
	
	public static ResourceSet load(String exampleFileName, String accessControlsystemFileName) {
		ResourceSet set = new ResourceSetImpl();
		set.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new EcoreResourceFactoryImpl());
		String pathModel = new File("").getAbsolutePath() + "/resources/" + exampleFileName + ".registryoffice";
		set.getResource(URI.createFileURI(pathModel), true);
		createTempFile(".accesscontrolsystem", accessControlsystemFileName, accessControlsystemFileName + "_temp");
		set.getResource(URI.createFileURI(
				new File("").getAbsolutePath() + "/resources/" + accessControlsystemFileName + "_temp.accesscontrolsystem"),
				true);
		return set;
	}
	

	private static void createTempFile(String suffix, String originalName, String tempName) {
		String original = new File("").getAbsolutePath() + "/resources/" + originalName + suffix;
		String copy = new File("").getAbsolutePath() + "/resources/" + tempName + suffix;
		try {
			Files.copy(Paths.get(original), Paths.get(copy), REPLACE_EXISTING);
		} catch (IOException e) {
			assert (false);
		}
	}
	
	public static void removeTemporaryFiles() {
		Arrays.stream(
				new File(new File("").getAbsolutePath() + "/resources/").listFiles((dir, name) -> name.contains("temp")))
				.forEach(t -> {
					t.delete();
				});
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
	public static RegistryOffice updateOffice(VirtualModel model, CommittableView view) {
		RegistryOffice office;
		view.commitChangesAndUpdate();

		view = Util.createView(model);
		office = Util.getRegistryOffice(view);
		return office;
	}
	
}
