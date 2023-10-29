export class AuthResponse {
    public username: string='';
    public accessToken: string='';
    public roles: string[]=[]
    
    constructor(public uname: string,
        public token: string,
        public r: string[])
        {
            this.username=uname;
            this.accessToken=token;
            this.roles=r;
        }
}



