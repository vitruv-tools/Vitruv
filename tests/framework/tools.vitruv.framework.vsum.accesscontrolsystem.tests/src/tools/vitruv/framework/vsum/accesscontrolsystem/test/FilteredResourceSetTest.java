/**
 * 
 */
package tools.vitruv.framework.vsum.accesscontrolsystem.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.junit.jupiter.api.Test;

import accesscontrol.OperationAccessRightEvaluator;
import accesscontrol.OperationAccessRightUtil;
import accesscontrol.ResourceSetFilter;
import accesscontrol.internal.FilteredResourceSet;
import accesscontrol.internal.OperationAccessRightEvaluatorImpl;
import accesscontrolsystem.RuleDatabase;
import registryoffice.RegistryOffice;

/**
 * @author Thomas Weber (thomas.weber@student.kit.edu)
 *
 */
class FilteredResourceSetTest {

	@Test
	final void fullAccess() {
		List<FilterOperationResult> filterOperationResult = load("AllAccess", "Example");
		assertTrue(FilterOperationResult.filteredUnfilteredEqual(filterOperationResult));
	}

	@Test
	final void fullAccess2() {
		Collection<FilterOperationResult> filterOperationResult = load("AllAccess", "Example", "Example");
		assertTrue(FilterOperationResult.filteredUnfilteredEqual(filterOperationResult));
	}

	@Test
	final void fullAccessInheritance() {
		Collection<FilterOperationResult> filterOperationResult = load("AllAccessInheritance", "Example");
		assertTrue(FilterOperationResult.filteredUnfilteredEqual(filterOperationResult));
	}

	@Test
	final void empty() {
		List<FilterOperationResult> filterOperationResult = load("Empty", "Empty");
		assertTrue(FilterOperationResult.filteredAreEmpty(filterOperationResult));
	}

	/**
	 * If the accesscontrolsystem is loaded twice, emf ignores the second operation
	 * and so does the accesscontrolsystem.
	 */
	@Test
	final void accessControlSystem1loadedTwice() {
		Collection<FilterOperationResult> filterOperationResult = load("AllAccess", "AllAccess.accesscontrolsystem",
				"Example");
		assertTrue(FilterOperationResult.filteredUnfilteredEqual(filterOperationResult));
	}

	@Test
	final void noAccess() {
		// no access rules at all
		Collection<FilterOperationResult> filterOperationResult = load("NoAccessrules", "Example");
		assertTrue(FilterOperationResult.filteredAreEmpty(filterOperationResult));
	}

	@Test
	final void noAccessRuleSubsumptions() {
		// access rules for all elements exist but the role does not have a subsumption
		// to gain access for the root
		Collection<FilterOperationResult> filterOperationResult = load("NoAccessNoSubsumption", "Example");
		assertTrue(FilterOperationResult.filteredAreEmpty(filterOperationResult));
	}

	@Test
	final void partAccess() {
		// access rules for the root and one parent exist
		List<FilterOperationResult> filterOperationResult = load("PartAccess", "Example");
		assertEquals(1, filterOperationResult.get(0).filtered.size());
		assertEquals(1, ((RegistryOffice) filterOperationResult.get(0).filtered.get(0)).getParent().size());
		assertEquals("Albert",
				((RegistryOffice) filterOperationResult.get(0).filtered.get(0)).getParent().get(0).getName());
		assertEquals(0, ((RegistryOffice) filterOperationResult.get(0).filtered.get(0)).getChild().size());
	}

	@Test
	final void referenceAccess() {
		// access rules for the root and one parent exist
		List<FilterOperationResult> filterOperationResult = load("PartAccess2", "Example");
		assertEquals(1, filterOperationResult.get(0).filtered.size());
		assertEquals(1, ((RegistryOffice) filterOperationResult.get(0).filtered.get(0)).getParent().size());
		assertEquals("Albert",
				((RegistryOffice) filterOperationResult.get(0).filtered.get(0)).getParent().get(0).getName());
		assertEquals(1, ((RegistryOffice) filterOperationResult.get(0).filtered.get(0)).getChild().size());
		assertEquals("Einstein",
				((RegistryOffice) filterOperationResult.get(0).filtered.get(0)).getChild().get(0).getName());
		assertEquals(1,
				((RegistryOffice) filterOperationResult.get(0).filtered.get(0)).getChild().get(0).getParent().size());
	}

	@Test
	final void partAccess2() {
		// access rules for one parent and one child exist
		List<FilterOperationResult> filterOperationResult = load("PartAccess2", "Example");
		assertEquals(1, filterOperationResult.get(0).filtered.size());
		assertEquals(1, ((RegistryOffice) filterOperationResult.get(0).filtered.get(0)).getParent().size());
		assertEquals("Albert",
				((RegistryOffice) filterOperationResult.get(0).filtered.get(0)).getParent().get(0).getName());
		assertEquals(1, ((RegistryOffice) filterOperationResult.get(0).filtered.get(0)).getChild().size());
		assertEquals("Einstein",
				((RegistryOffice) filterOperationResult.get(0).filtered.get(0)).getChild().get(0).getName());
	}

