import React from 'react';
import { Divider , Button} from 'antd';
import CollectionsPage from './PopUp';
import ModifyForm from './ModifyForm';

class PersonalInfo extends React.Component{
    
    deleteAccount=()=>{
        console.log("删除账号");
        //empty
    }

    render(){
        return(
            <div>
                <div>
                    <Divider orientation="left">更改密码</Divider>
                    <ModifyForm />
                </div>
                <div>
                    <Divider orientation="left">更改邮箱</Divider>
                    <CollectionsPage title="更改邮箱" newOne="新邮箱" newTwo="确认新邮箱"/>
                </div>
                <div>
                    <Divider orientation="left">更改号码</Divider>
                    <CollectionsPage title="更改号码" newOne="新号码" newTwo="确认新号码"/>
                </div>
                <div>
                    <Divider orientation="left">删除账号</Divider>
                    <Button type="danger" onClick={this.deleteAccount}>删除账号</Button>
                </div>
            </div>
        );
    }
}

export default PersonalInfo;