import React, { useEffect, useState } from 'react';
import {Row, Col} from 'react-bootstrap';
import Dropdown from 'react-bootstrap/Dropdown';
import DropdownButton from 'react-bootstrap/DropdownButton';
import Button from 'react-bootstrap/Button';
import styles from './styles/PopupForm.module.css';
import CloseIcon from '@mui/icons-material/Close';
import IconButton from '@mui/material/IconButton';
import { Pie } from 'react-chartjs-2';

const PopupRegal = (props) => {
  const {onClose, regalID} = props;

  const handleOutsideClick = (e) => {
    if (e.target === e.currentTarget) {
      onClose();
    }
  };

  const [categories, setCategories] = useState([]);
  const [category, setCategory] = useState("Alle"); 
  const [itemCount, setItemCount] = useState([]);
  const [name, setName] = useState("");
  const [id, setID] = useState(0);
  const [maxCapacity, setMaxCapacity] = useState(0);
  const [curCapacity, setCurCapacity] = useState(0);
  const [utilization, setUtilization] = useState(0);
  const [value, setValue] = useState(0);

  //Fetch ContainerData
  useEffect(() => {
    fetch('http://localhost:8080/server/container/get/' + regalID)
    .then(response => response.json())
    .then(responseData => {
      const container = responseData.data.container;
      setID(container.id);
      setName(container.name);
      setMaxCapacity(container.maxCapacity);
      setCurCapacity(container.curCapacity);
      const util = Math.round(container.curCapacity / container.maxCapacity * 100);
      setUtilization(util);
    })
  }, []);

  // Load ItemCount per Category
  useEffect(() => {
    fetch('http://localhost:8080/server/item/itemCountPerCategory/container/' + id)
    .then(response => response.json())
    .then(responseData => {setItemCount(responseData.data.catGroup); console.log(responseData.data.catGroup);});
  }, [])

    // Load Categories
    useEffect(() => {
      fetch("http://localhost:8080/server/category/list")
      .then(response => response.json())
      .then((responseData) => {
        setCategories(responseData.data.categories);
      })
    }, []);

  const categoryData = {
    labels: itemCount.map(category => category.name),
    datasets:[
      {
        data: itemCount.map(category => category.count),
        backgroundColor:categories.map(category => category.color)
      }
    ]
  };
  const categoryOptions = {}

  return (
    <>
      <div className={styles.backgroundDiv}
        onClick={handleOutsideClick}
      >
        <div className={styles.formDiv} id='regalForm'>
          <div className={styles.closingButtonArea}> <IconButton onClick={onClose} aria-label="close"> <CloseIcon /> </IconButton> </div>
          <h2>{name}</h2>
          <Row>
            <div className='col d-flex align-items-center'>
                <div className={styles.regalKPIWrapper}>
                    <span className={styles.regalKPI}> <h4>Regalnummer</h4> <h4>{id}</h4></span>
                    <span className={styles.regalKPI}> <h4>Max. Kapazität</h4> <h4>{maxCapacity}</h4></span>
                    <span className={styles.regalKPI}> <h4>Genutzte Kapazität</h4> <h4>{curCapacity}</h4></span>
                    <span className={styles.regalKPI}> <h4>Auslastung</h4> <h4>{utilization}%</h4></span>
                    <span className={styles.regalKPI}> <h4>Warenwert</h4> <h4>1000€</h4></span>
                </div>

            </div>
            <Col>
                <div className={styles.categoriesImport}>
                    <Pie data={categoryData} options={categoryOptions}></Pie>
                </div>
            </Col>
          </Row>
          
        </div>
          

        </div>
   
    </>  
  );
};

export default PopupRegal;
