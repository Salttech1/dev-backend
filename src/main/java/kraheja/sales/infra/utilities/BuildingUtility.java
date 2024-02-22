package kraheja.sales.infra.utilities;

import java.util.HashMap;
import java.util.Map;

public class BuildingUtility {
	public static Map<String, String> ownerIdToDetail(String ownerId) {
		Map<String, String> bldgMap = new HashMap<>();
		bldgMap.put("bldgCode", ownerId.substring(0, 4));
		bldgMap.put("wing", ownerId.substring(4, 5));
		bldgMap.put("flatNumber", ownerId.substring(5, 12).trim());
		return bldgMap;
	}
}
