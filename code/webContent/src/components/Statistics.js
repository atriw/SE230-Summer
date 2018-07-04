import React from 'react';
import {Layout, Icon, Divider, Form} from 'antd';
import styles from './component.css';
import Sidebar from '../components/Sidebar';
import Dropdown from '../components/Dropdown';
const {Header, Content, Sider}=Layout;

class StatisticPage extends React.Component {
    handleClick = (e) => {
      console.log('click ', e);
    }
  
    render() {
      return (
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
               <Divider orientation="left">最近课程</Divider>
            </Content>
          </Layout>
        </Layout>
        </Layout>
      );
    }
  }
  
  let Statistics = Form.create()(StatisticPage);
  export default Statistics;