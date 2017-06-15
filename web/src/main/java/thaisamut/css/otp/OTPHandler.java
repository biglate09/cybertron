package thaisamut.css.otp;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import thaisamut.cybertron.ejbweb.model.CssResetPasswordEntity;
import thaisamut.cybertron.ejbweb.remote.CssMasterService;
import thaisamut.cybertron.ejbweb.remote.CssMemberService;

public class OTPHandler {
	@Autowired CssMasterService cssMasterService;
	@Autowired CssMemberService cssMemberService;
	
	
	private static final Logger LOG = LoggerFactory.getLogger(OTPHandler.class);
	Map<String, OTP> provider = new HashMap<String, OTPHandler.OTP>();
	final char[] tokenSeq = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456879".toCharArray();
	final char[] refSeq = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	final long minute = 60*1000, hour = 60*minute,day = 24*hour;
	final int otpLength = 6;
	final int refLength = 4;
	final int tokenLength = 40;
	final int DEFAULT_EXP_TOKEN = 15;
	final Pattern expiredPattern = Pattern.compile("(\\d+)([m|h|d]?)");
	final Pattern keyPattern = Pattern.compile("\\+(\\d+)\\|(\\w+)\\!([A-za-z0-9]+)");
	private boolean isInit = false;
	

	public void initial(){
		try {
			if(!isInit){
				List<CssResetPasswordEntity> resets = cssMasterService.queryCssResetPasswordNotExprired();
				if(resets!=null&&!resets.isEmpty()){
					Matcher m;
					OTP otp;
					for(CssResetPasswordEntity reset : resets){
						m = keyPattern.matcher(reset.getToken());
						if(m.matches()){
							otp = new OTP(m.group(1),m.group(2),m.group(3),reset.getExpireDate().getTime(),reset.getUsername());
							provider.put(otp.key, otp);
						}
					}
				}
				isInit = true;
			}
		} catch (Exception e) {
			LOG.error("~ On OTPHandler : query ",e);
		}
	}
	
	public OTP generate(String exp, String sesssionId){
		initial();
		if (LOG.isDebugEnabled()) {
			LOG.debug("~ On OTPHandler : generate ");
		}
		OTP otp = null;
		
		do{
			otp = new OTP(genOTP(),genRef(),genToken(),System.currentTimeMillis()+findExpired(exp),sesssionId);
		}while( assignProvider(otp));
		
		return otp;
	}

	public OTP validToken(String token){
		initial();
		Iterator<Entry<String, OTP>> iter = provider.entrySet().iterator();
		token = String.format("!%s", token);
		while (iter.hasNext()) {
			Entry<String, OTP> entry = iter.next();
		    if(entry.getKey().contains(token)){
		    	iter.remove();
		    	try {
					cssMasterService.setExpriedCssResetPassword(toCssResetPassword(entry.getValue()));
				} catch (Exception e) {
					LOG.error(e.getMessage(),e);
				}
		        return entry.getValue();
		    }
		}
		return null;
	}

	public OTP validOTP(String ref,String otp){
		initial();
		Iterator<Entry<String, OTP>> iter = provider.entrySet().iterator();
		String token = String.format("+%s|%s", otp,ref);
		while (iter.hasNext()) {
			Entry<String, OTP> entry = iter.next();
		    if(entry.getKey().contains(token)){
		    	iter.remove();
		    	try {
					cssMasterService.setExpriedCssResetPassword(toCssResetPassword(entry.getValue()));
				} catch (Exception e) {
					LOG.error(e.getMessage(),e);
				}
		        return entry.getValue();
		    }
		}
		return null;
	}

	
	private long findExpired(String exp) {
		 try{
			Matcher m = expiredPattern.matcher(exp);
	        if (m.find()) {
	        	int num = Integer.parseInt(m.group(1));
	        	switch (m.group(2)) {
					case "m" : return minute*num;
					case "h" : return hour*num;
					case "d" : return day*num;
				}
	        } 
		 }catch(Exception ignored){}
		return minute*DEFAULT_EXP_TOKEN;
	}
	
