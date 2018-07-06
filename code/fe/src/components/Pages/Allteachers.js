import React from 'react';
import {Layout, Icon, Divider} from 'antd';
import Sidebar from '../Parts/Sidebar';
import Dropdown from '../Parts/Dropdown';
import Table from '../Parts/PaginationTable';
const {Header, Content, Sider}=Layout;
const columns = [{
  title: 'Name',
  dataIndex: 'name',
  key: 'name',
  type: 'link',
},{
  title: 'Coursenum',
  dataIndex: 'coursenum',
  key: 'coursenum',
},{
  title: 'Email',
  dataIndex: 'email',
  key: 'email',
},{
  title: 'Phone',
  dataIndex: 'phone',
  key: 'phone',
}];

const data = [{
  key: '1',
  name: '任爹',
  coursenum: '10',
  email: 'ren@126.com',
  phone: '13912345678',
},{
  key: '2',
  name: '沈阿姨',
  coursenum: '3',
  email: 'shen@126.com',
  phone: '12345678910',
}];

class AllteachersPage extends React.Component {
    handleClick = (e) => {
      console.log('click ', e);
    }
  
    render() {
      return (
        <Layout>
        <Header className="header" style={{background:'#aaa'}}>
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
               <Divider orientation="left"><h1>所有教师</h1></Divider>
               <Table column={columns} data={data}/>
               <div className="fill"/>
            </Content>
          </Layout>
        </Layout>
        </Layout>
      );
    }
  }
  
export default AllteachersPage;