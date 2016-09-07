package tools.vitruvius.framework.monitorededitor.registries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Base class for usual registries, which allow registering and unregistering of elements.
 *
 * @author Stephan Seifermann
 *
 * @param <T>
 *            The type of the elements.
 */
public abstract class RegistryBase<T> implements Registry<T> {

    protected final Collection<T> elements = new HashSet<T>();

    @Override
    public void registerElement(final T element) {
        this.elements.add(element);
    }

    @Override
    public void unregisterElement(final T element) {
        this.elements.remove(element);
    }

    @Override
    public void unregisterAllElements() {
        Collection<T> elementsCopy = new ArrayList<T>(this.elements);
        for (T element : elementsCopy) {
            unregisterElement(element);
        }
    }

    @Override
    public Collection<T> getRegisteredElements() {
        return Collections.unmodifiableCollection(this.elements);
    }

}
