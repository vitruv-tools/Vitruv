package tools.vitruv.framework.vsum.branch.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.Getter;

/**
 * Represents the semantic changelog produced by Vitruvius for a single Git commit.
 *
 * <p>Each instance captures both the standard Git commit metadata that appears in
 * {@code git log --pretty=fuller} (commit SHA, author, committer, dates, message)
 * and Vitruvius-specific information: the branch on which the commit was made and the
 * list of file-level changes detected in the VSUM
 *
 * <p>Example of the Git metadata format captured here:
 * <pre>
 * commit b36fc4bf1835a294f77bfa5f458741ab48b45c89
 * Author:     johnschmidt &lt;johnschmidt@gmail.com&gt;
 * AuthorDate: Sat Feb 8 10:30:00 2026
 * Commit:     johnschmidt &lt;johnschmidt@gmail.com&gt;
 * CommitDate: Sun Feb 9 15:45:00 2026
 *
 *     Commit message here
 * </pre>
 *
 * <p>Instances are created either through the {@link #create} convenience factory method
 * (when author and committer are the same, which is the common case)
 * or through the {@link #builder()} for cases such as cherry-picks or rebases where
 * author and committer differ.
 *
 * <p>The {@link #changes} list is unmodifiable after construction. All string fields are
 * validated to be non-null and non-blank at construction time.
 *
 * <p>TODO: parent commit SHAs for merge commit tracking and JSON serialization for storage
 * in {@code .vitruvius/changelogs/} with element-level change details
 */
@Getter
public class SemanticChangelog {

  /**
   * The full 40-character Git commit SHA that this changelog describes.
   */
  private final String commitSha;

  /**
   * The name and email of the person who authored the changes, in Git format.
   */
  private final String author;

  /**
   * The date and time when the changes were originally authored.
   */
  private final LocalDateTime authorDate;

  /**
   * The name and email of the person who committed the changes, in Git format.
   * Equal to {@link #author} for direct commits, differs for cherry-picks and rebases.
   */
  private final String committer;

  /**
   * The date and time when the commit was recorded in the repository.
   */
  private final LocalDateTime commitDate;

  /**
   * The full commit message as it appears in the Git history.
   */
  private final String message;

  /**
   * The name of the branch on which this commit was made.
   */
  private final String branch;

  /**
   * The file-level changes detected in this commit. Unmodifiable after construction.
   */
  private final List<FileChange> changes;

  /**
   * Private constructor.
   */
  private SemanticChangelog(String commitSha, String author, LocalDateTime authorDate,
      String committer, LocalDateTime commitDate, String message, String branch,
      List<FileChange> changes) {
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
    // copy and wrap to prevent external mutation of the change list.
    this.changes = Collections.unmodifiableList(new ArrayList<>(changes));
  }

