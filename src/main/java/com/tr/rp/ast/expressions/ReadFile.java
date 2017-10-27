package com.tr.rp.ast.expressions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLMiscException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.arrays.ArrayFactory;
import com.tr.rp.varstore.arrays.PersistentArray;
import com.tr.rp.varstore.types.Type;

public class ReadFile extends AbstractExpression {

	private final AbstractExpression pathExpr;
	private String lastPath;
	private PersistentArray lines;

	public ReadFile(AbstractExpression pathExpr) {
		this.pathExpr = pathExpr;
	}

	@Override
	public void getVariables(Set<String> list) {
		pathExpr.getVariables(list);
	}

	@Override
	public boolean hasRankExpression() {
		return pathExpr.hasRankExpression();
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		AbstractExpression e = new ReadFile(pathExpr.transformRankExpressions(v, rank));
		e.setLineNumber(getLineNumber());
		return e;
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		return pathExpr.getEmbeddedFunctionCall();
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		AbstractExpression e = new ReadFile((AbstractExpression) pathExpr.replaceEmbeddedFunctionCall(fc, var));
		e.setLineNumber(getLineNumber());
		return e;
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		String currentPath = pathExpr.getValue(e, Type.STRING);
		if (lines == null || !lastPath.equals(currentPath)) {
			try {
				lastPath = currentPath;
				lines = ArrayFactory.newArray(readFile(lastPath).toArray());
			} catch (IOException ex) {
				throw new RPLMiscException(ex.toString(), ReadFile.this);
			}
		}
		return lines;
	}

	@Override
	public boolean hasDefiniteValue() {
		return pathExpr.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		String currentPath = pathExpr.getDefiniteValue(Type.STRING);
		if (lines == null || !lastPath.equals(currentPath)) {
			try {
				lastPath = currentPath;
				lines = ArrayFactory.newArray(readFile(lastPath).toArray());
			} catch (IOException ex) {
				throw new RPLMiscException(ex.toString(), ReadFile.this);
			}
		}
		return lines;
	}

	public String toString() {
		return "readFile(" + pathExpr + ")";
	}

	public boolean equals(Object o) {
		return (o instanceof ReadFile) && ((ReadFile) o).pathExpr.equals(pathExpr);
	}

	@Override
	public int hashCode() {
		return Objects.hash(getClass(), pathExpr);
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
