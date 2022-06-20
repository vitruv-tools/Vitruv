package tools.vitruv.framework.views;

import static java.util.Collections.emptySet;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tools.vitruv.framework.views.ViewTypeFactory.createIdentityMappingViewType;

import java.util.HashSet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.TestLogging;

@ExtendWith({ TestLogging.class, RegisterMetamodelsInStandalone.class })
public class ViewTypeRepositoryTest {
	@Nested
	@DisplayName("register")
	public class Register {
		@Test
		@DisplayName("proper view type")
		public void properViewType() {
			ViewTypeRepository repository = new ViewTypeRepository();
			ViewType<?> viewType = createIdentityMappingViewType("type");
			repository.register(viewType);
			assertThat(repository.getViewTypes().size(), is(1));
			assertThat(repository.getViewTypes(), hasItem(viewType));
		}

		@Test
		@DisplayName("null view type")
		public void nullViewType() {
			ViewTypeRepository repository = new ViewTypeRepository();
			assertThrows(IllegalArgumentException.class, () -> repository.register(null));
		}

		@Test
		@DisplayName("same view type twice")
		public void sameViewTypeTwice() {
			ViewTypeRepository repository = new ViewTypeRepository();
			ViewType<?> viewType = createIdentityMappingViewType("type");
			repository.register(viewType);
			assertThrows(IllegalStateException.class, () -> repository.register(viewType));
		}

		@Test
		@DisplayName("another view type with same name")
		public void viewTypeWithSameName() {
			ViewTypeRepository repository = new ViewTypeRepository();
			ViewType<?> firstViewType = createIdentityMappingViewType("type");
			ViewType<?> secondViewType = createIdentityMappingViewType("type");
			repository.register(firstViewType);
			assertThrows(IllegalStateException.class, () -> repository.register(secondViewType));
		}
	}

	@Nested
	@DisplayName("retrieve")
	public class Retrieve {
		@Test
		@DisplayName("provides only a copy of view type list")
		public void providesOnlyCopy() {
			ViewTypeRepository repository = new ViewTypeRepository();
			ViewType<?> viewType = createIdentityMappingViewType("type");
			repository.getViewTypes().add(viewType);
			assertThat(new HashSet<>(repository.getViewTypes()), is(emptySet()));
		}
	}

	@Nested
	@DisplayName("find")
	public class Find {
		@Test
		@DisplayName("registered view type by name")
		public void registeredViewType() {
			ViewTypeRepository repository = new ViewTypeRepository();
			ViewType<?> viewType = createIdentityMappingViewType("type");
			repository.register(viewType);
			assertThat(repository.findViewType("type"), is(viewType));
		}

		@Test
		@DisplayName("non-registered view type by name")
		public void nonRegisteredViewType() {
			ViewTypeRepository repository = new ViewTypeRepository();
			ViewType<?> viewType = createIdentityMappingViewType("type");
			repository.register(viewType);
			assertThat(repository.findViewType("other"), equalTo(null));
		}

		@Test
		@DisplayName("null name")
		public void nullName() {
			ViewTypeRepository repository = new ViewTypeRepository();
			ViewType<?> viewType = createIdentityMappingViewType("type");
			repository.register(viewType);
			assertThrows(IllegalArgumentException.class, () -> repository.findViewType(null));
		}

	}
}
