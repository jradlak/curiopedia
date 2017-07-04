import {CommonModule} from "@angular/common";
import {NgModule} from "@angular/core/src/metadata/ng_module";
import {RouterModule, Routes} from "@angular/router";
import {SharedModule} from "../../components-shared";
import {CategoryListComponent} from "./category-list.component";

const routes: Routes = [
  {path: '', component: CategoryListComponent},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    SharedModule,
  ],
  declarations: [
    CategoryListComponent,
  ],
  exports: [
    CategoryListComponent,
  ]
})
export class CategoryListModule {
}
