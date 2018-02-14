package com.yesu.extend;

/*
 * Different implementations of this functional interface can be
 * used to filter out reviews, comments or any user input in general
 */
public interface LanguageFilter {
	
	boolean isLanguageAllowed(String language);

}
