import React from 'react';
import { View, TextInput, StyleSheet, Text, Button, AsyncStorage } from 'react-native'
import TopBar from '../components/TopBar';
import Request from '../request'
export default class LoginScreen extends React.Component {
    constructor(props){
        super(props)
        this.state={
            userName:'',
            password:''
        }
    }

    static navigationOptions = {
        header: null,
        tabBarVisible: false,
    }

    handleSubmit = () =>{
        if(!this.state.userName ||!this.state.password){
            alert('请确保账号/密码不为空')
            return false
        }
        Request.post('/api/user/login',{
            name: this.state.userName,
            pwd: this.state.password
        })
            .then((res) => {
                let data = res.data
                if(data === true){
                    this._logInAsync()
                }
                else{
                    alert('用户名或密码错误')
                    return false
                }
            })
            .catch((error) => {
                console.log(error);
        });
    }

    _logInAsync = () => {
        Request.get('/api/user/getRoles')
            .then((res) => {
                let data = res.data
                if(data!=null){
                    AsyncStorage.setItem('userName',this.state.userName);
                    if(data.indexOf("EA") !== -1){
                        AsyncStorage.setItem('userToken','admin');
                        this.props.navigation.navigate('Admin');
                    }
                    else{
                        AsyncStorage.setItem('userToken','user');
                        this.props.navigation.navigate('App');
                    }
                }
                else{
                    alert('用户名或密码错误')
                }

            })
            .catch((error) => {
                console.log(error);
        });

    }

    render(){
        //{this._logInAsync()}
        return(
            <View style = {styles.container}>
            <TopBar title='登陆' color='#2A3845'/>
            <View style = {styles.loginFrame}>
            <TextInput
                style = {styles.input}
                placeholder="请输入你的用户名"
                onChangeText={(text)=> this.setState({userName:text})}
                underlineColorAndroid='transparent'
            />
            <View style={{height:1, backgroundColor:'grey'}}/>
            <TextInput
                style = {styles.input}
                placeholder="请输入你的密码"
                onChangeText={(text)=> this.setState({password:text})}
                underlineColorAndroid='transparent'
            />
            <View style={{height:10, backgroundColor:'#202937'}}/>
            <Button 
                onPress={this.handleSubmit}
                title = '登录'
            />
            <Text
                style = {styles.baseText}
                onPress={() => this.props.navigation.navigate('Submit')}>>
                没账号？点击注册
            </Text>
            </View>
            </View>
        )
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#202937'
      },
    loginFrame: {
        marginTop: 150,
        backgroundColor: '#fbfbfb',
        width: 300,
        alignSelf: 'center',
    },
    input:{
        fontSize: 16,
        paddingLeft: 10,
        backgroundColor: '#fbfbfb',
    },
    baseText:{
        paddingTop: 5,
        paddingBottom: 5,
        fontSize: 16,
        textAlign: 'center',
        backgroundColor: '#fbfbfb',
    },
});
  