import React from 'react';
import {Layout, Icon} from 'antd';
import Sidebar from './Sidebar';
import Dropdown from './Dropdown';
const {Header, Content, Sider}=Layout;

function IndexPage() {
  return (
    <Layout>
      <Header className={"header"} style={{background:'#aaa'}}>
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
            <div className='normal'>
            <h1 className='title'>This is the demo page</h1>
            <div className='welcome' />
            <ul className='list'>
            <li>To get started, edit <code>src/index.js</code> and save to reload.</li>
            <li><a href="https://github.com/dvajs/dva-docs/blob/master/v1/en-us/getting-started.md">Getting Started</a></li>
            </ul>
            </div>
          </Content>
        </Layout>
      </Layout>
    </Layout>
  );
}

export default IndexPage;
