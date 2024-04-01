import React, { useEffect, useRef, useState } from 'react';
import {Row, Col, Form} from 'react-bootstrap';
import Dropdown from 'react-bootstrap/Dropdown';
import DropdownButton from 'react-bootstrap/DropdownButton';
import Button from 'react-bootstrap/Button';
import styles from './styles/PopupForm.module.css';
import CloseIcon from '@mui/icons-material/Close';
import IconButton from '@mui/material/IconButton';
import { Key } from '@mui/icons-material';
import { Bounce, ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import CreateCategoryPopup from './CreateCategoryPopup';

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

const PopupForm = ({ onClose }) => {

    // Form variables

    const [name, setName] = useState('');
    const [quantity, setQuantity] = useState('');
    const [space, setspace] = useState('');
    const [price, setPrice] = useState('');
    const [category, setCategory] = useState('Kategorie auswählen');
    const [categories, setCategories] = useState([]);
    const [containerID, setContainerID] = useState('Regal auswählen');
    const [containers, setContainers] = useState([]);
    
  



    
    const handleOutsideClick = (e) => {
      if (e.target === e.currentTarget) {
        onClose();
      }
    };



  

  const handleClick = async (e) => {
    e.preventDefault();
    const itemData = { name, price, space, category, containerID};
    console.log(JSON.stringify(itemData));

    // Füge Items hinzu

    for (let i = 0; i < Number(quantity); i++){

        await fetch("http://localhost:8080/server/item/save",{
          method: "POST",
          headers:{"Content-Type": "application/json"},
          body: JSON.stringify(itemData)
        });
    }
    notifySuccess();
    setTimeout(()=> {
        //onClose();
        window.location.reload();

    }, 1000);

  }

  // lade Kategorien
  useEffect(() => {
    fetch("http://localhost:8080/server/category/list")
    .then(response => response.json())
    .then((responseData) => {
      setCategories(responseData.data.categories);
    })
  }, []);

  // lade Container

  useEffect(() => {
    fetch("http://localhost:8080/server/container/list")
    .then(response => response.json())
    .then((responseData) => {
      setContainers(responseData.data.containers);
    })
  }, []);
  




  return (

    
    <>
      <script src="https://cdn.jsdelivr.net/npm/react/umd/react.production.min.js" crossorigin></script>

      <script src="https://cdn.jsdelivr.net/npm/react-dom/umd/react-dom.production.min.js" crossorigin></script>

      <script src="https://cdn.jsdelivr.net/npm/react-bootstrap@next/dist/react-bootstrap.min.js" crossorigin></script>

      <script>var Alert = ReactBootstrap.Alert;</script>
      <div className={styles.backgroundDiv}
        onClick={handleOutsideClick}
      >
      <ToastContainer
        position="top-right"
        autoClose={5000}
        hideProgressBar
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="colored"
        transition={Bounce}
        />
        <div className={styles.formDiv}>
          <div className={styles.closingButtonArea}> 
            <IconButton onClick={onClose} aria-label="close"> 
              <CloseIcon /> 
            </IconButton> 
            </div>
          <h2>Neue Ware erfassen</h2>
          <form>
            <Row>
              <div className='col d-grid gap-3'>
                <div className='row '>
                  <span className={styles.inputSpan}>
                    <input type="text" id="name" name="name" 
                    placeholder='Name' value={name} 
                    onChange={(e) => setName(e.target.value)}
                    />
                  </span>
                </div>
                <div className='row '>

                  <span className={styles.inputSpan}>
                    <input type="number" id="quantity" name="quantity" 
                    placeholder='Quantität' value={quantity} 
                    onChange={(e) => setQuantity(e.target.value)}
                    />
                  </span>
                </div>
                <div className='row '>
                  <span className={styles.inputSpan}>
                    <input type="number" id="space" name="space" 
                    placeholder='Größe' value={space} 
                    onChange={(e) => setspace(e.target.value)}
                    />
                  </span>
                </div>


              </div>
              <div className='col d-grid gap-3'>
                <div className='row '>
                    <span className={styles.inputSpan}>
                      <input type="number" id="price" name="price" 
                      placeholder='Preis' value={price}
                      onChange={(e) => setPrice(e.target.value)}
                      />
                    </span>
                </div> 
                <div className='row btn-row'>
                  <Form.Select aria-label="Default select example" 
                  onChange={(e)=>{setCategory(e.target.value)}}

                  >
                    <option>Kategorie auswählen</option>
                    <option value='CREATE'>Kategorie erstellen...</option>
                    
                    
                    { 
                    categories.map(category => (
                      <option value={category.name}>{category.name}</option>
                    )) }
                  </Form.Select>
                </div>
                <div className='row btn-row'>
                  <Form.Select aria-label="Default select example"
                  onChange={(e)=>{setContainerID(e.target.value)}}
                  >
                    <option>Regal auswählen</option>
                    {
                      containers.map(container => (
                        <option value={container.id}>{container.name}</option>
                      ))
                    }
                  </Form.Select>
                </div>  
              </div>
            </Row>
            <footer className={styles.formFooter}>
              <Button type="submit" onClick={handleClick}>Ware hinzufügen</Button>
            </footer>
            
            

          </form> 
        </div>
      </div>
     
    </>  
  );
};

export default PopupForm;
