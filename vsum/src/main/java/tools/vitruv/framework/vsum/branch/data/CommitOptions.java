package tools.vitruv.framework.vsum.branch.data;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Options for a commit operation performed by CommitManager.
 * Use the builder to specify additional files to stage or files to exclude from auto-staging.
 */
public class CommitOptions {

  /** Extra files to stage in addition to auto-detected model files. */
  private final List<Path> additionalFiles;

  /** Model files to exclude from auto-staging even if they match the model file filter. */
  private final List<Path> excludeFiles;

  private CommitOptions(Builder builder) {
    this.additionalFiles = List.copyOf(builder.additionalFiles);
    this.excludeFiles = List.copyOf(builder.excludeFiles);
  }

  /**
   * Returns the list of additional files to stage beyond auto-detected model files.
   */
  public List<Path> getAdditionalFiles() {
    return additionalFiles;
  }

  /**
   * Returns the list of model files explicitly excluded from auto-staging.
   */
  public List<Path> getExcludeFiles() {
    return excludeFiles;
  }

  /**
   * Returns a new {@link Builder} for constructing a {@link CommitOptions} instance.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Builder for {@link CommitOptions}.
   */
  public static class Builder {
    private final List<Path> additionalFiles = new ArrayList<>();
    private final List<Path> excludeFiles = new ArrayList<>();

    /**
     * Adds a file to be staged in addition to auto-detected model files.
     *
     * @param path the file to stage.
     * @return this builder.
     */
    public Builder addFile(Path path) {
      additionalFiles.add(path);
      return this;
    }

    /**
     * Excludes a model file from auto-staging.
     *
     * @param path the file to exclude.
     * @return this builder.
     */
    public Builder excludeFile(Path path) {
      excludeFiles.add(path);
      return this;
    }

    /**
     * Builds the {@link CommitOptions} instance.
     */
    public CommitOptions build() {
      return new CommitOptions(this);
    }
  }
}
