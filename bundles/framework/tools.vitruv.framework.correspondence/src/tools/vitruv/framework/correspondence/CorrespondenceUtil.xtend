package tools.vitruv.framework.correspondence

import java.util.List
import tools.vitruv.framework.tuid.Tuid

class CorrespondenceUtil {
	static def List<Tuid> getCorrespondingTuids(Correspondence correspondence, List<Tuid> tuids) {
		var List<Tuid> aTuids = correspondence.getATuids()
		var List<Tuid> bTuids = correspondence.getBTuids()
		if (aTuids === null || bTuids === null || aTuids.size == 0 || bTuids.size == 0) {
			throw new IllegalStateException(
				'''The correspondence '«»«correspondence»' links to an empty Tuid '«»«aTuids»' or '«»«bTuids»'!'''.
					toString)
		}
		if (aTuids.equals(tuids)) {
			return bTuids
		} else {
			return aTuids
		}
	}
	
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
	
	static def isUuidBased(Correspondence correspondence) {
		return !correspondence.AUuids.empty && !correspondence.BUuids.empty
	}
}