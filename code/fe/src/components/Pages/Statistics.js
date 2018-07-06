import React from 'react';
import {Layout, Icon, Divider} from 'antd';
import Sidebar from '../Parts/Sidebar';
import Dropdown from '../Parts/Dropdown';
import Chart from '../Charts/Chart';
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
              <span className="user"> <Icon type="user"/></span>
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
               <Chart />
            </Content>
          </Layout>
        </Layout>
        </Layout>
      );
    }
  }
  
  export default StatisticPage;