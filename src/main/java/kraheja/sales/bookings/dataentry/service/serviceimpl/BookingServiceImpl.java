package kraheja.sales.bookings.dataentry.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.Address;
import kraheja.commons.entity.Party;
import kraheja.commons.mappers.pojoentity.AddressMapper;
import kraheja.commons.mappers.pojoentity.PartyMapper;
import kraheja.commons.repository.AddressRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.repository.PartyRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonResultsetGenerator;
import kraheja.commons.utils.CommonUtils;
import kraheja.sales.bean.request.BookingAltBldgRequestBean;
import kraheja.sales.bean.request.BookingRequestBean;
import kraheja.sales.bean.request.BookingWrapperBean;
import kraheja.sales.bookings.dataentry.mappers.BookingEntityPojoMapper;
import kraheja.sales.bookings.dataentry.service.BldgwingmapService;
import kraheja.sales.bookings.dataentry.service.BookingService;
import kraheja.sales.bookings.dataentry.service.FlatcharService;
import kraheja.sales.bookings.dataentry.service.FlatownerService;
import kraheja.sales.bookings.dataentry.service.FlatpayService;
import kraheja.sales.bookings.dataentry.service.FlatsService;
import kraheja.sales.bookings.dataentry.service.LoanhistoryService;
import kraheja.sales.entity.Bldgwingmap;
import kraheja.sales.entity.Booking;
import kraheja.sales.entity.Flatchar;
import kraheja.sales.entity.Flatowner;
import kraheja.sales.entity.Flatpay;
import kraheja.sales.entity.Flats;
import kraheja.sales.entity.Loanhistory;
import kraheja.sales.repository.BldgwingmapRepository;
import kraheja.sales.repository.BookingRepository;
import kraheja.sales.repository.FlatcharRepository;
import kraheja.sales.repository.FlatownerRepository;
import kraheja.sales.repository.FlatpayRepository;
import kraheja.sales.repository.FlatsRepository;
import kraheja.sales.repository.LoanhistoryRepository;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

	private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

	private static final Object NULL = null;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private PartyRepository partyRepository;

	@Autowired
	private FlatownerRepository flatownerRepository;

	@Autowired
	private FlatpayRepository flatpayRepository;
	
	@Autowired
	private  EntityRepository entityRepository;

	@Autowired
	private FlatcharRepository flatcharRepository;

	@Autowired
	private BldgwingmapRepository bldgwingmapRepository;

	@Autowired
	private LoanhistoryRepository loanhistoryRepository;

	@Autowired
	private FlatsRepository flatsRepository;

//	@Autowired
//	private EntityRepository entityRepository;
//
//	@Autowired
//	private EntityManager entityManager;

	@Autowired
	private FlatownerService flatownerService;

	@Autowired
	private FlatcharService flatcharService;

	@Autowired
	private BldgwingmapService bldgwingmapService;

	@Autowired
	private FlatsService flatsService;

	@Autowired
	private LoanhistoryService loanhistoryService;

	@Autowired
	private FlatpayService flatpayService;

