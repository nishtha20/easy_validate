package fulfillment.support.concurrent;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.walmart.ecommerce.fulfillment.commons.concurrent.context.ParallelTaskExecutionContext;
import com.walmart.ecommerce.fulfillment.commons.concurrent.exception.InvalidTaskExecutionContext;
import com.walmart.ecommerce.fulfillment.commons.concurrent.processor.ParallelTaskProcessor;

import fulfillment.support.concurrent.processor.VersionValidateResponseProcessor;
import fulfillment.support.concurrent.retriever.VersionValidateRetriever;
import fulfillment.support.dto.InputDto;
import fulfillment.support.dto.VersionCheckResult;

@Component
public class VersionParallelProcessor {

	@Autowired
	private ParallelTaskProcessor parallelTaskProcessor;

	public List<VersionCheckResult> parallelTaskExecution(List<InputDto> inputList, Map<String, List<String>> urlMap)
			throws InterruptedException, InvalidTaskExecutionContext {
		List<VersionCheckResult> result = null;
		ParallelTaskExecutionContext executionContext = new ParallelTaskExecutionContext();
		executionContext.setResponseProcessor(new VersionValidateResponseProcessor());
		for (InputDto input : inputList) {
			List<String> urls = urlMap.get(input.getComponent() + input.getCountry());
			executionContext.addTask(new VersionValidateRetriever(urls, input));
		}
		Object response = parallelTaskProcessor.process(executionContext);
		if(response instanceof List) {
			result = (List<VersionCheckResult>) response;
		}
		return result;
	}
}
