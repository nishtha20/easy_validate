package fulfillment.support;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import fulfillment.support.dto.InputDto;
import fulfillment.support.dto.VersionCheckResult;

//@Component
public class VersionValidator {

	public List<VersionCheckResult> validateVersion(List<String> urls, InputDto input) {

		List<VersionCheckResult> versionCheckResultList = new ArrayList<VersionCheckResult>();

		System.out.println(urls.toString());
		for (String u : urls) {
			VersionCheckResult result = new VersionCheckResult();
			result.setComponent(input.getComponent());
			result.setCountry(input.getCountry());
			result.setUrl(u);
			result.setExpectedVersion(input.getVersion());

			String html = "", strTemp = "";

			try {
				URL url = new URL(u);
				BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
				while (null != (strTemp = br.readLine())) {
					html = html + strTemp;
				}
			} catch (Exception e) {
				result.setVersionEquals("Error in accessing version.html");
				versionCheckResultList.add(result);
				continue;
			}
			Document doc = Jsoup.parse(html);
			Elements elements = doc.getElementsByTag("h3");
			for (Element element : elements) {
				String txt = element.text();
				if (txt.contains("Version")) {
					Elements version = element.getElementsByTag("i");
					result.setActualResult(version.text());
					if (version.text().equals(input.getVersion())) {
						result.setVersionEquals("New War successfully uploaded");
					} else {
						result.setVersionEquals("New War not deployed");
					}
					break;
				}
			}
			versionCheckResultList.add(result);
		}
		return versionCheckResultList;
	}

}
