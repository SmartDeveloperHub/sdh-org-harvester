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
 *   Artifact    : org.smartdeveloperhub.harvester.org:org-harvester-ldp4j:0.2.0-SNAPSHOT
 *   Bundle      : org-harvester.war
 * #-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=#
 */
package org.smartdeveloperhub.harvester.org.backend;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartdeveloperhub.harvester.org.backend.pojo.Person;
import org.smartdeveloperhub.harvester.org.backend.pojo.Project;
import org.smartdeveloperhub.harvester.org.frontend.core.person.PersonVocabulary;
import org.smartdeveloperhub.harvester.org.frontend.core.project.ProjectVocabulary;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class PersonPublisher extends OntologyInstanceReader implements PersonVocabulary{

	private static final Logger LOGGER=LoggerFactory.getLogger(PersonPublisher.class);
	
	public PersonPublisher(OntModel ontModel) {
		super(ontModel);
		// TODO Auto-generated constructor stub
	}

	public Person getPerson(String personId) {
		long startTime = System.currentTimeMillis();
		Person person = new Person();		
		ResIterator iter = ontModel.listSubjectsWithProperty(ontModel.getProperty(PERSONID),personId);
		while (iter.hasNext()) {
		    Resource r = iter.nextResource();
		    
		    person.setId(personId);
		    
		    Statement name = r.getProperty(ontModel.getProperty(FOAFNAME));
		    if (name !=null)
		    	person.setName(name.getString());
		    
		    Statement nick = r.getProperty(ontModel.getProperty(FOAFNICK));
		    if (nick!=null)
		    	person.setNick(nick.getString());
		    
		    Statement mbox = r.getProperty(ontModel.getProperty(FOAFMBOX));
		    if (mbox!=null)
		    	person.setMbox(mbox.getString());
		    
		    Statement img = r.getProperty(ontModel.getProperty(FOAFIMG));
		    if (img!=null){
		    	Resource imageObj=img.getObject().asResource();		    
		    	Statement depictsStmt= imageObj.getProperty(ontModel.getProperty(FOAFDEPICTS));
		    	if (depictsStmt.getObject().isLiteral())
		    		person.setImg(depictsStmt.getString());
		    	else
		    		person.setImg(depictsStmt.getResource().getURI());
		    }
		    
		    Statement homepage = r.getProperty(ontModel.getProperty(FOAFHOMEPAGE));
		    if (homepage!=null){
		    	Resource documentObj=homepage.getObject().asResource();		    
		    	person.setHomepage(documentObj.getURI());
		    }
		    		       
		    StmtIterator memberOfIter = r.listProperties(ontModel.getProperty(MEMBEROF));
		    ArrayList<String> memberOf = new ArrayList<String>();
		    while (memberOfIter.hasNext()) {
		    	Statement stmtMembOf = memberOfIter.next();
			    Resource orgResource= stmtMembOf.getResource();
			    if (orgResource!=null){
			    	Statement orgIdStmt = orgResource.getProperty(ontModel.getProperty(ORGID));
			    	if (orgIdStmt!=null)
			    	    memberOf.add(orgIdStmt.getString());
			    }
		    }
		    person.setMemberOf(memberOf);
		    
		    StmtIterator membershipIter = r.listProperties(ontModel.getProperty(HASMEMBERSHIP));
		    ArrayList<String> hasMembership = new ArrayList<String>();
		    while (membershipIter.hasNext()) {
		    	Statement stmtMembership = membershipIter.next();
			    Resource MembershipResource= stmtMembership.getResource();
			    if (MembershipResource!=null){
			    	Statement membershipIdStmt = MembershipResource.getProperty(ontModel.getProperty(MEMBERSHIPID));
			    	if (membershipIdStmt!=null)
			    		hasMembership.add(membershipIdStmt.getString());
			    }
		    }
		    person.setHasMembership(hasMembership);
		    
		    
		    StmtIterator affiliationIter = r.listProperties(ontModel.getProperty(ISAFFILIATED));
		    ArrayList<String> isAffiliated = new ArrayList<String>();
		    while (affiliationIter.hasNext()) {
		    	Statement stmtisAffilited = affiliationIter.next();
			    Resource AffiliationResource= stmtisAffilited.getResource();
			    if (AffiliationResource!=null){ 
			    	Statement affiliationIdStmt = AffiliationResource.getProperty(ontModel.getProperty(AFFILIATIONID));
			    	if (affiliationIdStmt!=null)
			    		isAffiliated.add(affiliationIdStmt.getString());
			    }
		    }
		    person.setIsAffiliated(isAffiliated);

		    
		    long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			LOGGER.info("- Load the person, elapsed time (ms)..: {}",elapsedTime);
		}
		return person;
		
	}
}
