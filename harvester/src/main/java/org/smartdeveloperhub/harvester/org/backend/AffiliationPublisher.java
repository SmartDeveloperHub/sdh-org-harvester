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
package org.smartdeveloperhub.harvester.org.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartdeveloperhub.harvester.org.backend.pojo.Affiliation;
import org.smartdeveloperhub.harvester.org.backend.pojo.Role;
import org.smartdeveloperhub.harvester.org.frontend.core.affiliation.AffiliationVocabulary;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class AffiliationPublisher extends OntologyInstanceReader implements AffiliationVocabulary{

	private static final Logger LOGGER=LoggerFactory.getLogger(AffiliationPublisher.class);
	
	public AffiliationPublisher(OntModel ontModel) {
		super(ontModel);
		// TODO Auto-generated constructor stub
	}

	public Affiliation getAffiliation(String affiliationId) {
		
		long startTime = System.currentTimeMillis();
		Affiliation affiliation = new Affiliation();		
		//ResIterator iter = ontModel.listSubjectsWithProperty(ontModel.getProperty(TYPE),affiliationId);
		Resource affiliationResource  = ontModel.getResource(affiliationId);
	    if (affiliationResource!=null){
		    
		    affiliation.setUri(affiliationId);
		    
		    Statement affiliationWithStmt = affiliationResource.getProperty(ontModel.getProperty(AFFILIATIONWITH));
		    if (affiliationWithStmt !=null){
		    	Resource affiliationWithRes= affiliationWithStmt.getResource();
		    	if (affiliationWithRes!=null){
//		    		String projectId=affiliationWithRes.getProperty(ontModel.getProperty(PROJECTID)).getString();
//		    		affiliation.setAffiliationWith(projectId);
		    		affiliation.setAffiliationWith(affiliationWithRes.getURI());
		    	}		    	 
		    }
		    
		    Statement affiliateStmt = affiliationResource.getProperty(ontModel.getProperty(AFFILIATE));
		    if (affiliateStmt !=null){
		    	Resource affiliateRes= affiliateStmt.getResource();
		    	if (affiliateRes!=null){
//		    		String personId=affiliateRes.getProperty(ontModel.getProperty(PERSONID)).getString();
//		    		affiliation.setAffiliate(personId);
		    		affiliation.setAffiliate(affiliateRes.getURI());
		    	}		    	 
		    }
		    
		    
		    Statement roleStmt = affiliationResource.getProperty(ontModel.getProperty(ROLE));
		    if (roleStmt!=null){
		    	RolePublisher rolePublisher = new RolePublisher(ontModel);		    	
		    	Resource roleResource=roleStmt.getObject().asResource();
		    	if (roleResource!=null){
		    		Role role = rolePublisher.getRole(roleResource.getURI());
		    		affiliation.setRole(role);
		    	}
		    }		    
		}
	    long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		LOGGER.info("- Load the affiliation, elapsed time (ms)..: {}",elapsedTime);
	   return affiliation;		
	}

}
