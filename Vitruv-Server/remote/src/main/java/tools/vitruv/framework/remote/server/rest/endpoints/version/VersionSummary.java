package tools.vitruv.framework.remote.server.rest.endpoints.version;

import tools.vitruv.framework.vsum.versioning.data.VersionMetadata;

/**
 * Lightweight API response DTO for a version entry in the list endpoint.
 *
 * <p>Returned by {@code GET /vsum/version}. Use {@link VersionResponse} (via
 * {@code GET /vsum/version/detail}) to retrieve the full metadata including
 * commit SHA, branch, tagger identity, and creation timestamp.
 */
public record VersionSummary(String versionId, String description) {

  /** Creates a {@link VersionSummary} from a {@link VersionMetadata} domain object. */
  public static VersionSummary from(VersionMetadata metadata) {
    return new VersionSummary(metadata.getVersionId(), metadata.getDescription());
  }
}
