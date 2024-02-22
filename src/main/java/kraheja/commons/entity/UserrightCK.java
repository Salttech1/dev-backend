package kraheja.commons.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class UserrightCK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column
	private String usrtStaffcode;

	@Column
	private Integer usrtModulerightcode;

	public UserrightCK() {
	}

	public UserrightCK(String usrtStaffcode, Integer usrtModulerightcode) {
		super();
		this.usrtStaffcode = usrtStaffcode;
		this.usrtModulerightcode = usrtModulerightcode;
	}
}
