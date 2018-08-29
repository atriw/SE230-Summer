import React from 'react';
import { Text, View, ScrollView } from 'react-native';
import { Button, Modal, WhiteSpace, WingBlank, List, InputItem } from 'antd-mobile-rn';

export default class ShowInfo extends React.Component<any, any> {
  constructor(props: any) {
    super(props);
    this.state = {

    };
  }

  render() {
    return (
      <List renderHeader={() => '个人信息'}>
        <InputItem
          value={this.props.username}
          editable={false}
        >用户名</InputItem>
        <InputItem
          value={this.props.email}
          editable={false}
        >邮箱</InputItem>
        <InputItem
          value={this.props.phoneNumber}
          editable={false}
        >电话号码</InputItem>
      </List>
    );
  }
}