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
package org.smartdeveloperhub.harvester.org.frontend.core;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartdeveloperhub.harvester.org.backend.OrganizationPublisher;
import org.smartdeveloperhub.harvester.org.backend.PersonPublisher;
import org.smartdeveloperhub.harvester.org.backend.ProjectPublisher;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class BackendController {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(BackendController.class);
		
	OntModel ontModel;
	OrganizationPublisher orgPub;
	ProjectPublisher projPub;
	PersonPublisher perPub;
	
	
	public BackendController(){
		loadOntologyIndividuals();
		orgPub = new OrganizationPublisher(ontModel);
		projPub = new ProjectPublisher(ontModel);
		perPub = new PersonPublisher(ontModel);
	}
	
	public void loadOntologyIndividuals(){
						
		long startTime = System.currentTimeMillis();
		
		String inputFileName = "organization-individuals.ttl";
		InputStream in=
			Thread.
				currentThread().
					getContextClassLoader().
						getResourceAsStream(inputFileName);		
			
	    
//		InputStream in = FileManager.get().open(inputFileName);
		
		if (in == null) {
		    throw new IllegalArgumentException(
		                                 "File: " + inputFileName + " not found");
		}
		
				
		//this option worked!
		ontModel= ModelFactory.createOntologyModel();
		//OntDocumentManager docMgr = ontModel.getDocumentManager();
		
		// read the RDF/XML file
		ontModel.read(in, null, "TTL" );
		// write it to standard out
		//ontModel.writeAll(System.out, "TTL");
		//ontModel.write(System.out, "TTL");			
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;

		LOGGER.info("- Load Ontology, elapsed time (ms)..: {}",elapsedTime);
	}
	
	public OrganizationPublisher getOrganizationPublisher() {
		// TODO Auto-generated method stub
		return orgPub;
	}


	public ProjectPublisher getProjectPublisher() {
		// TODO Auto-generated method stub
		return projPub;
	}

	public PersonPublisher getPersonPublisher() {
		// TODO Auto-generated method stub
		return perPub;
	}

}
