package tools.vitruv.dsls.reactions.generator

import static extension java.lang.Character.*
import static tools.vitruv.dsls.reactions.generator.ReactionsEnvironmentGenerator.*
import edu.kit.ipd.sdq.activextendannotations.Lazy
import java.io.InputStreamReader
import java.io.IOException
import java.io.OutputStream
import java.util.function.Predicate

import java.io.OutputStreamWriter
import java.util.HashSet
import java.util.Collections
import edu.kit.ipd.sdq.activextendannotations.CloseResource
import java.io.InputStream

/**
 * Parser for ChangePropagationSpecification files, as they are written by
 * {@link ReactionsEnvironmentGenerator}. Allows to read in a file and change
 * the contained executors on the fly, while writing to an output.
 */
class ChangePropagationFileParser {
	// This parser operates solely on unicode code points, represented by ints
	// The term “readThrough” means that the read characters are written to the
	// output throughout this class
	
	@Lazy static val int[] EXECUTORS_MARKER_START_CODE_POINTS = EXECUTORS_MARKER_START.codePoints.toArray
	@Lazy static val int[] EXECUTORS_MARKER_END_CODE_POINTS = EXECUTORS_MARKER_END.codePoints.toArray
	@Lazy static val int[] VERSION_MARKER_CODE_POINTS = VERSION_MARKER.codePoints.toArray

	@Lazy static val int[] START_MARKER_KMP_TABLE = kmpTableFor(EXECUTORS_MARKER_START_CODE_POINTS)
	@Lazy static val int[] VERSION_MARKER_KMP_TABLE = kmpTableFor(VERSION_MARKER_CODE_POINTS)
	@Lazy static val String EXECUTOR_LIST_WHITESPACE = System.lineSeparator +
		Collections.nCopies(EXECUTOR_LIST_INDENT, '\t').join

	static val CONSTRUCTOR_REFERENCE = "::new".toCharArray()
	static val FULL_STOP = 0x002E
	static val COMMA = 0x002C

	// we’re using the string length (instead of code point length) for the
	// cache size, which is an overestimation but does not initialize the code
	// points fields.
	static val CACHE_LENGTH = Math.max(EXECUTORS_MARKER_START.length, VERSION_MARKER.length) + 1

	val InputStreamReader input

	// a read circular read cache, allowing us to jump back to already read
	// characters. Is used for the kmp search and as a lookahead cache later
	// on. It follows that a lookahead may not exceed the start marker length.
	// When writing through, only the characters that were discarded by a
	// jump back ( <=> we jumped behind them) will be written to the output.
	// The others might still be used by code that does not which to read
	// through.
	val matchCache = newIntArrayOfSize(CACHE_LENGTH)
	// marks the position in the matchCache to write the next read code point to
	var matchCacheWritePosition = 0
	// marks the position in the matchCache to read the next code point from	
	var matchCacheReadPosition = 0
	// marks the position that should be written to output next
	var matchCacheWriteBackPosition = 0

	val OutputStreamWriter output

	/**
	 * Creates a new parser that can be used to read the 
	 * ChangePropagationSpecification from {@code input} and write results to
	 * {@code Output}.
	 */
	new(InputStream input, OutputStream output) {
		this.input = new InputStreamReader(input, CHANGE_PROPAGATION_FILE_CHARSET)
		this.output = new OutputStreamWriter(output, CHANGE_PROPAGATION_FILE_CHARSET)
	}

	/**
	 * Creates the pattern table used in Knuth-Morris-Pratt for the provided
	 * needle.
	 */
	def private static kmpTableFor(int[] needle) {
		val needleLength = needle.length
		val result = newIntArrayOfSize(Math.max(2, needleLength + 1))
		var position = 2
		var prefixLength = 0
		result.set(0, -1)
		result.set(1, 0)

		while (position < needleLength) {
			if (needle.get(position - 1) === needle.get(prefixLength)) {
				prefixLength += 1
				result.set(position, prefixLength)
				position += 1
			} else if (prefixLength > 0) {
				prefixLength = result.get(prefixLength)
			} else {
				result.set(position, 0)
				position += 1
			}
		}
		result
	}

