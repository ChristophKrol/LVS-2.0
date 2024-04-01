import { useState } from "react";
import { Button, Col, Stack } from "react-bootstrap";
import styles from "./styles/PopupForm.module.css"
import IconButton from "@mui/material/IconButton";
import CloseIcon from '@mui/icons-material/Close';
import { Bounce, toast } from "react-toastify";

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

const PopupCreateRegal = ({onClose}) => {

    const[name, setName] = useState('');
    const [maxCapacity, setMaxCapacity] = useState('');

    const handleOutsideClick = (e) => {
        if (e.target === e.currentTarget) {
          onClose();
        }
    };

    const submitForm = (e) => {
        e.preventDefault();

        // Regal Attribute, die Default zum Erstellen sind 
        const curCapacity = 0;
        const items = [];

        const regalData = {maxCapacity, curCapacity, name, items}

        fetch("http://localhost:8080/server/container/save",{
          method: "POST",
          headers:{"Content-Type": "application/json"},
          body: JSON.stringify(regalData)
        }).then(() => {
          notifySuccess();
          setTimeout(()=> {
            onClose();
            window.location.reload();
          }, 1000);
        });
    }

    return(
        <>
        <div className={styles.backgroundDiv}
          onClick={handleOutsideClick}
        >
          <div className={styles.formDiv} id='regalForm'>
            <div className={styles.closingButtonArea}> 
                <IconButton onClick={onClose} aria-label="close"> 
                    <CloseIcon /> </IconButton> 
            </div>
            <h2> Lagerfläche hinzufügen </h2>
            <form>
                <Stack gap={3} className="col-md-3 mx-auto">
                    <input type="text" id="name" name="name" 
                        placeholder='Name' value={name} 
                        onChange={(e) => setName(e.target.value)}
                    />
                    <input type="number" id="space" name="space" 
                        placeholder='Größe' value={maxCapacity} 
                        onChange={(e) => setMaxCapacity(e.target.value)}
                    />
                </Stack>
            
            </form>
            <footer className={styles.formFooter}>
              <Button type="submit" onClick={submitForm}>Lager hinzufügen</Button>
            </footer>
            
            
          </div>
            
  
          </div>
     
      </> 
    );
}

export default PopupCreateRegal;