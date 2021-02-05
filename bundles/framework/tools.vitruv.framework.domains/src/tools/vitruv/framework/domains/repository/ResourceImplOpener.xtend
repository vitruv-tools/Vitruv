package tools.vitruv.framework.domains.repository

import java.util.Map
import org.eclipse.emf.ecore.resource.impl.ResourceImpl
import edu.kit.ipd.sdq.activextendannotations.Lazy
import java.lang.reflect.Field

/**
 * Makes internals of {@link ResourceImpl} visible. Avoid using this.
 */
package class ResourceImplOpener {
	private new() {}
	
	@Lazy static val Field defaultLoadOptionsField = ResourceImpl.getDeclaredField("defaultLoadOptions") => [accessible = true]
	@Lazy static val Field defaultSaveOptionsField = ResourceImpl.getDeclaredField("defaultSaveOptions") => [accessible = true]
	 
	def package static getDefaultLoadOptions(ResourceImpl resource) {
		defaultLoadOptionsField.get(resource) as Map<Object, Object>
	}
	
	def package static setDefaultLoadOptions(ResourceImpl resource, Map<Object, Object> loadOptions) {
		defaultLoadOptionsField.set(resource, loadOptions)
	} 
	
	def package static getDefaultSaveOptions(ResourceImpl resource) {
		defaultSaveOptionsField.get(resource) as Map<Object, Object>
	}
	
	def package static setDefaultSaveOptions(ResourceImpl resource, Map<Object, Object> saveOptions) {
		defaultSaveOptionsField.set(resource, saveOptions)
	}
}
