import React from 'react';
import axios from 'axios'
import {Layout, Divider, Col, Row} from 'antd';
import Sidebar from '../components/Parts/Sidebar';
import Table from '../components/Parts/PaginationTable';
import StatChart from "../components/Charts/StatChart";
import conor from "../components/../assets/0.gif"
import Avatar from "../components/Parts/Avatar";

const {Header, Content, Sider}=Layout;
class CourseDetail extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
           id: this.props.match.params.id,
           data:[],
           lastThreeData:[],
           allData:[]
        };
    }
  
    timestampToTime = (timestamp) => {
        let date = new Date(timestamp)
        let Y = date.getFullYear() + '-';
        let M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        let D = date.getDate() + ' ';
        let h = date.getHours() + ':';
        let m = date.getMinutes() + ':';
        let s = date.getSeconds();
        return Y+M+D+h+m+s;
    }

    processData = (data) => {
        let newData = []
        if (data.length > 13){
            data.splice(0,data.length-13);
        }
        data.foreach((column) =>{
            let timestamp = column.timestamp
            let value = column.stats[0].numOfFace
            let aColumn = {
                time: this.timestampToTime(timestamp),
                value: value
            }
            newData.push(aColumn)
        });
        return newData
    }

    addAction = (data) => {
        let newData = []
        data.foreach((column) => {
            column['action'] = 'update'
            newData.push(column)
        })
        return newData
    }

    componentDidMount = (e) => {
        axios.post('/api/course/byUser',)
            .then((res) => {
                let data = res.data;
                if (data === true) {
                    this.setState({
                        data: this.processData(data)
                    })
                } 
            })
            .catch((error) => {
                console.log(error);
        });
        axios.post('/api/stat/byLast3Courses')
            .then((res) => {
                let data = res.data;
                if (data === true) {
                    this.setState({
                        lastThreeData: this.processData(data)
                    })
                } 
            })
            .catch((error) => {
                console.log(error);
        });
        axios.post('/api/stat/byCourse', {
            courseId: this.state.courseId
        })
            .then((res) => {
                let data = res.data;
                if (data === true) {
                    this.setState({
                        allData: this.processData(data)
                    })
                } 
            })
            .catch((error) => {
                console.log(error);
        });
    }

    render() {
        const columnsOne = [{
            title: 'Id',
        },{
            title: 'Name',
        },{
            title: 'Time',
        },{
            title: 'Total',
        },{
            title: 'Interval',
        },{
            title: 'Action',
            type: 'link'
        }];

        const columnsTwo = [{
            title: 'Id',
        },{
            title: 'Name',
        },{
            title: 'Time',
        },{
            title: 'Total',
        },{
            title: 'Interval',
        }];

        /*const data = [{
            key: '1',
            id: '1',
            name: 'Math',
            time: '周二 08:00-10:00',
            total: 5,
            interval: 5,
            action: 'update'
        }];

        const data2 = [{
            key: '1',
            id: '1',
            name: 'Math',
            time: '周二 08:00-10:00',
            total: 5,
            interval: 5,
        },{
            key: '2',
            id: '2',
            name: 'English',
            time: '周二 08:00-10:00',
            total: 5,
            interval: 5,
        }];

        let mock_data = [
            {time: '2018-08-09 20:30:11', value: 5},
            {time: '2018-08-09 20:35:14', value: 6},
            {time: '2018-08-09 20:40:40', value: 8},
            {time: '2018-08-09 20:45:40', value: 2},
            {time: '2018-08-09 20:50:40', value: 9},
            {time: '2018-08-09 20:55:40', value: 3},
            {time: '2018-08-09 21:00:40', value: 6},
            {time: '2018-08-09 21:05:40', value: 5},
            {time: '2018-08-09 21:10:40', value: 1},
            {time: '2018-08-09 21:15:40', value: 2},
            {time: '2018-08-09 21:20:40', value: 7},
            {time: '2018-08-09 21:25:40', value: 8},
            {time: '2018-08-10 21:40:40', value: 200}
        ];*/

        
        return (
            <Layout>
                <Header className="header" style={{background: '#aaa'}}>
                    <Avatar/>
                </Header>
                <Layout>
                    <Sider width={256} style={{background: '#fff'}}>
                        <Sidebar/>
                    </Sider>
                    <Layout>
                        <Content>
                            <Divider orientation="left"><h1>课程信息</h1></Divider>
                            <Table column={columnsOne} data={this.addAction(this.state.data)} enablePage={false} enableSearchBar={false}/>
                            <Divider orientation="left"><h1>视频监控</h1></Divider>
                            <Divider orientation="left"><h1>统计信息</h1></Divider>
                            <div>
                                <Row>
                                    <Col span={12}>
                                        <Table column={columnsTwo} data={this.state.data}/>
                                    </Col>
                                    <Col span={12}>
                                        <img src={conor} height="100%" width="100%" alt="conor"/>
                                    </Col>
                                </Row>
                            </div>
                            <Divider orientation="left"><h1>统计图表</h1></Divider>
                            <div>
                                <Row>
                                    <Col span={12}>
                                        <StatChart data={this.state.allData}
                                                   style={{height: '100%', width: '100%', float: 'left'}}/>
                                    </Col>
                                    <Col span={12}>
                                        <StatChart data={this.state.allData} type='line'
                                                   style={{height: '100%', width: '100%', float: 'left'}}/>
                                    </Col>
                                </Row>
                            </div>

                            <Divider orientation="left"><h1>过去三次课的统计</h1></Divider>
                            <Row>
                                <StatChart data={this.state.lastThreeData} style={{height: '100%', width: '100%', float: 'left'}}/>
                            </Row>
                            <div className="fill"/>
                        </Content>
                    </Layout>
                </Layout>
            </Layout>
        );
    }
  }
  
export default CourseDetail;