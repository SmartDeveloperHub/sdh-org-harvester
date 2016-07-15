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
package org.smartdeveloperhub.harvester.org.frontend.core;

import java.net.URI;
import java.util.ArrayList;

import org.ldp4j.application.data.Name;
import org.ldp4j.application.data.NamingScheme;
import org.ldp4j.application.session.ContainerSnapshot;
import org.ldp4j.application.session.ResourceSnapshot;
import org.ldp4j.application.session.WriteSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartdeveloperhub.harvester.org.backend.OrganizationPublisher;
import org.smartdeveloperhub.harvester.org.backend.pojo.Organization;
import org.smartdeveloperhub.harvester.org.backend.pojo.Product;
import org.smartdeveloperhub.harvester.org.backend.pojo.Project;
import org.smartdeveloperhub.harvester.org.frontend.core.Organization.OrganizationContainerHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.Organization.OrganizationHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.affiliation.AffiliationContainerHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.harvester.HarvesterHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.membership.MembershipContainerHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.person.PersonContainerHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.position.PositionContainerHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.project.ProjectContainerHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.project.ProjectHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.role.RoleContainerHandler;
import org.smartdeveloperhub.harvester.scm.frontend.core.product.ProductContainerHandler;
import org.smartdeveloperhub.harvester.scm.frontend.core.product.ProductHandler;
//import org.smartdeveloperhub.harvesters.scm.backend.pojos.Repository;


public class BackendResourcePublisher {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(BackendResourcePublisher.class);
	
	WriteSession session;
	BackendController controller;
	
	public BackendResourcePublisher(WriteSession session, BackendController controller) {
		this.controller=controller;
		this.session=session;
	}
	
	void publishHarvesterResources(URI target) throws Exception{
		LOGGER.info("Publishing ORG Harvester Resource...");
		
		Name<URI> harvesterName = NamingScheme.getDefault().name(target);
		ResourceSnapshot harvesterSnapshot=session.find(ResourceSnapshot.class, harvesterName, HarvesterHandler.class);
		
		ContainerSnapshot organizationContainerSnapshot = harvesterSnapshot.createAttachedResource( ContainerSnapshot.class, HarvesterHandler.HARVESTER_ORGANIZATIONS,
												  harvesterName, OrganizationContainerHandler.class);
		LOGGER.debug("Published organization container for service {}", harvesterName);
		
		addOrganizationMembersToHarvester(target, organizationContainerSnapshot);
		
		LOGGER.info("**End of Publishing ORG Harvester Resource");
				
	}
	
	private void addOrganizationMembersToHarvester(URI target, ContainerSnapshot organizationContainerSnapshot) throws Exception{
		
		OrganizationPublisher organizationPublisher = controller.getOrganizationPublisher();	
		for (String organizationURI:organizationPublisher.getOrganizations()){
			
			Name<String> organizationName = NamingScheme.getDefault().name(organizationURI);	
			
			ResourceSnapshot organizationSnapshot = organizationContainerSnapshot.addMember(organizationName);
			
			//retrieve organization informationj			
		    Organization org = organizationPublisher.getOrganization(organizationURI);
			
			//Project container for each organization
			ContainerSnapshot projectContainerSnapshot = organizationSnapshot.createAttachedResource( ContainerSnapshot.class, OrganizationHandler.ORGANIZATION_PROJECTS,
					organizationName, ProjectContainerHandler.class);
			
			addProjectsToOrganization(projectContainerSnapshot, org);
			
			//Product container for each organization
			ContainerSnapshot productContainerSnapshot = organizationSnapshot.createAttachedResource( ContainerSnapshot.class, OrganizationHandler.ORGANIZATION_PRODUCTS,
					organizationName, ProductContainerHandler.class);
			
			addProductsToOrganization(productContainerSnapshot, org);
			

			//Member container for each organization
			ContainerSnapshot memberContainerSnapshot = organizationSnapshot.createAttachedResource( ContainerSnapshot.class, OrganizationHandler.ORGANIZATION_MEMBERS,
					organizationName, PersonContainerHandler.class);			
			
			addMembersToOrganization(memberContainerSnapshot, org);
			
			//Position container for each organization
			ContainerSnapshot positionContainerSnapshot = organizationSnapshot.createAttachedResource( ContainerSnapshot.class, 
					OrganizationHandler.ORGANIZATION_POSITIONS, organizationName, PositionContainerHandler.class);			
			
			addPositionsToOrganization(positionContainerSnapshot, org);
			
			//Role container for each organization
			ContainerSnapshot roleContainerSnapshot = organizationSnapshot.createAttachedResource( ContainerSnapshot.class, OrganizationHandler.ORGANIZATION_ROLES,
					organizationName, RoleContainerHandler.class);			
			
			addRolesToOrganization(roleContainerSnapshot, org);
			
			//Membership container for each organization
			ContainerSnapshot membershipContainerSnapshot = organizationSnapshot.createAttachedResource( ContainerSnapshot.class,
					OrganizationHandler.ORGANIZATION_MEMBERSSHIPS, organizationName, MembershipContainerHandler.class);			
			
			addMembershipsToOrganization(membershipContainerSnapshot, org);

			
			LOGGER.debug("Published resource for repository {} @ {} ({})",organizationURI, organizationContainerSnapshot.name(),organizationContainerSnapshot.templateId());
		}
	}


