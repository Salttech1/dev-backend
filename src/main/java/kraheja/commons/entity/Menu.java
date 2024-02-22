package kraheja.commons.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the MENU database table.
 * 
 */
@javax.persistence.Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name="Menu.findAll", query="SELECT m FROM Menu m")
public class Menu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer code;

	private String advrepyn;

	@Column(name="DATE_COLUMNNAME")
	private String dateColumnname;

	@Column(name="DATE_COLUMNTEXT")
	private String dateColumntext;

	private String description;

	private String directory;

	private String dllname;

	private String flgactive;

	private String formname;

	private Integer menuorder;

	private String multiformyn;

	private String multisaltypeyn;

	private String neumonic;

	private String reportname;

	private String rptname;

	private Integer sepgroup;

	private String shortcutkey;

	private Integer treelevel;

	private Integer treelink;

	@Column(name="ADD_URI")
	private String addUri;

	@Column(name="UPDATE_URI")
	private String updateUri;

	@Column(name="DELETE_URI")
	private String deleteUri;
	
	@Column(name="TABLE_NAMES")
	private String tableNames;
}