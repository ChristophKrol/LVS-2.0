import React from 'react'
import ReactDOM from 'react-dom/client'
import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom";
import App from './App.tsx'
import './index.css'
import Wareneingang from './pages/Wareneingang.tsx';
import Warenausgang from './pages/Warenausgang.tsx';


const router = createBrowserRouter([
  {
    path: "/",
    element: <App/>,
  },
  {
    path: "/wareneingang",
    element: <Wareneingang/>,
  },
  {
    path: "/warenausgang",
    element: <Warenausgang/>,
  }
]);

ReactDOM.createRoot(document.getElementById('root')!).render(
  <RouterProvider router={router} />
);


