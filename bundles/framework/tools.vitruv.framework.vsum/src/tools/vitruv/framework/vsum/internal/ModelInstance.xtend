package tools.vitruv.framework.vsum.internal

import org.apache.log4j.Logger
import org.eclipse.emf.ecore.resource.Resource
import static com.google.common.base.Preconditions.checkArgument
import org.eclipse.xtend.lib.annotations.Accessors
import java.io.IOException
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.util.URI

class ModelInstance {
	static val LOGGER = Logger.getLogger(ModelInstance)
	@Accessors(PUBLIC_GETTER)
	Resource resource
	
	new(Resource resource) {
		checkArgument(resource !== null, "cannot create a model instance for a null resource")
		this.resource = resource
		LOGGER.debug('''Create model instance for resource with URI: «URI»''')
	}
	
	def URI getURI() {
		return resource.URI
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
			resource.save(null)
			resource.modified = false
		} catch (IOException e) {
			LOGGER.error('''Model could not be saved: «URI»''', e)
			throw new IllegalStateException('''Could not save URI «URI»''', e)
		}
	}

	def void delete() {
		LOGGER.debug('''Delete resource: «resource»''')
		try {
			resource.delete(null)
		} catch (IOException e) {
			LOGGER.error('''Deletion of resource «resource» did not work.''', e)
			throw new IllegalStateException('''Could not delete URI «URI»''', e)
		}
	}

}
