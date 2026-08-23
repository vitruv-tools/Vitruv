package tools.vitruv.framework.vsum;

import static com.google.common.base.Preconditions.checkState;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import tools.vitruv.change.interaction.InteractionResultProvider;
import tools.vitruv.change.interaction.InternalUserInteractor;
import tools.vitruv.change.interaction.UserInteractionFactory;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.change.propagation.ChangePropagationSpecificationRepository;
import tools.vitruv.change.utils.ProjectMarker;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.views.ViewTypeRepository;
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.framework.vsum.internal.VirtualModelImpl;

/** Builder for creating and initializing virtual models in the VSUM framework. */
public class VirtualModelBuilder {
  private final ViewTypeRepository viewTypeRepository = new ViewTypeRepository();
  private final Set<ChangePropagationSpecification> changePropagationSpecifications =
      new HashSet<>();
  private final Map<ChangePropagationSpecification, Integer> changePropagationSpecificationsToLevel =
      new HashMap<>();
  private Path storageFolder;
  private InternalUserInteractor userInteractor;

  /**
   * Sets the storage folder for the virtual model.
   *
   * @param folder the storage folder
   * @return the builder instance
   */
  public VirtualModelBuilder withStorageFolder(File folder) {
    return withStorageFolder(folder.toPath());
  }

  /**
   * Sets the storage folder for the virtual model.
   *
   * @param folder the storage folder
   * @return the builder instance
   */
  public VirtualModelBuilder withStorageFolder(Path folder) {
    checkState(
        storageFolder == null || storageFolder.equals(folder),
        "There is already another storage folder set: %s",
        storageFolder);
    storageFolder = folder;
    return this;
  }

  /**
   * Sets the user interactor for the virtual model using the given result provider.
   *
   * @param resultProvider the interaction result provider
   * @return the builder instance
   */
  public VirtualModelBuilder withUserInteractorForResultProvider(
      InteractionResultProvider resultProvider) {
    return withUserInteractor(UserInteractionFactory.instance.createUserInteractor(resultProvider));
  }

  /**
   * Sets the user interactor for the virtual model.
   *
   * @param userInteractor the user interactor
   * @return the builder instance
   */
  public VirtualModelBuilder withUserInteractor(InternalUserInteractor userInteractor) {
    checkState(
        this.userInteractor == null || this.userInteractor.equals(userInteractor),
        "There is already another user interactor set: %s",
        this.userInteractor);
    this.userInteractor = userInteractor;
    return this;
  }

  /**
   * Adds the given view type to the virtual model.
   *
   * @param viewType the view type to add
   * @return the builder instance
   */
  public VirtualModelBuilder withViewType(ViewType<?> viewType) {
    this.viewTypeRepository.register(viewType);
    return this;
  }

  /**
   * Adds a view type supplied by the given supplier to the virtual model.
   *
   * @param viewTypeSupplier the supplier of the view type
   * @return the builder instance
   */
  public VirtualModelBuilder withViewType(Function<ViewTypeRepository, ViewType<?>> viewTypeSupplier) {
    return withViewType(viewTypeSupplier.apply(viewTypeRepository));
  }

  /**
   * Adds the given view types to the virtual model.
   *
   * @param viewTypes the view types to add
   * @return the builder instance
   */
  public VirtualModelBuilder withViewTypes(Collection<ViewType<?>> viewTypes) {
    for (ViewType<?> viewType : viewTypes) {
      withViewType(viewType);
    }
    return this;
  }

  /**
   * Adds the given change propagation specifications to the virtual model.
   *
   * @param changePropagationSpecifications the change propagation specifications to add
   * @return the builder instance
   */
  public VirtualModelBuilder withChangePropagationSpecifications(
      ChangePropagationSpecification... changePropagationSpecifications) {
    for (ChangePropagationSpecification spec : changePropagationSpecifications) {
      withChangePropagationSpecification(spec);
    }
    return this;
  }

  /**
   * Adds the given change propagation specifications to the virtual model, at the given level.
   *
   * @param level the level of the change propagation specifications, determines ordering
   * @param changePropagationSpecifications the change propagation specifications to add
   * @return the builder instance
   */
  public VirtualModelBuilder withChangePropagationSpecifications(
          int level,
          ChangePropagationSpecification... changePropagationSpecifications) {
    for (ChangePropagationSpecification spec : changePropagationSpecifications) {
      withChangePropagationSpecification(level, spec);
    }
    return this;
  }

