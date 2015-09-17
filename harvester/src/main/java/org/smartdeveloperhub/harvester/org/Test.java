package org.smartdeveloperhub.harvester.org;

public class Test {

	public static void main(String[] args) {
		OrganizationIndividuals onto = new OrganizationIndividuals();
		onto.loadIndividuals();
		onto.getOrganizations();
	}

}
