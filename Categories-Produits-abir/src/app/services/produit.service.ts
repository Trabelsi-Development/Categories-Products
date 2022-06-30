import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProduitService {

  constructor(private http: HttpClient) { }

  getProduits(){
    return this.http.get<any>('http://localhost:8080/Produit/GetAllProduits');
  }

  addProduct(data:any){
    let httpHeaders = new HttpHeaders({
      'Content-Type' : 'application/json',
      'Cache-Control': 'no-cache'
    });
   let data2={nom:data.nom,quantite:data.quantite,disponible:data.disponible,date_creation:data.date_creation,date_modif:data.date_modif,id_cat:{id:data.id_cat}};
   console.log(data2);
   return this.http.post<any>('http://localhost:8080/Produit/addProduit/',JSON.stringify(data2), {headers:httpHeaders});
  } 

  updateProduit(data:any, id:string){
    let data2={id:id,nom:data.nom,quantite:data.quantite,disponible:data.disponible,date_creation:data.date_creation,date_modif:data.date_modif};
    return this.http.put<any>('http://localhost:8080/Produit/updateProduit/'+id+'/'+data.id_cat,data2);
  }

  deleteProduit(id:string){
    return this.http.delete<any>('http://localhost:8080/Produit/deleteProduit/'+id);
  }
}
