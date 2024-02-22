// Developed By  - 	vikram.p
// Developed on - 03-01-24
// Mode  - Data Entry
// Purpose - Lrentmst Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.lessorrent.mappers;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.apache.commons.collections4.CollectionUtils;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;
import kraheja.sales.bean.request.LrentmstRequestBean;
import kraheja.sales.bean.response.LrentmstResponseBean;
import kraheja.sales.bean.response.LrentmstResponseBean.LrentmstResponseBeanBuilder;
import java.util.stream.Collectors;
import kraheja.sales.entity.Lrentmst;
import kraheja.sales.entity.LrentmstCK;

public interface LrentmstEntityPojoMapper {
	@SuppressWarnings("unchecked")
public static Function	<List<Lrentmst>, List<LrentmstResponseBean>> fetchLrentmstEntityPojoMapper = lrentmstEntityList -> {
return lrentmstEntityList.stream().map(lrentmstEntity -> {
return LrentmstResponseBean.builder()
.unitid(lrentmstEntity.getLrentmstCK().getRentmUnitid())
					.unitgroup(lrentmstEntity.getLrentmstCK().getRentmUnitgroup())
					.type(lrentmstEntity.getLrentmstCK().getRentmType())
					.to(Objects.nonNull(lrentmstEntity.getLrentmstCK().getRentmTo()) ? lrentmstEntity.getLrentmstCK().getRentmTo().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.propcode(lrentmstEntity.getLrentmstCK().getRentmPropcode())
					.unitno(lrentmstEntity.getLrentmstCK().getRentmUnitno())
					.amt(lrentmstEntity.getRentmAmt())
					.certstatus(lrentmstEntity.getRentmCertstatus())
					.from(Objects.nonNull(lrentmstEntity.getRentmFrom()) ? lrentmstEntity.getRentmFrom().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.incper(lrentmstEntity.getRentmIncper())
					.ipaddress(lrentmstEntity.getRentmIpaddress())
					.partycode(lrentmstEntity.getRentmPartycode())
					.paycycle(lrentmstEntity.getRentmPaycycle())
					.remarks(lrentmstEntity.getRentmRemarks())
					.servtaxflag(lrentmstEntity.getRentmServtaxflag())
					.servtaxper(lrentmstEntity.getRentmServtaxper())
					.site(lrentmstEntity.getRentmSite())
					.status(lrentmstEntity.getRentmStatus())
					.today(lrentmstEntity.getRentmToday())
					.userid(lrentmstEntity.getRentmUserid())
.build(); 
}).collect(Collectors.toList());

};


	public static Function<List<LrentmstRequestBean>, List <Lrentmst>> addLrentmstPojoEntityMapper = (lrentmstRequestBeanList) -> { 
return lrentmstRequestBeanList.stream().map(lrentmstRequestBean -> {
return Lrentmst.builder().lrentmstCK(LrentmstCK.builder()
					.rentmUnitid(lrentmstRequestBean.getUnitid())
					.rentmUnitgroup(lrentmstRequestBean.getUnitgroup())
					.rentmType(lrentmstRequestBean.getType())
					.rentmTo(Objects.nonNull(lrentmstRequestBean.getTo()) ? LocalDate.parse(lrentmstRequestBean.getTo(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.rentmPropcode(lrentmstRequestBean.getPropcode())
					.rentmUnitno(lrentmstRequestBean.getUnitno())
		.build())
					.rentmAmt(Objects.nonNull(lrentmstRequestBean.getAmt()) ? lrentmstRequestBean.getAmt() : BigInteger.ZERO.doubleValue())
					.rentmCertstatus(lrentmstRequestBean.getCertstatus())
					.rentmFrom(Objects.nonNull(lrentmstRequestBean.getFrom()) ? LocalDate.parse(lrentmstRequestBean.getFrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.rentmIncper(Objects.nonNull(lrentmstRequestBean.getIncper()) ? lrentmstRequestBean.getIncper() : BigInteger.ZERO.doubleValue())
					.rentmIpaddress(lrentmstRequestBean.getIpaddress())
					.rentmPartycode(lrentmstRequestBean.getPartycode())
					.rentmPaycycle(lrentmstRequestBean.getPaycycle())
					.rentmRemarks(lrentmstRequestBean.getRemarks())
					.rentmServtaxflag(lrentmstRequestBean.getServtaxflag())
					.rentmServtaxper(Objects.nonNull(lrentmstRequestBean.getServtaxper()) ? lrentmstRequestBean.getServtaxper() : BigInteger.ZERO.doubleValue())
					.rentmSite(GenericAuditContextHolder.getContext().getSite())
					.rentmStatus(lrentmstRequestBean.getStatus())
					.rentmToday(LocalDateTime.now())
					.rentmUserid(GenericAuditContextHolder.getContext().getUserid())
		
.build();
}).collect(Collectors.toList());
} ;
	public static BiFunction<Lrentmst, LrentmstRequestBean, Lrentmst> updateLrentmstEntityPojoMapper = (lrentmstEntity, lrentmstRequestBean) -> {
		lrentmstEntity.getLrentmstCK().setRentmUnitid(Objects.nonNull(lrentmstRequestBean.getUnitid()) ? lrentmstRequestBean.getUnitid().trim() : lrentmstEntity.getLrentmstCK().getRentmUnitid());
		lrentmstEntity.getLrentmstCK().setRentmUnitgroup(Objects.nonNull(lrentmstRequestBean.getUnitgroup()) ? lrentmstRequestBean.getUnitgroup().trim() : lrentmstEntity.getLrentmstCK().getRentmUnitgroup());
		lrentmstEntity.getLrentmstCK().setRentmType(Objects.nonNull(lrentmstRequestBean.getType()) ? lrentmstRequestBean.getType().trim() : lrentmstEntity.getLrentmstCK().getRentmType());
		lrentmstEntity.getLrentmstCK().setRentmTo(Objects.nonNull(lrentmstRequestBean.getTo()) ? LocalDate.parse(lrentmstRequestBean.getTo(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : lrentmstEntity.getLrentmstCK().getRentmTo());
		lrentmstEntity.getLrentmstCK().setRentmPropcode(Objects.nonNull(lrentmstRequestBean.getPropcode()) ? lrentmstRequestBean.getPropcode().trim() : lrentmstEntity.getLrentmstCK().getRentmPropcode());
		lrentmstEntity.getLrentmstCK().setRentmUnitno(Objects.nonNull(lrentmstRequestBean.getUnitno()) ? lrentmstRequestBean.getUnitno().trim() : lrentmstEntity.getLrentmstCK().getRentmUnitno());
		lrentmstEntity.setRentmAmt(Objects.nonNull(lrentmstRequestBean.getAmt()) ? lrentmstRequestBean.getAmt() : lrentmstEntity.getRentmAmt());
		lrentmstEntity.setRentmCertstatus(Objects.nonNull(lrentmstRequestBean.getCertstatus()) ? lrentmstRequestBean.getCertstatus().trim() : lrentmstEntity.getRentmCertstatus());
		lrentmstEntity.setRentmFrom(Objects.nonNull(lrentmstRequestBean.getFrom()) ? LocalDate.parse(lrentmstRequestBean.getFrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : lrentmstEntity.getRentmFrom());
		lrentmstEntity.setRentmIncper(Objects.nonNull(lrentmstRequestBean.getIncper()) ? lrentmstRequestBean.getIncper() : lrentmstEntity.getRentmIncper());
		lrentmstEntity.setRentmIpaddress(Objects.nonNull(lrentmstRequestBean.getIpaddress()) ? lrentmstRequestBean.getIpaddress().trim() : lrentmstEntity.getRentmIpaddress());
		lrentmstEntity.setRentmPartycode(Objects.nonNull(lrentmstRequestBean.getPartycode()) ? lrentmstRequestBean.getPartycode().trim() : lrentmstEntity.getRentmPartycode());
		lrentmstEntity.setRentmPaycycle(Objects.nonNull(lrentmstRequestBean.getPaycycle()) ? lrentmstRequestBean.getPaycycle().trim() : lrentmstEntity.getRentmPaycycle());
		lrentmstEntity.setRentmRemarks(Objects.nonNull(lrentmstRequestBean.getRemarks()) ? lrentmstRequestBean.getRemarks().trim() : lrentmstEntity.getRentmRemarks());
		lrentmstEntity.setRentmServtaxflag(Objects.nonNull(lrentmstRequestBean.getServtaxflag()) ? lrentmstRequestBean.getServtaxflag().trim() : lrentmstEntity.getRentmServtaxflag());
		lrentmstEntity.setRentmServtaxper(Objects.nonNull(lrentmstRequestBean.getServtaxper()) ? lrentmstRequestBean.getServtaxper() : lrentmstEntity.getRentmServtaxper());
		lrentmstEntity.setRentmSite(GenericAuditContextHolder.getContext().getSite()) ; 
		lrentmstEntity.setRentmStatus(Objects.nonNull(lrentmstRequestBean.getStatus()) ? lrentmstRequestBean.getStatus().trim() : lrentmstEntity.getRentmStatus());
		lrentmstEntity.setRentmToday(LocalDateTime.now()) ; 
		lrentmstEntity.setRentmUserid(GenericAuditContextHolder.getContext().getUserid()) ; 


		return lrentmstEntity;
	};

}
