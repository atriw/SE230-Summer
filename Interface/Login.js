import React from 'react';
import 'react-dom';
import {input, div} from 'react-bootstrap';

class Login extends React.Component{

    constructor(props) {
        super(props);

        this.state = {
            username_ok:true,
            password_ok:true,
        };

        this.checkUsername=this.checkUsername.bind(this);
        this.checkPassword=this.checkPassword.bind(this);
        this.Login=this.Login.bind(this);
        this.jump=this.jump.bind(this);

    }

    checkUsername(e){
        var patt=new RegExp("^[a-zA-Z0-9]{6,16}$");
        var result=patt.test(e.target.value);
        if (result === false){
            this.setState({username_ok:false})
        }
        else{
            this.setState({username_ok:true})
        }
    }

    checkPassword(e){
        var patt=new RegExp("^[a-zA-Z0-9]{6,16}$");
        var result=patt.test(e.target.value);
        if (result === false){
            this.setState({password_ok:false})
        }
        else{
            this.setState({password_ok:true})
        }
    }

    Login(e){
        //empty
    }

    jump(e){
        //empty
    }

    render(){
        const box_style={
            'width':'500px',
            'marginLeft':'auto',
            'marginRight':'auto'
        };
        const header_style={
            'height':'250px'
        }
        const input_style={
            'width':'500px'
        }
        const error_style={
            'color': '#86181d',
            'background-color': '#ffdce0',
            'border-color': '#cea0a5',
            'z-index': '10',
            'display': 'block',
            'padding': '5px 8px',
            'margin': '4px 0 0',
            'fontSize': '13px',
            'font-weight': '400',
            'border-style': 'solid',
            'border-width': '1px',
            'border-radius': '3px',
        }
        
        return(
            <div className="row" style={box_style}>
                <div style={header_style}>
                </div>
                <form className="bs-example bs-example-form" data-example-id="simple-input-groups" >
                    <label>Username</label>
                    <input type="text" className="form-control"  aria-describedby="basic-addon1" id="input_username" style={input_style} onChange={this.checkUsername}/>
                    {this.state.username_ok?null:<div style={error_style}>Username is invalid</div>}
                    <div style={{'height':'15px'}}></div>

                    <label>Password</label>
                    <input type="password" className="form-control" aria-describedby="basic-addon1" id="input_password" style={input_style} onChange={this.checkPassword}/>
                    {this.state.password_ok?null:<div style={error_style}>Password is invalid</div>}
                    <div style={{'height':'15px'}}></div>
                    <div>
                    <button className="btn btn-primary"  type="submit" onClick={this.Login}>sign in</button>
                    <a onClick={this.jump} style={{'float':'right'}}>sign up</a>
                    </div>
                </form>
            </div>
        );
    }
}


export default Login
