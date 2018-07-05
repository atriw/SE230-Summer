import React from 'react';
import {Form, Input, Button, Layout, Icon, Divider} from 'antd';
import Sidebar from './Sidebar';
import Dropdown from './Dropdown';
const {Header, Content, Sider}=Layout;
const FormItem = Form.Item;

class PersonalPage extends React.Component {
    handleClick = (e) => {
      console.log('click ', e);
    }
  
    render() {
      return(
        <Layout>
        <Header className={"header"} style={{background:'#aaa'}}>
            <div align = "right"> 
              <span className='user'> <Icon type="user"/></span>
              <Dropdown />
            </div>
        </Header>
        <Layout>
          <Sider width={256} style={{background: '#fff'}}>
            <Sidebar />
          </Sider>
          <Layout>
            <Content>
             <Divider orientation="left">修改密码</Divider>
             <Form horizontal className='changepassword'>
                <FormItem label = "旧密码" className='formitem'>
                    <Input type="password" placeholder="请输入旧密码"/>
                </FormItem>
                <FormItem label = "新密码" className='formitem'>
                    <Input type="newpassword" placeholder="请输入新密码"/>
                </FormItem>     
                <FormItem label = "确认新密码" className='formitem'>
                    <Input type="checkpassword" placeholder="请确认新密码"/>
                </FormItem>       
                <FormItem>
                    <Button type="primary" className='changebutton'>更改密码</Button>
                </FormItem>     
             </Form>
             <Divider orientation="left" className='divider'>修改邮箱</Divider>
                <FormItem className='changepassword'>
                    <Button type="primary" className='changebutton'>更改邮箱</Button>
                </FormItem>   
             <Divider orientation="left">修改手机</Divider>
                <FormItem className='changepassword'>
                    <Button type="primary" className='changebutton'>更改手机</Button>
                </FormItem>   
            </Content>
          </Layout>
        </Layout>
      </Layout>
      )
    }
  }
  
  export default PersonalPage;