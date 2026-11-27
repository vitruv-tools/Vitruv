package tools.vitruv.change.testutils;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tools.vitruv.change.interaction.UserInteractionFactory;
import tools.vitruv.change.interaction.UserInteractor;
import tools.vitruv.change.interaction.builder.ConfirmationInteractionBuilder;
import tools.vitruv.change.interaction.builder.InteractionBuilder;
import tools.vitruv.change.interaction.builder.MultipleChoiceSelectionInteractionBuilder;
import tools.vitruv.change.interaction.builder.TextInputInteractionBuilder;
import tools.vitruv.change.testutils.TestUserInteraction.ResultProvider;

/** Tests the {@link TestUserInteraction} class. */
public class TestUserInteractionTest {
    private static final String DIALOG_TITLE = "test";

    private TestUserInteraction testInteraction;

    /** Sets up the test interaction for each test. */
    @BeforeEach
    public void setupInteraction() {
        testInteraction = new TestUserInteraction();
    }

    /** Asserts that all interactions have occurred. */
    @AfterEach
    void assertAllInteractionOccurred() {
        testInteraction.assertAllInteractionsOccurred();
    }

    @Nested
    @DisplayName("text input")
    class TextInput {
        @Test
        @DisplayName("required and provided a single time")
        void provideAndRequireSingleTime() {
            String responseText = "response";
            testInteraction
                    .onTextInput(description -> description.getTitle().equals(DIALOG_TITLE))
                    .respondWith(responseText);
            UserInteractor userInteractor = generateInteractor(testInteraction);
            assertThat(
                    userInteractor
                            .getTextInputDialogBuilder()
                            .message(DIALOG_TITLE)
                            .title(DIALOG_TITLE)
                            .startInteraction(),
                    equalTo(responseText));
        }

        @Test
        @DisplayName("required and provided multiple times")
        void provideAndRequireMultipleTimes() {
            String responseText = "response";
            testInteraction
                    .onTextInput(description -> description.getTitle().equals(DIALOG_TITLE))
                    .always()
                    .respondWith(responseText);
            UserInteractor userInteractor = generateInteractor(testInteraction);
            userInteractor
                    .getTextInputDialogBuilder()
                    .message(DIALOG_TITLE)
                    .title(DIALOG_TITLE)
                    .startInteraction();
            assertThat(
                    userInteractor
                            .getTextInputDialogBuilder()
                            .message(DIALOG_TITLE)
                            .title(DIALOG_TITLE)
                            .startInteraction(),
                    equalTo(responseText));
        }

        @Test
        @DisplayName("required multiple but provided a single time")
        void provideSingleButRequireMultipleTimes() {
            String responseText = "response";
            testInteraction
                    .onTextInput(description -> description.getTitle().equals(DIALOG_TITLE))
                    .respondWith(responseText);
            UserInteractor userInteractor = generateInteractor(testInteraction);
            userInteractor
                    .getTextInputDialogBuilder()
                    .message(DIALOG_TITLE)
                    .title(DIALOG_TITLE)
                    .startInteraction();
            TextInputInteractionBuilder.OptionalSteps optionalSteps = userInteractor
                    .getTextInputDialogBuilder()
                    .message(DIALOG_TITLE)
                    .title(DIALOG_TITLE);
            assertThrows(
                    AssertionError.class,
                    () -> optionalSteps
                            .startInteraction());
        }
    }

    @Nested
    @DisplayName("confirmation")
    class Confirmation {
        @Test
        @DisplayName("required and provided a single time")
        void provideAndRequireSingleTime() {
            testInteraction
                    .onConfirmation(description -> description.getTitle().equals(DIALOG_TITLE))
                    .respondWith(true);
            UserInteractor userInteractor = generateInteractor(testInteraction);
            assertThat(
                    userInteractor
                            .getConfirmationDialogBuilder()
                            .message(DIALOG_TITLE)
                            .title(DIALOG_TITLE)
                            .startInteraction(),
                    equalTo(true));
        }

        @Test
        @DisplayName("required and provided multiple times")
        void provideAndRequireMultipleTimes() {
            testInteraction
                    .onConfirmation(description -> description.getTitle().equals(DIALOG_TITLE))
                    .always()
                    .respondWith(true);
            UserInteractor userInteractor = generateInteractor(testInteraction);
            userInteractor
                    .getConfirmationDialogBuilder()
                    .message(DIALOG_TITLE)
                    .title(DIALOG_TITLE)
                    .startInteraction();
            assertThat(
                    userInteractor
                            .getConfirmationDialogBuilder()
                            .message(DIALOG_TITLE)
                            .title(DIALOG_TITLE)
                            .startInteraction(),
                    equalTo(true));
        }

