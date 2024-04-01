import { Sidebar, Menu, MenuItem, SubMenu } from 'react-pro-sidebar';
import '../.././index.css';
import { SideBarData } from './SidebarData';
import {Row} from 'react-bootstrap';


function SidebarMenu(){
    return (
   
            <div className="SideBar">
                <ul className="SideBarList">
                    <li className="row">
                        <div className="sidebarIconDiv"> <h3> LVS </h3> </div> 

                    </li>
                {SideBarData.map((val, key) =>{
                return(
                    <li 
                    key={key} 
                    className='row' 
                    id={window.location.pathname == val.link ? "active" : ""} 
                    onClick={() => {window.location.pathname = val.link}
                    }>
                        {" "}
                        <div className="sidebarIconDiv">{val.icon}</div> {" "}

                    </li>
                )
            })}

                </ul>
            </div>

            
    );
}

export default SidebarMenu;

