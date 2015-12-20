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
package org.smartdeveloperhub.harvester.org.frontend.core.harvester;

import java.net.URI;
import java.util.ArrayList;

import org.ldp4j.application.data.DataSet;
import org.ldp4j.application.data.DataSets;
import org.ldp4j.application.data.DataSetHelper;
import org.ldp4j.application.data.DataSetUtils;
import org.ldp4j.application.data.Name;
import org.ldp4j.application.data.NamingScheme;
import org.ldp4j.application.ext.ApplicationRuntimeException;
import org.ldp4j.application.ext.ResourceHandler;
import org.ldp4j.application.ext.UnknownResourceException;
import org.ldp4j.application.ext.annotations.Attachment;
import org.ldp4j.application.ext.annotations.Resource;
import org.ldp4j.application.session.ResourceSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartdeveloperhub.harvester.org.backend.OrganizationPublisher;
import org.smartdeveloperhub.harvester.org.frontend.core.Organization.OrganizationContainerHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.Organization.OrganizationHandler;
import org.smartdeveloperhub.harvester.scm.frontend.core.product.ProductHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.BackendController;

@Resource(
		id=HarvesterHandler.ID,
		attachments={
			@Attachment(
				id=HarvesterHandler.HARVESTER_ORGANIZATIONS,
				path="organizations/",
				handler=OrganizationContainerHandler.class
			)
		}
	)
public class HarvesterHandler implements ResourceHandler, HarvesterVocabulary{
	
	private static final Logger LOGGER=LoggerFactory.getLogger(HarvesterHandler.class);
	
	public static final String ID="HarvesterHandler";
	public static final String HARVESTER_ORGANIZATIONS="HarvesterOrganizations";
	BackendController backendController;
	
	private static final URI VOCABULARY_PATH = URI.create("#vocabulary");
	
	public HarvesterHandler(BackendController backendController) {
//		//super(controller);
		this.backendController = backendController;
	}

	public DataSet get(ResourceSnapshot resource) throws UnknownResourceException, ApplicationRuntimeException {
		Name<URI> name = (Name<URI>)resource.name();
		try{
			OrganizationPublisher organizationPublisher = backendController.getOrganizationPublisher();	
			return maptoDataSet(name, organizationPublisher);
		
		}
		catch(Exception e){
			 throw new ApplicationRuntimeException(e);
		}
	}

	private DataSet maptoDataSet(Name<URI> harvesterName, OrganizationPublisher organizationPublisher) {
	
		Name<String> vocabularyName = NamingScheme.getDefault().name("vocabulary");
						
		DataSet dataSet=DataSets.createDataSet(harvesterName);

		DataSetHelper helper=DataSetUtils.newHelper(dataSet);

		helper.
			managedIndividual(harvesterName, ID).
				property(TYPE).
					withIndividual(DC_TYPE_SERVICE_TYPE).
					withIndividual(MICRO_SERVICE_TYPE).
					withIndividual(LINKED_DATA_MICRO_SERVICE_TYPE).
					withIndividual(HARVESTER).				
					withIndividual(ORGHARVESTER).
				property(HARVESTER_VOCABULARY).
					withIndividual(harvesterName,HarvesterHandler.ID,VOCABULARY_PATH);

		//organizations
		ArrayList<String> organizations = organizationPublisher.getOrganizations();
		LOGGER.debug("harvester Organizations URIs loaded ({})", organizations );
		
		for (String organizationURI:organizations){
			Name<String> organizationName = NamingScheme.getDefault().name(organizationURI);	
			helper.
			managedIndividual(harvesterName, ID).
					property(ORGANIZATION).
						withIndividual(organizationName,OrganizationHandler.ID);
		}


		helper.
			relativeIndividual(harvesterName,HarvesterHandler.ID,VOCABULARY_PATH).
				property(TYPE).
					withIndividual(ORGVOCABULARY).
					withIndividual(VOCABULARY).
				property(SOURCE).
					withLiteral(URI.create(ORG_V1_TTL)).
				property(DC_TERMS_SOURCE).
					withLiteral(URI.create(ORG_V1_TTL)).
				property(IMPLEMENTS).
					withIndividual(ORG_DOMAIN_TYPE);


		return dataSet;
	}

	
}
