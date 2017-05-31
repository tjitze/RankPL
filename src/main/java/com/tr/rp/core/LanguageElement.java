package com.tr.rp.core;

import java.util.Set;

public interface LanguageElement {

	/**
	 * Return true if this element contains (possibly as a sub expression)
	 * a variable with given name.
	 */
	public boolean containsVariable(String var);
	
	public void getVariables(Set<String> list);
	
	/**
	 * Return a copy of this element where all occurrences of the variable
	 * called 'a' are replaced with variables called 'b'.
	 */
	public LanguageElement replaceVariable(String a, String b);
	
}
