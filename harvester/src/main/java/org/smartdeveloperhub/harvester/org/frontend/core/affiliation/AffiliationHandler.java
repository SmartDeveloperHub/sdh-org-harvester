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
package org.smartdeveloperhub.harvester.org.frontend.core.affiliation;

import org.ldp4j.application.data.DataSet;
import org.ldp4j.application.data.DataSetHelper;
import org.ldp4j.application.data.DataSetUtils;
import org.ldp4j.application.data.DataSets;
import org.ldp4j.application.data.Name;
import org.ldp4j.application.data.NamingScheme;
import org.ldp4j.application.ext.ApplicationRuntimeException;
import org.ldp4j.application.ext.ResourceHandler;
import org.ldp4j.application.ext.UnknownResourceException;
import org.ldp4j.application.ext.annotations.Resource;
import org.ldp4j.application.session.ResourceSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartdeveloperhub.harvester.org.backend.pojo.Affiliation;
import org.smartdeveloperhub.harvester.org.backend.pojo.Membership;
import org.smartdeveloperhub.harvester.org.frontend.core.BackendController;
import org.smartdeveloperhub.harvester.org.frontend.core.Organization.OrganizationHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.membership.MembershipHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.membership.MembershipVocabulary;
import org.smartdeveloperhub.harvester.org.frontend.core.person.PersonHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.position.PositionHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.project.ProjectHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.role.RoleHandler;

@Resource(id=AffiliationHandler.ID)
public class AffiliationHandler implements ResourceHandler, AffiliationVocabulary{
	
	private static final Logger LOGGER=LoggerFactory.getLogger(AffiliationHandler.class);
	
	public static final String ID="AffiliationHandler";
	
	BackendController backendController;	
	
//	private static final URI IMAGE_PATH = URI.create("#image");
//	private static final URI HOMEPAGE_PATH = URI.create("#homepage");
	
	
	public AffiliationHandler(BackendController backendController){
		this.backendController=backendController;
	}

	@Override
	public DataSet get(ResourceSnapshot resource)
			throws UnknownResourceException, ApplicationRuntimeException {
		
		Name<String> affiliationName = (Name<String>)resource.name();						
		try{
			Affiliation affiliation= backendController.getAffiliationPublisher().getAffiliation(affiliationName.id().toString());
			LOGGER.debug("- Affiliation Info loaded..: {}",affiliation);
			return maptoDataSet(affiliation ,affiliationName);	
		}
		catch(Exception e){
			 throw new ApplicationRuntimeException(e);
		}	
	}

	private DataSet maptoDataSet(Affiliation affiliation, Name<String> affiliationName) {
		
		DataSet dataSet=DataSets.createDataSet(affiliationName);
		DataSetHelper helper=DataSetUtils.newHelper(dataSet);		
	
		Name<String> personName = NamingScheme.getDefault().name(affiliation.getAffiliate());	
		Name<String> projectName = NamingScheme.getDefault().name(affiliation.getAffiliationWith());
		Name<String> roleName = NamingScheme.getDefault().name(affiliation.getRole().getUri());		
	
		helper.
		managedIndividual(affiliationName, AffiliationHandler.ID).
			property(TYPE).
				withIndividual(AFFILIATION_CLASS).			
			property(AFFILIATE).
				withIndividual(personName, PersonHandler.ID).
			property(AFFILIATIONWITH).
				withIndividual(projectName, ProjectHandler.ID).
			property(ROLE).
				withIndividual(roleName, RoleHandler.ID);
				
		return dataSet;
	}
}
