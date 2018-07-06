import React from 'react';
import { Layout, Icon} from 'antd';
import Sidebar from '../Parts/Sidebar';
import Dropdown from '../Parts/Dropdown';
import PersonalInfo from '../Others/PersonalInfo';
const {Header, Content, Sider} = Layout;

class DemoPage extends React.Component {
    handleClick = (e) => {
      console.log('click ', e);
    }
  
    render() {
      return (
        <Layout>
        <Header className="header" style={{background:'#aaa'}}>
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
               <PersonalInfo />
            </Content>
          </Layout>
        </Layout>
        </Layout>
      );
    }
  }
  
export default DemoPage;