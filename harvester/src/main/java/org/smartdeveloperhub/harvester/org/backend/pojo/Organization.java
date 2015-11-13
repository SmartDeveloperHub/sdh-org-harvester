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
 *   Artifact    : org.smartdeveloperhub.harvester.org:org-harvester-frontend:0.1.0-SNAPSHOT
 *   Bundle      : org-harvester.war
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 */
package org.smartdeveloperhub.harvester.org.backend.pojo;

import java.util.ArrayList;

public class Organization {

	boolean organizationalCollaboration=false;
	String prefLabel = "";
	String title="";
	String description = "";
	String id = "";
	String uri="";
	String classification="";
	String purpose="";
	String depicts ="";
	ArrayList<String> hasMemberOrganization = new ArrayList<String>();
	ArrayList<String> hasProject = new ArrayList<String>();
	ArrayList<String> hasProduct = new ArrayList<String>();
	ArrayList<String> hasMember = new ArrayList<String>();
	ArrayList<String> membership = new ArrayList<String>();
	ArrayList<String> position = new ArrayList<String>();
	ArrayList<String> role = new ArrayList<String>();
		
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPrefLabel() {
		return prefLabel;
	}
	public void setPrefLabel(String prefLabel) {
		this.prefLabel = prefLabel;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public ArrayList<String> getHasMemberOrganization() {
		return hasMemberOrganization;
	}
	public void setHasMemberOrganization(ArrayList<String> hasMemberOrganization) {
		this.hasMemberOrganization = hasMemberOrganization;
	}
	public ArrayList<String> getHasProject() {
		return hasProject;
	}
	public void setHasProject(ArrayList<String> hasProject) {
		this.hasProject = hasProject;
	}
	public ArrayList<String> getHasMember() {
		return hasMember;
	}
	public void setHasMember(ArrayList<String> hasMember) {
		this.hasMember = hasMember;
	}
	public ArrayList<String> getMembership() {
		return membership;
	}
	public void setMembership(ArrayList<String> membership) {
		this.membership = membership;
	}
	
	public void setPosition(ArrayList<String> position) {
		this.position = position;
	}
	public ArrayList<String> getPosition() {
		return position;
	}
	public ArrayList<String> getRole() {
		return role;
	}
	public void setRole(ArrayList<String> role) {
		this.role = role;
	}
		
	public boolean isOrganizationalCollaboration() {
		return organizationalCollaboration;
	}
	public void setOrganizationalCollaboration(boolean organizationalCollaboration) {
		this.organizationalCollaboration = organizationalCollaboration;
	}
		
	public ArrayList<String> getHasProduct() {
		return hasProduct;
	}
	public void setHasProduct(ArrayList<String> hasProduct) {
		this.hasProduct = hasProduct;
	}		
	
	public String getDepicts() {
		return depicts;
	}
	public void setDepicts(String depicts) {
		this.depicts = depicts;
	}
	
	@Override
	public String toString() {
		return "Organization [organizationalCollaboration=" + organizationalCollaboration + ", prefLabel=" + prefLabel
				+ ", title=" + title + ", description=" + description + ", id=" + id + ", uri=" + uri
				+ ", classification=" + classification + ", purpose=" + purpose + ", depicts=" + depicts
				+ ", hasMemberOrganization=" + hasMemberOrganization + ", hasProject=" + hasProject + ", hasProduct="
				+ hasProduct + ", hasMember=" + hasMember + ", membership=" + membership + ", position=" + position
				+ ", role=" + role + "]";
	}
	
}
