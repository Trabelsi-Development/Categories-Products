package CatProduits.Services;

import CatProduits.Entities.Produit;
import CatProduits.Interfaces.ProduitInterface;
import CatProduits.Repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service ("ProduitService")
public class ProduitService implements ProduitInterface {


    @Autowired//pour autoriser la relation entre cette classe et l'interface repository
    ProduitRepository prodRep;

    @Override
    public Produit saveProduit (Produit prod) {
        return (prodRep.save(prod));

    }

    @Override
    public Produit updateProduit(long id,Produit prod) {
        return (prodRep.save(prod));

    }

    @Override
    public List<Produit> ListProduits() {
        return prodRep.findAll();
    }

    @Override
    public void removeProduit(long Id) {
        prodRep.deleteById(Id);

    }

    @Override
    public Optional<Produit> findProduitById(long id) {
        return prodRep.findById(id);
    }


}
