import {Routes} from "@angular/router";
import {ProfileDataResolver} from "./core/services/profile-data.resolver";
import {AuthComponent} from "./pages/auth/auth.component";
import {TopComponent} from "./pages/top/top.component";
import {PrivatePageGuard} from "./core/services/private-page.guard";
import {PublicPageGuard} from "./core/services/public-page.guard";
import {NoContentComponent} from "./pages/no-content/no-content.component";

/* tslint:disable:max-line-length */
export const ROUTES: Routes = [  
  {
    path: 'users',
    loadChildren: './pages/user-list/user-list.module#UserListModule',
    canActivate: [PrivatePageGuard],
  },
  {path: 'login', component: AuthComponent, canActivate: [PublicPageGuard]},
  {
    path: 'signup',
    loadChildren: './pages/signup/signup.module#SignupModule',
    canActivate: [PublicPageGuard],
  },
  {
    path: 'help',
    loadChildren: './pages/help/help.module#HelpModule',
  },  
  {path: '', component: TopComponent, canActivate: [PublicPageGuard]},
  {path: '**', component: NoContentComponent}  
];
/* tslint:enable:max-line-length */
