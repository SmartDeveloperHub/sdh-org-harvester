/**
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 *   This file is part of the Smart Developer Hub Project:
 *     http://www.smartdeveloperhub.org/
 *
 *   Center for Open Middleware
 *     http://www.centeropenmiddleware.com/
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 *   Copyright (C) 2015 Center for Open Middleware.
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 *   Artifact    : org.smartdeveloperhub.harvester.org:org-harvester-ldp4j:0.2.0-SNAPSHOT
 *   Bundle      : org-harvester.war
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 */
package org.smartdeveloperhub.harvester.org.backend.pojo;

import java.util.ArrayList;

public class Person {
	String id="";
	String name="";
	String nick="";
	String mbox="";
	String homepage="";
	String img="";
	ArrayList<String> memberOf = new ArrayList<String>();
	ArrayList<String> hasMembership = new ArrayList<String>();
	ArrayList<String> isAffiliated = new ArrayList<String>();
	
	public String toString(){
		String newLine = System.getProperty("line.separator");
		String str="id:"+id+",name:"+name+",nick:"+nick+",mbox:"+mbox+",homepage:"+homepage+",img:"+img+newLine
				+ ",memberOf:"+ memberOf.toString() + newLine
				+ ",hasMembershi:"+hasMembership.toString() + newLine
				+ ",isAffiliated:"+isAffiliated.toString();
		return str;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getMbox() {
		return mbox;
	}
	public void setMbox(String mbox) {
		this.mbox = mbox;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public ArrayList<String> getMemberOf() {
		return memberOf;
	}
	public void setMemberOf(ArrayList<String> memberOf) {
		this.memberOf = memberOf;
	}
	public ArrayList<String> getHasMembership() {
		return hasMembership;
	}
	public void setHasMembership(ArrayList<String> hasMembership) {
		this.hasMembership = hasMembership;
	}
	public ArrayList<String> getIsAffiliated() {
		return isAffiliated;
	}
	public void setIsAffiliated(ArrayList<String> isAffiliated) {
		this.isAffiliated = isAffiliated;
	}
	
	
	

}
