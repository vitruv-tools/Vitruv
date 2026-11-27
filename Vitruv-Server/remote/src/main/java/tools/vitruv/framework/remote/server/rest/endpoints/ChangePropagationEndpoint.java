package tools.vitruv.framework.remote.server.rest.endpoints;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_CONFLICT;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import java.io.IOException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import tools.vitruv.change.atomic.root.InsertRootEObject;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.common.rest.constants.Header;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.PatchEndpoint;
import tools.vitruv.framework.views.impl.ModifiableView;
import tools.vitruv.framework.views.impl.ViewCreatingViewType;

/** This endpoint applies given {@link VitruviusChange}s to the VSUM. */
public class ChangePropagationEndpoint implements PatchEndpoint {
  private static final String ENDPOINT_METRIC_NAME = "vitruv.server.rest.propagation";
  private final JsonMapper mapper;

  /**
   * Creates a new ChangePropagationEndpoint.
   *
   * @param mapper The JSON mapper to use.
   */
  public ChangePropagationEndpoint(JsonMapper mapper) {
    this.mapper = mapper;
  }

  @SuppressWarnings("unchecked")
  @Override
  public String process(HttpWrapper wrapper) {
    var view = Cache.getView(wrapper.getRequestHeader(Header.VIEW_UUID));
    if (view == null) {
      throw notFound("View with given id not found!");
    }

    String body;
    try {
      body = wrapper.getRequestBodyAsString();
    } catch (IOException e) {
      throw internalServerError(e.getMessage());
    }

    @SuppressWarnings("rawtypes")
    VitruviusChange change;
    var desTimer = Timer.start(Metrics.globalRegistry);
    try {
      change = mapper.deserialize(body, VitruviusChange.class);
      desTimer.stop(Metrics.timer(ENDPOINT_METRIC_NAME, "deserialization", "success"));
    } catch (JsonProcessingException e) {
      desTimer.stop(Metrics.timer(ENDPOINT_METRIC_NAME, "deserialization", "failure"));
      throw new ServerHaltingException(HTTP_BAD_REQUEST, e.getMessage());
    }
    change
        .getEChanges()
        .forEach(
            it -> {
              if (it instanceof InsertRootEObject<?> echange) {
                echange.setResource(new ResourceImpl(URI.createURI(echange.getUri())));
              }
            });

    var type = (ViewCreatingViewType<?, ?>) view.getViewType();
    var propTimer = Timer.start(Metrics.globalRegistry);
    try {
      type.commitViewChanges((ModifiableView) view, change);
      propTimer.stop(Metrics.timer(ENDPOINT_METRIC_NAME, "propagation", "success"));
    } catch (RuntimeException e) {
      propTimer.stop(Metrics.timer(ENDPOINT_METRIC_NAME, "propagation", "failure"));
      throw new ServerHaltingException(HTTP_CONFLICT, "Changes rejected: " + e.getMessage());
    }
    return null;
  }
}
