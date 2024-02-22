package kraheja.sales.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "OBILL1", columnList = "obillBldgcode Asc, obillOwnerid Asc, obillBillnum Asc, obillMonth Asc", unique = true) })

@Entity
@NamedStoredProcedureQuery(name = "OutBill.inserttempowner", procedureName = "SPR_INSERTTEMPFLATOWNER", parameters = {
		@StoredProcedureParameter(mode = ParameterMode.OUT, name = "pOut", type = Integer.class) })

public class OutBill implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OutbillCK outbillCK;

//	@Column(name = "OBILL_WING")
//	private String obillWing;

	@Embeddable
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor

	public static class OutbillCK implements Serializable {

		private static final long serialVersionUID = 1L;

		@Type(type = "kraheja.commons.utils.CharType")
		private String obillBldgcode;

		@Type(type = "kraheja.commons.utils.CharType")
		private String obillOwnerid;

		@Type(type = "kraheja.commons.utils.CharType")
		private String obillBillnum;

		@Type(type = "kraheja.commons.utils.CharType")
		private String obillMonth;
	}
}
