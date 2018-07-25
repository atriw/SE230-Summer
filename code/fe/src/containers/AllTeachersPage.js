import React from 'react';
import axios from 'axios'
import {Layout, Divider} from 'antd';
import Sidebar from '../components/Parts/Sidebar';
import Table from '../components/Parts/PaginationTable';
import Avatar from "../components/Parts/Avatar";
import {Redirect} from 'react-router-dom';

const {Header, Content, Sider}=Layout;
const columns = [{
    title: 'Name',
},{
    title: 'Coursenum',
},{
    title: 'Email',
},{
    title: 'Phone',
}];

class AllteachersPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            data:[]
        };
    }

    processUserData = (data) => {
        let newData = [];
        data.forEach((column) =>{
            let aColumn = {
                name: column.name,
                // courseNum: column.coursenum,
                // email: column.email,
                // phone: column.phone
            };
            newData.push(aColumn)
        });
        return newData
    };

    componentDidMount() {
        axios.get('/api/user/all')
            .then((res) => {
                let data = res.data;
                if (data.length > 0) {
                    this.setState({
                        data: data
                    })
                }
            })
          .catch((error) => {
              console.log(error);
      });
    };

    ToUserCourses = (userName) =>{
        console.log("user name:" + userName);
        this.props.history.push('/allcourses/' + userName)
    };

    render() {
        const onRow = (record) =>{
            return{
                onClick: ()=>{
                    let userName = (record.name);
                    this.ToUserCourses(userName)
                }
            }
        };
        return (
            <Layout>
                <Header className="header">
                    <Avatar/>
                </Header>
                <Layout>
                    <Sider style={{background: '#fff'}}>
                        <Sidebar />
                    </Sider>
                    <Layout>
                    <Content>
                        <Divider orientation="left"><h1>所有教师</h1></Divider>
                        <Table column={columns} data={this.state.data} onRow={onRow}/>
                        <div className="fill"/>
                    </Content>
                    </Layout>
                </Layout>
            </Layout>
        );
    }
}
  
export default AllteachersPage;