  /**
   * Validates that a string is non-null and non-blank.
   *
   * @param value     the value to validate.
   * @param fieldName the field name used in the error message.
   * @return the validated value, unchanged.
   * @throws IllegalArgumentException if the value is null or blank.
   */
  private static String validateNotEmpty(String value, String fieldName) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(fieldName + " must not be null or empty");
    }
    return value;
  }

  /**
   * Creates a changelog for the common case where the author and committer are the same
   * person, and the author date equals the commit date.
   * This covers direct commits where no rebasing or cherry-picking has taken place.
   *
   * @param commitSha  the full 40-character Git commit SHA.
   * @param author     the author name and email
   *                   (for example {@code "John Schmidt <johnschmidt@gmail.com>"}).
   * @param authorDate the date and time when the changes were authored.
   * @param message    the commit message.
   * @param branch     the branch on which the commit was made.
   * @param changes    the file-level changes in this commit. must not be null.
   * @return a new {@link SemanticChangelog} with committer and commit date equal to the
   *         author fields.
   * @throws IllegalArgumentException if any required field is null or blank.
   */
  public static SemanticChangelog create(String commitSha, String author,
      LocalDateTime authorDate, String message, String branch, List<FileChange> changes) {
    // for a direct commit, the committer is the author and both dates are the same.
    return new SemanticChangelog(commitSha, author, authorDate, author, authorDate, message,
        branch, changes);
  }

  /**
   * Returns a builder for constructing a changelog with a separate committer.
   * Use when the author and committer differ, for example after a cherry-pick, rebase,
   * or patch application.
   *
   * @return a new {@link Builder} instance.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Builder for creating {@link SemanticChangelog} instances where the committer differs
   * from the author.
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

    private Builder() {
    }

    /**
     * Sets the full 40-character Git commit SHA.
     *
     * @param commitSha the commit SHA.
     * @return this builder.
     */
    public Builder commitSha(String commitSha) {
      this.commitSha = commitSha;
      return this;
    }

    /**
     * Sets the author name and email in Git format.
     *
     * @param author the author string.
     * @return this builder.
     */
    public Builder author(String author) {
      this.author = author;
      return this;
    }

    /**
     * Sets the date and time when the changes were originally authored.
     *
     * @param authorDate the author date.
     * @return this builder.
     */
    public Builder authorDate(LocalDateTime authorDate) {
      this.authorDate = authorDate;
      return this;
    }

    /**
     * Sets the committer name and email in Git format.
     *
     * @param committer the committer string.
     * @return this builder.
     */
    public Builder committer(String committer) {
      this.committer = committer;
      return this;
    }

    /**
     * Sets the date and time when the commit was recorded in the repository.
     *
     * @param commitDate the commit date.
     * @return this builder.
     */
    public Builder commitDate(LocalDateTime commitDate) {
      this.commitDate = commitDate;
      return this;
    }

    /**
     * Sets the full commit message.
     *
     * @param message the commit message.
     * @return this builder.
     */
    public Builder message(String message) {
      this.message = message;
      return this;
    }

    /**
     * Sets the branch on which the commit was made.
     *
     * @param branch the branch name.
     * @return this builder.
     */
    public Builder branch(String branch) {
      this.branch = branch;
      return this;
    }

    /**
     * Sets the file-level changes detected in this commit.
     *
     * @param changes the list of file changes.
     * @return this builder.
     */
    public Builder changes(List<FileChange> changes) {
      this.changes = changes;
      return this;
    }

    /**
     * Builds the {@link SemanticChangelog} with the values set on this builder.
     *
     * @return a new {@link SemanticChangelog} instance.
     * @throws IllegalArgumentException if any required field is null or blank.
     */
    public SemanticChangelog build() {
      return new SemanticChangelog(commitSha, author, authorDate, committer, commitDate,
          message, branch, changes);
    }
  }

  /**
   * Returns true if the author and committer are different people. This is the case for
   * commits that were cherry-picked, rebased, or applied from a patch, where the original
   * author is preserved but the committer is the person who applied the change.
   */
  public boolean isAuthorDifferentFromCommitter() {
    return !author.equals(committer);
  }

  /**
   * Returns true if this changelog records at least one file-level change. A changelog
   * with no changes can occur for empty commits (created with {@code --allow-empty}).
   */
  public boolean hasChanges() {
    return !changes.isEmpty();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SemanticChangelog that = (SemanticChangelog) o;
    return Objects.equals(commitSha, that.commitSha)
        && Objects.equals(author, that.author)
        && Objects.equals(authorDate, that.authorDate)
        && Objects.equals(committer, that.committer)
        && Objects.equals(commitDate, that.commitDate)
        && Objects.equals(message, that.message)
        && Objects.equals(branch, that.branch)
        && Objects.equals(changes, that.changes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(commitSha, author, authorDate, committer, commitDate, message, branch,
        changes);
  }

  @Override
  public String toString() {
    var sb = new StringBuilder();
    sb.append("SemanticChangelog{");
    // show only the first seven characters of the commit SHA to match the short-hash convention.
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
   * Writes this changelog to a human-readable text file at the given path, following
   * the same layout as {@code git log --pretty=fuller}.
   * Parent directories are created automatically if they do not exist.
   *
   * <p>The committer section is omitted when author and committer are the same to keep
   * the output concise for the common case.
   *
   * @param path the file path to write the changelog to.
   * @throws IOException if the file or its parent directories cannot be created or written.
   */
  public void writeTo(Path path) throws IOException {
    Files.createDirectories(path.getParent());
    var sb = new StringBuilder();

    // header section
    sb.append("===================================================\n");
    sb.append("SEMANTIC CHANGELOG\n");
    sb.append("===================================================\n\n");

    // commit metadata section, mirroring the git log --pretty=fuller format.
    sb.append("Commit:     ").append(commitSha).append("\n");
    sb.append("Branch:     ").append(branch).append("\n");
    sb.append("Author:     ").append(author).append("\n");
    sb.append("AuthorDate: ").append(authorDate).append("\n");
    if (isAuthorDifferentFromCommitter()) {
      sb.append("Commit:     ").append(committer).append("\n");
      sb.append("CommitDate: ").append(commitDate).append("\n");
    }
    sb.append("\n    ").append(message).append("\n\n");
    // file changes section
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
    Files.writeString(path, sb.toString());
  }
}
