package org.smartdeveloperhub.harvester.org.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartdeveloperhub.harvester.org.backend.pojo.Affiliation;
import org.smartdeveloperhub.harvester.org.backend.pojo.Role;
import org.smartdeveloperhub.harvester.org.frontend.core.affiliation.AffiliationVocabulary;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class AffiliationPublisher extends OntologyInstanceReader implements AffiliationVocabulary{

	private static final Logger LOGGER=LoggerFactory.getLogger(AffiliationPublisher.class);
	
	public AffiliationPublisher(OntModel ontModel) {
		super(ontModel);
		// TODO Auto-generated constructor stub
	}

	public Affiliation getAffiliation(String affiliationId) {
		
		long startTime = System.currentTimeMillis();
		Affiliation affiliation = new Affiliation();		
		//ResIterator iter = ontModel.listSubjectsWithProperty(ontModel.getProperty(TYPE),affiliationId);
		Resource affiliationResource  = ontModel.getResource(affiliationId);
	    if (affiliationResource!=null){
		    
		    affiliation.setUri(affiliationId);
		    
		    Statement affiliationWith = affiliationResource.getProperty(ontModel.getProperty(AFFILIATIONWITH));
		    if (affiliationWith !=null)
		    	affiliation.setAffiliationWith(affiliationWith.getString());
		    
		    Statement affiliate = affiliationResource.getProperty(ontModel.getProperty(AFFILIATE));
		    if (affiliate !=null)
		    	affiliation.setAffiliate(affiliate.getString());		    
		    
		    Statement roleStmt = affiliationResource.getProperty(ontModel.getProperty(ROLE));
		    if (roleStmt!=null){
		    	RolePublisher rolePublisher = new RolePublisher(ontModel);		    	
		    	Resource roleResource=roleStmt.getObject().asResource();
		    	if (roleResource!=null){
		    		Role role = rolePublisher.getRole(roleResource.getURI());
		    		affiliation.setRole(role);
		    	}
		    }		    
		}
	    long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		LOGGER.info("- Load the affiliation, elapsed time (ms)..: {}",elapsedTime);
	   return affiliation;		
	}

}
