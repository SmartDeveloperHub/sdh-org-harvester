package org.smartdeveloperhub.harvester.org.frontend.core.membership;

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
import org.smartdeveloperhub.harvester.org.backend.pojo.Membership;
import org.smartdeveloperhub.harvester.org.backend.pojo.Person;
import org.smartdeveloperhub.harvester.org.frontend.core.BackendController;
import org.smartdeveloperhub.harvester.org.frontend.core.Organization.OrganizationHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.person.PersonHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.person.PersonVocabulary;
import org.smartdeveloperhub.harvester.org.frontend.core.position.PositionHandler;


@Resource(id=MembershipHandler.ID)
public class MembershipHandler implements ResourceHandler, MembershipVocabulary{
	
	private static final Logger LOGGER=LoggerFactory.getLogger(MembershipHandler.class);
	
	public static final String ID="MembershipHandler";
	
	BackendController backendController;	
	
//	private static final URI IMAGE_PATH = URI.create("#image");
//	private static final URI HOMEPAGE_PATH = URI.create("#homepage");
	
	
	public MembershipHandler(BackendController backendController){
		this.backendController=backendController;
	}

	@Override
	public DataSet get(ResourceSnapshot resource)
			throws UnknownResourceException, ApplicationRuntimeException {
		
		Name<String> membershipName = (Name<String>)resource.name();						
		try{
			Membership membership = backendController.getMembershipPublisher().getMembership(membershipName.id().toString());
			LOGGER.info("- Membership Info loaded..: {}",membership);
			return maptoDataSet(membership ,membershipName);	
		}
		catch(Exception e){
			 throw new ApplicationRuntimeException(e);
		}	
	}

	private DataSet maptoDataSet(Membership membership, Name<String> membershipName) {
		
		DataSet dataSet=DataSets.createDataSet(membershipName);
		DataSetHelper helper=DataSetUtils.newHelper(dataSet);		
	
		Name<String> personName = NamingScheme.getDefault().name(membership.getMember());	
		Name<String> organizationName = NamingScheme.getDefault().name(membership.getMembershipon());
		Name<String> positionName = NamingScheme.getDefault().name(membership.getPosition().getUri());		
	
		helper.
		managedIndividual(membershipName, MembershipHandler.ID).
			property(TYPE).
				withIndividual(MEMBERSHIP_CLASS).			
			property(MEMBER).
				withIndividual(personName, PersonHandler.ID).
			property(MEMBERSHIPON).
				withIndividual(organizationName, OrganizationHandler.ID).
			property(POSITION).
				withIndividual(positionName, PositionHandler.ID);
				
		return dataSet;
	}

}
