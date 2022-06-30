package CatProduits;

import CatProduits.Entities.Categorie;
import CatProduits.Entities.Produit;
import CatProduits.Repositories.CategorieRepository;
import CatProduits.Repositories.ProduitRepository;
import CatProduits.Services.CategorieService;
import CatProduits.Services.ProduitService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CategorieProduitsApplicationTests {

	@Autowired
	private CategorieService catServ;
	@Autowired
	private ProduitService prodServ;
	@MockBean
	private CategorieRepository catRep;
	@MockBean
	private ProduitRepository prodRep;

	@Test
	@DisplayName("Test lister toutes les catégories")
	void getAllCategoriesTest(){
		 when(catRep.findAll()).thenReturn(Stream.of(
				new Categorie("Animaux", 68, LocalDateTime.of(2021, 10, 18, 9, 26, 42), LocalDateTime.now()),
				new Categorie("Plantes", 34, LocalDateTime.of(2018, 04, 28, 11, 17, 03), LocalDateTime.now())).collect(Collectors.toList()));
		assertEquals(2, catServ.ListCategories().size());
	}

	@Test
	@DisplayName("Test lister tous les produits")
	void getAllProduitsTest(){
		when(prodRep.findAll()).thenReturn(Stream.of(
				new Produit("Vache", 90,true, LocalDateTime.of(2012, 06, 24, 13, 33, 56), LocalDateTime.now()),
				new Produit("Lion", 0,false, LocalDateTime.of(2014, 07, 11, 15, 23, 47), LocalDateTime.now()),
				new Produit("Tigre", 17,true, LocalDateTime.of(2010, 02, 06, 11, 27, 35), LocalDateTime.now())).collect(Collectors.toList()));
		assertEquals(3, prodServ.ListProduits().size());
	}

	@Test
	@DisplayName("Test chercher un produit par son ID")
	void findProduitByIDTest(){
		long id=1;
		Produit p =new Produit("Chat", 7,true, LocalDateTime.of(2017, 03, 10, 8, 21, 39), LocalDateTime.now());
		when(prodRep.findById(id)).thenReturn(Optional.of(p));
		prodServ.findProduitById(id);
	}

	@Test
	@DisplayName("Test chercher une catégorie par son ID")
	void findCategorieByIDTest(){
		long id=1;
		Categorie c =new Categorie("Animaux", 68, LocalDateTime.of(2021, 10, 18, 9, 26, 42), LocalDateTime.now());
		when(catRep.findById(id)).thenReturn(Optional.of(c));
		catServ.findCategorieById(id);
	}

	@Test
	@DisplayName("Test ajouter un nouveau produit")
	void ajouterProduitTest(){
	Produit p= new Produit("Chien", 0,false, LocalDateTime.of(2011, 01, 21, 15, 06, 22), LocalDateTime.now());
	when (prodRep.save(p)).thenReturn(p);
	assertEquals(p, prodServ.saveProduit(p));
	}
	@Test
	@DisplayName("Test ajouter une nouvelle catégorie")
	void ajouterCategorieTest(){
		Categorie c =new Categorie("Accessoires", 95, LocalDateTime.of(2022, 07, 23, 10, 53, 04), LocalDateTime.now());
		when (catRep.save(c)).thenReturn(c);
		assertEquals(c, catServ.saveCategorie(c));
	}
	@Test
	@DisplayName("Test mettre à jour un produit via son ID si ID trouvé")
	 void whenGivenId_shouldUpdateProduct_ifFound() {
		Produit p1 = new Produit("Lapin", 15,true, LocalDateTime.of(2002, 12, 03, 10, 06, 22), LocalDateTime.now());
		p1.setId(88L);
		p1.setDisponible(false);
		p1.setQuantite(0);

		Produit p2 = new Produit("Mouton", 0,false, LocalDateTime.of(2011, 01, 21, 15, 06, 22), LocalDateTime.now());
		p1.setDisponible(true);
		p1.setQuantite(126);

		when(prodRep.findById(p1.getId())).thenReturn(Optional.of(p1));
		prodServ.updateProduit(p1.getId(), p2);

		verify(prodRep).save(p2);
	}

	@Test
	@DisplayName("Test mettre à jour un produit via son ID si ID n'existe pas")
	public void should_throw_exception_when_product_doesnt_exist() {
		Produit old_p = new Produit();
		old_p.setId(44);
		old_p.setDisponible(true);
		old_p.setQuantite(80);

		Produit new_pp = new Produit();
		new_pp.setId(90);
		old_p.setQuantite(45);

		when(prodRep.findById(anyLong())).thenReturn(Optional.ofNullable(null));
		prodServ.updateProduit(old_p.getId(), new_pp);
	}

	@Test
	@DisplayName("Test mettre à jour une catégorie via son ID si ID trouvé")
	void whenGivenId_shouldUpdateCategory_ifFound() {
		Categorie c1 =new Categorie("Accessoires", 95, LocalDateTime.of(2020, 07, 23, 10, 53, 04), LocalDateTime.now());
		c1.setId(41L);
		c1.setQuantite(11);

		Categorie c2 =new Categorie("Animaux", 278, LocalDateTime.of(2011, 10, 18, 9, 26, 42), LocalDateTime.now());
		c1.setQuantite(126);

		when(catRep.findById(c1.getId())).thenReturn(Optional.of(c1));
		catServ.updateCategorie(c1.getId(), c2);

		verify(catRep).save(c2);
	}

	@Test
	@DisplayName("Test mettre à jour une catégorie via son ID si ID n'existe pas")
	public void should_throw_exception_when_category_doesnt_exist() {
		Categorie old_c = new Categorie();
		old_c.setId(202);
		old_c.setQuantite(101);

		Categorie new_cc = new Categorie();
		new_cc.setId(31);
		old_c.setQuantite(49);

		when(catRep.findById(anyLong())).thenReturn(Optional.ofNullable(null));
		catServ.updateCategorie(old_c.getId(), new_cc);
	}

	@Test
	@DisplayName("Test supprimer un produit via son ID si ID trouvé")
	 void whenGivenId_shouldDeleteProduct_ifFound(){
		Produit p = new Produit();
		p.setDisponible(false);
		p.setQuantite(0);
		p.setId(1L);

		when(prodRep.findById(p.getId())).thenReturn(Optional.of(p));

		prodServ.removeProduit(p.getId());
		verify(prodRep).deleteById(p.getId());
	}

	@Test
	@DisplayName("Test supprimer un produit via son ID si ID n'existe pas")
	public void should_throw_exception_when_productt_doesnt_exist() {
		Produit p = new Produit();
		p.setId(89L);
		p.setNom("Sacoche PC");

		when(prodRep.findById(anyLong())).thenReturn(Optional.ofNullable(null));
		prodServ.removeProduit(p.getId());
	}

	@Test
	@DisplayName("Test supprimer une catégorie via son ID si ID trouvé")
	void whenGivenId_shouldDeleteCategory_ifFound(){
		Categorie c = new Categorie();
		c.setQuantite(0);
		c.setId(5L);

		when(catRep.findById(c.getId())).thenReturn(Optional.of(c));

		catServ.removeCategorie(c.getId());
		verify(catRep).deleteById(c.getId());
	}

	@Test
	@DisplayName("Test supprimer une catégorie via son ID si ID n'existe pas")
	public void should_throw_exception_when_categoriee_doesnt_exist() {
		Categorie c = new Categorie();
		c.setId(89L);
		c.setNom("Téléphones portables");

		when(catRep.findById(anyLong())).thenReturn(Optional.ofNullable(null));
		catServ.removeCategorie(c.getId());
	}

}
