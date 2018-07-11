import React from 'react';
import {Layout, Icon, Divider} from 'antd';
import Sidebar from '../components/Parts/Sidebar';
import Table from '../components/Parts/PaginationTable';
import Avatar from "../components/Parts/Avatar";
const {Header, Content, Sider}=Layout;
const columns = [{
    title: 'Id',
},{
    title: 'Name',
    type: 'link',
},{
    title: 'Time',
},{
    title: 'Total',
},{
    title: 'Interval',
}];

const data = [{
    key: '1',
    id: '1',
    name: 'Math',
    time: '周二 08:00-10:00',
    total: 5,
    interval: 5,
},{
    key: '2',
    id: '2',
    name: 'English',
    time: '周二 08:00-10:00',
    total: 5,
    interval: 5,
}];


class AllcoursesPage extends React.Component {
    handleClick = (e) => {
      console.log('click', e);
    }
  
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
               <Divider orientation="left"><h1>所有课程</h1></Divider>
               <Table column={columns} data={data} />
               <div className="fill"/>
            </Content>
          </Layout>
        </Layout>
        </Layout>
      );
    }
  }
  
export default AllcoursesPage;