package kraheja.commons.utils;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import kraheja.commons.bean.ActrandBean;
import kraheja.commons.bean.ExnarrBean;

@Component
public class GenericExnarrLogic {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public static List<ExnarrBean> generateExnarrBean(ActrandBean actrandBean, String narrative) {
		if (StringUtils.isNoneBlank(narrative) && narrative.length() > 40) {
			List<ExnarrBean> exnarrBeanList = new ArrayList<>();
			StringBuilder splittedString = new StringBuilder();
			int length = narrative.length();
			int index = 0;
			Integer linenum = 0;

			while (length > 0) {
				int endIndex = index + 39;
				if (length <= 39) {
					endIndex = index + length;
				}
				splittedString.append(narrative.substring(index, endIndex));
				linenum++;
				exnarrBeanList.add(ExnarrBean.builder()
						.transer(actrandBean.getTranser())
						.bunum(actrandBean.getBunum().doubleValue())
						.oldbunum(Objects.nonNull(actrandBean.getOldbunum()) ? actrandBean.getOldbunum().doubleValue() : null)
						.coy(actrandBean.getCoy())
						.linenum(linenum.doubleValue())
						.narrtype("S")
						.today(LocalDateTime.now())
						.site(actrandBean.getSite())
						.userid(actrandBean.getUserid())
						.exnarr(narrative.substring(index, endIndex)).build());
				length -= 39;
				index += 39;
			}
			exnarrBeanList.remove(0);
			LOGGER.info("ExnarrBean :: {}", exnarrBeanList);
			return exnarrBeanList;
		}
		return null;
	}
}
