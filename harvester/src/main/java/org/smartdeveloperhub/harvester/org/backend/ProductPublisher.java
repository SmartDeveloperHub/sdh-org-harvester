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
package org.smartdeveloperhub.harvester.org.backend;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartdeveloperhub.harvester.org.backend.pojo.Product;
import org.smartdeveloperhub.harvester.org.backend.pojo.Project;
import org.smartdeveloperhub.harvester.org.frontend.core.project.ProjectVocabulary;
import org.smartdeveloperhub.harvester.scm.frontend.core.product.ProductVocabulary;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class ProductPublisher extends OntologyInstanceReader implements ProductVocabulary{

	private static final Logger LOGGER=LoggerFactory.getLogger(ProductPublisher.class);
	
	public ProductPublisher(OntModel ontModel) {
		super(ontModel);
		// TODO Auto-generated constructor stub
	}

	public Product getProduct(String productURI) {
		long startTime = System.currentTimeMillis();
		Product product = new Product();		

		Resource r  = ontModel.getResource(productURI);
		if(r!=null){
			product.setUri(productURI);
		    
			Statement productId = r.getProperty(ontModel.getProperty(PRODUCTID));
		    if (productId !=null)
		    	product.setId(productId.getString());
		    
		    Statement preflabel = r.getProperty(ontModel.getProperty(PREFLABEL));
		    if (preflabel!=null)
		    	product.setPrefLabel(preflabel.getString());	 
		    
		    Statement description = r.getProperty(ontModel.getProperty(DESCRIPTION)) ;
		    if (description!=null)
		    	product.setDescription(description.getString());
		    
		    Statement createdOn = r.getProperty(ontModel.getProperty(CREATEDON)) ;
		    if (createdOn!=null)
		    	product.setCreatedOn(createdOn.getString());
		    
		    //relatesToProject
		    StmtIterator relatesToProjectIter = r.listProperties(ontModel.getProperty(RELATESTOPROJECT));
		    ArrayList<String> relatesToProject = new ArrayList<String>();
		    while (relatesToProjectIter.hasNext()) {
			    Statement stmtRelatesToProject = relatesToProjectIter.next();
			    Resource relatesToProjectRes= stmtRelatesToProject.getResource();
			    if (relatesToProjectRes!=null){
			    	relatesToProject.add(relatesToProjectRes.getURI());
			    }
		    }
		    product.setRelatesToProject(relatesToProject);
		    
		    //depiction
		    Statement depiction = r.getProperty(ontModel.getProperty(DEPICTION)) ;
		    if (depiction!=null){
		    	Statement depicts = depiction.getProperty(ontModel.getProperty(DEPICTS));
		    	if (depicts!=null){
		    		Resource imgRes=depicts.getResource();
		    		if (imgRes!=null)
		    			product.setDepicts(depicts.getResource().getURI());
		    	}
		    }
		    
		    long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			LOGGER.debug("- Load the project, elapsed time (ms)..: {}",elapsedTime);
		}
		
		return product;
		
	}


}
