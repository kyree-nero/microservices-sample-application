import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Response, Http, Headers, RequestOptions} from "@angular/http";
import {Book} from "./book";
import {Rating} from "./rating";
 
@Injectable()
export class HttpService {
 
    constructor(private http: Http) { }
 
    me(): Observable<Response> {
        return this.http.get("/me", this.makeOptions())
    }
 
    logout(): Observable<Response> {
        return this.http.post("/logout", '', this.makeOptions())
    }
 
    private makeOptions(): RequestOptions {
        let headers = new Headers({'Content-Type': 'application/json'});
        return new RequestOptions({headers: headers});
    }
}