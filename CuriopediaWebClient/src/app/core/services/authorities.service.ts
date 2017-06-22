import {Observable} from "rxjs/Observable";
import {Injectable} from "@angular/core";
//import {Response} from "@angular/http";
import {objToSearchParams} from "./helpers";
import {PageRequest} from "../dto";
import {Authority} from "../domains";

import {JsonHttp} from "./";

const url = '/api/users/authorities';
const defaultPageRequest: PageRequest = {page: 1, size: 5};

@Injectable()
export class AuthoritiesService {
  constructor (private http: JsonHttp) {
    console.log(url);
  }

  list(pageRequest: PageRequest = defaultPageRequest): Observable<Authority[]> {
    return this.http.get(url, {search: objToSearchParams(pageRequest)})
      .map(res => res.json())
      ;
  }
}