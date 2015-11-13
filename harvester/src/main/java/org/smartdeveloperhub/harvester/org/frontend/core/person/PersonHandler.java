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
package org.smartdeveloperhub.harvester.org.frontend.core.person;

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
import org.ldp4j.application.ext.annotations.Resource;
import org.ldp4j.application.session.ResourceSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartdeveloperhub.harvester.org.backend.PersonPublisher;
import org.smartdeveloperhub.harvester.org.backend.pojo.Person;
import org.smartdeveloperhub.harvester.org.frontend.core.BackendController;
import org.smartdeveloperhub.harvester.org.frontend.core.Organization.OrganizationHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.affiliation.AffiliationHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.membership.MembershipHandler;

@Resource(id=PersonHandler.ID)
public class PersonHandler implements ResourceHandler, PersonVocabulary{
	
	private static final Logger LOGGER=LoggerFactory.getLogger(PersonHandler.class);
	
	public static final String ID="PersonHandler";
	
	BackendController backendController;	
	private static final URI IMAGE_PATH = URI.create("#image");
	private static final URI HOMEPAGE_PATH = URI.create("#homepage");
	
	
	public PersonHandler(BackendController backendController){
		this.backendController=backendController;
	}

	@Override
	public DataSet get(ResourceSnapshot resource)
			throws UnknownResourceException, ApplicationRuntimeException {
		
		Name<String> personName = (Name<String>)resource.name();						
		try{
			Person person = backendController.getPersonPublisher().getPerson(personName.id().toString());
			LOGGER.debug("- Person Info loaded..: {}",person);
			return maptoDataSet(person ,personName);	
		}
		catch(Exception e){
			 throw new ApplicationRuntimeException(e);
		}	
	}

	private DataSet maptoDataSet(Person person, Name<String> personName) {
		
		DataSet dataSet=DataSets.createDataSet(personName);
		DataSetHelper helper=DataSetUtils.newHelper(dataSet);		
	
		//Name<String> ownerName = NamingScheme.getDefault().name(repository.getOwner().getId().toString());	
	
		helper.
		managedIndividual(personName, PersonHandler.ID).
			property(TYPE).
				withIndividual(PERSONCLASS).			
			property(FOAFNAME).
				withLiteral(person.getName()).
			property(FOAFNICK).
				withLiteral(person.getNick()).
			property(FOAFMBOX).
				withLiteral(person.getMbox());

	for (String organizationURI:person.getMemberOf()){
		Name<String> OrganizationName = NamingScheme.getDefault().name(organizationURI);		
		helper.
		managedIndividual(personName, PersonHandler.ID).
				property(MEMBEROF).
					withIndividual(OrganizationName,OrganizationHandler.ID);
	}
	
	for (String membershipId:person.getHasMembership()){
		Name<String> membershipName = NamingScheme.getDefault().name(membershipId);
		helper.
		managedIndividual(personName, PersonHandler.ID).
				property(HASMEMBERSHIP).
					withIndividual(membershipName,MembershipHandler.ID);
	}
	
	for (String affiliationId:person.getIsAffiliated()){
		Name<String> affiliationName = NamingScheme.getDefault().name(affiliationId);
		helper.
		managedIndividual(personName, PersonHandler.ID).
				property(ISAFFILIATED).
					withIndividual(affiliationName,AffiliationHandler.ID);
	}	
	
		if ( person.getImg()!=null)
			if ( !person.getImg().isEmpty()){
				helper.
				managedIndividual(personName, PersonHandler.ID).
					property(FOAFIMG).
						withIndividual(personName, PersonHandler.ID,IMAGE_PATH);
				helper.
				relativeIndividual(personName,PersonHandler.ID,IMAGE_PATH).
					property(TYPE).
						withIndividual(FOAFIMG).
					property(FOAFDEPICTS).
						withLiteral(person.getImg());		
			}
						
		return dataSet;
	}

}
