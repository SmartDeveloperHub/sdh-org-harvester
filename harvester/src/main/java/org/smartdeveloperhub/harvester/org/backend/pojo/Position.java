package org.smartdeveloperhub.harvester.org.backend.pojo;

public class Position {
	String uri;
	String label;
	String positionTypeUri;
	String positionTypeLabel;
	
	public String toString(){		
		String str="uri:"+uri+",label:"+label+",positionTypeUri:"+positionTypeUri+",positionTypeLabel:"+"positionTypeLabel";				
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
	public String getPositionTypeUri() {
		return positionTypeUri;
	}
	public void setPositionTypeUri(String positionTypeUri) {
		this.positionTypeUri = positionTypeUri;
	}
	public String getPositionTypeLabel() {
		return positionTypeLabel;
	}
	public void setPositionTypeLabel(String positionTypeLabel) {
		this.positionTypeLabel = positionTypeLabel;
	}

	
	

}
