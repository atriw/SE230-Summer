import React from 'react';
import {Layout, Icon, Divider} from 'antd';
import Sidebar from './Sidebar';
import Dropdown from './Dropdown';
import Table from './Table';
const {Header, Content, Sider}=Layout;

class AllcoursePage extends React.Component {
    handleClick = (e) => {
      console.log('click ', e);
    }
  
    render() {
      return (
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
               <Divider orientation="left">所有课程</Divider>
               <Table />
               <div className="fill"/>
            </Content>
          </Layout>
        </Layout>
        </Layout>
      );
    }
  }
  
export default AllcoursePage;