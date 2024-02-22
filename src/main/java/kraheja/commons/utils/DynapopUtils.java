package kraheja.commons.utils;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import kraheja.commons.entity.Dynapop;

public enum DynapopUtils {

	INSTANCE;

	public boolean validateDynaPop(Dynapop dynapopEntity) {
		if (null != dynapopEntity.getDynColumn1() && null != dynapopEntity.getDynTable()) {
			return false;
		}
		return true;
	}

	public String validateString(String str) {
		return str != null ? str.trim(): "";
	}

	public String validateColumnString(String str) {
		return str != null ? ", " + str.trim(): "";
	}
	
	public String dateColumnFormatter(String str, String coltype) {
		if(Objects.nonNull(coltype) &&  coltype.equals("D"))
			return str != null    ?  "NVL(to_char(" + str +", 'dd/mm/yyyy'), '')"  : null;
		return str != null    ? str : null;
	}

	public String createWhereFields(Dynapop dynapopEntity) {
		String selectString = " WHERE " + dynapopEntity.getDynCondition();
		return selectString;
	}
	
	public String createSiteFilter(Dynapop dynapopEntity, String substitutedString) {
		return Objects.nonNull(dynapopEntity.getDynSitefilter())  &&  StringUtils.isNotBlank(substitutedString) ? substitutedString  : CommonConstraints.INSTANCE.SPACE_STRING;
	}

	public String createOrderByFields(Dynapop dynapopEntity) {
		String selectString = " ORDER BY " + dynapopEntity.getDynOrderby();
		return selectString;
	}

	public String formatstr(Dynapop dynapopEntity, String dateFromDb, String coltype, String formatFromDb) {
		String selectString = null;
		switch (coltype) {
		case "D":
			 selectString = " TO_DATE( " + dateFromDb +" , "+ formatFromDb + " ) ";
			break;
		case "N":
			//		    System.out.println("Tuesday");
			break;
		}		
		return selectString;
	}
	
	public StringBuilder bbcResultSet(Dynapop dynapopEntity, StringBuilder query, String searchText) {
		switch(dynapopEntity.getDynBbc().intValue()) {
		case 1:
			query.append("UPPER(".concat(dynapopEntity.getDynColumn1().contains(" AS ")?dynapopEntity.getDynColumn1().substring(0, dynapopEntity.getDynColumn1().indexOf(" AS ")).concat(")"):dynapopEntity.getDynColumn1().concat(")")));
			query.append(CommonConstraints.INSTANCE.EQUAL_TO_STRING);
			if(dynapopEntity.getDynColtype1().equals("N"))
				query.append(searchText);
			else
				query.append("'"+searchText+"'");
			return query;
		case 2:
			query.append("UPPER(".concat(dynapopEntity.getDynColumn2().contains(" AS ")?dynapopEntity.getDynColumn2().substring(0, dynapopEntity.getDynColumn2().indexOf(" AS ")).concat(")"):dynapopEntity.getDynColumn2().concat(")")));
			query.append(CommonConstraints.INSTANCE.EQUAL_TO_STRING);
			if(dynapopEntity.getDynColtype2().equals("N"))
				query.append(searchText);
			else
				query.append("'"+searchText+"'");
			return query;
		case 3:
			query.append("UPPER(".concat(dynapopEntity.getDynColumn3().contains(" AS ")?dynapopEntity.getDynColumn3().substring(0, dynapopEntity.getDynColumn3().indexOf(" AS ")).concat(")"):dynapopEntity.getDynColumn3().concat(")")));
			query.append(CommonConstraints.INSTANCE.EQUAL_TO_STRING);
			if(dynapopEntity.getDynColtype3().equals("N"))
				query.append(searchText);
			else
				query.append("'"+searchText+"'");
			return query;
		case 4:
			query.append("UPPER(".concat(dynapopEntity.getDynColumn4().contains(" AS ")?dynapopEntity.getDynColumn4().substring(0, dynapopEntity.getDynColumn4().indexOf(" AS ")).concat(")"):dynapopEntity.getDynColumn4().concat(")")));
			query.append(CommonConstraints.INSTANCE.EQUAL_TO_STRING);
			if(dynapopEntity.getDynColtype4().equals("N"))
				query.append(searchText);
			else
				query.append("'"+searchText+"'");
			return query;
		case 5:
			query.append("UPPER(".concat(dynapopEntity.getDynColumn5().contains(" AS ")?dynapopEntity.getDynColumn5().substring(0, dynapopEntity.getDynColumn5().indexOf(" AS ")).concat(")"):dynapopEntity.getDynColumn5().concat(")")));
			query.append(CommonConstraints.INSTANCE.EQUAL_TO_STRING);
			if(dynapopEntity.getDynColtype5().equals("N"))
				query.append(searchText);
			else
				query.append("'"+searchText+"'");
			return query;
		default :
			return query;
		}	
	}
	
	public StringBuilder appendRequestQuery(StringBuilder query, String requestQuery) {
		query.append(requestQuery);
		query.append(" AND ");
		return query;
	}
}
