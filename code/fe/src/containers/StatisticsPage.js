import React from 'react';
import {Layout, Divider} from 'antd';
import Sidebar from '../components/Parts/Sidebar';
import Chart from '../components/Charts/Chart';
import Avatar from "../components/Parts/Avatar";
const {Header, Content, Sider}=Layout;

class StatisticPage extends React.Component {
    handleClick = (e) => {
      console.log('click ', e);
    };
  
    render() {
      return (
        <Layout>
        <Header className={"header"} style={{background:'#aaa'}}>
            <Avatar/>
        </Header>
        <Layout>
          <Sider width={256} style={{background: '#fff'}}>
            <Sidebar />
          </Sider>
          <Layout>
            <Content>
               <Divider orientation="left"><h1>最近课程</h1></Divider>
               <Chart />
                <div className="fill"/>
            </Content>
          </Layout>
        </Layout>
        </Layout>
      );
    }
  }
  
  export default StatisticPage;