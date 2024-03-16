package kraheja.payroll.masterdetail.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kraheja.adminexp.vehicleexp.dataentry.entity.Admexpd;
import kraheja.adminexp.vehicleexp.dataentry.entity.AdmexpdCK;
import kraheja.adminexp.vehicleexp.dataentry.mappers.AdmexpdEntityPojoMapper;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.Address;
import kraheja.commons.entity.Party;
import kraheja.commons.enums.AdSegment;
import kraheja.commons.enums.AdType;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.mappers.pojoentity.AddressMapper;
import kraheja.commons.mappers.pojoentity.PartyMapper;
import kraheja.commons.repository.AddressRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.repository.PartyRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;
import kraheja.payroll.bean.CoyLeaveBean;
import kraheja.payroll.bean.CoySchemeBean;
import kraheja.payroll.bean.CoysalarypackageBean;
import kraheja.payroll.bean.EmployeeDetailsRequestBean;
import kraheja.payroll.bean.EmployeeDetailsResponseBean;
import kraheja.payroll.bean.EmployeeSalDetailsResponseBean;
import kraheja.payroll.bean.EmpsalarypackageEarnDedBean;
import kraheja.payroll.bean.request.EmpleaveinfoRequestBean;
import kraheja.payroll.bean.response.EmpleaveinfoResponseBean;
import kraheja.payroll.entity.Empassetinfo;
import kraheja.payroll.entity.Empeducation;
import kraheja.payroll.entity.Empexperience;
import kraheja.payroll.entity.Empfamily;
import kraheja.payroll.entity.Empjobinfo;
import kraheja.payroll.entity.Empleaveinfo;
import kraheja.payroll.entity.Emppersonal;
import kraheja.payroll.entity.Empreference;
import kraheja.payroll.entity.Empschemeinfo;
import kraheja.payroll.masterdetail.mappers.EmpassetinfoEntityPojoMapper;
import kraheja.payroll.masterdetail.mappers.EmpeducationEntityPojoMapper;
import kraheja.payroll.masterdetail.mappers.EmpexperienceEntityPojoMapper;
import kraheja.payroll.masterdetail.mappers.EmpfamilyEntityPojoMapper;
import kraheja.payroll.masterdetail.mappers.EmpjobinfoEntityPojoMapper;
import kraheja.payroll.masterdetail.mappers.EmpleaveinfoEntityPojoMapper;
import kraheja.payroll.masterdetail.mappers.EmppersonalEntityPojoMapper;
import kraheja.payroll.masterdetail.mappers.EmpreferenceEntityPojoMapper;
import kraheja.payroll.masterdetail.mappers.EmpsalarypackageEntityPojoMapper;
import kraheja.payroll.masterdetail.mappers.EmpschemeinfoEntityPojoMapper;
import kraheja.payroll.masterdetail.service.EmployeeDetailsEntryEditService;
import kraheja.payroll.repository.EmpassetinfoRepository;
import kraheja.payroll.repository.EmpeducationRepository;
import kraheja.payroll.repository.EmpexperienceRepository;
import kraheja.payroll.repository.EmpfamilyRepository;
import kraheja.payroll.repository.EmpjobinfoRepository;
import kraheja.payroll.repository.EmpleaveinfoRepository;
import kraheja.payroll.repository.EmployeeDetailsEntryEditRepository;
import kraheja.payroll.repository.EmppersonalRepository;
import kraheja.payroll.repository.EmpreferenceRepository;
import kraheja.payroll.repository.EmpsalarypackageRepository;
import kraheja.payroll.repository.EmpschemeinfoRepository;
import kraheja.payroll.repository.ReportParametersRepository;

@Service
@Transactional
public class EmployeeDetailsEntryEditServiceImpl implements EmployeeDetailsEntryEditService{

	private static final Logger logger = LoggerFactory.getLogger(EmployeeDetailsEntryEditServiceImpl.class);
	@Autowired
	private EmppersonalRepository emppersonalRepository;
	
	@Autowired
	private EmpeducationRepository empeducationRepository;
	
	@Autowired
	private EmpjobinfoRepository empjobinfoRepository;
	
	@Autowired
	private EmpsalarypackageRepository empsalarypackageRepository;
	
	@Autowired
	private EmpschemeinfoRepository empschemeinfoRepository;
	
	@Autowired
	private EmpleaveinfoRepository empleaveinfoRepository;
	
	@Autowired
	private EmpfamilyRepository empfamilyRepository;
	
	@Autowired
	private EmpexperienceRepository empexperienceRepository;
	
	@Autowired
	private EmpreferenceRepository empreferenceRepository;
	
	@Autowired
	private EmpassetinfoRepository empassetinfoRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private  EntityRepository entityRepository;
	
	@Autowired
	private EmployeeDetailsEntryEditRepository employeeDetailsEntryEditRepository;
	
	@Autowired
	private ReportParametersRepository reportParametersRepository;
	
	@Autowired
	private  PartyRepository partyRepository;
	
	public ResponseEntity<?> fetchEmplDetails(String empcode) throws Exception{
		EmployeeDetailsResponseBean employeeDetailsResponseBean = new EmployeeDetailsResponseBean();
		byte[] empPhoto;
		String empphotopath;
		
		//get emppersonal details
		List<Emppersonal> emppersonallist = emppersonalRepository.findByEmppersonalCK_EperEmpcode(empcode);
		if(CollectionUtils.isNotEmpty(emppersonallist)) {
			employeeDetailsResponseBean.setEmppersonalResponseBean(EmppersonalEntityPojoMapper.fetchEmppersonalEntityPojoMapper.apply(emppersonallist));
		
		//get empeducation details
		List<Empeducation> empeducationlist = empeducationRepository.findByEmpeducationCK_EeduEmpcode(empcode);
		employeeDetailsResponseBean.setEmpeducationResponseBean(EmpeducationEntityPojoMapper.fetchEmpeducationEntityPojoMapper.apply(empeducationlist));
		
		//get empjobinfo details
		List<Empjobinfo> empjobinfolist = empjobinfoRepository.findByEmpjobinfoCK_EjinEmpcode(empcode);
		if(CollectionUtils.isNotEmpty( empjobinfolist)) {
			employeeDetailsResponseBean.setEmpjobinfoResponseBean(EmpjobinfoEntityPojoMapper.fetchEmpjobinfoEntityPojoMapper.apply(empjobinfolist));
		}
		
		//get salarypackage details for earnings
		List<Tuple> empsalarypackagelist = empsalarypackageRepository.findByCurrentEarnPackage(empcode);
		if(empsalarypackagelist.size()>0) {
			List<EmpsalarypackageEarnDedBean> empsalarypackageEarnDedBeanList = 
					empsalarypackagelist.stream().map(t -> {return new EmpsalarypackageEarnDedBean(
							t.get(0, String.class),
							t.get(1, String.class).trim(),
							t.get(2, Character.class),
							t.get(3, Character.class),
							t.get(4,  BigDecimal.class).doubleValue(),
							t.get(5, Timestamp.class).toLocalDateTime().toLocalDate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).toString(),
							t.get(6, Timestamp.class).toLocalDateTime().toLocalDate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).toString(),
							t.get(7, String.class).trim(),
							t.get(8, String.class).trim(),
							t.get(9, String.class).trim(),
							t.get(10, String.class).trim(),
							t.get(11, String.class).trim(),
							t.get(12, Timestamp.class).toLocalDateTime(),
							t.get(13, String.class).trim(),
							t.get(14, Character.class),
							t.get(15, Character.class)
							);
					}
							).collect(Collectors.toList());
			logger.info("empsalarypackage_Kalpana :: {}", empsalarypackageEarnDedBeanList);
			employeeDetailsResponseBean.setEmpsalarypackageResponseBean(empsalarypackageEarnDedBeanList);
		}

		//get salarypackage details for deductions
		List<Tuple> empsalarypackagededlist = empsalarypackageRepository.findByCurrentDedPackage(empcode);
		if(empsalarypackagededlist.size()>0) {
			List<EmpsalarypackageEarnDedBean> empsalarypackageEarnDedBeanList = 
					empsalarypackagededlist.stream().map(t -> {return new EmpsalarypackageEarnDedBean(
							t.get(0, String.class),
							t.get(1, String.class).trim(),
							t.get(2, Character.class),
							t.get(3, Character.class),
							t.get(4,  BigDecimal.class).doubleValue(),
							t.get(5, Timestamp.class).toLocalDateTime().toLocalDate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).toString(),
							t.get(6, Timestamp.class).toLocalDateTime().toLocalDate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).toString(),
							t.get(7, String.class).trim(),
							t.get(8, String.class).trim(),
							t.get(9, String.class).trim(),
							t.get(10, String.class).trim(),
							t.get(11, String.class).trim(),
							t.get(12, Timestamp.class).toLocalDateTime(),
							t.get(13, String.class).trim(),
							t.get(14, Character.class),
							t.get(15, Character.class)
							);
					}
							).collect(Collectors.toList());
			logger.info("empsalarypackage_Kalpana :: {}", empsalarypackageEarnDedBeanList);
			employeeDetailsResponseBean.setEmpsalarypackagededResponseBean(empsalarypackageEarnDedBeanList);
		}
		
		
		//get empschemeinfo details
		List<Empschemeinfo> empschemeinfolist = empschemeinfoRepository.findByEmpschemeinfoCK_EschEmpcode(empcode);
		if(CollectionUtils.isNotEmpty( empschemeinfolist)) {
			employeeDetailsResponseBean.setEmpschemeinfoResponseBean(EmpschemeinfoEntityPojoMapper.fetchEmpschemeinfoEntityPojoMapper.apply(empschemeinfolist));;
		}
		
		//get empleave details
