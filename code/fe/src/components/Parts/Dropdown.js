import React from 'react'
import { Menu, Dropdown, Icon} from 'antd'
import { Link } from 'react-router-dom'
const menu = (
    <Menu>
        <Menu.Item>
            <Link to = "/personal">修改个人信息</Link>
        </Menu.Item>
        <Menu.Item>
            <Link to = "/login">登出</Link>
        </Menu.Item>
    </Menu>
);

const DropdownComp = () => {
    return (
     <Dropdown overlay={menu}>
       <span>
        username <Icon type="down" />
       </span>
     </Dropdown>
    );
  };

export default DropdownComp;