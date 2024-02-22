package kraheja.arch.projbldg.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import kraheja.arch.projbldg.dataentry.bean.request.BuildingRequestBean;
import kraheja.arch.projbldg.dataentry.bean.response.BuildingResponseBean;
import kraheja.arch.projbldg.dataentry.bean.response.BuildingResponseBean.BuildingResponseBeanBuilder;
import kraheja.arch.projbldg.dataentry.entity.Bldgmap;
import kraheja.arch.projbldg.dataentry.entity.Building;
import kraheja.arch.projbldg.dataentry.entity.BuildingCK;
import kraheja.arch.projbldg.dataentry.entity.Mailinfo;
import kraheja.commons.entity.Address;
import kraheja.commons.entity.Minors;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.mappers.pojoentity.AddressMapper.AddressEntityPojoMapper;
import kraheja.commons.mappers.pojoentity.MinorsEntityPojoMapper;
import kraheja.commons.utils.CommonConstraints;
import kraheja.sales.bean.response.FlatsResponseBean;
import kraheja.sales.bookings.dataentry.mappers.FlatsEntityPojoMapper;
import kraheja.sales.entity.Flats;

public interface BuildingEntityPojoMapper {
	@SuppressWarnings("unchecked")
	public static Function<Object[], List<BuildingResponseBean>> fetchBuildingEntityPojoMapper = objectArray -> {
		BuildingResponseBeanBuilder buildingBeanBuilder = BuildingResponseBean.builder();
		List<Building> buildingEntityList = (List<Building>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
						: null);
		Address addressEntity = (Address) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
				? objectArray[BigInteger.ONE.intValue()]
						: null);

