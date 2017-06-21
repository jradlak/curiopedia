import {CommonModule} from "@angular/common";
import {NgModule} from "@angular/core/src/metadata/ng_module";
import {TimeAgoPipe} from "./pipes";
import {StylesDirective} from "./directives/styles.directive";

@NgModule({
  imports: [
    CommonModule,
  ],
  declarations: [
    StylesDirective,
    TimeAgoPipe,
  ],
  exports: [    
    StylesDirective,
    TimeAgoPipe,
  ]
})
export class SharedModule {
}
