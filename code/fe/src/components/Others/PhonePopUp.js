import {Button, Modal, Form, Input} from 'antd';
import React from 'react';
import 'react-dom';
import axios from 'axios'

const FormItem = Form.Item;


const CollectionCreateForm = Form.create()(
    class extends React.Component {
        constructor(props){
            super(props);
            this.state={
                phoneNumberOk:null,
                phoneNumberAgainOk:null,
                phoneNumber:null,
                phoneNumberAgain:null,
            };
        }

        // check first phone number's format
        checkphoneNumber=(e)=>{
            let patt=new RegExp('^[0-9]{11}$');
            if (patt.test(e.target.value)){
                this.setState({
                    phoneNumberOk:'success',
                    phoneNumber:e.target.value,
                });
                if (this.state.phoneNumberAgain !== null){
                    if (e.target.value === this.state.phoneNumberAgain){
                        this.setState({
                            phoneNumberAgainOk:'success',
                        })
                    }
                    else{
                        this.setState({
                            phoneNumberAgainOk:'error',
                        })
                    }
                }
            }
            else{
                this.setState({
                    phoneNumberOk:'error',
                    phoneNumberAgainOk:'error',
                    phoneNumber:e.target.value,
                })
            }
            if (e.target.value === null){
                this.setState({
                    phoneNumberOk:null
                })
            }
            if (this.state.phoneNumberAgain === null){
                this.setState({
                    phoneNumberAgainOk:null
                })
            }
        };

        // check second phone number's format
        checkphoneNumberAgain=(e)=>{
            let patt=new RegExp('^[0-9]{11}$');
            if (patt.test(e.target.value) && e.target.value === this.state.phoneNumber){
                this.setState({
                    phoneNumberAgainOk:'success',
                    phoneNumberAgain:e.target.value,
                })
            }
            else{
                this.setState({
                    phoneNumberAgainOk:'error',
                    phoneNumberAgain:e.target.value,
                })
            }
            if (e.target.value === null){
                this.setState({
                    phoneNumberAgainOk:null,
                })
            }
        };

        handleOk = () => {
            axios.post('api/user/update', {
                mode: 2,
                newEmail: this.state.phoneNumberAgain
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

        render() {
        const { visible, form } = this.props;
        const { getFieldDecorator } = form;
        return (
            <Modal
            visible={visible}
            title='修改号码'
            okText='确认修改'
            cancelText='取消'
            onCancel={this.handleCancel}
            onOk={this.handleOk}
            >
            <Form layout="vertical">
                <FormItem label="新号码" hasFeedback validateStatus={this.state.phoneNumberOk}>
                {getFieldDecorator('newPhoneNumber', {})(<Input onChange={this.checkphoneNumber}/>)}
                </FormItem>
                <FormItem label="确认新号码" hasFeedback validateStatus={this.state.phoneNumberAgainOk}>
                {getFieldDecorator('confirmNewPhoneNumber')(<Input onChange={this.checkphoneNumberAgain}/>)}
                </FormItem>
            </Form>
            </Modal>
        );
        }
    }
);

/* Author: He Rongjun
 * Time: 2018/7/7
 * parameters: null
 * Intro: It handle things about modifying phone number, user need to type new phone number twice, it will compare
 * the two phone number to validate wether they are tha same, user click button to commit
 */
class PhonePopUp extends React.Component {
    constructor(props){
        super(props);
        this.state={
            visible:false
        }
    }

     // show pop-up
    showModal = () => {
        this.setState({ visible: true });
    };

    // Cancel when user click cancel
    handleCancel = () => {
        this.setState({ visible: false });
    };

    // handle things when user click button 修改号码
    handleCreate = () => {
        const form = this.formRef.props.form;
        form.resetFields();
        this.setState({ visible: false });
    };

    // call this when create a pop-up
    saveFormRef = (formRef) => {
        this.formRef = formRef;
    };

    render() {
        return (
        <div>
            <Button type="primary" onClick={this.showModal}>修改号码</Button>
            <CollectionCreateForm
            wrappedComponentRef={this.saveFormRef}
            visible={this.state.visible}
            onCancel={this.handleCancel}
            onCreate={this.handleCreate}
            />
        </div>
        );
    }
}

export default PhonePopUp;