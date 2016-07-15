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
 *   Artifact    : org.smartdeveloperhub.harvester.org:org-harvester-frontend:0.2.0
 *   Bundle      : org-harvester.war
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 */
package org.smartdeveloperhub.harvester.org.frontend.core.affiliation;

public interface AffiliationVocabulary {
	
	static final String TYPE       = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
	static final String AFFILIATION_CLASS = "http://www.smartdeveloperhub.org/vocabulary/organization#Affiliation";
	static final String AFFILIATIONWITH = "http://www.smartdeveloperhub.org/vocabulary/organization#affiliationWith";
	static final String AFFILIATE = "http://www.smartdeveloperhub.org/vocabulary/organization#affiliate";
	
	static final String ROLE = "http://www.smartdeveloperhub.org/vocabulary/organization#role";
	static final String ROLELABEL="http://www.w3.org/2000/01/rdf-schema#label";	
	static final String ROLETYPE = "http://www.smartdeveloperhub.org/vocabulary/organization#roleType";	
	static final String ROLETYPEClASS = "http://www.w3.org/2004/02/skos/core#Concept";
	static final String ROLETYPELABEL="http://www.w3.org/2004/02/skos/core#prefLabel";
	static final String ROLECLASS = "http://www.smartdeveloperhub.org/vocabulary/organization#Role";
		
	static final String PROJECTID = "http://www.smartdeveloperhub.org/vocabulary/organization#id";
	static final String PERSONID = "http://www.smartdeveloperhub.org/vocabulary/organization#id";
	
}
