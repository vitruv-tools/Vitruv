package tools.vitruv.variability.vave.tests.comparator;

/*******************************************************************************
 * Copyright (c) 2014
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Benjamin Klatt - initial API and implementation and/or initial documentation
 *******************************************************************************/

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Utilities to handle normalization of elements, values, name spaces etc.
 */
public final class NormalizationUtil {

	private static Logger logger = Logger.getLogger(NormalizationUtil.class);

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	/** Disable constructor to prevent initialization. */
	private NormalizationUtil() {
	}

	/**
	 * Apply a set of normalizations patterns to a string. The patterns to apply are provided as a map, linked to a string the pattern should be replaced with in case of a match.
	 *
	 * @param original       The string to normalize.
	 * @param normalizations The map of normalization patterns and according replacements.
	 * @return The normalized string. If null was submitted, an empty string will be returned.
	 */
	public static String normalize(String original, Map<Pattern, String> normalizations) {
		String renamed = Strings.nullToEmpty(original);
		for (Pattern pattern : normalizations.keySet()) {
			String replaceString = normalizations.get(pattern);
			renamed = pattern.matcher(renamed).replaceAll(replaceString);
		}
		return renamed;
	}

	/**
	 * Normalize a name space string (with '.' separators).
	 *
	 * @param namespace      The original name space to normalize.
	 * @param normalizations The list of normalizations to apply.
	 * @return The normalized name space string.
	 */
	public static String normalizeNamespace(String namespace, LinkedHashMap<Pattern, String> normalizations) {
		namespace = Strings.nullToEmpty(namespace);

		for (Pattern pattern : normalizations.keySet()) {
			String replacement = normalizations.get(pattern);
			namespace = pattern.matcher(namespace).replaceAll(replacement);
		}
		return namespace;
	}

	/**
	 * Load the remove normalization pattern configuration from a provided configuration string.
	 *
	 * @param configString The configuration string containing the normalization definitions.
	 * @param suffix       The (file extension) suffix to be ignored and preserved by the normalization.
	 * @return The prepared normalization pattern map.
	 */
	public static LinkedHashMap<Pattern, String> loadRemoveNormalizations(String configString, String suffix) {

		suffix = Strings.nullToEmpty(suffix);
		configString = Strings.nullToEmpty(configString);

		LinkedHashMap<Pattern, String> normalizations = Maps.newLinkedHashMap();
		Iterable<String> entries = Splitter.on(LINE_SEPARATOR).omitEmptyStrings().trimResults().split(configString);
		for (String entry : entries) {
			if (entry.startsWith("*")) {
				String patternString = "(.*)" + entry.substring(1) + suffix;
				Pattern pattern = Pattern.compile(patternString);
				normalizations.put(pattern, "$1" + suffix);
			} else if (entry.endsWith("*")) {
				String patternString = entry.substring(0, entry.length()) + "(.*)" + suffix;
				Pattern pattern = Pattern.compile(patternString);
				normalizations.put(pattern, "$1" + suffix);
			} else {
				logger.warn("Classifier normalization config without * wildcard: " + entry);
				continue;
			}
		}
		return normalizations;
	}

	/**
	 * Load the replace normalization pattern configuration from a provided configutration string.
	 *
	 * @param configString The normalization pattern configuration string.
	 * @return The prepared normalization pattern map.
	 */
	public static LinkedHashMap<Pattern, String> loadReplaceNormalizations(String configString) {
		String normalizations = Strings.nullToEmpty(configString);
		Iterable<String> entries = Splitter.on(LINE_SEPARATOR).omitEmptyStrings().trimResults().split(normalizations);
		LinkedHashMap<Pattern, String> uriNormalizationPatterns = Maps.newLinkedHashMap();
		for (String entry : entries) {
			List<String> pair = Lists.newArrayList(Splitter.on("|").trimResults().split(entry));
			if (pair.size() != 2) {
				logger.warn("Invalid package normalization config: " + entry);
				continue;
			}
			Pattern pattern = Pattern.compile(pair.get(0));
			uriNormalizationPatterns.put(pattern, pair.get(1));
		}
		return uriNormalizationPatterns;
	}

}
