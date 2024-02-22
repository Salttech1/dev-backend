package kraheja.sales.infra.service.impl;
import kraheja.sales.outgoing.service.impl.OutrateServiceImpl;
import java.math.BigInteger;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import kraheja.commons.bean.response.GstResponseBean;
import kraheja.commons.bean.response.GstResponseBean.GstResponseBeanBuilder;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.sales.bean.request.OutinfraRequestBean;
import kraheja.sales.bean.response.OutinfraFetchedPrevRecords;
import kraheja.sales.entity.Outinfra;
import kraheja.sales.infra.mappers.OutinfraEntityPojoMapper;
import kraheja.sales.infra.service.OutinfraService;
import kraheja.sales.repository.FlatownerRepository;
import kraheja.sales.repository.OutinfraRepository;
import kraheja.sales.repository.OutrateRepository;
 
@Service
@Transactional
public class OutinfraServiceImpl implements OutinfraService {

	private static final Logger logger = LoggerFactory.getLogger(OutinfraServiceImpl.class);

	@Autowired
	private OutinfraRepository outinfraRepository;
	
	@Autowired
	private OutrateRepository outrateRepository;
	
	@Autowired
	private FlatownerRepository flatownerRepository;
	
	@PersistenceContext
	private  EntityManager entityManager;
	
	private ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private  EntityRepository entityRepository;
	@Override
	public ResponseEntity<?> fetchOutinfraByBldgcodeAndOwneridAndRecnumAndMonthAndNarrcode(String  bldgcode, String  ownerid, String  recnum, String  month, String  narrcode) {
		Outinfra outinfraEntity = this.outinfraRepository.findByOutinfraCK_InfBldgcodeAndOutinfraCK_InfOwneridAndOutinfraCK_InfRecnumAndOutinfraCK_InfMonthAndOutinfraCK_InfNarrcode(bldgcode, ownerid, recnum, month, narrcode);
		logger.info("OutinfraEntity :: {}", outinfraEntity);
		if (outinfraEntity != null) {
			return ResponseEntity.ok(ServiceResponseBean.builder()
									.data(OutinfraEntityPojoMapper.fetchOutinfraEntityPojoMapper
									.apply(new Object[] { Arrays.asList(outinfraEntity) })
									.get(BigInteger.ZERO.intValue()))
									.status(Boolean.TRUE).build());
			}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No record found for your selections in Outinfra").build());
	}
	
