package edu.kit.ipd.sdq.vitruvius.framework.contracts;

public class VitruviusConstants {

    private static String EXTENSION_PROPERTY_NAME = "provider";
    private static String PLATFORM_RESOURCE_PREFIX = "platform:/resource/";

    /**
     * @return the name of the executable property of extension points
     */
    public static String getExtensionPropertyName() {
        return EXTENSION_PROPERTY_NAME;
    }

    public static String getPlatformResourcePrefix() {
        return PLATFORM_RESOURCE_PREFIX;
    }
}
