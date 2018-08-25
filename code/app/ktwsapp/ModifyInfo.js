import React from 'react';
import { Text, View, ScrollView } from 'react-native';
import { Button, Modal, WhiteSpace, WingBlank, List, InputItem } from 'antd-mobile-rn';

export default class ModifyInfo extends React.Component<any, any> {
  constructor(props: any) {
    super(props);
    this.state = {
      visible: false,
      visible2: false,
      visible3: false,
      visible4: false,
      password: null,
      oldPassword: null,
      newPassword: null,
      newPassword2: null,
      newEmail: null,
      newEmail2: null,
      newPhoneNumber: null,
      newPhoneNumber2: null,
      hasError: false,
      hasError2: false,
      hasError3: false,
      hasError4: false,
      hasError5: false,
      hasError6: false,
      hasError7: false,
      hasError8: false,
    };
  }

  onClose = () => {
    this.setState({
      visible: false,
    });
  }

  onClose2 = () => {
    this.setState({
      visible2: false,
    });
  }

  onClose3 = () => {
    this.setState({
      visible3: false,
    });
  }

  onClose4 = () => {
    this.setState({
      visible4: false,
    });
  }

  onChange = (value) =>{
    let patt=new RegExp('^.{6,16}$');
      if (patt.test(value)){
         this.setState({
             hasError: false,
             oldPassword: value,
         });
    }
    else{
      this.setState({
        hasError: true,
        oldPassword: value,
      });
    }
  }

  onChange2 = (value)=>{
    let patt=new RegExp('^.{6,16}$');
    if (patt.test(value)){
      if (value === this.state.newPassword2){
        this.setState({
          hasError2: false,
          hasError3: false,
          newPassword: value,
        })
      }
      else{
        this.setState({
          hasError2: false,
          hasError3: true,
          newPassword: value,
        })
      }
    }
    else{
      this.setState({
        hasError2: true,
        hasError3: true,
        newPassword: value,
      });
    }
    if (this.state.newPassword2 === null){
      this.setState({
        hasError3: false,
      });
    }
  }

  onChange3 = (value)=>{
    let patt=new RegExp('^.{6,16}$');
    if (patt.test(value)){
         if (value === this.state.newPassword){
           this.setState({
             hasError2: false,
             hasError3: false,
             newPassword2: value,
           })
         }
         else{
           this.setState({
             hasError3: true,
             newPassword2: value,
           })
         }
    }
    else{
      this.setState({
        hasError3: true,
        newPassword2: value,
      });
    }

  }

  onChange4 = (value)=>{
    let patt=new RegExp('^([0-9A-Za-z\\-_.]+)@([0-9a-z]+\\.[a-z]{2,3}(\\.[a-z]{2})?)$');
    if (patt.test(value)){
      if (value === this.state.newEmail2){
        this.setState({
          hasError4: false,
          hasError5: false,
          newEmail: value,
        })
      }
      else{
        this.setState({
          hasError4: false,
          hasError5: true,
          newEmail: value,
        })
      }
    }
    else{
      this.setState({
        hasError4: true,
        hasError5: true,
        newEmail: value,
      });
    }
    if (this.state.newEmail2 === null){
      this.setState({
        hasError5: false,
      });
    }
  }

  onChange5 = (value)=>{
    let patt=new RegExp('^([0-9A-Za-z\\-_.]+)@([0-9a-z]+\\.[a-z]{2,3}(\\.[a-z]{2})?)$');
    if (patt.test(value)){
         if (value === this.state.newEmail){
           this.setState({
             hasError4: false,
             hasError5: false,
             newEmail2: value,
           })
         }
         else{
           this.setState({
             hasError5: true,
             newEmail2: value,
           })
         }
    }
    else{
      this.setState({
        hasError5: true,
        newEmail2: value,
      });
    }
  }
  onChange6 = (value)=>{
    let patt=new RegExp('^[1][3,4,5,7,8][0-9]{9}$');
    if (patt.test(value)){
      if (value === this.state.newPhoneNumber2){
        this.setState({
          hasError6: false,
          hasError7: false,
          newPhoneNumber: value,
        })
      }
      else{
        this.setState({
          hasError6: false,
          hasError7: true,
          newPhoneNumber: value,
        })
      }
    }
    else{
      this.setState({
        hasError6: true,
        hasError7: true,
        newPhoneNumber: value,
      });
    }
    if (this.state.newPhoneNumber2 === null){
      this.setState({
        hasError7: false,
      });
    }
  }
  onChange7 = (value)=>{
    let patt=new RegExp('^[1][3,4,5,7,8][0-9]{9}$');
    if (patt.test(value)){
         if (value === this.state.newPhoneNumber){
           this.setState({
             hasError6: false,
             hasError7: false,
             newPhoneNumber2: value,
           })
         }
         else{
           this.setState({
             hasError7: true,
             newPhoneNumber2: value,
           })
         }
    }
    else{
      this.setState({
        hasError7: true,
        newPhoneNumber2: value,
      });
    }
  }
  onChange8 = (value)=>{
     let patt=new RegExp('^.{6,16}$');
     if (patt.test(value)){
        this.setState({
          hasError8: false,
          password: value,
        });
     }
     else{
        this.setState({
          hasError8: true,
          password: value,
        });
     }
  }
  onSubmit = () => {
  /*
   * handle submit
   */

    this.setState({
       visible: false,
    });
  }

