package kraheja.purch.purchasebills.service.impl;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.Address;
import kraheja.commons.entity.Party;
import kraheja.commons.repository.AddressRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.repository.PartyRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;
import kraheja.commons.utils.ValueContainer;
import kraheja.purch.bean.response.BillChallanDetailResponseBean;
import kraheja.purch.bean.response.BillDebitNoteDetailResponseBean;
import kraheja.purch.bean.response.BillDetailEnquiryResponseBean;
import kraheja.purch.bean.response.BillDetailResponseBean;
import kraheja.purch.bean.response.ItemRateDetailResponseBean;
import kraheja.purch.bean.response.NewBillDetailResponseBean;
import kraheja.purch.bean.response.VatDetailResponseBean;
import kraheja.purch.debitnotes.mappers.DbnotehMapper;
import kraheja.purch.entity.Dbnoted;
import kraheja.purch.entity.Dbnoteh;
import kraheja.purch.entity.Pbilld;
import kraheja.purch.entity.Pbillh;
import kraheja.purch.entity.Pbillvat;
import kraheja.purch.purchasebills.mappers.PbillhMapper;
import kraheja.purch.purchasebills.service.PurchBillEnquiryService;
import kraheja.purch.repository.DbnotedRepository;
import kraheja.purch.repository.DbnotehRepository;
import kraheja.purch.repository.PbilldRepository;
import kraheja.purch.repository.PbillhRepository;
import kraheja.purch.repository.PbillvatRepository;

