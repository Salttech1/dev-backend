package kraheja.commons.converter;

import java.io.IOException;
import java.util.List;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import kraheja.commons.bean.ParameterValidBean;
import kraheja.commons.utils.CommonUtils;

public class ReportMasterMetaConverter implements AttributeConverter<List<ParameterValidBean>, String> {
	    @Override
	    public String convertToDatabaseColumn(List<ParameterValidBean> customerInfo) {

	        String customerInfoJson = null;
	        try {
	            customerInfoJson = CommonUtils.INSTANCE.objectMapper.writeValueAsString(customerInfo);
	        } catch (final JsonProcessingException e) {
	        }

	        return customerInfoJson;
	    }

	    @Override
	    public List<ParameterValidBean> convertToEntityAttribute(String customerInfoJSON) {
	    	List<ParameterValidBean> customerInfo = null;
	        try {
	            customerInfo =CommonUtils.INSTANCE.objectMapper.readValue(customerInfoJSON, new TypeReference<List<ParameterValidBean>>(){});
	        } catch (final IOException e) {
	        }

	        return customerInfo;
	    }

	}	
