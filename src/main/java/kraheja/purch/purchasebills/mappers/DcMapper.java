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
import kraheja.purch.bean.request.DcRequestBean;
import kraheja.purch.bean.response.DcResponseBean;
import kraheja.purch.entity.Dc;
import kraheja.purch.entity.DcCK;

public interface DcMapper {

	public static Function<List<Dc>, List<DcResponseBean>> fetchDcEntityPojoMapper = dcEntityList -> {
		return dcEntityList.stream().map(dcEntity -> {
			return DcResponseBean.builder().entryno(dcEntity.getDcCK().getDcpEntryno())
					.suppcode(dcEntity.getDcCK().getDcpSuppcode())
					.suppbill(dcEntity.getDcpSuppbill())
					.billdt(Objects.nonNull(dcEntity.getDcpBilldt()) ? dcEntity.getDcpBilldt().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.dcno(dcEntity.getDcCK().getDcpDcno())
					.dcdate(Objects.nonNull(dcEntity.getDcpDcdate()) ? dcEntity.getDcpDcdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.today(dcEntity.getDcpToday())
					.site(dcEntity.getDcpSite())
					.pukka(dcEntity.getDcpPukka())
					.userid(dcEntity.getDcpUserid())
					.origsite(dcEntity.getDcpOrigsite()).build();
		}).collect(Collectors.toList());
	};

	public static BiFunction<List<DcRequestBean>, String, List<Dc>> addDcEntityPojoMapper = (dcRequestBeanList, ser) -> {

		return dcRequestBeanList.stream().map(dcRequestBean -> {		
			return Dc.builder()
					.dcCK(DcCK.builder().dcpEntryno(ser).dcpSuppcode(dcRequestBean.getSuppcode()).dcpDcno(dcRequestBean.getDcno()).build())
					.dcpSuppbill(dcRequestBean.getSuppbill())
					.dcpBilldt(Objects.nonNull(dcRequestBean.getBilldt()) ? LocalDate.parse(dcRequestBean.getBilldt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.dcpDcdate(Objects.nonNull(dcRequestBean.getDcdate()) ? LocalDate.parse(dcRequestBean.getDcdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.dcpToday(LocalDateTime.now())
					.dcpSite(GenericAuditContextHolder.getContext().getSite())
					.dcpPukka(dcRequestBean.getPukka())
					.dcpUserid(GenericAuditContextHolder.getContext().getUserid())
					.dcpOrigsite(GenericAuditContextHolder.getContext().getSite()).build();
		}).collect(Collectors.toList());

	};

	public static BiFunction<Dc, DcRequestBean, Dc> updateDcEntityPojoMapper = (dcEntity, dcRequestBean) -> {
		dcEntity.getDcCK().setDcpEntryno(Objects.nonNull(dcRequestBean.getEntryno()) ? dcRequestBean.getEntryno().trim() : dcEntity.getDcCK().getDcpEntryno());
		dcEntity.getDcCK().setDcpSuppcode(Objects.nonNull(dcRequestBean.getSuppcode()) ? dcRequestBean.getSuppcode().trim() : dcEntity.getDcCK().getDcpSuppcode());
		dcEntity.setDcpSuppbill(Objects.nonNull(dcRequestBean.getSuppbill()) ? dcRequestBean.getSuppbill().trim() : dcEntity.getDcpSuppbill());
		dcEntity.setDcpBilldt(Objects.nonNull(dcRequestBean.getBilldt()) ? LocalDate.parse(dcRequestBean.getBilldt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : dcEntity.getDcpBilldt());
		dcEntity.getDcCK().setDcpDcno(Objects.nonNull(dcRequestBean.getDcno()) ? dcRequestBean.getDcno().trim() : dcEntity.getDcCK().getDcpDcno());
		dcEntity.setDcpDcdate(Objects.nonNull(dcRequestBean.getDcdate()) ? LocalDate.parse(dcRequestBean.getDcdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : dcEntity.getDcpDcdate());
		dcEntity.setDcpToday(LocalDateTime.now());	
		dcEntity.setDcpSite(Objects.nonNull(dcRequestBean.getSite()) ? dcRequestBean.getSite().trim() : dcEntity.getDcpSite());
		dcEntity.setDcpPukka(Objects.nonNull(dcRequestBean.getPukka()) ? dcRequestBean.getPukka() : dcEntity.getDcpPukka());
		dcEntity.setDcpUserid(Objects.nonNull(dcRequestBean.getUserid()) ? dcRequestBean.getUserid().trim() : dcEntity.getDcpUserid());
		dcEntity.setDcpOrigsite(Objects.nonNull(dcRequestBean.getOrigsite()) ? dcRequestBean.getOrigsite().trim() : dcEntity.getDcpOrigsite());

		return dcEntity;
	};
}
