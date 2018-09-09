import React from 'react';
import {Layout} from 'antd';
import Sidebar from '../components/Parts/Sidebar';
import WrappedAddCourse from "../components/AddCourse/AddCourse";
import Avatar from "../components/Parts/Avatar";
const {Header, Content, Sider}=Layout;

class AddCoursePage extends React.Component {

    render() {
        return(
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
                            <WrappedAddCourse />
                            <div className="fill"/>
                        </Content>
                    </Layout>
                </Layout>
            </Layout>
        )
    }
}

export default AddCoursePage;