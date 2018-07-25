import React from 'react';
import { Form, Input, Button, notification, Checkbox, Divider} from 'antd';
import { Redirect,Link } from 'react-router-dom';
import axios from 'axios'
require(`../components/components.css`);

const FormItem = Form.Item;

class LoginPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userName: '',
            pwd: '',
            redirect:false
        };
    }

    handleUserNameChange = (e) => {
        e.preventDefault();
        this.setState({
            userName: e.target.value
        })
    };

    handlePwdChange = (e) => {
        e.preventDefault();
        this.setState({
            pwd: e.target.value
        })
    };

    handleSubmit = (e) => {
        e.preventDefault();
        axios.post('/api/user/login', {
            name: this.state.userName,
            pwd: this.state.pwd
            })
            .then((res) => {
                let data = res.data;
                if (data === true) {
                    this.setState({
                        redirect: true
                    })
                } else {
                    alert('用户名或密码错误！请重新输入');
                }
            })
            .catch((error) => {
                console.log(error);
        });

    };


    render() {
        if (this.state.redirect){
            return <Redirect push to="/" />
        }
        return (
            <div>
                <div className='mysign'>                
                    <Form layout="horizontal" onSubmit={this.handleSubmit}>
                        <FormItem>
                            <Input placeholder="用户名" onChange={this.handleUserNameChange}/>
                        </FormItem>
                        <FormItem>
                            <Input type="password" placeholder="密码" onChange={this.handlePwdChange}/>
                        </FormItem>     
                        <FormItem>
                            <Checkbox className='mycheck'>记住我</Checkbox>
                            <Button type="primary" htmlType="submit" className='mybutton'>登录</Button>
                        </FormItem>       
                        <Divider>新用户？</Divider>
                        <FormItem>
                            <Link to = "register">
                            <Button type="primary" className='mybutton'>注册</Button>
                            </Link>
                        </FormItem>     
                    </Form>
                </div>
            </div>
        );
    }
}

export default LoginPage;

