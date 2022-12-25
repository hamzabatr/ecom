import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {NavbarComponent} from "./components/shared/navbar/navbar.component";
import {HomeComponent} from "./components/home/home.component";

const routes: Routes = [
  {
    path: '', component: NavbarComponent,
    children: [
      {path: 'home', component: HomeComponent}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
