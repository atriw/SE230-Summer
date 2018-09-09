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
            oldPasswordHelp:"Type your Old password",
            newPasswordOk: null,
            newPasswordAgainOk: null,
            newPassword: null,
            newPasswordAgain: null,
        };
    }
    // check old password's format
    checkOldPassword = (e) =>{
        if(e.target.value===""){
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
                        oldPasswordHelp:"Wrong password!"
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
                oldPasswordHelp:"Type your Old password",
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
        let patt=new RegExp('^.{6,16}$');
        if (patt.test(e.target.value)){
            if (e.target.value === this.state.newPasswordAgain){
                this.setState({
                newPasswordOk: 'success',
                newPasswordAgainOk: 'success',
                newPassword: e.target.value,
                })
            }
            else{
                this.setState({
                newPasswordOk: 'success',
                newPasswordAgainOk: 'error',
                newPassword: e.target.value,
                })
            }
            }
            else{
            this.setState({
                newPasswordOk: 'error',
                newPasswordAgainOk: 'error',
                newPassword: e.target.value,
            });
            }
            if (this.state.newPasswordAgain === null){
            this.setState({
                newPasswordAgainOk: 'error',
            });
        }
    };

    // check new password again's format and compare it with the previous one
    checkNewPasswordAgain = (e) =>{
        let patt=new RegExp('^.{6,16}$');
        if (patt.test(e.target.value)){
            if (e.target.value === this.state.newPassword){
            this.setState({
                newPasswordOk: 'success',
                newPasswordAgainOk: 'success',
                newPasswordAgain: e.target.value,
            })
            }
            else{
            this.setState({
                newPasswordAgainOk: 'error',
                newPasswordAgain: e.target.value,
            })
            }
        }
        else{
        this.setState({
            newPasswordAgainOk: 'error',
            newPasswordAgain: e.target.value,
        });
        }
        
    };

    // handle things when user click 修改密码 
    handleSubmit = (e) =>{
        e.preventDefault();
        if (this.state.newPasswordAgainOk === 'error' || this.state.oldPasswordOk !== 'success'){
            alert('请确保两次输入的密码一致');
            return;
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
                    alert('修改成功')
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
                    <FormItem className = "formItem" hasFeedback validateStatus={this.state.newPasswordOk}   help="Type your new password">
                        <Input placeholder="新密码" id="newPassword" type="password" onChange={this.checkNewPassword}/>
                    </FormItem>
                    <FormItem className = "formItem" hasFeedback validateStatus={this.state.newPasswordAgainOk} help="Confirm your new password">
                        <Input placeholder="确认新密码" id="newPasswordAgain" type="password" onChange={this.checkNewPasswordAgain}/>
                    </FormItem>
                    <Button className = "changeButton" type="primary" htmlType="submit">修改密码</Button>
                </Form>
            </div>
        );
    }
}

export default ModifyForm;