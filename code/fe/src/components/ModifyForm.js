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
            hasFeedback1:null,
            hasFeedback2:null,
            hasFeedback3:null,
            oldPasswordOk:true,
            newPasswordOk:true,
            newPasswordAgainOk:true,
            newPassword1:null,
            newPassword2:null,
        };
    }
    checkOldPassword=(e)=>{
        var patt=new RegExp("^.{6,16}$");
        var result=patt.test(e.target.value);
        if (result === false){
            this.setState({oldPasswordOk:false});
        }
        else{
            this.setState({oldPasswordOk:true});
        }
        this.setState({hasFeedback1:true})
    }

    checkNewPassword=(e)=>{
        var patt=new RegExp("^.{6,16}$");
        var result=patt.test(e.target.value);
        if (result === false){
            this.setState({newPasswordOk:false});
        }
        else{
            this.setState({newPasswordOk:true});
        }
        if (this.state.newPassword2 !== null){
            if (e.target.value !== this.state.newPassword2){
                this.setState({newPasswordOk:false})
            }
        }
        this.setState({
            hasFeedback2:true,
            newPassword1:e.target.value
        });
    }
    checkNewPasswordAgain=(e)=>{
        var patt=new RegExp("^.{6,16}$");
        var result=patt.test(e.target.value);
        if (result === false){
            this.setState({newPasswordAgainOk:false});
        }
        else{
            this.setState({newPasswordAgainOk:true});
        }
        if (this.state.newPassword1 !== null){
            if (e.target.value !== this.state.newPassword1){
                this.setState({newPasswordAgainOk:false})
            }
        }
        //only twe are true,canbe true
        this.setState({
            hasFeedback3:true,
            newPassword2:e.target.value,
        })
    }

    handleSubmit=()=>{
        console.log("修改密码");
        //empty
    }

    render(){
        return(
            <div>
                <Form onSubmit={this.handleSubmit}>
                    <FormItem {...formItemLayout}  hasFeedback={this.state.hasFeedback1}  validateStatus={this.state.oldPasswordOk? "success":"error"} help="Type your Old password">
                        <Input placeholder="旧密码" id="old" type="password" onChange={this.checkOldPassword}/>
                    </FormItem>
                    <FormItem {...formItemLayout}  hasFeedback={this.state.hasFeedback2} validateStatus={this.state.newPasswordOk? "success":"error"}   help="Type your new password">
                        <Input placeholder="新密码" id="newPassword" type="password" onChange={this.checkNewPassword}/>
                    </FormItem>
                    <FormItem {...formItemLayout}   hasFeedback={this.state.hasFeedback3} validateStatus={this.state.newPasswordAgainOk? "success":"error"} help="Confirm your new password">
                        <Input placeholder="确认新密码" id="newPasswordAgain" type="password" onChange={this.checkNewPasswordAgain}/>
                    </FormItem>
                    <Button type="primary" htmlType="submit">修改密码</Button>
                </Form>
            </div>
        );
    }
}

export default ModifyForm;