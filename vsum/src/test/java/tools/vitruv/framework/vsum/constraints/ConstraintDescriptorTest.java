package tools.vitruv.framework.vsum.constraints;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ConstraintDescriptorTest {

  private static ConstraintDescriptor descriptor(String contextType, Set<String> metamodels) {
    return new ConstraintDescriptor(
        "c", null, contextType, null, null, "test", null, metamodels);
  }

  @Test
  void usesTheMetamodelOfTheContextTypeByDefault() {
    ConstraintDescriptor descriptor =
        new ConstraintDescriptor("c", null, "shop::Item", null, null, "test", null);

    assertEquals(Set.of("shop"), descriptor.metamodels());
  }

  @Test
  void addsTheMetamodelOfTheContextTypeFirst() {
    ConstraintDescriptor descriptor =
        descriptor("shop::Item", new LinkedHashSet<>(List.of("billing", "shop")));

    assertEquals(List.of("shop", "billing"), List.copyOf(descriptor.metamodels()));
  }

  @Test
  void hasNoMetamodelsForUnqualifiedOrMissingContextTypes() {
    assertTrue(descriptor("Item", null).metamodels().isEmpty());
    assertTrue(descriptor(null, null).metamodels().isEmpty());
  }

  @Test
  void keepsTheGivenMetamodelsWithoutContextType() {
    assertEquals(
        Set.of("shop", "billing"), descriptor(null, Set.of("shop", "billing")).metamodels());
  }

  @Test
  void ignoresBlankMetamodels() {
    Set<String> metamodels = new LinkedHashSet<>(Arrays.asList(" ", null, " billing "));

    assertEquals(Set.of("shop", "billing"), descriptor("shop::Item", metamodels).metamodels());
  }

  @Test
  void doesNotChangeWithTheGivenSet() {
    Set<String> metamodels = new LinkedHashSet<>(List.of("billing"));
    ConstraintDescriptor descriptor = descriptor("shop::Item", metamodels);

    metamodels.add("other");

    assertEquals(Set.of("shop", "billing"), descriptor.metamodels());
    assertThrows(UnsupportedOperationException.class, () -> descriptor.metamodels().add("x"));
  }

  @Test
  void comparesMetamodelsIndependentOfTheirOrder() {
    assertEquals(
        descriptor(null, new LinkedHashSet<>(List.of("a", "b"))),
        descriptor(null, new LinkedHashSet<>(List.of("b", "a"))));
  }

  @Test
  void extractsTheMetamodelOfQualifiedTypes() {
    assertEquals("shop", ConstraintDescriptor.metamodelOf("shop::Item"));
    assertEquals("shop", ConstraintDescriptor.metamodelOf("shop::sub::Item"));
    assertNull(ConstraintDescriptor.metamodelOf("Item"));
    assertNull(ConstraintDescriptor.metamodelOf("::Item"));
    assertNull(ConstraintDescriptor.metamodelOf(null));
  }
}
