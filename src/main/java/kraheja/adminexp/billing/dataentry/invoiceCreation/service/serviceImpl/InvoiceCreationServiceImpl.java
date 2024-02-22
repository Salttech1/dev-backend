package kraheja.adminexp.billing.dataentry.invoiceCreation.service.serviceImpl;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.adminexp.billing.dataentry.adminAdvancePayment.bean.response.GenericResponse;
import kraheja.adminexp.billing.dataentry.invoiceCreation.bean.request.CombinedEntity;
import kraheja.adminexp.billing.dataentry.invoiceCreation.bean.request.CombinedEntity2;
import kraheja.adminexp.billing.dataentry.invoiceCreation.bean.request.InvoicedetailRequestBean;
import kraheja.adminexp.billing.dataentry.invoiceCreation.entity.Invoicedetail;
import kraheja.adminexp.billing.dataentry.invoiceCreation.entity.Invoiceheader;
import kraheja.adminexp.billing.dataentry.invoiceCreation.entity.InvoiceheaderCK;
import kraheja.adminexp.billing.dataentry.invoiceCreation.mappers.InvoicedetailEntityPojoMapper;
import kraheja.adminexp.billing.dataentry.invoiceCreation.mappers.InvoiceheaderEntityPojoMapper;
import kraheja.adminexp.billing.dataentry.invoiceCreation.repository.CoybillserialRepository;
import kraheja.adminexp.billing.dataentry.invoiceCreation.repository.InvPartyMasterRepository;
import kraheja.adminexp.billing.dataentry.invoiceCreation.repository.InvoicedetailRepository;
import kraheja.adminexp.billing.dataentry.invoiceCreation.repository.InvoiceheaderRepository;
import kraheja.adminexp.billing.dataentry.invoiceCreation.service.InvoiceCreationService;
import kraheja.commons.bean.ActrandBean;
import kraheja.commons.entity.Actrand;
import kraheja.commons.entity.Company;
import kraheja.commons.entity.Glchart;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.mappers.pojoentity.AddPojoEntityMapper;
import kraheja.commons.repository.ActrandRepository;
import kraheja.commons.repository.CompanyRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.repository.GlchartRepository;
import kraheja.commons.utils.CommonUtils;
import kraheja.commons.utils.GenericAccountingLogic;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@Transactional
public class InvoiceCreationServiceImpl implements InvoiceCreationService {

	@Autowired
	InvPartyMasterRepository invPartyMasterRepository;

	@Autowired
	InvoiceheaderRepository invoiceheaderRepository;

	@Autowired
	InvoicedetailRepository invoicedetailRepository;

	@Autowired
	CoybillserialRepository coybillserialRepository;

	@Autowired
	GenericCounterIncrementLogicUtil counterIncrementLogicUtil;

	@Autowired
	private GenericAccountingLogic genericAccountingLogic;

	@Autowired
	private EntityRepository entityRepository;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	ActrandRepository actrandRepository;

	@Autowired
	GlchartRepository glchartRepository;

	@Override
	public GenericResponse<List<Map<String, Object>>> fetchInvPartMasterDetails() {

		try {
			List<Object[]> invMasterList = this.invPartyMasterRepository.findInvpartyMasterData();

			if (Objects.isNull(invMasterList)) {
				return new GenericResponse<>(false, "No record found for your selections in Admadvance");
			}

			List<Map<String, Object>> keyValueList = new ArrayList<>();

			String[] columnNames = { "companyCode", "partyCode", "partyType", "partyName", "biilType", "billCode",
					"billDesc", "rate", "qty", "serviceNature", "hsnSac", "subject", "signAuth", "acMajor",
					"billNote" };

			for (Object[] row : invMasterList) {
				Map<String, Object> keyValueMap = new HashMap<>();
				for (int i = 0; i < columnNames.length; i++) {
					keyValueMap.put(columnNames[i], row[i]);
				}
				keyValueList.add(keyValueMap);
			}

			return new GenericResponse<>(true, "Data fetched successfully", keyValueList);

		} catch (Exception e) {

			return new GenericResponse<>(false, "Internal Server Error.");

		}

	}

