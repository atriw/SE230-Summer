import React from 'react';
import Dropdown from './Dropdown';
import { Icon } from 'antd';


class Avatar extends React.Component{
    render(){
        return(
            <div align = "right"> 
            <span className='user'> <Icon type="user"/></span>
            <Dropdown />
            </div>
        );
    }
}

export default Avatar;