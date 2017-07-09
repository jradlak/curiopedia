import {CommonModule} from "@angular/common";
import {NgModule} from "@angular/core/src/metadata/ng_module";
import {RouterModule, Routes} from "@angular/router";
import {SharedModule} from "../../components-shared";
import {CategoryEditComponent} from "./category-edit.component";

const routes: Routes = [
  {path: '', component: CategoryEditComponent},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    SharedModule,
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
