package CatProduits.Controllers;

import CatProduits.Entities.Categorie;
import CatProduits.Interfaces.CategorieInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/Categorie")
public class CategorieController {
    @Autowired
    private CategorieInterface IC;

    @PostMapping("/addCategorie")
    public void addCategorie(@Validated @RequestBody Categorie c)
    {IC.saveCategorie(c);
    }

    @PutMapping("/updateCategorie/{id}")
    public void updateCategorie(@PathVariable (value="id") long id, @Validated @RequestBody Categorie newCat)
    {
        Optional<Categorie> oldCat=IC.findCategorieById(newCat.getId());
        System.out.println(newCat.getId());
        if (oldCat.isPresent())
        { Categorie cc =oldCat.get();
        cc.setNom(newCat.getNom());
        cc.setQuantite(newCat.getQuantite());
        cc.setDate_creation(newCat.getDate_creation());
        cc.setDate_modif(newCat.getDate_modif());
        IC.updateCategorie(id,cc);}

    }

    @GetMapping (value="/findCategorieById/{id}")
    public Optional <Categorie> findCategorieById(@PathVariable (value="id")String id)
    {return IC.findCategorieById(Long.parseLong(id));
    }

    @GetMapping ("/GetAllCategories")
    public List<Categorie> getAllCategories(){
        return IC.ListCategories();
    }

    @DeleteMapping (value="/deleteCategory/{id}")
    public void deleteCategorie(@PathVariable String id)
    {IC.removeCategorie(Long.parseLong(id));
    }

}
