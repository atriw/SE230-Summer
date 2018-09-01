import React from 'react';
import { View, TextInput, StyleSheet, Text } from 'react-native'
import { Button, WhiteSpace} from 'antd-mobile-rn';
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
                onChangeText={(text)=> this.setState({text})}
            />
            <TextInput
                style = {styles.input}
                placeholder="请输入你的密码"
                onChangeText={(text)=> this.setState({text})}
            />
            <Button
                type="primary" 
                style="button"
                onClick={() => this.props.navigation.navigate('Personal')}>
                点击登录
            </Button>
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
        paddingLeft: 10,
        backgroundColor: '#fbfbfb',
    },
    baseText:{
        paddingTop: 5,
        paddingBottom: 5,
        fontSize: 14,
        textAlign: 'center',
        backgroundColor: '#fbfbfb',
    },
    button:{
        fontSize: 14,
    }
});
  