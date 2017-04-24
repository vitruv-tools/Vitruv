package tools.vitruv.framework.tests.echange.command

import allElementTypes.AllElementTypesFactory
import allElementTypes.Root
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.edit.domain.EditingDomain
import org.junit.After
import org.junit.Before
import tools.vitruv.framework.change.echange.resolve.StagingArea
import tools.vitruv.framework.change.echange.util.EChangeUtil

/**
 * Abstract base class for the staging area commands tests.
 */
abstract class StagingAreaCommandTest extends CommandTest {
	protected var ResourceSet resourceSet = null
	protected var StagingArea stagingArea = null
	protected var Root object = null
	protected var Root object2 = null
	protected var EditingDomain editingDomain = null	
	
	@Before
	def public void beforeTest() {
		this.resourceSet = new ResourceSetImpl()
		this.stagingArea = StagingArea.getStagingArea(resourceSet)
		this.object = AllElementTypesFactory.eINSTANCE.createRoot
		this.object2 = AllElementTypesFactory.eINSTANCE.createRoot
		this.editingDomain = EChangeUtil.getEditingDomain(object)
	}
	
	@After
	def public void afterTest() {
		this.resourceSet = null
		this.stagingArea = null
		this.editingDomain = null
		this.object = null
		this.object2 = null
	}
}