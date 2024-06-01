import { IonButton, IonCard, IonCardContent, IonCol, IonContent, IonGrid, IonHeader, IonIcon, IonItem, IonItemGroup, IonLabel, IonLifeCycleContext, IonList, IonPage, IonRow, IonSearchbar, IonTitle, IonToolbar } from '@ionic/react';
import ExploreContainer from '../components/ExploreContainer';
import './Appointments.css';
import { create } from 'ionicons/icons';
import axios from 'axios';
import { useEffect, useState } from 'react';
import { backendURI } from '../App';


const Appointments: React.FC = () => {
  const [historyData, setHistoryData] = useState([]);
  const [scheduledData, setScheduledData] = useState([]);

  const getHistory = () => {
    axios.get('/api/v1/user/history', {withCredentials: true})
      .then(function (response) {
        console.log(response.data);
        setHistoryData(response.data);
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  const getSchedule = () => {
    axios.get('/api/v1/user/scheduled', {withCredentials: true})
      .then(function (response) {
        console.log(response.data);
        setScheduledData(response.data);
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  useEffect(() => {
    getHistory();
    getSchedule();
  }, []);

  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle color='primary'>ApolloCare</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent>
        <IonCard>
          <IonCardContent>
            <IonTitle>Upcoming consultations</IonTitle>
            <IonSearchbar color='primary' placeholder="Search for records..."></IonSearchbar>
            <IonGrid>
              <IonRow>
                <IonCol>
                  <IonLabel>
                    <h1>
                      Speciality
                    </h1>
                  </IonLabel>
                </IonCol>
                <IonCol>
                  <IonLabel>
                    <h1>
                      Date
                    </h1>
                  </IonLabel>
                </IonCol>
                <IonCol>
                  <IonLabel>
                    <h1>
                      Hour
                    </h1>
                  </IonLabel>
                </IonCol>
                <IonCol>
                  <IonLabel>
                    <h1>
                      Doctor
                    </h1>
                  </IonLabel>
                </IonCol>
                <IonCol>
                  <IonLabel>
                    <h1>
                      Hospital
                    </h1>
                  </IonLabel>
                </IonCol>
                <IonCol></IonCol>
              </IonRow>
              {scheduledData.map((item, index) => (
                <IonRow key={index}>
                  <IonCol className='appt_col'>
                    <IonLabel>{item.specialty}</IonLabel>
                  </IonCol>
                  <IonCol className='appt_col'>
                    <IonLabel>{new Date(item.scheduledDate).toLocaleDateString()}</IonLabel>
                  </IonCol>
                  <IonCol className='appt_col'>
                    <IonLabel>{new Date(item.scheduledDate).toLocaleTimeString()}</IonLabel>
                  </IonCol>
                  <IonCol className='appt_col'>
                    <IonLabel>{item.doctorId}</IonLabel>
                  </IonCol>
                  <IonCol className='appt_col'>
                    <IonLabel>{item.location}</IonLabel>
                  </IonCol>
                  <IonCol>
                    <IonButton className='appt_button' fill='outline'><IonIcon icon={create}></IonIcon></IonButton>
                  </IonCol>
                </IonRow>
              ))}
            </IonGrid>
          </IonCardContent>
        </IonCard>
        <IonCard>
          <IonCardContent>
            <IonTitle>Consulations history</IonTitle>
            <IonSearchbar color='primary' placeholder="Search for records..."></IonSearchbar>
            <IonGrid>
              <IonRow>
                <IonCol>
                  <IonLabel>
                    <h1>
                      Speciality
                    </h1>
                  </IonLabel>
                </IonCol>
                <IonCol>
                  <IonLabel>
                    <h1>
                      Date
                    </h1>
                  </IonLabel>
                </IonCol>
                <IonCol>
                  <IonLabel>
                    <h1>
                      Hour
                    </h1>
                  </IonLabel>
                </IonCol>
                <IonCol>
                  <IonLabel>
                    <h1>
                      Doctor
                    </h1>
                  </IonLabel>
                </IonCol>
                <IonCol>
                  <IonLabel>
                    <h1>
                      Hospital
                    </h1>
                  </IonLabel>
                </IonCol>
                <IonCol></IonCol>
              </IonRow>
              {historyData.map((item, index) => (
                <IonRow key={index}>
                  <IonCol className='appt_col'>
                    <IonLabel>{item.specialty}</IonLabel>
                  </IonCol>
                  <IonCol className='appt_col'>
                    <IonLabel>{new Date(item.scheduledDate).toLocaleDateString()}</IonLabel>
                  </IonCol>
                  <IonCol className='appt_col'>
                    <IonLabel>{new Date(item.scheduledDate).toLocaleTimeString()}</IonLabel>
                  </IonCol>
                  <IonCol className='appt_col'>
                    <IonLabel>{item.doctorId}</IonLabel>
                  </IonCol>
                  <IonCol className='appt_col'>
                    <IonLabel>{item.location}</IonLabel>
                  </IonCol>
                  <IonCol>
                    <IonButton className='appt_button' fill='outline'><IonIcon icon={create}></IonIcon></IonButton>
                  </IonCol>
                </IonRow>
              ))}
            </IonGrid>
          </IonCardContent>
        </IonCard>
      </IonContent>
    </IonPage>
  );
};

export default Appointments;
