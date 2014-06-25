package edu.kit.ipd.sdq.vitruvius.framework.contracts;

public class VitruviusConstants {

    private static final String EXTENSION_PROPERTY_NAME = "provider";
    private static final String PLATFORM_RESOURCE_PREFIX = "platform:/resource/";
    private static final String CORRESPONDENCE_INSTANCE_FILE_EXT = ".correspondence_instance";
    private static final String CORRESPONDENCES_FILE_EXT = ".correspondence";
    private static final String DEFAULT_NAME_OF_IDENTIFIER_FEATURE = "id";

    /**
     * @return the name of the executable property of extension points
     */
    public static String getExtensionPropertyName() {
        return EXTENSION_PROPERTY_NAME;
    }

    public static String getPlatformResourcePrefix() {
        return PLATFORM_RESOURCE_PREFIX;
    }

    public static String getCorrespondenceInstanceFileExt() {
        return CORRESPONDENCE_INSTANCE_FILE_EXT;
    }

    public static String getCorrespondencesFileExt() {
        return CORRESPONDENCES_FILE_EXT;
    }

    public static String getDefaultNameOfIdentifierFeature() {
        return DEFAULT_NAME_OF_IDENTIFIER_FEATURE;
    }
}
