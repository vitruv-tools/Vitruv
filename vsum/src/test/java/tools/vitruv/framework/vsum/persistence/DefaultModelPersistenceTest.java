package tools.vitruv.framework.vsum.persistence;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.vitruv.framework.vsum.persistence.delta.DeltaBasedModelPersistence;

class DefaultModelPersistenceTest {
  private String previousMode;

  @BeforeEach
  void rememberMode() {
    previousMode = System.clearProperty(DefaultModelPersistence.PROPERTY);
  }

  @AfterEach
  void restoreMode() {
    if (previousMode == null) {
      System.clearProperty(DefaultModelPersistence.PROPERTY);
    } else {
      System.setProperty(DefaultModelPersistence.PROPERTY, previousMode);
    }
  }

  @Test
  @DisplayName("the default persistence is delta-based")
  void defaultIsDeltaBased() {
    assertInstanceOf(DeltaBasedModelPersistence.class, DefaultModelPersistence.create());
  }

  @Test
  @DisplayName("the system property selects the state-based or delta-based persistence")
  void propertySelectsPersistence() {
    System.setProperty(DefaultModelPersistence.PROPERTY, "state");
    assertInstanceOf(StateBasedModelPersistence.class, DefaultModelPersistence.create());
    System.setProperty(DefaultModelPersistence.PROPERTY, "delta");
    assertInstanceOf(DeltaBasedModelPersistence.class, DefaultModelPersistence.create());
    System.setProperty(DefaultModelPersistence.PROPERTY, "other");
    assertThrows(IllegalArgumentException.class, DefaultModelPersistence::create);
  }
}
