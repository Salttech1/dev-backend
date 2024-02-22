// Developed By  - 	sandesh.c
// Developed on - 18-08-23
// Mode  - Data Entry
// Purpose - Booking Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.bookings.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.commons.collections4.CollectionUtils;

import kraheja.commons.entity.Address;
import kraheja.commons.entity.Party;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.mappers.pojoentity.AddressMapper;
import kraheja.commons.mappers.pojoentity.PartyMapper;
import kraheja.commons.utils.CommonConstraints;
import kraheja.sales.bean.request.BookingAltBldgRequestBean;
import kraheja.sales.bean.request.BookingRequestBean;
import kraheja.sales.bean.response.BookingResponseBean;
import kraheja.sales.bean.response.BookingResponseBean.BookingResponseBeanBuilder;
import kraheja.sales.entity.Bldgwingmap;
import kraheja.sales.entity.Booking;
import kraheja.sales.entity.BookingCK;
import kraheja.sales.entity.Flatchar;
import kraheja.sales.entity.Flatowner;
import kraheja.sales.entity.Flatpay;
import kraheja.sales.entity.Flats;
import kraheja.sales.entity.Loanhistory;

public interface BookingEntityPojoMapper {

	@SuppressWarnings("unchecked")
	public static Function<Object[], BookingResponseBean> fetchBookingEntityPojoMapper = objectArray -> {

		Booking bookingEntity = (Booking) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
				: null);

//		Flatowner flatownerEntity = (Flatowner) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
//				? objectArray[BigInteger.ONE.intValue()]
//				: null);

		List<Flatowner> flatownerEntity = (List<Flatowner>) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
				? objectArray[BigInteger.ONE.intValue()]
				: null);

		List<Flatchar> flatcharEntity = (List<Flatchar>) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.TWO])
				? objectArray[CommonConstraints.INSTANCE.TWO]
				: null);

		Bldgwingmap bldgwingmapEntity = (Bldgwingmap) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.THREE])
				? objectArray[CommonConstraints.INSTANCE.THREE]
				: null);

		Flats flatsEntity = (Flats) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.FOUR])
				? objectArray[CommonConstraints.INSTANCE.FOUR]
				: null);

		List<Loanhistory> loanhistoryEntity = (List<Loanhistory>) (Objects.nonNull(
				objectArray[CommonConstraints.INSTANCE.FIVE]) ? objectArray[CommonConstraints.INSTANCE.FIVE] : null);

		List<Flatpay> flatpayEntity = (List<Flatpay>) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.SIX])
				? objectArray[CommonConstraints.INSTANCE.SIX]
				: null);

		Party partyEntity = (Party) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.SEVEN])
				? objectArray[CommonConstraints.INSTANCE.SEVEN]
				: null);

		Address addressMAILEntity = (Address) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.EIGHT])
				? objectArray[CommonConstraints.INSTANCE.EIGHT]
				: null);

		Address addressPMTEntity = (Address) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.NINE])
				? objectArray[CommonConstraints.INSTANCE.NINE]
				: null);

		List<Flatpay> flatpayOtherEntity = (List<Flatpay>) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.TEN])
				? objectArray[CommonConstraints.INSTANCE.TEN]
				: null);

		BookingResponseBeanBuilder bookingResponseBean = BookingResponseBean.builder();
		bookingResponseBean.bldgcode(bookingEntity.getBookingCK().getBookBldgcode())
				.wing(bookingEntity.getBookingCK().getBookWing()).flatnum(bookingEntity.getBookingCK().getBookFlatnum())
				.ownerid(bookingEntity.getBookingCK().getBookOwnerid())
				// .aadharno(bookingEntity.getBookAadharno())
				.accomtype(bookingEntity.getBookAccomtype()).agprice(bookingEntity.getBookAgprice())
				.amtos(bookingEntity.getBookAmtos()).amtrec(bookingEntity.getBookAmtrec())
				.area(bookingEntity.getBookArea()).bookedby(bookingEntity.getBookBookedby())
				.brokent(bookingEntity.getBookBrokent()).broker(bookingEntity.getBookBroker())
				.brokos(bookingEntity.getBookBrokos()).brokpaid(bookingEntity.getBookBrokpaid())
				.broktdsamd(bookingEntity.getBookBroktdsamd()).broktdsamt(bookingEntity.getBookBroktdsamt())
				.broktdsper(bookingEntity.getBookBroktdsper()).broktranser(bookingEntity.getBookBroktranser())
				.cancelled(Objects.nonNull(bookingEntity.getBookCancelled())
						? bookingEntity.getBookCancelled().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
						: null)
				.community(bookingEntity.getBookCommunity())
				.contracton(Objects.nonNull(bookingEntity.getBookContracton())
						? bookingEntity.getBookContracton().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
						: null)
				.customercoy(bookingEntity.getBookCustomercoy()).custtype(bookingEntity.getBookCusttype())
				.date(Objects.nonNull(bookingEntity.getBookDate())
						? bookingEntity.getBookDate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
						: null)
				.designation(bookingEntity.getBookDesignation()).discount(bookingEntity.getBookDiscount())
				.firstvisitdate(Objects.nonNull(bookingEntity.getBookFirstvisitdate())
						? bookingEntity.getBookFirstvisitdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
						: null)
				.firstvisitexec(bookingEntity.getBookFirstvisitexec()).floor(bookingEntity.getBookFloor())
				.gstno(bookingEntity.getBookGstno())
				.ho2owner(Objects.nonNull(bookingEntity.getBookHo2owner())
						? bookingEntity.getBookHo2owner().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
						: null)
				.jobprofile(bookingEntity.getBookJobprofile())
				.leaddate(Objects.nonNull(bookingEntity.getBookLeaddate())
						? bookingEntity.getBookLeaddate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
						: null)
				.leasedto(bookingEntity.getBookLeasedto()).leaseref(bookingEntity.getBookLeaseref())
				.ledby(bookingEntity.getBookLedby()).maintrate(bookingEntity.getBookMaintrate())
				.mpaiddate(Objects.nonNull(bookingEntity.getBookMpaiddate())
						? bookingEntity.getBookMpaiddate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
						: null)
				.mpaidref(bookingEntity.getBookMpaidref()).mpaidyymm(bookingEntity.getBookMpaidyymm())
				.origsite(bookingEntity.getBookOrigsite())
				.overon(Objects.nonNull(bookingEntity.getBookOveron())
						? bookingEntity.getBookOveron().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
						: null)
				.panum(bookingEntity.getBookPanum()).poacode(bookingEntity.getBookPoacode())
				.poaname(bookingEntity.getBookPoaname())
				.regfees(bookingEntity.getBookRegfees() )
				.regno(bookingEntity.getBookRegno())
				 .regprice(bookingEntity.getBookRegprice())
				.remarks(bookingEntity.getBookRemarks()).salestatus(bookingEntity.getBookSalestatus())
				.saletype(bookingEntity.getBookSaletype())
				.scheduledpossession(bookingEntity.getBookScheduledpossession())
				.serialnum(bookingEntity.getBookSerialnum()).site(bookingEntity.getBookSite())
				.soi(bookingEntity.getBookSoi()).today(bookingEntity.getBookToday())
				.userid(bookingEntity.getBookUserid())
				.validtill(Objects.nonNull(bookingEntity.getBookValidtill())
						? bookingEntity.getBookValidtill().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
						: null)
				.build();

		// Flat Owner Details

