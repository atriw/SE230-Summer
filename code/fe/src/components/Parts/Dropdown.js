import React from 'react'
import { Menu, Dropdown, Icon} from 'antd'
import { Link } from 'react-router-dom'
import UserContext from '../../userContext'
import axios from 'axios'

const handleLogOut = () => {
    axios.post('/api/user/logout').catch((error) => {
        console.log(error);
    });
};

const menu = (
    <Menu>
        <Menu.Item>
            <Link to = "/personal">修改个人信息</Link>
        </Menu.Item>
        <Menu.Item>
            <Link to = "/login" onClick={handleLogOut}>登出</Link>
        </Menu.Item>
    </Menu>
);

class DropdownComp extends React.Component{
    render(){
        return (
            <UserContext.Consumer>
                {(value) => <Dropdown overlay={menu} placement="bottomCenter">
              <span>
                  {value === null ? 'username': value.name} <Icon type="down" />
              </span>
            </Dropdown>}
            </UserContext.Consumer>
        );
    }
  }

export default DropdownComp;