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

        checkFormat=(e)=>{
            let patt=new RegExp('^.{6,16}$');
            if (patt.test(e.target.value)){
                this.setState({formatOk:'success'});
            }
            else{
                this.setState({formatOk:'error'});
            }
        }

        render() {
        const { visible, onCancel, onCreate, form } = this.props;
        const { getFieldDecorator } = form;
        return (
            <Modal
            visible={visible}
            title='删除账号'
            okText='确认删除'
            cancelText='取消'
            okType='danger'
            onCancel={onCancel}
            onOk={onCreate}
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

class DeleteAccountPopUp extends React.Component {
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
            <Button type="danger" onClick={this.showModal}>删除账号</Button>
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