  /**
   * Adds the given change propagation specifications to the virtual model.
   *
   * @param changePropagationSpecifications the change propagation specifications to add
   * @return the builder instance
   */
  public VirtualModelBuilder withChangePropagationSpecifications(
      Iterable<ChangePropagationSpecification> changePropagationSpecifications) {
    for (ChangePropagationSpecification spec : changePropagationSpecifications) {
      withChangePropagationSpecification(spec);
    }
    return this;
  }

  /**
   * Adds the given change propagation specifications to the virtual model, at the given level.
   *
   * @param level the level of the change propagation specifications, determines ordering
   * @param changePropagationSpecifications the change propagation specifications to add
   * @return the builder instance
   */
  public VirtualModelBuilder withChangePropagationSpecifications(
          int level,
          Iterable<ChangePropagationSpecification> changePropagationSpecifications) {
    for (ChangePropagationSpecification spec : changePropagationSpecifications) {
      withChangePropagationSpecification(level, spec);
    }
    return this;
  }

  /**
   * Adds the given change propagation specification to the virtual model, at the given level.
   *
   * @param level the level of the change propagation specification, determines ordering
   * @param changePropagationSpecification the change propagation specification to add
   * @return the builder instance
   */
  public VirtualModelBuilder withChangePropagationSpecification(
          int level,
          ChangePropagationSpecification changePropagationSpecification) {
    withChangePropagationSpecification(changePropagationSpecification);
    changePropagationSpecificationsToLevel.put(changePropagationSpecification, level);
    return this;
  }

  /**
   * Adds the given change propagation specification to the virtual model.
   *
   * @param changePropagationSpecification the change propagation specification to add
   * @return the builder instance
   */
  public VirtualModelBuilder withChangePropagationSpecification(
      ChangePropagationSpecification changePropagationSpecification) {
    if (changePropagationSpecifications.contains(changePropagationSpecification)) {
      return this;
    }

    for (ChangePropagationSpecification existingPropagationSpecification :
        changePropagationSpecifications) {
      if (existingPropagationSpecification.equals(changePropagationSpecification)) {
        return this;
      }

      if (existingPropagationSpecification
              .getSourceMetamodelDescriptor()
              .equals(changePropagationSpecification.getSourceMetamodelDescriptor())
          && existingPropagationSpecification
              .getTargetMetamodelDescriptor()
              .equals(changePropagationSpecification.getTargetMetamodelDescriptor())) {
        throw new IllegalArgumentException(
            "This virtual model configuration already contains the change propagation specification"
                + " "
                + existingPropagationSpecification
                + " between "
                + existingPropagationSpecification.getSourceMetamodelDescriptor()
                + " and "
                + existingPropagationSpecification.getTargetMetamodelDescriptor()
                + "!");
      }
    }

    changePropagationSpecifications.add(changePropagationSpecification);
    return this;
  }

  /**
   * Builds and initializes the virtual model.
   *
   * @return the initialized virtual model
   * @throws IOException if an I/O error occurs during initialization
   */
  public InternalVirtualModel buildAndInitialize() throws IOException {
    checkState(storageFolder != null, "No storage folder was configured!");
    checkState(userInteractor != null, "No user interactor was configured!");

    ChangePropagationSpecificationRepository changeSpecificationRepository =
        new ChangePropagationSpecificationRepository(changePropagationSpecifications, changePropagationSpecificationsToLevel);

    VsumFileSystemLayout fileSystemLayout = new VsumFileSystemLayout(storageFolder);
    fileSystemLayout.prepare();

    VirtualModelImpl vsum =
        new VirtualModelImpl(
            fileSystemLayout, userInteractor, viewTypeRepository, changeSpecificationRepository);
    vsum.loadExistingModels();

    try {
      ProjectMarker.getProjectRootFolder(storageFolder);
    } catch (IllegalStateException exception) {
      ProjectMarker.markAsProjectRootFolder(storageFolder);
    }

    return vsum;
  }
}
