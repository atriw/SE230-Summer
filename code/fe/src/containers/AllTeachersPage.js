import React from 'react';
import axios from 'axios'
import {Layout, Divider} from 'antd';
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

const testdata = [{
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
    constructor(props) {
        super(props);
        this.state = {
            data:[]
        };
    }

    handleClick = (e) => {
        console.log('click ', e);
    }

    componentDidMount = (e) => {
        axios.post('/api/user/all')
            .then((res) => {
                let data = res.data;
                if (data === true) {
                    this.setState({
                    data: data
                    })
                }
                else{
                    this.setState({
                    data:testdata
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