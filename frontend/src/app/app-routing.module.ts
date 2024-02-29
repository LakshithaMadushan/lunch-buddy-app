import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./pages/home/home.component";
import {SessionComponent} from "./pages/session/session.component";
import {SignInComponent} from "./pages/sign-in/sign-in.component";

const routes: Routes = [
  {
    path: '', component: SignInComponent
  },
  {
    path: 'home', component: HomeComponent
  },
  {
    path: 'session/:sessionId', component: SessionComponent
  },
  {
    path: '**', redirectTo: '', component: SignInComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
