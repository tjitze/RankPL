package com.tr.rp.ast.statements;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.AssignmentTarget;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLMiscException;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankTransformIterator;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.varstore.PersistentList;
import com.tr.rp.varstore.VarStore;

public class ReadFile extends AbstractStatement {

	// TODO: add CSV mode
	
	public enum InputMethod {
		NEWLINE_SEPARATED
	}
	
	private final AssignmentTarget target;
	private final AbstractExpression path;
	private final InputMethod mode;
	
	public ReadFile(AssignmentTarget target, AbstractExpression path, InputMethod mode) {
		this.target = target;
		this.path = path;
		this.mode = mode;
	}
	
	@Override
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		RankTransformIterator rt = new RankTransformIterator(in, this, target, path);
		final AssignmentTarget target = (AssignmentTarget)rt.getExpression(0);
		final AssignmentTarget path = (AssignmentTarget)rt.getExpression(1);
		return new RankedIterator<VarStore>() {

			private String lastPath;
			private PersistentList lines;
			
			@Override
			public boolean next() throws RPLException {
				return in.next();
			}

			@Override
			public VarStore getItem() throws RPLException {
				VarStore v = in.getItem();
				if (v == null) {
					return null;
				}
				String currentPath = path.getStringValue(in.getItem());
				if (lines == null || !lastPath.equals(currentPath)) {
					try {
						lastPath = currentPath;
						lines = new PersistentList(readFile(path.getStringValue(in.getItem())).toArray());
					} catch (IOException e) {
						throw new RPLMiscException(e.toString());
					}
				}
				return target.assign(in.getItem(), lines);
			}

			@Override
			public int getRank() {
				return in.getRank();
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
		return new ReadFile((AssignmentTarget)target.replaceVariable(a, b), (AbstractExpression)path.replaceVariable(a, b), mode);
	}

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
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

	@Override
	public void getAssignedVariables(Set<String> variables) {
		variables.add(target.getAssignedVariable());
	}	

}
