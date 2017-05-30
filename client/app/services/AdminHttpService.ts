// import { Injectable } from "@angular/core";
// import { Http, Headers } from "@angular/http";
// import "rxjs/add/operator/toPromise";
//
// @Injectable()
// export class AdminHttpService {
//     private adminUrl = "/admin/user";
//     private blacklistUrl = "/admin/blacklist";
//     private killSwitchUrl = "/admin/killswitch";
//
//     constructor(private http: Http) { }
//
//     public getAdmins(): Promise<any> {
//         return this.http.get(this.adminUrl)
//             .toPromise()
//             .then(response => response)
//             .catch(this.handleError);
//     }
//
//     public setAdmins(admins): Promise<any> {
//         let header = new Headers({ "Content-Type": "application/json" });
//         return this.http.post(this.adminUrl, admins, { headers: header })
//             .toPromise()
//             .then(response => response)
//             .catch(this.handleError);
//     }
//
//     public getKillSwitchActive(): Promise<any> {
//         return this.http.get(this.killSwitchUrl)
//             .toPromise()
//             .then(response => response)
//             .catch(this.handleError);
//     }
//
//     public setKillSwitch(killSwitchActive): Promise<any> {
//         let header = new Headers({ "Content-Type": "application/json" });
//         return this.http.put(this.killSwitchUrl, { killswitchActive: killSwitchActive }, { headers: header })
//             .toPromise().then(response => response)
//             .catch(this.handleError);
//     }
//
//     public getBlacklist(): Promise<any> {
//         return this.http.get(this.blacklistUrl)
//             .toPromise()
//             .then(response => response)
//             .catch(this.handleError);
//     }
//
//     public setBlacklist(blacklist): Promise<any> {
//         let header = new Headers({ "Content-Type": "application/json" });
//         return this.http.post(this.blacklistUrl, blacklist, { headers: header })
//             .toPromise()
//             .then(response => response)
//             .catch(this.handleError);
//     }
//
//     public addUserToBlacklist(user): Promise<any> {
//         let header = new Headers({ "Content-Type": "application/json" });
//         return this.http.put(this.blacklistUrl, user, { headers: header })
//             .toPromise()
//             .then(response => response)
//             .catch(this.handleError);
//     }
//
//     public restartStream(): Promise<any> {
//         return this.http.get("/admin/stream?restart=true")
//             .toPromise()
//             .then(response => response)
//             .catch(this.handleError);
//     }
//
//     public getStreamStatus(): Promise<any> {
//         return this.http.get("/admin/stream?getStatus=true")
//             .toPromise()
//             .then(response => response)
//             .catch(this.handleError);
//     }
//
//     public refreshTweets(): Promise<any> {
//         return this.http.get("/admin/stream?refreshTweets=true")
//             .toPromise()
//             .then(response => response)
//             .catch(this.handleError);
//     }
//
//     private handleError(error: any): Promise<any> {
//         return Promise.reject(error.message || error);
//     }
// }
