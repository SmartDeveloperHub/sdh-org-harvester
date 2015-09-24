package org.smartdeveloperhub.harvester.org.backend.pojo;

public class Role {

	String uri;
	String label;
	String roleTypeUri;
	String roleTypeLabel;
	
	public String toString(){		
		String str="uri:"+uri+",label:"+label+",roleTypeUri:"+roleTypeUri+",roleTypeLabel:"+"roleTypeLabel";				
		return str;
	}
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getRoleTypeUri() {
		return roleTypeUri;
	}
	public void setRoleTypeUri(String roleTypeUri) {
		this.roleTypeUri = roleTypeUri;
	}
	public String getRoleTypeLabel() {
		return roleTypeLabel;
	}
	public void setRoleTypeLabel(String roleTypeLabel) {
		this.roleTypeLabel = roleTypeLabel;
	}
	
	
}
