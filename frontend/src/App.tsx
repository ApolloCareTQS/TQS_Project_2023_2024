import { Redirect, Route } from 'react-router-dom';
import {
  IonApp,
  IonIcon,
  IonLabel,
  IonRouterOutlet,
  IonTabBar,
  IonTabButton,
  IonTabs,
  setupIonicReact
} from '@ionic/react';
import { IonReactRouter } from '@ionic/react-router';
import { ellipse, square, home, calendar } from 'ionicons/icons';
import Home from './pages/Home';
import Schedule from './pages/Schedule';
import Tab3 from './pages/Tab3';

/* Core CSS required for Ionic components to work properly */
import '@ionic/react/css/core.css';

/* Basic CSS for apps built with Ionic */
import '@ionic/react/css/normalize.css';
import '@ionic/react/css/structure.css';
import '@ionic/react/css/typography.css';

/* Optional CSS utils that can be commented out */
import '@ionic/react/css/padding.css';
import '@ionic/react/css/float-elements.css';
import '@ionic/react/css/text-alignment.css';
import '@ionic/react/css/text-transformation.css';
import '@ionic/react/css/flex-utils.css';
import '@ionic/react/css/display.css';

/**
 * Ionic Dark Mode
 * -----------------------------------------------------
 * For more info, please see:
 * https://ionicframework.com/docs/theming/dark-mode
 */

/* import '@ionic/react/css/palettes/dark.always.css'; */
/* import '@ionic/react/css/palettes/dark.class.css'; */
/* import '@ionic/react/css/palettes/dark.system.css'; */

/* Theme variables */
import './theme/variables.css';
import Login from './pages/Login';
import Register from './pages/Register';
import React, {useState,createContext,useContext, useEffect} from "react";
import axios from 'axios';
import PrivateRoute from './components/PrivateRoute';

setupIonicReact();

export interface User {
  id: "",
  username: "",
  signedIn: false
}

export const AuthContext = createContext<any>(null);
const backendURI = "http://localhost:8080/";

const App: React.FC = () => { 
  const [authState, setAuthState] = useState<User | null>(null);

  useEffect(() => {
    //console.log(authState);
    const storedAuthState = localStorage.getItem('authState');
    if (storedAuthState) {
      console.log(storedAuthState);
      //setAuthState(JSON.parse(storedAuthState));
    }
  }, []);

  const login = (user: User) => {
    //setAuthState(user);
    localStorage.setItem('authState', JSON.stringify(user));
  }

  const logout = () => {
    //setAuthState(null);
    localStorage.removeItem('authState');
  }

  const checkAuth = () => {
    const storedAuthState = localStorage.getItem('authState');
    if (storedAuthState) {
      return JSON.parse(storedAuthState);
    }
    return null;
  }

  return (
    <AuthContext.Provider value={{ authState, login, logout, checkAuth }}>
      <IonApp>
        <IonReactRouter>
          {/*<IonTabs>*/}
          <IonRouterOutlet>
            <Route exact path="/home">
              <Home />
            </Route>
            <Route exact path="/login">
              <Login />
            </Route>
            <Route exact path="/register">
              <Register />
            </Route>
            {/*<Route exact path="/schedule">
            <Schedule />
          </Route>
          <Route path="/appointments">
            <Tab3 />
          </Route>
          */}
            <PrivateRoute exact path="/schedule" component={Schedule} />
            <PrivateRoute exact path="/appointments" component={Tab3} />
            <Route exact path="/">
              <Redirect to="/home" />
            </Route>
          </IonRouterOutlet>
        </IonReactRouter>
      </IonApp>
    </AuthContext.Provider>
);
}

export default App;
