package tools.vitruv.framework.versioning.commit.impl

import java.util.Date

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import org.eclipse.xtend.lib.annotations.Data

import tools.vitruv.framework.versioning.commit.CommitMessage

@Data
class CommitMessageImpl implements CommitMessage {
	static extension Gson = new GsonBuilder().create
	Date date
	String message
	String authorName
	String authorEMail

	override getSerialization() {
		toJson(this)
	}
}
