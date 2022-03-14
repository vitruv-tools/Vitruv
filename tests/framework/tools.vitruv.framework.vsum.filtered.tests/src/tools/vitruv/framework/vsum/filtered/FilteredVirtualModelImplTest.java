package tools.vitruv.framework.vsum.filtered;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import registryoffice.Child;
import registryoffice.RegistryOffice;
import registryoffice.RegistryofficeFactory;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.vsum.VirtualModel;
import tools.vitruv.framework.vsum.filtered.util.Util;

public class FilteredVirtualModelImplTest {

	public static final String ORIGINAL_FILE_NAME = "Example";
	public static final String TEMP_FILE_NAME = "Example_temp";

	/**
	 * Recreates the temporal model file as it is modified by some of the tests and
	 * removes the models.models file in case any Models are linked there.
	 */
	@BeforeEach
	final void createTemp() {
		Paths.get(new File("").getAbsolutePath() + "/vsum/models.models").toFile().delete();
		Util.createTempModelFile();
	}

	@AfterAll
	final static void clear() {
		Util.removeTemporaryFiles();
	}

	@Test
	@DisplayName("ExplicitAccessOneParent - Tests if the filtering with explicit access to one parent and forbidden access to all other elements works as expected")
	final void testFilteringExplicitAccessOneParent() {
		VirtualModel model = Util.constructFilteredVirtualModelAfterRootRegistration(Util.load(TEMP_FILE_NAME, "ExplicitAccessOneParent"));
		CommittableView view = Util.createView(model);
		RegistryOffice office = Util.getRegistryOffice(view);

		assertEquals(1, office.getParent().size());
		assertEquals("Albert", office.getParent().get(0).getName());
		assertEquals(0, office.getChild().size());
	}

	@Test
	@DisplayName("ExplicitAccessOneParent - Changes an attribute while gaining access on only the changed element")
	final void changeAttribute() {
		VirtualModel model = Util.constructFilteredVirtualModelAfterRootRegistration(Util.load(TEMP_FILE_NAME, "ExplicitAccessOneParent"));
		CommittableView view = Util.createView(model);
		RegistryOffice office = Util.getRegistryOffice(view);

		// TODO discuss why the generated changes are
		// tools.vitruv.framework.change.echange.eobject.impl.CreateEObjectImpl@42122f28
		// (idAttributeValue: null, affectedEObjectID: cache:/0),
		// tools.vitruv.framework.change.echange.feature.reference.impl.InsertEReferenceImpl@253ab1d3
		// (affectedEObjectID: containment) (index: 4) (newValueID: cache:/0, wasUnset:
		// false),
		// tools.vitruv.framework.change.echange.feature.attribute.impl.ReplaceSingleValuedEAttributeImpl@6f538dd7
		// (affectedEObjectID: containment/@child.4) (isUnset: false) (newValue: crkdg,
		// wasUnset: false, oldValue: null)
		String newName = Util.randomName();
		office.getParent().get(0).setName(newName);
		office = Util.updateOffice(model, view);

		assertEquals(1, office.getParent().size());
		assertEquals(0, office.getChild().size());
		assertEquals(newName, office.getParent().get(0).getName());
	}
	
	@Test
	@DisplayName("AllAccessThroughInheritance - Tests if the filtering with access to all elements throught access to the containment works")
	final void testFilteringAllAccessThroughInheritance() {
		VirtualModel model = Util.constructFilteredVirtualModelAfterRootRegistration(Util.load(TEMP_FILE_NAME, "AllAccessThroughInheritance"));
		CommittableView view = Util.createView(model);
		RegistryOffice office = Util.getRegistryOffice(view);

		// Tests if some parts of the data are available
		assertEquals(4, office.getParent().size());
		assertEquals("Albert", office.getParent().get(0).getName());
		assertEquals(List.of(office.getChild().get(0), office.getChild().get(1)), office.getParent().get(0).getChild());
		assertEquals(List.of(office.getParent().get(1)), office.getChild().get(2).getParent());
		assertEquals(4, office.getChild().size());
	}

