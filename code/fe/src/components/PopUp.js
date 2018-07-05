import {Button, Modal, Form, Input} from 'antd';
import React from 'react';
import 'react-dom';

const FormItem = Form.Item;

const CollectionCreateForm = Form.create()(
    class extends React.Component {
        render() {
        const { visible, onCancel, onCreate, form } = this.props;
        const { getFieldDecorator } = form;
        return (
            <Modal
            visible={visible}
            title={this.props.title}
            okText="确认修改"
            cancelText="取消"
            onCancel={onCancel}
            onOk={onCreate}
            >
            <Form layout="vertical">
                <FormItem label={this.props.newOne}>
                {getFieldDecorator('title', {})(<Input />)}
                </FormItem>
                <FormItem label={this.props.newTwo}>
                {getFieldDecorator('description')(<Input type="textarea" />)}
                </FormItem>
            </Form>
            </Modal>
        );
        }
    }
);

class CollectionsPage extends React.Component {
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
        // handle click modify
        console.log("修改");
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
            <Button type="primary" onClick={this.showModal}>{this.props.title}</Button>
            <CollectionCreateForm
            wrappedComponentRef={this.saveFormRef}
            visible={this.state.visible}
            onCancel={this.handleCancel}
            onCreate={this.handleCreate}
            title={this.props.title}
            newOne={this.props.newOne}
            newTwo={this.props.newTwo}
            />
        </div>
        );
    }
}

export default CollectionsPage;