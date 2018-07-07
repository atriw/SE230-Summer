import {Button, Modal, Form, Input} from 'antd';
import React from 'react';
import 'react-dom';

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
        checkphoneNumber=(e)=>{
            let patt=new RegExp('^[0-9]{11}$');
            if (patt.test(e.target.value)){
                this.setState({
                    phoneNumberOk:'success',
                    phoneNumber:e.target.value,
                })
                if (this.state.phoneNumberAgain !== null){
                    if (e.target.value===this.state.phoneNumberAgain){
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
            if (e.target.value===null){
                this.setState({
                    phoneNumberOk:null
                })
            }
            if (this.state.phoneNumberAgain === null){
                this.setState({
                    phoneNumberAgainOk:null
                })
            }
        }

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
        }

        render() {
        const { visible, onCancel, onCreate, form } = this.props;
        const { getFieldDecorator } = form;
        return (
            <Modal
            visible={visible}
            title='修改号码'
            okText='确认修改'
            cancelText='取消'
            onCancel={onCancel}
            onOk={onCreate}
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

    showModal = () => {
        this.setState({ visible: true });
    }

    handleCancel = () => {
        this.setState({ visible: false });
    }

    handleCreate = () => {
        const form = this.formRef.props.form;
        form.resetFields();
        this.setState({ visible: false });
    }

    saveFormRef = (formRef) => {
        this.formRef = formRef;
    }

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