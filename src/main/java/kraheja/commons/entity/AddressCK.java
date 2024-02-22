package kraheja.commons.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column
    @Type(type = "kraheja.commons.utils.CharType")
	private String adrAdowner;

	@Column
    @Type(type = "kraheja.commons.utils.CharType")
	private String adrAdsegment;

	@Column
    @Type(type = "kraheja.commons.utils.CharType")
	private String adrAdtype;

	@Column
    @Type(type = "kraheja.commons.utils.CharType")
	private String adrAdser;

}
