import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CategorieService } from '../services/categorie.service';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent implements OnInit {

  categoryForm!:FormGroup;
actionBtn:string="Enregistrer";
titre:string="Ajouter une catégorie";
  constructor(private formBuilder: FormBuilder, private catS:CategorieService,@Inject(MAT_DIALOG_DATA) public editData:any, private dialogRef:MatDialogRef<DialogComponent>) { }

  ngOnInit(): void {
    this.categoryForm=this.formBuilder.group({
      nom:['', Validators.required],
      quantite:['', Validators.required],
      date_creation: null,
      date_modif:null
    });

    if (this.editData){
      this.actionBtn="Mettre à jour";
      this.titre="Mettre à jour une catégorie";
      this.categoryForm.controls['nom'].setValue(this.editData.nom);
      this.categoryForm.controls['quantite'].setValue(this.editData.quantite);
    }
  }

  ajouterCat(){
    if(!this.editData){
      if (this.categoryForm.valid){
      this.categoryForm.controls['date_creation'].setValue(new Date());
        this.catS.addCategory(this.categoryForm.value).subscribe({
          next:(res)=>{
            alert("Ajout effectué avec succès!");
            this.categoryForm.reset();
            this.dialogRef.close('save');
          },
          error:(err)=>{
            alert("Erreur lors de l'ajout!")
    
          }
        })}
    }
    else{
      if (this.categoryForm.valid){
      this.categoryForm.controls['date_creation'].setValue(this.editData.date_creation);
      this.categoryForm.controls['date_modif'].setValue(new Date());
      this.updateCat();
    }}
}

updateCat(){
  this.catS.updateCategory(this.categoryForm.value,this.editData.id).subscribe({
    next:(res)=>{
      alert("Modification effectuée avec succès!");
      this.categoryForm.reset();
      this.dialogRef.close('update');
    }
  })}
}


