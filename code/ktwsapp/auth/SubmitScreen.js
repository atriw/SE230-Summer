import React from 'react';
import { View, TextInput, StyleSheet, Text, Button } from 'react-native'
import TopBar from '../components/TopBar';
import Request from '../request'
export default class SubmitScreen extends React.Component {
    constructor(props){
        super(props)
        this.state={
            userName:'',
            password:'',
            passwordAgain:'',
            email:'',
            phone:'',
        }
    }

    static navigationOptions = {
        header: null,
    }


    handleSubmit = () => {
        if(!this.state.userName ||!this.state.password||!this.state.passwordAgain){
            alert('请确保账号/密码不为空')
            return false
        }
        if(this.state.password != this.state.passwordAgain){
            alert('请确认两次输入的密码一致')
            return false
        }

        Request.post('/api/user/add',
            {
            name: this.state.userName,
            pwd: this.state.password,
            email: this.state.email,
            phone: this.state.phone
        })
            .then((res) => {
                let data = res.data;
                if (data != null){
                    this.props.navigation.navigate('Auth')
                }
                else{
                    alert('用户名重复，请重新输入');
                    return false
                }
            })
            .catch((error) => {
                console.log(error);
        });
    }
    

    render(){
        // if(this.state.redirect)
        //     this.props.navigation.navigate('Auth');
        return(
            <View style = {styles.container}>
            <TopBar title='注册' color='#2A3845'/>
            <View style = {styles.submitFrame}>
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
            <View style={{height:1, backgroundColor:'grey'}}/>
            <TextInput
                style = {styles.input}
                placeholder="请确认你的密码"
                onChangeText={(text)=> this.setState({passwordAgain:text})}
                underlineColorAndroid='transparent'
            />
            <View style={{height:10, backgroundColor:'#202937'}}/>
            <Button 
                onPress={this.handleSubmit}
                title = '注册'
            />
            </View>
            </View>
        )
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#202937',
    },
    submitFrame: {
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
        fontSize: 14,
    }
});
  