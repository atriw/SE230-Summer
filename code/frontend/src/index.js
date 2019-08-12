import React from 'react';
import ReactDOM from 'react-dom';
import IndexPage from './containers/IndexPage';
import Login from './containers/LoginPage';
import Register from './containers/RegisterPage';
import Personal from './containers/PersonalPage';
import Statistics from './containers/StatisticsPage';
import Allcourses from './containers/AllCoursesPage';
import Allteachers from './containers/AllTeachersPage';
import registerServiceWorker from './registerServiceWorker';
import {BrowserRouter, Switch, Route} from 'react-router-dom';
import CourseDetail from "./containers/CourseDetailPage";
import AddCoursePage from "./containers/AddCoursePage";
import RequireAuth from "./RequireAuth"
const Main = () => (
    <main>
        <Switch >
                <Route path="/" exact component={RequireAuth(IndexPage)}/>
                <Route path="/login" exact component={Login} />
                <Route path="/register" exact component={Register} />
                <Route path="/personal" exact component={RequireAuth(Personal)}/>
                <Route path="/statistics" exact component={RequireAuth(Statistics)} />
                <Route path="/allcourses" exact component={RequireAuth(Allcourses)} />
                <Route path="/allcourses/:name" exact component={RequireAuth(Allcourses)} />
                <Route path="/allteachers" exact component={RequireAuth(Allteachers)} />
                <Route path="/course/:id" exact component={RequireAuth(CourseDetail)} />
                <Route path="/addCourse" exact component={RequireAuth(AddCoursePage)} />
        </Switch>
    </main>
);

const App = () => (
    <div>
        <Main />
    </div>
);

ReactDOM.render((
    <BrowserRouter >
        <App />
    </BrowserRouter>
),
     document.getElementById('root')
);
registerServiceWorker();
