package thaisamut.css.camel.component;

import org.apache.camel.CamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import thaisamut.css.data.service.CssClearDataService;

@Component
public class ClearDataComponent {
	private static final Logger LOG = LoggerFactory.getLogger(ClearDataComponent.class);

	@Autowired
    @Qualifier("camel-scheduler-cybertronapp")
    private CamelContext camelContext;
	

	@Autowired
	private CssClearDataService cssClearDataService;
	
	public void clearExpiredToken() {
		if(LOG.isDebugEnabled()){
			LOG.debug("~On ClearDataComponent clearExpiredToken");
		}
		cssClearDataService.clearExpiredToken();
	}
}
