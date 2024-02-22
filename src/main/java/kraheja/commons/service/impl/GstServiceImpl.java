package kraheja.commons.service.impl;

import java.math.BigInteger;
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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kraheja.commons.bean.response.GstResponseBean;
import kraheja.commons.bean.response.GstResponseBean.GstResponseBeanBuilder;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.Address;
import kraheja.commons.enums.GstTypeEnum;
import kraheja.commons.repository.AddressRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.service.GstService;
import kraheja.commons.utils.CommonConstraints;

@Service
public class GstServiceImpl implements GstService {
	private static final Logger logger = LoggerFactory.getLogger(GstServiceImpl.class);
	
	@Autowired
	private  AddressRepository addressRepository;

	@Autowired
	private EntityRepository entityRepository;
	
	@PersistenceContext
	private  EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<?> fetchGstPerc(String hsncode, String partycode, String adowner, String segment, String adrPartySegment,String adPartyType, String adtype, String suppbilldt, String ser, Boolean isUpdate) {
		String gsttype = "";
		Address addressEntity = this.addressRepository.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtype(partycode.trim(), adrPartySegment.trim(), adPartyType.trim());
		if(Objects.nonNull(addressEntity)) 
			if(!addressEntity.getAdrCountry().equals("INDIA"))
				gsttype = GstTypeEnum.IGST.toString();
			else{
				Address addressBldgEntity = this.addressRepository.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtype(adowner, segment, adtype);
				if(Objects.nonNull(addressBldgEntity)) 
					if(Objects.nonNull(addressBldgEntity.getAdrState()) && Objects.nonNull(addressEntity.getAdrState()) && addressBldgEntity.getAdrState().trim().equals(addressEntity.getAdrState())) {
						String entChar3 = this.entityRepository.fetchByChar3EntityCk_EntClassAndEntityCk_EntId("STATE", addressBldgEntity.getAdrState().trim());
						if(StringUtils.isNotBlank(entChar3) && entChar3.equals("U"))
							gsttype = GstTypeEnum.CGSTUGST.toString();
						else
							gsttype = GstTypeEnum.CGSTSGST.toString();
					}else
						gsttype = GstTypeEnum.IGST.toString();
				else
					gsttype = GstTypeEnum.IGST.toString();
			}

		String gstTranDate = getGstTranDate(suppbilldt, ser, isUpdate);
		if(StringUtils.isNotBlank(hsncode)) {
			Query gstperc = this.entityManager.createNativeQuery("SELECT \r\n"
					+ "FUNC_GetGSTPerc(:hsncode,'',:gstTranDate,'DESC') AS HSMS_DESC , \r\n"
					+ "FUNC_GetGSTPerc(:hsncode,'CGST',:gstTranDate,'PERC') AS HSMS_CGSTPERC , \r\n"
					+ "FUNC_GetGSTPerc(:hsncode,'SGST',:gstTranDate,'PERC') AS HSMS_SGSTPERC , \r\n"
					+ "FUNC_GetGSTPerc(:hsncode,'IGST',:gstTranDate,'PERC') AS HSMS_IGSTPERC , \r\n"
					+ "FUNC_GetGSTPerc(:hsncode,'UGST',:gstTranDate,'PERC') AS HSMS_UGSTPERC \r\n"
					+ "FROM dual", Tuple.class);
			gstperc.setParameter("hsncode", hsncode);
			gstperc.setParameter("gstTranDate", gstTranDate);

			List<Tuple> tuplesList = gstperc.getResultList();
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
				switch(gsttype) {
				case "IGST": {
					gstResponseBean.description(getPercMap.get("description"));
					gstResponseBean.cgstperc(BigInteger.ZERO.doubleValue());
					gstResponseBean.sgstperc(BigInteger.ZERO.doubleValue());
					gstResponseBean.igstperc(Double.valueOf(getPercMap.get("igst")));
					gstResponseBean.ugstperc(BigInteger.ZERO.doubleValue());
					break;
				}
				case "CGSTSGST": {
					gstResponseBean.description(getPercMap.get("description"));
					gstResponseBean.cgstperc(Double.valueOf(getPercMap.get("cgst")));
					gstResponseBean.sgstperc(Double.valueOf(getPercMap.get("sgst")));
					gstResponseBean.igstperc(BigInteger.ZERO.doubleValue());
					gstResponseBean.ugstperc(BigInteger.ZERO.doubleValue());
					break;
				}
				case "CGSTUGST": {
					gstResponseBean.description(getPercMap.get("description"));
					gstResponseBean.cgstperc(Double.valueOf(getPercMap.get("cgst")));
					gstResponseBean.sgstperc(BigInteger.ZERO.doubleValue());
					gstResponseBean.igstperc(BigInteger.ZERO.doubleValue());
					gstResponseBean.ugstperc(Double.valueOf(getPercMap.get("ugst")));
					break;
				}
				default: {
					gstResponseBean.description(CommonConstraints.INSTANCE.BLANK_STRING);
					gstResponseBean.cgstperc(BigInteger.ZERO.doubleValue());
					gstResponseBean.sgstperc(BigInteger.ZERO.doubleValue());
					gstResponseBean.igstperc(BigInteger.ZERO.doubleValue());
					gstResponseBean.ugstperc(BigInteger.ZERO.doubleValue());
					break;
				}
				}
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(gstResponseBean.build()).build());
			}
		}return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No data found.").build());
	}
	
	private String getGstTranDate(String suppbilldt, String ser, Boolean isUpdate) {
		if(isUpdate) {
			Query ltdQuery = this.entityManager.createNativeQuery("SELECT to_char(acth_trandate,'DD/MM/YYYY') FROM actranh WHERE acth_transer = :ser");
			ltdQuery.setParameter("ser", ser);

			Object singleResult = ltdQuery.getSingleResult();
			logger.info("SingleResult :: ", singleResult.toString());

			return singleResult.toString();
		}else {
			Query ltdQuery = this.entityManager.createNativeQuery("select to_char((FUNC_GetGSTBillTranDate(TO_DATE(:suppbilldt,'DD/MM/YYYY'))), 'DD/MM/YYYY') from dual");

			ltdQuery.setParameter("suppbilldt", suppbilldt);

			Object singleResult = ltdQuery.getSingleResult();
			logger.info("SingleResult :: ", singleResult.toString());

			return singleResult.toString();
		}
	}
}