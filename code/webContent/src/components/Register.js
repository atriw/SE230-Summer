import React from 'react';
import { Form, Input, Button, notification} from 'antd';
import { Redirect,Link } from 'react-router-dom';
import styles from './component.css';

const FormItem = Form.Item;

class RegisterPage extends React.Component {
    constructor(props) {
        super(props);
        this.state={
            redirect:false
        }
    }

    handleSubmit = (e) => {
        e.preventDefault();
        let n = this.props.form.getFieldsValue().username;
        let p = this.props.form.getFieldsValue().password;
        if (n === 'user' && p === 'user') {
            // 表单的路由处理                       
            this.setState({redirect: true});
        } else {
            this.openNotificationWithIcon('info');
        }
    }

    // 返回一个弹框对象，提示用户名和密码
    openNotificationWithIcon = (type) => {
        return notification[type]({
                 message: '用户名&密码',
                 description: '都是：user',
                 duration: 6
               })
    }

    render() {  
        const { getFieldProps } = this.props.form;   
        const formItemLayout = {
            labelCol: { span: 6},
            wrapperCol: {span: 18},
        }   
        if (this.state.redirect){
            return <Redirect push to="/" />
        }
        return (
            <body className={styles.body}>
                <div className={styles.register}>                
                    <Form horizontal onSubmit={this.handleSubmit}>
                        <FormItem {...formItemLayout} label="用户名">
                            <Input placeholder="请输入用户名" {...getFieldProps('username')} />
                        </FormItem>
                        <FormItem {...formItemLayout} label="密码">
                            <Input type="password" placeholder="请输入密码" {...getFieldProps('password')} />
                        </FormItem>     
                        <FormItem {...formItemLayout} label='密码确认'>
                            <Input type="password2" placeholder="请确认密码" {...getFieldProps('password2')} />
                        </FormItem>     
                        <FormItem {...formItemLayout} label='邮箱'>
                            <Input type="email" placeholder="请输入邮箱" {...getFieldProps('email')} />
                        </FormItem>   
                        <FormItem {...formItemLayout} label='手机'> 
                            <Input type="phone" placeholder="请输入手机" {...getFieldProps('phone')} />
                        </FormItem>       
                        <FormItem>
                            <Link to = "login">
                            <Button type="primary" className={styles.registerbutton}>注册</Button>
                            </Link>
                        </FormItem>     
                    </Form>
                </div>
            </body>
        );
    }
}

let Login = Form.create()(RegisterPage);
export default Login;

