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
class PhonePopUp extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            phoneOk: null,
            phoneAgainOk: null,
            phone: null,
            phoneAgain: null,
            visible: false
        };
    }

    // check first e-mail's format
    checkPhone = (e) =>{
        let patt = new RegExp('^([0-9A-Za-z\\-_.]+)@([0-9a-z]+\\.[a-z]{2,3}(\\.[a-z]{2})?)$');
        if (patt.test(e.target.value)){
            this.setState({
                phoneOk: 'success',
                phone: e.target.value,
            });
            if (this.state.phoneAgain !== null){
                if (e.target.value === this.state.phoneAgain){
                    this.setState({
                        phoneAgainOk:'success',
                    })
                }
                else{
                    this.setState({
                        phoneAgainOk:'error',
                    })
                }
            }
        }
        else{
            this.setState({
                phoneOk: 'error',
                phoneAgainOk: 'error',
                phone: e.target.value,
            })
        }
        if (e.target.value === null){
            this.setState({
                phoneOk:null
            })
        }
        if (this.state.phoneAgain === null){
            this.setState({
                phoneAgainOk:null
            })
        }
    };

    // check second e-mail's format
    checkPhoneAgain = (e) =>{
        let patt=new RegExp('^([0-9A-Za-z\\-_.]+)@([0-9a-z]+\\.[a-z]{2,3}(\\.[a-z]{2})?)$');
        if (patt.test(e.target.value) && e.target.value === this.state.phone){
            this.setState({
                phoneAgainOk: 'success',
                phoneAgain: e.target.value,
            })
        }
        else{
            this.setState({
                phoneAgainOk: 'error',
                phoneAgain: e.target.value,
            })
        }
        if (e.target.value === null){
            this.setState({
                phoneAgainOk: null,
            })
        }
    };

    handleOk = () => {
        alert(this.state.phoneAgain)
        axios.post('api/user/update', {
            mode: "2",
            newPhone: String(this.state.phoneAgain)
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
            <Button className="changebutton" type = "primary" onClick = {this.showModal}>修改号码</Button>
            <Modal
                visible = {this.state.visible}
                title = '修改号码'
                okText = '确认修改'
                cancelText = '取消'
                onCancel = {this.handleCancel}
                onOk = {this.handleOk}
            >
                <Form layout="vertical">
                    <FormItem label="新号码" hasFeedback validateStatus={this.state.phoneOk}>
                        {getFieldDecorator('newPhone', {})(<Input onChange={this.checkPhone}/>)}
                    </FormItem>
                    <FormItem label="确认新号码" hasFeedback validateStatus={this.state.phoneAgainOk}>
                        {getFieldDecorator('confirmNewPhone')(<Input onChange={this.checkPhoneAgain}/>)}
                    </FormItem>
                </Form>
            </Modal>
        </div>
        );
    }
}

export default Form.create()(PhonePopUp);