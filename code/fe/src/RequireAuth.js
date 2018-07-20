import React, { Component } from 'react';
import axios from 'axios'
import {Redirect} from 'react-router-dom';
import RoleContext from './context'
const RequireAuth = (Component) => {
    return class AuthWrapper extends Component {
        constructor(props){
            super(props);
            this.state = {
                redirect: false,
                role: null
            }
        }
        componentWillMount() {
            axios.get('/api/user/getRoles')
                .then((res) => {
                    let data = res.data;
                    console.log(data);
                    for(let i=0;i<data.length;i++){
                        if(data[i]!=="EA" && data[i]!=="teacher")
                            this.setState({
                                redirect: true,
                            })
                    }
                    this.setState({
                        role: data
                    })
                })
                .catch((error) => {
                    this.setState({
                        redirect: true
                    });
                    console.log(error);
                });
        }
        render() {
            if(this.state.redirect ===true)
                return <Redirect push to="/login" />;
            console.log(this.state.role)
            return (
                <RoleContext.Provider value={this.state.role}>
                    <Component {...this.props}/>
                </RoleContext.Provider>
            )
        }
    }

}

export default RequireAuth