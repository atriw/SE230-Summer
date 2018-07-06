import React from 'react';
import ReactDOM from 'react-dom';
import IndexPage from './components/Pages/IndexPage';
import Login from './components/Pages/Login';
import Register from './components/Pages/Register';
import Personal from './components/Pages/Personal';
import Statistics from './components/Pages/Statistics';
import Allcourses from './components/Pages/Allcourses';
import Allteachers from './components/Pages/Allteachers';
import Test from './components/Parts/PaginationTable';  //test the component
import registerServiceWorker from './registerServiceWorker';
import {BrowserRouter, Switch, Route} from 'react-router-dom';
import CourseDetail from "./components/Pages/CourseDetail";
import AddCoursePage from "./components/Pages/AddCoursePage";

const Main = () => (
    <main>
        <Switch>
                <Route path="/" exact component={IndexPage} />
                <Route path="/login" exact component={Login} />
                <Route path="/register" exact component={Register} />
                <Route path="/personal" exact component={Personal} />
                <Route path="/statistics" exact component={Statistics} />
                <Route path="/allcourses" exact component={Allcourses} />
                <Route path="/allteachers" exact component={Allteachers} />
                <Route path="/Math" exact component={CourseDetail}/>
                <Route path="/test" exact component={Test} />
                <Route path="/addCourse" exact component={AddCoursePage} />
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
