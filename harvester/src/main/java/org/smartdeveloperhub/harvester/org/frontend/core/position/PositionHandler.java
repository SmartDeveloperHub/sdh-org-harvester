package org.smartdeveloperhub.harvester.org.frontend.core.position;

import java.net.URI;
import org.ldp4j.application.data.DataSet;
import org.ldp4j.application.data.DataSetHelper;
import org.ldp4j.application.data.DataSetUtils;
import org.ldp4j.application.data.DataSets;
import org.ldp4j.application.data.Name;
import org.ldp4j.application.ext.ApplicationRuntimeException;
import org.ldp4j.application.ext.ResourceHandler;
import org.ldp4j.application.ext.UnknownResourceException;
import org.ldp4j.application.ext.annotations.Resource;
import org.ldp4j.application.session.ResourceSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartdeveloperhub.harvester.org.backend.pojo.Position;
import org.smartdeveloperhub.harvester.org.frontend.core.BackendController;
import org.smartdeveloperhub.harvester.org.frontend.core.Role.RoleHandler;
import org.smartdeveloperhub.harvester.org.frontend.core.membership.MembershipVocabulary;

@Resource(id=PositionHandler.ID)
public class PositionHandler  implements ResourceHandler, MembershipVocabulary{
	
	private static final Logger LOGGER=LoggerFactory.getLogger(PositionHandler.class);
	public static final String ID="PositionHandler";				
		
	BackendController backendController;	
		
	private static final URI POSITIONTYPE_PATH = URI.create("#positiontype");
	
	
	public PositionHandler(BackendController backendController){
		this.backendController=backendController;
	}

	@Override
	public DataSet get(ResourceSnapshot resource)
			throws UnknownResourceException, ApplicationRuntimeException {
		
		Name<String> positionName = (Name<String>)resource.name();						
		try{
			Position position= backendController.getPositionPublisher().getPosition(positionName.id().toString());
			LOGGER.info("- Position Info loaded..: {}",position);
			return maptoDataSet(position ,positionName);	
		}
		catch(Exception e){
			 throw new ApplicationRuntimeException(e);
		}	
	}

	private DataSet maptoDataSet(Position position, Name<String> positionName) {
		
		DataSet dataSet=DataSets.createDataSet(positionName);
		DataSetHelper helper=DataSetUtils.newHelper(dataSet);		

	
		helper.
		managedIndividual(positionName, PositionHandler.ID).
			property(TYPE).
				withIndividual(POSITIONCLASS).			
			property(POSITIONLABEL).
				withLiteral(position.getLabel());
		
		helper.
		managedIndividual(positionName, PositionHandler.ID).
			property(POSITIONTYPE).
				withIndividual(positionName, PositionHandler.ID,POSITIONTYPE_PATH);
		helper.
		relativeIndividual(positionName,PositionHandler.ID,POSITIONTYPE_PATH).
			property(TYPE).
				withIndividual(POSITIONTYPECLASS).
			property(POSITIONTYPELABEL).
				withLiteral(position.getPositionTypeLabel());		
				
		return dataSet;
	}	
}