  onSubmit2 = () => {
  /*
   * handle submit
   */

    this.setState({
       visible2: false,
    });
  }

  onSubmit3 = () => {
  /*
   * handle submit
   */

    this.setState({
       visible3: false,
    });
  }

  onSubmit4 = () => {
  /*
   * handle submit
   */

    this.setState({
       visible4: false,
    });
  }

  render() {
    return (
      <ScrollView style={{ marginTop: 20 }}>
        <WingBlank>
          <Button type="primary" onClick={() => this.setState({ visible: true})}>
            修改密码
          </Button>
          <WhiteSpace />
          <Button type="primary" onClick={() => this.setState({ visible2: true })}>
            修改邮箱
          </Button>
          <WhiteSpace />
          <Button type="primary" onClick={() => this.setState({ visible3: true })}>
            修改号码
          </Button>
          <WhiteSpace />
          <Button type="warning" onClick={() => this.setState({ visible4: true })}>
            删除账号
          </Button>
        </WingBlank>
        <Modal
          title="修改密码"
          transparent
          maskClosable
          onClose={this.onClose}
          visible={this.state.visible}
          animationType="fade"
        >
          <View style={{ paddingVertical: 15 }}>
            <List>
              <InputItem
                placeholder="Old password"
                error={this.state.hasError}
                onChange={this.onChange}
                value={this.state.oldPassword}
                type="password"
              >旧密码</InputItem>
              <InputItem
                placeholder="New password"
                error={this.state.hasError2}
                onChange={this.onChange2}
                value={this.state.newPassword}
                type="password"
              >新密码</InputItem>
              <InputItem
                placeholder="Confirm new password"
                error={this.state.hasError3}
                onChange={this.onChange3}
                value={this.state.newPassword2}
                type="password"
              >确认新密码</InputItem>
            </List>
          </View>
          <Button type="primary" onClick={this.onSubmit}>
            确认修改
          </Button>
          <WhiteSpace />
          <Button type="primary" inline onClick={this.onClose}>
            取消
          </Button>
        </Modal>

        <Modal
          title="修改邮箱"
          transparent
          maskClosable
          onClose={this.onClose2}
          visible={this.state.visible2}
          animationType="fade"
        >
          <View style={{ paddingVertical: 15 }}>
            <List>
              <InputItem
                placeholder="E-mail"
                error={this.state.hasError4}
                onChange={this.onChange4}
                value={this.state.newEmail}
              >新邮箱</InputItem>
              <InputItem
                placeholder="Confirm E-mail"
                error={this.state.hasError5}
                onChange={this.onChange5}
                value={this.state.newEmail2}
              >确认新邮箱</InputItem>
            </List>
          </View>
          <Button type="primary" inline onClick={this.onSubmit2}>
            确认修改
          </Button>
          <WhiteSpace />
          <Button type="primary" inline onClick={this.onClose2}>
            取消
          </Button>
        </Modal>

        <Modal
          title="修改号码"
          transparent
          maskClosable
          onClose={this.onClose3}
          visible={this.state.visible3}
          animationType="fade"
        >
          <View style={{ paddingVertical: 15 }}>
            <List>
              <InputItem
                placeholder="Phone number"
                error={this.state.hasError6}
                onChange={this.onChange6}
                value={this.state.newPhoneNumber}
              >新号码</InputItem>
              <InputItem
                placeholder="Confirm phone number"
                error={this.state.hasError7}
                onChange={this.onChange7}
                value={this.state.newPhoneNumber2}
              >确认新号码</InputItem>
            </List>
          </View>
          <Button type="primary" inline onClick={this.onSubmit3}>
            确认修改
          </Button>
          <WhiteSpace />
          <Button type="primary" inline onClick={this.onClose3}>
            取消
          </Button>
        </Modal>

        <Modal
          title="删除账号"
          transparent
          maskClosable
          onClose={this.onClose4}
          visible={this.state.visible4}
          animationType="fade"
        >
          <View style={{ paddingVertical: 15 }}>
            <List>
              <InputItem
                placeholder="Password"
                error={this.state.hasError8}
                onChange={this.onChange8}
                value={this.state.password}
                type="password"
              >密码</InputItem>
            </List>
          </View>
          <Button type="warning" inline onClick={this.onSubmit4}>
            确认删除
          </Button>
          <WhiteSpace />
          <Button type="primary" inline onClick={this.onClose4}>
            取消
          </Button>
        </Modal>
      </ScrollView>
    );
  }
}