//	@Autowired
//	private BookingRequestBean bookingRequestBean;
//	
//	@Autowired
//    private BookingAltBldgRequestBean bookingAltBldgRequestBean;

	@Override
	public ResponseEntity<?> fetchBookingByBldgcodeAndWingAndFlatnumAndOwnerid(String bldgcode, String wing,
			String flatnum, String ownerid) {

		String StrLocMaxPartycode = "", StrPriAltBldg = "", StrPriAltOwner_ID = "", StrBookAgPrize = "",
				StrFpaydueAmt = "", StrCompany = "", StrOwnerCoy = "";
		List<String> AddtionalExtraInfo = new ArrayList<String>();

		Booking bookingEntity = this.bookingRepository
				.findByBookingCK_BookBldgcodeAndBookingCK_BookWingAndBookingCK_BookFlatnumAndBookingCK_BookOwnerid(
						bldgcode, wing, flatnum, ownerid);
		logger.info("BookingEntity :: {}", bookingEntity);

		if (Objects.nonNull(bookingEntity)) {

			// find Alternet building owner details

			StrPriAltBldg = CommonResultsetGenerator.getStringSingleQueryValue(
					"SELECT bldg_altbldg FROM Building WHERE bldg_code='".concat(bldgcode.trim().concat("'")));

			if (StrPriAltBldg == null || StrPriAltBldg.isEmpty()) {
				// put code here
			} else {
				StrPriAltOwner_ID = StrPriAltBldg + wing + flatnum;
				// find book_agprice for Alternet Owner id
				StrBookAgPrize = CommonResultsetGenerator
						.queryToSingalValue("SELECT nvl(book_agprice,0) FROM BOOKING WHERE book_ownerid='"
								.concat(StrPriAltOwner_ID.trim().concat("'")));

				// find flatpaydue amount for Alternet Owner id
				StrFpaydueAmt = CommonResultsetGenerator
						.queryToSingalValue("Select sum(nvl(fpay_dueamount,0)) from FLATPAY WHERE FPAY_OWNERID='"
								.concat(StrPriAltOwner_ID.trim().concat("'")));

				// find company for building

				StrCompany = CommonResultsetGenerator.queryToSingalValue(
						"Select bldg_coy from building where bldg_code='".concat(bldgcode.trim().concat("'")));

				StrOwnerCoy = CommonResultsetGenerator.queryToSingalValue(
						"Select bldg_coy from building where bldg_code='".concat(StrPriAltBldg.trim().concat("'")));

				AddtionalExtraInfo.add(StrPriAltOwner_ID);
				AddtionalExtraInfo.add(StrBookAgPrize);
				AddtionalExtraInfo.add(StrFpaydueAmt);
				AddtionalExtraInfo.add(StrCompany);
				AddtionalExtraInfo.add(StrOwnerCoy);
				AddtionalExtraInfo.add(StrPriAltBldg);

			}

			// end alternet building owner details

			List<Flatowner> flatownerEntity = flatownerRepository.findByFlatownerCK_FownOwnerid(ownerid);

			List<Flatchar> flatcharEntity = flatcharRepository
					.findByFlatcharCK_FchBldgcodeAndFlatcharCK_FchFlatnumAndFlatcharCK_FchWing(bldgcode, flatnum, wing);

			Bldgwingmap bldgwingmapEntity = bldgwingmapRepository.findByBldgwingmapCK_BwmapBldgcodeAndBldgwingmapCK_BwmapBldgwing(bldgcode, wing);

			Flats flatsEntity = flatsRepository
					.findByFlatsCK_FlatBldgcodeAndFlatsCK_FlatWingAndFlatsCK_FlatFlatnum(bldgcode, wing, flatnum);

			// List<Loanhistory>
			// loanhistoryEntity=loanhistoryRepository.findByLoanhistoryCK_LhistOwnerid("B1GH
			// U0501 ");

			// List<Loanhistory> loanhistoryEntity =
			// loanhistoryRepository.findByLoanhistoryCK_LhistOwnerid(ownerid);
			List<Loanhistory> loanhistoryEntity = loanhistoryRepository.findByLoanOwnerid(ownerid.trim());

			// for our building flatpay details
			List<Flatpay> flatpayEntity = flatpayRepository.FindByFlatpayOwnerId(ownerid.trim());
			logger.info("flatpayEntity :: {}", flatpayEntity);

			// for other building flatpay details
			List<Flatpay> flatpayOtherEntity = flatpayRepository.findByFlatpayCK_FpayOwnerid(StrPriAltOwner_ID.trim());
			logger.info("flatpayOtherEntity :: {}", flatpayOtherEntity);

			// find max party code from party

			StrLocMaxPartycode = CommonResultsetGenerator
					.queryToSingalValue("select max(par_partycode) FROM party WHERE par_partycode like '%"
							.concat(ownerid.trim().concat("%'")));

			Party partyEntity = this.partyRepository
					.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(StrLocMaxPartycode,
							CommonUtils.INSTANCE.closeDateInLocalDateTime(), "F");
			logger.info("Party :: {}", partyEntity);

			Address addressMAILEntity = this.addressRepository
					.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtypeAndAddressCK_AdrAdser(
							ownerid, CommonConstraints.INSTANCE.adrAdsegment, "MAIL", "01");

			logger.info("Address :: {}", addressMAILEntity);

			Address addressPMTEntity = this.addressRepository
					.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtypeAndAddressCK_AdrAdser(
							ownerid, CommonConstraints.INSTANCE.adrAdsegment, "PMT", "01");

			logger.info("Address :: {}", addressPMTEntity);

			// its dispaly data as well as extra data

			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
					.data(BookingEntityPojoMapper.fetchBookingEntityPojoMapper.apply(new Object[] { bookingEntity,
							flatownerEntity, flatcharEntity, bldgwingmapEntity, flatsEntity, loanhistoryEntity,
							flatpayEntity, partyEntity, addressMAILEntity, addressPMTEntity, flatpayOtherEntity }))
					.extraData(AddtionalExtraInfo).build());
		}

		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No data found.").build());

	}

	@Override
	public ResponseEntity<?> fetchBookingByCopyFromAccourdingTabControl(String ownerid, Integer tabIndex) {
		String StrPriAltBldg = "", StrPriAltOwner_ID = "", Strwing = "", StrFlatnum = "";

		if (tabIndex == 1) {
			Booking bookingEntity = this.bookingRepository.findByBookingCK_bookOwnerid(ownerid);
			logger.info("BookingEntity :: {}", bookingEntity);

			List<Flatowner> flatownerEntity = flatownerRepository.findByFlatownerCK_FownOwnerid(ownerid);

			// SELECT bldg_altbldg FROM Building WHERE bldg_code
			StrPriAltBldg = CommonResultsetGenerator
					.queryToSingalValue("SELECT nvl(bldg_altbldg,'') FROM Building WHERE bldg_code='"
							.concat(bookingEntity.getBookingCK().getBookBldgcode().trim().concat("'")));

			Strwing = bookingEntity.getBookingCK().getBookWing();
			StrFlatnum = bookingEntity.getBookingCK().getBookFlatnum();
			StrPriAltOwner_ID = StrPriAltBldg + Strwing + StrFlatnum;

			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
					.data(BookingEntityPojoMapper.fetchBookingEntityPojoMapper.apply(new Object[] { bookingEntity,
							flatownerEntity, null, null, null, null, null, null, null, null, null }))
					.extraData(StrPriAltOwner_ID).build()

			);

		} else {
			if (tabIndex == 2) {

				Booking bookingEntity = this.bookingRepository.findByBookingCK_bookOwnerid(ownerid);
				logger.info("BookingEntity :: {}", bookingEntity);

				List<Flatowner> flatownerEntity = flatownerRepository.findByFlatownerCK_FownOwnerid(ownerid);
				// find max party code from party
				String StrLocMaxPartycode = "";
				StrLocMaxPartycode = CommonResultsetGenerator
						.queryToSingalValue("select max(par_partycode) FROM party WHERE par_partycode like '%"
								.concat(ownerid.trim().concat("%'")));

				Party partyEntity = this.partyRepository
						.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(StrLocMaxPartycode,
								CommonUtils.INSTANCE.closeDateInLocalDateTime(), "F");
				logger.info("Party :: {}", partyEntity);

				Address addressMAILEntity = this.addressRepository
						.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtypeAndAddressCK_AdrAdser(
								ownerid, CommonConstraints.INSTANCE.adrAdsegment, "MAIL", "01");

				logger.info("Address :: {}", addressMAILEntity);

				Address addressPMTEntity = this.addressRepository
						.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtypeAndAddressCK_AdrAdser(
								ownerid, CommonConstraints.INSTANCE.adrAdsegment, "PMT", "01");

				logger.info("Address :: {}", addressPMTEntity);

				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
						.data(BookingEntityPojoMapper.fetchBookingEntityPojoMapper
								.apply(new Object[] { bookingEntity, flatownerEntity, NULL, NULL, NULL, NULL, NULL,
										partyEntity, addressMAILEntity, addressPMTEntity, NULL }))
						.extraData(StrPriAltOwner_ID).build());

			} else {
				if (tabIndex == 3) {

					Booking bookingEntity = this.bookingRepository.findByBookingCK_bookOwnerid(ownerid);
					logger.info("BookingEntity :: {}", bookingEntity);

					List<Loanhistory> loanhistoryEntity = loanhistoryRepository
							.findByLoanhistoryCK_LhistOwnerid(ownerid);
					List<Flatpay> flatpayEntity = flatpayRepository.FindByFlatpayOwnerId(ownerid);

					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
							.data(BookingEntityPojoMapper.fetchBookingEntityPojoMapper
									.apply(new Object[] { bookingEntity, NULL, NULL, NULL, NULL, loanhistoryEntity,
											flatpayEntity, NULL, NULL, NULL, NULL }))
							.extraData(StrPriAltOwner_ID).build());
				}
			}
		}

		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No data found.").build());
	}

	@Override
	// fetchBookingStampdutyAmount
	public Integer fetchBookingStampdutyAmount(Integer IntPrmFlatCost, String StrPrmUniType, String StrPrmBookDate) {
		Integer StampDutyAmt = 0;

		StampDutyAmt = BookingCommonFunction.FuncCompute_StampDuty(IntPrmFlatCost, StrPrmUniType, StrPrmBookDate);
		return StampDutyAmt;
	}

	// addBooking1

	@Override
	public ResponseEntity<?> addBooking(BookingWrapperBean wrapperBean) {
		BookingRequestBean bookingRequestBean = wrapperBean.getBookingRequestBean();
		BookingAltBldgRequestBean bookingAltBldgRequestBean = wrapperBean.getBookingAltBldgRequestBean();
		String siteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
		
		Booking bookingEntity = this.bookingRepository
				.findByBookingCK_BookBldgcodeAndBookingCK_BookWingAndBookingCK_BookFlatnumAndBookingCK_BookOwnerid(
						bookingRequestBean.getBldgcode(), bookingRequestBean.getWing(), bookingRequestBean.getFlatnum(),
						bookingRequestBean.getOwnerid());

		if (bookingEntity == null) {
			this.bookingRepository.save(BookingEntityPojoMapper.addBookingPojoEntityMapper.apply(bookingRequestBean));

			flatownerService.addFlatowner(bookingRequestBean.getFlatownerRequestBean());

			if (bookingRequestBean.getFlatcharRequestBean() != null) {
				try {
					flatcharService.addFlatchar(bookingRequestBean.getFlatcharRequestBean());
				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Flatowner Not Inserted ").build());
				}

			}
			if (bookingRequestBean.getBldgwingmapRequestBean() != null) {
				try {
					bldgwingmapService.addBldgwingmap(bookingRequestBean.getBldgwingmapRequestBean());
				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Building wing Map Not Inserted ").build());
				}

			}
			if (bookingRequestBean.getFlatsRequestBean() != null) {
				try {
					flatsService.addFlats(bookingRequestBean.getFlatsRequestBean());
				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Flat Data Not Inserted ").build());
				}
			}
			if (bookingRequestBean.getLoanhistoryRequestBean() != null) {
				try {
					loanhistoryService.addLoanhistory(bookingRequestBean.getLoanhistoryRequestBean());
				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Loan Histry Not Inserted ").build());
				}
			}
			if (bookingRequestBean.getFlatpayRequestBean() != null) {
				try {
					flatpayService.addFlatpay(bookingRequestBean.getFlatpayRequestBean());
				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Flat Pay Data Not Inserted ").build());
				}
			}
			//comment for chk
			if (bookingRequestBean.getPartyRequestBean() != null) {
				try {
					this.partyRepository.save(PartyMapper.addPartyPojoEntityMapping
							.apply(new Object[] { bookingRequestBean.getPartyRequestBean(),
									siteFromDBEntity,
									bookingRequestBean.getPartyRequestBean().getPartycode()}));
				} catch (Exception e) {
					return ResponseEntity.ok(
							ServiceResponseBean.builder().status(Boolean.FALSE).message("Party Not Inserted ").build());
				}
			}
			if (bookingRequestBean.getAddressmailRequestBean() != null) {
				try {
					
					//LOGGER.info("Entity :: {}" , siteFromDBEntity);
					logger.info("Entity :: {}" , siteFromDBEntity);
					this.addressRepository.save(AddressMapper.addAddressPojoEntityMapping
							.apply(new Object[] { bookingRequestBean.getAddressmailRequestBean(),siteFromDBEntity,bookingRequestBean.getAddressmailRequestBean().getOwner() }));
				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Mailing Not Inserted ").build());
				}
			}
			if (bookingRequestBean.getAddresspmtRequestBean() != null) {
				try {
					this.addressRepository.save(AddressMapper.addAddressPojoEntityMapping
							.apply(new Object[] { bookingRequestBean.getAddresspmtRequestBean(),
									siteFromDBEntity,
									bookingRequestBean.getAddresspmtRequestBean().getOwner()}));
				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Perment Address Not Inserted ").build());
				}
			}

			// ************* for alternent Building ************************
			this.bookingRepository
					.save(BookingEntityPojoMapper.addAltBldgBookingPojoEntityMapper.apply(bookingAltBldgRequestBean));

			if (bookingAltBldgRequestBean.getFlatownerAltBldgRequestBean() != null) {
				try {
					flatownerService.addFlatowner(bookingAltBldgRequestBean.getFlatownerAltBldgRequestBean());
				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Flatowner Alternet Building Data Not Inserted ").build());
				}
			}
			if (bookingAltBldgRequestBean.getFlatcharAltBldgRequestBean() != null) {
				try {
					flatcharService.addFlatchar(bookingAltBldgRequestBean.getFlatcharAltBldgRequestBean());
				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Flatchar for Alternet Building Data Not Inserted ").build());
				}
			}
			if (bookingAltBldgRequestBean.getLoanhistoryAltBldgRequestBean() != null) {
				try {
					loanhistoryService.addLoanhistory(bookingAltBldgRequestBean.getLoanhistoryAltBldgRequestBean());
				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Loan Histry for Alternet Building Data Not Inserted ").build());
				}
			}
			if (bookingAltBldgRequestBean.getFlatpayAltBldgRequestBean() != null) {
				try {
					flatpayService.addFlatpay(bookingAltBldgRequestBean.getFlatpayAltBldgRequestBean());
				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Flat Pay for Alternet Building Data Not Inserted ").build());
				}
			}
			
//			if (bookingAltBldgRequestBean.getPartyAltBldgRequestBean() != null) {
//				try {
//					this.partyRepository.save(PartyMapper.addPartyPojoEntityMapping
//							.apply(new Object[] { bookingAltBldgRequestBean.getPartyAltBldgRequestBean() }));
//				} catch (Exception e) {
//					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
//							.message("Party for Alternet Building Data Not Inserted ").build());
//				}
//			}
			
			if (bookingAltBldgRequestBean.getAddressmailAltBldgRequestBean() != null) {
				try {
					this.addressRepository.save(AddressMapper.addAddressPojoEntityMapping
							.apply(new Object[] { bookingAltBldgRequestBean.getAddressmailAltBldgRequestBean()
									,siteFromDBEntity,bookingAltBldgRequestBean.getAddressmailAltBldgRequestBean().getOwner()}));
				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Mailing Address for Alternet Building Data Not Inserted ").build());
				}
			}

			if (bookingAltBldgRequestBean.getAddresspmtAltBldgRequestBean() != null) {
				try {
					this.addressRepository.save(AddressMapper.addAddressPojoEntityMapping
							.apply(new Object[] { bookingAltBldgRequestBean.getAddresspmtAltBldgRequestBean()
									,siteFromDBEntity,bookingAltBldgRequestBean.getAddressmailAltBldgRequestBean().getOwner() }));
				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Permentant Address for Alternet Building Data Not Inserted ").build());
				}
			}
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
					.message("New Booking Entry Done Successfully").build());
		}

		return ResponseEntity
				.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Data Not Saved.").build());
	}

	@Override

	public ResponseEntity<?> updateBooking1(BookingRequestBean bookingRequestBean,
			BookingAltBldgRequestBean bookingAltBldgRequestBean) {
		logger.info("bookingRequestBean :: {}", bookingRequestBean);
		Booking bookingEntity = this.bookingRepository
				.findByBookingCK_BookBldgcodeAndBookingCK_BookWingAndBookingCK_BookFlatnumAndBookingCK_BookOwnerid(
						bookingRequestBean.getBldgcode(), bookingRequestBean.getWing(), bookingRequestBean.getFlatnum(),
						bookingRequestBean.getOwnerid());

		logger.info("BookingEntity :: {}", bookingEntity);

		Booking bookingAltBldgEntity = this.bookingRepository
				.findByBookingCK_BookBldgcodeAndBookingCK_BookWingAndBookingCK_BookFlatnumAndBookingCK_BookOwnerid(
						bookingAltBldgRequestBean.getBldgcode(), bookingAltBldgRequestBean.getWing(),
						bookingAltBldgRequestBean.getFlatnum(), bookingAltBldgRequestBean.getOwnerid());

		logger.info("BookingEntity :: {}", bookingAltBldgEntity);

		if (bookingEntity != null) {

			String StrLocMaxPartycode = "";

			List<Flatowner> flatownerEntityList = flatownerRepository
					.findByFlatownerCK_FownOwnerid(bookingRequestBean.getOwnerid());

			// for alternet building flatowner update

			List<Flatowner> flatownerAlternetBldgEntityList = flatownerRepository
					.findByFlatownerCK_FownOwnerid(bookingRequestBean.getAlternentOwnerid());

			List<Flatchar> flatcharEntity = flatcharRepository
					.findByFlatcharCK_FchBldgcodeAndFlatcharCK_FchFlatnumAndFlatcharCK_FchWing(
							bookingRequestBean.getBldgcode(), bookingRequestBean.getFlatnum(),
							bookingRequestBean.getWing());

			Bldgwingmap bldgwingmapEntity = bldgwingmapRepository
					.findByBldgwingmapCK_BwmapBldgcodeAndBldgwingmapCK_BwmapBldgwing(bookingRequestBean.getBldgcode(),bookingRequestBean.getWing());

			Flats flatsEntity = flatsRepository.findByFlatsCK_FlatBldgcodeAndFlatsCK_FlatWingAndFlatsCK_FlatFlatnum(
					bookingRequestBean.getBldgcode(), bookingRequestBean.getWing(), bookingRequestBean.getFlatnum());

			List<Loanhistory> loanhistoryEntity = loanhistoryRepository
					.findByLoanhistoryCK_LhistOwnerid(bookingRequestBean.getOwnerid());

			List<Flatpay> flatpayEntity = flatpayRepository.FindByFlatpayOwnerId(bookingRequestBean.getOwnerid());

			StrLocMaxPartycode = CommonResultsetGenerator
					.queryToSingalValue("select max(par_partycode) FROM party WHERE par_partycode like '%"
							.concat(bookingRequestBean.getOwnerid().trim().concat("%'")));

			Party partyEntity = this.partyRepository
					.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(StrLocMaxPartycode,
							CommonUtils.INSTANCE.closeDateInLocalDateTime(), "F");
			logger.info("Party :: {}", partyEntity);

			Address addressMAILEntity = this.addressRepository
					.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtypeAndAddressCK_AdrAdser(
							bookingRequestBean.getOwnerid(), CommonConstraints.INSTANCE.adrAdsegment, "MAIL", "01");

			logger.info("Address :: {}", addressMAILEntity);

			Address addressPMTEntity = this.addressRepository
					.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtypeAndAddressCK_AdrAdser(
							bookingRequestBean.getOwnerid(), CommonConstraints.INSTANCE.adrAdsegment, "PMT", "01");

			this.bookingRepository.save(
					BookingEntityPojoMapper.updateBookingEntityPojoMapper.apply(bookingEntity, bookingRequestBean));

			if (Objects.nonNull(flatownerEntityList)) {
				try {
					flatownerService.updateFlatowner(bookingRequestBean.getFlatownerRequestBean(),
							bookingRequestBean.getOwnerid());
				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Flat Owner Data Not Updated ").build());
				}
			}

			if (Objects.nonNull(flatcharEntity)) {
				try {
					flatcharService.updateFlatchar(bookingRequestBean.getFlatcharRequestBean(),
							bookingRequestBean.getBldgcode(), bookingRequestBean.getFlatnum(),
							bookingRequestBean.getWing());

				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Flat Char Data Not Updated ").build());
				}
			}

			if (Objects.nonNull(bldgwingmapEntity)) {
				try {
					bldgwingmapService.updateBldgwingmap(bookingRequestBean.getBldgwingmapRequestBean());

				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Flat Char Data Not Updated ").build());
				}

			}
			if (Objects.nonNull(flatsEntity)) {
				try {
					flatsService.updateFlats(bookingRequestBean.getFlatsRequestBean());
				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Flat  Data Not Updated ").build());
				}
			}

			if (Objects.nonNull(loanhistoryEntity)) {
				try {
					loanhistoryService.updateLoanhistory(bookingRequestBean.getLoanhistoryRequestBean());
				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Loan Histry Data Not Updated ").build());
				}

			}

			if (Objects.nonNull(flatpayEntity)) {
				try {
					flatpayService.updateFlatpay(bookingRequestBean.getFlatpayRequestBean(),
							bookingRequestBean.getOwnerid());
				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Flat Pay Data Not Updated ").build());
				}
			}

			if (Objects.nonNull(partyEntity)) {
//				try {
//					this.partyRepository.save(PartyMapper.updatePartyEntityPojoMapper.apply(partyEntity, bookingRequestBean.getPartyRequestBean()));
//				} catch (Exception e) {
//					return ResponseEntity.ok(
//							ServiceResponseBean.builder().status(Boolean.FALSE).message("Party Not Updated ").build());
//				}

			}

			if (Objects.nonNull(addressMAILEntity)) {
				try {
					this.addressRepository.save(AddressMapper.updateAddressPojoEntityMapping.apply(addressMAILEntity,
							bookingRequestBean.getAddressmailRequestBean()));
				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Mail Address Not Updated ").build());
				}
			}

			if (Objects.nonNull(addressPMTEntity)) {
				try {
					this.addressRepository.save(AddressMapper.updateAddressPojoEntityMapping.apply(addressPMTEntity,
							bookingRequestBean.getAddresspmtRequestBean()));
				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Permentant Address Not Updated ").build());
				}

			}

			// ************* for alternent Building ************************

			if (Objects.nonNull(bookingAltBldgEntity)) {
				this.bookingRepository.save(BookingEntityPojoMapper.updateAltBldgBookingEntityPojoMapper
						.apply(bookingAltBldgEntity, bookingAltBldgRequestBean));

				if (Objects.nonNull(flatownerEntityList)) {
					try {
						flatownerService.updateFlatowner(bookingAltBldgRequestBean.getFlatownerAltBldgRequestBean(),
								bookingRequestBean.getAlternentOwnerid());
					} catch (Exception e) {
						return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
								.message("Flat Owner Data for Alternet Building Not Updated ").build());
					}
				}

				if (Objects.nonNull(flatcharEntity)) {
					try {
						flatcharService.updateFlatchar(bookingAltBldgRequestBean.getFlatcharAltBldgRequestBean(),
								bookingAltBldgRequestBean.getBldgcode(), bookingAltBldgRequestBean.getFlatnum(),
								bookingAltBldgRequestBean.getWing());

					} catch (Exception e) {
						return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
								.message("Flat Char Data for Alternet Building  Not Updated ").build());
					}
				}

				if (Objects.nonNull(loanhistoryEntity)) {
					try {
						loanhistoryService
								.updateLoanhistory(bookingAltBldgRequestBean.getLoanhistoryAltBldgRequestBean());
					} catch (Exception e) {
						return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
								.message("Loan Histry Data for Alternet Building  Not Updated ").build());
					}

				}

				if (Objects.nonNull(flatpayEntity)) {
					try {
						flatpayService.updateFlatpay(bookingAltBldgRequestBean.getFlatpayAltBldgRequestBean(),
								bookingAltBldgRequestBean.getOwnerid());
					} catch (Exception e) {
						return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
								.message("Flat Pay Data for Alternet Building  Not Updated ").build());
					}
				}

				if (Objects.nonNull(partyEntity)) {
//					try {
//						this.partyRepository.save(PartyMapper.updatePartyEntityPojoMapper.apply(partyEntity, bookingRequestBean.getPartyRequestBean()));
//					} catch (Exception e) {
//						return ResponseEntity.ok(
//								ServiceResponseBean.builder().status(Boolean.FALSE).message("Party Not Updated ").build());
//					}

				}

				if (Objects.nonNull(addressMAILEntity)) {
					try {
						this.addressRepository.save(AddressMapper.updateAddressPojoEntityMapping.apply(
								addressMAILEntity, bookingAltBldgRequestBean.getAddressmailAltBldgRequestBean()));
					} catch (Exception e) {
						return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
								.message("Mail Address for Alternet Building Not Updated ").build());
					}

				}

				if (Objects.nonNull(addressPMTEntity)) {
					try {
						this.addressRepository.save(AddressMapper.updateAddressPojoEntityMapping.apply(addressPMTEntity,
								bookingAltBldgRequestBean.getAddresspmtAltBldgRequestBean()));
					} catch (Exception e) {
						return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
								.message("Permentant Address for Alternet Building Not Updated ").build());
					}

				}

			}
			return ResponseEntity.ok(
					ServiceResponseBean.builder().status(Boolean.TRUE).message("Booking Modify Successfully").build());
		}

		return ResponseEntity
				.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Data Not Saved.").build());

	}

	@Override
	public ResponseEntity<?> updateBooking(BookingWrapperBean wrapperBean) {
		// BookingAltBldgRequestBean bookingAltBldgRequestBean;

		BookingRequestBean bookingRequestBean = wrapperBean.getBookingRequestBean();
		BookingAltBldgRequestBean bookingAltBldgRequestBean = wrapperBean.getBookingAltBldgRequestBean();

		Booking bookingEntity = this.bookingRepository
				.findByBookingCK_BookBldgcodeAndBookingCK_BookWingAndBookingCK_BookFlatnumAndBookingCK_BookOwnerid(
						bookingRequestBean.getBldgcode(), bookingRequestBean.getWing(), bookingRequestBean.getFlatnum(),
						bookingRequestBean.getOwnerid());

		logger.info("BookingEntity :: {}", bookingEntity);

		Booking bookingAltBldgEntity = this.bookingRepository
				.findByBookingCK_BookBldgcodeAndBookingCK_BookWingAndBookingCK_BookFlatnumAndBookingCK_BookOwnerid(
						bookingAltBldgRequestBean.getBldgcode(), bookingAltBldgRequestBean.getWing(),
						bookingAltBldgRequestBean.getFlatnum(), bookingAltBldgRequestBean.getOwnerid());

		logger.info("BookingEntity :: {}", bookingAltBldgEntity);

		if (bookingEntity != null) {

			String StrLocMaxPartycode = "";

			List<Flatowner> flatownerEntityList = flatownerRepository
					.findByFlatownerCK_FownOwnerid(bookingRequestBean.getOwnerid());

			// for alternet building flatowner update

			List<Flatowner> flatownerAlternetBldgEntityList = flatownerRepository
					.findByFlatownerCK_FownOwnerid(bookingRequestBean.getAlternentOwnerid());

			List<Flatchar> flatcharEntity = flatcharRepository
					.findByFlatcharCK_FchBldgcodeAndFlatcharCK_FchFlatnumAndFlatcharCK_FchWing(
							bookingRequestBean.getBldgcode(), bookingRequestBean.getFlatnum(),
							bookingRequestBean.getWing());

			Bldgwingmap bldgwingmapEntity = bldgwingmapRepository
					.findByBldgwingmapCK_BwmapBldgcodeAndBldgwingmapCK_BwmapBldgwing(bookingRequestBean.getBldgcode(),bookingRequestBean.getWing());

			Flats flatsEntity = flatsRepository.findByFlatsCK_FlatBldgcodeAndFlatsCK_FlatWingAndFlatsCK_FlatFlatnum(
					bookingRequestBean.getBldgcode(), bookingRequestBean.getWing(), bookingRequestBean.getFlatnum());

			List<Loanhistory> loanhistoryEntity = loanhistoryRepository
					.findByLoanhistoryCK_LhistOwnerid(bookingRequestBean.getOwnerid());

			List<Flatpay> flatpayEntity = flatpayRepository.FindByFlatpayOwnerId(bookingRequestBean.getOwnerid());

			StrLocMaxPartycode = CommonResultsetGenerator
					.queryToSingalValue("select max(par_partycode) FROM party WHERE par_partycode like '%"
							.concat(bookingRequestBean.getOwnerid().trim().concat("%'")));

			Party partyEntity = this.partyRepository
					.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(StrLocMaxPartycode,
							CommonUtils.INSTANCE.closeDateInLocalDateTime(), "F");
			logger.info("Party :: {}", partyEntity);

			Address addressMAILEntity = this.addressRepository
					.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtypeAndAddressCK_AdrAdser(
							bookingRequestBean.getOwnerid(), CommonConstraints.INSTANCE.adrAdsegment, "MAIL", "01");

			logger.info("Address :: {}", addressMAILEntity);

			Address addressPMTEntity = this.addressRepository
					.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtypeAndAddressCK_AdrAdser(
							bookingRequestBean.getOwnerid(), CommonConstraints.INSTANCE.adrAdsegment, "PMT", "01");

			this.bookingRepository.save(
					BookingEntityPojoMapper.updateBookingEntityPojoMapper.apply(bookingEntity, bookingRequestBean));

			if (Objects.nonNull(flatownerEntityList)) {
				try {
					flatownerService.updateFlatowner(bookingRequestBean.getFlatownerRequestBean(),
							bookingRequestBean.getOwnerid());
				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Flat Owner Data Not Updated ").build());
				}
			}

			if (Objects.nonNull(flatcharEntity)) {
				try {
					flatcharService.updateFlatchar(bookingRequestBean.getFlatcharRequestBean(),
							bookingRequestBean.getBldgcode(), bookingRequestBean.getFlatnum(),
							bookingRequestBean.getWing());

				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Flat Char Data Not Updated ").build());
				}
			}

			// if (Objects.nonNull(bldgwingmapEntity)) {
			if (Objects.nonNull(bookingRequestBean.getBldgwingmapRequestBean())) {
				try {
					bldgwingmapService.updateBldgwingmap(bookingRequestBean.getBldgwingmapRequestBean());

				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Flat Char Data Not Updated ").build());
				}
			}

			// commented diss with utpal sir chk
//			if (Objects.nonNull(flatsEntity)) {
//				try {
//					flatsService.updateFlats(bookingRequestBean.getFlatsRequestBean());
//				} catch (Exception e) {
//					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
//							.message("Flat  Data Not Updated ").build());
//				}
//			}

			if (Objects.nonNull(bookingRequestBean.getLoanhistoryRequestBean())) {
				try {
					loanhistoryService.updateLoanhistory(bookingRequestBean.getLoanhistoryRequestBean());
				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Loan Histry Data Not Updated ").build());
				}
			}

			if (Objects.nonNull(bookingRequestBean.getFlatpayRequestBean())) {
				try {
					flatpayService.updateFlatpay(bookingRequestBean.getFlatpayRequestBean(),
							bookingRequestBean.getOwnerid());
				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Flat Pay Data Not Updated ").build());
				}
			}

			// **********Chk After
//			if (Objects.nonNull(partyEntity)) {
//				try {
//					this.partyRepository.save(PartyMapper.updatePartyEntityPojoMapper.apply(partyEntity, bookingRequestBean.getPartyRequestBean()));
//				} catch (Exception e) {
//					return ResponseEntity.ok(
//							ServiceResponseBean.builder().status(Boolean.FALSE).message("Party Not Updated ").build());
//				}

//			}

			// if (Objects.nonNull(addressMAILEntity)) {
			if (Objects.nonNull(bookingRequestBean.getAddressmailRequestBean())) {
				try {
					this.addressRepository.save(AddressMapper.updateAddressPojoEntityMapping.apply(addressMAILEntity,
							bookingRequestBean.getAddressmailRequestBean()));
				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Mail Address Not Updated ").build());
				}
			}

			// if (Objects.nonNull(addressPMTEntity)) {
			if (Objects.nonNull(bookingRequestBean.getAddresspmtRequestBean())) {
				try {
					this.addressRepository.save(AddressMapper.updateAddressPojoEntityMapping.apply(addressPMTEntity,
							bookingRequestBean.getAddresspmtRequestBean()));
				} catch (Exception e) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Permentant Address Not Updated ").build());
				}

			}

			// ************* for alternent Building ************************

			if (Objects.nonNull(bookingAltBldgEntity)) {
				this.bookingRepository.save(BookingEntityPojoMapper.updateAltBldgBookingEntityPojoMapper
						.apply(bookingAltBldgEntity, bookingAltBldgRequestBean));

				if (Objects.nonNull(bookingAltBldgRequestBean.getFlatownerAltBldgRequestBean())) {
					try {
						flatownerService.updateFlatowner(bookingAltBldgRequestBean.getFlatownerAltBldgRequestBean(),
								bookingAltBldgRequestBean.getOwnerid());
					} catch (Exception e) {
						return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
								.message("Flat Owner Data for Alternet Building Not Updated ").build());
					}
				}

				if (Objects.nonNull(flatcharEntity)) {
					try {
						flatcharService.updateFlatchar(bookingAltBldgRequestBean.getFlatcharAltBldgRequestBean(),
								bookingAltBldgRequestBean.getBldgcode(), bookingAltBldgRequestBean.getFlatnum(),
								bookingAltBldgRequestBean.getWing());

					} catch (Exception e) {
						return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
								.message("Flat Char Data for Alternet Building  Not Updated ").build());
					}
				}

				// if (Objects.nonNull(loanhistoryEntity)) {
				if (Objects.nonNull(bookingAltBldgRequestBean.getLoanhistoryAltBldgRequestBean())) {
					try {
						loanhistoryService
								.updateLoanhistory(bookingAltBldgRequestBean.getLoanhistoryAltBldgRequestBean());
					} catch (Exception e) {
						return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
								.message("Loan Histry Data for Alternet Building  Not Updated ").build());
					}

				}

				// if (Objects.nonNull(flatpayEntity)) {
				if (Objects.nonNull(bookingAltBldgRequestBean.getFlatpayAltBldgRequestBean())) {
					try {
						flatpayService.updateFlatpay(bookingAltBldgRequestBean.getFlatpayAltBldgRequestBean(),
								bookingAltBldgRequestBean.getOwnerid());
					} catch (Exception e) {
						return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
								.message("Flat Pay Data for Alternet Building  Not Updated ").build());
					}
				}

