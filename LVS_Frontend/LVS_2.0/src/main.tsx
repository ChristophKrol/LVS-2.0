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
import Lagerflaechen from './pages/Lagerflaechen.tsx';


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
  },
  {
    path: "/lagerflaechen",
    element: <Lagerflaechen/>,
  }
]);

ReactDOM.createRoot(document.getElementById('root')!).render(
  <RouterProvider router={router} />
);


