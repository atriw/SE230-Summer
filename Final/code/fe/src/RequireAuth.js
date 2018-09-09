import React from 'react';
import axios from 'axios'
import {Redirect} from 'react-router-dom';
import RoleContext from './roleContext'
import UserContext from './userContext'

const RequireAuth = (Component) => {
    return class AuthWrapper extends Component {
        constructor(props){
            super(props);
            this.state = {
                redirect: false,
                role: null,
                user: null
            }
        }
        componentDidMount() {
            axios.get('/api/user/getRoles')
                .then((res) => {
                    let data = res.data;
                    console.log(data);
                    if (!data) {
                        this.setState({
                            redirect: true
                        })
                    }
                    for(let i=0;i<data.length;i++){
                        if(data[i]!=="EA" && data[i]!=="teacher") {
                            this.setState({
                                redirect: true
                            })
                        }
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
            axios.get('/api/user/userInfo')
                .then((res) => {
                    let data = res.data;
                    this.setState({
                        user: data
                    })
                })
                .catch((error) => {
                    console.log(error);
                });
        }
        render() {
            if(this.state.redirect ===true)
                return <Redirect push to="/login" />;
            return (
                <RoleContext.Provider value={this.state.role}>
                    <UserContext.Provider value={this.state.user}>
                        <Component {...this.props}/>
                    </UserContext.Provider>
                </RoleContext.Provider>
            )
        }
    }
};

export default RequireAuth