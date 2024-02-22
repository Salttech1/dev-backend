package kraheja.commons.mappers.pojoentity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import kraheja.commons.bean.ExnarrBean;
import kraheja.commons.entity.Exnarr;
import kraheja.commons.entity.ExnarrCK;

public class ExnarrMapper {

	public static Function<List<ExnarrBean>, List<Exnarr>> addExnarrMapperList = exnarrBeanList -> {
		List<Exnarr> exnarrList = new ArrayList<>(); 
		exnarrBeanList.stream().map(exnarrBean -> {
			exnarrList.add(Exnarr.builder()
					.exnarrCK(ExnarrCK.builder().exnTranser(exnarrBean.getTranser()).exnBunum(exnarrBean.getBunum().doubleValue()).exnCoy(exnarrBean.getCoy()).exnLinenum(exnarrBean.getLinenum()).exnNarrtype(exnarrBean.getNarrtype()).build())
					.exnSite(exnarrBean.getSite())
					.exnToday(LocalDateTime.now())
					.exnUserid(exnarrBean.getUserid())
					.exnExnarr(exnarrBean.getExnarr())
					.build());
			return exnarrBean;
		}).collect(Collectors.toList());
		return exnarrList;
	};
	
	public static Function<ExnarrBean, Exnarr> addExnarrMapper = exnarrBean -> {
		return Exnarr.builder()
					.exnarrCK(ExnarrCK.builder().exnTranser(exnarrBean.getTranser()).exnBunum(exnarrBean.getBunum().doubleValue()).exnCoy(exnarrBean.getCoy()).exnLinenum(exnarrBean.getLinenum()).exnNarrtype(exnarrBean.getNarrtype()).build())
					.exnSite(exnarrBean.getSite())
					.exnToday(LocalDateTime.now())
					.exnUserid(exnarrBean.getUserid())
					.exnExnarr(exnarrBean.getExnarr())
					.build();
	};
}
