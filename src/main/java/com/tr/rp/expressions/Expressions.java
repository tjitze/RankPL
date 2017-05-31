package com.tr.rp.expressions;

import java.util.function.BiFunction;

import com.tr.rp.core.Expression;

public class Expressions {

	public static Equals eq(Expression a, Expression b) {
		return new Equals(a, b);
	}
	
//	public static Equals equals(String a, Expression b) {
//		return equals(new Var(a), b);
//	}
//
//	public static Equals equals(String a, int b) {
//		return equals(new Var(a), new Literal<Integer>(b));
//	}
//
//	public static Equals equals(Expression a, String b) {
//		return equals(a, new Var(b));
//	}

//	public static Equals equals(String a, String b) {
//		return equals(new Var(a), new Var(b));
//	}

	public static NotEquals notEquals(Expression a, Expression b) {
		return new NotEquals(a, b);
	}

//	public static NotEquals notEquals(String a, Expression b) {
//		return notEquals(new Var(a), b);
//	}
//
//	public static NotEquals notEquals(Expression a, String b) {
//		return notEquals(a, new Var(b));
//	}
//
//	public static NotEquals notEquals(String a, String b) {
//		return notEquals(new Var(a), new Var(b));
//	}

	private static BiFunction<Integer, Integer, Boolean> lt = new BiFunction<Integer, Integer, Boolean>() {
		@Override
		public Boolean apply(Integer t, Integer u) {
			return t < u;
		}
		public String toString() {
			return "$1 < $2";
		}
	};	
	public static NumNumBoolOp lt(Expression a, Expression b) {
		return new NumNumBoolOp(lt, a, b);
	}
	
//	public static NumNumBoolOp lt(String a, Expression b) {
//		return lt(new Var(a), b);
//	}

//	public static NumNumBoolOp lt(Expression a, String b) {
//		return lt(a, new Var(b));
//	}

//	public static NumNumBoolOp lt(String a, String b) {
//		return lt(new Var(a), new Var(b));
//	}

	private static BiFunction<Integer, Integer, Boolean> gt = new BiFunction<Integer, Integer, Boolean>() {
		@Override
		public Boolean apply(Integer t, Integer u) {
			return t > u;
		}
		public String toString() {
			return "$1 > $2";
		}
	};	
	
	public static NumNumBoolOp gt(Expression a, Expression b) {
		return new NumNumBoolOp(gt, a, b);
	}
	
//	public static NumNumBoolOp gt(String a, Expression b) {
//		return gt(new Var(a), b);
//	}

//	public static NumNumBoolOp gt(Expression a, String b) {
//		return gt(a, new Var(b));
//	}

//	public static NumNumBoolOp gt(String a, String b) {
//		return gt(new Var(a), new Var(b));
//	}

	private static BiFunction<Integer, Integer, Boolean> leq = new BiFunction<Integer, Integer, Boolean>() {
		@Override
		public Boolean apply(Integer t, Integer u) {
			return t <= u;
		}
		public String toString() {
			return "$1 <= $2";
		}
	};	
	public static NumNumBoolOp leq(Expression a, Expression b) {
		return new NumNumBoolOp(leq, a, b);
	}

//	public static NumNumBoolOp leq(String a, Expression b) {
//		return leq(new Var(a), b);
//	}

//	public static NumNumBoolOp leq(Expression a, String b) {
//		return leq(a, new Var(b));
//	}

//	public static NumNumBoolOp leq(String a, String b) {
//		return leq(new Var(a), new Var(b));
//	}

	private static BiFunction<Integer, Integer, Boolean> geq = new BiFunction<Integer, Integer, Boolean>() {
		@Override
		public Boolean apply(Integer t, Integer u) {
			return t >= u;
		}
		public String toString() {
			return "$1 >= $2";
		}
	};
	
	public static NumNumBoolOp geq(Expression a, Expression b) {
		return new NumNumBoolOp(geq, a, b);
	}

//	public static NumNumBoolOp geq(String a, Expression b) {
//		return geq(new Var(a), b);
//	}

//	public static NumNumBoolOp geq(Expression a, String b) {
//		return geq(a, new Var(b));
//	}

//	public static NumNumBoolOp geq(String a, String b) {
//		return geq(new Var(a), new Var(b));
//	}

	public static Plus plus(Expression a, Expression b) {
		return new Plus(a, b);
	}

//	public static NumNumNumOp plus(String a, Expression b) {
//		return plus(new Var(a), b);
//	}

//	public static NumNumNumOp plus(Expression a, String b) {
//		return plus(a, new Var(b));
//	}

//	public static NumNumNumOp plus(String a, String b) {
//		return plus(new Var(a), new Var(b));
//	}

