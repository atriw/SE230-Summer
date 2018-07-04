import React from 'react';
import {Link} from 'react-router-dom';
import { Menu, Icon } from 'antd';

//const SubMenu = Menu.SubMenu;

class Sidebar extends React.Component {
  handleClick = (e) => {
    console.log('click ', e);
  }

  render() {
    return (
      <Menu
        onClick={this.handleClick}
        style={{ height: '100%', width: 256 }}
        mode="inline"
      >
            <Menu.Item key="1">   
            <Link to = "statistics">                 
                <Icon type="book" />
                <span>最近课程</span>
            </Link>
            </Menu.Item>    
            <Menu.Item key="2">       
            <Link to = "allcourses">             
                <Icon type="edit" />
                <span>课程管理</span>
            </Link>
            </Menu.Item>    
            <Menu.Item key="3">       
            <Link to = "/">             
                <Icon type="home" />
                <span>回到首页</span>
            </Link>
            </Menu.Item> 
      </Menu>
    );
  }
}

export default Sidebar;