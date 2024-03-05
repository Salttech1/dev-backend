package kraheja.adminexp.billing.dataentry.intercompany.service.impl;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.adminexp.billing.dataentry.entity.Intercoybilldetail;
import kraheja.adminexp.billing.dataentry.entity.Intercoybillheader;
import kraheja.adminexp.billing.dataentry.intercompany.bean.request.MinorRequest;
import kraheja.adminexp.billing.dataentry.intercompany.bean.response.MinorResponse;
import kraheja.adminexp.billing.dataentry.intercompany.service.InterCoyPostingToAccountService;
import kraheja.adminexp.billing.dataentry.repository.IntercoybillDetailRepository;
import kraheja.adminexp.billing.dataentry.repository.IntercoybillheaderRepository;
import kraheja.commons.bean.ActrandBean;
import kraheja.commons.entity.Actrand;
import kraheja.commons.entity.Company;
import kraheja.commons.mappers.pojoentity.AddPojoEntityMapper;
import kraheja.commons.repository.ActrandRepository;
import kraheja.commons.repository.CompanyRepository;
import kraheja.commons.repository.GlchartRepository;
import kraheja.commons.utils.CommonUtils;
import kraheja.commons.utils.GenericAccountingLogic;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.constant.ApiResponseCode;
import kraheja.constant.ApiResponseMessage;
import kraheja.constant.Result;
import kraheja.payload.GenericResponse;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
public class InterCoyPostingToAccountServiceImpl implements InterCoyPostingToAccountService {

	private final ActrandRepository actrandRepository;

	private final IntercoybillheaderRepository billheaderRepository;

	private final IntercoybillDetailRepository billdetailRepository;

	private final GlchartRepository glchartRepository;

	private final CompanyRepository companyRepository;

	private final GenericAccountingLogic genericAccountingLogic;

	public InterCoyPostingToAccountServiceImpl(ActrandRepository actrandRepository,
			IntercoybillheaderRepository billheaderRepository, IntercoybillDetailRepository billdetailRepository,
			GlchartRepository glchartRepository, CompanyRepository companyRepository,
			GenericAccountingLogic genericAccountingLogic) {
		super();
		this.actrandRepository = actrandRepository;
		this.billheaderRepository = billheaderRepository;
		this.billdetailRepository = billdetailRepository;
		this.glchartRepository = glchartRepository;
		this.companyRepository = companyRepository;
		this.genericAccountingLogic = genericAccountingLogic;
	}

