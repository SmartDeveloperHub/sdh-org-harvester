package org.smartdeveloperhub.harvester.org.backend.pojo;

public class Affiliation {

	String uri;
	String affiliationWith;
	String affiliate;
	Role role;
	
	public String toString(){
		String newLine = System.getProperty("line.separator");
		String str="uri:"+uri+",affiliationWith:"+affiliationWith+",affiliate:"+affiliate+newLine+
				",role:{"+role.toString()+"}";				
		return str;
	}
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getAffiliationWith() {
		return affiliationWith;
	}
	public void setAffiliationWith(String affiliationWith) {
		this.affiliationWith = affiliationWith;
	}
	public String getAffiliate() {
		return affiliate;
	}
	public void setAffiliate(String affiliate) {
		this.affiliate = affiliate;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}

	
}