	def private static pp(int cachePosition) {
		(cachePosition + 1) % CACHE_LENGTH
	}

	def private static m(int cachePosition, int length) {
		(cachePosition + CACHE_LENGTH - length) % CACHE_LENGTH
	}

	def private void writeCacheToBeRead(int character) {
		jumpBack(1)
		matchCache.set(matchCacheReadPosition, character)
	}

	/**
	 * Reads on character and writes it to the cache. Clients must remember
	 * to call {@link #jumpBack} or {@link #jumpBackAndWrite} from time 
	 * to time to ensure the cache does not overflow 
	 */
	def private readCached() throws IOException {
		var int result
		if (matchCacheReadPosition != matchCacheWritePosition) {
			result = matchCache.get(matchCacheReadPosition)
		} else {
			result = input.read()
			matchCache.set(matchCacheWritePosition, result)
			matchCacheWritePosition = matchCacheWritePosition.pp
		}
		matchCacheReadPosition = matchCacheReadPosition.pp
		return result
	}

	/**
	 * Reads the next character from input. That means either to read the next
	 * character from cache, if any, or to read a character from the input
	 * stream.
	 */
	def private read() throws IOException {
		if (matchCacheReadPosition != matchCacheWritePosition) {
			val result = matchCache.get(matchCacheReadPosition)
			matchCacheReadPosition = matchCacheReadPosition.pp
			matchCacheWriteBackPosition = matchCacheReadPosition
			result
		} else {
			input.read()
		}
	}

	/**
	 * „jumps back“ in the character stream, meaning that cached characters
	 * will be read again.
	 * 
	 * <p> This method also communicates that all characters before the character
	 * that was jumped to can be removed from the cache. So calling
	 * {@code jumpBack(0)} simply clears the cache. 
	 * 
	 * @param length
	 * 		„How far“ to jump back. 
	 */
	def private jumpBack(int length) {
		matchCacheReadPosition = matchCacheReadPosition.m(length)
		matchCacheWriteBackPosition = matchCacheReadPosition
	}

	/**
	 * Like {@link #jumpBack}, but writes those characters in the cache that
	 * are before the character that was jumped to to the output instead of
	 * discarding them.
	 * 
	 * @param length
	 * 		„How far“ to jump back. 
	 */
	def private jumpBackAndWrite(int length) throws IOException {
		matchCacheReadPosition = matchCacheReadPosition.m(length)
		for (; matchCacheWriteBackPosition !=
			matchCacheReadPosition; matchCacheWriteBackPosition = matchCacheWriteBackPosition.pp) {
			output.write(matchCache.get(matchCacheWriteBackPosition))
		}
	}

	/**
	 * Jumps over any whitespace.
	 * 
	 * @param readThrough
	 * 		Whether to write the characters that were jumped over to the output.
	 */
	def private skipWhitespace(boolean readThrough) throws IOException {
		var int character
		while ((character = read()).isWhitespace) {
			if (readThrough) {
				output.write(character)
			}
		}
		writeCacheToBeRead(character)
	}

	/**
	 * Checks whether the next character is {@code c}.
	 * 
	 * @param c
	 * 		The character to look for.
	 * @param readThrough
	 * 		Whether to write {@code c} to the output if it’s found.
	 */
	def private nextIs(int c, boolean readThrough) throws IOException {
		val character = read()
		val result = character === c
		if (result) {
			if (readThrough) {
				output.write(c)
			}
		} else {
			writeCacheToBeRead(character)
		}
		result
	}

	def private nextIs(char[] needle) throws IOException {
		// assert needle < cacheLength!
		var i = 0
		for (; readCached() === needle.get(i); i++) {
			if (i === needle.length - 1) {
				jumpBack(0)
				return true
			}
		}
		jumpBack(i)
		return false
	}

