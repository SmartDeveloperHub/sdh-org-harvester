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

import org.ldp4j.application.data.NamingScheme;
import org.ldp4j.application.ext.Application;
import org.ldp4j.application.ext.ApplicationInitializationException;
import org.ldp4j.application.session.WriteSession;
import org.ldp4j.application.setup.Bootstrap;
import org.ldp4j.application.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartdeveloperhub.harvester.org.frontend.core.Organization.OrganizationContainerHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.Organization.OrganizationHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.affiliation.AffiliationContainerHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.affiliation.AffiliationHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.harvester.HarvesterHandler;
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


public final class HarvesterApplication extends Application<HarvesterConfiguration> {

		private static final Logger LOGGER=LoggerFactory.getLogger(HarvesterApplication.class);

		private static final String SERVICE_PATH="service/";

		private URI target;
			

		private BackendController controller;

		@Override
		public void setup(Environment environment, Bootstrap<HarvesterConfiguration> bootstrap){
			LOGGER.info("Starting ORG Harvester Application configuration...");

			HarvesterConfiguration configuration = bootstrap.configuration();

			try {
				LOGGER.info("- Target..: {}",configuration.target());		
				this.target=configuration.target();
			
				controller = new BackendController();
		
				bootstrap.addHandler(new HarvesterHandler(controller));				
		 	    bootstrap.addHandler(new OrganizationHandler(controller));
		 	    bootstrap.addHandlerClass(OrganizationContainerHandler.class);
		 	    bootstrap.addHandler(new ProjectHandler(controller));
		 	    bootstrap.addHandlerClass(ProjectContainerHandler.class);
		 	    bootstrap.addHandler(new ProductHandler(controller));
		 	    bootstrap.addHandlerClass(ProductContainerHandler.class);
				bootstrap.addHandler(new PersonHandler(controller));
				bootstrap.addHandlerClass(PersonContainerHandler.class);
				bootstrap.addHandler(new RoleHandler(controller));
				bootstrap.addHandlerClass(RoleContainerHandler.class);
				bootstrap.addHandler(new PositionHandler(controller));
				bootstrap.addHandlerClass(PositionContainerHandler.class);
				bootstrap.addHandler(new MembershipHandler(controller));
				bootstrap.addHandlerClass(MembershipContainerHandler.class);
				bootstrap.addHandler(new AffiliationHandler(controller));
				bootstrap.addHandlerClass(AffiliationContainerHandler.class);

					
				environment.
					publishResource(NamingScheme.getDefault().name(target),HarvesterHandler.class, SERVICE_PATH);
//				environment.
//					publishResource(NamingScheme.getDefault().name(UserContainerHandler.NAME), UserContainerHandler.class, UserContainerHandler.path);
			
			LOGGER.info("ORG Harvester Application configuration completed.");
			
			} catch (Exception e) {
				String errorMessage = "ORG Harvester Application Setup failed";
				LOGGER.warn(errorMessage+". Full stacktrace follows: ",e);			
			}		
		}

		@Override
		public void initialize(WriteSession session) throws ApplicationInitializationException {
			LOGGER.info("Initializing ORG Harvester Application...");
			BackendResourcePublisher publisher = new BackendResourcePublisher(session, controller);		
			
			try {
				publisher.publishHarvesterResources(target);
				//publisher.publishUserResources();
					
//				ContainerSnapshot repositoryContainerSnapshot = session.find(ContainerSnapshot.class, harvesterName ,RepositoryContainerHandler.class);			
//				if(repositoryContainerSnapshot==null) {
//					LOGGER.warn("Repository Container does not exits");
//					return;
//				}
				
				session.saveChanges();
				
				LOGGER.info("ORG Harvester Application initialization completed.");
			} catch (Exception e) {
				String errorMessage = "ORG Harvester Application initialization failed";
				LOGGER.warn(errorMessage+". Full stacktrace follows: ",e);
				throw new ApplicationInitializationException(e);
			}
		}

		@Override
		public void shutdown() {
			LOGGER.info("Starting *ORG Harvester Application* shutdown...");
//			this.controller.disconnect();
			LOGGER.info("ORG Harvester Application shutdown completed.");
		}	

  }
