import React from 'react';
import Dropdown from './Dropdown';
import { Icon } from 'antd';
import { Link } from 'react-router-dom'


class Avatar extends React.Component{
    render(){
        return(
            <div> 
                <Link to = '/'><span className='ktws'>课堂威视</span></Link>
                <span className='dropdown'><Dropdown /></span>
                <span className='user'> <Icon type="user"/></span>
            </div>
        );
    }
}

export default Avatar;