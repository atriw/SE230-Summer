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
                    <h1 className='title'>This is the demo page</h1>
                    <div className='welcome' />
                    <ul className='list'>
                    <li>To get started, edit <code>src/index.js</code> and save to reload.</li>
                    <li><a href="https://github.com/dvajs/dva-docs/blob/master/v1/en-us/getting-started.md">Getting Started</a></li>
                    </ul>
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
