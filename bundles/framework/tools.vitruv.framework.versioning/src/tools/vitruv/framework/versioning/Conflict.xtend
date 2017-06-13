package tools.vitruv.framework.versioning

/**
 * 
 * 
 * @author Patrick Stoeckle <p.stoeckle@gmx.net>
 * @version 0.1.0
 * @since 2017-06-12
 */
interface Conflict {
	def int getLevenshteinDistance()
}