//		if (Objects.nonNull(flatownerEntity))
//			bookingResponseBean.flatownerResponseBean(
//					FlatownerEntityPojoMapper.fetchFlatownerEntityPojoMapper.apply(new Object[] { flatownerEntity }));

		if (CollectionUtils.isNotEmpty(flatownerEntity))
			bookingResponseBean.flatownerResponseBean(
					FlatownerEntityPojoMapper.fetchFlatownerEntityPojoMapper.apply(flatownerEntity));

		// flatcharEntity

		if (CollectionUtils.isNotEmpty(flatcharEntity))
			bookingResponseBean
					.flatcharResponseBean(FlatcharEntityPojoMapper.fetchFlatcharEntityPojoMapper.apply(flatcharEntity));

//	//bldgwingmapEntity
		if (Objects.nonNull(bldgwingmapEntity))
			bookingResponseBean.bldgwingmapResponseBean(BldgwingmapEntityPojoMapper.fetchBldgwingmapEntityPojoMapper
					.apply(new Object[] { bldgwingmapEntity }));

//	//flatsEntity
		if (Objects.nonNull(flatsEntity))
			bookingResponseBean.flatsResponseBean(
					FlatsEntityPojoMapper.fetchFlatsEntityPojoMapper.apply(new Object[] { flatsEntity }));

//	//loanhistoryEntity
		if (CollectionUtils.isNotEmpty(loanhistoryEntity))
			bookingResponseBean.loanhistoryResponseBean(
					LoanhistoryEntityPojoMapper.fetchLoanhistoryEntityPojoMapper.apply(loanhistoryEntity));

//flatpayEntity	

		if (CollectionUtils.isNotEmpty(flatpayEntity)) {
			if (flatpayEntity.stream().allMatch(Objects::isNull)) {
				System.out.println("All elements are null.");
			} else {
				bookingResponseBean
						.flatpayResponseBean(FlatpayEntityPojoMapper.fetchFlatpayEntityPojoMapper.apply(flatpayEntity));
			}
		}

//flatpayOtherEntity
		if (CollectionUtils.isNotEmpty(flatpayOtherEntity)) {
			if (flatpayOtherEntity.stream().allMatch(Objects::isNull)) {
				System.out.println("All elements are null.");
			} else {
				bookingResponseBean
				.flatpayOtherBldgResponseBean(FlatpayEntityPojoMapper
						.fetchFlatpayOtherBldgEntityPojoMapper.apply(flatpayOtherEntity));
				
//						.fetchFlatpayEntityPojoMapper.apply(flatpayOtherEntity));
//				.flatpayOtherBldgResponseBean(
//						FlatpayEntityPojoMapper.fetchFlatpayEntityPojoMapper.apply(flatpayOtherEntity));
			}
		}