	/**
	 * Reads all following, contiguous characters that can be part of class
	 * name and return them.
	 */
	def private readClassName() throws IOException {
		val result = new StringBuilder()
		var int character
		while ((character = read()).isJavaIdentifierPart || character === FULL_STOP) {
			result.appendCodePoint(character)
		}
		writeCacheToBeRead(character)
		return result.toString
	}

	/**
	 * Reads all following, contiguous characters that can be part of an
	 * integer and return them.
	 */
	def private readThroughInteger() throws IOException {
		var int result = 0
		var int character
		while ((character = read()).isDigit) {
			result = result * 10 + character.digit(10)
			output.write(character)
		}
		writeCacheToBeRead(character)
		return result
	}

	/**
	 * Checks that the end marker follows at this position, throws an exception
	 * if it doesn’t.
	 */
	def private checkNextIsEndMarker() throws IOException, ParseException {
		val pattern = EXECUTORS_MARKER_END_CODE_POINTS
		for (var patternPosition = 0; patternPosition < pattern.length; patternPosition++) {
			if (read() !== pattern.get(patternPosition)) {
				throw new ParseException("does not contain the end marker comment!")
			}
		}
	}

	/**
	 * Reads in the ChangePropagationFile, finds the contained executors,
	 * retains or removes them according to {@code retainExecutor} and adds
	 * the provided {@code newExecutors}. Throws an exception if the file
	 * contains any errors.
	 * 
	 * @param newExecutors
	 * 		The executors that must be added to the file.
	 * @param retainExecutor
	 * 		Predicate that is evaluated for every found executor that’s not in
	 * 		{@code newExecutors}. Said executor will be kept if the
	 * 	predicate return {@code true} and be removed if it returns
	 * 		{@code false}.
	 * @throws IOException
	 * 		If an exception occurs while reading the input.
	 * @throws ParseException
	 * 		If the file is malformed in any way, for example if it doesn’t
	 * 		contain a required marker or was created from an old template 
	 * 		version.
	 */
	def changeExecutors(Iterable<String> newExecutors,
		Predicate<String> retainExecutor) throws ParseException, IOException {
		safelyChangeExecutors(newExecutors, retainExecutor, input, output)
	}

	/**
	 * Actual logic of {@link #changeExecutors}, in its own method to surely
	 * close the streams.
	 */
	def private void safelyChangeExecutors(Iterable<String> newExecutors, Predicate<String> retainExecutor,
		@CloseResource InputStreamReader in, @CloseResource OutputStreamWriter out) throws ParseException, IOException {
		readThroughAndCheckTemplateVersion()
		if (!readThroughToStartMarker()) {
			throw new ParseException("does not contain the start marker comment!")
		}
		val exectuorsToWrite = newExecutors.toSet
		skipWhitespace(true)
		val Predicate<String> retainNotAddedExecutor = [exectuorsToWrite.contains(it) || retainExecutor.test(it)]
		val existingExecutors = readThroughExistingExecutors(retainNotAddedExecutor)
		exectuorsToWrite -= existingExecutors
		writeExecutors(exectuorsToWrite, existingExecutors.isEmpty)
		output.write(EXECUTOR_LIST_WHITESPACE)
		output.write(EXECUTORS_MARKER_END)
		flush()
	}

	/**
	 * Writes all remaining input characters to the output.
	 */
	def private flush() throws IOException {
		var int character
		while ((character = read()) !== -1) {
			output.write(character)
		}
	}

