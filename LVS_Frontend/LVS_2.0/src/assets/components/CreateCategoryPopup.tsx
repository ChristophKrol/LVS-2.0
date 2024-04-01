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



    const [name, setName] = useState('');
    const [categories, setCategories] = useState([]);
    const [isCreateCategoryOpen, setIsCreateCategoryOpen] = useState(false);

    const openCreateCategory = () => {
      setIsCreateCategoryOpen(true);
    }
    const closeCreateCategory = () => {
      setIsCreateCategoryOpen(false);
    }
  



    
    const handleOutsideClick = (e) => {
      if (e.target === e.currentTarget) {
        onClose();
      }
    };



  

  const handleClick = async (e) => {
    e.preventDefault();
    const data = { name };
    console.log(JSON.stringify(data));

    // Füge Kategorie hinzu

        await fetch("http://localhost:8080/server/category/save",{
          method: "POST",
          headers:{"Content-Type": "application/json"},
          body: JSON.stringify(data)
        });

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

  return (

    
    <>


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
          <h2>Neue Kategorie erstellen</h2>
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
               </div>
            </Row>
            <footer className={styles.formFooter}>
              <Button type="submit" onClick={handleClick}>Kategorie hinzufügen</Button>
            </footer>

          </form> 
        </div>
      </div>
    </>  
  );
};

export default PopupForm;
