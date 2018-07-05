import React from 'react';
import {Button, Table }from "antd"


//export default () => <h1>Hello World</h1>;
const headers = [
    "教师姓名", "授课数量", "邮箱", "电话"
]

const data = [
    ["任锐","5","ren@126.com","12345678910"],
    ["沈阿姨","3","shen@126.com","13912345678"]
]

class Excel extends React.Component{
  constructor(props){
    super(props);
    this.state={
      data:data,
      headers:headers,
      sortby: null,
      descending: false,
      edit: null,
      search: false,
      _preSearchData: null,
    };
  }


  _sort(e){
    var column = e.target.cellIndex;
    var data = this.state.data.slice();
    var descending = this.state.sortby === column
    && !this.state.descending;
    data.sort((a,b)=>{
       return descending?(a[column]<b[column]?1:-1):
       (a[column]>b[column]?1:-1)        
    });
    this.setState({
      data:data,
      sortby:column,
      descending:descending,
    });
  }
 
  _showEditor(e){
    this.setState({edit:{
      row: parseInt(e.target.dataset.row, 10),
      cell: e.target.cellIndex,
    }});
  }

  _save(e){
    e.preventDefault();
    var input = e.target.firstchild;
    var data = this.state.data.slice();
    data[this.state.edit.row][this.state.edit.cell]=input.value;
    this.setState({
      edit:null,
      data:data,
    });
  }

  _toggleSearch(){
    if(this.state.search){
      this.setState({
        data: this.state._preSearchData,
        search: false
      });
      this.setState({
        _preSearchData: null
      });
      //this.state._preSearchData = null;
    }
    else{
      this.setState({
        _preSearchData: this.state.data
      })
      //this.state._preSearchData = this.state.data;
      this.setState({
        search: true,
      });
    }
  }

  _search(e){
    var needle = e.target.value.toLowerCase();
    if(!needle){
      this.setState({data: this.state._preSearchData});
      return;
    }
    var idx = e.target.dataset.idx;
    var searchdata = this.state._preSearchData.filter(
      (row)=>{
        return row[idx].toString().toLowerCase().indexOf(needle)>-1;
      }
    );
    this.setState({data: searchdata});
  }

  _download(format,ev){
    var contents = format === 'json'
       ?JSON.stringify(this.state.data)
       :this.state.data.reduce((result,row)=>{
          return result
            + row.reduce((rowresult,cell,idx)=>{
                return rowresult
                    +'"'
                    +cell.replace(/"/g,'""')
                    +'"'
                    +(idx < row.length - 1 ? ',' : '');
              }, '')
            +"\n";
       },'');
       var URL = window.URL || window.webkitURL;
       var blob = new Blob([contents],{type:'text/'+format});
       ev.target.href = URL.createObjectURL(blob);
       ev.target.download = 'data.'+format;
  }

  
  _renderToolbar(){
    return(
      <div className="toolbar">
       <Button onClick={this._toggleSearch.bind(this)}>Search</Button>
       <a onClick = {this._download.bind(this,'json')}href="data.json">
       Export JSON
       </a>
       <a onClick = {this._download.bind(this,'csv')}href="data.csv">
       Export CSV
       </a>
      </div>
    )
  }

  _renderSearch(){
    if(!this.state.search){
      return null;
    }
    return(
      <tr onChange={this._search.bind(this)}>
      {this.state.headers.map((_ignore,idx)=>{
        return <td key={idx}>
        <input type="text" data-idx={idx}/>
        </td>;
      })}
      </tr>
    )
  }

  _renderTable(){
    return(
      <Table>
        <thead onClick={this._sort.bind(this)}>
        <tr>{
          this.state.headers.map((title,idx)=>{
            if(this.state.sortby === idx){
              title += this.state.descending?'\u2191':'\u2193';
            }
            return <th key={idx}>{title}</th>
          },this)
        }</tr>
        </thead>
        <tbody onClick={this._showEditor.bind(this)}>
         {this._renderSearch()}
         {this.state.data.map((row,rowidx)=>{
           return(
            <tr key={rowidx}>{
              row.map((cell,idx)=>{
                var content = cell;
                var edit = this.state.edit;
                if(edit && edit.row === rowidx && edit.cell === idx){
                 content=(
                   <form onSubmit={this._save}>
                     <input type="text" defaultValue={cell}/>
                   </form>
                 );
               }
               return <td key={idx} data-row={rowidx}>{content}</td>
              },this)}
            </tr>
           );
         },this)}
         </tbody>
      </Table>
    );
  }

  render(){
    return(
      <div>
           {this._renderToolbar()}
      </div>
    );
  }
}

export default Excel;
