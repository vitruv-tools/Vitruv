package tools.vitruv.framework.remote.common.util;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * Contains utility functions to work with {@link Resource}s.
 */
public class ResourceUtil {

    private ResourceUtil() throws InstantiationException {
        throw new InstantiationException("Cannot be instantiated");
    }

    /**
     * Serializes the given {@link Resource}.
     *
     * @param resource the resource to serialize
     * @return the string representation of the serialized resource.
     */
    public static String serialize(Resource resource) throws IOException {
        var outputStream = new ByteArrayOutputStream();
        resource.save(outputStream, Map.of(XMLResource.OPTION_PROCESS_DANGLING_HREF, XMLResource.OPTION_PROCESS_DANGLING_HREF_DISCARD));
        return outputStream.toString(StandardCharsets.UTF_8);
    }

    /**
     * Deserializes a {@link Resource}.
     *
     * @param uri            the uri of the resource
     * @param resourceString the string representation of the resource
     * @param parentSet      the parent {@link ResourceSet} of the resource
     * @return the deserialized {@link Resource}.
     */
    public static Resource deserialize(URI uri, String resourceString, ResourceSet parentSet) throws IOException {
        checkArgument(resourceString != null, "xmi resource string must not be null");
        checkArgument(parentSet != null, "parent resource set must not be null");

        var resource = parentSet.createResource(uri);
        var inputStream = new ByteArrayInputStream(resourceString.getBytes(StandardCharsets.UTF_8));
        resource.load(inputStream, Collections.EMPTY_MAP);
        return resource;
    }

    /**
     * Deserializes a {@link Resource}. Uses a new {@link ResourceSet} as parent set.
     *
     * @param uri the uri of the resource
     * @param res the string representation of the resource
     * @return the deserialized {@link Resource}.
     */
    public static Resource deserialize(URI uri, String res) throws IOException {
        return deserialize(uri, res, ResourceSetUtil.withGlobalFactories(new XMIResourceSetImpl()));
    }

    /**
     * Creates a {@link Resource} with the given {@link URI} and given content.
     *
     * @param uri       the uri of the resource
     * @param content   the content of the resource
     * @param parentSet the parent {@link ResourceSet} of the resource
     * @return the created {@link Resource}.
     */
    public static Resource createResourceWith(URI uri, Collection<? extends EObject> content, ResourceSet parentSet) {
        var resource = parentSet.createResource(uri);
        resource.getContents().addAll(content);
        return resource;
    }

    /**
     * Creates a {@link Resource} with the given {@link URI} and given content.
     * Uses a new {@link ResourceSet} as parent set.
     *
     * @param uri     the uri of the resource
     * @param content the content of the resource
     * @return the created {@link Resource}.
     */
    public static Resource createResourceWith(URI uri, Collection<? extends EObject> content) {
        return createResourceWith(uri, content, ResourceSetUtil.withGlobalFactories(new ResourceSetImpl()));
    }
}
