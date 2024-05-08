import { IonButton, IonCard, IonCardContent, IonCol, IonContent, IonGrid, IonHeader, IonIcon, IonItem, IonItemGroup, IonLabel, IonLifeCycleContext, IonList, IonPage, IonRow, IonSearchbar, IonTitle, IonToolbar } from '@ionic/react';
import ExploreContainer from '../components/ExploreContainer';
import './Tab3.css';
import { create } from 'ionicons/icons';

const Tab3: React.FC = () => {
  const agendaData = [
    {
      speciality: 'Cardiology',
      date: '2024-05-05',
      hour: '10:00 AM',
      doctor: 'Dr. Smith',
      hospital: 'General Hospital'
    },
    {
      speciality: 'Dermatology',
      date: '2024-05-06',
      hour: '02:30 PM',
      doctor: 'Dr. Johnson',
      hospital: 'City Clinic'
    },
    {
      speciality: 'Orthopedics',
      date: '2024-05-07',
      hour: '11:15 AM',
      doctor: 'Dr. Williams',
      hospital: 'OrthoCare Hospital'
    },
    {
      speciality: 'Ophthalmology',
      date: '2024-05-08',
      hour: '09:45 AM',
      doctor: 'Dr. Anderson',
      hospital: 'Eye Institute'
    },
    {
      speciality: 'Pediatrics',
      date: '2024-05-09',
      hour: '03:00 PM',
      doctor: 'Dr. Martinez',
      hospital: 'Children\'s Hospital'
    },
    {
      speciality: 'Neurology',
      date: '2024-05-10',
      hour: '01:00 PM',
      doctor: 'Dr. Lee',
      hospital: 'Neuro Clinic'
    },
    {
      speciality: 'Gastroenterology',
      date: '2024-05-11',
      hour: '04:45 PM',
      doctor: 'Dr. Brown',
      hospital: 'Gastro Health Center'
    }
  ];

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
              {agendaData.map((item, index) => (
                <IonRow key={index}>
                  <IonCol className='appt_col'>
                    <IonLabel>{item.speciality}</IonLabel>
                  </IonCol>
                  <IonCol className='appt_col'>
                    <IonLabel>{item.date}</IonLabel>
                  </IonCol>
                  <IonCol className='appt_col'>
                    <IonLabel>{item.hour}</IonLabel>
                  </IonCol>
                  <IonCol className='appt_col'>
                    <IonLabel>{item.doctor}</IonLabel>
                  </IonCol>
                  <IonCol className='appt_col'>
                    <IonLabel>{item.hospital}</IonLabel>
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

export default Tab3;
