package tools.vitruv.framework.views;

import static java.util.Collections.emptySet;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import tools.vitruv.change.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.change.testutils.TestLogging;
import static tools.vitruv.framework.views.ViewTypeFactory.createIdentityMappingViewType;

/** Tests for the {@link ViewTypeRepository} class. */
@ExtendWith({TestLogging.class, RegisterMetamodelsInStandalone.class})
public class ViewTypeRepositoryTest {

  /** Creates a view type with the given name that maps elements of the given type to themselves. */
  @Nested
  @DisplayName("register")
  class Register {
    @Test
    @DisplayName("proper view type")
    void properViewType() {
      ViewTypeRepository repository = new ViewTypeRepository();
      ViewType<?> viewType = createIdentityMappingViewType("type");
      repository.register(viewType);
      assertThat(repository.getViewTypes().size(), is(1));
      assertThat(repository.getViewTypes(), hasItem(viewType));
    }

    @Test
    @DisplayName("null view type")
    void nullViewType() {
      ViewTypeRepository repository = new ViewTypeRepository();
      assertThrows(IllegalArgumentException.class, () -> repository.register(null));
    }

    @Test
    @DisplayName("same view type twice")
    void sameViewTypeTwice() {
      ViewTypeRepository repository = new ViewTypeRepository();
      ViewType<?> viewType = createIdentityMappingViewType("type");
      repository.register(viewType);
      assertThrows(IllegalStateException.class, () -> repository.register(viewType));
    }

    @Test
    @DisplayName("another view type with same name")
    void viewTypeWithSameName() {
      ViewTypeRepository repository = new ViewTypeRepository();
      ViewType<?> firstViewType = createIdentityMappingViewType("type");
      ViewType<?> secondViewType = createIdentityMappingViewType("type");
      repository.register(firstViewType);
      assertThrows(IllegalStateException.class, () -> repository.register(secondViewType));
    }
  }

  /** Tests for the {@link ViewTypeRepository#getViewTypes()} method. */
  @Nested
  @DisplayName("retrieve")
  class Retrieve {
    @Test
    @DisplayName("provides only a copy of view type list")
    void providesOnlyCopy() {
      ViewTypeRepository repository = new ViewTypeRepository();
      ViewType<?> viewType = createIdentityMappingViewType("type");
      repository.getViewTypes().add(viewType);
      assertThat(new HashSet<>(repository.getViewTypes()), is(emptySet()));
    }
  }

  /** Tests for the {@link ViewTypeRepository#findViewType(String)} method. */
  @Nested
  @DisplayName("find")
  class Find {
    @Test
    @DisplayName("registered view type by name")
    void registeredViewType() {
      ViewTypeRepository repository = new ViewTypeRepository();
      ViewType<?> viewType = createIdentityMappingViewType("type");
      repository.register(viewType);
      assertThat(repository.findViewType("type"), is(viewType));
    }

    @Test
    @DisplayName("non-registered view type by name")
    void nonRegisteredViewType() {
      ViewTypeRepository repository = new ViewTypeRepository();
      ViewType<?> viewType = createIdentityMappingViewType("type");
      repository.register(viewType);
      assertThat(repository.findViewType("other"), equalTo(null));
    }

    @Test
    @DisplayName("null name")
    void nullName() {
      ViewTypeRepository repository = new ViewTypeRepository();
      ViewType<?> viewType = createIdentityMappingViewType("type");
      repository.register(viewType);
      assertThrows(IllegalArgumentException.class, () -> repository.findViewType(null));
    }
  }
}
