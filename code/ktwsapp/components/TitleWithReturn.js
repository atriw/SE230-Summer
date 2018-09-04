import React from 'react';
import {StyleSheet, View, Text, Dimensions, TouchableOpacity} from 'react-native';
import Icon from 'react-native-vector-icons/MaterialIcons';
export default class Title extends React.Component {
    render() {
        let destination = this.props.destination;
    return (
        <View style={styles.top}>
        <TouchableOpacity style={styles.addCourse}
          onPress={() => this.props.navigation.navigate(destination)}>
          <Icon name='chevron-left' style={{fontSize:25}} color='white'/>
        </TouchableOpacity>
        <View style={styles.titleCenter}>
          <Text style={styles.topText}>
            {this.props.name}
          </Text>
          </View>
        </View>
    );
  } 
}

const styles = StyleSheet.create({
    container: {
        backgroundColor: 'rgba(232,232,232, 1)',
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
  });
  