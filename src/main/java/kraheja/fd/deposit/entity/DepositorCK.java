package kraheja.fd.deposit.entity;

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
public class DepositorCK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column
    @Type(type = "kraheja.commons.utils.CharType")
	private String deptrCoy;

	@Column
    @Type(type = "kraheja.commons.utils.CharType")
	private String deptrDepositor;

}
