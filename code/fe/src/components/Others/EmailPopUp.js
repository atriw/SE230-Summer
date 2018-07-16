import {Button, Modal, Form, Input} from 'antd';
import React from 'react';
import 'react-dom';

const FormItem = Form.Item;

const CollectionCreateForm = Form.create()(
    class extends React.Component {
        constructor(props){
            super(props);
            this.state = {
                emailOk: null,
                emailAgainOk: null,
                email: null,
                emailAgain: null,
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
                mode: 1,
                newEmail: this.state.emailAgain
            })
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
                    visible = {visible}
                    title = '修改邮箱'
                    okText = '确认修改'
                    cancelText = '取消'
                    onCancel = {handleCancel}
                    onOk = {handleOk}
                >
                <Form layout="vertical">
                    <FormItem label="新邮箱" hasFeedback validateStatus={this.state.emailOk}>
                        {getFieldDecorator('newEmail', {})(<Input onChange={this.checkEmail}/>)}
                    </FormItem>
                    <FormItem label="确认新邮箱" hasFeedback validateStatus={this.state.emailAgainOk}>
                        {getFieldDecorator('confirmNewEmail')(<Input onChange={this.checkEmailAgain}/>)}
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
 * Intro: it is a pop up handle things about modify e-mail: user need to type valid e-mail twice, 
 * it will validate e-mail's format and compare whether two e-mail are the same, user click button to commit
 */
class EmailPopUp extends React.Component {
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

    // handle things when user click button 修改邮箱
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
            <Button type = "primary" onClick = {this.showModal}>修改邮箱</Button>
            <CollectionCreateForm
                wrappedComponentRef = {this.saveFormRef}
                visible = {this.state.visible}
                onCancel = {this.handleCancel}
                onCreate = {this.handleCreate}
            />
        </div>
        );
    }
}

export default EmailPopUp;