package CatProduits.Controllers;

import CatProduits.Entities.Categorie;
import CatProduits.Entities.Produit;
import CatProduits.Interfaces.CategorieInterface;
import CatProduits.Interfaces.ProduitInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/Produit")
public class ProduitController {

    @Autowired
    private ProduitInterface IP;

    @Autowired
    private CategorieInterface IC;

    @PostMapping("/addProduit")
    public void addProduit(@Validated @RequestBody Produit p)
    {IP.saveProduit(p);
    }

    @PutMapping("/updateProduit/{idProd}/{idCat}")
    public void updateProduit(@PathVariable (value="idProd") long id,@PathVariable (value="idCat") long idC, @Validated @RequestBody Produit newProduit)
    {
        Optional<Produit> oldProd=IP.findProduitById(newProduit.getId());
        if (oldProd.isPresent())
        {Produit pp =oldProd.get();
        pp.setNom(newProduit.getNom());
        pp.setQuantite(newProduit.getQuantite());
        pp.setDate_creation(newProduit.getDate_creation());
        pp.setDate_modif(newProduit.getDate_modif());
        if (newProduit.isDisponible())
            pp.setDisponible(true);
        else pp.setDisponible(false);
        Optional <Categorie> cat=IC.findCategorieById(idC);
        Categorie c=cat.get();
        pp.setId_cat(c);
        IP.updateProduit(id,pp);}

    }

    @GetMapping (value="/findProduitById/{id}")
    public Optional <Produit> findProduitById(@PathVariable (value="id")String id)
    {return IP.findProduitById(Long.parseLong(id));
    }


    @GetMapping ("/GetAllProduits")
    public List<Produit> getAllProduits(){
        return IP.ListProduits();
    }

    @DeleteMapping (value="/deleteProduit/{id}")
    public void deleteProduit(@PathVariable String id)
    {IP.removeProduit(Long.parseLong(id));
    }

}
