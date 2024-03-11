import styles from './styles/MainDashboard.module.css';
import {Line} from 'react-chartjs-2';
import { Doughnut } from 'react-chartjs-2';
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
  const data = {
    labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
    datasets: [{ 
      label: 'Eingänge',
      data: [3,6,9,10,2,5],
      backgroundColor: 'blue',
      borderColor: 'blue',
      pointBorderColor: 'blue',
      tension:0.4
    },
    { 
      label: 'Ausgänge',
      data: [2,8,2,4,3,5],
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
        min:1,
        max:15
      }
    }

  }

  const auslastungData={
    labels:['Auslastung', 'Leer'],
    datasets: [{
      label: 'Auslastung',
      data: [700, 300],
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
  return(
    <>
      <header>
        <h1>Dashboard</h1>
        <div className={styles.header}>
          <span>
            <h2> Gelagerte Ware </h2>
            <h2> 100 </h2>
          </span>
          <span>
            <h2> Eingänge </h2>
            <h2> 50 </h2>
          </span>
          <span>
            <h2> Ausgänge</h2>
            <h2> 30 </h2>
          </span>
          <span>
            <h2> Warenwert </h2>
            <h2> 1249,50 </h2>
          </span>
        </div>

      </header>
      
      <div className={styles.diagramDiv}>

        <div className={styles.ein_ausgaenge}>
          <h2>Ein- und Ausgänge</h2>
          <Line data = {data} options ={options}></Line>
        </div>

        <div className={styles.auslastung}>
          <h2>Lagerauslastung</h2>
          <Doughnut 
            data = {auslastungData} 
            options = {auslastungOptions}
            >

          </Doughnut>
          
        </div>

      </div>

      
    
    </>
  )
}

export default MainDashboard