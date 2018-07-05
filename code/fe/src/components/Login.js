import React from 'react';
import { Form, Input, Button, notification, Checkbox, Divider} from 'antd';
import { Redirect,Link } from 'react-router-dom';

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
        let n = 'user'
        let p = 'user'
        /*fetch('/login/checklogin',{
            method: 'POST',
            headers:{},
            body: body,
        }).then((res)=>{
            if(res.ok){
                res.text().then((data)=>{
                    alert(data);
                })
            }
        }).catch((res)=>{
            console.log(res.status);
        })*/

        if (n === 'user' && p === 'user') {                      
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

    componentDidMount() {
        this.openNotificationWithIcon('info');  
    }

    render() {  
        if (this.state.redirect){
            return <Redirect push to="/" />
        }
        return (
            <body id="body">
                <div className='mysign'>                
                    <Form horizontal onSubmit={this.handleSubmit}>
                        <FormItem>
                            <Input placeholder="用户名" />
                        </FormItem>
                        <FormItem>
                            <Input type="password" placeholder="密码"/>
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
            </body>
        );
    }
}

export default LoginPage;

