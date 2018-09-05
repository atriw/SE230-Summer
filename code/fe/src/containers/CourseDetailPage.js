import React from 'react';
import axios from 'axios'
import {Layout, Divider, Col, Row, Card, Alert} from 'antd';
import Sidebar from '../components/Parts/Sidebar';
import MyTable from '../components/Parts/PaginationTable';
import StatChart from "../components/Charts/StatChart";
import conor from "../components/../assets/0.gif"
import errorPic from "../components/../assets/videoError.png"
import Avatar from "../components/Parts/Avatar";
import UpdateCourse from "../components/AddCourse/UpdateCourse";
import DeleteCourse from "../components/AddCourse/DeleteCourse";
import Evaluations from "../components/Parts/Evaluations";
import Emotion from "../components/Parts/Emotion";
const {Header, Content, Sider}=Layout;
const testData = [
    {
        id: 5,
        courseId: 4,
        datetime: "2018-07-25 14:00:46",
        info: {
            max: {
                time: "14:00:46",
                value: 80
            },
            average: "80%",
            min: {
                time: "14:45:00",
                value: 40
            }
        }
    },
    {
        id: 6,
        courseId: 4,
        datetime: "2018-07-27 14:00:46",
        info:{
            max: {
                time: "14:00:46",
                value: 70
            },
            average: "70%",
            min: {
                time: "14:45:00",
                value: 30
            }
        }
    }
]

class CourseDetail extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            id: this.props.match.params.id,
            data:[],
            lastThreeData:[],
            allData:[],
            sectionStat: [],
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
        // if (data.length > 13){
        //     data.splice(0,data.length-13);
        // }
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
                id: column.id,
                filename: 'photo' + column.id
            };
            newData.push(aColumn)
        });
        return newData
    };

    processData3 = (data) => {
        let newData = []
        data.forEach((column) =>{
            let aColumn = {
                id: column.id,
                datetime: column.datetime,
                average: column.info.average,
                max: column.info.max.time,
                maxNum: column.info.max.value,
                min: column.info.min.time,
                minNum: column.info.min.value,
                emotion: column.info.emotion
            };
            newData.push(aColumn)
        });
        return newData
    }

    componentWillMount() {
        let id = this.state.id;
        let COURSE_INFO_URL = '/api/course/byCourseId?courseId=' + id;
        let LAST_THREE_COURSE_STAT_URL = '/api/stat/byLast3Courses?courseId=' + id;
        let LAST_COURSE_STAT_URL = '/api/stat/byLastCourse?courseId=' + id;
        let SECTION_STAT_URL = '/api/stat/sectionStat?courseId=' + id;
        axios.get(COURSE_INFO_URL)
            .then((res) => {
                let data = res.data;
                this.setState({
                    data: [data],
                    camera: data.camera
                })

            })
            .catch((error) => {
                console.log(error);
        });
        axios.get(LAST_THREE_COURSE_STAT_URL)
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
        axios.get(LAST_COURSE_STAT_URL)
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
        axios.get(SECTION_STAT_URL)
        .then((res) => {
            let data = res.data;
            if (data.length > 0) {
                this.setState({
                    sectionStat: this.processData3(data)
                })
            } 
        })
        .catch((error) => {
            console.log(error);
        });
    };

    videoError = () =>{
        this.refs.video.src = errorPic;
        this.refs.video.onError = null;
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
        const id = this.state.id;
        const columnsOne = [{
            title: 'Id',
        },{
            title: 'Name',
        },{
            title: 'Time',
        },{
            title: 'Address',
        },{
            title: 'NumOfStudent',
        },{
            title: 'Interval',
        },{
            title: 'Action',
            render: () => <div><div style={{"float":"left", marginRight: '10px'}}><UpdateCourse id = {id}/></div><div style={{"float":"left"}}><DeleteCourse id = {id} history={this.props.history}/></div></div>
        }];

        const columnsTwo = [{
            title: 'Id',
        },{
            title: 'Filename',
        },{
            title: 'Time',
        },{
            title: 'NumOfFace',
        }];

        let data2 = this.processData2(this.state.allData);
        const onRow = (record) =>{
            return{
                onClick: ()=>{
                    let photoId = (record.id);
                    this.handlePhoto(photoId)
                }
            }
        };

        let columnsThree = [{
            title: 'id',
        },{
            title: 'datetime'
        },{
            title: 'average'
        },{
            title: 'max',
            colSpan: 2,
        },{
            title: 'maxNum',
            colSpan: 0,
        },{
            title: 'min',
            colSpan: 2,
        },{
            title: 'minNum',
            colSpan: 0
        }
        ]

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
                            <MyTable column={columnsOne} data={this.state.data} enablePage={false} enableSearchBar={false}/>
                            <Divider orientation="left"><h1>视频监控</h1></Divider>
                            <img ref='video' alt="video here" onError={this.videoError} src={this.state.camera + '/video'}/>
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
                            <Divider orientation="left"><h1>历史课程状态分析</h1></Divider>
                            <Row>
                            <MyTable column={columnsThree} data={this.state.sectionStat} pageSize={6} enableSearchBar={false} bordered/>
                            </Row>
                            <Divider orientation="left"><h1>课程总结</h1></Divider>
                            <Evaluations evaluationData={this.state.sectionStat} total = {this.state.data[0]? this.state.data[0].numOfStudent : 5}/>
                            <Divider orientation="left"><h1>情绪指数</h1></Divider>
                            <Emotion result={this.state.sectionStat.emotion} />
                            
                        </Content>
                    </Layout>
                </Layout>
            </Layout>
        );
    }
  }
  
export default CourseDetail;