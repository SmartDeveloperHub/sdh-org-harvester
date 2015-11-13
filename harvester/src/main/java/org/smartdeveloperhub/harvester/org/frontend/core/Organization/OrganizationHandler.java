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
package org.smartdeveloperhub.harvester.org.frontend.core.Organization;

import java.net.URI;

import org.ldp4j.application.data.DataSet;
import org.ldp4j.application.data.DataSetHelper;
import org.ldp4j.application.data.DataSetUtils;
import org.ldp4j.application.data.DataSets;
import org.ldp4j.application.data.Name;
import org.ldp4j.application.data.NamingScheme;
import org.ldp4j.application.ext.ApplicationRuntimeException;
import org.ldp4j.application.ext.ResourceHandler;
import org.ldp4j.application.ext.UnknownResourceException;
import org.ldp4j.application.ext.annotations.Attachment;
import org.ldp4j.application.ext.annotations.Resource;
import org.ldp4j.application.session.ResourceSnapshot;
import org.smartdeveloperhub.harvester.org.backend.pojo.Organization;
import org.smartdeveloperhub.harvester.org.frontend.core.BackendController;
import org.smartdeveloperhub.harvester.org.frontend.core.membership.MembershipContainerHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.membership.MembershipHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.person.PersonContainerHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.person.PersonHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.position.PositionContainerHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.position.PositionHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.project.ProjectContainerHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.project.ProjectHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.role.RoleContainerHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.role.RoleHandler;
import org.smartdeveloperhub.harvester.scm.frontend.core.product.ProductContainerHandler;
import org.smartdeveloperhub.harvester.scm.frontend.core.product.ProductHandler;


@Resource(id=OrganizationHandler.ID
	,attachments={
		@Attachment(
			id=OrganizationHandler.ORGANIZATION_PROJECTS,
			path="projects/",
			handler=ProjectContainerHandler.class
		),
		@Attachment(
			id=OrganizationHandler.ORGANIZATION_PRODUCTS,
			path="products/",
			handler=ProductContainerHandler.class
		),
		@Attachment(
			id=OrganizationHandler.ORGANIZATION_MEMBERS,
			path="members/",
			handler=PersonContainerHandler.class
		),		
		@Attachment(
			id=OrganizationHandler.ORGANIZATION_MEMBERSSHIPS,
			path="memberships/",
			handler=MembershipContainerHandler.class
		),			
		@Attachment(
			id=OrganizationHandler.ORGANIZATION_POSITIONS,
			path="positions/",
			handler=PositionContainerHandler.class
		),
		@Attachment(
				id=OrganizationHandler.ORGANIZATION_ROLES,
				path="roles/",
				handler=RoleContainerHandler.class
			)
	}
)

public class OrganizationHandler implements ResourceHandler, OrganizationVocabulary{
	
	public static final String ID="OrganizationHandler";
	public static final String ORGANIZATION_PROJECTS="ORGANIZATIONPROJECTS";
	public static final String ORGANIZATION_PRODUCTS="ORGANIZATIONPRODUCTS";
	public static final String ORGANIZATION_MEMBERS="ORGANIZATIONMEMBERS";
	public static final String ORGANIZATION_MEMBERSSHIPS="ORGANIZATIONMEMBERSSHIPS";
	public static final String ORGANIZATION_POSITIONS="ORGANIZATIONPOSITIONS";
	public static final String ORGANIZATION_ROLES="ORGANIZATIONROLES"; 
	
	private static final URI IMG_PATH = URI.create("#img");
	
	BackendController backendController;
	
	private static final URI CLASSIFICATION_PATH = URI.create("#classification");
	
	public OrganizationHandler(BackendController backendController){
		this.backendController=backendController;
	}

	@Override
	public DataSet get(ResourceSnapshot resource)
			throws UnknownResourceException, ApplicationRuntimeException {
		
		Name<String> name = (Name<String>)resource.name();						
		try{
			Organization organization = backendController.getOrganizationPublisher().getOrganization(name.id().toString());		
			return maptoDataSet(organization,name);	
		}
		catch(Exception e){
			 throw new ApplicationRuntimeException(e);
		}				
	}
	
						

