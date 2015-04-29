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

import org.apache.log4j.Logger;

import br.com.ecommerce.models.Product;

@Path("/products")
public class ProductsResource {

	/**
	 * Log
	 */
	static Logger log = Logger.getLogger(ProductsResource.class);

	// Mapa estatico para testes
	static private Map<Integer, Product> productsMap;

	static {
		productsMap = new HashMap<Integer, Product>();

		Product p1 = new Product(1, 20.0, "Estojo");
		productsMap.put(p1.getId(), p1);

		Product p2 = new Product(2, 50.0, "Livro");
		productsMap.put(p2.getId(), p2);
	}

	/**
	 * Lista todos os produtos existentes com um limite
	 * 
	 * @param limit
	 * @return
	 */
	@GET
	@Produces("application/json")
	public List<Product> getProducts(@QueryParam("limit") int limit) {
		log.info("Returning products, limit = " + limit);
		
		if (limit == 0) {
			return new ArrayList<Product>(productsMap.values());
		}
		// Como os IDs sao inseridos pelo usuario, pode acontecer erros aqui.
		List<Product> productsList = new ArrayList<Product>(limit);
		for (int i = 1; i <= limit; i++) {
			productsList.add(productsMap.get(i));
		}
		return productsList;
	}

	/**
	 * Busca um produto com ID especifico
	 * 
	 * @param id
	 * @return
	 */
	@Path("{productId}")
	@GET
	@Produces("application/json")
	public Product getProduct(@PathParam("productId") int id) {
		Product product = productsMap.get(id);
		if (product != null) {
			log.info("Returning product " + product.getName());
		} else {
			log.info("No product found for ID " + id);
		}
		return productsMap.get(id);
	}

	/**
	 * Adiciona um produto com os parametros especificados
	 * 
	 * @param productId
	 * @param price
	 * @param name
	 * @return
	 */
	@POST
	@Consumes("text/xml")
	@Produces("application/json")
	public String addProduct(@FormParam("productId") int productId,
			@FormParam("price") Double price, @FormParam("name") String name) {
		Product product = new Product(productId, price, name);
		productsMap.put(product.getId(), product);
		log.info("Person " + product.getName() + " successfully added.");
		
		String message = product.getName() + " adicionado com sucesso.";
		return message;
	}

	/**
	 * Remove um produto de ID especifico
	 * 
	 * @param id
	 * @return
	 */
	@Path("{productId}")
	@DELETE
	@Produces("application/json")
	public String removeProduct(@PathParam("productId") int id) {
		String name = productsMap.get(id).getName();
		productsMap.remove(id);
		log.info(name + " successfully removed.");
		
		String message = "Produto " + name + " removido com sucesso.";
		return message;
	}

}
