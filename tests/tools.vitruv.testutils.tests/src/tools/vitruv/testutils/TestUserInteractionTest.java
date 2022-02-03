package tools.vitruv.testutils;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tools.vitruv.framework.userinteraction.UserInteractionFactory;
import tools.vitruv.framework.userinteraction.UserInteractor;
import tools.vitruv.testutils.TestUserInteraction.ResultProvider;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static java.util.Collections.emptySet;
import static java.util.Collections.emptyList;

public class TestUserInteractionTest {
	private static String DIALOG_TITLE = "test";

	@Nested
	@DisplayName("text input")
	class TextInput {
		@Test
		@DisplayName("required and provided a single time")
		public void provideAndRequireSingleTime() {
			String responseText = "response";
			TestUserInteraction testInteraction = new TestUserInteraction();
			testInteraction.onTextInput((description) -> description.getTitle().equals(DIALOG_TITLE))
					.respondWith(responseText);
			UserInteractor userInteractor = generateInteractor(testInteraction);
			assertThat(userInteractor.getTextInputDialogBuilder().message(DIALOG_TITLE).title(DIALOG_TITLE)
					.startInteraction(), equalTo(responseText));
		}

		@Test
		@DisplayName("required and provided multiple times")
		public void provideAndRequireMultipleTimes() {
			String responseText = "response";
			TestUserInteraction testInteraction = new TestUserInteraction();
			testInteraction.onTextInput((description) -> description.getTitle().equals(DIALOG_TITLE)).always()
					.respondWith(responseText);
			UserInteractor userInteractor = generateInteractor(testInteraction);
			userInteractor.getTextInputDialogBuilder().message(DIALOG_TITLE).title(DIALOG_TITLE).startInteraction();
			assertThat(userInteractor.getTextInputDialogBuilder().message(DIALOG_TITLE).title(DIALOG_TITLE)
					.startInteraction(), equalTo(responseText));
		}

		@Test
		@DisplayName("required multiple but provided a single time")
		public void provideSingleButRequireMultipleTimes() {
			String responseText = "response";
			TestUserInteraction testInteraction = new TestUserInteraction();
			testInteraction.onTextInput((description) -> description.getTitle().equals(DIALOG_TITLE))
					.respondWith(responseText);
			UserInteractor userInteractor = generateInteractor(testInteraction);
			userInteractor.getTextInputDialogBuilder().message(DIALOG_TITLE).title(DIALOG_TITLE).startInteraction();
			assertThrows(AssertionError.class, () -> userInteractor.getTextInputDialogBuilder().message(DIALOG_TITLE)
					.title(DIALOG_TITLE).startInteraction());
		}
	}

	@Nested
	@DisplayName("confirmation")
	class Confirmation {
		@Test
		@DisplayName("required and provided a single time")
		public void provideAndRequireSingleTime() {
			TestUserInteraction testInteraction = new TestUserInteraction();
			testInteraction.onConfirmation((description) -> description.getTitle().equals(DIALOG_TITLE))
					.respondWith(true);
			UserInteractor userInteractor = generateInteractor(testInteraction);
			assertThat(userInteractor.getConfirmationDialogBuilder().message(DIALOG_TITLE).title(DIALOG_TITLE)
					.startInteraction(), equalTo(true));
		}

		@Test
		@DisplayName("required and provided multiple times")
		public void provideAndRequireMultipleTimes() {
			TestUserInteraction testInteraction = new TestUserInteraction();
			testInteraction.onConfirmation((description) -> description.getTitle().equals(DIALOG_TITLE)).always()
					.respondWith(true);
			UserInteractor userInteractor = generateInteractor(testInteraction);
			userInteractor.getConfirmationDialogBuilder().message(DIALOG_TITLE).title(DIALOG_TITLE).startInteraction();
			assertThat(userInteractor.getConfirmationDialogBuilder().message(DIALOG_TITLE).title(DIALOG_TITLE)
					.startInteraction(), equalTo(true));
		}

		@Test
		@DisplayName("required multiple but provided a single time")
		public void provideSingleButRequireMultipleTimes() {
			TestUserInteraction testInteraction = new TestUserInteraction();
			testInteraction.onConfirmation((description) -> description.getTitle().equals(DIALOG_TITLE))
					.respondWith(true);
			UserInteractor userInteractor = generateInteractor(testInteraction);
			userInteractor.getConfirmationDialogBuilder().message(DIALOG_TITLE).title(DIALOG_TITLE).startInteraction();
			assertThrows(AssertionError.class, () -> userInteractor.getConfirmationDialogBuilder().message(DIALOG_TITLE)
					.title(DIALOG_TITLE).startInteraction());
		}
	}

