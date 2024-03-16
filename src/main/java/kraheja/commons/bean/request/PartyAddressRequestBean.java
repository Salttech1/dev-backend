package kraheja.commons.bean.request;

import java.io.Serializable;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.commons.bean.GSTValdiationBean;
import kraheja.commons.entity.Party_Ex;
import kraheja.commons.entity.Partycontactinfo;
import kraheja.commons.entity.Partyenterpriseinfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class PartyAddressRequestBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Valid
	private GSTValdiationBean gstValdiationBean;
	
	private PartyRequestBean partyRequestBean;
	
	private AddressRequestBean addressRequestBean;
}