package fulfillment.support.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;

public class Component {

	private String id;

	private List<Country> countries;

	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Country> getCountries() {
		return countries;
	}

	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}

}
