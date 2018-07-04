import React from 'react';
import { connect } from 'dva';
import {Layout, Icon} from 'antd';
import styles from './IndexPage.css';
import Sidebar from '../components/Sidebar';
import Dropdown from '../components/Dropdown';
const {Header, Content, Sider}=Layout;

function IndexPage() {
  return (
    <Layout>
      <Header className={"header"} style={{background:'#aaa'}}>
          <div align = "right"> 
            <span className={styles.user}> <Icon type="user"/></span>
            <Dropdown />
          </div>
      </Header>
      <Layout>
        <Sider width={256} style={{background: '#fff'}}>
          <Sidebar />
        </Sider>
        <Layout>
          <Content>
            <div className={styles.normal}>
            <h1 className={styles.title}>This is the demo page</h1>
            <div className={styles.welcome} />
            <ul className={styles.list}>
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

IndexPage.propTypes = {
};

export default connect()(IndexPage);
