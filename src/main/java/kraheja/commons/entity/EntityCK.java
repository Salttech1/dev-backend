package kraheja.commons.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EntityCK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column
	private String entClass;

	@Column
	private String entId;

	@Column
	private String entChar1;

	@Column
	private String entChar2;

	@Column
	private String entChar3;
}
