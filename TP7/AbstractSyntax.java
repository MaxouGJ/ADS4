import java.util.*;

class Arbre {
	private String name;
	private List<Attribute> attributes;
	private List<Arbre> elements;
	
	public Arbre(String name, List<Attribute> attributes, List<Arbre> elements){
		this.name = name ;
		this.attributes = attributes ;
		this.elements = elements ;
	}
	
	public String afficheAttr(){
		for(int i=0; i<attributes.size(); i++){
			if(attributes.get(i).getValue().equals("restaurant")){
				return attributes.get(i).toString();
			}
			//return attributes.get(i).toString();
		}
		return "";
	}
	
	public void afficheElem(){
		int a=0;
		for(int i=0; i<elements.size(); i++){
			System.out.print(elements.get(i).afficheAttr());
			elements.get(i).afficheElem();
		}
		//System.out.println(a);
	}
}

class Attribute {
	private String key;
	private String value;
	
	public Attribute(String key, String value){
		this.key=key;
		this.value=value;
	}
	
	public String toString(){
		return "key = "+key+" value : "+value;
		
	}
	
	public String getValue(){return value;}
}

