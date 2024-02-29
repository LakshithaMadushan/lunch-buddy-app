import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './pages/home/home.component';
import {SessionComponent} from './pages/session/session.component';
import {SignInComponent} from './pages/sign-in/sign-in.component';
import {InvitationComponent} from "./components/invitation/invitation.component";
import {LoggedUserComponent} from "./components/logged-user/logged-user.component";
import {AddMembersComponent} from "./components/add-members/add-members.component";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {SectionCardComponent} from "./components/section-card/section-card.component";
import {LoaderComponent} from "./components/loader/loader.component";
import {CustomInterceptor} from "./services/custom.interceptor";
import { NotificationComponent } from './components/notification/notification.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    InvitationComponent,
    LoggedUserComponent,
    SignInComponent,
    SessionComponent,
    SectionCardComponent,
    AddMembersComponent,
    LoaderComponent,
    NotificationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: CustomInterceptor,
    multi: true,
  },],
  bootstrap: [AppComponent]
})
export class AppModule {
}
