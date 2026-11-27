package tools.vitruv.framework.remote.server.rest.endpoints.version;

import java.time.format.DateTimeFormatter;
import tools.vitruv.framework.vsum.versioning.data.VersionMetadata;

/**
 * API response DTO for a single version.
 *
 * <p>Converts {@link VersionMetadata} into a Jackson-serializable record by formatting
 * the {@code LocalDateTime} createdAt field as an ISO string.
 */
public record VersionResponse(
    String versionId,
    String commitSha,
    String branch,
    String taggerName,
    String taggerEmail,
    String createdAt,
    String description) {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

  /** Creates a {@link VersionResponse} from a {@link VersionMetadata} domain object. */
  public static VersionResponse from(VersionMetadata metadata) {
    return new VersionResponse(
        metadata.getVersionId(),
        metadata.getCommitSha(),
        metadata.getBranch(),
        metadata.getTaggerName(),
        metadata.getTaggerEmail(),
        metadata.getCreatedAt().format(FORMATTER),
        metadata.getDescription());
  }
}
