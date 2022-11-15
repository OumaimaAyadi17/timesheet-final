package tn.esprit.spring;



import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;


import tn.esprit.spring.controller.IControllerEntrepriseImpl;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AddEntrepriseAndDepartements {
	private static final Logger l = LogManager.getLogger(AddEntrepriseAndDepartements.class);
	@Autowired
    IControllerEntrepriseImpl entrepriseControl;
	@Test
	public void contextLoads() {
		    Entreprise ssiiConsulting = new Entreprise("SSII Consulting", "Cite El Ghazela");
			
			Departement depTelecom = new Departement("Telecom");
			Departement depRH = new Departement("RH");
			
			ssiiConsulting.addDepartement(depRH);
			ssiiConsulting.addDepartement(depTelecom);

			int ssiiConsultingId = entrepriseControl.ajouterEntreprise(ssiiConsulting);
			ssiiConsulting.setId(ssiiConsultingId);
			
			
			depTelecom.setEntreprise(ssiiConsulting);//En ajoutant cette ligne, on n'a plus besoin
			//d'appeler la methode affecterDepartementAEntreprise(depTelecomId, ssiiConsultingId)
			int depTelecomId = entrepriseControl.ajouterDepartement(depTelecom);
			
			depRH.setEntreprise(ssiiConsulting);//En ajoutant cette ligne, on n'a plus besoin
			//d'appeler la methode affecterDepartementAEntreprise(depTelecomId, ssiiConsultingId)
			int depRhId = entrepriseControl.ajouterDepartement(depRH);


			entrepriseControl.affecterDepartementAEntreprise(depTelecomId, ssiiConsultingId);
			entrepriseControl.affecterDepartementAEntreprise(depRhId, ssiiConsultingId);

			List<String> departements = entrepriseControl.getAllDepartementsNamesByEntreprise(ssiiConsultingId);
			for (String departementName : departements) {
				l.info(departementName);
			}
			
			Entreprise entreprise = entrepriseControl.getEntrepriseById(1);
			
			for(Departement dep : entreprise.getDepartements()){
				l.info(dep.getName());
			}
		}

}
