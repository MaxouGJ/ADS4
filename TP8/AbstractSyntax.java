import java.util.*;

abstract class Expression {
    abstract int eval();   // La fonction récursive qui calcule l'expression
}
class Int extends Expression {
	private int value;
	public Int (int i) {
		value = i;
	}
	public int eval(){
		return value;
	}
}
class Sum extends Expression {
	private Expression left, right;
	public Sum(Expression l, Expression r) {
		left = l;
		right = r;
	}
	public int eval(){
		return left.eval()+right.eval();
	}
}
class Difference extends Expression {
	private Expression left, right;
	public Difference(Expression l, Expression r) {
		left = l;
		right = r;
	}
	public int eval(){
		return left.eval()-right.eval();
	}
}
class Product extends Expression {
	private Expression left, right;
	public Product(Expression l, Expression r) {
		left = l;
		right = r;
	}
	public int eval(){
		return left.eval()*right.eval();
	}
}
class Division extends Expression {
	private Expression left, right;
	public Division(Expression l, Expression r) {
		left = l;
		right = r;
	}
	public int eval(){

		return left.eval()/right.eval();
	}
}
