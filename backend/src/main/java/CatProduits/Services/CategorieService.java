package CatProduits.Services;

import CatProduits.Entities.Categorie;
import CatProduits.Interfaces.CategorieInterface;
import CatProduits.Repositories.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service  ("CategorieService")
public class CategorieService implements CategorieInterface {

    @Autowired//pour autoriser la relation entre cette classe et l'interface repository
    CategorieRepository catRep;

    @Override
    public Categorie saveCategorie (Categorie cat) {
        return (catRep.save(cat));
    }

    @Override
    public Categorie updateCategorie(long id, Categorie cat) {
        return (catRep.save(cat));

    }

    @Override
    public List<Categorie> ListCategories() {
        return catRep.findAll();
    }

    @Override
    public void removeCategorie(long id) {

        catRep.deleteById(id);

    }

    @Override
    public Optional<Categorie> findCategorieById(long id) {
        return catRep.findById(id);
    }

}
