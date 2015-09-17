package org.smartdeveloperhub.harvester.org;


import java.io.InputStream;

import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;      
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.query.*;

public class OrganizationIndividuals {
	String inputFileName; 
	OntModel ontModel;
	
	public void loadIndividuals(){
						
		inputFileName = "organization-individuals.ttl";
		InputStream in=
			Thread.
				currentThread().
					getContextClassLoader().
						getResourceAsStream(inputFileName);		
			
	    
//		InputStream in = FileManager.get().open(inputFileName);
		
		if (in == null) {
		    throw new IllegalArgumentException(
		                                 "File: " + inputFileName + " not found");
		}
		
				
		//this option worked!
		ontModel= ModelFactory.createOntologyModel();
		//OntDocumentManager docMgr = ontModel.getDocumentManager();
		
		// read the RDF/XML file
		ontModel.read(in, null, "TTL" );
		// write it to standard out
		//ontModel.writeAll(System.out, "TTL");
		//ontModel.write(System.out, "TTL");
					
	}
	
	public OntModel getJenaModel(){
		return ontModel;
	}
	
	public void getOrganizations_sparql(){
		String prefix = "PREFIX org: <http://www.smartpersonhub.org/vocabulary/v1/organization#> ";
		String queryString=prefix + "SELECT ?org WHERE {?org a org:Organization .} ";
		Query query = QueryFactory.create(queryString) ;
		try (QueryExecution qexec = QueryExecutionFactory.create(query, ontModel)) {
		    ResultSet results = qexec.execSelect() ;
		    for ( ; results.hasNext() ; )
		    {
		      QuerySolution soln = results.nextSolution() ;
		      //RDFNode x = soln.get("varName") ;       // Get a result variable by name.
		      Resource r = soln.getResource("org") ; // Get a result variable - must be a resource
		      //Literal l = soln.getLiteral("VarL") ;   // Get a result variable - must be a literal
		      
		      if (!r.isAnon()){
		    	  System.out.println(r.getURI());
		      }
		    }
		  }
	}
	
	public void getOrganizations(){
		//Property a=ontModel.getProperty("rdf:type");
		Resource organization=ontModel.getResource("http://www.smartdeveloperhub.org/vocabulary/organization#Organization");		                                            
		
		ResIterator iter =ontModel.listSubjectsWithProperty(RDF.type, organization);
		
		if (iter.hasNext()) {
		    System.out.println("The database contains organizations:");
		    while (iter.hasNext()) {
		        //System.out.println("  " + iter.nextResource().getProperty(RDF.type));
		        System.out.println("  " + iter.nextResource().getURI());
		        System.out.println("   -" + iter.nextResource().getProperty(ontModel.getProperty("http://www.smartdeveloperhub.org/vocabulary/organization#id")).getString());
		        System.out.println("   -" + iter.nextResource().getProperty(ontModel.getProperty("http://www.w3.org/ns/org#classification")).getResource().getURI());		        
		    }
		} else {
		    System.out.println("No organizations were found in the database");
		}
	}
	
}
