import { IonContent, IonHeader, IonPage, IonTitle, IonToolbar } from '@ionic/react';
import ExploreContainer from '../components/ExploreContainer';
import './Schedule.css';
import ScheduleFormContainer from '../components/ScheduleFormContainer';

const Schedule: React.FC = () => {
  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>Schedule</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent fullscreen>
        <ScheduleFormContainer />
      </IonContent>
    </IonPage>
  );
};

export default Schedule;
