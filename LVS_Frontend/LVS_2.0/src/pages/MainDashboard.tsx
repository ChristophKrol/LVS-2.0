import styles from './styles/MainDashboard.module.css';
import {Line} from 'react-chartjs-2';
import { Doughnut } from 'react-chartjs-2';
import { Pie } from 'react-chartjs-2';
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
import DashboardHeader from '../assets/components/DashboardHeader';
import { Container, Row, Col } from 'react-bootstrap';
import { useEffect, useState } from 'react';
import { formatISO, subDays } from 'date-fns';
import { MoveToInboxSharp } from '@mui/icons-material';
import Footer from '../Footer';

ChartJS.register(
  LineElement,
  CategoryScale,
  LinearScale,
  PointElement,
  Legend,
  Tooltip,
  ArcElement
)




function MainDashboard(){

  //LineChart Data
  const[last7Days, setLast7Days] = useState([]);
  const[daysLabels, setDaysLabels] = useState([]);
  const[maxValue, setMaxValue] = useState(0);
  const[last7DaysImports, setLast7DaysImports] = useState([]);
  const [last7DaysExports, setLast7DaysExports] = useState([]);

  //PieCharts
  const [containers, setContainers]=  useState([]);
  const [capacityTotal, setCapacityTotal] = useState(0);
  const [curCapacity, setCurCapacity] = useState(0);
  const [utilization, setUtilization] = useState(0);
  const [categories, setCategories] = useState([]);
  const [categoryData, setCategoryData] = useState([]);
  const [categoryValues, setCategoryValues] = useState([]);
  const [itemCount, setItemCount] = useState(0);
  
  //KPI
  const[importsCount, setImportsCount] = useState(0);
  const[exportsCount, setExportsCount] = useState(0);
  const[totalItemValue, setTotalItemValue] = useState(0);

  // Item overview
  const[itemData, setItemData] = useState([]);
  //Item overview - Filter Data
  const[chosenCategory, setChosenCategory] = useState(0);
  const[chosenRegal, setChosenRegal] = useState(0);

  //Get category Value
  useEffect(() => {
    fetch('http://localhost:8080/server/item/itemValueByCategory')
    .then(response => response.json())
    .then((responseData) => {
      setCategoryValues(responseData.data.catPriceSum);
    })
  }, []);


   // Item Count Per Catgeory
  useEffect(() => {
    fetch('http://localhost:8080/server/item/itemCountPerCategory')
    .then(response => response.json())
    .then((responseData) => {
      setCategoryData(responseData.data.catGroup);
    })
  }, []);
 
  //imports count 
  useEffect(() => {
    fetch('http://localhost:8080/server/itemhistory/getImports')
    .then(response => response.json())
    .then((responseData) => {
      setImportsCount(responseData.data.ImportsTotal);
      //console.log(responseData.data.ImportsTotal);
    })
  }, [])

  // get total exports
  useEffect(() => {
    fetch('http://localhost:8080/server/itemhistory/getExports')
    .then(response => response.json())
    .then((responseData) => {
      setExportsCount(responseData.data.countAllExportedItems);
      //console.log(responseData.data.ImportsTotal);
    })
  }, []);
  
  
  
 // total item value 
  useEffect(() => {
    fetch('http://localhost:8080/server/item/totalValue')
    .then(response => response.json())
    .then((responseData) => {
      setTotalItemValue(responseData.data.itemValue);
    })
  })

  // Container Capacity Data
  useEffect(() => {
    fetch("http://localhost:8080/server/container/list")
    .then(response => response.json())
    .then((responseData) => {
        const containerData = responseData.data.containers;
      setContainers(containerData);
      // sum totalCapacity of all containers 
      const totalCapacity = containerData.reduce((accumulator, container) => accumulator + container.maxCapacity, 0);
      setCapacityTotal(totalCapacity);
      //sum usedCapacity of all Containers
      const usedCapacity = containerData.reduce((accumulator, container) => accumulator + container.curCapacity, 0);
      setCurCapacity(usedCapacity);
      setUtilization(usedCapacity/totalCapacity * 100); 
    })
  }, []);


  //LineChart Import
  useEffect(() => {
    const fetchData = async () => {
        const days = [];
        const daysLabel = [];
        const last7DaysImportsData = [];
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

        //Imports
        for (let i = 1; i < days.length; i++) {
            let dayBefore = days[i - 1];
            let dayAfter = days[i];
            const response = await fetch('http://localhost:8080/server/itemhistory/getImports/' + dayBefore + '/' + dayAfter);
            const responseData = await response.json();
            last7DaysImportsData.push(responseData.data.getImportsByTime);
        }
        setLast7DaysImports(last7DaysImportsData);
        setMaxValue(Math.max(... last7DaysImportsData, maxValue));
    };

    fetchData();
}, [maxValue]);

//LineChart Export
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
    setMaxValue(Math.max(... last7DaysExportsData, maxValue));    

    };

    fetchData();
  }, [maxValue]);

   // total item count
   useEffect(() => {
    fetch('http://localhost:8080/server/item/list')
    .then(response => response.json())
    .then((responseData) => {
      setItemCount(responseData.data.items.length);
    })
  })

   // Load Categories
   useEffect(() => {
    fetch("http://localhost:8080/server/category/list")
    .then(response => response.json())
    .then((responseData) => {
      setCategories(responseData.data.categories);
    })
  }, []);

  // Load item overview data
  useEffect(() => {
    // Fetch All ItemData
    if(chosenCategory == 0 && chosenRegal == 0){
      fetch("http://localhost:8080/server/item/list/grouped")
      .then(response => response.json())
      .then((responseData) => {
        setItemData(responseData.data.items);
      });

    }
    // Fetch ItemData by Category
    else if(chosenCategory != 0 && chosenRegal == 0){
      fetch('http://localhost:8080/server/item/list/grouped/category/' + chosenCategory)
      .then(response => response.json())
      .then((responseData) => {
        setItemData(responseData.data.items);
      });
    }
    // Fetch ItemData by Container
    else if(chosenCategory == 0 && chosenRegal != 0){
      fetch('http://localhost:8080/server/item/list/grouped/container/' + chosenRegal)
      .then(response => response.json())
      .then((responseData) => {
        setItemData(responseData.data.items);
      });
    }
    // Fetch ItemData by Both
    else{
      fetch('http://localhost:8080/server/item/list/grouped/container/' + chosenRegal +'/category/' + chosenCategory)
      .then(response => response.json())
      .then((responseData) => {
        setItemData(responseData.data.items);
      });
    }

  }, [chosenCategory, chosenRegal]);

  

  
  



  const data = {
    labels: daysLabels,
    datasets: [{ 
      label: 'Eingänge',
      data: last7DaysImports,
      backgroundColor: 'blue',
      borderColor: 'blue',
      pointBorderColor: 'blue',
      tension:0.4
    },
    { 
      label: 'Ausgänge',
      data: last7DaysExports,
      backgroundColor: 'red',
      borderColor: 'red',
      pointBorderColor: 'red',
      tension:0.4
    }
  ]
  };
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

  const auslastungData={
    labels:['Auslastung', 'Leer'],
    datasets: [{
      label: 'Auslastung',
      data: [utilization, 100 - utilization],
      backgroundColor:['red','grey'],
      borderColor:['red','grey']
    }]
  };

  const auslastungOptions={

  }
  /*
  const textCenter = {
    id:'textCenter',
    beforeDatasetsDraw(chart, args, pluginOptions){
      const{ctx, data} = chart;
      ctx.save();
      ctx.font ='bolder 30px sans-serif'
      ctx.fillStyle = 'red';
      ctx.fillText('text', chart.getDataSetMeta(0).data[0].x, chart.getDataSetMeta(0).data[0].y)
    }
    
  }
  */
  const main = {
    kpis: [
    {name: 'Gelagerte Ware', value: itemCount},
    {name: 'Eingänge', value: importsCount},
    {name: 'Ausgänge', value: exportsCount},
    {name: 'Warenwert', value: totalItemValue.toFixed(2) + '€'}
    ]
  }

  const warenGruppenData = {
    labels: categoryData.map(data => data.category),
    datasets:[
      {
        data: categoryData.map(data => data.count),
        backgroundColor: categories.map(category => category.color)
      }
    ]
  };

  const warenGruppenOptions = {};

  const warenGruppenValueData = {
    labels: categoryValues.map(categoryData => categoryData.category),
    datasets:[
      {
        data: categoryValues.map(categoryData => categoryData.price),
        backgroundColor: categories.map(category => category.color)
      }
    ]
  }
  const warenGruppenValueOptions = {};

  return(
    <div style={{padding: "0px 0px 0px 50px"}} >
      <DashboardHeader title="Dashboard" kpiData={main}/> 
      
      <div className={styles.diagramDiv}>

        <div className={styles.ein_ausgaenge}>
          <span>
            <h2>Ein- und Ausgänge</h2>
          </span>
          <Container fluid className={styles.lineChartContainer}> 
            <div className={styles.lineChart}>
              <Line data = {data} options ={options}></Line>
            </div>  
          </Container>
        </div>
      </div>
      
      <section className={styles.warenDaten}>
        <h1>Warendaten</h1>
        <Container fluid className={styles.pieChartContainer}>
          <Row className={styles.warenGruppenRow}>
            <Col className='text-center'>
              <h2> Warenkategorien </h2>
              <div className={styles.piechartDiv}>
                <Pie data={warenGruppenData} options={warenGruppenOptions}></Pie>
              </div>
            </Col>

            <Col className='text-center'>
              <h2> Wertmäßiger Anteil </h2>
              <div className={styles.piechartDiv}>
                <Pie data={warenGruppenValueData} options={warenGruppenValueOptions}></Pie>
              </div>
            </Col>

            <Col className='text-center'>
              <h2>Lagerauslastung</h2>
              <div className={styles.piechartDiv}>
                <Doughnut 
                  data = {auslastungData} 
                  options = {auslastungOptions}
                >

                </Doughnut>
          
              </div>
            </Col>
          </Row>
        </Container>

      </section>
      <section className={styles.warenUebersicht}>
        <h1>Warenübersicht</h1>
        <div className={styles.tableFilter}>
            <select className='select' onChange={(e)=>{
              const category = e.target.value;
              setChosenCategory(category);
            }}
            >
              <option value="0">Kategorie auswählen</option>
              {categories.map(category =>(
                <option value={category.id}>{category.name}</option>
              ))}
            </select>

            <select className='select' onChange={(e) =>{
              const regal = e.target.value;
              setChosenRegal(regal);
            }}
            >
               <option value="0">Alle</option>
                  {containers.map(regal =>(
                    <option value={regal.id}>{regal.name}</option>
                  ))}
            </select>

          </div>
        <div className={styles.tableContainer}>
          <table className="table table-striped table-hover table-bordered" id="table-panel">
              <thead>
                <tr>
                  <th> Name </th>
                  <th> Größe </th>
                  <th> Preis </th>
                  <th> Kategorie </th>
                  <th> Regal ID </th>
                  <th> Anzahl </th>
                </tr>
              </thead>
              <tbody>
                { itemData.map(data =>(
                  <tr>
                    <th> {data.name} </th>
                    <th> {data.space} </th>
                    <th> {data.price} </th>
                    <th> {data.category} </th>
                    <th> {data.container_id} </th>
                    <th> {data.itemCount} </th>
                  </tr>
                )) }

              </tbody>
          </table>
        </div>        
      </section>

      <Footer/>
    
    </div>
  )
}

export default MainDashboard