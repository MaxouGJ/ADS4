import java.util.HashMap;
import java.util.Set;

public class Etat {

    private HashMap<Character, Etat> transitions = new HashMap<Character, Etat>();
    private boolean init = false;
    private boolean term = false;
    private int id;

    public Etat(boolean init, boolean term) {
        this.init = init;
        this.term = term;

    }

    public Etat(int id) {
        this.id = id;
    }

    public Etat(boolean init, boolean term, int id) {
        this.init = init;
        this.term = term;
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isInit() {
        return init;
    }

    public boolean isTerm() {
        return term;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public void setTerm(boolean term) {
        this.term = term;
    }

    public Set<Character> alphabet() {
        return this.transitions.keySet();
    }

    public String toString() {
        String s = "Etat " + id;
        if (isInit()) {
            s += " initial";
        }
        if (isTerm()) {
            s += " terminal";
        }
        s += "\n";
        for (char c : this.alphabet()) {
            s += c + " " + this.transitions.get(c).id + "\n";
        }
        return s;
    }
    
    
    //exo 1
    //return null if c is not a key of transitions
    public Etat succ(char c) {
        //c'est à toi !
        Etat e = transitions.get(c); 
    	return e;
    }
    
    //exo 2
    //if c already a key of transitions, the old value is replaced by e
    public void ajouteTransition(char c, Etat e) {
    	//c'est à toi !
    	transitions.put(c,e);
    }

    
    //exo 6 (plus partie automate)
    boolean accepte(String mot) {
    	//c'est à toi
    	Etat e = this;
    	for(int i = 0; i < mot.length(); i++){
    		e = e.succ(mot.charAt(i));
    		if(e == null)
    			return false;
    	}
    	return true;
    	
    }
   
    //exo 7 (plus partie automate)
    boolean accepteFacteur(String mot) {
    	Etat e = this;
    	for(int i = 0; i < mot.length(); i++){
    		e = e.succ(mot.charAt(i));
    		if(e == null) 
    			return false;
			if(e.term)
				return true;
    	}
    	return false;
    }
}
