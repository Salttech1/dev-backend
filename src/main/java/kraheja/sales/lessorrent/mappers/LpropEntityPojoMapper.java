// Developed By  - 	vikram.p
// Developed on - 22-11-23
// Mode  - Data Entry
// Purpose - Lprop Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.lessorrent.mappers;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonUtils;
import kraheja.sales.bean.request.LpropRequestBean;
import kraheja.sales.bean.response.LpropResponseBean;
import kraheja.sales.bean.response.LpropResponseBean.LpropResponseBeanBuilder;
import kraheja.sales.entity.Lprop;
import kraheja.sales.entity.LpropCK;

public interface LpropEntityPojoMapper {
	@SuppressWarnings("unchecked")
public static Function<Object[], 	LpropResponseBean> fetchLpropEntityPojoMapper = objectArray -> {
Lprop lpropEntity = (Lprop) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()] : null);
		LpropResponseBeanBuilder lpropResponseBean = LpropResponseBean.builder();
		lpropResponseBean
.propcode(lpropEntity.getLpropCK().getLessorPropcode())
					.coy(lpropEntity.getLessorCoy())
					.description(lpropEntity.getLessorDescription())
					.ipaddress(lpropEntity.getLessorIpaddress())
					.location(lpropEntity.getLessorLocation())
					.machinename(lpropEntity.getLessorMachinename())
					.paytype(lpropEntity.getLessorPaytype())
					.propname(lpropEntity.getLessorPropname())
					.propritor(lpropEntity.getLessorPropritor())
					.proptype(lpropEntity.getLessorProptype())
					.site(lpropEntity.getLessorSite())
					.today(lpropEntity.getLessorToday())
					.userid(lpropEntity.getLessorUserid())
.build();

			return lpropResponseBean.build();
};


	public static Function<LpropRequestBean, Lprop> addLpropPojoEntityMapper = lpropRequestBean -> {
return Lprop.builder().lpropCK(LpropCK.builder()
					.lessorPropcode(lpropRequestBean.getPropcode())
		.build())
					.lessorCoy(lpropRequestBean.getCoy())
					.lessorDescription(lpropRequestBean.getDescription())
					.lessorIpaddress(CommonUtils.INSTANCE.getClientConfig().getIpAddress())
					.lessorLocation(lpropRequestBean.getLocation())
					.lessorMachinename(CommonUtils.INSTANCE.getClientConfig().getMachineName())
					.lessorPaytype(lpropRequestBean.getPaytype())
					.lessorPropname(lpropRequestBean.getPropname())
					.lessorPropritor(lpropRequestBean.getPropritor())
					.lessorProptype(lpropRequestBean.getProptype())
					.lessorSite(GenericAuditContextHolder.getContext().getSite())
					.lessorToday(LocalDateTime.now())
					.lessorUserid(GenericAuditContextHolder.getContext().getUserid())
		
.build();
} ;
	public static BiFunction<Lprop, LpropRequestBean, Lprop> updateLpropEntityPojoMapper = (lpropEntity, lpropRequestBean) -> {
		lpropEntity.getLpropCK().setLessorPropcode(Objects.nonNull(lpropRequestBean.getPropcode()) ? lpropRequestBean.getPropcode().trim() : lpropEntity.getLpropCK().getLessorPropcode());
		lpropEntity.setLessorCoy(Objects.nonNull(lpropRequestBean.getCoy()) ? lpropRequestBean.getCoy().trim() : lpropEntity.getLessorCoy());
		lpropEntity.setLessorDescription(Objects.nonNull(lpropRequestBean.getDescription()) ? lpropRequestBean.getDescription().trim() : lpropEntity.getLessorDescription());
		lpropEntity.setLessorIpaddress(CommonUtils.INSTANCE.getClientConfig().getIpAddress());
		lpropEntity.setLessorLocation(Objects.nonNull(lpropRequestBean.getLocation()) ? lpropRequestBean.getLocation().trim() : lpropEntity.getLessorLocation());
		lpropEntity.setLessorMachinename(CommonUtils.INSTANCE.getClientConfig().getMachineName());
		lpropEntity.setLessorPaytype(Objects.nonNull(lpropRequestBean.getPaytype()) ? lpropRequestBean.getPaytype().trim() : lpropEntity.getLessorPaytype());
		lpropEntity.setLessorPropname(Objects.nonNull(lpropRequestBean.getPropname()) ? lpropRequestBean.getPropname().trim() : lpropEntity.getLessorPropname());
		lpropEntity.setLessorPropritor(Objects.nonNull(lpropRequestBean.getPropritor()) ? lpropRequestBean.getPropritor().trim() : lpropEntity.getLessorPropritor());
		lpropEntity.setLessorProptype(Objects.nonNull(lpropRequestBean.getProptype()) ? lpropRequestBean.getProptype().trim() : lpropEntity.getLessorProptype());
		lpropEntity.setLessorSite(GenericAuditContextHolder.getContext().getSite()) ; 
		lpropEntity.setLessorToday(LocalDateTime.now()) ; 
		lpropEntity.setLessorUserid(GenericAuditContextHolder.getContext().getUserid()) ; 


		return lpropEntity;
	};

}
