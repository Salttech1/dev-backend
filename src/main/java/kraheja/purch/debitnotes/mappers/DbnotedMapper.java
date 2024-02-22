package kraheja.purch.debitnotes.mappers;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.purch.bean.request.DbnotedRequestBean;
import kraheja.purch.bean.response.DbnotedResponseBean;
import kraheja.purch.bean.response.DbnotedResponseBean.DbnotedResponseBeanBuilder;
import kraheja.purch.entity.Dbnoted;
import kraheja.purch.entity.DbnotedCK;

public interface DbnotedMapper {
	@SuppressWarnings("unchecked")
	public static Function<Object[], List<DbnotedResponseBean>> fetchDbnotedEntityPojoMapper = objectArray -> {
		DbnotedResponseBeanBuilder dbnotedBeanBuilder = DbnotedResponseBean.builder();
		List<Dbnoted> dbnotedEntityList = (List<Dbnoted>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
				: null);
		return dbnotedEntityList.stream().map(dbnotedEntity -> {
			dbnotedBeanBuilder					
			.partycode(dbnotedEntity.getDbndPartyCode())
			
			.amount(dbnotedEntity.getDbndAmount())
					.dequantity(dbnotedEntity.getDbndDequantity())
					.derate(dbnotedEntity.getDbndDerate())
					.deuom(dbnotedEntity.getDbndDeuom())
					.itemcode(dbnotedEntity.getDbndItemcode())
					.length(dbnotedEntity.getDbndLength())
					.matcode(dbnotedEntity.getDbndMatcode())
					.number(dbnotedEntity.getDbndNumber())
					.origsite(dbnotedEntity.getDbndOrigsite())
					.quantity(dbnotedEntity.getDbndQuantity())
					.rate(dbnotedEntity.getDbndRate())
					.site(dbnotedEntity.getDbndSite())
					.uom(dbnotedEntity.getDbndUom())
					.userid(dbnotedEntity.getDbndUserid())
					.matgroup(dbnotedEntity.getDbndMatGroup())
;
			return dbnotedBeanBuilder.build();
		}).collect(Collectors.toList());
	};

	public static Function<Object[], Dbnoted> addDbnotedEntityPojoMapper = objectArray -> {
		DbnotedRequestBean dbnotedRequestBean = (DbnotedRequestBean) (Objects
		.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);

		Dbnoted.		DbnotedBuilder dbnotedbuilder = Dbnoted.builder();

dbnotedbuilder
			.dbnotedCK(DbnotedCK.builder().dbndDbnoteser(dbnotedRequestBean.getDbnoteser())
					.dbndLineno(dbnotedRequestBean.getLineno())
		.build())
					.dbndMatcode(dbnotedRequestBean.getMatcode())
					.dbndMatGroup(dbnotedRequestBean.getMatgroup())
					.dbndSuppBillNo(dbnotedRequestBean.getSuppbillno())
					.dbndAmount(dbnotedRequestBean.getAmount())
					.dbndDequantity(dbnotedRequestBean.getDequantity())
					.dbndDerate(dbnotedRequestBean.getDerate())
					.dbndDeuom(dbnotedRequestBean.getDeuom())
					.dbndItemcode(dbnotedRequestBean.getItemcode())
					.dbndLength(dbnotedRequestBean.getLength())
					.dbndMatcode(dbnotedRequestBean.getMatcode())
					.dbndPartyCode(dbnotedRequestBean.getPartycode())
					.dbndNumber(dbnotedRequestBean.getNumber())
					.dbndOrigsite(GenericAuditContextHolder.getContext().getSite())
					.dbndQuantity(dbnotedRequestBean.getQuantity())
					.dbndRate(dbnotedRequestBean.getRate())
					.dbndSite( GenericAuditContextHolder.getContext().getSite())
					.dbndToday(LocalDateTime.now())
					.dbndUom(dbnotedRequestBean.getUom())
					.dbndUserid( GenericAuditContextHolder.getContext().getUserid())
					;

		return dbnotedbuilder.build();
};

