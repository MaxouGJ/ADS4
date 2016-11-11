import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.SwingUtilities;

public class Canevas extends JFrame {
	
	private Dessin dessin;
	private String nom;
	
	public Canevas(String test){
		nom = test;		
		setTitle("Canevas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setPreferredSize(new Dimension(900, 600));
		setMinimumSize(new Dimension(900, 600));
		setMaximumSize(new Dimension(900, 600));
		newFile();		
		pack();
		setVisible(true);
	}

	public void newFile(){
		JPanel test = new JPanel();
		test.setLayout(new GridBagLayout());
		JTextArea text = new JTextArea(load());
		JPanel text2 = new JPanel();
		text2.setLayout(new FlowLayout());
		JScrollPane scroll = new JScrollPane(text);		
		scroll.setPreferredSize(new Dimension(300,500)); // Dimensionnement en pixels
		JButton run = new JButton("run");
		run.addActionListener(new ActionListener() {
       		public void actionPerformed(ActionEvent e){
				try{
					save(text);
					newRun();
				}catch (Exception ex){
					System.out.println("Error can't launched run" +ex);
				}
	        }
       	});      
		text2.add(scroll);
		text2.add(run);
		GridBagConstraints gbc = new GridBagConstraints();
	        gbc.fill = GridBagConstraints.BOTH;    //Elle prendra toute la place disponible
        	gbc.gridx = 0; //Elle se situera à gauche
        	gbc.gridy = 0; // Et en haut
        	gbc.gridwidth = 1; //Et sa taille sera unitaire
        	gbc.gridheight = 1;
            	
		
		text2.setPreferredSize(new Dimension(300,600));
		text2.setMinimumSize(new Dimension(300,600));
		text2.setMaximumSize(new Dimension(300,600));
		dessin = new Dessin(getHeight());
		dessin.setPreferredSize(new Dimension(600,600));
		dessin.setMinimumSize(new Dimension(600,600));
		dessin.setMaximumSize(new Dimension(600,600));
		
		test.add(text2);				
		test.add(text2, gbc);
        	gbc.gridx = 1; //Il sera à droite de la console
        	gbc.gridwidth = GridBagConstraints.REMAINDER;  //Il occupera toout ce qui se situe après la console
        	gbc.gridheight = GridBagConstraints.REMAINDER;
        	gbc.weightx = 2.0; //Sa largeur sera de deux fois celle de la console
        	gbc.weighty = 1.0; //Sa hauteur sera la même
		test.add(dessin, gbc);
		
		test.add(dessin);
		setContentPane(test);
		this.repaint();
		this.revalidate();

	}

	public void newRun() throws Exception {
		File input = new File(nom);
		Reader reader = new FileReader(input);
    	Lexer lexer = new Lexer(reader);
    	LookAhead1 look = new LookAhead1(lexer);
    	Parser parser = new Parser(look, nom);
   		Canevas c = this;
    	SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				try {
					Program p = parser.programme();
					System.out.println("Réalisation du dessin");
					p.run(new ValueEnvironment());
					dispose();
				}
				catch (Exception e){
					if(!(e instanceof ParserException))
						dispose();
					System.out.println("The expression is not correct.");
					System.out.println(e);
					messageErreur(c, "The expression is not correct.\n"+e);
				}
			}
		});
	}

	public String load() {
		String str = nom;
        String filePath = str; 
		String tot = "";
           	try {
              	BufferedReader buff = new BufferedReader(new FileReader(filePath));
               	try {
					String line = "";
               		while ((line = buff.readLine()) != null) { 
						if(line != null){					
							tot = tot + line +"\n";	
						}
					}			
				}catch( Exception e){
					System.out.print(e);
				}
	    	}catch( Exception e){
				System.out.print(e);
		}
		return tot;
	}       	
	public void save(JTextArea jt) throws Exception{
		File ff = new File(nom); // définit le fichier de sauvegarde du niveau
        ff.createNewFile(); // creer le fichier de sauvegarde
        FileWriter fW = new FileWriter(ff); // Permet d'ecrire dans le fichier
		fW.write(jt.getText());
		fW.close();
	}
		
	public void add(Pinceau p){
		p = new Pinceau(p); //Pour nouvelle allocation mémoire
		dessin.add(p);
	}

	public void setBackgroundColor(Color c){
		dessin.setBackground(c);
	}	
	
	public void messageErreur(Canevas canevas, String e){
		JOptionPane.showMessageDialog(canevas, e,"Error !",JOptionPane.WARNING_MESSAGE);
	}
}
