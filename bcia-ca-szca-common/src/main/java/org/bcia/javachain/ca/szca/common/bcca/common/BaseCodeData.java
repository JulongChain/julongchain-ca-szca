package org.bcia.javachain.ca.szca.common.bcca.common;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.MapsId;
import javax.persistence.Table;
 
@Entity
@Table(name = "sys_basecode")
public class BaseCodeData extends BaseCodeId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1006350474299157823L;
//	private String section;
//	private String key;
	private String value;
	private String description;
	
	@MapsId("BaseCodeId")  
    @EmbeddedId  
    @AttributeOverrides({  
        @AttributeOverride(name = "section", column = @Column(name = "section")),  
        @AttributeOverride(name = "key", column = @Column(name = "key"))  
    })  
    private BaseCodeId baseCodeId;
	
	 
//	public String getSection() {
//		return section;
//	}
//	public void setSection(String section) {
//		this.section = section;
//	}
//	public String getKey() {
//		return key;
//	}
//	public void setKey(String key) {
//		this.key = key;
//	}
	/*
	@Column(insertable=false)
	public String getSection() {
		return baseCodeId.getSection();
	}
	public void setSection(String section) {
		this.baseCodeId.setSection(section);
	}
	@Column(insertable=false)
	public String getKey() {
		return baseCodeId.getKey();
	}
	public void setKey(String key) {
		this.baseCodeId.setKey(key);
	}
	*/
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}