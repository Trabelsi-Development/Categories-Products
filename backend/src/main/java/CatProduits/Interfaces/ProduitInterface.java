package CatProduits.Interfaces;

import CatProduits.Entities.Produit;

import java.util.List;
import java.util.Optional;

public interface ProduitInterface {

    public Produit saveProduit (Produit p);
    public Produit updateProduit (long id, Produit p);
    public List<Produit> ListProduits();
    public void removeProduit (long id);
    public Optional<Produit> findProduitById(long id);
}
