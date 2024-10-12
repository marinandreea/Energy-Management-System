import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import GetDevicesForUser from './user/GetDevicesForUser';
import UpdateUser from './user/UpdateUser';
import UpdateDevice from './devices/UpdateDevices';
import FetchData from './user/FetchData';
import ManageDevicesPage from './devices/ManageDevicesPage';
import ChatRoom from './chatApp/ChatRoom';
import ChatRoomUser from './chatApp/ChatRoomUser';

import {
  createBrowserRouter,
  RouterProvider,
  Route,
  Link,
} from "react-router-dom";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App/>,
  },
  {
    path: "/manageDevicesPage",
    element: <ManageDevicesPage/>,
  },
  {
    path: "/getDevicesUser/:id",
    element: <GetDevicesForUser/>,
  },
  {
    path: "/fetch",
    element: <FetchData/>,
  },
  {
    path: "/update/:id",
    element: <UpdateUser/>,
  },
  {
    path: "/updateD/:id",
    element: <UpdateDevice/>,
  },
  {
    path: "/chat/:usernm",
    element: <ChatRoom/>,
  },
  {
    path: "/chatUser/:usernm/",
    element: <ChatRoomUser/>,
  },

]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    {/* <App /> */}
    <RouterProvider router={router}/>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
