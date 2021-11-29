package java8.ex02;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import org.junit.Test;

import java8.data.Data;
import java8.data.domain.Customer;
import java8.data.domain.Order;

/**
 * Exercice 02 - Transformation
 */
public class Stream_02_Test {

	@Test
	public void test_map() throws Exception {

		List<Order> orders = new Data().getOrders();

		// Trouver la liste des clients ayant déjà passés une commande
		List<Customer> result = orders.stream().map(o -> o.getCustomer()).collect(Collectors.toList());

		assertThat(result, hasSize(8));
	}

	@Test
	public void test_map_count() throws Exception {

		List<Order> orders = new Data().getOrders();

		// TODO Trouver la liste des clients associés aux différentes commandes
		long result = orders.stream().map(o -> o.getCustomer()).count();

		assertThat(result, equalTo(8L));
	}

	@Test
	public void test_map_distinct() throws Exception {

		List<Order> orders = new Data().getOrders();

		// Trouver la liste des clients ayant déjà passés une commande
		List<Customer> result = orders.stream().map(o -> o.getCustomer()).distinct().collect(Collectors.toList());

		assertThat(result, hasSize(2));
	}

	@Test
	public void test_map_distinct_count() throws Exception {

		List<Order> orders = new Data().getOrders();

		// TODO Compter le nombre des différents clients associés aux commandes
		long result = orders.stream().map(o -> o.getCustomer()).distinct().count();

		assertThat(result, equalTo(2L));
	}

	@Test
	public void test_mapToDouble_sum() throws Exception {

		List<Order> orders = new Data().getOrders();

		/*
		 * TODO Calculer le chiffre d'affaires total de la pizzeria (somme des prix des
		 * commandes)
		 */
		double result = orders.stream().mapToDouble(o -> o.getPrice()).sum();

		assertThat(result, equalTo(10900.0));
	}

	@Test
	public void test_mapToDouble_avg() throws Exception {

		List<Order> orders = new Data().getOrders();

		/*
		 * TODO Calculer le chiffre d'affaires total de la pizzeria (somme des prix des
		 * commandes)
		 */
		OptionalDouble result = orders.stream().mapToDouble(o -> o.getPrice()).average();

		assertThat(result.isPresent(), equalTo(true));
		assertThat(result.getAsDouble(), equalTo(1362.5));
	}
}