	@Nested
	@DisplayName("single selection")
	class SingleSelection {
		@Nested
		@DisplayName("matched by string")
		class MatchedByString {
			@Test
			@DisplayName("required and provided a single time")
			public void provideAndRequireSingleTime() {
				String response = "selectedItem";
				List<String> choices = List.of("dummy", response);
				TestUserInteraction testInteraction = new TestUserInteraction();
				testInteraction
						.onMultipleChoiceSingleSelection((description) -> description.getTitle().equals(DIALOG_TITLE))
						.respondWith(response);
				UserInteractor userInteractor = generateInteractor(testInteraction);
				assertThat(userInteractor.getSingleSelectionDialogBuilder().message(DIALOG_TITLE).choices(choices)
						.title(DIALOG_TITLE).startInteraction(), equalTo(choices.indexOf(response)));
			}

			@Test
			@DisplayName("required and provided multiple times")
			public void provideAndRequireMultipleTimes() {
				String response = "selectedItem";
				List<String> choices = List.of("dummy", response);
				TestUserInteraction testInteraction = new TestUserInteraction();
				testInteraction
						.onMultipleChoiceSingleSelection((description) -> description.getTitle().equals(DIALOG_TITLE))
						.always().respondWith(response);
				UserInteractor userInteractor = generateInteractor(testInteraction);
				userInteractor.getSingleSelectionDialogBuilder().message(DIALOG_TITLE).choices(choices)
						.title(DIALOG_TITLE).startInteraction();
				assertThat(userInteractor.getSingleSelectionDialogBuilder().message(DIALOG_TITLE).choices(choices)
						.title(DIALOG_TITLE).startInteraction(), equalTo(choices.indexOf(response)));
			}

			@Test
			@DisplayName("required multiple but provided a single time")
			public void provideSingleButRequireMultipleTimes() {
				String response = "selectedItem";
				List<String> choices = List.of("dummy", response);
				TestUserInteraction testInteraction = new TestUserInteraction();
				testInteraction
						.onMultipleChoiceSingleSelection((description) -> description.getTitle().equals(DIALOG_TITLE))
						.respondWith(response);
				UserInteractor userInteractor = generateInteractor(testInteraction);
				userInteractor.getSingleSelectionDialogBuilder().message(DIALOG_TITLE).choices(choices)
						.title(DIALOG_TITLE).startInteraction();
				assertThrows(AssertionError.class, () -> userInteractor.getSingleSelectionDialogBuilder()
						.message(DIALOG_TITLE).choices(choices).title(DIALOG_TITLE).startInteraction());
			}
		}

		@Nested
		@DisplayName("matched by index")
		class MatchedByIndex {
			@Test
			@DisplayName("required and provided a single time")
			public void provideAndRequireSingleTime() {
				int selectedIndex = 1;
				List<String> choices = List.of("firstDummy", "secondDummy");
				TestUserInteraction testInteraction = new TestUserInteraction();
				testInteraction
						.onMultipleChoiceSingleSelection((description) -> description.getTitle().equals(DIALOG_TITLE))
						.respondWithChoiceAt(selectedIndex);
				UserInteractor userInteractor = generateInteractor(testInteraction);
				assertThat(userInteractor.getSingleSelectionDialogBuilder().message(DIALOG_TITLE).choices(choices)
						.title(DIALOG_TITLE).startInteraction(), equalTo(selectedIndex));
			}

			@Test
			@DisplayName("required and provided multiple times")
			public void provideAndRequireMultipleTimes() {
				int selectedIndex = 1;
				List<String> choices = List.of("firstDummy", "secondDummy");
				TestUserInteraction testInteraction = new TestUserInteraction();
				testInteraction
						.onMultipleChoiceSingleSelection((description) -> description.getTitle().equals(DIALOG_TITLE))
						.always().respondWithChoiceAt(selectedIndex);
				UserInteractor userInteractor = generateInteractor(testInteraction);
				userInteractor.getSingleSelectionDialogBuilder().message(DIALOG_TITLE).choices(choices)
						.title(DIALOG_TITLE).startInteraction();
				assertThat(userInteractor.getSingleSelectionDialogBuilder().message(DIALOG_TITLE).choices(choices)
						.title(DIALOG_TITLE).startInteraction(), equalTo(selectedIndex));
			}