	@Override
	public GenericResponse<CombinedEntity2> retreiveInvoiceDetails(String invoiceNo) {

		Invoiceheader invoiceheader = invoiceheaderRepository.findByInvoiceheaderCK_InvhInvoiceno(invoiceNo);

		if (invoiceheader == null) {

			return new GenericResponse<>(false, "No record found for your selections in invoice header");

		} else {

			List<Invoicedetail> invoicedetail = invoicedetailRepository.findByInvoicedetailCK_InvdInvoiceno(invoiceNo);

			CombinedEntity2 combinedEntity = CombinedEntity2.builder().invoiceheader(invoiceheader)
					.invoiceDetail(invoicedetail).build();

			return new GenericResponse<>(true, "Data retreived successfully .", combinedEntity);
		}

	}

	@Override
	public GenericResponse<String> saveInvoiceDetails(CombinedEntity combinedEntity) {
		try {

			String invoiceNum = combinedEntity.getInvoiceheaderRequestBean().getInvoiceno();

			Invoiceheader invoiceheader = invoiceheaderRepository.findByInvoiceheaderCK_InvhInvoiceno(invoiceNum);
			List<Invoicedetail> invoicedetail = null;

			if (combinedEntity.getIsUpdate() == 'Y' && Objects.nonNull(invoiceheader)) {

				invoiceheader = InvoiceheaderEntityPojoMapper.updateInvoiceheaderEntityPojoMapper.apply(invoiceheader,
						combinedEntity.getInvoiceheaderRequestBean());
				log.info("Invoice Bill Header Modification.");

				invoiceheaderRepository.save(invoiceheader);
				log.info("Invoice Bill Detail Old record Deletion.");
				invoicedetailRepository.deleteByInvoiceNum(invoiceNum);

				invoicedetail = InvoicedetailEntityPojoMapper.addInvoicedetailPojoEntityMapper
						.apply(combinedEntity.getInvoicedetailRequestBean());

				log.info("Invoice Bill Detail Creation.");
				invoicedetailRepository.saveAll(invoicedetail);

				return new GenericResponse<>(true, "Data updated successfully .", invoiceNum);

			} else {
				invoiceheader = InvoiceheaderEntityPojoMapper.addInvoiceheaderPojoEntityMapper
						.apply(combinedEntity.getInvoiceheaderRequestBean());

				String date = coybillserialRepository.findAcademicYear(combinedEntity.getInvoiceheaderRequestBean()
						.getTrandate().format(DateTimeFormatter.ofPattern("YYYY")));

				String accyear = date.substring(0, 4);
				String serNo = counterIncrementLogicUtil.generateTranNoWithCompanyCodeAndEntClassAndEntIdAndSite(
						combinedEntity.getInvoiceheaderRequestBean().getCoy(), "#INC", "IA",
						GenericAuditContextHolder.getContext().getSite(), accyear);

				InvoiceheaderCK ck = InvoiceheaderCK.builder().invhInvoiceno(serNo).build();

				invoiceheader.setInvoiceheaderCK(ck);

				for (InvoicedetailRequestBean bean : combinedEntity.getInvoicedetailRequestBean()) {
					bean.setInvoiceno(serNo);
				}

				log.info("Invoice Bill Header Creation.");

				invoiceheaderRepository.save(invoiceheader);

				log.info("Invoice Bill Detail Old record Deletion.");

				invoicedetailRepository.deleteByInvoiceNum(invoiceNum);

				invoicedetail = InvoicedetailEntityPojoMapper.addInvoicedetailPojoEntityMapper
						.apply(combinedEntity.getInvoicedetailRequestBean());

				log.info("Invoice Bill Detail Creation.");
				invoicedetailRepository.saveAll(invoicedetail);

				return new GenericResponse<>(true, "Data saved successfully .",
						invoiceheader.getInvoiceheaderCK().getInvhInvoiceno());
			}

		} catch (Exception e) {

			return new GenericResponse<>(false, "Internal Server Error.");

		}
	}

