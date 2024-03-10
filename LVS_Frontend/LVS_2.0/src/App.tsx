import Footer from "./Footer.tsx";
import Header from "./Header.tsx";
import MainDashboard from "./MainDashboard.tsx";
import { Itemdata } from "./assets/data/ItemData.ts";
import DoughnutChart from "./assets/components/visualization/DonutChart.tsx";
import LineChartDashboard from "./assets/components/visualization/LineChart.tsx";
import { useState } from "react";

function App() {

  return(
    <>
        <Header/>
        <MainDashboard/>
        <DoughnutChart/>
        <LineChartDashboard/>
        <Footer/>
    </>

  );
  
}

export default App
