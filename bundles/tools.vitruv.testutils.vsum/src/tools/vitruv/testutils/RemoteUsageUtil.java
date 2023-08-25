package tools.vitruv.testutils;

public class RemoteUsageUtil {
	
	private RemoteUsageUtil() throws InstantiationException {
		throw new InstantiationException("Cannot be instantiated");
	}
	
	public static boolean shouldUseRemote() {
		var argument = System.getProperty("remote");
		return Boolean.parseBoolean(argument);
	}
}
