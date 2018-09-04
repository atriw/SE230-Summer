import React from 'react';
import { Text, View, ScrollView, StyleSheet, Dimensions, TouchableOpacity, Modal,  Button } from 'react-native';
import { WhiteSpace, WingBlank, List, InputItem } from 'antd-mobile-rn';
import Icon from 'react-native-vector-icons/MaterialIcons';

export default class ModifyInfo extends React.Component {

  static navigationOptions = {
    header: null,
  }
  constructor(props) {
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
      <View style = {styles.container}>
          <View style={styles.top}>
            <TouchableOpacity
              onPress={() => this.props.navigation.navigate('Personal')}>
              <Icon name='chevron-left' style={{fontSize:25}} color='white'/>
            </TouchableOpacity>
            <View style={styles.titleCenter}>
              <Text style={styles.topText}>
                设置
              </Text>
            </View>
          </View>
      <ScrollView style={{ marginTop: 20 }}>
          <TouchableOpacity style={styles.modifyInfo} onPress={() => this.setState({ visible: true})}>
            <View style={styles.textContainer}><Text style={styles.bodyText}>修改密码</Text></View>
          </TouchableOpacity>
          <TouchableOpacity style={styles.modifyInfo} onPress={() => this.setState({ visible2: true})}>
            <View style={styles.textContainer}><Text style={styles.bodyText}>邮箱</Text></View>
          </TouchableOpacity>
          <TouchableOpacity style={styles.modifyInfo} onPress={() => this.setState({ visible3: true})}>
            <View style={styles.textContainer}><Text style={styles.bodyText}>手机</Text></View>
          </TouchableOpacity>
          <TouchableOpacity style={styles.signOut}>
            <View style={styles.textContainer}><Text style={styles.signOutText}>退出登录</Text></View>
          </TouchableOpacity>
          
        <Modal
          title="修改密码"
          transparent={true}
          presentationStyle='overFullScreen'
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
          <Button title='确认修改' onPress={this.onSubmit}/>

          <Button title='取消' inline onPress={this.onClose}/>
        </Modal>

        <Modal
          title="修改邮箱"
          transparent={true}
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
          <Button title='确认修改' onPress={this.onSubmit2}/>

          <Button title='取消' inline onPress={this.onClose2}/>
        </Modal>

        <Modal
          title="修改号码"
          transparent={true}
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
          <Button title='确认修改' onPress={this.onSubmit3}/>

          <Button title='取消' inline onPress={this.onClose3}/>
        </Modal>

        <Modal
          title="删除账号"
          transparent={true}
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
          <Button title='确认修改' onPress={this.onSubmit4}/>

          <Button title='取消' inline onPress={this.onClose4}/>
        </Modal>
      </ScrollView>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F3F3F3',
  },
  titleCenter: {
    width: Dimensions.get('window').width,
    position:'absolute',
    alignItems:'center',
    paddingTop: 20,
    paddingBottom: 20,
  },
  top: {
    backgroundColor: 'rgba(155,207,246, 1)',
    flexDirection: 'row',
    paddingTop: 20,
    paddingBottom: 20,
  },
  topText: {
    fontSize: 17,
    color: 'white',
  },
  modifyInfo: {
    backgroundColor:'#FFFFFF',
    alignItems: 'flex-start',
    marginTop: 5,
    marginBottom: 5,
  },
  signOut: {
    backgroundColor:'#FFFFFF',
    alignItems: 'center',
    marginTop: 5,
    marginBottom: 5,
  },
  textContainer: {
    alignItems: 'center',
    backgroundColor:'#FFFFFF',
    marginLeft: 10,
    height:40,
  },
  bodyText: {
    marginTop: 8,
    fontSize: 16,
    color: 'black',
  },
  signOutText:{
    marginTop: 8,
    fontSize:16,
    color: 'red',
  }
});