	private boolean assignProvider(OTP otp) {
		//clearExprired();
		if(!provider.containsKey(otp.key)){
			try {
				cssMasterService.addCssResetPassword(toCssResetPassword(otp));
				provider.put(otp.key, otp);
			} catch (Exception e) {
				return true;
			}
			return false;
		}
		return true;
	}
	public void clearExprired() {
        try{
		long time = System.currentTimeMillis();
		CssResetPasswordEntity entity;
		Iterator<Entry<String, OTP>> iter = provider.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, OTP> entry = iter.next();
		    if(entry.getValue().expiredTs<time){
		        iter.remove();
		        try{
		        	entity = cssMemberService.findResetPasswordByToken(entry.getValue().getToken());
		        	if(entity!=null)
		        	cssMemberService.removeResetPasswordInfoByEntity(entity);
		        }catch(Exception ex){}
		    }
		}
        }catch(Exception ex){}
	}
	
	public OTP validateOtpEasyLoan(String ref,String otp) throws Exception{
		Map<String, OTP> provider = getCssResetPasswordNotExprired();
		if(null != provider && !provider.isEmpty()){
			Iterator<Entry<String, OTP>> iter = provider.entrySet().iterator();
			String token = String.format("+%s|%s", otp,ref);
			while (iter.hasNext()) {
				Entry<String, OTP> entry = iter.next();
				if(entry.getKey().contains(token)){
					iter.remove();
					try {
						cssMasterService.setExpriedCssResetPassword(toCssResetPassword(entry.getValue()));
					} catch (Exception e) {
						LOG.error(e.getMessage(),e);
					}
					return entry.getValue();
				}
			}
		}
		return null;
	}

	private Map<String, OTP> getCssResetPasswordNotExprired() throws Exception {
		Map<String, OTP> provider = new HashMap<String, OTPHandler.OTP>();
		List<CssResetPasswordEntity> resets = cssMasterService.queryCssResetPasswordNotExprired();
		if(resets!=null&&!resets.isEmpty()){
			Matcher m;
			OTP otpฺBean;
			for(CssResetPasswordEntity reset : resets){
				m = keyPattern.matcher(reset.getToken());
				if(m.matches()){
					otpฺBean = new OTP(m.group(1),m.group(2),m.group(3),reset.getExpireDate().getTime(),reset.getUsername());
					provider.put(otpฺBean.key, otpฺBean);
				}
			}
		}
		return provider;
	}
	
	public OTP validateOtpEasyLoan(String key) throws Exception {
		Map<String, OTP> owners = new HashMap<String, OTPHandler.OTP>();
		List<CssResetPasswordEntity> resets = cssMasterService.queryCssResetPasswordNotExprired();
		OTP otp = null;
		if(resets!=null && !resets.isEmpty()){
			Matcher m;
			for(CssResetPasswordEntity reset : resets){
				m = keyPattern.matcher(reset.getToken());
				if(m.matches()){
					otp = new OTP(m.group(1),m.group(2),m.group(3),reset.getExpireDate().getTime(),reset.getUsername());
					owners.put(otp.owner, otp);
				}
			}
		}
		if(null != owners && !owners.isEmpty() && owners.containsKey(key)){
			return otp;
		}
		return null;
	}
	
	public boolean resetOtpEasyLoan(String key) throws Exception {
		try {
			OTP otp = validateOtpEasyLoan(key);
			if(null != otp){
				cssMasterService.setExpriedCssResetPassword(toCssResetPassword(otp));
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private String genOTP(){
		int randomNumber = new Random().nextInt((1000000 - 100000) + 1) + 100000;
		return String.valueOf(randomNumber).substring(0, otpLength);
	}
	private String genRef(){
		StringBuilder sb = new StringBuilder(refLength);
		Random random = new Random();
		for (int i = 0; i < refLength; i++) {
		    char c = refSeq[random.nextInt(refSeq.length)];
		    sb.append(c);
		}
		return sb.toString();
	}
	private String genToken(){
		StringBuilder sb = new StringBuilder(refLength);
		Random random = new Random();
		for (int i = 0; i < tokenLength; i++) {
		    char c = tokenSeq[random.nextInt(tokenSeq.length)];
		    sb.append(c);
		}
		return sb.toString();
	}
	private CssResetPasswordEntity toCssResetPassword(OTP otp) {
		CssResetPasswordEntity reset = new CssResetPasswordEntity();
		reset.setExpireDate(otp.expiredDate);
		reset.setToken(otp.key);
		reset.setUsername(otp.owner);
		return reset;
	}
	
	public class OTP {
		private final String otp;
		private final String refNo;
		private final String token;
		private final long expiredTs;
		private final Date expiredDate;
		private final String owner;
		private final String key;
		
		private OTP(String otp,String refNo,String token,long expiredTs, String sesssionId){
			this.otp = otp;
			this.refNo = refNo;
			this.token = token;
			this.expiredTs = expiredTs;
			this.expiredDate = new Date(expiredTs);
			this.owner = sesssionId;
			this.key = String.format("+%s|%s!%s", this.otp,this.refNo,this.token);
		}
		
		public String getOtp() {
			return otp;
		}
		public String getRefNo() {
			return refNo;
		}
		public String getToken() {
			return token;
		}
		public Date getExpiredDate(){
			return expiredDate;
		}
		public boolean isOwner(String owner) {
			return this.owner!=null&&this.owner.equals(owner);
		}
		public boolean isNotExpired(){
			return this.expiredTs>System.currentTimeMillis();
		}
		
	}
}
