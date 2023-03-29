package tools.vitruv.remote.common.util;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.remote.common.deserializer.ResourceDeserializer;
import tools.vitruv.remote.common.deserializer.ResourceSetDeserializer;
import tools.vitruv.remote.common.deserializer.VitruviusChangeDeserializer;
import tools.vitruv.remote.common.serializer.ResourceSerializer;
import tools.vitruv.remote.common.serializer.ResourceSetSerializer;
import tools.vitruv.remote.common.serializer.VitruviusChangeSerializer;

/**
 * This mapper can be used to serialize objects and deserialize json in the context of vitruv.
 * It has custom De-/Serializers for {@link ResourceSet}s, {@link Resource}s and {@link VitruviusChange}s.
 */
public class JsonMapper {

    private JsonMapper() {
        throw new IllegalAccessError();
    }

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        var module = new SimpleModule();

        //Register serializer
        module.addSerializer(ResourceSet.class, new ResourceSetSerializer());
        module.addSerializer(Resource.class, new ResourceSerializer());
        module.addSerializer(VitruviusChange.class, new VitruviusChangeSerializer());

        //Register deserializer
        module.addDeserializer(ResourceSet.class, new ResourceSetDeserializer());
        module.addDeserializer(Resource.class, new ResourceDeserializer());
        module.addDeserializer(VitruviusChange.class, new VitruviusChangeDeserializer());

        mapper.registerModule(module);
    }

    /**
     * Serialize the given object.
     *
     * @param obj the object to serialize
     * @return the json or {@code null}, if an {@link JsonProcessingException} occurred.
     */
    public static String serialize(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    /**
     * Deserializes the given json.
     *
     * @param json  the json to deserialize
     * @param clazz the class of the jsons type.
     * @return the object or {@code null}, if an {@link JsonProcessingException} occurred.
     */
    public static <T> T deserialize(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    /**
     * Deserializes the given json array.
     *
     * @param json  the json array to deserialize
     * @param clazz the class of the json type of the objects in the array
     * @return the list of objects or {@code null}, if an {@link JsonProcessingException} occurred.
     */
    public static <T> List<T> deserializeListOf(String json, Class<T> clazz) {
        try {
            var javaType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
            return mapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
