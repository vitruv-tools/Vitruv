package tools.vitruv.framework.util;

public class VitruviusConstants {
	private static final String TEST_PROJECT_MARKER_FILE_NAME = "test_project.marker_vitruv";
	private static final String EXTENSION_PROPERTY_NAME = "provider";
	private static final String EXTENSION_PRIORITY_PROPERTY_NAME = "priority";
	private static final String PATHMAP_PREFIX = "pathmap:/";
	private static final String PLATFORM_RESOURCE_PREFIX = "platform:/resource/";
	private static final String CORRESPONDENCES_FILE_EXT = "correspondence";
	private static final String UUID_FILE_EXT = "uuid";
	private static final String DEFAULT_NAME_OF_IDENTIFIER_FEATURE = "id";
	private static final String DEFAULT_NAME_OF_NAME_FEAUTRE = "entityName";
	private static final String FILE_EXT_SEPARATOR = ".";
	/**
	 * Fields are needed as options to save correspondence instance (we need to
	 * ignore dangling HREFs) Copied from XMLResource.java
	 */
	private static final String OPTION_PROCESS_DANGLING_HREF = "PROCESS_DANGLING_HREF";
	private static final String OPTION_PROCESS_DANGLING_HREF_DISCARD = "DISCARD";

	/**
	 * @return the name of the executable property of extension points
	 */
	public static String getExtensionPropertyName() {
		return EXTENSION_PROPERTY_NAME;
	}

	public static String getExtensionPriorityPropertyName() {
		return EXTENSION_PRIORITY_PROPERTY_NAME;
	}

	public static String getPlatformResourcePrefix() {
		return PLATFORM_RESOURCE_PREFIX;
	}

	public static String getPathmapPrefix() {
		return PATHMAP_PREFIX;
	}

	public static String getCorrespondencesFileExt() {
		return CORRESPONDENCES_FILE_EXT;
	}

	public static String getUuidFileExt() {
		return UUID_FILE_EXT;
	}

	public static String getDefaultNameOfIdentifierFeature() {
		return DEFAULT_NAME_OF_IDENTIFIER_FEATURE;
	}

	public static String getDefaultNameOfNameFeature() {
		return DEFAULT_NAME_OF_NAME_FEAUTRE;
	}

	public static String getFileExtSeparator() {
		return FILE_EXT_SEPARATOR;
	}

	public static String getOptionProcessDanglingHref() {
		return OPTION_PROCESS_DANGLING_HREF;
	}

	public static String getOptionProcessDanglingHrefDiscard() {
		return OPTION_PROCESS_DANGLING_HREF_DISCARD;
	}

	/**
	 * Returns the name of a file that is placed in every test project as a marker
	 * for the test project. This enables tests to be run independent from Eclipse
	 * but still be able to detect a project folder without having PlatformURIs.
	 * 
	 * @return the name of an identifier file placed in each test project
	 */
	public static String getTestProjectMarkerFileName() {
		return TEST_PROJECT_MARKER_FILE_NAME;
	}
}
