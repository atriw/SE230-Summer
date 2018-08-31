import React from 'react';
import { View, TextInput, StyleSheet, Text } from 'react-native'
import { Button, WhiteSpace} from 'antd-mobile-rn';
export default class SubmitScreen extends React.Component {
    render(){
        return(
            <View style = {styles.container}>
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
        )
    }
}

const styles = StyleSheet.create({
    container: {
        marginTop: 150,
        backgroundColor: 'rgba(255,255,255,0.8)',
        maxWidth: 300,
        marginLeft: 40
    },
    input:{
        textAlign: 'center',
        height: 50,
        backgroundColor: '#fbfbfb',
        marginLeft: 50,
        marginRight: 50
    },
    baseText:{
        fontSize: 14,
    }
});
  