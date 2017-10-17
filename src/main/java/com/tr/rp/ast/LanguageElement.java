package com.tr.rp.ast;

import java.util.Set;

public interface LanguageElement {

	/**
	 * Return list of variables occurring in this language element.
     *
	 * @param list Variables occurring in this language element
	 */
	public void getVariables(Set<String> list);
	
}