	public GenericResponse postInterCoy(String groupInvoiceNum) {

		try {
			List<Intercoybillheader> billHeaderList = billheaderRepository.retriveHeader(groupInvoiceNum);

			if (Objects.isNull(billHeaderList)) {
				return GenericResponse.builder().message(ApiResponseMessage.BILL_DOES_NOT_EXIST)
						.responseCode(ApiResponseCode.DATA_DOES_NOT_EXIST).result(Result.FAILED).build();
			}
			if ((Objects.isNull(billHeaderList.get(0).getIcbehPostedyn()) ? ""
					: billHeaderList.get(0).getIcbehPostedyn()).equalsIgnoreCase("Y")) {
				return GenericResponse.builder().message(ApiResponseMessage.BILL_ALREADY_PASSED)
						.responseCode(ApiResponseCode.DUPLICATE_DATA_CONFLICT).result(Result.FAILED).build();
			}

			for (Intercoybillheader intercoybillheader : billHeaderList) {

				Company companyEntity1 = companyRepository.findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(
						intercoybillheader.getIcbehCoy().trim(), CommonUtils.INSTANCE.closeDate());

				String tranSer = GenericCounterIncrementLogicUtil.generateTranNo("#TSER", "#IA");
				int bunum = 1;
				int newBunum = 1;

				genericAccountingLogic.updateActranh(tranSer,
						intercoybillheader.getIcbehTrandate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), "GL",
						intercoybillheader.getIcbehPartytype(), intercoybillheader.getIcbehPartycode(),
						intercoybillheader.getIcbehTranamt(),
						intercoybillheader.getIntercoybillheaderCK().getIcbehInvoiceno().trim(),
						intercoybillheader.getIcbehTrandate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), "Y",
						"N", "N", "N", "N",
						Objects.isNull(intercoybillheader.getIcbehNarration()) ? ""
								: intercoybillheader.getIcbehNarration(),
						intercoybillheader.getIcbehCoy().trim(), "Y", "N", "N", "IA", false);

				String locICBillCoy = intercoybillheader.getIcbehPartycode().trim();
				String locICBillPartycode = intercoybillheader.getIcbehCoy().trim();
				String locICBillPartytype = "C";

				Company companyEntity2 = companyRepository.findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(
						locICBillCoy, CommonUtils.INSTANCE.closeDate());

				String locICbillAcTranser = GenericCounterIncrementLogicUtil.generateTranNo("#TSER", "#BO");
				Boolean locCompanyYN = (Objects.nonNull(companyEntity2) ? true : false);

				if (locCompanyYN) {
					genericAccountingLogic.updateActranh(locICbillAcTranser,
							intercoybillheader.getIcbehTrandate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
							"GL", locICBillPartytype, locICBillPartycode, intercoybillheader.getIcbehTranamt(),
							intercoybillheader.getIntercoybillheaderCK().getIcbehInvoiceno().trim(),
							intercoybillheader.getIcbehTrandate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
							"Y", "N", "N", "N", "N",
							Objects.isNull(intercoybillheader.getIcbehNarration()) ? ""
									: intercoybillheader.getIcbehNarration(),
							intercoybillheader.getIcbehCoy().trim(), "Y", "N", "N", "BO", false);
				}

				List<Intercoybilldetail> intercoybilldetailList = billdetailRepository
						.headerWisePartyDate(intercoybillheader.getIntercoybillheaderCK().getIcbehInvoiceno().trim());
				if (Objects.nonNull(intercoybilldetailList))
					for (Intercoybilldetail intercoybilldetail : intercoybilldetailList) {
						Double locCGSTAmount = Objects.isNull(intercoybilldetail.getIcbedCgstamt()) ? 0d
								: intercoybilldetail.getIcbedCgstamt();
						Double locSGSTAmount = Objects.isNull(intercoybilldetail.getIcbedSgstamt()) ? 0d
								: intercoybilldetail.getIcbedSgstamt();

						Double tranAmount = Objects.isNull(intercoybilldetail.getIcbedTranamt()) ? 0d
								: intercoybilldetail.getIcbedTranamt();

						List<ActrandBean> invoiceAmountBreakup = new ArrayList<>();
						List<ActrandBean> cgstAmountBreakup = new ArrayList<>();
						List<ActrandBean> sgstAmountBreakup = new ArrayList<>();
						List<ActrandBean> igstAmountBreakup = new ArrayList<>();

						String gstAcMajor = "20402005";
						Company company = companyRepository.findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(
								intercoybillheader.getIcbehCoy().trim(),
								new SimpleDateFormat("dd/MMM/yyyy").parse("01/JAN/2050"));

						MinorRequest minorRequest = MinorRequest.builder().acMajor(intercoybilldetail.getIcbedAcmajor())
								.acMinor(intercoybilldetail.getIcbedMinor())
								.minType(intercoybilldetail.getIcbedMintype())
								.partyType(intercoybillheader.getIcbehPartytype())
								.partyCode(intercoybillheader.getIcbehPartycode()).build();

						MinorResponse minorResponse = setMinorDetails(minorRequest);

						invoiceAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("IA", "20402005",
								Objects.isNull(intercoybilldetail.getIcbedMintype()) ? " "
										: intercoybilldetail.getIcbedMintype(),
								Objects.isNull(intercoybilldetail.getIcbedMinor()) ? " "
										: intercoybilldetail.getIcbedMinor(),
								intercoybillheader.getIcbehPartytype(), intercoybillheader.getIcbehPartycode(), "GL",
								Objects.isNull(intercoybilldetail.getIcbedMinor()) ? " "
										: intercoybilldetail.getIcbedMinor(),
								intercoybilldetail.getIcbedAcmajor(),
								Objects.isNull(intercoybilldetail.getIcbedMintype()) ? " "
										: intercoybilldetail.getIcbedMintype(),
								intercoybillheader.getIcbehPartytype(), intercoybillheader.getIcbehPartycode(),
								intercoybillheader.getIcbehProjcode(),
								Objects.isNull(intercoybilldetail.getIcbedMinor()) ? " "
										: intercoybilldetail.getIcbedMinor(),
								0d, (double) intercoybilldetail.getIcbedTranamt() * -1, "", "", "", "", "",
								intercoybillheader.getIcbehTrandate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
								bunum, "", tranSer, "GL", company.getCompanyCK().getCoyProp(),
								company.getCompanyCK().getCoyCode(),
								intercoybillheader.getIntercoybillheaderCK().getIcbehInvoiceno(),
								intercoybillheader.getIcbehTrandate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
								"",
								intercoybillheader.getIcbehPeriodfrom()
										.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
								intercoybillheader.getIcbehPeriodto().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
								"Q", "", "", "", 0d, intercoybillheader.getIntercoybillheaderCK().getIcbehInvoiceno(),
								intercoybillheader.getIcbehTrandate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
								"", intercoybillheader.getIcbehPartytype(), intercoybillheader.getIcbehPartycode());

						log.info("invoiceAmountBreakup : {}", invoiceAmountBreakup);
						bunum += 2;

						if (intercoybilldetail.getIcbedCgstamt() > 0 && intercoybilldetail.getIcbedSgstamt() > 0) {

							sgstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("IA", gstAcMajor,
									Objects.isNull(intercoybilldetail.getIcbedMintype()) ? " "
											: intercoybilldetail.getIcbedMintype(),
									Objects.isNull(intercoybilldetail.getIcbedMinor()) ? " "
											: intercoybilldetail.getIcbedMinor(),
									intercoybillheader.getIcbehPartytype(), intercoybillheader.getIcbehPartycode(),
									"GL",
									Objects.isNull(intercoybilldetail.getIcbedMinor()) ? " "
											: intercoybilldetail.getIcbedMinor(),
									"11402433", minorResponse.getLocXAccMinType(), minorResponse.getLocXAccPartyType(),
									minorResponse.getLocXAccParty(), "GL", minorResponse.getLocXAccCodeAcMinor(), 0d,
									locSGSTAmount * -1, "", "", "", "", "",
									intercoybillheader.getIcbehTrandate()
											.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
									bunum, "", tranSer, "GL", company.getCompanyCK().getCoyProp(),
									company.getCompanyCK().getCoyCode(),
									intercoybillheader.getIntercoybillheaderCK().getIcbehInvoiceno(),
									intercoybillheader.getIcbehTrandate()
											.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
									"",
									intercoybillheader.getIcbehPeriodfrom()
											.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
									intercoybillheader.getIcbehPeriodto()
											.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
									"Q", "", "", "", 0d,
									intercoybillheader.getIntercoybillheaderCK().getIcbehInvoiceno(),
									intercoybillheader.getIcbehTrandate()
											.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
									"", intercoybillheader.getIcbehPartytype(), intercoybillheader.getIcbehPartycode());

							log.info("SGST Breakup : {}", sgstAmountBreakup);

							bunum += 2;

							cgstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("IA", gstAcMajor,
									Objects.isNull(intercoybilldetail.getIcbedMintype()) ? " "
											: intercoybilldetail.getIcbedMintype(),
									Objects.isNull(intercoybilldetail.getIcbedMinor()) ? " "
											: intercoybilldetail.getIcbedMinor(),
									intercoybillheader.getIcbehPartytype(), intercoybillheader.getIcbehPartycode(),
									"GL",
									Objects.isNull(intercoybilldetail.getIcbedMinor()) ? " "
											: intercoybilldetail.getIcbedMinor(),
									"11402431", minorResponse.getLocXAccMinType(), minorResponse.getLocXAccPartyType(),
									minorResponse.getLocXAccParty(), "GL", minorResponse.getLocXAccCodeAcMinor(), 0d,
									locCGSTAmount * -1, "", "", "", "", "",
									intercoybillheader.getIcbehTrandate()
											.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
									bunum, "", tranSer, "GL", company.getCompanyCK().getCoyProp(),
									company.getCompanyCK().getCoyCode(),
									intercoybillheader.getIntercoybillheaderCK().getIcbehInvoiceno(),
									intercoybillheader.getIcbehTrandate()
											.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
									"",
									intercoybillheader.getIcbehPeriodfrom()
											.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
									intercoybillheader.getIcbehPeriodto()
											.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
									"Q", "", "", "", 0d,
									intercoybillheader.getIntercoybillheaderCK().getIcbehInvoiceno(),
									intercoybillheader.getIcbehTrandate()
											.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
									"", intercoybillheader.getIcbehPartytype(), intercoybillheader.getIcbehPartycode());

							log.info("CGST Breakup : {}", cgstAmountBreakup);

							bunum += 2;

						} else {
							if (intercoybilldetail.getIcbedIgstamt() > 0) {

								igstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("IA", gstAcMajor,
										minorResponse.getLocAccMinType(), minorResponse.getLocAccMinCode(),
										intercoybillheader.getIcbehPartytype(), intercoybillheader.getIcbehPartycode(),
										"GL", minorResponse.getLocAccCodeAcMinor(), "11402435",
										minorResponse.getLocXAccMinType(), minorResponse.getLocXAccPartyType(),
										minorResponse.getLocXAccParty(), "GL", minorResponse.getLocXAccCodeAcMinor(),
										0d, (double) intercoybilldetail.getIcbedIgstamt() * -1, "", "", "", "", "",
										intercoybillheader.getIcbehTrandate()
												.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
										bunum, "", tranSer, "GL", company.getCompanyCK().getCoyProp(),
										company.getCompanyCK().getCoyCode(),
										intercoybillheader.getIntercoybillheaderCK().getIcbehInvoiceno(),
										intercoybillheader
												.getIcbehTrandate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
										"",
										intercoybillheader.getIcbehPeriodfrom()
												.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
										intercoybillheader.getIcbehPeriodto()
												.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
										"Q", "", "", "", 0d,
										intercoybillheader.getIntercoybillheaderCK().getIcbehInvoiceno(),
										intercoybillheader.getIcbehTrandate()
												.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
										"", intercoybillheader.getIcbehPartytype(),
										intercoybillheader.getIcbehPartycode());

								log.info("IGST Breakup : {}", igstAmountBreakup);

								bunum += 2;
							}
						}

						List<List<ActrandBean>> listOfLists = new ArrayList<>();
						listOfLists.add(invoiceAmountBreakup);
						if (Objects.nonNull(cgstAmountBreakup) && Objects.nonNull(sgstAmountBreakup)) {
							listOfLists.add(cgstAmountBreakup);
							listOfLists.add(sgstAmountBreakup);
						}
						if (Objects.isNull(cgstAmountBreakup) && Objects.isNull(sgstAmountBreakup)
								&& Objects.nonNull(igstAmountBreakup)) {
							listOfLists.add(igstAmountBreakup);
						}

						List<ActrandBean> actrandList = listOfLists.stream().flatMap(List::stream)
								.collect(Collectors.toList());

						log.info("actrandList : {}", actrandList);

						List<Actrand> actrandEntityList = new ArrayList<Actrand>();
						actrandEntityList.addAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandList));

