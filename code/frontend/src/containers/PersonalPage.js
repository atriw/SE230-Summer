import React from 'react';
import {Layout} from 'antd';
import Sidebar from '../components/Parts/Sidebar';
import PersonalInfo from '../components/Others/PersonalInfo';
import Avatar from "../components/Parts/Avatar";
const {Header, Content, Sider}=Layout;

class PersonalPage extends React.Component {
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
             <PersonalInfo />
             <div style={{height: '100px'}}/>
            </Content>
          </Layout>
        </Layout>
      </Layout>
      )
    }
  }
  
  export default PersonalPage;