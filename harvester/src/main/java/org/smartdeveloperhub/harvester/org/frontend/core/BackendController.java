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
package org.smartdeveloperhub.harvester.org.frontend.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartdeveloperhub.harvester.org.backend.AffiliationPublisher;
import org.smartdeveloperhub.harvester.org.backend.MembershipPublisher;
import org.smartdeveloperhub.harvester.org.backend.OrganizationPublisher;
import org.smartdeveloperhub.harvester.org.backend.PersonPublisher;
import org.smartdeveloperhub.harvester.org.backend.PositionPublisher;
import org.smartdeveloperhub.harvester.org.backend.ProjectPublisher;
import org.smartdeveloperhub.harvester.org.backend.RolePublisher;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

public class BackendController {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(BackendController.class);
		
	OntModel ontModel;
	OrganizationPublisher orgPub;
	ProjectPublisher projPub;
	PersonPublisher perPub;
	MembershipPublisher memPub;
	AffiliationPublisher affPub;
	PositionPublisher posPub;
	RolePublisher rolePub;
	
	public BackendController() throws FileNotFoundException{
		loadOntologyIndividuals();
		orgPub = new OrganizationPublisher(ontModel);
		projPub = new ProjectPublisher(ontModel);
		perPub = new PersonPublisher(ontModel);
		memPub = new MembershipPublisher(ontModel);
		affPub =new AffiliationPublisher(ontModel);
		posPub = new PositionPublisher(ontModel);
		rolePub = new RolePublisher(ontModel);
	}
	
	public void loadOntologyIndividuals() throws FileNotFoundException{
						
		long startTime = System.currentTimeMillis();
		InputStream in;
		
		 String inputFileName = System.getenv("ORG_INDIVIDUALS");
		 if (inputFileName==null){
			 inputFileName="organization-individuals.ttl";
			  LOGGER.info("ORG_INDIVIDUALS by default {}",inputFileName);		
			  in= Thread.currentThread().getContextClassLoader().getResourceAsStream(inputFileName);
		  }
		  else{		 			 
			  LOGGER.info("ORG_INDIVIDUALS environment variable {}",inputFileName);	
			  File file = new  File(inputFileName);
			  in = new FileInputStream(file);
		  }
		 
		
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

	public MembershipPublisher getMembershipPublisher() {
		// TODO Auto-generated method stub
		return memPub;
	}
	
	public AffiliationPublisher getAffiliationPublisher() {
		// TODO Auto-generated method stub
		return affPub;
	}

	public RolePublisher getRolePublisher() {		
		return rolePub;		
	}
	
	public PositionPublisher getPositionPublisher() {
		return posPub;		
	}

}
