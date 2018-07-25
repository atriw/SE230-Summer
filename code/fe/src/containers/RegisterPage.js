import React from 'react';
import { Form, Input, Button, notification} from 'antd';
import { Redirect } from 'react-router-dom';
import axios from 'axios'

const FormItem = Form.Item;

class RegisterPage extends React.Component {
    constructor(props) {
        super(props);
        this.state={
            userName: '',
            pwd: '',
            email: '',
            phone: '',
            redirect: false
        }
    }

    handleSubmit = (e) => {
        e.preventDefault();
        axios.post('/api/user/add', {
            name: this.state.userName,
            pwd: this.state.pwd,
            email: this.state.email,
            phone: this.state.phone
        })
            .then((res) => {
                let data = res.data;
                if (data === true) {
                    this.setState({
                        redirect: true
                    })
                } else {
                    alert('用户名重复，请重新输入');
                }
            })
            .catch((error) => {
                console.log(error);
            });
    };

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

    handleEmailChange = (e) => {
        e.preventDefault();
        this.setState({
            email: e.target.value
        })
    };

    handlePhoneChange = (e) => {
        e.preventDefault();
        this.setState({
            phone: e.target.value
        })
    };

    render() {  
        const formItemLayout = {
            labelCol: { span: 6},
            wrapperCol: {span: 18},
        };
        if (this.state.redirect){
            return <Redirect push to="/login" />
        }
        return (
            <body>
                <div className='register'>                
                    <Form horizontal onSubmit={this.handleSubmit}>
                        <FormItem {...formItemLayout} label="用户名">
                            <Input placeholder="请输入用户名" onChange={this.handleUserNameChange} />
                        </FormItem>
                        <FormItem {...formItemLayout} label="密码">
                            <Input type="password" placeholder="请输入密码" onChange={this.handlePwdChange} />
                        </FormItem>     
                        <FormItem {...formItemLayout} label='密码确认'>
                            <Input type="password2" placeholder="请确认密码"/>
                        </FormItem>     
                        <FormItem {...formItemLayout} label='邮箱'>
                            <Input type="email" placeholder="请输入邮箱" onChange={this.handleEmailChange} />
                        </FormItem>   
                        <FormItem {...formItemLayout} label='手机'> 
                            <Input type="phone" placeholder="请输入手机" onChange={this.handlePhoneChange} />
                        </FormItem>       
                        <FormItem>
                            <Button type="primary" htmlType="submit" className='registerbutton'>注册</Button>
                        </FormItem>     
                    </Form>
                </div>
            </body>
        );
    }
}

export default RegisterPage;

