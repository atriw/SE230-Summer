import React from 'react';
import {Layout, Divider} from 'antd';
import Sidebar from '../components/Parts/Sidebar';
import Avatar from "../components/Parts/Avatar";
import axios from "axios/index";
import StatChart from "../components/Charts/StatChart";
const {Header, Content, Sider}=Layout;

class StatisticPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            lastData: []
        }
    }

    timestampToTime = (timestamp) => {
        let date = new Date(timestamp);
        let Y = date.getFullYear() + '-';
        let M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        let D = date.getDate() + ' ';
        let h = date.getHours() + ':';
        let m = date.getMinutes() + ':';
        let s = date.getSeconds();
        if(h.length < 3)
            h = '0' + h;
        if(m.length < 3)
            m = '0' + m;
        if(s.length < 3)
            s = '0' + s;
        return Y+M+D+h+m+s;
    };

    processData = (data) => {
        if (data.length === 0){
            return false
        }
        let newData = [];
        // if (data.length > 13){
        //     data.splice(0,data.length-13);
        // }
        data.sort(function (a, b) {
            return a.timestamp - b.timestamp;
        });
        data.forEach((column) =>{
            let timestamp = column.timestamp;
            let value = column.stats[0].numOfFace;
            let id = column.photoId;
            let aColumn = {
                time: this.timestampToTime(timestamp),
                value: value,
                id: id
            };
            newData.push(aColumn)
        });
        return newData
    };

    componentDidMount() {
        axios.get('/api/stat/byUserLastCourse')
            .then((res) => {
                let data = res.data;
                if (data.length > 0) {
                    this.setState({
                        lastData: this.processData(data)
                    })
                }
            })
            .catch((error) => {
                console.log(error);
            });
    }

    render() {
      return (
        <Layout>
        <Header className="header">
            <Avatar/>
        </Header>
        <Layout>
          <Sider style={{background: '#fff'}}>
            <Sidebar />
          </Sider>
          <Layout>
            <Content>
               <Divider orientation="left"><h1>最近课程</h1></Divider>
               <StatChart data={this.state.lastData}/>
                <StatChart data={this.state.lastData} type={'line'}/>
                <div className="fill"/>
            </Content>
          </Layout>
        </Layout>
        </Layout>
      );
    }
  }
  
  export default StatisticPage;