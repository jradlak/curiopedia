import {inject, TestBed} from "@angular/core/testing";
import {
  ResponseOptions,
  Response,
  BaseResponseOptions,
  RequestMethod,
  HttpModule
} from "@angular/http";
import {MockBackend} from "@angular/http/testing";
import {CategoryService} from "../category.service";
import {CategoryParams} from "../../dto";
import {APP_TEST_HTTP_PROVIDERS} from "../../../../testing";

const dummyListJson = [
  {
    name: 'category1',
    description: 'desc1',
  },
  {
    name: 'category2',
    description: 'desc2',
  },
];

describe('CategoryService', () => {

  let categoryService: CategoryService;
  let backend: MockBackend;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpModule,
      ],
      providers: [
        APP_TEST_HTTP_PROVIDERS,
        CategoryService,
      ],
    });
  });
  beforeEach(inject([CategoryService, MockBackend], (..._) => {
    [categoryService, backend] = _;
  }));

  describe('.list', () => {
    it("list categories", (done) => {
      backend.connections.subscribe(conn => {
        conn.mockRespond(new Response(new ResponseOptions({
          body: JSON.stringify(dummyListJson),
        })));
        expect(conn.request.method).toEqual(RequestMethod.Get);
        expect(conn.request.url).toEqual('/api/categories?page=1&size=5');
      });
      categoryService.list().subscribe(res => {
        expect(res).toEqual(dummyListJson);
        done();
      });
    });
  }); // .list

  describe('.create', () => {
    it("create category", (done) => {
      const params:  CategoryParams = {
        name: 'cat3',
        description: 'desc3'
      };
      backend.connections.subscribe(conn => {
        conn.mockRespond(new Response(new BaseResponseOptions()));
        expect(conn.request.method).toEqual(RequestMethod.Post);
        expect(conn.request.url).toEqual('/api/categories');
        expect(conn.request.json()).toEqual(params);
      });
      categoryService.create(params).subscribe(() => {
        done();
      });
    });
  }); // .create
});
