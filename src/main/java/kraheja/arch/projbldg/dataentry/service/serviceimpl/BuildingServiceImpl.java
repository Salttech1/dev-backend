package kraheja.arch.projbldg.dataentry.service.serviceimpl;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.arch.projbldg.dataentry.bean.request.BuildingRequestBean;
import kraheja.arch.projbldg.dataentry.entity.Bldgmap;
import kraheja.arch.projbldg.dataentry.entity.Building;
import kraheja.arch.projbldg.dataentry.entity.Mailinfo;
import kraheja.arch.projbldg.dataentry.mappers.BldgmapEntityPojoMapper;
import kraheja.arch.projbldg.dataentry.mappers.BuildingEntityPojoMapper;
import kraheja.arch.projbldg.dataentry.mappers.MailinfoEntityPojoMapper;
import kraheja.arch.projbldg.dataentry.repository.BldgmapRepository;
import kraheja.arch.projbldg.dataentry.repository.BuildingRepository;
import kraheja.arch.projbldg.dataentry.repository.MailinfoRepository;
import kraheja.arch.projbldg.dataentry.service.BuildingService;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.Address;
import kraheja.commons.entity.Minors;
import kraheja.commons.enums.AdSegment;
import kraheja.commons.enums.AdType;
import kraheja.commons.enums.MinType;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.mappers.pojoentity.AddressMapper;
import kraheja.commons.mappers.pojoentity.MinorsEntityPojoMapper;
import kraheja.commons.repository.AddressRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.repository.MinorsRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;
import kraheja.sales.bookings.dataentry.mappers.FlatsEntityPojoMapper;
import kraheja.sales.entity.Flats;
import kraheja.sales.repository.FlatsRepository;

@Service
@Transactional
public class BuildingServiceImpl implements BuildingService {

	private static final Logger logger = LoggerFactory.getLogger(BuildingServiceImpl.class);

	@Autowired
	private BuildingRepository buildingRepository;

	@Autowired
	private  AddressRepository addressRepository;

	@Autowired
	private  MinorsRepository minorsRepository;

	@Autowired
	private  EntityRepository entityRepository;
	
	@Autowired
	private  BldgmapRepository bldgmapRepository;
	
	@Autowired
	private  MailinfoRepository mailinfoRepository;
	
	@Autowired
	private  FlatsRepository flatsRepository;
	
	@PersistenceContext
	private  EntityManager entityManager;

	@Override
	public ResponseEntity<?> fetchBuildingByCode(String  code) {
		Building buildingEntity = this.buildingRepository.findByBuildingCK_BldgCode(code);
		if (buildingEntity != null) {
			Minors minorsEntity = this.minorsRepository.findByMinorsCK_MinMinorscodeAndMinorsCK_MinMinorstypeAndMinorsCK_MinClosedate(code, MinType.B.toString(), CommonUtils.INSTANCE.closeDateInLocalDateTime().toLocalDate());
			logger.info("Minors :: {}", minorsEntity);

			Address addressEntity = this.addressRepository.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtypeAndAddressCK_AdrAdser(
					code, AdSegment.BLDG.toString(), AdType.LOC.toString(),
					CommonConstraints.INSTANCE.adrAdserZero);
			
			Bldgmap bldgmapEntity = this.bldgmapRepository.findByBldgmapCK_BmapEbldgcodeAndBldgmapCK_BmapAbldgcode(code, code);
			logger.info("bldgEntity :: {}", bldgmapEntity);
			
			Mailinfo mailinfoEntity = this.mailinfoRepository.findByMailinfoCK_MinfBldgcode(code);
			logger.info("Mailinfo :: {}", mailinfoEntity);
			
			List<Flats> flatsEntityList = this.flatsRepository.findByFlatsCK_FlatBldgcode(code);
			logger.info("Flats :: {}", flatsEntityList);
			
			return ResponseEntity.ok(ServiceResponseBean.builder()
									.data(BuildingEntityPojoMapper.fetchBuildingEntityPojoMapper
									.apply(new Object[] { Arrays.asList(buildingEntity), addressEntity, minorsEntity ,bldgmapEntity, mailinfoEntity, flatsEntityList}))
									.status(Boolean.TRUE).build());
			}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No record found for your selections in Building").build());
	}