	private static BiFunction<Integer, Integer, Integer> minus = new BiFunction<Integer, Integer, Integer>() {
		@Override
		public Integer apply(Integer t, Integer u) {
			return t - u;
		}
		public String toString() {
			return "$1 - $2";
		}
	};
	
	public static NumNumNumOp minus(Expression a, Expression b) {
		return new NumNumNumOp(minus, a, b);
	}

	public static Expression minus(Expression a) {
		if (a instanceof Literal && ((Literal)a).getLiteralValue() instanceof Integer) {
			return new Literal<Integer>(-(int)((Literal)a).getLiteralValue());
		} else {
			return new Negative(a);
		}
	}

//	public static NumNumNumOp minus(String a, Expression b) {
//		return minus(new Var(a), b);
//	}

//	public static NumNumNumOp minus(Expression a, String b) {
//		return minus(a, new Var(b));
//	}
	
//	public static NumNumNumOp minus(String a, String b) {
//		return minus(new Var(a), new Var(b));
//	}

	private static BiFunction<Integer, Integer, Integer> times = new BiFunction<Integer, Integer, Integer>() {
		@Override
		public Integer apply(Integer t, Integer u) {
			return t * u;
		}
		public String toString() {
			return "$1 * $2";
		}
	};
	
	public static NumNumNumOp times(Expression a, Expression b) {
		return new NumNumNumOp(times, a, b);
	}
	
//	public static NumNumNumOp times(String a, Expression b) {
//		return times(new Var(a), b);
//	}

//	public static NumNumNumOp times(Expression a, String b) {
//		return times(a, new Var(b));
//	}

//	public static NumNumNumOp times(String a, String b) {
//		return times(new Var(a), new Var(b));
//	}

	private static BiFunction<Integer, Integer, Integer> div = new BiFunction<Integer, Integer, Integer>() {
		@Override
		public Integer apply(Integer t, Integer u) {
			return t / u;
		}
		public String toString() {
			return "$1 / $2";
		}
	};
	
	public static NumNumNumOp div(Expression a, Expression b) {
		return new NumNumNumOp(div, a, b);
	}

//	public static NumNumNumOp div(String a, Expression b) {
//		return div(new Var(a), b);
//	}

//	public static NumNumNumOp div(Expression a, String b) {
//		return div(a, new Var(b));
//	}

//	public static NumNumNumOp div(String a, String b) {
//		return div(new Var(a), new Var(b));
//	}

	private static BiFunction<Integer, Integer, Integer> mod = new BiFunction<Integer, Integer, Integer>() {
		@Override
		public Integer apply(Integer t, Integer u) {
			return t % u;
		}
		public String toString() {
			return "$1 % $2";
		}
	};
	
	public static NumNumNumOp mod(Expression a, Expression b) {
		return new NumNumNumOp(mod, a, b);
	}

//	public static NumNumNumOp mod(String a, Expression b) {
//		return mod(new Var(a), b);
//	}

//	public static NumNumNumOp mod(Expression a, String b) {
//		return mod(a, new Var(b));
//	}

//	public static NumNumNumOp mod(String a, String b) {
//		return mod(new Var(a), new Var(b));
//	}

	private static BiFunction<Integer, Integer, Integer> max = new BiFunction<Integer, Integer, Integer>() {
		@Override
		public Integer apply(Integer t, Integer u) {
			return Integer.max(t, u);
		}
		public String toString() {
			return "max($1, $2)";
		}
	};
	
	public static NumNumNumOp max(Expression a, Expression b) {
		return new NumNumNumOp(max, a, b);
	}
	
//	public static NumNumNumOp max(String a, Expression b) {
//		return max(new Var(a), b);
//	}

//	public static NumNumNumOp max(Expression a, String b) {
//		return max(a, new Var(b));
//	}
	
//	public static NumNumNumOp max(String a, String b) {
//		return max(new Var(a), new Var(b));
//	}

	private static BiFunction<Integer, Integer, Integer> min = new BiFunction<Integer, Integer, Integer>() {
		@Override
		public Integer apply(Integer t, Integer u) {
			return Integer.min(t, u);
		}
		public String toString() {
			return "min($1, $2)";
		}
	};

	public static NumNumNumOp min(Expression a, Expression b) {
		return new NumNumNumOp(min, a, b);
	}
	
//	public static NumNumNumOp min(String a, Expression b) {
//		return min(new Var(a), b);
//	}

//	public static NumNumNumOp min(Expression a, String b) {
//		return min(a, new Var(b));
//	}

//	public static NumNumNumOp min(String a, String b) {
//		return min(new Var(a), new Var(b));
//	}

