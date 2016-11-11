import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Dessin extends JPanel {
	private int height, taille;
	private ArrayList<Pinceau> pinceaux;
	
	public Dessin(int y){
		height = y;
		pinceaux = new ArrayList<Pinceau>();
	}
		
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(int i = 0; i < pinceaux.size()-1; i+=2){
			Pinceau previous = pinceaux.get(i);
			Pinceau current = pinceaux.get(i+1);
			Graphics2D g2 = (Graphics2D) g; 
			g2.setStroke(new BasicStroke(current.getTaille()));
			g2.setColor(current.getColor());
			g2.drawLine(previous.getX(), (height-previous.getY()), current.getX(), (height-current.getY()));
		}
	}
	
	public void add(Pinceau p){
		pinceaux.add(p);
	}
}
