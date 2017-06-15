package thaisamut.nbs.css.oceanclub.action;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.mapping.Array;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import thaisamut.css.dwh.service.DataWarehouseService;
import thaisamut.css.dwh.service.pojo.PersonBean;
import thaisamut.css.dwh.service.pojo.PolicyBean;
import thaisamut.css.model.CssUser;
import thaisamut.css.struts.action.CssJsonAction;
import thaisamut.cybertron.ejbweb.model.CssAddressTempEntity;
import thaisamut.cybertron.ejbweb.model.MarkChoicesEntity;
import thaisamut.cybertron.ejbweb.model.ThailandZipcodeEntity;
import thaisamut.cybertron.ejbweb.model.BranchEntity;
import thaisamut.cybertron.ejbweb.model.CampaignEntity;
import thaisamut.cybertron.ejbweb.model.PlaceEntity;
import thaisamut.cybertron.ejbweb.remote.CssMasterService;
import thaisamut.cybertron.ejbweb.remote.MarkChoicesService;
import thaisamut.cybertron.ejbweb.remote.BranchService;
import thaisamut.cybertron.ejbweb.remote.CampaignService;
import thaisamut.cybertron.ejbweb.remote.PlaceService;
import thaisamut.nbs.struts.action.JsonAction;
import thaisamut.philotesapp.services.AddressBean;
import thaisamut.philotesapp.services.CardType;
import thaisamut.philotesapp.services.Gender;
import thaisamut.philotesapp.services.MaritalStatus;
import thaisamut.philotesapp.services.Q1;
import thaisamut.philotesapp.services.Q2;
import thaisamut.philotesapp.services.Q3;
import thaisamut.philotesapp.services.Q4;
import thaisamut.philotesapp.services.Q5;
import thaisamut.philotesapp.services.Q6;
import thaisamut.philotesapp.services.Q7;
import thaisamut.philotesapp.services.Q8;
import thaisamut.philotesapp.services.Q9;
import thaisamut.philotesapp.services.RegisterOceanClubBean;
import thaisamut.philoteswsclient.component.PhilotesWsClientFactory;

