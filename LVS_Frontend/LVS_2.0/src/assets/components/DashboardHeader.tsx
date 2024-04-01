import styles from './styles/DashboardHeader.module.css';
const DashboardHeader = (props) => {
   const dashboardData = props.kpiData;
   const kpis = dashboardData.kpis;
 

    return(
        <header>
        <h1>{props.title}</h1>
        <div className={styles.header}>
            { kpis.map((kpi) => (
                <span>
                    <h2> {kpi.name}</h2> 
                    <h2> {kpi.value} </h2> 
                </span>

            )

            ) 
            }
          
        </div>

      </header>
    );

    
}
export default DashboardHeader