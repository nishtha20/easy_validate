package fulfillment.support.concurrent.processor;

import java.util.ArrayList;
import java.util.List;

import com.walmart.ecommerce.fulfillment.commons.concurrent.processor.spi.IResponseProcessor;

import fulfillment.support.dto.VersionCheckResult;

public class VersionValidateResponseProcessor implements IResponseProcessor {

	@SuppressWarnings("unchecked")
	@Override
	public Object process(List<Object> responseObject) {
		List<VersionCheckResult> finalResult = new ArrayList<>();
		for (Object object : responseObject) {
			if (object instanceof List) {
				List<VersionCheckResult> result = (List<VersionCheckResult>) object;
				finalResult.addAll(result);
			}
		}
		return finalResult;
	}

}
