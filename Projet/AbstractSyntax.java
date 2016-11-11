import java.util.*;
import java.awt.Color;

abstract class Expression {
    abstract int eval(ValueEnvironment env) throws Exception;
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
		try{
			int i = left.eval(env)/right.eval(env); 
		}
		catch(ArithmeticException e){
			System.out.println("You try a division by 0");
		}
		return left.eval(env)/right.eval(env);
		
	}
}


class Program {
	private Declaration declaration;
	private Instruction instruction;
	protected Pinceau pinceau;
	protected Canevas canevas;
	
	public Program(Declaration d, Instruction i, String test){
		declaration = d;
		instruction = i;
		pinceau = new Pinceau();
		canevas = new Canevas(test);
	}
	
	public void run(ValueEnvironment env) throws Exception {
		while(declaration != null){
			declaration.exec(env);
			declaration = declaration.next;
		}
		while(instruction != null){
			try{
				instruction.exec(this, env);
			}catch(Exception e){
				System.out.println(e);
				canevas.messageErreur(canevas, e.toString());
			}
			instruction = instruction.next;
		}
	}
}

class Declaration {
	private String varName;
	protected Declaration next;
	protected int line;
	public Declaration(String s, Declaration d, int l) {
		varName = s;
		next = d;
		line = l;
	}
	
	public void setNext(Declaration d){
		next = d;
	}
	
	public void exec(ValueEnvironment env) throws Exception {
		env.addVariable(varName);
	} 
}

abstract class Instruction {
	protected Instruction next;
	protected int line;
	
	public void setNext(Instruction i){
		next = i;
	}
	
	abstract void exec(Program p, ValueEnvironment env) throws Exception;
}
class Assignment extends Instruction {
	private String varName;
	private Expression exp;
	public Assignment(String s, Expression e, Instruction i, int l) {
		varName = s;
		exp = e;
		next = i;
		line = l;
	}
	public void exec(Program p, ValueEnvironment env) throws Exception {
		env.setVariable(varName,exp.eval(env));
	}
	
	public String toString(){
		return "Assignement";
	}
}
class Avance extends Instruction {
	private Expression expression;
		
	public Avance(Expression e, Instruction i, int l){
		expression = e;
		next = i;
		line = l;
	}
	
	public void exec(Program p, ValueEnvironment env) throws Exception {
		int distance = expression.eval(env);
		if(p.pinceau.getEcrit()){
			p.canevas.add(p.pinceau);
		}
		double angle = Math.PI * p.pinceau.getAngle() / 180;
		int x = (int) (Math.cos(angle)*distance);
		int y = (int) (Math.sin(angle)*distance);
		if(p.pinceau.getX()+x < 0 || p.pinceau.getX()+x > 600 || p.pinceau.getY()+y < 0 || p.pinceau.getY()+y > 600){
			throw new Exception("Out of canevas ("+(p.pinceau.getX()+x)+","+(p.pinceau.getY()+y)+") line "+line);
		}
		p.pinceau.setX(p.pinceau.getX()+x);
		p.pinceau.setY(p.pinceau.getY()+y);
		if(p.pinceau.getEcrit()){
			p.canevas.add(p.pinceau);
		}
	}
	
	public String toString(){
		return "Avance";
	}
}
class Tourne extends Instruction {
	private Expression expression;
	
	public Tourne(Expression e, Instruction i, int l){
		expression = e;
		next = i;
		line = l;
	}
	
	public void exec(Program p, ValueEnvironment env) throws Exception {
		int angle = expression.eval(env);
		while(Math.abs(angle) > 360)
			angle /= 360;
		p.pinceau.setAngle(p.pinceau.getAngle()+angle);		
	}
	
	public String toString(){
		return "Tourne";
	}
}
class BasPinceau extends Instruction {
	
	public BasPinceau(Instruction i, int l){
		next = i;
		line = l;
	}
	
	public void exec(Program p, ValueEnvironment env) throws Exception {
		p.pinceau.setEcrit(true);	
	}
	
	public String toString(){
		return "Bas";
	}
}
class HautPinceau extends Instruction {
	
	public HautPinceau(Instruction i, int l){
		next = i;
		line = l;
	}
	
	public void exec(Program p, ValueEnvironment env) throws Exception {
		p.pinceau.setEcrit(false);
	}
	
	public String toString(){
		return "Haut";
	}
}

class Condition extends Instruction {
	private Expression exp;
	private Instruction si, sinon;
	
	public Condition(Expression e, Instruction s, Instruction sin, Instruction n, int l){
		exp = e;
		si = s;
		sinon = sin;
		next = n;
		line = l;
	}
	
	public void exec(Program p, ValueEnvironment env) throws Exception{
		int n = exp.eval(env);
		if(n > 0){
			while(si != null){
				si.exec(p, env);
				si = si.next;
			}
		}
		else if(n == 0) {
			while(sinon != null){
				sinon.exec(p, env);
				sinon = sinon.next;
			}
		}
		else {
			throw new Exception("Bad expression for the condition line "+line);
		}
	}
	
	public String toString(){
		return "Condition";
	}
}
 
class Loop extends Instruction {
	private Expression exp;
	private Instruction instruction;
	
	public Loop(Expression e, Instruction i, Instruction n, int l) {
		exp = e;
		instruction = i;
		next = n;
		line = l;
	}
	
	public void exec(Program p, ValueEnvironment env) throws Exception {
		int c = exp.eval(env);
		if(c <= 0){
			throw new Exception("Bad expression for the loop line "+line);
		}
		else{
			for(int i=0;i<c;i++){
				Instruction instr = instruction;
				while(instr != null){
					instr.exec(p, env);
					instr = instr.next;
				}
			}
		}
	}
	
