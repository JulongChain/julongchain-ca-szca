

package org.bcia.javachain.ca.szca.common.bcca.core.ejb.hardtoken;

import java.io.Serializable;

public class HardTokenPropertyDataPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public String id;
	public String property;

	public HardTokenPropertyDataPK() { }

	public HardTokenPropertyDataPK(String id, String property) {
		setId(id);
		setProperty(property);
	}

    //@Column
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	
    //@Column
	public String getProperty() { return property; }
	public void setProperty(String property) { this.property = property; }

	public int hashCode() {
		int hashCode = 0;
		if (id != null) { hashCode += id.hashCode(); }
		if (property != null) { hashCode += property.hashCode(); }
		return hashCode;
	}

	public boolean equals(Object obj) {
		if ( obj == this ) { return true; }
		if ( !(obj instanceof HardTokenPropertyDataPK) ) { return false; }
		HardTokenPropertyDataPK pk = (HardTokenPropertyDataPK)obj;
		if ( id == null || !id.equals(pk.id) ) { return false; }
		if ( property == null || !property.equals(pk.property) ) { return false; }
		return true;
	}
}
