package tools.vitruv.framework.vsum.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.vitruv.framework.vsum.internal.ConstraintStore.StoredConstraint;

/** Tests for {@link ConstraintStore}. */
class ConstraintStoreTest {
  @TempDir Path folder;

  @Test
  @DisplayName("nothing is stored before the first save")
  void emptyWithoutFile() {
    assertTrue(new ConstraintStore(folder.resolve("constraints.properties")).load().isEmpty());
  }

  @Test
  @DisplayName("constraints with special characters are stored unchanged")
  void roundTrip() {
    ConstraintStore store = new ConstraintStore(folder.resolve("constraints.properties"));
    String source =
        "context shop::Item inv a:\n  @message \"Größe = #1: \\\\ ok\"\r\n  self.price >= 0 -- €";
    List<StoredConstraint> constraints =
        List.of(
            StoredConstraint.builtIn("shop::Item::a", "vitruvocl", source),
            StoredConstraint.runtime("id = with:colon#hash", "other", "x").asDeleted(),
            StoredConstraint.builtIn("b", "vitruvocl", "old").withSource("new"));

    store.save(constraints);

    assertEquals(constraints, store.load());
  }

  @Test
  @DisplayName("a corrupt file is reported with its entry")
  void corruptFile() throws Exception {
    Path file = folder.resolve("constraints.properties");
    Files.writeString(file, "0.id=a\n0.language=vitruvocl\n0.origin=SOMETHING\n0.source=x\n");

    IllegalStateException exception =
        assertThrows(IllegalStateException.class, () -> new ConstraintStore(file).load());

    assertTrue(exception.getMessage().contains("entry 0"));
  }

  @Test
  @DisplayName("a built-in constraint without its original source is reported as corrupt")
  void missingBuiltInSource() throws Exception {
    Path file = folder.resolve("constraints.properties");
    Files.writeString(file, "0.id=a\n0.language=vitruvocl\n0.origin=BUILT_IN\n0.source=x\n");

    assertThrows(IllegalStateException.class, () -> new ConstraintStore(file).load());
  }
}
