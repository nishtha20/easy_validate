package fulfillment.support.dto;

public class VersionCheckResult {

	String component;
	String country;
	String url;
	String expectedVersion;
	String ActualVersion;
	String versionEquals;

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getExpectedVersion() {
		return expectedVersion;
	}

	public void setExpectedVersion(String expectedVersion) {
		this.expectedVersion = expectedVersion;
	}

	public String getActualResult() {
		return ActualVersion;
	}

	public void setActualResult(String actualResult) {
		ActualVersion = actualResult;
	}

	public String getVersionEquals() {
		return versionEquals;
	}

	public void setVersionEquals(String versionEquals) {
		this.versionEquals = versionEquals;
	}

	@Override
	public String toString() {
		return "VersionCheckResult [component=" + component + ", country=" + country + ", url=" + url
				+ ", expectedVersion=" + expectedVersion + ", ActualVersion=" + ActualVersion + ", versionEquals="
				+ versionEquals + "]";
	}

}
