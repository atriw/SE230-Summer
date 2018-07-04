import React from 'react';
import { Router, Route, Switch } from 'dva/router';
import IndexPage from './routes/IndexPage';
import Login from './components/Login';
import Example from './components/Example';
import Register from './components/Register';
import Personal from './components/Personal';
import Statistics from './components/Statistics';
import Allcourses from './components/Allcourses';
//import component from './components/component';  引入组件（页面）
//在route加入引入的页面

function RouterConfig({ history }) {
  return (
    <Router history={history}>
      <Switch>
        <Route path="/" exact component={IndexPage} />
        <Route path="/example" exact component={Example} />
        <Route path="/login" exact component={Login} />
        <Route path="/register" exact component={Register} />
        <Route path="/personal" exact component={Personal} />
        <Route path="/statistics" exact component={Statistics} />
        <Route path="/allcourses" exact component={Allcourses} />
      </Switch>
    </Router>
  );
}

export default RouterConfig;
