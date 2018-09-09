import React from 'react';
import {Link} from 'react-router-dom';
import { Menu, Icon } from 'antd';
import RoleContext from '../../roleContext'
//const SubMenu = Menu.SubMenu;

class Sidebar extends React.Component {
  render() {
    return (
        <RoleContext.Consumer>
            {(value) =>
            <Menu
                style={{height: '100%', width: 200}}
                mode="inline"
                theme="dark"
            >
                <Menu.Item key="1">
                    <Link to="/statistics">
                        <Icon type="book"/>
                        <span>最近课程</span>
                    </Link>
                </Menu.Item>
                <Menu.Item key="2">
                    <Link to="/allcourses">
                        <Icon type="edit"/>
                        <span>课程管理</span>
                    </Link>
                </Menu.Item>
                <Menu.Item key="3">
                    <Link to="/addCourse">
                        <Icon type="plus"/>
                        <span>添加课程</span>
                    </Link>
                </Menu.Item>

                {value === null ? null : ((value.indexOf("EA") !== -1) ?
                            (<Menu.Item key="4">
                                <Link to="/allteachers">
                                    <Icon type="idcard"/>
                                    <span>教师信息</span>
                                </Link>
                            </Menu.Item>)
                            : null
                    )
                }

                <Menu.Item key="5">
                    <Link to="/">
                        <Icon type="home"/>
                        <span>回到首页</span>
                    </Link>
                </Menu.Item>
            </Menu>
        }
        </RoleContext.Consumer>
    );
  }
}

export default Sidebar;