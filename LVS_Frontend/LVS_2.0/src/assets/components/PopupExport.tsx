import React, { useEffect, useState } from 'react';
import {Row, Col} from 'react-bootstrap';
import Dropdown from 'react-bootstrap/Dropdown';
import DropdownButton from 'react-bootstrap/DropdownButton';
import Button from 'react-bootstrap/Button';
import styles from './styles/PopupForm.module.css';
import CloseIcon from '@mui/icons-material/Close';
import IconButton from '@mui/material/IconButton';
import { Bounce, toast } from 'react-toastify';


const notifySuccess = () => {
  toast.success('Ware erfasst', {
    position: "top-right",
    autoClose: 5000,
    hideProgressBar: true,
    closeOnClick: true,
    pauseOnHover: true,
    draggable: true,
    progress: undefined,
    theme: "colored",
    transition: Bounce
    });
}

const PopupExport = ({ onClose }) => {

  const[chosenCategory, setChosenCategory] = useState(0);
  const [chosenRegal, setChosenRegal] = useState(0);
  const [categories, setCategories] = useState([]);
  const [regale, setRegale] = useState([]);
  const[items, setItems] = useState([]);
  const [itemsToDelete, setItemsToDelete] = useState([]);

  const handleSelectCategory = (event) =>{
    setChosenCategory(event.target.value);
  }
  const handleSelectRegal = (event) =>{
    console.log(event.target.value);
    setChosenRegal(event.target.value);
    //console.log(items);
  }
       

  // Load Categories
  useEffect(() => {
    fetch("http://localhost:8080/server/category/list")
    .then(response => response.json())
    .then((responseData) => {
      setCategories(responseData.data.categories);
    })
  }, []);

  // Load Containers
  useEffect(() => {
    fetch("http://localhost:8080/server/container/list")
    .then(response => response.json())
    .then((responseData) => {
      setRegale(responseData.data.containers);
    })
  }, []);

  // LOADING ITEMS

  //Load all Items
  useEffect(() => {
    //ALL ITEMS
    if(chosenCategory == 0 && chosenRegal == 0){
      fetch('http://localhost:8080/server/item/list')
      .then(response => response.json())
      .then(responseData => setItems(responseData.data.items));
    }
    // Fetch by Categroy
    else if(chosenCategory != 0 && chosenRegal == 0){
      fetch('http://localhost:8080/server/item/get/category/' + chosenCategory)
          .then(response => response.json())
          .then(responseData => setItems(responseData.data.items));
    }
    // Fetch by Regal 
    else if(chosenCategory == 0 && chosenRegal != 0){
      fetch('http://localhost:8080/server/item/get/container/' + chosenRegal)
      .then(response => response.json())
      .then(responseData => setItems(responseData.data.container));
    }
    // Fetch by Both
    else{
      fetch('http://localhost:8080/server/item/get/container/' 
      + chosenRegal + '/category/' + chosenCategory)
      .then(response => response.json())
      .then(responseData => setItems(responseData.data.items));
    }
    
  }, [chosenCategory, chosenRegal]);

  

   



  const handleOutsideClick = (e) => {
    if (e.target === e.currentTarget) {
      onClose();
    }
  };

  const handleCheckboxChange = (e, itemId) => {
    const isChecked = e.target.checked;
    if (isChecked) {
      setItemsToDelete((prevIds) => [...prevIds, itemId]); // Add itemId to selected items
      console.log(itemsToDelete);
    } else {
      setItemsToDelete((prevIds) => prevIds.filter((id) => id !== itemId)); // Remove itemId from selected items
      console.log(itemsToDelete);
    } 
  };

  const handleSubmit = async () => {
    for (const itemId of itemsToDelete) {
      try {
        await fetch(`http://localhost:8080/server/item/delete/${itemId}`, {
          method: 'DELETE'
        });
        console.log(`Item with ID ${itemId} deleted successfully.`);
      } catch (error) {
        console.error(`Failed to delete item with ID ${itemId}:`, error);
      }
    }
    notifySuccess();
    setTimeout(()=> {
        onClose();
        window.location.reload();

    }, 1000);
    
  }
  

  return (
    <>
      <div className={styles.backgroundDiv}
        onClick={handleOutsideClick}
      >
        <div className={styles.formDiv}>
          <div className={styles.closingButtonArea}> <IconButton onClick={onClose} aria-label="close"> <CloseIcon /> </IconButton> </div>
          <h2>Ware exportieren</h2>
          <header className={styles.popupHeader}>
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
                  {regale.map(regal =>(
                    <option value={regal.id}>{regal.name}</option>
                  ))}
            </select>

          </header>
          <div className={styles.tableContainer}>

            <table className="table table-striped table-hover table-bordered" id="table-panel">
              <thead>
                <tr>
                  <th> Name </th>
                  <th> ID </th>
                  <th> Größe </th>
                  <th> Preis </th>
                  <th> Kategorie </th>
                  <th> Regal ID </th>
                  <th> Auswählen </th>
                </tr>
              </thead>
              <tbody>
                { items.map(item =>(
                  <tr>
                      <th> {item.name} </th>
                      <th> {item.id} </th>
                      <th> {item.space} </th>
                      <th> {item.price} </th>
                      <th> {item.category} </th>
                      <th> {item.containerID} </th>
                      <th> <input type="checkbox"
                          onChange={(e) => handleCheckboxChange(e, item.id)}
                          checked={itemsToDelete.includes(item.id)}
                      />
                      </th>
                  </tr>
                  
                )
                  )
                }
              </tbody>
            </table>

        </div>
          
          
        <footer className={styles.formFooter}>
          <Button type="submit" onClick={handleSubmit}>Waren exportieren</Button>
        </footer>

        </div>
      </div>
    </>  
  );
};

export default PopupExport;