//		List<Empleaveinfo> empleaveinfolist = empleaveinfoRepository.findByEmpleaveinfoCK_ElinEmpcodeOrderByEmpleaveinfoCK_ElinAcyearDesc(empcode);
//		if(CollectionUtils.isNotEmpty( empschemeinfolist)) {
//			employeeDetailsResponseBean.setEmpleaveinfoResponseBean(EmpleaveinfoEntityPojoMapper.fetchEmpleaveinfoEntityPojoMapper.apply(empleaveinfolist));
//		}
		List<Tuple> empleaveinfolist = empleaveinfoRepository.findByEmpleaveinfoEmpCode(empcode);
		if(empleaveinfolist.size()>0) {
			List<EmpleaveinfoResponseBean> empleaveinfoResponseBean = 
					empleaveinfolist.stream().map(lev -> {
						String element15 = lev.get(15, String.class);
	                    if (element15 != null) {
	                        element15 = element15.trim();
	                    }
						return new EmpleaveinfoResponseBean(
							lev.get(0, String.class).trim(),
							lev.get(1, BigDecimal.class).doubleValue(),
							lev.get(2, BigDecimal.class).doubleValue(),
							lev.get(3, BigDecimal.class).doubleValue(),
							lev.get(4, BigDecimal.class).doubleValue(),
							lev.get(5, BigDecimal.class).doubleValue(),
							lev.get(6, BigDecimal.class).doubleValue(),
							lev.get(7, String.class).trim(),
							lev.get(8, String.class).trim(),
							lev.get(9, Character.class),
							lev.get(10, String.class).trim(),
							lev.get(11, BigDecimal.class).doubleValue(),
							lev.get(12, BigDecimal.class).doubleValue(),
							lev.get(13, Timestamp.class).toLocalDateTime(),
							lev.get(14, String.class).trim(),
							element15,
							lev.get(16, String.class).trim(),
							lev.get(17, String.class).trim(),
							lev.get(18, Character.class)
							);
					}
					).collect(Collectors.toList());
			employeeDetailsResponseBean.setEmpleaveinfoResponseBean(empleaveinfoResponseBean);
		}
		
		//get empfamily details
		List<Empfamily> empfamilylist = empfamilyRepository.findByEmpfamilyCK_EfamEmpcode(empcode);
		if(CollectionUtils.isNotEmpty( empfamilylist)) {
			employeeDetailsResponseBean.setEmpfamilyResponseBean(EmpfamilyEntityPojoMapper.fetchEmpfamilyEntityPojoMapper.apply(empfamilylist));
		}
		
		//get empexperience details
		List<Empexperience> empexperiencelist = empexperienceRepository.findByEmpexperienceCK_EexpEmpcode(empcode);
		if(CollectionUtils.isNotEmpty( empexperiencelist)) {
			employeeDetailsResponseBean.setEmpexperienceResponseBean(EmpexperienceEntityPojoMapper.fetchEmpexperienceEntityPojoMapper.apply(empexperiencelist));
		}
		
		//get empreference details
		List<Empreference> empreferencelist = empreferenceRepository.findByEmpreferenceCK_ErefEmpcode(empcode);
		if(CollectionUtils.isNotEmpty( empreferencelist)) {
			employeeDetailsResponseBean.setEmpreferenceResponseBean(EmpreferenceEntityPojoMapper.fetchEmpreferenceEntityPojoMapper.apply(empreferencelist));
		}
		
//		get empassest details
		List<Empassetinfo> empassetinfolist = empassetinfoRepository.findByEmpassetinfoCK_EassEmpcode(empcode);
		if(CollectionUtils.isNotEmpty( empassetinfolist)) {
			employeeDetailsResponseBean.setEmpassetinfoResponseBean(EmpassetinfoEntityPojoMapper.fetchEmpassetinfoEntityPojoMapper.apply(empassetinfolist));
		}
		
//		get empaddress details
		Address addressmail = addressRepository.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtype(empcode, AdSegment.EMPL.toString(), AdType.MAIL.toString());
		if (Objects.nonNull(addressmail)) {
			employeeDetailsResponseBean.setAddressmail(AddressMapper.AddressEntityPojoMapper.fetchAddressEntityPojoMapper.apply(new Object[] {addressmail}));
		}
		Address addressres = addressRepository.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtype(empcode, AdSegment.EMPL.toString(), AdType.RES.toString());
		if (Objects.nonNull(addressres)) {
			employeeDetailsResponseBean.setAddressres(AddressMapper.AddressEntityPojoMapper.fetchAddressEntityPojoMapper.apply(new Object[] {addressres}));
		}
		
		   File empfile = new File(CommonConstraints.INSTANCE.EMPPHOTOPATH + empcode.trim() + ".jpg");
		   if (empfile.exists() && empfile.canRead()) {
			   empphotopath = CommonConstraints.INSTANCE.EMPPHOTOPATH + empcode.trim() + ".jpg";
		    } else {
		    	empphotopath = CommonConstraints.INSTANCE.EMPPHOTOPATH + "employee.jpg";
		    } 

		empPhoto = ImageToByteArray.imagetoblob(empphotopath);
		employeeDetailsResponseBean.setEmpPhoto(empPhoto);
		
		
////		String SQuery = "select (select efor_formula from emppayformula where efor_coy = 'UNIQ' and efor_emptype = 'S' and efor_jobtype = 'P' and efor_earndedcode = 'MTHLYGROSS') as gross from v_empsalarypackage where vspk_empcode = 'WD009' and vspk_todate = '01-JAN-2050' ";
//		String SQuery = "select ('Select ' || efor_formula || ' from v_empsalarypackage where vspk_empcode = ' || '''WD009''' || ' and vspk_todate = ' || '''01-JAN-2050''') as Q from emppayformula where efor_coy = 'UNIQ' and efor_emptype = 'S' and efor_jobtype = 'P' and efor_earndedcode = 'MTHLYGROSS'";
//		Query q = entityManager.createNativeQuery(SQuery);
//		List<String> resultSet = q.getResultList();
//		
//		logger.info("MonthGross :: {}", resultSet);
//		System.out.println("Query is "+ resultSet);
//		String SMQuery = "";
//		for(String FQuery:resultSet) 
//			SMQuery = FQuery;
//		
//		Query newq = entityManager.createNativeQuery(SMQuery);
//		List<Object[]> newresultSet = newq.getResultList();
//		logger.info("MonthGrossAmount :: {}", newresultSet);
		
//		logger.info("Coy :: {}", empjobinfolist.get(0).getEjinCompany());
//		logger.info("Emptype :: {}",empjobinfolist.get(0).getEjinEmptype());
//		logger.info("Jobtype :: {}",empjobinfolist.get(0).getEjinJobtype());
//		
//		String MonthlyGrossQuery = employeeDetailsEntryEditRepository.GetMonthGrossQuery(empcode,empjobinfolist.get(0).getEjinCompany(),empjobinfolist.get(0).getEjinEmptype(),empjobinfolist.get(0).getEjinJobtype());
//		logger.info("MonthGrossQuery :: {}", MonthlyGrossQuery);
//		
//		Query newq = entityManager.createNativeQuery(MonthlyGrossQuery);
//		List<Object[]> newresultSet = newq.getResultList();
//		logger.info("MonthGrossAmount :: {}", newresultSet);
		
