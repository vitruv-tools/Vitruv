package tools.vitruv.framework.views;

import java.util.Collection;

import org.eclipse.emf.ecore.resource.Resource;

import tools.vitruv.change.atomic.uuid.UuidResolver;

/**
 * A view source giving access to the underlying source models of the view.
 */
public interface ViewSource {
	/**
	 * Returns {@link Resource}s representing the source models of a view to be
	 * instantiated.
	 * 
	 * @return {@link Resource}s as the sources of a view
	 */
	Collection<Resource> getViewSourceModels();

	/**
	 * Returns the {@link UuidResolver} associated with the resources in this view
	 * source.
	 * 
	 * @return the {@link UuidResolver} of this view source.
	 */
	public UuidResolver getUuidResolver();
}
