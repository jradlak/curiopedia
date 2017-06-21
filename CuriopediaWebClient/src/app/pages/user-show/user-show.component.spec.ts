import {Component, DebugElement} from "@angular/core";
import {By} from "@angular/platform-browser/src/dom/debug/by";
import {inject, TestBed, fakeAsync} from "@angular/core/testing";
import {Router} from "@angular/router";
import {RouterTestingModule} from "@angular/router/testing";
import {UserShowComponent} from "./user-show.component";
import {CoreModule} from "../../core";
import {UserShowModule} from "./user-show.module";
import {APP_TEST_HTTP_PROVIDERS} from "../../../testing";


describe('UserShowComponent', () => {

  @Component({
    template: `<router-outlet></router-outlet>`,
  })
  class TestComponent {
  }

  let cmpDebugElement: DebugElement;

  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([
          {path: 'users/:id', component: UserShowComponent},
        ]),
        CoreModule,
        UserShowModule,
      ],
      providers: [
        APP_TEST_HTTP_PROVIDERS,
      ],
      declarations: [
        TestComponent,
      ]
    });
  });
  beforeEach(inject([Router], (..._) => [router] = _));
  beforeEach(fakeAsync(() => {
    TestBed.compileComponents().then(() => {
      const fixture = TestBed.createComponent(TestComponent);
      return router.navigate(['/users', '1']).then(() => {
        fixture.detectChanges();
        cmpDebugElement = fixture.debugElement.query(By.directive(UserShowComponent)); 
      });
    }).catch(e => console.log(e));
  }));

  it('can be shown', () => {
    expect(cmpDebugElement).toBeTruthy();
  });
});
