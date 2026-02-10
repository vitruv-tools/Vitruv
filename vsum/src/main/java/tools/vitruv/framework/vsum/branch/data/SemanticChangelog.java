package tools.vitruv.framework.vsum.branch.data;

import lombok.Getter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a semantic changelog for a Git commit.
 *
 * <p>Captures both Git commit metadata (author, committer, dates, message)
 * and Vitruvius-specific information (branch, file changes).
 *
 * <p>Matches the format of {@code git log --pretty=fuller}:
 * <pre>
 * commit b36fc4bf1835a294f77bfa5f458741ab48b45c89
 * Author:     nplinh61 <nguyenlinh05072002@gmail.com>
 * AuthorDate: Sat Feb 8 10:30:00 2026
 * Commit:     nplinh61 <nguyenlinh05072002@gmail.com>
 * CommitDate: Sun Feb 9 15:45:00 2026
 *
 *     Commit message here
 * </pre>
 *
 *
 * <p>todo: Add parent commit SHAs for merge commit tracking
 * <p>todo: Serialize to/from JSON for storage in .vitruvius/changelogs/ with concrete changes
 */
@Getter
public class SemanticChangelog {

    /**
     * -- GETTER --
     *
     */
    private final String commitSha;
    /**
     * -- GETTER --
     *
     */
    private final String author;
    /**
     * -- GETTER --
     *
     */
    private final LocalDateTime authorDate;
    /**
     * -- GETTER --
     *
     */
    private final String committer;
    /**
     * -- GETTER --
     *
     */
    private final LocalDateTime commitDate;
    /**
     * -- GETTER --
     *
     */
    private final String message;
    /**
     * -- GETTER --
     *
     */
    private final String branch;
    /**
     * -- GETTER --
     *
     */
    private final List<FileChange> changes;

    /**
     * Private constructor. Use {@link #create} factory method or {@link #builder()}.
     */
    private SemanticChangelog(String commitSha, String author, LocalDateTime authorDate, String committer, LocalDateTime commitDate, String message, String branch, List<FileChange> changes) {

        // Validate required fields (not null or empty for strings)
        this.commitSha = validateNotEmpty(commitSha, "commitSha");
        this.author = validateNotEmpty(author, "author");
        if (authorDate == null) {
            throw new IllegalArgumentException("authorDate must not be null");
        }
        this.authorDate = authorDate;
        this.committer = validateNotEmpty(committer, "committer");
        if (commitDate == null) {
            throw new IllegalArgumentException("commitDate must not be null");
        }
        this.commitDate = commitDate;
        if (message == null) {
            throw new IllegalArgumentException("message must not be null");
        }
        this.message = message;
        this.branch = validateNotEmpty(branch, "branch");

        if (changes == null) {
            throw new IllegalArgumentException("changes must not be null");
        }
        this.changes = Collections.unmodifiableList(new ArrayList<>(changes));
    }

