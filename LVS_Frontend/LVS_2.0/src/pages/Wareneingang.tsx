import React, { useEffect, useState } from "react";
import {Line} from 'react-chartjs-2';
import { Pie } from "react-chartjs-2";
import{
    Chart as ChartJS,
    LineElement,
    CategoryScale,
    LinearScale,
    PointElement,
    Legend,
    Tooltip,
    ArcElement
  } from 'chart.js';

import Dropdown from 'react-bootstrap/Dropdown';
import 'bootstrap/dist/css/bootstrap.min.css';
import DropdownButton from 'react-bootstrap/DropdownButton';
import { Container } from "react-bootstrap";
import {Row} from "react-bootstrap";
import {Col} from "react-bootstrap";
import Button from 'react-bootstrap/Button';

import DashboardHeader from "../assets/components/DashboardHeader";
import styles from './styles/Wareneingang.module.css';
import PopupForm from "../assets/components/PopupForm";
import SidebarMenu from "../assets/components/SidebarMenu";

import {formatISO, subDays} from 'date-fns';
import Footer from "../Footer";
import CreateCategoryPopup from "../assets/components/CreateCategoryPopup";



function Wareneingang(){
  const[weeklyImports, setWeeklyImports] = useState(0);
  const [importsToday, setTotalImportsToday] = useState(0);
  const [totalImports, setTotalImports] = useState(0);
  const [totalImportValue, setTotalImportValue] = useState(0);

  // Für PieChart
  const [categorieData, setCategorieData] = useState([]);
  const [categories, setCategories] = useState([]);

  //für LineChart
  const[last7Days, setLast7Days] = useState([]);
  const[daysLabels, setDaysLabels] = useState([]);
  const[maxValue, setMaxValue] = useState(0);
  const[last7DaysImports, setLast7DaysImports] = useState([]);

  const [isPopupOpen, setIsPopupOpen] = useState(false);
  const [isCreateCategoryOpen, setIsCreateCategoryOpen] = useState(false);

    const openCreateCategory = () => {
      setIsCreateCategoryOpen(true);
    }
    const closeCreateCategory = () => {
      setIsCreateCategoryOpen(false);
    }

  const openPopup = () => {
    setIsPopupOpen(true);
  }
  const closePopup = () => {
    setIsPopupOpen(false);
  }

  //FETCH KPIs



  //Alle imports
  useEffect(() => {
    fetch('http://localhost:8080/server/itemhistory/getImports')
    .then(response => response.json())
    .then((responseData) => {
      setTotalImports(responseData.data.ImportsTotal);
      console.log(responseData.data.ImportsTotal);
    })
  }, [])

  //Importwert
  useEffect(() => {
    fetch('http://localhost:8080/server/itemhistory/getImportValue')
    .then(response => response.json())
    .then((responseData) => {{
      setTotalImportValue(responseData.data.getImportValue);
    }})
  }, []);

  //Tagesimport
  useEffect(() => {
    const currentTime = new Date();
    currentTime.setHours(0);
    currentTime.setMinutes(0);
    currentTime.setSeconds(0);
    const today0 = formatISO(currentTime, {representation: 'complete'}).slice(0,19);
    currentTime.setHours(23);
    currentTime.setMinutes(59);
    currentTime.setSeconds(59);
    const today1 = formatISO(currentTime, {representation: 'complete'}).slice(0,19);
    console.log(today0);
    console.log(today1);
    fetch('http://localhost:8080/server/itemhistory/getImports/' + today0 + '/' + today1)
    .then(response => response.json())
    .then(responseData => {
      setTotalImportsToday(responseData.data.getImportsByTime);
    })
  }, [])

  //Import letzte 7 Tage
  useEffect(()=> {
    const currentTime = formatISO(new Date(), {representation:'complete'}).slice(0,19);
    const last7Day = formatISO(subDays(currentTime, 7), { representation: 'complete'}).slice(0,19);
    fetch('http://localhost:8080/server/itemhistory/getImports/' + last7Day + '/' + currentTime)
    .then(response => response.json())
    .then(responseData => {
      setWeeklyImports(responseData.data.getImportsByTime);
    })
  }, [])


  // Lade Kategorien
  useEffect(() => {
    fetch("http://localhost:8080/server/itemhistory/getImports/category")
    .then(response => response.json())
    .then((responseData) => {
      setCategorieData(responseData.data.countImportedItemsPerCategory);
    })
  }, []);

     // Load Categories
     useEffect(() => {
      fetch("http://localhost:8080/server/category/list")
      .then(response => response.json())
      .then((responseData) => {
        setCategories(responseData.data.categories);
      })
    }, []);

  //Import-Verlauf
  useEffect(() => {
    const fetchData = async () => {
        const days = [];
        const daysLabel = [];
        const last7DaysImportsData = [];

        for (let dayCounter = 0; dayCounter < 7; dayCounter++) {
            let day = formatISO(subDays(new Date(), dayCounter), { representation: 'complete' }).slice(0, 19);
            let dayLabel = subDays(new Date(), dayCounter);
            days.unshift(day);
            daysLabel.unshift(dayLabel.toLocaleDateString('de-DE', { weekday: 'short' }));
        }

        days.unshift(formatISO(subDays(new Date(), 7), { representation: 'complete' }).slice(0, 19));

        setDaysLabels(daysLabel);
        setLast7Days(days);

        for (let i = 1; i < days.length; i++) {
            let dayBefore = days[i - 1];
            let dayAfter = days[i];
            const response = await fetch('http://localhost:8080/server/itemhistory/getImports/' + dayBefore + '/' + dayAfter);
            const responseData = await response.json();
            last7DaysImportsData.push(responseData.data.getImportsByTime);
        }

        setLast7DaysImports(last7DaysImportsData);
        setMaxValue(Math.max(... last7DaysImportsData));
    };

    fetchData();
}, []);






    const data = {
        labels: daysLabels,
        datasets: [{ 
          label: 'Eingänge',
          data: last7DaysImports,
          backgroundColor: 'blue',
          borderColor: 'blue',
          pointBorderColor: 'blue',
          tension:0.4
        }]
    }

    const options = {
        plugins: {
          legend: true
        },
        scales:{
          y: {
            min:0,
            max: maxValue + 3
          }
        }
    }
    const categoryData = {
      labels: categorieData.map(categoryData => categoryData.CategoryName),
      datasets:[
        {
          data: categorieData.map(categoryData => categoryData.Imports),
          backgroundColor: categories.map(category => category.color)
        }
      ]
    };

    const categoryOptions = {}

    const dashboardData = {
      kpis: [
        {name: 'Lieferungen heute', value: importsToday},
        {name: 'Lieferungen gesamt', value: totalImports},
        {name: 'Lieferungen diese Woche', value: weeklyImports},
        {name: 'Importierter Gesamtwert', value: totalImportValue.toFixed(2)},
      ]
    }



    return (
      <>
        <SidebarMenu/>
        <div style={{padding: "0px 0px 0px 50px", backgroundColor: '#fafafa'}}>
            <DashboardHeader title = "Wareneingang" kpiData = {dashboardData}/>
            <Container style={{backgroundColor: '#fafafa'}}>
              <Row>
                <Col className="text-center">
                  <h2>Lieferungen pro Woche</h2> 
                  <div className={styles.lieferungenVerlauf}>
                    
                    <Line data = {data} options={options}></Line>
                  </div>
                </Col>  
                <Col className="text-center">
                  <h2>Importierte Kategorien</h2> 
                  <div className={styles.categoriesImport}>
                    
                    <Pie data={categoryData} options={categoryOptions}></Pie>
                  </div>
                </Col>  
              </Row>    
              </Container>
                

            <div className={styles.buttonDiv}>
                        <span className={styles.buttonWrapper}>
                          <Button bsClass="standardButton" variant="primary" onClick={openCreateCategory}>Kategorie hinzufügen</Button>
                          {isCreateCategoryOpen && < CreateCategoryPopup onClose={closeCreateCategory}/>}
                          <Button bsClass="standardButton" variant="primary" onClick={openPopup}>Ware hinzufügen</Button>
                          {isPopupOpen && < PopupForm onClose={closePopup} />}
                        </span>

                </div>
                <Footer/>
            
        </div>
        

      </>
        
        
    );
}

export default Wareneingang