        @Test
        @DisplayName("required multiple but provided a single time")
        void provideSingleButRequireMultipleTimes() {
            testInteraction
                    .onConfirmation(description -> description.getTitle().equals(DIALOG_TITLE))
                    .respondWith(true);
            UserInteractor userInteractor = generateInteractor(testInteraction);
            userInteractor
                    .getConfirmationDialogBuilder()
                    .message(DIALOG_TITLE)
                    .title(DIALOG_TITLE)
                    .startInteraction();
            ConfirmationInteractionBuilder.OptionalSteps optionalSteps = userInteractor
                    .getConfirmationDialogBuilder()
                    .message(DIALOG_TITLE)
                    .title(DIALOG_TITLE);
            assertThrows(
                    AssertionError.class,
                    () -> optionalSteps
                            .startInteraction());
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
            void provideAndRequireSingleTime() {
                String response = "selectedItem";
                List<String> choices = List.of("dummy", response);
                testInteraction
                        .onMultipleChoiceSingleSelection(
                                description -> description.getTitle().equals(DIALOG_TITLE))
                        .respondWith(response);
                UserInteractor userInteractor = generateInteractor(testInteraction);
                assertThat(
                        userInteractor
                                .getSingleSelectionDialogBuilder()
                                .message(DIALOG_TITLE)
                                .choices(choices)
                                .title(DIALOG_TITLE)
                                .startInteraction(),
                        equalTo(choices.indexOf(response)));
            }

            @Test
            @DisplayName("required and provided multiple times")
            void provideAndRequireMultipleTimes() {
                String response = "selectedItem";
                List<String> choices = List.of("dummy", response);
                testInteraction
                        .onMultipleChoiceSingleSelection(
                                description -> description.getTitle().equals(DIALOG_TITLE))
                        .always()
                        .respondWith(response);
                UserInteractor userInteractor = generateInteractor(testInteraction);
                userInteractor
                        .getSingleSelectionDialogBuilder()
                        .message(DIALOG_TITLE)
                        .choices(choices)
                        .title(DIALOG_TITLE)
                        .startInteraction();
                assertThat(
                        userInteractor
                                .getSingleSelectionDialogBuilder()
                                .message(DIALOG_TITLE)
                                .choices(choices)
                                .title(DIALOG_TITLE)
                                .startInteraction(),
                        equalTo(choices.indexOf(response)));
            }

            @Test
            @DisplayName("required multiple but provided a single time")
            void provideSingleButRequireMultipleTimes() {
                String response = "selectedItem";
                List<String> choices = List.of("dummy", response);
                testInteraction
                        .onMultipleChoiceSingleSelection(
                                description -> description.getTitle().equals(DIALOG_TITLE))
                        .respondWith(response);
                UserInteractor userInteractor = generateInteractor(testInteraction);
                userInteractor
                        .getSingleSelectionDialogBuilder()
                        .message(DIALOG_TITLE)
                        .choices(choices)
                        .title(DIALOG_TITLE)
                        .startInteraction();
                MultipleChoiceSelectionInteractionBuilder.OptionalSteps<Integer> optionalSteps = userInteractor
                        .getSingleSelectionDialogBuilder()
                        .message(DIALOG_TITLE)
                        .choices(choices)
                        .title(DIALOG_TITLE);
                assertThrows(
                        AssertionError.class,
                        () -> optionalSteps.startInteraction());
            }
        }

        @Nested
        @DisplayName("matched by index")
        class MatchedByIndex {
            @Test
            @DisplayName("required and provided a single time")
            void provideAndRequireSingleTime() {
                int selectedIndex = 1;
                List<String> choices = List.of("firstDummy", "secondDummy");
                testInteraction
                        .onMultipleChoiceSingleSelection(
                                description -> description.getTitle().equals(DIALOG_TITLE))
                        .respondWithChoiceAt(selectedIndex);
                UserInteractor userInteractor = generateInteractor(testInteraction);
                assertThat(
                        userInteractor
                                .getSingleSelectionDialogBuilder()
                                .message(DIALOG_TITLE)
                                .choices(choices)
                                .title(DIALOG_TITLE)
                                .startInteraction(),
                        equalTo(selectedIndex));
            }

            @Test
            @DisplayName("required and provided multiple times")
            void provideAndRequireMultipleTimes() {
                int selectedIndex = 1;
                List<String> choices = List.of("firstDummy", "secondDummy");
                testInteraction
                        .onMultipleChoiceSingleSelection(
                                description -> description.getTitle().equals(DIALOG_TITLE))
                        .always()
                        .respondWithChoiceAt(selectedIndex);
                UserInteractor userInteractor = generateInteractor(testInteraction);
                userInteractor
                        .getSingleSelectionDialogBuilder()
                        .message(DIALOG_TITLE)
                        .choices(choices)
                        .title(DIALOG_TITLE)
                        .startInteraction();
                assertThat(
                        userInteractor
                                .getSingleSelectionDialogBuilder()
                                .message(DIALOG_TITLE)
                                .choices(choices)
                                .title(DIALOG_TITLE)
                                .startInteraction(),
                        equalTo(selectedIndex));
            }

