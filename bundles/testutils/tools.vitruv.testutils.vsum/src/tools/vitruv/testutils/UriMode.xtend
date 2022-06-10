package tools.vitruv.testutils

/**
 * Determines how relative paths should be resolved to EMF URIs in a test.
 */
enum UriMode {
	/**
	 * Use a {@code file://} URI to the absolute path of the resource.
	 */
	FILE_URIS,
	/**
	 * Use a {@code platform://} URI that effectively resolves to the resource.
	 */
	PLATFORM_URIS
}