	@Override
	public ResponseEntity<?> addBuilding(BuildingRequestBean buildingRequestBean) {
		String code = buildingRequestBean.getCode();
		Building buildingEntity = this.buildingRepository.findByBuildingCK_BldgCode(code);
		logger.info("mailinfoRequestBean.getCoy() {}", buildingRequestBean.getMailinfoRequestBean().getCoy());
		if(buildingRequestBean.getMailinfoRequestBean().getCoy().equals(""))
		{
			logger.info("coy is empty");
		}
		if(Objects.isNull(buildingEntity)) {
		
		String siteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);

		this.buildingRepository.save(BuildingEntityPojoMapper.addBuildingEntityPojoMapper.apply(new Object[] {buildingRequestBean,siteFromDBEntity,code}));

		buildingRequestBean.getAddressRequestBean().setAdsegment(AdSegment.BLDG.toString());
		buildingRequestBean.getAddressRequestBean().setSer(CommonConstraints.INSTANCE.adrAdserZero);
		buildingRequestBean.getAddressRequestBean().setAdtype(AdType.LOC.toString());
		buildingRequestBean.getAddressRequestBean().setTopser(CommonConstraints.INSTANCE.adrAdser);

		this.addressRepository.save(AddressMapper.addAddressPojoEntityMapping.apply(new Object[] {buildingRequestBean.getAddressRequestBean(), siteFromDBEntity, code}));

		buildingRequestBean.getMinorsRequestBean().setMinorstype(MinType.B.toString());
		this.minorsRepository.save(MinorsEntityPojoMapper.addMinorsEntityPojoMapper.apply(new Object[] {buildingRequestBean.getMinorsRequestBean(), siteFromDBEntity, code}));

		this.bldgmapRepository.save(BldgmapEntityPojoMapper.addBldgmapEntityPojoMapper.apply(new Object[] {buildingRequestBean.getBldgmapRequestBean(), siteFromDBEntity, code}));
		
//		buildingRequestBean.getMailinfoRequestBean().setCoy(buildingRequestBean.getCoy());
//		buildingRequestBean.getMailinfoRequestBean().setCity(buildingRequestBean.getCity());
//		buildingRequestBean.getMailinfoRequestBean().setTownship(buildingRequestBean.getTownship());
//		buildingRequestBean.getMailinfoRequestBean().setProject(buildingRequestBean.getProject());
//		buildingRequestBean.getMailinfoRequestBean().setBldgcode(code);
//		buildingRequestBean.getMailinfoRequestBean().setPossdate(buildingRequestBean.getPossdate().toString());
		this.mailinfoRepository.save(MailinfoEntityPojoMapper.addMailinfoEntityPojoMapper.apply(new Object[] {buildingRequestBean.getMailinfoRequestBean(), siteFromDBEntity, code}));

		buildingRequestBean.getFlatsRequestBeanList().stream().map(flatsRequestBean ->{
			this.flatsRepository.save(FlatsEntityPojoMapper.addFlatsEntityPojoMapper.apply(new Object[] {flatsRequestBean, siteFromDBEntity, code}));
			return flatsRequestBean;
		} ).collect(Collectors.toList());		

		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Data Added Successfully").build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(code.concat(" Already Exist")).build());

	}