            @Test
            @DisplayName("required multiple but provided a single time")
            void provideSingleButRequireMultipleTimes() {
                int selectedIndex = 1;
                List<String> choices = List.of("firstDummy", "secondDummy");
                testInteraction
                        .onMultipleChoiceSingleSelection(
                                description -> description.getTitle().equals(DIALOG_TITLE))
                        .respondWithChoiceAt(selectedIndex);
                UserInteractor userInteractor = generateInteractor(testInteraction);
                userInteractor
                        .getSingleSelectionDialogBuilder()
                        .message(DIALOG_TITLE)
                        .choices(choices)
                        .title(DIALOG_TITLE)
                        .startInteraction();
                MultipleChoiceSelectionInteractionBuilder.OptionalSteps<Integer> optionalSteps = userInteractor
                        .getSingleSelectionDialogBuilder()
                        .message(DIALOG_TITLE)
                        .choices(choices)
                        .title(DIALOG_TITLE);
                assertThrows(
                        AssertionError.class,
                        () -> optionalSteps.startInteraction());
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
            void provideAndRequireSingleTime() {
                List<String> choices = List.of("firstDummy", "secondDummy");
                testInteraction
                        .onMultipleChoiceMultiSelection(
                                description -> description.getTitle().equals(DIALOG_TITLE))
                        .respondWith(emptySet());
                UserInteractor userInteractor = generateInteractor(testInteraction);
                assertThat(
                        userInteractor
                                .getMultiSelectionDialogBuilder()
                                .message(DIALOG_TITLE)
                                .choices(choices)
                                .title(DIALOG_TITLE)
                                .startInteraction(),
                        equalTo(emptyList()));
            }

            @Test
            @DisplayName("required and provided multiple times")
            void provideAndRequireMultipleTimes() {
                List<String> choices = List.of("firstDummy", "secondDummy");
                testInteraction
                        .onMultipleChoiceMultiSelection(
                                description -> description.getTitle().equals(DIALOG_TITLE))
                        .always()
                        .respondWith(new String[] {});
                UserInteractor userInteractor = generateInteractor(testInteraction);
                userInteractor
                        .getMultiSelectionDialogBuilder()
                        .message(DIALOG_TITLE)
                        .choices(choices)
                        .title(DIALOG_TITLE)
                        .startInteraction();
                assertThat(
                        userInteractor
                                .getMultiSelectionDialogBuilder()
                                .message(DIALOG_TITLE)
                                .choices(choices)
                                .title(DIALOG_TITLE)
                                .startInteraction(),
                        equalTo(emptyList()));
            }

            @Test
            @DisplayName("required multiple but provided a single time")
            void provideSingleButRequireMultipleTimes() {
                List<String> choices = List.of("firstDummy", "secondDummy");
                testInteraction
                        .onMultipleChoiceMultiSelection(
                                description -> description.getTitle().equals(DIALOG_TITLE))
                        .respondWith(new String[] {});
                UserInteractor userInteractor = generateInteractor(testInteraction);
                userInteractor
                        .getMultiSelectionDialogBuilder()
                        .message(DIALOG_TITLE)
                        .choices(choices)
                        .title(DIALOG_TITLE)
                        .startInteraction();
                MultipleChoiceSelectionInteractionBuilder.OptionalSteps<Collection<Integer>> optionalSteps = userInteractor
                        .getMultiSelectionDialogBuilder()
                        .message(DIALOG_TITLE)
                        .choices(choices)
                        .title(DIALOG_TITLE);
                assertThrows(
                        AssertionError.class,
                        () -> optionalSteps.startInteraction());
            }
        }

        @Nested
        @DisplayName("with index selected")
        class IndexSelected {
            @Test
            @DisplayName("required and provided a single time")
            void provideAndRequireSingleTime() {
                int response = 0;
                List<String> choices = List.of("firstDummy", "secondDummy");
                testInteraction
                        .onMultipleChoiceMultiSelection(
                                description -> description.getTitle().equals(DIALOG_TITLE))
                        .respondWithChoicesAt(new int[] { response });
                UserInteractor userInteractor = generateInteractor(testInteraction);
                assertThat(
                        userInteractor
                                .getMultiSelectionDialogBuilder()
                                .message(DIALOG_TITLE)
                                .choices(choices)
                                .title(DIALOG_TITLE)
                                .startInteraction(),
                        equalTo(List.of(response)));
            }