	@Override
	public GenericResponse<String> postInvoiceBill(CombinedEntity combinedEntity) {

		try {
			if (combinedEntity.getInvoiceheaderRequestBean().getPostedyn().trim().equalsIgnoreCase("Y")) {

				return new GenericResponse<>(false, "Invoice Bill already Posted.");

			}
			Company companyEntity = companyRepository.findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(
					combinedEntity.getInvoiceheaderRequestBean().getCoy().trim(), CommonUtils.INSTANCE.closeDate());
			List<Actrand> actrandEntityList = new ArrayList<Actrand>();
			Integer bunumCounter = 1;
			/* same in all breakups */
			String tranSer = GenericCounterIncrementLogicUtil.generateTranNo("#TSER", "#IA");

			Double tranAmount = Double.valueOf(combinedEntity.getInvoiceheaderRequestBean().getTranamt());

			genericAccountingLogic.updateActranh(tranSer,
					DateTimeFormatter.ofPattern("dd/MM/yyyy")
							.format(combinedEntity.getInvoiceheaderRequestBean().getTrandate()),
					"GL", combinedEntity.getInvoiceheaderRequestBean().getPartytype(),
					combinedEntity.getInvoiceheaderRequestBean().getPartycode(), tranAmount,
					combinedEntity.getInvoiceheaderRequestBean().getInvoiceno(),
					DateTimeFormatter.ofPattern("dd/MM/yyyy")
							.format(combinedEntity.getInvoiceheaderRequestBean().getTrandate()),
					"Y", "Y", "N", "N", "N", combinedEntity.getInvoiceheaderRequestBean().getRemarks(),
					combinedEntity.getInvoiceheaderRequestBean().getCoy().trim(), "Y", "N", "N", "IA", false);

//			bunumCounter = bunumCounter.equals(BigInteger.ZERO.intValue()) ? bunumCounter + 1 : bunumCounter + 2;

			List<ActrandBean> invoiceAmountBreakup = new ArrayList<>();
			List<ActrandBean> cgstAmountBreakup = new ArrayList<>();
			List<ActrandBean> sgstAmountBreakup = new ArrayList<>();
			List<ActrandBean> igstAmountBreakup = new ArrayList<>();
			List<List<ActrandBean>> listOfLists = new ArrayList<>();

			for (InvoicedetailRequestBean e : combinedEntity.getInvoicedetailRequestBean()) {

				log.info("e : {}", e);

				String acMajor = "20402005";

				String strLocAccCodeAcMinor = null;
				String strLocAccParty = null;
				String strLocAccPartyType = null;
				String strLocAccMinType = null;
				String strLocAccMinCode = null;

				List<String> glchartList = glchartRepository.findValidMinorAndValidPartiesByCharAcnum(acMajor);

				String glchart = glchartList.get(0);

				String strLocValidMinors = glchart.substring(0, glchart.indexOf(','));

				String validParty = glchart.substring(glchart.indexOf(',') + 1);

				String strLocXAccCodeAcMinor = null;
				String strLocXAccParty = null;
				String strLocXAccPartyType = null;
				String strLocXAccMinType = null;
				String strLocxAccMinCode = null;

				int strLocXBunNum;

				if (strLocValidMinors.trim().equals("") && !validParty.trim().equals("")) {
					strLocAccCodeAcMinor = combinedEntity.getInvoiceheaderRequestBean().getPartycode();
					strLocAccParty = combinedEntity.getInvoiceheaderRequestBean().getPartycode();
					strLocAccPartyType = combinedEntity.getInvoiceheaderRequestBean().getPartytype();
					strLocAccMinType = " ";
					strLocAccMinCode = " ";
				} else {
					if (!strLocValidMinors.trim().equals("") && validParty.trim().equals("")) {
						strLocAccParty = " ";
						strLocAccPartyType = " ";
						strLocAccCodeAcMinor = e.getAcminor();
						strLocAccMinType = e.getMinortype();
						strLocAccMinCode = e.getAcminor();
					} else {
						if (strLocValidMinors.trim().equals("") && validParty.trim().equals("")) {
							strLocAccParty = " ";
							strLocAccPartyType = " ";
							strLocAccCodeAcMinor = " ";
							strLocAccMinType = " ";
							strLocAccMinCode = " ";
						}
					}
				}
				if (strLocValidMinors.trim() == "" && validParty.trim() != "") {
					strLocXAccCodeAcMinor = combinedEntity.getInvoiceheaderRequestBean().getPartycode();
					strLocXAccParty = combinedEntity.getInvoiceheaderRequestBean().getPartycode();
					strLocXAccPartyType = combinedEntity.getInvoiceheaderRequestBean().getPartytype();
					strLocXAccMinType = " ";
					strLocxAccMinCode = " ";
				} else if (strLocValidMinors.trim() != "" && validParty.trim() == "") {
					strLocXAccParty = " ";
					strLocXAccPartyType = " ";

					strLocXAccCodeAcMinor = e.getAcminor();
					strLocXAccMinType = e.getMinortype();
					strLocxAccMinCode = e.getAcminor();
				} else if (strLocValidMinors.trim() == "" && validParty.trim() == "") {
					strLocXAccParty = " ";
					strLocXAccPartyType = " ";
					strLocXAccCodeAcMinor = " ";
					strLocXAccMinType = " ";
					strLocxAccMinCode = " ";
				}

				String acTranser = tranSer;
				String acBldgCode = combinedEntity.getInvoiceheaderRequestBean().getBldgcode().trim();
				String acDocNum = combinedEntity.getInvoiceheaderRequestBean().getInvoiceno().trim();
				String acDocDate = DateTimeFormatter.ofPattern("dd/MM/yyyy")
						.format(combinedEntity.getInvoiceheaderRequestBean().getTrandate());
				String acDocParType = combinedEntity.getInvoiceheaderRequestBean().getPartytype();
				String acDocParCode = combinedEntity.getInvoiceheaderRequestBean().getPartycode();

				Company company = companyRepository.findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(
						combinedEntity.getInvoiceheaderRequestBean().getCoy().trim(),
						new SimpleDateFormat("dd/MMM/yyyy").parse("01/JAN/2050"));

				invoiceAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("IA", acMajor, e.getMinortype(),
						e.getAcminor(), combinedEntity.getInvoiceheaderRequestBean().getPartytype(),
						combinedEntity.getInvoiceheaderRequestBean().getPartycode(), "GL", e.getAcminor(),
						(entityRepository.fetchChar1ByEntClassAndEntId(e.getCode())).trim(), e.getMinortype(),
						strLocXAccPartyType, strLocXAccParty, "GL", strLocXAccCodeAcMinor, 0d,
						(double) e.getTranamtgstpayable() * -1, acBldgCode, "", "", "", "",
						DateTimeFormatter.ofPattern("dd/MM/yyyy")
								.format(combinedEntity.getInvoiceheaderRequestBean().getTrandate()),
						bunumCounter, combinedEntity.getInvoiceheaderRequestBean().getRemarks(), acTranser, "GL",
						company.getCompanyCK().getCoyProp(), company.getCompanyCK().getCoyCode(),
						combinedEntity.getInvoiceheaderRequestBean().getInvoiceno(),
						DateTimeFormatter.ofPattern("dd/MM/yyyy")
								.format(combinedEntity.getInvoiceheaderRequestBean().getTrandate()),
						"",
						Objects.isNull(combinedEntity.getInvoiceheaderRequestBean().getPeriodfrom())? "": DateTimeFormatter.ofPattern("dd/MM/yyyy")
								.format(combinedEntity.getInvoiceheaderRequestBean().getPeriodfrom()),
						Objects.isNull(combinedEntity.getInvoiceheaderRequestBean().getPeriodto())? "": DateTimeFormatter.ofPattern("dd/MM/yyyy")
								.format(combinedEntity.getInvoiceheaderRequestBean().getPeriodto()),
						"", "", "", "", (double) e.getQuantity(), acDocNum, acDocDate, "", acDocParType, acDocParCode);

				invoiceAmountBreakup.get(0).setNarrative(e.getNarration());
				invoiceAmountBreakup.get(1).setNarrative(e.getNarration());

				bunumCounter += 2;

				if (e.getGstyn().trim().equalsIgnoreCase("Y")) {

					if (e.getCgstpayable() > 0 && e.getSgstpayable() > 0) {

						if (strLocValidMinors.equals("") && !validParty.equals("")) {
							strLocAccCodeAcMinor = combinedEntity.getInvoiceheaderRequestBean().getPartycode();
							strLocAccParty = combinedEntity.getInvoiceheaderRequestBean().getPartycode();
							strLocAccPartyType = combinedEntity.getInvoiceheaderRequestBean().getPartytype();
							strLocAccMinType = " ";
							strLocAccMinCode = " ";
						} else {
							if (!strLocValidMinors.equals("") && validParty.equals("")) {
								strLocAccParty = " ";
								strLocAccPartyType = " ";
								strLocAccCodeAcMinor = e.getAcminor();
								strLocAccMinType = e.getMinortype();
								strLocAccMinCode = e.getAcminor();
							} else {
								if (strLocValidMinors.equals("") && validParty.equals("")) {
									strLocAccParty = " ";
									strLocAccPartyType = " ";
									strLocAccCodeAcMinor = " ";
									strLocAccMinType = " ";
									strLocAccMinCode = " ";
								}
							}
						}
						if (strLocValidMinors == "" && validParty != "") {
							strLocXAccCodeAcMinor = combinedEntity.getInvoiceheaderRequestBean().getPartycode();
							strLocXAccParty = combinedEntity.getInvoiceheaderRequestBean().getPartycode();
							strLocXAccPartyType = combinedEntity.getInvoiceheaderRequestBean().getPartytype();
							strLocXAccMinType = " ";
							strLocxAccMinCode = " ";
						} else if (strLocValidMinors != "" && validParty == "") {
							strLocXAccParty = " ";
							strLocXAccPartyType = " ";

							strLocXAccCodeAcMinor = e.getAcminor();
							strLocXAccMinType = e.getMinortype();
							strLocxAccMinCode = e.getAcminor();
						} else if (strLocValidMinors == "" && validParty == "") {
							strLocXAccParty = " ";
							strLocXAccPartyType = " ";
							strLocXAccCodeAcMinor = " ";
							strLocXAccMinType = " ";
							strLocxAccMinCode = " ";
						}

						sgstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("IA", acMajor,
								strLocAccMinType, strLocAccMinCode,
								combinedEntity.getInvoiceheaderRequestBean().getPartytype(),
								combinedEntity.getInvoiceheaderRequestBean().getPartycode(), "GL", strLocAccCodeAcMinor,
								"11402433", strLocXAccMinType, strLocXAccPartyType, strLocXAccParty, "GL",
								strLocXAccCodeAcMinor, (double) e.getSgstpayable(), (double) e.getSgstpayable() * -1,
								acBldgCode, "", "", "", "",
								DateTimeFormatter.ofPattern("dd/MM/yyyy")
										.format(combinedEntity.getInvoiceheaderRequestBean().getTrandate()),
								bunumCounter, e.getNarration(), acTranser, "GL", company.getCompanyCK().getCoyProp(),
								company.getCompanyCK().getCoyCode(),
								combinedEntity.getInvoiceheaderRequestBean().getInvoiceno(),
								DateTimeFormatter.ofPattern("dd/MM/yyyy")
										.format(combinedEntity.getInvoiceheaderRequestBean().getTrandate()),
								"",
								Objects.isNull(combinedEntity.getInvoiceheaderRequestBean().getPeriodfrom())? "": DateTimeFormatter.ofPattern("dd/MM/yyyy")
										.format(combinedEntity.getInvoiceheaderRequestBean().getPeriodfrom()),
								Objects.isNull(combinedEntity.getInvoiceheaderRequestBean().getPeriodto())? "": DateTimeFormatter.ofPattern("dd/MM/yyyy")
										.format(combinedEntity.getInvoiceheaderRequestBean().getPeriodto()),
								"", "", "", "", (double) e.getQuantity(), acDocNum, acDocDate, "", acDocParType,
								acDocParCode);

						log.info("SGST Breakup : {}", sgstAmountBreakup);

						sgstAmountBreakup.get(0).setNarrative(e.getNarration());
						sgstAmountBreakup.get(1).setNarrative(e.getNarration());

						bunumCounter += 2;

						cgstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("IA", acMajor,
								strLocAccMinType, strLocAccMinCode,
								combinedEntity.getInvoiceheaderRequestBean().getPartytype(),
								combinedEntity.getInvoiceheaderRequestBean().getPartycode(), "GL", strLocAccCodeAcMinor,
								"11402431", strLocXAccMinType, strLocXAccPartyType, strLocXAccParty, "GL",
								strLocXAccCodeAcMinor, (double) e.getCgstpayable(), (double) e.getCgstpayable() * -1,
								acBldgCode, "", "", "", "",
								DateTimeFormatter.ofPattern("dd/MM/yyyy")
										.format(combinedEntity.getInvoiceheaderRequestBean().getTrandate()),
								bunumCounter, e.getNarration(), acTranser, "GL", company.getCompanyCK().getCoyProp(),
								company.getCompanyCK().getCoyCode(),
								combinedEntity.getInvoiceheaderRequestBean().getInvoiceno(),
								DateTimeFormatter.ofPattern("dd/MM/yyyy")
										.format(combinedEntity.getInvoiceheaderRequestBean().getTrandate()),
								"",
								Objects.isNull(combinedEntity.getInvoiceheaderRequestBean().getPeriodfrom())? "": DateTimeFormatter.ofPattern("dd/MM/yyyy")
										.format(combinedEntity.getInvoiceheaderRequestBean().getPeriodfrom()),
								Objects.isNull(combinedEntity.getInvoiceheaderRequestBean().getPeriodto())? "": DateTimeFormatter.ofPattern("dd/MM/yyyy")
										.format(combinedEntity.getInvoiceheaderRequestBean().getPeriodto()),
								"", "", "", "", (double) e.getQuantity(), acDocNum, acDocDate, "", acDocParType,
								acDocParCode);

						log.info("CGST Breakup : {}", cgstAmountBreakup);

						cgstAmountBreakup.get(0).setNarrative(e.getNarration());
						cgstAmountBreakup.get(1).setNarrative(e.getNarration());

						bunumCounter += 2;

					} else {
						if (combinedEntity.getInvoicedetailRequestBean().get(0).getIgstpayable() > 0) {

							igstAmountBreakup = GenericAccountingLogic
									.initialiseActrandBreakups("IA", acMajor, strLocAccMinType, strLocAccMinCode,
											combinedEntity.getInvoiceheaderRequestBean().getPartytype(),
											combinedEntity.getInvoiceheaderRequestBean().getPartycode(), "GL",
											strLocAccCodeAcMinor, "11402435", strLocXAccMinType, strLocXAccPartyType,
											strLocXAccParty, "GL", strLocXAccCodeAcMinor, 0d,
											(double) e.getIgstpayable() * -1, acBldgCode, "", "", "", "",
											DateTimeFormatter.ofPattern("dd/MM/yyyy")
													.format(combinedEntity.getInvoiceheaderRequestBean().getTrandate()),
											bunumCounter, e.getNarration(), acTranser, "GL",
											company.getCompanyCK().getCoyProp(), company.getCompanyCK().getCoyCode(),
											combinedEntity.getInvoiceheaderRequestBean().getInvoiceno(),
											DateTimeFormatter.ofPattern("dd/MM/yyyy")
													.format(combinedEntity.getInvoiceheaderRequestBean().getTrandate()),
											"",
											DateTimeFormatter.ofPattern("dd/MM/yyyy").format(
													combinedEntity.getInvoiceheaderRequestBean().getPeriodfrom()),
											DateTimeFormatter.ofPattern("dd/MM/yyyy")
													.format(combinedEntity.getInvoiceheaderRequestBean().getPeriodto()),
											"", "", "", "", (double) e.getQuantity(), acDocNum, acDocDate, "",
											acDocParType, acDocParCode);

							log.info("IGST Breakup : {}", igstAmountBreakup);

							bunumCounter += 2;

							igstAmountBreakup.get(0).setNarrative(e.getNarration());
							igstAmountBreakup.get(1).setNarrative(e.getNarration());
						}
					}
				}

				listOfLists.add(invoiceAmountBreakup);
				if (Objects.nonNull(cgstAmountBreakup) && Objects.nonNull(sgstAmountBreakup)) {
					listOfLists.add(cgstAmountBreakup);
					listOfLists.add(sgstAmountBreakup);
				}
				if (Objects.isNull(cgstAmountBreakup) && Objects.isNull(sgstAmountBreakup)
						&& Objects.nonNull(igstAmountBreakup)) {
					listOfLists.add(igstAmountBreakup);
				}

			}
			List<ActrandBean> actrandList = listOfLists.stream().flatMap(List::stream).collect(Collectors.toList());

			log.info("actrandList : {}", actrandList);

			actrandEntityList.addAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandList));