	public String toString(){
		return "Loop";
	}
}

class TailleTrait extends Instruction {
	private Expression expression;
	
	public TailleTrait(Expression e, Instruction n, int l){
		expression = e;
		next = n;
		line = l;
	}
	
	public void exec(Program p, ValueEnvironment env) throws Exception{
		p.pinceau.setTaille(expression.eval(env));
	}
	
	public String toString(){
		return "Taille trait";
	}
}

class CouleurTrait extends Instruction {
	private Expression rouge, vert, bleu;
	
	public CouleurTrait(Expression r, Expression v, Expression b, Instruction n, int l) {
		rouge = r;
		vert = v;
		bleu = b;
		next = n;
		line = l;
	}
	
	public void exec(Program p, ValueEnvironment env) throws Exception{
		int r = rouge.eval(env);
		int v = vert.eval(env);
		int b = bleu.eval(env);
		if(r < 0 || r > 255){
			throw new Exception("Indice de couleur rouge non acceptable : "+r+" line "+line);
		}
		if(v < 0 || v > 255){
			throw new Exception("Indice de couleur vert non acceptable : "+v+" line "+line);
		}
		if(b < 0 || b > 255){
			throw new Exception("Indice de couleur bleu non acceptable : "+b+" line "+line);
		}
		p.pinceau.setColor(new Color(r, v, b));
	}
	
	public String toString(){
		return "Couleur Trait";
	}
}

class Fond extends Instruction {
	private Expression rouge, vert, bleu;
	
	public Fond(Expression r, Expression v, Expression b, Instruction n, int l) {
		rouge = r;
		vert = v;
		bleu = b;
		next = n;
		line = l;
	}
	
	public void exec(Program p, ValueEnvironment env) throws Exception{
		int r = rouge.eval(env);
		int v = vert.eval(env);
		int b = bleu.eval(env);
		if(r < 0 || r > 255){
			throw new Exception("Indice de couleur rouge non acceptable : "+r+" line "+line);
		}
		if(v < 0 || v > 255){
			throw new Exception("Indice de couleur vert non acceptable : "+v+" line "+line);
		}
		if(b < 0 || b > 255){
			throw new Exception("Indice de couleur bleu non acceptable : "+b+" line "+line);
		}
		p.canevas.setBackgroundColor(new Color(r, v, b));
	}
	
	public String toString(){
		return "Fond";
	}
}

class Top extends Instruction {
	
	public Top(Instruction i, int l){
		next = i;
		line = l;
	}
	
	public void exec(Program p, ValueEnvironment env){
		if(p.pinceau.getEcrit()){
			p.canevas.add(p.pinceau);
		}
		p.pinceau.setY(600);
		if(p.pinceau.getEcrit()){
			p.canevas.add(p.pinceau);
		}
	}
	
	public String toString() {
		return "Haut";
	}
}

class Bottom extends Instruction {
	
	public Bottom(Instruction i, int l){
		next = i;
		line = l;
	}
	
	public void exec(Program p, ValueEnvironment env){
		if(p.pinceau.getEcrit()){
			p.canevas.add(p.pinceau);
		}
		p.pinceau.setY(0);
		if(p.pinceau.getEcrit()){
			p.canevas.add(p.pinceau);
		}
	}
	
	public String toString() {
		return "Bas";
	}
}

class Right extends Instruction {
	
	public Right(Instruction i, int l){
		next = i;
		line = l;
	}
	
	public void exec(Program p, ValueEnvironment env){
		if(p.pinceau.getEcrit()){
			p.canevas.add(p.pinceau);
		}
		p.pinceau.setX(600);
		if(p.pinceau.getEcrit()){
			p.canevas.add(p.pinceau);
		}
	}
	
	public String toString() {
		return "Droite";
	}
}

class Left extends Instruction {
	
	public Left(Instruction i, int l){
		next = i;
		line = l;
	}
	
	public void exec(Program p, ValueEnvironment env){
		if(p.pinceau.getEcrit()){
			p.canevas.add(p.pinceau);
		}
		p.pinceau.setX(0);
		if(p.pinceau.getEcrit()){
			p.canevas.add(p.pinceau);
		}
	}
	
	public String toString() {
		return "Gauche";
	}
}

class Middle extends Instruction {
	
	public Middle(Instruction i, int l){
		next = i;
		line = l;
	}
	
	public void exec(Program p, ValueEnvironment env){
		if(p.pinceau.getEcrit()){
			p.canevas.add(p.pinceau);
		}
		p.pinceau.setX(300);
		p.pinceau.setY(300);
		if(p.pinceau.getEcrit()){
			p.canevas.add(p.pinceau);
		}
	}
	
	public String toString() {
		return "Milieu";
	}
}

class ValueEnvironment extends HashMap<String, Integer> {
	public ValueEnvironment() {
		super();
	}
	public void addVariable(String name) throws Exception {
		if(containsKey(name)){
			throw new Exception("Variable already defined");
		}
		else{
			super.put(name,null);
		}
	}
	public void setVariable(String name, int value)	throws Exception {
		if(containsKey(name)){
			super.put(name,new Integer(value));
		}
		else {
			throw new Exception("Variable undifined");
		}
	}
	public int getValue(String name) throws Exception {
		if(containsKey(name)){
			return get(name);
		}
		else{
			throw new Exception("Invalid identificateur");
		}
	}	
}
