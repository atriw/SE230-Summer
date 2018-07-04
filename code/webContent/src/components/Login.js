import React from 'react';
import { Form, Input, Button, notification, Checkbox, Divider} from 'antd';
import { Redirect,Link } from 'react-router-dom';
import styles from './component.css';

const FormItem = Form.Item;

class LoginPage extends React.Component {
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


        fetch('/login/checklogin',{
            method: 'POST',
            headers:{},
        }).then((res)=>{
            if(res.ok){
                res.text().then((data)=>{
                    this.setState({redirect: true});
                    alert(data);
                })
            }
        }).catch((res)=>{
            console.log(res.status);
        })

        if (n === 'user' && p === 'user') {
            // 表单的路由处理                       
           // this.setState({redirect: true});
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

    componentDidMount() {
        this.openNotificationWithIcon('info');  
    }

    render() {  
        const { getFieldProps } = this.props.form;      
        if (this.state.redirect){
            return <Redirect push to="/" />
        }
        return (
            <body className={styles.body}>
                <div className={styles.mysign}>                
                    <Form horizontal onSubmit={this.handleSubmit}>
                        <FormItem>
                            <Input placeholder="用户名" {...getFieldProps('username')} />
                        </FormItem>
                        <FormItem>
                            <Input type="password" placeholder="密码" {...getFieldProps('password')} />
                        </FormItem>     
                        <FormItem>
                            <Checkbox className={styles.mycheck} {...getFieldProps('agreement')}>记住我</Checkbox>
                            <Button type="primary" htmlType="submit" className={styles.mybutton}>登录</Button>
                        </FormItem>       
                        <Divider>新用户？</Divider>
                        <FormItem>
                            <Link to = "register">
                            <Button type="primary" className={styles.mybutton}>注册</Button>
                            </Link>
                        </FormItem>     
                    </Form>
                </div>
            </body>
        );
    }
}

let Login = Form.create()(LoginPage);
export default Login;