	private void addMembershipsToOrganization(
			ContainerSnapshot membershipContainerSnapshot, Organization org) {
		
		for (String membershipURI:org.getMembership()){
	    	Name<String> membershipName = NamingScheme.getDefault().name(membershipURI);	
			
			ResourceSnapshot membershipSnapshot = membershipContainerSnapshot.addMember(membershipName);
	    }
		
	}

	private void addRolesToOrganization(
			ContainerSnapshot roleContainerSnapshot, Organization org) {
		
		for (String roleURI:org.getRole()){
	    	Name<String> roleName = NamingScheme.getDefault().name(roleURI);	
			
			ResourceSnapshot roleSnapshot = roleContainerSnapshot.addMember(roleName);
	    }
		
	}

	private void addPositionsToOrganization(
			ContainerSnapshot positionContainerSnapshot, Organization org) {
		  
		for (String positionURI:org.getPosition()){
		    	Name<String> positionName = NamingScheme.getDefault().name(positionURI);	
				
				ResourceSnapshot positionSnapshot = positionContainerSnapshot.addMember(positionName);
		    }
		
	}

	private void addMembersToOrganization(
			ContainerSnapshot memberContainerSnapshot, Organization org) {		
	    
	    for (String memberURI:org.getHasMember()){
	    	Name<String> personName = NamingScheme.getDefault().name(memberURI);	
			
			ResourceSnapshot personSnapshot = memberContainerSnapshot.addMember(personName);
	    }
		
	}

	private void addProjectsToOrganization(ContainerSnapshot projectContainerSnapshot, Organization org) {	
	    
	    for (String projectURI:org.getHasProject()){
	    	Project project = controller.getProjectPublisher().getProject(projectURI);
	    	Name<String> projectName = NamingScheme.getDefault().name(projectURI);	    	
			
			ResourceSnapshot projectSnapshot = projectContainerSnapshot.addMember(projectName);
			
			//Affiliation container for each project
			ContainerSnapshot affiliationContainerSnapshot = projectSnapshot.createAttachedResource( ContainerSnapshot.class,
					ProjectHandler.PROJECT_AFFILIATIONS, projectName, AffiliationContainerHandler.class);			
			
			addAffiliationsToProject(affiliationContainerSnapshot, project);
	    }	    	
	}
	
	private void addProductsToOrganization(ContainerSnapshot productContainerSnapshot, Organization org) {
	    for (String productURI:org.getHasProduct()){
	    	//Product product = controller.getProductPublisher().getProduct(productURI);
	    	Name<String> productName = NamingScheme.getDefault().name(productURI);	    				
			ResourceSnapshot productSnapshot = productContainerSnapshot.addMember(productName);			
	    }			
		
	}
	
	
	private void addAffiliationsToProject(
			ContainerSnapshot affiliationContainerSnapshot, Project project) {		
	    
	    for (String affiliationURI:project.getAffiliation()){
	    	Name<String> affiliationName = NamingScheme.getDefault().name(affiliationURI);	
			
			ResourceSnapshot affiliationSnapshot = affiliationContainerSnapshot.addMember(affiliationName);
	    }
		
	}	

}
