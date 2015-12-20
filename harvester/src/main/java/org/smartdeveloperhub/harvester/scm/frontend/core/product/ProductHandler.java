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
package org.smartdeveloperhub.harvester.scm.frontend.core.product;

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
import org.ldp4j.application.ext.annotations.Resource;
import org.ldp4j.application.session.ResourceSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartdeveloperhub.harvester.org.backend.pojo.Organization;
import org.smartdeveloperhub.harvester.org.backend.pojo.Product;
import org.smartdeveloperhub.harvester.org.frontend.core.BackendController;
import org.smartdeveloperhub.harvester.org.frontend.core.Organization.OrganizationHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.project.ProjectHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.project.ProjectVocabulary;
import org.smartdeveloperhub.harvester.org.frontend.core.util.Mapper;

@Resource(id=ProductHandler.ID)
public class ProductHandler  implements ResourceHandler, ProductVocabulary{
	
	private static final Logger LOGGER=LoggerFactory.getLogger(ProductHandler.class);
	
	public static final String ID="ProductHandler";
	//public static final String RELATESTOPROJECT="RELATESTOPROJECTS";
	
	private static final URI IMG_PATH = URI.create("#img");
	
	BackendController backendController;
	
	//private static final URI CLASSIFICATION_PATH = URI.create("#classification");
	
	public ProductHandler(BackendController backendController){
		this.backendController=backendController;
	}

	@Override
	public DataSet get(ResourceSnapshot resource) throws UnknownResourceException {
		
		Name<String> name = (Name<String>)resource.name();						
		try{
			Product product= backendController.getProductPublisher().getProduct(name.id().toString());	
			LOGGER.debug("product loaded ({})", product);
			return maptoDataSet(product,name);	
		}
		catch(Exception e){
			 throw new ApplicationRuntimeException(e);
		}				
	}

	private DataSet maptoDataSet(Product product, Name<String> name) {
		// TODO Auto-generated method stub
		DataSet dataSet=DataSets.createDataSet(name);
		DataSetHelper helper=DataSetUtils.newHelper(dataSet);			
		//Name<String> ownerName = NamingScheme.getDefault().name(repository.getOwner().getId().toString());	
	
		helper.
		managedIndividual(name, ProductHandler.ID).
			property(TYPE).
				withIndividual(PRODUCT_CLASS).
			property(PRODUCTID).			
				withLiteral(product.getId()).
			property(PREFLABEL).
				withLiteral(product.getPrefLabel()).
			property(DESCRIPTION).
				withLiteral(product.getDescription()).
			property(CREATEDON).
				withLiteral(Mapper.toLiteral(product.getCreatedOn()));
		
		for (String projectURI:product.getRelatesToProject()){			
			Name<String> projectOrgName = NamingScheme.getDefault().name(projectURI);
			helper.
			managedIndividual(name, ProductHandler.ID).
					property(RELATESTOPROJECT).
						withIndividual(projectOrgName, ProjectHandler.ID);
		}
		
		if ( product.getDepicts() !=null)
			if ( !product.getDepicts().isEmpty()) {
			helper.
			managedIndividual(name, ProductHandler.ID).
				property(DEPICTION).
					withIndividual(name, ProductHandler.ID,IMG_PATH);
			helper.
			relativeIndividual(name,ProductHandler.ID,IMG_PATH).
				property(TYPE).
					withIndividual(IMAGE_CLASS).
				property(DEPICTS).
					withIndividual(product.getDepicts());		
			}
		
		return dataSet;
	}


}
