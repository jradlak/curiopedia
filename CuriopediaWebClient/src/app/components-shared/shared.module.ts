import {CommonModule} from "@angular/common";
import {NgModule} from "@angular/core/src/metadata/ng_module";
import {TimeAgoPipe} from "./pipes";
import {PagerComponent} from "./pager/pager.component";
import {ModalComponent} from "./modal/modal.component";
import {ModalService} from "./modal/modal.service";
import {StylesDirective} from "./directives/styles.directive";

@NgModule({
  imports: [
    CommonModule,
  ],
  declarations: [
    PagerComponent,    
    ModalComponent,
    StylesDirective,

    TimeAgoPipe,
  ],
  providers: [
    ModalService
  ],
  exports: [    
    PagerComponent,    
    StylesDirective,
    ModalComponent,
    TimeAgoPipe,
  ]
})
export class SharedModule {
}
