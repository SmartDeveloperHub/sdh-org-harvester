package org.smartdeveloperhub.harvester.org.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartdeveloperhub.harvester.org.backend.pojo.Membership;
import org.smartdeveloperhub.harvester.org.backend.pojo.Role;
import org.smartdeveloperhub.harvester.org.frontend.core.affiliation.AffiliationVocabulary;
import org.smartdeveloperhub.harvester.org.frontend.core.membership.MembershipVocabulary;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class RolePublisher extends OntologyInstanceReader implements AffiliationVocabulary {

	private static final Logger LOGGER=LoggerFactory.getLogger(RolePublisher.class);
	
	public RolePublisher(OntModel ontModel) {
		super(ontModel);
		// TODO Auto-generated constructor stub
	}

	public Role getRole(String roleId) {
		
		long startTime = System.currentTimeMillis();
		Role role = new Role();		
		//ResIterator iter = ontModel.listSubjectsWithProperty(ontModel.getProperty(TYPE),membershipId);
		Resource roleResource  = ontModel.getResource(roleId);
	 		     
    	if (roleResource!=null){
    		
    		role.setUri(roleResource.getURI());
    		
    		Statement label=roleResource.getProperty(ontModel.getProperty(ROLELABEL));
    		if (label!=null)
    			role.setLabel(label.getString());
    		
    		Statement roleTypeStmt=roleResource.getProperty(ontModel.getProperty(ROLETYPE));
    		if (roleTypeStmt!=null){
    			Resource roleTypeResource=roleTypeStmt.getObject().asResource();
    			if (roleTypeResource!=null){
    				
    				role.setRoleTypeUri(roleTypeResource.getURI());
    				
    				Statement roleTypeLabel=roleResource.getProperty(ontModel.getProperty(ROLETYPELABEL));
		    		if (roleTypeLabel!=null)
		    			role.setRoleTypeLabel(roleTypeLabel.getString());
    			}
    		}
    	}    
    	 long stopTime = System.currentTimeMillis();
 		long elapsedTime = stopTime - startTime;
 		LOGGER.info("- Load the Role, elapsed time (ms)..: {}",elapsedTime);
    	return role;
	}

}
