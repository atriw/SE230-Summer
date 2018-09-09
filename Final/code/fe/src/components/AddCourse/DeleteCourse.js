import {Modal, Icon } from 'antd';
import React from 'react';
import 'react-dom';
import axios from 'axios'

class DeleteCourse extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            visible: false
        }
    }

    handleOk = () => {
        console.log(this.props.id);
        axios.post('/api/course/delete', {
            id: this.props.id
        })
            .then((res) => {
                let data = res.data;
                if (data === true) {
                    alert('删除成功');
                    this.props.history.push('/')
                } else {
                    alert('删除失败');
                }
            })
            .catch((error) => {
                console.log(error);
            });
        this.setState({
            visible: false
        });
    };

    handleCancel = () => {
        this.setState({
            visible: false,
        });
    };

    showModal = () => {
        this.setState({
            visible: true,
        })
    };

    render() {
        return (
            <div>
                <Icon type = "delete" onClick = {this.showModal} />
                <Modal
                    visible = {this.state.visible}
                    title = '删除课程'
                    okText = '确认删除'
                    cancelText = '取消'
                    onCancel = {this.handleCancel}
                    onOk = {this.handleOk}
                    okButtonProps={{ type: "danger" }}
                >
                    Are you sure to delete this course ?
                </Modal>
            </div>
        );
    }
}

export default DeleteCourse;