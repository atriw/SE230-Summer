import React from 'react';
import { Divider } from 'antd';
import EmailPopUp from './EmailPopUp';
import PhonePopUp from './PhonePopUp';
import ModifyForm from './ModifyForm';
import DeleteAccountPopUp from './DeleteAccountPopUp';
import UserContext from '../../userContext'

/* Author: He Rongjun
 * Time: 2018/7/7
 * parameters: null
 * Intro: It combine several samll components into a big one, and render them.
 */
class PersonalInfo extends React.Component{
    render(){
        return(
            <UserContext.Consumer>
                { (value) =>
                    <div>
                        <Divider orientation="left">更改密码</Divider>
                        <ModifyForm/>
                        <Divider orientation="left">更改邮箱</Divider>
                        {value === null ? null : value.email}
                        <EmailPopUp/>
                        <Divider orientation="left">更改号码</Divider>
                        {value === null ? null : value.phone}
                        <PhonePopUp/>
                        <Divider orientation="left">删除账号</Divider>
                        <DeleteAccountPopUp/>
                        <div className="fill"/>
                    </div>
                }
            </UserContext.Consumer>
        );
    }
}

export default PersonalInfo;