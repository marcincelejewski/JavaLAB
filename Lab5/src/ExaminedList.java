import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class ExaminedList {

	private List<Examined> list = new ArrayList<Examined>();

	@XmlElements(@XmlElement)
	public List<Examined> getList() {
		return list;
	}

	public void setList(ArrayList<Examined> list) {
		this.list = list;
	}
	
	public void add(Examined e) {
        this.list.add(e);
    }

	public void update(int genotype, String clas) {
		for(Examined e : list) {
			if(e.getGenotype()==genotype) {
				e.setClas(clas);
			}
		}
	}
}
