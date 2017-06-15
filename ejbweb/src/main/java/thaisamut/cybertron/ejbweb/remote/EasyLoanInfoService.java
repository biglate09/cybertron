package thaisamut.cybertron.ejbweb.remote;

import thaisamut.cybertron.ejbweb.bean.CssLoanBean;

public interface EasyLoanInfoService {
	public CssLoanBean findCssLoan(String id,String policyNo) throws Exception;
	public void save(CssLoanBean bean) throws Exception;
	public CssLoanBean updateCssLoanStatus(String idNo,String policyNo,String REQ_NO) throws Exception;
}
