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
            <div className={styles.lieferungenVerlauf}>
                <span className={styles.lieferungenHeader}>
                    <h2>Ein- und Ausgänge</h2>
                    <DropdownButton id="dropdown-basic-button" title="Dropdown button">
                        <Dropdown.Item href="#/action-1">Action</Dropdown.Item>
                        <Dropdown.Item href="#/action-2">Another action</Dropdown.Item>
                        <Dropdown.Item href="#/action-3">Something else</Dropdown.Item>
                    </DropdownButton>
                </span>  
                <Line data = {data} options ={options}></Line>
            </div>
        </>
        
    );
}

export default Wareneingang