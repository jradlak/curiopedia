import {inject, TestBed} from "@angular/core/testing";
import {
  ResponseOptions,
  Response,
  BaseResponseOptions,
  RequestMethod,
  HttpModule
} from "@angular/http";
import {MockBackend} from "@angular/http/testing";
import {UserService} from "../user.service";
import {UserParams} from "../../dto";
import {APP_TEST_HTTP_PROVIDERS} from "../../../../testing";

//Uwaga! Jeśli pojawią się błędy przy korzystaniu z dummyListJson, wykonać: npm uninstall @types/jasmine; npm install @types/jasmine@2.5.45 

describe('UserService', () => {

  let userService: UserService;
  let backend: MockBackend;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpModule,
      ],
      providers: [
        APP_TEST_HTTP_PROVIDERS,
        UserService,
      ],
    });
  });
  beforeEach(inject([UserService, MockBackend], (..._) => {
    [userService, backend] = _;
  }));


  describe('.create', () => {
    it("create user", (done) => {
      const params: UserParams = {
        email: 'test1@test.com',
        password: 'secret',
        name: 'test1',
      };
      backend.connections.subscribe(conn => {
        conn.mockRespond(new Response(new BaseResponseOptions()));
        expect(conn.request.method).toEqual(RequestMethod.Post);
        expect(conn.request.url).toEqual('/api/users');
        expect(conn.request.json()).toEqual(params);
      });
      userService.create(params).subscribe(() => {
        done();
      });
    });
  }); // .create

});
