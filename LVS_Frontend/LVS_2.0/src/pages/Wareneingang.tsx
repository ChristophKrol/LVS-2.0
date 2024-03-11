import React from "react";
import {Line} from 'react-chartjs-2';
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
import styles from './styles/Wareneingang.module.css';
import Button from 'react-bootstrap/Button';


function Wareneingang(){
    const data = {
        labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
        datasets: [{ 
          label: 'Eingänge',
          data: [3,6,9,10,2,5],
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
            min:1,
            max:15
          }
        }
      }
    return (
        <>
        
            <h1>Wareneingang</h1>
            <div className={styles.dataWrapper}>
                <div className={styles.lieferungenVerlauf}>
                    <h2>Lieferungen pro Woche</h2> 
                    <Line data = {data} options ={options}></Line>
                </div>
                <div className={styles.kpiWrapper}>
                    <span className={styles.kpiSpan}>
                        <h2> Lieferungen diese Woche </h2>
                        <h2> 100 </h2>
                    </span>
                    <span className={styles.kpiSpan}>
                        <h2> Lieferungen heute </h2>
                        <h2> 10 </h2>
                    </span>
                </div>
            </div>
            <div className={styles.buttonDiv}>
                        <span className={styles.buttonWrapper}>
                            <DropdownButton id="dropdown-basic-button" title="Zeitraum auswählen">
                                <Dropdown.Item href="#/action-1">Woche</Dropdown.Item>
                                <Dropdown.Item href="#/action-2">Monat</Dropdown.Item>
                                <Dropdown.Item href="#/action-3">Zeitraum</Dropdown.Item>
                            </DropdownButton>
                            <Button variant="primary">Ware hinzufügen</Button>
                        </span>

                </div>
            
        </>
        
    );
}

export default Wareneingang