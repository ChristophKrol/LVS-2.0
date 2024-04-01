import Card from 'react-bootstrap/Card';
import ListGroup from 'react-bootstrap/ListGroup';
import { Button, Col, Container, Row } from 'react-bootstrap';

import styles from './styles/Lagerflaechen.module.css';
import SidebarMenu from '../assets/components/SidebarMenu';
import DashboardHeader from '../assets/components/DashboardHeader';
import { useEffect, useState } from 'react';
import PopupRegal from '../assets/components/PopupRegal';
import PopupCreateRegal from '../assets/components/PopupCreateRegal';
import Footer from '../Footer';





function Lagerflaechen(){

    const [isPopupOpen, setIsPopupOpen] = useState(false);
    const [popupID, setPopupID] = useState(0);
    const[isCreateRegalOpen, setIsCreateRegalOpen] = useState(false);
    const [containers, setContainers]=  useState([]);
    const [containersCount, setContainersCount] = useState(0);
    const [capacityTotal, setCapacityTotal] = useState(0);

    const headerData = {
        kpis: [
          {
            name: 'Anzahl Regale', 
            value: containersCount
          },
          {
            name: 'Kapazität gesamt', 
            value: capacityTotal
          },
    
        ]
      }

    // Render Containers
    useEffect(() => {
        fetch("http://localhost:8080/server/container/list")
        .then(response => response.json())
        .then((responseData) => {
            const containerData = responseData.data.containers;
          setContainers(containerData);
          setContainersCount(containerData.length);
          const totalCapacity = containerData.reduce((accumulator, container) => accumulator + container.maxCapacity, 0);
          setCapacityTotal(totalCapacity);

        })
      }, []);

    const openCreateRegalPopup = () => {
        setIsCreateRegalOpen(true);
    }

    const closeCreateRegalPopup = () => {
        setIsCreateRegalOpen(false);
    }

    const openPopup = (containerID: number) => {
      setPopupID(containerID);
     setIsPopupOpen(true);
    }
    const closePopup = () => {
      setIsPopupOpen(false);
    }

    
    return(
        <>
            <SidebarMenu/>
            <div style={{padding: "0px 0px 0px 100px"}} className={styles.wrapper}>
                <DashboardHeader title = "Lagerflächen" kpiData = {headerData}/>
                <main className={styles.regalSection}>
                    <Row className='g-4 justify-content-center'>
                        { containers.map(container =>(
                            <Col>
                                <Card style={{ width: '18rem', padding: '0 0 0 0 ' }}>
                                <Card.Header as="h5" className="text-center">{container.name}</Card.Header>
                                    <Card.Body className="text-center">
                                        <Card.Title>Gesamte Kapazität:</Card.Title>
                                        <Card.Text> {container.maxCapacity} </Card.Text>
                                        <Card.Title>Auslastung:</Card.Title>
                                        <Card.Text> {Math.round(container.curCapacity / container.maxCapacity * 100) + '%'} </Card.Text>
                                        <Button variant="primary" onClick={() => openPopup(container.id)}> Mehr </Button>
                                        
                                    </Card.Body>
                                </Card>
                            </Col>
                        )) 
                      }    
                    </Row>
                    {isPopupOpen && < PopupRegal regalID={popupID} onClose={closePopup}  />}
                    
                    
                </main>
                <Container fluid style={{display: 'flex',justifyContent: 'center'}}>
                    <Button variant="primary" onClick={openCreateRegalPopup}> Lagerfläche hinzufügen </Button>
                    {isCreateRegalOpen && <PopupCreateRegal onClose={closeCreateRegalPopup} />}
                </Container>
                <Footer/>
            </div>
            
            
        </>
        
    );

}

export default Lagerflaechen;

