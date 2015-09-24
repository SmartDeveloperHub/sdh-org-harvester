package org.smartdeveloperhub.harvester.org.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartdeveloperhub.harvester.org.backend.pojo.Position;
import org.smartdeveloperhub.harvester.org.backend.pojo.Role;
import org.smartdeveloperhub.harvester.org.frontend.core.affiliation.AffiliationVocabulary;
import org.smartdeveloperhub.harvester.org.frontend.core.membership.MembershipVocabulary;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class PositionPublisher extends OntologyInstanceReader implements MembershipVocabulary {

	private static final Logger LOGGER=LoggerFactory.getLogger(PositionPublisher.class);
	
	public PositionPublisher(OntModel ontModel) {
		super(ontModel);
		// TODO Auto-generated constructor stub
	}

	public Position getPosition(String positionId) {
		
		long startTime = System.currentTimeMillis();
		Position position = new Position();		
		//ResIterator iter = ontModel.listSubjectsWithProperty(ontModel.getProperty(TYPE),membershipId);
		Resource positionResource  = ontModel.getResource(positionId);
	 		     
    	if (positionResource!=null){    		  		
			position.setUri(positionResource.getURI());
			
			Statement label=positionResource.getProperty(ontModel.getProperty(POSITIONLABEL));
			if (label!=null)
				position.setLabel(label.getString());
			
			Statement positionTypeStmt=positionResource.getProperty(ontModel.getProperty(POSITIONTYPE));
			if (positionTypeStmt!=null){
				Resource positionTypeResource=positionTypeStmt.getObject().asResource();
				if (positionTypeResource!=null){
					
					position.setPositionTypeUri(positionTypeResource.getURI());
					
					Statement positionTypeLabel=positionResource.getProperty(ontModel.getProperty(POSITIONTYPELABEL));
		    		if (positionTypeLabel!=null)
		    			position.setPositionTypeLabel(positionTypeLabel.getString());
				}
			}
    	}	    		
    	 long stopTime = System.currentTimeMillis();
 		long elapsedTime = stopTime - startTime;
 		LOGGER.info("- Load the Position, elapsed time (ms)..: {}",elapsedTime);
    	return position;
	}
 


}
