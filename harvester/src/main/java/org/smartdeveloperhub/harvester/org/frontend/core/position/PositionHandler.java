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
package org.smartdeveloperhub.harvester.org.frontend.core.position;

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
import org.smartdeveloperhub.harvester.org.backend.pojo.Position;
import org.smartdeveloperhub.harvester.org.frontend.core.BackendController;
import org.smartdeveloperhub.harvester.org.frontend.core.membership.MembershipVocabulary;
import org.smartdeveloperhub.harvester.org.frontend.core.role.RoleHandler;

@Resource(id=PositionHandler.ID)
public class PositionHandler  implements ResourceHandler, MembershipVocabulary{
	
	private static final Logger LOGGER=LoggerFactory.getLogger(PositionHandler.class);
	public static final String ID="PositionHandler";				
		
	BackendController backendController;	
		
	private static final URI POSITIONTYPE_PATH = URI.create("#positiontype");
	
	
	public PositionHandler(BackendController backendController){
		this.backendController=backendController;
	}

	@Override
	public DataSet get(ResourceSnapshot resource)
			throws UnknownResourceException, ApplicationRuntimeException {
		
		Name<String> positionName = (Name<String>)resource.name();						
		try{
			Position position= backendController.getPositionPublisher().getPosition(positionName.id().toString());
			LOGGER.debug("- Position Info loaded..: {}",position);
			return maptoDataSet(position ,positionName);	
		}
		catch(Exception e){
			 throw new ApplicationRuntimeException(e);
		}	
	}

	private DataSet maptoDataSet(Position position, Name<String> positionName) {
		
		DataSet dataSet=DataSets.createDataSet(positionName);
		DataSetHelper helper=DataSetUtils.newHelper(dataSet);		

	
		helper.
		managedIndividual(positionName, PositionHandler.ID).
			property(TYPE).
				withIndividual(POSITIONCLASS).			
			property(POSITIONLABEL).
				withLiteral(position.getLabel());
		
		helper.
		managedIndividual(positionName, PositionHandler.ID).
			property(POSITIONTYPE).
				withIndividual(positionName, PositionHandler.ID,POSITIONTYPE_PATH);
		helper.
		relativeIndividual(positionName,PositionHandler.ID,POSITIONTYPE_PATH).
			property(TYPE).
				withIndividual(POSITIONTYPECLASS).
			property(POSITIONTYPELABEL).
				withLiteral(position.getPositionTypeLabel());		
				
		return dataSet;
	}	
}