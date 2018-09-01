import React from 'react';
import { View, TextInput, StyleSheet, Text } from 'react-native'
import { Button, WhiteSpace} from 'antd-mobile-rn';
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
                onChangeText={(text)=> this.setState({text})}
            />
            <TextInput
                style = {styles.input}
                placeholder="请输入你的密码"
                onChangeText={(text)=> this.setState({text})}
            />
            <TextInput
                style = {styles.input}
                placeholder="请确认你的密码"
                onChangeText={(text)=> this.setState({text})}
            />
            <Button
                type="primary" 
                onClick={() => this.props.navigation.navigate('Personal')}>
                点击注册
            </Button>
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
        paddingLeft: 10,
        backgroundColor: '#fbfbfb',
    },
    baseText:{
        fontSize: 14,
    }
});
  