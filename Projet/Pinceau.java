import java.awt.Color;

class Pinceau {
	private int abs, ord, taille, angle;
	private boolean ecrit;
	private Color color;
	
	public Pinceau(){
		abs = 0;
		ord = 0;
		angle = 90;
		ecrit = false;
		taille = 1;
		color = Color.black;
	} 
	
	public Pinceau(Pinceau p){
		abs = p.abs;
		ord = p.ord;
		angle = p.angle;
		ecrit = p.ecrit;
		taille = p.taille;
		color = p.color;
	}
	
	public String toString(){
		return "("+abs+", "+ord+") "+angle+" "+ecrit+" "+taille ;
	}
	
	public int getX(){
		return abs;
	}
	
	public int getY(){
		return ord;
	}
	
	public int getAngle(){
		return angle;
	}
	
	public boolean getEcrit(){
		return ecrit;
	}
	
	public int getTaille(){
		return taille;
	}
	
	public Color getColor(){
		return color;
	}
	
	public void setX(int x){
		abs = x;
	}
	
	public void setY(int y){
		ord = y;
	}
	
	public void setAngle(int a){
		angle = a;
	}
	
	public void setEcrit(boolean b){
		ecrit = b;
	}

	public void setTaille(int t){
		taille = t;
	}	
	
	public void setColor(Color c){
		color = c;
	}	
}
