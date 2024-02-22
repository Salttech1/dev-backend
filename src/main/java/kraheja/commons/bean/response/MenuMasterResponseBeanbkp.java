package kraheja.commons.bean.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class MenuMasterResponseBeanbkp {

	@JsonProperty(value ="code")
	private Integer faMenucode;
	
	@JsonProperty(value ="description")
	private String faMenuDescription;
	
	@JsonProperty(value ="neumonic")
	private String faMenuNeumonic;
	
	@JsonProperty(value ="treelink")
	private Integer faMenuTreelink;
	
	@JsonProperty(value ="treelevel")
	private Integer faMenuTreelevel;
	
	@JsonProperty(value ="menu_order")
	private Integer faMenuMenuorder;
	
	@JsonProperty(value ="shortcut_key")
	private String faMenuShortcutkey;
	
	@JsonProperty(value ="flgactive")
	private String faMenuFlgactive;
	
	@JsonProperty(value ="sepgroup")
	private Integer faMenuSepgroup;
	
	@JsonProperty(value ="sub_menu")
	private List<MenuMasterResponseBeanbkp> faMenuResponseBeanList;
}
