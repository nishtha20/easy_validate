package fulfillment.support;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.walmart.ecommerce.fulfillment.commons.concurrent.exception.InvalidTaskExecutionContext;

import fulfillment.support.concurrent.VersionParallelProcessor;
import fulfillment.support.dto.Component;
import fulfillment.support.dto.Country;
import fulfillment.support.dto.InputDto;
import fulfillment.support.dto.Version;
import fulfillment.support.dto.VersionCheckResult;

public class MainClass {

	private Map<String, List<String>> urlMap = new HashMap<String, List<String>>();

	private VersionParallelProcessor versionParallelProcessor;

	private VersionValidator versionValidator;

	private void loadVersionURls() {
		try {
			File file = new File("settings.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Version.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Version v = (Version) jaxbUnmarshaller.unmarshal(file);
			List<Component> components = v.getComponents();
			for (Component comp : components) {
				List<Country> countries = comp.getCountries();
				for (Country ctry : countries) {
					List<String> urls = ctry.getUrl();
					urlMap.put(comp.getId() + ctry.getId(), urls);
				}
			}

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		System.out.println(urlMap.toString());
	}

	private void validateVersions() throws InterruptedException, InvalidTaskExecutionContext {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/spring.xml");
		versionParallelProcessor = ctx.getBean("versionParallelProcessor", VersionParallelProcessor.class);
		versionValidator = ctx.getBean("versionValidator", VersionValidator.class);
		loadVersionURls();
		List<InputDto> inputList = generateInputList();
		List<VersionCheckResult> result = versionParallelProcessor.parallelTaskExecution(inputList, urlMap);
		System.out.println("Result");
		System.out.println(result);
	}

	private InputDto createInputDto(String component, String country, String version) {
		InputDto dto = new InputDto();
		dto.setComponent(component);
		dto.setCountry(country);
		dto.setVersion(version);
		return dto;
	}

	private List<InputDto> generateInputList() {
		List<InputDto> inputDtoList = new ArrayList<>();
		inputDtoList.add(createInputDto("NCM", "US", "1707.10"));
		inputDtoList.add(createInputDto("NCM", "UK", "1707.3"));
		inputDtoList.add(createInputDto("Node Orchestrator", "US", "1707.13"));
		inputDtoList.add(createInputDto("Node Orchestrator", "UK", "1707.5"));
		return inputDtoList;
	}

	public static void main(String args[]) throws IOException, InterruptedException, InvalidTaskExecutionContext {
		long startTime = System.currentTimeMillis();
		MainClass obj = new MainClass();
		obj.validateVersions();
		System.out.println("Time taken = " + (System.currentTimeMillis() - startTime));
	}
}