			@Test
			@DisplayName("required multiple but provided a single time")
			public void provideSingleButRequireMultipleTimes() {
				int selectedIndex = 1;
				List<String> choices = List.of("firstDummy", "secondDummy");
				TestUserInteraction testInteraction = new TestUserInteraction();
				testInteraction
						.onMultipleChoiceSingleSelection((description) -> description.getTitle().equals(DIALOG_TITLE))
						.respondWithChoiceAt(selectedIndex);
				UserInteractor userInteractor = generateInteractor(testInteraction);
				userInteractor.getSingleSelectionDialogBuilder().message(DIALOG_TITLE).choices(choices)
						.title(DIALOG_TITLE).startInteraction();
				assertThrows(AssertionError.class, () -> userInteractor.getSingleSelectionDialogBuilder()
						.message(DIALOG_TITLE).choices(choices).title(DIALOG_TITLE).startInteraction());
			}
		}
	}

	@Nested
	@DisplayName("multiple selection")
	class MultipleSelection {
		@Nested
		@DisplayName("with no element selected")
		class NoIndexSelected {
			@Test
			@DisplayName("required and provided a single time")
			public void provideAndRequireSingleTime() {
				List<String> choices = List.of("firstDummy", "secondDummy");
				TestUserInteraction testInteraction = new TestUserInteraction();
				testInteraction
						.onMultipleChoiceMultiSelection((description) -> description.getTitle().equals(DIALOG_TITLE))
						.respondWith(emptySet());
				UserInteractor userInteractor = generateInteractor(testInteraction);
				assertThat(userInteractor.getMultiSelectionDialogBuilder().message(DIALOG_TITLE).choices(choices)
						.title(DIALOG_TITLE).startInteraction(), equalTo(emptyList()));
			}

			@Test
			@DisplayName("required and provided multiple times")
			public void provideAndRequireMultipleTimes() {
				List<String> choices = List.of("firstDummy", "secondDummy");
				TestUserInteraction testInteraction = new TestUserInteraction();
				testInteraction
						.onMultipleChoiceMultiSelection((description) -> description.getTitle().equals(DIALOG_TITLE))
						.always().respondWith(new String[] {});
				UserInteractor userInteractor = generateInteractor(testInteraction);
				userInteractor.getMultiSelectionDialogBuilder().message(DIALOG_TITLE).choices(choices)
						.title(DIALOG_TITLE).startInteraction();
				assertThat(userInteractor.getMultiSelectionDialogBuilder().message(DIALOG_TITLE).choices(choices)
						.title(DIALOG_TITLE).startInteraction(), equalTo(emptyList()));
			}

			@Test
			@DisplayName("required multiple but provided a single time")
			public void provideSingleButRequireMultipleTimes() {
				List<String> choices = List.of("firstDummy", "secondDummy");
				TestUserInteraction testInteraction = new TestUserInteraction();
				testInteraction
						.onMultipleChoiceMultiSelection((description) -> description.getTitle().equals(DIALOG_TITLE))
						.respondWith(new String[] {});
				UserInteractor userInteractor = generateInteractor(testInteraction);
				userInteractor.getMultiSelectionDialogBuilder().message(DIALOG_TITLE).choices(choices)
						.title(DIALOG_TITLE).startInteraction();
				assertThrows(AssertionError.class, () -> userInteractor.getMultiSelectionDialogBuilder()
						.message(DIALOG_TITLE).choices(choices).title(DIALOG_TITLE).startInteraction());
			}
		}

		@Nested
		@DisplayName("with index selected")
		class IndexSelected {
			@Test
			@DisplayName("required and provided a single time")
			public void provideAndRequireSingleTime() {
				int response = 0;
				List<String> choices = List.of("firstDummy", "secondDummy");
				TestUserInteraction testInteraction = new TestUserInteraction();
				testInteraction
						.onMultipleChoiceMultiSelection((description) -> description.getTitle().equals(DIALOG_TITLE))
						.respondWithChoicesAt(new int[] { response });
				UserInteractor userInteractor = generateInteractor(testInteraction);
				assertThat(userInteractor.getMultiSelectionDialogBuilder().message(DIALOG_TITLE).choices(choices)
						.title(DIALOG_TITLE).startInteraction(), equalTo(List.of(response)));
			}

