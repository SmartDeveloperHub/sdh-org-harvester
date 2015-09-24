package org.smartdeveloperhub.harvester.org.frontend.core.Role;

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
import org.smartdeveloperhub.harvester.org.backend.pojo.Role;
import org.smartdeveloperhub.harvester.org.frontend.core.BackendController;
import org.smartdeveloperhub.harvester.org.frontend.core.affiliation.AffiliationVocabulary;

@Resource(id=RoleHandler.ID)
public class RoleHandler  implements ResourceHandler, AffiliationVocabulary{
	
	private static final Logger LOGGER=LoggerFactory.getLogger(RoleHandler.class);
	public static final String ID="RoleHandler";
	
	BackendController backendController;	
	
	private static final URI ROLETYPE_PATH = URI.create("#roletype");
//	private static final URI HOMEPAGE_PATH = URI.create("#homepage");
	
	
	public RoleHandler(BackendController backendController){
		this.backendController=backendController;
	}

	@Override
	public DataSet get(ResourceSnapshot resource)
			throws UnknownResourceException, ApplicationRuntimeException {
		
		Name<String> roleName = (Name<String>)resource.name();						
		try{
			Role role= backendController.getRolePublisher().getRole(roleName.id().toString());
			LOGGER.info("- Role Info loaded..: {}",role);
			return maptoDataSet(role ,roleName);	
		}
		catch(Exception e){
			 throw new ApplicationRuntimeException(e);
		}	
	}

	private DataSet maptoDataSet(Role role, Name<String> roleName) {
		
		DataSet dataSet=DataSets.createDataSet(roleName);
		DataSetHelper helper=DataSetUtils.newHelper(dataSet);		

	
		helper.
		managedIndividual(roleName, RoleHandler.ID).
			property(TYPE).
				withIndividual(ROLECLASS).			
			property(ROLELABEL).
				withLiteral(role.getLabel());
		
		helper.
		managedIndividual(roleName, RoleHandler.ID).
			property(ROLETYPE).
				withIndividual(roleName, RoleHandler.ID, ROLETYPE_PATH);
		helper.
		relativeIndividual(roleName,RoleHandler.ID,ROLETYPE_PATH).
			property(TYPE).
				withIndividual(ROLETYPEClASS).
			property(ROLETYPELABEL).
				withLiteral(role.getRoleTypeLabel());		
				
		return dataSet;
	}	
}
