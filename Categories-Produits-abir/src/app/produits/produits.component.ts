import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { jsPDF } from 'jspdf';
import 'jspdf-autotable';
import { ProduitService } from '../services/produit.service';
import { DialogProduitComponent } from '../dialog-produit/dialog-produit.component';

@Component({
  selector: 'app-produits',
  templateUrl: './produits.component.html',
  styleUrls: ['./produits.component.css']
})
export class ProduitsComponent implements OnInit {
 
  head = [['ID', 'NOM', 'QTE','DISPONIBILITE', 'DATE CREATION','DATE MODIFICATION','ID_CAT']];
  
  displayedColumns: string[] = ['id', 'nom', 'quantite','disponible', 'date_creation', 'date_modif','id_cat','action'];
  dataSource!: MatTableDataSource<any>;
 

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  
  constructor(private dialog2:MatDialog, private catP:ProduitService){

  }
  ngOnInit(): void {
    this.getAllProduits();
    
  }

  openDialog() {
    const dialogRef = this.dialog2.open(DialogProduitComponent,{
      width:'30%'
    }).afterClosed().subscribe(val=>{
      if (val==='save'){
        this.getAllProduits();
      }
    })
  }

  getAllProduits(){
    this.catP.getProduits().subscribe({
      next:(res)=>{
       this.dataSource=new MatTableDataSource(res);
       console.log()
       this.dataSource.paginator=this.paginator;
       this.dataSource.sort=this.sort;
      },
      error:(err)=>{
        alert("Erreur lors du chargement des produits!")

      }
    })

  }

  editProduit(row:any){
    this.dialog2.open(DialogProduitComponent,{
      width:'30%',
      data:row
    }).afterClosed().subscribe(val=>{
      if (val==='update'){
        this.getAllProduits();
      }
    })
     }

  deleteProd(id:string){
    this.catP.deleteProduit(id).subscribe({
      next:(res)=>{
        alert("Produit supprimé avec succès!");
        this.getAllProduits();
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
      if(this.dataSource.filteredData[i].disponible===true){
     data.push(this.dataSource.filteredData[i].id,this.dataSource.filteredData[i].nom,this.dataSource.filteredData[i].quantite,"Oui",this.dataSource.filteredData[i].date_creation,this.dataSource.filteredData[i].date_modif,this.dataSource.filteredData[i].id_cat.id)
      }
      else { data.push(this.dataSource.filteredData[i].id,this.dataSource.filteredData[i].nom,this.dataSource.filteredData[i].quantite,"Non",this.dataSource.filteredData[i].date_creation,this.dataSource.filteredData[i].date_modif,this.dataSource.filteredData[i].id_cat.id)
    }
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
    const title = "La liste des produits";
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
  doc.save('Produits.pdf');

}
}
