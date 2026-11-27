package tools.vitruv.framework.remote.server.rest.endpoints;

import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.common.rest.constants.Header;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.GetEndpoint;
import tools.vitruv.framework.views.View;

/** This endpoint returns whether a {@link tools.vitruv.framework.views.View View} is closed. */
public class IsViewClosedEndpoint implements GetEndpoint {
  @Override
  public String process(HttpWrapper wrapper) {
    View view = Cache.getView(wrapper.getRequestHeader(Header.VIEW_UUID));
    if (view == null) {
      return Boolean.TRUE.toString();
    }
    if (view.isClosed()) {
      Cache.removeView(wrapper.getRequestHeader(Header.VIEW_UUID));
    }
    wrapper.setContentType(ContentType.TEXT_PLAIN);
    return view.isClosed() ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
  }
}
