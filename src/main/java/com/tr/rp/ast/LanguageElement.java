package com.tr.rp.ast;

import java.util.Set;

public interface LanguageElement {

	/**
	 * Return list of variables occurring in this language element.
     *
	 * @param list Variables occurring in this language element
	 */
	public void getVariables(Set<String> list);

	/**
	 * Replace occurrences of variable a with b.
	 * 
	 * @param a Variable to replace
	 * @param b Variable to replace with
	 * @return Transformed language element.
	 */
	public LanguageElement replaceVariable(String a, String b);
	
}