	@Test
	@DisplayName("AllAccessThroughInheritance- Adds a new child and checks if it is inserted at the end of the list while gaining full access through inheritance")
	final void addChildAllAccess() {
		VirtualModel model = Util.constructFilteredVirtualModelAfterRootRegistration(Util.load(TEMP_FILE_NAME, "AllAccessThroughInheritance"));
		CommittableView view = Util.createView(model);
		RegistryOffice office = Util.getRegistryOffice(view);
		final int childCount = office.getChild().size();

		Child child = RegistryofficeFactory.eINSTANCE.createChild();
		final String newName = Util.randomName();
		child.setName(newName);
		office.getChild().add(child);
		office = Util.updateOffice(model, view);

		assertEquals(childCount + 1, office.getChild().size());
		assertEquals(newName, office.getChild().get(office.getChild().size() - 1).getName());
	}
	
	@Test
	@DisplayName("ExplicitAccessOneParentOneChild- Tests if the filtering with access to one child and one parent works")
	final void testFilteringExplicitAccessOneParentOneChild() {
		VirtualModel model = Util.constructFilteredVirtualModelAfterRootRegistration(Util.load(TEMP_FILE_NAME, "ExplicitAccessOneParentOneChild"));
		CommittableView view = Util.createView(model);
		RegistryOffice office = Util.getRegistryOffice(view);

		assertEquals(1, office.getParent().size());
		assertEquals("Albert", office.getParent().get(0).getName());
		assertEquals(1, office.getChild().size());
		assertEquals("Einstein", office.getChild().get(0).getName());
	}

	@Test
	@DisplayName("ExplicitAccessOneParentOneChild - Adds a new child and checks if it is inserted at the end of the list while part explicit access")
	final void addChildPartAccess() {
		VirtualModel model = Util.constructFilteredVirtualModelAfterRootRegistration(Util.load(TEMP_FILE_NAME, "ExplicitAccessOneParentOneChild"));
		CommittableView view = Util.createView(model);
		RegistryOffice office = Util.getRegistryOffice(view);
		final int childCount = office.getChild().size();
		
		Child child = RegistryofficeFactory.eINSTANCE.createChild();
		final String newName = Util.randomName();
		child.setName(newName);
		office.getChild().add(child);
		office = Util.updateOffice(model, view);

		assertEquals(childCount + 1, office.getChild().size());
		assertEquals(newName, office.getChild().get(office.getChild().size() - 1).getName());
	}
	
	@Test
	@DisplayName("ExplicitAccessSecondParentFirstChild - Tests if the filtering with access to one child and one parent (second one in unfiltered) works")
	final void testFilteringExplicitAccessSecondParentFirstChild() {
		VirtualModel model = Util.constructFilteredVirtualModelAfterRootRegistration(Util.load(TEMP_FILE_NAME, "ExplicitAccessSecondParentFirstChild"));
		CommittableView view = Util.createView(model);
		RegistryOffice office = Util.getRegistryOffice(view);

		assertEquals(1, office.getParent().size());
		assertEquals("Berta", office.getParent().get(0).getName());
		assertEquals(1, office.getChild().size());
		assertEquals("Einstein", office.getChild().get(0).getName());
	}

	@Disabled
	@Test
	@DisplayName("ExplicitAccessSecondParentFirstChild - ")
	final void removeChildPartAccess() {
		VirtualModel model = Util.constructFilteredVirtualModelAfterRootRegistration(Util.load(TEMP_FILE_NAME, "ExplicitAccessSecondParentFirstChild"));
		CommittableView view = Util.createView(model);
		RegistryOffice office = Util.getRegistryOffice(view);
		
		office.getChild().remove(0);
		office = Util.updateOffice(model, view);

		assertEquals(0, office.getChild().size());
		assertEquals("Berta", office.getParent().get(0).getName());
	}
	
	@Test
	@DisplayName("ExplicitAccessSecondParentSecondChild - Tests if the filtering with access to one child (second one in unfiltered) and one parent (second one in unfiltered) works")
	final void testFilteringExplicitAccessSecondParentSecondChild() {
		VirtualModel model = Util.constructFilteredVirtualModelAfterRootRegistration(Util.load(TEMP_FILE_NAME, "ExplicitAccessSecondParentSecondChild"));
		CommittableView view = Util.createView(model);
		RegistryOffice office = Util.getRegistryOffice(view);

		assertEquals(1, office.getParent().size());
		assertEquals("Berta", office.getParent().get(0).getName());
		assertEquals(1, office.getChild().size());
		assertEquals("Findus", office.getChild().get(0).getName());
	}