						log.info("Intercompany Invoice Actrand Entity List : {}", actrandEntityList);

						actrandRepository.saveAll(actrandEntityList);

						if (locCompanyYN) {
							
							genericAccountingLogic.updateActranh(locICbillAcTranser,
									intercoybillheader.getIcbehTrandate()
											.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
									"GL", locICBillPartytype, locICBillPartycode, intercoybillheader.getIcbehTranamt(),
									intercoybillheader.getIntercoybillheaderCK().getIcbehInvoiceno(),
									intercoybillheader
											.getIcbehTrandate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
									"Y", "N", "N", "N", "N",
									Objects.isNull(intercoybillheader.getIcbehNarration()) ? ""
											: intercoybillheader.getIcbehNarration(),
									locICBillCoy, "Y", "N", "N", "BO", false);

							String interCompanyAcMajor = "11401026";

							List<ActrandBean> intercompanyInvoiceBreakup = new ArrayList<>();
							List<ActrandBean> intercompanyCgstAmountBreakup = new ArrayList<>();
							List<ActrandBean> intercompanySgstAmountBreakup = new ArrayList<>();

							
							if(intercoybilldetail.getIcbedAcmajor().trim().contains("40502247")) {
								log.debug("Stop : {} ",intercoybilldetail.getIcbedAcmajor());
							}
							
							intercompanyInvoiceBreakup = GenericAccountingLogic.initialiseActrandBreakups("BO",
									interCompanyAcMajor,
									Objects.isNull(intercoybilldetail.getIcbedMintype()) ? " "
											: intercoybilldetail.getIcbedMintype(),
									Objects.isNull(intercoybilldetail.getIcbedMinor()) ? " "
											: intercoybilldetail.getIcbedMinor().trim(),
									intercoybillheader.getIcbehPartytype(), intercoybillheader.getIcbehCoy(), "GL",
									Objects.isNull(intercoybilldetail.getIcbedMinor()) ? " "
											: intercoybilldetail.getIcbedMinor(),
									intercoybilldetail.getIcbedAcmajor(),
									Objects.isNull(intercoybilldetail.getIcbedMintype()) ? " "
											: intercoybilldetail.getIcbedMintype(),
									intercoybillheader.getIcbehPartytype(),
									intercoybillheader.getIcbehPartycode().trim(),
									intercoybillheader.getIcbehRecbillprojcode(), minorResponse.getLocXAccCodeAcMinor(),
									0d, (double) intercoybilldetail.getIcbedTranamt(), "", "", "", "", "",
									intercoybillheader.getIcbehTrandate()
											.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
									newBunum, "", locICbillAcTranser, "GL",
									intercoybillheader.getIcbehPartycode().trim(),
									intercoybillheader.getIcbehPartycode().trim(),
									intercoybillheader.getIntercoybillheaderCK().getIcbehInvoiceno(),
									intercoybillheader.getIcbehTrandate()
											.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
									"",
									intercoybillheader.getIcbehPeriodfrom()
											.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
									intercoybillheader.getIcbehPeriodto()
											.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
									"Q", "", "", "", 0d,
									intercoybillheader.getIntercoybillheaderCK().getIcbehInvoiceno(),
									intercoybillheader.getIcbehTrandate()
											.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
									"", intercoybillheader.getIcbehPartytype(), intercoybillheader.getIcbehCoy());

							newBunum += 2;

							if (intercoybilldetail.getIcbedSgstamt() > 0 && intercoybilldetail.getIcbedCgstamt() > 0) {

								intercompanySgstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("BO",
										interCompanyAcMajor,
										Objects.isNull(intercoybilldetail.getIcbedMintype()) ? " "
												: intercoybilldetail.getIcbedMintype(),
										Objects.isNull(intercoybilldetail.getIcbedMinor()) ? " "
												: intercoybilldetail.getIcbedMinor(),
										intercoybillheader.getIcbehPartytype(), intercoybillheader.getIcbehCoy(), "GL",
										Objects.isNull(intercoybilldetail.getIcbedMinor()) ? " "
												: intercoybilldetail.getIcbedMinor(),
										"20404391",
										Objects.isNull(intercoybilldetail.getIcbedMintype()) ? " "
												: intercoybilldetail.getIcbedMintype(),
										intercoybillheader.getIcbehPartytype(),
										intercoybillheader.getIcbehPartycode().trim(), "GL",
										minorResponse.getLocXAccCodeAcMinor(), 0d, locSGSTAmount, "", "", "", "", "",
										intercoybillheader.getIcbehTrandate()
												.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
										newBunum, "", locICbillAcTranser, "GL", intercoybillheader.getIcbehPartycode().trim(),
										intercoybillheader.getIcbehPartycode().trim(),
										intercoybillheader.getIntercoybillheaderCK().getIcbehInvoiceno(),
										intercoybillheader.getIcbehTrandate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
										"",
										intercoybillheader.getIcbehPeriodfrom()
												.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
										intercoybillheader.getIcbehPeriodto()
												.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
										"", "", "", "", 0d,
										intercoybillheader.getIntercoybillheaderCK().getIcbehInvoiceno(),
										intercoybillheader.getIcbehTrandate()
												.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
										"", intercoybillheader.getIcbehPartytype(),
										intercoybillheader.getIcbehCoy());

								log.info("intercompanySgstAmountBreakup : {}", intercompanySgstAmountBreakup);

								newBunum += 2;

								intercompanyCgstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("BO",
										interCompanyAcMajor,
										Objects.isNull(intercoybilldetail.getIcbedMintype()) ? " "
												: intercoybilldetail.getIcbedMintype(),
										Objects.isNull(intercoybilldetail.getIcbedMinor()) ? " "
												: intercoybilldetail.getIcbedMinor(),
										intercoybillheader.getIcbehPartytype(), intercoybillheader.getIcbehCoy(),
										"GL",Objects.isNull(intercoybilldetail.getIcbedMinor()) ? " "
												: intercoybilldetail.getIcbedMinor(),
										"20404392",Objects.isNull(intercoybilldetail.getIcbedMintype()) ? " "
												: intercoybilldetail.getIcbedMintype(),
										intercoybillheader.getIcbehPartytype(),intercoybillheader.getIcbehPartycode().trim(), "GL",
										minorResponse.getLocXAccCodeAcMinor(), 0d, locCGSTAmount, "", "", "", "", "",
										intercoybillheader.getIcbehTrandate()
												.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
										newBunum, "", locICbillAcTranser, "GL",intercoybillheader.getIcbehPartycode().trim(),
										intercoybillheader.getIcbehPartycode().trim(),
										intercoybillheader.getIntercoybillheaderCK().getIcbehInvoiceno(),
										intercoybillheader.getIcbehTrandate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
										"",
										intercoybillheader.getIcbehPeriodfrom()
												.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
										intercoybillheader.getIcbehPeriodto()
												.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
										"", "", "", "", 0d,
										intercoybillheader.getIntercoybillheaderCK().getIcbehInvoiceno(),
										intercoybillheader.getIcbehTrandate()
												.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
										"", intercoybillheader.getIcbehPartytype(),
										intercoybillheader.getIcbehCoy());

								log.info("intercompanyCgstAmountBreakup {}", intercompanyCgstAmountBreakup);

								newBunum += 2;

							}
							List<List<ActrandBean>> listOfLists2 = new ArrayList<>();
							listOfLists2.add(intercompanyInvoiceBreakup);
							if (Objects.nonNull(intercompanyCgstAmountBreakup)
									&& Objects.nonNull(intercompanySgstAmountBreakup)) {
								listOfLists2.add(intercompanySgstAmountBreakup);
								listOfLists2.add(intercompanyCgstAmountBreakup);
							}

							List<ActrandBean> actrandList2 = listOfLists2.stream().flatMap(List::stream)
									.collect(Collectors.toList());
							log.info("actrandList : {}", actrandList2);

							List<Actrand> actrandEntityList2 = new ArrayList<Actrand>();
							actrandEntityList2
									.addAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandList2));

							log.info("Intercompany Bill Actrand Entity List : {}", actrandEntityList2);

							actrandRepository.saveAll(actrandEntityList2);

						}
					}

				List<Intercoybillheader> interCoyBillHeaderlist = billheaderRepository
						.fetchIntercoybillheadrerByInvoiceNoAndCoy(
								intercoybillheader.getIntercoybillheaderCK().getIcbehInvoiceno(),
								intercoybillheader.getIcbehCoy().trim());

				if (locCompanyYN) {
					for (Intercoybillheader intercoybillheader2 : interCoyBillHeaderlist) {
						intercoybillheader2.setIcbehInvoicetranser(tranSer);
						intercoybillheader2.setIcbehBilltranser(locICbillAcTranser);
						intercoybillheader2.setIcbehPostedyn("Y");
						log.info("intercoybillheader2 : {}", intercoybillheader2);
						billheaderRepository.save(intercoybillheader2);
					}
				} else {
					for (Intercoybillheader intercoybillheader2 : interCoyBillHeaderlist) {
						intercoybillheader2.setIcbehInvoicetranser(tranSer);
						intercoybillheader2.setIcbehPostedyn("Y");
						log.info("intercoybillheader2 : {}", intercoybillheader2);
						billheaderRepository.save(intercoybillheader2);
					}

				}
			}

			return GenericResponse.builder().message(ApiResponseMessage.SUCCESSFULLY_PERSIST)
					.responseCode(ApiResponseCode.SUCCESS).result(Result.SUCCESS).build();
		} catch (Exception e) {
			e.printStackTrace();
			return GenericResponse.builder().message(ApiResponseMessage.EXCEPTION_OCCURE)
					.responseCode(ApiResponseCode.FAILED).result(Result.FAILED).build();
		}

	}

	public MinorResponse setMinorDetails(MinorRequest minorRequest) {

		String locAccCodeAcMinor = "";
		String locAccParty = "";
		String locAccPartyType = "";
		String locAccMinType = "";
		String locAccMinCode = "";
		String locXAccCodeAcMinor = "";
		String locXAccParty = "";
		String locXAccPartyType = "";
		String locXAccMinType = "";
		String locxAccMinCode = "";
		List<String> glchartList = glchartRepository
				.findValidMinorAndValidPartiesByCharAcnum(minorRequest.getAcMajor());

		String glchart = glchartList.get(0);

		String strLocValidMinors = glchart.substring(0, glchart.indexOf(','));

		String validParty = glchart.substring(glchart.indexOf(',') + 1);

		if (strLocValidMinors.trim().equals("") && !validParty.trim().equals("")) {
			locAccCodeAcMinor = minorRequest.getPartyCode();
			locAccParty = minorRequest.getPartyCode();
			locAccPartyType = minorRequest.getPartyType();
			locAccMinType = " ";
			locAccMinCode = " ";
		} else {
			if (!strLocValidMinors.trim().equals("") && validParty.trim().equals("")) {
				locAccParty = " ";
				locAccPartyType = " ";
				locAccCodeAcMinor = minorRequest.getAcMinor();
				locAccMinType = minorRequest.getMinType();
				locAccMinCode = minorRequest.getAcMinor();
			} else {
				if (strLocValidMinors.trim().equals("") && validParty.trim().equals("")) {
					locAccParty = " ";
					locAccPartyType = " ";
					locAccCodeAcMinor = " ";
					locAccMinType = " ";
					locAccMinCode = " ";
				}
			}
		}
		if (strLocValidMinors.trim() == "" && validParty.trim() != "") {
			locXAccCodeAcMinor = minorRequest.getPartyCode();
			locXAccParty = minorRequest.getPartyCode();
			locXAccPartyType = minorRequest.getPartyType();
			locXAccMinType = " ";
			locxAccMinCode = " ";
		} else if (strLocValidMinors.trim() != "" && validParty.trim() == "") {
			locXAccParty = " ";
			locXAccPartyType = " ";
			locXAccCodeAcMinor = minorRequest.getAcMinor();
			locXAccMinType = minorRequest.getMinType();
			locxAccMinCode = minorRequest.getAcMinor();
		} else if (strLocValidMinors.trim() == "" && validParty.trim() == "") {
			locXAccParty = " ";
			locXAccPartyType = " ";
			locXAccCodeAcMinor = " ";
			locXAccMinType = " ";
			locxAccMinCode = " ";
		}

		MinorResponse minorResponse = MinorResponse.builder().locAccCodeAcMinor(locAccCodeAcMinor)
				.locAccMinCode(locAccMinCode).locAccMinType(locAccMinType).locAccParty(locAccParty)
				.locAccPartyType(locAccPartyType).locXAccCodeAcMinor(locXAccCodeAcMinor).locXAccMinType(locXAccMinType)
				.locxAccMinCode(locxAccMinCode).locXAccParty(locXAccParty).locXAccPartyType(locXAccPartyType).build();
		return minorResponse;

	}
}
