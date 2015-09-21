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

import org.smartdeveloperhub.harvester.org.backend.pojo.Organization;
import org.smartdeveloperhub.harvester.org.frontend.core.Organization.OrganizationVocabulary;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

public class OrganizationPublisher extends OntologyInstanceReader implements OrganizationVocabulary{

	public OrganizationPublisher(OntModel ontModel) {
		super(ontModel);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<String> getOrganizations() {
	    
		ArrayList<String> organizations=new ArrayList<String>();
		
		Resource organization=ontModel.getResource(ORGANIZATION_CLASS);		                                            
		
		ResIterator iter =ontModel.listSubjectsWithProperty(RDF.type, organization);
		
		if (iter.hasNext()) {
		    System.out.println("The database contains organizations:");
		    while (iter.hasNext()) {
		    	Resource organizationResource = iter.nextResource();
		    	String organizationUri= organizationResource.getURI();		        
		        System.out.println("  " + organizationUri);
		        String organizationId = organizationResource.getProperty(ontModel.getProperty(ID)).getString();
		        System.out.println("  " + organizationId);
		        organizations.add(organizationId);
		        //System.out.println("   -" + iter.nextResource().getProperty(ontModel.getProperty("http://www.smartdeveloperhub.org/vocabulary/organization#id")).getString());
		        //System.out.println("   -" + iter.nextResource().getProperty(ontModel.getProperty("http://www.w3.org/ns/org#classification")).getResource().getURI());		        
		    }
		} else {
		    System.out.println("No organizations were found in the database");
		}
		
		return organizations;
	}
	

	public Organization getOrganization(String organizationId) {
		Organization org = new Organization();		
		ResIterator iter = ontModel.listSubjectsWithProperty(ontModel.getProperty(ID),organizationId);
		while (iter.hasNext()) {
		    Resource r = iter.nextResource();
		    org.setId(organizationId);
		    org.setTitle(r.getProperty(ontModel.getProperty(TITLE)).getString());
		    org.setPrefLabel(r.getProperty(ontModel.getProperty(PREFLABEL)).getString());
		    org.setPurpose(r.getProperty(ontModel.getProperty(PURPOSE)).getString());
		    org.setDescription(r.getProperty(ontModel.getProperty(DESCRIPTION)).getString());
		    
		    if (r.getProperty(ontModel.getProperty(CLASSIFICATION))!=null){
		    	Resource classificationObj=r.getProperty(ontModel.getProperty(CLASSIFICATION)).getObject().asResource();
		    	org.setClassification(classificationObj.getProperty(ontModel.getProperty(PREFLABEL)).getString());		    			    			    	
		    }
		    
		}
		return org;
		
	}
}
