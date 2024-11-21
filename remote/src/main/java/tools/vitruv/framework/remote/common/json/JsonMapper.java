package tools.vitruv.framework.remote.common.json;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emfcloud.jackson.annotations.EcoreIdentityInfo;
import org.eclipse.emfcloud.jackson.databind.EMFContext;
import org.eclipse.emfcloud.jackson.module.EMFModule;
import org.eclipse.emfcloud.jackson.module.EMFModule.Feature;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.framework.remote.common.json.deserializer.ReferenceDeserializerModifier;
import tools.vitruv.framework.remote.common.json.deserializer.ResourceSetDeserializer;
import tools.vitruv.framework.remote.common.json.deserializer.VitruviusChangeDeserializer;
import tools.vitruv.framework.remote.common.json.serializer.ReferenceSerializerModifier;
import tools.vitruv.framework.remote.common.json.serializer.ResourceSetSerializer;
import tools.vitruv.framework.remote.common.json.serializer.VitruviusChangeSerializer;

/**
 * This mapper can be used to serialize objects and deserialize JSON in the context of Vitruvius.
 * It has custom De-/Serializers for {@link ResourceSet}s, {@link Resource}s and {@link VitruviusChange}s.
 */
public class JsonMapper {
    private final ObjectMapper mapper = new ObjectMapper();

    public JsonMapper(Path vsumPath) {
    	final var transformation = new IdTransformation(vsumPath);
    	
    	mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        var module = new EMFModule();
        
        //Register serializer
        module.addSerializer(ResourceSet.class, new ResourceSetSerializer(transformation));
        module.addSerializer(VitruviusChange.class, new VitruviusChangeSerializer());

        //Register deserializer
        module.addDeserializer(ResourceSet.class, new ResourceSetDeserializer(this, transformation));
        module.addDeserializer(VitruviusChange.class, new VitruviusChangeDeserializer(this, transformation));
        
        //Register modifiers for references to handle HierarichalId
        module.setSerializerModifier(new ReferenceSerializerModifier(transformation));
        module.setDeserializerModifier(new ReferenceDeserializerModifier(transformation));
        
	    //Use IDs to identify eObjects on client and server
	    module.configure(Feature.OPTION_USE_ID, true);
	    module.setIdentityInfo(new EcoreIdentityInfo("_id"));

        mapper.registerModule(module);
    }

    /**
     * Serializes the given object.
     *
     * @param obj The object to serialize.
     * @return The JSON or {@code null} if an {@link JsonProcessingException} occurred.
     */
    public String serialize(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    /**
     * Deserializes the given JSON string.
     *
     * @param <T> The type of the returned object.
     * @param json The JSON to deserialize.
     * @param clazz The class of the JSON type.
     * @return The object or {@code null} if an {@link JsonProcessingException} occurred.
     */
    public <T> T deserialize(String json, Class<T> clazz) throws JsonProcessingException {
        return mapper.reader().forType(clazz).readValue(json);
    }
    
    /**
     * Deserializes the given JSON node.
     * 
     * @param <T> The type of the returned object.
     * @param json The JSON node to deserialize.
     * @param clazz The class of the JSON type.
     * @return The object.
     * @throws JsonProcessingException If the JSON node cannot be processed.
     * @throws IOException If there is an IO exception during deserialization.
     */
    public <T> T deserialize(JsonNode json, Class<T> clazz) throws JsonProcessingException, IOException {
    	return mapper.reader().forType(clazz).readValue(json);
    }
    
    public Resource deserializeResource(String json, String uri, ResourceSet parentSet) throws JsonProcessingException {
    	return mapper.reader()
    			.withAttribute(EMFContext.Attributes.RESOURCE_SET, parentSet)
    			.withAttribute(EMFContext.Attributes.RESOURCE_URI, URI.createURI(uri))
    			.forType(Resource.class)
    			.readValue(json);
    }

    /**
     * Deserializes the given JSON array to a list.
     *
     * @param json The JSON array to deserialize.
     * @param clazz The class representing the JSON type of the objects in the JSON array.
     * @return The list of objects or {@code null} if an {@link JsonProcessingException} occurred.
     */
    public <T> List<T> deserializeArrayOf(String json, Class<T> clazz) throws JsonProcessingException {
        var javaType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
        return mapper.readValue(json, javaType);
    }
}
