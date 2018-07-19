import React from 'react';
import Dropdown from './Dropdown';
import { Icon } from 'antd';


class Avatar extends React.Component{
    render(){
        return(
            <div> 
                <span className='ktws'>课堂威视</span>
                <span className='dropdown'><Dropdown /></span>
                <span className='user'> <Icon type="user"/></span>
            </div>
        );
    }
}

export default Avatar;