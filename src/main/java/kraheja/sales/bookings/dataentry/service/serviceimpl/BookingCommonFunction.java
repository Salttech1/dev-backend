package kraheja.sales.bookings.dataentry.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import kraheja.commons.utils.CommonResultsetGenerator;
import kraheja.commons.utils.ValueContainer;

public class BookingCommonFunction {

	public static Integer FuncCompute_StampDuty(Integer IntPrmFlatCost, String StrPrmUniType, String StrPrmBookDate) {
		Integer IntStamduty = 0;
		String StrLocStamp_UType = "", DblLocLower_Limit = "", DblLocUpper_Limit = "", DblLocFixed_Amt = "",
				DblLocPercent = "";
		String StrLocStamp_Class = "", StrLocWherclaus = "";

		StrLocStamp_UType = CommonResultsetGenerator.queryToSingalValue("SELECT ent_char1 FROM entity WHERE ent_id ="
				.concat(StrPrmUniType.trim().concat("and ent_class = 'ACOMM'")));

		if (StrLocStamp_UType == "") {
			IntStamduty = 0;
		} 
		else {
				if (StrLocStamp_UType.trim().contains("R") ) {
					StrLocStamp_Class = "RSTAM";
				} 
				else 
				{
					StrLocStamp_Class = "CSTAM";
				}

				if (StrLocStamp_Class != "") {
					StrLocWherclaus = " ent_class = '" + StrLocStamp_Class + "'";
				}

					if (IntPrmFlatCost >= 0) 
					{
		
						if (StrLocWherclaus == "") {
							StrLocWherclaus = " ent_num1 <= '" + IntPrmFlatCost + "' and ent_num2 >= '" + IntPrmFlatCost + "'";
						} else {
							StrLocWherclaus = StrLocWherclaus + " and ent_num1 <= '" + IntPrmFlatCost + "' and ent_num2 >= '"
									+ IntPrmFlatCost + "'";
						}
					}
					if (StrPrmBookDate.trim() != "") {
						if (StrLocWherclaus == "") {
							StrLocWherclaus = " to_date('" + StrPrmBookDate + "','dd.mm.yyyy') between ent_date1 and ent_date2";
						} else {
							StrLocWherclaus = StrLocWherclaus + " and to_date('" + StrPrmBookDate
									+ "','dd.mm.yyyy') between ent_date1 and ent_date2";
						}
		
					}
		
					List<Map<String, Object>> tuplesList = CommonResultsetGenerator.queryToResultSetBuilder(
							"SELECT ent_num1, ent_num2,ent_num3, ent_num4 FROM entity WHERE  ".concat(StrLocWherclaus));
		
					ValueContainer<List<Object>> stampdutyDtls = new ValueContainer<List<Object>>();
					List<Object> valueList = new ArrayList<Object>();
					tuplesList.get(0).entrySet().stream().map(valueMap -> {
						valueList.add(valueMap.getValue());
						stampdutyDtls.setValue(valueList);
						return valueMap;
					}).collect(Collectors.toList());
		
					DblLocLower_Limit = Objects.nonNull(stampdutyDtls.getValue().get(0))
							? stampdutyDtls.getValue().get(0).toString()
							: "";
		
					DblLocUpper_Limit = Objects.nonNull(stampdutyDtls.getValue().get(1))
							? stampdutyDtls.getValue().get(1).toString()
							: "";
		
					DblLocFixed_Amt = Objects.nonNull(stampdutyDtls.getValue().get(2))
							? stampdutyDtls.getValue().get(2).toString()
							: "";
		
					DblLocPercent = Objects.nonNull(stampdutyDtls.getValue().get(3))
							? stampdutyDtls.getValue().get(3).toString()
							: "";
		
					IntPrmFlatCost = IntPrmFlatCost - Integer.parseInt(DblLocLower_Limit) - 1;
					IntStamduty = IntPrmFlatCost / 100 * Integer.parseInt(DblLocPercent);
					IntStamduty = IntStamduty + Integer.parseInt(DblLocFixed_Amt);

		}

		return IntStamduty;
	}
}
