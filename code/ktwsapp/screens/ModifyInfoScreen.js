import React from 'react';
import { Text, View, ScrollView, Dimensions, StyleSheet, TouchableOpacity, TextInput,  Button } from 'react-native';
import Icon from 'react-native-vector-icons/MaterialIcons';
import Request from '../request'
export default class ModifyInfo extends React.Component {
  constructor(props){
    super(props)
    this.state=({
      userName:'',
      password:'',
      newPassword:'',
      newPasswordAgain:'',
      email:'',
      phone:'',
    })
  }
  static navigationOptions = {
    header: null,
  }

  componentWillMount(){
    Request.get('/api/user/userInfo')
    .then((res) => {
      let data = res.data
      this.setState({
        email:data.email,
        phone:data.phone,
      })
    })
    .catch((error) => {
        console.log(error);
    });
  }

  check = (mode) =>{
    if(mode===0){
      if(!this.state.password ||!this.state.newPassword||!this.state.newPasswordAgain){
        alert('请确认输入不为空')
        return false
      }
      if(this.state.newPassword != this.state.newPasswordAgain){
        alert('请确认两次输入的密码一致')
        return false
      }
    }
    else if(mode===1){
      if(!this.state.email){
        alert('请确保邮箱不为空')
        return false
      }
      let patt=new RegExp('^([0-9A-Za-z\\-_.]+)@([0-9a-z]+\\.[a-z]{2,3}(\\.[a-z]{2})?)$');
      if (!patt.test(this.state.email)){
        alert('请确认输入的邮箱格式正确')
        return false
      }
    }
    else{
      if(!this.state.phone){
        alert('请确保手机不为空')
        return false
      }
      let patt = new RegExp("^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\\d{8}$");
      if (!patt.test(this.state.phone)){
        alert('请确认输入的手机格式正确')
        return false
      }
    }
    return true
  }

  submit = (mode) => {
    const UPDATE_URL = '/api/user/update'
    if(mode==='0'){
      if(!this.check(0))
        return false
      Request.post(UPDATE_URL,{
        mode:mode,
        oldPwd: this.state.password,
        newPwd: this.state.newPassword
      })
      .then((res) => {
          let data = res.data
          if(data === true){
            alert('修改成功')
          }
          else{
            alert('修改失败，请重新输入')
          }
      })
      .catch((error) => {
          console.log(error);
      });
    }
    else if(mode==='1'){
      if(!this.check(1))
        return false
        Request.post(UPDATE_URL,{
          mode:mode,
          newEmail: this.state.email
        })
        .then((res) => {
          let data = res.data
            if(data === true){
              alert('修改成功')
            }
            else{
              alert('修改失败，请重新输入')
            }
        })
        .catch((error) => {
            console.log(error);
        });
    }
    else if(mode==='2'){
      if(!this.check(2))
        return false
        Request.post(UPDATE_URL,{
          mode:mode,
          newPhone: this.state.phone
        })
        .then((res) => {
          let data = res.data
            if(data === true){
              alert('修改成功')
            }
            else{
              alert('修改失败，请重新输入')
            }
        })
        .catch((error) => {
            console.log(error);
        });
    }
    this.props.navigation.navigate('Setting')
  }

  render() {
    const title = this.props.navigation.getParam('title')
    if (title === '修改密码'){
    return (
        <View style = {styles.container}>
            <View style={styles.top}>
            <TouchableOpacity
              onPress={() => this.props.navigation.navigate('Setting')}>
              <Icon name='chevron-left' style={{fontSize:25}} color='white'/>
            </TouchableOpacity>
            <View style={styles.titleCenter}>
              <Text style={styles.topText}>
                {title}
              </Text>
            </View>
          </View>
        <ScrollView style={{ marginTop: 20 }}>
            <TextInput
                style = {styles.password}
                placeholder="原密码"
                onChangeText={(text)=> this.setState({password:text})}
            />
            <TextInput
                style = {styles.newPassword}
                placeholder="新密码"
                onChangeText={(text)=> this.setState({newPassword:text})}
            />
            <TextInput
                style = {styles.input}
                placeholder="再次输入新密码"
                onChangeText={(text)=> this.setState({newPasswordAgain:text})}
            />
            <View style = {{margin:10}}>
            <Button 
                onPress={()=>this.submit('0')}
                title = '确认'
            />
            </View>
      </ScrollView>
      </View>
      );
    }

    else{
      return(
      <View style = {styles.container}>
      <View style={styles.top}>
      <TouchableOpacity
        onPress={() => this.props.navigation.navigate('Setting')}>
        <Icon name='chevron-left' style={{fontSize:25}} color='white'/>
      </TouchableOpacity>
      <View style={styles.titleCenter}>
        <Text style={styles.topText}>
          修改{title}
        </Text>
      </View>
      </View>
      <ScrollView style={{ marginTop: 20 }}>
      {title==="邮箱"?(
      <TextInput
        style = {styles.password}
        defaultValue={this.state.email}
        onChangeText={(text)=> this.setState({email:text})}
      />
      ):
      <TextInput
        style = {styles.password}
        defaultValue={this.state.phone}
        onChangeText={(text)=> this.setState({phone:text})}
      />
      }
      <View style = {{margin:10}}>
      {title==="邮箱"?(
      <Button 
          onPress={()=>this.submit('1')}
          title = '确认'
      />
      ):
      <Button 
      onPress={()=>this.submit('2')}
      title = '确认'
      />}
      </View>
    </ScrollView>
    </View>
      )
    }
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