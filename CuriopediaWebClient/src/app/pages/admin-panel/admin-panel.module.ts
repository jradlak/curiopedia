import {CommonModule} from "@angular/common";
import {NgModule} from "@angular/core/src/metadata/ng_module";
import {RouterModule, Routes} from "@angular/router";
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {SharedModule} from "../../components-shared";
import {AdminPanelComponent} from "./admin-panel.component";


const routes: Routes = [
  {path: '', component: AdminPanelComponent},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    SharedModule,
    FormsModule, ReactiveFormsModule     
  ],
  declarations: [
    AdminPanelComponent
  ],  
  exports: [
    AdminPanelComponent,
  ]
})
export class AdminPanelModule {
}