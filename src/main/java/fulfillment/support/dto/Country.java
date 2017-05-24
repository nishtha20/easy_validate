package fulfillment.support.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;

public class Country {

	
	private String id;
	private List<String> url;

	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getUrl() {
		return url;
	}

	public void setUrl(List<String> url) {
		this.url = url;
	}

}