	@Test
	final void partAccessModifying() {
		// access rules for one parent and one child exist
		// full access for child 1 and only read access for child 2
		List<FilterOperationResult> filterOperationResult = load("PartAccessModifying", "Example");
		assertEquals(1, filterOperationResult.get(0).filtered.size());
		assertEquals(1, ((RegistryOffice) filterOperationResult.get(0).filtered.get(0)).getParent().size());
		assertEquals("Albert",
				((RegistryOffice) filterOperationResult.get(0).filtered.get(0)).getParent().get(0).getName());
		assertEquals(2, ((RegistryOffice) filterOperationResult.get(0).filtered.get(0)).getChild().size());
		assertEquals("Findus",
				((RegistryOffice) filterOperationResult.get(0).filtered.get(0)).getChild().get(0).getName());
		assertEquals("Grimhild",
				((RegistryOffice) filterOperationResult.get(0).filtered.get(0)).getChild().get(1).getName());
		assertTrue(set.canModify(
				Set.of(((RegistryOffice) filterOperationResult.get(0).filtered.get(0)).getParent().get(0)),
				List.of(OperationAccessRightUtil.allowWrite())));
		assertTrue(
				set.canModify(Set.of(((RegistryOffice) filterOperationResult.get(0).filtered.get(0)).getChild().get(0)),
						List.of(OperationAccessRightUtil.allowWrite())));
		assertFalse(
				set.canModify(Set.of(((RegistryOffice) filterOperationResult.get(0).filtered.get(0)).getChild().get(1)),
						List.of(OperationAccessRightUtil.allowWrite())));
	}

	@Test
	final void models2access2part() {
		List<FilterOperationResult> filterOperationResult = load("PartAccess2Modells", "Example", "Example2");
		assertEquals(1, filterOperationResult.get(0).filtered.size());
		assertEquals(1, ((RegistryOffice) filterOperationResult.get(0).filtered.get(0)).getParent().size());
		assertEquals("Albert",
				((RegistryOffice) filterOperationResult.get(0).filtered.get(0)).getParent().get(0).getName());
		assertEquals(0, ((RegistryOffice) filterOperationResult.get(0).filtered.get(0)).getChild().size());
		assertEquals(1, filterOperationResult.get(1).filtered.size());
		assertEquals(1, ((RegistryOffice) filterOperationResult.get(1).filtered.get(0)).getParent().size());
		assertEquals("Berta",
				((RegistryOffice) filterOperationResult.get(1).filtered.get(0)).getParent().get(0).getName());
		assertEquals(0, ((RegistryOffice) filterOperationResult.get(1).filtered.get(0)).getChild().size());
	}

	@Test
	final void models2access1() {
		List<FilterOperationResult> fifilterOperationResultltered = load("AllAccess", "Example", "Example2");
		assertTrue(fifilterOperationResultltered.get(0).unfilteredEqualsFiltered());
		assertTrue(fifilterOperationResultltered.get(1).filteredEmpty());
	}

	@Test
	final void models2access2() {
		List<FilterOperationResult> filterOperationResult = load("AllAccess2", "Example", "Example2");
		assertTrue(filterOperationResult.get(0).unfilteredEqualsFiltered());
		assertTrue(filterOperationResult.get(1).unfilteredEqualsFiltered());
	}

	@Test
	final void models2part2() {
		List<FilterOperationResult> filterOperationResult = load("AllAccess2", "Example", "Example2");
		assertTrue(filterOperationResult.get(0).unfilteredEqualsFiltered());
		assertTrue(filterOperationResult.get(1).unfilteredEqualsFiltered());
	}

	private static ResourceSetFilter set;
	private static OperationAccessRightEvaluator evaluator = new OperationAccessRightEvaluatorImpl();

	private List<FilterOperationResult> load(String accessControlsystemFileName, String... exampleFileNames) {

		String path = new File("").getAbsolutePath() + "/resources/" + accessControlsystemFileName
				+ ".accesscontrolsystem";

		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new EcoreResourceFactoryImpl());

		for (String exampleFileName : exampleFileNames) {
			// if the given model already has a suffix, dont add the example suffix
			String pathModel = exampleFileName.contains(".")
					? new File("").getAbsolutePath() + "/resources/" + exampleFileName
					: new File("").getAbsolutePath() + "/resources/" + exampleFileName + ".registryoffice";
			resourceSet.getResource(URI.createFileURI(pathModel), true);
		}
		RuleDatabase ruleDatabase = (RuleDatabase) resourceSet.getResource(URI.createFileURI(path), true).getContents()
				.get(0);

		ResourceSetFilter filteredResourceSet = new FilteredResourceSet(ruleDatabase, List.of(0), evaluator);
		List<FilterOperationResult> result = buildCorrespondences(filteredResourceSet, resourceSet);
		set = filteredResourceSet;
		return result;
	}

	private List<FilterOperationResult> buildCorrespondences(ResourceSetFilter resourceSetFilter,
			ResourceSet toFilter) {
		List<FilterOperationResult> result = new ArrayList<>();
		for (Resource filtered : resourceSetFilter.filter(toFilter, List.of(OperationAccessRightUtil.allowRead())).getResources()) {
			var unfiltered = resourceSetFilter.getSourceResource(filtered);
			result.add(new FilterOperationResult(unfiltered.getContents(), filtered.getContents()));
		}
		return result;
	}

	private static final class FilterOperationResult {
		public FilterOperationResult(EList<EObject> unfiltered, EList<EObject> filtered) {
			this.unfiltered = unfiltered;
			this.filtered = filtered;
		}

		List<EObject> unfiltered;
		List<EObject> filtered;

		public boolean unfilteredEqualsFiltered() {
			return new EqualityHelper().equals(unfiltered, filtered);
		}

		public boolean filteredEmpty() {
			return filtered.isEmpty();
		}

		public static boolean filteredAreEmpty(Collection<FilterOperationResult> filterOperationResults) {
			return filterOperationResults.stream().map(result -> result.filteredEmpty()).reduce((t, u) -> t && u).get();
		}

		public static boolean filteredUnfilteredEqual(Collection<FilterOperationResult> filterOperationResults) {
			return filterOperationResults.stream().map(result -> result.unfilteredEqualsFiltered())
					.reduce((t, u) -> t && u).get();
		}
	}

}
