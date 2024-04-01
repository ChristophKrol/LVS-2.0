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
import styles from './styles/Warenausgang.module.css';
import Footer from "../Footer";
import SidebarMenu from "../assets/components/SidebarMenu";
import PopupExport from "../assets/components/PopupExport";
import { formatISO, subDays } from "date-fns";


function Warenausgang(){

  const [totalExportsToday, setTotalExportsToday] = useState(0);
  const [totalExports, setTotalExports] = useState(0);
  const[weeklyExports, setWeeklyExports] = useState(0);
  const [totalExportValue, setTotalExportValue] = useState(0);
  const[categoryValues, setCategoryValues] = useState([]);
  const[categoryExportCount, setCategoryExportCount] = useState([]);
  const[daysLabels, setDaysLabels] = useState([]);
  const[last7Days, setLast7Days] = useState([]);
  const[maxValue, setMaxValue] = useState(0);
  const [last7DaysExports, setLast7DaysExports] = useState([]);
  const [categories, setCategories] = useState([]);

  //fetch categories for colors
  useEffect(() => {
    fetch("http://localhost:8080/server/category/list")
    .then(response => response.json())
    .then((responseData) => {
      setCategories(responseData.data.categories);
    })
  }, []);


  // total exports today
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
    fetch('http://localhost:8080/server/itemhistory/getExports/' + today0 + '/' + today1)
    .then(response => response.json())
    .then(responseData => {
      setTotalExportsToday(responseData.data.countAllExportedItems);
    })
  }, [])

  //total exports
  useEffect(() => {
    fetch('http://localhost:8080/server/itemhistory/getExports')
    .then(response => response.json())
    .then((responseData) => {
      setTotalExports(responseData.data.countAllExportedItems);
      console.log(responseData.data.countAllExportedItems);
    })
  }, []);

  // Export letzte 7 Tage
  useEffect(() => {
    const fetchData = async () => {
        const days = [];
        const daysLabel = [];
        const last7DaysExportsData = [];
  
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
          const response = await fetch('http://localhost:8080/server/itemhistory/getExports/' + dayBefore + '/' + dayAfter);
          const responseData = await response.json();
          last7DaysExportsData.push(responseData.data.countAllExportedItems);
      }
      setLast7DaysExports(last7DaysExportsData);
      setMaxValue(Math.max(... last7DaysExportsData));
      };
  
      fetchData();
    }, []);

  //Export Value
  useEffect(() => {
    fetch('http://localhost:8080/server/itemhistory/getExportValue')
    .then(response => response.json())
    .then((responseData) => {{
      setTotalExportValue(responseData.data.getExportValue);
    }})
  }, []);



  //ExportValueOfCategories
  useEffect(() => {
    fetch('http://localhost:8080/server/itemhistory/getExportValue/category')
    .then(response => response.json())
    .then((responseData) => {{
      setCategoryValues(responseData.data.ExportValueOfCategories);
    }})
  }, []);


  // Count Exports of categories
  useEffect(() => {
    fetch('http://localhost:8080/server/itemhistory/getExports/category')
    .then(response => response.json())
    .then((responseData) => {{
      setCategoryExportCount(responseData.data.countExportedItemsPerCategory);
    }})
  }, []);

  //weekly Exports
  useEffect(()=> {
    const currentTime = formatISO(new Date(), {representation:'complete'}).slice(0,19);
    const last7Day = formatISO(subDays(currentTime, 7), { representation: 'complete'}).slice(0,19);
    fetch('http://localhost:8080/server/itemhistory/getExports/' + last7Day + '/' + currentTime)
    .then(response => response.json())
    .then(responseData => {
      setWeeklyExports(responseData.data.countAllExportedItems);
    })
  }, [])





    const data = {
        labels: daysLabels,
        datasets: [{ 
          label: 'Ausgänge',
          data: last7DaysExports,
          backgroundColor: 'red',
          borderColor: 'red',
          pointBorderColor: 'red',
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
            max:maxValue + 3
          }
        }
    }
    const categoryData = {
      labels: categoryExportCount.map(categoryExports => categoryExports.category),
      datasets:[
        {
          data: categoryExportCount.map(categoryExports => categoryExports.value),
          backgroundColor: categories.map(category => category.color)
        }
      ]
    };
    const categoryOptions = {}

    const categoryValueData = {
        labels: categoryValues.map(categoryValue => categoryValue.category),
        datasets:[
          {
            data: categoryValues.map(categoryValue => categoryValue.value),
            backgroundColor: categories.map(category => category.color)
          }
        ]
    };
    const categoryValueOptions = {}



    const dashboardData = {
      kpis: [
        {name: 'Export heute', value: totalExportsToday},
        {name: 'Export gesamt', value: totalExports},
        {name: 'Export diese Woche', value: weeklyExports},
        {name: 'Exportierter Gesamtwert', value: totalExportValue.toFixed(2) + '€'},
      ]
    }




    	
    const [isPopupOpen, setIsPopupOpen] = useState(false);

    const openPopup = () => {
      setIsPopupOpen(true);
    }
    const closePopup = () => {
      setIsPopupOpen(false);
    }




    return (


      <>
        <SidebarMenu/>
        <div className={styles.mainSection}>
            <DashboardHeader title = "Warenausgang" kpiData = {dashboardData}/>

            <Container className={styles.main}>
              <Row>
                <Col className="text-center">
                  <h2>Exporte pro Woche</h2> 
                  <Container fluid className={styles.lineChart}>
                    <div className={styles.lieferungenVerlauf}> 
                        <Line data = {data} options ={options}></Line>
                    </div>
                  </Container>  
                </Col>  
              </Row>   
              <Row>
                <Col className="text-center">
                  <h2>Wertmäßiger Anteil</h2> 
                  <div className={styles.categoriesImport}>
                    <Pie data={categoryValueData} options={categoryValueOptions}></Pie>
                  </div>
                </Col>  
                <Col className="text-center">
                    <h2>Exportierte Kategorien</h2> 
                    <div className={styles.categoriesImport}>        
                        <Pie data={categoryData} options={categoryOptions}></Pie>
                    </div>
                    
                </Col>
              </Row> 
              </Container>
                

            <div className={styles.buttonDiv}>
                        <span className={styles.buttonWrapper}>
                            
                            <Button bsClass="standardButton" variant="primary" onClick={openPopup}>Waren exportieren</Button>
                            {isPopupOpen && < PopupExport onClose={closePopup} />}
                        </span>

            </div>
            <Footer></Footer>
            
        </div>
      </>  
        
    );

}

export default Warenausgang
