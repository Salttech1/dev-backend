package kraheja.commons.bean.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class PartyAddressResponseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private PartyResponseBean partyResponseBean;
	
	private AddressResponseBean addressResponseBean;
}