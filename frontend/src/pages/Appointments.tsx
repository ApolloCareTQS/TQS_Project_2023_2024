import { IonButton, IonCard, IonCardContent, IonCol, IonContent, IonGrid, IonHeader, IonIcon, IonInput, IonItem, IonItemGroup, IonLabel, IonList, IonModal, IonPage, IonRow, IonSearchbar, IonSelect, IonSelectOption, IonTitle, IonToolbar } from '@ionic/react';
import ExploreContainer from '../components/ExploreContainer';
import './Appointments.css';
import { create, funnel } from 'ionicons/icons';
import axios from 'axios';
import { useEffect, useState } from 'react';
import { backendURI } from '../App';

const Appointments: React.FC = () => {
  const [historyData, setHistoryData] = useState([]);
  const [scheduledData, setScheduledData] = useState([]);
  const [searchQueryScheduled, setSearchQueryScheduled] = useState('');
  const [searchQueryHistory, setSearchQueryHistory] = useState('');
  const [filterCriteriaScheduled, setFilterCriteriaScheduled] = useState({ specialty: '', date: '', hospital: '' });
  const [filterCriteriaHistory, setFilterCriteriaHistory] = useState({ specialty: '', date: '', hospital: '' });
  const [showFilterScheduled, setShowFilterScheduled] = useState(false);
  const [showFilterHistory, setShowFilterHistory] = useState(false);

  const getHistory = () => {
    axios.get('/api/v1/user/history', {withCredentials: true})
      .then(function (response) {
        setHistoryData(response.data);
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  const getSchedule = () => {
    axios.get('/api/v1/user/scheduled', {withCredentials: true})
      .then(function (response) {
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

  const filterData = (data, query, criteria) => {
    return data.filter(item => {
      const matchesSearchQuery = Object.values(item).some(value => 
        value.toString().toLowerCase().includes(query.toLowerCase())
      );
      const matchesDate = criteria.date ? new Date(item.scheduledDate).toLocaleDateString() === new Date(criteria.date).toLocaleDateString() : true;
      const matchesHospital = criteria.hospital ? item.location.toLowerCase() === criteria.hospital.toLowerCase() : true;

      return matchesSearchQuery && matchesDate && matchesHospital;
    });
  };

  const handleSearchChangeScheduled = (e) => {
    setSearchQueryScheduled(e.target.value);
  };

  const handleSearchChangeHistory = (e) => {
    setSearchQueryHistory(e.target.value);
  };

  const handleFilterChangeScheduled = (e, type) => {
    setFilterCriteriaScheduled({ ...filterCriteriaScheduled, [type]: e.target.value });
  };

  const handleFilterChangeHistory = (e, type) => {
    setFilterCriteriaHistory({ ...filterCriteriaHistory, [type]: e.target.value });
  };

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
            <IonGrid>
              <IonRow>
                <IonCol className='appt_col'>
                  <IonSearchbar mode="ios" placeholder="Search for records..." value={searchQueryScheduled} onIonInput={handleSearchChangeScheduled}></IonSearchbar>
                </IonCol>
                <IonCol size='auto' className='appt_col'>
                    <IonButton className="appt_button" onClick={() => setShowFilterScheduled(true)}>
                      <IonIcon icon={funnel} /> Filters
                    </IonButton>
                  <IonModal isOpen={showFilterScheduled} onDidDismiss={() => setShowFilterScheduled(false)}>
                    <IonHeader>
                      <IonTitle>Filter by:</IonTitle>
                    </IonHeader>
                    <IonContent className='ion-padding'>
                      <IonItemGroup>
                        <IonItem>
                          <IonInput label="Date" type="date" value={filterCriteriaScheduled.date} onIonChange={(e) => handleFilterChangeScheduled(e, 'date')}></IonInput>
                        </IonItem>
                        <IonItem>
                          <IonInput label="Hospital" placeholder="LISBON" value={filterCriteriaScheduled.hospital} onIonChange={(e) => handleFilterChangeScheduled(e, 'hospital')}></IonInput>
                        </IonItem>
                        <IonButton onClick={() => setShowFilterScheduled(false)}>Apply Filters</IonButton>
                      </IonItemGroup>
                    </IonContent>
                  </IonModal>
                </IonCol>
              </IonRow>
              <IonRow>
                <IonCol>
                  <IonLabel>
                    <h1>Specialty</h1>
                  </IonLabel>
                </IonCol>
                <IonCol>
                  <IonLabel>
                    <h1>Date</h1>
                  </IonLabel>
                </IonCol>
                <IonCol>
                  <IonLabel>
                    <h1>Hour</h1>
                  </IonLabel>
                </IonCol>
                <IonCol>
                  <IonLabel>
                    <h1>Doctor</h1>
                  </IonLabel>
                </IonCol>
                <IonCol>
                  <IonLabel>
                    <h1>Hospital</h1>
                  </IonLabel>
                </IonCol>
                <IonCol></IonCol>
              </IonRow>
              {filterData(scheduledData, searchQueryScheduled, filterCriteriaScheduled).map((item, index) => (
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
              {filterData(scheduledData, searchQueryScheduled, filterCriteriaScheduled).length === 0 ? (<IonLabel>You don't have any upcoming consultations.</IonLabel>) : <></>}
            </IonGrid>
          </IonCardContent>
        </IonCard>
        <IonCard>
          <IonCardContent>
            <IonTitle>Consultations history</IonTitle>
            <IonGrid>
              <IonRow>
                <IonCol className='appt_col'>
                  <IonSearchbar mode="ios" placeholder="Search for records..." value={searchQueryHistory} onIonInput={handleSearchChangeHistory}></IonSearchbar>
                </IonCol>
                <IonCol size='auto' className='appt_col'>
                  <IonButton className='appt_button' onClick={() => setShowFilterHistory(true)}>
                    <IonIcon icon={funnel} /> Filters
                  </IonButton>
                  <IonModal isOpen={showFilterHistory} onDidDismiss={() => setShowFilterHistory(false)}>
                    <IonHeader>
                      <IonTitle>Filter by:</IonTitle>
                    </IonHeader>
                    <IonContent className='ion-padding'>
                      <IonItemGroup>
                        <IonItem>
                          <IonInput label="Date" type="date" value={filterCriteriaHistory.date} onIonChange={(e) => handleFilterChangeHistory(e, 'date')}></IonInput>
                        </IonItem>
                        <IonItem>
                          <IonInput label="Hospital" placeholder="LISBON" value={filterCriteriaHistory.hospital} onIonChange={(e) => handleFilterChangeHistory(e, 'hospital')}></IonInput>
                        </IonItem>
                      </IonItemGroup>
                      <IonButton onClick={() => setShowFilterHistory(false)}>Apply Filters</IonButton>
                    </IonContent>
                  </IonModal>
                </IonCol>
              </IonRow>
              <IonRow>
                <IonCol>
                  <IonLabel>
                    <h1>Specialty</h1>
                  </IonLabel>
                </IonCol>
                <IonCol>
                  <IonLabel>
                    <h1>Date</h1>
                  </IonLabel>
                </IonCol>
                <IonCol>
                  <IonLabel>
                    <h1>Hour</h1>
                  </IonLabel>
                </IonCol>
                <IonCol>
                  <IonLabel>
                    <h1>Doctor</h1>
                  </IonLabel>
                </IonCol>
                <IonCol>
                  <IonLabel>
                    <h1>Hospital</h1>
                  </IonLabel>
                </IonCol>
                <IonCol></IonCol>
              </IonRow>
              {filterData(historyData, searchQueryHistory, filterCriteriaHistory).map((item, index) => (
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
              {filterData(historyData, searchQueryHistory, filterCriteriaHistory).length === 0 ? (<IonLabel>You don't have any history data.</IonLabel>) : <></>}
            </IonGrid>
          </IonCardContent>
        </IonCard>
      </IonContent>
    </IonPage>
  );
};

export default Appointments;
