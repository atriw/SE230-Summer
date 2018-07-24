import {Button, Modal, Form, Input} from 'antd';
import React from 'react';
import 'react-dom';
import axios from 'axios'


const FormItem = Form.Item;


/* Author: He Rongjun
 * Time: 2018/7/7
 * parameters: null
 * Intro: it is a pop up handle things about modify e-mail: user need to type valid e-mail twice, 
 * it will validate e-mail's format and compare whether two e-mail are the same, user click button to commit
 */
class EmailPopUp extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            emailOk: null,
            emailAgainOk: null,
            email: null,
            emailAgain: null,
            visible: false
        };
    }

    // check first e-mail's format
    checkEmail = (e) =>{
        let patt = new RegExp('^([0-9A-Za-z\\-_.]+)@([0-9a-z]+\\.[a-z]{2,3}(\\.[a-z]{2})?)$');
        if (patt.test(e.target.value)){
            this.setState({
                emailOk: 'success',
                email: e.target.value,
            });
            if (this.state.emailAgain !== null){
                if (e.target.value === this.state.emailAgain){
                    this.setState({
                        emailAgainOk:'success',
                    })
                }
                else{
                    this.setState({
                        emailAgainOk:'error',
                    })
                }
            }
        }
        else{
            this.setState({
                emailOk: 'error',
                emailAgainOk: 'error',
                email: e.target.value,
            })
        }
        if (e.target.value === null){
            this.setState({
                emailOk:null
            })
        }
        if (this.state.emailAgain === null){
            this.setState({
                emailAgainOk:null
            })
        }
    };

    // check second e-mail's format
    checkEmailAgain = (e) =>{
        let patt=new RegExp('^([0-9A-Za-z\\-_.]+)@([0-9a-z]+\\.[a-z]{2,3}(\\.[a-z]{2})?)$');
        if (patt.test(e.target.value) && e.target.value === this.state.email){
            this.setState({
                emailAgainOk: 'success',
                emailAgain: e.target.value,
            })
        }
        else{
            this.setState({
                emailAgainOk: 'error',
                emailAgain: e.target.value,
            })
        }
        if (e.target.value === null){
            this.setState({
                emailAgainOk: null,
            })
        }
    };

    handleOk = () => {
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
    

    render() {
        const { getFieldDecorator } = this.props.form;
        return (
        <div>
            <Button className="changebutton" type = "primary" onClick = {this.showModal}>修改邮箱</Button>
            <Modal
                visible = {this.state.visible}
                title = '修改邮箱'
                okText = '确认修改'
                cancelText = '取消'
                onCancel = {this.handleCancel}
                onOk = {this.handleOk}
            >
                <Form layout="vertical" >
                    <FormItem label="新邮箱" hasFeedback validateStatus={this.state.emailOk}>
                        {getFieldDecorator('newEmail', {})(<Input onChange={this.checkEmail}/>)}
                    </FormItem>
                    <FormItem label="确认新邮箱" hasFeedback validateStatus={this.state.emailAgainOk}>
                        {getFieldDecorator('confirmNewEmail')(<Input onChange={this.checkEmailAgain}/>)}
                    </FormItem>
                </Form>
            </Modal>
        </div>
        );
    }
}

export default Form.create()(EmailPopUp);