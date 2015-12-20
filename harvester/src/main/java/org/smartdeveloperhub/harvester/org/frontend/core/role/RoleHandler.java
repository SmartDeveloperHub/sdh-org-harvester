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
 *   Artifact    : org.smartdeveloperhub.harvester.org:org-harvester-frontend:0.2.0-SNAPSHOT
 *   Bundle      : org-harvester.war
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 */
package org.smartdeveloperhub.harvester.org.frontend.core.role;

import java.net.URI;

import org.ldp4j.application.data.DataSet;
import org.ldp4j.application.data.DataSetHelper;
import org.ldp4j.application.data.DataSetUtils;
import org.ldp4j.application.data.DataSets;
import org.ldp4j.application.data.Name;
import org.ldp4j.application.ext.ApplicationRuntimeException;
import org.ldp4j.application.ext.ResourceHandler;
import org.ldp4j.application.ext.UnknownResourceException;
import org.ldp4j.application.ext.annotations.Resource;
import org.ldp4j.application.session.ResourceSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartdeveloperhub.harvester.org.backend.pojo.Role;
import org.smartdeveloperhub.harvester.org.frontend.core.BackendController;
import org.smartdeveloperhub.harvester.org.frontend.core.affiliation.AffiliationVocabulary;

@Resource(id=RoleHandler.ID)
public class RoleHandler  implements ResourceHandler, AffiliationVocabulary{
	
	private static final Logger LOGGER=LoggerFactory.getLogger(RoleHandler.class);
	public static final String ID="RoleHandler";
	
	BackendController backendController;	
	
	private static final URI ROLETYPE_PATH = URI.create("#roletype");
//	private static final URI HOMEPAGE_PATH = URI.create("#homepage");
	
	
	public RoleHandler(BackendController backendController){
		this.backendController=backendController;
	}

	@Override
	public DataSet get(ResourceSnapshot resource)
			throws UnknownResourceException, ApplicationRuntimeException {
		
		Name<String> roleName = (Name<String>)resource.name();						
		try{
			Role role= backendController.getRolePublisher().getRole(roleName.id().toString());
			LOGGER.debug("- Role Info loaded..: {}",role);
			return maptoDataSet(role ,roleName);	
		}
		catch(Exception e){
			 throw new ApplicationRuntimeException(e);
		}	
	}

	private DataSet maptoDataSet(Role role, Name<String> roleName) {
		
		DataSet dataSet=DataSets.createDataSet(roleName);
		DataSetHelper helper=DataSetUtils.newHelper(dataSet);		

	
		helper.
		managedIndividual(roleName, RoleHandler.ID).
			property(TYPE).
				withIndividual(ROLECLASS).			
			property(ROLELABEL).
				withLiteral(role.getLabel());
		
		helper.
		managedIndividual(roleName, RoleHandler.ID).
			property(ROLETYPE).
				withIndividual(roleName, RoleHandler.ID, ROLETYPE_PATH);
		helper.
		relativeIndividual(roleName,RoleHandler.ID,ROLETYPE_PATH).
			property(TYPE).
				withIndividual(ROLETYPEClASS).
			property(ROLETYPELABEL).
				withLiteral(role.getRoleTypeLabel());		
				
		return dataSet;
	}	
}