@Service
@Transactional
public class PurchBillEnquiryServiceImpl implements PurchBillEnquiryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@PersistenceContext
	private  EntityManager entityManager;

	@Autowired
	private  EntityRepository entityRepository;

	@Autowired
	private  PbillhRepository pbillhRepository;

	@Autowired
	private  PbilldRepository pbilldRepository;

	@Autowired
	private  PbillvatRepository pbillvatRepository;

	@Autowired
	private  DbnotedRepository dbnotedRepository;
	
	@Autowired
	private  DbnotehRepository dbnotehRepository;

	@Autowired
	private  PartyRepository partyRepository;
	
	@Autowired
	private  AddressRepository addressRepository;

	@Override
	public ResponseEntity<?> fetchBillDetails(String suppCode, String billNo, String challanNo, String matGroup, String matCode, String itemCode, String radioButton) {
		Query query; 
		List<Tuple> tuplesList = null;
		if(radioButton.equals("Bill")) {
			query = this.entityManager.createNativeQuery(""
					+ "SELECT 	  \r\n"
					+ "H.PBLH_SUPPBILLNO,                to_char(D.PBLD_RATE,'99G99G99G990D99') as PBLD_RATE,                H.PBLH_AUTHNUM,                H.PBLH_AUTHDATE,                \r\n"
					+ "to_char(H.PBLH_AMOUNT,'99G99G99G990D99') as PBLH_AMOUNT,                H.PBLH_RETENTION,                H.PBLH_BILLTYPE,                H.PBLH_ADVANCEADJ,                \r\n"
					+ "D.PBLD_CESER,                H.PBLH_BLDGCODE ||'-'||(Select bldg_name   			  								  from 	 building   			  								  where  bldg_code = H.PBLH_BLDGCODE) as  bldgname,                \r\n"
					+ ":suppCode ||'-'|| (Select par_partyname   								   from 	 party  								   where	 trim(par_partycode) = :suppCode 								   and  	 \r\n"
					+ "par_partytype   = 'S'  								   and 	 par_closedate   = to_date('01/01/2050','dd/mm/yyyy')) as partyname,                H.PBLH_COY ||'-'||  \r\n"
					+ "(Select 	coy_name   										  from 	company  										  where coy_code = H.PBLH_COY and (coy_closedate is null or coy_closedate = :closeDate)) as coyname,                pbld_matgroup ||'-'||  (\r\n"
					+ "Select mat_matname   											  from material  											  where mat_matgroup = pbld_matgroup   											  and mat_level = '1') as  grpname,                \r\n"
					+ "to_char(D.PBLD_QUANTITY,'999999990D999') as PBLD_QUANTITY,                H.PBLH_SER,                D.PBLD_UOM,                H.PBLH_SUPPBILLDT,                \r\n"
					+ "to_char(D.PBLD_DEQUANTITY,'999999990D999') as PBLD_DEQUANTITY,                to_char(D.PBLD_DBQTY,'999999990D999') as  PBLD_DBQTY    ,\r\n"
					+ "To_Char(PBLH_PAIDAMT) as PaidAmt, to_char(PBLH_PAIDDATE) as DatePaid, \r\n"
					+ "PBLH_CHEQUENO, PBLH_BANKCODE, PBLH_PAIDREF,\r\n"
					+ "TO_CHAR(PBLH_TDSAMOUNT) AS TDSAMOUNT, TO_CHAR(PBLH_TDSPERC) AS PERCENTAGE, PBLH_TDSCHALNO, TO_CHAR(PBLH_TDSCHALDT) AS TDSCHALDT, PBLH_TDSBANKCODE, PBLH_TDSCERTNO, TO_CHAR(PBLH_TDCERTDT) AS TDCERTDT,\r\n"
					+ " HUND_NUM,(select par_partyname from party where(par_partycode = HUND_PARTYCODE) and par_partytype='S' and (par_closedate='01-Jan-2050'or par_closedate is null)) as HUND_PARTYCODE , HUND_PARTYTYPE,HUND_DOCNUM,HUND_DOCTSER,HUND_PAYAMT,(select bank_name from bank where bank_code=HUND_BANK) as HUND_BANK,(select coy_name from company where coy_code = HUND_COY and (coy_closedate is null or coy_closedate = to_date(:closeDate,'dd.MM.yyyy'))) as CoyNamehundi\r\n"
					+ "FROM 		  PBILLD D,                PBILLH H  ,HUNDIDET\r\n"
					+ "WHERE 	(H.PBLH_SER = D.PBLD_SER ) and      \r\n"
					+ "(HUND_DOCNUM (+) = H.PBLH_AUTHNUM ) and         \r\n"
					+ "((trim(H.PBLH_PARTYCODE) = :suppCode) AND             \r\n"
					+ "( trim(H.PBLH_SUPPBILLNO) = :billNo ))", Tuple.class);

			query.setParameter("suppCode", Objects.nonNull(suppCode) ? suppCode.trim()  : " ");
			query.setParameter("closeDate", CommonUtils.INSTANCE.closeDate());
			query.setParameter("billNo", Objects.nonNull(billNo) ? billNo.trim()  : " ");

			tuplesList = query.getResultList();
			ValueContainer<BillDetailEnquiryResponseBean> valueContainerBillDetailEnquiryResponseBean = new ValueContainer<BillDetailEnquiryResponseBean>();
			if(CollectionUtils.isNotEmpty(tuplesList)) {
				tuplesList.stream().map(t -> {
					valueContainerBillDetailEnquiryResponseBean.setValue(BillDetailEnquiryResponseBean.builder()
							.suppbillno(t.get(0, String.class))
							.rate(Objects.nonNull(t.get(1, String.class)) ? t.get(1, String.class).trim() : null)
							.authnum(t.get(2, String.class))
							.authdate(t.get(3, Timestamp.class))
							.amount(Objects.nonNull(t.get(4, String.class)) ? t.get(4, String.class).trim() : null)
							.retention(t.get(5, BigDecimal.class))
							.billtype(t.get(6, Character.class))
							.advanceadj(t.get(7, BigDecimal.class))
							.ceser(t.get(8, String.class))
							.bldgName(t.get(9, String.class))
							.partyName(t.get(10, String.class))
							.coyName(t.get(11, String.class))
							.grpName(t.get(12, String.class))
							.quantity(Objects.nonNull(t.get(13, String.class)) ? t.get(13, String.class).trim() : null)
							.ser(t.get(14, String.class))
							.uom(t.get(15, String.class))
							.suppbilldt(t.get(16, Timestamp.class))
							.dequantity(Objects.nonNull(t.get(17, String.class)) ? t.get(17, String.class).trim() : null)
							.dbqty(Objects.nonNull(t.get(18, String.class)) ? t.get(18, String.class).trim() : null)
							.paidAmt(Objects.nonNull(t.get(19, String.class)) ? t.get(19, String.class).trim() : null)
							.datePaid(t.get(20, String.class))
							.chequeno(t.get(21, String.class))
							.bankcode(t.get(22, String.class))
							.paidref(t.get(23, String.class))
							.percentage(t.get(24, String.class))
							.tdschalno(t.get(25, String.class))
							.tdschaldt(t.get(26, String.class))
							.tdsbankcode(t.get(27, String.class))
							.tdcertdt(t.get(28, String.class))
							.hundnum(t.get(29, String.class))
							.hundpartycode(t.get(30, String.class))
							.hundpartytype(t.get(31, String.class))
							.hunddocnum(t.get(32, String.class))
							.hunddoctser(t.get(33, String.class))
							.hundpayamt(Objects.nonNull(t.get(34, String.class)) ? t.get(34, String.class).trim() : null)
							.hundbank(t.get(35, String.class))
							.coyNamehundi(t.get(36, String.class))
							.build());
					return t;
				}).collect(Collectors.toList());

				Query queryForVatDetail = this.entityManager.createNativeQuery("select pblv_vatpercent,pblv_vatamount FROM pbillvat WHERE  PBLV_SER = :ser ", Tuple.class);
				queryForVatDetail.setParameter("ser", Objects.nonNull(valueContainerBillDetailEnquiryResponseBean.getValue().getSer()) ? valueContainerBillDetailEnquiryResponseBean.getValue().getSer().trim()  : " ");
				List<Tuple> tuplesListForVatDetail = queryForVatDetail.getResultList();
				List<VatDetailResponseBean> vatDetailResponseBeanList = new ArrayList<>();
				if(CollectionUtils.isNotEmpty(tuplesListForVatDetail)) {
					tuplesListForVatDetail.stream().map(t -> {
						vatDetailResponseBeanList.add(VatDetailResponseBean.builder()
								.vatpercent(t.get(0, BigDecimal.class))
								.vatamount(t.get(1, BigDecimal.class))
								.build());
						return t;
					}).collect(Collectors.toList());
					LOGGER.info("VatResponseBean :: {}", vatDetailResponseBeanList);
					if(CollectionUtils.isNotEmpty(vatDetailResponseBeanList)) {
						valueContainerBillDetailEnquiryResponseBean.getValue().setVatDetailResponseBean(vatDetailResponseBeanList);
					}
					LOGGER.info("BillDetailEnquiryResponseBean :: {}", valueContainerBillDetailEnquiryResponseBean.getValue());
				}
				Query queryForDebitNoteDetail = this.entityManager.createNativeQuery("SELECT     DBNOTEH.DBNH_SUPPBILLNO, DBNOTEH.DBNH_PARTYCODE, DBNOTEH.DBNH_DBNOTESER, TO_CHAR(DBNOTEH.DBNH_AMOUNT, \r\n"
						+ "                      '99G99G99G990D99') AS DBNH_AMOUNT, TO_CHAR(DBNOTEH.DBNH_TDSAMOUNT, '99G99G99G990D99') AS DBNH_TDSAMOUNT, \r\n"
						+ "                      DBNOTEH.DBNH_DATE, TO_CHAR(DBNOTED.DBND_QUANTITY, '999999990D999') AS DBND_QUANTITY\r\n"
						+ "FROM         DBNOTEH INNER JOIN\r\n"
						+ "                      DBNOTED ON DBNOTEH.DBNH_DBNOTESER = DBNOTED.DBND_DBNOTESER\r\n"
						+ "WHERE     (TRIM(DBNOTEH.DBNH_PARTYCODE) = :suppCode) AND (TRIM(DBNOTEH.DBNH_SUPPBILLNO) = :billNo)", Tuple.class);
				queryForDebitNoteDetail.setParameter("suppCode", Objects.nonNull(suppCode) ? suppCode.trim()  : " ");
				queryForDebitNoteDetail.setParameter("billNo", Objects.nonNull(billNo) ? billNo.trim()  : " ");

				List<Tuple> tuplesListForDebitNoteDetail = queryForDebitNoteDetail.getResultList();
				List<BillDebitNoteDetailResponseBean> billDebitNoteDetailResponseBean = new ArrayList<>();
				if(CollectionUtils.isNotEmpty(tuplesListForDebitNoteDetail)) {
					tuplesListForDebitNoteDetail.stream().map(t -> {
						billDebitNoteDetailResponseBean.add(BillDebitNoteDetailResponseBean.builder()
								.suppbillno(t.get(0, String.class))
								.partycode(t.get(1, String.class))
								.dbnoteser(t.get(2, String.class))
								.amount(Objects.nonNull(t.get(3, String.class)) ? t.get(3, String.class).trim() : null)
								.tdsamount(Objects.nonNull(t.get(4, String.class)) ? t.get(4, String.class).trim() : null)
								.date(t.get(5, Timestamp.class))
								.quantity(Objects.nonNull(t.get(6, String.class)) ? t.get(6, String.class).trim() : null)
								.build());
						return t;
					}).collect(Collectors.toList());
					LOGGER.info("VatResponseBean :: {}", billDebitNoteDetailResponseBean);
					if(CollectionUtils.isNotEmpty(billDebitNoteDetailResponseBean)) {
						valueContainerBillDetailEnquiryResponseBean.getValue().setBillDebitNoteDetailResponseBean(billDebitNoteDetailResponseBean);
					}
					LOGGER.info("BillDetailEnquiryResponseBean :: {}", valueContainerBillDetailEnquiryResponseBean.getValue());
				}
			}
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(BillDetailResponseBean.builder().billDetailEnquiryResponseBean(valueContainerBillDetailEnquiryResponseBean.getValue()).build()).build());
		}
		if(radioButton.equals("Bill-Challan")) {
			query = this.entityManager.createNativeQuery("SELECT  DCP_ENTRYNO, DCP_SUPPCODE, DCP_SUPPBILL, DCP_DCNO, DCP_BILLDT, DCP_DCDATE\r\n"
					+ "FROM         DC\r\n"
					+ "WHERE     (trim(DCP_SUPPCODE) = :suppCode) AND (trim(DCP_SUPPBILL) = :billNo)", Tuple.class);

			query.setParameter("suppCode", Objects.nonNull(suppCode) ? suppCode.trim()  : " ");
			query.setParameter("billNo", Objects.nonNull(billNo) ? billNo.trim()  : " ");
			tuplesList = query.getResultList();
			List<BillChallanDetailResponseBean> billChallanDetailResponseBean = new ArrayList<>();

			if(CollectionUtils.isNotEmpty(tuplesList)) {
				tuplesList.stream().map(t -> {
					billChallanDetailResponseBean.add(BillChallanDetailResponseBean.builder()
							.entryno(t.get(0, String.class))
							.suppcode(t.get(1, String.class))
							.suppbill(t.get(2, String.class))
							.dcno(t.get(3, String.class))
							.billdt(t.get(4, Timestamp.class))
							.dcdate(t.get(5, Timestamp.class))
							.build());
					return t;
				}).collect(Collectors.toList());

				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(BillDetailResponseBean.builder().billChallanDetailResponseBeanList(billChallanDetailResponseBean).build()).build());
			}
		}
		if(radioButton.equals("Challan")) {
			query = this.entityManager.createNativeQuery("SELECT     DCP_SUPPCODE, DCP_SUPPBILL, DCP_DCNO, DCP_BILLDT, DCP_DCDATE\r\n"
					+ "FROM         DC\r\n"
					+ "WHERE     (trim(DCP_SUPPCODE) = :suppCode) AND (trim(DCP_DCNO) = :challanNo)", Tuple.class);
			query.setParameter("suppCode", Objects.nonNull(suppCode) ? suppCode.trim()  : " ");
			query.setParameter("challanNo", Objects.nonNull(challanNo) ? challanNo.trim()  : " ");
			tuplesList = query.getResultList();
			List<BillChallanDetailResponseBean> billChallanDetailResponseBean = new ArrayList<>();

			if(CollectionUtils.isNotEmpty(tuplesList)) {
				tuplesList.stream().map(t -> {
					billChallanDetailResponseBean.add(BillChallanDetailResponseBean.builder()
							.suppcode(t.get(0, String.class))	
							.suppbill(t.get(1, String.class))
							.dcno(t.get(2, String.class))
							.billdt(t.get(3, Timestamp.class))
							.dcdate(t.get(4, Timestamp.class))
							.build());
					return t;
				}).collect(Collectors.toList());

				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(BillDetailResponseBean.builder().billChallanDetailResponseBeanList(billChallanDetailResponseBean).build()).build());
			}
		}
		if(radioButton.equals("Item")) { 
			Double month = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntId("ITRT", "00000");
			LOGGER.info("month :: {}", month);

			query = this.entityManager.createNativeQuery(" SELECT  (select 	upper(par_partyname)   	                   \r\n"
					+ "                                                       from 	party   	                   \r\n"
					+ "                                                       where 	par_partycode = OH.POH_PARTYCODE  	                   \r\n"
					+ "                                                       and 	par_partytype = 'S'   	                   \r\n"
					+ "                                                       and 	(par_closedate is null or par_closedate='01-Jan-2050')) as partyname, \r\n"
					+ "                                              	        To_Char(OH.POH_DATE,'dd.mm.yyyy') as POH_DATE,  \r\n"
					+ "                                              	        TRIM(To_char(OD.POD_RATE,'99G99G99G990D99')) as POD_RATE, \r\n"
					+ "                                              	        To_char(NVL(OD.POD_STPER,0),'99G99G99G990D99') as POD_STPER, \r\n"
					+ "                                              	        To_Char(NVL(OD.POD_EXCISEPER,0),'99G99G99G990D99') as POD_EXCISEPER,\r\n"
					+ "                                              	        To_Char(NVL(OD.POD_DISCPER,0),'99G99G99G990D99') as POD_DISCPER\r\n"
					+ "                                              FROM 	ORDERD OD,\r\n"
					+ "                                              	        ORDERH OH \r\n"
					+ "                                              WHERE   (OD.POD_SER = OH.POH_SER )\r\n"
					+ "                                              and	  ((OH.POH_DATE >= add_months(sysdate, - ".concat(String.valueOf(month.intValue())).concat(") ))\r\n")
					+ "                                              AND     (trim(OD.POD_MATGROUP) = :matGroup)\r\n"
					+ "																and ('ALL' = :matCode OR trim(OD.POD_MATCODE) = :matCode)\r\n"
					+ "																and ('ALL' = :itemCode OR trim(OD.POD_ITEMCODE) = :itemCode) ORDER BY OH.POH_DATE DESC", Tuple.class);

			query.setParameter("matGroup", Objects.nonNull(matGroup) ? matGroup.trim()  : " ");
			query.setParameter("matCode", Objects.nonNull(matCode) ? matCode.trim()  : "ALL");
			query.setParameter("itemCode", Objects.nonNull(itemCode) ? itemCode.trim()  : "ALL");

			tuplesList = query.getResultList();
			List<ItemRateDetailResponseBean> itemRateDetailResponseBean = new ArrayList<>();
			if(CollectionUtils.isNotEmpty(tuplesList)) {
				tuplesList.stream().map(t -> {
					itemRateDetailResponseBean.add(
							ItemRateDetailResponseBean.builder()
							.partyName(t.get(0, String.class))
							.date(t.get(1, String.class))
							.rate(t.get(2, String.class))
							.stper(t.get(3, String.class))
							.exciseper(t.get(4, String.class))
							.discper(t.get(5, String.class))
							.build()
							);
					return t;
				}).collect(Collectors.toList());

				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(BillDetailResponseBean.builder().itemRateDetailResponseBeanList(itemRateDetailResponseBean).build()).build());
			}
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("No Data Found").build());
	}

	@Override
	public ResponseEntity<?> fetchNewBillDetails(String partyCode, String suppCode) {
		Pbillh pbillhEntity = this.pbillhRepository.findByPblhPartycodeAndPblhSuppbillno(partyCode.trim(), suppCode.trim());
		LOGGER.info("Pbillh :: {}", pbillhEntity);		

		if(Objects.nonNull(pbillhEntity)) {
			Pbilld pbilldEntity = this.pbilldRepository.findByPbilldCK_PbldSerAndPbilldCK_PbldLineno(pbillhEntity.getPbillhCK().getPblhSer().trim(), BigInteger.ONE.intValue());
			LOGGER.info("Pbilld :: {}", pbilldEntity);

			List<Pbillvat> pbillvatEntityList = this.pbillvatRepository.findByPbillvatCK_PblvSer(pbillhEntity.getPbillhCK().getPblhSer().trim());
			LOGGER.info("Pbillvat :: {}", pbillvatEntityList);
			
			List<Dbnoteh>  dbnotehEntityList = this.dbnotehRepository.findByDbnhSuppbillno(suppCode.trim());
			LOGGER.info("dbnotehEntity {} :: ", dbnotehEntityList);
			
			List<Dbnoted> dbnotedEntityList = new ArrayList<>();
			
			if(CollectionUtils.isNotEmpty(dbnotehEntityList)) {
			dbnotedEntityList = this.dbnotedRepository.findByDbndSuppBillNo(suppCode.trim());
			LOGGER.info("dbnotedEntityList {} :: ", dbnotedEntityList);
			}
			
			Party partyEntity = this.partyRepository.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(pbillhEntity.getPblhPartycode().trim(), CommonUtils.INSTANCE.closeDateInLocalDateTime(), pbillhEntity.getPblhPartytype().trim());
			LOGGER.info("Party :: {}", partyEntity);
			
			Address addressEntity = this.addressRepository.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtypeAndAddressCK_AdrAdser(pbillhEntity.getPblhPartycode().trim(), CommonConstraints.INSTANCE.adrAdsegment, CommonConstraints.INSTANCE.addresstype, CommonConstraints.INSTANCE.adrAdser);
			LOGGER.info("Address :: {}", addressEntity);
			
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(NewBillDetailResponseBean.builder()
					.dbnotehResponseBeanList( Objects.nonNull(dbnotehEntityList) ?  DbnotehMapper.fetchDbnotehEntityPojoMapper.apply(new Object [] {dbnotehEntityList, dbnotedEntityList, null}) : null)
					.pbillhResponseBean(PbillhMapper.fetchPbillhEntityPojoMapper.apply(new Object [] {pbillhEntity, pbilldEntity, pbillvatEntityList, null, partyEntity, addressEntity})) .build()).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
	}
}