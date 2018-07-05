import React from 'react';
import ReactDOM from 'react-dom';
import IndexPage from './components/IndexPage';
import Login from './components/Login';
import Register from './components/Register';
import Personal from './components/Personal';
import Statistics from './components/Statistics';
import Allcourses from './components/Allcourses';
import Test from './components/PaginationTable';  //test the component
import registerServiceWorker from './registerServiceWorker';
import {BrowserRouter, Switch, Route} from 'react-router-dom';

const Main = () => (
    <main>
        <Switch>
        <Route path="/" exact component={IndexPage} />
        <Route path="/login" exact component={Login} />
        <Route path="/register" exact component={Register} />
        <Route path="/personal" exact component={Personal} />
        <Route path="/statistics" exact component={Statistics} />
        <Route path="/allcourses" exact component={Allcourses} />
        <Route path="/test" exact component={Test} />
        </Switch>
    </main>
)

const App = () => (
    <div>
        <Main />
    </div>
)

ReactDOM.render((
    <BrowserRouter>
        <App />
    </BrowserRouter>
),
     document.getElementById('root')
);
registerServiceWorker();
