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
package org.smartdeveloperhub.harvester.org.frontend.core.membership;

public interface MembershipVocabulary {

	static final String TYPE       = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
	static final String MEMBERSHIP_CLASS = "http://www.smartdeveloperhub.org/vocabulary/organization#Membership";
	static final String MEMBERSHIPON = "http://www.smartdeveloperhub.org/vocabulary/organization#membershipOn";	
	static final String MEMBER = "http://www.smartdeveloperhub.org/vocabulary/organization#member";

	static final String POSITION = "http://www.smartdeveloperhub.org/vocabulary/organization#position";
	static final String POSITIONLABEL="http://www.w3.org/2000/01/rdf-schema#label";
	
	static final String POSITIONTYPE = "http://www.smartdeveloperhub.org/vocabulary/organization#positionType";
	static final String POSITIONTYPECLASS="http://www.w3.org/2004/02/skos/core#Concept";
	static final String POSITIONTYPELABEL="http://www.w3.org/2004/02/skos/core#prefLabel";
			
	static final String POSITIONCLASS = "http://www.smartdeveloperhub.org/vocabulary/organization#Position";
	
	static final String ORGID = "http://www.smartdeveloperhub.org/vocabulary/organization#id";
	static final String PERSONID = "http://www.smartdeveloperhub.org/vocabulary/organization#id";
	
	
	
}
