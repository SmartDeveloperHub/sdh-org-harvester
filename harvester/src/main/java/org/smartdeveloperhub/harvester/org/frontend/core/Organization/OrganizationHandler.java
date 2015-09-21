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
package org.smartdeveloperhub.harvester.org.frontend.core.Organization;

import java.net.URI;

import org.joda.time.DateTime;
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



@Resource(id=OrganizationHandler.ID
//	,attachments={
//		@Attachment(
//			id=RepositoryHandler.REPOSITORY_BRANCHES,
//			path="branches/",
//			handler=BranchContainerHandler.class
//		),
//		@Attachment(
//				id=RepositoryHandler.REPOSITORY_COMMITS,
//				path="commits/",
//				handler=CommitContainerHandler.class
//			)
//	}
)

public class OrganizationHandler implements ResourceHandler, OrganizationVocabulary{
	
	public static final String ID="OrganizationHandler";
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
			property(ID).			
				withLiteral(organization.getId()).
			property(PREFLABEL).
				withLiteral(organization.getPrefLabel()).			
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
		
//		for (Integer userId:repository.getContributors()){
//			Name<String> userName = NamingScheme.getDefault().name(Integer.toString(userId));
//			helper.
//			managedIndividual(repoName, RepositoryHandler.ID).
//					property(DEVELOPER).
//						withIndividual(userName,UserHandler.ID);
//		}
		
	//	for (String branchId:repository.getBranches().getBranchIds()){
	//		Name<String> branchName = NamingScheme.getDefault().name(repository.getId().toString(),branchId);
	//		helper.
	//		managedIndividual(repoName, RepositoryHandler.ID).
	//				property(HASBRANCH).
	//					withIndividual(branchName,BranchHandler.ID);
	//	}
	//	

		if ( organization.getClass()!=null){
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
