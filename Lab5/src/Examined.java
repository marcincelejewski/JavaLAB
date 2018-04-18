import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Examined {
	
	private int genotype;
	private String clas;
	
	public Examined() {
		
	}
	
	public Examined(int genotype, String clas) {
		this.genotype = genotype;
		this.clas = clas;
	}
	public int getGenotype() {
		return genotype;
	}
	public void setGenotype(int genotype) {
		this.genotype = genotype;
	}
	
	
	public String getClas() {
		return clas;
	}
	public void setClas(String clas) {
		this.clas = clas;
	}	
}