			@Test
			@DisplayName("required and provided multiple times")
			public void provideAndRequireMultipleTimes() {
				int response = 0;
				List<String> choices = List.of("firstDummy", "secondDummy");
				TestUserInteraction testInteraction = new TestUserInteraction();
				testInteraction
						.onMultipleChoiceMultiSelection((description) -> description.getTitle().equals(DIALOG_TITLE))
						.always().respondWithChoicesAt(new int[] { response });
				UserInteractor userInteractor = generateInteractor(testInteraction);
				userInteractor.getMultiSelectionDialogBuilder().message(DIALOG_TITLE).choices(choices)
						.title(DIALOG_TITLE).startInteraction();
				assertThat(userInteractor.getMultiSelectionDialogBuilder().message(DIALOG_TITLE).choices(choices)
						.title(DIALOG_TITLE).startInteraction(), equalTo(List.of(response)));
			}

			@Test
			@DisplayName("required multiple but provided a single time")
			public void provideSingleButRequireMultipleTimes() {
				int response = 0;
				List<String> choices = List.of("firstDummy", "secondDummy");
				TestUserInteraction testInteraction = new TestUserInteraction();
				testInteraction
						.onMultipleChoiceMultiSelection((description) -> description.getTitle().equals(DIALOG_TITLE))
						.respondWithChoicesAt(new int[] { response });
				UserInteractor userInteractor = generateInteractor(testInteraction);
				userInteractor.getMultiSelectionDialogBuilder().message(DIALOG_TITLE).choices(choices)
						.title(DIALOG_TITLE).startInteraction();
				assertThrows(AssertionError.class, () -> userInteractor.getMultiSelectionDialogBuilder()
						.message(DIALOG_TITLE).choices(choices).title(DIALOG_TITLE).startInteraction());
			}
		}

		@Nested
		@DisplayName("with string selected")
		class StringSelected {
			@Test
			@DisplayName("required and provided a single time")
			public void provideAndRequireSingleTime() {
				String response = "selectedItem";
				List<String> choices = List.of("firstDummy", response);
				TestUserInteraction testInteraction = new TestUserInteraction();
				testInteraction
						.onMultipleChoiceMultiSelection((description) -> description.getTitle().equals(DIALOG_TITLE))
						.respondWith(new String[] { response });
				UserInteractor userInteractor = generateInteractor(testInteraction);
				assertThat(userInteractor.getMultiSelectionDialogBuilder().message(DIALOG_TITLE).choices(choices)
						.title(DIALOG_TITLE).startInteraction(), equalTo(List.of(choices.indexOf(response))));
			}

			@Test
			@DisplayName("required and provided multiple times")
			public void provideAndRequireMultipleTimes() {
				String response = "selectedItem";
				List<String> choices = List.of("dummy", response);
				TestUserInteraction testInteraction = new TestUserInteraction();
				testInteraction
						.onMultipleChoiceMultiSelection((description) -> description.getTitle().equals(DIALOG_TITLE))
						.always().respondWith(new String[] { response });
				UserInteractor userInteractor = generateInteractor(testInteraction);
				userInteractor.getMultiSelectionDialogBuilder().message(DIALOG_TITLE).choices(choices)
						.title(DIALOG_TITLE).startInteraction();
				assertThat(userInteractor.getMultiSelectionDialogBuilder().message(DIALOG_TITLE).choices(choices)
						.title(DIALOG_TITLE).startInteraction(), equalTo(List.of(choices.indexOf(response))));
			}

			@Test
			@DisplayName("required multiple but provided a single time")
			public void provideSingleButRequireMultipleTimes() {
				String response = "selectedItem";
				List<String> choices = List.of("dummy", response);
				TestUserInteraction testInteraction = new TestUserInteraction();
				testInteraction
						.onMultipleChoiceMultiSelection((description) -> description.getTitle().equals(DIALOG_TITLE))
						.respondWith(new String[] { response });
				UserInteractor userInteractor = generateInteractor(testInteraction);
				userInteractor.getMultiSelectionDialogBuilder().message(DIALOG_TITLE).choices(choices)
						.title(DIALOG_TITLE).startInteraction();
				assertThrows(AssertionError.class, () -> userInteractor.getMultiSelectionDialogBuilder()
						.message(DIALOG_TITLE).choices(choices).title(DIALOG_TITLE).startInteraction());
			}
		}

	}

	private UserInteractor generateInteractor(TestUserInteraction testInteraction) {
		return UserInteractionFactory.instance.createUserInteractor(new ResultProvider(testInteraction));
	}
}
