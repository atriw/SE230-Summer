import React from 'react';
import { View, TextInput, StyleSheet, Text, Button } from 'react-native'
import TopBar from '../components/TopBar';
export default class LoginScreen extends React.Component {
    static navigationOptions = {
        header: null,
    }
    render(){
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
                onPress={() => this.props.navigation.navigate('Personal')}
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
  