		Minors minorsEntity = (Minors) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.TWO])
				? objectArray[CommonConstraints.INSTANCE.TWO]
						: null);

		Bldgmap bldgEntity = (Bldgmap) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.THREE])
				? objectArray[CommonConstraints.INSTANCE.THREE]
						: null);

		Mailinfo mailinfoEntity = (Mailinfo) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.FOUR])
				? objectArray[CommonConstraints.INSTANCE.FOUR]
						: null);

		List<Flats> flatsEntityList = (List<Flats>) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.FIVE])
				? objectArray[CommonConstraints.INSTANCE.FIVE]
						: null);

		List<FlatsResponseBean> flatsResponseBeanList = new ArrayList();
		return buildingEntityList.stream().map(buildingEntity -> {
			buildingBeanBuilder.code(buildingEntity.getBuildingCK().getBldgCode())
			.acclearyn(buildingEntity.getBldgAcclearyn())
			.adminrate(buildingEntity.getBldgAdminrate())
			.altbldg(buildingEntity.getBldgAltbldg())
			.amencoy(buildingEntity.getBldgAmencoy())
			.areaarch(buildingEntity.getBldgAreaarch())
			.areaengg(buildingEntity.getBldgAreaengg())
			.areasales(buildingEntity.getBldgAreasales())
			.bhk(buildingEntity.getBldgBhk())
			.bldgnum(buildingEntity.getBldgBldgnum())
			.bldgtype(buildingEntity.getBldgBldgtype())
			.ccdate(Objects.nonNull(buildingEntity.getBldgCcdate()) ? buildingEntity.getBldgCcdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.city(buildingEntity.getBldgCity())
			.closedate(Objects.nonNull(buildingEntity.getBldgClosedate()) ? buildingEntity.getBldgClosedate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.collrecdwobj(buildingEntity.getBldgCollrecdwobj())
			.complex(buildingEntity.getBldgComplex())
			.concoy(buildingEntity.getBldgConcoy())
			.coy(buildingEntity.getBldgCoy())
			.coy4(buildingEntity.getBldgCoy4())
			.debsocyn(buildingEntity.getBldgDebsocyn())
			.ea(buildingEntity.getBldgEa())
			.elecsupp(buildingEntity.getBldgElecsupp())
			.eng_group(buildingEntity.getBldgEng_Group())
			.flatssold(buildingEntity.getBldgFlatssold())
			.garagecoy(buildingEntity.getBldgGaragecoy())
			.ho2sales(Objects.nonNull(buildingEntity.getBldgHo2sales()) ? buildingEntity.getBldgHo2sales().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.icsaleyn(buildingEntity.getBldgIcsaleyn())
			.infrayn(buildingEntity.getBldgInfrayn())
			.intbrokcoy(buildingEntity.getBldgIntbrokcoy())
			.intrate(buildingEntity.getBldgIntrate())
			.lastyrcomp(buildingEntity.getBldgLastyrcomp())
			.lastyrinc(buildingEntity.getBldgLastyrinc())
			.lastyrmat(buildingEntity.getBldgLastyrmat())
			.lastyrwork(buildingEntity.getBldgLastyrwork())
			.leasingcoy(buildingEntity.getBldgLeasingcoy())
			.mainbldg(buildingEntity.getBldgMainbldg())
			.maintcoy(buildingEntity.getBldgMaintcoy())
			.maintrate(buildingEntity.getBldgMaintrate())
			.misbldg(buildingEntity.getBldgMisbldg())
			.misproject(buildingEntity.getBldgMisproject())
			.modagdoc(buildingEntity.getBldgModagdoc())
			.name(buildingEntity.getBldgName())
			.occdate(Objects.nonNull(buildingEntity.getBldgOccdate()) ? buildingEntity.getBldgOccdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null) 
			.opendate(Objects.nonNull(buildingEntity.getBldgOpendate()) ? buildingEntity.getBldgOpendate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.opt2engg(buildingEntity.getBldgOpt2engg())
			.opt2sales(buildingEntity.getBldgOpt2sales())
			//.origsite(buildingEntity.getBldgOrigsite())
			.origsite(GenericAuditContextHolder.getContext().getSite())//NS 01.02.2023
			.outformat(buildingEntity.getBldgOutformat())
			.parentbldg(buildingEntity.getBldgParentbldg())
			.paycoy(buildingEntity.getBldgPaycoy())
			.possdate(Objects.nonNull(buildingEntity.getBldgPossdate()) ? buildingEntity.getBldgPossdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.progstat(buildingEntity.getBldgProgstat())
			.project(buildingEntity.getBldgProject())
			.prop(buildingEntity.getBldgProp())
			.propbucaarea(buildingEntity.getBldgPropbucaarea())
			.property(buildingEntity.getBldgProperty())
			.region(buildingEntity.getBldgRegion())
			.remark(buildingEntity.getBldgRemark())
			.salesadline1(buildingEntity.getBldgSalesadline1())
			.salesadline2(buildingEntity.getBldgSalesadline2())
			.salesadline3(buildingEntity.getBldgSalesadline3())
			.salesadline4(buildingEntity.getBldgSalesadline4())
			.salesadline5(buildingEntity.getBldgSalesadline5())
			.salescity(buildingEntity.getBldgSalescity())
			.salescountry(buildingEntity.getBldgSalescountry())
			.salesctsnum(buildingEntity.getBldgSalesctsnum())
			.salesimages(buildingEntity.getBldgSalesimages())
			.salesname(buildingEntity.getBldgSalesname())
			.salesname1(buildingEntity.getBldgSalesname1())
			.salesname2(buildingEntity.getBldgSalesname2())
			.salespincode(buildingEntity.getBldgSalespincode())
			.salesstate(buildingEntity.getBldgSalesstate())
			.salestownship(buildingEntity.getBldgSalestownship())
			.saleyn(buildingEntity.getBldgSaleyn())
			.schposs(Objects.nonNull(buildingEntity.getBldgSchposs()) ? buildingEntity.getBldgSchposs().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.shortname(buildingEntity.getBldgShortname())
			//.site(buildingEntity.getBldgSite())
			.site(GenericAuditContextHolder.getContext().getSite())//NS 01.02.2023
			.staxcost(buildingEntity.getBldgStaxcost())
			.surveynum(buildingEntity.getBldgSurveynum())
			.tenure(buildingEntity.getBldgTenure())
			.thisyrcomp(buildingEntity.getBldgThisyrcomp())
			.thisyrinc(buildingEntity.getBldgThisyrinc())
			.thisyrmat(buildingEntity.getBldgThisyrmat())
			.thisyrwork(buildingEntity.getBldgThisyrwork())
			.todatecomp(buildingEntity.getBldgTodatecomp())
			.todateinc(buildingEntity.getBldgTodateinc())
			.todatemat(buildingEntity.getBldgTodatemat())
			.todatework(buildingEntity.getBldgTodatework())
			.today(LocalDateTime.now())//NS 01.02.2023
			.totalflats(buildingEntity.getBldgTotalflats())
			.township(buildingEntity.getBldgTownship())
			.ulcdate(Objects.nonNull(buildingEntity.getBldgUlcdate()) ? buildingEntity.getBldgUlcdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.userid(buildingEntity.getBldgUserid())
			.validyn(buildingEntity.getBldgValidyn())
			.xfered(Objects.nonNull(buildingEntity.getBldgXfered()) ? buildingEntity.getBldgXfered().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			;
			if(Objects.nonNull(addressEntity)) {
				buildingBeanBuilder.addressResponseBean(AddressEntityPojoMapper.fetchAddressEntityPojoMapper.apply(new Object[] {addressEntity}));
			}			
			if(Objects.nonNull(minorsEntity)) {
				buildingBeanBuilder.minorsResponseBean(MinorsEntityPojoMapper.fetchMinorsEntityPojoMapper.apply(new Object[] {minorsEntity}));
			}			
			if(Objects.nonNull(bldgEntity)) {
				buildingBeanBuilder.bldgmapResponseBean(BldgmapEntityPojoMapper.fetchBldgmapEntityPojoMapper.apply(new Object[] {bldgEntity}));
			}

			if(Objects.nonNull(mailinfoEntity)) {
				buildingBeanBuilder.mailinfoResponseBean(MailinfoEntityPojoMapper.fetchMailinfoEntityPojoMapper.apply(new Object[] {mailinfoEntity}));
			}
			if(CollectionUtils.isNotEmpty(flatsEntityList)) {
				for(Flats flats : flatsEntityList) {
					flatsResponseBeanList.add(FlatsEntityPojoMapper.fetchFlatsEntityPojoMapper.apply(new Object[] {flats}));
				}
				buildingBeanBuilder.flatsResponseBeanList(flatsResponseBeanList);

			}
			return buildingBeanBuilder.build();


		}).collect(Collectors.toList());

	};

	public static Function<Object[], Building> addBuildingEntityPojoMapper = objectArray -> {
		BuildingRequestBean buildingRequestBean = (BuildingRequestBean) (Objects
				.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String site = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
				? objectArray[BigInteger.ONE.intValue()]
						: null);

		Building.BuildingBuilder buildingbuilder = Building.builder();

		buildingbuilder
		.buildingCK(BuildingCK.builder()
				.bldgCode(buildingRequestBean.getCode())
				.build())
		.bldgAcclearyn(buildingRequestBean.getAcclearyn())
		.bldgAdminrate(buildingRequestBean.getAdminrate())
		.bldgAltbldg(buildingRequestBean.getAltbldg())
		.bldgAmencoy(buildingRequestBean.getAmencoy())
		.bldgAreaarch(buildingRequestBean.getAreaarch())
		.bldgAreaengg(buildingRequestBean.getAreaengg())
		.bldgAreasales(buildingRequestBean.getAreasales())
		.bldgBhk(buildingRequestBean.getBhk())
		.bldgBldgnum(buildingRequestBean.getBldgnum())
		.bldgBldgtype(buildingRequestBean.getBldgtype())
		.bldgCcdate(Objects.nonNull(buildingRequestBean.getCcdate()) ? LocalDate.parse(buildingRequestBean.getCcdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
		.bldgCity(buildingRequestBean.getCity())
		.bldgClosedate(Objects.nonNull(buildingRequestBean.getClosedate()) ? LocalDate.parse(buildingRequestBean.getClosedate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
		.bldgCollrecdwobj(buildingRequestBean.getCollrecdwobj())
		.bldgComplex(buildingRequestBean.getComplex())
		.bldgConcoy(buildingRequestBean.getConcoy())
		.bldgCoy(buildingRequestBean.getCoy())
		.bldgCoy4(buildingRequestBean.getCoy4())
		.bldgDebsocyn(buildingRequestBean.getDebsocyn())
		.bldgEa(buildingRequestBean.getEa())
		.bldgElecsupp(buildingRequestBean.getElecsupp())
		.bldgEng_Group(buildingRequestBean.getEng_group())
		.bldgFlatssold(buildingRequestBean.getFlatssold())
		.bldgGaragecoy(buildingRequestBean.getGaragecoy())
		.bldgHo2sales(Objects.nonNull(buildingRequestBean.getHo2sales()) ? LocalDate.parse(buildingRequestBean.getHo2sales(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
		.bldgIcsaleyn(buildingRequestBean.getIcsaleyn())
		.bldgInfrayn(buildingRequestBean.getInfrayn())
		.bldgIntbrokcoy(buildingRequestBean.getIntbrokcoy())
		.bldgIntrate(buildingRequestBean.getIntrate())
		.bldgLastyrcomp(buildingRequestBean.getLastyrcomp())
		.bldgLastyrinc(buildingRequestBean.getLastyrinc())
		.bldgLastyrmat(buildingRequestBean.getLastyrmat())
		.bldgLastyrwork(buildingRequestBean.getLastyrwork())
		.bldgLeasingcoy(buildingRequestBean.getLeasingcoy())
		.bldgMainbldg(buildingRequestBean.getMainbldg())
		.bldgMaintcoy(buildingRequestBean.getMaintcoy())
		.bldgMaintrate(buildingRequestBean.getMaintrate())
		.bldgMisbldg(buildingRequestBean.getMisbldg())
		.bldgMisproject(buildingRequestBean.getMisproject())
		.bldgModagdoc(buildingRequestBean.getModagdoc())
		.bldgName(buildingRequestBean.getName())
		.bldgOccdate(Objects.nonNull(buildingRequestBean.getOccdate()) ? LocalDate.parse(buildingRequestBean.getOccdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
		.bldgOpendate(Objects.nonNull(buildingRequestBean.getOpendate()) ? LocalDate.parse(buildingRequestBean.getOpendate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
		.bldgOpt2engg(buildingRequestBean.getOpt2engg())
		.bldgOpt2sales(buildingRequestBean.getOpt2sales())
		//.bldgOrigsite(buildingRequestBean.getOrigsite())
		.bldgOrigsite(GenericAuditContextHolder.getContext().getSite())//NS 01.02.2023
		.bldgOutformat(buildingRequestBean.getOutformat())
		.bldgParentbldg(buildingRequestBean.getParentbldg())
		.bldgPaycoy(buildingRequestBean.getPaycoy())
		.bldgPossdate(Objects.nonNull(buildingRequestBean.getPossdate()) ? LocalDate.parse(buildingRequestBean.getPossdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
		.bldgProgstat(buildingRequestBean.getProgstat())
		.bldgProject(buildingRequestBean.getProject())
		.bldgProp(buildingRequestBean.getProp())
		.bldgPropbucaarea(buildingRequestBean.getPropbucaarea())
		.bldgProperty(buildingRequestBean.getProperty())
		.bldgRegion(buildingRequestBean.getRegion())
		.bldgRemark(buildingRequestBean.getRemark())
		.bldgSalesadline1(buildingRequestBean.getSalesadline1())
		.bldgSalesadline2(buildingRequestBean.getSalesadline2())
		.bldgSalesadline3(buildingRequestBean.getSalesadline3())
		.bldgSalesadline4(buildingRequestBean.getSalesadline4())
		.bldgSalesadline5(buildingRequestBean.getSalesadline5())
		.bldgSalescity(buildingRequestBean.getSalescity())
		.bldgSalescountry(buildingRequestBean.getSalescountry())
		.bldgSalesctsnum(buildingRequestBean.getSalesctsnum())
		.bldgSalesimages(buildingRequestBean.getSalesimages())
		.bldgSalesname(buildingRequestBean.getSalesname())
		.bldgSalesname1(buildingRequestBean.getSalesname1())
		.bldgSalesname2(buildingRequestBean.getSalesname2())
		.bldgSalespincode(buildingRequestBean.getSalespincode())
		.bldgSalesstate(buildingRequestBean.getSalesstate())
		.bldgSalestownship(buildingRequestBean.getSalestownship())
		.bldgSaleyn(buildingRequestBean.getSaleyn())
		.bldgSchposs(Objects.nonNull(buildingRequestBean.getSchposs()) ? LocalDate.parse(buildingRequestBean.getSchposs(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
		.bldgShortname(buildingRequestBean.getShortname())
		.bldgSite(GenericAuditContextHolder.getContext().getSite())//NS 01.02.2023
		.bldgStaxcost(buildingRequestBean.getStaxcost())
		.bldgSurveynum(buildingRequestBean.getSurveynum())
		.bldgTenure(buildingRequestBean.getTenure())
		.bldgThisyrcomp(buildingRequestBean.getThisyrcomp())
		.bldgThisyrinc(buildingRequestBean.getThisyrinc())
		.bldgThisyrmat(buildingRequestBean.getThisyrmat())
		.bldgThisyrwork(buildingRequestBean.getThisyrwork())
		.bldgTodatecomp(buildingRequestBean.getTodatecomp())
		.bldgTodateinc(buildingRequestBean.getTodateinc())
		.bldgTodatemat(buildingRequestBean.getTodatemat())
		.bldgTodatework(buildingRequestBean.getTodatework())
		.bldgToday(LocalDateTime.now())//NS 01.02.2023
		.bldgTotalflats(buildingRequestBean.getTotalflats())
		.bldgTownship(buildingRequestBean.getTownship())
		.bldgUlcdate(Objects.nonNull(buildingRequestBean.getUlcdate()) ? LocalDate.parse(buildingRequestBean.getUlcdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
		.bldgUserid(GenericAuditContextHolder.getContext().getUserid()) //NS 01.02.2023
		.bldgValidyn(buildingRequestBean.getValidyn())
		.bldgXfered(Objects.nonNull(buildingRequestBean.getXfered()) ? LocalDate.parse(buildingRequestBean.getXfered(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
		;

		return buildingbuilder.build();
	};

	public static BiFunction<Building, BuildingRequestBean, Building> updateBuildingEntityPojoMapper = (buildingEntity, buildingRequestBean) -> {
		buildingEntity.getBuildingCK().setBldgCode(StringUtils.isNotBlank(buildingRequestBean.getCode()) ? buildingRequestBean.getCode().trim() : buildingEntity.getBuildingCK().getBldgCode());
		buildingEntity.setBldgAcclearyn(StringUtils.isNotBlank(buildingRequestBean.getAcclearyn()) ? buildingRequestBean.getAcclearyn().trim() : buildingEntity.getBldgAcclearyn());
		buildingEntity.setBldgAdminrate(Objects.nonNull(buildingRequestBean.getAdminrate()) ? buildingRequestBean.getAdminrate() : buildingEntity.getBldgAdminrate());
		buildingEntity.setBldgAltbldg(StringUtils.isNotBlank(buildingRequestBean.getAltbldg()) ? buildingRequestBean.getAltbldg().trim() : buildingEntity.getBldgAltbldg());
		buildingEntity.setBldgAmencoy(StringUtils.isNotBlank(buildingRequestBean.getAmencoy()) ? buildingRequestBean.getAmencoy().trim() : buildingEntity.getBldgAmencoy());
		buildingEntity.setBldgAreaarch(Objects.nonNull(buildingRequestBean.getAreaarch()) ? buildingRequestBean.getAreaarch() : buildingEntity.getBldgAreaarch());
		buildingEntity.setBldgAreaengg(Objects.nonNull(buildingRequestBean.getAreaengg()) ? buildingRequestBean.getAreaengg() : buildingEntity.getBldgAreaengg());
		buildingEntity.setBldgAreasales(Objects.nonNull(buildingRequestBean.getAreasales()) ? buildingRequestBean.getAreasales() : buildingEntity.getBldgAreasales());
		buildingEntity.setBldgBhk(StringUtils.isNotBlank(buildingRequestBean.getBhk()) ? buildingRequestBean.getBhk().trim() : buildingEntity.getBldgBhk());
		buildingEntity.setBldgBldgnum(StringUtils.isNotBlank(buildingRequestBean.getBldgnum()) ? buildingRequestBean.getBldgnum().trim() : buildingEntity.getBldgBldgnum());
		buildingEntity.setBldgBldgtype(StringUtils.isNotBlank(buildingRequestBean.getBldgtype()) ? buildingRequestBean.getBldgtype().trim() : buildingEntity.getBldgBldgtype());
		//buildingEntity.setBldgCcdate(Objects.nonNull(buildingRequestBean.getCcdate()) ? LocalDate.parse( buildingRequestBean.getCcdate() , CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : buildingEntity.getBldgCcdate());
		buildingEntity.setBldgCcdate(Objects.nonNull(buildingRequestBean.getCcdate()) ? LocalDate.parse( buildingRequestBean.getCcdate() , CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null); //NS 31.03.2023 above line was not as per requirement.
		buildingEntity.setBldgCity(StringUtils.isNotBlank(buildingRequestBean.getCity()) ? buildingRequestBean.getCity().trim() : buildingEntity.getBldgCity());
		buildingEntity.setBldgClosedate(Objects.nonNull(buildingRequestBean.getClosedate()) ? LocalDate.parse( buildingRequestBean.getClosedate() , CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : buildingEntity.getBldgClosedate());
		buildingEntity.setBldgCollrecdwobj(StringUtils.isNotBlank(buildingRequestBean.getCollrecdwobj()) ? buildingRequestBean.getCollrecdwobj().trim() : buildingEntity.getBldgCollrecdwobj());
		buildingEntity.setBldgComplex(StringUtils.isNotBlank(buildingRequestBean.getComplex()) ? buildingRequestBean.getComplex().trim() : buildingEntity.getBldgComplex());
		buildingEntity.setBldgConcoy(StringUtils.isNotBlank(buildingRequestBean.getConcoy()) ? buildingRequestBean.getConcoy().trim() : buildingEntity.getBldgConcoy());
		buildingEntity.setBldgCoy(StringUtils.isNotBlank(buildingRequestBean.getCoy()) ? buildingRequestBean.getCoy().trim() : buildingEntity.getBldgCoy());
		buildingEntity.setBldgCoy4(StringUtils.isNotBlank(buildingRequestBean.getCoy4()) ? buildingRequestBean.getCoy4().trim() : buildingEntity.getBldgCoy4());
		buildingEntity.setBldgDebsocyn(StringUtils.isNotBlank(buildingRequestBean.getDebsocyn()) ? buildingRequestBean.getDebsocyn().trim() : buildingEntity.getBldgDebsocyn());
		buildingEntity.setBldgEa(StringUtils.isNotBlank(buildingRequestBean.getEa()) ? buildingRequestBean.getEa().trim() : buildingEntity.getBldgEa());
		buildingEntity.setBldgElecsupp(StringUtils.isNotBlank(buildingRequestBean.getElecsupp()) ? buildingRequestBean.getElecsupp().trim() : buildingEntity.getBldgElecsupp());
		buildingEntity.setBldgEng_Group(StringUtils.isNotBlank(buildingRequestBean.getEng_group()) ? buildingRequestBean.getEng_group().trim() : buildingEntity.getBldgEng_Group());
		buildingEntity.setBldgFlatssold(Objects.nonNull(buildingRequestBean.getFlatssold()) ? buildingRequestBean.getFlatssold() : buildingEntity.getBldgFlatssold());
		buildingEntity.setBldgGaragecoy(StringUtils.isNotBlank(buildingRequestBean.getGaragecoy()) ? buildingRequestBean.getGaragecoy().trim() : buildingEntity.getBldgGaragecoy());
		buildingEntity.setBldgHo2sales(Objects.nonNull(buildingRequestBean.getHo2sales()) ? LocalDate.parse( buildingRequestBean.getHo2sales() , CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : buildingEntity.getBldgHo2sales());
		buildingEntity.setBldgIcsaleyn(StringUtils.isNotBlank(buildingRequestBean.getIcsaleyn()) ? buildingRequestBean.getIcsaleyn().trim() : buildingEntity.getBldgIcsaleyn());
		buildingEntity.setBldgInfrayn(StringUtils.isNotBlank(buildingRequestBean.getInfrayn()) ? buildingRequestBean.getInfrayn().trim() : buildingEntity.getBldgInfrayn());
		buildingEntity.setBldgIntbrokcoy(StringUtils.isNotBlank(buildingRequestBean.getIntbrokcoy()) ? buildingRequestBean.getIntbrokcoy().trim() : buildingEntity.getBldgIntbrokcoy());
		buildingEntity.setBldgIntrate(Objects.nonNull(buildingRequestBean.getIntrate()) ? buildingRequestBean.getIntrate() : buildingEntity.getBldgIntrate());
		buildingEntity.setBldgLastyrcomp(Objects.nonNull(buildingRequestBean.getLastyrcomp()) ? buildingRequestBean.getLastyrcomp() : buildingEntity.getBldgLastyrcomp());
		buildingEntity.setBldgLastyrinc(Objects.nonNull(buildingRequestBean.getLastyrinc()) ? buildingRequestBean.getLastyrinc() : buildingEntity.getBldgLastyrinc());
		buildingEntity.setBldgLastyrmat(Objects.nonNull(buildingRequestBean.getLastyrmat()) ? buildingRequestBean.getLastyrmat() : buildingEntity.getBldgLastyrmat());
		buildingEntity.setBldgLastyrwork(Objects.nonNull(buildingRequestBean.getLastyrwork()) ? buildingRequestBean.getLastyrwork() : buildingEntity.getBldgLastyrwork());
		buildingEntity.setBldgLeasingcoy(StringUtils.isNotBlank(buildingRequestBean.getLeasingcoy()) ? buildingRequestBean.getLeasingcoy().trim() : buildingEntity.getBldgLeasingcoy());
		buildingEntity.setBldgMainbldg(StringUtils.isNotBlank(buildingRequestBean.getMainbldg()) ? buildingRequestBean.getMainbldg().trim() : buildingEntity.getBldgMainbldg());
		buildingEntity.setBldgMaintcoy(StringUtils.isNotBlank(buildingRequestBean.getMaintcoy()) ? buildingRequestBean.getMaintcoy().trim() : buildingEntity.getBldgMaintcoy());
		buildingEntity.setBldgMaintrate(Objects.nonNull(buildingRequestBean.getMaintrate()) ? buildingRequestBean.getMaintrate() : buildingEntity.getBldgMaintrate());
		buildingEntity.setBldgMisbldg(StringUtils.isNotBlank(buildingRequestBean.getMisbldg()) ? buildingRequestBean.getMisbldg().trim() : buildingEntity.getBldgMisbldg());
		//buildingEntity.setBldgMisproject(StringUtils.isNotBlank(buildingRequestBean.getMisproject()) ? buildingRequestBean.getMisproject().trim() : buildingEntity.getBldgMisproject());
		buildingEntity.setBldgMisproject(Objects.nonNull(buildingRequestBean.getMisproject()) ? buildingRequestBean.getMisproject().trim() : null);//NS 11.04.2023
		buildingEntity.setBldgModagdoc(StringUtils.isNotBlank(buildingRequestBean.getModagdoc()) ? buildingRequestBean.getModagdoc().trim() : buildingEntity.getBldgModagdoc());
		buildingEntity.setBldgName(StringUtils.isNotBlank(buildingRequestBean.getName()) ? buildingRequestBean.getName().trim() : buildingEntity.getBldgName());
		//buildingEntity.setBldgOccdate(Objects.nonNull(buildingRequestBean.getOccdate()) ? LocalDate.parse( buildingRequestBean.getOccdate() , CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : buildingEntity.getBldgOccdate());
		buildingEntity.setBldgOccdate(Objects.nonNull(buildingRequestBean.getOccdate()) ? LocalDate.parse( buildingRequestBean.getOccdate() , CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null); // NS 31.03.2023 above line was not as per requirement.
		//buildingEntity.setBldgOpendate(Objects.nonNull(buildingRequestBean.getOpendate()) ? LocalDate.parse( buildingRequestBean.getOpendate() , CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : buildingEntity.getBldgOpendate());
		buildingEntity.setBldgOpendate(Objects.nonNull(buildingRequestBean.getOpendate()) ? LocalDate.parse( buildingRequestBean.getOpendate() , CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null);//NS 31.03.2023 above line was not part of requirement.
		buildingEntity.setBldgOpt2engg(StringUtils.isNotBlank(buildingRequestBean.getOpt2engg()) ? buildingRequestBean.getOpt2engg().trim() : buildingEntity.getBldgOpt2engg());
		buildingEntity.setBldgOpt2sales(StringUtils.isNotBlank(buildingRequestBean.getOpt2sales()) ? buildingRequestBean.getOpt2sales().trim() : buildingEntity.getBldgOpt2sales());
		buildingEntity.setBldgOrigsite(GenericAuditContextHolder.getContext().getSite());//NS 01.02.2023
		buildingEntity.setBldgOutformat(StringUtils.isNotBlank(buildingRequestBean.getOutformat()) ? buildingRequestBean.getOutformat().trim() : buildingEntity.getBldgOutformat());
		buildingEntity.setBldgParentbldg(StringUtils.isNotBlank(buildingRequestBean.getParentbldg()) ? buildingRequestBean.getParentbldg().trim() : buildingEntity.getBldgParentbldg());
		buildingEntity.setBldgPaycoy(StringUtils.isNotBlank(buildingRequestBean.getPaycoy()) ? buildingRequestBean.getPaycoy().trim() : buildingEntity.getBldgPaycoy());
		buildingEntity.setBldgPossdate(Objects.nonNull(buildingRequestBean.getPossdate()) ?  LocalDate.parse( buildingRequestBean.getPossdate() , CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): buildingEntity.getBldgPossdate());
		buildingEntity.setBldgProgstat(StringUtils.isNotBlank(buildingRequestBean.getProgstat()) ? buildingRequestBean.getProgstat().trim() : buildingEntity.getBldgProgstat());
		buildingEntity.setBldgProject(StringUtils.isNotBlank(buildingRequestBean.getProject()) ? buildingRequestBean.getProject().trim() : buildingEntity.getBldgProject());
		buildingEntity.setBldgProp(StringUtils.isNotBlank(buildingRequestBean.getProp()) ? buildingRequestBean.getProp().trim() : buildingEntity.getBldgProp());
		buildingEntity.setBldgPropbucaarea(StringUtils.isNotBlank(buildingRequestBean.getPropbucaarea()) ? buildingRequestBean.getPropbucaarea().trim() : buildingEntity.getBldgPropbucaarea());
		buildingEntity.setBldgProperty(StringUtils.isNotBlank(buildingRequestBean.getProperty()) ? buildingRequestBean.getProperty().trim() : buildingEntity.getBldgProperty());
		buildingEntity.setBldgRegion(StringUtils.isNotBlank(buildingRequestBean.getRegion()) ? buildingRequestBean.getRegion().trim() : buildingEntity.getBldgRegion());
		buildingEntity.setBldgRemark(StringUtils.isNotBlank(buildingRequestBean.getRemark()) ? buildingRequestBean.getRemark().trim() : buildingEntity.getBldgRemark());
		buildingEntity.setBldgSalesadline1(StringUtils.isNotBlank(buildingRequestBean.getSalesadline1()) ? buildingRequestBean.getSalesadline1().trim() : buildingEntity.getBldgSalesadline1());
		buildingEntity.setBldgSalesadline2(StringUtils.isNotBlank(buildingRequestBean.getSalesadline2()) ? buildingRequestBean.getSalesadline2().trim() : buildingEntity.getBldgSalesadline2());
		buildingEntity.setBldgSalesadline3(StringUtils.isNotBlank(buildingRequestBean.getSalesadline3()) ? buildingRequestBean.getSalesadline3().trim() : buildingEntity.getBldgSalesadline3());
		buildingEntity.setBldgSalesadline4(StringUtils.isNotBlank(buildingRequestBean.getSalesadline4()) ? buildingRequestBean.getSalesadline4().trim() : buildingEntity.getBldgSalesadline4());
		buildingEntity.setBldgSalesadline5(StringUtils.isNotBlank(buildingRequestBean.getSalesadline5()) ? buildingRequestBean.getSalesadline5().trim() : buildingEntity.getBldgSalesadline5());
		buildingEntity.setBldgSalescity(StringUtils.isNotBlank(buildingRequestBean.getSalescity()) ? buildingRequestBean.getSalescity().trim() : buildingEntity.getBldgSalescity());
		buildingEntity.setBldgSalescountry(StringUtils.isNotBlank(buildingRequestBean.getSalescountry()) ? buildingRequestBean.getSalescountry().trim() : buildingEntity.getBldgSalescountry());
		buildingEntity.setBldgSalesctsnum(StringUtils.isNotBlank(buildingRequestBean.getSalesctsnum()) ? buildingRequestBean.getSalesctsnum().trim() : buildingEntity.getBldgSalesctsnum());
		buildingEntity.setBldgSalesimages(StringUtils.isNotBlank(buildingRequestBean.getSalesimages()) ? buildingRequestBean.getSalesimages().trim() : buildingEntity.getBldgSalesimages());
		buildingEntity.setBldgSalesname(StringUtils.isNotBlank(buildingRequestBean.getSalesname()) ? buildingRequestBean.getSalesname().trim() : buildingEntity.getBldgSalesname());
		buildingEntity.setBldgSalesname1(StringUtils.isNotBlank(buildingRequestBean.getSalesname1()) ? buildingRequestBean.getSalesname1().trim() : buildingEntity.getBldgSalesname1());
		buildingEntity.setBldgSalesname2(StringUtils.isNotBlank(buildingRequestBean.getSalesname2()) ? buildingRequestBean.getSalesname2().trim() : buildingEntity.getBldgSalesname2());
		buildingEntity.setBldgSalespincode(StringUtils.isNotBlank(buildingRequestBean.getSalespincode()) ? buildingRequestBean.getSalespincode().trim() : buildingEntity.getBldgSalespincode());
		buildingEntity.setBldgSalesstate(StringUtils.isNotBlank(buildingRequestBean.getSalesstate()) ? buildingRequestBean.getSalesstate().trim() : buildingEntity.getBldgSalesstate());
		buildingEntity.setBldgSalestownship(StringUtils.isNotBlank(buildingRequestBean.getSalestownship()) ? buildingRequestBean.getSalestownship().trim() : buildingEntity.getBldgSalestownship());
		buildingEntity.setBldgSaleyn(StringUtils.isNotBlank(buildingRequestBean.getSaleyn()) ? buildingRequestBean.getSaleyn().trim() : buildingEntity.getBldgSaleyn());
		//buildingEntity.setBldgSchposs(Objects.nonNull(buildingRequestBean.getSchposs()) ? LocalDate.parse( buildingRequestBean.getSchposs() , CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : buildingEntity.getBldgSchposs());
		buildingEntity.setBldgSchposs(Objects.nonNull(buildingRequestBean.getSchposs()) ? LocalDate.parse( buildingRequestBean.getSchposs() , CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null);// NS 31.03.2023 above line was not part of requirement.
		buildingEntity.setBldgShortname(StringUtils.isNotBlank(buildingRequestBean.getShortname()) ? buildingRequestBean.getShortname().trim() : buildingEntity.getBldgShortname());
		buildingEntity.setBldgSite(GenericAuditContextHolder.getContext().getSite());//NS 01.02.2023
		buildingEntity.setBldgStaxcost(StringUtils.isNotBlank(buildingRequestBean.getStaxcost()) ? buildingRequestBean.getStaxcost().trim() : buildingEntity.getBldgStaxcost());
		buildingEntity.setBldgSurveynum(StringUtils.isNotBlank(buildingRequestBean.getSurveynum()) ? buildingRequestBean.getSurveynum().trim() : buildingEntity.getBldgSurveynum());
		buildingEntity.setBldgTenure(StringUtils.isNotBlank(buildingRequestBean.getTenure()) ? buildingRequestBean.getTenure().trim() : buildingEntity.getBldgTenure());
		buildingEntity.setBldgThisyrcomp(Objects.nonNull(buildingRequestBean.getThisyrcomp()) ? buildingRequestBean.getThisyrcomp() : buildingEntity.getBldgThisyrcomp());
		buildingEntity.setBldgThisyrinc(Objects.nonNull(buildingRequestBean.getThisyrinc()) ? buildingRequestBean.getThisyrinc() : buildingEntity.getBldgThisyrinc());
		buildingEntity.setBldgThisyrmat(Objects.nonNull(buildingRequestBean.getThisyrmat()) ? buildingRequestBean.getThisyrmat() : buildingEntity.getBldgThisyrmat());
		buildingEntity.setBldgThisyrwork(Objects.nonNull(buildingRequestBean.getThisyrwork()) ? buildingRequestBean.getThisyrwork() : buildingEntity.getBldgThisyrwork());
		buildingEntity.setBldgTodatecomp(Objects.nonNull(buildingRequestBean.getTodatecomp()) ? buildingRequestBean.getTodatecomp() : buildingEntity.getBldgTodatecomp());
		buildingEntity.setBldgTodateinc(Objects.nonNull(buildingRequestBean.getTodateinc()) ? buildingRequestBean.getTodateinc() : buildingEntity.getBldgTodateinc());
		buildingEntity.setBldgTodatemat(Objects.nonNull(buildingRequestBean.getTodatemat()) ? buildingRequestBean.getTodatemat() : buildingEntity.getBldgTodatemat());
		buildingEntity.setBldgTodatework(Objects.nonNull(buildingRequestBean.getTodatework()) ? buildingRequestBean.getTodatework() : buildingEntity.getBldgTodatework());
//		buildingEntity.setBldgToday(Objects.nonNull(buildingRequestBean.getToday()) ?  LocalDateTime.now() : buildingEntity.getBldgToday());
		buildingEntity.setBldgToday(LocalDateTime.now());//NS 01.02.2023
		buildingEntity.setBldgTotalflats(Objects.nonNull(buildingRequestBean.getTotalflats()) ? buildingRequestBean.getTotalflats() : buildingEntity.getBldgTotalflats());
		buildingEntity.setBldgTownship(StringUtils.isNotBlank(buildingRequestBean.getTownship()) ? buildingRequestBean.getTownship().trim() : buildingEntity.getBldgTownship());
		//buildingEntity.setBldgUlcdate(Objects.nonNull(buildingRequestBean.getUlcdate()) ? LocalDate.parse( buildingRequestBean.getUlcdate() , CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : buildingEntity.getBldgUlcdate());
		buildingEntity.setBldgUlcdate(Objects.nonNull(buildingRequestBean.getUlcdate()) ? LocalDate.parse( buildingRequestBean.getUlcdate() , CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null); //NS 31.03.2023 the above code is not part of requirement.
		buildingEntity.setBldgUserid(StringUtils.isNotBlank(buildingRequestBean.getUserid()) ? buildingRequestBean.getUserid().trim() : buildingEntity.getBldgUserid());
		buildingEntity.setBldgValidyn(StringUtils.isNotBlank(buildingRequestBean.getValidyn()) ? buildingRequestBean.getValidyn().trim() : buildingEntity.getBldgValidyn());
		buildingEntity.setBldgXfered(Objects.nonNull(buildingRequestBean.getXfered()) ? LocalDate.parse( buildingRequestBean.getXfered() , CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): buildingEntity.getBldgXfered());
		return buildingEntity;
	};

}
