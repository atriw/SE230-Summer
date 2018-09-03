import React from 'react';
import { Text, View, ScrollView, Dimensions, StyleSheet, TouchableOpacity, TextInput,  Button } from 'react-native';
import Icon from 'react-native-vector-icons/MaterialIcons';

export default class ModifyInfo extends React.Component {
  static navigationOptions = {
    header: null,
  }

  submit = () => {
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
                onChangeText={(text)=> this.setState({newpasswordAgain:text})}
            />
            <View style = {{margin:10}}>
            <Button 
                onPress={this.submit}
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
      <TextInput
          style = {styles.password}
          defaultValue='testValue'
          onChangeText={(text)=> this.setState({value:text})}
      />
      <View style = {{margin:10}}>
      <Button 
          onPress={this.submit}
          title = '确认'
      />
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