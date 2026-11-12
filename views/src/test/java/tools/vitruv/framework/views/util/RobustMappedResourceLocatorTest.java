package tools.vitruv.framework.views.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RobustMappedResourceLocatorTest {
  private static final URI FIRST_URI = URI.createURI("test://first.xmi");
  private static final URI SECOND_URI = URI.createURI("test://second.xmi");

  private ResourceSetImpl resourceSet;
  private Resource first;
  private Resource second;

  @BeforeEach
  void setup() {
    resourceSet = new ResourceSetImpl();
    new RobustMappedResourceLocator(resourceSet);
    first = new ResourceImpl(FIRST_URI);
    second = new ResourceImpl(SECOND_URI);
    resourceSet.getResources().add(first);
    resourceSet.getResources().add(second);
  }

  @Test
  @DisplayName("finds resources by their URI")
  void findsResources() {
    assertThat(resourceSet.getResource(FIRST_URI, false), is(first));
    assertThat(resourceSet.getResource(SECOND_URI, false), is(second));
  }

  @Test
  @DisplayName("follows changes of a resource's URI")
  void followsUriChange() {
    URI movedUri = URI.createURI("test://moved.xmi");
    first.setURI(movedUri);
    assertThat(resourceSet.getResource(FIRST_URI, false), is(nullValue()));
    assertThat(resourceSet.getResource(movedUri, false), is(first));
  }

  @Test
  @DisplayName("removes all resources at once after the URI map changed")
  void clearAfterUriMapChange() {
    // makes the locator rebuild its index on the next access, which happens while the resources
    // are removed and no longer in the resource set
    resourceSet
        .getURIConverter()
        .getURIMap()
        .put(URI.createURI("pathmap://SOME/"), URI.createURI("file:/some/"));
    assertDoesNotThrow(() -> resourceSet.getResources().clear());

    assertThat(resourceSet.getResource(FIRST_URI, false), is(nullValue()));
    resourceSet.getResources().add(first);
    assertThat(resourceSet.getResource(FIRST_URI, false), is(first));
  }
}
