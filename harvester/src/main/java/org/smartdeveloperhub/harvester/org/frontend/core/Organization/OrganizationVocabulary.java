/**
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 *   This file is part of the Smart Developer Hub Project:
 *     http://www.smartdeveloperhub.org/
 *
 *   Center for Open Middleware
 *     http://www.centeropenmiddleware.com/
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 *   Copyright (C) 2015-2016 Center for Open Middleware.
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
package org.smartdeveloperhub.harvester.org.frontend.core.Organization;

public interface OrganizationVocabulary {

	static final String TYPE       = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
	static final String ORGANIZATION_CLASS = "http://www.smartdeveloperhub.org/vocabulary/organization#Organization";
	static final String ORGANIZATIONALCOLLABORATION = "http://www.smartdeveloperhub.org/vocabulary/organization#OrganizationalCollaboration";
	static final String PREFLABEL = "http://www.w3.org/2004/02/skos/core#prefLabel";
	static final String ORGID = "http://www.smartdeveloperhub.org/vocabulary/organization#id";
	static final String TITLE = "http://www.smartdeveloperhub.org/vocabulary/organization#title";
	static final String DESCRIPTION = "http://www.smartdeveloperhub.org/vocabulary/organization#description";
	static final String CLASSIFICATION = "http://www.w3.org/ns/org#classification";
	static final String PURPOSE = "http://www.w3.org/ns/org#purpose";
	static final String HASMEMBERORGANIZATION="http://www.smartdeveloperhub.org/vocabulary/organization#hasMemberOrganization";
	static final String HASPROJECT="http://www.smartdeveloperhub.org/vocabulary/organization#hasProject";
	static final String HASPRODUCT="http://www.smartdeveloperhub.org/vocabulary/organization#hasProduct";
	static final String HASMEMBER ="http://www.smartdeveloperhub.org/vocabulary/organization#hasMember";
	static final String MEMBERSHIP = "http://www.smartdeveloperhub.org/vocabulary/organization#membership";
	
	static final String SKOSCONCEPT = "http://www.w3.org/2004/02/skos/core#Concept";
	static final String LABEL = "http://www.w3.org/2000/01/rdf-schema#label";
	
	static final String PROJECTID = "http://www.smartdeveloperhub.org/vocabulary/organization#id";
	static final String PERSONID = "http://www.smartdeveloperhub.org/vocabulary/organization#id";
	
	static final String ORGPOSITION = "http://www.smartdeveloperhub.org/vocabulary/organization#organizationPosition";
	static final String ORGROLE = "http://www.smartdeveloperhub.org/vocabulary/organization#organizationRole";	
	
	static final String DEPICTION = "http://xmlns.com/foaf/0.1/depiction";
	static final String IMAGE_CLASS = "http://xmlns.com/foaf/0.1/Image";
	static final String DEPICTS = "http://xmlns.com/foaf/0.1/depicts";
	
}
