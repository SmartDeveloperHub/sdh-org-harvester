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
package org.smartdeveloperhub.harvester.org.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartdeveloperhub.harvester.org.backend.pojo.Position;
import org.smartdeveloperhub.harvester.org.backend.pojo.Role;
import org.smartdeveloperhub.harvester.org.frontend.core.affiliation.AffiliationVocabulary;
import org.smartdeveloperhub.harvester.org.frontend.core.membership.MembershipVocabulary;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class PositionPublisher extends OntologyInstanceReader implements MembershipVocabulary {

	private static final Logger LOGGER=LoggerFactory.getLogger(PositionPublisher.class);
	
	public PositionPublisher(OntModel ontModel) {
		super(ontModel);
		// TODO Auto-generated constructor stub
	}

	public Position getPosition(String positionId) {
		
		long startTime = System.currentTimeMillis();
		Position position = new Position();		
		//ResIterator iter = ontModel.listSubjectsWithProperty(ontModel.getProperty(TYPE),membershipId);
		Resource positionResource  = ontModel.getResource(positionId);
	 		     
    	if (positionResource!=null){    		  		
			position.setUri(positionResource.getURI());
			
			Statement label=positionResource.getProperty(ontModel.getProperty(POSITIONLABEL));
			if (label!=null)
				position.setLabel(label.getString());
			
			Statement positionTypeStmt=positionResource.getProperty(ontModel.getProperty(POSITIONTYPE));
			if (positionTypeStmt!=null){
				Resource positionTypeResource=positionTypeStmt.getObject().asResource();
				if (positionTypeResource!=null){
					
					position.setPositionTypeUri(positionTypeResource.getURI());
					
					Statement positionTypeLabel=positionTypeResource.getProperty(ontModel.getProperty(POSITIONTYPELABEL));
		    		if (positionTypeLabel!=null)
		    			position.setPositionTypeLabel(positionTypeLabel.getString());
				}
			}
    	}	    		
    	 long stopTime = System.currentTimeMillis();
 		long elapsedTime = stopTime - startTime;
 		LOGGER.debug("- Load the Position, elapsed time (ms)..: {}",elapsedTime);
    	return position;
	}
 


}
