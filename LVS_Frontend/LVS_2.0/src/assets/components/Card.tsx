import { Button } from "react-bootstrap";

function Card(){
     return(
        <div className="card">
            <h2> Regal 1 </h2>
            <h3> Gesamte Kapazität: </h3>
            <p>500</p>
            <h3> Auslastung: </h3>
            <p> 45% </p>
            <Button variant="primary">Mehr</Button>{' '}

        </div>
     );
}

export default Card;

