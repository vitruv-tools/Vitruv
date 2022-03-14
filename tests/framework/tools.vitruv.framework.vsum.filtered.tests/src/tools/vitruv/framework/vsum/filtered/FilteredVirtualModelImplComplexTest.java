package tools.vitruv.framework.vsum.filtered;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Paths;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.uml2.uml.Model;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import accesscontrolsystem.RuleDatabase;
import registryoffice.Child;
import registryoffice.RegistryOffice;
import registryoffice.RegistryofficeFactory;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.vsum.VirtualModel;
import tools.vitruv.framework.vsum.filtered.util.Util;

/**
 * Tests not only changes to the filtered model but also to the
 * accesscontrolsystem.
 * 
 * @author Thomas Weber
 *
 */
public class FilteredVirtualModelImplComplexTest {

	public static final String TEMP = "_temp";
	public static final String ORIGINAL_FILE_NAME = "Example";
	public static final String TEMP_FILE_NAME = ORIGINAL_FILE_NAME + TEMP;
	public static final String MODEL_SUFFIX = ".registryoffice";
	public static final String ORIGINAL_ACS_NAME = "ExplicitAccessOneParentOneChild";
	public static final String TEMP_ACS_NAME = ORIGINAL_ACS_NAME+ TEMP;
	public static final String ACS_SUFFIX = ".accesscontrolsystem";
	public static final String UML_MODEL = "orhanobut/uml/model";
	public static final String UML_MODEL_TEMP = UML_MODEL + TEMP;

	@BeforeEach
	final void createTemp() {
		// if a temporary file was created, delete it
		String modelsFile = new File("").getAbsolutePath() + "/vsum/models.models";
		Paths.get(modelsFile).toFile().delete();
		Util.createTempModelFile();
		Util.createTempACSFile();
	}
	
	@AfterAll
	static final void clear() {
		Util.removeTemporaryFiles();
	}
	
	@Disabled
	@Test
	@DisplayName("Adds and removes elements")
	final void addAndRemoveElements() {
		VirtualModel model = Util.constructFilteredVirtualModelBeforeRootRegistration(Util.load(TEMP_FILE_NAME, TEMP_ACS_NAME));
		CommittableView view = Util.createView(model);
		RegistryOffice office = Util.getRegistryOffice(view);

		Child child = RegistryofficeFactory.eINSTANCE.createChild();
		final String newName = "Gundula";
		child.setName(newName);

		office.getChild().add(child);
		office = Util.updateOffice(model, view);

		assertEquals(2, office.getChild().size());
		assertEquals(newName, office.getChild().get(1).getName());
		
		office.getChild().remove(1);
		
		office = Util.updateOffice(model, view);
		view = Util.createView(model);
		office = Util.getRegistryOffice(view);
		
		assertEquals(1, office.getChild().size());
		
		for(int i = 0; i < 4; i++) {
			child = RegistryofficeFactory.eINSTANCE.createChild();
			child.setName(newName + i);
			office.getChild().add(child);
		}
		office.getChild().remove(2);
		office = Util.updateOffice(model, view);
		
		assertEquals(4, office.getChild().size());
		assertEquals(newName+"0", office.getChild().get(1).getName());
		assertEquals(newName+"2", office.getChild().get(2).getName());
		
	}

	@Disabled
	@Test
	@DisplayName("Modifies the AccessControlSystem")
	final void modifyAccessControlSystem() {
		FilteredVirtualModelImpl model = Util.constructFilteredVirtualModelBeforeRootRegistration(Util.load(TEMP_FILE_NAME, TEMP_ACS_NAME));
		CommittableView view = Util.createView(model);
		RegistryOffice office = Util.getRegistryOffice(view);

		assertEquals(1, office.getChild().size());
		Child deleted = office.getChild().remove(0);
		office = Util.updateOffice(model, view);

		assertEquals(0, office.getChild().size());
		RuleDatabase db = model.getFilteredResourceSet().getRuleDatabase();
		assertFalse(db.getAccessrules().stream().anyMatch(it -> it.getElement().equals(deleted)));
	}

	@Disabled
	@Test
	@DisplayName("Loads a uml model")
	final void loadComplexModelsTest() {
		ResourceSet set = new ResourceSetImpl();
		set.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new EcoreResourceFactoryImpl());
		Util.createTempFile(UML_MODEL, UML_MODEL_TEMP, ".uml");
		String pathModel = new File("").getAbsolutePath() + "/resources/orhanobut/uml/model_temp.uml";
		Util.createTempFile("PartAccess", "PartAccess_temp", ".accesscontrolsystem");
		String pathACS = new File("").getAbsolutePath() + "/resources/PartAccess_temp.accesscontrolsystem";
		set.getResource(URI.createFileURI(pathModel), true);
		set.getResource(URI.createFileURI(pathACS), true);
		VirtualModel model = Util.constructFilteredVirtualModelBeforeRootRegistration(set);
		CommittableView view = Util.createView(model);
		
		assertTrue(view.getRootObjects().iterator().next().eContents().stream().filter(org.eclipse.uml2.uml.Class.class::isInstance)
				.noneMatch(element -> ((org.eclipse.uml2.uml.Class) element).getName().equals("AndroidLogAdapter")));

	}

	@Disabled
	@Test
	@DisplayName("Loads a uml model but without any access")
	final void loadComplexModelsTestNoAccess() {
		ResourceSet set = new ResourceSetImpl();
//		set.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new EcoreResourceFactoryImpl());
		String pathModel = Util.createTempFile(".uml", UML_MODEL, UML_MODEL_TEMP);
		String pathACS = Util.createTempFile(".accesscontrolsystem", "NoAccess", "NoAccess_temp");
		set.getResource(URI.createFileURI(pathModel), true);
		set.getResource(URI.createFileURI(pathACS), true);
		VirtualModel model = Util.constructFilteredVirtualModelBeforeRootRegistration(set);
		CommittableView view = Util.createView(model);

		assertFalse(view.getRootObjects().iterator().hasNext());
	}

	@Test
	@DisplayName("Loads a uml model with full access")
	final void loadComplexModelsTestFullAccess() {
		ResourceSet set = new ResourceSetImpl();
		set.getResource(URI.createFileURI(Util.createTempFile(".uml", UML_MODEL, UML_MODEL_TEMP)), true);
		FilteredVirtualModelImpl model = Util.constructFilteredVirtualModelBeforeRootRegistration(set);
		CommittableView view = Util.createView(model);
		
		assertTrue(new EcoreUtil.EqualityHelper().equals(view.getRootObjects(Model.class).iterator().next(),
				model.getUnfilteredViewSourceModels().iterator().next().getContents().get(0)));
	}

	@Test
	@DisplayName("Creates a new accesscontrolsystem with full access to all new elements")
	final void createAccessControlSystem() {
		ResourceSet set = new ResourceSetImpl();
		String pathModel = Util.createTempFile(".uml", "synthetic/model1", "synthetic/model1"+TEMP);
		set.getResource(URI.createFileURI(pathModel), true);
		FilteredVirtualModelImpl model = Util.constructFilteredVirtualModelBeforeRootRegistration(set);
		CommittableView view = Util.createView(model);
		
		
		assertTrue(new EcoreUtil.EqualityHelper().equals(view.getRootObjects(Model.class).iterator().next(),
				model.getUnfilteredViewSourceModels().iterator().next().getContents().get(0)));
	}

}
