

import java.util.HashMap;
import java.util.Map;

import com.tr.rp.ast.Function;
import com.tr.rp.exceptions.RPLFunctionUndefinedException;

/**
 * Interface for objects that store a mapping of function names
 * to function objects.
 */
public class FunctionScope {

	private Map<String, Function> definedFunctions = new HashMap<String, Function>();
	
	public Function getFunction(String name) throws RPLFunctionUndefinedException {
		if (!definedFunctions.containsKey(name)) {
			throw new RPLFunctionUndefinedException(name);
		}
		return definedFunctions.get(name);
	}
	
	public void registerFunction(Function function) {
		definedFunctions.put(function.getName(), function);
	}

	public boolean hasFunction(String name) {
		return definedFunctions.containsKey(name);
	}
	
}