	private DataSet maptoDataSet(Organization organization, Name<String> organizationName) {
					
		DataSet dataSet=DataSets.createDataSet(organizationName);
		DataSetHelper helper=DataSetUtils.newHelper(dataSet);
		
	
		//Name<String> ownerName = NamingScheme.getDefault().name(repository.getOwner().getId().toString());	
	
		helper.
		managedIndividual(organizationName, OrganizationHandler.ID).
			property(TYPE).
				withIndividual(ORGANIZATION_CLASS).
			property(ORGID).			
				withLiteral(organization.getId()).
			property(PREFLABEL).
				withLiteral(organization.getPrefLabel()).
			property(TITLE).
				withLiteral(organization.getTitle()).
//			property(FIRSTCOMMIT).
//				withLiteral(Mapper.toLiteral(new DateTime(repository.getFirstCommitAt()).toDate())).
			property(PURPOSE).
				withLiteral(organization.getPurpose());
//			property(ARCHIVED).
//				withLiteral(new Boolean(repository.getArchived())).
//			property(PUBLIC).
//				withLiteral(new Boolean(repository.getPublic())).
//			property(OWNER).
//				withIndividual(ownerName, UserHandler.ID).
//			property(REPOSITORYID).
//				withLiteral(repository.getId().toString()).
//			property(TAGS).
//				withLiteral(repository.getTags());		
	//			property(DEFAULTBRANCH).
	//			withIndividual(repository.getDefaultBranch());
		if ( organization.getDepicts() !=null)
			if ( !organization.getDepicts().isEmpty()){
				helper.
				managedIndividual(organizationName, OrganizationHandler.ID).
					property(DEPICTION).
						withIndividual(organizationName, OrganizationHandler.ID,IMG_PATH);
				helper.
				relativeIndividual(organizationName,OrganizationHandler.ID,IMG_PATH).
					property(TYPE).
						withIndividual(IMAGE_CLASS).
					property(DEPICTS).
						withIndividual(organization.getDepicts());		
			}
		
	if(organization.isOrganizationalCollaboration()){
		helper.
		managedIndividual(organizationName, OrganizationHandler.ID).
			property(TYPE).
				withIndividual(ORGANIZATIONALCOLLABORATION);
	}

	for (String organizationURI:organization.getHasMemberOrganization()){
		Name<String> memberOrgName = NamingScheme.getDefault().name(organizationURI);
		helper.
		managedIndividual(organizationName, OrganizationHandler.ID).
				property(HASMEMBERORGANIZATION).
					withIndividual(memberOrgName,OrganizationHandler.ID);
	}
	
	for (String projectId:organization.getHasProject()){
		Name<String> projectOrgName = NamingScheme.getDefault().name(projectId);
		helper.
		managedIndividual(organizationName, OrganizationHandler.ID).
				property(HASPROJECT).
					withIndividual(projectOrgName,ProjectHandler.ID);
	}
	
	for (String productId:organization.getHasProduct()){
		Name<String> productOrgName = NamingScheme.getDefault().name(productId);
		helper.
		managedIndividual(organizationName, OrganizationHandler.ID).
				property(HASPRODUCT).
					withIndividual(productOrgName,ProductHandler.ID);
	}
		
	for (String personURI:organization.getHasMember()){
		Name<String> personName = NamingScheme.getDefault().name(personURI);
		helper.
		managedIndividual(organizationName, OrganizationHandler.ID).
				property(HASMEMBER).
					withIndividual(personName,PersonHandler.ID);
	}
	
	for (String positionId:organization.getPosition()){
		Name<String> positionName = NamingScheme.getDefault().name(positionId);
		helper.
		managedIndividual(organizationName, OrganizationHandler.ID).
				property(ORGPOSITION).
					withIndividual(positionName,PositionHandler.ID);
	}	
	
	for (String roleId:organization.getRole()){
		Name<String> roleName = NamingScheme.getDefault().name(roleId);
		helper.
		managedIndividual(organizationName, OrganizationHandler.ID).
				property(ORGROLE).
					withIndividual(roleName,RoleHandler.ID);
	}	
	
	for (String membershipId:organization.getMembership()){
		Name<String> membershipName = NamingScheme.getDefault().name(membershipId);
		helper.
		managedIndividual(organizationName, OrganizationHandler.ID).
				property(MEMBERSHIP).
					withIndividual(membershipName,MembershipHandler.ID);
	}

	
	//	for (String branchId:repository.getBranches().getBranchIds()){
	//		Name<String> branchName = NamingScheme.getDefault().name(repository.getId().toString(),branchId);
	//		helper.
	//		managedIndividual(repoName, RepositoryHandler.ID).
	//				property(HASBRANCH).
	//					withIndividual(branchName,BranchHandler.ID);
	//	}
	//	

		if ( organization.getClassification()!=null){
			helper.
			managedIndividual(organizationName, OrganizationHandler.ID).
				property(CLASSIFICATION).
					withIndividual(organizationName, OrganizationHandler.ID,CLASSIFICATION_PATH);
			helper.
			relativeIndividual(organizationName,OrganizationHandler.ID,CLASSIFICATION_PATH).
				property(TYPE).
					withIndividual(SKOSCONCEPT).
				property(PREFLABEL).
					withLiteral(organization.getClassification()).
				property(LABEL).
					withLiteral(organization.getClassification());		
		}

				
//		if ( repository.getAvatarUrl() !=null){
//			helper.
//			managedIndividual(repoName, RepositoryHandler.ID).
//				property(DEPICTION).
//					withIndividual(repoName, RepositoryHandler.ID,DEPICTION_PATH);
//			helper.
//			relativeIndividual(repoName,RepositoryHandler.ID,DEPICTION_PATH).
//				property(TYPE).
//					withIndividual(IMAGE).
//				property(DEPICTS).
//					withIndividual(repository.getAvatarUrl());		
//		}
		
		return dataSet;
	}
	
}
