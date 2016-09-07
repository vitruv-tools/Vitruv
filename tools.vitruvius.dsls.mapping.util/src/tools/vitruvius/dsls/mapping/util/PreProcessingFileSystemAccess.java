package tools.vitruvius.dsls.mapping.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.xtext.generator.IFileSystemAccess;

/**
 * Wrapper class for {@link IFileSystemAccess} that allows the registering of
 * pre-processors for a file extension.
 * 
 * Note that extensions are case insensitive.
 * 
 * @author Dominik Werle
 */
public class PreProcessingFileSystemAccess implements IFileSystemAccess {
	@FunctionalInterface
	public static interface Preprocessor {
		public CharSequence format(CharSequence input);
	}

	public static IFileSystemAccess createJavaFormattingFSA(IFileSystemAccess fsa) {
		PreProcessingFileSystemAccess result = new PreProcessingFileSystemAccess(fsa);

		// TODO: possibly outsource because it creates dependencies to
		// jface-plugins
		final CodeFormatter codeformatter = ToolFactory.createCodeFormatter(null);
		Preprocessor preprocessor = (input) -> {
			Document document = new Document(input.toString());
			TextEdit formatEdit = codeformatter.format(CodeFormatter.K_COMPILATION_UNIT, document.get(), 0,
					document.getLength(), 0, null);

			try {
				formatEdit.apply(document);
			} catch (MalformedTreeException | BadLocationException e) {
				e.printStackTrace();
			}
			return document.get();
		};

		result.registerPreprocessor("java", preprocessor);

		return result;
	}

	private final IFileSystemAccess delegate;

	// TODO: allow multiple registering?
	private final Map<String, Preprocessor> preprocessors;

	public PreProcessingFileSystemAccess(IFileSystemAccess delegate) {
		this.delegate = delegate;
		this.preprocessors = new HashMap<>();
	}

	public void registerPreprocessor(String extension, Preprocessor preprocessor) {
		preprocessors.put(extension.toLowerCase(), preprocessor);
	}

	private CharSequence preprocess(String fileName, CharSequence contents) {
		String fileExtension = URI.createFileURI(fileName).fileExtension().toLowerCase();

		CharSequence processedContent = preprocessors.containsKey(fileExtension)
				? preprocessors.get(fileExtension).format(contents) : contents;

		return processedContent;
	}

	@Override
	public void generateFile(String fileName, CharSequence contents) {
		delegate.generateFile(fileName, preprocess(fileName, contents));
	}

	@Override
	public void generateFile(String fileName, String outputConfigurationName, CharSequence contents) {
		delegate.generateFile(fileName, outputConfigurationName, preprocess(fileName, contents));
	}

	@Override
	public void deleteFile(String fileName) {
		delegate.deleteFile(fileName);
	}
}