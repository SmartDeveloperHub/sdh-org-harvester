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
import org.smartdeveloperhub.harvester.org.frontend.core.Organization.OrganizationContainerHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.Organization.OrganizationHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.harvester.HarvesterHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.person.PersonContainerHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.project.ProjectContainerHandler;
//import org.smartdeveloperhub.harvesters.scm.backend.pojos.Repository;


public class BackendResourcePublisher {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(HarvesterApplication.class);
	
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
				
	}
	
	private void addOrganizationMembersToHarvester(URI target, ContainerSnapshot organizationContainerSnapshot) throws Exception{
		
		OrganizationPublisher organizationPublisher = controller.getOrganizationPublisher();	
		for (String organizationId:organizationPublisher.getOrganizations()){
			
			Name<String> organizationName = NamingScheme.getDefault().name(organizationId);	
			
			ResourceSnapshot organizationSnapshot = organizationContainerSnapshot.addMember(organizationName);
			
			//retrieve organization informationj			
		    Organization org = organizationPublisher.getOrganization(organizationId);
			
			//Project container for each organization
			ContainerSnapshot projectContainerSnapshot = organizationSnapshot.createAttachedResource( ContainerSnapshot.class, OrganizationHandler.ORGANIZATION_PROJECTS,
					organizationName, ProjectContainerHandler.class);
			
			addProjectsToOrganization(projectContainerSnapshot, org);
			

			//Member container for each organization
			ContainerSnapshot memberContainerSnapshot = organizationSnapshot.createAttachedResource( ContainerSnapshot.class, OrganizationHandler.ORGANIZATION_MEMBERS,
					organizationName, PersonContainerHandler.class);			
			
			addMembersToOrganization(memberContainerSnapshot, org);
			
//			ContainerSnapshot branchContainerSnapshot = repositorySnapshot.createAttachedResource( ContainerSnapshot.class, RepositoryHandler.REPOSITORY_BRANCHES,
//					repositoryName, BranchContainerHandler.class);
//			
//			Repository repo=controller.getRepository(Integer.toString(repositoryId));
//			
//			addBranchMemberstToRepository(repo, branchContainerSnapshot);
//			
//			ContainerSnapshot commitContainerSnapshot = repositorySnapshot.createAttachedResource( ContainerSnapshot.class, RepositoryHandler.REPOSITORY_COMMITS,
//					repositoryName, CommitContainerHandler.class);
//			
//			addCommitMembersToRepository(repo, commitContainerSnapshot);
			
			LOGGER.debug("Published resource for repository {} @ {} ({})",organizationId, organizationContainerSnapshot.name(),organizationContainerSnapshot.templateId());
		}
	}

	private void addMembersToOrganization(
			ContainerSnapshot memberContainerSnapshot, Organization org) {		
	    
	    for (String memberId:org.getHasMember()){
	    	Name<String> personName = NamingScheme.getDefault().name(memberId);	
			
			ResourceSnapshot personSnapshot = memberContainerSnapshot.addMember(personName);
	    }
		
	}

	private void addProjectsToOrganization(ContainerSnapshot projectContainerSnapshot, Organization org) {	
	    
	    for (String projectId:org.getHasProject()){
	    	Name<String> projectName = NamingScheme.getDefault().name(projectId);	
			
			ResourceSnapshot projectSnapshot = projectContainerSnapshot.addMember(projectName);
	    }
	    
		
	}
	
//	private void addBranchMemberstToRepository(Repository repository, ContainerSnapshot branchContainerSnapshot) throws Exception{		
//		for (String branchId:repository.getBranches().getBranchIds()){
//			Name<String> branchName = NamingScheme.getDefault().name(Integer.toString(repository.getId()),branchId);			
//			//keeptrack of the branch key and resource name
//			controller.getBranchIdentityMap().addKey(new BranchKey(Integer.toString(repository.getId()),branchId), branchName);
//			ResourceSnapshot branchSnapshot = branchContainerSnapshot.addMember(branchName);			
//		}		
//	}
//	
//	private void addCommitMembersToRepository(Repository repository,
//			ContainerSnapshot commitContainerSnapshot) throws Exception {
//		for (String commitId:repository.getCommits().getCommitIds()){
//			Name<String> commitName = NamingScheme.getDefault().name(Integer.toString(repository.getId()),commitId);			
//			//keeptrack of the branch key and resource name
//			controller.getCommitIdentityMap().addKey(new CommitKey(Integer.toString(repository.getId()),commitId), commitName);
//			ResourceSnapshot commitSnapshot = commitContainerSnapshot.addMember(commitName);			
//		}
//		
//	}
//	
//	void publishUserResources() throws Exception{
//		Name<String> userContainerName = NamingScheme.getDefault().name(UserContainerHandler.NAME);
//		ContainerSnapshot userContainerSnapshot = session.find(ContainerSnapshot.class, userContainerName ,UserContainerHandler.class);			
//		if(userContainerSnapshot==null) {
//			LOGGER.warn("User Container does not exits");
//			return;
//		}
//		
//		ArrayList<String> userIds = controller.getUsers();	
//		for (String userId:userIds){			
//			Name<String> userName = NamingScheme.getDefault().name(userId);			
//			ResourceSnapshot userSnapshot = userContainerSnapshot.addMember(userName);
//			LOGGER.debug("Published resource for user {} @ {} ({})",userId, userSnapshot.name(),userSnapshot.templateId());
//		}
//		
//	}

}
