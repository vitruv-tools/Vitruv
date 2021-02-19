package tools.vitruv.framework.correspondence

import java.util.List

class CorrespondenceUtil {
	static def getCorrespondingUuids(Correspondence correspondence, List<String> uuids) {
		var List<String> aUuids = correspondence.getAUuids()
		var List<String> bUuids = correspondence.getBUuids()
		if (aUuids === null || bUuids === null || aUuids.size == 0 || bUuids.size == 0) {
			throw new IllegalStateException(
				'''The correspondence '«»«correspondence»' links to an empty Uuid '«»«aUuids»' or '«»«bUuids»'!'''.
					toString)
		}
		if (aUuids.equals(uuids)) {
			return bUuids;
		} else {
			return aUuids
		}
	}
}