public class OceanClubAction extends CssJsonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5648883017934995363L;
	private static final Logger LOG = LoggerFactory.getLogger(OceanClubAction.class);
	
	@Autowired private PhilotesWsClientFactory pWsClientFactory;
	@Autowired private DataWarehouseService dwhService;
	@Autowired private CssMasterService cssMasterService;
	@Autowired private MarkChoicesService markChoicesService;
	@Autowired private CampaignService campaignService;
	@Autowired private BranchService branchService;
	@Autowired private PlaceService placeService;
	
	public String[] c1 = {}, c2 = {}, c3 = {}, c4 = {}, c5 = {}, c6 = {}, c7 = {}, c8 = {}, sc4 = {}, sc5_1 = {}, sc5_2 = {}, sc6 = {};
	public String c7a14other, c8a20other, sc4a10other, sc5a25other, sc6a16other, remark;
	private int campaignId;
	private int placeId;
	
	private int branchId;
	
	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}

	public int getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(int campaignId) {
		this.campaignId = campaignId;
	}

	public String[] getC1() {
		return c1;
	}

	public void setC1(String[] c1) {
		this.c1 = c1;
	}

	public String[] getC2() {
		return c2;
	}

	public void setC2(String[] c2) {
		this.c2 = c2;
	}

	public String[] getC3() {
		return c3;
	}

	public void setC3(String[] c3) {
		this.c3 = c3;
	}

	public String[] getC4() {
		return c4;
	}

	public void setC4(String[] c4) {
		this.c4 = c4;
	}

	public String[] getC5() {
		return c5;
	}

	public void setC5(String[] c5) {
		this.c5 = c5;
	}

	public String[] getC6() {
		return c6;
	}

	public void setC6(String[] c6) {
		this.c6 = c6;
	}

	public String[] getC7() {
		return c7;
	}

	public void setC7(String[] c7) {
		this.c7 = c7;
	}

	public String[] getC8() {
		return c8;
	}

	public void setC8(String[] c8) {
		this.c8 = c8;
	}

	public String[] getSc4() {
		return sc4;
	}

	public void setSc4(String[] sc4) {
		this.sc4 = sc4;
	}

	public String[] getSc5_1() {
		return sc5_1;
	}

	public void setSc5_1(String[] sc5_1) {
		this.sc5_1 = sc5_1;
	}

	public String[] getSc5_2() {
		return sc5_2;
	}

	public void setSc5_2(String[] sc5_2) {
		this.sc5_2 = sc5_2;
	}

	public String[] getSc6() {
		return sc6;
	}

	public void setSc6(String[] sc6) {
		this.sc6 = sc6;
	}

	public String getC7a14other() {
		return c7a14other;
	}

	public void setC7a14other(String c7a14other) {
		this.c7a14other = c7a14other;
	}

	public String getC8a20other() {
		return c8a20other;
	}

	public void setC8a20other(String c8a20other) {
		this.c8a20other = c8a20other;
	}

	public String getSc4a10other() {
		return sc4a10other;
	}

	public void setSc4a10other(String sc4a10other) {
		this.sc4a10other = sc4a10other;
	}

	public String getSc5a25other() {
		return sc5a25other;
	}

	public void setSc5a25other(String sc5a25other) {
		this.sc5a25other = sc5a25other;
	}

	public String getSc6a16other() {
		return sc6a16other;
	}

	public void setSc6a16other(String sc6a16other) {
		this.sc6a16other = sc6a16other;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String index() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On OceanClubAction : index");
		}
		return super.index();
	}
	
	public String checkOceanClubCampaign() throws Exception{
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On OceanClubAction : checkOceanClubCampaign");
		}
		CssUser user = getSessionUser();
		List<CampaignEntity> camps = new ArrayList<CampaignEntity>();
		camps = campaignService.findAllCampaign();
		sessionAttributes.put("camps",camps);
		return super.index();
	}
	
	public String checkOceanClubPlace() throws Exception{
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On OceanClubAction : checkOceanClubDetail");
		}
		CssUser user = getSessionUser();
		List<PlaceEntity> places = new ArrayList<PlaceEntity>();
		places = placeService.findAllPlace();
		sessionAttributes.put("places",places);
		return super.index();
	}

	public String setMarkChoices() throws Exception {
		CssUser user = getSessionUser();
		String idNo = user.getUsername();
		List<MarkChoicesEntity> markChoices = markChoicesService.findByIdNo(idNo);
		requestAttributes.put("markChoices", markChoices);
		return super.index();
	}

	public String editMember() throws Exception {
		CssUser user = getSessionUser();
		String idNo = user.getUsername();
		Map<String,String[]> maps = request.getParameterMap();
		List<Integer> list = new LinkedList<Integer>();
		for(String[] values : maps.values()){
			for(String v : values){
				list.add(Integer.parseInt(v));
			}
		}

		markChoicesService.editChoicesId(list,idNo);
		return setMarkChoices();
	}
	
	public String checkOceanClubCampaignDetail() throws Exception{
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On OceanClubAction : checkOceanClubDetail");
		}
		CssUser user = getSessionUser();
		CampaignEntity camp = campaignService.findCampaignDetail(campaignId);
	 
		sessionAttributes.put("camp",camp);
		return super.index();
	}
	
	public String checkOceanClubPlaceDetail() throws Exception{
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On OceanClubAction : checkOceanClubDetail");
		}
		CssUser user = getSessionUser();
		PlaceEntity place = placeService.findByPlaceId(placeId);
	 
		sessionAttributes.put("place",place);
		return super.index();
	}
	
	
	public String checkOceanClub() throws Exception {
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				if (LOG.isDebugEnabled()) {
					LOG.debug("~On OceanClubAction : checkOceanClub");
				}
				
				CssUser user = getSessionUser();
				sessionAttributes.put("ocFullname", "à¸„à¸¸à¸“"+user.getFirstName() + " " + user.getLastName());
				sessionAttributes.put("ocExpireDate", "à¸šà¸±à¸•à¸£à¸«à¸¡à¸”à¸­à¸²à¸¢à¸¸ " + pWsClientFactory.getOceanClubMemberWSService().getExpiredCardDate(user.getCardNo()));
				
				Map<String, Object> data = new HashMap<String, Object>();
				
				if(sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST) != null){
					HashMap<String, PolicyBean> policies = (HashMap<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
					
					boolean status = false;
					if(pWsClientFactory.getOceanClubMemberWSService().getMemberFlag(user.getCardNo()).equals("Y")){
						status = true;
					}
					data.put("status", status);
				}
				result.setData(data);
			}
		}).run();
	}
	
	public String register() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On OceanClubAction : register");
		}
		return super.index();
	}
	
	public String member() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On OceanClubAction : member");
		}
		CssUser user = getSessionUser();
		sessionAttributes.put("ocFullname", "à¸„à¸¸à¸“"+user.getFirstName() + " " + user.getLastName());
		sessionAttributes.put("ocExpireDate","à¸šà¸±à¸•à¸£à¸«à¸¡à¸”à¸­à¸²à¸¢à¸¸ " + pWsClientFactory.getOceanClubMemberWSService().getExpiredCardDate(user.getCardNo()));
		return super.index();
	}
	
	public String preMember() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On OceanClubAction : preMember");
		}
		return super.index();
	}
	
	public String editInfo() throws Exception {
		if(LOG.isDebugEnabled()) {
			LOG.debug("~On OceanClubAction : editInfo");
		}
		
		try {
			return "success";
		}
		catch(Exception ex) {
			LOG.info("## Exception : " + ex.getMessage());
			return "fail";
		}
	}
	
	public String oceanBranch() throws Exception {
		if(LOG.isDebugEnabled()) {
			LOG.debug("~On OceanClubAction : oceanBranch");
		}
		
		try {
			CssUser user = getSessionUser();
			List<BranchEntity> branch = new ArrayList<BranchEntity>();
			branch = branchService.findAll();
		 
			sessionAttributes.put("branch", branch);
			
			return super.index();
		}
		catch(Exception ex) {
			LOG.info("## Exception : " + ex.getMessage());
			return "fail";
		}
	}
	
	public String branchDetail() throws Exception{
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On OceanClubAction : checkOceanClubDetail");
		}
		CssUser user = getSessionUser();
		BranchEntity branch = branchService.findPosition(branchId);
		 
		sessionAttributes.put("branch", branch);
		return super.index();
	}
	
	public String saveRegister() throws Exception {

		if (LOG.isDebugEnabled()) {
			LOG.debug("~On OceanClubAction : saveRegister");
		}
		try{
			CssUser user = getSessionUser();
			PolicyBean lastestPolicy = null;
			PersonBean lastestPerson = null;
			
			if (sessionAttributes.get(SESSION_ATTRIBUTE_USER) == null) {
				PersonBean person = dwhService.getPersonData(user.getCardNo());
				sessionAttributes.put(SESSION_ATTRIBUTE_USER, person);
			}
			
			if (sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST) == null) {
				Map<String,PolicyBean> policies = dwhService.getPolicyData(user.getCardNo(),user.getEmail());
				sessionAttributes.put(SESSION_ATTRIBUTE_POLICY_LIST, policies);
			}
			
			if(sessionAttributes.get(SESSION_ATTRIBUTE_USER) != null){
				lastestPerson = (PersonBean) sessionAttributes.get(SESSION_ATTRIBUTE_USER);
			}
			
			if(sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST) != null){
				HashMap<String, PolicyBean> policies = (HashMap<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
				List<PolicyBean> policyList = new ArrayList<PolicyBean>(policies.values());
				if(policyList != null){
					Collections.sort(policyList);
				}
				
				Map<String,PolicyBean> rqcpList = new HashMap<String,PolicyBean>();
				
				
				
				int index = 0;
				for (PolicyBean policyBean : policyList) {
					rqcpList.put(policyBean.getPolicyNo(), policyBean);
					if(index == 0){
						lastestPolicy = policyBean;
					}
					index++;
				}
			}
			

			String email = null;
			if(StringUtils.isNotBlank(user.getEmail())){
				email = user.getEmail();
			}else{
				CssAddressTempEntity cssAddressTemp = cssMasterService.queryCssAddressTempByIdNo(user.getCardNo());
				if(cssAddressTemp != null){
					Calendar c = Calendar.getInstance();
					c.setTime(new Date());
					c.add(Calendar.DATE, -1);
					Date dwh = c.getTime();
					Date cssTemp = cssAddressTemp.getUpdateDate();
					if(cssTemp.compareTo(dwh) >= 0){
						if(StringUtils.isNotBlank(cssAddressTemp.getEmail())){
							email = cssAddressTemp.getEmail();
						}
					}
				}
			}
			
			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy",new Locale("TH","th"));
			
			RegisterOceanClubBean bean = new RegisterOceanClubBean();
			bean.setIdCard(lastestPerson.getIdNo());
			bean.setIdTypeDesc(CardType.ID_CARD);
			bean.setTitleDesc(lastestPerson.getTitleDesc());
			bean.setFirstName(lastestPerson.getFirstName());
			bean.setLastName(lastestPerson.getLastName());
			
			Date birthDate = sf.parse(lastestPerson.getBirthDate());
			GregorianCalendar birthGcal = new GregorianCalendar();
			birthGcal.setTime(birthDate);
			XMLGregorianCalendar birthXgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(birthGcal);
			
			bean.setBirthDate(birthXgcal);

			bean.setGenderDesc(lastestPerson.getGenderCode().equals("M") ? Gender.M : Gender.F);
			bean.setGenderKey(lastestPerson.getGenderCode().equals("M") ? Gender.M : Gender.F);
			
			lastestPerson.getMaritalStatusDesc().equals("à¹‚à¸ªà¸”");
			if(StringUtils.isNotBlank(lastestPerson.getMaritalStatusDesc())){
				if(lastestPerson.getMaritalStatusDesc().equals("à¹‚à¸ªà¸”")){
					bean.setMaritalDesc(MaritalStatus.SINGLE);
				}else if(lastestPerson.getMaritalStatusDesc().equals("à¸ªà¸¡à¸£à¸ª")){
					bean.setMaritalDesc(MaritalStatus.MARRIED);
				}else if(lastestPerson.getMaritalStatusDesc().equals("à¸«à¸¡à¹‰à¸²à¸¢")){
					bean.setMaritalDesc(MaritalStatus.WIDOWED);
				}else if(lastestPerson.getMaritalStatusDesc().equals("à¸ªà¸¡à¸£à¸ªà¸¡à¸µà¸šà¸¸à¸•à¸£à¹à¸¥à¹‰à¸§")){
					bean.setMaritalDesc(MaritalStatus.HAVE_CHILDREN);
				}else if(lastestPerson.getMaritalStatusDesc().equals("à¹à¸¢à¸à¸à¸±à¸™à¸­à¸¢à¸¹à¹ˆ")){
					bean.setMaritalDesc(MaritalStatus.SEPARATED);
				}else if(lastestPerson.getMaritalStatusDesc().equals("à¸«à¸¢à¹ˆà¸²")){
					bean.setMaritalDesc(MaritalStatus.DIVORCED);
				}
			}else{
				bean.setMaritalDesc(null);
			}

			
			bean.setEmail(email);
			if(lastestPolicy != null){
				bean.setMobile1(lastestPolicy.getMobile1());
				bean.setMobile2(lastestPolicy.getMobile2());
				bean.setPhone1(lastestPolicy.getPhone1());
				bean.setPhone2(lastestPolicy.getPhone2());
				bean.setExt1(lastestPolicy.getExt1());
				bean.setExt2(lastestPolicy.getExt2());
//					bean.setUsername(lastestPolicy.get);
			}
			GregorianCalendar gcal = new GregorianCalendar();
		    XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		    bean.setCreateDate(xgcal);
		    bean.setCreateBy(user.getFullname());
		    
			AddressBean addressBean = new AddressBean();
			
			ThailandZipcodeEntity tzEntity = cssMasterService.queryCssThailandZipcodeBySubDistrictAndDistrictAndProvince(lastestPolicy.getSubdistrictName(), lastestPolicy.getDistrictName(), lastestPolicy.getProvinceName());
			
			addressBean.setAddr(lastestPolicy.getAddressLine1());
			addressBean.setSdtDesc(tzEntity.getSubdistrict());
			addressBean.setDtDesc(tzEntity.getDistrict());
			addressBean.setDtCode(String.valueOf(tzEntity.getDistrictAs400()));
			addressBean.setPvDesc(tzEntity.getProvince());
			addressBean.setPvCode(String.valueOf(tzEntity.getProvinceAs400()));
			addressBean.setZipcode(String.valueOf(tzEntity.getZipcode()));
			bean.setAddressBean(addressBean);
			
			if(c1 != null){
				Q1 q1 = new Q1();
				for (String c : c1) {
					if(c.equals("1")){
						q1.setAis(true);
					}else if(c.equals("2")){
						q1.setDtac(true);
					}else if(c.equals("3")){
						q1.setTruemoveH(true);
					}else if(c.equals("4")){
						q1.setOtherNetwork(true);
					}
				}
				bean.setQ1(q1);
			}
			
			if(c2 != null){
				Q2 q2 = new Q2(); 
				for (String c : c2) {
					if(c.equals("5")){
						q2.setMonthly(true);
					}else if(c.equals("6")){
						q2.setPrepay(true);
					}else if(c.equals("7")){
						q2.setOtherPackage(true);
					}
				}
				bean.setQ2(q2);
			}
			
			if(c3 != null){
				Q3 q3 = new Q3();
				for (String c : c3) {
					if(c.equals("8")){
						q3.setSmartPhone(true);
					}else if(c.equals("9")){
						q3.setBasicPhone(true);
					}else if(c.equals("10")){
						q3.setOtherPhone(true);
					}
				}
				bean.setQ3(q3);
			}
			
			if(c4 != null){
				Q4 q4 = new Q4();
				String c4m = null;
				for (String c : c4) {
					c4m = c;
					break;
				}
				if(c4m.equals("11")){
					q4.setLike(true);
					for (String sc : sc4) {
						if(sc.equals("13")){
							q4.setSea(true);
						}else if(sc.equals("14")){
							q4.setMountain(true);
						}else if(sc.equals("15")){
							q4.setWaterfall(true);
						}else if(sc.equals("16")){
							q4.setMuseum(true);
						}else if(sc.equals("17")){
							q4.setTemple(true);
						}else if(sc.equals("18")){
							q4.setForeign(true);
						}else if(sc.equals("19")){
							q4.setThemePark(true);
						}else if(sc.equals("20")){
							q4.setZoo(true);
						}else if(sc.equals("21")){
							q4.setDepartmentStore(true);
						}else if(sc.equals("22")){
							q4.setOtherPlace(true);
							if(StringUtils.isNotBlank(sc4a10other)){
								q4.setOtherRemark(sc4a10other);
							}
						}
					}
				}else{
					q4.setDislike(true);
				}
				bean.setQ4(q4);
			}
			
			if(c5 != null){
				Q5 q5 = new Q5();
				String c5m = null;
				for (String c : c5) {
					c5m = c;
					break;
				}
				if(c5m.equals("24")){
					q5.setExercise(true);
					for (String sc1 : sc5_1) {
						if(sc1.equals("25")){
							q5.setOncePerWeek(true);
						}else if(sc1.equals("26")){
							q5.setTwicePerWeek(true);
						}else if(sc1.equals("27")){
							q5.setThricePerWeek(true);
						}else if(sc1.equals("28")){
							q5.setFourTimesPerWeek(true);
						}else if(sc1.equals("29")){
							q5.setMoreThan4TimesPerWeek(true);
						}
					}
					for (String sc2 : sc5_2) {
						if(sc2.equals("30")){
							q5.setRopeJumping(true);
						}else if(sc2.equals("31")){
							q5.setGolf(true);
						}else if(sc2.equals("32")){
							q5.setPhysicalExercise(true);
						}else if(sc2.equals("33")){
							q5.setExerciseMachine(true);
						}else if(sc2.equals("34")){
							q5.setWalk(true);
						}else if(sc2.equals("35")){
							q5.setSepakTakraw(true);
						}else if(sc2.equals("36")){
							q5.setDance(true);
						}else if(sc2.equals("37")){
							q5.setTennis(true);
						}else if(sc2.equals("38")){
							q5.setTaichi(true);
						}else if(sc2.equals("39")){
							q5.setBasketball(true);
						}else if(sc2.equals("40")){
							q5.setBadminton(true);
						}else if(sc2.equals("41")){
							q5.setRideBicycle(true);
						}else if(sc2.equals("42")){
							q5.setTableTennis(true);
						}else if(sc2.equals("43")){
							q5.setPetanque(true);
						}else if(sc2.equals("44")){
							q5.setFitness(true);
						}else if(sc2.equals("45")){
							q5.setFootball(true);
						}else if(sc2.equals("46")){
							q5.setBoxing(true);
						}else if(sc2.equals("47")){
							q5.setPole(true);
						}else if(sc2.equals("48")){
							q5.setYoga(true);
						}else if(sc2.equals("49")){
							q5.setVolleyball(true);
						}else if(sc2.equals("50")){
							q5.setSwimming(true);
						}else if(sc2.equals("51")){
							q5.setRun(true);
						}else if(sc2.equals("52")){
							q5.setAerobics(true);
						}else if(sc2.equals("53")){
							q5.setHulaHoop(true);
						}else if(sc2.equals("54")){
							q5.setOtherExercise(true);
							if(StringUtils.isNotBlank(sc5a25other)){
								q5.setOtherRemark(sc5a25other);
							}
						}
					}
				}else{
					q5.setNotExercise(true);
				}
				bean.setQ5(q5);
			}
			
			if(c6 != null){
				Q6 q6 = new Q6();
				String c6m = null;
				for (String c : c6) {
					c6m = c;
					break;
				}
				if(c6m.equals("56")){
					q6.setLike(true);
					for (String sc : sc6) {
						if(sc.equals("57")){
							q6.setThaiPopMusic(true);
						}else if(sc.equals("58")){
							q6.setThaiRockMusic(true);
						}else if(sc.equals("59")){
							q6.setThaiDanceMusic(true);
						}else if(sc.equals("60")){
							q6.setCountryMusic(true);
						}else if(sc.equals("61")){
							q6.setCityMusic(true);
						}else if(sc.equals("62")){
							q6.setNorthEastStyleMusic(true);
						}else if(sc.equals("63")){
							q6.setMusicForLifeMusic(true);
						}else if(sc.equals("64")){
							q6.setOriginalThaiMusic(true);
						}else if(sc.equals("65")){
							q6.setClassicalMusic(true);
						}else if(sc.equals("66")){
							q6.setJazzMusic(true);
						}else if(sc.equals("67")){
							q6.setKoreanSong(true);
						}else if(sc.equals("68")){
							q6.setJapaneseSong(true);
						}else if(sc.equals("69")){
							q6.setChineseSong(true);
						}else if(sc.equals("70")){
							q6.setWesternSong(true);
						}else if(sc.equals("71")){
							q6.setChildrenSong(true);
						}else if(sc.equals("72")){
							q6.setOtherSong(true);
							if(StringUtils.isNotBlank(sc6a16other)){
								q6.setOtherSongRemark(sc6a16other);
							}
						}
					}
				}else{
					q6.setDislike(true);
				}
				bean.setQ6(q6);
			}
			
			if(c7 != null){
				Q7 q7 = new Q7();
				for (String c : c7) {
					if(c.equals("74")){
						q7.setThaiNorthFood(true);
					}else if(c.equals("75")){
						q7.setThaiNorthEastFood(true);
					}else if(c.equals("76")){
						q7.setThaiCentralFood(true);
					}else if(c.equals("77")){
						q7.setThaiSouthernFood(true);
					}else if(c.equals("78")){
						q7.setKoreanFood(true);
					}else if(c.equals("79")){
						q7.setChineseFood(true);
					}else if(c.equals("80")){
						q7.setJapaneseFood(true);
					}else if(c.equals("81")){
						q7.setWesternFood(true);
					}else if(c.equals("82")){
						q7.setSeaFood(true);
					}else if(c.equals("83")){
						q7.setVegetarianFood(true);
					}else if(c.equals("84")){
						q7.setVegetarian(true);
					}else if(c.equals("85")){
						q7.setHealthyFood(true);
					}else if(c.equals("86")){
						q7.setIslamicFood(true);
					}else if(c.equals("87")){
						q7.setOtherFood(true);
						if(StringUtils.isNotBlank(c7a14other)){
							q7.setOtherRemark(c7a14other);
						}
					}
				}
				bean.setQ7(q7);
			}
			
			if(c8 != null){
				Q8 q8 = new Q8();
				for (String c : c8) {
					if(c.equals("88")){
						q8.setGrocery(true);
					}else if(c.equals("89")){
						q8.setFreshFoodMarket(true);
					}else if(c.equals("90")){
						q8.setFleaMarket(true);
					}else if(c.equals("91")){
						q8.setDirectSale(true);
					}else if(c.equals("92")){
						q8.setMall(true);
					}else if(c.equals("93")){
						q8.setSevenEleven(true);
					}else if(c.equals("94")){
						q8.setFamilyMart(true);
					}else if(c.equals("95")){
						q8.setLawson(true);
					}else if(c.equals("96")){
						q8.setLotus(true);
					}else if(c.equals("97")){
						q8.setBigCMini(true);
					}else if(c.equals("98")){
						q8.setTops(true);
					}else if(c.equals("99")){
						q8.setGourmetMarket(true);
					}else if(c.equals("100")){
						q8.setHomeFreschMart(true);
					}else if(c.equals("101")){
						q8.setVillaMarket(true);
					}else if(c.equals("102")){
						q8.setMaxValue(true);
					}else if(c.equals("103")){
						q8.setTopDaily(true);
					}else if(c.equals("104")){
						q8.setTescoLotus(true);
					}else if(c.equals("105")){
						q8.setBigC(true);
					}else if(c.equals("106")){
						q8.setMakro(true);
					}else if(c.equals("107")){
						q8.setOtherPurchases(true);
						if(StringUtils.isNotBlank(c8a20other)){
							q8.setOtherRemark(c8a20other);
						}
					}
				}
				bean.setQ8(q8);
			}
			
			if(StringUtils.isNotBlank(remark)){
				Q9 q9 = new Q9();
				q9.setOtherOceanClub(remark);
				bean.setQ9(q9);
			}
			
			pWsClientFactory.getOceanClubMemberWSService().registerOceanClub(bean);
			
			return "success";
		}catch(Exception ex){
			LOG.info("## Exception : " + ex.getMessage());
			return "fail";
		}
	}
	
	//-----------------------------------------------------------------------------------
	
	
}