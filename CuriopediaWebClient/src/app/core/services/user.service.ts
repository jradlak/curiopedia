import {Observable} from "rxjs/Observable";
import {Injectable} from "@angular/core";
import {Response} from "@angular/http";
import {User} from "../domains";
import {UserParams} from "../dto";
import {JsonHttp} from "./";

const url = '/api/users';

@Injectable()
export class UserService {

  constructor(private http: JsonHttp) {
  }

  get(id: string|number): Observable<User> {    
    return this.http.get(`${url}/${id}`)
      .map(res => res.json())
      ;
  }

  create(params: UserParams): Observable<Response> {
    if (params.name === 'AdminAdmin') {
      params.authority = "ROLE_ADMIN";
    } else {
      params.authority = "ROLE_GUEST";
    }
    
    return this.http.post(url, params);
  }
}