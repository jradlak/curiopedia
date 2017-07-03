import {Observable} from "rxjs/Observable";
import {Injectable} from "@angular/core";
import {Response} from "@angular/http";
import {objToSearchParams} from "./helpers";
import {PageRequest, Page, CategoryParams} from "../dto";
import {Category} from "../domains";

import {JsonHttp} from "./";

const url = '/api/categories';
const defaultPageRequest: PageRequest = {page: 1, size: 5};

@Injectable()
export class CategoryService {
  constructor (private http: JsonHttp) {
    console.log(url);
  }

  list(pageRequest: PageRequest = defaultPageRequest): Observable<Page<Category>> {
    return this.http.get(url, {search: objToSearchParams(pageRequest)})
      .map(res => res.json())
      ;
  }

  create(params: CategoryParams): Observable<Response> {    
    return this.http.post(url, params);
  }
}