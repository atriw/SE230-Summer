import { List, InputItem, Toast, Picker, Button } from 'antd-mobile-rn';
import React from 'react';
import Request from '../request'

const range = [
  [
    {
      label: 'Monday',
      value: 'MON',
    },
    {
      label: 'Tuesday',
      value: 'TUE',
    },
    {
      label: 'Wednesday',
      value: 'WED',
    },
    {
      label: 'Thursday',
      value: 'THU',
    },
    {
      label: 'Friday',
      value: 'FRI',
    },
    {
      label: 'Saturday',
      value: 'SAT',
    },
    {
      label: 'Sunday',
      value: 'SUN',
    },
  ],
  [
    {
      label: '8:00-10:00',
      value: '08:00-10:00',
    },
    {
      label: '14:00-16:00',
      value: '14:00-16:00',
    },
    {
      label: '16:00-18:00',
      value: '16:00-18:00',
    },
    {
      label: '10:00-12:00',
      value: '10:00-12:00',
    },
  ],
];
let uuid = 0;

export default class AddCourse extends React.Component {
  state = {
    courseNameHasError: false,
    studentNumberHasError: false,
    intervalHasError: false,
    addressHasError: false,
    ipHasError: false,
    courseName: '',
    studentNumber: '',
    interval: '',
    address: '',
    ip: '',
    keys:[]
  }

  courseNameOnErrorClick = () => {
    if (this.state.courseNameHasError) {
      Toast.info('课程名称不能为空！');
    }
  }
  studentNumberOnErrorClick = () => {
      if (this.state.studentNumberHasError) {
        Toast.info('学生数量必须为正整数！');
      }
  }
  intervalOnErrorClick = () => {
      if (this.state.intervalHasError) {
        Toast.info('拍照间隔必须大于60并且小于等于300！');
      }
  }
  addressOnErrorClick = () => {
      if (this.state.addressHasError) {
        Toast.info('地址不能为空！');
      }
  }
  ipOnErrorClick = () => {
      if (this.state.ipHasError) {
        Toast.info('IP摄像头地址不能为空！');
      }
  }

  courseNameOnChange = (value) => {
    if (value === '') {
      this.setState({
        courseNameHasError: true,
      });
    } else {
      this.setState({
        courseNameHasError: false,
      });
    }
    this.setState({
      courseName: value
    });
  }
  studentNumberOnChange = (value) => {
      if (value <= 0 || value === null) {
        this.setState({
          studentNumberHasError: true,
        });
      } else {
        this.setState({
          studentNumberHasError: false,
        });
      }
      this.setState({
        studentNumber: value
      });
  }
  intervalOnChange = (value) => {
      if (value <= 60 || value >300 || value === null) {
        this.setState({
          intervalHasError: true,
        });
      } else {
        this.setState({
          intervalHasError: false,
        });
      }
      this.setState({
        interval: value
      });
  }
  addressOnChange = (value) => {
      if (value === '') {
        this.setState({
          addressHasError: true,
        });
      } else {
        this.setState({
          addressHasError: false,
        });
      }
      this.setState({
        address: value
      });
  }
  ipOnChange = (value) => {
      if (value === '') {
        this.setState({
          ipHasError: true,
        });
      } else {
        this.setState({
          ipHasError: false,
        });
      }
      this.setState({
        ip: value
      });
  }

  // remove class time
  remove = (k) => {
      const keys = this.state.keys;
      if (keys.length === 1) {
        return;
      }
      this.setState({
        keys: keys.filter(key => key !== k),
      })
  };
  // add class time
  add = () => {
      const keys = this.state.keys;
      const addkey = [[{uuid}]];
      const nextKeys = keys.concat(addkey);
      uuid++;
      this.setState({
        keys:nextKeys,
      });
  };

    getTime = () => {
            const keys = this.state.keys;
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
            }
            return column;
        };

    submit = () => {
      //TODO: 错误判断
      Request.post('/api/course/add', {
              name: this.state.courseName,
              address: this.state.address,
              numOfStudent: this.state.studentNumber,
              interval: this.state.interval,
              camera: this.state.ip,
              time: this.getTime()
      })
      .then((res) => {
        let data = res.data;
        if (data!=null) {
            alert('提交成功')
        } else {
            alert('提交失败，请重新输入');
        }
      })
      .catch((error) => {
          console.log(error);
      });
    }

  render() {
    const keys = this.state.keys;
    const formItems = keys.map((k, index) => {
        return(
            <List key={k[0]}>
                <Picker
                     data={range}
                     title="选择时间"
                     cascade={false}
                     extra="请选择(可选)"
                     value={k[1]}
                     onChange={v => {
                        let nextKeys = this.state.keys;
                        nextKeys[index] = [k[0],v];
                        this.setState({ keys: nextKeys })}}
                     onOk={v => {
                        let nextKeys = this.state.keys;
                        nextKeys[index] = [k[0],v];
                        this.setState({ keys: nextKeys })}}
                     >
                     <List.Item arrow="horizontal">每周上课时间</List.Item>
                </Picker>
                <List.Item
                     extra={<Button type="ghost" size="small" inline onClick={() => this.remove(k)}>删除时间</Button>}
                     multipleLine
                >
                每周上课时间
                </List.Item>
            </List>
        );
    });

    return (
        <List renderHeader={() => '添加课程'}>
          <InputItem
            placeholder="请输入课程名称"
            error={this.state.courseNameHasError}
            onErrorClick={this.courseNameOnErrorClick}
            onChange={this.courseNameOnChange}
            value={this.state.courseName}
          >课程名称</InputItem>
          <InputItem
            placeholder="请输入课程人数"
            error={this.state.studentNumberHasError}
            onErrorClick={this.studentNumberOnErrorClick}
            onChange={this.studentNumberOnChange}
            value={this.state.studentNumber}
           >课程人数</InputItem>
           <InputItem
            placeholder="请输入拍照间隔"
            error={this.state.intervalHasError}
            onErrorClick={this.intervalOnErrorClick}
            onChange={this.intervalOnChange}
            value={this.state.interval}
           >拍照间隔</InputItem>
           <InputItem
            placeholder="请输入课程地址"
            error={this.state.addressHasError}
            onErrorClick={this.addressOnErrorClick}
            onChange={this.addressOnChange}
            value={this.state.address}
           >课程地址</InputItem>
           <InputItem
            placeholder="请输入相机地址"
            error={this.state.ipHasError}
            onErrorClick={this.ipOnErrorClick}
            onChange={this.ipOnChange}
            value={this.state.ip}
           >相机地址</InputItem>
           {formItems}
           <List.Item
                 extra={<Button type="ghost" size="small" inline onClick={this.add}>添加时间</Button>}
                 multipleLine
               >
               每周上课时间
           </List.Item>
           <List.Item>
               <Button type="ghost" size="small"  onClick={this.submit}>提交</Button>
          </List.Item>
        </List>

    );
  }
}

