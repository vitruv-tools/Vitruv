package tools.vitruv.dsls.commonalities.ui.quickfix

import org.hamcrest.TypeSafeMatcher
import org.hamcrest.Description
import org.eclipse.xtext.validation.Issue
import java.util.List
import javax.inject.Inject
import com.google.inject.Singleton
import org.eclipse.xtext.validation.IResourceValidator
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.validation.CheckMode
import static java.lang.System.lineSeparator
import org.hamcrest.Matcher
import org.eclipse.xtext.ui.editor.model.IXtextDocument
import static org.eclipse.xtext.ui.editor.utils.EditorUtils.getActiveXtextEditor
import org.eclipse.swt.widgets.Display
import tools.vitruv.framework.util.Capture
import static extension tools.vitruv.framework.util.Capture.*
import java.util.ArrayList

@Singleton
class XtextAssertions {
	@Inject
	protected IResourceValidator resourceValidator

	def Matcher<? super IXtextDocument> hasNoValidationIssues() {
		new NoValidationIssues(resourceValidator)
	}
	
	def Matcher<? super IXtextDocument> hasIssues(String... codes) {
		new HasIssuesWithCode(resourceValidator, codes)
	}

	def static IXtextDocument getCurrentlyOpenedXtextDocument() {
		val document = new Capture<IXtextDocument>
		Display.^default.syncExec[activeXtextEditor.document >> document]
		return -document
	}
	
	@FinalFieldsConstructor
	private static class NoValidationIssues extends TypeSafeMatcher<IXtextDocument> {
		val IResourceValidator resourceValidator
		var List<Issue> issues

		override protected matchesSafely(IXtextDocument document) {
			issues = document.readOnly [ state |
				resourceValidator.validate(state, CheckMode.ALL, null)
			]
			return issues.isEmpty
		}

		override describeTo(Description description) {
			description.appendText("an Xtext document with no validation issues")
		}

		override protected describeMismatchSafely(IXtextDocument document, Description mismatchDescription) {
			mismatchDescription.appendText("the document ").appendValue(document.resourceURI) //
			.appendText(" had the following issues:").printIssueList(issues, false)
		}
	}

	@FinalFieldsConstructor
	private static class HasIssuesWithCode extends TypeSafeMatcher<IXtextDocument> {
		val IResourceValidator resourceValidator
		val List<String> expectedIssueCodes
		val List<String> missingIssueCodes = new ArrayList()
		var List<Issue> issues

		override describeTo(Description description) {
			description.appendText("an Xtext document with at least these issues: ").appendValueList('[', ', ', ']',
				expectedIssueCodes)
		}

		override protected matchesSafely(IXtextDocument document) {
			issues = document.readOnly [ state |
				resourceValidator.validate(state, CheckMode.ALL, null)
			]
			var issuesCopy = new ArrayList(issues)
			for (issueCode: expectedIssueCodes) {
				var found = false
				for (val iter = issuesCopy.iterator(); iter.hasNext() && !found;) {
					if (iter.next().code == issueCode) {
						found = true
						iter.remove()
					}
				}
				if (!found) {
					missingIssueCodes += issueCode
				}
			}
			return !expectedIssueCodes.exists[!issues.exists[code == it]]
		}

		override protected describeMismatchSafely(IXtextDocument document, Description mismatchDescription) {
			mismatchDescription.appendText("the document ").appendValue(document.resourceURI) //
			.appendText(" lacked these issues: ").appendValueList('[', ',', ']', missingIssueCodes).appendText(
				lineSeparator).appendText('    all found issues were:').printIssueList(issues, true)

		}
	}

	def private static printIssueList(Description description, List<Issue> issues, boolean printCode) {
		issues.forEach [
			description.appendText(lineSeparator).appendText('      • ').appendText(severity.toString).appendText(': ').
				appendText(message).appendText(' (').appendText('@lines ').appendText(lineNumber.toString).
				appendText(':').appendText(column.toString).appendText('–').appendText(lineNumberEnd.toString).
				appendText(':').appendText(columnEnd.toString).appendText(')')
			if (printCode) {
				description.appendText(' (code: ').appendValue(code).appendText(')')
			}
		]
	}
}
