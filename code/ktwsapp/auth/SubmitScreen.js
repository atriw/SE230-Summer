import React from 'react';
import { View, TextInput, StyleSheet, Text, Button } from 'react-native'
import TopBar from '../components/TopBar';
export default class SubmitScreen extends React.Component {
    static navigationOptions = {
        header: null,
    }
    render(){
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
                onPress={() => this.props.navigation.navigate('Auth')}
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
  