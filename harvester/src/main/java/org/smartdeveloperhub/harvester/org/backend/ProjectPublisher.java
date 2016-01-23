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
package org.smartdeveloperhub.harvester.org.backend;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartdeveloperhub.harvester.org.backend.pojo.Organization;
import org.smartdeveloperhub.harvester.org.backend.pojo.Project;
import org.smartdeveloperhub.harvester.org.frontend.core.Organization.OrganizationVocabulary;
import org.smartdeveloperhub.harvester.org.frontend.core.project.ProjectVocabulary;
import org.smartdeveloperhub.harvester.org.backend.OntologyInstanceReader;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;

public class ProjectPublisher extends OntologyInstanceReader implements ProjectVocabulary{

	private static final Logger LOGGER=LoggerFactory.getLogger(ProjectPublisher.class);
	
	public ProjectPublisher(OntModel ontModel) {
		super(ontModel);
		// TODO Auto-generated constructor stub
	}

	public Project getProject(String projectURI) {
		long startTime = System.currentTimeMillis();
		Project project = new Project();		
//		ResIterator iter = ontModel.listSubjectsWithProperty(ontModel.getProperty(PROJECTID),projectId);
//		while (iter.hasNext()) {
//		    Resource r = iter.nextResource();
		Resource r  = ontModel.getResource(projectURI);
		if(r!=null){
			project.setUri(projectURI);
		    
			Statement projectId = r.getProperty(ontModel.getProperty(PROJECTID));
		    if (projectId !=null)
		    project.setId(projectId.getString());
		    
		    Statement name = r.getProperty(ontModel.getProperty(DOAPNAME));
		    if (name !=null)
		    	project.setName(name.getString());
		    
		    Statement description = r.getProperty(ontModel.getProperty(DOAPDESCRIPTION));
		    if (description!=null)
		    	project.setDescription(description.getString());
		    
		    Statement createdOn = r.getProperty(ontModel.getProperty(CREATEDON)) ;
		    if (createdOn!=null)
		    	project.setCreatedOn(createdOn.getString());
		    
		    //depiction
		    Statement depiction = r.getProperty(ontModel.getProperty(DEPICTION)) ;
		    if (depiction!=null){
		    	Statement depicts = depiction.getProperty(ontModel.getProperty(DEPICTS));
		    	if (depicts!=null){
		    		Resource imgRes=depicts.getResource();
		    		if (imgRes!=null)
		    			project.setDepicts(depicts.getResource().getURI());
		    	}
		    }
		    
		    
//			//projectRole
//		    StmtIterator projectRoleIter = r.listProperties(ontModel.getProperty(PROJECTROLE));
//		    ArrayList<String> role = new ArrayList<String>();
//		    while (projectRoleIter.hasNext()) {
//			    Statement stmtProjectRole = projectRoleIter.next();
//			    Resource roleRes= stmtProjectRole.getResource();
//			    if (roleRes!=null){
//			    	role.add(roleRes.getURI());
//			    }
//		    }
//		   project.setRole(role);
		    
		    //membership
		    StmtIterator affiliationIter = r.listProperties(ontModel.getProperty(AFFILIATION));
		    ArrayList<String> affiliation = new ArrayList<String>();
		    while (affiliationIter.hasNext()) {
			    Statement stmtAffiliation = affiliationIter.next();
			    Resource affiliationRes= stmtAffiliation.getResource();
			    if (affiliationRes!=null){
			    	affiliation.add(affiliationRes.getURI());
			    }
		    }
		    project.setAffiliation(affiliation);		    
		    
		    //doap:repository
		    StmtIterator repoIter = r.listProperties(ontModel.getProperty(DOAPREPOSITORY));
		    ArrayList<String> repositories = new ArrayList<String>();
		    while (repoIter.hasNext()) {
			    Statement stmtRepo = repoIter.next();
			    Resource repoRes= stmtRepo.getResource();
			    if (repoRes!=null){
			    	repositories.add(repoRes.getURI());
			    }
		    }
		    project.setRepository(repositories);		
		    
		  //scm:location
		    StmtIterator locationIter = r.listProperties(ontModel.getProperty(SCMLOCATION));
		    ArrayList<String> locations = new ArrayList<String>();
		    while (locationIter.hasNext()) {
			    Statement stmtLocation = locationIter.next();
			    String location= stmtLocation.getString();
			    if (location!=null){
			    	locations.add(location);
			    }
		    }
		    project.setLocation(locations);
		    
		    long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			LOGGER.debug("- Load the project, elapsed time (ms)..: {}",elapsedTime);
		}
		return project;
		
	}


}

