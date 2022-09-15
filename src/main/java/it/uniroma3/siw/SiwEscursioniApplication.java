package it.uniroma3.siw;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import it.uniroma3.siw.escursioni.model.Activity;
import it.uniroma3.siw.escursioni.model.Credentials;
import it.uniroma3.siw.escursioni.model.Itinerary;
import it.uniroma3.siw.escursioni.model.Path;
import it.uniroma3.siw.escursioni.model.User;
import it.uniroma3.siw.escursioni.model.Place;

@SpringBootApplication
public class SiwEscursioniApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiwEscursioniApplication.class, args);

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("escursioni-unit");
		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();

		User admin = new User();
		admin.setName("bibi");
		admin.setSurname("bibi");

		User user2 = new User();
		user2.setName("gigi");
		user2.setSurname("gigi");

		Credentials credentials = new Credentials();
		credentials.setUsername("gigi@bibi.it");
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		credentials.setPassword(passwordEncoder.encode("test"));
		credentials.setUser(user2);
		credentials.setRole(Credentials.DEFAULT_ROLE);

		Credentials adminCredentials = new Credentials();
		adminCredentials.setUsername("bibi@bibi.it");
		adminCredentials.setPassword(passwordEncoder.encode("test"));
		adminCredentials.setUser(admin);
		adminCredentials.setRole(Credentials.ADMIN_ROLE);

		Place luogo = new Place();
		luogo.setName("Funivia Courmayeur");
		luogo.setNation("Italia");
		luogo.setRegion("Valle d'aoosta");
		luogo.setProvince("AO");
		luogo.setLatitude("45.7866492");
		luogo.setLongitude("6.9711282");
		luogo.setCity("Courmayeur");
		luogo.setAddress("Strada delle Volpi 17");
		
		Place luogo2 = new Place();
		luogo2.setName("Molina di Ledro");
		luogo2.setNation("Italia");
		luogo2.setRegion("Trentino Alto Adige");
		luogo2.setProvince("Provincia autonoma di Trento");
		luogo2.setLatitude("45.8739666");
		luogo2.setLongitude("10.765411452014543");
		luogo2.setCity("Molina di Ledro");
		luogo2.setAddress("Via al Lago 1");
		
		Place luogo3 = new Place();
		luogo3.setName("Grotte di Stiffe");
		luogo3.setNation("Italia");
		luogo3.setRegion("Abruzzo");
		luogo3.setProvince("AQ");
		luogo3.setLatitude("42.2562799");
		luogo3.setLongitude("13.5162624");
		luogo3.setCity("San Demetrio Ne' Vestini");
		luogo3.setAddress("Via Del Mulino Stiffe 2");
		
		Place luogo4 = new Place();
		luogo4.setName("Cascata delle marmore");
		luogo4.setNation("Italia");
		luogo4.setRegion("Umbria");
		luogo4.setProvince("TR");
		luogo4.setLatitude("42.5506333");
		luogo4.setLongitude("12.7141001");
		luogo4.setCity("Marmore");
		luogo4.setAddress("Vocabolo cascata");

		Path path = new Path();
		path.setName("Percorso in funivia sul Monte Bianco");
		path.setDescription("È un viaggio emozionante e indimenticabile che sovrasta il Monte Bianco e conduce fino a Chamonix Mont Blanc. Dai 1.300 m di Courmayeur/The Valley si arriva a quota 2.173 m del Pavillon The Mountain. Qui ci si prepara all’ultima sfida, alla vetta di Punta Helbronner.Da Punta Helbronner attraverso la Trans Mont Blanc, il viaggio prosegue, le funi della Compagnie du Mont Blanc solcano il ghiacciaio della Mer de Glace arrivando prima fino all’Aiguille du Midi e poi scendendo fino a Chamonix, dissolvendo il confine tra Italia e Francia, per unire due nazioni.");
		path.setDifficulty("F-facile");
		path.setDuration("60");
		path.setHeight(2000);
		path.setStart(luogo);
		
		
		List<Path> paths = new ArrayList<>();
		paths.add(path);
		
		luogo.setRoutes(paths);
		
		Path path2 = new Path();
		path2.setName("Percorso sul Lago di Ledro");
		path2.setDescription("L’itinerario individuato si sviluppa per circa 4 km lungo il lago di Ledro, partendo dal Museo delle Palafitte.");
		path2.setDifficulty("F-facile");
		path2.setDuration("40");
		path2.setStart(luogo2);
		
		List<Path> paths2 = new ArrayList<>();
		paths2.add(path2);
		
		luogo2.setRoutes(paths2);
		
		Path path3 = new Path();
		path3.setName("Grotte di Stiffe - Lago Sinizzo");
		path3.setDescription("Questo percorso rappresenta un collegamento tra la biglietteria delle Grotte di Stiffe e il Lago Sinizzo.");
		path3.setDifficulty("M-intermedio");
		path3.setDuration("30");
		path3.setStart(luogo3);
		
		List<Path> paths3 = new ArrayList<>();
		paths3.add(path3);
		
		luogo3.setRoutes(paths3);
		
		Activity activity = new Activity();
		activity.setName("Rafting");
		activity.setDescription("La bellezza della Cascata delle Marmore non si ferma solo alla maestosità della Cascata e al fragore delle acque. Nel parco della Cascata è possibile cimentarsi in tantissime attività, delle cui il Rafting è sicuramente la più divertente ed emozionante, perfetta per coniugare il contatto con la natura del parco fluviale all'adrenalina della discesa nelle rapide.");
		activity.setPlace(luogo4);
		activity.setDifficulty("F-facile");
		activity.setPrice(50.0);
		activity.setPeoples(6);
		activity.setDuration("40 minuti");
		
		Activity activity2 = new Activity();
		activity2.setName("Hydrospeed");
		activity2.setDescription("Hydrospeed cascate delle marmore");
		activity2.setPlace(luogo4);
		activity2.setDifficulty("F-facile");
		activity2.setPrice(70.0);
		activity2.setPeoples(1);
		activity2.setDuration("2 ore");

		List<Activity> activities = new ArrayList<>();
		activities.add(activity);
		activities.add(activity2);
		luogo4.setActivities(activities);
		
		Itinerary itinerary = new Itinerary();
		itinerary.setName("Montagna Gennaio 2023");
		itinerary.setDescription("Vacanza in montagna gennaio 2023 in settimana bianca");
		itinerary.setTraveler(user2);
		
		
		List<Itinerary> itineraries = new ArrayList<>();
		itineraries.add(itinerary);
		
		user2.setItineraries(itineraries);
		
		itinerary.setPaths(paths);
		
		tx.begin ();
		em.persist (user2);
		em.persist (admin);
		em.persist (credentials);
		em.persist (adminCredentials);
		em.persist (luogo);
		em.persist (luogo2);
		em.persist (luogo3);
		em.persist (luogo4);
		em.persist (path);
		em.persist (path2);
		em.persist (path3);
		em.persist (activity);
		em.persist (activity2);
		em.persist (itinerary);
		tx.commit ();

		em.close();
		emf.close();
	}

}
