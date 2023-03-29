package tools.vitruv.framework.remote.server;

import java.util.HashMap;

import tools.vitruv.framework.views.View;

/**
 * A Cache used to hold {@link View}s as long as the corresponding remote view has not yet been closed.
 */
public class ViewCache {
	
	private ViewCache() {
		throw new IllegalAccessError();
	}
	
	private static final HashMap<String, View> cache = new HashMap<>();
	
	public static void  addView(String uuid, View view) {
		cache.put(uuid, view);
	}
	
	public static View getView(String uuid) {
		return cache.get(uuid);
	}
	
	public static View removeView(String uuid) {
		return cache.remove(uuid);
	}
}
