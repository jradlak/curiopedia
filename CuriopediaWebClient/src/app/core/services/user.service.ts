import {Observable} from "rxjs/Observable";
import {Injectable} from "@angular/core";
import {Response} from "@angular/http";
import {User} from "../domains";
import {objToSearchParams} from "./helpers";
import {PageRequest, Page, UserParams} from "../dto";
import {JsonHttp} from "./";

const url = '/api/users';
const defaultPageRequest: PageRequest = {page: 1, size: 5};

@Injectable()
export class UserService {

  constructor(private http: JsonHttp) {
  }

  list(pageRequest: PageRequest = defaultPageRequest): Observable<Page<User>> {
    return this.http.get(url, {search: objToSearchParams(pageRequest)})
      .map(res => res.json())
      ;
  }

  get(id: string|number): Observable<User> {    
    return this.http.get(`${url}/${id}`)
      .map(res => res.json())
      ;
  }

  create(params: UserParams): Observable<Response> {
    if (params.name === 'SuperAdmin') {
      params.authority = "ROLE_ADMIN";
    } else {
      params.authority = "ROLE_GUEST";
    }
    
    return this.http.post(url, params);
  }

  updateMe(userParam: UserParams): Observable<Response> {
    return this.http.patch(`${url}/me`, userParam);
  }

  update(userParam: UserParams): Observable<Response> {
    console.log(userParam);    
    return this.http.patch(`${url}/update`, userParam);
  }

  delete(userParam: UserParams): Observable<Response> {
    console.log(userParam);    
    return this.http.patch(`${url}/delete`, userParam);
  }
}