package com.tr.rp.ast.expressions;

import java.util.function.BiFunction;

import com.tr.rp.ast.AbstractExpression;

public class Expressions {

	public static Equals eq(AbstractExpression a, AbstractExpression b) {
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

	public static NotEquals notEquals(AbstractExpression a, AbstractExpression b) {
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
	public static NumNumBoolOp lt(AbstractExpression a, AbstractExpression b) {
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
	
	public static NumNumBoolOp gt(AbstractExpression a, AbstractExpression b) {
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
	public static NumNumBoolOp leq(AbstractExpression a, AbstractExpression b) {
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
	
	public static NumNumBoolOp geq(AbstractExpression a, AbstractExpression b) {
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

	public static Plus plus(AbstractExpression a, AbstractExpression b) {
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
	
	public static NumNumNumOp minus(AbstractExpression a, AbstractExpression b) {
		return new NumNumNumOp(minus, a, b);
	}

	public static AbstractExpression minus(AbstractExpression a) {
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
	
	public static NumNumNumOp times(AbstractExpression a, AbstractExpression b) {
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
	
	public static NumNumNumOp div(AbstractExpression a, AbstractExpression b) {
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
	
	public static NumNumNumOp mod(AbstractExpression a, AbstractExpression b) {
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
	
	public static NumNumNumOp max(AbstractExpression a, AbstractExpression b) {
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

	public static NumNumNumOp min(AbstractExpression a, AbstractExpression b) {
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
			
	public static BoolBoolBoolOp and(AbstractExpression a, AbstractExpression b) {
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

	public static BoolBoolBoolOp or(AbstractExpression a, AbstractExpression b) {
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

	public static BoolBoolBoolOp xor(AbstractExpression a, AbstractExpression b) {
		return new BoolBoolBoolOp(xor, a, b);
	}

//	public static BoolBoolBoolOp xor(String a, String b) {
//		return new BoolBoolBoolOp(xor, new Var(a), new Var(b));
//	}

	public static Len len(AbstractExpression e) {
		return new Len(e);
	}
	
	public static Abs abs(AbstractExpression e) {
		return new Abs(e);
	}
	
	public static ArrayInitExpression arrayInitX(AbstractExpression dimension, AbstractExpression initValue) {
		return new ArrayInitExpression(dimension, initValue);
	}
	
	public static Variable var(String name) {
		return new Variable(name);
	}
	
	public static AbstractExpression var(String name, AbstractExpression firstIndex, AbstractExpression ... otherIndices) {
		AbstractExpression[] indices = new AbstractExpression[otherIndices.length+1];
		indices[0] = firstIndex;
		for (int i = 0; i < otherIndices.length; i++) {
			indices[i+1] = otherIndices[i];
		}
		return new IndexElementExpression(new Variable(name), indices);
	}
	
	public static IndexElementExpression var(String name, int firstIndex, int ... otherIndices) {
		AbstractExpression[] indices = new AbstractExpression[otherIndices.length+1];
		indices[0] = lit(firstIndex);
		for (int i = 0; i < otherIndices.length; i++) {
			indices[i+1] = lit(otherIndices[i]);
		}
		return new IndexElementExpression(new Variable(name), indices);
	}

	public static AssignmentTarget target(String name) {
		return new AssignmentTarget(name);
	}
	
	public static AssignmentTarget target(String name, AbstractExpression firstIndex, AbstractExpression ... otherIndices) {
		AbstractExpression[] indices = new AbstractExpression[otherIndices.length+1];
		indices[0] = firstIndex;
		for (int i = 0; i < otherIndices.length; i++) {
			indices[i+1] = otherIndices[i];
		}
		return new AssignmentTarget(name, indices);
	}
	
	public static AssignmentTarget target(String name, int firstIndex, int ... otherIndices) {
		AbstractExpression[] indices = new AbstractExpression[otherIndices.length+1];
		indices[0] = lit(firstIndex);
		for (int i = 0; i < otherIndices.length; i++) {
			indices[i+1] = lit(otherIndices[i]);
		}
		return new AssignmentTarget(name, indices);
	}

	public static IndexElementExpression indexedExp(AbstractExpression exp, AbstractExpression firstIndex, AbstractExpression ... otherIndices) {
		AbstractExpression[] indices = new AbstractExpression[otherIndices.length+1];
		indices[0] = firstIndex;
		for (int i = 0; i < otherIndices.length; i++) {
			indices[i+1] = otherIndices[i];
		}
		return new IndexElementExpression(exp, indices);
	}
	
	public static IndexElementExpression indexedExp(AbstractExpression exp, int firstIndex, int ... otherIndices) {
		AbstractExpression[] indices = new AbstractExpression[otherIndices.length+1];
		indices[0] = lit(firstIndex);
		for (int i = 0; i < otherIndices.length; i++) {
			indices[i+1] = lit(otherIndices[i]);
		}
		return new IndexElementExpression(exp, indices);
	}

	public static <T> Literal<T> lit(T value) {
		return new Literal<T>(value);
	}
	
	public static Not not(AbstractExpression e) {
		return new Not(e);
	}

	public static RankExpr rank(AbstractExpression e) {
		return new RankExpr(e);
	}

	public static SubString subString(AbstractExpression s, AbstractExpression begin, AbstractExpression end) {
		return new SubString(s, begin, end);
	}
	
	public static Min min(AbstractExpression ... es) {
		return new Min(es);
	}

	public static Max max(AbstractExpression ... es) {
		return new Max(es);
	}

	public static ParseInt parseInt(AbstractExpression e) {
		return new ParseInt(e);
	}

}