            @Test
            @DisplayName("required and provided multiple times")
            void provideAndRequireMultipleTimes() {
                int response = 0;
                List<String> choices = List.of("firstDummy", "secondDummy");
                testInteraction
                        .onMultipleChoiceMultiSelection(
                                description -> description.getTitle().equals(DIALOG_TITLE))
                        .always()
                        .respondWithChoicesAt(new int[] { response });
                UserInteractor userInteractor = generateInteractor(testInteraction);
                userInteractor
                        .getMultiSelectionDialogBuilder()
                        .message(DIALOG_TITLE)
                        .choices(choices)
                        .title(DIALOG_TITLE)
                        .startInteraction();
                assertThat(
                        userInteractor
                                .getMultiSelectionDialogBuilder()
                                .message(DIALOG_TITLE)
                                .choices(choices)
                                .title(DIALOG_TITLE)
                                .startInteraction(),
                        equalTo(List.of(response)));
            }

            @Test
            @DisplayName("required multiple but provided a single time")
            void provideSingleButRequireMultipleTimes() {
                int response = 0;
                List<String> choices = List.of("firstDummy", "secondDummy");
                testInteraction
                        .onMultipleChoiceMultiSelection(
                                description -> description.getTitle().equals(DIALOG_TITLE))
                        .respondWithChoicesAt(new int[] { response });
                UserInteractor userInteractor = generateInteractor(testInteraction);
                userInteractor
                        .getMultiSelectionDialogBuilder()
                        .message(DIALOG_TITLE)
                        .choices(choices)
                        .title(DIALOG_TITLE)
                        .startInteraction();
                MultipleChoiceSelectionInteractionBuilder.OptionalSteps<Collection<Integer>> optionalSteps = userInteractor
                        .getMultiSelectionDialogBuilder()
                        .message(DIALOG_TITLE)
                        .choices(choices)
                        .title(DIALOG_TITLE);
                assertThrows(
                        AssertionError.class,
                        () -> optionalSteps.startInteraction());
            }
        }

        @Nested
        @DisplayName("with string selected")
        class StringSelected {
            @Test
            @DisplayName("required and provided a single time")
            void provideAndRequireSingleTime() {
                String response = "selectedItem";
                List<String> choices = List.of("firstDummy", response);
                testInteraction
                        .onMultipleChoiceMultiSelection(
                                description -> description.getTitle().equals(DIALOG_TITLE))
                        .respondWith(new String[] { response });
                UserInteractor userInteractor = generateInteractor(testInteraction);
                assertThat(
                        userInteractor
                                .getMultiSelectionDialogBuilder()
                                .message(DIALOG_TITLE)
                                .choices(choices)
                                .title(DIALOG_TITLE)
                                .startInteraction(),
                        equalTo(List.of(choices.indexOf(response))));
            }

            @Test
            @DisplayName("required and provided multiple times")
            void provideAndRequireMultipleTimes() {
                String response = "selectedItem";
                List<String> choices = List.of("dummy", response);
                testInteraction
                        .onMultipleChoiceMultiSelection(
                                description -> description.getTitle().equals(DIALOG_TITLE))
                        .always()
                        .respondWith(new String[] { response });
                UserInteractor userInteractor = generateInteractor(testInteraction);
                userInteractor
                        .getMultiSelectionDialogBuilder()
                        .message(DIALOG_TITLE)
                        .choices(choices)
                        .title(DIALOG_TITLE)
                        .startInteraction();
                assertThat(
                        userInteractor
                                .getMultiSelectionDialogBuilder()
                                .message(DIALOG_TITLE)
                                .choices(choices)
                                .title(DIALOG_TITLE)
                                .startInteraction(),
                        equalTo(List.of(choices.indexOf(response))));
            }

            @Test
            @DisplayName("required multiple but provided a single time")
            void provideSingleButRequireMultipleTimes() {
                String response = "selectedItem";
                List<String> choices = List.of("dummy", response);
                testInteraction
                        .onMultipleChoiceMultiSelection(
                                description -> description.getTitle().equals(DIALOG_TITLE))
                        .respondWith(new String[] { response });
                UserInteractor userInteractor = generateInteractor(testInteraction);
                userInteractor
                        .getMultiSelectionDialogBuilder()
                        .message(DIALOG_TITLE)
                        .choices(choices)
                        .title(DIALOG_TITLE)
                        .startInteraction();
                MultipleChoiceSelectionInteractionBuilder.OptionalSteps<Collection<Integer>> optionalSteps = userInteractor
                        .getMultiSelectionDialogBuilder()
                        .message(DIALOG_TITLE)
                        .choices(choices)
                        .title(DIALOG_TITLE);
                assertThrows(
                        AssertionError.class,
                        () -> optionalSteps.startInteraction());
            }
        }
    }

    private UserInteractor generateInteractor(TestUserInteraction testInteraction) {
        return UserInteractionFactory.instance.createUserInteractor(
                new ResultProvider(testInteraction));
    }
}
