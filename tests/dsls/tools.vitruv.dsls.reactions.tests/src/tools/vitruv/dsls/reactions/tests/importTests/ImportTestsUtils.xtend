package tools.vitruv.dsls.reactions.tests.importTests

import java.util.List

class ImportTestsUtils {
	private new() {
	}

	public static val TEST_NAME_PREFIX = "test:";
	public static val DATA_PREFIX = "data:";
	public static val DATA_SEPARATOR = ";";

	static def String toTestDataString(String testName, String... data) {
		val dataList = (data as List<String> ?: #[]);
		return TEST_NAME_PREFIX + testName + dataList.join(DATA_SEPARATOR, DATA_SEPARATOR, DATA_SEPARATOR, [DATA_PREFIX + it]);
	}

	static def boolean containsData(String testDataString, String data) {
		return testDataString !== null && testDataString.contains(DATA_SEPARATOR + DATA_PREFIX + data + DATA_SEPARATOR)
	}
}
