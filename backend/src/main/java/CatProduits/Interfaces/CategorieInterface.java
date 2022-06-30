package CatProduits.Interfaces;

import CatProduits.Entities.Categorie;

import java.util.List;
import java.util.Optional;

public interface CategorieInterface {

    public Categorie saveCategorie (Categorie c);
    public Categorie updateCategorie (long id, Categorie c);
    public List<Categorie> ListCategories();
    public void removeCategorie (long id);
    public Optional<Categorie> findCategorieById(long id);
}
