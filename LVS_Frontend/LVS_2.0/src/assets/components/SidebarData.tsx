import React from "react";
import HomeIcon from '@mui/icons-material/Home';
import Home from "@mui/icons-material/Home";
import ExitToAppIcon from '@mui/icons-material/ExitToApp';
import ExitToApp from "@mui/icons-material/ExitToApp";
import InputIcon from '@mui/icons-material/Input';
import { Input, Output } from "@mui/icons-material";
import WarehouseIcon from '@mui/icons-material/Warehouse';
import Warehouse from "@mui/icons-material/Warehouse";

export const SideBarData = [
    {
        icon: <Home fontSize="large"/>,
        link:"/"
    },
    {
        icon: <Warehouse fontSize="large"/>,
        link:"/lagerflaechen"
    },
    {
        icon: <Input fontSize="large"/>,
        link:"/wareneingang"
    },
    {
        icon: <Output fontSize="large"/>,
        link:"/warenausgang"
    }

]

