package tools.vitruv.dsls.reactions.tests.importTests

import java.util.List
import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
class ImportTestsUtils {
	public static val TEST_NAME_PREFIX = "test:";
	public static val DATA_TAG_PREFIX = "data:";
	public static val DATA_TAG_SEPARATOR = ";";

	static def String toTestDataString(String testName, String... dataTags) {
		val dataTagsList = (dataTags as List<String> ?: #[]);
		return TEST_NAME_PREFIX + testName + dataTagsList.join(DATA_TAG_SEPARATOR, DATA_TAG_SEPARATOR, DATA_TAG_SEPARATOR, [DATA_TAG_PREFIX + it]);
	}

	static def boolean containsDataTag(String testDataString, String dataTag) {
		return testDataString !== null && testDataString.contains(DATA_TAG_SEPARATOR + DATA_TAG_PREFIX + dataTag + DATA_TAG_SEPARATOR)
	}
}
