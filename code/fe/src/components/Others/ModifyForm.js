import React from 'react';
import 'react-dom';
import {Form,  Input, Button} from 'antd';

const FormItem = Form.Item;
const formItemLayout = {
    labelCol: {
      xs: { span: 24 },
      sm: { span: 5 },
    },
    wrapperCol: {
      xs: { span: 24 },
      sm: { span: 5 },
    },
};

class ModifyForm extends React.Component{
    constructor(props){
        super(props);
        this.state={
            oldPasswordOk:null,
            newPasswordOk:null,
            newPasswordAgainOk:null,
            newPassword:null,
            newPasswordAgain:null,
        };
    }
    checkOldPassword=(e)=>{
        let patt=new RegExp("^.{6,16}$");
        if (patt.test(e.target.value)){
            this.setState({oldPasswordOk:"success"});
        }
        else{
            this.setState({oldPasswordOk:"error"});
        }
    }

    checkNewPassword=(e)=>{
        let patt=new RegExp("^.{6,16}$");
        if (patt.test(e.target.value)){
            this.setState({
                newPasswordOk:"success",
                newPassword:e.target.value,
            })
            if (this.state.newPasswordAgain!==null){
                if (e.target.value===this.state.newPasswordAgain){
                    this.setState({
                        newPasswordAgainOk:"success",
                    })
                }
                else{
                    this.setState({
                        newPasswordAgainOk:"error",
                    })
                }
            }
        }
        else{
            this.setState({
                newPasswordOk:"error",
                newPasswordAgainOk:"error",
                newPassword:e.target.value,
            })
        }
        if (e.target.value===null){
            this.setState({
                newPasswordOk:null
            })
        }
        if (this.state.newPasswordAgain === null){
            this.setState({
                newPasswordAgainOk:null
            })
        }
        
    }
    checkNewPasswordAgain=(e)=>{
        let patt=new RegExp("^.{6,16}$");
        if (patt.test(e.target.value) && e.target.value === this.state.newPassword){
            this.setState({
                newPasswordAgainOk:"success",
                newPasswordAgain:e.target.value,
            })
        }
        else{
            this.setState({
                newPasswordAgainOk:"error",
                newPasswordAgain:e.target.value,
            })
        }
        if (e.target.value === null){
            this.setState({
                newPasswordAgainOk:null,
            })
        }
        
    }

    handleSubmit=()=>{
        console.log("修改密码");
        //empty
    }

    render(){
        return(
            <div>
                <Form onSubmit={this.handleSubmit}>
                    <FormItem {...formItemLayout}  hasFeedback validateStatus={this.state.oldPasswordOk} help="Type your Old password">
                        <Input placeholder="旧密码" id="old" type="password" onChange={this.checkOldPassword}/>
                    </FormItem>
                    <FormItem {...formItemLayout}  hasFeedback validateStatus={this.state.newPasswordOk}   help="Type your new password">
                        <Input placeholder="新密码" id="newPassword" type="password" onChange={this.checkNewPassword}/>
                    </FormItem>
                    <FormItem {...formItemLayout}   hasFeedback validateStatus={this.state.newPasswordAgainOk} help="Confirm your new password">
                        <Input placeholder="确认新密码" id="newPasswordAgain" type="password" onChange={this.checkNewPasswordAgain}/>
                    </FormItem>
                    <Button type="primary" htmlType="submit">修改密码</Button>
                </Form>
            </div>
        );
    }
}

export default ModifyForm;