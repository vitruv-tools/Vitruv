package tools.vitruv.testutils;

import tools.vitruv.framework.views.impl.IdentityMappingViewType;

@SuppressWarnings("restriction")
public class TestViewType extends IdentityMappingViewType {

	public TestViewType() {
		super("changeme");
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
