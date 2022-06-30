import { Component, OnInit, ViewChild } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import { CategorieService } from './services/categorie.service';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { BreakpointObserver } from '@angular/cdk/layout';
import { MatSidenav } from '@angular/material/sidenav';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})


export class AppComponent implements OnInit{
  title = 'Categories-Produits-abir';
 
  head = [['ID', 'NOM', 'QTE', 'DATE CREATION','DATE MODIFICATION']];
  
  displayedColumns: string[] = ['id', 'nom', 'quantite', 'date_creation', 'date_modif','action'];
  dataSource!: MatTableDataSource<any>;
 

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatSidenav) sidenav!: MatSidenav;

  constructor(private dialog:MatDialog, private dialog2:MatDialog, private catS:CategorieService, private observer: BreakpointObserver){

  }

  ngOnInit(): void {
    this.getAllCategories();
    
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

  ngAfterViewInit() {
  this.observer.observe(['(max-width: 800px)']).subscribe((res) => {
    if (res.matches) {
      this.sidenav.mode = 'over';
      this.sidenav.close();
    } else {
      this.sidenav.mode = 'side';
      this.sidenav.open();
    }
  });
}

}
