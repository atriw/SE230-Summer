import React from 'react';
import {Layout, Icon, Divider} from 'antd';
import styles from './component.css';
import Sidebar from './Sidebar';
import Dropdown from './Dropdown';
const {Header, Content, Sider}=Layout;

class StatisticPage extends React.Component {
    handleClick = (e) => {
      console.log('click ', e);
    }
  
    render() {
      return (
        <body2>
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
               <div className="fill"/>
            </Content>
          </Layout>
        </Layout>
        </Layout>
        </body2>
      );
    }
  }
  
  export default StatisticPage;