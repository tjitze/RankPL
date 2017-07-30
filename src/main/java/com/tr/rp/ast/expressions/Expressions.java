package com.tr.rp.ast.expressions;

import java.util.function.BiFunction;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ranks.Rank;

public class Expressions {

	public static Equals eq(AbstractExpression a, AbstractExpression b) {
		return new Equals(a, b);
	}
	
	public static NotEquals notEquals(AbstractExpression a, AbstractExpression b) {
		return new NotEquals(a, b);
	}

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

	public static Plus plus(AbstractExpression a, AbstractExpression b) {
		return new Plus(a, b);
	}

	private static BiFunction<Integer, Integer, Integer> rankPlus = new BiFunction<Integer, Integer, Integer>() {
		@Override
		public Integer apply(Integer t, Integer u) {
			return Rank.add(t, u);
		}
		public String toString() {
			return "$1 - $2";
		}
	};

	public static NumNumNumOp rankPlus(AbstractExpression a, AbstractExpression b) {
		return new NumNumNumOp(rankPlus, a, b);
	}
	
	private static BiFunction<Integer, Integer, Integer> rankMinus = new BiFunction<Integer, Integer, Integer>() {
		@Override
		public Integer apply(Integer t, Integer u) {
			return Rank.sub(t, u);
		}
		public String toString() {
			return "$1 - $2";
		}
	};


	public static NumNumNumOp rankMinus(AbstractExpression a, AbstractExpression b) {
		return new NumNumNumOp(rankMinus, a, b);
	}


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

	public static Size len(AbstractExpression e) {
		return new Size(e);
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

	public static AssignmentTargetTerminal target(String name) {
		return new AssignmentTargetTerminal(name);
	}
		
	public static AssignmentTarget target(String name, AbstractExpression firstIndex, AbstractExpression ... otherIndices) {
		AbstractExpression[] indices = new AbstractExpression[otherIndices.length+1];
		indices[0] = firstIndex;
		for (int i = 0; i < otherIndices.length; i++) {
			indices[i+1] = otherIndices[i];
		}
		return new AssignmentTargetTerminal(name, indices);
	}
	
	public static AssignmentTarget target(String name, int firstIndex, int ... otherIndices) {
		AbstractExpression[] indices = new AbstractExpression[otherIndices.length+1];
		indices[0] = lit(firstIndex);
		for (int i = 0; i < otherIndices.length; i++) {
			indices[i+1] = lit(otherIndices[i]);
		}
		return new AssignmentTargetTerminal(name, indices);
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

	public static Size size(AbstractExpression e) {
		return new Size(e);
	}

}
