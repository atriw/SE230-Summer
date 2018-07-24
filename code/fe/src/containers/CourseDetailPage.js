import React from 'react';
import axios from 'axios'
import {Layout, Divider, Col, Row} from 'antd';
import Sidebar from '../components/Parts/Sidebar';
import MyTable from '../components/Parts/PaginationTable';
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
           allData:[],
            camera: ''
        };
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
        data.sort(function (a, b) {
            return a.timestamp - b.timestamp
        });
        if (data.length > 13){
            data.splice(0,data.length-13);
        }
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

    processData2 = (data) => {
        let newData = [];
        data.forEach((column) =>{
            let aColumn = {
                time: column.time,
                numOfFace: column.value,
                id: <a onClick={this.handlePhoto}>{column.id}</a>,
                filename: 'photo' + column.id
            };
            newData.push(aColumn)
        });
        return newData
    };

    addAction = (data) => {
        let newData = [];
        if (data.length === 0){
            return false
        }
        data.forEach((column) => {
            if (column['id'])
                column['action'] = 'update';
            newData.push(column)
        });
        return newData
    };

    componentDidMount = () => {
        axios.get('/api/course/byCourseId?courseId=' + this.state.id)
            .then((res) => {
                let data = res.data;
                let arr = /(http:\/\/)([a-zA-Z0-9]+:[a-zA-Z0-9]+)@([0-9.]+:[0-9]+)/.exec(data.camera);

                this.setState({
                    data: [data],
                    camera: arr[1] + arr[3]
                })
            })
            .catch((error) => {
                console.log(error);
        });
        axios.get('/api/stat/byLast3Courses')
            .then((res) => {
                let data = res.data;
                if (data.length > 0) {
                    this.setState({
                        lastThreeData: this.processData(data)
                    })
                }
            })
            .catch((error) => {
                console.log(error);
        });
        axios.get('/api/stat/byCourse?courseId=' + this.state.id)
            .then((res) => {
                let data = res.data;
                if (data.length > 0) {
                    this.setState({
                        allData: this.processData(data)
                    })
                } 
            })
            .catch((error) => {
                console.log(error);
        });
    };

    handlePhoto = (photoId) =>{
        axios.get( "/api/photo/byPhotoId?photoId="+photoId, {
            responseType: "arraybuffer",
          }).then(res => {
            return 'data:image/png;base64,' + btoa(
                new Uint8Array(res.data)
                  .reduce((data, byte) => data + String.fromCharCode(byte), '')
              );
          })
          .then(data => {
              this.refs.photo.src = data
          })
          .catch(ex => {
            console.error(ex);
          });
    };

    render() {
        const columnsOne = [{
            title: 'Id',
        },{
            title: 'Name',
        },{
            title: 'Time',
        },{
            title: 'NumOfStudent',
        },{
            title: 'Interval',
        },{
            title: 'Action',
            type: 'link'
        }];

        const columnsTwo = [{
            title: 'Id',
        },{
            title: 'PhotoId',
        },{
            title: 'Time',
        },{
            title: 'NumOfFace',
        }];

        let data2 = this.processData2(this.state.allData)
        const onRow = (record, index) =>{
            return{
                onClick: ()=>{
                    let photoId = (this.processData2(this.state.allData)[index].photoId)
                    this.handlePhoto(photoId)
                }
            }
        }

        return (
            <Layout>
                <Header className="header">
                    <Avatar/>
                </Header>
                <Layout>
                    <Sider style={{background: '#fff'}}>
                        <Sidebar/>
                    </Sider>
                    <Layout>
                        <Content>
                            <Divider orientation="left"><h1>课程信息</h1></Divider>
                            <MyTable column={columnsOne} data={this.addAction(this.state.data)} enablePage={false} enableSearchBar={false}/>
                            <Divider orientation="left"><h1>视频监控</h1></Divider>
                            <img alt="video here" src={this.state.camera + '/video'}/>
                            <Divider orientation="left"><h1>统计信息</h1></Divider>
                            <div>
                                <Row>
                                    <Col span={12}>

                                        <MyTable column={columnsTwo} data={data2} pageSize={6} onRow={onRow} searchItem="id"/>

                                    </Col>
                                    <Col span={12}>
                                        <img ref='photo' className = "img" src={conor} height="95%" width="95%" alt="conor"/>
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