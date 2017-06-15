package thaisamut.css.data.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import thaisamut.css.data.service.CssClearDataService;
import thaisamut.cybertron.ejbweb.remote.CssMemberService;

public class CssClearDataServiceImpl implements CssClearDataService {
	private static final Logger LOG = LoggerFactory.getLogger(CssClearDataServiceImpl.class);
	
	@Autowired CssMemberService cssMemberService;

	@Override
	public void clearExpiredToken() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssClearDataServiceImpl clearExpiredToken");
		}
		cssMemberService.clearExpiredToken();
	}
	
}
