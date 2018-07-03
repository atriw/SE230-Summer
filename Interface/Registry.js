import React from 'react';
import 'react-dom';
import {input, div} from 'react-bootstrap';

class App extends React.Component{

    constructor(props) {
        super(props);

        this.state = {
            username_ok:true,
            password_ok:true,
            'e-mail_ok':true,
            phonenumber_ok:true
        };

        this.checkUsername=this.checkUsername.bind(this);
        this.checkPassword=this.checkPassword.bind(this);
        this.checkEmail=this.checkEmail.bind(this);
        this.checkPhoneNumber=this.checkPhoneNumber.bind(this);
        this.Registry=this.Registry.bind(this);
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

    checkEmail(e){
        var patt=new RegExp("^.*@.+$");
        var result=patt.test(e.target.value);
        if (result === false){
            this.setState({'e-mail_ok':false})
        }
        else{
            this.setState({'e-mail_ok':true})
        }
    }

    checkPhoneNumber(e){
        var patt=new RegExp("^[0-9]{11}$");
        var result=patt.test(e.target.value);
        if (result === false){
            this.setState({phonenumber_ok:false})
        }
        else{
            this.setState({phonenumber_ok:true})
        }
    }

    Registry(e){
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
                    <div style={{'height':'30px'}}></div>
                    <h1 style={{'fontSize': '45px'}}>sign up for free!</h1>
                    <div style={{'height':'40px'}}></div>
                    <h3 style={{'fontSize':'24px !important'}}>Create your personal account</h3>
                    <div style={{'height':'20px'}}></div>
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

                    <label>E-mail</label>
                    <input type="text" className="form-control" aria-describedby="basic-addon1" id="input_e-mail" style={input_style} onChange={this.checkEmail}/>
                    {this.state["e-mail_ok"]?null:<div style={error_style}>E-amil is invalid</div>}
                    <div style={{'height':'15px'}}></div>

                    <label>Phone number</label>
                    <input type="text" className="form-control" aria-describedby="basic-addon1" id="input_phonenumber" style={input_style} onChange={this.checkPhoneNumber}/>
                    {this.state.phonenumber_ok?null:<div style={error_style}>Phone number is invalid</div>}
                    <div style={{'height':'25px'}}></div>

                    <p>By clicking “Create an account” below, you agree to our terms of service and privacy statement.</p>
                    <button className="btn btn-primary"  type="submit" onClick={this.Registry}>Create an account</button>
                </form>
            </div>
        );
    }
}


export default App;
