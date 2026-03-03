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
    public List<Path> getAdditionalFiles() { return additionalFiles; }
    public List<Path> getExcludeFiles() { return excludeFiles; }
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private final List<Path> additionalFiles = new ArrayList<>();
        private final List<Path> excludeFiles = new ArrayList<>();

        public Builder addFile(Path path) {
            additionalFiles.add(path);
            return this;
        }
        public Builder excludeFile(Path path) {
            excludeFiles.add(path);
            return this;
        }
        public CommitOptions build() {
            return new CommitOptions(this);
        }
    }
}