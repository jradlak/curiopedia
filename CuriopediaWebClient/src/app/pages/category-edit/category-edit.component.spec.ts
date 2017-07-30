//import {Observable} from "rxjs/Observable";
import {Component, DebugElement} from "@angular/core";
import {inject, TestBed, fakeAsync} from "@angular/core/testing";
import {Router} from "@angular/router";
import {By} from "@angular/platform-browser/src/dom/debug/by";
import {getDOM} from "@angular/platform-browser/src/dom/dom_adapter";
import {MockBackend} from "@angular/http/testing";
import {CategoryEditComponent} from "./category-edit.component";
import {RouterTestingModule} from "@angular/router/testing";
import {CategoryEditModule} from "./category-edit.module";
import {CategoryService} from "../../core/services/category.service";
//import {Category} from "../../core/domains";
import {CoreModule} from "../../core";
import {APP_TEST_HTTP_PROVIDERS} from "../../../testing";

describe('CategoryEditComponent', () => {

  @Component({
    template: `<router-outlet></router-outlet>`,
  })
  class TestComponent {
  }

  let cmpDebugElement: DebugElement;

  let categoryService: CategoryService;
  let router: Router;
  let backend: MockBackend;

/*
  const category: Category = {    
    name: "Category1",
    description: "Category1 description"    
  };
*/

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([
          {
            path: 'categories/edit',
            component: CategoryEditComponent            
          },
        ]),
        CoreModule,
        CategoryEditModule,
      ],
      providers: [
        APP_TEST_HTTP_PROVIDERS,
      ],
      declarations: [
        TestComponent,
      ]
    });
  });
  beforeEach(inject([CategoryService, Router, MockBackend], (..._) => {    
    [categoryService, router, backend] = _;    
  }));
  beforeEach(fakeAsync(() => {
    TestBed.compileComponents().then(() => {
      const fixture = TestBed.createComponent(TestComponent);
      return router.navigate(['/categories', 'edit']).then(() => {
        fixture.detectChanges();
        cmpDebugElement = fixture.debugElement.query(By.directive(CategoryEditComponent));
      });
    });
  }));

  it('can be shown', () => {
    expect(cmpDebugElement).toBeTruthy();
    //const cmp: CategoryEditComponent = cmpDebugElement.componentInstance;
    //expect(cmp.category).toEqual(category);

    const el = cmpDebugElement.nativeElement;
    const nameInput = <HTMLInputElement>getDOM().querySelector(el, '#nameInput');
    expect(nameInput.value).toEqual('');

    const descriptionInput = <HTMLInputElement>getDOM().querySelector(el, '#descriptionInput');
    expect(descriptionInput.value).toEqual('');    
  });
});