import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TransaccionesService {
  private urlretirar='http://localhost:8080/transaccion/retiro';
  private urlhistorial='http://localhost:8080/transaccion';
  private urlcomprar = 'http://localhost:8080/transaccion/compra';
  private urlsaldo = 'http://localhost:8080/cuenta/saldo';
  private urldepositar = 'http://localhost:8080/transaccion/deposito';


  constructor(private httpClient: HttpClient) { }

  consultarSaldo(idCuenta: number): Observable<any>{
    const body = {idCuenta}
    console.log(body);
    return this.httpClient.post<any>(this.urlsaldo,body)
  }

  realizarRetiro(idCuenta: string, monto: number): Observable<any>{
    const body = {idCuenta, monto}
    console.log(body);
    return this.httpClient.post<any>(this.urlretirar,body)
  }

  realizarDeposito(idCuenta: string, monto: number, tipoDeposito: string): Observable<any>{
    const body = {idCuenta, monto, tipoDeposito}
    console.log(body);
    return this.httpClient.post<any>(this.urldepositar,body)
  }

  realizarCompra(idCuenta: string, monto: number, tipoCompra: string): Observable<any>{
    const body = {idCuenta, monto, tipoCompra}
    console.log(body);
    return this.httpClient.post<any>(this.urlcomprar,body)
  }

  realizarHistorial(idCuenta: string): Observable<any> {
    const body = { idCuenta };
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.httpClient.post<any>(this.urlhistorial, body, { headers });
  }
}
