package tools.vitruv.framework.util.datatypes

import org.apache.log4j.Logger
import org.eclipse.emf.ecore.resource.Resource
import static com.google.common.base.Preconditions.checkArgument
import org.eclipse.xtend.lib.annotations.Accessors
import static extension tools.vitruv.framework.util.bridges.EcoreResourceBridge.saveResource
import java.io.IOException
import org.eclipse.emf.ecore.EObject

class ModelInstance extends AbstractURIHaving {
	static val LOGGER = Logger.getLogger(ModelInstance)
	@Accessors(PUBLIC_GETTER)
	Resource resource

	new(Resource resource) {
		super(VURI.getInstance(resource))
		checkArgument(resource !== null, "cannot create a model instance at the URI %s for a null resource", URI)
		this.resource = resource
		LOGGER.debug('''Create model instance for resource with URI: «URI.getEMFUri()»''')
	}

	def void addRoot(EObject root) {
		resource.contents += root
		resource.modified = true
		LOGGER.debug('''Add root to resource: «resource»''')
	}

	def void markModified() {
		resource.modified = true
	}

	def boolean isEmpty() {
		resource.contents.isEmpty
	}

	def void save() {
		if (!resource.modified) {
			return
		}
		LOGGER.debug('''Save resource: «resource»''')
		try {
			resource.saveResource
			resource.modified = false
		} catch (IOException e) {
			LOGGER.error('''Model could not be saved: «URI»''', e)
			throw new IllegalStateException('''Could not save VURI «URI»''', e)
		}
	}

	def void delete() {
		LOGGER.debug('''Delete resource: «resource»''')
		try {
			resource.delete(null)
		} catch (IOException e) {
			LOGGER.error('''Deletion of resource «resource» did not work.''', e)
			throw new IllegalStateException('''Could not delete VURI «URI»''', e)
		}
	}

}
