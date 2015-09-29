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
package org.smartdeveloperhub.harvester.org.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartdeveloperhub.harvester.org.backend.pojo.Membership;
import org.smartdeveloperhub.harvester.org.backend.pojo.Person;
import org.smartdeveloperhub.harvester.org.backend.pojo.Position;
import org.smartdeveloperhub.harvester.org.frontend.core.membership.MembershipVocabulary;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class MembershipPublisher extends OntologyInstanceReader implements MembershipVocabulary{

	private static final Logger LOGGER=LoggerFactory.getLogger(MembershipPublisher.class);
	
	public MembershipPublisher(OntModel ontModel) {
		super(ontModel);
		// TODO Auto-generated constructor stub
	}

	public Membership getMembership(String membershipId) {
		
		long startTime = System.currentTimeMillis();
		Membership membership = new Membership();		
		//ResIterator iter = ontModel.listSubjectsWithProperty(ontModel.getProperty(TYPE),membershipId);
		Resource membershipResource  = ontModel.getResource(membershipId);
	    if (membershipResource!=null){
		    
		    membership.setUri(membershipId);
		    
		    Statement membershipOnStmt = membershipResource.getProperty(ontModel.getProperty(MEMBERSHIPON));
		    if (membershipOnStmt !=null){
		    	Resource membershipOnRes= membershipOnStmt.getResource();
		    	if (membershipOnRes!=null){
//		    		String orgId=membershipOnRes.getProperty(ontModel.getProperty(ORGID)).getString();
//		    		membership.setMembershipon(orgId);
		    	    membership.setMembershipon(membershipOnRes.getURI());
		    	}		    	 
		    }
		    
		    Statement memberStmt = membershipResource.getProperty(ontModel.getProperty(MEMBER));
		    
		    if (memberStmt !=null){
		    	Resource memberRes= memberStmt.getResource();
		    	if (memberRes!=null){
//		    		String personId=memberRes.getProperty(ontModel.getProperty(PERSONID)).getString();
//		    		membership.setMember(personId);
		    		membership.setMember(memberRes.getURI());
		    	}
		    }
	    		    
		    Statement positionStmt = membershipResource.getProperty(ontModel.getProperty(POSITION));
		    if (positionStmt!=null){		    	
		    	PositionPublisher posPublisher = new PositionPublisher(ontModel);
		    	Resource positionResource=positionStmt.getObject().asResource();
		    	if (positionResource!=null){
		    		Position position= posPublisher.getPosition(positionResource.getURI());		    		
		    		membership.setPosition(position);
		    	}		    	
		    }		    
		}
	    long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		LOGGER.info("- Load the membership, elapsed time (ms)..: {}",elapsedTime);
	   return membership;		
	}
}
