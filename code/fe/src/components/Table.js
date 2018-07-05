import React from 'react';
import { Table }from "antd";

const columns = [{
    title: 'Name',
    dataIndex: 'name',
    key: 'name',
},{
    title: 'CourseNum',
    dataIndex: 'courseNum',
    key: 'courseNum',
},{
    title: 'Email',
    dataIndex: 'email',
    key: 'email',
},{
    title: 'Phone',
    dataIndex: 'phone',
    key: 'phone',
}];

const data = [{
    key: '1',
    name: '任爹',
    courseNum: '5',
    email: 'ren@126.com',
    phone: '13912345678',
},{
    key: '2',
    name: '沈阿姨',
    courseNum: '3',
    email: 'shen@126.com',
    phone: '12345678910',
}];

class Excel extends React.Component{
    render(){
        return(
           <Table columns={columns} dataSource={data} />
        )
    }
}

export default Excel;