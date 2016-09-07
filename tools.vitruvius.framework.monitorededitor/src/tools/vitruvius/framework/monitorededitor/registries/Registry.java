package tools.vitruvius.framework.monitorededitor.registries;

import java.util.Collection;

/**
 * Registery for multiple elements.
 * 
 * @author Stephan Seifermann
 *
 * @param <T>
 *            The type of the elements.
 */
public interface Registry<T> {

    /**
     * Registers the given element.
     * 
     * @param element
     *            The element to register.
     */
    public void registerElement(T element);

    /**
     * Unregisters the given element.
     * 
     * @param element
     *            The element to unregister.
     */
    public void unregisterElement(T element);

    /**
     * Unregisters all elements. This method has the same effect as calling the unregister method
     * for every single element.
     */
    public void unregisterAllElements();

    /**
     * @return All registered elements.
     */
    public Collection<T> getRegisteredElements();

}
