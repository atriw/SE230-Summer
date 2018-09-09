import {Button, Modal, Form, Input} from 'antd';
import React from 'react';
import 'react-dom';
import axios from 'axios'
import { Redirect } from 'react-router-dom';
const FormItem = Form.Item;

/* Author: He Rongjun
 * Time: 2018/7/7
 * parameters: null
 * Intro: it is a pop up handle things about modify e-mail: user need to type valid e-mail twice, 
 * it will validate e-mail's format and compare whether two e-mail are the same, user click button to commit
 */
class DeleteAccountPopUp extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            visible:false,
            password: null,
            passwordOk: null,
            redirect: false,
        };
    }

    handleOk = () => {
        if (this.state.emailAgainOk !== 'success'){

        }
        else{
        alert(this.state.emailAgain)
        axios.post('api/user/update', {
            mode: "1",
            newEmail: String(this.state.emailAgain)
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
        this.setState({
            visible: false
        });
        }
    }
    
    handleCancel = () => {
        this.setState({
            visible: false,
        });
    }

    showModal = () => {
        this.setState({
            visible: true,
        })
    }

    checkPassword = (e) => {
        let patt=new RegExp('^.{6,16}$');
        if (patt.test(e.target.value)){
            this.setState({
                passwordOk:'success',
                password: e.target.value,
            });
        }
        else{
            this.setState({passwordOk:'error'});
        }
    }

    handleOk = () => {
        if (this.state.passwordOk !== 'success'){
            alert("请输入正确的密码！");
        }
        else{
        axios.post('api/user/delete', {
        })
        .then((res) => {
            let data = res.data;
            if (data === true) {
                this.setState({
                    redirect: true,
                });
            } else {
                alert('删除失败，请重新输入');
            }
        })
        .catch((error) => {
            console.log(error);
        });
        }
    }


    

    render() {
        const { getFieldDecorator } = this.props.form;
        if (this.state.redirect){
            return <Redirect push to="/login" />
        }
        return (
        <div>
            <Button className="changebutton" type="danger" onClick={this.showModal}>删除账号</Button>
            <Modal
                visible = {this.state.visible}
                title = '删除账号'
                okText = '确认删除'
                cancelText = '取消'
                onCancel = {this.handleCancel}
                onOk = {this.handleOk}
            >
                <Form layout="vertical" >
                    <FormItem label="密码" hasFeedback validateStatus={this.state.passwordOk}>
                        {getFieldDecorator('password', {})(<Input type='password' onChange={this.checkPassword}/>)}
                    </FormItem>
                </Form>
            </Modal>
        </div>
        );
    }
}

export default Form.create()(DeleteAccountPopUp);