package tools.vitruv.dsls.reactions.helper;

public class StringHelper {
	public static String lastFragmentOrComplete(String string, String separator) {
		final int lastIndex = string.lastIndexOf(separator);
		if (lastIndex == -1) {
			return string;
		} else {
			return string.substring(lastIndex + separator.length());
		}
	}
}