			log.info("actrand Entity List : {}", actrandEntityList);

			actrandRepository.saveAll(actrandEntityList);

			Invoiceheader invoiceheader = invoiceheaderRepository
					.findByInvoiceheaderCK_InvhInvoiceno(combinedEntity.getInvoiceheaderRequestBean().getInvoiceno());

			invoiceheader.setInvhPostedyn("Y");
			invoiceheader.setInvhActranser(tranSer);
			invoiceheader.setInvhOrigsite(GenericAuditContextHolder.getContext().getSite());
			invoiceheader.setInvhSite(GenericAuditContextHolder.getContext().getSite());
			invoiceheader.setInvhUserid(GenericAuditContextHolder.getContext().getUserid());
			invoiceheader.setInvhToday(LocalDateTime.now());

			log.info("invoiceheader : {}", invoiceheader);
			invoiceheaderRepository.save(invoiceheader);

			return new GenericResponse<>(true, "Data posted successfully .",
					combinedEntity.getInvoiceheaderRequestBean().getInvoiceno());
		} catch (Exception e) {
			log.debug("Error Occured", e);
			return new GenericResponse<>(false, "Data posting unsuccessful .");

		}

	}

	@Override
	public GenericResponse<Boolean> fetchPartyCodeExists(String invoiceNo) {

		Invoiceheader invoiceheader = invoiceheaderRepository.findByInvoiceheaderCK_InvhInvoiceno(invoiceNo);

		if (invoiceheader == null) {

			return new GenericResponse<>(false, "No data found", false);

		}

		if (invoiceheader.getInvhPartycode() == null || invoiceheader.getInvhPartycode().isEmpty()) {

			return new GenericResponse<>(false, "No data found", false);

		}

		else {
			return new GenericResponse<>(true, "Party code exists.", true);
		}

	}

}