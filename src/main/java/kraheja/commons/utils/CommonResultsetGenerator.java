package kraheja.commons.utils;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommonResultsetGenerator {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private static EntityManager entityManager;

	@PersistenceContext
	public void setEntityRepository(EntityManager entityManager) {
		CommonResultsetGenerator.entityManager = entityManager;
	}

	public static List<Map<String, Object>> queryToResultSetBuilder(String selectQuery) {
		Query query = entityManager.createNativeQuery(selectQuery);
		NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
		nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		logger.info("QUERY SET :: " + nativeQuery);
		return nativeQuery.getResultList();
	}

	@SuppressWarnings("unchecked")
	public static String queryToSingalValue(String selectQuery) {
		String Value = "";
		ValueContainer<List<Object>> valueList1 = new ValueContainer<List<Object>>();
		List<Map<String, Object>> valueList = new ArrayList<Map<String,Object>>();

		Query query = entityManager.createNativeQuery(selectQuery);
		NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
		nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		if (CollectionUtils.isNotEmpty(nativeQuery.getResultList())) {
			valueList.addAll((List<Map<String, Object>>) nativeQuery.getResultList());

			Optional<Object> firstElementValue = valueList.get(0).values().stream().findFirst();
			Value = firstElementValue.isPresent() ? firstElementValue.get().toString() : Value;
		}
		return Value;
	}

	public static Double getNumericSingleQueryValue(String selectQuery) {
		Query createNativeQuery = entityManager.createNativeQuery(selectQuery);

		Object singleResult = createNativeQuery.getSingleResult();
		return Objects.nonNull(singleResult) ? Double.valueOf(singleResult.toString()) : null;
	}
	
	public static String getStringSingleQueryValue(String selectQuery) {
		Query createNativeQuery = entityManager.createNativeQuery(selectQuery);

		Object singleResult = createNativeQuery.getSingleResult();
		return Objects.nonNull(singleResult) ? singleResult.toString() : null;
	}
}
