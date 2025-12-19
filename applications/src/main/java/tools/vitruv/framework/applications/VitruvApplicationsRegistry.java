package tools.vitruv.framework.applications;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

/** Registry for managing Vitruv applications that can be discovered and registered. */
public class VitruvApplicationsRegistry {
  public static final String EXTENSION_POINT_ID = "tools.vitruv.framework.applications.application";

  private static final Logger LOGGER = LogManager.getLogger(VitruvApplicationsRegistry.class);
  private static final VitruvApplicationsRegistry instance = new VitruvApplicationsRegistry();

  private final Collection<VitruvApplication> applications;
  private boolean initialized;

  private VitruvApplicationsRegistry() {
    this.applications = new HashSet<>();
    this.initialized = false;
  }

  /**
   * Gets the singleton instance of this registry.
   *
   * @return the singleton instance
   */
  public static VitruvApplicationsRegistry getInstance() {
    return instance;
  }

  /**
   * Adds the given application to the registry.
   *
   * @param application the {@link VitruvApplication} to add, must not be null
   * @return true if the application was added, false if it was already present
   * @throws IllegalArgumentException if application is null
   */
  public boolean addApplication(VitruvApplication application) {
    checkArgument(application != null, "Application must not be null");
    return applications.add(application);
  }

  /**
   * Gets all applications registered in this registry. On first access, applications are loaded
   * from the extension point.
   *
   * @return an unmodifiable collection of registered applications
   */
  public Collection<VitruvApplication> getApplications() {
    if (!initialized) {
      applications.addAll(
          (Collection<? extends VitruvApplication>) getAllApplicationsFromExtensionPoint());
      initialized = true;
    }
    return Collections.unmodifiableCollection(applications);
  }

  /**
   * Retrieves all applications from the extensions registered to the VitruvApplication extension
   * point.
   *
   * @return an iterable of applications discovered from the extension point
   */
  private static Iterable<VitruvApplication> getAllApplicationsFromExtensionPoint() {
    List<VitruvApplication> applications = new ArrayList<>();

    if (!Platform.isRunning()) {
      return applications;
    }

    IConfigurationElement[] elements =
        Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT_ID);

    for (IConfigurationElement element : elements) {
      try {
        Object executableExtension = element.createExecutableExtension("class");
        if (executableExtension instanceof VitruvApplication application) {
          applications.add(application);
        }
      } catch (Exception e) {
        LOGGER.warn("Error when loading application for extension: {}", element, e);
      }
    }
    return applications;
  }
}
