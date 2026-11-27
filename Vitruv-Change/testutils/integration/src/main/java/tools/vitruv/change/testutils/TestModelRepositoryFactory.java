package tools.vitruv.change.testutils;

import edu.kit.ipd.sdq.activextendannotations.Utility;
import java.io.IOException;
import java.nio.file.Files;
import tools.vitruv.change.composite.propagation.ChangeableModelRepository;
import tools.vitruv.change.interaction.InternalUserInteractor;
import tools.vitruv.change.interaction.UserInteractionFactory;
import tools.vitruv.change.propagation.ChangePropagationSpecificationProvider;
import tools.vitruv.change.propagation.PersistableChangeRecordingModelRepository;
import tools.vitruv.change.propagation.impl.DefaultChangeRecordingModelRepository;
import tools.vitruv.change.propagation.impl.DefaultChangeableModelRepository;

/** Factory for creating test model repositories. */
@Utility
public class TestModelRepositoryFactory {
  private TestModelRepositoryFactory() {
    throw new UnsupportedOperationException("Utility class");
  }

  /**
   * Creates a {@link ChangeableModelRepository} for the given change propagation
   * specification
   * provider and user interaction, which stores metadata in a temporary folder.
   *
   * @param changePropagationSpecificationProvider provides the {@link
   *                                               tools.vitruv.change.propagation.ChangePropagationSpecification}
   *                                               to use in the repository
   * @param userInteraction                        the {@link TestUserInteraction}
   *                                               to use for interactions during
   *                                               change
   *                                               propagation
   * @return the test model repository
   * @throws IOException is thrown if no temporary directory can be created
   */
  public static ChangeableModelRepository createTestChangeableModelRepository(
      ChangePropagationSpecificationProvider changePropagationSpecificationProvider,
      TestUserInteraction userInteraction)
      throws IOException {
    PersistableChangeRecordingModelRepository recordingModelRepository = new DefaultChangeRecordingModelRepository(null,
        Files.createTempDirectory(null));
    return createTestChangeableModelRepository(
        recordingModelRepository, changePropagationSpecificationProvider, userInteraction);
  }

  /**
   * Creates a {@link ChangeableModelRepository} for the given change propagation
   * specification
   * provider, user interaction, and persistable change recording model
   * repository.
   *
   * @param modelRepository                        manages where files are stored.
   * @param changePropagationSpecificationProvider provides the {@link
   *                                               tools.vitruv.change.propagation.ChangePropagationSpecification}
   *                                               to use in the repository
   * @param userInteraction                        the {@link TestUserInteraction}
   *                                               to use for interactions during
   *                                               change
   *                                               propagation
   * @return the test model repository
   */
  public static ChangeableModelRepository createTestChangeableModelRepository(
      PersistableChangeRecordingModelRepository modelRepository,
      ChangePropagationSpecificationProvider changePropagationSpecificationProvider,
      TestUserInteraction userInteraction) {
    InternalUserInteractor userInteractor = UserInteractionFactory.instance.createUserInteractor(
        new TestUserInteraction.ResultProvider(userInteraction));
    return new DefaultChangeableModelRepository(
        modelRepository,
        changePropagationSpecificationProvider,
        userInteractor);
  }
}
