package CatProduits.Entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

    @Column(nullable=false)
    private String nom;

    @Column(nullable=false)
    private int quantite;

    @Column(nullable=false)
    private boolean disponible;

    @Column(nullable=false)
    private LocalDateTime date_creation;

    private LocalDateTime date_modif;

    @ManyToOne
    @JoinColumn(name="id_Categorie")
    private Categorie id_cat;//un produit n'appartient qu'à une seule catégorie

    public Produit(String nom, int qte, boolean disp, LocalDateTime dc, LocalDateTime dm) {
        super();
        this.nom = nom;
        this.quantite = qte;
        this.disponible=disp;
        this.date_creation=dc;
        this.date_modif=dm;
    }
}
