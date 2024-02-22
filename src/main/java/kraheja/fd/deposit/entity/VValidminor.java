package kraheja.fd.deposit.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the V_VALIDMINOR database table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="V_VALIDMINOR")
public class VValidminor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="VMIN_ACMAJOR")
	private String vminAcmajor;

	@Column(name="VMIN_MINTYPE")
	private String vminMintype;

}