	public static BiFunction<Dbnoted, DbnotedRequestBean, Dbnoted> updateDbnotedEntityPojoMapper = (dbnotedEntity, dbnotedRequestBean) -> {
		dbnotedEntity.setDbndAmount(Objects.nonNull(dbnotedRequestBean.getAmount()) ? dbnotedRequestBean.getAmount() : dbnotedEntity.getDbndAmount());
		dbnotedEntity.setDbndDequantity(Objects.nonNull(dbnotedRequestBean.getDequantity()) ? dbnotedRequestBean.getDequantity() : dbnotedEntity.getDbndDequantity());
		dbnotedEntity.setDbndDerate(Objects.nonNull(dbnotedRequestBean.getDerate()) ? dbnotedRequestBean.getDerate() : dbnotedEntity.getDbndDerate());
		dbnotedEntity.setDbndDeuom(Objects.nonNull(dbnotedRequestBean.getDeuom()) ? dbnotedRequestBean.getDeuom().trim() : dbnotedEntity.getDbndDeuom());
		dbnotedEntity.setDbndItemcode(Objects.nonNull(dbnotedRequestBean.getItemcode()) ? dbnotedRequestBean.getItemcode().trim() : dbnotedEntity.getDbndItemcode());
		dbnotedEntity.setDbndLength(Objects.nonNull(dbnotedRequestBean.getLength()) ? dbnotedRequestBean.getLength().trim() : dbnotedEntity.getDbndLength());
		dbnotedEntity.setDbndMatcode(Objects.nonNull(dbnotedRequestBean.getMatcode()) ? dbnotedRequestBean.getMatcode().trim() : dbnotedEntity.getDbndMatcode());
		dbnotedEntity.setDbndNumber(Objects.nonNull(dbnotedRequestBean.getNumber()) ? dbnotedRequestBean.getNumber() : dbnotedEntity.getDbndNumber());
		dbnotedEntity.setDbndOrigsite(Objects.nonNull(dbnotedRequestBean.getOrigsite()) ? dbnotedRequestBean.getOrigsite().trim() : dbnotedEntity.getDbndOrigsite());
		dbnotedEntity.setDbndQuantity(Objects.nonNull(dbnotedRequestBean.getQuantity()) ? dbnotedRequestBean.getQuantity() : dbnotedEntity.getDbndQuantity());
		dbnotedEntity.setDbndRate(Objects.nonNull(dbnotedRequestBean.getRate()) ? dbnotedRequestBean.getRate() : dbnotedEntity.getDbndRate());
		dbnotedEntity.setDbndSite(GenericAuditContextHolder.getContext().getSite());
		dbnotedEntity.setDbndToday(LocalDateTime.now());
		dbnotedEntity.setDbndUom(Objects.nonNull(dbnotedRequestBean.getUom()) ? dbnotedRequestBean.getUom().trim() : dbnotedEntity.getDbndUom());
		dbnotedEntity.setDbndUserid(GenericAuditContextHolder.getContext().getUserid());
		dbnotedEntity.setDbndMatcode(Objects.nonNull(dbnotedRequestBean.getMatcode()) ? dbnotedRequestBean.getMatcode().trim() : dbnotedEntity.getDbndMatcode());
		dbnotedEntity.setDbndMatGroup(Objects.nonNull(dbnotedRequestBean.getMatgroup()) ? dbnotedRequestBean.getMatgroup().trim() : dbnotedEntity.getDbndMatGroup());
		dbnotedEntity.setDbndSuppBillNo(Objects.nonNull(dbnotedRequestBean.getSuppbillno()) ? dbnotedRequestBean.getSuppbillno().trim() : dbnotedEntity.getDbndSuppBillNo());
		return dbnotedEntity;
	};
}