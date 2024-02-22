package kraheja.commons.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the MODULERIGHTS database table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="MODULERIGHTS", indexes = {
		@Index(name = "CPK_MENURIGHTS", columnList = "mdrgMenucd ASC, mdrgRightsoptioncd ASC", unique = true),
})
public class Moduleright implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ModulerightCK modulerightCK;

	@Column(name="MDRG_CODE")
	private Integer mdrgCode;
}