	@Test
	@DisplayName("ExplicitAccessSecondParentSecondChild - ")
	final void removeChildPartAccess2() {
		VirtualModel model = Util.constructFilteredVirtualModelAfterRootRegistration(Util.load(TEMP_FILE_NAME, "ExplicitAccessSecondParentSecondChild"));
		CommittableView view = Util.createView(model);
		RegistryOffice office = Util.getRegistryOffice(view);

		office.getChild().remove(0);
		office = Util.updateOffice(model, view);

		assertEquals(0, office.getChild().size());
		assertEquals("Berta", office.getParent().get(0).getName());
	}
	
	@Test
	@DisplayName("ExplicitAccessSecondParent123Childs - Tests if the filtering with access to three childs and one parent (second one in unfiltered) works")
	final void testFilteringExplicitAccessSecondParent123Childs() {
		VirtualModel model = Util.constructFilteredVirtualModelAfterRootRegistration(Util.load(TEMP_FILE_NAME, "ExplicitAccessSecondParent123Childs"));
		CommittableView view = Util.createView(model);
		RegistryOffice office = Util.getRegistryOffice(view);

		assertEquals(3, office.getChild().size());
		assertEquals("Einstein", office.getChild().get(0).getName());
		assertEquals("Findus", office.getChild().get(1).getName());
		assertEquals("Grimhild", office.getChild().get(2).getName());
		assertEquals(1, office.getParent().size());
		assertEquals("Berta", office.getParent().get(0).getName());
	}

	@Disabled
	@Test
	@DisplayName("ExplicitAccessSecondParent123Childs - ")
	final void removeChildPartAccessMultipleChilds() {
		VirtualModel model = Util.constructFilteredVirtualModelAfterRootRegistration(Util.load(TEMP_FILE_NAME, "ExplicitAccessSecondParent123Childs"));
		CommittableView view = Util.createView(model);
		RegistryOffice office = Util.getRegistryOffice(view);
		
		office.getChild().remove(2);
		office = Util.updateOffice(model, view);

		assertEquals(2, office.getChild().size());
		assertEquals("Einstein", office.getChild().get(0).getName());
		assertEquals("Findus", office.getChild().get(1).getName());
		assertEquals(1, office.getParent().size());
		assertEquals("Berta", office.getParent().get(0).getName());
	}

	@Test
	@DisplayName("ExplicitAccessOneParentOneChild - Removes the reference to an existing parent")
	final void removeChildReference() {
		VirtualModel model = Util.constructFilteredVirtualModelAfterRootRegistration(Util.load(TEMP_FILE_NAME, "ExplicitAccessOneParentOneChild"));
		CommittableView view = Util.createView(model);
		RegistryOffice office = Util.getRegistryOffice(view);

		assertEquals(1, office.getChild().get(0).getParent().size());
		assertEquals(List.of(office.getParent().get(0)), office.getChild().get(0).getParent());
		office.getChild().get(0).getParent().clear();
		office = Util.updateOffice(model, view);

		assertEquals(0, office.getChild().get(0).getParent().size());
		assertEquals(0, office.getParent().get(0).getChild().size());
	}

	@Test
	@DisplayName("ExplicitAccessSecondParent123Childs - add a reference to a child for a parent")
	final void addChildReference() {
		VirtualModel model = Util.constructFilteredVirtualModelAfterRootRegistration(Util.load(TEMP_FILE_NAME, "ExplicitAccessSecondParent123Childs"));
		CommittableView view = Util.createView(model);
		RegistryOffice office = Util.getRegistryOffice(view);
		
		assertEquals(3, office.getChild().size());
		assertEquals("Einstein", office.getChild().get(0).getName());
		assertEquals("Findus", office.getChild().get(1).getName());
		assertEquals("Grimhild", office.getChild().get(2).getName());
		assertEquals(1, office.getParent().size());
		assertEquals("Berta", office.getParent().get(0).getName());
		office.getParent().get(0).getChild().add(office.getChild().get(1));
		office = Util.updateOffice(model, view);

		assertEquals(3, office.getChild().size());
		assertEquals("Einstein", office.getChild().get(0).getName());
		assertEquals("Findus", office.getChild().get(1).getName());
		assertEquals("Berta", office.getParent().get(0).getName());
		assertEquals("Findus", office.getParent().get(0).getChild().get(2).getName());
	}

}
