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
 *   Artifact    : org.smartdeveloperhub.harvester.org:org-harvester-frontend:0.2.0-SNAPSHOT
 *   Bundle      : org-harvester.war
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 */
package org.smartdeveloperhub.harvester.org.backend.pojo;

import java.util.ArrayList;

public class Project {

	String id="";
	String name="";
	String description="";
	String uri="";
	String depicts ="";
	ArrayList<String> affiliation = new ArrayList<String>();
	ArrayList<String> repository = new ArrayList<String>();	
	ArrayList<String> location = new ArrayList<String>();
	String createdOn ="";
	
//	ArrayList<String> role = new ArrayList<String>();

	public String getUri() {
		return uri;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getDepicts() {
		return depicts;
	}
	public void setDepicts(String depicts) {
		this.depicts = depicts;
	}
	public void setUri(String uri) {
		this.uri = uri;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<String> getAffiliation() {
		return affiliation;
	}
	public void setAffiliation(ArrayList<String> affiliation) {
		this.affiliation = affiliation;
	}
	public ArrayList<String> getRepository() {
		return repository;
	}
	public void setRepository(ArrayList<String> repository) {
		this.repository = repository;
	}	
	
	public ArrayList<String> getLocation() {
		return location;
	}
	public void setLocation(ArrayList<String> location) {
		this.location = location;
	}
	
	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", description=" + description + ", uri=" + uri + ", depicts="
				+ depicts + ", affiliation=" + affiliation + ", repository=" + repository + ", location=" + location
				+ ", createdOn=" + createdOn + "]";
	}
	
	
//	public ArrayList<String> getRole() {
//		return role;
//	}
//	public void setRole(ArrayList<String> role) {
//		this.role = role;
//	}
	
	
}
