import {Button, Modal, Form, Input} from 'antd';
import React from 'react';
import 'react-dom';

const FormItem = Form.Item;


const CollectionCreateForm = Form.create()(
    class extends React.Component {
        constructor(props){
            super();
            this.state={
                formatOk:null,
            }
        }

        // check password's format
        checkFormat = (e) =>{
            let patt=new RegExp('^.{6,16}$');
            if (patt.test(e.target.value)){
                this.setState({formatOk:'success'});
            }
            else{
                this.setState({formatOk:'error'});
            }
        };

        render() {
        const { visible, form } = this.props;
        const { getFieldDecorator } = form;
        return (
            <Modal
            visible={visible}
            title='删除账号'
            okText='确认删除'
            cancelText='取消'
            okType='danger'
            onCancel={this.handleCancel}
            onOk={this.handleOk}
            >
            <Form layout="vertical">
                <FormItem label="请输入密码" hasFeedback validateStatus={this.state.formatOk}>
                {getFieldDecorator('password', {})(<Input type="password" onChange={this.checkFormat}/>)}
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
 * Intro: it is a pop up handle things about delete account: user need to type password to validate, user click button to commit
 */
class DeleteAccountPopUp extends React.Component {
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

    // handle things when user click button 确认删除
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
            <Button className="changebutton" type="danger" onClick={this.showModal}>删除账号</Button>
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

export default DeleteAccountPopUp;