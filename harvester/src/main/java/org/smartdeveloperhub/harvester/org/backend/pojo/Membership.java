package org.smartdeveloperhub.harvester.org.backend.pojo;

public class Membership {
	String uri="";	
	String membershipon="";
	String member="";
	Position position;
	
	public String toString(){
		String newLine = System.getProperty("line.separator");
		String str="uri:"+uri+",membershipOn:"+membershipon+",member:"+member+newLine+
				",position:{"+position.toString()+"}";				
		return str;
	}
		
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getMembershipon() {
		return membershipon;
	}
	public void setMembershipon(String membershipon) {
		this.membershipon = membershipon;
	}
	public String getMember() {
		return member;
	}
	public void setMember(String member) {
		this.member = member;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	
}
