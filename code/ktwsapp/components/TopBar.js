import React from 'react';
import {StyleSheet, View, Text} from 'react-native'
export default class TopBar extends React.Component {
  render() {
    return (
      <View style={{backgroundColor:this.props.color?this.props.color:'rgba(155,207,246, 1)'}}>
      <Text style={styles.topText}>
        {this.props.title}
      </Text>
      </View>
    );
  } 
}

const styles = StyleSheet.create({
  topText: {
    paddingTop: 20,
    paddingBottom: 20,
    textAlign: 'center',
    fontSize: 17,
    color: 'white',
  }
})