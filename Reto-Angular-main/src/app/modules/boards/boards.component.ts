import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TransaccionesService } from '../../services/transacciones.service';

@Component({
  selector: 'app-boards',
  templateUrl: './boards.component.html',
  styleUrls: ['./boards.component.css']
})
export class BoardsComponent {
  inputValue: string = '';
  openFormularioSaldo: boolean = false;
  openFormularioRetiro: boolean = false;
  openFormularioDeposito: boolean = false;
  openFormularioCompra: boolean = false;
  OpenHistorial: boolean = false;
  formularioSaldo: FormGroup;
  formularioDeposito: FormGroup;
  formularioCompra: FormGroup;
  formularioRetiro: FormGroup;
  results: any[] = [];
  opcionSeleccionada: string = '';
  opcionesDeposito: string[] = ['SUCURSAL', 'CAJERO_AUTOMATICO', 'OTRA_CUENTA'];
  opcionesCompra: string[] = ['ESTABLECIMIENTO_FISICO', 'PAGINA_WEB'];
  modalTitle: string = '';
  modalMessage: string = '';
  openModalFlag: boolean = false;

  constructor(private fb: FormBuilder, private transaccionesServices: TransaccionesService) {

    this.formularioSaldo = this.fb.group({
      idCuenta: [''],
    });

    this.formularioRetiro = this.fb.group({
      idCuenta: [''],
      monto: ['']
    });

    this.formularioDeposito = this.fb.group({
      idCuenta: [''],
      monto: [''],
      tipoDeposito: ['', Validators.required]
    });

    this.formularioCompra = this.fb.group({
      idCuenta: [''],
      monto: [''],
      tipoCompra: ['']
    });
  }

  // Mostrar formularios
  mostrarFormularioSaldo() {
    this.openFormularioSaldo = true;
    this.openFormularioRetiro = false;
    this.openFormularioDeposito = false;
    this.openFormularioCompra = false;
  }

  mostrarFormularioRetiro() {
    this.openFormularioSaldo = false;
    this.openFormularioRetiro = true;
    this.openFormularioDeposito = false;
    this.openFormularioCompra = false;
  }

  mostrarFormularioDeposito() {
    this.openFormularioSaldo = false;
    this.openFormularioRetiro = false;
    this.openFormularioDeposito = true;
    this.openFormularioCompra = false;
  }

  mostrarFormularioCompra() {
    this.openFormularioSaldo = false;
    this.openFormularioRetiro = false;
    this.openFormularioDeposito = false;
    this.openFormularioCompra = true;
  }

  mostrarHistorico() {
    this.OpenHistorial = true;
    this.openFormularioSaldo = false;
    this.openFormularioRetiro = false;
    this.openFormularioDeposito = false;
    this.openFormularioCompra = false;
  }

  // Función para cerrar todos los formularios
  cancelModal() {
    this.openFormularioSaldo = false;
    this.openFormularioRetiro = false;
    this.openFormularioDeposito = false;
    this.openFormularioCompra = false;
    this.OpenHistorial = false;
  }

  openModal(title: string, message: string) {
    this.modalTitle = title;
    this.modalMessage = message;
    this.openModalFlag = true;
  }

  closeModal() {
    this.openModalFlag = false;
    this.modalTitle = '';
    this.modalMessage = '';
  }

  // Enviar formulario de consulta saldo
  enviarFormularioSaldo() {
    console.log('Datos del formulario 1:', this.formularioSaldo.value);
    const { idCuenta } = this.formularioSaldo.value;
    this.Saldo(idCuenta);
    this.cancelModal();
  }

  // Enviar formulario de Retiro
  enviarFormularioRetiro() {
    console.log('Datos del formulario 2:', this.formularioRetiro.value);
    const { idCuenta, monto } = this.formularioRetiro.value;
    this.retirar(idCuenta, monto);
    this.cancelModal();
  }

  // Enviar formulario de depósito
  enviarFormularioDeposito() {
    console.log('Datos del formulario de depósito:', this.formularioDeposito.value);
    const { idCuenta, monto, tipoDeposito } = this.formularioDeposito.value;
    this.depositar(idCuenta, monto, tipoDeposito);
    this.cancelModal();
  }

  // Enviar formulario compra
  enviarFormularioCompra() {
    console.log('Datos del formulario de compra:', this.formularioCompra.value);
    const { idCuenta, monto, tipoCompra } = this.formularioCompra.value;
    this.comprar(idCuenta, monto, tipoCompra);
    this.cancelModal();
  }

  // Obtener saldo de cuenta
  Saldo(idCuenta: number): void {
    this.transaccionesServices.consultarSaldo(idCuenta).subscribe((data) => {
      this.openModal('Consulta de saldo', `Cuenta: ${data.nroCuenta}, Saldo disponible: ${data.saldo}`);
    }, (error) => {
      this.openModal('Error', 'Hubo un error al consultar el saldo.');
    });
  }

  // Realizar un retiro
  retirar(idCuenta: number, monto: number): void {
    this.transaccionesServices.realizarRetiro(idCuenta, monto).subscribe((data) => {
      this.openModal(
        'Retiro realizado',
        `Cuenta: ${data.cuentaId}, Valor Transacción: ${data.valor}, Nuevo saldo: ${data.saldoCuenta}`
      );
    }, (error) => {
      this.openModal('Error', 'Hubo un error al realizar el retiro.');
    });
  }

  // Realizar un depósito
  depositar(idCuenta: number, monto: number, tipoDeposito: string): void {
    this.transaccionesServices.realizarDeposito(idCuenta, monto, tipoDeposito).subscribe((data) => {
      this.openModal(
        'Depósito realizado',
        `Cuenta: ${data.cuentaId}, Valor Transacción: ${data.valor}, Nuevo saldo: ${data.saldoCuenta}`
      );
    }, (error) => {
      this.openModal('Error', 'Hubo un error al realizar el depósito.');
    });
  }

  // Realizar una compra
  comprar(idCuenta: number, monto: number, tipoCompra: string): void {
    this.transaccionesServices.realizarCompra(idCuenta, monto, tipoCompra).subscribe((data) => {
      this.openModal(
        'Compra realizada',
        `Cuenta: ${data.cuentaId}, Valor Transacción: ${data.valor}, Nuevo saldo: ${data.saldoCuenta}`
      );
    }, (error) => {
      this.openModal('Error', 'Hubo un error al realizar la compra.');
    });
  }

  // Consultar historial de transacciones
  historico(idCuenta: string): void {
    this.transaccionesServices.realizarHistorial(idCuenta).subscribe((data) => {
      this.results = data;
      console.log('Historial de transacciones:', this.results);
    });
  }
}