	/**
	 * Reads all following executors and writes them to the ouptut if 
	 * {@code retainExecutor} returns {@code true} for them.
	 */
	def private readThroughExistingExecutors(Predicate<String> retainExecutor) throws IOException, ParseException  {
		val existingExecutors = new HashSet<String>
		var String nextClassName
		while ((nextClassName = readClassName()) != "") {
			if (!nextIs(CONSTRUCTOR_REFERENCE)) {
				throw new ParseException('''contains the class “«nextClassName»” without following constructor reference!''')
			}
			val retainNext = retainExecutor.test(nextClassName)
			if (retainNext) {
				existingExecutors.add(nextClassName)
				output.write(nextClassName)
				output.write(CONSTRUCTOR_REFERENCE)
			}
			val readThrough = retainNext
			// whitespace at this point is syntactically correct but not nicely
			// formatted so we discard it.
			skipWhitespace(false)
			if (!nextIs(COMMA, readThrough)) {
				checkNextIsEndMarker()
				return existingExecutors
			} else {
				skipWhitespace(readThrough)
			}
		}
		if (existingExecutors.isEmpty) {
			checkNextIsEndMarker()
			return existingExecutors
		}
		throw new ParseException(
			"contains a malformed list before the end marker comment or no end marker comment at all!")
	}

	def private writeExecutors(Iterable<String> executors, boolean isFirst) throws IOException {
		val separator = EXECUTOR_LIST_WHITESPACE
		var needSeparation = !isFirst
		for (executor : executors) {
			if (needSeparation) {
				output.write(',')
				output.write(separator)
			} else {
				needSeparation = true
			}
			output.write(executor)
			output.write(CONSTRUCTOR_REFERENCE)
		}
	}

	/**
	 * Reads through input characters until the version marker is found. Checks
	 * that the version is 
	 * {@link ReactionsEnvironmentGenerator#TEMPLATE_VERSION}. Throws an
	 * exception if the versions don’t match or the marker cannot be found.
	 */
	def private readThroughAndCheckTemplateVersion() throws IOException, ParseException {
		if (!readThroughToVersionMarker()) {
			throw new ParseException("does not contain a version marker!")
		}
		skipWhitespace(true)
		val version = readThroughInteger()
		if (version !== TEMPLATE_VERSION) {
			throw new ParseException("was created from an old template version!")
		}
	}

	/**
	 * Reads through input characters until a kmp pattern is found. Leaves the
	 * state such that the character read next is the first character after the
	 * pattern.
	 * 
	 * @param pattern
	 * 		The search pattern’s code points.
	 * @param table
	 * 		The search pattern’s KMP table, as created by 
	 * 		{@link #kmpPatternFor}.
	 * @return {@code true} iff the pattern was found.
	 */
	def private readThroughKmpSearch(int[] pattern, int[] table) throws IOException, ParseException {
		var int character
		var int patternPosition = 0
		while ((character = readCached()) !== -1) {
			if (character === pattern.get(patternPosition)) {
				if (patternPosition === pattern.length - 1) {
					jumpBackAndWrite(0)
					return true
				}
				patternPosition += 1
			} else {
				val patternValue = table.get(patternPosition)
				// we’re *always* advancing by one character. So to re-read the
				// current character, we need to jump back by one, not by zero
				// like in usual KMP implementations
				jumpBackAndWrite(patternValue + 1)
				patternPosition = Math.max(0, patternValue)
			}
		}
		return false
	}

	/**
	 * Reads through until behind the start marker.
	 * 
	 * @return {@code true} iff the start marker was found.
	 */
	def private readThroughToStartMarker() throws IOException, ParseException {
		readThroughKmpSearch(EXECUTORS_MARKER_START_CODE_POINTS, tools.vitruv.dsls.reactions.generator.ChangePropagationFileParser.getSTART_MARKER_KMP_TABLE)
	}

	/**
	 * Reads through until behind the version marker.
	 * 
	 * @return {@code true} iff the version marker was found.
	 */
	def private readThroughToVersionMarker() throws IOException, ParseException {
		readThroughKmpSearch(VERSION_MARKER_CODE_POINTS, tools.vitruv.dsls.reactions.generator.ChangePropagationFileParser.getVERSION_MARKER_KMP_TABLE)
	}

	static class ParseException extends Exception {
		new(String message) {
			super("The ChangePropagationSpecification file " + message)
		}
	}
}
