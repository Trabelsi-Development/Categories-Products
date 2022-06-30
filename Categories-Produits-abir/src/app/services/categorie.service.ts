import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CategorieService {

  constructor(private http: HttpClient) { }

  getCategories(){
    return this.http.get<any>('http://localhost:8080/Categorie/GetAllCategories');
  }

  addCategory(data:any){
    let httpHeaders = new HttpHeaders({
      'Content-Type' : 'application/json',
      'Cache-Control': 'no-cache'
    });
    return this.http.post<any>('http://localhost:8080/Categorie/addCategorie',JSON.stringify(data), {headers:httpHeaders});
  } 

  updateCategory(data:any, id:string){
    data.id=id
    return this.http.put<any>('http://localhost:8080/Categorie/updateCategorie/'+id,data);
  }

  deleteCategory(id:string){
    return this.http.delete<any>('http://localhost:8080/Categorie/deleteCategory/'+id);
  }
  
}