//		Double MonthlyGross = employeeDetailsEntryEditRepository.GetMonthGross(MonthlyGrossQuery);
//		logger.info("MonthGrossAmount :: {}", MonthlyGross);
		
		if(CollectionUtils.isNotEmpty(emppersonallist)) {
		Tuple empjobinfodetforpayformula = employeeDetailsEntryEditRepository.GetEmployeeJobinfoForFormula(empcode);
		if(Objects.nonNull(empjobinfodetforpayformula)) {
			String coy = empjobinfodetforpayformula.get(0, String.class).trim();
			char emptype = empjobinfodetforpayformula.get(1, Character.class);
			char jobtype = empjobinfodetforpayformula.get(2, Character.class);
			String formulaMthlyGross = employeeDetailsEntryEditRepository.GetFormula(coy, emptype, jobtype, "MTHLYGROSS");
			
			Query queryMthlyGross = entityManager.createNativeQuery(formulaMthlyGross);
			queryMthlyGross.setParameter("empcode", empcode.trim());
			List mthlyGrossresultSet = queryMthlyGross.getResultList();
			employeeDetailsResponseBean.setMTHLYGROSS(mthlyGrossresultSet);
			
			
			String formulaCTC = employeeDetailsEntryEditRepository.GetFormula(coy, emptype, jobtype, "CTC");
			Query queryCTC = entityManager.createNativeQuery(formulaCTC);
			queryCTC.setParameter("empcode", empcode.trim());
			List CTCresultSet = queryCTC.getResultList();
			employeeDetailsResponseBean.setCTC(CTCresultSet);
		}
		}
		
		//check if it is first salary or not if first salary then modification is allowed 
