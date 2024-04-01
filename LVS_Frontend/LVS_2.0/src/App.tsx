import Footer from "./Footer.tsx";
import Header from "./Header.tsx";
import SidebarMenu from "./assets/components/SidebarMenu.tsx";
import MainDashboard from "./pages/MainDashboard.tsx";
import { useState } from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";

function App() {

  /**
   * 
        */

  return(

      <>
        <SidebarMenu/>
        <MainDashboard/>
       
      </>

      




  );
  
}

export default App
