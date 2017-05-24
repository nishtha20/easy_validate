package fulfillment.support.concurrent.retriever;

import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;

import fulfillment.support.VersionValidator;
import fulfillment.support.dto.InputDto;
import fulfillment.support.dto.VersionCheckResult;

public class VersionValidateRetriever implements Callable<Object>{

	private List <String> urls;
	private InputDto input;
	
	private VersionValidator versionValidator;

	public VersionValidateRetriever(List <String> urls, InputDto input) {
		super();
		this.urls = urls;
		this.input = input;
		versionValidator = new VersionValidator();
	}
	
	@Override
	public List<VersionCheckResult> call() throws Exception {
		List <VersionCheckResult> result = null;
		result = versionValidator.validateVersion(urls, input);
		return result;
	}

	public List <String> getUrls() {
		return urls;
	}

	public void setUrls(List <String> urls) {
		this.urls = urls;
	}

	public InputDto getInput() {
		return input;
	}

	public void setInput(InputDto input) {
		this.input = input;
	}
	
	
	
}
