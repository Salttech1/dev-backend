package kraheja.commons.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ModulerightCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column
	private Integer mdrgMenucd;

	@Column
	private Integer mdrgRightsoptioncd;

	public Integer getMdrgMenucd() {
		return mdrgMenucd;
	}

	public void setMdrgMenucd(Integer mdrgMenucd) {
		this.mdrgMenucd = mdrgMenucd;
	}

	public Integer getMdrgRightsoptioncd() {
		return mdrgRightsoptioncd;
	}

	public void setMdrgRightsoptioncd(Integer mdrgRightsoptioncd) {
		this.mdrgRightsoptioncd = mdrgRightsoptioncd;
	}

	public ModulerightCK() {
		super();
	}

	public ModulerightCK(Integer mdrgMenucd, Integer mdrgRightsoptioncd) {
		super();
		this.mdrgMenucd = mdrgMenucd;
		this.mdrgRightsoptioncd = mdrgRightsoptioncd;
	}
	
	@Override
	public String toString() {
		return "ModulerightCK [mdrgMenucd=" + mdrgMenucd + ", mdrgRightsoptioncd=" + mdrgRightsoptioncd + "]";
	}
}
