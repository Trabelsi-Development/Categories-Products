import { Component, OnInit, ViewChild } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { jsPDF } from 'jspdf';
import 'jspdf-autotable';
import { CategorieService } from '../services/categorie.service';
import { DialogComponent } from '../dialog/dialog.component';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})
export class CategoriesComponent implements OnInit {
 
  head = [['ID', 'NOM', 'QTE', 'DATE CREATION','DATE MODIFICATION']];
  
  displayedColumns: string[] = ['id', 'nom', 'quantite', 'date_creation', 'date_modif','action'];
  dataSource!: MatTableDataSource<any>;
 

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private dialog:MatDialog, private catS:CategorieService){

  }
  ngOnInit(): void {
    this.getAllCategories();
    
  }

  openDialog() {
    const dialogRef = this.dialog.open(DialogComponent,{
      width:'30%'
    }).afterClosed().subscribe(val=>{
      if (val==='save'){
        this.getAllCategories();
      }
    })
  }

  getAllCategories(){
    this.catS.getCategories().subscribe({
      next:(res)=>{
       this.dataSource=new MatTableDataSource(res);
       this.dataSource.paginator=this.paginator;
       this.dataSource.sort=this.sort;
      },
      error:(err)=>{
        alert("Erreur lors du chargement des catégories!")

      }
    })

  }
 editCategory(row:any){
this.dialog.open(DialogComponent,{
  width:'30%',
  data:row
}).afterClosed().subscribe(val=>{
  if (val==='update'){
    this.getAllCategories();
  }
})
 }

  deleteCat(id:string){
    this.catS.deleteCategory(id).subscribe({
      next:(res)=>{
        alert("Catégorie supprimée avec succès!");
        this.getAllCategories();
      },error:(err)=>{
        alert("Erreur lors de la suppression!")

      }

    })
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  exportDataToPDF() {
  
    var data2:string[][] = new Array(); 

    for (let i= 0; i < this.dataSource.filteredData.length; i++) {
      var data:string[] = new Array();
     data.push(this.dataSource.filteredData[i].id,this.dataSource.filteredData[i].nom,this.dataSource.filteredData[i].quantite,this.dataSource.filteredData[i].date_creation,this.dataSource.filteredData[i].date_modif)
    data2.push(data);
   
}
    var img = new Image()
    img.src = '../assets/images/trc.png';

    const unit = "pt";
    const size = "A4"; // Use A1, A2, A3 or A4
    const orientation = "portrait"; // portrait or landscape
    var date = new Date().toLocaleString()
    var doc = new jsPDF(orientation, unit, size);
    doc.setFontSize(15);
    const title = "La liste des catégories";
    var xOffset = (doc.internal.pageSize.width / 2) - (doc.getStringUnitWidth(title) * doc.getFontSize() / 2);
        
    doc.addImage(img, 'PNG', 25, 13, 80, 65);
    doc.setFont('helvetica');
    doc.setTextColor(52, 124, 187);
    doc.text(title, xOffset, 50);
    doc.setTextColor(0, 0, 0);
    doc.setFontSize(12)
    doc.text(date, 460, 20);

  (doc as any).autoTable({
    startY: 80,
    head: this.head,
    body: data2,
    theme: 'striped'
  })

  // below line for Download PDF document  
  doc.save('Catégories.pdf');

}


}