//				if (Objects.nonNull(partyEntity)) {
//					try {
//						this.partyRepository.save(PartyMapper.updatePartyEntityPojoMapper.apply(partyEntity, bookingRequestBean.getPartyRequestBean()));
//					} catch (Exception e) {
//						return ResponseEntity.ok(
//								ServiceResponseBean.builder().status(Boolean.FALSE).message("Party Not Updated ").build());
//					}

//				}

				// if (Objects.nonNull(addressMAILEntity)) {
				if (Objects.nonNull(bookingAltBldgRequestBean.getAddressmailAltBldgRequestBean())) {
					try {
						this.addressRepository.save(AddressMapper.updateAddressPojoEntityMapping.apply(
								addressMAILEntity, bookingAltBldgRequestBean.getAddressmailAltBldgRequestBean()));
					} catch (Exception e) {
						return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
								.message("Mail Address for Alternet Building Not Updated ").build());
					}
				}

				// if (Objects.nonNull(addressPMTEntity)) {
				if (Objects.nonNull(bookingAltBldgRequestBean.getAddresspmtAltBldgRequestBean())) {
					try {
						this.addressRepository.save(AddressMapper.updateAddressPojoEntityMapping.apply(addressPMTEntity,
								bookingAltBldgRequestBean.getAddresspmtAltBldgRequestBean()));
					} catch (Exception e) {
						return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
								.message("Permentant Address for Alternet Building Not Updated ").build());
					}

				}

			}
			return ResponseEntity.ok(
					ServiceResponseBean.builder().status(Boolean.TRUE).message("Booking Modify Successfully").build());
		}

		return ResponseEntity
				.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Data Not Saved.").build());

	}

	@Override
	public ResponseEntity<?> deleteBooking(String bldgcode, String wing, String flatnum, String ownerid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> checkBldgcodeAndWingAndFlatnumAndOwneridExists(String bldgcode, String wing,
			String flatnum, String ownerid) {
		Booking bookingEntity = this.bookingRepository
				.findByBookingCK_BookBldgcodeAndBookingCK_BookWingAndBookingCK_BookFlatnumAndBookingCK_BookOwnerid(
						bldgcode, wing, flatnum, ownerid);
		logger.info("BookingEntity :: {}", bookingEntity);

		if (Objects.nonNull(bookingEntity)) {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
		}
		return ResponseEntity
				.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("This Booking Already Exist.").build());

	}

}
