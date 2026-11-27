package tools.vitruv.framework.remote.client.impl;

import org.eclipse.emf.ecore.EPackage;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewType;

/**
 * A Vitruvius view type representing actual types on the virtual model, but is still capable of
 * providing a view selector and allows creating views by querying the Vitruvius server.
 */
public class RemoteViewType implements ViewType<ViewSelector> {
  private final String name;
  private final VitruvRemoteConnection remoteConnection;
  private final EPackage metamodel;

  /**
   * Creates a new RemoteViewType.
   *
   * @param name the name of the view type
   * @param remoteConnection the remote connection to the Vitruvius server
   * @param metamodel the metamodel of the view type
   */
  RemoteViewType(String name, VitruvRemoteConnection remoteConnection, EPackage metamodel) {
    this.name = name;
    this.remoteConnection = remoteConnection;
    this.metamodel = metamodel;
  }

  /**
   * Creates a new RemoteViewType without metamodel.
   *
   * @param name the name of the view type
   * @param remoteConnection the remote connection to the Vitruvius server
   */
  RemoteViewType(String name, VitruvRemoteConnection remoteConnection) {
    this.name = name;
    this.remoteConnection = remoteConnection;
    this.metamodel = null;
  }

  @Override
  public String getName() {
    return name;
  }

  /**
   * Returns the {@link ViewSelector} of the {@link ViewType}, which allows configuring views by
   * delegating the request to the Vitruvius server.
   *
   * @param viewSource Ignored, can be null.
   * @return A view selector for the view type represented by this remote view type.
   */
  @Override
  public ViewSelector createSelector(ChangeableViewSource viewSource) {
    return remoteConnection.getSelector(name);
  }

  @Override
  public EPackage getMetamodel() {
    return this.metamodel;
  }
}
