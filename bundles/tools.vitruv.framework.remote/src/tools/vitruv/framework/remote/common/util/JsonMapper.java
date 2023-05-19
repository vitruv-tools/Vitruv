package tools.vitruv.framework.remote.common.util;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.framework.remote.common.deserializer.ResourceDeserializer;
import tools.vitruv.framework.remote.common.deserializer.ResourceSetDeserializer;
import tools.vitruv.framework.remote.common.deserializer.VitruviusChangeDeserializer;
import tools.vitruv.framework.remote.common.serializer.ResourceSerializer;
import tools.vitruv.framework.remote.common.serializer.ResourceSetSerializer;
import tools.vitruv.framework.remote.common.serializer.VitruviusChangeSerializer;

/**
 * This mapper can be used to serialize objects and deserialize json in the context of vitruv.
 * It has custom De-/Serializers for {@link ResourceSet}s, {@link Resource}s and {@link VitruviusChange}s.
 */
public class JsonMapper {

    private JsonMapper() throws InstantiationException {
        throw new InstantiationException("Cannot be instantiated");
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
     * Serializes the given object.
     *
     * @param obj the object to serialize
     * @return the json or {@code null}, if an {@link JsonProcessingException} occurred.
     */
    public static String serialize(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    /**
     * Deserializes the given json string.
     *
     * @param json  the json to deserialize
     * @param clazz the class of the jsons type.
     * @return the object or {@code null}, if an {@link JsonProcessingException} occurred.
     */
    public static <T> T deserialize(String json, Class<T> clazz) throws JsonProcessingException {
        return mapper.readValue(json, clazz);
    }

    /**
     * Deserializes the given json array to a list.
     *
     * @param json  the json array to deserialize
     * @param clazz the class representing the json type of the objects in the json array
     * @return the list of objects or {@code null}, if an {@link JsonProcessingException} occurred.
     */
    public static <T> List<T> deserializeArrayOf(String json, Class<T> clazz) throws JsonProcessingException {
            var javaType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
            return mapper.readValue(json, javaType);
    }
}
