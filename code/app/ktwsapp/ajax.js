import React, { Component } from 'react';
import {View, Text, Image} from 'react-native'

export default class Ajax extends Component {

    constructor(props){
        super(props);
//        this.state ={ test: "fail"}
        this.state = {pic: ''}

        this,renderImg = this.renderImg.bind(this);
     }

     componentDidMount(){
//         return fetch('http://10.0.2.2:8080/api/user/test')
//           .then((response) => response.text())
//           .then((responseData) => {
//
//             this.setState({
//               test: responseData,
//             }, function(){
//
//             });
//
//           })
//           .catch((error) =>{
//             console.error(error);
//           });

    }

    renderImg() {
        return <Image style={{width: 100, height: 50, resizeMode: Image.resizeMode.contain, borderWidth: 1, borderColor: 'red'}} source={{uri: 'http://10.0.2.2:8080/api/photo/byPhotoId?photoId=7'}}/>;
    }

    render() {
//        return <Text>{this.state.test}</Text>
            return this.renderImg();


    }
}
