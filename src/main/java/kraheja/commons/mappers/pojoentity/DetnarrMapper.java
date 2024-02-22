// Developed By  - 	kalpana.m
// Developed on - 04-08-23
// Mode  - Data Entry
// Purpose - Detnarr Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.commons.mappers.pojoentity;

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
import java.util.stream.Collectors;

import kraheja.commons.bean.DetnarrBean;
import kraheja.commons.entity.Detnarr;
import kraheja.commons.entity.DetnarrCK;

public interface DetnarrMapper {
	@SuppressWarnings("unchecked")
public static Function	<List<Detnarr>, List<DetnarrBean>> fetchDetnarrEntityPojoMapper = detnarrEntityList -> {
return detnarrEntityList.stream().map(detnarrEntity -> {
return DetnarrBean.builder()
.coy(detnarrEntity.getDetnarrCK().getDetCoy())
					.transer(detnarrEntity.getDetnarrCK().getDetTranser())
					.bunum(detnarrEntity.getDetnarrCK().getDetBunum())
					.dettype(detnarrEntity.getDetDettype())
					.narrative(detnarrEntity.getDetNarrative())
					.site(detnarrEntity.getDetSite())
					.today(detnarrEntity.getDetToday())
					.userid(detnarrEntity.getDetUserid())
.build(); 
}).collect(Collectors.toList());

};


	public static Function<List<DetnarrBean>, List <Detnarr>> addDetnarrPojoEntityMapper = (detnarrRequestBeanList) -> { 
return detnarrRequestBeanList.stream().map(detnarrRequestBean -> {
return Detnarr.builder().detnarrCK(DetnarrCK.builder()
					.detCoy(detnarrRequestBean.getCoy())
					.detTranser(detnarrRequestBean.getTranser())
					.detBunum(Objects.nonNull(detnarrRequestBean.getBunum()) ? detnarrRequestBean.getBunum() : BigInteger.ZERO.intValue())
		.build())
					.detDettype(detnarrRequestBean.getDettype())
					.detNarrative(detnarrRequestBean.getNarrative())
					.detSite(GenericAuditContextHolder.getContext().getSite())
					.detToday(LocalDateTime.now())
					.detUserid(GenericAuditContextHolder.getContext().getUserid())
		
.build();
}).collect(Collectors.toList());
} ;
	public static BiFunction<Detnarr, DetnarrBean, Detnarr> updateDetnarrEntityPojoMapper = (detnarrEntity, detnarrRequestBean) -> {
		detnarrEntity.getDetnarrCK().setDetCoy(Objects.nonNull(detnarrRequestBean.getCoy()) ? detnarrRequestBean.getCoy().trim() : detnarrEntity.getDetnarrCK().getDetCoy());
		detnarrEntity.getDetnarrCK().setDetTranser(Objects.nonNull(detnarrRequestBean.getTranser()) ? detnarrRequestBean.getTranser().trim() : detnarrEntity.getDetnarrCK().getDetTranser());
		detnarrEntity.getDetnarrCK().setDetBunum(Objects.nonNull(detnarrRequestBean.getBunum()) ? detnarrRequestBean.getBunum() : detnarrEntity.getDetnarrCK().getDetBunum());
		detnarrEntity.setDetDettype(Objects.nonNull(detnarrRequestBean.getDettype()) ? detnarrRequestBean.getDettype().trim() : detnarrEntity.getDetDettype());
		detnarrEntity.setDetNarrative(Objects.nonNull(detnarrRequestBean.getNarrative()) ? detnarrRequestBean.getNarrative().trim() : detnarrEntity.getDetNarrative());
		detnarrEntity.setDetSite(GenericAuditContextHolder.getContext().getSite()) ; 
		detnarrEntity.setDetToday(LocalDateTime.now()) ; 
		detnarrEntity.setDetUserid(GenericAuditContextHolder.getContext().getUserid()) ; 


		return detnarrEntity;
	};

}
