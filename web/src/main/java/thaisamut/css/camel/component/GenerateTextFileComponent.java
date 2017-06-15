package thaisamut.css.camel.component;

import org.apache.camel.CamelContext;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import thaisamut.css.data.service.GenerateFileService;

@Component
public class GenerateTextFileComponent {
	private static final Logger LOG = LoggerFactory.getLogger(GenerateTextFileComponent.class);

	@Autowired
    @Qualifier("camel-scheduler-cybertronapp")
    private CamelContext camelContext;
	
	@Autowired
	private GenerateFileService generateFileService;
	
	public boolean generateAddressTempFile() {
		boolean result = false;
		try {
			StopWatch sw = new StopWatch();
    		sw.start();
    		result = generateFileService.generateAddressTempFile();
			sw.stop();    		
    		LOG.info(String.format("# Generate File from CSS_ADDRESS_TEMP done in %1$s seconds", sw.getTime()/1000.00));
    		sw.reset();
    	
		} catch (Exception e) {
			LOG.error("", e);
		}
		return result;
	}
	
	public boolean manualTrigger() {
		boolean result = false;
		try {
			StopWatch sw = new StopWatch();
    		sw.start();
    		result = generateFileService.generateAddressTempFile();
			sw.stop();    		
    		LOG.info(String.format("# Manual Trigger done in %1$s seconds", sw.getTime()/1000.00));
    		sw.reset();
    	
		} catch (Exception e) {
			LOG.error("", e);
		}
		return result;
	}
	
	
	public boolean readAddressSend() {
		boolean result = false;
		try {
			StopWatch sw = new StopWatch();
    		sw.start();
    		result = generateFileService.readAddressSend();
			sw.stop();    		
    		LOG.info(String.format("# Read CSS_ADDRESS_SEND and update CSS_ADDRESS_TEMP done in %1$s seconds", sw.getTime()/1000.00));
    		sw.reset();
    	
		} catch (Exception e) {
			LOG.error("", e);
		}
		return result;
	}
	public boolean manualUpdateTrigger(){
		boolean result = false;
		try {
			StopWatch sw = new StopWatch();
    		sw.start();
    		result = generateFileService.readAddressSend();
			sw.stop();    		
    		LOG.info(String.format("# Manual Trigger update send done in %1$s seconds", sw.getTime()/1000.00));
    		sw.reset();
    	
		} catch (Exception e) {
			LOG.error("", e);
		}
		return result;
		
	}
}
