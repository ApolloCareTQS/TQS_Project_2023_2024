import React, { useContext } from 'react';
import { Route, Redirect } from 'react-router-dom';
import { AuthContext } from '../App';

const PrivateRoute: React.FC<{ component: React.FC; path: string; exact: boolean }> = ({ component: Component, path, exact }) => {
  const { checkAuth } = useContext(AuthContext);

  return (
    <Route
      path={path}
      exact={exact}
      render={(props) =>
        checkAuth() ? <Component {...props} /> : <Redirect to="/login" />
      }
    />
  );
};

export default PrivateRoute;
