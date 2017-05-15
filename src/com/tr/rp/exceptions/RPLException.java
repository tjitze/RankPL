package com.tr.rp.exceptions;

import java.util.ArrayList;
import java.util.List;

import com.tr.rp.core.DStatement;

public abstract class RPLException extends Exception {

	private List<DStatement> statements = new ArrayList<DStatement>();
	
	public void addStatement(DStatement statement) {
		statements.add(statement);
	}
	
	public abstract String getDescription();
		
	public String toString() {
		String desc = getDescription();
		if (statements.size() > 0) {
			int lineNumber = statements.get(0).getLineNumber();
			if (lineNumber != -1) {
				desc += " at line " + lineNumber;
			}
			desc += " (" + statements.get(0).toString() + ")";
		}
		return desc;
	}
}
