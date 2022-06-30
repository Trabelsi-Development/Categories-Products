import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CategorieService } from '../services/categorie.service';
import { ProduitService } from '../services/produit.service';

@Component({
  selector: 'app-dialog-produit',
  templateUrl: './dialog-produit.component.html',
  styleUrls: ['./dialog-produit.component.css']
})
export class DialogProduitComponent implements OnInit {
  
  categories:any[] = new Array();
  productForm!:FormGroup;

  actionBtn:string="Enregistrer";
  titre:string="Ajouter un produit";

  constructor(private formBuilder: FormBuilder,private catS:CategorieService,private catP:ProduitService, @Inject(MAT_DIALOG_DATA) public editData:any, private dialogRef:MatDialogRef<DialogProduitComponent>) { }

  ngOnInit(): void {
    this.getAllCategories();
   
    this.productForm=this.formBuilder.group({
      nom:['', Validators.required],
      quantite:['', Validators.required],
      disponible:['', Validators.required],
      id_cat:['', Validators.required],
      date_creation: null,
      date_modif:null
    });

    if (this.editData){
      this.actionBtn="Mettre à jour";
      this.titre="Mettre à jour un produit";
      this.productForm.controls['nom'].setValue(this.editData.nom);
      this.productForm.controls['quantite'].setValue(this.editData.quantite);
      this.productForm.controls['disponible'].setValue(this.editData.disponible.toString());
      this.productForm.controls['id_cat'].setValue(this.editData.id_cat.id);
    }
  }

  getAllCategories(){
    this.catS.getCategories().subscribe({
      next:(res)=>{
       this.categories=res;
      },
      error:(err)=>{
        alert("Erreur lors du chargement des catégories!")
      }
    })

  }

  ajouterProd(){
    if(!this.editData){
      if (this.productForm.valid){
      this.productForm.controls['date_creation'].setValue(new Date());
        this.catP.addProduct(this.productForm.value).subscribe({
          next:(res)=>{
            alert("Ajout effectué avec succès!");
            this.productForm.reset();
            this.dialogRef.close('save');
          },
          error:(err)=>{
            alert("Erreur lors de l'ajout!")
    
          }
        })}
    }
    else{
      if (this.productForm.valid){
      this.productForm.controls['date_creation'].setValue(this.editData.date_creation);
      this.productForm.controls['date_modif'].setValue(new Date());
      this.updateProd();
    }}
}

updateProd(){
  this.catP.updateProduit(this.productForm.value,this.editData.id).subscribe({
    next:(res)=>{
      alert("Modification effectuée avec succès!");
      this.productForm.reset();
      this.dialogRef.close('update');
    }
  })}
}