//		FirstSalaryYN
		double empsalarypaidCount = employeeDetailsEntryEditRepository.GetEmpSalarypaidCountForEmpcode(empcode);
		String firstSalaryYN = empsalarypaidCount > 0 ? "N" : "Y";
		employeeDetailsResponseBean.setFirstSalaryYN(firstSalaryYN);
		
		} else {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No record found for your Employee Code").build());
		}
		if(Objects.nonNull(employeeDetailsResponseBean)) {
			logger.info("employeeDetailsResponseBean :: {}", employeeDetailsResponseBean);
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(employeeDetailsResponseBean).build());	
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No record found for your Employee Code").build());
	}
	
	@Override
	public ResponseEntity<?> addEmplDetails(EmployeeDetailsRequestBean employeeDetailsRequestBean) {
//		get empcode
		String empcode = GetEmplCode(employeeDetailsRequestBean.getEmpjobinfoRequestBean().getCompany().trim());
		String empPFNumber = "";
		logger.info("empcode :: {}", empcode);
//		set values for personal
		employeeDetailsRequestBean.getEmppersonalRequestBean().setEffectiveupto(CommonConstraints.INSTANCE.closeDateDDMMYYYY);
		employeeDetailsRequestBean.getEmppersonalRequestBean().setEffectivefrom(employeeDetailsRequestBean.getEmpjobinfoRequestBean().getJoindate());
		employeeDetailsRequestBean.getEmppersonalRequestBean().setPhotopath(empcode.concat(".JPG"));
		employeeDetailsRequestBean.getEmppersonalRequestBean().setModule("MAINSCRADD");
		employeeDetailsRequestBean.getEmppersonalRequestBean().setEmpcode(empcode);
		logger.info("employeeDetailsRequestBean :: {}", employeeDetailsRequestBean);
		this.emppersonalRepository.save(EmppersonalEntityPojoMapper.addEmppersonalPojoEntityMapper.apply(employeeDetailsRequestBean.getEmppersonalRequestBean()));
//		set values for empjobinfo
		employeeDetailsRequestBean.getEmpjobinfoRequestBean().setEffectiveupto(CommonConstraints.INSTANCE.closeDateDDMMYYYY);
		employeeDetailsRequestBean.getEmpjobinfoRequestBean().setEffectivefrom(employeeDetailsRequestBean.getEmpjobinfoRequestBean().getJoindate());
		employeeDetailsRequestBean.getEmpjobinfoRequestBean().setEmpcode(empcode);
		employeeDetailsRequestBean.getEmpjobinfoRequestBean().setModule("MAINSCRADD");
		this.empjobinfoRepository.save(EmpjobinfoEntityPojoMapper.addEmpjobinfoPojoEntityMapper.apply(employeeDetailsRequestBean.getEmpjobinfoRequestBean()));
//		set address - Mail
		employeeDetailsRequestBean.getAddressmail().setAdsegment(AdSegment.EMPL.toString());
		employeeDetailsRequestBean.getAddressmail().setSer("02");
		employeeDetailsRequestBean.getAddressmail().setTopser("01");
		employeeDetailsRequestBean.getAddressmail().setAdtype(AdType.MAIL.toString());
		employeeDetailsRequestBean.getAddressmail().setUserid(GenericAuditContextHolder.getContext().getUserid());
		if (employeeDetailsRequestBean.getEmppersonalRequestBean().getFullname().length() > 40) {
			employeeDetailsRequestBean.getAddressmail().setFname(employeeDetailsRequestBean.getEmppersonalRequestBean().getFullname().substring(0, 40));
		} else {
			employeeDetailsRequestBean.getAddressmail().setFname(employeeDetailsRequestBean.getEmppersonalRequestBean().getFullname());
		}
		String siteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
		this.addressRepository.save(AddressMapper.addAddressPojoEntityMapping.apply(new Object[] {employeeDetailsRequestBean.getAddressmail(), siteFromDBEntity, empcode}));
//		set address - Res
		if(Objects.nonNull(employeeDetailsRequestBean.getAddressres())) {
		employeeDetailsRequestBean.getAddressres().setAdsegment(AdSegment.EMPL.toString());
		employeeDetailsRequestBean.getAddressres().setSer("02");
		employeeDetailsRequestBean.getAddressres().setTopser("02");
		employeeDetailsRequestBean.getAddressres().setAdtype(AdType.RES.toString());
		employeeDetailsRequestBean.getAddressres().setUserid(GenericAuditContextHolder.getContext().getUserid());
		if (employeeDetailsRequestBean.getEmppersonalRequestBean().getFullname().length() > 40) {
			employeeDetailsRequestBean.getAddressres().setFname(employeeDetailsRequestBean.getEmppersonalRequestBean().getFullname().substring(0, 40));
		} else {
			employeeDetailsRequestBean.getAddressres().setFname(employeeDetailsRequestBean.getEmppersonalRequestBean().getFullname());
		}
		this.addressRepository.save(AddressMapper.addAddressPojoEntityMapping.apply(new Object[] {employeeDetailsRequestBean.getAddressres(), siteFromDBEntity, empcode}));
		}
//		set assetinfo
		String module = "MAINSCRADD";
		this.empassetinfoRepository.saveAll(EmpassetinfoEntityPojoMapper.addEmpassetinfoPojoEntityMapper.apply(new Object[] {employeeDetailsRequestBean.getEmpassetinfoRequestBean(),empcode,module}));
//		set empeducation
		this.empeducationRepository.saveAll(EmpeducationEntityPojoMapper.addEmpeducationPojoEntityMapper.apply(new Object[] {employeeDetailsRequestBean.getEmpeducationRequestBean(),empcode,"MAINSCRADD"}));
//		set empfamily
		this.empfamilyRepository.saveAll(EmpfamilyEntityPojoMapper.addEmpfamilyPojoEntityMapper.apply(new Object[] {employeeDetailsRequestBean.getEmpfamilyRequestBean(),empcode,"MAINSCRADD"}));
//		set empreference
		this.empreferenceRepository.saveAll(EmpreferenceEntityPojoMapper.addEmpreferencePojoEntityMapper.apply(new Object[] {employeeDetailsRequestBean.getEmpreferenceRequestBean(),empcode,"MAINSCRADD"}));
//		set empexperience
		this.empexperienceRepository.saveAll(EmpexperienceEntityPojoMapper.addEmpexperiencePojoEntityMapper.apply(new Object[] {employeeDetailsRequestBean.getEmpexperienceRequestBean(),empcode,"MAINSCRADD"}));
//		set empsalarypackage
		this.empsalarypackageRepository.saveAll(EmpsalarypackageEntityPojoMapper.addEmpsalarypackagePojoEntityMapper.apply(new Object[] {employeeDetailsRequestBean.getEmpsalarypackageRequestBean(),empcode,"MAINSCRADD",employeeDetailsRequestBean.getEmpjobinfoRequestBean().getJoindate(),CommonConstraints.INSTANCE.closeDateDDMMYYYY}));
//		set empsalarypackage for ded
		this.empsalarypackageRepository.saveAll(EmpsalarypackageEntityPojoMapper.addEmpsalarypackagePojoEntityMapper.apply(new Object[] {employeeDetailsRequestBean.getEmpsalarypackagededRequestBean(),empcode,"MAINSCRADD",employeeDetailsRequestBean.getEmpjobinfoRequestBean().getJoindate(),CommonConstraints.INSTANCE.closeDateDDMMYYYY}));
//		set empschemeinfo
//		this.empschemeinfoRepository.saveAll(EmpschemeinfoEntityPojoMapper.addEmpschemeinfoPojoEntityMapper.apply(new Object[] {employeeDetailsRequestBean.getEmpschemeinfoRequestBean(),empcode,"MAINSCRADD",employeeDetailsRequestBean.getEmpjobinfoRequestBean().getJoindate(),CommonConstraints.INSTANCE.closeDateDDMMYYYY}));
		List <Empschemeinfo> empschemeinfo = EmpschemeinfoEntityPojoMapper.addEmpschemeinfoPojoEntityMapper.apply(new Object[] {employeeDetailsRequestBean.getEmpschemeinfoRequestBean(),empcode,"MAINSCRADD",employeeDetailsRequestBean.getEmpjobinfoRequestBean().getJoindate(),CommonConstraints.INSTANCE.closeDateDDMMYYYY});
		if(CollectionUtils.isNotEmpty(empschemeinfo)) {
			for (Empschemeinfo empschemeinfolist : empschemeinfo) {
				String coyCode, schemeCode;
				coyCode = employeeDetailsRequestBean.getEmpjobinfoRequestBean().getCompany().trim();
				schemeCode = empschemeinfolist.getEmpschemeinfoCK().getEschSchemecode().trim();
				String schemeNo = GetEmplSchemeNo(coyCode, schemeCode);
				empschemeinfolist.setEschEmpschemeno(schemeNo);
				if (schemeCode.trim().equals("PF") || schemeCode.trim().equals("PF&PENS") ) {
					empPFNumber = " PF no. is " + schemeNo;
				}
				this.empschemeinfoRepository.save(empschemeinfolist);
			}
		}
//		set leaveinfo
		this.empleaveinfoRepository.saveAll(EmpleaveinfoEntityPojoMapper.addEmpleaveinfoPojoEntityMapper.apply(new Object[] {employeeDetailsRequestBean.getEmpleaveinfoRequestBean(),empcode,"MAINSCRADD"}));
		
//		set party
		if(Objects.nonNull(employeeDetailsRequestBean.getPartyRequestBean())) {
		employeeDetailsRequestBean.getPartyRequestBean().setValidminor(CommonConstraints.INSTANCE.validMinor);
		employeeDetailsRequestBean.getPartyRequestBean().setValidparty(CommonConstraints.INSTANCE.validParty);
		employeeDetailsRequestBean.getPartyRequestBean().setPartytype(CommonConstraints.INSTANCE.employees);
		employeeDetailsRequestBean.getPartyRequestBean().setOpendate(CommonUtils.INSTANCE.stringToDateFormatter(employeeDetailsRequestBean.getEmpjobinfoRequestBean().getJoindate()));
		logger.info("Party Entity :: {}" , employeeDetailsRequestBean.getPartyRequestBean());

		this.partyRepository.save(PartyMapper.addPartyPojoEntityMapping.apply(new Object[] {employeeDetailsRequestBean.getPartyRequestBean(), siteFromDBEntity, empcode}));
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message(empcode.concat(" Added Successfully.").concat(empPFNumber)).build());

	}
	
	private String GetEmplCode(String CoyCode) {
		String returnEmpCode = "";
		String hotelPropYN = reportParametersRepository.GetHotelPropYN() ;
		int lastDigitNumberPart = 999;
		int lengthOfNumberPart = 3;
		String lastCode = "";
		if(StringUtils.equals(hotelPropYN, "N")) {
			lastDigitNumberPart = 999;
			lengthOfNumberPart = 3;
			lastCode = employeeDetailsEntryEditRepository.GetLastcode().trim();
		} else {
//			logic for hotel property is with 4 digits
			lastDigitNumberPart = 9999;
			lengthOfNumberPart = 4;
			if (CoyCode.trim().equals("AJIN")) {
				lastCode = employeeDetailsEntryEditRepository.GetLastcodeChar3().trim();
			} else {
				lastCode = employeeDetailsEntryEditRepository.GetLastcode().trim();
			}
		}
		
//		String lastCode = employeeDetailsEntryEditRepository.GetLastcode().trim();
		String prefix = employeeDetailsEntryEditRepository.GetPrefix().trim();
		String alphabetPart = lastCode.substring(0, 1);
		String numberPart = lastCode.substring(lastCode.length() - lengthOfNumberPart);
		int numberPartInCode = Integer.parseInt(numberPart);
		if (numberPartInCode == lastDigitNumberPart) {
			numberPartInCode = 1;
			int intalphabetPart = alphabetPart.charAt(0);
			alphabetPart = String.valueOf( (char) (intalphabetPart + 1));
		} else {
			numberPartInCode = numberPartInCode + 1;
		}
		numberPart = String.valueOf(numberPartInCode);
		numberPart = StringUtils.leftPad(numberPart, lengthOfNumberPart,"0");
		returnEmpCode = prefix + alphabetPart + numberPart;
		
		//update empcode and then return the empcode
		logger.info("char1 :: {}", alphabetPart + numberPart);
		if (CoyCode.trim().equals("AJIN")) {
			this.employeeDetailsEntryEditRepository.updateIncrementEmpcodeEntChar3("PAYRL", "CODE1", alphabetPart + numberPart);
		} else {
			this.employeeDetailsEntryEditRepository.updateIncrementEmpcode("PAYRL", "CODE1", alphabetPart + numberPart);
		}
		return returnEmpCode;
	}

	public ResponseEntity<?> fetchAllSalaryPackage(String empcode,Character currentAll){
		EmployeeSalDetailsResponseBean employeeSalDetailsResponseBean = new EmployeeSalDetailsResponseBean();
		if(currentAll == 'A') {
		//get salarypackage details for earnings
		List<Tuple> empsalarypackagelist = empsalarypackageRepository.findByAllEarnPackage(empcode);
		if(empsalarypackagelist.size()>0) {
			List<EmpsalarypackageEarnDedBean> empsalarypackageEarnDedBeanList = 
					empsalarypackagelist.stream().map(t -> {return new EmpsalarypackageEarnDedBean(
							t.get(0, String.class).trim(),
							t.get(1, String.class).trim(),
							t.get(2, Character.class),
							t.get(3, Character.class),
							t.get(4,  BigDecimal.class).doubleValue(),
							t.get(5, Timestamp.class).toLocalDateTime().toLocalDate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).toString(),
							t.get(6, Timestamp.class).toLocalDateTime().toLocalDate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).toString(),
							t.get(7, String.class).trim(),
							t.get(8, String.class).trim(),
							t.get(9, String.class).trim(),
							t.get(10, String.class).trim(),
							t.get(11, String.class).trim(),
							t.get(12, Timestamp.class).toLocalDateTime(),
							t.get(13, String.class).trim(),
							t.get(14, Character.class),
							t.get(15, Character.class)
							);
					}
							).collect(Collectors.toList());
			logger.info("empsalarypackage_Kalpana :: {}", empsalarypackageEarnDedBeanList);
			employeeSalDetailsResponseBean.setEmpsalarypackageResponseBean(empsalarypackageEarnDedBeanList);
		}

		//get salarypackage details for deductions
		List<Tuple> empsalarypackagededlist = empsalarypackageRepository.findByAllDedPackage(empcode);
		if(empsalarypackagededlist.size()>0) {
			List<EmpsalarypackageEarnDedBean> empsalarypackageEarnDedBeanList = 
					empsalarypackagededlist.stream().map(t -> {return new EmpsalarypackageEarnDedBean(
							t.get(0, String.class).trim(),
							t.get(1, String.class).trim(),
							t.get(2, Character.class),
							t.get(3, Character.class),
							t.get(4,  BigDecimal.class).doubleValue(),
							t.get(5, Timestamp.class).toLocalDateTime().toLocalDate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).toString(),
							t.get(6, Timestamp.class).toLocalDateTime().toLocalDate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).toString(),
							t.get(7, String.class).trim(),
							t.get(8, String.class).trim(),
							t.get(9, String.class).trim(),
							t.get(10, String.class).trim(),
							t.get(11, String.class).trim(),
							t.get(12, Timestamp.class).toLocalDateTime(),
							t.get(13, String.class).trim(),
							t.get(14, Character.class),
							t.get(15, Character.class)
							);
					}
							).collect(Collectors.toList());
			logger.info("empsalarypackage_Kalpana :: {}", empsalarypackageEarnDedBeanList);
			employeeSalDetailsResponseBean.setEmpsalarypackagededResponseBean(empsalarypackageEarnDedBeanList);
		}
		} else {
			//get salarypackage details for earnings
			List<Tuple> empsalarypackagelist = empsalarypackageRepository.findByCurrentEarnPackage(empcode);
			if(empsalarypackagelist.size()>0) {
				List<EmpsalarypackageEarnDedBean> empsalarypackageEarnDedBeanList = 
						empsalarypackagelist.stream().map(t -> {return new EmpsalarypackageEarnDedBean(
								t.get(0, String.class).trim(),
								t.get(1, String.class).trim(),
								t.get(2, Character.class),
								t.get(3, Character.class),
								t.get(4,  BigDecimal.class).doubleValue(),
								t.get(5, Timestamp.class).toLocalDateTime().toLocalDate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).toString(),
								t.get(6, Timestamp.class).toLocalDateTime().toLocalDate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).toString(),
								t.get(7, String.class).trim(),
								t.get(8, String.class).trim(),
								t.get(9, String.class).trim(),
								t.get(10, String.class).trim(),
								t.get(11, String.class).trim(),
								t.get(12, Timestamp.class).toLocalDateTime(),
								t.get(13, String.class).trim(),
								t.get(14, Character.class),
								t.get(15, Character.class)
								);
						}
								).collect(Collectors.toList());
				logger.info("empsalarypackage_Kalpana :: {}", empsalarypackageEarnDedBeanList);
				employeeSalDetailsResponseBean.setEmpsalarypackageResponseBean(empsalarypackageEarnDedBeanList);
			}

			//get salarypackage details for deductions
			List<Tuple> empsalarypackagededlist = empsalarypackageRepository.findByCurrentDedPackage(empcode);
			if(empsalarypackagededlist.size()>0) {
				List<EmpsalarypackageEarnDedBean> empsalarypackageEarnDedBeanList = 
						empsalarypackagededlist.stream().map(t -> {return new EmpsalarypackageEarnDedBean(
								t.get(0, String.class).trim(),
								t.get(1, String.class).trim(),
								t.get(2, Character.class),
								t.get(3, Character.class),
								t.get(4,  BigDecimal.class).doubleValue(),
								t.get(5, Timestamp.class).toLocalDateTime().toLocalDate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).toString(),
								t.get(6, Timestamp.class).toLocalDateTime().toLocalDate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).toString(),
								t.get(7, String.class).trim(),
								t.get(8, String.class).trim(),
								t.get(9, String.class).trim(),
								t.get(10, String.class).trim(),
								t.get(11, String.class).trim(),
								t.get(12, Timestamp.class).toLocalDateTime(),
								t.get(13, String.class).trim(),
								t.get(14, Character.class),
								t.get(15, Character.class)
								);
						}
								).collect(Collectors.toList());
				logger.info("empsalarypackage_Kalpana :: {}", empsalarypackageEarnDedBeanList);
				employeeSalDetailsResponseBean.setEmpsalarypackagededResponseBean(empsalarypackageEarnDedBeanList);
			}
		}

		if(Objects.nonNull(employeeSalDetailsResponseBean)) {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(employeeSalDetailsResponseBean).build());	
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Salary Package found for your Employee Code").build());

	}


	public ResponseEntity<?> fetchCompanySalPackage(String coycode,String closeDate){
		List<Tuple> coysalarypackagelist = employeeDetailsEntryEditRepository.fetchCompanySalPackage(coycode,CommonConstraints.INSTANCE.closeDate);
		if (coysalarypackagelist.size()>0) {
			List<CoysalarypackageBean> coysalarypackageBean =
					coysalarypackagelist.stream().map(t -> {return new CoysalarypackageBean(
							t.get(0, String.class).trim(),
							t.get(1, String.class).trim(),
							t.get(2, Character.class),
							t.get(3, Character.class)
							);
					}
							).collect(Collectors.toList());
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(coysalarypackageBean).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Salary Package found for your Company").build());
	}
	
	public ResponseEntity<?> fetchCompanySalDedPackage(String coycode,String closeDate){
		List<Tuple> coysalarypackagelist = employeeDetailsEntryEditRepository.fetchCompanySalDedPackage(coycode,CommonConstraints.INSTANCE.closeDate);
		if (coysalarypackagelist.size()>0) {
			List<CoysalarypackageBean> coysalarypackageBean =
					coysalarypackagelist.stream().map(t -> {return new CoysalarypackageBean(
							t.get(0, String.class).trim(),
							t.get(1, String.class).trim(),
							t.get(2, Character.class),
							t.get(3, Character.class)
							);
					}
							).collect(Collectors.toList());
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(coysalarypackageBean).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Salary Deduction Package found for your Company").build());
	}
	
	public ResponseEntity<?> fetchCompanySchemeDetails(String coycode,String joinpaymonth,String closeDate){
		List<Tuple> coyschemelist = employeeDetailsEntryEditRepository.fetchCompanySchemeDetails(coycode,joinpaymonth,CommonConstraints.INSTANCE.closeDate);
		if (coyschemelist.size()>0) {
			List<CoySchemeBean> coySchemeBean = 
					coyschemelist.stream().map(t -> {return new CoySchemeBean(
							t.get(0, String.class).trim(),
							t.get(1, String.class).trim(),
							t.get(2, Character.class),
							t.get(3, String.class),
							t.get(4, BigDecimal.class).doubleValue(),
							t.get(5, String.class),
							t.get(6, Character.class)
							);
						
					}).collect(Collectors.toList());
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(coySchemeBean).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Scheme Details for this Company").build());
	}
	
	public ResponseEntity<?> fetchCompanyLeaveDetails(String coycode,String joindate,String emptype){
		List<Tuple> coyleavelist = employeeDetailsEntryEditRepository.fetchCompanyLeaveDetails(coycode, joindate,  emptype);
		if(CollectionUtils.isNotEmpty(coyleavelist)) {
			String acyear = employeeDetailsEntryEditRepository.GetAcyear(joindate);
//			Double maxDaysallowed = 0.00;
			List<CoyLeaveBean> coyLeaveBeanList = coyleavelist.stream().map(t->
			{return new CoyLeaveBean(
					t.get(0,String.class).trim(),
					acyear,
					CalcLeave("L",joindate,t.get(1,BigDecimal.class).doubleValue()),
					CalcLeave("L",joindate,t.get(4,BigDecimal.class).doubleValue()),
					BigDecimal.ZERO.doubleValue(),
					BigDecimal.ZERO.doubleValue(),
					BigDecimal.ZERO.doubleValue(),
					BigDecimal.ZERO.doubleValue(),
					BigDecimal.ZERO.doubleValue(),
					BigDecimal.ZERO.doubleValue()
					);
			}).collect(Collectors.toList());
			IntStream.range(0, coyLeaveBeanList.size()).forEach(i -> {
				
			});
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(coyLeaveBeanList).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Leave Details for this Company").build());
		
	}
	
	private double CalcLeave(String FlgModule, String joinDate, Double AnnLeavethissite) {
		double LeaveEntitle = employeeDetailsEntryEditRepository.CalLeaveEntitle(joinDate, AnnLeavethissite);
		Integer intLeaveEntitle = (int)LeaveEntitle ;
		if (FlgModule == "L") {
	        if (LeaveEntitle - intLeaveEntitle < 0.25) {
            return intLeaveEntitle;
        } else if (LeaveEntitle - intLeaveEntitle > 0.75) {
            return intLeaveEntitle + 1;
        } else {
            return intLeaveEntitle + 0.5;
        }			
		} else {
			return intLeaveEntitle + 0.5;
		}
	}
	
	private String GetEmplSchemeNo(String coyCode, String schemeCode) {
		LocalDate closedate = CommonUtils.INSTANCE.closeDateInLocalDateTime().toLocalDate();
		String emplSchemeNo = this.employeeDetailsEntryEditRepository.GetSchemeNo(coyCode, schemeCode, closedate);
		emplSchemeNo = emplSchemeNo.trim();
	
		// Create an empty List<String>
	    List<String> SchemeCodeList = new ArrayList<>();
	
	    // Add elements to the list
	    SchemeCodeList.add("PF");
	    SchemeCodeList.add("PF&PENS");
		
		if (!emplSchemeNo.trim().equals(".")) {
		//increment no then send scheme no
		int numberSchemeNo = Integer.parseInt(emplSchemeNo);
		numberSchemeNo = numberSchemeNo + 1 ;
		String strSchemeNo = String.valueOf(numberSchemeNo);
		
		if (schemeCode.trim().equals("PF") || schemeCode.trim().equals("PF&PENS") ) {
			this.employeeDetailsEntryEditRepository.updateIncrementEmplSchemeCodePF(coyCode, SchemeCodeList, strSchemeNo);
		} else {
			this.employeeDetailsEntryEditRepository.updateIncrementEmplSchemeCode(coyCode, schemeCode, strSchemeNo);
			}
		}
		return emplSchemeNo;
				
	}

	@Override
	public ResponseEntity<?> updateNewEmplDetails(EmployeeDetailsRequestBean employeeDetailsRequestBean) {
		logger.info("employeeDetailsRequestBean :: {}", employeeDetailsRequestBean);
		String empcode = employeeDetailsRequestBean.getEmppersonalRequestBean().getEmpcode().trim();
//		row is deleted and inserted again since salary is not done. so modification is allowed
//		Emppersonal
//		this.employeeDetailsEntryEditRepository.deleteEmppersonal(empcode);
		logger.info("CloseDate :: {}",CommonUtils.INSTANCE.closeDateInLocalDateTime().toLocalDate());
		Emppersonal emppersonal = emppersonalRepository.GetEmppersonalDetails(empcode,CommonUtils.INSTANCE.closeDateInLocalDateTime().toLocalDate());
		employeeDetailsRequestBean.getEmppersonalRequestBean().setEffectiveupto(CommonConstraints.INSTANCE.closeDateDDMMYYYY);
		employeeDetailsRequestBean.getEmppersonalRequestBean().setEffectivefrom(employeeDetailsRequestBean.getEmpjobinfoRequestBean().getJoindate());
		employeeDetailsRequestBean.getEmppersonalRequestBean().setPhotopath(empcode.concat(".JPG"));
		employeeDetailsRequestBean.getEmppersonalRequestBean().setModule("MAINSCRMOD");
		logger.info("employeeDetailsRequestBean :: {}", employeeDetailsRequestBean);;
		this.emppersonalRepository.save(EmppersonalEntityPojoMapper.updateEmppersonalEntityPojoMapper.apply(emppersonal, employeeDetailsRequestBean.getEmppersonalRequestBean()));
//		Empjobinfo
		this.employeeDetailsEntryEditRepository.deleteEmpjobinfo(empcode);
		employeeDetailsRequestBean.getEmpjobinfoRequestBean().setEffectiveupto(CommonConstraints.INSTANCE.closeDateDDMMYYYY);
		employeeDetailsRequestBean.getEmpjobinfoRequestBean().setEffectivefrom(employeeDetailsRequestBean.getEmpjobinfoRequestBean().getJoindate());
		employeeDetailsRequestBean.getEmpjobinfoRequestBean().setEmpcode(empcode);
		employeeDetailsRequestBean.getEmpjobinfoRequestBean().setModule("MAINSCRMOD");
		this.empjobinfoRepository.save(EmpjobinfoEntityPojoMapper.addEmpjobinfoPojoEntityMapper.apply(employeeDetailsRequestBean.getEmpjobinfoRequestBean()));
//		set address - Mail
		Address addressmail = addressRepository.findByAdrAdownerAndAdrAdsegmentAndAdrAdtypeAndAdrAdser(empcode,AdSegment.EMPL.toString(),AdType.MAIL.toString(),"02");
		employeeDetailsRequestBean.getAddressmail().setAdsegment(AdSegment.EMPL.toString());
		employeeDetailsRequestBean.getAddressmail().setSer("02");
		employeeDetailsRequestBean.getAddressmail().setTopser("01");
		employeeDetailsRequestBean.getAddressmail().setAdtype(AdType.MAIL.toString());
		employeeDetailsRequestBean.getAddressmail().setUserid(GenericAuditContextHolder.getContext().getUserid());
		if (employeeDetailsRequestBean.getEmppersonalRequestBean().getFullname().length() > 40) {
			employeeDetailsRequestBean.getAddressmail().setFname(employeeDetailsRequestBean.getEmppersonalRequestBean().getFullname().substring(0, 40));
		} else {
			employeeDetailsRequestBean.getAddressmail().setFname(employeeDetailsRequestBean.getEmppersonalRequestBean().getFullname());
		}
		
		employeeDetailsRequestBean.getAddressmail().setToday(CommonConstraints.INSTANCE.closeDateDDMMYYYY);
		String siteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
		this.addressRepository.save(AddressMapper.updateAddressPojoEntityMapping.apply(addressmail, employeeDetailsRequestBean.getAddressmail())) ;
//		set address - Res
		Address addressres = addressRepository.findByAdrAdownerAndAdrAdsegmentAndAdrAdtypeAndAdrAdser(empcode,AdSegment.EMPL.toString(),AdType.RES.toString(),"02");
		if (Objects.nonNull(addressres)){
//			update
			if(Objects.nonNull(employeeDetailsRequestBean.getAddressres())) {
			employeeDetailsRequestBean.getAddressres().setAdsegment(AdSegment.EMPL.toString());
			employeeDetailsRequestBean.getAddressres().setSer("02");
			employeeDetailsRequestBean.getAddressres().setTopser("02");
			employeeDetailsRequestBean.getAddressres().setAdtype(AdType.RES.toString());
			employeeDetailsRequestBean.getAddressres().setUserid(GenericAuditContextHolder.getContext().getUserid());
			if (employeeDetailsRequestBean.getEmppersonalRequestBean().getFullname().length() > 40) {
				employeeDetailsRequestBean.getAddressres().setFname(employeeDetailsRequestBean.getEmppersonalRequestBean().getFullname().substring(0, 40));
			} else {
				employeeDetailsRequestBean.getAddressres().setFname(employeeDetailsRequestBean.getEmppersonalRequestBean().getFullname());
			}
			
			employeeDetailsRequestBean.getAddressmail().setToday(CommonConstraints.INSTANCE.closeDateDDMMYYYY);
			this.addressRepository.save(AddressMapper.updateAddressPojoEntityMapping.apply(addressres, employeeDetailsRequestBean.getAddressres()));
			} else {
//				row is there in address table and null is coming from frontend means delete the row from database
//				address data is deleted from frontend
				this.addressRepository.deleteByAdrAdownerAndAdrAdsegmentAndAdrAdtypeAndAdrAdser(empcode,AdSegment.EMPL.toString(),AdType.RES.toString(),"02");
			}
		} else {
//			add
			if(Objects.nonNull(employeeDetailsRequestBean.getAddressres())) {
			employeeDetailsRequestBean.getAddressres().setAdsegment(AdSegment.EMPL.toString());
			employeeDetailsRequestBean.getAddressres().setSer("02");
			employeeDetailsRequestBean.getAddressres().setTopser("02");
			employeeDetailsRequestBean.getAddressres().setAdtype(AdType.RES.toString());
			employeeDetailsRequestBean.getAddressres().setUserid(GenericAuditContextHolder.getContext().getUserid());
			if (employeeDetailsRequestBean.getEmppersonalRequestBean().getFullname().length() > 40) {
				employeeDetailsRequestBean.getAddressres().setFname(employeeDetailsRequestBean.getEmppersonalRequestBean().getFullname().substring(0, 40));
			} else {
				employeeDetailsRequestBean.getAddressres().setFname(employeeDetailsRequestBean.getEmppersonalRequestBean().getFullname());
			}
			
			this.addressRepository.save(AddressMapper.addAddressPojoEntityMapping.apply(new Object[] {employeeDetailsRequestBean.getAddressres(), siteFromDBEntity, empcode}));
			} 
		}
//		Empassetinfo
		this.employeeDetailsEntryEditRepository.deleteEmpassetinfo(empcode);
		this.empassetinfoRepository.saveAll(EmpassetinfoEntityPojoMapper.addEmpassetinfoPojoEntityMapper.apply(new Object[] {employeeDetailsRequestBean.getEmpassetinfoRequestBean(),empcode,"MAINSCRMOD"}));
//		Empeducation
		this.employeeDetailsEntryEditRepository.deleteEmpeducation(empcode);
		this.empeducationRepository.saveAll(EmpeducationEntityPojoMapper.addEmpeducationPojoEntityMapper.apply(new Object[] {employeeDetailsRequestBean.getEmpeducationRequestBean(),empcode,"MAINSCRMOD"}));
//		set empfamily
		this.employeeDetailsEntryEditRepository.deleteEmpfamily(empcode);
		this.empfamilyRepository.saveAll(EmpfamilyEntityPojoMapper.addEmpfamilyPojoEntityMapper.apply(new Object[] {employeeDetailsRequestBean.getEmpfamilyRequestBean(),empcode,"MAINSCRMOD"}));
//		set empreference
		this.employeeDetailsEntryEditRepository.deleteEmpreference(empcode);
		this.empreferenceRepository.saveAll(EmpreferenceEntityPojoMapper.addEmpreferencePojoEntityMapper.apply(new Object[] {employeeDetailsRequestBean.getEmpreferenceRequestBean(),empcode,"MAINSCRMOD"}));
//		set empexperience
		this.employeeDetailsEntryEditRepository.deleteEmpexperience(empcode);
		this.empexperienceRepository.saveAll(EmpexperienceEntityPojoMapper.addEmpexperiencePojoEntityMapper.apply(new Object[] {employeeDetailsRequestBean.getEmpexperienceRequestBean(),empcode,"MAINSCRMOD"}));
//		set empsalarypackage
		this.employeeDetailsEntryEditRepository.deleteEmpsalarypackageA(empcode);
		this.empsalarypackageRepository.saveAll(EmpsalarypackageEntityPojoMapper.addEmpsalarypackagePojoEntityMapper.apply(new Object[] {employeeDetailsRequestBean.getEmpsalarypackageRequestBean(),empcode,"MAINSCRMOD",employeeDetailsRequestBean.getEmpjobinfoRequestBean().getJoindate(),CommonConstraints.INSTANCE.closeDateDDMMYYYY}));
//		set empsalarypackage for ded
		this.employeeDetailsEntryEditRepository.deleteEmpsalarypackageD(empcode);
		this.empsalarypackageRepository.saveAll(EmpsalarypackageEntityPojoMapper.addEmpsalarypackagePojoEntityMapper.apply(new Object[] {employeeDetailsRequestBean.getEmpsalarypackagededRequestBean(),empcode,"MAINSCRMOD",employeeDetailsRequestBean.getEmpjobinfoRequestBean().getJoindate(),CommonConstraints.INSTANCE.closeDateDDMMYYYY}));
//		set empschemeinfo
		this.employeeDetailsEntryEditRepository.deleteEmpschemeinfo(empcode);
		this.empschemeinfoRepository.saveAll(EmpschemeinfoEntityPojoMapper.addEmpschemeinfoPojoEntityMapper.apply(new Object[] {employeeDetailsRequestBean.getEmpschemeinfoRequestBean(),empcode,"MAINSCRMOD",employeeDetailsRequestBean.getEmpjobinfoRequestBean().getJoindate(),CommonConstraints.INSTANCE.closeDateDDMMYYYY}));
//		set leaveinfo
		this.employeeDetailsEntryEditRepository.deleteEmpleaveinfo(empcode);
		this.empleaveinfoRepository.saveAll(EmpleaveinfoEntityPojoMapper.addEmpleaveinfoPojoEntityMapper.apply(new Object[] {employeeDetailsRequestBean.getEmpleaveinfoRequestBean(),empcode,"MAINSCRMOD"}));
//		set party
		Party partyEmpl = partyRepository.findByPartyCodeAndPartytype(empcode, CommonConstraints.INSTANCE.employees);
		if(Objects.nonNull(employeeDetailsRequestBean.getPartyRequestBean())) {
		employeeDetailsRequestBean.getPartyRequestBean().setValidminor(CommonConstraints.INSTANCE.validMinor);
		employeeDetailsRequestBean.getPartyRequestBean().setValidparty(CommonConstraints.INSTANCE.validParty);
		employeeDetailsRequestBean.getPartyRequestBean().setPartytype(CommonConstraints.INSTANCE.employees);
		employeeDetailsRequestBean.getPartyRequestBean().setOpendate(CommonUtils.INSTANCE.stringToDateFormatter(employeeDetailsRequestBean.getEmpjobinfoRequestBean().getJoindate()));
		employeeDetailsRequestBean.getPartyRequestBean().setToday(CommonConstraints.INSTANCE.closeDateDDMMYYYY);
		logger.info("Party Entity :: {}" , employeeDetailsRequestBean.getPartyRequestBean());
		this.partyRepository.save(PartyMapper.updatePartyEntityPojoMapper.apply(partyEmpl, employeeDetailsRequestBean.getPartyRequestBean()));
//		this.partyRepository.save(PartyMapper.addPartyPojoEntityMapping.apply(new Object[] {employeeDetailsRequestBean.getPartyRequestBean(), siteFromDBEntity, empcode}));
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message(empcode.concat(" Modified Successfully.")).build());
	}
	
	public ResponseEntity<?> updateOldEmplDetails(EmployeeDetailsRequestBean employeeDetailsRequestBean) {
//		Emppersonal
		logger.info("employeeDetailsRequestBean :: {}", employeeDetailsRequestBean);
		String empcode = employeeDetailsRequestBean.getEmppersonalRequestBean().getEmpcode().trim();
		Boolean InsertPersonal = false ,InsertParty = false;
		
	    String site = Objects.nonNull(GenericAuditContextHolder.getContext().getSite()) ? GenericAuditContextHolder.getContext().getSite() : "MUM"; 
	    String userid = Objects.nonNull(GenericAuditContextHolder.getContext().getUserid()) ? GenericAuditContextHolder.getContext().getUserid(): "KRaheja";
	    String machineName = CommonUtils.INSTANCE.getClientConfig().getMachineName();
	    String ipAddress = CommonUtils.INSTANCE.getClientConfig().getIpAddress();
	    String modifiedDate = employeeDetailsRequestBean.getModifiedDate(); //"01/01/2024"; //Get this date from employeeDetailsRequestBean which will be send by frontend
	    String lastDay = CommonUtils.INSTANCE.addDaysInString(modifiedDate, -1);  //"31-DEC-2023";
	    logger.info("lastday :: {}",lastDay);	
		Emppersonal emppersonal = emppersonalRepository.GetEmppersonalDetails(empcode,CommonUtils.INSTANCE.closeDateInLocalDateTime().toLocalDate());
		if (!emppersonal.getEperFullname().trim().equals(employeeDetailsRequestBean.getEmppersonalRequestBean().getFullname().trim())) {
			InsertPersonal = true;
			InsertParty = true;
			}
		if (!emppersonal.getEperMaritalstat().trim().equals(employeeDetailsRequestBean.getEmppersonalRequestBean().getMaritalstat().trim())) {
			InsertPersonal = true;
		}
		if (InsertPersonal == true) {
			//add new record and update the effectiveupto date in last record
//			String empcode,String lastday,String site, String userid, String machinename, String ipaddress,String closedate
			this.emppersonalRepository.updateemppersonal(empcode, lastDay, site, userid, machineName, ipAddress, CommonConstraints.INSTANCE.closeDate);
//			set values for personal
			employeeDetailsRequestBean.getEmppersonalRequestBean().setEffectiveupto(CommonConstraints.INSTANCE.closeDateDDMMYYYY);
			employeeDetailsRequestBean.getEmppersonalRequestBean().setEffectivefrom(modifiedDate);
			employeeDetailsRequestBean.getEmppersonalRequestBean().setModule("MAINSCRMOD");
			logger.info("employeeDetailsRequestBean :: {}", employeeDetailsRequestBean);
			this.emppersonalRepository.save(EmppersonalEntityPojoMapper.addEmppersonalPojoEntityMapper.apply(employeeDetailsRequestBean.getEmppersonalRequestBean()));
		} else { //update emppersonal record
			this.emppersonalRepository.save(EmppersonalEntityPojoMapper.updateEmppersonalEntityPojoMapper.apply(emppersonal, employeeDetailsRequestBean.getEmppersonalRequestBean()));
		}
//		Empjobinfo
		Boolean InsertEmpjobinfo = false;
		Empjobinfo empjobinfo = empjobinfoRepository.GetEmpjobinfoDetails(empcode,CommonUtils.INSTANCE.closeDateInLocalDateTime().toLocalDate());
		if (!empjobinfo.getEjinEmptype().trim().equals(employeeDetailsRequestBean.getEmpjobinfoRequestBean().getEmptype().trim())) {
			InsertEmpjobinfo = true;
		}
		if (!empjobinfo.getEjinJobtype().trim().equals(employeeDetailsRequestBean.getEmpjobinfoRequestBean().getJobtype().trim())) {
			InsertEmpjobinfo = true;
		}
		if (!empjobinfo.getEjinLocation().trim().equals(employeeDetailsRequestBean.getEmpjobinfoRequestBean().getLocation().trim())) {
			InsertEmpjobinfo = true;
		}
		if (!empjobinfo.getEjinWorksite().trim().equals(employeeDetailsRequestBean.getEmpjobinfoRequestBean().getWorksite().trim())) {
			InsertEmpjobinfo = true;
		}
		if (!empjobinfo.getEjinDepartment().trim().equals(employeeDetailsRequestBean.getEmpjobinfoRequestBean().getDepartment().trim())) {
			InsertEmpjobinfo = true;
		}
		if (empjobinfo.getEjinGrade()!= null) {
			if (employeeDetailsRequestBean.getEmpjobinfoRequestBean().getGrade() == null) {
				employeeDetailsRequestBean.getEmpjobinfoRequestBean().setGrade("");
			}
		if (!empjobinfo.getEjinGrade().trim().equals(employeeDetailsRequestBean.getEmpjobinfoRequestBean().getGrade().trim())) {
			InsertEmpjobinfo = true;
		}
		}
		if (!empjobinfo.getEjinDesigpost().trim().equals(employeeDetailsRequestBean.getEmpjobinfoRequestBean().getDesigpost().trim())) {
			InsertEmpjobinfo = true;
		}
		if (InsertEmpjobinfo == true) {
			//add new record and update the effectiveupto date in last record
			this.empjobinfoRepository.updateempjobinfo(empcode, lastDay, site, userid, machineName, ipAddress, CommonConstraints.INSTANCE.closeDate);
			employeeDetailsRequestBean.getEmpjobinfoRequestBean().setEffectiveupto(CommonConstraints.INSTANCE.closeDateDDMMYYYY);
			employeeDetailsRequestBean.getEmpjobinfoRequestBean().setEffectivefrom(modifiedDate);
			employeeDetailsRequestBean.getEmpjobinfoRequestBean().setModule("MAINSCRMOD");
			this.empjobinfoRepository.save(EmpjobinfoEntityPojoMapper.addEmpjobinfoPojoEntityMapper.apply(employeeDetailsRequestBean.getEmpjobinfoRequestBean()));
		} else { //update empjobinfo record
			this.empjobinfoRepository.save(EmpjobinfoEntityPojoMapper.updateEmpjobinfoEntityPojoMapper.apply(empjobinfo, employeeDetailsRequestBean.getEmpjobinfoRequestBean()));
		}
//		set address - Mail
		Address addressmail = addressRepository.findByAdrAdownerAndAdrAdsegmentAndAdrAdtypeAndAdrAdser(empcode,AdSegment.EMPL.toString(),AdType.MAIL.toString(),"02");
		employeeDetailsRequestBean.getAddressmail().setAdsegment(AdSegment.EMPL.toString());
		employeeDetailsRequestBean.getAddressmail().setSer("02");
		employeeDetailsRequestBean.getAddressmail().setTopser("01");
		employeeDetailsRequestBean.getAddressmail().setAdtype(AdType.MAIL.toString());
		employeeDetailsRequestBean.getAddressmail().setUserid(GenericAuditContextHolder.getContext().getUserid());
		if (employeeDetailsRequestBean.getEmppersonalRequestBean().getFullname().length() > 40) {
			employeeDetailsRequestBean.getAddressmail().setFname(employeeDetailsRequestBean.getEmppersonalRequestBean().getFullname().substring(0, 40));
		} else {
			employeeDetailsRequestBean.getAddressmail().setFname(employeeDetailsRequestBean.getEmppersonalRequestBean().getFullname());
		}
		
		employeeDetailsRequestBean.getAddressmail().setToday(CommonConstraints.INSTANCE.closeDateDDMMYYYY);
		String siteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
		this.addressRepository.save(AddressMapper.updateAddressPojoEntityMapping.apply(addressmail, employeeDetailsRequestBean.getAddressmail())) ;
//		set address - Res
		Address addressres = addressRepository.findByAdrAdownerAndAdrAdsegmentAndAdrAdtypeAndAdrAdser(empcode,AdSegment.EMPL.toString(),AdType.RES.toString(),"02");
		if (Objects.nonNull(addressres)){
//			update
			if(Objects.nonNull(employeeDetailsRequestBean.getAddressres())) {
			employeeDetailsRequestBean.getAddressres().setAdsegment(AdSegment.EMPL.toString());
			employeeDetailsRequestBean.getAddressres().setSer("02");
			employeeDetailsRequestBean.getAddressres().setTopser("02");
			employeeDetailsRequestBean.getAddressres().setAdtype(AdType.RES.toString());
			employeeDetailsRequestBean.getAddressres().setUserid(GenericAuditContextHolder.getContext().getUserid());
			if (employeeDetailsRequestBean.getEmppersonalRequestBean().getFullname().length() > 40) {
				employeeDetailsRequestBean.getAddressres().setFname(employeeDetailsRequestBean.getEmppersonalRequestBean().getFullname().substring(0, 40));
			} else {
				employeeDetailsRequestBean.getAddressres().setFname(employeeDetailsRequestBean.getEmppersonalRequestBean().getFullname());
			}
			
			employeeDetailsRequestBean.getAddressmail().setToday(CommonConstraints.INSTANCE.closeDateDDMMYYYY);
			this.addressRepository.save(AddressMapper.updateAddressPojoEntityMapping.apply(addressres, employeeDetailsRequestBean.getAddressres()));
			} else {
//				row is there in address table and null is coming from frontend means delete the row from database
//				address data is deleted from frontend
				this.addressRepository.deleteByAdrAdownerAndAdrAdsegmentAndAdrAdtypeAndAdrAdser(empcode,AdSegment.EMPL.toString(),AdType.RES.toString(),"02");
			}
		} else {
//			add
			if(Objects.nonNull(employeeDetailsRequestBean.getAddressres())) {
			employeeDetailsRequestBean.getAddressres().setAdsegment(AdSegment.EMPL.toString());
			employeeDetailsRequestBean.getAddressres().setSer("02");
			employeeDetailsRequestBean.getAddressres().setTopser("02");
			employeeDetailsRequestBean.getAddressres().setAdtype(AdType.RES.toString());
			employeeDetailsRequestBean.getAddressres().setUserid(GenericAuditContextHolder.getContext().getUserid());
			if (employeeDetailsRequestBean.getEmppersonalRequestBean().getFullname().length() > 40) {
				employeeDetailsRequestBean.getAddressres().setFname(employeeDetailsRequestBean.getEmppersonalRequestBean().getFullname().substring(0, 40));
			} else {
				employeeDetailsRequestBean.getAddressres().setFname(employeeDetailsRequestBean.getEmppersonalRequestBean().getFullname());
			}
			
			this.addressRepository.save(AddressMapper.addAddressPojoEntityMapping.apply(new Object[] {employeeDetailsRequestBean.getAddressres(), siteFromDBEntity, empcode}));
			} 
		}
//		Empassetinfo
		this.employeeDetailsEntryEditRepository.deleteEmpassetinfo(empcode);
		this.empassetinfoRepository.saveAll(EmpassetinfoEntityPojoMapper.addEmpassetinfoPojoEntityMapper.apply(new Object[] {employeeDetailsRequestBean.getEmpassetinfoRequestBean(),empcode,"MAINSCRMOD"}));
//		Empeducation
		this.employeeDetailsEntryEditRepository.deleteEmpeducation(empcode);
		this.empeducationRepository.saveAll(EmpeducationEntityPojoMapper.addEmpeducationPojoEntityMapper.apply(new Object[] {employeeDetailsRequestBean.getEmpeducationRequestBean(),empcode,"MAINSCRMOD"}));
//		set empfamily
		this.employeeDetailsEntryEditRepository.deleteEmpfamily(empcode);
		this.empfamilyRepository.saveAll(EmpfamilyEntityPojoMapper.addEmpfamilyPojoEntityMapper.apply(new Object[] {employeeDetailsRequestBean.getEmpfamilyRequestBean(),empcode,"MAINSCRMOD"}));
//		set empreference
		this.employeeDetailsEntryEditRepository.deleteEmpreference(empcode);
		this.empreferenceRepository.saveAll(EmpreferenceEntityPojoMapper.addEmpreferencePojoEntityMapper.apply(new Object[] {employeeDetailsRequestBean.getEmpreferenceRequestBean(),empcode,"MAINSCRMOD"}));
//		set empexperience
		this.employeeDetailsEntryEditRepository.deleteEmpexperience(empcode);
		this.empexperienceRepository.saveAll(EmpexperienceEntityPojoMapper.addEmpexperiencePojoEntityMapper.apply(new Object[] {employeeDetailsRequestBean.getEmpexperienceRequestBean(),empcode,"MAINSCRMOD"}));
//		set empleaveinfo
		if (Objects.nonNull(employeeDetailsRequestBean.getEmpleaveinfoRequestBean())){
			List<EmpleaveinfoRequestBean> empleaveinfoRequestBean = employeeDetailsRequestBean.getEmpleaveinfoRequestBean();
			logger.info("EmpleaveinfoRequestBean :: {}", empleaveinfoRequestBean);
			Integer counter = 0;
			String lEmpcode; 
			String lLeavecode; 
			String lAcyear;
			if (CollectionUtils.isNotEmpty(empleaveinfoRequestBean))
				for (EmpleaveinfoRequestBean empleaveinfo : empleaveinfoRequestBean) {
					if (empleaveinfoRequestBean.get(counter).getIsUpdate().equals(true)) {
						lEmpcode = empleaveinfoRequestBean.get(counter).getEmpcode(); 
						lLeavecode = empleaveinfoRequestBean.get(counter).getLeavecode(); 
						lAcyear = empleaveinfoRequestBean.get(counter).getAcyear();						
						Empleaveinfo lempleaveinfo = this.empleaveinfoRepository.findByEmpleaveinfoCK_ElinEmpcodeAndEmpleaveinfoCK_ElinLeavecodeAndEmpleaveinfoCK_ElinAcyear(lEmpcode, lLeavecode, lAcyear);
						this.empleaveinfoRepository.save(EmpleaveinfoEntityPojoMapper.updateEmpleaveinfoEntityPojoMapper.apply(lempleaveinfo,empleaveinfo))	;
						}
					counter++;
				}
		}
//		Package, scheme, and party work pending
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message(empcode.concat(" Modified Successfully.")).build());
	}
}
