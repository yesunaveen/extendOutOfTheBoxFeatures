package com.yesu.extend;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CurseLanguageFilter implements LanguageFilter {
	
	private List<String> curseWords;
	private Predicate<String> containsCurseWords = getCurseWords()::contains;
	/*
	 * This can be converted to a service in itself
	 * where the service will either read from external
	 * RESTful APIs or database
	 */
	private List<String> getCurseWords() {
		if(curseWords == null) {
			/*
			 * the first time this method is called
			 * it will read the list of curse words
			 * from a file or database.
			 * In this case we will use an example of
			 * read from file.
			 */
			curseWords = new ArrayList<>();
			/*
			 * This path can be setup as a spring resource using the Value annotation
			 */
			Path curseWordsFilePath = Paths.get("/pathTo/curse_words.txt");
			try( Stream<String> stream = Files.lines(curseWordsFilePath) ) {
				/*
				 * during comparison, both the list of disallowed words 
				 * and text of the review should be either upper case or lower case
				 */
				stream.map(String::toUpperCase)
				.forEach(curseWords::add);
			} catch (IOException e) {
				// TODO log exception
				e.printStackTrace();
			}	
		}
		return curseWords;
	}
	
	/*
	 * This method will return false if "language" contains
	 * any curse word 
	 */
	@Override
	public boolean isLanguageAllowed(String language) {
		/*
		 * The input string parameter will be split
		 * into an array of words which will be streamed
		 * through in parallel to find at least 1 curse
		 * word. If found false will be returned
		 */
		boolean isOffensive = Arrays.stream(language.split("\\s"))
			.parallel()
			.map(String::toUpperCase)
			.anyMatch(containsCurseWords);
		return !isOffensive;
	}

}