//		if (flatpayEntity.stream().allMatch(Objects::isNull)) {
//			System.out.println("All elements are null.");
//		} else {
//			if (!CollectionUtils.isEmpty(flatpayEntity))
//				bookingResponseBean
//						.flatpayResponseBean(FlatpayEntityPojoMapper.fetchFlatpayEntityPojoMapper.apply(flatpayEntity));
//		}

		// Party
		if (Objects.nonNull(partyEntity))
			bookingResponseBean
					.partyResponseBean(PartyMapper.fetchPartyEntityPojoMapper.apply(new Object[] { partyEntity }));

		// Address MAIL
		if (Objects.nonNull(addressMAILEntity))
			bookingResponseBean
					.addressmailResponseBean(AddressMapper.AddressEntityPojoMapper.fetchAddressEntityPojoMapper
							.apply(new Object[] { addressMAILEntity }));

		// Address PMT
		if (Objects.nonNull(addressPMTEntity))
			bookingResponseBean
					.addresspmtResponseBean(AddressMapper.AddressEntityPojoMapper.fetchAddressEntityPojoMapper
							.apply(new Object[] { addressPMTEntity }));

		return bookingResponseBean.build();

	};

	public static Function<BookingRequestBean, Booking> addBookingPojoEntityMapper = bookingRequestBean -> {
		return Booking.builder()
				.bookingCK(BookingCK.builder().bookBldgcode(bookingRequestBean.getBldgcode())
						.bookWing(bookingRequestBean.getWing()).bookFlatnum(bookingRequestBean.getFlatnum())
						.bookOwnerid(bookingRequestBean.getOwnerid()).build())

				// .bookAadharno(bookingRequestBean.getAadharno())
				.bookAccomtype(bookingRequestBean.getAccomtype())
				.bookAgprice(Objects.nonNull(bookingRequestBean.getAgprice()) ? bookingRequestBean.getAgprice()
						: BigInteger.ZERO.doubleValue())
				.bookAmtos(Objects.nonNull(bookingRequestBean.getAmtos()) ? bookingRequestBean.getAmtos()
						: BigInteger.ZERO.doubleValue())
				.bookAmtrec(Objects.nonNull(bookingRequestBean.getAmtrec()) ? bookingRequestBean.getAmtrec()
						: BigInteger.ZERO.doubleValue())
				.bookArea(bookingRequestBean.getArea()).bookBookedby(bookingRequestBean.getBookedby())
				.bookBrokent(Objects.nonNull(bookingRequestBean.getBrokent()) ? bookingRequestBean.getBrokent()
						: BigInteger.ZERO.doubleValue())
				.bookBroker(bookingRequestBean.getBroker())
				.bookBrokos(Objects.nonNull(bookingRequestBean.getBrokos()) ? bookingRequestBean.getBrokos()
						: BigInteger.ZERO.doubleValue())
				.bookBrokpaid(Objects.nonNull(bookingRequestBean.getBrokpaid()) ? bookingRequestBean.getBrokpaid()
						: BigInteger.ZERO.doubleValue())
				.bookBroktdsamd(Objects.nonNull(bookingRequestBean.getBroktdsamd()) ? bookingRequestBean.getBroktdsamd()
						: BigInteger.ZERO.doubleValue())
				.bookBroktdsamt(Objects.nonNull(bookingRequestBean.getBroktdsamt()) ? bookingRequestBean.getBroktdsamt()
						: BigInteger.ZERO.doubleValue())
				.bookBroktdsper(Objects.nonNull(bookingRequestBean.getBroktdsper()) ? bookingRequestBean.getBroktdsper()
						: BigInteger.ZERO.doubleValue())
				.bookBroktranser(bookingRequestBean.getBroktranser())
				// .bookCancelled(Objects.nonNull(bookingRequestBean.getCancelled()) ?
				// LocalDate.parse(bookingRequestBean.getCancelled(),
				// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.bookCommunity(bookingRequestBean.getCommunity())
				// .bookContracton(Objects.nonNull(bookingRequestBean.getContracton()) ?
				// LocalDate.parse(bookingRequestBean.getContracton(),
				// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.bookCustomercoy(bookingRequestBean.getCustomercoy()).bookCusttype(bookingRequestBean.getCusttype())
				// .bookDate(Objects.nonNull(bookingRequestBean.getDate()) ?
				// LocalDate.parse(bookingRequestBean.getDate(),
				// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.bookDesignation(bookingRequestBean.getDesignation())
				.bookDiscount(Objects.nonNull(bookingRequestBean.getDiscount()) ? bookingRequestBean.getDiscount()
						: BigInteger.ZERO.doubleValue())
				// .bookFirstvisitdate(Objects.nonNull(bookingRequestBean.getFirstvisitdate()) ?
				// LocalDate.parse(bookingRequestBean.getFirstvisitdate(),
				// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.bookFirstvisitexec(bookingRequestBean.getFirstvisitexec()).bookFloor(bookingRequestBean.getFloor())
				.bookGstno(bookingRequestBean.getGstno())
				// .bookHo2owner(Objects.nonNull(bookingRequestBean.getHo2owner()) ?
				// LocalDate.parse(bookingRequestBean.getHo2owner(),
				// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.bookJobprofile(bookingRequestBean.getJobprofile())
				// .bookLeaddate(Objects.nonNull(bookingRequestBean.getLeaddate()) ?
				// LocalDate.parse(bookingRequestBean.getLeaddate(),
				// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.bookLeasedto(bookingRequestBean.getLeasedto()).bookLeaseref(bookingRequestBean.getLeaseref())
				.bookLedby(bookingRequestBean.getLedby())
				.bookMaintrate(Objects.nonNull(bookingRequestBean.getMaintrate()) ? bookingRequestBean.getMaintrate()
						: BigInteger.ZERO.doubleValue())
				// .bookMpaiddate(Objects.nonNull(bookingRequestBean.getMpaiddate()) ?
				// LocalDate.parse(bookingRequestBean.getMpaiddate(),
				// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.bookMpaidref(bookingRequestBean.getMpaidref()).bookMpaidyymm(bookingRequestBean.getMpaidyymm())
				.bookOrigsite(GenericAuditContextHolder.getContext().getSite())
				// .bookOveron(Objects.nonNull(bookingRequestBean.getOveron()) ?
				// LocalDate.parse(bookingRequestBean.getOveron(),
				// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.bookPanum(bookingRequestBean.getPanum()).bookPoacode(bookingRequestBean.getPoacode())
				.bookPoaname(bookingRequestBean.getPoaname())
				// .bookRegfees(Objects.nonNull(bookingRequestBean.getRegfees()) ?
				// bookingRequestBean.getRegfees() : BigInteger.ZERO.intValue())
				.bookRegno(bookingRequestBean.getRegno())
				// .bookRegprice(Objects.nonNull(bookingRequestBean.getRegprice()) ?
				// bookingRequestBean.getRegprice() : BigInteger.ZERO.intValue())
				.bookRemarks(bookingRequestBean.getRemarks()).bookSalestatus(bookingRequestBean.getSalestatus())
				.bookSaletype(bookingRequestBean.getSaletype())
				.bookScheduledpossession(bookingRequestBean.getScheduledpossession())
				.bookSerialnum(bookingRequestBean.getSerialnum())
				.bookSite(GenericAuditContextHolder.getContext().getSite()).bookSoi(bookingRequestBean.getSoi())
				.bookToday(LocalDateTime.now()).bookUserid(GenericAuditContextHolder.getContext().getUserid())
				// .bookValidtill(Objects.nonNull(bookingRequestBean.getValidtill()) ?
				// LocalDate.parse(bookingRequestBean.getValidtill(),
				// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)

				.build();
	};

	public static Function<BookingAltBldgRequestBean, Booking> addAltBldgBookingPojoEntityMapper = bookingAltBldgRequestBean -> {
		return Booking.builder()
				.bookingCK(BookingCK.builder().bookBldgcode(bookingAltBldgRequestBean.getBldgcode())
						.bookWing(bookingAltBldgRequestBean.getWing())
						.bookFlatnum(bookingAltBldgRequestBean.getFlatnum())
						.bookOwnerid(bookingAltBldgRequestBean.getOwnerid()).build())

				// .bookAadharno(bookingAltBldgRequestBean.getAadharno())
				.bookAccomtype(bookingAltBldgRequestBean.getAccomtype())
				.bookAgprice(
						Objects.nonNull(bookingAltBldgRequestBean.getAgprice()) ? bookingAltBldgRequestBean.getAgprice()
								: BigInteger.ZERO.doubleValue())
				.bookAmtos(Objects.nonNull(bookingAltBldgRequestBean.getAmtos()) ? bookingAltBldgRequestBean.getAmtos()
						: BigInteger.ZERO.doubleValue())
				.bookAmtrec(
						Objects.nonNull(bookingAltBldgRequestBean.getAmtrec()) ? bookingAltBldgRequestBean.getAmtrec()
								: BigInteger.ZERO.doubleValue())
				.bookArea(bookingAltBldgRequestBean.getArea()).bookBookedby(bookingAltBldgRequestBean.getBookedby())
				.bookBrokent(
						Objects.nonNull(bookingAltBldgRequestBean.getBrokent()) ? bookingAltBldgRequestBean.getBrokent()
								: BigInteger.ZERO.doubleValue())
				.bookBroker(bookingAltBldgRequestBean.getBroker())
				.bookBrokos(
						Objects.nonNull(bookingAltBldgRequestBean.getBrokos()) ? bookingAltBldgRequestBean.getBrokos()
								: BigInteger.ZERO.doubleValue())
				.bookBrokpaid(Objects.nonNull(bookingAltBldgRequestBean.getBrokpaid())
						? bookingAltBldgRequestBean.getBrokpaid()
						: BigInteger.ZERO.doubleValue())
				.bookBroktdsamd(Objects.nonNull(bookingAltBldgRequestBean.getBroktdsamd())
						? bookingAltBldgRequestBean.getBroktdsamd()
						: BigInteger.ZERO.doubleValue())
				.bookBroktdsamt(Objects.nonNull(bookingAltBldgRequestBean.getBroktdsamt())
						? bookingAltBldgRequestBean.getBroktdsamt()
						: BigInteger.ZERO.doubleValue())
				.bookBroktdsper(Objects.nonNull(bookingAltBldgRequestBean.getBroktdsper())
						? bookingAltBldgRequestBean.getBroktdsper()
						: BigInteger.ZERO.doubleValue())
				.bookBroktranser(bookingAltBldgRequestBean.getBroktranser())
				// .bookCancelled(Objects.nonNull(bookingAltBldgRequestBean.getCancelled()) ?
				// LocalDate.parse(bookingAltBldgRequestBean.getCancelled(),
				// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.bookCommunity(bookingAltBldgRequestBean.getCommunity())
				// .bookContracton(Objects.nonNull(bookingAltBldgRequestBean.getContracton()) ?
				// LocalDate.parse(bookingAltBldgRequestBean.getContracton(),
				// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.bookCustomercoy(bookingAltBldgRequestBean.getCustomercoy())
				.bookCusttype(bookingAltBldgRequestBean.getCusttype())
				// .bookDate(Objects.nonNull(bookingAltBldgRequestBean.getDate()) ?
				// LocalDate.parse(bookingAltBldgRequestBean.getDate(),
				// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.bookDesignation(bookingAltBldgRequestBean.getDesignation())
				.bookDiscount(Objects.nonNull(bookingAltBldgRequestBean.getDiscount())
						? bookingAltBldgRequestBean.getDiscount()
						: BigInteger.ZERO.doubleValue())
				// .bookFirstvisitdate(Objects.nonNull(bookingAltBldgRequestBean.getFirstvisitdate())
				// ?
				// LocalDate.parse(bookingAltBldgRequestBean.getFirstvisitdate(),
				// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.bookFirstvisitexec(bookingAltBldgRequestBean.getFirstvisitexec())
				.bookFloor(bookingAltBldgRequestBean.getFloor()).bookGstno(bookingAltBldgRequestBean.getGstno())
				// .bookHo2owner(Objects.nonNull(bookingAltBldgRequestBean.getHo2owner()) ?
				// LocalDate.parse(bookingAltBldgRequestBean.getHo2owner(),
				// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.bookJobprofile(bookingAltBldgRequestBean.getJobprofile())
				// .bookLeaddate(Objects.nonNull(bookingAltBldgRequestBean.getLeaddate()) ?
				// LocalDate.parse(bookingAltBldgRequestBean.getLeaddate(),
				// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.bookLeasedto(bookingAltBldgRequestBean.getLeasedto())
				.bookLeaseref(bookingAltBldgRequestBean.getLeaseref()).bookLedby(bookingAltBldgRequestBean.getLedby())
				.bookMaintrate(Objects.nonNull(bookingAltBldgRequestBean.getMaintrate())
						? bookingAltBldgRequestBean.getMaintrate()
						: BigInteger.ZERO.doubleValue())
				// .bookMpaiddate(Objects.nonNull(bookingAltBldgRequestBean.getMpaiddate()) ?
				// LocalDate.parse(bookingAltBldgRequestBean.getMpaiddate(),
				// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.bookMpaidref(bookingAltBldgRequestBean.getMpaidref())
				.bookMpaidyymm(bookingAltBldgRequestBean.getMpaidyymm())
				.bookOrigsite(GenericAuditContextHolder.getContext().getSite())
				// .bookOveron(Objects.nonNull(bookingAltBldgRequestBean.getOveron()) ?
				// LocalDate.parse(bookingAltBldgRequestBean.getOveron(),
				// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.bookPanum(bookingAltBldgRequestBean.getPanum()).bookPoacode(bookingAltBldgRequestBean.getPoacode())
				.bookPoaname(bookingAltBldgRequestBean.getPoaname())
				// .bookRegfees(Objects.nonNull(bookingAltBldgRequestBean.getRegfees()) ?
				// bookingAltBldgRequestBean.getRegfees() : BigInteger.ZERO.intValue())
				.bookRegno(bookingAltBldgRequestBean.getRegno())
				// .bookRegprice(Objects.nonNull(bookingAltBldgRequestBean.getRegprice()) ?
				// bookingAltBldgRequestBean.getRegprice() : BigInteger.ZERO.intValue())
				.bookRemarks(bookingAltBldgRequestBean.getRemarks())
				.bookSalestatus(bookingAltBldgRequestBean.getSalestatus())
				.bookSaletype(bookingAltBldgRequestBean.getSaletype())
				.bookScheduledpossession(bookingAltBldgRequestBean.getScheduledpossession())
				.bookSerialnum(bookingAltBldgRequestBean.getSerialnum())
				.bookSite(GenericAuditContextHolder.getContext().getSite()).bookSoi(bookingAltBldgRequestBean.getSoi())
				.bookToday(LocalDateTime.now()).bookUserid(GenericAuditContextHolder.getContext().getUserid())
				// .bookValidtill(Objects.nonNull(bookingAltBldgRequestBean.getValidtill()) ?
				// LocalDate.parse(bookingAltBldgRequestBean.getValidtill(),
				// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)

				.build();
	};

	public static BiFunction<Booking, BookingRequestBean, Booking> updateBookingEntityPojoMapper = (bookingEntity,
			bookingRequestBean) -> {
		bookingEntity.getBookingCK().setBookBldgcode(
				Objects.nonNull(bookingRequestBean.getBldgcode()) ? bookingRequestBean.getBldgcode().trim()
						: bookingEntity.getBookingCK().getBookBldgcode());
		bookingEntity.getBookingCK()
				.setBookWing(Objects.nonNull(bookingRequestBean.getWing()) ? bookingRequestBean.getWing().trim()
						: bookingEntity.getBookingCK().getBookWing());
		bookingEntity.getBookingCK().setBookFlatnum(
				Objects.nonNull(bookingRequestBean.getFlatnum()) ? bookingRequestBean.getFlatnum().trim()
						: bookingEntity.getBookingCK().getBookFlatnum());
		bookingEntity.getBookingCK().setBookOwnerid(
				Objects.nonNull(bookingRequestBean.getOwnerid()) ? bookingRequestBean.getOwnerid().trim()
						: bookingEntity.getBookingCK().getBookOwnerid());

		// bookingEntity.setBookAadharno(Objects.nonNull(bookingRequestBean.getAadharno())
		// ? bookingRequestBean.getAadharno().trim() : bookingEntity.getBookAadharno());
		bookingEntity.setBookAccomtype(
				Objects.nonNull(bookingRequestBean.getAccomtype()) ? bookingRequestBean.getAccomtype().trim()
						: bookingEntity.getBookAccomtype());
		bookingEntity.setBookAgprice(Objects.nonNull(bookingRequestBean.getAgprice()) ? bookingRequestBean.getAgprice()
				: bookingEntity.getBookAgprice());
		bookingEntity.setBookAmtos(Objects.nonNull(bookingRequestBean.getAmtos()) ? bookingRequestBean.getAmtos()
				: bookingEntity.getBookAmtos());
		bookingEntity.setBookAmtrec(Objects.nonNull(bookingRequestBean.getAmtrec()) ? bookingRequestBean.getAmtrec()
				: bookingEntity.getBookAmtrec());
		bookingEntity.setBookArea(Objects.nonNull(bookingRequestBean.getArea()) ? bookingRequestBean.getArea().trim()
				: bookingEntity.getBookArea());
		bookingEntity.setBookBookedby(
				Objects.nonNull(bookingRequestBean.getBookedby()) ? bookingRequestBean.getBookedby().trim()
						: bookingEntity.getBookBookedby());
		bookingEntity.setBookBrokent(Objects.nonNull(bookingRequestBean.getBrokent()) ? bookingRequestBean.getBrokent()
				: bookingEntity.getBookBrokent());
		bookingEntity
				.setBookBroker(Objects.nonNull(bookingRequestBean.getBroker()) ? bookingRequestBean.getBroker().trim()
						: bookingEntity.getBookBroker());
		bookingEntity.setBookBrokos(Objects.nonNull(bookingRequestBean.getBrokos()) ? bookingRequestBean.getBrokos()
				: bookingEntity.getBookBrokos());
		bookingEntity
				.setBookBrokpaid(Objects.nonNull(bookingRequestBean.getBrokpaid()) ? bookingRequestBean.getBrokpaid()
						: bookingEntity.getBookBrokpaid());
		bookingEntity.setBookBroktdsamd(
				Objects.nonNull(bookingRequestBean.getBroktdsamd()) ? bookingRequestBean.getBroktdsamd()
						: bookingEntity.getBookBroktdsamd());
		bookingEntity.setBookBroktdsamt(
				Objects.nonNull(bookingRequestBean.getBroktdsamt()) ? bookingRequestBean.getBroktdsamt()
						: bookingEntity.getBookBroktdsamt());
		bookingEntity.setBookBroktdsper(
				Objects.nonNull(bookingRequestBean.getBroktdsper()) ? bookingRequestBean.getBroktdsper()
						: bookingEntity.getBookBroktdsper());
		bookingEntity.setBookBroktranser(
				Objects.nonNull(bookingRequestBean.getBroktranser()) ? bookingRequestBean.getBroktranser().trim()
						: bookingEntity.getBookBroktranser());
		// bookingEntity.setBookCancelled(Objects.nonNull(bookingRequestBean.getCancelled())
		// ? LocalDate.parse(bookingRequestBean.getCancelled(),
		// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) :
		// bookingEntity.getBookCancelled());
		bookingEntity.setBookCommunity(
				Objects.nonNull(bookingRequestBean.getCommunity()) ? bookingRequestBean.getCommunity().trim()
						: bookingEntity.getBookCommunity());
		// bookingEntity.setBookContracton(Objects.nonNull(bookingRequestBean.getContracton())
		// ? LocalDate.parse(bookingRequestBean.getContracton(),
		// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) :
		// bookingEntity.getBookContracton());
		bookingEntity.setBookCustomercoy(
				Objects.nonNull(bookingRequestBean.getCustomercoy()) ? bookingRequestBean.getCustomercoy().trim()
						: bookingEntity.getBookCustomercoy());
		bookingEntity.setBookCusttype(
				Objects.nonNull(bookingRequestBean.getCusttype()) ? bookingRequestBean.getCusttype().trim()
						: bookingEntity.getBookCusttype());
		// bookingEntity.setBookDate(Objects.nonNull(bookingRequestBean.getDate()) ?
		// LocalDate.parse(bookingRequestBean.getDate(),
		// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) :
		// bookingEntity.getBookDate());
		bookingEntity.setBookDesignation(
				Objects.nonNull(bookingRequestBean.getDesignation()) ? bookingRequestBean.getDesignation().trim()
						: bookingEntity.getBookDesignation());
		bookingEntity
				.setBookDiscount(Objects.nonNull(bookingRequestBean.getDiscount()) ? bookingRequestBean.getDiscount()
						: bookingEntity.getBookDiscount());
		// bookingEntity.setBookFirstvisitdate(Objects.nonNull(bookingRequestBean.getFirstvisitdate())
		// ? LocalDate.parse(bookingRequestBean.getFirstvisitdate(),
		// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) :
		// bookingEntity.getBookFirstvisitdate());
		bookingEntity.setBookFirstvisitexec(
				Objects.nonNull(bookingRequestBean.getFirstvisitexec()) ? bookingRequestBean.getFirstvisitexec().trim()
						: bookingEntity.getBookFirstvisitexec());
		bookingEntity.setBookFloor(Objects.nonNull(bookingRequestBean.getFloor()) ? bookingRequestBean.getFloor().trim()
				: bookingEntity.getBookFloor());
		bookingEntity.setBookGstno(Objects.nonNull(bookingRequestBean.getGstno()) ? bookingRequestBean.getGstno().trim()
				: bookingEntity.getBookGstno());
		// bookingEntity.setBookHo2owner(Objects.nonNull(bookingRequestBean.getHo2owner())
		// ? LocalDate.parse(bookingRequestBean.getHo2owner(),
		// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) :
		// bookingEntity.getBookHo2owner());
		bookingEntity.setBookJobprofile(
				Objects.nonNull(bookingRequestBean.getJobprofile()) ? bookingRequestBean.getJobprofile().trim()
						: bookingEntity.getBookJobprofile());
		// bookingEntity.setBookLeaddate(Objects.nonNull(bookingRequestBean.getLeaddate())
		// ? LocalDate.parse(bookingRequestBean.getLeaddate(),
		// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) :
		// bookingEntity.getBookLeaddate());
		bookingEntity.setBookLeasedto(
				Objects.nonNull(bookingRequestBean.getLeasedto()) ? bookingRequestBean.getLeasedto().trim()
						: bookingEntity.getBookLeasedto());
		bookingEntity.setBookLeaseref(
				Objects.nonNull(bookingRequestBean.getLeaseref()) ? bookingRequestBean.getLeaseref().trim()
						: bookingEntity.getBookLeaseref());
		bookingEntity.setBookLedby(Objects.nonNull(bookingRequestBean.getLedby()) ? bookingRequestBean.getLedby().trim()
				: bookingEntity.getBookLedby());
		bookingEntity
				.setBookMaintrate(Objects.nonNull(bookingRequestBean.getMaintrate()) ? bookingRequestBean.getMaintrate()
						: bookingEntity.getBookMaintrate());
		// bookingEntity.setBookMpaiddate(Objects.nonNull(bookingRequestBean.getMpaiddate())
		// ? LocalDate.parse(bookingRequestBean.getMpaiddate(),
		// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) :
		// bookingEntity.getBookMpaiddate());
		bookingEntity.setBookMpaidref(
				Objects.nonNull(bookingRequestBean.getMpaidref()) ? bookingRequestBean.getMpaidref().trim()
						: bookingEntity.getBookMpaidref());
		bookingEntity.setBookMpaidyymm(
				Objects.nonNull(bookingRequestBean.getMpaidyymm()) ? bookingRequestBean.getMpaidyymm().trim()
						: bookingEntity.getBookMpaidyymm());
		bookingEntity.setBookOrigsite(GenericAuditContextHolder.getContext().getSite());
		// bookingEntity.setBookOveron(Objects.nonNull(bookingRequestBean.getOveron()) ?
		// LocalDate.parse(bookingRequestBean.getOveron(),
		// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) :
		// bookingEntity.getBookOveron());
		bookingEntity.setBookPanum(Objects.nonNull(bookingRequestBean.getPanum()) ? bookingRequestBean.getPanum().trim()
				: bookingEntity.getBookPanum());
		bookingEntity.setBookPoacode(
				Objects.nonNull(bookingRequestBean.getPoacode()) ? bookingRequestBean.getPoacode().trim()
						: bookingEntity.getBookPoacode());
		bookingEntity.setBookPoaname(
				Objects.nonNull(bookingRequestBean.getPoaname()) ? bookingRequestBean.getPoaname().trim()
						: bookingEntity.getBookPoaname());
		// bookingEntity.setBookRegfees(Objects.nonNull(bookingRequestBean.getRegfees())
		// ? bookingRequestBean.getRegfees() : bookingEntity.getBookRegfees());
		bookingEntity.setBookRegno(Objects.nonNull(bookingRequestBean.getRegno()) ? bookingRequestBean.getRegno().trim()
				: bookingEntity.getBookRegno());
		// bookingEntity.setBookRegprice(Objects.nonNull(bookingRequestBean.getRegprice())
		// ? bookingRequestBean.getRegprice() : bookingEntity.getBookRegprice());
		bookingEntity.setBookRemarks(
				Objects.nonNull(bookingRequestBean.getRemarks()) ? bookingRequestBean.getRemarks().trim()
						: bookingEntity.getBookRemarks());
		bookingEntity.setBookSalestatus(
				Objects.nonNull(bookingRequestBean.getSalestatus()) ? bookingRequestBean.getSalestatus().trim()
						: bookingEntity.getBookSalestatus());
		bookingEntity.setBookSaletype(
				Objects.nonNull(bookingRequestBean.getSaletype()) ? bookingRequestBean.getSaletype().trim()
						: bookingEntity.getBookSaletype());
		bookingEntity.setBookScheduledpossession(Objects.nonNull(bookingRequestBean.getScheduledpossession())
				? bookingRequestBean.getScheduledpossession().trim()
				: bookingEntity.getBookScheduledpossession());
		bookingEntity.setBookSerialnum(
				Objects.nonNull(bookingRequestBean.getSerialnum()) ? bookingRequestBean.getSerialnum().trim()
						: bookingEntity.getBookSerialnum());
		bookingEntity.setBookSoi(Objects.nonNull(bookingRequestBean.getSoi()) ? bookingRequestBean.getSoi().trim()
				: bookingEntity.getBookSoi());

		bookingEntity.setBookSite(bookingRequestBean.getSite());
		bookingEntity.setBookToday(LocalDateTime.now());
		bookingEntity.setBookUserid(bookingRequestBean.getUserid());

//		bookingEntity.setBookSite(GenericAuditContextHolder.getContext().getSite());
//		bookingEntity.setBookToday(LocalDateTime.now());
//		bookingEntity.setBookUserid(GenericAuditContextHolder.getContext().getUserid());

		// bookingEntity.setBookValidtill(Objects.nonNull(bookingRequestBean.getValidtill())
		// ? LocalDate.parse(bookingRequestBean.getValidtill(),
		// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) :
		// bookingEntity.getBookValidtill());

		return bookingEntity;
	};

	// for alternet booking
	public static BiFunction<Booking, BookingAltBldgRequestBean, Booking> updateAltBldgBookingEntityPojoMapper = (
			bookingAltBldgEntity, bookingAltBldgRequestBean) -> {

		bookingAltBldgEntity.getBookingCK()
				.setBookBldgcode(Objects.nonNull(bookingAltBldgRequestBean.getBldgcode())
						? bookingAltBldgRequestBean.getBldgcode().trim()
						: bookingAltBldgEntity.getBookingCK().getBookBldgcode());
		bookingAltBldgEntity.getBookingCK()
				.setBookWing(Objects.nonNull(bookingAltBldgRequestBean.getWing())
						? bookingAltBldgRequestBean.getWing().trim()
						: bookingAltBldgEntity.getBookingCK().getBookWing());
		bookingAltBldgEntity.getBookingCK()
				.setBookFlatnum(Objects.nonNull(bookingAltBldgRequestBean.getFlatnum())
						? bookingAltBldgRequestBean.getFlatnum().trim()
						: bookingAltBldgEntity.getBookingCK().getBookFlatnum());
		bookingAltBldgEntity.getBookingCK()
				.setBookOwnerid(Objects.nonNull(bookingAltBldgRequestBean.getOwnerid())
						? bookingAltBldgRequestBean.getOwnerid().trim()
						: bookingAltBldgEntity.getBookingCK().getBookOwnerid());

		// bookingAltBldgEntity.setBookAadharno(Objects.nonNull(bookingAltBldgRequestBean.getAadharno())
		// ? bookingAltBldgRequestBean.getAadharno().trim() :
		// bookingAltBldgEntity.getBookAadharno());
		bookingAltBldgEntity.setBookAccomtype(Objects.nonNull(bookingAltBldgRequestBean.getAccomtype())
				? bookingAltBldgRequestBean.getAccomtype().trim()
				: bookingAltBldgEntity.getBookAccomtype());
		bookingAltBldgEntity.setBookAgprice(
				Objects.nonNull(bookingAltBldgRequestBean.getAgprice()) ? bookingAltBldgRequestBean.getAgprice()
						: bookingAltBldgEntity.getBookAgprice());
		bookingAltBldgEntity.setBookAmtos(
				Objects.nonNull(bookingAltBldgRequestBean.getAmtos()) ? bookingAltBldgRequestBean.getAmtos()
						: bookingAltBldgEntity.getBookAmtos());
		bookingAltBldgEntity.setBookAmtrec(
				Objects.nonNull(bookingAltBldgRequestBean.getAmtrec()) ? bookingAltBldgRequestBean.getAmtrec()
						: bookingAltBldgEntity.getBookAmtrec());
		bookingAltBldgEntity.setBookArea(
				Objects.nonNull(bookingAltBldgRequestBean.getArea()) ? bookingAltBldgRequestBean.getArea().trim()
						: bookingAltBldgEntity.getBookArea());
		bookingAltBldgEntity.setBookBookedby(Objects.nonNull(bookingAltBldgRequestBean.getBookedby())
				? bookingAltBldgRequestBean.getBookedby().trim()
				: bookingAltBldgEntity.getBookBookedby());
		bookingAltBldgEntity.setBookBrokent(
				Objects.nonNull(bookingAltBldgRequestBean.getBrokent()) ? bookingAltBldgRequestBean.getBrokent()
						: bookingAltBldgEntity.getBookBrokent());
		bookingAltBldgEntity.setBookBroker(
				Objects.nonNull(bookingAltBldgRequestBean.getBroker()) ? bookingAltBldgRequestBean.getBroker().trim()
						: bookingAltBldgEntity.getBookBroker());
		bookingAltBldgEntity.setBookBrokos(
				Objects.nonNull(bookingAltBldgRequestBean.getBrokos()) ? bookingAltBldgRequestBean.getBrokos()
						: bookingAltBldgEntity.getBookBrokos());
		bookingAltBldgEntity.setBookBrokpaid(
				Objects.nonNull(bookingAltBldgRequestBean.getBrokpaid()) ? bookingAltBldgRequestBean.getBrokpaid()
						: bookingAltBldgEntity.getBookBrokpaid());
		bookingAltBldgEntity.setBookBroktdsamd(
				Objects.nonNull(bookingAltBldgRequestBean.getBroktdsamd()) ? bookingAltBldgRequestBean.getBroktdsamd()
						: bookingAltBldgEntity.getBookBroktdsamd());
		bookingAltBldgEntity.setBookBroktdsamt(
				Objects.nonNull(bookingAltBldgRequestBean.getBroktdsamt()) ? bookingAltBldgRequestBean.getBroktdsamt()
						: bookingAltBldgEntity.getBookBroktdsamt());
		bookingAltBldgEntity.setBookBroktdsper(
				Objects.nonNull(bookingAltBldgRequestBean.getBroktdsper()) ? bookingAltBldgRequestBean.getBroktdsper()
						: bookingAltBldgEntity.getBookBroktdsper());
		bookingAltBldgEntity.setBookBroktranser(Objects.nonNull(bookingAltBldgRequestBean.getBroktranser())
				? bookingAltBldgRequestBean.getBroktranser().trim()
				: bookingAltBldgEntity.getBookBroktranser());
		// bookingAltBldgEntity.setBookCancelled(Objects.nonNull(bookingAltBldgRequestBean.getCancelled())
		// ? LocalDate.parse(bookingAltBldgRequestBean.getCancelled(),
		// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) :
		// bookingAltBldgEntity.getBookCancelled());
		bookingAltBldgEntity.setBookCommunity(Objects.nonNull(bookingAltBldgRequestBean.getCommunity())
				? bookingAltBldgRequestBean.getCommunity().trim()
				: bookingAltBldgEntity.getBookCommunity());
		// bookingAltBldgEntity.setBookContracton(Objects.nonNull(bookingAltBldgRequestBean.getContracton())
		// ? LocalDate.parse(bookingAltBldgRequestBean.getContracton(),
		// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) :
		// bookingAltBldgEntity.getBookContracton());
		bookingAltBldgEntity.setBookCustomercoy(Objects.nonNull(bookingAltBldgRequestBean.getCustomercoy())
				? bookingAltBldgRequestBean.getCustomercoy().trim()
				: bookingAltBldgEntity.getBookCustomercoy());
		bookingAltBldgEntity.setBookCusttype(Objects.nonNull(bookingAltBldgRequestBean.getCusttype())
				? bookingAltBldgRequestBean.getCusttype().trim()
				: bookingAltBldgEntity.getBookCusttype());
		// bookingAltBldgEntity.setBookDate(Objects.nonNull(bookingAltBldgRequestBean.getDate())
		// ?
		// LocalDate.parse(bookingAltBldgRequestBean.getDate(),
		// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) :
		// bookingAltBldgEntity.getBookDate());
		bookingAltBldgEntity.setBookDesignation(Objects.nonNull(bookingAltBldgRequestBean.getDesignation())
				? bookingAltBldgRequestBean.getDesignation().trim()
				: bookingAltBldgEntity.getBookDesignation());
		bookingAltBldgEntity.setBookDiscount(
				Objects.nonNull(bookingAltBldgRequestBean.getDiscount()) ? bookingAltBldgRequestBean.getDiscount()
						: bookingAltBldgEntity.getBookDiscount());
		// bookingAltBldgEntity.setBookFirstvisitdate(Objects.nonNull(bookingAltBldgRequestBean.getFirstvisitdate())
		// ? LocalDate.parse(bookingAltBldgRequestBean.getFirstvisitdate(),
		// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) :
		// bookingAltBldgEntity.getBookFirstvisitdate());
		bookingAltBldgEntity.setBookFirstvisitexec(Objects.nonNull(bookingAltBldgRequestBean.getFirstvisitexec())
				? bookingAltBldgRequestBean.getFirstvisitexec().trim()
				: bookingAltBldgEntity.getBookFirstvisitexec());
		bookingAltBldgEntity.setBookFloor(
				Objects.nonNull(bookingAltBldgRequestBean.getFloor()) ? bookingAltBldgRequestBean.getFloor().trim()
						: bookingAltBldgEntity.getBookFloor());
		bookingAltBldgEntity.setBookGstno(
				Objects.nonNull(bookingAltBldgRequestBean.getGstno()) ? bookingAltBldgRequestBean.getGstno().trim()
						: bookingAltBldgEntity.getBookGstno());
		// bookingAltBldgEntity.setBookHo2owner(Objects.nonNull(bookingAltBldgRequestBean.getHo2owner())
		// ? LocalDate.parse(bookingAltBldgRequestBean.getHo2owner(),
		// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) :
		// bookingAltBldgEntity.getBookHo2owner());
		bookingAltBldgEntity.setBookJobprofile(Objects.nonNull(bookingAltBldgRequestBean.getJobprofile())
				? bookingAltBldgRequestBean.getJobprofile().trim()
				: bookingAltBldgEntity.getBookJobprofile());
		// bookingAltBldgEntity.setBookLeaddate(Objects.nonNull(bookingAltBldgRequestBean.getLeaddate())
		// ? LocalDate.parse(bookingAltBldgRequestBean.getLeaddate(),
		// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) :
		// bookingAltBldgEntity.getBookLeaddate());
		bookingAltBldgEntity.setBookLeasedto(Objects.nonNull(bookingAltBldgRequestBean.getLeasedto())
				? bookingAltBldgRequestBean.getLeasedto().trim()
				: bookingAltBldgEntity.getBookLeasedto());
		bookingAltBldgEntity.setBookLeaseref(Objects.nonNull(bookingAltBldgRequestBean.getLeaseref())
				? bookingAltBldgRequestBean.getLeaseref().trim()
				: bookingAltBldgEntity.getBookLeaseref());
		bookingAltBldgEntity.setBookLedby(
				Objects.nonNull(bookingAltBldgRequestBean.getLedby()) ? bookingAltBldgRequestBean.getLedby().trim()
						: bookingAltBldgEntity.getBookLedby());
		bookingAltBldgEntity.setBookMaintrate(
				Objects.nonNull(bookingAltBldgRequestBean.getMaintrate()) ? bookingAltBldgRequestBean.getMaintrate()
						: bookingAltBldgEntity.getBookMaintrate());
		// bookingAltBldgEntity.setBookMpaiddate(Objects.nonNull(bookingAltBldgRequestBean.getMpaiddate())
		// ? LocalDate.parse(bookingAltBldgRequestBean.getMpaiddate(),
		// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) :
		// bookingAltBldgEntity.getBookMpaiddate());
		bookingAltBldgEntity.setBookMpaidref(Objects.nonNull(bookingAltBldgRequestBean.getMpaidref())
				? bookingAltBldgRequestBean.getMpaidref().trim()
				: bookingAltBldgEntity.getBookMpaidref());
		bookingAltBldgEntity.setBookMpaidyymm(Objects.nonNull(bookingAltBldgRequestBean.getMpaidyymm())
				? bookingAltBldgRequestBean.getMpaidyymm().trim()
				: bookingAltBldgEntity.getBookMpaidyymm());
		bookingAltBldgEntity.setBookOrigsite(GenericAuditContextHolder.getContext().getSite());
		// bookingAltBldgEntity.setBookOveron(Objects.nonNull(bookingAltBldgRequestBean.getOveron())
		// ?
		// LocalDate.parse(bookingAltBldgRequestBean.getOveron(),
		// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) :
		// bookingAltBldgEntity.getBookOveron());
		bookingAltBldgEntity.setBookPanum(
				Objects.nonNull(bookingAltBldgRequestBean.getPanum()) ? bookingAltBldgRequestBean.getPanum().trim()
						: bookingAltBldgEntity.getBookPanum());
		bookingAltBldgEntity.setBookPoacode(
				Objects.nonNull(bookingAltBldgRequestBean.getPoacode()) ? bookingAltBldgRequestBean.getPoacode().trim()
						: bookingAltBldgEntity.getBookPoacode());
		bookingAltBldgEntity.setBookPoaname(
				Objects.nonNull(bookingAltBldgRequestBean.getPoaname()) ? bookingAltBldgRequestBean.getPoaname().trim()
						: bookingAltBldgEntity.getBookPoaname());
		// bookingAltBldgEntity.setBookRegfees(Objects.nonNull(bookingAltBldgRequestBean.getRegfees())
		// ? bookingAltBldgRequestBean.getRegfees() :
		// bookingAltBldgEntity.getBookRegfees());
		bookingAltBldgEntity.setBookRegno(
				Objects.nonNull(bookingAltBldgRequestBean.getRegno()) ? bookingAltBldgRequestBean.getRegno().trim()
						: bookingAltBldgEntity.getBookRegno());
		// bookingAltBldgEntity.setBookRegprice(Objects.nonNull(bookingAltBldgRequestBean.getRegprice())
		// ? bookingAltBldgRequestBean.getRegprice() :
		// bookingAltBldgEntity.getBookRegprice());
		bookingAltBldgEntity.setBookRemarks(
				Objects.nonNull(bookingAltBldgRequestBean.getRemarks()) ? bookingAltBldgRequestBean.getRemarks().trim()
						: bookingAltBldgEntity.getBookRemarks());
		bookingAltBldgEntity.setBookSalestatus(Objects.nonNull(bookingAltBldgRequestBean.getSalestatus())
				? bookingAltBldgRequestBean.getSalestatus().trim()
				: bookingAltBldgEntity.getBookSalestatus());
		bookingAltBldgEntity.setBookSaletype(Objects.nonNull(bookingAltBldgRequestBean.getSaletype())
				? bookingAltBldgRequestBean.getSaletype().trim()
				: bookingAltBldgEntity.getBookSaletype());
		bookingAltBldgEntity
				.setBookScheduledpossession(Objects.nonNull(bookingAltBldgRequestBean.getScheduledpossession())
						? bookingAltBldgRequestBean.getScheduledpossession().trim()
						: bookingAltBldgEntity.getBookScheduledpossession());
		bookingAltBldgEntity.setBookSerialnum(Objects.nonNull(bookingAltBldgRequestBean.getSerialnum())
				? bookingAltBldgRequestBean.getSerialnum().trim()
				: bookingAltBldgEntity.getBookSerialnum());
		bookingAltBldgEntity.setBookSoi(
				Objects.nonNull(bookingAltBldgRequestBean.getSoi()) ? bookingAltBldgRequestBean.getSoi().trim()
						: bookingAltBldgEntity.getBookSoi());

		bookingAltBldgEntity.setBookSite(bookingAltBldgRequestBean.getSite());
		bookingAltBldgEntity.setBookToday(LocalDateTime.now());
		bookingAltBldgEntity.setBookUserid(bookingAltBldgRequestBean.getUserid());

//				bookingAltBldgEntity.setBookSite(GenericAuditContextHolder.getContext().getSite());
//				bookingAltBldgEntity.setBookToday(LocalDateTime.now());
//				bookingAltBldgEntity.setBookUserid(GenericAuditContextHolder.getContext().getUserid());

		// bookingAltBldgEntity.setBookValidtill(Objects.nonNull(bookingAltBldgRequestBean.getValidtill())
		// ? LocalDate.parse(bookingAltBldgRequestBean.getValidtill(),
		// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) :
		// bookingAltBldgEntity.getBookValidtill());

		return bookingAltBldgEntity;
	};

}
