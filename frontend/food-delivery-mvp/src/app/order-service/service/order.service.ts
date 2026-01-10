
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { API_URL_Order } from '../../constants/url';

@Injectable({
  providedIn: 'root'
})

export class OrderService {

  private apiUrl = API_URL_Order+'/api/v1/order/saveOrder';

  constructor(private http: HttpClient) { }

 httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':'text/plain',
      'Access-Control-Allow-Origin': 'http://localhost:4200'
    })
  };

  saveOrder(data: any):Observable<any>  {
    return this.http.post<any>(this.apiUrl, data);
  }

  private handleError(error: any) {
    console.error('An error occurred:', error);
    return throwError(()=> new Error(`${error} : ${error.message || 'server error'}`));
  }


}
