import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthComponent } from './components/auth/auth.component';
import { BoardsComponent } from '../boards/boards.component';
import { authGuard } from 'src/app/auth.guard';

const routes: Routes = [
  {
    path: '',
    component:AuthComponent,
  },
    {
    path: 'boards',
    component:BoardsComponent,
    canActivate: [authGuard]
    }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
