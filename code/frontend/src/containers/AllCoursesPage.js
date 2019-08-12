import React from 'react';
import axios from 'axios'
import {Layout, Divider} from 'antd';
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
    title: 'NumOfStudent',
},{
    title: 'Interval',
}];

class AllcoursesPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            data:[],
            name: this.props.match.params.name
        };
    }

    componentWillMount() {
        let COURSE_OF_CURRENT_USER_URL = '/api/course/byUser';
        let COURSE_OF_USER_BY_NAME_URL = "/api/course/byUserName?userName=" + this.state.name;
        if(this.state.name == null){
            axios.get(COURSE_OF_CURRENT_USER_URL)
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
        }
        else{
            axios.get(COURSE_OF_USER_BY_NAME_URL)
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
        }

    }
  
    render() {
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
                   <Divider orientation="left"><h1>所有课程</h1></Divider>
                   <Table className="table" column={columns} data={this.state.data} />
                   <div className="fill"/>
                </Content>
              </Layout>
            </Layout>
        </Layout>
      );
    }
  }
  
export default AllcoursesPage;