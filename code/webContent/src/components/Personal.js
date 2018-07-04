import React from 'react';
import {Form, Input, Button, Layout, Icon, Divider} from 'antd';
import styles from './component.css';
import Sidebar from '../components/Sidebar';
import Dropdown from '../components/Dropdown';
const {Header, Content, Sider}=Layout;
const FormItem = Form.Item;

class PersonalPage extends React.Component {
    handleClick = (e) => {
      console.log('click ', e);
    }
  
    render() {
      const { getFieldProps } = this.props.form;    
      return(
        <Layout>
        <Header className={"header"} style={{background:'#aaa'}}>
            <div align = "right"> 
              <span className={styles.user}> <Icon type="user"/></span>
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
             <Form horizontal className={styles.changepassword}>
                <FormItem label = "旧密码" className={styles.formitem}>
                    <Input type="password" placeholder="请输入旧密码" {...getFieldProps('oldpassword')} />
                </FormItem>
                <FormItem label = "新密码" className={styles.formitem}>
                    <Input type="newpassword" placeholder="请输入新密码" {...getFieldProps('newpassword')} />
                </FormItem>     
                <FormItem label = "确认新密码" className={styles.formitem}>
                    <Input type="checkpassword" placeholder="请确认新密码" {...getFieldProps('checkpassword')} />
                </FormItem>       
                <FormItem>
                    <Button type="primary" className={styles.changebutton}>更改密码</Button>
                </FormItem>     
             </Form>
             <Divider orientation="left" className={styles.divider}>修改邮箱</Divider>
                <FormItem className={styles.changepassword}>
                    <Button type="primary" className={styles.changebutton}>更改邮箱</Button>
                </FormItem>   
             <Divider orientation="left">修改手机</Divider>
                <FormItem className={styles.changepassword}>
                    <Button type="primary" className={styles.changebutton}>更改手机</Button>
                </FormItem>   
            </Content>
          </Layout>
        </Layout>
      </Layout>
      )
    }
  }
  
  let Personal = Form.create()(PersonalPage);
  export default Personal;