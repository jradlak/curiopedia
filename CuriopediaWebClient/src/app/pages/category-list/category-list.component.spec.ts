import {Component, DebugElement} from "@angular/core";
import {By} from "@angular/platform-browser/src/dom/debug/by";
import {getDOM} from "@angular/platform-browser/src/dom/dom_adapter";
import {
  inject,
  fakeAsync,
  ComponentFixture,
  TestBed
} from "@angular/core/testing";
import {ResponseOptions, Response} from "@angular/http";
import {Router} from "@angular/router";
import {MockBackend} from "@angular/http/testing";
import {RouterTestingModule} from "@angular/router/testing";
import {CategoryListComponent} from "./category-list.component";
import {CategoryListModule} from "./category-list.module";
import {CoreModule} from "../../core";
import {APP_TEST_HTTP_PROVIDERS, advance} from "../../../testing";
import {PagerComponent} from "../../components-shared/pager/pager.component";

describe('CategoryListComponent', () => {

  @Component({
    template: `<router-outlet></router-outlet>`,
  })
  class TestComponent {
  }

  let cmpDebugElement: DebugElement;
  let pagerDebugElement: DebugElement;

  let router: Router;
  let backend: MockBackend;
  let fixture: ComponentFixture<any>;

  const dummyResponse = new Response(new ResponseOptions({
    body: JSON.stringify({
      content: [
        {name: 'test1', description: 'desc1'},
        {name: 'test2', description: 'desc2'},
      ],
      totalPages: 1,
      totalElements: 2,
    }),
  }));

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([
          {
            path: 'categories',
            component: CategoryListComponent,
          },
        ]),
        CoreModule,
        CategoryListModule,
      ],
      providers: [
        APP_TEST_HTTP_PROVIDERS,
      ],
      declarations: [
        TestComponent,
      ]
    });
  });
  beforeEach(inject([Router, MockBackend], (..._) => {
    [router, backend] = _;
    backend.connections.subscribe(conn => conn.mockRespond(dummyResponse));
  }));
  beforeEach(fakeAsync(() => {
    TestBed.compileComponents().then(() => {
      fixture = TestBed.createComponent(TestComponent);
      return router.navigate(['/categories']).then(() => {
        cmpDebugElement = fixture.debugElement.query(By.directive(CategoryListComponent));
        pagerDebugElement = cmpDebugElement.query(By.directive(PagerComponent));
        fixture.detectChanges();
      });
    });
  }));

  it('can be shown', () => {
    expect(cmpDebugElement).toBeTruthy();
    expect(pagerDebugElement).toBeTruthy();
  });

  it('can list categories', () => {
    const page: CategoryListComponent = cmpDebugElement.componentInstance;
    expect(page.categories.length).toEqual(2);
    expect(page.totalPages).toEqual(1);

    const el = cmpDebugElement.nativeElement;
    expect(getDOM().querySelectorAll(el, 'td')[0].innerText).toEqual('test1');
    expect(getDOM().querySelectorAll(el, 'td')[3].innerText).toEqual('test2');
    
    const pager: PagerComponent = pagerDebugElement.componentInstance;
    expect(pager.totalPages).toEqual(1);
  });

  it('list another page when page was changed', fakeAsync(() => {
    pagerDebugElement.triggerEventHandler('pageChanged', {page: 2});
    advance(fixture);
    cmpDebugElement = fixture.debugElement.query(By.directive(CategoryListComponent));
    const cmp: CategoryListComponent = cmpDebugElement.componentInstance;
    expect(cmp.page).toEqual(2);
  }));

});