	@Override
	public ResponseEntity<?> fetchStartdateAndEnddateByBldgcodeAndWingAndFlatnumAndbilltype(String bldgcode, String wing, String flatnum, String billtype) { //NS 04.09.2023
		String startDate = this.outrateRepository.fetchStartdateForAuxiGSTFirst(bldgcode, wing, flatnum, billtype);
		String data = "";
		if(startDate != "")
		{
			if(startDate != null) //NS 05.09.2023
			{
				String endDate = this.outrateRepository.fetchEndDeteForAuxiGSTFirst(bldgcode, wing, flatnum, billtype);
				if(endDate != "")
				{
					if(endDate != null) //NS 05.09.2023
					{
					data = startDate + "/" + endDate;
					return ResponseEntity.ok(ServiceResponseBean.builder()
							.status(Boolean.TRUE).data(data).build());
					}
					else
					{
						return ResponseEntity.ok(ServiceResponseBean.builder()
								.status(Boolean.FALSE).message("END DATE NOT EXIST.").build());//NS 05.09.2023
					}
				}	
				else
				{
					return ResponseEntity.ok(ServiceResponseBean.builder()
							.status(Boolean.FALSE).message("END DATE NOT EXIST.").build());
				}
			}
			else
			{
				return ResponseEntity.ok(ServiceResponseBean.builder()
						.status(Boolean.FALSE).message("START DATE NOT EXIST.").build());//NS 05.09.2023
			}
		}
		else
		{
			return ResponseEntity.ok(ServiceResponseBean.builder()
					.status(Boolean.FALSE).message("START DATE NOT EXIST.").build());
		}
	}

	
	@Override
	public ResponseEntity<?> fetchMaintainanceRate(String  bldgcode, String  wing, String  flatnum, String billtype) { //NS 14.08.2023
		String maintainanceRate = this.outrateRepository.fetchMaintainanceRateForAuxilliary(bldgcode, wing, flatnum, billtype);	
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(maintainanceRate).build());
	}
	
	@Override
	public ResponseEntity<?> fetchAdminRate(String  bldgcode, String  wing, String  flatnum, String billtype) { //NS 17.08.2023
		String adminRate = this.outrateRepository.fetchAdminRateForAuxilliary(bldgcode, wing, flatnum, billtype);	
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(adminRate).build());
	}
	
	@Override
	public ResponseEntity<?> fetchTDSRate(String  bldgcode, String  wing, String  flatnum, String billtype) { //NS 17.08.2023
		String adminRate = this.outrateRepository.fetchTDSRateForAuxilliary(bldgcode, wing, flatnum, billtype);	
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(adminRate).build());
	}
	
	@Override
	public ResponseEntity<?> fetchFlatOwnerByBldgcodeAndFlatnumAndWing(String bldgcode, String flatnum, String wing) //NS 15.05.2023 with the help of YC
	{
		Query query = this.entityManager.createNativeQuery("Select fown_name from V_SACLRP02B where flat_bldgcode ='".concat(bldgcode)
				.concat("'  and flat_wing='".concat(StringUtils.isNotBlank(wing) ? wing.trim() : CommonConstraints.INSTANCE.SPACE_STRING).concat("' and flat_flatnum ='").concat(flatnum).concat("'")));
		List<Object> flatOwnerName = query.getResultList();
		if(!flatOwnerName.isEmpty()) {
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(query.getResultList().get(0).toString().trim()).build());
			
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No record found.").build());
	}
	
	@Override
	public ResponseEntity<?> fetchPreviousOgRecords(String ownerid, String startDate, String typeAuxi, String billType)//NS 09.06.2023
	{
		List<Tuple> arrayOfPrevRecords = this.outinfraRepository.findPrevOgRecords(ownerid, startDate, typeAuxi, billType); //NS 10.06.2023
		if(!arrayOfPrevRecords.isEmpty()) //NS 12.06.2023
		{
			List<OutinfraFetchedPrevRecords> outInfraFetchPrevList = //YC, NS 12.06.23
					arrayOfPrevRecords.stream().map(t -> {
							return new OutinfraFetchedPrevRecords(
									t.get(0, Double.class),
									t.get(1, Double.class),
									t.get(2, Double.class),
									t.get(3, Double.class),
									t.get(4, String.class),
									t.get(5, String.class),
									t.get(6, Double.class),
									t.get(7, Double.class),
									t.get(8, Double.class)
							);
					}).collect(Collectors.toList());
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(outInfraFetchPrevList).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No record found.").build());
	}
	
	
	@Override
	public ResponseEntity<?> fetchGstRates() //NS 29.05.2023
	{
		String gsttype = "";
		String strlochsmscode = "995419"; // this code will fetch the related code from the database 
		String StrLocTranDate = LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER);
		
		Query query = this.entityManager.createNativeQuery("SELECT FUNC_GetGSTPerc('" + strlochsmscode + "','','" + StrLocTranDate + "','DESC') AS HSMS_DESC , " + "FUNC_GetGSTPerc('" + strlochsmscode + "','CGST','" + StrLocTranDate + "','PERC') AS HSMS_CGSTPERC , FUNC_GetGSTPerc('" + strlochsmscode + "','SGST','" + StrLocTranDate + "','PERC') AS HSMS_SGSTPERC , FUNC_GetGSTPerc('" + strlochsmscode + "','IGST','" + StrLocTranDate + "','PERC') AS HSMS_IGSTPERC , FUNC_GetGSTPerc('" + strlochsmscode + "','UGST','" + StrLocTranDate + "','PERC') AS HSMS_UGSTPERC FROM dual", Tuple.class);
		List<Tuple> tuplesList = query.getResultList();
//		Object data = tuplesList.get(0);
//		//String something = tuplesList.get(0).get(0).toString();//to be test the data
//		String cgst = tuplesList.get(0)..toString();
//		String sgst = tuplesList.get(0).toString();
//		String igst = tuplesList.get(0).toString();
//		String ugst = tuplesList.get(0).toString(); 
//		String responseData = cgst+sgst+igst+ugst;
		if(CollectionUtils.isNotEmpty(tuplesList)) {
			Map<String, String> getPercMap = new HashMap<>();
			tuplesList.stream().map(t -> {
				getPercMap.put("description", t.get(0, String.class));
				getPercMap.put("cgst", t.get(1, String.class));
				getPercMap.put("sgst", t.get(2, String.class));
				getPercMap.put("igst", t.get(3, String.class));
				getPercMap.put("ugst", t.get(4, String.class));

				return t;
			}).collect(Collectors.toList());
			if(getPercMap.get("description").equals(CommonConstraints.INSTANCE.NODATA_STRING) && 
					getPercMap.get("cgst").equals(CommonConstraints.INSTANCE.NODATA_STRING) &&
					getPercMap.get("sgst").equals(CommonConstraints.INSTANCE.NODATA_STRING) &&
					getPercMap.get("igst").equals(CommonConstraints.INSTANCE.NODATA_STRING) &&
					getPercMap.get("ugst").equals(CommonConstraints.INSTANCE.NODATA_STRING))
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Invalid HSN code.").build());
			GstResponseBeanBuilder gstResponseBean = GstResponseBean.builder();
			gstResponseBean.description(getPercMap.get("description"));
			gstResponseBean.cgstperc(Double.valueOf(getPercMap.get("cgst")));
			gstResponseBean.sgstperc(Double.valueOf(getPercMap.get("sgst")));
			gstResponseBean.igstperc(Double.valueOf(getPercMap.get("igst")));
			gstResponseBean.ugstperc(Double.valueOf(getPercMap.get("ugst")));
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(gstResponseBean.build()).build());
	}return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No data found.").build());
	}
	
	//Start -----------NS 19.05.2023
	@Override
	public ResponseEntity<?> fetchStartDateByBldgcodeAndWingAndFlatnoAndBilltype(String bldgcode, String wing, String flatno, String billtype)
	{
//		if(wing.equals("")) //NS 20.05.2023
//		{
//			wing=" ";
//		}
//		else
//		{
//			wing= wing.trim();
//		}
		String startDate = outrateRepository.fetchStartDate(bldgcode, wing, flatno, billtype).get(0);
		int constraintDate = Integer.parseInt(startDate);
		if(startDate == null) //NS 20.05.2023
		{
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Invalid Building Code/Wing/Flat No.").build());
		}
		
		if(StringUtils.isBlank(startDate) || Integer.parseInt(startDate)< constraintDate)
		{
			
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data("201707").build());
		}
		else //NS 20.05.2023
		{
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(startDate).build());
		}
		
	}
	//End -----------NS 19.05.2023
	@Override
	public ResponseEntity<?> addOutinfra(OutinfraRequestBean outinfraRequestBean) throws ParseException {
		String siteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);

		this.outinfraRepository.save(OutinfraEntityPojoMapper.addOutinfraEntityPojoMapper.apply(new Object[] {outinfraRequestBean,siteFromDBEntity,outinfraRequestBean.getOwnerid(),outinfraRequestBean.getRecnum(),outinfraRequestBean.getMonth(),outinfraRequestBean.getNarrcode()}));

		GenericAuditContextHolder.getContext().setTransactionNo( outinfraRequestBean.getRecnum());
		GenericAuditContextHolder.getContext().setAuditable(Boolean.FALSE);

		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Data Added Successfully").build());
	}

	@Override
	public ResponseEntity<?> updateOutinfra(OutinfraRequestBean outinfraRequestBean) throws ParseException {
		Outinfra outinfraEntity = this.outinfraRepository.findByOutinfraCK_InfBldgcodeAndOutinfraCK_InfOwneridAndOutinfraCK_InfRecnumAndOutinfraCK_InfMonthAndOutinfraCK_InfNarrcode(outinfraRequestBean.getBldgcode() , outinfraRequestBean.getOwnerid() , outinfraRequestBean.getRecnum() , outinfraRequestBean.getMonth() , outinfraRequestBean.getNarrcode() ) ; 

		if(Objects.nonNull(outinfraEntity)) {

			String SiteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);

			if(Objects.nonNull(outinfraRequestBean))
				this.outinfraRepository.save(OutinfraEntityPojoMapper.updateOutinfraEntityPojoMapper.apply(outinfraEntity, outinfraRequestBean));
				GenericAuditContextHolder.getContext().setTransactionNo(outinfraRequestBean.getRecnum());
				GenericAuditContextHolder.getContext().setAuditable(Boolean.FALSE);

				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Updated Successfully").build());
		} else {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
		}
	}

	@Override
	public ResponseEntity<?> deleteOutinfra(String  bldgcode, String  ownerid, String  recnum, String  month, String  narrcode) throws ParseException {
		Outinfra outinfraEntity = this.outinfraRepository.findByOutinfraCK_InfBldgcodeAndOutinfraCK_InfOwneridAndOutinfraCK_InfRecnumAndOutinfraCK_InfMonthAndOutinfraCK_InfNarrcode(bldgcode , ownerid , recnum , month , narrcode ) ; 

		if(Objects.nonNull(outinfraEntity)) {

			this.outinfraRepository.delete(outinfraEntity);
			GenericAuditContextHolder.getContext().setTransactionNo(outinfraEntity.getOutinfraCK().getInfRecnum());
			GenericAuditContextHolder.getContext().setAuditable(Boolean.FALSE);

			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Deleted Successfully").build());
		} 
		else {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
		}
	}



}