    /**
     * Validates that a string is not null or empty.
     *
     * @param value the value to validate
     * @param fieldName the field name for error message
     * @return the validated value
     * @throws IllegalArgumentException if value is null or empty
     */
    private static String validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be null or empty");
        }
        return value;
    }

    /**
     * Creates a semantic changelog where author and committer are the same.
     *
     * <p>This is the most common case, used for normal commits where the
     * person who wrote the code is also the person who committed it.
     *
     * @param commitSha full Git commit SHA (40 characters)
     * @param author author name and email (e.g., "Jane Doe &lt;jane@example.com&gt;")
     * @param authorDate when the code was authored
     * @param message commit message
     * @param branch branch name where this commit exists
     * @param changes list of file changes in this commit
     * @return a new SemanticChangelog with committer set to author
     * @throws IllegalArgumentException if any required field is null or empty
     */
    public static SemanticChangelog create(String commitSha, String author, LocalDateTime authorDate, String message, String branch, List<FileChange> changes) {
        // Commit = Author
        // CommitDate = AuthorDate
        return new SemanticChangelog(commitSha, author, authorDate, author, authorDate, message, branch, changes);
    }

    /**
     * Returns a builder for creating SemanticChangelog with custom committer.
     *
     * <p>Use this when author and committer are different (e.g., after
     * cherry-pick, rebase, or applying patches).
     * @return a new builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for creating SemanticChangelog instances with custom committer.
     */
    public static class Builder {
        private String commitSha;
        private String author;
        private LocalDateTime authorDate;
        private String committer;
        private LocalDateTime commitDate;
        private String message;
        private String branch;
        private List<FileChange> changes;

        private Builder() {}

        public Builder commitSha(String commitSha) {
            this.commitSha = commitSha;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder authorDate(LocalDateTime authorDate) {
            this.authorDate = authorDate;
            return this;
        }

        public Builder committer(String committer) {
            this.committer = committer;
            return this;
        }

        public Builder commitDate(LocalDateTime commitDate) {
            this.commitDate = commitDate;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder branch(String branch) {
            this.branch = branch;
            return this;
        }

        public Builder changes(List<FileChange> changes) {
            this.changes = changes;
            return this;
        }

        /**
         * Builds the SemanticChangelog.
         *
         * @return a new SemanticChangelog instance
         * @throws IllegalArgumentException if any required field is null or empty
         */
        public SemanticChangelog build() {
            return new SemanticChangelog(commitSha, author, authorDate, committer, commitDate, message, branch, changes);
        }
    }

    // Getters

    /**
     * @return true if author and committer are different (e.g., cherry-picked commit)
     */
    public boolean isAuthorDifferentFromCommitter() {
        return !author.equals(committer);
    }

    /**
     * @return true if there are any file changes
     */
    public boolean hasChanges() {
        return !changes.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SemanticChangelog that = (SemanticChangelog) o;
        return Objects.equals(commitSha, that.commitSha) &&
                Objects.equals(author, that.author) &&
                Objects.equals(authorDate, that.authorDate) &&
                Objects.equals(committer, that.committer) &&
                Objects.equals(commitDate, that.commitDate) &&
                Objects.equals(message, that.message) &&
                Objects.equals(branch, that.branch) &&
                Objects.equals(changes, that.changes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commitSha, author, authorDate, committer, commitDate, message, branch, changes);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SemanticChangelog{");
        sb.append("commit=").append(commitSha, 0, Math.min(7, commitSha.length()));
        sb.append(", branch=").append(branch);
        sb.append(", author=").append(author);
        if (isAuthorDifferentFromCommitter()) {
            sb.append(", committer=").append(committer);
        }
        sb.append(", changes=").append(changes.size());
        sb.append("}");
        return sb.toString();
    }

    /**
     * Writes this changelog to a text file at the given path.
     * Parent directories will be created if they do not exist.
     *
     * @param path the file path to write the changelog to
     * @throws IOException if file cannot be written
     */
    public void writeTo(Path path) throws IOException {
        java.nio.file.Files.createDirectories(path.getParent());
        StringBuilder sb = new StringBuilder();

        // Header
        sb.append("===================================================\n");
        sb.append("SEMANTIC CHANGELOG\n");
        sb.append("===================================================\n\n");
        // Commit info
        sb.append("Commit:     ").append(commitSha).append("\n");
        sb.append("Branch:     ").append(branch).append("\n");
        sb.append("Author:     ").append(author).append("\n");
        sb.append("AuthorDate: ").append(authorDate).append("\n");
        if (isAuthorDifferentFromCommitter()) {
            sb.append("Commit:     ").append(committer).append("\n");
            sb.append("CommitDate: ").append(commitDate).append("\n");
        }
        sb.append("\n    ").append(message).append("\n\n");
        // Changes
        sb.append("===================================================\n");
        sb.append("FILE CHANGES (").append(changes.size()).append(")\n");
        sb.append("===================================================\n\n");
        if (changes.isEmpty()) {
            sb.append("No file changes detected.\n");
        } else {
            for (FileChange change : changes) {
                sb.append(change.getOperation()).append(": ").append(change.getFilePath()).append("\n");
                if (change.getOperation() == FileOperation.RENAMED && change.getOldPath() != null) {
                    sb.append("    (from: ").append(change.getOldPath()).append(")\n");
                }
            }
        }
        java.nio.file.Files.writeString(path, sb.toString());
    }
}