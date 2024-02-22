package kraheja.sales.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

import kraheja.adminexp.overheads.dataentry.entity.OverheadtxnCK;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class FlatownerCK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String fownOwnerid;

	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String fownBldgcode;

	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String fownWing;

	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String fownFlatnum;

	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String fownOwnertype;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
}