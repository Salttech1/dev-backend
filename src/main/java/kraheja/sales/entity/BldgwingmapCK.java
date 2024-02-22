package kraheja.sales.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

import kraheja.adminexp.overheads.dataentry.entity.Overheadcons;
import kraheja.adminexp.overheads.dataentry.entity.OverheadconsCK;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class BldgwingmapCK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String bwmapBldgcode;

	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String bwmapBldgwing;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
}