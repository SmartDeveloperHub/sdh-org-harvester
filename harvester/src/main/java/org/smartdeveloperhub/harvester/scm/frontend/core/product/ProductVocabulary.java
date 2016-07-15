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
package org.smartdeveloperhub.harvester.scm.frontend.core.product;

public interface ProductVocabulary {
	
	static final String TYPE       = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
	static final String PRODUCT_CLASS = "http://www.smartdeveloperhub.org/vocabulary/organization#Product";	
	static final String PREFLABEL = "http://www.w3.org/2004/02/skos/core#prefLabel";
	static final String PRODUCTID = "http://www.smartdeveloperhub.org/vocabulary/organization#id";	
	static final String DESCRIPTION = "http://www.smartdeveloperhub.org/vocabulary/organization#description";	
	static final String CREATEDON= "http://www.smartdeveloperhub.org/vocabulary/organization#createdOn";	
	static final String RELATESTOPROJECT="http://www.smartdeveloperhub.org/vocabulary/organization#relatesToProject";
	
	static final String DEPICTION = "http://xmlns.com/foaf/0.1/depiction";
	static final String IMAGE_CLASS = "http://xmlns.com/foaf/0.1/Image";
	static final String DEPICTS = "http://xmlns.com/foaf/0.1/depicts";
	
	

}
