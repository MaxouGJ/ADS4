import java.util.*;

abstract class Expression {
    abstract int eval(ValueEnvironment env)
    throws Exception;
}
class Int extends Expression {
	private int value;
	public Int(int i) {
		value = i;
	}
	
	public int eval(ValueEnvironment env) throws Exception{
		return value;
	}
}
class Var extends Expression {
	private String name;
	public Var(String s) {
		name = s;
	}
	public int eval(ValueEnvironment env) throws Exception{
		return env.getValue(name);
	}
}
class Sum extends Expression {
	private Expression left, right;
	public Sum(Expression l, Expression r) {
		left = l;
		right = r;
	}
	
	public int eval(ValueEnvironment env) throws Exception{
		return left.eval(env)+right.eval(env);
	}	
}
class Difference extends Expression {
	private Expression left, right;
	public Difference(Expression l, Expression r) {
		left = l;
		right = r;
	}
	
	public int eval(ValueEnvironment env) throws Exception{
		return  left.eval(env)-right.eval(env);
	}
}
class Product extends Expression {
	private Expression left, right;
	public Product(Expression l, Expression r) {
		left = l;
		right = r;
	}
	
	public int eval(ValueEnvironment env) throws Exception{
		return  left.eval(env)*right.eval(env);
	}
}
class Division extends Expression {
	private Expression left, right;
	public Division(Expression l, Expression r) {
		left = l;
		right = r;
	}
	
	public int eval(ValueEnvironment env) throws Exception{
		return left.eval(env)/right.eval(env);
	}
}

class Program {
	private Instruction first;
	private Program rest;
	public Program(Instruction i, Program p) {
		first = i;
		rest = p;
	}
	public void run(ValueEnvironment env)
	throws Exception {
		if (first != null) {
			first.exec(env);
			if(rest!=null){
				rest.run(env);
			}
		}
	} 
}

abstract class Instruction {
	abstract void exec(ValueEnvironment env)
	throws Exception;
}
class Declaration extends Instruction {
	private String varName;
	public Declaration(String s) {
		varName = s;
	}
	public void exec(ValueEnvironment env) 
	throws Exception {
		env.addVariable(varName);
	} 
}
class Assignment extends Instruction {
	private String varName;
	private Expression exp;
	public Assignment(String s, Expression e) {
		varName = s;
		exp = e;
	}
	public void exec(ValueEnvironment env)
	throws Exception {
		env.setVariable(varName,exp.eval(env));
	}
}
class Print extends Instruction {
	private Expression exp;
	public Print(Expression e) {
		exp = e;
	}
	public void exec(ValueEnvironment env) 
	throws Exception {
		System.out.println(exp.eval(env));
	}
}
class Loop extends Instruction {
	private Expression exp;
	private Program prog;
	public Loop(Expression e, Program p) {
		exp = e;
		prog = p;
	}
	public void exec(ValueEnvironment env)
	throws Exception {
		int c = exp.eval(env);
		for(int i=0;i<c;i++){
			prog.run(env);
		}
	}
}

class ValueEnvironment extends HashMap<String, Integer> {
	public ValueEnvironment() {
		super();
	}
	public void addVariable(String name) 
	throws Exception {
		super.put(name,null);
	}
	public void setVariable(String name, int value) 
	throws Exception {
			super.put(name,new Integer(value));
	}
	public int getValue(String name) 
	throws Exception {
		if(containsKey(name)){
			return get(name);
		}
		else{
			return 0;
		}
	}	
}
