package kraheja.purch.purchasebills.mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;
import kraheja.purch.bean.request.PbilldRequestBean;
import kraheja.purch.bean.response.PbilldResponseBean;
import kraheja.purch.entity.Pbilld;
import kraheja.purch.entity.PbilldCK;

public interface PbilldMapper {
	@SuppressWarnings("unchecked")
	public static Function<List<Pbilld>, List<PbilldResponseBean>> fetchPbilldEntityPojoMapperList = pbilldEntityList -> {
		return pbilldEntityList.stream().map(pbilldEntity -> {
			return PbilldResponseBean.builder().ser(pbilldEntity.getPbilldCK().getPbldSer())
					.lineno(pbilldEntity.getPbilldCK().getPbldLineno())
					.amount(pbilldEntity.getPbldAmount())
					.authdate(Objects.nonNull(pbilldEntity.getPbldAuthdate()) ? pbilldEntity.getPbldAuthdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.authnum(pbilldEntity.getPbldAuthnum())
					.bldgcode(pbilldEntity.getPbldBldgcode())
					.ceser(pbilldEntity.getPbldCeser())
					.dbqty(pbilldEntity.getPbldDbqty())
					.dequantity(pbilldEntity.getPbldDequantity())
					.derate(pbilldEntity.getPbldDerate())
					.deuom(pbilldEntity.getPbldDeuom())
					.grnln(pbilldEntity.getPbldGrnln())
					.grnno(pbilldEntity.getPbldGrnno())
					.indln(pbilldEntity.getPbldIndln())
					.indno(pbilldEntity.getPbldIndno())
					.itemcode(pbilldEntity.getPbldItemcode())
					.lotno(pbilldEntity.getPbldLotno())
					.matgroup(pbilldEntity.getPbldMatgroup())
					.matcode(pbilldEntity.getPbldMatcode())
					.origsite(pbilldEntity.getPbldOrigsite())
					.poln(pbilldEntity.getPbldPoln())
					.pono(pbilldEntity.getPbldPono())
					.quantity(pbilldEntity.getPbldQuantity())
					.rate(pbilldEntity.getPbldRate())
					.site(pbilldEntity.getPbldSite())
					.suppbilldt(Objects.nonNull(pbilldEntity.getPbldSuppbilldt()) ? pbilldEntity.getPbldSuppbilldt().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.suppbillno(pbilldEntity.getPbldSuppbillno())
					.today(pbilldEntity.getPbldToday())
					.uom(pbilldEntity.getPbldUom())
					.userid(pbilldEntity.getPbldUserid()).build();
		}).collect(Collectors.toList());
	};

	public static Function<Pbilld, PbilldResponseBean> fetchPbilldEntityPojoMapper = pbilldEntity -> {
		return PbilldResponseBean.builder().ser(pbilldEntity.getPbilldCK().getPbldSer())
				.lineno(pbilldEntity.getPbilldCK().getPbldLineno())
				.amount(pbilldEntity.getPbldAmount())
				.authdate(Objects.nonNull(pbilldEntity.getPbldAuthdate()) ? pbilldEntity.getPbldAuthdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.authnum(pbilldEntity.getPbldAuthnum())
				.bldgcode(pbilldEntity.getPbldBldgcode())
				.ceser(pbilldEntity.getPbldCeser())
				.dbqty(pbilldEntity.getPbldDbqty())
				.dequantity(pbilldEntity.getPbldDequantity())
				.derate(pbilldEntity.getPbldDerate())
				.deuom(pbilldEntity.getPbldDeuom())
				.grnln(pbilldEntity.getPbldGrnln())
				.grnno(pbilldEntity.getPbldGrnno())
				.indln(pbilldEntity.getPbldIndln())
				.indno(pbilldEntity.getPbldIndno())
				.itemcode(pbilldEntity.getPbldItemcode())
				.lotno(pbilldEntity.getPbldLotno())
				.matgroup(pbilldEntity.getPbldMatgroup())
				.matcode(pbilldEntity.getPbldMatcode())
				.origsite(pbilldEntity.getPbldOrigsite())
				.poln(pbilldEntity.getPbldPoln())
				.pono(pbilldEntity.getPbldPono())
				.quantity(pbilldEntity.getPbldQuantity())
				.rate(pbilldEntity.getPbldRate())
				.site(pbilldEntity.getPbldSite())
				.suppbilldt(Objects.nonNull(pbilldEntity.getPbldSuppbilldt()) ? pbilldEntity.getPbldSuppbilldt().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.suppbillno(pbilldEntity.getPbldSuppbillno())
				.today(pbilldEntity.getPbldToday())
				.uom(pbilldEntity.getPbldUom())
				.userid(pbilldEntity.getPbldUserid()).build();
	};

	public static BiFunction<PbilldRequestBean, String, Pbilld> addPbilldPojoEntityMapper = (pbilldRequestBean, ser) -> {

		return Pbilld.builder()
				.pbilldCK(PbilldCK.builder()
						.pbldSer(ser)
						.pbldLineno(pbilldRequestBean.getLineno())
						.build())
				.pbldAmount(pbilldRequestBean.getAmount())
				.pbldAuthdate(Objects.nonNull(pbilldRequestBean.getAuthdate()) ? LocalDate.parse(pbilldRequestBean.getAuthdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.pbldAuthnum(pbilldRequestBean.getAuthnum())
				.pbldBldgcode(pbilldRequestBean.getBldgcode())
				.pbldCeser(pbilldRequestBean.getCeser())
				.pbldDbqty(pbilldRequestBean.getDbqty())
				.pbldDequantity(pbilldRequestBean.getDequantity())
				.pbldDerate(pbilldRequestBean.getDerate())
				.pbldDeuom(pbilldRequestBean.getDeuom())
				.pbldGrnln(pbilldRequestBean.getGrnln())
				.pbldGrnno(pbilldRequestBean.getGrnno())
				.pbldIndln(pbilldRequestBean.getIndln())
				.pbldIndno(pbilldRequestBean.getIndno())
				.pbldItemcode(pbilldRequestBean.getItemcode())
				.pbldLotno(pbilldRequestBean.getLotno())
				.pbldMatgroup(pbilldRequestBean.getMatgroup())
				.pbldMatcode(pbilldRequestBean.getMatcode())
				.pbldOrigsite(GenericAuditContextHolder.getContext().getSite())
				.pbldPoln(pbilldRequestBean.getPoln())
				.pbldPono(pbilldRequestBean.getPono())
				.pbldQuantity(pbilldRequestBean.getQuantity())
				.pbldRate(pbilldRequestBean.getRate())
				.pbldSite(GenericAuditContextHolder.getContext().getSite())
				.pbldPartycode(pbilldRequestBean.getPartycode())
				.pbldSuppbilldt(Objects.nonNull(pbilldRequestBean.getSuppbilldt()) ? LocalDate.parse(pbilldRequestBean.getSuppbilldt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.pbldSuppbillno(pbilldRequestBean.getSuppbillno())
				.pbldToday(LocalDateTime.now())
				.pbldUom(pbilldRequestBean.getUom())
				.pbldUserid(GenericAuditContextHolder.getContext().getUserid()).build();

	};

	public static BiFunction<Pbilld, PbilldRequestBean, Pbilld> updatePbilldEntityPojoMapper = (pbilldEntity, pbilldRequestBean) -> {
		pbilldEntity.setPbldAmount(Objects.nonNull(pbilldRequestBean.getAmount()) ? pbilldRequestBean.getAmount() : pbilldEntity.getPbldAmount());
		pbilldEntity.setPbldAuthdate(Objects.nonNull(pbilldRequestBean.getAuthdate()) ? LocalDate.parse(pbilldRequestBean.getAuthdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : pbilldEntity.getPbldAuthdate());
		pbilldEntity.setPbldAuthnum(Objects.nonNull(pbilldRequestBean.getAuthnum()) ? pbilldRequestBean.getAuthnum().trim() : pbilldEntity.getPbldAuthnum());
		pbilldEntity.setPbldBldgcode(Objects.nonNull(pbilldRequestBean.getBldgcode()) ? pbilldRequestBean.getBldgcode().trim() : pbilldEntity.getPbldBldgcode());
		pbilldEntity.setPbldCeser(Objects.nonNull(pbilldRequestBean.getCeser()) ? pbilldRequestBean.getCeser().trim() : pbilldEntity.getPbldCeser());
		pbilldEntity.setPbldDbqty(Objects.nonNull(pbilldRequestBean.getDbqty()) ? pbilldRequestBean.getDbqty() : pbilldEntity.getPbldDbqty());
		pbilldEntity.setPbldDequantity(Objects.nonNull(pbilldRequestBean.getDequantity()) ? pbilldRequestBean.getDequantity() : pbilldEntity.getPbldDequantity());
		pbilldEntity.setPbldDerate(Objects.nonNull(pbilldRequestBean.getDerate()) ? pbilldRequestBean.getDerate() : pbilldEntity.getPbldDerate());
		pbilldEntity.setPbldDeuom(Objects.nonNull(pbilldRequestBean.getDeuom()) ? pbilldRequestBean.getDeuom().trim() : pbilldEntity.getPbldDeuom());
		pbilldEntity.setPbldGrnln(Objects.nonNull(pbilldRequestBean.getGrnln()) ? pbilldRequestBean.getGrnln() : pbilldEntity.getPbldGrnln());
		pbilldEntity.setPbldGrnno(Objects.nonNull(pbilldRequestBean.getGrnno()) ? pbilldRequestBean.getGrnno().trim() : pbilldEntity.getPbldGrnno());
		pbilldEntity.setPbldIndln(Objects.nonNull(pbilldRequestBean.getIndln()) ? pbilldRequestBean.getIndln() : pbilldEntity.getPbldIndln());
		pbilldEntity.setPbldIndno(Objects.nonNull(pbilldRequestBean.getIndno()) ? pbilldRequestBean.getIndno().trim() : pbilldEntity.getPbldIndno());
		pbilldEntity.setPbldItemcode(Objects.nonNull(pbilldRequestBean.getItemcode()) ? pbilldRequestBean.getItemcode().trim() : pbilldEntity.getPbldItemcode());
		pbilldEntity.setPbldLotno(Objects.nonNull(pbilldRequestBean.getLotno()) ? pbilldRequestBean.getLotno().trim() : pbilldEntity.getPbldLotno());
		pbilldEntity.setPbldMatcode(pbilldRequestBean.getMatcode());
		pbilldEntity.setPbldMatgroup(Objects.nonNull(pbilldRequestBean.getMatgroup()) ? pbilldRequestBean.getMatgroup().trim() : pbilldEntity.getPbldMatgroup());
		pbilldEntity.setPbldOrigsite(Objects.nonNull(pbilldRequestBean.getOrigsite()) ? pbilldRequestBean.getOrigsite().trim() : pbilldEntity.getPbldOrigsite());
		pbilldEntity.setPbldPoln(Objects.nonNull(pbilldRequestBean.getPoln()) ? pbilldRequestBean.getPoln() : pbilldEntity.getPbldPoln());
		pbilldEntity.setPbldPono(Objects.nonNull(pbilldRequestBean.getPono()) ? pbilldRequestBean.getPono().trim() : pbilldEntity.getPbldPono());
		pbilldEntity.setPbldQuantity(Objects.nonNull(pbilldRequestBean.getQuantity()) ? pbilldRequestBean.getQuantity() : pbilldEntity.getPbldQuantity());
		pbilldEntity.setPbldRate(Objects.nonNull(pbilldRequestBean.getRate()) ? pbilldRequestBean.getRate() : pbilldEntity.getPbldRate());
		pbilldEntity.setPbldSuppbilldt(Objects.nonNull(pbilldRequestBean.getSuppbilldt()) ? LocalDate.parse(pbilldRequestBean.getSuppbilldt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : pbilldEntity.getPbldSuppbilldt());
		pbilldEntity.setPbldSuppbillno(Objects.nonNull(pbilldRequestBean.getSuppbillno()) ? pbilldRequestBean.getSuppbillno().trim() : pbilldEntity.getPbldSuppbillno());
		pbilldEntity.setPbldToday(LocalDateTime.now());
		pbilldEntity.setPbldUom(Objects.nonNull(pbilldRequestBean.getUom()) ? pbilldRequestBean.getUom().trim() : pbilldEntity.getPbldUom());
		pbilldEntity.setPbldSite(GenericAuditContextHolder.getContext().getSite());
		pbilldEntity.setPbldUserid(GenericAuditContextHolder.getContext().getUserid());


		return pbilldEntity;
	};

}