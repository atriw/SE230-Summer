import React from 'react';
import { Form, Input, Button} from 'antd';
import { Redirect } from 'react-router-dom';
import axios from 'axios'

const FormItem = Form.Item;

class RegisterPage extends React.Component {
    constructor(props) {
        super(props);
        this.state={
            userName: '',
            password: '',
            passwordAgain: '',
            email: '',
            phone: '',
            redirect: false
        }
    }

    handleSubmit = (e) => {
        e.preventDefault();
        if(!this.check())
            return false
        axios.post('/api/user/add', {
            name: this.state.userName,
            pwd: this.state.password,
            email: this.state.email,
            phone: this.state.phone
        })
            .then((res) => {
                let data = res.data;
                if (data!==null) {
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

    handlePasswordChange = (e) => {
        e.preventDefault();
        this.setState({
            password: e.target.value
        })
    };

    handlePasswordAgainChange = (e) => {
        e.preventDefault();
        this.setState({
            passwordAgain: e.target.value
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

    check = () => {
        if(!this.state.userName||!this.state.password){
            alert('请确保账号/密码不为空')
            return false
        }
        if(this.state.password !== this.state.passwordAgain){
            alert('请确认两次输入的密码一致')
            return false
        }
        let patt=new RegExp('^.{6,16}$');
        if (!patt.test(this.state.password)){
            alert('密码应在6-16位之间')
            return false
        }
        patt=new RegExp('^([0-9A-Za-z\\-_.]+)@([0-9a-z]+\\.[a-z]{2,3}(\\.[a-z]{2})?)$');
        if (!patt.test(this.state.email)){
            alert('请确认输入的邮箱格式正确')
            return false
        }
        patt = new RegExp("^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\\d{8}$");
        if (!patt.test(this.state.phone)){
            alert('请确认输入的手机格式正确')
            return false
        }
    }

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
                            <Input type="password" placeholder="请输入密码" onChange={this.handlePasswordChange} />
                        </FormItem>     
                        <FormItem {...formItemLayout} label='密码确认'>
                            <Input type="password" placeholder="请确认密码" onChange={this.handlePasswordAgainChange}/>
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

