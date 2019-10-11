package java8.ex08;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.summingInt;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * Exercice 5 - Files
 */
public class Stream_08_Test {

	// Chemin vers un fichier de données des naissances
		private static final String NAISSANCES_DEPUIS_1900_CSV = "./naissances_depuis_1900.csv";
		//private static final String NAISSANCES_DEPUIS_1900_CSV = "naissances_depuis_1900.csv";

		// Structure modélisant les informations d'une ligne du fichier
		class Naissance {
			String annee;
			String jour;
			Integer nombre;

			public Naissance(String annee, String jour, Integer nombre) {
				this.annee = annee;
				this.jour = jour;
				this.nombre = nombre;
			}

			public String getAnnee() {
				return annee;
			}

			public void setAnnee(String annee) {
				this.annee = annee;
			}

			public String getJour() {
				return jour;
			}

			public void setJour(String jour) {
				this.jour = jour;
			}

			public Integer getNombre() {
				return nombre;
			}

			public void setNombre(Integer nombre) {
				this.nombre = nombre;
			}
		}

		@Test
		public void test_group() throws IOException {

			// TODO utiliser la méthode java.nio.file.Files.lines pour créer un
			// stream de lignes du fichier naissances_depuis_1900.csv
			// Le bloc try(...) permet de fermer (close()) le stream après
			// utilisation
			try (Stream<String> lines = java.nio.file.Files.lines(Paths.get(NAISSANCES_DEPUIS_1900_CSV))) {

				// TODO construire une MAP (clé = année de naissance, valeur = somme
				// des nombres de naissance de l'année)
				Map<String, Integer> result = lines.skip(1).map(str -> {
					String[] infos = str.split(";");
					Naissance n = new Naissance(infos[1], infos[2], Integer.parseInt(infos[3]));
					return n;
				}).collect(groupingBy(Naissance::getAnnee, summingInt(Naissance::getNombre)));

				assertThat(result.get("2015"), is(8097));
				assertThat(result.get("1900"), is(5130));
			}
		}

		@Test
		public void test_max() throws IOException {

			// TODO utiliser la méthode java.nio.file.Files.lines pour créer un
			// stream de lignes du fichier naissances_depuis_1900.csv
			// Le bloc try(...) permet de fermer (close()) le stream après
			// utilisation
			try (Stream<String> lines = java.nio.file.Files.lines(Paths.get(NAISSANCES_DEPUIS_1900_CSV))) {

				// TODO trouver le jour où il y a eu le plus grand nombre de
				// naissances
				Optional<Naissance> result = lines.skip(1).map(str -> {
					String[] infos = str.split(";");
					Naissance n = new Naissance(infos[1], infos[2], Integer.parseInt(infos[3]));
					return n;
				}).max(Comparator.comparing(Naissance::getNombre));

				assertThat(result.get().getNombre(), is(48));
				assertThat(result.get().getJour(), is("19640228"));
				assertThat(result.get().getAnnee(), is("1964"));
			}
		}

		@Test
		public void test_collectingAndThen() throws IOException {
			// TODO utiliser la méthode java.nio.file.Files.lines pour créer un
			// stream de lignes du fichier naissances_depuis_1900.csv
			// Le bloc try(...) permet de fermer (close()) le stream après
			// utilisation
			try (Stream<String> lines = java.nio.file.Files.lines(Paths.get(NAISSANCES_DEPUIS_1900_CSV))) {

				// TODO construire une MAP (clé = année de naissance, valeur = maximum de nombre de naissances)
				// TODO utiliser la méthode "collectingAndThen" à la suite d'un "grouping"
				
				// Etape 1 : je produis une map avec en clé l'année de naissance et en valeur la liste de 
				// naissances correspondante
//				Map<String, List<Naissance>> result1 = lines.skip(1).map(str -> {
//					String[] infos = str.split(";");
//					Naissance n = new Naissance(infos[1], infos[2], Integer.parseInt(infos[3]));
//					return n;
//				}).collect(groupingBy(n -> n.getAnnee()));
				
				// Etape 2 : je traite la liste de naissances avec une fonction maxBy qui produit un Optional<Naissance>
//				Map<String, Optional<Naissance>> result2 = lines.skip(1).map(str -> {
//					String[] infos = str.split(";");
//					Naissance n = new Naissance(infos[1], infos[2], Integer.parseInt(infos[3]));
//					return n;
//				}).collect(groupingBy(n -> n.getAnnee(), maxBy(Comparator.comparingInt(n -> n.getNombre()))));
				
				// Etape 3 : j'utile la fonction collectingAndThen pour récupérer la naissance qui est stockée
				// dans l'optional une fois l'opération de collecte (maxBy) terminée.
				// Il faut comprendre le deuxième paramètre de la méthode collectingAndThen comme un post-traitement
				// effectué sur le maxBy.
				Map<String, Naissance> result = lines.skip(1).map(str -> {
					String[] infos = str.split(";");
					Naissance n = new Naissance(infos[1], infos[2], Integer.parseInt(infos[3]));
					return n;
				}).collect(groupingBy(n -> n.getAnnee(), collectingAndThen(maxBy(Comparator.comparingInt(n -> n.getNombre())), opt -> opt.get())));

				assertThat(result.get("2015").getNombre(), is(38));
				assertThat(result.get("2015").getJour(), is("20150909"));
				assertThat(result.get("2015").getAnnee(), is("2015"));

				assertThat(result.get("1900").getNombre(), is(31));
				assertThat(result.get("1900").getJour(), is("19000123"));
				assertThat(result.get("1900").getAnnee(), is("1900"));
			}
		}

		// Des données figurent dans le répertoire pizza-data
		// TODO explorer les fichiers pour voir leur forme
		// TODO compléter le test

		@Test
		public void test_pizzaData() throws IOException {
			// TODO utiliser la méthode java.nio.file.Files.list pour parcourir un
			// répertoire

			// TODO trouver la pizza la moins chère
			String pizzaNamePriceMin = null;

			assertThat(pizzaNamePriceMin, is("L'indienne"));

		}

}