	@Override
	public ResponseEntity<?> updateBuilding(BuildingRequestBean buildingRequestBean) {
		String code = buildingRequestBean.getCode();
		Building buildingEntity = this.buildingRepository.findByBuildingCK_BldgCode(code);
		logger.info("buildingEntity",buildingEntity);
		
		if(Objects.nonNull(buildingEntity)) {
//
			String SiteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
//														
			Minors minorsEntity = this.minorsRepository.findByMinorsCK_MinMinorscodeAndMinorsCK_MinMinorstypeAndMinorsCK_MinClosedate(code, MinType.B.toString(), CommonUtils.INSTANCE.closeDateInLocalDateTime().toLocalDate());

			Address addressEntity = this.addressRepository.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtypeAndAddressCK_AdrAdser(
					code, AdSegment.BLDG.toString(), AdType.LOC.toString(),
					CommonConstraints.INSTANCE.adrAdserZero);
			
			Bldgmap bldgmapEntity = this.bldgmapRepository.findByBldgmapCK_BmapEbldgcodeAndBldgmapCK_BmapAbldgcode(code, code);
			
			Mailinfo mailinfoEntity = this.mailinfoRepository.findByMailinfoCK_MinfBldgcode(code);
			
			List<Flats> flatsEntityList = this.flatsRepository.findByFlatsCK_FlatBldgcode(code);

			if(Objects.nonNull(buildingRequestBean))
				this.buildingRepository.save(BuildingEntityPojoMapper.updateBuildingEntityPojoMapper.apply(buildingEntity, buildingRequestBean));
			if(Objects.nonNull(buildingRequestBean.getAddressRequestBean())) {
				this.addressRepository.save(AddressMapper.updateAddressPojoEntityMapping.apply(addressEntity, buildingRequestBean.getAddressRequestBean()));
			}
			if(Objects.nonNull(buildingRequestBean.getMinorsRequestBean())) {
				this.minorsRepository.save(MinorsEntityPojoMapper.updateMinorsEntityPojoMapper.apply(minorsEntity, buildingRequestBean.getMinorsRequestBean()));
			}
			if(Objects.nonNull(buildingRequestBean.getBldgmapRequestBean())) {
				this.bldgmapRepository.save(BldgmapEntityPojoMapper.updateBldgmapEntityPojoMapper.apply(bldgmapEntity, buildingRequestBean.getBldgmapRequestBean()));
			}
			if(Objects.nonNull(buildingRequestBean.getMailinfoRequestBean())) {
				this.mailinfoRepository.save(MailinfoEntityPojoMapper.updateMailinfoEntityPojoMapper.apply(mailinfoEntity, buildingRequestBean.getMailinfoRequestBean()));
			}

			if(Objects.nonNull(buildingRequestBean.getFlatsRequestBeanList())) {
				//NS 13.02.203 commenting already written code by third developer as it failed to match with requirement of our project
//	 			buildingRequestBean.getFlatsRequestBeanList().stream().map(flatsRequestBean ->{
//	 				flatsEntityList.stream().filter(f -> 
//	 				f.getFlatsCK().getFlatBldgcode().equals(flatsRequestBean.getBldgcode()) && 
//	 				f.getFlatsCK().getFlatWing().equals(flatsRequestBean.getWing()) && 
//	 				f.getFlatsCK().getFlatFlatnum().equals(flatsRequestBean.getFlatnum()))
//	 				.map(flatsEntity -> {
//	 					this.flatsRepository.save(FlatsEntityPojoMapper.updateFlatsEntityPojoMapper.apply(flatsEntity, flatsRequestBean));
//	 					return flatsEntity;
//	 				})
//	 				.collect(Collectors.toList());
//	 				return flatsRequestBean;
//				}).collect(Collectors.toList());
				
				//NS 13.02.2023 Start // inserting logic that can handle a situation of wing with blank string that matches our requirement.

				
				buildingRequestBean.getFlatsRequestBeanList().stream().map(flat -> {
					//NS 21.02.2023 determining operation for flat grids records Insert or Update.
					if(Objects.nonNull(flat.getDboperation())) 
					{
						if(flat.getDboperation().equals("I"))
						{
							this.flatsRepository.save(FlatsEntityPojoMapper.addFlatsEntityPojoMapper.apply(new Object[] {flat, GenericAuditContextHolder.getContext().getSite(), buildingRequestBean.getCode()}));
							//return flat;
						}
						else if(flat.getDboperation().equals("U"))
						{
//							Query updateQuery = entityManager.createNativeQuery("update flats set FLAT_WING=:FLAT_WING, FLAT_FLOOR=:FLAT_FLOOR, FLAT_FLATNUM=:FLAT_FLATNUM, FLAT_ACCOMTYPE=:FLAT_ACCOMTYPE, FLAT_BUNITAREA=:FLAT_BUNITAREA, FLAT_BPARKAREA=:FLAT_BPARKAREA, FLAT_BTERAAREA=:FLAT_BTERAAREA, FLAT_BAMENAREA=:FLAT_BAMENAREA, FLAT_CUNITAREA=:FLAT_CUNITAREA, FLAT_CPARKAREA=:FLAT_CPARKAREA, FLAT_CTERAAREA=:FLAT_CTERAAREA, FLAT_CAMENAREA=:FLAT_CAMENAREA, flat_curera=:flat_curera, flat_enclbalcrera=:flat_enclbalcrera, FLAT_SITE=:FLAT_SITE, FLAT_USERID=:FLAT_USERID, FLAT_TODAY=:FLAT_TODAY, FLAT_COY=:FLAT_COY, FLAT_CONFIG=:FLAT_CONFIG, FLAT_OWNERID=:FLAT_OWNERID, FLAT_OCCUPDATE=:FLAT_OCCUPDATE where  trim(FLAT_FLATNUM)=:OLD_FLAT_FLATNUM and trim(FLAT_BLDGCODE)=:FLAT_BLDGCODE"); // 
							Query updateQuery = entityManager.createNativeQuery("update flats set FLAT_WING=:FLAT_WING, FLAT_FLOOR=:FLAT_FLOOR, FLAT_FLATNUM=:FLAT_FLATNUM, FLAT_ACCOMTYPE=:FLAT_ACCOMTYPE, FLAT_BUNITAREA=:FLAT_BUNITAREA, FLAT_BPARKAREA=:FLAT_BPARKAREA, FLAT_BTERAAREA=:FLAT_BTERAAREA, FLAT_BAMENAREA=:FLAT_BAMENAREA, FLAT_CUNITAREA=:FLAT_CUNITAREA, FLAT_CPARKAREA=:FLAT_CPARKAREA, FLAT_CTERAAREA=:FLAT_CTERAAREA, FLAT_CAMENAREA=:FLAT_CAMENAREA, flat_curera=:flat_curera, flat_enclbalcrera=:flat_enclbalcrera, FLAT_SITE=:FLAT_SITE, FLAT_USERID=:FLAT_USERID, FLAT_TODAY=:FLAT_TODAY, FLAT_COY=:FLAT_COY, FLAT_CONFIG=:FLAT_CONFIG, FLAT_OWNERID=:FLAT_OWNERID, FLAT_OCCUPDATE=:FLAT_OCCUPDATE where  trim(FLAT_FLATNUM)=:OLD_FLAT_FLATNUM and FLAT_WING=:OLD_FLAT_WING and trim(FLAT_BLDGCODE)=:FLAT_BLDGCODE");
							String flatWing="";
							if(Objects.nonNull(flat.getWing())) //NS 17.02.2023
							{
								updateQuery.setParameter("FLAT_WING", flat.getWing());//NS 17.02.2023
								flatWing = flat.getWing();
								logger.info("flat wing  {}", flat.getWing());
							}
							else
							{
								String tempWing = "  ";
								updateQuery.setParameter("FLAT_WING", tempWing);//inserts the blank space if the request contains the blank string.
								flatWing = tempWing;
								logger.info("flat wing  {}", flatWing);
							}
							
							if(Objects.nonNull(flat.getOldwing())) //NS 17.02.2023
							{
								updateQuery.setParameter("OLD_FLAT_WING", flat.getOldwing()); //NS 17.02.2023
								logger.info("old flat wing  {}", flat.getOldwing());
							}
							else
							{
								String tempWing = "  ";
								updateQuery.setParameter("OLD_FLAT_WING", tempWing);//inserts the blank space if the request contains the blank string.
								logger.info("old flat wing  {}", tempWing);
							}
																					
							DecimalFormat df_obj = new DecimalFormat("#.###");
							
							if(Objects.nonNull(flat.getFloor())) //NS 29.03.2023
							{
								
								updateQuery.setParameter("FLAT_FLOOR", flat.getFloor().trim()); //NS 29.03.2023
								logger.info("floor  {}", flat.getFloor());
							}
							else
							{
								String tempFloor = "  ";
								updateQuery.setParameter("FLAT_FLOOR", tempFloor);//inserts the blank space if the request contains the blank string.
								logger.info("floor  {}", tempFloor);
							}
							
							updateQuery.setParameter("FLAT_FLATNUM", flat.getFlatnum());
							updateQuery.setParameter("OLD_FLAT_FLATNUM", flat.getOldflatnum()); //NS 17.02.2023
							logger.info("old flat number {}", flat.getOldflatnum());
							updateQuery.setParameter("FLAT_BLDGCODE", flat.getBldgcode());
							updateQuery.setParameter("FLAT_ACCOMTYPE",  flat.getAccomtype().trim());
							updateQuery.setParameter("FLAT_BUNITAREA", Objects.nonNull(flat.getBunitarea()) ? df_obj.format(flat.getBunitarea()): null);//round to 3
							updateQuery.setParameter("FLAT_BPARKAREA", df_obj.format(flat.getBparkarea()));//round to 3
							updateQuery.setParameter("FLAT_BTERAAREA", df_obj.format(flat.getBteraarea()));//round to 3
							updateQuery.setParameter("FLAT_BAMENAREA", df_obj.format(flat.getBamenarea()));//round to 3
							updateQuery.setParameter("FLAT_CUNITAREA", df_obj.format(flat.getCunitarea()));//round to 3
							updateQuery.setParameter("FLAT_CPARKAREA", df_obj.format(flat.getCparkarea()));//round to 3
							updateQuery.setParameter("FLAT_CTERAAREA", df_obj.format(flat.getCteraarea()));//round to 3
							updateQuery.setParameter("FLAT_CAMENAREA", df_obj.format(flat.getCamenarea()));//round to 3
							//updateQuery.setParameter("flat_curera", Objects.nonNull(flat.getCurera()) ?  df_obj.format(flat.getCurera()) : BigInteger.ZERO.doubleValue() );//round to 3
							//updateQuery.setParameter("flat_enclbalcrera",Objects.nonNull(flat.getEnclbalcrera()) ?    df_obj.format(flat.getEnclbalcrera()) : BigInteger.ZERO.doubleValue());//round to 3
							//logger.info("curera {}", flat.getCurera());
							//updateQuery.setParameter("flat_enclbalcrera", df_obj.format(flat.getEnclbalcrera()));//round to 3
							if(Objects.nonNull(flat.getCurera())) //NS 31.03.2023
							{
								updateQuery.setParameter("flat_curera", df_obj.format(flat.getCurera()));//round to 3
							}
							else
							{
								updateQuery.setParameter("flat_curera",  BigInteger.ZERO.doubleValue());
							}
							
							if(Objects.nonNull(flat.getEnclbalcrera())) //NS 31.03.2023
							{
								updateQuery.setParameter("flat_enclbalcrera", df_obj.format(flat.getEnclbalcrera()));//round to 3
							}
							else
							{
								updateQuery.setParameter("flat_enclbalcrera",  BigInteger.ZERO.doubleValue());
							}
							updateQuery.setParameter("FLAT_SITE", SiteFromDBEntity);
							updateQuery.setParameter("FLAT_USERID", GenericAuditContextHolder.getContext().getUserid());
							updateQuery.setParameter("FLAT_TODAY", LocalDateTime.now());
							updateQuery.setParameter("FLAT_COY", flat.getCoy().trim());
							updateQuery.setParameter("FLAT_CONFIG", Objects.nonNull(flat.getConfig()) ? flat.getConfig().trim() : CommonConstraints.INSTANCE.BLANK_STRING);
							if(flatWing.equals("  "))
							{
							updateQuery.setParameter("FLAT_OWNERID", code+" "+flat.getFlatnum().trim());
							}
							else
							{
							updateQuery.setParameter("FLAT_OWNERID", code+flatWing+flat.getFlatnum());
							}
							updateQuery.setParameter("FLAT_OCCUPDATE", Objects.nonNull(flat.getOccupdate()) ? flat.getOccupdate() : "");					
							Integer rowCount = updateQuery.executeUpdate();
							logger.info("Updated Row count :: {}", rowCount);
							//return flat;
						}
						
					}
					return flat;
						
					

				}).collect(Collectors.toList());
				//NS 13.02.2023 End 
			
			}

			 return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Updated Successfully").build());
		} else {
//			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
		}
//	}
//
//
//
return null;
	}

	@Override
	public ResponseEntity<?> checkCodeExists(String code) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ResponseEntity<?> fetchProjectCompanyByCode(String code){ //NS 16.03.2023
		String coy_name = this.buildingRepository.findProjectCompanyByCode(code);
		if(Objects.nonNull(coy_name)) {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(coy_name).build());
		}
		 return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).build());
	}
}
