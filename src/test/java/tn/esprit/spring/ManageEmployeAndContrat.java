package tn.esprit.spring;

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


import tn.esprit.spring.controller.IControllerEmployeImpl;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;

import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.text.SimpleDateFormat;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManageEmployeAndContrat {
	    private static final Logger l = LogManager.getLogger(ManageEmployeAndContrat.class);
		@Autowired
		IControllerEmployeImpl employeControl;
		@Test
		public void contextLoads() throws ParseException {
			
		
		Employe khaledKallel = new Employe("kallel", "khaled", "Khaled.kallel@ssiiconsulting.tn", true, Role.INGENIEUR);
		Employe mohamedZitouni = new Employe("zitouni", "mohamed", "mohamed.zitouni@ssiiconsulting.tn", true, Role.TECHNICIEN);
		Employe aymenOuali = new Employe("ouali", "aymen", "aymen.ouali@ssiiconsulting.tn", true, Role.INGENIEUR);
		Employe bochraBouzid = new Employe("bouzid", "bochra", "bochra.bouzid@ssiiconsulting.tn", true, Role.CHEF_DEPARTEMENT);
		Employe yosraArbi = new Employe("arbi", "yosra", "yosra.arbi@ssiiconsulting.tn", true, Role.CHEF_DEPARTEMENT);		
		
		int khaledKallelId = employeControl.ajouterEmploye(khaledKallel);
		int mohamedZitouniId = employeControl.ajouterEmploye(mohamedZitouni);
		int aymenOualiId = employeControl.ajouterEmploye(aymenOuali);
		int bochraBouzidId = employeControl.ajouterEmploye(bochraBouzid);
		int yosraArbiId = employeControl.ajouterEmploye(yosraArbi);
		
		int depRhId = 2;
		int depTelecomId = 1;
				
		employeControl.affecterEmployeADepartement(khaledKallelId, depRhId);
		employeControl.affecterEmployeADepartement(khaledKallelId, depTelecomId);
		
		employeControl.affecterEmployeADepartement(mohamedZitouniId, depRhId);
		employeControl.affecterEmployeADepartement(mohamedZitouniId, depTelecomId);
		
		employeControl.affecterEmployeADepartement(aymenOualiId, depTelecomId);
		
		employeControl.affecterEmployeADepartement(bochraBouzidId, depRhId);

		employeControl.affecterEmployeADepartement(yosraArbiId, depTelecomId);

		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Contrat khaledKallelContrat = new Contrat(dateFormat.parse("01/02/2015"), "CDI", 1600); 
		Contrat yosraArbiContrat = new Contrat(dateFormat.parse("01/03/2010"), "CDI", 2600);
		Contrat mohamedZitouniContrat = new Contrat(dateFormat.parse("15/05/2013"), "CDI", 900);
		Contrat aymenOualiContrat = new Contrat(dateFormat.parse("10/05/2014"), "CDI", 2000);
		Contrat bochraBouzidContrat = new Contrat(dateFormat.parse("12/06/2010"), "CDI", 2700);
		
		int khaledKallelContratId = employeControl.ajouterContrat(khaledKallelContrat);
		int yosraArbiContratId = employeControl.ajouterContrat(yosraArbiContrat);
		int mohamedZitouniContratId = employeControl.ajouterContrat(mohamedZitouniContrat);
		int aymenOualiContratId = employeControl.ajouterContrat(aymenOualiContrat);
		int bochraBouzidContratId = employeControl.ajouterContrat(bochraBouzidContrat);

		employeControl.affecterContratAEmploye(khaledKallelContratId, khaledKallelId);
		employeControl.affecterContratAEmploye(yosraArbiContratId, yosraArbiId);
		employeControl.affecterContratAEmploye(mohamedZitouniContratId, mohamedZitouniId);
		employeControl.affecterContratAEmploye(aymenOualiContratId, aymenOualiId);
		employeControl.affecterContratAEmploye(bochraBouzidContratId, bochraBouzidId);
		
		//Delete employe
		
		l.info(employeControl.getEmployePrenomById(aymenOualiId));
		//On ne peut pas supprimer un departement donc on desaffecte l'employe du departement
		employeControl.desaffecterEmployeDuDepartement(aymenOualiId, depTelecomId);
		
		//On supprime le contrat avant de supprimer l'employe
		//employeControl.deleteContratById(aymenOualiContratId);
		//Maintenant on peut supprimer l'employe
		//employeControl.deleteEmployeById(3);
		
		employeControl.mettreAjourEmailByEmployeId("newEmail@email.tn", bochraBouzidId);
		
		employeControl.mettreAjourEmailByEmployeIdJPQL("newEmail2@email.tn", bochraBouzidId);

		//employeControl.deleteAllContratJPQL();
		
		
		// TEST
		//int aymenOualiId = 3;
		float salaire = employeControl.getSalaireByEmployeIdJPQL(aymenOualiId);
		l.info("Le salaire de l'employe dont l'id est : " + aymenOualiId + " est : " + salaire);
		
		Entreprise entreprise = new Entreprise();
		entreprise.setId(1);
		List<Employe> employes = employeControl.getAllEmployeByEntreprise(entreprise);
		
		for(Employe employe : employes){
			l.info("*****" + employe.getNom());
		}
		
		
		l.info("Salaire Moyen By Departement"+employeControl.getSalaireMoyenByDepartementId(1));
		
		Employe employe = new Employe();
		employe.setId(1);
		Mission mission = new Mission();
		mission.setId(2);
		
		/*SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
		Date dateDebut = dateFormat1.parse("01/01/2016");
		Date dateFin = dateFormat.parse("15/06/2016");
		employeControl.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);*/
	}

}
