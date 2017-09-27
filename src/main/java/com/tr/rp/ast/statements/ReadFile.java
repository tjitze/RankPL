package com.tr.rp.ast.statements;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.AssignmentTarget;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.base.ExecutionContext;
import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLMiscException;
import com.tr.rp.executors.Executor;
import com.tr.rp.executors.RankTransformer;
import com.tr.rp.varstore.arrays.ArrayFactory;
import com.tr.rp.varstore.arrays.PersistentArray;
import com.tr.rp.varstore.arrays.PersistentObjectArray;
import com.tr.rp.varstore.types.Type;

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
	public Executor getExecutor(Executor out, ExecutionContext c) {
		RankTransformer<AssignmentTarget> transformTarget = RankTransformer.create(target);
		RankTransformer<AbstractExpression> transformPath = RankTransformer.create(path);
		Executor exec = new Executor() {

			private String lastPath;
			private PersistentArray lines;

			@Override
			public void close() throws RPLException {
				out.close();
			}

			@Override
			public void push(State s) throws RPLException {
				String currentPath = path.getValue(s.getVarStore(), Type.STRING);
				if (lines == null || !lastPath.equals(currentPath)) {
					try {
						lastPath = currentPath;
						lines = ArrayFactory.newArray(readFile(path.getValue(s.getVarStore(), Type.STRING)).toArray());
					} catch (IOException e) {
						throw new RPLMiscException(e.toString(), ReadFile.this);
					}
				}
				out.push(target.assign(s.getVarStore(), lines), s.getRank());
			}
		};
		transformTarget.setOutput(transformPath, this);
		transformPath.setOutput(exec, this);
		return transformTarget;
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
		target.getAssignedVariables(variables);
	}

	@Override
	public boolean equals(Object o) {
		return (o instanceof ReadFile) &&
					((ReadFile)o).target.equals(target) &&
					((ReadFile)o).path.equals(path) &&
					((ReadFile)o).mode.equals(mode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(getClass(), target, path, mode);
	}
}
