package br.com.ecommerce.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import br.com.ecommerce.models.Person;

@Path("/people")
public class PeopleResource {

	// Mapa estatico para testes
	static private Map<Integer, Person> peopleMap;

	static {
		peopleMap = new HashMap<Integer, Person>();

		Person p1 = new Person(1, "carolinepereiraf@gmail.com", "Caroline");
		peopleMap.put(p1.getId(), p1);

		Person p2 = new Person(2, "luiza@gmail.com", "Luiza");
		peopleMap.put(p2.getId(), p2);
	}

	/**
	 * Retorna todas as pessoas existentes com um limite
	 * @param limit
	 * @return
	 */
	@GET
	@Produces("application/json")
	public List<Person> getPeople(@QueryParam("limit") int limit) {
		if (limit == 0) {
			return new ArrayList<Person>(peopleMap.values());
		}
		// Como os IDs sao inseridos pelo usuario, pode acontecer erros aqui.
		List<Person> peopleList = new ArrayList<Person>(limit);
		for (int i = 1; i <= limit; i++) {
			peopleList.add(peopleMap.get(i));
		}
		return peopleList;
	}

	/**
	 * Busca pessoa com ID especifico
	 * 
	 * @param id
	 * @return
	 */
	@Path("{personId}")
	@GET
	@Produces("application/json")
	public Person getPerson(@PathParam("personId") int id) {
		return peopleMap.get(id);
	}

	/**
	 * Adiciona uma pessoa com os parametros especificados
	 * 
	 * @param personId
	 * @param email
	 * @param name
	 * @return
	 */
	@POST
	@Consumes("text/xml")
	@Produces("application/json")
	public String addPerson(@FormParam("personId") int personId,
			@FormParam("email") String email, @FormParam("name") String name) {

		Person person = new Person(personId, email, name);
		peopleMap.put(person.getId(), person);
		String message = person.getName() + " adicionado com sucesso.";
		return message;
	}

	/**
	 * Deleta uma pessoa de ID especifico
	 * 
	 * @param id
	 * @return
	 */
	@Path("{personId}")
	@DELETE
	@Produces("application/json")
	public String removePerson(@PathParam("personId") int id) {
		String name = peopleMap.get(id).getName();
		peopleMap.remove(id);
		String message = "Pessoa de nome " + name + " removida com sucesso.";
		return message;
	}
}
