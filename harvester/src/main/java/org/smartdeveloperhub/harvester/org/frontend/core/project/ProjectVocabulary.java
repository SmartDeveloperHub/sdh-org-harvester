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
package org.smartdeveloperhub.harvester.org.frontend.core.project;

public interface ProjectVocabulary {
	
	static final String TYPE       = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
	static final String PROJECT_CLASS = "http://www.smartdeveloperhub.org/vocabulary/org#Project";
	static final String ORGANIZATION_CLASS = "http://www.smartdeveloperhub.org/vocabulary/org#Organization";
	static final String PROJECTID = "http://www.smartdeveloperhub.org/vocabulary/org#id";
	
	static final String DOAPNAME = "http://usefulinc.com/ns/doap#name";
	static final String DOAPDESCRIPTION = "http://usefulinc.com/ns/doap#description";
	static final String DOAPREPOSITORY = "http://usefulinc.com/ns/doap#repository";
	
	static final String AFFILIATION = "http://www.smartdeveloperhub.org/vocabulary/org#affiliation";
	
}
