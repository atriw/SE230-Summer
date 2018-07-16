import React from 'react';
import {Layout, Icon, Divider} from 'antd';
import Sidebar from '../components/Parts/Sidebar';
import Table from '../components/Parts/PaginationTable';
import Avatar from "../components/Parts/Avatar";
const {Header, Content, Sider}=Layout;
const columns = [{
  title: 'Name',
  dataIndex: 'name',
  key: 'name',
  //type: 'link',
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

/*const data = [{
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
}];*/

class AllteachersPage extends React.Component {
    handleClick = (e) => {
      console.log('click ', e);
    }

    componentDidMount = (e) => {
      e.preventDefault();
      axios.post('/api/user/all')
          .then((res) => {
              let data = res.data;
              if (data === true) {
                  this.setState({
                      data: data
                  })
              }
          })
          .catch((error) => {
              console.log(error);
      });
  }
  
    render() {
      return (
        <Layout>
        <Header className="header" style={{background:'#aaa'}}>
            <Avatar/>
        </Header>
        <Layout>
          <Sider width={256} style={{background: '#fff'}}>
            <Sidebar />
          </Sider>
          <Layout>
            <Content>
               <Divider orientation="left"><h1>所有教师</h1></Divider>
               <Table column={columns} data={this.state.data}/>
               <div className="fill"/>
            </Content>
          </Layout>
        </Layout>
        </Layout>
      );
    }
  }
  
export default AllteachersPage;