import Footer from "./Footer.tsx";
import Header from "./Header.tsx";
import MainDashboard from "./assets/components/visualization/MainDashboard.tsx";
import { useState } from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";

function App() {

  return(

      <>
        <MainDashboard/>
        <Footer/>
      </>

      




  );
  
}

export default App
