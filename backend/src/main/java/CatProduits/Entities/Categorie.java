package CatProduits.Entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

    @Column(nullable=false)
    private String nom;

    @Column(nullable=false)
    private int quantite;

    @Column(nullable=false)
    private LocalDateTime date_creation;

    private LocalDateTime date_modif;

    @OneToMany(mappedBy = "id_cat", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private List<Produit> produits;//une catégorie a plusieurs produits
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id= id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getQuantite() {
        return quantite;
    }
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public LocalDateTime getDate_creation() {
        return date_creation;
    }
    public void setDate_creation(LocalDateTime dc) {
        this.date_creation = dc;
    }

    public LocalDateTime getDate_modif() {
        return date_modif;
    }
    public void setDate_modif(LocalDateTime dm) {
        this.date_modif = dm;
    }

    public Categorie(String nom, int qte, LocalDateTime dc, LocalDateTime dm) {
        super();
        this.nom = nom;
        this.quantite = qte;
        this.date_creation=dc;
        this.date_modif=dm;
    }
}
