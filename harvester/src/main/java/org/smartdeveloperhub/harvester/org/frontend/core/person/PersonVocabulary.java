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
 *   Artifact    : org.smartdeveloperhub.harvester.org:org-harvester-frontend:0.1.0
 *   Bundle      : org-harvester.war
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 */
package org.smartdeveloperhub.harvester.org.frontend.core.person;

public interface PersonVocabulary {
	
	static final String TYPE       = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
	static final String PERSONCLASS = "http://www.smartdeveloperhub.org/vocabulary/organization#Person";
	static final String PERSONID = "http://www.smartdeveloperhub.org/vocabulary/organization#id";

	static final String FOAFNAME = "http://xmlns.com/foaf/0.1/name";
	static final String FOAFNICK ="http://xmlns.com/foaf/0.1/nick";
	static final String FOAFMBOX = "http://xmlns.com/foaf/0.1/mbox";
	static final String FOAFIMG = "http://xmlns.com/foaf/0.1/img";
	static final String FOAFIMAGE = "http://xmlns.com/foaf/0.1/Image";
	static final String FOAFDEPICTS = "http://xmlns.com/foaf/0.1/depicts";
	static final String FOAFHOMEPAGE = "http://xmlns.com/foafhomepage";
	static final String FOAFDOCUMENT = "http://xmlns.com/Document";
	
	
	
	static final String MEMBEROF = "http://www.smartdeveloperhub.org/vocabulary/organization#memberOf";
	static final String ORGID = "http://www.smartdeveloperhub.org/vocabulary/organization#id";
	static final String HASMEMBERSHIP = "http://www.smartdeveloperhub.org/vocabulary/organization#hasMembership";
	static final String MEMBERSHIPID = "http://www.smartdeveloperhub.org/vocabulary/organization#id";
	static final String ISAFFILIATED = "http://www.smartdeveloperhub.org/vocabulary/organization#isAffiliated";
	static final String AFFILIATIONID = "http://www.smartdeveloperhub.org/vocabulary/organization#id";
	    
}
