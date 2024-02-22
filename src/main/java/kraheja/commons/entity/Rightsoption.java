package kraheja.commons.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the RIGHTSOPTIONS database table.
 * 
 */
@Entity
@Table(name="RIGHTSOPTIONS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name="Rightsoption.findAll", query="SELECT r FROM Rightsoption r")
public class Rightsoption implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="RGOP_CODE")
	private long rgopCode;

	@Column(name="RGOP_DESCRIPTION")
	private String rgopDescription;

	@Column(name="RGOP_FLGACTIVE")
	private String rgopFlgactive;

	@Column(name="RGOP_SORTORDER")
	private Integer rgopSortorder;
}