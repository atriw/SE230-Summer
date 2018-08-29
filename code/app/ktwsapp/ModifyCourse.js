import React from 'react';
import ModifyCourseForm from './ModifyCourseForm';
import { Text, View, ScrollView } from 'react-native';
import { Button, Modal, WhiteSpace, WingBlank, List, InputItem } from 'antd-mobile-rn';

export default class ModifyCourse extends React.Component{
  state = {
    visible: false,
  }

  onClose = () => {
    this.setState({
      visible: false,
    });
  }

  onSubmit = () => {
  /*
   * handle submit
   */

    this.setState({
       visible: false,
    });
  }

  render(){
    return(
      <ScrollView style={{ marginTop: 20 }}>
        <WingBlank>
          <Button type="primary" onClick={() => this.setState({ visible: true})}>
          修改课程信息
          </Button>
        </WingBlank>

        <Modal
          transparent
          maskClosable
          onClose={this.onClose}
          visible={this.state.visible}
          animationType="fade"
        >
          <View style={{ paddingVertical: 15 }}>
            <ModifyCourseForm />
          </View>
          <WhiteSpace />
          <Button type="primary" inline onClick={this.onClose}>
            取消
          </Button>
        </Modal>
      </ScrollView>
    );
  }

}