import React from 'react';
import 'react-dom';
import {Form, Input, Button} from 'antd';
import axios from 'axios'

const FormItem = Form.Item;


/* Author: He Rongjun
 * Time: 2018/7/7
 * parameters: null
 * Intro: It handle things about modifying password,user need to type old password to validate and type
 * new password twice to make sure user remember his new password. And warn user when the two new passwords
 * are different. 
 */
class ModifyForm extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            oldPassword: null,
            oldPasswordOk: null,
            oldPasswordHelp:"请输入旧密码",
            newPasswordOk: null,
            newPasswordHelp:"请输入新密码",
            newPasswordAgainOk: null,
            newPasswordAgainHelp:"请再次输入新密码",
            newPassword: null,
            newPasswordAgain: null,
        };
    }
    // check old password's format
    checkOldPassword = (e) =>{
        if(e.target.value===""){
            this.setState({
                oldPasswordOk: 'error',
            });
            return;
        }
        axios.get('api/user/checkPw',{
            params:{
                pw:this.state.oldPassword
            }
        })
            .then((res) => {
                let data = res.data;
                if (data === true) {
                    this.setState({
                        oldPasswordOk:'success',
                        oldPasswordHelp:""
                    });
                } else {
                    this.setState({
                        oldPasswordOk:'error',
                        oldPasswordHelp:"密码错误!"
                    });
                }
            })
            .catch((error) => {
                console.log(error);
            });
    };

    //change pw in state when changed
    handleChangeOldPassword = (e) =>{
        if(e.target.value ===""){
            this.setState({
                oldPasswordHelp:"请输入旧密码",
                oldPassword:e.target.value
            })
        }
        else{
            this.setState({
                oldPassword:e.target.value
            });
        }
    };

    // check new password's format 
    checkNewPassword = (e) =>{
        if(e.target.value===""){
            this.setState({
                newPasswordOk: 'error',
            });
            return;
        }
        let patt=new RegExp('^.{6,16}$');
        if (patt.test(e.target.value)){
            this.setState({
                newPasswordOk: 'success',
                newPasswordHelp:""
            });
        }
        else{
            this.setState({
                newPasswordOk: 'error',
                newPasswordHelp:"请输入6~16位的密码"
            });
        }
        if(this.state.newPasswordAgain !== null){
            if (this.state.newPasswordAgain !== this.state.newPassword){
                this.setState({
                    newPasswordAgainOk: 'error',
                    newPasswordAgainHelp:"请确保两次输入密码一致"
                })
            }
        }
    };

    //change new pw in state when changed
    handleChangeNewPassword = (e) =>{
        if(e.target.value ===""){
            this.setState({
                newPasswordHelp:"请输入新密码",
                newPassword:e.target.value
            })
        }
        else{
            this.setState({
                newPassword:e.target.value
            });
        }
    };

    // check new password again's format and compare it with the previous one
    checkNewPasswordAgain = (e) =>{
            if (this.state.newPasswordAgain === this.state.newPassword){
                if(this.state.newPasswordOk === 'success') {
                    this.setState({
                        newPasswordAgainOk: 'success',
                        newPasswordAgainHelp: ""
                    })
                }
                else{
                    this.setState({
                        newPasswordAgainOk: 'error',
                        newPasswordAgainHelp: "请先输入正确的新密码"
                    })
                }
            }
            else{
            this.setState({
                newPasswordAgainOk: 'error',
                newPasswordAgainHelp:"请确保两次输入密码一致"
            })
            }
    };

    //change  pw again in state when changed
    handleChangeNewPasswordAgain = (e) =>{
        if(e.target.value ===""){
            this.setState({
                newPasswordAgainHelp:"请再次输入新密码",
                newPasswordAgain:e.target.value
            })
        }
        else{
            this.setState({
                newPasswordAgain:e.target.value
            });
        }
    };

    // handle things when user click 修改密码 
    handleSubmit = (e) =>{
        e.preventDefault();
        if (this.state.newPasswordAgainOk === 'error' || this.state.oldPasswordOk !== 'success'){
            alert('填写有误！请修正后再提交');
        }
        else{
            axios.post('api/user/update', {
                mode: "0",
                oldPwd: String(this.state.oldPassword),
                newPwd: String(this.state.newPasswordAgain)
            })
            .then((res) => {
                let data = res.data;
                if (data === true) {
                    alert('修改成功');
                    this.setState({
                        oldPassword: null,
                        oldPasswordOk: null,
                        oldPasswordHelp:"请输入旧密码",
                        newPasswordOk: null,
                        newPasswordHelp:"请输入新密码",
                        newPasswordAgainOk: null,
                        newPasswordAgainHelp:"请再次输入新密码",
                        newPassword: null,
                        newPasswordAgain: null,
                    })
                } else {
                    alert('修改失败，请重新输入');
                }
            })
            .catch((error) => {
                console.log(error);
            });
        }
    };

    render(){
        /*const formItemLayout = {
            labelCol: {
              xs: { span: 24 },
              sm: { span: 5 },
            },
            wrapperCol: {
              xs: { span: 24 },
              sm: { span: 5 },
            },
        };*/

        return(
            <div>
                <Form onSubmit={this.handleSubmit} className="changepassword">
                    <FormItem className = "formItem" hasFeedback validateStatus={this.state.oldPasswordOk} help={this.state.oldPasswordHelp}>
                        <Input placeholder="旧密码" id="old" type="password" onBlur={this.checkOldPassword} value={this.state.oldPassword} onChange={this.handleChangeOldPassword}/>
                    </FormItem>
                    <FormItem className = "formItem" hasFeedback validateStatus={this.state.newPasswordOk}   help={this.state.newPasswordHelp}>
                        <Input placeholder="新密码" id="newPassword" type="password" onBlur={this.checkNewPassword} value={this.state.newPassword} onChange={this.handleChangeNewPassword}/>
                    </FormItem>
                    <FormItem className = "formItem" hasFeedback validateStatus={this.state.newPasswordAgainOk} help={this.state.newPasswordAgainHelp}>
                        <Input placeholder="确认新密码" id="newPasswordAgain" type="password" onBlur={this.checkNewPasswordAgain} value={this.state.newPasswordAgain} onChange={this.handleChangeNewPasswordAgain}/>
                    </FormItem>
                    <Button className = "changeButton" type="primary" htmlType="submit">修改密码</Button>
                </Form>
            </div>
        );
    }
}

export default ModifyForm;