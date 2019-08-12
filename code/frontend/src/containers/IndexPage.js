import React from 'react';
import {Layout} from 'antd';
import Sidebar from '../components/Parts/Sidebar';
import Avatar from '../components/Parts/Avatar';
const {Header, Content, Sider}=Layout;


class IndexPage extends React.Component {
    render(){
          return (
            <Layout>
              <Header className={"header"}>
                  <Avatar />
              </Header>
              <Layout>
                <Sider style={{background: '#fff'}}>
                  <Sidebar />
                </Sider>
                <Layout>
                  <Content>
                    <div className='normal'>
                    <h1 className='title'>课堂威视系统</h1>
                    <div className='welcome' />
                    </div>
                    <div className="fill"/>
                  </Content>
                </Layout>
              </Layout>
            </Layout>
          );
    }
}

export default IndexPage;