	private static BiFunction<Boolean, Boolean, Boolean> and = new BiFunction<Boolean, Boolean, Boolean>() {
		@Override
		public Boolean apply(Boolean t, Boolean u) {
			return t && u;
		}
		public String toString() {
			return "$1 & $2";
		}
	};
			
	public static BoolBoolBoolOp and(Expression a, Expression b) {
		return new BoolBoolBoolOp(and, a, b);
	}
	
//	public static BoolBoolBoolOp and(String a, String b) {
//		return new BoolBoolBoolOp(and, new Var(a), new Var(b));
//	}
	
	private static BiFunction<Boolean, Boolean, Boolean> or = new BiFunction<Boolean, Boolean, Boolean>() {
		@Override
		public Boolean apply(Boolean t, Boolean u) {
			return t || u;
		}
		public String toString() {
			return "$1 | $2";
		}
	};

	public static BoolBoolBoolOp or(Expression a, Expression b) {
		return new BoolBoolBoolOp(or, a, b);
	}

	private static BiFunction<Boolean, Boolean, Boolean> xor = new BiFunction<Boolean, Boolean, Boolean>() {
		@Override
		public Boolean apply(Boolean t, Boolean u) {
			return (t && !u) || (!t && u);
		}
		public String toString() {
			return "$1 ^ $2";
		}
	};

	public static BoolBoolBoolOp xor(Expression a, Expression b) {
		return new BoolBoolBoolOp(xor, a, b);
	}

//	public static BoolBoolBoolOp xor(String a, String b) {
//		return new BoolBoolBoolOp(xor, new Var(a), new Var(b));
//	}

	public static Len len(Expression e) {
		return new Len(e);
	}
	
	public static Abs abs(Expression e) {
		return new Abs(e);
	}
	
	public static ArrayInitExpression arrayInitX(Expression dimension, Expression initValue) {
		return new ArrayInitExpression(dimension, initValue);
	}
	
	public static Variable var(String name) {
		return new Variable(name);
	}
	
	public static Expression var(String name, Expression firstIndex, Expression ... otherIndices) {
		Expression[] indices = new Expression[otherIndices.length+1];
		indices[0] = firstIndex;
		for (int i = 0; i < otherIndices.length; i++) {
			indices[i+1] = otherIndices[i];
		}
		return new IndexElementExpression(new Variable(name), indices);
	}
	
	public static IndexElementExpression var(String name, int firstIndex, int ... otherIndices) {
		Expression[] indices = new Expression[otherIndices.length+1];
		indices[0] = lit(firstIndex);
		for (int i = 0; i < otherIndices.length; i++) {
			indices[i+1] = lit(otherIndices[i]);
		}
		return new IndexElementExpression(new Variable(name), indices);
	}

	public static AssignmentTarget target(String name) {
		return new AssignmentTarget(name);
	}
	
	public static AssignmentTarget target(String name, Expression firstIndex, Expression ... otherIndices) {
		Expression[] indices = new Expression[otherIndices.length+1];
		indices[0] = firstIndex;
		for (int i = 0; i < otherIndices.length; i++) {
			indices[i+1] = otherIndices[i];
		}
		return new AssignmentTarget(name, indices);
	}
	
	public static AssignmentTarget target(String name, int firstIndex, int ... otherIndices) {
		Expression[] indices = new Expression[otherIndices.length+1];
		indices[0] = lit(firstIndex);
		for (int i = 0; i < otherIndices.length; i++) {
			indices[i+1] = lit(otherIndices[i]);
		}
		return new AssignmentTarget(name, indices);
	}

	public static IndexElementExpression indexedExp(Expression exp, Expression firstIndex, Expression ... otherIndices) {
		Expression[] indices = new Expression[otherIndices.length+1];
		indices[0] = firstIndex;
		for (int i = 0; i < otherIndices.length; i++) {
			indices[i+1] = otherIndices[i];
		}
		return new IndexElementExpression(exp, indices);
	}
	
	public static IndexElementExpression indexedExp(Expression exp, int firstIndex, int ... otherIndices) {
		Expression[] indices = new Expression[otherIndices.length+1];
		indices[0] = lit(firstIndex);
		for (int i = 0; i < otherIndices.length; i++) {
			indices[i+1] = lit(otherIndices[i]);
		}
		return new IndexElementExpression(exp, indices);
	}

	public static <T> Literal<T> lit(T value) {
		return new Literal<T>(value);
	}
	
	public static Not not(Expression e) {
		return new Not(e);
	}

	public static RankExpr rank(Expression e) {
		return new RankExpr(e);
	}

	public static SubString subString(Expression s, Expression begin, Expression end) {
		return new SubString(s, begin, end);
	}
}
