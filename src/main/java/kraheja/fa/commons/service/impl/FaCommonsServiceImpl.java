// 29.02.24 RS  -  FA Common APIs

package kraheja.fa.commons.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.fa.commons.service.FaCommonsService;
//import lombok.extern.log4j.Log4j2;

@Service
@Transactional
public class FaCommonsServiceImpl implements FaCommonsService {
	@Autowired
	private EntityManager entityManager;

	@Override
	public ResponseEntity<?> fetchMergedCoy(String coy, String mergeDate) {
		Query query = this.entityManager.createNativeQuery(
				"select listagg('''' || merg_coy	|| ''' ', ',') within group (order by merg_coy) AS coyCodes , "
						+ "listagg(merg_confirmyn 	, ',') within group (order by merg_confirmyn) AS ConfirmYN "
						+ "from ( " + "select distinct RTrim(merg_coy) as merg_coy , merg_confirmyn from mergecoy "
						+ "WHERE merg_mergecoy IN (" + coy + ") AND merg_mergeddate <= '" + mergeDate + "' ) ",
				Tuple.class);
		List<Tuple> mergeCoyList = query.getResultList();
		String coyCodesValues = "", mergeConfirmYn = "";
		for (Tuple mergeCoy : mergeCoyList) {
			coyCodesValues = coyCodesValues + mergeCoy.get("coyCodes", String.class);
			mergeConfirmYn = mergeConfirmYn + mergeCoy.get("ConfirmYN", String.class);
		}
		if (coyCodesValues.equals("null") == false) { // Company is merged
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(coyCodesValues)
					.extraData(mergeConfirmYn).build());
		}
		// Company is not merged
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
				.message("No merge data for the selections").build());
	}
}
