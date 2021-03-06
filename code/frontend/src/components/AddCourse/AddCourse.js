import React from 'react'
import axios from 'axios'
import { Divider, Form,  Input, Button,Select,Icon} from 'antd';
import { Redirect } from 'react-router-dom';

const FormItem = Form.Item;
const Option = Select.Option;

let uuid = 0;

/* Author: He Rongjun
 * Time: 2018/7/7
 * parameters: null
 * Intro: This component handle event when user want to add courses. validate values of input,
 * user can add several class time, but at least one class time.
 */
class AddCourse extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            studentNumberOk: null,
            frequencyOk: null,
            courseTitleOk: null,
            addressOk: null,
            cameraOk: null,
            redirect: false,
        }
    }

    // length of title must be >= 1 and <= 16, and can not be blank
    checkCourseTitle= (e) => {
        e.preventDefault();
        if (e.target.value === '' || e.target.value.length > 16 || this.isAllBlank(e.target.value)){
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

    // set error when frequency is less than 60 or bigger than 300 or not Int
    checkFrequency = (e) => {
        e.preventDefault();
        if(e.target.value < 60 || e.target.value > 300 || e.target.value === null || !this.isInt(e.target.value)){
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

    // set error when student number is less than or equal to 0 or not Int
    checkStudentNumber = (e) => {
        e.preventDefault();
        if (e.target.value <= 0 || e.target.value === null || !this.isInt(e.target.value)){
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

    // check if a thing is Int
    isInt = (e) => {
        var reg = /^[0-9]*[1-9][0-9]*$/;
        return e.match(reg);
    }

    // return true if Ipv4 + port number
    isIPV4 = (e) => {
        var reg = /^[a-zA-Z]+:\/\/(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\:([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-5]{2}[0-3][0-5])$/;
        return e.match(reg);
    }

    // check if a thing is all blank
    isAllBlank = (e) => {
        let flag = true;
        let i = 0;
        for (; i < e.length; i++){
            if (e.charAt(i) !== ' '){
                flag = false;
            }
        }
        return flag;
    }

    checkAddress = (e) =>{
        e.preventDefault();
        if (e.target.value <= 0 || e.target.value === null || this.isAllBlank(e.target.value)){
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
        if (e.target.value <= 0 || e.target.value === null || !this.isIPV4(e.target.value)){
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
        && this.state.frequencyOk && this.state.studentNumberOk && this.checkTimeEmpty()
    };

    checkTimeEmpty = () =>{
        const { form } = this.props;
        const keys = form.getFieldValue('keys');
        if (keys.length === 0) {
          return false;
        }
        if (!this.getTime()){
            return false;
        }
        return true;
    }

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
            if (form.getFieldValue(`hour[${i}]`) === undefined || form.getFieldValue(`day[${i}]`) === undefined){
                return false;
            }
            let time = String(form.getFieldValue(`hour[${i}]`));
            let startTime = time.substring(0,5);
            let endTime = time.substring(6,11);
            let aColumn = {
                day: String(form.getFieldValue(`day[${i}]`)),
                startTime: startTime,
                endTime: endTime
            };
            column.push(aColumn);
        }
        return column;
    };

    // handle submit
    handleSubmit = (e) =>{
        e.preventDefault();
        if (!this.check()){
            alert("请确认你的输入正确");
            return false;
        }
        const time = this.getTime();
        axios.post('/api/course/add', {
            name: this.state.name,
            address: this.state.address,
            numOfStudent: this.state.numOfStudent,
            interval: this.state.interval,
            camera: this.state.camera,
            time: time
        })
            .then((res) => {
                let data = res.data;
                if (data) {
                    this.setState({
                        redirect: true
                    })
                } else {
                    alert('提交失败，请重新输入');
                }
            })
            .catch((error) => {
                console.log(error);
            });
    };

    render(){
        const formItemLayout = {
            labelCol: {
                xs: { span: 24 },
                sm: { span: 4 },
            },
            wrapperCol: {
                xs: { span: 24 },
                sm: { span: 5 },
            },
        };
        const formItemLayoutWithOutLabel = {
            wrapperCol: {
                xs: { span: 24, offset: 0 },
                sm: { span: 5, offset: 4 },
            },
        };
        const { getFieldDecorator, getFieldValue } = this.props.form;
        getFieldDecorator('keys', { initialValue: [] });
        const keys = getFieldValue('keys');
        const formItems = keys.map((k, index) => {
            return (
                <div>
                <FormItem
                    {...formItemLayoutWithOutLabel}
                    required={false}
                    key={k}
                >   
                    {getFieldDecorator(`day[${k}]`)(
                        <Select placeholder="Please select day">
                            <Option value="MON">Monday</Option>
                            <Option value="TUE">Tuesday</Option>
                            <Option value="WED">Wednesday</Option>
                            <Option value="THU">Thursday</Option>
                            <Option value="FRI">Friday</Option>
                            <Option value="SAT">Saturday</Option>
                            <Option value="SUN">Sunday</Option>
                        </Select>
                    )}

                    {getFieldDecorator(`hour[${k}]`)(
                        <Select placeholder="Please select hour">
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
        if (this.state.redirect){
            return <Redirect push to="/allcourses" />
        }
        return(
            <div>
                <Form {...formItemLayout} onSubmit={this.handleSubmit}>
                    <Divider orientation="left">填写课程信息</Divider>
                    <FormItem {...formItemLayout}  hasFeedback validateStatus={this.state.courseTitleOk} help="请输入课程名称" label="课程名称">
                        <Input  type="text" onChange={this.checkCourseTitle}/>
                    </FormItem>
                    <FormItem {...formItemLayout}  hasFeedback validateStatus={this.state.studentNumberOk} help="请输入课程人数" label="课程人数">
                        <Input  type="text" onChange={this.checkStudentNumber}/>
                    </FormItem>
                    <FormItem {...formItemLayout}  hasFeedback validateStatus={this.state.frequencyOk} help="请输入拍照间隔(60~300)s" label="拍照间隔">
                        <Input  type="text" onChange={this.checkFrequency}/>
                    </FormItem>
                    <FormItem {...formItemLayout}  hasFeedback validateStatus={this.state.addressOk} help="请输入课程地址" label="课程地址">
                        <Input  type="text" onChange={this.checkAddress}/>
                    </FormItem>
                    <FormItem {...formItemLayout}  hasFeedback validateStatus={this.state.cameraOk} help="请输入相机的ip地址" label="相机地址">
                        <Input  type="text" onChange={this.checkCamera}/>
                    </FormItem>
                    {formItems}
                    <FormItem {...formItemLayout} label="每周上课时间"> 
                        <Button type="dashed" onClick={this.add} style={{ width: '100%' }}><Icon type="plus" />添加上课时间</Button>
                    </FormItem>
                        <Button className="addCourseButton" type="primary" htmlType="submit">提交课程</Button>
                </Form>
            </div>
        );
    }
}
const WrappedAddCourse = Form.create()(AddCourse);

export default WrappedAddCourse;