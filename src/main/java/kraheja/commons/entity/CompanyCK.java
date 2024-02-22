package kraheja.commons.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CompanyCK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column
	private String coyCode;

	@Column
	private Date coyClosedate;

	@Column
	private String coyProp;

	public String getCoyCode() {
		return coyCode;
	}

	public void setCoyCode(String coyCode) {
		this.coyCode = coyCode;
	}

	public Date getCoyClosedate() {
		return coyClosedate;
	}

	public void setCoyClosedate(Date coyClosedate) {
		this.coyClosedate = coyClosedate;
	}

	public String getCoyProp() {
		return coyProp;
	}

	public void setCoyProp(String coyProp) {
		this.coyProp = coyProp;
	}

	public CompanyCK() {
		super();
	}

	public CompanyCK(String coyCode, Date coyClosedate, String coyProp) {
		super();
		this.coyCode = coyCode;
		this.coyClosedate = coyClosedate;
		this.coyProp = coyProp;
	}
}
