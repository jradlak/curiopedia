import {CommonModule} from "@angular/common";
import {NgModule} from "@angular/core/src/metadata/ng_module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {RouterModule, Routes} from "@angular/router";
import {CategoryEditComponent} from "./category-edit.component";

const routes: Routes = [
  {path: '', component: CategoryEditComponent},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),    
    FormsModule,
    ReactiveFormsModule,
  ],
  declarations: [
    CategoryEditComponent,
  ],
  exports: [
    CategoryEditComponent,
  ]
})
export class CategoryEditModule {
}
