import {inject, TestBed} from "@angular/core/testing";
import {
  ResponseOptions,
  Response,  
  RequestMethod,
  HttpModule
} from "@angular/http";
import {MockBackend} from "@angular/http/testing";
import {AuthoritiesService} from "../authorities.service";
import {APP_TEST_HTTP_PROVIDERS} from "../../../../testing";

const dummyListJson = [{
      name : 'USER_ADMIN'      
}];

describe('AuthoritiesService', () => {
  let authoritiesService: AuthoritiesService;
  let backend: MockBackend;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpModule,
      ],
      providers: [
        APP_TEST_HTTP_PROVIDERS,
        AuthoritiesService,
      ],
    });
  });

  beforeEach(inject([AuthoritiesService, MockBackend], (..._) => {
    [authoritiesService, backend] = _;
  }));

describe('.list', () => {
    it("list authorities", (done) => {
      backend.connections.subscribe(conn => {
        conn.mockRespond(new Response(new ResponseOptions({
          body: JSON.stringify(dummyListJson),
        })));
        expect(conn.request.method).toEqual(RequestMethod.Get);
        expect(conn.request.url).toEqual('/api/users/authorities?page=1&size=5');
      });
      authoritiesService.list().subscribe(res => {
        expect(res).toEqual(dummyListJson);
        done();
      });
    });
  }); // .list

});
