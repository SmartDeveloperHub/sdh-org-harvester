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

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartdeveloperhub.harvester.org.backend.pojo.Organization;
import org.smartdeveloperhub.harvester.org.backend.OntologyInstanceReader;
import org.smartdeveloperhub.harvester.org.frontend.core.HarvesterApplication;
import org.smartdeveloperhub.harvester.org.frontend.core.Organization.OrganizationVocabulary;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.rdf.model.Statement;

public class OrganizationPublisher extends OntologyInstanceReader implements OrganizationVocabulary{

	private static final Logger LOGGER=LoggerFactory.getLogger(OrganizationPublisher.class);
	
	public OrganizationPublisher(OntModel ontModel) {
		super(ontModel);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<String> getOrganizations() {
		long startTime = System.currentTimeMillis();
		
		ArrayList<String> organizations=new ArrayList<String>();
		
		Resource organization=ontModel.getResource(ORGANIZATION_CLASS);		                                            
		
		ResIterator iter =ontModel.listSubjectsWithProperty(RDF.type, organization);
		
		if (iter.hasNext()) {
		    System.out.println("The database contains these organizations:");
		    while (iter.hasNext()) {
		    	Resource organizationResource = iter.nextResource();
		    	String organizationUri= organizationResource.getURI();		        
		        LOGGER.info(organizationUri);
//		        String organizationId = organizationResource.getProperty(ontModel.getProperty(ORGID)).getString();
//		        System.out.println("  " + organizationId);
		        organizations.add(organizationUri);
		        //System.out.println("   -" + iter.nextResource().getProperty(ontModel.getProperty("http://www.smartdeveloperhub.org/vocabulary/organization#id")).getString());
		        //System.out.println("   -" + iter.nextResource().getProperty(ontModel.getProperty("http://www.w3.org/ns/org#classification")).getResource().getURI());		        
		    }
		} else {
			LOGGER.info("No organizations were found in the database");
		}
		
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		//System.out.println("Load organizations, elapsed time (ms):"+elapsedTime);
		LOGGER.info("- Load organizations, elapsed time (ms)..: {}",elapsedTime);
		
		return organizations;
	}
	

	public Organization getOrganization(String organizationUri) {
		long startTime = System.currentTimeMillis();
		Organization org = new Organization();		
		//ResIterator iter = ontModel.listSubjectsWithProperty(ontModel.getProperty(ORGID),organizationId);
		
		Resource r  = ontModel.getResource(organizationUri);
		if(r!=null){
		    //Resource r = iter.nextResource();
		    
		    org.setUri(organizationUri);
		    
		    Statement id = r.getProperty(ontModel.getProperty(ORGID));
		    if (id !=null)
		    	org.setId(id.getString());
		    
		    
		    Statement title = r.getProperty(ontModel.getProperty(TITLE));
		    if (title !=null)
		    	org.setTitle(title.getString());
		    
		    Statement preflabel = r.getProperty(ontModel.getProperty(PREFLABEL));
		    if (preflabel!=null)
		    	org.setPrefLabel(preflabel.getString());	    
		    
		    Statement purpose = r.getProperty(ontModel.getProperty(PURPOSE));
		    if (purpose!=null)
		    	org.setPurpose(purpose.getString());
		   
		    Statement description = r.getProperty(ontModel.getProperty(DESCRIPTION)) ;
		    if (description!=null)
		    	org.setDescription(description.getString());
		    
		    Statement classification = r.getProperty(ontModel.getProperty(CLASSIFICATION));
		    if (classification != null ){
		    	Resource classificationObj=classification.getObject().asResource();
		    	Statement preflabelClassification= classificationObj.getProperty(ontModel.getProperty(PREFLABEL));
		    	org.setClassification(preflabelClassification.getString());		    			    			    	
		    }
		    
		    //memberOrganizations
		    StmtIterator memberOrgIter = r.listProperties(ontModel.getProperty(HASMEMBERORGANIZATION));
		    ArrayList<String> hasMemberOrganization = new ArrayList<String>();
		    while (memberOrgIter.hasNext()) {
			    Statement stmtMembOrg = memberOrgIter.next();
			    Resource memberOrgRes= stmtMembOrg.getResource();
			    if (memberOrgRes!=null){
//			    	Statement memberOrgStmt = memberOrgRes.getProperty(ontModel.getProperty(ORGID));
//			    	if (memberOrgStmt!=null)
//			    	    hasMemberOrganization.add(memberOrgStmt.getString());
			    	hasMemberOrganization.add(memberOrgRes.getURI());
			    }
		    }
		    org.setHasMemberOrganization(hasMemberOrganization);
		    		    
		  //hasProject
		    StmtIterator projectOrgIter = r.listProperties(ontModel.getProperty(HASPROJECT));
		    ArrayList<String> hasProject = new ArrayList<String>();
		    while (projectOrgIter.hasNext()) {
			    Statement stmtProjOrg = projectOrgIter.next();
			    Resource projRes= stmtProjOrg.getResource();
			    if (projRes!=null){
//			    	Statement stmtProjectId = projRes.getProperty(ontModel.getProperty(PROJECTID));
//			    	if (stmtProjectId!=null)
//			    		hasProject.add(stmtProjectId.getString());
			    	hasProject.add(projRes.getURI());
			    }
		    }
		    org.setHasProject(hasProject);
		    
		  //hasMember
		    StmtIterator hasMemberIter = r.listProperties(ontModel.getProperty(HASMEMBER));
		    ArrayList<String> hasMember = new ArrayList<String>();
		    while (hasMemberIter.hasNext()) {
			    Statement stmtHasMember = hasMemberIter.next();
			    Resource personRes= stmtHasMember.getResource();
			    if (personRes!=null){
//			    	Statement personIdStmt = personRes.getProperty(ontModel.getProperty(PERSONID));
//			    	if (personIdStmt!=null)
//			    	    hasMember.add(personIdStmt.getString());
			    	hasMember.add(personRes.getURI());
			    }
		    }
		    org.setHasMember(hasMember);
		    
			  //organizationPosition
		    StmtIterator orgPositionIter = r.listProperties(ontModel.getProperty(ORGPOSITION));
		    ArrayList<String> position = new ArrayList<String>();
		    while (orgPositionIter.hasNext()) {
			    Statement stmtOrgPosition = orgPositionIter.next();
			    Resource positionRes= stmtOrgPosition.getResource();
			    if (positionRes!=null){
			    	position.add(positionRes.getURI());
			    }
		    }
		    org.setPosition(position);
		    
		    //organizationRole
		    StmtIterator orgRoleIter = r.listProperties(ontModel.getProperty(ORGROLE));
		    ArrayList<String> role = new ArrayList<String>();
		    while (orgRoleIter.hasNext()) {
			    Statement stmtOrgRole = orgRoleIter.next();
			    Resource roleRes= stmtOrgRole.getResource();
			    if (roleRes!=null){
			    	role.add(roleRes.getURI());
			    }
		    }
		    org.setRole(role);
		    
		    //membership
		    StmtIterator membershipIter = r.listProperties(ontModel.getProperty(MEMBERSHIP));
		    ArrayList<String> membership = new ArrayList<String>();
		    while (membershipIter.hasNext()) {
			    Statement stmtMembership = membershipIter.next();
			    Resource membershipRes= stmtMembership.getResource();
			    if (membershipRes!=null){
			    	membership.add(membershipRes.getURI());
			    }
		    }
		    org.setMembership(membership);
		    
		    long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			LOGGER.info("- Load the organization, elapsed time (ms)..: {}",elapsedTime);
		}
		return org;
		
	}
}
