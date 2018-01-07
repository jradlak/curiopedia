import {inject, ComponentFixture, tick} from "@angular/core/testing";
import {AuthService} from "../app/core/services/auth.service";

export function login() {
  return inject([AuthService], (authService) => {
    spyOn(authService, 'isSignedIn').and.returnValue(true);
  });
}

export function loginAuthor() {
  return inject([AuthService], (authService) => {
    spyOn(authService, 'isAuthor').and.returnValue(true);
  });
}

export function advance(fixture: ComponentFixture<any>, millis?:number): void {
  tick(millis);
  fixture.detectChanges();
}