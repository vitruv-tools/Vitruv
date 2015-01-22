package edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.plugintests.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.lang.StringUtils;

import difflib.DiffUtils;
import difflib.Patch;
import difflib.myers.Equalizer;

public class DirDiffer {

	private final File originalDirectory;
	private final File changedDirectory;
	private final List<String> extensionsToCompare;
	
	public DirDiffer(File originalDirectory, File changedDirectory, String... extensionsToCompare) {
		this.originalDirectory = originalDirectory;
		this.changedDirectory = changedDirectory;
		this.extensionsToCompare = Arrays.asList(extensionsToCompare);
	}
	
	public String getPatch() throws IOException {
		List<String> patchLines = new Vector<String>();
		Iterator<File> originalIter = FileUtils.iterateFiles(originalDirectory, new SuffixFileFilter(extensionsToCompare), DirectoryFileFilter.INSTANCE);
		while (originalIter.hasNext()) {
			File originalFile = originalIter.next();
			String relativePath = originalFile.toString().replace(originalDirectory.toString(), "");
			File changedFile = new File(changedDirectory, relativePath);
			patchLines.addAll(createPatchPart(originalFile, changedFile, relativePath));
		}
		
		return StringUtils.join(patchLines, '\n');
	}
	
	private List<String> createPatchPart(File original, File changed, String relativePath) throws IOException {
		String relativePathWithoutLeadingSeparator = relativePath;
		if (relativePath.charAt(0) == File.separatorChar) {
			relativePathWithoutLeadingSeparator = relativePath.substring(1);
		}
		InputStream originalIs = null;
		InputStream changedIs = null;
		try {
			originalIs = new FileInputStream(original);
			List<String> originalLines = IOUtils.readLines(originalIs);
			List<String> changedLines = new ArrayList<String>();
			if (changed.exists()) {
				changedIs = new FileInputStream(changed);
				changedLines = IOUtils.readLines(changedIs);
			}
			
			DiffUtils.diff(originalLines, changedLines, createWhitespaceIgnoringEqualizer());
			Patch<String> patch = DiffUtils.diff(originalLines, changedLines);	
			if (patch.getDeltas().size() == 0) {
				return Collections.emptyList();
			}
			List<String> patchLines = DiffUtils.generateUnifiedDiff(relativePathWithoutLeadingSeparator, relativePathWithoutLeadingSeparator, originalLines, patch, 3);
			List<String> patchLinesWithDelimiter = new ArrayList<String>(patchLines.size() + 3);
			patchLinesWithDelimiter.add("===================================================================");
			patchLinesWithDelimiter.addAll(patchLines);
			patchLinesWithDelimiter.add("");
			return patchLinesWithDelimiter;
		} finally {
			IOUtils.closeQuietly(originalIs);
			IOUtils.closeQuietly(changedIs);
		}

	}
	
	private static Equalizer<String> createWhitespaceIgnoringEqualizer() {
		return new Equalizer<String>() {
			public boolean equals(String original, String revised) {
				original = original.trim().replaceAll("\\s+", " ");
				revised = revised.trim().replaceAll("\\s+", " ");
				return original.equals(revised);
			}
		};
	}
}
