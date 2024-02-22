// Developed By  - 	vikram.p
// Developed on - 16-12-23
// Mode  - Data Entry
// Purpose - Lunitdtls Entry / Edit
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
import kraheja.sales.bean.request.LunitdtlsRequestBean;
import kraheja.sales.bean.response.LunitdtlsResponseBean;
import kraheja.sales.bean.response.LunitdtlsResponseBean.LunitdtlsResponseBeanBuilder;
import kraheja.sales.entity.Lunitdtls;
import kraheja.sales.entity.LunitdtlsCK;

public interface LunitdtlsEntityPojoMapper {
	@SuppressWarnings("unchecked")
public static Function<Object[], 	LunitdtlsResponseBean> fetchLunitdtlsEntityPojoMapper = objectArray -> {
Lunitdtls lunitdtlsEntity = (Lunitdtls) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()] : null);
		LunitdtlsResponseBeanBuilder lunitdtlsResponseBean = LunitdtlsResponseBean.builder();
		lunitdtlsResponseBean
.propcode(lunitdtlsEntity.getLunitdtlsCK().getLessorPropcode())
					.unitid(lunitdtlsEntity.getLunitdtlsCK().getLessorUnitid())
					.unitno(lunitdtlsEntity.getLunitdtlsCK().getLessorUnitno())
					.amenity(lunitdtlsEntity.getLessorAmenity())
					.conenddate(Objects.nonNull(lunitdtlsEntity.getLessorConenddate()) ? lunitdtlsEntity.getLessorConenddate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.constartdate(Objects.nonNull(lunitdtlsEntity.getLessorConstartdate()) ? lunitdtlsEntity.getLessorConstartdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.contactperson(lunitdtlsEntity.getLessorContactperson())
					.conttenuare(lunitdtlsEntity.getLessorConttenuare())
					.depositeamt(lunitdtlsEntity.getLessorDepositeamt())
					.flatdesc(lunitdtlsEntity.getLessorFlatdesc())
					.furniture(lunitdtlsEntity.getLessorFurniture())
					.ipaddress(lunitdtlsEntity.getLessorIpaddress())
					.lockfromdate(Objects.nonNull(lunitdtlsEntity.getLessorLockfromdate()) ? lunitdtlsEntity.getLessorLockfromdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.locktodate(Objects.nonNull(lunitdtlsEntity.getLessorLocktodate()) ? lunitdtlsEntity.getLessorLocktodate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.machinename(lunitdtlsEntity.getLessorMachinename())
					.partycode(lunitdtlsEntity.getLessorPartycode())
					.paycycle(lunitdtlsEntity.getLessorPaycycle())
					.paysendingmode(lunitdtlsEntity.getLessorPaysendingmode())
					.paystartdate(Objects.nonNull(lunitdtlsEntity.getLessorPaystartdate()) ? lunitdtlsEntity.getLessorPaystartdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.paystatus(lunitdtlsEntity.getLessorPaystatus())
					.person_pay(lunitdtlsEntity.getLessorPerson_Pay())
					.person_rec(lunitdtlsEntity.getLessorPerson_Rec())
					.remarks(lunitdtlsEntity.getLessorRemarks())
					.servtax(lunitdtlsEntity.getLessorServtax())
					.sharecertstatus(lunitdtlsEntity.getLessorSharecertstatus())
					.site(lunitdtlsEntity.getLessorSite())
					.tds(lunitdtlsEntity.getLessorTds())
					.today(lunitdtlsEntity.getLessorToday())
					.unitarea(lunitdtlsEntity.getLessorUnitarea())
					.unitflatstatus(lunitdtlsEntity.getLessorUnitflatstatus())
					.unitgroup(lunitdtlsEntity.getLessorUnitgroup())
					.unitstatus(lunitdtlsEntity.getLessorUnitstatus())
					.unittype(lunitdtlsEntity.getLessorUnittype())
					.userid(lunitdtlsEntity.getLessorUserid())
.build();

			return lunitdtlsResponseBean.build();
};


	public static Function<LunitdtlsRequestBean, Lunitdtls> addLunitdtlsPojoEntityMapper = lunitdtlsRequestBean -> {
return Lunitdtls.builder().lunitdtlsCK(LunitdtlsCK.builder()
					.lessorPropcode(lunitdtlsRequestBean.getPropcode())
					.lessorUnitid(lunitdtlsRequestBean.getUnitid())
					.lessorUnitno(lunitdtlsRequestBean.getUnitno())
		.build())
					.lessorAmenity(lunitdtlsRequestBean.getAmenity())
					.lessorConenddate(Objects.nonNull(lunitdtlsRequestBean.getConenddate()) ? LocalDate.parse(lunitdtlsRequestBean.getConenddate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.lessorConstartdate(Objects.nonNull(lunitdtlsRequestBean.getConstartdate()) ? LocalDate.parse(lunitdtlsRequestBean.getConstartdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.lessorContactperson(lunitdtlsRequestBean.getContactperson())
					.lessorConttenuare(lunitdtlsRequestBean.getConttenuare())
					.lessorDepositeamt(Objects.nonNull(lunitdtlsRequestBean.getDepositeamt()) ? lunitdtlsRequestBean.getDepositeamt() : BigInteger.ZERO.doubleValue())
					.lessorFlatdesc(lunitdtlsRequestBean.getFlatdesc())
					.lessorFurniture(lunitdtlsRequestBean.getFurniture())
					.lessorIpaddress(lunitdtlsRequestBean.getIpaddress())
					.lessorLockfromdate(Objects.nonNull(lunitdtlsRequestBean.getLockfromdate()) ? LocalDate.parse(lunitdtlsRequestBean.getLockfromdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.lessorLocktodate(Objects.nonNull(lunitdtlsRequestBean.getLocktodate()) ? LocalDate.parse(lunitdtlsRequestBean.getLocktodate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.lessorMachinename(lunitdtlsRequestBean.getMachinename())
					.lessorPartycode(lunitdtlsRequestBean.getPartycode())
					.lessorPaycycle(lunitdtlsRequestBean.getPaycycle())
					.lessorPaysendingmode(lunitdtlsRequestBean.getPaysendingmode())
					.lessorPaystartdate(Objects.nonNull(lunitdtlsRequestBean.getPaystartdate()) ? LocalDate.parse(lunitdtlsRequestBean.getPaystartdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.lessorPaystatus(lunitdtlsRequestBean.getPaystatus())
					.lessorPerson_Pay(lunitdtlsRequestBean.getPerson_pay())
					.lessorPerson_Rec(lunitdtlsRequestBean.getPerson_rec())
					.lessorRemarks(lunitdtlsRequestBean.getRemarks())
					.lessorServtax(Objects.nonNull(lunitdtlsRequestBean.getServtax()) ? lunitdtlsRequestBean.getServtax() : BigInteger.ZERO.doubleValue())
					.lessorSharecertstatus(lunitdtlsRequestBean.getSharecertstatus())
					.lessorSite(GenericAuditContextHolder.getContext().getSite())
					.lessorTds(Objects.nonNull(lunitdtlsRequestBean.getTds()) ? lunitdtlsRequestBean.getTds() : BigInteger.ZERO.doubleValue())
					.lessorToday(LocalDateTime.now())
					.lessorUnitarea(Objects.nonNull(lunitdtlsRequestBean.getUnitarea()) ? lunitdtlsRequestBean.getUnitarea() : BigInteger.ZERO.doubleValue())
					.lessorUnitflatstatus(lunitdtlsRequestBean.getUnitflatstatus())
					.lessorUnitgroup(lunitdtlsRequestBean.getUnitgroup())
					.lessorUnitstatus(lunitdtlsRequestBean.getUnitstatus())
					.lessorUnittype(lunitdtlsRequestBean.getUnittype())
					.lessorUserid(GenericAuditContextHolder.getContext().getUserid())
		
.build();
} ;
	public static BiFunction<Lunitdtls, LunitdtlsRequestBean, Lunitdtls> updateLunitdtlsEntityPojoMapper = (lunitdtlsEntity, lunitdtlsRequestBean) -> {
		lunitdtlsEntity.getLunitdtlsCK().setLessorPropcode(Objects.nonNull(lunitdtlsRequestBean.getPropcode()) ? lunitdtlsRequestBean.getPropcode().trim() : lunitdtlsEntity.getLunitdtlsCK().getLessorPropcode());
		lunitdtlsEntity.getLunitdtlsCK().setLessorUnitid(Objects.nonNull(lunitdtlsRequestBean.getUnitid()) ? lunitdtlsRequestBean.getUnitid().trim() : lunitdtlsEntity.getLunitdtlsCK().getLessorUnitid());
		lunitdtlsEntity.getLunitdtlsCK().setLessorUnitno(Objects.nonNull(lunitdtlsRequestBean.getUnitno()) ? lunitdtlsRequestBean.getUnitno().trim() : lunitdtlsEntity.getLunitdtlsCK().getLessorUnitno());
		lunitdtlsEntity.setLessorAmenity(Objects.nonNull(lunitdtlsRequestBean.getAmenity()) ? lunitdtlsRequestBean.getAmenity().trim() : lunitdtlsEntity.getLessorAmenity());
		lunitdtlsEntity.setLessorConenddate(Objects.nonNull(lunitdtlsRequestBean.getConenddate()) ? LocalDate.parse(lunitdtlsRequestBean.getConenddate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : lunitdtlsEntity.getLessorConenddate());
		lunitdtlsEntity.setLessorConstartdate(Objects.nonNull(lunitdtlsRequestBean.getConstartdate()) ? LocalDate.parse(lunitdtlsRequestBean.getConstartdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : lunitdtlsEntity.getLessorConstartdate());
		lunitdtlsEntity.setLessorContactperson(Objects.nonNull(lunitdtlsRequestBean.getContactperson()) ? lunitdtlsRequestBean.getContactperson().trim() : lunitdtlsEntity.getLessorContactperson());
		lunitdtlsEntity.setLessorConttenuare(Objects.nonNull(lunitdtlsRequestBean.getConttenuare()) ? lunitdtlsRequestBean.getConttenuare().trim() : lunitdtlsEntity.getLessorConttenuare());
		lunitdtlsEntity.setLessorDepositeamt(Objects.nonNull(lunitdtlsRequestBean.getDepositeamt()) ? lunitdtlsRequestBean.getDepositeamt() : lunitdtlsEntity.getLessorDepositeamt());
		lunitdtlsEntity.setLessorFlatdesc(Objects.nonNull(lunitdtlsRequestBean.getFlatdesc()) ? lunitdtlsRequestBean.getFlatdesc().trim() : lunitdtlsEntity.getLessorFlatdesc());
		lunitdtlsEntity.setLessorFurniture(Objects.nonNull(lunitdtlsRequestBean.getFurniture()) ? lunitdtlsRequestBean.getFurniture().trim() : lunitdtlsEntity.getLessorFurniture());
		lunitdtlsEntity.setLessorIpaddress(Objects.nonNull(lunitdtlsRequestBean.getIpaddress()) ? lunitdtlsRequestBean.getIpaddress().trim() : lunitdtlsEntity.getLessorIpaddress());
		lunitdtlsEntity.setLessorLockfromdate(Objects.nonNull(lunitdtlsRequestBean.getLockfromdate()) ? LocalDate.parse(lunitdtlsRequestBean.getLockfromdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : lunitdtlsEntity.getLessorLockfromdate());
		lunitdtlsEntity.setLessorLocktodate(Objects.nonNull(lunitdtlsRequestBean.getLocktodate()) ? LocalDate.parse(lunitdtlsRequestBean.getLocktodate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : lunitdtlsEntity.getLessorLocktodate());
		lunitdtlsEntity.setLessorMachinename(Objects.nonNull(lunitdtlsRequestBean.getMachinename()) ? lunitdtlsRequestBean.getMachinename().trim() : lunitdtlsEntity.getLessorMachinename());
		lunitdtlsEntity.setLessorPartycode(Objects.nonNull(lunitdtlsRequestBean.getPartycode()) ? lunitdtlsRequestBean.getPartycode().trim() : lunitdtlsEntity.getLessorPartycode());
		lunitdtlsEntity.setLessorPaycycle(Objects.nonNull(lunitdtlsRequestBean.getPaycycle()) ? lunitdtlsRequestBean.getPaycycle().trim() : lunitdtlsEntity.getLessorPaycycle());
		lunitdtlsEntity.setLessorPaysendingmode(Objects.nonNull(lunitdtlsRequestBean.getPaysendingmode()) ? lunitdtlsRequestBean.getPaysendingmode().trim() : lunitdtlsEntity.getLessorPaysendingmode());
		lunitdtlsEntity.setLessorPaystartdate(Objects.nonNull(lunitdtlsRequestBean.getPaystartdate()) ? LocalDate.parse(lunitdtlsRequestBean.getPaystartdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : lunitdtlsEntity.getLessorPaystartdate());
		lunitdtlsEntity.setLessorPaystatus(Objects.nonNull(lunitdtlsRequestBean.getPaystatus()) ? lunitdtlsRequestBean.getPaystatus().trim() : lunitdtlsEntity.getLessorPaystatus());
		lunitdtlsEntity.setLessorPerson_Pay(Objects.nonNull(lunitdtlsRequestBean.getPerson_pay()) ? lunitdtlsRequestBean.getPerson_pay().trim() : lunitdtlsEntity.getLessorPerson_Pay());
		lunitdtlsEntity.setLessorPerson_Rec(Objects.nonNull(lunitdtlsRequestBean.getPerson_rec()) ? lunitdtlsRequestBean.getPerson_rec().trim() : lunitdtlsEntity.getLessorPerson_Rec());
		lunitdtlsEntity.setLessorRemarks(Objects.nonNull(lunitdtlsRequestBean.getRemarks()) ? lunitdtlsRequestBean.getRemarks().trim() : lunitdtlsEntity.getLessorRemarks());
		lunitdtlsEntity.setLessorServtax(Objects.nonNull(lunitdtlsRequestBean.getServtax()) ? lunitdtlsRequestBean.getServtax() : lunitdtlsEntity.getLessorServtax());
		lunitdtlsEntity.setLessorSharecertstatus(Objects.nonNull(lunitdtlsRequestBean.getSharecertstatus()) ? lunitdtlsRequestBean.getSharecertstatus().trim() : lunitdtlsEntity.getLessorSharecertstatus());
		lunitdtlsEntity.setLessorSite(GenericAuditContextHolder.getContext().getSite()) ; 
		lunitdtlsEntity.setLessorTds(Objects.nonNull(lunitdtlsRequestBean.getTds()) ? lunitdtlsRequestBean.getTds() : lunitdtlsEntity.getLessorTds());
		lunitdtlsEntity.setLessorToday(LocalDateTime.now()) ; 
		lunitdtlsEntity.setLessorUnitarea(Objects.nonNull(lunitdtlsRequestBean.getUnitarea()) ? lunitdtlsRequestBean.getUnitarea() : lunitdtlsEntity.getLessorUnitarea());
		lunitdtlsEntity.setLessorUnitflatstatus(Objects.nonNull(lunitdtlsRequestBean.getUnitflatstatus()) ? lunitdtlsRequestBean.getUnitflatstatus().trim() : lunitdtlsEntity.getLessorUnitflatstatus());
		lunitdtlsEntity.setLessorUnitgroup(Objects.nonNull(lunitdtlsRequestBean.getUnitgroup()) ? lunitdtlsRequestBean.getUnitgroup().trim() : lunitdtlsEntity.getLessorUnitgroup());
		lunitdtlsEntity.setLessorUnitstatus(Objects.nonNull(lunitdtlsRequestBean.getUnitstatus()) ? lunitdtlsRequestBean.getUnitstatus().trim() : lunitdtlsEntity.getLessorUnitstatus());
		lunitdtlsEntity.setLessorUnittype(Objects.nonNull(lunitdtlsRequestBean.getUnittype()) ? lunitdtlsRequestBean.getUnittype().trim() : lunitdtlsEntity.getLessorUnittype());
		lunitdtlsEntity.setLessorUserid(GenericAuditContextHolder.getContext().getUserid()) ; 


		return lunitdtlsEntity;
	};

}
