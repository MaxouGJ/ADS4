
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Automate extends HashSet<Etat> {

    private Etat init;

    public Automate(Etat e) {
        super();
        this.init = e; 
        this.ajouteEtatRecursif(e);
    }
    
    @Override
    public String toString() {
        String s = "" + this.size() + " etats\n\n";
        for (Etat e : this) {
            s += e.toString() + "\n";
        }
        return s;
    }

    //exo 3
    boolean ajouteEtatSeul(Etat e) {
        return this.add(e);
    }
    
    //exo 4
    boolean ajouteEtatRecursif(Etat e) {
        if (!ajouteEtatSeul(e)) {
            return false;
        }
        for (char c : e.alphabet()) {
            ajouteEtatRecursif(e.succ(c));
		}
        return true;
    }

    //exo 6 (plus partie etat)
    boolean accepte(String s) {
        // c'est à toi!
        if(init == null)
    		return false;
    	else 
    		return init.accepte(s);
    }
    
    //exo 7 (plus partie etat)
    boolean facteur(String s) {
        // c'est à toi!
        if(init == null)
    		return false;
    	else 
    		return init.accepteFacteur(s);
    }
    
    //exo 8
    int[] motif(String s){
        // c'est à toi!
        for(int i=0; i < s.length(); i++){
        	for(int j=i; j < s.length(); j++){
        		if(this.accepte(s.substring(i, j)) && (j==s.length()-1 || !this.accepte(s.substring(i, j+1)))){
        			int[] t = new int[2];
        			t[0] = i;
        			t[1] = j;
        			return t;
        		}
        	}        
        }
        return null;
    }
    
    //exo 9
    String replace(String a_remplacer, String remplacant){
        // c'est à toi!
        int[] t = motif(a_remplacer);
        if(t == null)
        	return a_remplacer;
        String motif = a_remplacer.substring(t[0], t[1]);
        String remplacement = "";
        for(int i = 0; i < remplacant.length(); i++){
        	if(remplacant.charAt(i) == '&')
        		remplacement +=  motif;
        	else if(remplacant.charAt(i) == '\\'){
        		remplacement += "&";
        		i++;
        	}
        	else
        		remplacement += remplacant.charAt(i);
        }
        System.out.println(remplacement);
        
        String retour = "";
        retour += a_remplacer.substring(0, t[0]) + remplacement + a_remplacer.substring(t[1], a_remplacer.length());
        System.out.println(retour);
        return retour;
    }
    
    public static void main(String[] args){
    	//Premier automate
    	Etat e10 = new Etat(true, false, 0);
    	Etat e11 = new Etat(false, false, 1);
    	Etat e12 = new Etat(false, true, 2);
    	
    	e10.ajouteTransition('a', e11);
    	e11.ajouteTransition('a', e11);
    	e11.ajouteTransition('c', e11);
    	e11.ajouteTransition('b', e12);
    	
    	Automate un = new Automate(e10);
    	un.ajouteEtatSeul(e11);
    	un.ajouteEtatSeul(e12);
    	
    	System.out.println(un);
    	
    	//Deuxieme automate
    	Etat e20 = new Etat(true, false, 0);
    	Etat e21 = new Etat(false, true, 1);
    	Etat e22 = new Etat(false, true, 2);
    	
    	e20.ajouteTransition('a', e21);
    	e20.ajouteTransition('b', e22);
    	e21.ajouteTransition('a', e22);
    	e21.ajouteTransition('b', e20);
    	e22.ajouteTransition('a', e20);
    	e22.ajouteTransition('b', e21);
    	
	  	Automate deux = new Automate(e20);
	  	deux.ajouteEtatRecursif(e21);
	  	deux.ajouteEtatRecursif(e22);
	  	
	  	System.out.println(deux);
	  	
	  	//Test de accepte
	  	System.out.println(un.accepte("acccb"));
	  	System.out.println(un.accepte("b")+"\n");
	  	
	  	//Test de facteur
	  	System.out.println(un.facteur("a"));
	  	System.out.println(deux.facteur("ab")+"\n");
	  	
	  	//Test de motif
	  	int[]t = un.motif("cacbaacb");
	  	System.out.println(t[0]);
	  	System.out.println(t[1]);
    }
}
