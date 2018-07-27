import {Modal, Form, Input, Icon, Button, Select} from 'antd';
import React from 'react';
import 'react-dom';
import axios from 'axios'


const FormItem = Form.Item;
const Option = Select.Option;
let isSet = false;
let uuid = 0;
let initialValue = [];
let days = [];
let hours = [];

/* Author: He Rongjun
 * Time: 2018/7/7
 * parameters: null
 * Intro: it is a pop up handle things about modify e-mail: user need to type valid e-mail twice, 
 * it will validate e-mail's format and compare whether two e-mail are the same, user click button to commit
 */
class UpdateCourse extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            id: this.props.id,
            oldName: '',
            studentNumberOk: null,
            frequencyOk: null,
            courseTitleOk: null,
            addressOk: null,
            cameraOk: null,
            data:  
                [{
                    name: null,
                    address: null,
                    camera: null,
                    numOfStudent: null,
                    interval: null,
                    time: null
                }]
        }
    }

    componentDidMount = () =>{
        axios.get('/api/course/byCourseId?courseId=' + this.state.id)
        .then((res) => {
            let data = res.data;
            if (data) {
                this.setState({
                    data: data,
                    oldName: data.name
                });
                this.setTime(data)
            } 
            else {
                alert('fail');
            }
        })
        .catch((error) => {
            console.log(error);
        });

    };


    // set error when title is empty.
    checkCourseTitle= (e) => {
        e.preventDefault();
        if (e.target.value === ''){
            this.setState({
                courseTitleOk: 'error',
            })
        }
        else{
            this.setState({
                courseTitleOk: 'success',
                name: e.target.value
            })
        }
    };

    // set error when frequency is less than 60 or equal to 60 or bigger than 300
    checkFrequency = (e) => {
        e.preventDefault();
        if(e.target.value <= 60 || e.target.value > 300 || e.target.value === null){
            this.setState({
                frequencyOk:'error',
            })
        }
        else{
            this.setState({
                frequencyOk:'success',
                interval: e.target.value
            })
        }
    };

    // set error when student number is less than or equal to 0 
    checkStudentNumber = (e) => {
        e.preventDefault();
        if (e.target.value <= 0 || e.target.value === null){
            this.setState({
                studentNumberOk: 'error',
            })
        }
        else{
            this.setState({
                studentNumberOk: 'success',
                numOfStudent: e.target.value
            })
        }
    };

    checkAddress = (e) =>{
        e.preventDefault();
        if (e.target.value <= 0 || e.target.value === null){
            this.setState({
                addressOk: 'error',
            })
        }
        else{
            this.setState({
                addressOk: 'success',
                address: e.target.value
            })
        }
    };

    checkCamera = (e) =>{
        e.preventDefault();
        if (e.target.value <= 0 || e.target.value === null){
            this.setState({
                cameraOk: 'error',
            })
        }
        else{
            this.setState({
                cameraOk: 'success',
                camera: e.target.value
            })
        }
    };

    check = () => {
        return this.state.addressOk && this.state.cameraOk && this.state.courseTitleOk
        && this.state.frequencyOk && this.state.studentNumberOk
    };

    // remove class time
    remove = (k) => {
        const { form } = this.props;
        const keys = form.getFieldValue('keys');
        if (keys.length === 1) {
          return;
        }
        form.setFieldsValue({
          keys: keys.filter(key => key !== k),
        });
    };

    // add class time
    add = () => {
        const { form } = this.props;
        const keys = form.getFieldValue('keys');
        const nextKeys = keys.concat(uuid);
        console.log(keys);
        uuid++;
        form.setFieldsValue({
            keys: nextKeys,
        });
    };

    getTime = () => {
        const { form } = this.props;
        const keys = form.getFieldValue('keys');
        let column = [];
        for (const i in keys){
            let time = String(form.getFieldValue(`hour[${i}]`));
            let startTime = time.substring(0,5);
            let endTime = time.substring(6,11);
            let aColumn = {
                day: String(form.getFieldValue(`day[${i}]`)),
                startTime: startTime,
                endTime: endTime
            };
            column.push(aColumn);
        };
        return column;
    };

    setTime = (data) => {
        if(isSet||!data.time)
            return false;
        let time = data.time.split("\n");
        time.forEach((column)=>{
            if (column!==""){
                initialValue.push(uuid);
                days.push(column.substring(0,3));
                hours.push(column.substring(4,15));
                uuid++
            }
        });
        isSet = true
    };
    

    handleOk = () => {
        let oldname = this.state.oldName;
        let newname = this.props.form.getFieldValue('name');
        let address = this.props.form.getFieldValue('address');
        let numOfStudent = this.props.form.getFieldValue('numOfStudent');
        let interval = this.props.form.getFieldValue('interval');
        let camera = this.props.form.getFieldValue('camera');
        let time = this.getTime();
        if(!(newname&&address&&numOfStudent&&interval&&camera&&time))
            return false;

        axios.post('/api/course/update', {
            oldName: oldname,
            newName: newname,
            address: address,
            numOfStudent: numOfStudent,
            interval: interval,
            camera: camera,
            time: time
        })
        .then((res) => {
            let data = res.data;
            if (data === true) {
                alert('修改成功')
            } else {
                alert('修改失败，请重新输入');
            }
        })
        .catch((error) => {
            console.log(error);
        });
        this.setState({
            visible: false
        });
    };
    
    handleCancel = () => {
        this.setState({
            visible: false,
        });
    };

    showModal = () => {
        this.setState({
            visible: true,
        })
    };

    render() {               
        const formItemLayout = {
            labelCol: {
                xs: { span: 24 },
                sm: { span: 5 },
            },
            wrapperCol: {
                xs: { span: 24 },
                sm: { span: 15 },
            },
        };
        const formItemLayoutWithOutLabel = {
            wrapperCol: {
                xs: { span: 24, offset: 4 },
                sm: { span: 15, offset: 5 },
            },
        };
        const { getFieldDecorator, getFieldValue} = this.props.form;

        getFieldDecorator('keys', { initialValue: initialValue });
        const keys = getFieldValue('keys');
        const formItems = keys.map((k, index) => {
            return (
                <div>
                <FormItem
                    {...formItemLayoutWithOutLabel}
                    required={false}
                    key={k}
                >   
                    {getFieldDecorator(`day[${k}]`,{
                        initialValue: days[k]
                    })(
                        <Select placeholder={days[k]}>
                            <Option value="MON">Monday</Option>
                            <Option value="TUE">Tuesday</Option>
                            <Option value="WED">Wednesday</Option>
                            <Option value="THU">Thursday</Option>
                            <Option value="FRI">Friday</Option>
                            <Option value="SAT">Saturday</Option>
                            <Option value="SUN">Sunday</Option>
                        </Select>
                    )}

                    {getFieldDecorator(`hour[${k}]`,{
                        initialValue: hours[k]
                    })(
                        <Select placeholder={hours[k]}>
                            <Option value="08:00-10:00">8:00-10:00</Option>
                            <Option value="10:00-12:00">10:00-12:00</Option>
                            <Option value="14:00-16:00">14:00-16:00</Option>
                            <Option value="16:00-18:00">16:00-18:00</Option>
                        </Select>
                    )}

                    {keys.length > 1 ? (
                    <Icon
                        className="dynamic-delete-button"
                        type="minus-circle-o"
                        disabled={keys.length === 1}
                        onClick={() => this.remove(k)}
                    />
                    ) : null}
                </FormItem>
                </div>
            );
        });


        return (
        <div>
            <Icon type = "edit" onClick = {this.showModal}/>
            <Modal
                visible = {this.state.visible}
                title = '修改课程'
                okText = '确认修改'
                cancelText = '取消'
                onCancel = {this.handleCancel}
                onOk = {this.handleOk}
            >
                <Form {...formItemLayout} layout="vertical" >
                    <FormItem {...formItemLayout}  hasFeedback validateStatus={this.state.courseTitleOk} help="请输入课程名称" label="课程名称">
                    {getFieldDecorator('name',{
                        initialValue:this.state.data.name
                    })(
                        <Input placeholder={this.state.data.name} type="text" onChange={this.checkCourseTitle}/>
                    )}
                    </FormItem>
                    <FormItem {...formItemLayout}  hasFeedback validateStatus={this.state.studentNumberOk} help="请输入学生总人数" label="学生总数">
                    {getFieldDecorator('numOfStudent',{
                        initialValue:this.state.data.numOfStudent? this.state.data.numOfStudent.toString() : ''
                    })(
                        <Input placeholder={this.state.data.numOfStudent} type="text" onChange={this.checkStudentNumber}/>
                    )}
                    </FormItem>
                    <FormItem {...formItemLayout}  hasFeedback validateStatus={this.state.frequencyOk} help="请输入拍照间隔(60~300)s" label="拍照间隔">
                    {getFieldDecorator('interval',{
                        initialValue:this.state.data.interval? this.state.data.interval.toString() : ''
                    })( 
                        <Input placeholder={this.state.data.interval} type="text" onChange={this.checkFrequency}/>
                    )}
                    </FormItem>
                    <FormItem {...formItemLayout}  hasFeedback validateStatus={this.state.addressOk} help="请输入课程地址" label="课程地址">
                    {getFieldDecorator('address',{
                        initialValue:this.state.data.address
                    })( 
                        <Input placeholder={this.state.data.address} type="text" onChange={this.checkAddress}/>
                    )}
                    </FormItem>
                    <FormItem {...formItemLayout}  hasFeedback validateStatus={this.state.cameraOk} help="请输入相机的ip地址" label="相机地址">
                    {getFieldDecorator('camera',{
                        initialValue:this.state.data.camera
                    })( 
                        <Input placeholder={this.state.data.camera} type="text" onChange={this.checkCamera}/>
                    )}
                    </FormItem>
                    {formItems}
                    <FormItem {...formItemLayout} label="每周上课时间"> 
                        <Button type="dashed" onClick={this.add} style={{ width: '100%' }}><Icon type="plus" />添加上课时间</Button>
                    </FormItem>
                </Form>
            </Modal>
        </div>
        );
    }
}

export default Form.create()(UpdateCourse);