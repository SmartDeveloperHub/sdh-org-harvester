package org.smartdeveloperhub.harvester.org.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartdeveloperhub.harvester.org.backend.pojo.Membership;
import org.smartdeveloperhub.harvester.org.backend.pojo.Person;
import org.smartdeveloperhub.harvester.org.backend.pojo.Position;
import org.smartdeveloperhub.harvester.org.frontend.core.membership.MembershipVocabulary;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class MembershipPublisher extends OntologyInstanceReader implements MembershipVocabulary{

	private static final Logger LOGGER=LoggerFactory.getLogger(MembershipPublisher.class);
	
	public MembershipPublisher(OntModel ontModel) {
		super(ontModel);
		// TODO Auto-generated constructor stub
	}

	public Membership getMembership(String membershipId) {
		
		long startTime = System.currentTimeMillis();
		Membership membership = new Membership();		
		//ResIterator iter = ontModel.listSubjectsWithProperty(ontModel.getProperty(TYPE),membershipId);
		Resource membershipResource  = ontModel.getResource(membershipId);
	    if (membershipResource!=null){
		    
		    membership.setUri(membershipId);
		    
		    Statement membershipOn = membershipResource.getProperty(ontModel.getProperty(MEMBERSHIPON));
		    if (membershipOn !=null)
		    	membership.setMembershipon(membershipOn.getString());
		    
		    Statement member = membershipResource.getProperty(ontModel.getProperty(MEMBER));
		    if (member !=null)
		    	membership.setMember(member.getString());		    
		    
		    Statement positionStmt = membershipResource.getProperty(ontModel.getProperty(POSITION));
		    if (positionStmt!=null){		    	
		    	PositionPublisher posPublisher = new PositionPublisher(ontModel);
		    	Resource positionResource=positionStmt.getObject().asResource();
		    	if (positionResource!=null){
		    		Position position= posPublisher.getPosition(positionResource.getURI());		    		
		    		membership.setPosition(position);
		    	}		    	
		    }		    
		}
	    long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		LOGGER.info("- Load the membership, elapsed time (ms)..: {}",elapsedTime);
	   return membership;		
	}
}
