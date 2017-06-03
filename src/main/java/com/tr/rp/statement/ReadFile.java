package com.tr.rp.statement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.ExecutionContext;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLMiscException;
import com.tr.rp.expressions.AssignmentTarget;
import com.tr.rp.expressions.PersistentList;
import com.tr.rp.statement.FunctionCallForm.ExtractedExpression;

public class ReadFile extends DStatement {

	public enum InputMethod {
		OnePerLine
	}
	
	private final AssignmentTarget target;
	private final Expression path;
	private final InputMethod mode;
	
	public ReadFile(AssignmentTarget target, Expression path, InputMethod mode) {
		this.target = target;
		this.path = path;
		this.mode = mode;
	}
	
	@Override
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> parent, ExecutionContext c) throws RPLException {
		return new RankedIterator<VarStore>() {

			private String lastPath;
			private PersistentList lines;
			
			@Override
			public boolean next() throws RPLException {
				return parent.next();
			}

			@Override
			public VarStore getItem() throws RPLException {
				VarStore v = parent.getItem();
				if (v == null) {
					return null;
				}
				String currentPath = path.getStringValue(parent.getItem());
				if (lines == null || !lastPath.equals(currentPath)) {
					try {
						lastPath = currentPath;
						lines = new PersistentList(readFile(path.getStringValue(parent.getItem())).toArray());
					} catch (IOException e) {
						throw new RPLMiscException(e.toString());
					}
				}
				return target.assign(parent.getItem(), lines);
			}

			@Override
			public int getRank() {
				return parent.getRank();
			}
			
		};
	}

	@Override
	public void getVariables(Set<String> list) {
		target.getVariables(list);
		path.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new ReadFile((AssignmentTarget)target.replaceVariable(a, b), (Expression)path.replaceVariable(a, b), mode);
	}

	@Override
	public DStatement rewriteEmbeddedFunctionCalls() {
		ExtractedExpression rewrittenTarget = FunctionCallForm.extractFunctionCalls(target);
		ExtractedExpression rewrittenPath = FunctionCallForm.extractFunctionCalls(path);
		if (rewrittenTarget.isRewritten() || rewrittenPath.isRewritten()) {
			return new FunctionCallForm(
					new ReadFile(
							(AssignmentTarget)rewrittenTarget.getExpression(), 
							rewrittenPath.getExpression(), 
							mode), 
					rewrittenTarget.getAssignments(),
					rewrittenPath.getAssignments());
		} else {
			return this;
		}
	}

	private List<String> readFile(String path) throws FileNotFoundException, IOException {
		List<String> lines = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	lines.add(line);
		    }
		}
		return lines;
	}
	
}
